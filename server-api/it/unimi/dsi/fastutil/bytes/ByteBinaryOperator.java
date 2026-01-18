/*    */ package it.unimi.dsi.fastutil.bytes;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.SafeMath;
/*    */ import java.util.function.BinaryOperator;
/*    */ import java.util.function.IntBinaryOperator;
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
/*    */ public interface ByteBinaryOperator
/*    */   extends BinaryOperator<Byte>, IntBinaryOperator
/*    */ {
/*    */   @Deprecated
/*    */   default int applyAsInt(int x, int y) {
/* 54 */     return apply(SafeMath.safeIntToByte(x), SafeMath.safeIntToByte(y));
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
/*    */   default Byte apply(Byte x, Byte y) {
/* 66 */     return Byte.valueOf(apply(x.byteValue(), y.byteValue()));
/*    */   }
/*    */   
/*    */   byte apply(byte paramByte1, byte paramByte2);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteBinaryOperator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */