/*    */ package org.bson.json;
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
/*    */ class JsonStringBuffer
/*    */   implements JsonBuffer
/*    */ {
/*    */   private final String buffer;
/*    */   private int position;
/*    */   private boolean eof;
/*    */   
/*    */   JsonStringBuffer(String buffer) {
/* 26 */     this.buffer = buffer;
/*    */   }
/*    */   
/*    */   public int getPosition() {
/* 30 */     return this.position;
/*    */   }
/*    */   
/*    */   public int read() {
/* 34 */     if (this.eof)
/* 35 */       throw new JsonParseException("Trying to read past EOF."); 
/* 36 */     if (this.position >= this.buffer.length()) {
/* 37 */       this.eof = true;
/* 38 */       return -1;
/*    */     } 
/* 40 */     return this.buffer.charAt(this.position++);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unread(int c) {
/* 45 */     this.eof = false;
/* 46 */     if (c != -1 && this.buffer.charAt(this.position - 1) == c) {
/* 47 */       this.position--;
/*    */     }
/*    */   }
/*    */   
/*    */   public int mark() {
/* 52 */     return this.position;
/*    */   }
/*    */   
/*    */   public void reset(int markPos) {
/* 56 */     if (markPos > this.position) {
/* 57 */       throw new IllegalStateException("mark cannot reset ahead of position, only back");
/*    */     }
/* 59 */     this.position = markPos;
/*    */   }
/*    */   
/*    */   public void discard(int markPos) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonStringBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */