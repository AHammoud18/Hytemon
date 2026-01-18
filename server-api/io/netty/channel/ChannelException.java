/*    */ package io.netty.channel;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChannelException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 2908618315971075004L;
/*    */   
/*    */   public ChannelException() {}
/*    */   
/*    */   public ChannelException(String message, Throwable cause) {
/* 38 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChannelException(String message) {
/* 45 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChannelException(Throwable cause) {
/* 52 */     super(cause);
/*    */   }
/*    */   
/*    */   protected ChannelException(String message, Throwable cause, boolean shared) {
/* 56 */     super(message, cause, false, true);
/* 57 */     assert shared;
/*    */   }
/*    */   
/*    */   static ChannelException newStatic(String message, Class<?> clazz, String method) {
/* 61 */     ChannelException exception = new StacklessChannelException(message, null, true);
/* 62 */     return (ChannelException)ThrowableUtil.unknownStackTrace(exception, clazz, method);
/*    */   }
/*    */   
/*    */   private static final class StacklessChannelException extends ChannelException {
/*    */     private static final long serialVersionUID = -6384642137753538579L;
/*    */     
/*    */     StacklessChannelException(String message, Throwable cause, boolean shared) {
/* 69 */       super(message, cause, shared);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Throwable fillInStackTrace() {
/* 77 */       return this;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */