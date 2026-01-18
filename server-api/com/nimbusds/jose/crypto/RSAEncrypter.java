/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.EncryptionMethod;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.JWECryptoParts;
/*     */ import com.nimbusds.jose.JWEEncrypter;
/*     */ import com.nimbusds.jose.JWEEncrypterOption;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.crypto.impl.AlgorithmSupportMessage;
/*     */ import com.nimbusds.jose.crypto.impl.ContentCryptoProvider;
/*     */ import com.nimbusds.jose.crypto.impl.JWEHeaderValidation;
/*     */ import com.nimbusds.jose.crypto.impl.RSA1_5;
/*     */ import com.nimbusds.jose.crypto.impl.RSACryptoProvider;
/*     */ import com.nimbusds.jose.crypto.impl.RSA_OAEP;
/*     */ import com.nimbusds.jose.crypto.impl.RSA_OAEP_SHA2;
/*     */ import com.nimbusds.jose.crypto.opts.CipherMode;
/*     */ import com.nimbusds.jose.jwk.RSAKey;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.util.Collections;
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class RSAEncrypter
/*     */   extends RSACryptoProvider
/*     */   implements JWEEncrypter
/*     */ {
/*     */   private final RSAPublicKey publicKey;
/*     */   private final Set<JWEEncrypterOption> opts;
/*     */   
/*     */   public RSAEncrypter(RSAPublicKey publicKey) {
/* 102 */     this(publicKey, (SecretKey)null);
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
/*     */   public RSAEncrypter(RSAKey rsaJWK) throws JOSEException {
/* 116 */     this(rsaJWK.toRSAPublicKey());
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
/*     */   public RSAEncrypter(RSAPublicKey publicKey, SecretKey contentEncryptionKey) {
/* 135 */     this(publicKey, contentEncryptionKey, Collections.emptySet());
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
/*     */   public RSAEncrypter(RSAPublicKey publicKey, SecretKey contentEncryptionKey, Set<JWEEncrypterOption> opts) {
/* 158 */     super(contentEncryptionKey);
/* 159 */     this.publicKey = Objects.<RSAPublicKey>requireNonNull(publicKey);
/* 160 */     this.opts = (opts != null) ? opts : Collections.<JWEEncrypterOption>emptySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RSAPublicKey getPublicKey() {
/* 171 */     return this.publicKey;
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
/*     */   @Deprecated
/*     */   public JWECryptoParts encrypt(JWEHeader header, byte[] clearText) throws JOSEException {
/* 193 */     return encrypt(header, clearText, AAD.compute(header));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private CipherMode resolveCipherModeForOAEP() {
/* 199 */     if (this.opts.contains(CipherMode.ENCRYPT_DECRYPT)) {
/* 200 */       return CipherMode.ENCRYPT_DECRYPT;
/*     */     }
/*     */     
/* 203 */     return CipherMode.WRAP_UNWRAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWECryptoParts encrypt(JWEHeader header, byte[] clearText, byte[] aad) throws JOSEException {
/*     */     Base64URL encryptedKey;
/* 213 */     JWEAlgorithm alg = JWEHeaderValidation.getAlgorithmAndEnsureNotNull(header);
/* 214 */     EncryptionMethod enc = header.getEncryptionMethod();
/* 215 */     SecretKey cek = getCEK(enc);
/*     */ 
/*     */ 
/*     */     
/* 219 */     if (alg.equals(JWEAlgorithm.RSA1_5)) {
/* 220 */       encryptedKey = Base64URL.encode(RSA1_5.encryptCEK(this.publicKey, cek, getJCAContext().getKeyEncryptionProvider()));
/* 221 */     } else if (alg.equals(JWEAlgorithm.RSA_OAEP)) {
/* 222 */       encryptedKey = Base64URL.encode(RSA_OAEP.encryptCEK(this.publicKey, cek, resolveCipherModeForOAEP(), getJCAContext().getKeyEncryptionProvider()));
/* 223 */     } else if (alg.equals(JWEAlgorithm.RSA_OAEP_256)) {
/* 224 */       encryptedKey = Base64URL.encode(RSA_OAEP_SHA2.encryptCEK(this.publicKey, cek, 256, resolveCipherModeForOAEP(), getJCAContext().getKeyEncryptionProvider()));
/* 225 */     } else if (alg.equals(JWEAlgorithm.RSA_OAEP_384)) {
/* 226 */       encryptedKey = Base64URL.encode(RSA_OAEP_SHA2.encryptCEK(this.publicKey, cek, 384, resolveCipherModeForOAEP(), getJCAContext().getKeyEncryptionProvider()));
/* 227 */     } else if (alg.equals(JWEAlgorithm.RSA_OAEP_512)) {
/* 228 */       encryptedKey = Base64URL.encode(RSA_OAEP_SHA2.encryptCEK(this.publicKey, cek, 512, resolveCipherModeForOAEP(), getJCAContext().getKeyEncryptionProvider()));
/*     */     } else {
/* 230 */       throw new JOSEException(AlgorithmSupportMessage.unsupportedJWEAlgorithm(alg, SUPPORTED_ALGORITHMS));
/*     */     } 
/*     */     
/* 233 */     return ContentCryptoProvider.encrypt(header, clearText, aad, cek, encryptedKey, getJCAContext());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\RSAEncrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */