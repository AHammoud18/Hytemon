/*    */ package ch.randelshofer.fastdoubleparser;
/*    */ 
/*    */ import java.nio.charset.StandardCharsets;
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
/*    */ final class JavaFloatBitsFromByteArray
/*    */   extends AbstractJavaFloatingPointBitsFromByteArray
/*    */ {
/*    */   long nan() {
/* 24 */     return Float.floatToRawIntBits(Float.NaN);
/*    */   }
/*    */ 
/*    */   
/*    */   long negativeInfinity() {
/* 29 */     return Float.floatToRawIntBits(Float.NEGATIVE_INFINITY);
/*    */   }
/*    */ 
/*    */   
/*    */   long positiveInfinity() {
/* 34 */     return Float.floatToRawIntBits(Float.POSITIVE_INFINITY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   long valueOfFloatLiteral(byte[] str, int startIndex, int endIndex, boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/* 41 */     float result = FastFloatMath.tryDecFloatToFloatTruncated(isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/* 42 */     return Float.floatToRawIntBits(Float.isNaN(result) ? Float.parseFloat(new String(str, startIndex, endIndex - startIndex, StandardCharsets.ISO_8859_1)) : 
/* 43 */         result);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   long valueOfHexLiteral(byte[] str, int startIndex, int endIndex, boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/* 50 */     float d = FastFloatMath.tryHexFloatToFloatTruncated(isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/* 51 */     return Float.floatToRawIntBits(Float.isNaN(d) ? Float.parseFloat(new String(str, startIndex, endIndex - startIndex, StandardCharsets.ISO_8859_1)) : d);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaFloatBitsFromByteArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */