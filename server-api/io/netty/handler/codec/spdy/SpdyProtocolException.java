/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ import io.netty.util.internal.ThrowableUtil;
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
/*    */ public class SpdyProtocolException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 7870000537743847264L;
/*    */   
/*    */   public SpdyProtocolException() {}
/*    */   
/*    */   public SpdyProtocolException(String message, Throwable cause) {
/* 33 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SpdyProtocolException(String message) {
/* 40 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SpdyProtocolException(Throwable cause) {
/* 47 */     super(cause);
/*    */   }
/*    */   
/*    */   static SpdyProtocolException newStatic(String message, Class<?> clazz, String method) {
/* 51 */     SpdyProtocolException exception = new StacklessSpdyProtocolException(message, true);
/* 52 */     return (SpdyProtocolException)ThrowableUtil.unknownStackTrace(exception, clazz, method);
/*    */   }
/*    */   
/*    */   private SpdyProtocolException(String message, boolean shared) {
/* 56 */     super(message, null, false, true);
/* 57 */     assert shared;
/*    */   }
/*    */   
/*    */   private static final class StacklessSpdyProtocolException extends SpdyProtocolException {
/*    */     private static final long serialVersionUID = -6302754207557485099L;
/*    */     
/*    */     StacklessSpdyProtocolException(String message, boolean shared) {
/* 64 */       super(message, shared);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Throwable fillInStackTrace() {
/* 71 */       return this;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\spdy\SpdyProtocolException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */