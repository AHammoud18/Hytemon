/*    */ package ch.randelshofer.fastdoubleparser.chr;
/*    */ 
/*    */ import java.util.Set;
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
/*    */ public interface CharTrie
/*    */ {
/*    */   default int match(CharSequence str) {
/* 21 */     return match(str, 0, str.length());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default int match(char[] str) {
/* 32 */     return match(str, 0, str.length);
/*    */   }
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
/*    */   int match(CharSequence paramCharSequence, int paramInt1, int paramInt2);
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
/*    */   int match(char[] paramArrayOfchar, int paramInt1, int paramInt2);
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
/*    */   static CharTrie copyOf(Set<String> set, boolean ignoreCase) {
/* 68 */     switch (set.size()) {
/*    */       case 0:
/* 70 */         return new CharTrieOfNone();
/*    */       case 1:
/* 72 */         if (((String)set.iterator().next()).length() == 1) {
/* 73 */           return ignoreCase ? new CharTrieOfFewIgnoreCase(set) : new CharTrieOfOneSingleChar(set);
/*    */         }
/* 75 */         return ignoreCase ? new CharTrieOfFewIgnoreCase(set) : new CharTrieOfOne(set);
/*    */     } 
/* 77 */     return ignoreCase ? new CharTrieOfFewIgnoreCase(set) : new CharTrieOfFew(set);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\chr\CharTrie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */