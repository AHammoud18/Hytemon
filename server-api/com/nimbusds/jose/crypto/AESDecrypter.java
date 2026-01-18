/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.CriticalHeaderParamsAware;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.JWEDecrypter;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.KeyLengthException;
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.crypto.impl.AESCryptoProvider;
/*     */ import com.nimbusds.jose.crypto.impl.AESGCMKW;
/*     */ import com.nimbusds.jose.crypto.impl.AESKW;
/*     */ import com.nimbusds.jose.crypto.impl.AlgorithmSupportMessage;
/*     */ import com.nimbusds.jose.crypto.impl.AuthenticatedCipherText;
/*     */ import com.nimbusds.jose.crypto.impl.ContentCryptoProvider;
/*     */ import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
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
/*     */ @ThreadSafe
/*     */ public class AESDecrypter
/*     */   extends AESCryptoProvider
/*     */   implements JWEDecrypter, CriticalHeaderParamsAware
/*     */ {
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
/*     */   public AESDecrypter(SecretKey kek) throws KeyLengthException {
/*  99 */     this(kek, (Set<String>)null);
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
/*     */   public AESDecrypter(byte[] keyBytes) throws KeyLengthException {
/* 115 */     this(new SecretKeySpec(keyBytes, "AES"));
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
/*     */   public AESDecrypter(OctetSequenceKey octJWK) throws KeyLengthException {
/* 132 */     this(octJWK.toSecretKey("AES"));
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
/*     */   public AESDecrypter(SecretKey kek, Set<String> defCritHeaders) throws KeyLengthException {
/* 151 */     super(kek, null);
/*     */     
/* 153 */     this.critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getProcessedCriticalHeaderParams() {
/* 160 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getDeferredCriticalHeaderParams() {
/* 167 */     return this.critPolicy.getProcessedCriticalHeaderParams();
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
/* 202 */     return decrypt(header, encryptedKey, iv, cipherText, authTag, AAD.compute(header));
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
/*     */   public byte[] decrypt(JWEHeader header, Base64URL encryptedKey, Base64URL iv, Base64URL cipherText, Base64URL authTag, byte[] aad) throws JOSEException {
/*     */     SecretKey cek;
/* 216 */     if (encryptedKey == null) {
/* 217 */       throw new JOSEException("Missing JWE encrypted key");
/*     */     }
/*     */     
/* 220 */     if (iv == null) {
/* 221 */       throw new JOSEException("Missing JWE initialization vector (IV)");
/*     */     }
/*     */     
/* 224 */     if (authTag == null) {
/* 225 */       throw new JOSEException("Missing JWE authentication tag");
/*     */     }
/*     */ 
/*     */     
/* 229 */     JWEAlgorithm alg = JWEHeaderValidation.getAlgorithmAndEnsureNotNull(header);
/*     */     
/* 231 */     this.critPolicy.ensureHeaderPasses(header);
/*     */     
/* 233 */     int keyLength = header.getEncryptionMethod().cekBitLength();
/*     */ 
/*     */ 
/*     */     
/* 237 */     if (alg.equals(JWEAlgorithm.A128KW) || alg
/* 238 */       .equals(JWEAlgorithm.A192KW) || alg
/* 239 */       .equals(JWEAlgorithm.A256KW)) {
/*     */       
/* 241 */       cek = AESKW.unwrapCEK(getKey(), encryptedKey.decode(), getJCAContext().getKeyEncryptionProvider());
/*     */     }
/* 243 */     else if (alg.equals(JWEAlgorithm.A128GCMKW) || alg
/* 244 */       .equals(JWEAlgorithm.A192GCMKW) || alg
/* 245 */       .equals(JWEAlgorithm.A256GCMKW)) {
/*     */       
/* 247 */       if (header.getIV() == null) {
/* 248 */         throw new JOSEException("Missing JWE \"iv\" header parameter");
/*     */       }
/*     */       
/* 251 */       byte[] keyIV = header.getIV().decode();
/*     */       
/* 253 */       if (header.getAuthTag() == null) {
/* 254 */         throw new JOSEException("Missing JWE \"tag\" header parameter");
/*     */       }
/*     */       
/* 257 */       byte[] keyTag = header.getAuthTag().decode();
/*     */       
/* 259 */       AuthenticatedCipherText authEncrCEK = new AuthenticatedCipherText(encryptedKey.decode(), keyTag);
/* 260 */       cek = AESGCMKW.decryptCEK(getKey(), keyIV, authEncrCEK, keyLength, getJCAContext().getKeyEncryptionProvider());
/*     */     }
/*     */     else {
/*     */       
/* 264 */       throw new JOSEException(AlgorithmSupportMessage.unsupportedJWEAlgorithm(alg, SUPPORTED_ALGORITHMS));
/*     */     } 
/*     */     
/* 267 */     return ContentCryptoProvider.decrypt(header, aad, encryptedKey, iv, cipherText, authTag, cek, getJCAContext());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\AESDecrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */