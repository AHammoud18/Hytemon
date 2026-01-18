/*     */ package com.hypixel.hytale.server.core.universe.world.storage.provider;
/*     */ 
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.metrics.MetricProvider;
/*     */ import com.hypixel.hytale.metrics.MetricResults;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.BufferChunkSaver;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import com.hypixel.hytale.storage.IndexedStorageFile;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class IndexedStorageChunkSaver
/*     */   extends BufferChunkSaver
/*     */   implements MetricProvider
/*     */ {
/*     */   protected IndexedStorageChunkSaver(@Nonnull Store<ChunkStore> store) {
/* 133 */     super(store);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 138 */     IndexedStorageChunkStorageProvider.IndexedStorageCache indexedStorageCache = (IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType());
/* 139 */     indexedStorageCache.close();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<Void> saveBuffer(int x, int z, @Nonnull ByteBuffer buffer) {
/* 145 */     int regionX = x >> 5;
/* 146 */     int regionZ = z >> 5;
/* 147 */     int localX = x & 0x1F;
/* 148 */     int localZ = z & 0x1F;
/* 149 */     int index = ChunkUtil.indexColumn(localX, localZ);
/*     */     
/* 151 */     IndexedStorageChunkStorageProvider.IndexedStorageCache indexedStorageCache = (IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType());
/* 152 */     return CompletableFuture.runAsync(SneakyThrow.sneakyRunnable(() -> {
/*     */             IndexedStorageFile chunks = indexedStorageCache.getOrCreate(regionX, regionZ);
/*     */             chunks.writeBlob(index, buffer);
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<Void> removeBuffer(int x, int z) {
/* 161 */     int regionX = x >> 5;
/* 162 */     int regionZ = z >> 5;
/* 163 */     int localX = x & 0x1F;
/* 164 */     int localZ = z & 0x1F;
/* 165 */     int index = ChunkUtil.indexColumn(localX, localZ);
/*     */     
/* 167 */     IndexedStorageChunkStorageProvider.IndexedStorageCache indexedStorageCache = (IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType());
/* 168 */     return CompletableFuture.runAsync(SneakyThrow.sneakyRunnable(() -> {
/*     */             IndexedStorageFile chunks = indexedStorageCache.getOrTryOpen(regionX, regionZ);
/*     */             if (chunks != null)
/*     */               chunks.removeBlob(index); 
/*     */           }));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public LongSet getIndexes() throws IOException {
/* 177 */     return ((IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType())).getIndexes();
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 182 */     ((IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType())).flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public MetricResults toMetricResults() {
/* 187 */     return ((IndexedStorageChunkStorageProvider.IndexedStorageCache)getStore().getResource(IndexedStorageChunkStorageProvider.IndexedStorageCache.getResourceType())).toMetricResults();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\provider\IndexedStorageChunkStorageProvider$IndexedStorageChunkSaver.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */