/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ import javax.net.ssl.SSLEngine;
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
/*    */ final class BoringSSLLoggingKeylog
/*    */   implements BoringSSLKeylog
/*    */ {
/* 24 */   static final BoringSSLLoggingKeylog INSTANCE = new BoringSSLLoggingKeylog();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 29 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(BoringSSLLoggingKeylog.class);
/*    */ 
/*    */   
/*    */   public void logKey(SSLEngine engine, String key) {
/* 33 */     logger.debug(key);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLLoggingKeylog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */