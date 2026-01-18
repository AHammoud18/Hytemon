/*    */ package com.hypixel.hytale.builtin.hytalegenerator.delimiters;
/*    */ 
/*    */ public class RangeInt {
/*    */   private final int minInclusive;
/*    */   private final int maxExclusive;
/*    */   
/*    */   public RangeInt(int minInclusive, int maxExclusive) {
/*  8 */     this.minInclusive = minInclusive;
/*  9 */     this.maxExclusive = maxExclusive;
/*    */   }
/*    */   
/*    */   public boolean contains(int value) {
/* 13 */     return (this.minInclusive <= value && this.maxExclusive > value);
/*    */   }
/*    */   
/*    */   public int getMinInclusive() {
/* 17 */     return this.minInclusive;
/*    */   }
/*    */   
/*    */   public int getMaxExclusive() {
/* 21 */     return this.maxExclusive;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\delimiters\RangeInt.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */