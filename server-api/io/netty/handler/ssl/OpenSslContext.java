/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.buffer.ByteBufAllocator;
/*    */ import java.security.cert.Certificate;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.net.ssl.SNIServerName;
/*    */ import javax.net.ssl.SSLEngine;
/*    */ import javax.net.ssl.SSLException;
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
/*    */ public abstract class OpenSslContext
/*    */   extends ReferenceCountedOpenSslContext
/*    */ {
/*    */   OpenSslContext(Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apnCfg, int mode, Certificate[] keyCertChain, ClientAuth clientAuth, String[] protocols, boolean startTls, String endpointIdentificationAlgorithm, boolean enableOcsp, List<SNIServerName> serverNames, ResumptionController resumptionController, Map.Entry<SslContextOption<?>, Object>... options) throws SSLException {
/* 39 */     super(ciphers, cipherFilter, toNegotiator(apnCfg), mode, keyCertChain, clientAuth, protocols, startTls, endpointIdentificationAlgorithm, enableOcsp, false, serverNames, resumptionController, options);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   OpenSslContext(Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, int mode, Certificate[] keyCertChain, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp, List<SNIServerName> serverNames, ResumptionController resumptionController, Map.Entry<SslContextOption<?>, Object>... options) throws SSLException {
/* 50 */     super(ciphers, cipherFilter, apn, mode, keyCertChain, clientAuth, protocols, startTls, null, enableOcsp, false, serverNames, resumptionController, options);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   final SSLEngine newEngine0(ByteBufAllocator alloc, String peerHost, int peerPort, boolean jdkCompatibilityMode) {
/* 56 */     return new OpenSslEngine(this, alloc, peerHost, peerPort, jdkCompatibilityMode, this.endpointIdentificationAlgorithm, this.serverNames);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final void finalize() throws Throwable {
/* 63 */     super.finalize();
/* 64 */     OpenSsl.releaseIfNeeded(this);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */