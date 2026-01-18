/*   */ package com.hypixel.hytale.procedurallib.supplier;
/*   */ @FunctionalInterface
/*   */ public interface ISeedDoubleRange {
/*   */   static {
/* 5 */     DIRECT = ((seed, value) -> value);
/*   */   }
/*   */   
/*   */   public static final ISeedDoubleRange DIRECT;
/*   */   
/*   */   double getValue(int paramInt, double paramDouble);
/*   */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\supplier\ISeedDoubleRange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */