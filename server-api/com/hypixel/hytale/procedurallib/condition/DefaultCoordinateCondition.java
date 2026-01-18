/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultCoordinateCondition
/*    */   implements ICoordinateCondition
/*    */ {
/* 10 */   public static final DefaultCoordinateCondition DEFAULT_TRUE = new DefaultCoordinateCondition(true);
/* 11 */   public static final DefaultCoordinateCondition DEFAULT_FALSE = new DefaultCoordinateCondition(false);
/*    */   
/*    */   protected final boolean result;
/*    */   
/*    */   private DefaultCoordinateCondition(boolean result) {
/* 16 */     this.result = result;
/*    */   }
/*    */   
/*    */   public boolean getResult() {
/* 20 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int seed, int x, int y) {
/* 25 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int seed, int x, int y, int z) {
/* 30 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 36 */     return "DefaultCoordinateCondition{result=" + this.result + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\DefaultCoordinateCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */