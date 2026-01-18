/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWECryptoParts;
/*     */ import com.nimbusds.jose.JWEEncrypter;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.crypto.impl.ECDH1PU;
/*     */ import com.nimbusds.jose.crypto.impl.ECDH1PUCryptoProvider;
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.jwk.OctetKeyPair;
/*     */ import com.nimbusds.jose.jwk.gen.OctetKeyPairGenerator;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class ECDH1PUX25519Encrypter
/*     */   extends ECDH1PUCryptoProvider
/*     */   implements JWEEncrypter
/*     */ {
/*     */   private final OctetKeyPair publicKey;
/*     */   private final OctetKeyPair privateKey;
/*     */   
/*     */   public ECDH1PUX25519Encrypter(OctetKeyPair privateKey, OctetKeyPair publicKey) throws JOSEException {
/* 120 */     this(privateKey, publicKey, (SecretKey)null);
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
/*     */   public ECDH1PUX25519Encrypter(OctetKeyPair privateKey, OctetKeyPair publicKey, SecretKey contentEncryptionKey) throws JOSEException {
/* 143 */     super(publicKey.getCurve(), contentEncryptionKey);
/*     */     
/* 145 */     this.publicKey = publicKey;
/* 146 */     this.privateKey = privateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Curve> supportedEllipticCurves() {
/* 152 */     return Collections.singleton(Curve.X25519);
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
/* 163 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OctetKeyPair getPrivateKey() {
/* 173 */     return this.privateKey;
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
/* 195 */     return encrypt(header, clearText, AAD.compute(header));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWECryptoParts encrypt(JWEHeader header, byte[] clearText, byte[] aad) throws JOSEException {
/* 203 */     OctetKeyPair ephemeralPrivateKey = (new OctetKeyPairGenerator(getCurve())).generate();
/* 204 */     OctetKeyPair ephemeralPublicKey = ephemeralPrivateKey.toPublicJWK();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     JWEHeader updatedHeader = (new JWEHeader.Builder(header)).ephemeralPublicKey((JWK)ephemeralPublicKey).build();
/*     */     
/* 211 */     SecretKey Z = ECDH1PU.deriveSenderZ(this.privateKey, this.publicKey, ephemeralPrivateKey);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     byte[] updatedAAD = Arrays.equals(AAD.compute(header), aad) ? AAD.compute(updatedHeader) : aad;
/*     */     
/* 220 */     return encryptWithZ(updatedHeader, Z, clearText, updatedAAD);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\ECDH1PUX25519Encrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */