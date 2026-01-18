/*    */ package com.hypixel.hytale.builtin.hytalegenerator.bounds;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class SpaceSize
/*    */ {
/*    */   @Nonnull
/*    */   private final Vector3i minInclusive;
/*    */   @Nonnull
/*    */   private final Vector3i maxExclusive;
/*    */   @Nonnull
/*    */   private final Vector3i maxInclusive;
/*    */   
/*    */   public SpaceSize(@Nonnull Vector3i minInclusive, @Nonnull Vector3i maxExclusive) {
/* 20 */     this.minInclusive = minInclusive.clone();
/* 21 */     this.maxExclusive = maxExclusive.clone();
/* 22 */     this.maxInclusive = maxExclusive.clone().add(-1, -1, -1);
/*    */   }
/*    */   
/*    */   public SpaceSize(@Nonnull Vector3i voxel) {
/* 26 */     this(voxel.clone(), voxel.clone().add(1, 1, 1));
/*    */   }
/*    */   
/*    */   public SpaceSize() {
/* 30 */     this(new Vector3i(), new Vector3i());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public SpaceSize moveBy(@Nonnull Vector3i delta) {
/* 35 */     this.minInclusive.add(delta);
/* 36 */     this.maxExclusive.add(delta);
/* 37 */     this.maxInclusive.add(delta);
/* 38 */     return this;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3i getMinInclusive() {
/* 43 */     return this.minInclusive.clone();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3i getMaxExclusive() {
/* 48 */     return this.maxExclusive.clone();
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3i getMaxInclusive() {
/* 53 */     return this.maxInclusive.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3i getRange() {
/* 60 */     Vector3i absMin = VectorUtil.fromOperation(value -> Math.abs(value.from(this.minInclusive)));
/* 61 */     Vector3i absMax = VectorUtil.fromOperation(value -> Math.abs(value.from(this.maxInclusive)));
/* 62 */     return Vector3i.max(absMin, absMax);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceSize clone() {
/* 68 */     return new SpaceSize(this.minInclusive, this.maxExclusive);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SpaceSize merge(@Nonnull SpaceSize a, @Nonnull SpaceSize b) {
/* 73 */     return new SpaceSize(Vector3i.min(a.minInclusive, b.minInclusive), Vector3i.max(a.maxExclusive, b.maxExclusive));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static SpaceSize stack(@Nonnull SpaceSize a, @Nonnull SpaceSize b) {
/* 79 */     SpaceSize aMovedToMin = a.clone().moveBy(b.minInclusive);
/* 80 */     SpaceSize aMovedToMax = a.clone().moveBy(b.maxInclusive);
/*    */     
/* 82 */     Vector3i stackedMin = Vector3i.min(aMovedToMin.minInclusive, b.minInclusive);
/* 83 */     Vector3i stackedMax = Vector3i.max(aMovedToMax.maxExclusive, b.maxExclusive);
/* 84 */     return new SpaceSize(stackedMin, stackedMax);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SpaceSize empty() {
/* 89 */     return new SpaceSize(new Vector3i(), new Vector3i());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\bounds\SpaceSize.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */