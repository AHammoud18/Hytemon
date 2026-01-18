/*    */ package ch.randelshofer.fastdoubleparser;
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
/*    */ final class JavaDoubleBitsFromCharArray
/*    */   extends AbstractJavaFloatingPointBitsFromCharArray
/*    */ {
/*    */   long nan() {
/* 21 */     return Double.doubleToRawLongBits(Double.NaN);
/*    */   }
/*    */ 
/*    */   
/*    */   long negativeInfinity() {
/* 26 */     return Double.doubleToRawLongBits(Double.NEGATIVE_INFINITY);
/*    */   }
/*    */ 
/*    */   
/*    */   long positiveInfinity() {
/* 31 */     return Double.doubleToRawLongBits(Double.POSITIVE_INFINITY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   long valueOfFloatLiteral(char[] str, int startIndex, int endIndex, boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/* 38 */     double d = FastDoubleMath.tryDecFloatToDoubleTruncated(isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/*    */     
/* 40 */     return Double.doubleToRawLongBits(Double.isNaN(d) ? Double.parseDouble(new String(str, startIndex, endIndex - startIndex)) : d);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   long valueOfHexLiteral(char[] str, int startIndex, int endIndex, boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/* 47 */     double d = FastDoubleMath.tryHexFloatToDoubleTruncated(isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/*    */     
/* 49 */     return Double.doubleToRawLongBits(Double.isNaN(d) ? Double.parseDouble(new String(str, startIndex, endIndex - startIndex)) : d);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaDoubleBitsFromCharArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */