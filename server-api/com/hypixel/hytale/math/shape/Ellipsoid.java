/*    */ package com.hypixel.hytale.math.shape;
/*    */ 
/*    */ import com.hypixel.hytale.function.predicate.TriIntObjPredicate;
/*    */ import com.hypixel.hytale.function.predicate.TriIntPredicate;
/*    */ import com.hypixel.hytale.math.block.BlockSphereUtil;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ellipsoid
/*    */   implements Shape
/*    */ {
/*    */   public double radiusX;
/*    */   public double radiusY;
/*    */   public double radiusZ;
/*    */   
/*    */   public Ellipsoid() {}
/*    */   
/*    */   public Ellipsoid(double radius) {
/* 24 */     this(radius, radius, radius);
/*    */   }
/*    */   
/*    */   public Ellipsoid(double radiusX, double radiusY, double radiusZ) {
/* 28 */     this.radiusX = radiusX;
/* 29 */     this.radiusY = radiusY;
/* 30 */     this.radiusZ = radiusZ;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Ellipsoid assign(double radius) {
/* 35 */     this.radiusX = radius;
/* 36 */     this.radiusY = radius;
/* 37 */     this.radiusZ = radius;
/* 38 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Box getBox(double x, double y, double z) {
/* 44 */     Box boundingBox = new Box();
/* 45 */     boundingBox.min.assign(x - this.radiusX, y - this.radiusY, z - this.radiusZ);
/* 46 */     boundingBox.max.assign(x + this.radiusX, y + this.radiusY, z + this.radiusZ);
/* 47 */     return boundingBox;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean containsPosition(double x, double y, double z) {
/* 52 */     double xRatio = x / this.radiusX;
/* 53 */     double yRatio = y / this.radiusY;
/* 54 */     double zRatio = z / this.radiusZ;
/* 55 */     return (xRatio * xRatio + yRatio * yRatio + zRatio * zRatio <= 1.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public void expand(double radius) {
/* 60 */     this.radiusX += radius;
/* 61 */     this.radiusY += radius;
/* 62 */     this.radiusZ += radius;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean forEachBlock(double x, double y, double z, double epsilon, @Nonnull TriIntPredicate consumer) {
/* 68 */     return BlockSphereUtil.forEachBlock(
/* 69 */         MathUtil.floor(x), MathUtil.floor(y), MathUtil.floor(z), 
/* 70 */         MathUtil.floor(this.radiusX + epsilon), 
/* 71 */         MathUtil.floor(this.radiusY + epsilon), 
/* 72 */         MathUtil.floor(this.radiusZ + epsilon), null, (_x, _y, _z, aVoid) -> consumer.test(_x, _y, _z));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> boolean forEachBlock(double x, double y, double z, double epsilon, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/* 78 */     return BlockSphereUtil.forEachBlock(
/* 79 */         MathUtil.floor(x), MathUtil.floor(y), MathUtil.floor(z), 
/* 80 */         MathUtil.floor(this.radiusX + epsilon), 
/* 81 */         MathUtil.floor(this.radiusY + epsilon), 
/* 82 */         MathUtil.floor(this.radiusZ + epsilon), t, consumer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 89 */     return "Ellipsoid{radiusX=" + this.radiusX + ", radiusY=" + this.radiusY + ", radiusZ=" + this.radiusZ + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\shape\Ellipsoid.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */