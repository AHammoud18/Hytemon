/*     */ package com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BatchTransfer<T>
/*     */   implements Runnable
/*     */ {
/*     */   private final VoxelSpace<T> source;
/*     */   private final VoxelSpace<T> destination;
/*     */   private final int minX;
/*     */   private final int minY;
/*     */   private final int minZ;
/*     */   private final int maxX;
/*     */   private final int maxY;
/*     */   private final int maxZ;
/*     */   
/*     */   private BatchTransfer(VoxelSpace<T> source, VoxelSpace<T> destination, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
/* 101 */     this.source = source;
/* 102 */     this.destination = destination;
/* 103 */     this.minX = minX;
/* 104 */     this.minY = minY;
/* 105 */     this.minZ = minZ;
/* 106 */     this.maxX = maxX;
/* 107 */     this.maxY = maxY;
/* 108 */     this.maxZ = maxZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 125 */       for (int x = this.minX; x < this.maxX; x++) {
/* 126 */         for (int y = this.minY; y < this.maxY; y++)
/* 127 */         { for (int z = this.minZ; z < this.maxZ; z++)
/* 128 */           { if (this.destination.isInsideSpace(x, y, z))
/*     */             {
/* 130 */               this.destination.set(this.source.getContent(x, y, z), x, y, z); }  }  } 
/*     */       } 
/* 132 */     } catch (Exception e) {
/* 133 */       String msg = "Exception thrown by HytaleGenerator while attempting a BatchTransfer operation:\n";
/* 134 */       msg = msg + msg;
/* 135 */       LoggerUtil.getLogger().severe(msg);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\voxelspace\VoxelSpaceUtil$BatchTransfer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */