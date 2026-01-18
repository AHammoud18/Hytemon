/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ public class QuicException
/*    */   extends Exception
/*    */ {
/*    */   private final QuicTransportError error;
/*    */   
/*    */   QuicException(String message) {
/* 28 */     super(message);
/* 29 */     this.error = null;
/*    */   }
/*    */   
/*    */   public QuicException(QuicTransportError error) {
/* 33 */     super(error.name());
/* 34 */     this.error = error;
/*    */   }
/*    */   
/*    */   public QuicException(String message, QuicTransportError error) {
/* 38 */     super(message);
/* 39 */     this.error = error;
/*    */   }
/*    */   
/*    */   public QuicException(Throwable cause, QuicTransportError error) {
/* 43 */     super(cause);
/* 44 */     this.error = error;
/*    */   }
/*    */   
/*    */   public QuicException(String message, Throwable cause, QuicTransportError error) {
/* 48 */     super(message, cause);
/* 49 */     this.error = error;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public QuicTransportError error() {
/* 60 */     return this.error;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */