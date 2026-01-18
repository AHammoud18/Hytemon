/*     */ package com.hypixel.hytale.procedurallib.supplier;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FloatRange
/*     */ {
/*  11 */   public static final Constant ZERO = new Constant(0.0F);
/*  12 */   public static final Constant ONE = new Constant(1.0F);
/*     */   
/*     */   public static class Constant implements IFloatRange {
/*     */     protected final float result;
/*     */     
/*     */     public Constant(float result) {
/*  18 */       this.result = result;
/*     */     }
/*     */     
/*     */     public float getResult() {
/*  22 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getValue(float v) {
/*  27 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getValue(FloatSupplier supplier) {
/*  32 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getValue(Random random) {
/*  37 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getValue(int seed, double x, double y, IDoubleCoordinateSupplier2d supplier) {
/*  42 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getValue(int seed, double x, double y, double z, IDoubleCoordinateSupplier3d supplier) {
/*  47 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/*  53 */       return "FloatRange.Constant{result=" + this.result + "}";
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Normal
/*     */     implements IFloatRange {
/*     */     protected final float min;
/*     */     protected final float range;
/*     */     
/*     */     public Normal(float min, float max) {
/*  63 */       this.min = min;
/*  64 */       this.range = max - min;
/*     */     }
/*     */     
/*     */     public float getMin() {
/*  68 */       return this.min;
/*     */     }
/*     */     
/*     */     public float getRange() {
/*  72 */       return this.range;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getValue(float v) {
/*  77 */       return this.min + this.range * v;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getValue(@Nonnull FloatSupplier supplier) {
/*  82 */       return this.min + this.range * supplier.getAsFloat();
/*     */     }
/*     */ 
/*     */     
/*     */     public float getValue(@Nonnull Random random) {
/*  87 */       return getValue(random.nextFloat());
/*     */     }
/*     */ 
/*     */     
/*     */     public float getValue(int seed, double x, double y, @Nonnull IDoubleCoordinateSupplier2d supplier) {
/*  92 */       return this.min + this.range * (float)supplier.get(seed, x, y);
/*     */     }
/*     */ 
/*     */     
/*     */     public float getValue(int seed, double x, double y, double z, @Nonnull IDoubleCoordinateSupplier3d supplier) {
/*  97 */       return this.min + this.range * (float)supplier.get(seed, x, y, z);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 103 */       return "FloatRange.Normal{min=" + this.min + ", range=" + this.range + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\supplier\FloatRange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */