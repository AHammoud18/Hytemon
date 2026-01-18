/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import it.unimi.dsi.fastutil.longs.LongArraySet;
/*    */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*    */ import it.unimi.dsi.fastutil.longs.LongSet;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NViewport
/*    */ {
/*    */   @Nonnull
/*    */   private final World world;
/*    */   @Nonnull
/*    */   private final CommandSender sender;
/*    */   @Nonnull
/*    */   private final LongSet affectedChunkIndices;
/*    */   
/*    */   public NViewport(@Nonnull Bounds3i viewportBounds_voxelGrid, @Nonnull World world, @Nonnull CommandSender sender) {
/* 28 */     this.world = world;
/* 29 */     this.sender = sender;
/*    */     
/* 31 */     int minCX = ChunkUtil.chunkCoordinate(viewportBounds_voxelGrid.min.x);
/* 32 */     int minCZ = ChunkUtil.chunkCoordinate(viewportBounds_voxelGrid.min.z);
/* 33 */     int maxCX = ChunkUtil.chunkCoordinate(viewportBounds_voxelGrid.max.x);
/* 34 */     int maxCZ = ChunkUtil.chunkCoordinate(viewportBounds_voxelGrid.max.z);
/* 35 */     this.affectedChunkIndices = (LongSet)new LongArraySet();
/*    */     
/* 37 */     for (int x = minCX; x <= maxCX; x++) {
/* 38 */       for (int z = minCZ; z <= maxCZ; z++) {
/* 39 */         long chunkIndex = ChunkUtil.indexChunk(x, z);
/* 40 */         this.affectedChunkIndices.add(chunkIndex);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void refresh() {
/* 46 */     LoggerUtil.getLogger().info("Refreshing viewport...");
/*    */     
/* 48 */     CompletableFuture[] arrayOfCompletableFuture = new CompletableFuture[this.affectedChunkIndices.size()];
/*    */     
/* 50 */     int i = 0;
/* 51 */     for (LongIterator<Long> longIterator = this.affectedChunkIndices.iterator(); longIterator.hasNext(); ) { long chunkIndex = ((Long)longIterator.next()).longValue();
/* 52 */       ChunkStore chunkStore = this.world.getChunkStore();
/* 53 */       CompletableFuture<?> future = chunkStore.getChunkReferenceAsync(chunkIndex, 9);
/* 54 */       arrayOfCompletableFuture[i++] = future; }
/*    */ 
/*    */     
/* 57 */     CompletableFuture.allOf((CompletableFuture<?>[])arrayOfCompletableFuture)
/* 58 */       .handle((r, e) -> {
/*    */           if (e == null) {
/*    */             return r;
/*    */           }
/*    */           
/*    */           LoggerUtil.logException("viewport refresh", e);
/*    */           return null;
/* 65 */         }).thenRun(() -> LoggerUtil.getLogger().info("Viewport refresh complete."));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\NViewport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */