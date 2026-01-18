/*    */ package ch.randelshofer.fastdoubleparser.bte;
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ConsecutiveByteDigitSet
/*    */   implements ByteDigitSet
/*    */ {
/*    */   private final byte zeroDigit;
/*    */   
/*    */   public ConsecutiveByteDigitSet(char zeroDigit) {
/* 11 */     if (zeroDigit > '') {
/* 12 */       throw new IllegalArgumentException("can not map to a single byte. zeroDigit=" + zeroDigit + "' 0x" + Integer.toHexString(zeroDigit));
/*    */     }
/* 14 */     this.zeroDigit = (byte)zeroDigit;
/*    */   }
/*    */ 
/*    */   
/*    */   public int toDigit(byte ch) {
/* 19 */     return (char)(ch - this.zeroDigit);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\bte\ConsecutiveByteDigitSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */