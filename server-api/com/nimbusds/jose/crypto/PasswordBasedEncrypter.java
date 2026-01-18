/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.EncryptionMethod;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.JWECryptoParts;
/*     */ import com.nimbusds.jose.JWEEncrypter;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.crypto.impl.AESKW;
/*     */ import com.nimbusds.jose.crypto.impl.ContentCryptoProvider;
/*     */ import com.nimbusds.jose.crypto.impl.JWEHeaderValidation;
/*     */ import com.nimbusds.jose.crypto.impl.PBKDF2;
/*     */ import com.nimbusds.jose.crypto.impl.PRFParams;
/*     */ import com.nimbusds.jose.crypto.impl.PasswordBasedCryptoProvider;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.StandardCharset;
/*     */ import java.util.Arrays;
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
/*     */ 
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
/*     */ public class PasswordBasedEncrypter
/*     */   extends PasswordBasedCryptoProvider
/*     */   implements JWEEncrypter
/*     */ {
/*     */   public static final int MIN_SALT_LENGTH = 8;
/*     */   private final int saltLength;
/*     */   public static final int MIN_RECOMMENDED_ITERATION_COUNT = 1000;
/*     */   private final int iterationCount;
/*     */   
/*     */   public PasswordBasedEncrypter(byte[] password, int saltLength, int iterationCount) {
/* 108 */     super(password);
/*     */     
/* 110 */     if (saltLength < 8) {
/* 111 */       throw new IllegalArgumentException("The minimum salt length (p2s) is 8 bytes");
/*     */     }
/*     */     
/* 114 */     this.saltLength = saltLength;
/*     */     
/* 116 */     if (iterationCount < 1000) {
/* 117 */       throw new IllegalArgumentException("The minimum recommended iteration count (p2c) is 1000");
/*     */     }
/*     */     
/* 120 */     this.iterationCount = iterationCount;
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
/*     */   public PasswordBasedEncrypter(String password, int saltLength, int iterationCount) {
/* 138 */     this(password.getBytes(StandardCharset.UTF_8), saltLength, iterationCount);
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
/* 160 */     return encrypt(header, clearText, AAD.compute(header));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWECryptoParts encrypt(JWEHeader header, byte[] clearText, byte[] aad) throws JOSEException {
/* 168 */     JWEAlgorithm alg = JWEHeaderValidation.getAlgorithmAndEnsureNotNull(header);
/* 169 */     EncryptionMethod enc = header.getEncryptionMethod();
/*     */     
/* 171 */     byte[] salt = new byte[this.saltLength];
/* 172 */     getJCAContext().getSecureRandom().nextBytes(salt);
/* 173 */     byte[] formattedSalt = PBKDF2.formatSalt(alg, salt);
/* 174 */     PRFParams prfParams = PRFParams.resolve(alg, getJCAContext().getMACProvider());
/* 175 */     SecretKey psKey = PBKDF2.deriveKey(getPassword(), formattedSalt, this.iterationCount, prfParams, getJCAContext().getProvider());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     JWEHeader updatedHeader = (new JWEHeader.Builder(header)).pbes2Salt(Base64URL.encode(salt)).pbes2Count(this.iterationCount).build();
/*     */     
/* 183 */     SecretKey cek = ContentCryptoProvider.generateCEK(enc, getJCAContext().getSecureRandom());
/*     */ 
/*     */     
/* 186 */     Base64URL encryptedKey = Base64URL.encode(AESKW.wrapCEK(cek, psKey, getJCAContext().getKeyEncryptionProvider()));
/*     */ 
/*     */     
/* 189 */     byte[] updatedAAD = Arrays.equals(AAD.compute(header), aad) ? AAD.compute(updatedHeader) : aad;
/*     */     
/* 191 */     return ContentCryptoProvider.encrypt(updatedHeader, clearText, updatedAAD, cek, encryptedKey, getJCAContext());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSaltLength() {
/* 202 */     return this.saltLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIterationCount() {
/* 213 */     return this.iterationCount;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\PasswordBasedEncrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */