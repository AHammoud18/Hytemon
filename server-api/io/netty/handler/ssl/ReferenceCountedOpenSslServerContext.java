/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.internal.tcnative.CertificateCallback;
/*     */ import io.netty.internal.tcnative.SSL;
/*     */ import io.netty.internal.tcnative.SSLContext;
/*     */ import io.netty.internal.tcnative.SniHostNameMatcher;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.security.KeyStore;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SNIServerName;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import javax.net.ssl.X509ExtendedTrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ReferenceCountedOpenSslServerContext
/*     */   extends ReferenceCountedOpenSslContext
/*     */ {
/*  49 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslServerContext.class);
/*  50 */   private static final byte[] ID = new byte[] { 110, 101, 116, 116, 121 };
/*     */ 
/*     */ 
/*     */   
/*     */   private final OpenSslServerSessionContext sessionContext;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ReferenceCountedOpenSslServerContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp, String keyStore, ResumptionController resumptionController, Map.Entry<SslContextOption<?>, Object>... options) throws SSLException {
/*  60 */     this(trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, ciphers, cipherFilter, 
/*  61 */         toNegotiator(apn), sessionCacheSize, sessionTimeout, clientAuth, protocols, startTls, enableOcsp, keyStore, resumptionController, options);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ReferenceCountedOpenSslServerContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean enableOcsp, String keyStore, ResumptionController resumptionController, Map.Entry<SslContextOption<?>, Object>... options) throws SSLException {
/*  72 */     super(ciphers, cipherFilter, apn, 1, (Certificate[])keyCertChain, clientAuth, protocols, startTls, (String)null, enableOcsp, true, (List<SNIServerName>)null, resumptionController, options);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     boolean success = false;
/*     */     try {
/*  79 */       this.sessionContext = newSessionContext(this, this.ctx, this.engines, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, keyStore, sessionCacheSize, sessionTimeout, resumptionController, 
/*     */           
/*  81 */           isJdkSignatureFallbackEnabled(options));
/*  82 */       if (SERVER_ENABLE_SESSION_TICKET) {
/*  83 */         this.sessionContext.setTicketKeys(new OpenSslSessionTicketKey[0]);
/*     */       }
/*  85 */       success = true;
/*     */     } finally {
/*  87 */       if (!success) {
/*  88 */         release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public OpenSslServerSessionContext sessionContext() {
/*  95 */     return this.sessionContext;
/*     */   }
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
/*     */   static OpenSslServerSessionContext newSessionContext(ReferenceCountedOpenSslContext thiz, long ctx, Map<Long, ReferenceCountedOpenSslEngine> engines, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, String keyStore, long sessionCacheSize, long sessionTimeout, ResumptionController resumptionController, boolean fallbackToJdkSignatureProviders) throws SSLException {
/* 108 */     OpenSslKeyMaterialProvider keyMaterialProvider = null;
/*     */     try {
/*     */       try {
/* 111 */         SSLContext.setVerify(ctx, 0, 10);
/*     */ 
/*     */ 
/*     */         
/* 115 */         if (keyManagerFactory == null && key != null && key.getEncoded() == null) {
/* 116 */           if (!fallbackToJdkSignatureProviders)
/*     */           {
/* 118 */             throw new SSLException("Private key requiring alternative signature provider detected (such as hardware security key, smart card, or remote signing service) but alternative key fallback is disabled.");
/*     */           }
/*     */ 
/*     */           
/* 122 */           keyMaterialProvider = setupSecurityProviderSignatureSource(thiz, ctx, keyCertChain, key, manager -> new OpenSslServerCertificateCallback(engines, manager));
/*     */         }
/* 124 */         else if (!OpenSsl.useKeyManagerFactory()) {
/* 125 */           if (keyManagerFactory != null) {
/* 126 */             throw new IllegalArgumentException("KeyManagerFactory not supported with external keys");
/*     */           }
/*     */           
/* 129 */           ObjectUtil.checkNotNull(keyCertChain, "keyCertChain");
/*     */           
/* 131 */           setKeyMaterial(ctx, keyCertChain, key, keyPassword);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 136 */           if (keyManagerFactory == null) {
/* 137 */             keyManagerFactory = certChainToKeyManagerFactory(keyCertChain, key, keyPassword, keyStore);
/*     */           }
/* 139 */           keyMaterialProvider = providerFor(keyManagerFactory, keyPassword);
/*     */           
/* 141 */           SSLContext.setCertificateCallback(ctx, new OpenSslServerCertificateCallback(engines, new OpenSslKeyMaterialManager(keyMaterialProvider, thiz.hasTmpDhKeys)));
/*     */         }
/*     */       
/* 144 */       } catch (Exception e) {
/* 145 */         throw new SSLException("failed to set certificate and key", e);
/*     */       } 
/*     */       try {
/* 148 */         if (trustCertCollection != null) {
/* 149 */           trustManagerFactory = buildTrustManagerFactory(trustCertCollection, trustManagerFactory, keyStore);
/* 150 */         } else if (trustManagerFactory == null) {
/*     */           
/* 152 */           trustManagerFactory = TrustManagerFactory.getInstance(
/* 153 */               TrustManagerFactory.getDefaultAlgorithm());
/* 154 */           trustManagerFactory.init((KeyStore)null);
/*     */         } 
/*     */         
/* 157 */         X509TrustManager manager = chooseTrustManager(trustManagerFactory
/* 158 */             .getTrustManagers(), resumptionController);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 166 */         setVerifyCallback(ctx, engines, manager);
/*     */         
/* 168 */         X509Certificate[] issuers = manager.getAcceptedIssuers();
/* 169 */         if (issuers != null && issuers.length > 0) {
/* 170 */           long bio = 0L;
/*     */           try {
/* 172 */             bio = toBIO(ByteBufAllocator.DEFAULT, issuers);
/* 173 */             if (!SSLContext.setCACertificateBio(ctx, bio)) {
/* 174 */               String msg = "unable to setup accepted issuers for trustmanager " + manager;
/* 175 */               int error = SSL.getLastErrorNumber();
/* 176 */               if (error != 0) {
/* 177 */                 msg = msg + ". " + SSL.getErrorString(error);
/*     */               }
/* 179 */               throw new SSLException(msg);
/*     */             } 
/*     */           } finally {
/* 182 */             freeBio(bio);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 189 */         SSLContext.setSniHostnameMatcher(ctx, new OpenSslSniHostnameMatcher(engines));
/* 190 */       } catch (SSLException e) {
/* 191 */         throw e;
/* 192 */       } catch (Exception e) {
/* 193 */         throw new SSLException("unable to setup trustmanager", e);
/*     */       } 
/*     */       
/* 196 */       OpenSslServerSessionContext sessionContext = new OpenSslServerSessionContext(thiz, keyMaterialProvider);
/* 197 */       sessionContext.setSessionIdContext(ID);
/*     */       
/* 199 */       sessionContext.setSessionCacheEnabled(SERVER_ENABLE_SESSION_CACHE);
/* 200 */       if (sessionCacheSize > 0L) {
/* 201 */         sessionContext.setSessionCacheSize((int)Math.min(sessionCacheSize, 2147483647L));
/*     */       }
/* 203 */       if (sessionTimeout > 0L) {
/* 204 */         sessionContext.setSessionTimeout((int)Math.min(sessionTimeout, 2147483647L));
/*     */       }
/*     */       
/* 207 */       keyMaterialProvider = null;
/*     */       
/* 209 */       return sessionContext;
/*     */     } finally {
/* 211 */       if (keyMaterialProvider != null) {
/* 212 */         keyMaterialProvider.destroy();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setVerifyCallback(long ctx, Map<Long, ReferenceCountedOpenSslEngine> engines, X509TrustManager manager) {
/* 221 */     if (useExtendedTrustManager(manager)) {
/* 222 */       SSLContext.setCertVerifyCallback(ctx, new ExtendedTrustManagerVerifyCallback(engines, (X509ExtendedTrustManager)manager));
/*     */     } else {
/*     */       
/* 225 */       SSLContext.setCertVerifyCallback(ctx, new TrustManagerVerifyCallback(engines, manager));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class OpenSslServerCertificateCallback
/*     */     implements CertificateCallback {
/*     */     private final Map<Long, ReferenceCountedOpenSslEngine> engines;
/*     */     private final OpenSslKeyMaterialManager keyManagerHolder;
/*     */     
/*     */     OpenSslServerCertificateCallback(Map<Long, ReferenceCountedOpenSslEngine> engines, OpenSslKeyMaterialManager keyManagerHolder) {
/* 235 */       this.engines = engines;
/* 236 */       this.keyManagerHolder = keyManagerHolder;
/*     */     }
/*     */ 
/*     */     
/*     */     public void handle(long ssl, byte[] keyTypeBytes, byte[][] asn1DerEncodedPrincipals) throws Exception {
/* 241 */       ReferenceCountedOpenSslEngine engine = this.engines.get(Long.valueOf(ssl));
/* 242 */       if (engine == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 249 */         this.keyManagerHolder.setKeyMaterialServerSide(engine);
/* 250 */       } catch (Throwable cause) {
/* 251 */         engine.initHandshakeException(cause);
/*     */         
/* 253 */         if (cause instanceof Exception) {
/* 254 */           throw (Exception)cause;
/*     */         }
/* 256 */         throw new SSLException(cause);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class TrustManagerVerifyCallback
/*     */     extends ReferenceCountedOpenSslContext.AbstractCertificateVerifier {
/*     */     private final X509TrustManager manager;
/*     */     
/*     */     TrustManagerVerifyCallback(Map<Long, ReferenceCountedOpenSslEngine> engines, X509TrustManager manager) {
/* 266 */       super(engines);
/* 267 */       this.manager = manager;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void verify(ReferenceCountedOpenSslEngine engine, X509Certificate[] peerCerts, String auth) throws Exception {
/* 273 */       this.manager.checkClientTrusted(peerCerts, auth);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ExtendedTrustManagerVerifyCallback
/*     */     extends ReferenceCountedOpenSslContext.AbstractCertificateVerifier {
/*     */     private final X509ExtendedTrustManager manager;
/*     */     
/*     */     ExtendedTrustManagerVerifyCallback(Map<Long, ReferenceCountedOpenSslEngine> engines, X509ExtendedTrustManager manager) {
/* 282 */       super(engines);
/* 283 */       this.manager = manager;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void verify(ReferenceCountedOpenSslEngine engine, X509Certificate[] peerCerts, String auth) throws Exception {
/* 289 */       this.manager.checkClientTrusted(peerCerts, auth, engine);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class OpenSslSniHostnameMatcher implements SniHostNameMatcher {
/*     */     private final Map<Long, ReferenceCountedOpenSslEngine> engines;
/*     */     
/*     */     OpenSslSniHostnameMatcher(Map<Long, ReferenceCountedOpenSslEngine> engines) {
/* 297 */       this.engines = engines;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean match(long ssl, String hostname) {
/* 302 */       ReferenceCountedOpenSslEngine engine = this.engines.get(Long.valueOf(ssl));
/* 303 */       if (engine != null)
/*     */       {
/* 305 */         return engine.checkSniHostnameMatch(hostname.getBytes(CharsetUtil.UTF_8));
/*     */       }
/* 307 */       ReferenceCountedOpenSslServerContext.logger.warn("No ReferenceCountedOpenSslEngine found for SSL pointer: {}", Long.valueOf(ssl));
/* 308 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\ReferenceCountedOpenSslServerContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */