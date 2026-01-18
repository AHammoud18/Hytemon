/*    */ package ch.randelshofer.fastdoubleparser;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ import java.util.Map;
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
/*    */ final class ParseDigitsTaskByteArray
/*    */ {
/*    */   static BigInteger parseDigitsIterative(byte[] str, int from, int to) {
/*    */     int i;
/* 28 */     assert str != null : "str==null";
/*    */     
/* 30 */     int numDigits = to - from;
/*    */     
/* 32 */     BigSignificand bigSignificand = new BigSignificand(FastIntegerMath.estimateNumBits(numDigits));
/* 33 */     int preroll = from + (numDigits & 0x7);
/* 34 */     int value = FastDoubleSwar.tryToParseUpTo7Digits(str, from, preroll);
/* 35 */     boolean success = (value >= 0);
/* 36 */     bigSignificand.add(value);
/* 37 */     for (from = preroll; from < to; from += 8) {
/* 38 */       int addend = FastDoubleSwar.tryToParseEightDigits(str, from);
/* 39 */       i = success & ((addend >= 0) ? 1 : 0);
/* 40 */       bigSignificand.fma(100000000, addend);
/*    */     } 
/* 42 */     if (i == 0) {
/* 43 */       throw new NumberFormatException("illegal syntax");
/*    */     }
/* 45 */     return bigSignificand.toBigInteger();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static BigInteger parseDigitsRecursive(byte[] str, int from, int to, Map<Integer, BigInteger> powersOfTen, int recursionThreshold) {
/* 56 */     assert str != null : "str==null";
/* 57 */     assert powersOfTen != null : "powersOfTen==null";
/*    */     
/* 59 */     int numDigits = to - from;
/*    */ 
/*    */     
/* 62 */     if (numDigits <= recursionThreshold) {
/* 63 */       return parseDigitsIterative(str, from, to);
/*    */     }
/*    */ 
/*    */     
/* 67 */     int mid = FastIntegerMath.splitFloor16(from, to);
/* 68 */     BigInteger high = parseDigitsRecursive(str, from, mid, powersOfTen, recursionThreshold);
/* 69 */     BigInteger low = parseDigitsRecursive(str, mid, to, powersOfTen, recursionThreshold);
/*    */ 
/*    */     
/* 72 */     high = FftMultiplier.multiply(high, powersOfTen.get(Integer.valueOf(to - mid)));
/* 73 */     return low.add(high);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\ParseDigitsTaskByteArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */