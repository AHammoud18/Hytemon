/*     */ package com.hypixel.hytale.procedurallib.condition;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NoiseHeightThresholdInterpreter
/*     */   implements IHeightThresholdInterpreter
/*     */ {
/*     */   protected final NoiseProperty noise;
/*     */   @Nonnull
/*     */   protected final float[] keys;
/*     */   @Nonnull
/*     */   protected final IHeightThresholdInterpreter[] values;
/*     */   protected final int length;
/*     */   protected final int lowestNonOne;
/*     */   protected final int highestNonZero;
/*     */   
/*     */   public NoiseHeightThresholdInterpreter(NoiseProperty noise, @Nonnull float[] keys, @Nonnull IHeightThresholdInterpreter[] values) {
/*  22 */     if (keys.length != values.length) throw new IllegalStateException("Length of keys and values are different!"); 
/*  23 */     checkInterpreterLength(values);
/*     */     
/*  25 */     this.noise = noise;
/*  26 */     this.keys = keys;
/*  27 */     this.values = values;
/*  28 */     this.length = values[0].getLength();
/*     */     
/*  30 */     int lowestNonOne = 0;
/*  31 */     for (IHeightThresholdInterpreter value : values) {
/*  32 */       if (value.getLowestNonOne() < lowestNonOne) {
/*  33 */         lowestNonOne = value.getLowestNonOne();
/*     */       }
/*     */     } 
/*  36 */     this.lowestNonOne = lowestNonOne;
/*     */     
/*  38 */     int highestNonZero = this.length - 1;
/*  39 */     for (IHeightThresholdInterpreter value : values) {
/*  40 */       if (value.getHighestNonZero() > highestNonZero) {
/*  41 */         highestNonZero = value.getHighestNonZero();
/*     */       }
/*     */     } 
/*  44 */     this.highestNonZero = highestNonZero;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLowestNonOne() {
/*  49 */     return this.lowestNonOne;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighestNonZero() {
/*  54 */     return this.highestNonZero;
/*     */   }
/*     */   
/*     */   protected double noise(int seed, double x, double y) {
/*  58 */     return this.noise.get(seed, x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getContext(int seed, double x, double y) {
/*  63 */     return noise(seed, x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLength() {
/*  68 */     return this.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getThreshold(int seed, double x, double z, int height) {
/*  73 */     return getThreshold(seed, x, z, height, getContext(seed, x, z));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getThreshold(int seed, double x, double z, int height, double context) {
/*  78 */     if (height > this.highestNonZero) return 0.0F; 
/*  79 */     int length = this.keys.length;
/*  80 */     for (int i = 0; i < length; i++) {
/*  81 */       if (context <= this.keys[i]) {
/*  82 */         if (i == 0) {
/*  83 */           return this.values[0].getThreshold(seed, x, z, height);
/*     */         }
/*  85 */         float distance = ((float)context - this.keys[i - 1]) / (this.keys[i] - this.keys[i - 1]);
/*  86 */         return IHeightThresholdInterpreter.lerp(this.values[i - 1].getThreshold(seed, x, z, height), this.values[i].getThreshold(seed, x, z, height), distance);
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     return this.values[length - 1].getThreshold(seed, x, z, height);
/*     */   }
/*     */   
/*     */   static float lerp(float from, float to, float t) {
/*  94 */     return from + (to - from) * t;
/*     */   }
/*     */   
/*     */   private static void checkInterpreterLength(@Nonnull IHeightThresholdInterpreter[] values) {
/*  98 */     int length = values[0].getLength();
/*  99 */     for (int i = 1; i < values.length; i++) {
/* 100 */       if (values[i].getLength() != length) throw new IllegalStateException("ThresholdKeyInterpreter have different size!");
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 107 */     return "NoiseHeightThresholdInterpreter{noise=" + String.valueOf(this.noise) + ", keys=" + 
/*     */       
/* 109 */       Arrays.toString(this.keys) + ", values=" + 
/* 110 */       Arrays.toString((Object[])this.values) + ", length=" + this.length + ", lowestNonOne=" + this.lowestNonOne + ", highestNonZero=" + this.highestNonZero + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\NoiseHeightThresholdInterpreter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */