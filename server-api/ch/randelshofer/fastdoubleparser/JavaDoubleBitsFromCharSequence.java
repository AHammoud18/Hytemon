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
/*    */ final class JavaDoubleBitsFromCharSequence
/*    */   extends AbstractJavaFloatingPointBitsFromCharSequence
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
/*    */   long valueOfFloatLiteral(CharSequence str, int startIndex, int endIndex, boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/* 38 */     double d = FastDoubleMath.tryDecFloatToDoubleTruncated(isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/*    */     
/* 40 */     return Double.doubleToRawLongBits(Double.isNaN(d) ? 
/* 41 */         Double.parseDouble(str.subSequence(startIndex, endIndex).toString()) : 
/* 42 */         d);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   long valueOfHexLiteral(CharSequence str, int startIndex, int endIndex, boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/* 49 */     double d = FastDoubleMath.tryHexFloatToDoubleTruncated(isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/*    */     
/* 51 */     return Double.doubleToRawLongBits(Double.isNaN(d) ? 
/* 52 */         Double.parseDouble(str.subSequence(startIndex, endIndex).toString()) : 
/* 53 */         d);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaDoubleBitsFromCharSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */