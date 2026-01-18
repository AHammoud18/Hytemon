/*     */ package com.hypixel.hytale.server.core.universe.world.storage.provider;
/*     */ 
/*     */ import com.hypixel.fastutil.longs.Long2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.metrics.MetricProvider;
/*     */ import com.hypixel.hytale.metrics.MetricResults;
/*     */ import com.hypixel.hytale.metrics.MetricsRegistry;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ import java.nio.file.FileAlreadyExistsException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.Iterator;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IndexedStorageCache
/*     */   implements Closeable, MetricProvider, Resource<ChunkStore>
/*     */ {
/*     */   @Nonnull
/*     */   public static final MetricsRegistry<IndexedStorageCache> METRICS_REGISTRY;
/*     */   
/*     */   static {
/* 218 */     METRICS_REGISTRY = (new MetricsRegistry()).register("Files", cache -> (CacheEntryMetricData[])cache.cache.long2ObjectEntrySet().stream().map(CacheEntryMetricData::new).toArray(()), (Codec)new ArrayCodec(CacheEntryMetricData.CODEC, x$0 -> new CacheEntryMetricData[x$0]));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceType<ChunkStore, IndexedStorageCache> getResourceType() {
/* 224 */     return Universe.get().getIndexedStorageCacheResourceType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 231 */   private final Long2ObjectConcurrentHashMap<IndexedStorageFile> cache = new Long2ObjectConcurrentHashMap(true, ChunkUtil.NOT_FOUND);
/*     */ 
/*     */   
/*     */   private Path path;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Long2ObjectConcurrentHashMap<IndexedStorageFile> getCache() {
/* 240 */     return this.cache;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 245 */     IOException exception = null;
/* 246 */     for (Iterator<IndexedStorageFile> iterator = this.cache.values().iterator(); iterator.hasNext();) {
/*     */       try {
/* 248 */         ((IndexedStorageFile)iterator.next()).close();
/* 249 */         iterator.remove();
/* 250 */       } catch (Exception e) {
/* 251 */         if (exception == null) exception = new IOException("Failed to close one or more loaders!"); 
/* 252 */         exception.addSuppressed(e);
/*     */       } 
/*     */     } 
/* 255 */     if (exception != null) throw exception;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IndexedStorageFile getOrTryOpen(int regionX, int regionZ) {
/* 267 */     return (IndexedStorageFile)this.cache.computeIfAbsent(ChunkUtil.indexChunk(regionX, regionZ), k -> {
/*     */           Path regionFile = this.path.resolve(IndexedStorageChunkStorageProvider.toFileName(regionX, regionZ));
/*     */           
/*     */           if (!Files.exists(regionFile, new java.nio.file.LinkOption[0])) {
/*     */             return null;
/*     */           }
/*     */           try {
/*     */             return IndexedStorageFile.open(regionFile, new OpenOption[] { StandardOpenOption.READ, StandardOpenOption.WRITE });
/* 275 */           } catch (FileNotFoundException e) {
/*     */             return null;
/* 277 */           } catch (IOException e) {
/*     */             throw SneakyThrow.sneakyThrow(e);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IndexedStorageFile getOrCreate(int regionX, int regionZ) {
/* 292 */     return (IndexedStorageFile)this.cache.computeIfAbsent(ChunkUtil.indexChunk(regionX, regionZ), k -> {
/*     */           try {
/*     */             if (!Files.exists(this.path, new java.nio.file.LinkOption[0])) {
/*     */               try {
/*     */                 Files.createDirectory(this.path, (FileAttribute<?>[])new FileAttribute[0]);
/* 297 */               } catch (FileAlreadyExistsException fileAlreadyExistsException) {}
/*     */             }
/*     */             
/*     */             Path regionFile = this.path.resolve(IndexedStorageChunkStorageProvider.toFileName(regionX, regionZ));
/*     */             
/*     */             return IndexedStorageFile.open(regionFile, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE });
/* 303 */           } catch (IOException e) {
/*     */             throw SneakyThrow.sneakyThrow(e);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public LongSet getIndexes() throws IOException {
/* 317 */     if (!Files.exists(this.path, new java.nio.file.LinkOption[0])) return (LongSet)LongSets.EMPTY_SET; 
/* 318 */     LongOpenHashSet chunkIndexes = new LongOpenHashSet();
/* 319 */     Stream<Path> stream = Files.list(this.path); 
/* 320 */     try { stream.forEach(path -> {
/*     */             long regionIndex;
/*     */             if (Files.isDirectory(path, new java.nio.file.LinkOption[0]))
/*     */               return; 
/*     */             try {
/*     */               regionIndex = IndexedStorageChunkStorageProvider.fromFileName(path.getFileName().toString());
/* 326 */             } catch (IllegalArgumentException e) {
/*     */               return;
/*     */             } 
/*     */             
/*     */             int regionX = ChunkUtil.xOfChunkIndex(regionIndex);
/*     */             int regionZ = ChunkUtil.zOfChunkIndex(regionIndex);
/*     */             IndexedStorageFile regionFile = getOrTryOpen(regionX, regionZ);
/*     */             if (regionFile == null) {
/*     */               return;
/*     */             }
/*     */             IntList blobIndexes = regionFile.keys();
/*     */             IntListIterator iterator = blobIndexes.iterator();
/*     */             while (iterator.hasNext()) {
/*     */               int blobIndex = iterator.nextInt();
/*     */               int localX = ChunkUtil.xFromColumn(blobIndex);
/*     */               int localZ = ChunkUtil.zFromColumn(blobIndex);
/*     */               int chunkX = regionX << 5 | localX;
/*     */               int chunkZ = regionZ << 5 | localZ;
/*     */               chunkIndexes.add(ChunkUtil.indexChunk(chunkX, chunkZ));
/*     */             } 
/*     */           });
/* 347 */       if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/* 348 */         try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return (LongSet)chunkIndexes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 357 */     IOException exception = null;
/*     */     
/* 359 */     for (IndexedStorageFile indexedStorageFile : this.cache.values()) {
/*     */       try {
/* 361 */         indexedStorageFile.force(false);
/* 362 */       } catch (Exception e) {
/* 363 */         if (exception == null) exception = new IOException("Failed to close one or more loaders!"); 
/* 364 */         exception.addSuppressed(e);
/*     */       } 
/*     */     } 
/* 367 */     if (exception != null) throw exception;
/*     */   
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public MetricResults toMetricResults() {
/* 373 */     return METRICS_REGISTRY.toMetricResults(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Resource<ChunkStore> clone() {
/* 379 */     return new IndexedStorageCache();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CacheEntryMetricData
/*     */   {
/*     */     @Nonnull
/*     */     private static final Codec<CacheEntryMetricData> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     private long key;
/*     */ 
/*     */ 
/*     */     
/*     */     private IndexedStorageFile value;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 402 */       CODEC = (Codec<CacheEntryMetricData>)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CacheEntryMetricData.class, CacheEntryMetricData::new).append(new KeyedCodec("Key", (Codec)Codec.LONG), (entry, o) -> entry.key = o.longValue(), entry -> Long.valueOf(entry.key)).add()).append(new KeyedCodec("File", (Codec)IndexedStorageFile.METRICS_REGISTRY), (entry, o) -> entry.value = o, entry -> entry.value).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CacheEntryMetricData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CacheEntryMetricData(@Nonnull Long2ObjectMap.Entry<IndexedStorageFile> entry) {
/* 422 */       this.key = entry.getLongKey();
/* 423 */       this.value = (IndexedStorageFile)entry.getValue();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\provider\IndexedStorageChunkStorageProvider$IndexedStorageCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */