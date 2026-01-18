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
/*    */ 
/*    */ final class JavaFloatBitsFromCharSequence
/*    */   extends AbstractJavaFloatingPointBitsFromCharSequence
/*    */ {
/*    */   long nan() {
/* 22 */     return Float.floatToRawIntBits(Float.NaN);
/*    */   }
/*    */ 
/*    */   
/*    */   long negativeInfinity() {
/* 27 */     return Float.floatToRawIntBits(Float.NEGATIVE_INFINITY);
/*    */   }
/*    */ 
/*    */   
/*    */   long positiveInfinity() {
/* 32 */     return Float.floatToRawIntBits(Float.POSITIVE_INFINITY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   long valueOfFloatLiteral(CharSequence str, int startIndex, int endIndex, boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/* 39 */     float d = FastFloatMath.tryDecFloatToFloatTruncated(isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/* 40 */     return Float.floatToRawIntBits(Float.isNaN(d) ? Float.parseFloat(str.subSequence(startIndex, endIndex).toString()) : d);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   long valueOfHexLiteral(CharSequence str, int startIndex, int endIndex, boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/* 47 */     float d = FastFloatMath.tryHexFloatToFloatTruncated(isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/* 48 */     return Float.floatToRawIntBits(Float.isNaN(d) ? Float.parseFloat(str.subSequence(startIndex, endIndex).toString()) : d);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaFloatBitsFromCharSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */