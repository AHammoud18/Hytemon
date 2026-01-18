/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLEngineResult;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLParameters;
/*     */ import javax.net.ssl.SSLSession;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class JdkSslEngine
/*     */   extends SSLEngine
/*     */   implements ApplicationProtocolAccessor
/*     */ {
/*     */   private final SSLEngine engine;
/*     */   private volatile String applicationProtocol;
/*     */   
/*     */   JdkSslEngine(SSLEngine engine) {
/*  31 */     this.engine = engine;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNegotiatedApplicationProtocol() {
/*  36 */     return this.applicationProtocol;
/*     */   }
/*     */   
/*     */   void setNegotiatedApplicationProtocol(String applicationProtocol) {
/*  40 */     this.applicationProtocol = applicationProtocol;
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLSession getSession() {
/*  45 */     return this.engine.getSession();
/*     */   }
/*     */   
/*     */   public SSLEngine getWrappedEngine() {
/*  49 */     return this.engine;
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeInbound() throws SSLException {
/*  54 */     this.engine.closeInbound();
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeOutbound() {
/*  59 */     this.engine.closeOutbound();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPeerHost() {
/*  64 */     return this.engine.getPeerHost();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPeerPort() {
/*  69 */     return this.engine.getPeerPort();
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
/*  74 */     return this.engine.wrap(byteBuffer, byteBuffer2);
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult wrap(ByteBuffer[] byteBuffers, ByteBuffer byteBuffer) throws SSLException {
/*  79 */     return this.engine.wrap(byteBuffers, byteBuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult wrap(ByteBuffer[] byteBuffers, int i, int i2, ByteBuffer byteBuffer) throws SSLException {
/*  84 */     return this.engine.wrap(byteBuffers, i, i2, byteBuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
/*  89 */     return this.engine.unwrap(byteBuffer, byteBuffer2);
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBuffers) throws SSLException {
/*  94 */     return this.engine.unwrap(byteBuffer, byteBuffers);
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBuffers, int i, int i2) throws SSLException {
/*  99 */     return this.engine.unwrap(byteBuffer, byteBuffers, i, i2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Runnable getDelegatedTask() {
/* 104 */     return this.engine.getDelegatedTask();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInboundDone() {
/* 109 */     return this.engine.isInboundDone();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOutboundDone() {
/* 114 */     return this.engine.isOutboundDone();
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getSupportedCipherSuites() {
/* 119 */     return this.engine.getSupportedCipherSuites();
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getEnabledCipherSuites() {
/* 124 */     return this.engine.getEnabledCipherSuites();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabledCipherSuites(String[] strings) {
/* 129 */     this.engine.setEnabledCipherSuites(strings);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getSupportedProtocols() {
/* 134 */     return this.engine.getSupportedProtocols();
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getEnabledProtocols() {
/* 139 */     return this.engine.getEnabledProtocols();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabledProtocols(String[] strings) {
/* 144 */     this.engine.setEnabledProtocols(strings);
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLSession getHandshakeSession() {
/* 149 */     return this.engine.getHandshakeSession();
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginHandshake() throws SSLException {
/* 154 */     this.engine.beginHandshake();
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
/* 159 */     return this.engine.getHandshakeStatus();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUseClientMode(boolean b) {
/* 164 */     this.engine.setUseClientMode(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getUseClientMode() {
/* 169 */     return this.engine.getUseClientMode();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNeedClientAuth(boolean b) {
/* 174 */     this.engine.setNeedClientAuth(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getNeedClientAuth() {
/* 179 */     return this.engine.getNeedClientAuth();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWantClientAuth(boolean b) {
/* 184 */     this.engine.setWantClientAuth(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getWantClientAuth() {
/* 189 */     return this.engine.getWantClientAuth();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnableSessionCreation(boolean b) {
/* 194 */     this.engine.setEnableSessionCreation(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getEnableSessionCreation() {
/* 199 */     return this.engine.getEnableSessionCreation();
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLParameters getSSLParameters() {
/* 204 */     return this.engine.getSSLParameters();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSSLParameters(SSLParameters sslParameters) {
/* 209 */     this.engine.setSSLParameters(sslParameters);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\JdkSslEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */