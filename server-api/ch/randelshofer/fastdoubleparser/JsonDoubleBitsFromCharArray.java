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
/*    */ 
/*    */ final class JsonDoubleBitsFromCharArray
/*    */   extends AbstractJsonFloatingPointBitsFromCharArray
/*    */ {
/*    */   long valueOfFloatLiteral(char[] str, int startIndex, int endIndex, boolean isNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand) {
/* 23 */     double d = FastDoubleMath.tryDecFloatToDoubleTruncated(isNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/*    */     
/* 25 */     return Double.doubleToRawLongBits(Double.isNaN(d) ? Double.parseDouble(new String(str, startIndex, endIndex - startIndex)) : d);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\JsonDoubleBitsFromCharArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */