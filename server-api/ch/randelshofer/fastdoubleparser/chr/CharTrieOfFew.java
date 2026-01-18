/*    */ package ch.randelshofer.fastdoubleparser.chr;
/*    */ 
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CharTrieOfFew
/*    */   implements CharTrie
/*    */ {
/* 13 */   private CharTrieNode root = new CharTrieNode();
/*    */   
/*    */   public CharTrieOfFew(Set<String> set) {
/* 16 */     for (String str : set) {
/* 17 */       if (!str.isEmpty()) {
/* 18 */         add(str);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private void add(String str) {
/* 24 */     CharTrieNode node = this.root;
/* 25 */     for (int i = 0; i < str.length(); i++) {
/* 26 */       node = node.insert(str.charAt(i));
/*    */     }
/* 28 */     node.setEnd();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int match(CharSequence str, int startIndex, int endIndex) {
/* 34 */     CharTrieNode node = this.root;
/* 35 */     int longestMatch = startIndex;
/* 36 */     for (int i = startIndex; i < endIndex; i++) {
/* 37 */       node = node.get(str.charAt(i));
/* 38 */       if (node == null)
/* 39 */         break;  longestMatch = node.isEnd() ? (i + 1) : longestMatch;
/*    */     } 
/* 41 */     return longestMatch - startIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   public int match(char[] str, int startIndex, int endIndex) {
/* 46 */     CharTrieNode node = this.root;
/* 47 */     int longestMatch = startIndex;
/* 48 */     for (int i = startIndex; i < endIndex; i++) {
/* 49 */       node = node.get(str[i]);
/* 50 */       if (node == null)
/* 51 */         break;  longestMatch = node.isEnd() ? (i + 1) : longestMatch;
/*    */     } 
/* 53 */     return longestMatch - startIndex;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\chr\CharTrieOfFew.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */