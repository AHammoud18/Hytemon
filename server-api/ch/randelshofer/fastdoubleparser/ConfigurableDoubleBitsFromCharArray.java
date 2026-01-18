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
/*    */ final class ConfigurableDoubleBitsFromCharArray
/*    */   extends AbstractConfigurableFloatingPointBitsFromCharArray
/*    */ {
/*    */   public ConfigurableDoubleBitsFromCharArray(NumberFormatSymbols symbols, boolean ignoreCase) {
/* 15 */     super(symbols, ignoreCase);
/*    */   }
/*    */ 
/*    */   
/*    */   long nan() {
/* 20 */     return Double.doubleToRawLongBits(Double.NaN);
/*    */   }
/*    */ 
/*    */   
/*    */   long negativeInfinity() {
/* 25 */     return Double.doubleToRawLongBits(Double.NEGATIVE_INFINITY);
/*    */   }
/*    */ 
/*    */   
/*    */   long positiveInfinity() {
/* 30 */     return Double.doubleToRawLongBits(Double.POSITIVE_INFINITY);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   long valueOfFloatLiteral(char[] str, int integerStartIndex, int integerEndIndex, int fractionStartIndex, int fractionEndIndex, boolean isSignificandNegative, long significand, int exponent, boolean isSignificandTruncated, int exponentOfTruncatedSignificand, int exponentValue, int startIndex, int endIndex) {
/* 36 */     double d = FastDoubleMath.tryDecFloatToDoubleTruncated(isSignificandNegative, significand, exponent, isSignificandTruncated, exponentOfTruncatedSignificand);
/*    */     
/* 38 */     return Double.doubleToRawLongBits(Double.isNaN(d) ? 
/* 39 */         slowPathToDouble(str, integerStartIndex, integerEndIndex, fractionStartIndex, fractionEndIndex, isSignificandNegative, exponentValue) : 
/* 40 */         d);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\ConfigurableDoubleBitsFromCharArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */