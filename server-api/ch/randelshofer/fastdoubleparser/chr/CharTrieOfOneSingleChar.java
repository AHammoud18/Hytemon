/*    */ package ch.randelshofer.fastdoubleparser.chr;
/*    */ 
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CharTrieOfOneSingleChar
/*    */   implements CharTrie
/*    */ {
/*    */   private final char ch;
/*    */   
/*    */   public CharTrieOfOneSingleChar(Set<String> set) {
/* 13 */     if (set.size() != 1) throw new IllegalArgumentException("set size must be 1, size=" + set.size()); 
/* 14 */     char[] chars = ((String)set.iterator().next()).toCharArray();
/* 15 */     if (chars.length != 1) throw new IllegalArgumentException("char size must be 1, size=" + set.size()); 
/* 16 */     this.ch = chars[0];
/*    */   }
/*    */   
/*    */   public CharTrieOfOneSingleChar(char ch) {
/* 20 */     this.ch = ch;
/*    */   }
/*    */ 
/*    */   
/*    */   public int match(CharSequence str, int startIndex, int endIndex) {
/* 25 */     return (startIndex < endIndex && str.charAt(startIndex) == this.ch) ? 1 : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int match(char[] str) {
/* 30 */     return match(str, 0, str.length);
/*    */   }
/*    */ 
/*    */   
/*    */   public int match(char[] str, int startIndex, int endIndex) {
/* 35 */     return (startIndex < endIndex && str[startIndex] == this.ch) ? 1 : 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\chr\CharTrieOfOneSingleChar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */