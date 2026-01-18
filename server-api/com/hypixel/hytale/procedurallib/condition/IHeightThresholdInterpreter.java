/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IHeightThresholdInterpreter
/*    */ {
/*    */   int getLowestNonOne();
/*    */   
/*    */   int getHighestNonZero();
/*    */   
/*    */   float getThreshold(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2);
/*    */   
/*    */   float getThreshold(int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, double paramDouble3);
/*    */   
/*    */   double getContext(int paramInt, double paramDouble1, double paramDouble2);
/*    */   
/*    */   int getLength();
/*    */   
/*    */   default boolean isSpawnable(int height) {
/* 22 */     return (height >= getLowestNonOne() && height <= getHighestNonZero());
/*    */   }
/*    */   
/*    */   static float lerp(float from, float to, float t) {
/* 26 */     return from + (to - from) * t;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\IHeightThresholdInterpreter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */