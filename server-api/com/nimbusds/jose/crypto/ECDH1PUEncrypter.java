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
/*     */ import com.nimbusds.jose.jwk.ECKey;
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.interfaces.ECPrivateKey;
/*     */ import java.security.interfaces.ECPublicKey;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.util.Arrays;
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
/*     */ @ThreadSafe
/*     */ public class ECDH1PUEncrypter
/*     */   extends ECDH1PUCryptoProvider
/*     */   implements JWEEncrypter
/*     */ {
/*     */   public static final Set<Curve> SUPPORTED_ELLIPTIC_CURVES;
/*     */   private final ECPublicKey publicKey;
/*     */   private final ECPrivateKey privateKey;
/*     */   
/*     */   static {
/* 107 */     Set<Curve> curves = new LinkedHashSet<>();
/* 108 */     curves.add(Curve.P_256);
/* 109 */     curves.add(Curve.P_384);
/* 110 */     curves.add(Curve.P_521);
/* 111 */     SUPPORTED_ELLIPTIC_CURVES = Collections.unmodifiableSet(curves);
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
/*     */   public ECDH1PUEncrypter(ECPrivateKey privateKey, ECPublicKey publicKey) throws JOSEException {
/* 136 */     this(privateKey, publicKey, (SecretKey)null);
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
/*     */   public ECDH1PUEncrypter(ECPrivateKey privateKey, ECPublicKey publicKey, SecretKey contentEncryptionKey) throws JOSEException {
/* 161 */     super(Curve.forECParameterSpec(publicKey.getParams()), contentEncryptionKey);
/*     */     
/* 163 */     this.privateKey = privateKey;
/* 164 */     this.publicKey = publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ECPublicKey getPublicKey() {
/* 175 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ECPrivateKey getPrivateKey() {
/* 186 */     return this.privateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Curve> supportedEllipticCurves() {
/* 193 */     return SUPPORTED_ELLIPTIC_CURVES;
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
/* 215 */     return encrypt(header, clearText, AAD.compute(header));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWECryptoParts encrypt(JWEHeader header, byte[] clearText, byte[] aad) throws JOSEException {
/* 224 */     KeyPair ephemeralKeyPair = generateEphemeralKeyPair(this.publicKey.getParams());
/* 225 */     ECPublicKey ephemeralPublicKey = (ECPublicKey)ephemeralKeyPair.getPublic();
/* 226 */     ECPrivateKey ephemeralPrivateKey = (ECPrivateKey)ephemeralKeyPair.getPrivate();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     JWEHeader updatedHeader = (new JWEHeader.Builder(header)).ephemeralPublicKey((JWK)(new ECKey.Builder(getCurve(), ephemeralPublicKey)).build()).build();
/*     */     
/* 233 */     SecretKey Z = ECDH1PU.deriveSenderZ(this.privateKey, this.publicKey, ephemeralPrivateKey, 
/*     */ 
/*     */ 
/*     */         
/* 237 */         getJCAContext().getKeyEncryptionProvider());
/*     */ 
/*     */ 
/*     */     
/* 241 */     byte[] updatedAAD = Arrays.equals(AAD.compute(header), aad) ? AAD.compute(updatedHeader) : aad;
/*     */     
/* 243 */     return encryptWithZ(updatedHeader, Z, clearText, updatedAAD);
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
/*     */   private KeyPair generateEphemeralKeyPair(ECParameterSpec ecParameterSpec) throws JOSEException {
/* 259 */     Provider keProvider = getJCAContext().getKeyEncryptionProvider();
/*     */     
/*     */     try {
/*     */       KeyPairGenerator generator;
/*     */       
/* 264 */       if (keProvider != null) {
/* 265 */         generator = KeyPairGenerator.getInstance("EC", keProvider);
/*     */       } else {
/* 267 */         generator = KeyPairGenerator.getInstance("EC");
/*     */       } 
/*     */       
/* 270 */       generator.initialize(ecParameterSpec);
/* 271 */       return generator.generateKeyPair();
/* 272 */     } catch (NoSuchAlgorithmException|java.security.InvalidAlgorithmParameterException e) {
/* 273 */       throw new JOSEException("Couldn't generate ephemeral EC key pair: " + e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\ECDH1PUEncrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */