/*    */ package ch.randelshofer.fastdoubleparser.bte;
/*    */ 
/*    */ import java.nio.charset.StandardCharsets;
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
/*    */ final class ByteTrieOfFewIgnoreCase
/*    */   implements ByteTrie
/*    */ {
/* 50 */   private ByteTrieNode root = new ByteTrieNode();
/*    */   
/*    */   public ByteTrieOfFewIgnoreCase(Set<String> set) {
/* 53 */     for (String str : set) {
/* 54 */       if (!str.isEmpty()) {
/* 55 */         add(str);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void add(String str) {
/* 63 */     ByteTrieNode upperNode = this.root;
/* 64 */     ByteTrieNode lowerNode = this.root;
/* 65 */     String upperStr = str.toUpperCase();
/* 66 */     String lowerStr = upperStr.toLowerCase();
/* 67 */     for (int i = 0; i < str.length(); i++) {
/* 68 */       byte[] upper = upperStr.substring(i, i + 1).getBytes(StandardCharsets.UTF_8);
/* 69 */       byte[] lower = lowerStr.substring(i, i + 1).getBytes(StandardCharsets.UTF_8);
/* 70 */       for (int u = 0; u < upper.length; u++) {
/* 71 */         upperNode = upperNode.insert(upper[u]);
/*    */       }
/* 73 */       for (int l = 0; l < upper.length - 1; l++) {
/* 74 */         lowerNode = lowerNode.insert(lower[l]);
/*    */       }
/* 76 */       lowerNode = lowerNode.insert(lower[lower.length - 1], upperNode);
/*    */     } 
/*    */     
/* 79 */     upperNode.setEnd();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int match(byte[] str, int startIndex, int endIndex) {
/* 85 */     ByteTrieNode node = this.root;
/* 86 */     int longestMatch = startIndex;
/* 87 */     for (int i = startIndex; i < endIndex; i++) {
/* 88 */       node = node.get(str[i]);
/* 89 */       if (node == null)
/* 90 */         break;  longestMatch = node.isEnd() ? (i + 1) : longestMatch;
/*    */     } 
/* 92 */     return longestMatch - startIndex;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\bte\ByteTrieOfFewIgnoreCase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */