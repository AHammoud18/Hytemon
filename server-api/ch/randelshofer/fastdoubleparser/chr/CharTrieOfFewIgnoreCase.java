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
/*    */ final class CharTrieOfFewIgnoreCase
/*    */   implements CharTrie
/*    */ {
/* 32 */   private CharTrieNode root = new CharTrieNode();
/*    */   
/*    */   public CharTrieOfFewIgnoreCase(Set<String> set) {
/* 35 */     for (String str : set) {
/* 36 */       if (!str.isEmpty()) {
/* 37 */         add(str);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void add(String str) {
/* 45 */     CharTrieNode upperNode = this.root;
/* 46 */     CharTrieNode lowerNode = this.root;
/* 47 */     String upperStr = str.toUpperCase();
/* 48 */     String lowerStr = upperStr.toLowerCase();
/* 49 */     for (int i = 0; i < str.length(); i++) {
/* 50 */       char upper = upperStr.charAt(i);
/* 51 */       char lower = lowerStr.charAt(i);
/* 52 */       upperNode = upperNode.insert(upper);
/* 53 */       lowerNode = lowerNode.insert(lower, upperNode);
/*    */     } 
/*    */     
/* 56 */     upperNode.setEnd();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int match(char[] str, int startIndex, int endIndex) {
/* 62 */     CharTrieNode node = this.root;
/* 63 */     int longestMatch = startIndex;
/* 64 */     for (int i = startIndex; i < endIndex; i++) {
/* 65 */       node = node.get(str[i]);
/* 66 */       if (node == null)
/* 67 */         break;  longestMatch = node.isEnd() ? (i + 1) : longestMatch;
/*    */     } 
/* 69 */     return longestMatch - startIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   public int match(CharSequence str, int startIndex, int endIndex) {
/* 74 */     CharTrieNode node = this.root;
/* 75 */     int longestMatch = startIndex;
/* 76 */     for (int i = startIndex; i < endIndex; i++) {
/* 77 */       node = node.get(str.charAt(i));
/* 78 */       if (node == null)
/* 79 */         break;  longestMatch = node.isEnd() ? (i + 1) : longestMatch;
/*    */     } 
/* 81 */     return longestMatch - startIndex;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\chr\CharTrieOfFewIgnoreCase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */