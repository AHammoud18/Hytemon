/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.EncryptionMethod;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.JWECryptoParts;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ECDH1PUCryptoProvider
/*     */   extends BaseJWEProvider
/*     */ {
/*     */   public static final Set<JWEAlgorithm> SUPPORTED_ALGORITHMS;
/*  94 */   public static final Set<EncryptionMethod> SUPPORTED_ENCRYPTION_METHODS = ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS;
/*     */   private final Curve curve;
/*     */   
/*     */   static {
/*  98 */     Set<JWEAlgorithm> algs = new LinkedHashSet<>();
/*  99 */     algs.add(JWEAlgorithm.ECDH_1PU);
/* 100 */     algs.add(JWEAlgorithm.ECDH_1PU_A128KW);
/* 101 */     algs.add(JWEAlgorithm.ECDH_1PU_A192KW);
/* 102 */     algs.add(JWEAlgorithm.ECDH_1PU_A256KW);
/* 103 */     SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);
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
/*     */   private final ConcatKDF concatKDF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ECDH1PUCryptoProvider(Curve curve, SecretKey cek) throws JOSEException {
/* 136 */     super(SUPPORTED_ALGORITHMS, ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS, cek);
/*     */     
/* 138 */     Curve definedCurve = (curve != null) ? curve : new Curve("unknown");
/*     */     
/* 140 */     if (!supportedEllipticCurves().contains(curve)) {
/* 141 */       throw new JOSEException(AlgorithmSupportMessage.unsupportedEllipticCurve(definedCurve, 
/* 142 */             supportedEllipticCurves()));
/*     */     }
/*     */     
/* 145 */     this.curve = curve;
/*     */     
/* 147 */     this.concatKDF = new ConcatKDF("SHA-256");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ConcatKDF getConcatKDF() {
/* 158 */     return this.concatKDF;
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
/*     */   public Curve getCurve() {
/* 178 */     return this.curve;
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
/*     */   protected JWECryptoParts encryptWithZ(JWEHeader header, SecretKey Z, byte[] clearText, byte[] aad) throws JOSEException {
/* 192 */     JWEAlgorithm alg = JWEHeaderValidation.getAlgorithmAndEnsureNotNull(header);
/* 193 */     ECDH.AlgorithmMode algMode = ECDH1PU.resolveAlgorithmMode(alg);
/* 194 */     EncryptionMethod enc = header.getEncryptionMethod();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     if (algMode.equals(ECDH.AlgorithmMode.DIRECT)) {
/* 200 */       if (isCEKProvided()) {
/* 201 */         throw new JOSEException("The provided CEK is not supported");
/*     */       }
/*     */       
/* 204 */       getConcatKDF().getJCAContext().setProvider(getJCAContext().getMACProvider());
/* 205 */       SecretKey cek = ECDH1PU.deriveSharedKey(header, Z, getConcatKDF());
/*     */       
/* 207 */       return ContentCryptoProvider.encrypt(header, clearText, aad, cek, null, getJCAContext());
/*     */     } 
/*     */     
/* 210 */     if (algMode.equals(ECDH.AlgorithmMode.KW)) {
/*     */ 
/*     */ 
/*     */       
/* 214 */       if (!EncryptionMethod.Family.AES_CBC_HMAC_SHA.contains(enc)) {
/* 215 */         throw new JOSEException(AlgorithmSupportMessage.unsupportedEncryptionMethod(header
/* 216 */               .getEncryptionMethod(), EncryptionMethod.Family.AES_CBC_HMAC_SHA));
/*     */       }
/*     */ 
/*     */       
/* 220 */       SecretKey cek = getCEK(enc);
/*     */       
/* 222 */       JWECryptoParts encrypted = ContentCryptoProvider.encrypt(header, clearText, aad, cek, null, getJCAContext());
/*     */       
/* 224 */       SecretKey sharedKey = ECDH1PU.deriveSharedKey(header, Z, encrypted.getAuthenticationTag(), getConcatKDF());
/* 225 */       Base64URL encryptedKey = Base64URL.encode(AESKW.wrapCEK(cek, sharedKey, getJCAContext().getKeyEncryptionProvider()));
/*     */       
/* 227 */       return new JWECryptoParts(header, encryptedKey, encrypted
/*     */ 
/*     */           
/* 230 */           .getInitializationVector(), encrypted
/* 231 */           .getCipherText(), encrypted
/* 232 */           .getAuthenticationTag());
/*     */     } 
/*     */ 
/*     */     
/* 236 */     throw new JOSEException("Unexpected JWE ECDH algorithm mode: " + algMode);
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
/*     */   protected byte[] decryptWithZ(JWEHeader header, byte[] aad, SecretKey Z, Base64URL encryptedKey, Base64URL iv, Base64URL cipherText, Base64URL authTag) throws JOSEException {
/*     */     SecretKey cek;
/* 252 */     JWEAlgorithm alg = JWEHeaderValidation.getAlgorithmAndEnsureNotNull(header);
/* 253 */     ECDH.AlgorithmMode algMode = ECDH1PU.resolveAlgorithmMode(alg);
/*     */ 
/*     */     
/* 256 */     getConcatKDF().getJCAContext().setProvider(getJCAContext().getMACProvider());
/*     */ 
/*     */ 
/*     */     
/* 260 */     if (algMode.equals(ECDH.AlgorithmMode.DIRECT)) {
/* 261 */       cek = ECDH1PU.deriveSharedKey(header, Z, getConcatKDF());
/* 262 */     } else if (algMode.equals(ECDH.AlgorithmMode.KW)) {
/* 263 */       if (encryptedKey == null) {
/* 264 */         throw new JOSEException("Missing JWE encrypted key");
/*     */       }
/*     */       
/* 267 */       SecretKey sharedKey = ECDH1PU.deriveSharedKey(header, Z, authTag, getConcatKDF());
/* 268 */       cek = AESKW.unwrapCEK(sharedKey, encryptedKey.decode(), getJCAContext().getKeyEncryptionProvider());
/*     */     } else {
/* 270 */       throw new JOSEException("Unexpected JWE ECDH algorithm mode: " + algMode);
/*     */     } 
/*     */     
/* 273 */     return ContentCryptoProvider.decrypt(header, aad, null, iv, cipherText, authTag, cek, getJCAContext());
/*     */   }
/*     */   
/*     */   public abstract Set<Curve> supportedEllipticCurves();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\ECDH1PUCryptoProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */