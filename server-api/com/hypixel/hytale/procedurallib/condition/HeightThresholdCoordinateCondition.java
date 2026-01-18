/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ import com.hypixel.hytale.math.util.HashUtil;
/*    */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class HeightThresholdCoordinateCondition
/*    */   implements ICoordinateCondition
/*    */ {
/*    */   private final IHeightThresholdInterpreter interpreter;
/*    */   
/*    */   public HeightThresholdCoordinateCondition(IHeightThresholdInterpreter interpreter) {
/* 13 */     this.interpreter = interpreter;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int seed, int x, int y) {
/* 18 */     throw new UnsupportedOperationException("This needs a height to operate.");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int seed, int x, int y, int z) {
/* 23 */     return (this.interpreter.getThreshold(seed, x, z, y) >= HashUtil.random(seed, GeneralNoise.fastFloor(x), GeneralNoise.fastFloor(y), GeneralNoise.fastFloor(z)));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 29 */     return "HeightThresholdCoordinateCondition{interpreter=" + String.valueOf(this.interpreter) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\HeightThresholdCoordinateCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */