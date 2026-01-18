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
/*    */ public final class TooLongHttpLineException
/*    */   extends TooLongFrameException
/*    */ {
/*    */   private static final long serialVersionUID = 1614751125592211890L;
/*    */   
/*    */   public TooLongHttpLineException() {}
/*    */   
/*    */   public TooLongHttpLineException(String message, Throwable cause) {
/* 38 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TooLongHttpLineException(String message) {
/* 45 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TooLongHttpLineException(Throwable cause) {
/* 52 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\TooLongHttpLineException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */