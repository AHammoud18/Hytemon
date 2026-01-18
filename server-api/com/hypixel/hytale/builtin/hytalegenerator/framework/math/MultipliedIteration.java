/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MultipliedIteration
/*    */ {
/*    */   public static double calculateMultiplier(double startValue, double endValue, int numberOfIterations, double precision) {
/* 20 */     if (startValue < endValue)
/* 21 */       throw new IllegalArgumentException("start smaller than end"); 
/* 22 */     if (numberOfIterations <= 0) {
/* 23 */       throw new IllegalArgumentException("number of iterations must be greater than 0");
/*    */     }
/* 25 */     if (precision <= 0.0D) {
/* 26 */       throw new IllegalArgumentException("precision must be greater than 0");
/*    */     }
/* 28 */     double candidate = 0.0D;
/* 29 */     int result = 0;
/* 30 */     while (candidate < 1.0D) {
/* 31 */       result = calculateIterations(candidate, startValue, endValue);
/* 32 */       if (result >= numberOfIterations)
/*    */         break; 
/* 34 */       candidate += precision;
/*    */     } 
/* 36 */     return Math.min(candidate, 0.99999D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int calculateIterations(double multiplier, double startValue, double endValue) {
/* 46 */     double currentSize = startValue;
/* 47 */     int iterations = 0;
/* 48 */     while (currentSize > endValue) {
/* 49 */       currentSize *= multiplier;
/* 50 */       iterations++;
/*    */     } 
/* 52 */     return iterations;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\math\MultipliedIteration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */