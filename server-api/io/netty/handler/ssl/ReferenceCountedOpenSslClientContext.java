/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.internal.tcnative.CertificateCallback;
/*     */ import io.netty.internal.tcnative.SSL;
/*     */ import io.netty.internal.tcnative.SSLContext;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import java.security.KeyStore;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SNIServerName;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import javax.net.ssl.X509ExtendedTrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ import javax.security.auth.x500.X500Principal;
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
/*     */ 
/*     */ public final class ReferenceCountedOpenSslClientContext
/*     */   extends ReferenceCountedOpenSslContext
/*     */ {
/*  48 */   private static final String[] SUPPORTED_KEY_TYPES = new String[] { "RSA", "DH_RSA", "EC", "EC_RSA", "EC_EC" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final OpenSslSessionContext sessionContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ReferenceCountedOpenSslClientContext(X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apn, String[] protocols, long sessionCacheSize, long sessionTimeout, boolean enableOcsp, String keyStore, String endpointIdentificationAlgorithm, List<SNIServerName> serverNames, ResumptionController resumptionController, Map.Entry<SslContextOption<?>, Object>... options) throws SSLException {
/*  66 */     super(ciphers, cipherFilter, toNegotiator(apn), 0, (Certificate[])keyCertChain, ClientAuth.NONE, protocols, false, endpointIdentificationAlgorithm, enableOcsp, true, serverNames, resumptionController, options);
/*     */ 
/*     */     
/*  69 */     boolean success = false;
/*     */     try {
/*  71 */       this.sessionContext = newSessionContext(this, this.ctx, this.engines, trustCertCollection, trustManagerFactory, keyCertChain, key, keyPassword, keyManagerFactory, keyStore, sessionCacheSize, sessionTimeout, resumptionController, 
/*     */ 
/*     */           
/*  74 */           isJdkSignatureFallbackEnabled(options));
/*  75 */       success = true;
/*     */     } finally {
/*  77 */       if (!success) {
/*  78 */         release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public OpenSslSessionContext sessionContext() {
/*  85 */     return this.sessionContext;
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
/*     */   static OpenSslSessionContext newSessionContext(ReferenceCountedOpenSslContext thiz, long ctx, Map<Long, ReferenceCountedOpenSslEngine> engines, X509Certificate[] trustCertCollection, TrustManagerFactory trustManagerFactory, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, KeyManagerFactory keyManagerFactory, String keyStore, long sessionCacheSize, long sessionTimeout, ResumptionController resumptionController, boolean fallbackToJdkProviders) throws SSLException {
/*  98 */     if ((key == null && keyCertChain != null) || (key != null && keyCertChain == null)) {
/*  99 */       throw new IllegalArgumentException("Either both keyCertChain and key needs to be null or none of them");
/*     */     }
/*     */     
/* 102 */     OpenSslKeyMaterialProvider keyMaterialProvider = null;
/*     */ 
/*     */     
/*     */     try {
/*     */       try {
/* 107 */         if (keyManagerFactory == null && key != null && key.getEncoded() == null) {
/* 108 */           if (!fallbackToJdkProviders) {
/* 109 */             throw new SSLException("Private key requiring alternative signature provider detected (such as hardware security key, smart card, or remote signing service) but alternative key fallback is disabled.");
/*     */           }
/*     */ 
/*     */           
/* 113 */           keyMaterialProvider = setupSecurityProviderSignatureSource(thiz, ctx, keyCertChain, key, materialManager -> new OpenSslClientCertificateCallback(engines, materialManager));
/*     */         
/*     */         }
/* 116 */         else if (!OpenSsl.useKeyManagerFactory()) {
/* 117 */           if (keyManagerFactory != null) {
/* 118 */             throw new IllegalArgumentException("KeyManagerFactory not supported");
/*     */           }
/*     */           
/* 121 */           if (keyCertChain != null) {
/* 122 */             setKeyMaterial(ctx, keyCertChain, key, keyPassword);
/*     */           }
/*     */         } else {
/*     */           
/* 126 */           if (keyManagerFactory == null && keyCertChain != null) {
/* 127 */             keyManagerFactory = certChainToKeyManagerFactory(keyCertChain, key, keyPassword, keyStore);
/*     */           }
/* 129 */           if (keyManagerFactory != null) {
/* 130 */             keyMaterialProvider = providerFor(keyManagerFactory, keyPassword);
/*     */           }
/*     */           
/* 133 */           if (keyMaterialProvider != null) {
/* 134 */             OpenSslKeyMaterialManager materialManager = new OpenSslKeyMaterialManager(keyMaterialProvider, thiz.hasTmpDhKeys);
/*     */             
/* 136 */             SSLContext.setCertificateCallback(ctx, new OpenSslClientCertificateCallback(engines, materialManager));
/*     */           }
/*     */         
/*     */         } 
/* 140 */       } catch (Exception e) {
/* 141 */         throw new SSLException("failed to set certificate and key", e);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       SSLContext.setVerify(ctx, 1, 10);
/*     */       
/*     */       try {
/* 153 */         if (trustCertCollection != null) {
/* 154 */           trustManagerFactory = buildTrustManagerFactory(trustCertCollection, trustManagerFactory, keyStore);
/* 155 */         } else if (trustManagerFactory == null) {
/* 156 */           trustManagerFactory = TrustManagerFactory.getInstance(
/* 157 */               TrustManagerFactory.getDefaultAlgorithm());
/* 158 */           trustManagerFactory.init((KeyStore)null);
/*     */         } 
/* 160 */         X509TrustManager manager = chooseTrustManager(trustManagerFactory
/* 161 */             .getTrustManagers(), resumptionController);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 169 */         setVerifyCallback(ctx, engines, manager);
/* 170 */       } catch (Exception e) {
/* 171 */         if (keyMaterialProvider != null) {
/* 172 */           keyMaterialProvider.destroy();
/*     */         }
/* 174 */         throw new SSLException("unable to setup trustmanager", e);
/*     */       } 
/* 176 */       OpenSslClientSessionContext context = new OpenSslClientSessionContext(thiz, keyMaterialProvider);
/* 177 */       context.setSessionCacheEnabled(CLIENT_ENABLE_SESSION_CACHE);
/* 178 */       if (sessionCacheSize > 0L) {
/* 179 */         context.setSessionCacheSize((int)Math.min(sessionCacheSize, 2147483647L));
/*     */       }
/* 181 */       if (sessionTimeout > 0L) {
/* 182 */         context.setSessionTimeout((int)Math.min(sessionTimeout, 2147483647L));
/*     */       }
/*     */       
/* 185 */       if (CLIENT_ENABLE_SESSION_TICKET) {
/* 186 */         context.setTicketKeys(new OpenSslSessionTicketKey[0]);
/*     */       }
/*     */       
/* 189 */       keyMaterialProvider = null;
/* 190 */       return context;
/*     */     } finally {
/* 192 */       if (keyMaterialProvider != null) {
/* 193 */         keyMaterialProvider.destroy();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setVerifyCallback(long ctx, Map<Long, ReferenceCountedOpenSslEngine> engines, X509TrustManager manager) {
/* 202 */     if (useExtendedTrustManager(manager)) {
/* 203 */       SSLContext.setCertVerifyCallback(ctx, new ExtendedTrustManagerVerifyCallback(engines, (X509ExtendedTrustManager)manager));
/*     */     } else {
/*     */       
/* 206 */       SSLContext.setCertVerifyCallback(ctx, new TrustManagerVerifyCallback(engines, manager));
/*     */     } 
/*     */   }
/*     */   
/*     */   static final class OpenSslClientSessionContext extends OpenSslSessionContext {
/*     */     OpenSslClientSessionContext(ReferenceCountedOpenSslContext context, OpenSslKeyMaterialProvider provider) {
/* 212 */       super(context, provider, SSL.SSL_SESS_CACHE_CLIENT, new OpenSslClientSessionCache(context.engines));
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class TrustManagerVerifyCallback extends ReferenceCountedOpenSslContext.AbstractCertificateVerifier {
/*     */     private final X509TrustManager manager;
/*     */     
/*     */     TrustManagerVerifyCallback(Map<Long, ReferenceCountedOpenSslEngine> engines, X509TrustManager manager) {
/* 220 */       super(engines);
/* 221 */       this.manager = manager;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void verify(ReferenceCountedOpenSslEngine engine, X509Certificate[] peerCerts, String auth) throws Exception {
/* 227 */       this.manager.checkServerTrusted(peerCerts, auth);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ExtendedTrustManagerVerifyCallback
/*     */     extends ReferenceCountedOpenSslContext.AbstractCertificateVerifier {
/*     */     private final X509ExtendedTrustManager manager;
/*     */     
/*     */     ExtendedTrustManagerVerifyCallback(Map<Long, ReferenceCountedOpenSslEngine> engines, X509ExtendedTrustManager manager) {
/* 236 */       super(engines);
/* 237 */       this.manager = manager;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void verify(ReferenceCountedOpenSslEngine engine, X509Certificate[] peerCerts, String auth) throws Exception {
/* 243 */       this.manager.checkServerTrusted(peerCerts, auth, engine);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class OpenSslClientCertificateCallback
/*     */     implements CertificateCallback {
/*     */     private final Map<Long, ReferenceCountedOpenSslEngine> engines;
/*     */     private final OpenSslKeyMaterialManager keyManagerHolder;
/*     */     
/*     */     OpenSslClientCertificateCallback(Map<Long, ReferenceCountedOpenSslEngine> engines, OpenSslKeyMaterialManager keyManagerHolder) {
/* 253 */       this.engines = engines;
/* 254 */       this.keyManagerHolder = keyManagerHolder;
/*     */     }
/*     */ 
/*     */     
/*     */     public void handle(long ssl, byte[] keyTypeBytes, byte[][] asn1DerEncodedPrincipals) throws Exception {
/* 259 */       ReferenceCountedOpenSslEngine engine = this.engines.get(Long.valueOf(ssl));
/*     */       
/* 261 */       if (engine == null)
/*     */         return; 
/*     */       try {
/*     */         X500Principal[] issuers;
/* 265 */         String[] keyTypes = supportedClientKeyTypes(keyTypeBytes);
/*     */         
/* 267 */         if (asn1DerEncodedPrincipals == null) {
/* 268 */           issuers = null;
/*     */         } else {
/* 270 */           issuers = new X500Principal[asn1DerEncodedPrincipals.length];
/* 271 */           for (int i = 0; i < asn1DerEncodedPrincipals.length; i++) {
/* 272 */             issuers[i] = new X500Principal(asn1DerEncodedPrincipals[i]);
/*     */           }
/*     */         } 
/* 275 */         this.keyManagerHolder.setKeyMaterialClientSide(engine, keyTypes, issuers);
/* 276 */       } catch (Throwable cause) {
/* 277 */         engine.initHandshakeException(cause);
/* 278 */         if (cause instanceof Exception) {
/* 279 */           throw (Exception)cause;
/*     */         }
/* 281 */         throw new SSLException(cause);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static String[] supportedClientKeyTypes(byte[] clientCertificateTypes) {
/* 294 */       if (clientCertificateTypes == null)
/*     */       {
/* 296 */         return (String[])ReferenceCountedOpenSslClientContext.SUPPORTED_KEY_TYPES.clone();
/*     */       }
/* 298 */       Set<String> result = new HashSet<>(clientCertificateTypes.length);
/* 299 */       for (byte keyTypeCode : clientCertificateTypes) {
/* 300 */         String keyType = clientKeyType(keyTypeCode);
/* 301 */         if (keyType != null)
/*     */         {
/*     */ 
/*     */           
/* 305 */           result.add(keyType); } 
/*     */       } 
/* 307 */       return result.<String>toArray(EmptyArrays.EMPTY_STRINGS);
/*     */     }
/*     */ 
/*     */     
/*     */     private static String clientKeyType(byte clientCertificateType) {
/* 312 */       switch (clientCertificateType) {
/*     */         case 1:
/* 314 */           return "RSA";
/*     */         case 3:
/* 316 */           return "DH_RSA";
/*     */         case 64:
/* 318 */           return "EC";
/*     */         case 65:
/* 320 */           return "EC_RSA";
/*     */         case 66:
/* 322 */           return "EC_EC";
/*     */       } 
/* 324 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\ReferenceCountedOpenSslClientContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */