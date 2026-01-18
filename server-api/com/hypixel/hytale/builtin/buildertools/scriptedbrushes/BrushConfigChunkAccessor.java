/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*    */ import com.hypixel.hytale.server.core.universe.world.accessor.LocalCachedChunkAccessor;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BrushConfigChunkAccessor
/*    */   extends LocalCachedChunkAccessor
/*    */ {
/*    */   private final BrushConfigEditStore editOperation;
/*    */   
/*    */   @Nonnull
/*    */   public static BrushConfigChunkAccessor atWorldCoords(BrushConfigEditStore editOperation, ChunkAccessor<WorldChunk> delegate, int centerX, int centerZ, int blockRadius) {
/* 20 */     int chunkRadius = ChunkUtil.chunkCoordinate(blockRadius) + 1;
/* 21 */     return atChunkCoords(editOperation, delegate, ChunkUtil.chunkCoordinate(centerX), ChunkUtil.chunkCoordinate(centerZ), chunkRadius);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static BrushConfigChunkAccessor atChunkCoords(BrushConfigEditStore editOperation, ChunkAccessor<WorldChunk> delegate, int centerX, int centerZ, int chunkRadius) {
/* 27 */     return new BrushConfigChunkAccessor(editOperation, delegate, centerX, centerZ, chunkRadius);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected BrushConfigChunkAccessor(BrushConfigEditStore editOperation, ChunkAccessor<WorldChunk> delegate, int centerX, int centerZ, int radius) {
/* 33 */     super(delegate, centerX, centerZ, radius);
/* 34 */     this.editOperation = editOperation;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlock(@Nonnull Vector3i pos) {
/* 39 */     if (this.editOperation.getAfter().hasBlockAtWorldPos(pos.x, pos.y, pos.z)) {
/* 40 */       return this.editOperation.getAfter().getBlockAtWorldPos(pos.x, pos.y, pos.z);
/*    */     }
/*    */     
/* 43 */     return getBlockIgnoringHistory(pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlock(int x, int y, int z) {
/* 48 */     if (this.editOperation.getAfter().hasBlockAtWorldPos(x, y, z)) {
/* 49 */       return this.editOperation.getAfter().getBlockAtWorldPos(x, y, z);
/*    */     }
/* 51 */     return getBlockIgnoringHistory(x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBlockIgnoringHistory(@Nonnull Vector3i pos) {
/* 58 */     return getBlockIgnoringHistory(pos.x, pos.y, pos.z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getBlockIgnoringHistory(int x, int y, int z) {
/* 65 */     if (this.editOperation.getBefore().hasBlockAtWorldPos(x, y, z)) {
/* 66 */       return this.editOperation.getBefore().getBlockAtWorldPos(x, y, z);
/*    */     }
/* 68 */     return getChunk(ChunkUtil.indexChunkFromBlock(x, z)).getBlock(x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFluidId(int x, int y, int z) {
/* 73 */     return this.editOperation.getFluid(x, y, z);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\BrushConfigChunkAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */