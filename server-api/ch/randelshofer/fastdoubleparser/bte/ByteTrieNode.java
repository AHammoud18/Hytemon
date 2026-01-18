/*    */ package ch.randelshofer.fastdoubleparser.bte;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ByteTrieNode
/*    */ {
/* 13 */   private byte[] chars = new byte[0];
/* 14 */   private ByteTrieNode[] children = new ByteTrieNode[0];
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
/*    */   public ByteTrieNode insert(byte ch) {
/* 29 */     int index = indexOf(ch);
/* 30 */     if (index < 0) {
/* 31 */       index = this.chars.length;
/* 32 */       this.chars = Arrays.copyOf(this.chars, this.chars.length + 1);
/* 33 */       this.children = Arrays.<ByteTrieNode>copyOf(this.children, this.children.length + 1);
/* 34 */       this.chars[index] = ch;
/* 35 */       this.children[index] = new ByteTrieNode();
/*    */     } 
/* 37 */     return this.children[index];
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
/*    */   public ByteTrieNode insert(byte ch, ByteTrieNode forcedNode) {
/* 49 */     int index = indexOf(ch);
/* 50 */     if (index < 0) {
/* 51 */       index = this.chars.length;
/* 52 */       this.chars = Arrays.copyOf(this.chars, this.chars.length + 1);
/* 53 */       this.children = Arrays.<ByteTrieNode>copyOf(this.children, this.children.length + 1);
/* 54 */       this.chars[index] = ch;
/* 55 */       this.children[index] = forcedNode;
/*    */     } 
/* 57 */     if (this.children[index] != forcedNode) {
/* 58 */       throw new AssertionError("trie is corrupt");
/*    */     }
/* 60 */     return this.children[index];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteTrieNode get(byte ch) {
/* 70 */     int index = indexOf(ch);
/* 71 */     return (index < 0) ? null : this.children[index];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int indexOf(byte ch) {
/* 82 */     int index = -1;
/* 83 */     for (int i = 0; i < this.chars.length; i++) {
/* 84 */       if (this.chars[i] == ch) index = i; 
/*    */     } 
/* 86 */     return index;
/*    */   }
/*    */   
/*    */   public void setEnd() {
/* 90 */     this.isEnd = true;
/*    */   }
/*    */   
/*    */   public boolean isEnd() {
/* 94 */     return this.isEnd;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\bte\ByteTrieNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */