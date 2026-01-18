/*    */ package ch.randelshofer.fastdoubleparser.chr;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface CharDigitSet
/*    */ {
/*    */   int toDigit(char paramChar);
/*    */   
/*    */   static CharDigitSet copyOf(List<Character> digits) {
/*    */     int j;
/* 36 */     boolean consecutive = true;
/* 37 */     char zeroDigit = ((Character)digits.get(0)).charValue();
/* 38 */     for (int i = 1; i < 10; i++) {
/* 39 */       char current = ((Character)digits.get(i)).charValue();
/* 40 */       j = consecutive & ((current == zeroDigit + i) ? 1 : 0);
/*    */     } 
/* 42 */     return (j != 0) ? 
/* 43 */       new ConsecutiveCharDigitSet(((Character)digits.get(0)).charValue()) : 
/* 44 */       new CharToIntMap(digits);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\chr\CharDigitSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */