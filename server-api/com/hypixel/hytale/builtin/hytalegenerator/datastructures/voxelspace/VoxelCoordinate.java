/*    */ package com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VoxelCoordinate
/*    */ {
/*    */   int x;
/*    */   int y;
/*    */   int z;
/*    */   
/*    */   public VoxelCoordinate(int x, int y, int z) {
/* 15 */     this.x = x;
/* 16 */     this.y = y;
/* 17 */     this.z = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/*    */     VoxelCoordinate otherVoxelCoordinate;
/* 23 */     if (other instanceof VoxelCoordinate) { otherVoxelCoordinate = (VoxelCoordinate)other; }
/* 24 */     else { return false; }
/* 25 */      return (this == otherVoxelCoordinate || (this.x == otherVoxelCoordinate.x && this.y == otherVoxelCoordinate.y && this.z == otherVoxelCoordinate.z));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public VoxelCoordinate clone() {
/* 33 */     return new VoxelCoordinate(this.x, this.y, this.z);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 39 */     return "VoxelCoordinate{x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\voxelspace\VoxelCoordinate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */