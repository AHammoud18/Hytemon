/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.math;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Normalizer
/*    */ {
/*    */   public static double normalizeNoise(double input) {
/* 13 */     return normalize(-1.0D, 1.0D, 0.0D, 1.0D, input);
/*    */   }
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
/*    */   public static double normalize(double fromMin, double fromMax, double toMin, double toMax, double input) {
/* 27 */     if (fromMin > fromMax || toMin > toMax) {
/* 28 */       throw new IllegalArgumentException("min larger than max");
/*    */     }
/* 30 */     input -= fromMin;
/* 31 */     input /= fromMax - fromMin;
/* 32 */     input *= toMax - toMin;
/* 33 */     input += toMin;
/* 34 */     return input;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\math\Normalizer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */