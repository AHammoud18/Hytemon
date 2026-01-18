/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.internal.tcnative.SSLPrivateKeyMethod;
/*     */ import io.netty.util.collection.IntCollections;
/*     */ import io.netty.util.collection.IntObjectHashMap;
/*     */ import io.netty.util.collection.IntObjectMap;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.security.Signature;
/*     */ import java.security.spec.MGF1ParameterSpec;
/*     */ import java.security.spec.PSSParameterSpec;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
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
/*     */ final class JdkDelegatingPrivateKeyMethod
/*     */   implements SSLPrivateKeyMethod
/*     */ {
/*  46 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(JdkDelegatingPrivateKeyMethod.class);
/*     */   
/*     */   private static final IntObjectMap<String> SSL_TO_JDK_SIGNATURE_ALGORITHM;
/*  49 */   private static final ConcurrentMap<CacheKey, String> PROVIDER_CACHE = new ConcurrentHashMap<>();
/*     */   
/*     */   static {
/*  52 */     IntObjectHashMap intObjectHashMap = new IntObjectHashMap();
/*     */ 
/*     */     
/*  55 */     intObjectHashMap.put(OpenSslAsyncPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_SHA1, "SHA1withRSA");
/*  56 */     intObjectHashMap.put(OpenSslAsyncPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_SHA256, "SHA256withRSA");
/*  57 */     intObjectHashMap.put(OpenSslAsyncPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_SHA384, "SHA384withRSA");
/*  58 */     intObjectHashMap.put(OpenSslAsyncPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_SHA512, "SHA512withRSA");
/*  59 */     intObjectHashMap.put(OpenSslAsyncPrivateKeyMethod.SSL_SIGN_RSA_PKCS1_MD5_SHA1, "MD5andSHA1withRSA");
/*     */ 
/*     */     
/*  62 */     intObjectHashMap.put(OpenSslAsyncPrivateKeyMethod.SSL_SIGN_ECDSA_SHA1, "SHA1withECDSA");
/*  63 */     intObjectHashMap.put(OpenSslAsyncPrivateKeyMethod.SSL_SIGN_ECDSA_SECP256R1_SHA256, "SHA256withECDSA");
/*  64 */     intObjectHashMap.put(OpenSslAsyncPrivateKeyMethod.SSL_SIGN_ECDSA_SECP384R1_SHA384, "SHA384withECDSA");
/*  65 */     intObjectHashMap.put(OpenSslAsyncPrivateKeyMethod.SSL_SIGN_ECDSA_SECP521R1_SHA512, "SHA512withECDSA");
/*     */ 
/*     */     
/*  68 */     intObjectHashMap.put(OpenSslAsyncPrivateKeyMethod.SSL_SIGN_RSA_PSS_RSAE_SHA256, "RSASSA-PSS");
/*  69 */     intObjectHashMap.put(OpenSslAsyncPrivateKeyMethod.SSL_SIGN_RSA_PSS_RSAE_SHA384, "RSASSA-PSS");
/*  70 */     intObjectHashMap.put(OpenSslAsyncPrivateKeyMethod.SSL_SIGN_RSA_PSS_RSAE_SHA512, "RSASSA-PSS");
/*     */ 
/*     */     
/*  73 */     intObjectHashMap.put(OpenSslAsyncPrivateKeyMethod.SSL_SIGN_ED25519, "EdDSA");
/*     */     
/*  75 */     SSL_TO_JDK_SIGNATURE_ALGORITHM = IntCollections.unmodifiableMap((IntObjectMap)intObjectHashMap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final PrivateKey privateKey;
/*     */ 
/*     */   
/*     */   private final String privateKeyTypeName;
/*     */ 
/*     */   
/*     */   JdkDelegatingPrivateKeyMethod(PrivateKey privateKey) {
/*  87 */     this.privateKey = (PrivateKey)ObjectUtil.checkNotNull(privateKey, "privateKey");
/*  88 */     this.privateKeyTypeName = privateKey.getClass().getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] sign(long ssl, int signatureAlgorithm, byte[] input) throws Exception {
/*  93 */     Signature signature = createSignature(signatureAlgorithm);
/*  94 */     signature.update(input);
/*  95 */     byte[] result = signature.sign();
/*     */     
/*  97 */     if (logger.isDebugEnabled()) {
/*  98 */       logger.debug("Signing operation completed successfully, result length: {}", Integer.valueOf(result.length));
/*     */     }
/* 100 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(long ssl, byte[] input) {
/* 107 */     throw new UnsupportedOperationException("Direct decryption is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   private Signature createSignature(int opensslAlgorithm) throws NoSuchAlgorithmException {
/* 112 */     String jdkAlgorithm = (String)SSL_TO_JDK_SIGNATURE_ALGORITHM.get(opensslAlgorithm);
/* 113 */     if (jdkAlgorithm == null) {
/* 114 */       String errorMsg = "Unsupported signature algorithm: " + opensslAlgorithm;
/* 115 */       throw new NoSuchAlgorithmException(errorMsg);
/*     */     } 
/*     */     
/* 118 */     CacheKey cacheKey = new CacheKey(jdkAlgorithm, this.privateKeyTypeName);
/*     */ 
/*     */     
/* 121 */     String cachedProviderName = PROVIDER_CACHE.get(cacheKey);
/* 122 */     if (cachedProviderName != null) {
/*     */       try {
/* 124 */         Signature signature1 = Signature.getInstance(jdkAlgorithm, cachedProviderName);
/* 125 */         configureOpenSslAlgorithmParameters(signature1, opensslAlgorithm);
/* 126 */         signature1.initSign(this.privateKey);
/* 127 */         if (logger.isDebugEnabled()) {
/* 128 */           logger.debug("Using cached provider {} for OpenSSL algorithm {} ({}) with key type {}", new Object[] { cachedProviderName, 
/* 129 */                 Integer.valueOf(opensslAlgorithm), jdkAlgorithm, this.privateKeyTypeName });
/*     */         }
/* 131 */         return signature1;
/* 132 */       } catch (Exception e) {
/*     */         
/* 134 */         PROVIDER_CACHE.remove(cacheKey);
/* 135 */         if (logger.isDebugEnabled()) {
/* 136 */           logger.debug("Cached provider {} failed for key type {}, removing from cache: {}", new Object[] { cachedProviderName, this.privateKeyTypeName, e
/* 137 */                 .getMessage() });
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 143 */     Signature signature = findCompatibleSignature(opensslAlgorithm, jdkAlgorithm);
/* 144 */     PROVIDER_CACHE.put(cacheKey, signature.getProvider().getName());
/*     */     
/* 146 */     if (logger.isDebugEnabled()) {
/* 147 */       logger.debug("Discovered and cached provider {} for OpenSSL algorithm {} ({}) with key type {}", new Object[] { signature
/* 148 */             .getProvider().getName(), Integer.valueOf(opensslAlgorithm), jdkAlgorithm, this.privateKeyTypeName });
/*     */     }
/*     */     
/* 151 */     return signature;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Signature findCompatibleSignature(int opensslAlgorithm, String jdkAlgorithm) throws NoSuchAlgorithmException {
/*     */     try {
/* 159 */       Signature signature = Signature.getInstance(jdkAlgorithm);
/* 160 */       configureOpenSslAlgorithmParameters(signature, opensslAlgorithm);
/* 161 */       signature.initSign(this.privateKey);
/* 162 */       if (logger.isDebugEnabled()) {
/* 163 */         logger.debug("Default provider {} handles key type {} for OpenSSL algorithm {} ({})", new Object[] { signature
/* 164 */               .getProvider().getName(), this.privateKey.getClass().getName(), 
/* 165 */               Integer.valueOf(opensslAlgorithm), jdkAlgorithm });
/*     */       }
/* 167 */       return signature;
/* 168 */     } catch (InvalidKeyException e) {
/*     */       
/* 170 */       if (logger.isDebugEnabled()) {
/* 171 */         logger.debug("Default provider cannot handle key type {} for OpenSSL algorithm {} ({}): {}", new Object[] { this.privateKey
/* 172 */               .getClass().getName(), Integer.valueOf(opensslAlgorithm), jdkAlgorithm, e.getMessage() });
/*     */       }
/* 174 */     } catch (Exception e) {
/*     */       
/* 176 */       if (logger.isDebugEnabled()) {
/* 177 */         logger.debug("Default provider failed for OpenSSL algorithm {} ({}): {}", new Object[] {
/* 178 */               Integer.valueOf(opensslAlgorithm), jdkAlgorithm, e.getMessage()
/*     */             });
/*     */       }
/*     */     } 
/*     */     
/* 183 */     Provider[] providers = Security.getProviders();
/* 184 */     for (Provider provider : providers) {
/*     */       try {
/* 186 */         Signature signature = Signature.getInstance(jdkAlgorithm, provider);
/* 187 */         configureOpenSslAlgorithmParameters(signature, opensslAlgorithm);
/* 188 */         signature.initSign(this.privateKey);
/*     */         
/* 190 */         if (logger.isDebugEnabled()) {
/* 191 */           logger.debug("Found compatible provider {} for key type {} with OpenSSL algorithm {} ({})", new Object[] { provider
/* 192 */                 .getName(), this.privateKey.getClass().getName(), Integer.valueOf(opensslAlgorithm), jdkAlgorithm });
/*     */         }
/* 194 */         return signature;
/* 195 */       } catch (InvalidKeyException e) {
/*     */         
/* 197 */         if (logger.isTraceEnabled()) {
/* 198 */           logger.trace("Provider {} cannot handle key type {}: {}", new Object[] { provider
/* 199 */                 .getName(), this.privateKey.getClass().getName(), e.getMessage() });
/*     */         }
/* 201 */       } catch (Exception e) {
/*     */         
/* 203 */         if (logger.isTraceEnabled()) {
/* 204 */           logger.trace("Provider {} failed for OpenSSL algorithm {} ({}): {}", new Object[] { provider
/* 205 */                 .getName(), Integer.valueOf(opensslAlgorithm), jdkAlgorithm, e.getMessage() });
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     throw new NoSuchAlgorithmException("No provider found for OpenSSL algorithm " + opensslAlgorithm + " (" + jdkAlgorithm + ") with private key type: " + this.privateKey
/* 211 */         .getClass().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void configureOpenSslAlgorithmParameters(Signature signature, int opensslAlgorithm) throws InvalidAlgorithmParameterException {
/* 217 */     if (opensslAlgorithm == OpenSslAsyncPrivateKeyMethod.SSL_SIGN_RSA_PSS_RSAE_SHA256) {
/*     */       
/* 219 */       configurePssParameters(signature, MGF1ParameterSpec.SHA256, 32);
/* 220 */     } else if (opensslAlgorithm == OpenSslAsyncPrivateKeyMethod.SSL_SIGN_RSA_PSS_RSAE_SHA384) {
/*     */       
/* 222 */       configurePssParameters(signature, MGF1ParameterSpec.SHA384, 48);
/* 223 */     } else if (opensslAlgorithm == OpenSslAsyncPrivateKeyMethod.SSL_SIGN_RSA_PSS_RSAE_SHA512) {
/*     */       
/* 225 */       configurePssParameters(signature, MGF1ParameterSpec.SHA512, 64);
/* 226 */     } else if (SSL_TO_JDK_SIGNATURE_ALGORITHM.containsKey(opensslAlgorithm)) {
/*     */       
/* 228 */       if (logger.isTraceEnabled()) {
/* 229 */         logger.trace("No parameter configuration needed for OpenSSL algorithm {}", Integer.valueOf(opensslAlgorithm));
/*     */       }
/*     */     }
/* 232 */     else if (logger.isDebugEnabled()) {
/* 233 */       logger.debug("Unknown OpenSSL algorithm {}, using default configuration", Integer.valueOf(opensslAlgorithm));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void configurePssParameters(Signature signature, MGF1ParameterSpec mgfSpec, int saltLength) throws InvalidAlgorithmParameterException {
/* 241 */     PSSParameterSpec pssSpec = new PSSParameterSpec(mgfSpec.getDigestAlgorithm(), "MGF1", mgfSpec, saltLength, 1);
/* 242 */     signature.setParameter(pssSpec);
/*     */     
/* 244 */     if (logger.isDebugEnabled()) {
/* 245 */       logger.debug("Configured PSS parameters: hash={}, saltLength={}", mgfSpec.getDigestAlgorithm(), Integer.valueOf(saltLength));
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class CacheKey
/*     */   {
/*     */     private final String jdkAlgorithm;
/*     */     private final String keyTypeName;
/*     */     private final int hashCode;
/*     */     
/*     */     public boolean equals(Object o) {
/* 256 */       if (o == null || getClass() != o.getClass()) {
/* 257 */         return false;
/*     */       }
/* 259 */       CacheKey cacheKey = (CacheKey)o;
/* 260 */       return (Objects.equals(cacheKey.jdkAlgorithm, this.jdkAlgorithm) && 
/* 261 */         Objects.equals(cacheKey.keyTypeName, this.keyTypeName));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 266 */       return this.hashCode;
/*     */     }
/*     */     
/*     */     CacheKey(String jdkAlgorithm, String keyTypeName) {
/* 270 */       this.jdkAlgorithm = jdkAlgorithm;
/* 271 */       this.keyTypeName = keyTypeName;
/* 272 */       this.hashCode = 31 * jdkAlgorithm.hashCode() + keyTypeName.hashCode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\JdkDelegatingPrivateKeyMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */