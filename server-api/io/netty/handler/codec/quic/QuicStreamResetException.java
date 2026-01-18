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
/*    */ public final class QuicStreamResetException
/*    */   extends QuicException
/*    */ {
/*    */   private final long applicationProtocolCode;
/*    */   
/*    */   public QuicStreamResetException(String message, long applicationProtocolCode) {
/* 26 */     super(message);
/*    */     
/* 28 */     this.applicationProtocolCode = applicationProtocolCode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long applicationProtocolCode() {
/* 39 */     return this.applicationProtocolCode;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicStreamResetException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */