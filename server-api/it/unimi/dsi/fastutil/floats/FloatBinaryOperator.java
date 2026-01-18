/*    */ package it.unimi.dsi.fastutil.floats;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.SafeMath;
/*    */ import java.util.function.BinaryOperator;
/*    */ import java.util.function.DoubleBinaryOperator;
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
/*    */ @FunctionalInterface
/*    */ public interface FloatBinaryOperator
/*    */   extends BinaryOperator<Float>, DoubleBinaryOperator
/*    */ {
/*    */   @Deprecated
/*    */   default double applyAsDouble(double x, double y) {
/* 54 */     return apply(SafeMath.safeDoubleToFloat(x), SafeMath.safeDoubleToFloat(y));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default Float apply(Float x, Float y) {
/* 66 */     return Float.valueOf(apply(x.floatValue(), y.floatValue()));
/*    */   }
/*    */   
/*    */   float apply(float paramFloat1, float paramFloat2);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatBinaryOperator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */