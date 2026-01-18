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
/*     */ public class Multiple
/*     */   implements IDoubleRange
/*     */ {
/*     */   protected final double[] thresholds;
/*     */   protected final double[] values;
/*     */   
/*     */   public Multiple(double[] thresholds, double[] values) {
/* 117 */     this.thresholds = thresholds;
/* 118 */     this.values = values;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getValue(double v) {
/* 123 */     if (v > this.thresholds[this.thresholds.length - 1]) return this.values[this.values.length - 1];
/*     */     
/* 125 */     double min = 0.0D;
/*     */     
/* 127 */     for (int i = 0; i < this.thresholds.length; i++) {
/* 128 */       double max = this.thresholds[i];
/*     */       
/* 130 */       if (v < max) {
/* 131 */         if (i == 0) return this.values[0];
/*     */         
/* 133 */         double alpha = (v - min) / (max - min);
/* 134 */         double valueMin = this.values[i - 1];
/* 135 */         double valueMax = this.values[i];
/* 136 */         double range = valueMax - valueMin;
/* 137 */         return valueMin + alpha * range;
/*     */       } 
/*     */       
/* 140 */       min = max;
/*     */     } 
/*     */     
/* 143 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getValue(@Nonnull DoubleSupplier supplier) {
/* 148 */     return getValue(supplier.getAsDouble());
/*     */   }
/*     */ 
/*     */   
/*     */   public double getValue(@Nonnull Random random) {
/* 153 */     return getValue(random.nextDouble());
/*     */   }
/*     */ 
/*     */   
/*     */   public double getValue(int seed, double x, double y, @Nonnull IDoubleCoordinateSupplier2d supplier) {
/* 158 */     return getValue(supplier.get(seed, x, y));
/*     */   }
/*     */ 
/*     */   
/*     */   public double getValue(int seed, double x, double y, double z, @Nonnull IDoubleCoordinateSupplier3d supplier) {
/* 163 */     return getValue(supplier.get(seed, x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 170 */     return "Multiple{thresholds=" + Arrays.toString(this.thresholds) + ", values=" + 
/* 171 */       Arrays.toString(this.values) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\supplier\DoubleRange$Multiple.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */