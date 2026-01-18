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
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import com.nimbusds.jose.jwk.OctetKeyPair;
/*     */ import com.nimbusds.jose.util.Base64URL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X25519Decrypter
/*     */   extends ECDHCryptoProvider
/*     */   implements JWEDecrypter, CriticalHeaderParamsAware
/*     */ {
/*     */   private final OctetKeyPair privateKey;
/*  92 */   private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X25519Decrypter(OctetKeyPair privateKey) throws JOSEException {
/* 105 */     this(privateKey, null);
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
/*     */   public X25519Decrypter(OctetKeyPair privateKey, Set<String> defCritHeaders) throws JOSEException {
/* 122 */     super(privateKey.getCurve(), null);
/*     */     
/* 124 */     if (!Curve.X25519.equals(privateKey.getCurve())) {
/* 125 */       throw new JOSEException("X25519Decrypter only supports OctetKeyPairs with crv=X25519");
/*     */     }
/*     */     
/* 128 */     if (!privateKey.isPrivate()) {
/* 129 */       throw new JOSEException("The OctetKeyPair doesn't contain a private part");
/*     */     }
/*     */     
/* 132 */     this.privateKey = privateKey;
/*     */     
/* 134 */     this.critPolicy.setDeferredCriticalHeaderParams(defCritHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Curve> supportedEllipticCurves() {
/* 141 */     return Collections.singleton(Curve.X25519);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OctetKeyPair getPrivateKey() {
/* 152 */     return this.privateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getProcessedCriticalHeaderParams() {
/* 159 */     return this.critPolicy.getProcessedCriticalHeaderParams();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getDeferredCriticalHeaderParams() {
/* 166 */     return this.critPolicy.getProcessedCriticalHeaderParams();
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
/* 201 */     return decrypt(header, encryptedKey, iv, cipherText, authTag, AAD.compute(header));
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
/* 215 */     this.critPolicy.ensureHeaderPasses(header);
/*     */ 
/*     */     
/* 218 */     OctetKeyPair ephemeralPublicKey = (OctetKeyPair)header.getEphemeralPublicKey();
/*     */     
/* 220 */     if (ephemeralPublicKey == null) {
/* 221 */       throw new JOSEException("Missing ephemeral public key epk JWE header parameter");
/*     */     }
/*     */     
/* 224 */     if (!this.privateKey.getCurve().equals(ephemeralPublicKey.getCurve())) {
/* 225 */       throw new JOSEException("Curve of ephemeral public key does not match curve of private key");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     SecretKey Z = ECDH.deriveSharedSecret(ephemeralPublicKey, this.privateKey);
/*     */     
/* 233 */     return decryptWithZ(header, aad, Z, encryptedKey, iv, cipherText, authTag);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\X25519Decrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */