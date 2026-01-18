/*    */ package com.hypixel.hytale.math;
/*    */ 
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Range
/*    */ {
/*    */   private float min;
/*    */   private float max;
/*    */   
/*    */   public Range() {}
/*    */   
/*    */   public Range(float min, float max) {
/* 35 */     this.min = min;
/* 36 */     this.max = max;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getMin() {
/* 43 */     return this.min;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getMax() {
/* 50 */     return this.max;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 56 */     return "Range{min=" + this.min + ", max='" + this.max + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\Range.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */