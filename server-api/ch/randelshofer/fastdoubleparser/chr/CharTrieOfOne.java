/*    */ package ch.randelshofer.fastdoubleparser.chr;
/*    */ 
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CharTrieOfOne
/*    */   implements CharTrie
/*    */ {
/*    */   private final char[] chars;
/*    */   
/*    */   public CharTrieOfOne(Set<String> set) {
/* 13 */     if (set.size() != 1) throw new IllegalArgumentException("set size must be 1, size=" + set.size()); 
/* 14 */     this.chars = ((String)set.iterator().next()).toCharArray();
/*    */   }
/*    */   
/*    */   public CharTrieOfOne(char[] chars) {
/* 18 */     this.chars = chars;
/*    */   }
/*    */ 
/*    */   
/*    */   public int match(CharSequence str) {
/* 23 */     return match(str, 0, str.length());
/*    */   }
/*    */ 
/*    */   
/*    */   public int match(CharSequence str, int startIndex, int endIndex) {
/* 28 */     int i = 0;
/* 29 */     int limit = Math.min(endIndex - startIndex, this.chars.length);
/* 30 */     while (i < limit && str.charAt(i + startIndex) == this.chars[i]) {
/* 31 */       i++;
/*    */     }
/* 33 */     return (i == this.chars.length) ? this.chars.length : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int match(char[] str) {
/* 38 */     return match(str, 0, str.length);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int match(char[] str, int startIndex, int endIndex) {
/* 47 */     int i = 0;
/* 48 */     int limit = Math.min(endIndex - startIndex, this.chars.length);
/* 49 */     while (i < limit && str[i + startIndex] == this.chars[i]) {
/* 50 */       i++;
/*    */     }
/* 52 */     return (i == this.chars.length) ? this.chars.length : 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\chr\CharTrieOfOne.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */