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
/*    */ public class BsonWriterSettings
/*    */ {
/*    */   private final int maxSerializationDepth;
/*    */   
/*    */   public BsonWriterSettings(int maxSerializationDepth) {
/* 33 */     this.maxSerializationDepth = maxSerializationDepth;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BsonWriterSettings() {
/* 40 */     this(1024);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxSerializationDepth() {
/* 49 */     return this.maxSerializationDepth;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonWriterSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */