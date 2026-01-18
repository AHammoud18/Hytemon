/*    */ package it.unimi.dsi.fastutil.longs;
/*    */ 
/*    */ import java.util.function.BinaryOperator;
/*    */ import java.util.function.LongBinaryOperator;
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
/*    */ public interface LongBinaryOperator
/*    */   extends BinaryOperator<Long>, LongBinaryOperator
/*    */ {
/*    */   @Deprecated
/*    */   default long applyAsLong(long x, long y) {
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
/*    */   default Long apply(Long x, Long y) {
/* 59 */     return Long.valueOf(apply(x.longValue(), y.longValue()));
/*    */   }
/*    */   
/*    */   long apply(long paramLong1, long paramLong2);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongBinaryOperator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */