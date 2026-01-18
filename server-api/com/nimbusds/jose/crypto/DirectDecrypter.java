/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.CriticalHeaderParamsAware;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.JWEDecrypter;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.KeyLengthException;
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.crypto.impl.AlgorithmSupportMessage;
/*     */ import com.nimbusds.jose.crypto.impl.ContentCryptoProvider;
/*     */ import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
/*     */ import com.nimbusds.jose.crypto.impl.DirectCryptoProvider;
/*     */ import com.nimbusds.jose.crypto.impl.JWEHeaderValidation;
/*     */ import com.nimbusds.jose.jwk.OctetSequenceKey;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.util.Set;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public class DirectDecrypter
/*     */   extends DirectCryptoProvider
/*     */   implements JWEDecrypter, CriticalHeaderParamsAware
/*     */ {
/*     */   private final boolean promiscuousMode;
/*  84 */   private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();
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
/*     */   public DirectDecrypter(SecretKey key) throws KeyLengthException {
/* 101 */     this(key, false);
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
/*     */   public DirectDecrypter(SecretKey key, boolean promiscuousMode) throws KeyLengthException {
/* 127 */     super(key);
/*     */     
/* 129 */     this.promiscuousMode = promiscuousMode;
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
/*     */   public DirectDecrypter(byte[] keyBytes) throws KeyLengthException {
/* 147 */     this(new SecretKeySpec(keyBytes, "AES"), false);
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
/*     */   public DirectDecrypter(OctetSequenceKey octJWK) throws KeyLengthException {
/* 165 */     this(octJWK.toSecretKey("AES"));
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
/*     */   public DirectDecrypter(SecretKey key, Set<String> defCritHeaders) throws KeyLengthException {
/* 188 */     this(key, defCritHeaders, false);
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
/*     */   public DirectDecrypter(SecretKey key, Set<String> defCritHeaders, boolean promiscuousMode) throws KeyLengthException {
/* 218 */     super(key);
/* 219 */     this.critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
/* 220 */     this.promiscuousMode = promiscuousMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getProcessedCriticalHeaderParams() {
/* 227 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getDeferredCriticalHeaderParams() {
/* 234 */     return this.critPolicy.getProcessedCriticalHeaderParams();
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
/* 269 */     return decrypt(header, encryptedKey, iv, cipherText, authTag, AAD.compute(header));
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
/*     */   public byte[] decrypt(JWEHeader header, Base64URL encryptedKey, Base64URL iv, Base64URL cipherText, Base64URL authTag, byte[] aad) throws JOSEException {
/* 283 */     if (!this.promiscuousMode) {
/*     */       
/* 285 */       JWEAlgorithm alg = JWEHeaderValidation.getAlgorithmAndEnsureNotNull(header);
/*     */       
/* 287 */       if (!alg.equals(JWEAlgorithm.DIR)) {
/* 288 */         throw new JOSEException(AlgorithmSupportMessage.unsupportedJWEAlgorithm(alg, SUPPORTED_ALGORITHMS));
/*     */       }
/*     */       
/* 291 */       if (encryptedKey != null) {
/* 292 */         throw new JOSEException("Unexpected present JWE encrypted key");
/*     */       }
/*     */     } 
/*     */     
/* 296 */     if (iv == null) {
/* 297 */       throw new JOSEException("Unexpected present JWE initialization vector (IV)");
/*     */     }
/*     */     
/* 300 */     if (authTag == null) {
/* 301 */       throw new JOSEException("Missing JWE authentication tag");
/*     */     }
/*     */     
/* 304 */     this.critPolicy.ensureHeaderPasses(header);
/*     */     
/* 306 */     return ContentCryptoProvider.decrypt(header, aad, null, iv, cipherText, authTag, getKey(), getJCAContext());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\DirectDecrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */