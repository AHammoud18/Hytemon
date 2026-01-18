/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.CriticalHeaderParamsAware;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEDecrypter;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
/*     */ import com.nimbusds.jose.crypto.impl.ECDH;
/*     */ import com.nimbusds.jose.crypto.impl.ECDHCryptoProvider;
/*     */ import com.nimbusds.jose.crypto.utils.ECChecks;
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import com.nimbusds.jose.jwk.ECKey;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.interfaces.ECPrivateKey;
/*     */ import java.security.interfaces.ECPublicKey;
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
/*     */ public class ECDHDecrypter
/*     */   extends ECDHCryptoProvider
/*     */   implements JWEDecrypter, CriticalHeaderParamsAware
/*     */ {
/*     */   public static final Set<Curve> SUPPORTED_ELLIPTIC_CURVES;
/*     */   private final PrivateKey privateKey;
/*     */   
/*     */   static {
/*  98 */     Set<Curve> curves = new LinkedHashSet<>();
/*  99 */     curves.add(Curve.P_256);
/* 100 */     curves.add(Curve.P_384);
/* 101 */     curves.add(Curve.P_521);
/* 102 */     SUPPORTED_ELLIPTIC_CURVES = Collections.unmodifiableSet(curves);
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
/* 115 */   private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ECDHDecrypter(ECPrivateKey privateKey) throws JOSEException {
/* 128 */     this(privateKey, (Set<String>)null);
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
/*     */   public ECDHDecrypter(ECKey ecJWK) throws JOSEException {
/* 143 */     super(ecJWK.getCurve(), null);
/*     */     
/* 145 */     if (!ecJWK.isPrivate()) {
/* 146 */       throw new JOSEException("The EC JWK doesn't contain a private part");
/*     */     }
/*     */     
/* 149 */     this.privateKey = ecJWK.toECPrivateKey();
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
/*     */   public ECDHDecrypter(ECPrivateKey privateKey, Set<String> defCritHeaders) throws JOSEException {
/* 166 */     this(privateKey, defCritHeaders, Curve.forECParameterSpec(privateKey.getParams()));
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
/*     */   public ECDHDecrypter(PrivateKey privateKey, Set<String> defCritHeaders, Curve curve) throws JOSEException {
/* 189 */     super(curve, null);
/*     */     
/* 191 */     this.critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
/*     */     
/* 193 */     this.privateKey = privateKey;
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
/* 207 */     return this.privateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Curve> supportedEllipticCurves() {
/* 214 */     return SUPPORTED_ELLIPTIC_CURVES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getProcessedCriticalHeaderParams() {
/* 221 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getDeferredCriticalHeaderParams() {
/* 228 */     return this.critPolicy.getProcessedCriticalHeaderParams();
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
/*     */   
/*     */   @Deprecated
/*     */   public byte[] decrypt(JWEHeader header, Base64URL encryptedKey, Base64URL iv, Base64URL cipherText, Base64URL authTag) throws JOSEException {
/* 264 */     return decrypt(header, encryptedKey, iv, cipherText, authTag, AAD.compute(header));
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
/* 277 */     this.critPolicy.ensureHeaderPasses(header);
/*     */ 
/*     */     
/* 280 */     ECKey ephemeralKey = (ECKey)header.getEphemeralPublicKey();
/*     */     
/* 282 */     if (ephemeralKey == null) {
/* 283 */       throw new JOSEException("Missing ephemeral public EC key \"epk\" JWE header parameter");
/*     */     }
/*     */     
/* 286 */     ECPublicKey ephemeralPublicKey = ephemeralKey.toECPublicKey();
/*     */ 
/*     */     
/* 289 */     if (getPrivateKey() instanceof ECPrivateKey) {
/* 290 */       ECPrivateKey ecPrivateKey = (ECPrivateKey)getPrivateKey();
/* 291 */       if (!ECChecks.isPointOnCurve(ephemeralPublicKey, ecPrivateKey)) {
/* 292 */         throw new JOSEException("Invalid ephemeral public EC key: Point(s) not on the expected curve");
/*     */       }
/*     */     }
/* 295 */     else if (!ECChecks.isPointOnCurve(ephemeralPublicKey, getCurve().toECParameterSpec())) {
/* 296 */       throw new JOSEException("Invalid ephemeral public EC key: Point(s) not on the expected curve");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 301 */     SecretKey Z = ECDH.deriveSharedSecret(ephemeralPublicKey, this.privateKey, 
/*     */ 
/*     */         
/* 304 */         getJCAContext().getKeyEncryptionProvider());
/*     */     
/* 306 */     return decryptWithZ(header, aad, Z, encryptedKey, iv, cipherText, authTag);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\ECDHDecrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */