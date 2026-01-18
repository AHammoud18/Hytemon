/*    */ package org.bson;
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
/*    */ public class BsonBinaryWriterSettings
/*    */ {
/*    */   private final int maxDocumentSize;
/*    */   
/*    */   public BsonBinaryWriterSettings(int maxDocumentSize) {
/* 33 */     this.maxDocumentSize = maxDocumentSize;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BsonBinaryWriterSettings() {
/* 40 */     this(2147483647);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxDocumentSize() {
/* 49 */     return this.maxDocumentSize;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonBinaryWriterSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */