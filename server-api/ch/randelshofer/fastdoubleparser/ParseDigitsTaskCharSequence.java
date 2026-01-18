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
/*    */ 
/*    */ final class ParseDigitsTaskCharSequence
/*    */ {
/*    */   static BigInteger parseDigitsIterative(CharSequence str, int from, int to) {
/*    */     int i;
/* 29 */     assert str != null : "str==null";
/*    */     
/* 31 */     int numDigits = to - from;
/*    */     
/* 33 */     BigSignificand bigSignificand = new BigSignificand(FastIntegerMath.estimateNumBits(numDigits));
/* 34 */     int preroll = from + (numDigits & 0x7);
/* 35 */     int value = FastDoubleSwar.tryToParseUpTo7Digits(str, from, preroll);
/* 36 */     boolean success = (value >= 0);
/* 37 */     bigSignificand.add(value);
/* 38 */     for (from = preroll; from < to; from += 8) {
/* 39 */       int addend = FastDoubleSwar.tryToParseEightDigits(str, from);
/* 40 */       i = success & ((addend >= 0) ? 1 : 0);
/* 41 */       bigSignificand.fma(100000000, addend);
/*    */     } 
/* 43 */     if (i == 0) {
/* 44 */       throw new NumberFormatException("illegal syntax");
/*    */     }
/* 46 */     return bigSignificand.toBigInteger();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static BigInteger parseDigitsRecursive(CharSequence str, int from, int to, Map<Integer, BigInteger> powersOfTen, int recursionThreshold) {
/* 57 */     assert str != null : "str==null";
/* 58 */     assert powersOfTen != null : "powersOfTen==null";
/*    */ 
/*    */     
/* 61 */     int numDigits = to - from;
/*    */ 
/*    */     
/* 64 */     if (numDigits <= recursionThreshold) {
/* 65 */       return parseDigitsIterative(str, from, to);
/*    */     }
/*    */ 
/*    */     
/* 69 */     int mid = FastIntegerMath.splitFloor16(from, to);
/* 70 */     BigInteger high = parseDigitsRecursive(str, from, mid, powersOfTen, recursionThreshold);
/* 71 */     BigInteger low = parseDigitsRecursive(str, mid, to, powersOfTen, recursionThreshold);
/*    */ 
/*    */     
/* 74 */     high = FftMultiplier.multiply(high, powersOfTen.get(Integer.valueOf(to - mid)));
/* 75 */     return low.add(high);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\ParseDigitsTaskCharSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */