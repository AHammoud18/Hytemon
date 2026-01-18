/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.CriticalHeaderParamsAware;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.JWEDecrypter;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.crypto.impl.AESKW;
/*     */ import com.nimbusds.jose.crypto.impl.ContentCryptoProvider;
/*     */ import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
/*     */ import com.nimbusds.jose.crypto.impl.JWEHeaderValidation;
/*     */ import com.nimbusds.jose.crypto.impl.PBKDF2;
/*     */ import com.nimbusds.jose.crypto.impl.PRFParams;
/*     */ import com.nimbusds.jose.crypto.impl.PasswordBasedCryptoProvider;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.StandardCharset;
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
/*     */ @ThreadSafe
/*     */ public class PasswordBasedDecrypter
/*     */   extends PasswordBasedCryptoProvider
/*     */   implements JWEDecrypter, CriticalHeaderParamsAware
/*     */ {
/*     */   public static final int MAX_ALLOWED_ITERATION_COUNT = 1000000;
/*  80 */   private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PasswordBasedDecrypter(byte[] password) {
/*  91 */     super(password);
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
/*     */   public PasswordBasedDecrypter(String password) {
/* 103 */     super(password.getBytes(StandardCharset.UTF_8));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getProcessedCriticalHeaderParams() {
/* 110 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getDeferredCriticalHeaderParams() {
/* 117 */     return this.critPolicy.getProcessedCriticalHeaderParams();
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
/* 152 */     return decrypt(header, encryptedKey, iv, cipherText, authTag, AAD.compute(header));
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
/* 166 */     if (encryptedKey == null) {
/* 167 */       throw new JOSEException("Missing JWE encrypted key");
/*     */     }
/*     */     
/* 170 */     if (iv == null) {
/* 171 */       throw new JOSEException("Missing JWE initialization vector (IV)");
/*     */     }
/*     */     
/* 174 */     if (authTag == null) {
/* 175 */       throw new JOSEException("Missing JWE authentication tag");
/*     */     }
/*     */     
/* 178 */     if (header.getPBES2Salt() == null) {
/* 179 */       throw new JOSEException("Missing JWE p2s header parameter");
/*     */     }
/*     */     
/* 182 */     byte[] salt = header.getPBES2Salt().decode();
/*     */     
/* 184 */     if (header.getPBES2Count() < 1) {
/* 185 */       throw new JOSEException("Missing JWE p2c header parameter");
/*     */     }
/*     */     
/* 188 */     int iterationCount = header.getPBES2Count();
/*     */     
/* 190 */     if (iterationCount > 1000000) {
/* 191 */       throw new JOSEException("The JWE p2c header exceeds the maximum allowed 1000000 count");
/*     */     }
/*     */     
/* 194 */     this.critPolicy.ensureHeaderPasses(header);
/*     */     
/* 196 */     JWEAlgorithm alg = JWEHeaderValidation.getAlgorithmAndEnsureNotNull(header);
/* 197 */     byte[] formattedSalt = PBKDF2.formatSalt(alg, salt);
/* 198 */     PRFParams prfParams = PRFParams.resolve(alg, getJCAContext().getMACProvider());
/* 199 */     SecretKey psKey = PBKDF2.deriveKey(getPassword(), formattedSalt, iterationCount, prfParams, getJCAContext().getProvider());
/*     */     
/* 201 */     SecretKey cek = AESKW.unwrapCEK(psKey, encryptedKey.decode(), getJCAContext().getKeyEncryptionProvider());
/*     */     
/* 203 */     return ContentCryptoProvider.decrypt(header, aad, encryptedKey, iv, cipherText, authTag, cek, getJCAContext());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\PasswordBasedDecrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */