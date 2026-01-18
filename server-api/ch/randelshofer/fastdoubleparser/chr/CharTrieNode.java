/*    */ package ch.randelshofer.fastdoubleparser.chr;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CharTrieNode
/*    */ {
/* 11 */   private char[] chars = new char[0];
/* 12 */   private CharTrieNode[] children = new CharTrieNode[0];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean isEnd;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CharTrieNode insert(char ch) {
/* 28 */     int index = indexOf(ch);
/* 29 */     if (index < 0) {
/* 30 */       index = this.chars.length;
/* 31 */       this.chars = Arrays.copyOf(this.chars, this.chars.length + 1);
/* 32 */       this.children = Arrays.<CharTrieNode>copyOf(this.children, this.children.length + 1);
/* 33 */       this.chars[index] = ch;
/* 34 */       this.children[index] = new CharTrieNode();
/*    */     } 
/* 36 */     return this.children[index];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CharTrieNode get(char ch) {
/* 46 */     int index = indexOf(ch);
/* 47 */     return (index < 0) ? null : this.children[index];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int indexOf(char ch) {
/* 58 */     int index = -1;
/* 59 */     for (int i = 0; i < this.chars.length; i++) {
/* 60 */       if (this.chars[i] == ch) index = i; 
/*    */     } 
/* 62 */     return index;
/*    */   }
/*    */   
/*    */   public void setEnd() {
/* 66 */     this.isEnd = true;
/*    */   }
/*    */   
/*    */   public boolean isEnd() {
/* 70 */     return this.isEnd;
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
/*    */   public CharTrieNode insert(char ch, CharTrieNode forcedNode) {
/* 82 */     int index = indexOf(ch);
/* 83 */     if (index < 0) {
/* 84 */       index = this.chars.length;
/* 85 */       this.chars = Arrays.copyOf(this.chars, this.chars.length + 1);
/* 86 */       this.children = Arrays.<CharTrieNode>copyOf(this.children, this.children.length + 1);
/* 87 */       this.chars[index] = ch;
/* 88 */       this.children[index] = forcedNode;
/*    */     } 
/* 90 */     if (this.children[index] != forcedNode) {
/* 91 */       throw new AssertionError("trie is corrupt");
/*    */     }
/* 93 */     return this.children[index];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\chr\CharTrieNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */