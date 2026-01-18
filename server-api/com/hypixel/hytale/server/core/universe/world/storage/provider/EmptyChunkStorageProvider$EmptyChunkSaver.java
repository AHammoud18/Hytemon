/*     */ package com.hypixel.hytale.server.core.universe.world.storage.provider;
/*     */ 
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkSaver;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSets;
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
/*     */ class EmptyChunkSaver
/*     */   implements IChunkSaver
/*     */ {
/*     */   public void close() {}
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<Void> saveHolder(int x, int z, @Nonnull Holder<ChunkStore> holder) {
/* 101 */     return CompletableFuture.completedFuture(null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<Void> removeHolder(int x, int z) {
/* 107 */     return CompletableFuture.completedFuture(null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public LongSet getIndexes() {
/* 113 */     return (LongSet)LongSets.EMPTY_SET;
/*     */   }
/*     */   
/*     */   public void flush() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\provider\EmptyChunkStorageProvider$EmptyChunkSaver.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */