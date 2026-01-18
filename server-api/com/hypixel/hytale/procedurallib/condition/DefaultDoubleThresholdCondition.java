/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultDoubleThresholdCondition
/*    */   implements IDoubleThreshold
/*    */ {
/* 10 */   public static final DefaultDoubleThresholdCondition DEFAULT_TRUE = new DefaultDoubleThresholdCondition(true);
/* 11 */   public static final DefaultDoubleThresholdCondition DEFAULT_FALSE = new DefaultDoubleThresholdCondition(false);
/*    */   
/*    */   protected final boolean result;
/*    */   
/*    */   public DefaultDoubleThresholdCondition(boolean result) {
/* 16 */     this.result = result;
/*    */   }
/*    */   
/*    */   public boolean isResult() {
/* 20 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(double d) {
/* 25 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(double d, double factor) {
/* 30 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 36 */     return "DefaultDoubleThresholdCondition{result=" + this.result + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\DefaultDoubleThresholdCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */