/*     */ package com.hypixel.hytale.procedurallib.supplier;
/*     */ 
/*     */ import java.util.Random;
/*     */ import java.util.function.DoubleSupplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class Normal
/*     */   implements IDoubleRange
/*     */ {
/*     */   protected final double min;
/*     */   protected final double range;
/*     */   
/*     */   public Normal(double min, double max) {
/*  65 */     this.min = min;
/*  66 */     this.range = max - min;
/*     */   }
/*     */   
/*     */   public double getMin() {
/*  70 */     return this.min;
/*     */   }
/*     */   
/*     */   public double getRange() {
/*  74 */     return this.range;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getValue(double v) {
/*  79 */     return this.min + this.range * v;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getValue(@Nonnull DoubleSupplier supplier) {
/*  84 */     return this.min + this.range * supplier.getAsDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getValue(@Nonnull Random random) {
/*  89 */     return getValue(random.nextDouble());
/*     */   }
/*     */ 
/*     */   
/*     */   public double getValue(int seed, double x, double y, @Nonnull IDoubleCoordinateSupplier2d supplier) {
/*  94 */     return this.min + this.range * supplier.get(seed, x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getValue(int seed, double x, double y, double z, @Nonnull IDoubleCoordinateSupplier3d supplier) {
/*  99 */     return this.min + this.range * supplier.get(seed, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 105 */     return "DoubleRange.Normal{min=" + this.min + ", range=" + this.range + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\supplier\DoubleRange$Normal.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */