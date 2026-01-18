/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultCoordinateRndCondition
/*    */   implements ICoordinateRndCondition
/*    */ {
/* 11 */   public static final DefaultCoordinateRndCondition DEFAULT_TRUE = new DefaultCoordinateRndCondition(true);
/* 12 */   public static final DefaultCoordinateRndCondition DEFAULT_FALSE = new DefaultCoordinateRndCondition(false);
/*    */   
/*    */   protected final boolean result;
/*    */   
/*    */   public DefaultCoordinateRndCondition(boolean result) {
/* 17 */     this.result = result;
/*    */   }
/*    */   
/*    */   public boolean getResult() {
/* 21 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int seed, int x, int z, int y, Random random) {
/* 26 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 32 */     return "DefaultCoordinateRndCondition{result=" + this.result + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\DefaultCoordinateRndCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */