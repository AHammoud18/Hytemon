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
/*    */ public final class TooLongHttpContentException
/*    */   extends TooLongFrameException
/*    */ {
/*    */   private static final long serialVersionUID = 3238341182129476117L;
/*    */   
/*    */   public TooLongHttpContentException() {}
/*    */   
/*    */   public TooLongHttpContentException(String message, Throwable cause) {
/* 38 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TooLongHttpContentException(String message) {
/* 45 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TooLongHttpContentException(Throwable cause) {
/* 52 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\TooLongHttpContentException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */