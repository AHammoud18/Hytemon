/*    */ package io.netty.handler.codec.http3;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface QpackDecoderStateSyncStrategy
/*    */ {
/*    */   void sectionAcknowledged(int paramInt);
/*    */   
/*    */   boolean entryAdded(int paramInt);
/*    */   
/*    */   static QpackDecoderStateSyncStrategy ackEachInsert() {
/* 55 */     return new QpackDecoderStateSyncStrategy()
/*    */       {
/*    */         private int lastCountAcknowledged;
/*    */         
/*    */         public void sectionAcknowledged(int requiredInsertCount) {
/* 60 */           if (this.lastCountAcknowledged < requiredInsertCount) {
/* 61 */             this.lastCountAcknowledged = requiredInsertCount;
/*    */           }
/*    */         }
/*    */ 
/*    */         
/*    */         public boolean entryAdded(int insertCount) {
/* 67 */           if (this.lastCountAcknowledged < insertCount) {
/* 68 */             this.lastCountAcknowledged = insertCount;
/* 69 */             return true;
/*    */           } 
/* 71 */           return false;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\QpackDecoderStateSyncStrategy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */