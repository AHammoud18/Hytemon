/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConstantIntCondition
/*    */   implements IIntCondition
/*    */ {
/* 10 */   public static final ConstantIntCondition DEFAULT_TRUE = new ConstantIntCondition(true);
/* 11 */   public static final ConstantIntCondition DEFAULT_FALSE = new ConstantIntCondition(false);
/*    */   
/*    */   protected final boolean result;
/*    */   
/*    */   public ConstantIntCondition(boolean result) {
/* 16 */     this.result = result;
/*    */   }
/*    */   
/*    */   public boolean getResult() {
/* 20 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int value) {
/* 25 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 31 */     return "ConstantIntCondition{result=" + this.result + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\ConstantIntCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */