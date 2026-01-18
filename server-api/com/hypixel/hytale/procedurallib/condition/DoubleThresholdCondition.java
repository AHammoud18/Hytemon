/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DoubleThresholdCondition
/*    */   implements IDoubleCondition
/*    */ {
/*    */   protected final IDoubleThreshold threshold;
/*    */   
/*    */   public DoubleThresholdCondition(IDoubleThreshold threshold) {
/* 13 */     this.threshold = threshold;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(double value) {
/* 18 */     return this.threshold.eval(value);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 24 */     return "DoubleThresholdCondition{threshold=" + String.valueOf(this.threshold) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\DoubleThresholdCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */