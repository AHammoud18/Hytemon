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
/*    */ final class JavaFloatBitsFromCharArray
/*    */   extends AbstractJavaFloatingPointBitsFromCharArray
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
/*    */   long valueOfFloatLiteral(char[] str, int startIndex, int endIndex, boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/* 39 */     float result = FastFloatMath.tryDecFloatToFloatTruncated(isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/* 40 */     return Float.isNaN(result) ? Float.floatToRawIntBits(Float.parseFloat(new String(str, startIndex, endIndex - startIndex))) : Float.floatToRawIntBits(result);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   long valueOfHexLiteral(char[] str, int startIndex, int endIndex, boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/* 47 */     float d = FastFloatMath.tryHexFloatToFloatTruncated(isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/* 48 */     return Float.floatToRawIntBits(Float.isNaN(d) ? Float.parseFloat(new String(str, startIndex, endIndex - startIndex)) : d);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaFloatBitsFromCharArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */