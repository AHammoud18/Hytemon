/*     */ package com.google.crypto.tink.hybrid;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.internal.EllipticCurvesUtil;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import com.google.crypto.tink.subtle.X25519;
/*     */ import com.google.crypto.tink.util.SecretBigInteger;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.security.spec.ECPoint;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public final class EciesPrivateKey
/*     */   extends HybridPrivateKey
/*     */ {
/*     */   private final EciesPublicKey publicKey;
/*     */   @Nullable
/*     */   private final SecretBigInteger nistPrivateKeyValue;
/*     */   @Nullable
/*     */   private final SecretBytes x25519PrivateKeyBytes;
/*     */   
/*     */   private EciesPrivateKey(EciesPublicKey publicKey, @Nullable SecretBigInteger nistPrivateKeyValue, @Nullable SecretBytes x25519PrivateKeyBytes) {
/*  50 */     this.publicKey = publicKey;
/*  51 */     this.nistPrivateKeyValue = nistPrivateKeyValue;
/*  52 */     this.x25519PrivateKeyBytes = x25519PrivateKeyBytes;
/*     */   }
/*     */   
/*     */   private static ECParameterSpec toParameterSpecNistCurve(EciesParameters.CurveType curveType) {
/*  56 */     if (curveType == EciesParameters.CurveType.NIST_P256) {
/*  57 */       return EllipticCurves.getNistP256Params();
/*     */     }
/*  59 */     if (curveType == EciesParameters.CurveType.NIST_P384) {
/*  60 */       return EllipticCurves.getNistP384Params();
/*     */     }
/*  62 */     if (curveType == EciesParameters.CurveType.NIST_P521) {
/*  63 */       return EllipticCurves.getNistP521Params();
/*     */     }
/*  65 */     throw new IllegalArgumentException("Unable to determine NIST curve type for " + curveType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateNistPrivateKeyValue(BigInteger privateValue, ECPoint publicPoint, EciesParameters.CurveType curveType) throws GeneralSecurityException {
/*  71 */     BigInteger order = toParameterSpecNistCurve(curveType).getOrder();
/*  72 */     if (privateValue.signum() <= 0 || privateValue.compareTo(order) >= 0) {
/*  73 */       throw new GeneralSecurityException("Invalid private value");
/*     */     }
/*     */     
/*  76 */     ECPoint p = EllipticCurvesUtil.multiplyByGenerator(privateValue, toParameterSpecNistCurve(curveType));
/*  77 */     if (!p.equals(publicPoint)) {
/*  78 */       throw new GeneralSecurityException("Invalid private value");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validateX25519PrivateKeyBytes(byte[] privateKeyBytes, byte[] publicKeyBytes) throws GeneralSecurityException {
/*  84 */     if (privateKeyBytes.length != 32) {
/*  85 */       throw new GeneralSecurityException("Private key bytes length for X25519 curve must be 32");
/*     */     }
/*  87 */     byte[] expectedPublicKeyBytes = X25519.publicFromPrivate(privateKeyBytes);
/*  88 */     if (!Arrays.equals(expectedPublicKeyBytes, publicKeyBytes)) {
/*  89 */       throw new GeneralSecurityException("Invalid private key for public key.");
/*     */     }
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
/*     */   @AccessesPartialKey
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static EciesPrivateKey createForCurveX25519(EciesPublicKey publicKey, SecretBytes x25519PrivateKeyBytes) throws GeneralSecurityException {
/* 107 */     if (publicKey == null) {
/* 108 */       throw new GeneralSecurityException("ECIES private key cannot be constructed without an ECIES public key");
/*     */     }
/*     */     
/* 111 */     if (publicKey.getX25519CurvePointBytes() == null) {
/* 112 */       throw new GeneralSecurityException("ECIES private key for X25519 curve cannot be constructed with NIST-curve public key");
/*     */     }
/*     */     
/* 115 */     if (x25519PrivateKeyBytes == null) {
/* 116 */       throw new GeneralSecurityException("ECIES private key cannot be constructed without secret");
/*     */     }
/* 118 */     validateX25519PrivateKeyBytes(x25519PrivateKeyBytes
/* 119 */         .toByteArray(InsecureSecretKeyAccess.get()), publicKey
/* 120 */         .getX25519CurvePointBytes().toByteArray());
/*     */     
/* 122 */     return new EciesPrivateKey(publicKey, null, x25519PrivateKeyBytes);
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
/*     */   @AccessesPartialKey
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static EciesPrivateKey createForNistCurve(EciesPublicKey publicKey, SecretBigInteger nistPrivateKeyValue) throws GeneralSecurityException {
/* 140 */     if (publicKey == null) {
/* 141 */       throw new GeneralSecurityException("ECIES private key cannot be constructed without an ECIES public key");
/*     */     }
/*     */     
/* 144 */     if (publicKey.getNistCurvePoint() == null) {
/* 145 */       throw new GeneralSecurityException("ECIES private key for NIST curve cannot be constructed with X25519-curve public key");
/*     */     }
/*     */     
/* 148 */     if (nistPrivateKeyValue == null) {
/* 149 */       throw new GeneralSecurityException("ECIES private key cannot be constructed without secret");
/*     */     }
/* 151 */     validateNistPrivateKeyValue(nistPrivateKeyValue
/* 152 */         .getBigInteger(InsecureSecretKeyAccess.get()), publicKey
/* 153 */         .getNistCurvePoint(), publicKey
/* 154 */         .getParameters().getCurveType());
/*     */     
/* 156 */     return new EciesPrivateKey(publicKey, nistPrivateKeyValue, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBytes getX25519PrivateKeyBytes() {
/* 166 */     return this.x25519PrivateKeyBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBigInteger getNistPrivateKeyValue() {
/* 176 */     return this.nistPrivateKeyValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public EciesParameters getParameters() {
/* 181 */     return this.publicKey.getParameters();
/*     */   }
/*     */ 
/*     */   
/*     */   public EciesPublicKey getPublicKey() {
/* 186 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 191 */     if (!(o instanceof EciesPrivateKey)) {
/* 192 */       return false;
/*     */     }
/* 194 */     EciesPrivateKey other = (EciesPrivateKey)o;
/* 195 */     if (!this.publicKey.equalsKey(other.publicKey)) {
/* 196 */       return false;
/*     */     }
/* 198 */     if (this.x25519PrivateKeyBytes == null && other.x25519PrivateKeyBytes == null) {
/* 199 */       return this.nistPrivateKeyValue.equalsSecretBigInteger(other.nistPrivateKeyValue);
/*     */     }
/*     */     
/* 202 */     return this.x25519PrivateKeyBytes.equalsSecretBytes(other.x25519PrivateKeyBytes);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\EciesPrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */