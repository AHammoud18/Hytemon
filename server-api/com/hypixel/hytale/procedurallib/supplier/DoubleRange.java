/*     */ package com.hypixel.hytale.procedurallib.supplier;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import java.util.function.DoubleSupplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleRange
/*     */ {
/*  13 */   public static final Constant ZERO = new Constant(0.0D);
/*  14 */   public static final Constant ONE = new Constant(1.0D);
/*     */   
/*     */   public static class Constant implements IDoubleRange {
/*     */     protected final double result;
/*     */     
/*     */     public Constant(double result) {
/*  20 */       this.result = result;
/*     */     }
/*     */     
/*     */     public double getResult() {
/*  24 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(double v) {
/*  29 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(DoubleSupplier supplier) {
/*  34 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(Random random) {
/*  39 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(int seed, double x, double y, IDoubleCoordinateSupplier2d supplier) {
/*  44 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(int seed, double x, double y, double z, IDoubleCoordinateSupplier3d supplier) {
/*  49 */       return this.result;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/*  55 */       return "DoubleRange.Constant{result=" + this.result + "}";
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Normal
/*     */     implements IDoubleRange {
/*     */     protected final double min;
/*     */     protected final double range;
/*     */     
/*     */     public Normal(double min, double max) {
/*  65 */       this.min = min;
/*  66 */       this.range = max - min;
/*     */     }
/*     */     
/*     */     public double getMin() {
/*  70 */       return this.min;
/*     */     }
/*     */     
/*     */     public double getRange() {
/*  74 */       return this.range;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(double v) {
/*  79 */       return this.min + this.range * v;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(@Nonnull DoubleSupplier supplier) {
/*  84 */       return this.min + this.range * supplier.getAsDouble();
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(@Nonnull Random random) {
/*  89 */       return getValue(random.nextDouble());
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(int seed, double x, double y, @Nonnull IDoubleCoordinateSupplier2d supplier) {
/*  94 */       return this.min + this.range * supplier.get(seed, x, y);
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(int seed, double x, double y, double z, @Nonnull IDoubleCoordinateSupplier3d supplier) {
/*  99 */       return this.min + this.range * supplier.get(seed, x, y, z);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 105 */       return "DoubleRange.Normal{min=" + this.min + ", range=" + this.range + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Multiple
/*     */     implements IDoubleRange
/*     */   {
/*     */     protected final double[] thresholds;
/*     */     protected final double[] values;
/*     */     
/*     */     public Multiple(double[] thresholds, double[] values) {
/* 117 */       this.thresholds = thresholds;
/* 118 */       this.values = values;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(double v) {
/* 123 */       if (v > this.thresholds[this.thresholds.length - 1]) return this.values[this.values.length - 1];
/*     */       
/* 125 */       double min = 0.0D;
/*     */       
/* 127 */       for (int i = 0; i < this.thresholds.length; i++) {
/* 128 */         double max = this.thresholds[i];
/*     */         
/* 130 */         if (v < max) {
/* 131 */           if (i == 0) return this.values[0];
/*     */           
/* 133 */           double alpha = (v - min) / (max - min);
/* 134 */           double valueMin = this.values[i - 1];
/* 135 */           double valueMax = this.values[i];
/* 136 */           double range = valueMax - valueMin;
/* 137 */           return valueMin + alpha * range;
/*     */         } 
/*     */         
/* 140 */         min = max;
/*     */       } 
/*     */       
/* 143 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(@Nonnull DoubleSupplier supplier) {
/* 148 */       return getValue(supplier.getAsDouble());
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(@Nonnull Random random) {
/* 153 */       return getValue(random.nextDouble());
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(int seed, double x, double y, @Nonnull IDoubleCoordinateSupplier2d supplier) {
/* 158 */       return getValue(supplier.get(seed, x, y));
/*     */     }
/*     */ 
/*     */     
/*     */     public double getValue(int seed, double x, double y, double z, @Nonnull IDoubleCoordinateSupplier3d supplier) {
/* 163 */       return getValue(supplier.get(seed, x, y, z));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 170 */       return "Multiple{thresholds=" + Arrays.toString(this.thresholds) + ", values=" + 
/* 171 */         Arrays.toString(this.values) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\supplier\DoubleRange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */