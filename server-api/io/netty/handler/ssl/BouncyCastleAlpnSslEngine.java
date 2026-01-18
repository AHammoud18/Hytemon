/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.BiFunction;
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
/*    */ final class BouncyCastleAlpnSslEngine
/*    */   extends JdkAlpnSslEngine
/*    */ {
/*    */   BouncyCastleAlpnSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
/* 28 */     super(engine, applicationNegotiator, isServer, new BiConsumer<SSLEngine, JdkAlpnSslEngine.AlpnSelector>()
/*    */         {
/*    */           public void accept(SSLEngine e, JdkAlpnSslEngine.AlpnSelector s)
/*    */           {
/* 32 */             BouncyCastleAlpnSslUtils.setHandshakeApplicationProtocolSelector(e, s);
/*    */           }
/*    */         }new BiConsumer<SSLEngine, List<String>>()
/*    */         {
/*    */           public void accept(SSLEngine e, List<String> p)
/*    */           {
/* 38 */             BouncyCastleAlpnSslUtils.setApplicationProtocols(e, p);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public String getApplicationProtocol() {
/* 44 */     return BouncyCastleAlpnSslUtils.getApplicationProtocol(getWrappedEngine());
/*    */   }
/*    */   
/*    */   public String getHandshakeApplicationProtocol() {
/* 48 */     return BouncyCastleAlpnSslUtils.getHandshakeApplicationProtocol(getWrappedEngine());
/*    */   }
/*    */   
/*    */   public void setHandshakeApplicationProtocolSelector(BiFunction<SSLEngine, List<String>, String> selector) {
/* 52 */     BouncyCastleAlpnSslUtils.setHandshakeApplicationProtocolSelector(getWrappedEngine(), selector);
/*    */   }
/*    */   
/*    */   public BiFunction<SSLEngine, List<String>, String> getHandshakeApplicationProtocolSelector() {
/* 56 */     return BouncyCastleAlpnSslUtils.getHandshakeApplicationProtocolSelector(getWrappedEngine());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\BouncyCastleAlpnSslEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */