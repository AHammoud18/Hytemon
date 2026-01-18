/*     */ package com.hypixel.hytale.server.core.universe.world.storage.provider;
/*     */ 
/*     */ import com.hypixel.fastutil.longs.Long2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.system.StoreSystem;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.metrics.MetricProvider;
/*     */ import com.hypixel.hytale.metrics.MetricResults;
/*     */ import com.hypixel.hytale.metrics.MetricsRegistry;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.BufferChunkLoader;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.BufferChunkSaver;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkLoader;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkSaver;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import com.hypixel.hytale.storage.IndexedStorageFile;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntListIterator;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSets;
/*     */ import java.io.Closeable;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.file.FileAlreadyExistsException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class IndexedStorageChunkStorageProvider
/*     */   implements IChunkStorageProvider
/*     */ {
/*     */   public static final String ID = "IndexedStorage";
/*     */   @Nonnull
/*  55 */   public static final BuilderCodec<IndexedStorageChunkStorageProvider> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(IndexedStorageChunkStorageProvider.class, IndexedStorageChunkStorageProvider::new)
/*  56 */     .documentation("Uses the indexed storage file format to store chunks."))
/*  57 */     .build();
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IChunkLoader getLoader(@Nonnull Store<ChunkStore> store) {
/*  62 */     return (IChunkLoader)new IndexedStorageChunkLoader(store);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public IChunkSaver getSaver(@Nonnull Store<ChunkStore> store) {
/*  67 */     return (IChunkSaver)new IndexedStorageChunkSaver(store);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  73 */     return "IndexedStorageChunkStorageProvider{}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class IndexedStorageChunkLoader
/*     */     extends BufferChunkLoader
/*     */     implements MetricProvider
/*     */   {
/*     */     public IndexedStorageChunkLoader(@Nonnull Store<ChunkStore> store) {
/*  87 */       super(store);
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/*  92 */       ((IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType())).close();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CompletableFuture<ByteBuffer> loadBuffer(int x, int z) {
/*  98 */       int regionX = x >> 5;
/*  99 */       int regionZ = z >> 5;
/* 100 */       int localX = x & 0x1F;
/* 101 */       int localZ = z & 0x1F;
/* 102 */       int index = ChunkUtil.indexColumn(localX, localZ);
/*     */       
/* 104 */       IndexedStorageChunkStorageProvider.IndexedStorageCache indexedStorageCache = (IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType());
/* 105 */       return CompletableFuture.supplyAsync(SneakyThrow.sneakySupplier(() -> {
/*     */               IndexedStorageFile chunks = indexedStorageCache.getOrTryOpen(regionX, regionZ);
/*     */               return (chunks == null) ? null : chunks.readBlob(index);
/*     */             }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public LongSet getIndexes() throws IOException {
/* 115 */       return ((IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType())).getIndexes();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public MetricResults toMetricResults() {
/* 122 */       if (((ChunkStore)getStore().getExternalData()).getSaver() instanceof IndexedStorageChunkStorageProvider.IndexedStorageChunkSaver) return null; 
/* 123 */       return ((IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType())).toMetricResults();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class IndexedStorageChunkSaver
/*     */     extends BufferChunkSaver
/*     */     implements MetricProvider
/*     */   {
/*     */     protected IndexedStorageChunkSaver(@Nonnull Store<ChunkStore> store) {
/* 133 */       super(store);
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 138 */       IndexedStorageChunkStorageProvider.IndexedStorageCache indexedStorageCache = (IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType());
/* 139 */       indexedStorageCache.close();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CompletableFuture<Void> saveBuffer(int x, int z, @Nonnull ByteBuffer buffer) {
/* 145 */       int regionX = x >> 5;
/* 146 */       int regionZ = z >> 5;
/* 147 */       int localX = x & 0x1F;
/* 148 */       int localZ = z & 0x1F;
/* 149 */       int index = ChunkUtil.indexColumn(localX, localZ);
/*     */       
/* 151 */       IndexedStorageChunkStorageProvider.IndexedStorageCache indexedStorageCache = (IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType());
/* 152 */       return CompletableFuture.runAsync(SneakyThrow.sneakyRunnable(() -> {
/*     */               IndexedStorageFile chunks = indexedStorageCache.getOrCreate(regionX, regionZ);
/*     */               chunks.writeBlob(index, buffer);
/*     */             }));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CompletableFuture<Void> removeBuffer(int x, int z) {
/* 161 */       int regionX = x >> 5;
/* 162 */       int regionZ = z >> 5;
/* 163 */       int localX = x & 0x1F;
/* 164 */       int localZ = z & 0x1F;
/* 165 */       int index = ChunkUtil.indexColumn(localX, localZ);
/*     */       
/* 167 */       IndexedStorageChunkStorageProvider.IndexedStorageCache indexedStorageCache = (IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType());
/* 168 */       return CompletableFuture.runAsync(SneakyThrow.sneakyRunnable(() -> {
/*     */               IndexedStorageFile chunks = indexedStorageCache.getOrTryOpen(regionX, regionZ);
/*     */               if (chunks != null)
/*     */                 chunks.removeBlob(index); 
/*     */             }));
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public LongSet getIndexes() throws IOException {
/* 177 */       return ((IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType())).getIndexes();
/*     */     }
/*     */ 
/*     */     
/*     */     public void flush() throws IOException {
/* 182 */       ((IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType())).flush();
/*     */     }
/*     */ 
/*     */     
/*     */     public MetricResults toMetricResults() {
/* 187 */       return ((IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType())).toMetricResults();
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static String toFileName(int regionX, int regionZ) {
/* 193 */     return "" + regionX + "." + regionX + ".region.bin";
/*     */   }
/*     */   
/*     */   private static long fromFileName(@Nonnull String fileName) {
/* 197 */     String[] split = fileName.split("\\.");
/*     */     
/* 199 */     if (split.length != 4) throw new IllegalArgumentException("Unexpected file name format!"); 
/* 200 */     if (!"region".equals(split[2])) throw new IllegalArgumentException("Unexpected file name format!"); 
/* 201 */     if (!"bin".equals(split[3])) throw new IllegalArgumentException("Unexpected file extension!");
/*     */     
/* 203 */     int regionX = Integer.parseInt(split[0]);
/* 204 */     int regionZ = Integer.parseInt(split[1]);
/* 205 */     return ChunkUtil.indexChunk(regionX, regionZ);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class IndexedStorageCache
/*     */     implements Closeable, MetricProvider, Resource<ChunkStore>
/*     */   {
/*     */     @Nonnull
/*     */     public static final MetricsRegistry<IndexedStorageCache> METRICS_REGISTRY;
/*     */ 
/*     */     
/*     */     static {
/* 218 */       METRICS_REGISTRY = (new MetricsRegistry()).register("Files", cache -> (CacheEntryMetricData[])cache.cache.long2ObjectEntrySet().stream().map(CacheEntryMetricData::new).toArray(()), (Codec)new ArrayCodec(CacheEntryMetricData.CODEC, x$0 -> new CacheEntryMetricData[x$0]));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static ResourceType<ChunkStore, IndexedStorageCache> getResourceType() {
/* 224 */       return Universe.get().getIndexedStorageCacheResourceType();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     private final Long2ObjectConcurrentHashMap<IndexedStorageFile> cache = new Long2ObjectConcurrentHashMap(true, ChunkUtil.NOT_FOUND);
/*     */ 
/*     */     
/*     */     private Path path;
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Long2ObjectConcurrentHashMap<IndexedStorageFile> getCache() {
/* 240 */       return this.cache;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 245 */       IOException exception = null;
/* 246 */       for (Iterator<IndexedStorageFile> iterator = this.cache.values().iterator(); iterator.hasNext();) {
/*     */         try {
/* 248 */           ((IndexedStorageFile)iterator.next()).close();
/* 249 */           iterator.remove();
/* 250 */         } catch (Exception e) {
/* 251 */           if (exception == null) exception = new IOException("Failed to close one or more loaders!"); 
/* 252 */           exception.addSuppressed(e);
/*     */         } 
/*     */       } 
/* 255 */       if (exception != null) throw exception;
/*     */     
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public IndexedStorageFile getOrTryOpen(int regionX, int regionZ) {
/* 267 */       return (IndexedStorageFile)this.cache.computeIfAbsent(ChunkUtil.indexChunk(regionX, regionZ), k -> {
/*     */             Path regionFile = this.path.resolve(IndexedStorageChunkStorageProvider.toFileName(regionX, regionZ));
/*     */             
/*     */             if (!Files.exists(regionFile, new java.nio.file.LinkOption[0])) {
/*     */               return null;
/*     */             }
/*     */             try {
/*     */               return IndexedStorageFile.open(regionFile, new OpenOption[] { StandardOpenOption.READ, StandardOpenOption.WRITE });
/* 275 */             } catch (FileNotFoundException e) {
/*     */               return null;
/* 277 */             } catch (IOException e) {
/*     */               throw SneakyThrow.sneakyThrow(e);
/*     */             } 
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public IndexedStorageFile getOrCreate(int regionX, int regionZ) {
/* 292 */       return (IndexedStorageFile)this.cache.computeIfAbsent(ChunkUtil.indexChunk(regionX, regionZ), k -> {
/*     */             try {
/*     */               if (!Files.exists(this.path, new java.nio.file.LinkOption[0])) {
/*     */                 try {
/*     */                   Files.createDirectory(this.path, (FileAttribute<?>[])new FileAttribute[0]);
/* 297 */                 } catch (FileAlreadyExistsException fileAlreadyExistsException) {}
/*     */               }
/*     */               
/*     */               Path regionFile = this.path.resolve(IndexedStorageChunkStorageProvider.toFileName(regionX, regionZ));
/*     */               
/*     */               return IndexedStorageFile.open(regionFile, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE });
/* 303 */             } catch (IOException e) {
/*     */               throw SneakyThrow.sneakyThrow(e);
/*     */             } 
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public LongSet getIndexes() throws IOException {
/* 317 */       if (!Files.exists(this.path, new java.nio.file.LinkOption[0])) return (LongSet)LongSets.EMPTY_SET; 
/* 318 */       LongOpenHashSet chunkIndexes = new LongOpenHashSet();
/* 319 */       Stream<Path> stream = Files.list(this.path); 
/* 320 */       try { stream.forEach(path -> {
/*     */               long regionIndex;
/*     */               if (Files.isDirectory(path, new java.nio.file.LinkOption[0]))
/*     */                 return; 
/*     */               try {
/*     */                 regionIndex = IndexedStorageChunkStorageProvider.fromFileName(path.getFileName().toString());
/* 326 */               } catch (IllegalArgumentException e) {
/*     */                 return;
/*     */               } 
/*     */               
/*     */               int regionX = ChunkUtil.xOfChunkIndex(regionIndex);
/*     */               int regionZ = ChunkUtil.zOfChunkIndex(regionIndex);
/*     */               IndexedStorageFile regionFile = getOrTryOpen(regionX, regionZ);
/*     */               if (regionFile == null) {
/*     */                 return;
/*     */               }
/*     */               IntList blobIndexes = regionFile.keys();
/*     */               IntListIterator iterator = blobIndexes.iterator();
/*     */               while (iterator.hasNext()) {
/*     */                 int blobIndex = iterator.nextInt();
/*     */                 int localX = ChunkUtil.xFromColumn(blobIndex);
/*     */                 int localZ = ChunkUtil.zFromColumn(blobIndex);
/*     */                 int chunkX = regionX << 5 | localX;
/*     */                 int chunkZ = regionZ << 5 | localZ;
/*     */                 chunkIndexes.add(ChunkUtil.indexChunk(chunkX, chunkZ));
/*     */               } 
/*     */             });
/* 347 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/* 348 */           try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return (LongSet)chunkIndexes;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void flush() throws IOException {
/* 357 */       IOException exception = null;
/*     */       
/* 359 */       for (IndexedStorageFile indexedStorageFile : this.cache.values()) {
/*     */         try {
/* 361 */           indexedStorageFile.force(false);
/* 362 */         } catch (Exception e) {
/* 363 */           if (exception == null) exception = new IOException("Failed to close one or more loaders!"); 
/* 364 */           exception.addSuppressed(e);
/*     */         } 
/*     */       } 
/* 367 */       if (exception != null) throw exception;
/*     */     
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public MetricResults toMetricResults() {
/* 373 */       return METRICS_REGISTRY.toMetricResults(this);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Resource<ChunkStore> clone() {
/* 379 */       return new IndexedStorageCache();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static class CacheEntryMetricData
/*     */     {
/*     */       @Nonnull
/*     */       private static final Codec<CacheEntryMetricData> CODEC;
/*     */ 
/*     */ 
/*     */       
/*     */       private long key;
/*     */ 
/*     */ 
/*     */       
/*     */       private IndexedStorageFile value;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       static {
/* 402 */         CODEC = (Codec<CacheEntryMetricData>)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CacheEntryMetricData.class, CacheEntryMetricData::new).append(new KeyedCodec("Key", (Codec)Codec.LONG), (entry, o) -> entry.key = o.longValue(), entry -> Long.valueOf(entry.key)).add()).append(new KeyedCodec("File", (Codec)IndexedStorageFile.METRICS_REGISTRY), (entry, o) -> entry.value = o, entry -> entry.value).add()).build();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public CacheEntryMetricData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public CacheEntryMetricData(@Nonnull Long2ObjectMap.Entry<IndexedStorageFile> entry)
/*     */       {
/* 422 */         this.key = entry.getLongKey();
/* 423 */         this.value = (IndexedStorageFile)entry.getValue(); } } } private static class CacheEntryMetricData { @Nonnull private static final Codec<CacheEntryMetricData> CODEC; public CacheEntryMetricData(@Nonnull Long2ObjectMap.Entry<IndexedStorageFile> entry) { this.key = entry.getLongKey(); this.value = (IndexedStorageFile)entry.getValue(); }
/*     */     
/*     */     private long key; private IndexedStorageFile value;
/*     */     
/*     */     static {
/*     */       CODEC = (Codec<CacheEntryMetricData>)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CacheEntryMetricData.class, CacheEntryMetricData::new).append(new KeyedCodec("Key", (Codec)Codec.LONG), (entry, o) -> entry.key = o.longValue(), entry -> Long.valueOf(entry.key)).add()).append(new KeyedCodec("File", (Codec)IndexedStorageFile.METRICS_REGISTRY), (entry, o) -> entry.value = o, entry -> entry.value).add()).build();
/*     */     }
/*     */     
/*     */     public CacheEntryMetricData() {} }
/*     */   
/*     */   public static class IndexedStorageCacheSetupSystem extends StoreSystem<ChunkStore> { @Nullable
/*     */     public SystemGroup<ChunkStore> getGroup() {
/* 435 */       return ChunkStore.INIT_GROUP;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onSystemAddedToStore(@Nonnull Store<ChunkStore> store) {
/* 442 */       World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 443 */       ((IndexedStorageChunkStorageProvider.IndexedStorageCache)store.getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType())).path = world.getSavePath().resolve("chunks");
/*     */     }
/*     */     
/*     */     public void onSystemRemovedFromStore(@Nonnull Store<ChunkStore> store) {} }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\provider\IndexedStorageChunkStorageProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */