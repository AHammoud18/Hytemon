/*    */ package io.netty.handler.codec.quic;
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
/*    */ final class QuicStreamIdGenerator
/*    */ {
/*    */   private long nextBidirectionalStreamId;
/*    */   private long nextUnidirectionalStreamId;
/*    */   
/*    */   QuicStreamIdGenerator(boolean server) {
/* 27 */     this.nextBidirectionalStreamId = server ? 1L : 0L;
/* 28 */     this.nextUnidirectionalStreamId = server ? 3L : 2L;
/*    */   }
/*    */   
/*    */   long nextStreamId(boolean bidirectional) {
/* 32 */     if (bidirectional) {
/* 33 */       long l = this.nextBidirectionalStreamId;
/* 34 */       this.nextBidirectionalStreamId += 4L;
/* 35 */       return l;
/*    */     } 
/* 37 */     long stream = this.nextUnidirectionalStreamId;
/* 38 */     this.nextUnidirectionalStreamId += 4L;
/* 39 */     return stream;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicStreamIdGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */