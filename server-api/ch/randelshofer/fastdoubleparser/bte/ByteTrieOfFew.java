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
/*    */ final class ByteTrieOfFew
/*    */   implements ByteTrie
/*    */ {
/* 14 */   private ByteTrieNode root = new ByteTrieNode();
/*    */   
/*    */   public ByteTrieOfFew(Set<String> set) {
/* 17 */     for (String str : set) {
/* 18 */       if (!str.isEmpty()) {
/* 19 */         add(str);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private void add(String str) {
/* 25 */     ByteTrieNode node = this.root;
/* 26 */     byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
/* 27 */     for (int i = 0; i < strBytes.length; i++) {
/* 28 */       node = node.insert(strBytes[i]);
/*    */     }
/* 30 */     node.setEnd();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int match(byte[] str, int startIndex, int endIndex) {
/* 36 */     ByteTrieNode node = this.root;
/* 37 */     int longestMatch = startIndex;
/* 38 */     for (int i = startIndex; i < endIndex; i++) {
/* 39 */       node = node.get(str[i]);
/* 40 */       if (node == null)
/* 41 */         break;  longestMatch = node.isEnd() ? (i + 1) : longestMatch;
/*    */     } 
/* 43 */     return longestMatch - startIndex;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\bte\ByteTrieOfFew.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */