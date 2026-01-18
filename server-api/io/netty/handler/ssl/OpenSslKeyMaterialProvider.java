/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.UnpooledByteBufAllocator;
/*     */ import io.netty.internal.tcnative.SSL;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.X509KeyManager;
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
/*     */ class OpenSslKeyMaterialProvider
/*     */ {
/*     */   private final X509KeyManager keyManager;
/*     */   private final String password;
/*     */   
/*     */   OpenSslKeyMaterialProvider(X509KeyManager keyManager, String password) {
/*  38 */     this.keyManager = keyManager;
/*  39 */     this.password = password;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void validateKeyMaterialSupported(X509Certificate[] keyCertChain, PrivateKey key, String keyPassword, boolean allowSignatureFallback) throws SSLException {
/*  45 */     validateSupported(keyCertChain);
/*  46 */     validateSupported(key, keyPassword, allowSignatureFallback);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validateSupported(PrivateKey key, String password, boolean allowSignatureFallback) throws SSLException {
/*  51 */     if (key == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  57 */     if (key.getEncoded() == null && allowSignatureFallback) {
/*     */       return;
/*     */     }
/*     */     
/*  61 */     long pkeyBio = 0L;
/*  62 */     long pkey = 0L;
/*     */     
/*     */     try {
/*  65 */       pkeyBio = ReferenceCountedOpenSslContext.toBIO((ByteBufAllocator)UnpooledByteBufAllocator.DEFAULT, key);
/*  66 */       pkey = SSL.parsePrivateKey(pkeyBio, password);
/*  67 */     } catch (Exception e) {
/*  68 */       throw new SSLException("PrivateKey type not supported " + key.getFormat(), e);
/*     */     } finally {
/*  70 */       SSL.freeBIO(pkeyBio);
/*  71 */       if (pkey != 0L) {
/*  72 */         SSL.freePrivateKey(pkey);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void validateSupported(X509Certificate[] certificates) throws SSLException {
/*  78 */     if (certificates == null || certificates.length == 0) {
/*     */       return;
/*     */     }
/*     */     
/*  82 */     long chainBio = 0L;
/*  83 */     long chain = 0L;
/*  84 */     PemEncoded encoded = null;
/*     */     try {
/*  86 */       encoded = PemX509Certificate.toPEM((ByteBufAllocator)UnpooledByteBufAllocator.DEFAULT, true, certificates);
/*  87 */       chainBio = ReferenceCountedOpenSslContext.toBIO((ByteBufAllocator)UnpooledByteBufAllocator.DEFAULT, encoded.retain());
/*  88 */       chain = SSL.parseX509Chain(chainBio);
/*  89 */     } catch (Exception e) {
/*  90 */       throw new SSLException("Certificate type not supported", e);
/*     */     } finally {
/*  92 */       SSL.freeBIO(chainBio);
/*  93 */       if (chain != 0L) {
/*  94 */         SSL.freeX509Chain(chain);
/*     */       }
/*  96 */       if (encoded != null) {
/*  97 */         encoded.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   X509KeyManager keyManager() {
/* 106 */     return this.keyManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   OpenSslKeyMaterial chooseKeyMaterial(ByteBufAllocator allocator, String alias) throws Exception {
/* 114 */     X509Certificate[] certificates = this.keyManager.getCertificateChain(alias);
/* 115 */     if (certificates == null || certificates.length == 0) {
/* 116 */       return null;
/*     */     }
/*     */     
/* 119 */     PrivateKey key = this.keyManager.getPrivateKey(alias);
/* 120 */     PemEncoded encoded = PemX509Certificate.toPEM(allocator, true, certificates);
/* 121 */     long chainBio = 0L;
/* 122 */     long pkeyBio = 0L;
/* 123 */     long chain = 0L;
/* 124 */     long pkey = 0L; try {
/*     */       OpenSslKeyMaterial keyMaterial;
/* 126 */       chainBio = ReferenceCountedOpenSslContext.toBIO(allocator, encoded.retain());
/* 127 */       chain = SSL.parseX509Chain(chainBio);
/*     */ 
/*     */       
/* 130 */       if (key instanceof OpenSslPrivateKey) {
/* 131 */         keyMaterial = ((OpenSslPrivateKey)key).newKeyMaterial(chain, certificates);
/*     */       } else {
/* 133 */         pkeyBio = ReferenceCountedOpenSslContext.toBIO(allocator, key);
/* 134 */         pkey = (key == null) ? 0L : SSL.parsePrivateKey(pkeyBio, this.password);
/* 135 */         keyMaterial = new DefaultOpenSslKeyMaterial(chain, pkey, certificates);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 140 */       chain = 0L;
/* 141 */       pkey = 0L;
/* 142 */       return keyMaterial;
/*     */     } finally {
/* 144 */       SSL.freeBIO(chainBio);
/* 145 */       SSL.freeBIO(pkeyBio);
/* 146 */       if (chain != 0L) {
/* 147 */         SSL.freeX509Chain(chain);
/*     */       }
/* 149 */       if (pkey != 0L) {
/* 150 */         SSL.freePrivateKey(pkey);
/*     */       }
/* 152 */       encoded.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   void destroy() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslKeyMaterialProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */