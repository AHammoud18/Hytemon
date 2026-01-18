/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.google.crypto.tink.subtle.X25519;
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWECryptoParts;
/*     */ import com.nimbusds.jose.JWEEncrypter;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.crypto.impl.ECDH;
/*     */ import com.nimbusds.jose.crypto.impl.ECDHCryptoProvider;
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.jwk.OctetKeyPair;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public class X25519Encrypter
/*     */   extends ECDHCryptoProvider
/*     */   implements JWEEncrypter
/*     */ {
/*     */   private final OctetKeyPair publicKey;
/*     */   
/*     */   public X25519Encrypter(OctetKeyPair publicKey) throws JOSEException {
/* 103 */     this(publicKey, (SecretKey)null);
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
/*     */   public X25519Encrypter(OctetKeyPair publicKey, SecretKey contentEncryptionKey) throws JOSEException {
/* 123 */     super(publicKey.getCurve(), contentEncryptionKey);
/*     */     
/* 125 */     if (!Curve.X25519.equals(publicKey.getCurve())) {
/* 126 */       throw new JOSEException("X25519Encrypter only supports OctetKeyPairs with crv=X25519");
/*     */     }
/*     */     
/* 129 */     if (publicKey.isPrivate()) {
/* 130 */       throw new JOSEException("X25519Encrypter requires a public key, use OctetKeyPair.toPublicJWK()");
/*     */     }
/*     */     
/* 133 */     this.publicKey = publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Curve> supportedEllipticCurves() {
/* 140 */     return Collections.singleton(Curve.X25519);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OctetKeyPair getPublicKey() {
/* 151 */     return this.publicKey;
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
/* 173 */     return encrypt(header, clearText, AAD.compute(header));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWECryptoParts encrypt(JWEHeader header, byte[] clearText, byte[] aad) throws JOSEException {
/* 182 */     byte[] ephemeralPublicKeyBytes, ephemeralPrivateKeyBytes = X25519.generatePrivateKey();
/*     */     
/*     */     try {
/* 185 */       ephemeralPublicKeyBytes = X25519.publicFromPrivate(ephemeralPrivateKeyBytes);
/*     */     }
/* 187 */     catch (InvalidKeyException e) {
/*     */       
/* 189 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     OctetKeyPair ephemeralPrivateKey = (new OctetKeyPair.Builder(getCurve(), Base64URL.encode(ephemeralPublicKeyBytes))).d(Base64URL.encode(ephemeralPrivateKeyBytes)).build();
/* 196 */     OctetKeyPair ephemeralPublicKey = ephemeralPrivateKey.toPublicJWK();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     JWEHeader updatedHeader = (new JWEHeader.Builder(header)).ephemeralPublicKey((JWK)ephemeralPublicKey).build();
/*     */ 
/*     */     
/* 204 */     SecretKey Z = ECDH.deriveSharedSecret(this.publicKey, ephemeralPrivateKey);
/*     */ 
/*     */     
/* 207 */     byte[] updatedAAD = Arrays.equals(AAD.compute(header), aad) ? AAD.compute(updatedHeader) : aad;
/*     */     
/* 209 */     return encryptWithZ(updatedHeader, Z, clearText, updatedAAD);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\X25519Encrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */