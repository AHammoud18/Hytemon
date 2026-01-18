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
/*     */ public abstract class ECDHCryptoProvider
/*     */   extends BaseJWEProvider
/*     */ {
/*     */   public static final Set<JWEAlgorithm> SUPPORTED_ALGORITHMS;
/*  85 */   public static final Set<EncryptionMethod> SUPPORTED_ENCRYPTION_METHODS = ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS;
/*     */   private final Curve curve;
/*     */   
/*     */   static {
/*  89 */     Set<JWEAlgorithm> algs = new LinkedHashSet<>();
/*  90 */     algs.add(JWEAlgorithm.ECDH_ES);
/*  91 */     algs.add(JWEAlgorithm.ECDH_ES_A128KW);
/*  92 */     algs.add(JWEAlgorithm.ECDH_ES_A192KW);
/*  93 */     algs.add(JWEAlgorithm.ECDH_ES_A256KW);
/*  94 */     SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);
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
/*     */   protected ECDHCryptoProvider(Curve curve, SecretKey cek) throws JOSEException {
/* 127 */     super(SUPPORTED_ALGORITHMS, ContentCryptoProvider.SUPPORTED_ENCRYPTION_METHODS, cek);
/*     */     
/* 129 */     Curve definedCurve = (curve != null) ? curve : new Curve("unknown");
/*     */     
/* 131 */     if (!supportedEllipticCurves().contains(curve)) {
/* 132 */       throw new JOSEException(AlgorithmSupportMessage.unsupportedEllipticCurve(definedCurve, 
/* 133 */             supportedEllipticCurves()));
/*     */     }
/*     */     
/* 136 */     this.curve = curve;
/*     */     
/* 138 */     this.concatKDF = new ConcatKDF("SHA-256");
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
/* 149 */     return this.concatKDF;
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
/* 169 */     return this.curve;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JWECryptoParts encryptWithZ(JWEHeader header, SecretKey Z, byte[] clearText, byte[] aad) throws JOSEException {
/*     */     SecretKey cek;
/*     */     Base64URL encryptedKey;
/* 180 */     JWEAlgorithm alg = JWEHeaderValidation.getAlgorithmAndEnsureNotNull(header);
/* 181 */     ECDH.AlgorithmMode algMode = ECDH.resolveAlgorithmMode(alg);
/* 182 */     EncryptionMethod enc = header.getEncryptionMethod();
/*     */ 
/*     */     
/* 185 */     getConcatKDF().getJCAContext().setProvider(getJCAContext().getMACProvider());
/* 186 */     SecretKey sharedKey = ECDH.deriveSharedKey(header, Z, getConcatKDF());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     if (algMode.equals(ECDH.AlgorithmMode.DIRECT)) {
/* 192 */       if (isCEKProvided()) {
/* 193 */         throw new JOSEException("The provided CEK is not supported");
/*     */       }
/* 195 */       cek = sharedKey;
/* 196 */       encryptedKey = null;
/* 197 */     } else if (algMode.equals(ECDH.AlgorithmMode.KW)) {
/* 198 */       cek = getCEK(enc);
/* 199 */       encryptedKey = Base64URL.encode(AESKW.wrapCEK(cek, sharedKey, getJCAContext().getKeyEncryptionProvider()));
/*     */     } else {
/* 201 */       throw new JOSEException("Unexpected JWE ECDH algorithm mode: " + algMode);
/*     */     } 
/*     */     
/* 204 */     return ContentCryptoProvider.encrypt(header, clearText, aad, cek, encryptedKey, getJCAContext());
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
/*     */   protected byte[] decryptWithZ(JWEHeader header, byte[] aad, SecretKey Z, Base64URL encryptedKey, Base64URL iv, Base64URL cipherText, Base64URL authTag) throws JOSEException {
/*     */     SecretKey cek;
/* 221 */     JWEAlgorithm alg = JWEHeaderValidation.getAlgorithmAndEnsureNotNull(header);
/* 222 */     ECDH.AlgorithmMode algMode = ECDH.resolveAlgorithmMode(alg);
/*     */ 
/*     */     
/* 225 */     getConcatKDF().getJCAContext().setProvider(getJCAContext().getMACProvider());
/* 226 */     SecretKey sharedKey = ECDH.deriveSharedKey(header, Z, getConcatKDF());
/*     */ 
/*     */ 
/*     */     
/* 230 */     if (algMode.equals(ECDH.AlgorithmMode.DIRECT)) {
/* 231 */       cek = sharedKey;
/* 232 */     } else if (algMode.equals(ECDH.AlgorithmMode.KW)) {
/* 233 */       if (encryptedKey == null) {
/* 234 */         throw new JOSEException("Missing JWE encrypted key");
/*     */       }
/* 236 */       cek = AESKW.unwrapCEK(sharedKey, encryptedKey.decode(), getJCAContext().getKeyEncryptionProvider());
/*     */     } else {
/* 238 */       throw new JOSEException("Unexpected JWE ECDH algorithm mode: " + algMode);
/*     */     } 
/*     */     
/* 241 */     return ContentCryptoProvider.decrypt(header, aad, encryptedKey, iv, cipherText, authTag, cek, getJCAContext());
/*     */   }
/*     */   
/*     */   public abstract Set<Curve> supportedEllipticCurves();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\ECDHCryptoProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */