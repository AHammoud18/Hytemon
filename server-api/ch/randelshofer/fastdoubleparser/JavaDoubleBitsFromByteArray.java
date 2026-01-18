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
/*    */ final class JavaDoubleBitsFromByteArray
/*    */   extends AbstractJavaFloatingPointBitsFromByteArray
/*    */ {
/*    */   long nan() {
/* 23 */     return Double.doubleToRawLongBits(Double.NaN);
/*    */   }
/*    */ 
/*    */   
/*    */   long negativeInfinity() {
/* 28 */     return Double.doubleToRawLongBits(Double.NEGATIVE_INFINITY);
/*    */   }
/*    */ 
/*    */   
/*    */   long positiveInfinity() {
/* 33 */     return Double.doubleToRawLongBits(Double.POSITIVE_INFINITY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   long valueOfFloatLiteral(byte[] str, int startIndex, int endIndex, boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/* 40 */     double d = FastDoubleMath.tryDecFloatToDoubleTruncated(isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/*    */     
/* 42 */     return Double.doubleToRawLongBits(Double.isNaN(d) ? 
/*    */         
/* 44 */         Double.parseDouble(new String(str, startIndex, endIndex - startIndex, StandardCharsets.ISO_8859_1)) : 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 53 */         d);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   long valueOfHexLiteral(byte[] str, int startIndex, int endIndex, boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/* 60 */     double d = FastDoubleMath.tryHexFloatToDoubleTruncated(isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/*    */     
/* 62 */     return Double.doubleToRawLongBits(Double.isNaN(d) ? Double.parseDouble(new String(str, startIndex, endIndex - startIndex, StandardCharsets.ISO_8859_1)) : d);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JavaDoubleBitsFromByteArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */