/*    */ package ch.randelshofer.fastdoubleparser.bte;
/*    */ 
/*    */ import java.util.List;
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
/*    */ public interface ByteDigitSet
/*    */ {
/*    */   int toDigit(byte paramByte);
/*    */   
/*    */   static ByteDigitSet copyOf(List<Character> digits) {
/*    */     int j;
/* 33 */     boolean consecutive = true;
/* 34 */     char zeroDigit = ((Character)digits.get(0)).charValue();
/* 35 */     for (int i = 1; i < 10; i++) {
/* 36 */       char current = ((Character)digits.get(i)).charValue();
/* 37 */       j = consecutive & ((current == zeroDigit + i) ? 1 : 0);
/*    */     } 
/* 39 */     return (j != 0) ? 
/* 40 */       new ConsecutiveByteDigitSet(((Character)digits.get(0)).charValue()) : 
/* 41 */       new ByteToIntMap(digits);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\bte\ByteDigitSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */