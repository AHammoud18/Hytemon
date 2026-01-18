/*    */ package com.hypixel.hytale.server.core.universe.world.storage.provider;
/*    */ 
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.IChunkLoader;
/*    */ import it.unimi.dsi.fastutil.longs.LongSet;
/*    */ import it.unimi.dsi.fastutil.longs.LongSets;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class EmptyChunkLoader
/*    */   implements IChunkLoader
/*    */ {
/*    */   public void close() {}
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<Holder<ChunkStore>> loadHolder(int x, int z) {
/* 80 */     return CompletableFuture.completedFuture(null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public LongSet getIndexes() {
/* 86 */     return (LongSet)LongSets.EMPTY_SET;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\provider\EmptyChunkStorageProvider$EmptyChunkLoader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */