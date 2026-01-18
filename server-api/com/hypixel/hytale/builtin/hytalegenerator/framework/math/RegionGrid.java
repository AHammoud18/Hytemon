/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.math;
/*    */ 
/*    */ public class RegionGrid {
/*    */   private int regionSizeX;
/*    */   private int regionSizeZ;
/*    */   
/*    */   public RegionGrid(int regionSizeX, int regionSizeZ) {
/*  8 */     this.regionSizeX = regionSizeX;
/*  9 */     this.regionSizeZ = regionSizeZ;
/*    */   }
/*    */   
/*    */   public int regionMinX(int chunkX) {
/* 13 */     if (chunkX >= 0) {
/* 14 */       return chunkX / this.regionSizeX * this.regionSizeX;
/*    */     }
/* 16 */     return (chunkX - this.regionSizeZ - 1) / this.regionSizeX * this.regionSizeX;
/*    */   }
/*    */ 
/*    */   
/*    */   public int regionMinZ(int chunkZ) {
/* 21 */     if (chunkZ >= 0) {
/* 22 */       return chunkZ / this.regionSizeZ * this.regionSizeZ;
/*    */     }
/* 24 */     return (chunkZ - this.regionSizeX - 1) / this.regionSizeZ * this.regionSizeZ;
/*    */   }
/*    */   
/*    */   public int regionMaxX(int chunkX) {
/* 28 */     return regionMinX(chunkX) + this.regionSizeX;
/*    */   }
/*    */   
/*    */   public int regionMaxZ(int chunkZ) {
/* 32 */     return regionMinZ(chunkZ) + this.regionSizeZ;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\math\RegionGrid.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */