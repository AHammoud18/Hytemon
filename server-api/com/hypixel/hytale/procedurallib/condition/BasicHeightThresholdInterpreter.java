/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicHeightThresholdInterpreter
/*    */   implements IHeightThresholdInterpreter
/*    */ {
/*    */   @Nonnull
/*    */   protected final float[] interpolatedThresholds;
/*    */   protected final int lowestNonOne;
/*    */   protected final int highestNonZero;
/*    */   
/*    */   public BasicHeightThresholdInterpreter(@Nonnull int[] positions, @Nonnull float[] thresholds, int length) {
/* 18 */     if (positions.length != thresholds.length) throw new IllegalArgumentException(String.format("Mismatching array lengths! positions: %s, thresholds: %s", new Object[] { Integer.valueOf(positions.length), Integer.valueOf(thresholds.length) }));
/*    */     
/* 20 */     this.interpolatedThresholds = new float[length];
/* 21 */     for (int y = 0; y < this.interpolatedThresholds.length; y++) {
/* 22 */       float threshold = thresholds[thresholds.length - 1];
/* 23 */       for (int i = 0; i < positions.length; i++) {
/* 24 */         if (y < positions[i]) {
/* 25 */           if (i == 0) {
/* 26 */             threshold = thresholds[i]; break;
/*    */           } 
/* 28 */           float distance = (y - positions[i - 1]) / (positions[i] - positions[i - 1]);
/* 29 */           threshold = IHeightThresholdInterpreter.lerp(thresholds[i - 1], thresholds[i], distance);
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 34 */       this.interpolatedThresholds[y] = threshold;
/*    */     } 
/*    */     
/* 37 */     int lowestNonOne = 0;
/* 38 */     for (; lowestNonOne < length && 
/* 39 */       this.interpolatedThresholds[lowestNonOne] >= 1.0F; lowestNonOne++);
/*    */ 
/*    */ 
/*    */     
/* 43 */     this.lowestNonOne = lowestNonOne;
/*    */     
/* 45 */     int highestNonZero = length - 1;
/* 46 */     for (; highestNonZero >= 0 && 
/* 47 */       this.interpolatedThresholds[highestNonZero] <= 0.0F; highestNonZero--);
/*    */ 
/*    */ 
/*    */     
/* 51 */     this.highestNonZero = highestNonZero;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLowestNonOne() {
/* 56 */     return this.lowestNonOne;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHighestNonZero() {
/* 61 */     return this.highestNonZero;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getContext(int seed, double x, double y) {
/* 66 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLength() {
/* 71 */     return this.interpolatedThresholds.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getThreshold(int seed, double x, double y, int height) {
/* 76 */     return getThreshold(seed, x, y, height, getContext(seed, x, y));
/*    */   }
/*    */ 
/*    */   
/*    */   public float getThreshold(int seed, double x, double y, int height, double context) {
/* 81 */     if (height > this.highestNonZero) return 0.0F; 
/* 82 */     if (height < 0) height = 0; 
/* 83 */     if (height >= this.interpolatedThresholds.length) height = this.interpolatedThresholds.length - 1; 
/* 84 */     return this.interpolatedThresholds[height];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 91 */     return "BasicHeightThresholdInterpreter{interpolatedThresholds=" + Arrays.toString(this.interpolatedThresholds) + ", lowestNonOne=" + this.lowestNonOne + ", highestNonZero=" + this.highestNonZero + "}";
/*    */   }
/*    */   
/*    */   public static interface Constants {
/*    */     public static final String ERROR_ARRAY_LENGTH = "Mismatching array lengths! positions: %s, thresholds: %s";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\BasicHeightThresholdInterpreter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */