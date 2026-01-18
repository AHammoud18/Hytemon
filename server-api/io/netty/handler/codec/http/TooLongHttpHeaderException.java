/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.handler.codec.TooLongFrameException;
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
/*    */ public final class TooLongHttpHeaderException
/*    */   extends TooLongFrameException
/*    */ {
/*    */   private static final long serialVersionUID = -8295159138628369730L;
/*    */   
/*    */   public TooLongHttpHeaderException() {}
/*    */   
/*    */   public TooLongHttpHeaderException(String message, Throwable cause) {
/* 38 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TooLongHttpHeaderException(String message) {
/* 45 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TooLongHttpHeaderException(Throwable cause) {
/* 52 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\TooLongHttpHeaderException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */