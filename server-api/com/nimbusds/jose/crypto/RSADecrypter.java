/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.CriticalHeaderParamsAware;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.JWEDecrypter;
/*     */ import com.nimbusds.jose.JWEDecrypterOption;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.crypto.impl.AlgorithmSupportMessage;
/*     */ import com.nimbusds.jose.crypto.impl.ContentCryptoProvider;
/*     */ import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
/*     */ import com.nimbusds.jose.crypto.impl.JWEHeaderValidation;
/*     */ import com.nimbusds.jose.crypto.impl.RSA1_5;
/*     */ import com.nimbusds.jose.crypto.impl.RSACryptoProvider;
/*     */ import com.nimbusds.jose.crypto.impl.RSAKeyUtils;
/*     */ import com.nimbusds.jose.crypto.impl.RSA_OAEP;
/*     */ import com.nimbusds.jose.crypto.impl.RSA_OAEP_SHA2;
/*     */ import com.nimbusds.jose.crypto.opts.AllowWeakRSAKey;
/*     */ import com.nimbusds.jose.crypto.opts.CipherMode;
/*     */ import com.nimbusds.jose.crypto.opts.OptionUtils;
/*     */ import com.nimbusds.jose.jwk.RSAKey;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.security.PrivateKey;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import javax.crypto.SecretKey;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public class RSADecrypter
/*     */   extends RSACryptoProvider
/*     */   implements JWEDecrypter, CriticalHeaderParamsAware
/*     */ {
/*  86 */   private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final PrivateKey privateKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Set<JWEDecrypterOption> opts;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Exception cekDecryptionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RSADecrypter(PrivateKey privateKey) {
/* 120 */     this(privateKey, (Set<String>)null, Collections.emptySet());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RSADecrypter(RSAKey rsaJWK) throws JOSEException {
/* 138 */     this(RSAKeyUtils.toRSAPrivateKey(rsaJWK));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RSADecrypter(PrivateKey privateKey, Set<String> defCritHeaders) {
/* 159 */     this(privateKey, defCritHeaders, Collections.emptySet());
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
/*     */   @Deprecated
/*     */   public RSADecrypter(PrivateKey privateKey, Set<String> defCritHeaders, boolean allowWeakKey) {
/* 184 */     this(privateKey, defCritHeaders, 
/*     */ 
/*     */         
/* 187 */         allowWeakKey ? (Set)Collections.singleton(AllowWeakRSAKey.getInstance()) : Collections.<JWEDecrypterOption>emptySet());
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
/*     */   public RSADecrypter(PrivateKey privateKey, Set<String> defCritHeaders, Set<JWEDecrypterOption> opts) {
/* 212 */     super(null);
/*     */     
/* 214 */     if (!privateKey.getAlgorithm().equalsIgnoreCase("RSA")) {
/* 215 */       throw new IllegalArgumentException("The private key algorithm must be RSA");
/*     */     }
/*     */     
/* 218 */     this.opts = (opts != null) ? opts : Collections.<JWEDecrypterOption>emptySet();
/*     */     
/* 220 */     OptionUtils.ensureMinRSAPrivateKeySize(privateKey, this.opts);
/*     */     
/* 222 */     this.privateKey = privateKey;
/*     */     
/* 224 */     this.critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
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
/*     */   
/*     */   public PrivateKey getPrivateKey() {
/* 238 */     return this.privateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getProcessedCriticalHeaderParams() {
/* 245 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getDeferredCriticalHeaderParams() {
/* 252 */     return this.critPolicy.getProcessedCriticalHeaderParams();
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
/*     */   @Deprecated
/*     */   public byte[] decrypt(JWEHeader header, Base64URL encryptedKey, Base64URL iv, Base64URL cipherText, Base64URL authTag) throws JOSEException {
/* 287 */     return decrypt(header, encryptedKey, iv, cipherText, authTag, AAD.compute(header));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private CipherMode resolveCipherModeForOAEP() {
/* 293 */     if (this.opts.contains(CipherMode.ENCRYPT_DECRYPT)) {
/* 294 */       return CipherMode.ENCRYPT_DECRYPT;
/*     */     }
/*     */     
/* 297 */     return CipherMode.WRAP_UNWRAP;
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
/*     */ 
/*     */   
/*     */   public byte[] decrypt(JWEHeader header, Base64URL encryptedKey, Base64URL iv, Base64URL cipherText, Base64URL authTag, byte[] aad) throws JOSEException {
/*     */     SecretKey cek;
/* 313 */     if (encryptedKey == null) {
/* 314 */       throw new JOSEException("Missing JWE encrypted key");
/*     */     }
/*     */     
/* 317 */     if (iv == null) {
/* 318 */       throw new JOSEException("Missing JWE initialization vector (IV)");
/*     */     }
/*     */     
/* 321 */     if (authTag == null) {
/* 322 */       throw new JOSEException("Missing JWE authentication tag");
/*     */     }
/*     */     
/* 325 */     this.critPolicy.ensureHeaderPasses(header);
/*     */ 
/*     */ 
/*     */     
/* 329 */     JWEAlgorithm alg = JWEHeaderValidation.getAlgorithmAndEnsureNotNull(header);
/*     */ 
/*     */ 
/*     */     
/* 333 */     if (alg.equals(JWEAlgorithm.RSA1_5)) {
/*     */       
/* 335 */       int keyLength = header.getEncryptionMethod().cekBitLength();
/*     */ 
/*     */ 
/*     */       
/* 339 */       SecretKey randomCEK = ContentCryptoProvider.generateCEK(header.getEncryptionMethod(), getJCAContext().getSecureRandom());
/*     */       
/*     */       try {
/* 342 */         cek = RSA1_5.decryptCEK(this.privateKey, encryptedKey.decode(), keyLength, getJCAContext().getKeyEncryptionProvider());
/*     */         
/* 344 */         if (cek == null)
/*     */         {
/*     */           
/* 347 */           cek = randomCEK;
/*     */         }
/*     */       }
/* 350 */       catch (Exception e) {
/*     */         
/* 352 */         this.cekDecryptionException = e;
/* 353 */         cek = randomCEK;
/*     */       } 
/*     */       
/* 356 */       this.cekDecryptionException = null;
/*     */     }
/* 358 */     else if (alg.equals(JWEAlgorithm.RSA_OAEP)) {
/* 359 */       cek = RSA_OAEP.decryptCEK(this.privateKey, encryptedKey.decode(), resolveCipherModeForOAEP(), getJCAContext().getKeyEncryptionProvider());
/* 360 */     } else if (alg.equals(JWEAlgorithm.RSA_OAEP_256)) {
/* 361 */       cek = RSA_OAEP_SHA2.decryptCEK(this.privateKey, encryptedKey.decode(), 256, resolveCipherModeForOAEP(), getJCAContext().getKeyEncryptionProvider());
/* 362 */     } else if (alg.equals(JWEAlgorithm.RSA_OAEP_384)) {
/* 363 */       cek = RSA_OAEP_SHA2.decryptCEK(this.privateKey, encryptedKey.decode(), 384, resolveCipherModeForOAEP(), getJCAContext().getKeyEncryptionProvider());
/* 364 */     } else if (alg.equals(JWEAlgorithm.RSA_OAEP_512)) {
/* 365 */       cek = RSA_OAEP_SHA2.decryptCEK(this.privateKey, encryptedKey.decode(), 512, resolveCipherModeForOAEP(), getJCAContext().getKeyEncryptionProvider());
/*     */     } else {
/* 367 */       throw new JOSEException(AlgorithmSupportMessage.unsupportedJWEAlgorithm(alg, SUPPORTED_ALGORITHMS));
/*     */     } 
/*     */     
/* 370 */     return ContentCryptoProvider.decrypt(header, aad, encryptedKey, iv, cipherText, authTag, cek, getJCAContext());
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
/*     */   public Exception getCEKDecryptionException() {
/* 383 */     return this.cekDecryptionException;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\RSADecrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */