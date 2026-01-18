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
/*    */ abstract class AbstractBigDecimalParser
/*    */   extends AbstractNumberParser
/*    */ {
/*    */   public static final int MANY_DIGITS_THRESHOLD = 32;
/*    */   static final int RECURSION_THRESHOLD = 400;
/*    */   protected static final long MAX_EXPONENT_NUMBER = 2147483647L;
/*    */   protected static final int MAX_DIGITS_WITHOUT_LEADING_ZEROS = 646456993;
/*    */   
/*    */   protected static boolean hasManyDigits(int length) {
/* 58 */     return (length >= 32);
/*    */   }
/*    */   
/*    */   protected static void checkParsedBigDecimalBounds(boolean illegal, int index, int endIndex, int digitCount, long exponent) {
/* 62 */     if (illegal || index < endIndex) {
/* 63 */       throw new NumberFormatException("illegal syntax");
/*    */     }
/* 65 */     if (exponent <= -2147483648L || exponent > 2147483647L || digitCount > 646456993)
/* 66 */       throw new NumberFormatException("value exceeds limits"); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\AbstractBigDecimalParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */