/*    */ package it.unimi.dsi.fastutil.doubles;
/*    */ 
/*    */ import java.util.function.DoubleUnaryOperator;
/*    */ import java.util.function.UnaryOperator;
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
/*    */ public interface DoubleUnaryOperator
/*    */   extends UnaryOperator<Double>, DoubleUnaryOperator
/*    */ {
/*    */   static DoubleUnaryOperator identity() {
/* 45 */     return i -> i;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static DoubleUnaryOperator negation() {
/* 57 */     return i -> -i;
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
/*    */   default double applyAsDouble(double x) {
/* 69 */     return apply(x);
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
/*    */   default Double apply(Double x) {
/* 81 */     return Double.valueOf(apply(x.doubleValue()));
/*    */   }
/*    */   
/*    */   double apply(double paramDouble);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleUnaryOperator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */