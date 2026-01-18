/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.containers;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.GridUtils;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class FloatContainer3d
/*    */ {
/*    */   private final Bounds3i bounds_voxelGrid;
/*    */   private final Vector3i size_voxelGrid;
/*    */   private final float[] data;
/*    */   private final float outOfBoundsValue;
/*    */   
/*    */   public FloatContainer3d(@Nonnull Bounds3i bounds_voxelGrid, float outOfBoundsValue) {
/* 16 */     this.bounds_voxelGrid = bounds_voxelGrid.clone();
/* 17 */     this.size_voxelGrid = bounds_voxelGrid.getSize();
/* 18 */     this.data = new float[this.size_voxelGrid.x * this.size_voxelGrid.y * this.size_voxelGrid.z];
/* 19 */     this.outOfBoundsValue = outOfBoundsValue;
/*    */   }
/*    */   
/*    */   public float get(@Nonnull Vector3i position_voxelGrid) {
/* 23 */     if (!this.bounds_voxelGrid.contains(position_voxelGrid)) {
/* 24 */       return this.outOfBoundsValue;
/*    */     }
/* 26 */     int index = GridUtils.toIndexFromPositionYXZ(position_voxelGrid, this.bounds_voxelGrid);
/* 27 */     return this.data[index];
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Bounds3i getBounds_voxelGrid() {
/* 32 */     return this.bounds_voxelGrid;
/*    */   }
/*    */   
/*    */   public void set(@Nonnull Vector3i position_voxelGrid, float value) {
/* 36 */     assert this.bounds_voxelGrid.contains(position_voxelGrid);
/*    */     
/* 38 */     int index = GridUtils.toIndexFromPositionYXZ(position_voxelGrid, this.bounds_voxelGrid);
/* 39 */     this.data[index] = value;
/*    */   }
/*    */   
/*    */   public void moveMinTo(@Nonnull Vector3i min_voxelGrid) {
/* 43 */     Vector3i oldMin_voxelGrid = this.bounds_voxelGrid.min.clone().scale(-1);
/* 44 */     this.bounds_voxelGrid.offset(oldMin_voxelGrid);
/* 45 */     this.bounds_voxelGrid.offset(min_voxelGrid);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\containers\FloatContainer3d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */