/*    */ package io.netty.handler.ssl;
/*    */ 
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
/*    */ public final class SslHandshakeTimeoutException
/*    */   extends SSLHandshakeException
/*    */ {
/*    */   public SslHandshakeTimeoutException(String reason) {
/* 26 */     super(reason);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\SslHandshakeTimeoutException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */