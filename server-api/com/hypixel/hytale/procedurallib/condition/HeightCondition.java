/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HeightCondition
/*    */   implements ICoordinateRndCondition
/*    */ {
/*    */   protected final IHeightThresholdInterpreter interpreter;
/*    */   
/*    */   public HeightCondition(IHeightThresholdInterpreter interpreter) {
/* 14 */     this.interpreter = interpreter;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(int seed, int x, int z, int y, @Nonnull Random random) {
/* 19 */     double threshold = this.interpreter.getThreshold(seed, x, z, y);
/* 20 */     return (threshold > 0.0D && (threshold >= 1.0D || threshold > random.nextDouble()));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 26 */     return "HeightCondition{interpreter=" + String.valueOf(this.interpreter) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\HeightCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */