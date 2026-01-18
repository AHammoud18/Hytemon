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
/*    */ abstract class AbstractBigIntegerParser
/*    */   extends AbstractNumberParser
/*    */ {
/*    */   private static final int MAX_DECIMAL_DIGITS = 646456993;
/*    */   private static final int MAX_HEX_DIGITS = 536870912;
/*    */   static final int RECURSION_THRESHOLD = 400;
/*    */   
/*    */   protected static boolean hasManyDigits(int length) {
/* 37 */     return (length > 18);
/*    */   }
/*    */   
/*    */   protected static void checkHexBigIntegerBounds(int numDigits) {
/* 41 */     if (numDigits > 536870912) {
/* 42 */       throw new NumberFormatException("value exceeds limits");
/*    */     }
/*    */   }
/*    */   
/*    */   protected static void checkDecBigIntegerBounds(int numDigits) {
/* 47 */     if (numDigits > 646456993)
/* 48 */       throw new NumberFormatException("value exceeds limits"); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\AbstractBigIntegerParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */