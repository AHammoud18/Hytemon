/*    */ package io.netty.handler.codec.quic;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ final class BoringSSLKeylogCallback
/*    */ {
/*    */   private final QuicheQuicSslEngineMap engineMap;
/*    */   private final BoringSSLKeylog keylog;
/*    */   
/*    */   BoringSSLKeylogCallback(QuicheQuicSslEngineMap engineMap, BoringSSLKeylog keylog) {
/* 27 */     this.engineMap = engineMap;
/* 28 */     this.keylog = keylog;
/*    */   }
/*    */ 
/*    */   
/*    */   void logKey(long ssl, String key) {
/* 33 */     SSLEngine engine = this.engineMap.get(ssl);
/* 34 */     if (engine != null)
/* 35 */       this.keylog.logKey(engine, key); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLKeylogCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */