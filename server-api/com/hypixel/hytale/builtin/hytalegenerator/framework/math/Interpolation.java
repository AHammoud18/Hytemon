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
/*    */ public class Interpolation
/*    */ {
/*    */   public static double linear(double valueA, double valueB, double weight) {
/* 17 */     if (weight < 0.0D || weight > 1.0D)
/* 18 */       throw new IllegalArgumentException("weight outside range"); 
/* 19 */     return valueA * (1.0D - weight) + valueB * weight;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\math\Interpolation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */