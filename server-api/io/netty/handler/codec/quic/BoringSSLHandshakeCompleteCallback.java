/*    */ package io.netty.handler.codec.quic;
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
/*    */ final class BoringSSLHandshakeCompleteCallback
/*    */ {
/*    */   private final QuicheQuicSslEngineMap map;
/*    */   
/*    */   BoringSSLHandshakeCompleteCallback(QuicheQuicSslEngineMap map) {
/* 23 */     this.map = map;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void handshakeComplete(long ssl, byte[] id, String cipher, String protocol, byte[] peerCertificate, byte[][] peerCertificateChain, long creationTime, long timeout, byte[] applicationProtocol, boolean sessionReused) {
/* 30 */     QuicheQuicSslEngine engine = this.map.get(ssl);
/* 31 */     if (engine != null)
/* 32 */       engine.handshakeFinished(id, cipher, protocol, peerCertificate, peerCertificateChain, creationTime, timeout, applicationProtocol, sessionReused); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLHandshakeCompleteCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */