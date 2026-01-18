/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.util.internal.ThrowableUtil;
/*    */ import javax.net.ssl.SSLHandshakeException;
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
/*    */ final class StacklessSSLHandshakeException
/*    */   extends SSLHandshakeException
/*    */ {
/*    */   private static final long serialVersionUID = -1244781947804415549L;
/*    */   
/*    */   private StacklessSSLHandshakeException(String reason) {
/* 30 */     super(reason);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable fillInStackTrace() {
/* 37 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static StacklessSSLHandshakeException newInstance(String reason, Class<?> clazz, String method) {
/* 44 */     return (StacklessSSLHandshakeException)ThrowableUtil.unknownStackTrace(new StacklessSSLHandshakeException(reason), clazz, method);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\StacklessSSLHandshakeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */