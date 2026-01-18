/*     */ package com.hypixel.hytale.math.shape;
/*     */ 
/*     */ import com.hypixel.hytale.function.predicate.TriIntObjPredicate;
/*     */ import com.hypixel.hytale.function.predicate.TriIntPredicate;
/*     */ import com.hypixel.hytale.math.block.BlockCylinderUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Cylinder
/*     */   implements Shape
/*     */ {
/*     */   public double height;
/*     */   public double radiusX;
/*     */   public double radiusZ;
/*     */   
/*     */   public Cylinder() {}
/*     */   
/*     */   public Cylinder(double height, double radiusX, double radiusZ) {
/*  24 */     this.height = height;
/*  25 */     this.radiusX = radiusX;
/*  26 */     this.radiusZ = radiusZ;
/*     */   }
/*     */   
/*     */   public double getRadiusX() {
/*  30 */     return this.radiusX;
/*     */   }
/*     */   
/*     */   public double getRadiusZ() {
/*  34 */     return this.radiusZ;
/*     */   }
/*     */   
/*     */   public double getHeight() {
/*  38 */     return this.height;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Cylinder assign(double radius) {
/*  43 */     this.radiusX = radius;
/*  44 */     this.radiusZ = radius;
/*  45 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsPosition(double x, double y, double z) {
/*  50 */     if (y > this.height || y < 0.0D) return false;
/*     */ 
/*     */     
/*  53 */     double result = x * x / this.radiusX * this.radiusX + z * z / this.radiusZ * this.radiusZ;
/*     */     
/*  55 */     return (result <= 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean forEachBlock(double x, double y, double z, double epsilon, @Nonnull TriIntPredicate consumer) {
/*  61 */     return BlockCylinderUtil.forEachBlock(
/*  62 */         MathUtil.floor(x), MathUtil.floor(y), MathUtil.floor(z), 
/*  63 */         MathUtil.floor(this.radiusX + epsilon), MathUtil.floor(this.height + epsilon), MathUtil.floor(this.radiusZ + epsilon), null, (_x, _y, _z, aVoid) -> consumer.test(_x, _y, _z));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> boolean forEachBlock(double x, double y, double z, double epsilon, T t, @Nonnull TriIntObjPredicate<T> consumer) {
/*  69 */     return BlockCylinderUtil.forEachBlock(
/*  70 */         MathUtil.floor(x), MathUtil.floor(y), MathUtil.floor(z), 
/*  71 */         MathUtil.floor(this.radiusX + epsilon), MathUtil.floor(this.height + epsilon), MathUtil.floor(this.radiusZ + epsilon), t, consumer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void expand(double radius) {
/*  77 */     this.radiusX += radius;
/*  78 */     this.radiusZ += radius;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Box getBox(double x, double y, double z) {
/*  84 */     double biggestRadius = Math.max(this.radiusX, this.radiusZ);
/*     */     
/*  86 */     Box boundingBox = new Box();
/*  87 */     boundingBox.min.assign(x - biggestRadius, y, z - biggestRadius);
/*  88 */     boundingBox.max.assign(x + biggestRadius, y + this.height, z + biggestRadius);
/*  89 */     return boundingBox;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Cylinder clone() {
/*  95 */     return new Cylinder(this.height, this.radiusX, this.radiusZ);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 101 */     return "Cylinder{height=" + this.height + ", radiusX=" + this.radiusX + ", radiusZ=" + this.radiusZ + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\shape\Cylinder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */