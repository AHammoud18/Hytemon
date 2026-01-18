/*     */ package com.google.crypto.tink.hybrid;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.internal.BigIntegerEncoding;
/*     */ import com.google.crypto.tink.internal.EllipticCurvesUtil;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import com.google.crypto.tink.subtle.X25519;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.ECParameterSpec;
/*     */ import java.security.spec.ECPoint;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class HpkePrivateKey
/*     */   extends HybridPrivateKey
/*     */ {
/*     */   private final HpkePublicKey publicKey;
/*     */   private final SecretBytes privateKeyBytes;
/*     */   
/*     */   private HpkePrivateKey(HpkePublicKey publicKey, SecretBytes privateKeyBytes) {
/*  42 */     this.publicKey = publicKey;
/*  43 */     this.privateKeyBytes = privateKeyBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validatePrivateKeyByteLength(HpkeParameters.KemId kemId, SecretBytes privateKeyBytes) throws GeneralSecurityException {
/*  49 */     int keyLengthInBytes = privateKeyBytes.size();
/*  50 */     String parameterizedErrorMessage = "Encoded private key byte length for " + kemId + " must be %d, not " + keyLengthInBytes;
/*     */     
/*  52 */     if (kemId == HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256) {
/*  53 */       if (keyLengthInBytes != 32) {
/*  54 */         throw new GeneralSecurityException(String.format(parameterizedErrorMessage, new Object[] { Integer.valueOf(32) }));
/*     */       }
/*     */       return;
/*     */     } 
/*  58 */     if (kemId == HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384) {
/*  59 */       if (keyLengthInBytes != 48) {
/*  60 */         throw new GeneralSecurityException(String.format(parameterizedErrorMessage, new Object[] { Integer.valueOf(48) }));
/*     */       }
/*     */       return;
/*     */     } 
/*  64 */     if (kemId == HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512) {
/*  65 */       if (keyLengthInBytes != 66) {
/*  66 */         throw new GeneralSecurityException(String.format(parameterizedErrorMessage, new Object[] { Integer.valueOf(66) }));
/*     */       }
/*     */       return;
/*     */     } 
/*  70 */     if (kemId == HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256) {
/*  71 */       if (keyLengthInBytes != 32) {
/*  72 */         throw new GeneralSecurityException(String.format(parameterizedErrorMessage, new Object[] { Integer.valueOf(32) }));
/*     */       }
/*     */       return;
/*     */     } 
/*  76 */     throw new GeneralSecurityException("Unable to validate private key length for " + kemId);
/*     */   }
/*     */   
/*     */   private static boolean isNistKem(HpkeParameters.KemId kemId) {
/*  80 */     return (kemId == HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256 || kemId == HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384 || kemId == HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ECParameterSpec getNistCurveParams(HpkeParameters.KemId kemId) {
/*  86 */     if (kemId == HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256) {
/*  87 */       return EllipticCurves.getNistP256Params();
/*     */     }
/*  89 */     if (kemId == HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384) {
/*  90 */       return EllipticCurves.getNistP384Params();
/*     */     }
/*  92 */     if (kemId == HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512) {
/*  93 */       return EllipticCurves.getNistP521Params();
/*     */     }
/*  95 */     throw new IllegalArgumentException("Unable to determine NIST curve params for " + kemId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateKeyPair(HpkeParameters.KemId kemId, byte[] publicKeyBytes, byte[] privateKeyBytes) throws GeneralSecurityException {
/* 105 */     if (isNistKem(kemId)) {
/* 106 */       ECParameterSpec spec = getNistCurveParams(kemId);
/* 107 */       BigInteger order = spec.getOrder();
/* 108 */       BigInteger privateKey = BigIntegerEncoding.fromUnsignedBigEndianBytes(privateKeyBytes);
/* 109 */       if (privateKey.signum() <= 0 || privateKey.compareTo(order) >= 0) {
/* 110 */         throw new GeneralSecurityException("Invalid private key.");
/*     */       }
/* 112 */       ECPoint expectedPoint = EllipticCurvesUtil.multiplyByGenerator(privateKey, spec);
/*     */       
/* 114 */       ECPoint publicPoint = EllipticCurves.pointDecode(spec
/* 115 */           .getCurve(), EllipticCurves.PointFormatType.UNCOMPRESSED, publicKeyBytes);
/* 116 */       if (!expectedPoint.equals(publicPoint)) {
/* 117 */         throw new GeneralSecurityException("Invalid private key for public key.");
/*     */       }
/*     */       return;
/*     */     } 
/* 121 */     if (kemId == HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256) {
/* 122 */       byte[] expectedPublicKeyBytes = X25519.publicFromPrivate(privateKeyBytes);
/* 123 */       if (!Arrays.equals(expectedPublicKeyBytes, publicKeyBytes)) {
/* 124 */         throw new GeneralSecurityException("Invalid private key for public key.");
/*     */       }
/*     */       return;
/*     */     } 
/* 128 */     throw new IllegalArgumentException("Unable to validate key pair for " + kemId);
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
/*     */   public static HpkePrivateKey create(HpkePublicKey publicKey, SecretBytes privateKeyBytes) throws GeneralSecurityException {
/* 146 */     if (publicKey == null) {
/* 147 */       throw new GeneralSecurityException("HPKE private key cannot be constructed without an HPKE public key");
/*     */     }
/*     */     
/* 150 */     if (privateKeyBytes == null) {
/* 151 */       throw new GeneralSecurityException("HPKE private key cannot be constructed without secret");
/*     */     }
/* 153 */     validatePrivateKeyByteLength(publicKey.getParameters().getKemId(), privateKeyBytes);
/* 154 */     validateKeyPair(publicKey
/* 155 */         .getParameters().getKemId(), publicKey
/* 156 */         .getPublicKeyBytes().toByteArray(), privateKeyBytes
/* 157 */         .toByteArray(InsecureSecretKeyAccess.get()));
/* 158 */     return new HpkePrivateKey(publicKey, privateKeyBytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public SecretBytes getPrivateKeyBytes() {
/* 167 */     return this.privateKeyBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public HpkeParameters getParameters() {
/* 172 */     return this.publicKey.getParameters();
/*     */   }
/*     */ 
/*     */   
/*     */   public HpkePublicKey getPublicKey() {
/* 177 */     return this.publicKey;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 182 */     if (!(o instanceof HpkePrivateKey)) {
/* 183 */       return false;
/*     */     }
/* 185 */     HpkePrivateKey other = (HpkePrivateKey)o;
/* 186 */     return (this.publicKey.equalsKey(other.publicKey) && this.privateKeyBytes
/* 187 */       .equalsSecretBytes(other.privateKeyBytes));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HpkePrivateKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */