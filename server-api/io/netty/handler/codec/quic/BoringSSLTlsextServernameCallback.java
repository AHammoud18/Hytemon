/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import io.netty.util.Mapping;
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
/*    */ final class BoringSSLTlsextServernameCallback
/*    */ {
/*    */   private final QuicheQuicSslEngineMap engineMap;
/*    */   private final Mapping<? super String, ? extends QuicSslContext> mapping;
/*    */   
/*    */   BoringSSLTlsextServernameCallback(QuicheQuicSslEngineMap engineMap, Mapping<? super String, ? extends QuicSslContext> mapping) {
/* 27 */     this.engineMap = engineMap;
/* 28 */     this.mapping = mapping;
/*    */   }
/*    */ 
/*    */   
/*    */   long selectCtx(long ssl, String serverName) {
/* 33 */     QuicheQuicSslEngine engine = this.engineMap.get(ssl);
/* 34 */     if (engine == null)
/*    */     {
/* 36 */       return -1L;
/*    */     }
/*    */     
/* 39 */     QuicSslContext context = (QuicSslContext)this.mapping.map(serverName);
/* 40 */     if (context == null) {
/* 41 */       return -1L;
/*    */     }
/* 43 */     return engine.moveTo(serverName, (QuicheQuicSslContext)context);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\BoringSSLTlsextServernameCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */