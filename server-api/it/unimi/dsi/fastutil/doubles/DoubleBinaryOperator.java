/*    */ package it.unimi.dsi.fastutil.doubles;
/*    */ 
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
/*    */ @FunctionalInterface
/*    */ public interface DoubleBinaryOperator
/*    */   extends BinaryOperator<Double>, DoubleBinaryOperator
/*    */ {
/*    */   @Deprecated
/*    */   default double applyAsDouble(double x, double y) {
/* 47 */     return apply(x, y);
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
/*    */   default Double apply(Double x, Double y) {
/* 59 */     return Double.valueOf(apply(x.doubleValue(), y.doubleValue()));
/*    */   }
/*    */   
/*    */   double apply(double paramDouble1, double paramDouble2);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleBinaryOperator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */