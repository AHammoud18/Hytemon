/*     */ package com.nimbusds.jose.crypto;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWECryptoParts;
/*     */ import com.nimbusds.jose.JWEEncrypter;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.crypto.impl.AAD;
/*     */ import com.nimbusds.jose.crypto.impl.ECDH;
/*     */ import com.nimbusds.jose.crypto.impl.ECDHCryptoProvider;
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
/*     */ 
/*     */ @ThreadSafe
/*     */ public class ECDHEncrypter
/*     */   extends ECDHCryptoProvider
/*     */   implements JWEEncrypter
/*     */ {
/*     */   public static final Set<Curve> SUPPORTED_ELLIPTIC_CURVES;
/*     */   private final ECPublicKey publicKey;
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
/*     */   public ECDHEncrypter(ECPublicKey publicKey) throws JOSEException {
/* 130 */     this(publicKey, (SecretKey)null);
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
/*     */   public ECDHEncrypter(ECKey ecJWK) throws JOSEException {
/* 144 */     this(ecJWK.toECPublicKey(), (SecretKey)null);
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
/*     */   public ECDHEncrypter(ECPublicKey publicKey, SecretKey contentEncryptionKey) throws JOSEException {
/* 164 */     super(Curve.forECParameterSpec(publicKey.getParams()), contentEncryptionKey);
/*     */     
/* 166 */     this.publicKey = publicKey;
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
/* 177 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Curve> supportedEllipticCurves() {
/* 184 */     return SUPPORTED_ELLIPTIC_CURVES;
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
/* 206 */     return encrypt(header, clearText, AAD.compute(header));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWECryptoParts encrypt(JWEHeader header, byte[] clearText, byte[] aad) throws JOSEException {
/* 215 */     KeyPair ephemeralKeyPair = generateEphemeralKeyPair(this.publicKey.getParams());
/* 216 */     ECPublicKey ephemeralPublicKey = (ECPublicKey)ephemeralKeyPair.getPublic();
/* 217 */     ECPrivateKey ephemeralPrivateKey = (ECPrivateKey)ephemeralKeyPair.getPrivate();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     JWEHeader updatedHeader = (new JWEHeader.Builder(header)).ephemeralPublicKey((JWK)(new ECKey.Builder(getCurve(), ephemeralPublicKey)).build()).build();
/*     */ 
/*     */     
/* 225 */     SecretKey Z = ECDH.deriveSharedSecret(this.publicKey, ephemeralPrivateKey, 
/*     */ 
/*     */         
/* 228 */         getJCAContext().getKeyEncryptionProvider());
/*     */ 
/*     */     
/* 231 */     byte[] updatedAAD = Arrays.equals(AAD.compute(header), aad) ? AAD.compute(updatedHeader) : aad;
/*     */     
/* 233 */     return encryptWithZ(updatedHeader, Z, clearText, updatedAAD);
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
/* 249 */     Provider keProvider = getJCAContext().getKeyEncryptionProvider();
/*     */     
/*     */     try {
/*     */       KeyPairGenerator generator;
/*     */       
/* 254 */       if (keProvider != null) {
/* 255 */         generator = KeyPairGenerator.getInstance("EC", keProvider);
/*     */       } else {
/* 257 */         generator = KeyPairGenerator.getInstance("EC");
/*     */       } 
/*     */       
/* 260 */       generator.initialize(ecParameterSpec);
/* 261 */       return generator.generateKeyPair();
/* 262 */     } catch (NoSuchAlgorithmException|java.security.InvalidAlgorithmParameterException e) {
/* 263 */       throw new JOSEException("Couldn't generate ephemeral EC key pair: " + e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\ECDHEncrypter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */