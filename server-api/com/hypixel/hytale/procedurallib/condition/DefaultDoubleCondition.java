/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultDoubleCondition
/*    */   implements IDoubleCondition
/*    */ {
/* 10 */   public static final DefaultDoubleCondition DEFAULT_TRUE = new DefaultDoubleCondition(true);
/* 11 */   public static final DefaultDoubleCondition DEFAULT_FALSE = new DefaultDoubleCondition(false);
/*    */   
/*    */   protected final boolean result;
/*    */   
/*    */   public DefaultDoubleCondition(boolean result) {
/* 16 */     this.result = result;
/*    */   }
/*    */   
/*    */   public boolean getResult() {
/* 20 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(double value) {
/* 25 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 31 */     return "DefaultDoubleCondition{result=" + this.result + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\DefaultDoubleCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */