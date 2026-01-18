/*    */ package com.hypixel.hytale.builtin.hytalegenerator.delimiters;
/*    */ 
/*    */ public class RangeDouble {
/*    */   private final double minInclusive;
/*    */   private final double maxExclusive;
/*    */   
/*    */   public RangeDouble(double minInclusive, double maxExclusive) {
/*  8 */     this.minInclusive = minInclusive;
/*  9 */     this.maxExclusive = maxExclusive;
/*    */   }
/*    */   
/*    */   public boolean contains(double value) {
/* 13 */     return (this.minInclusive <= value && this.maxExclusive > value);
/*    */   }
/*    */   
/*    */   public double min() {
/* 17 */     return this.minInclusive;
/*    */   }
/*    */   
/*    */   public double max() {
/* 21 */     return this.maxExclusive;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\delimiters\RangeDouble.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */