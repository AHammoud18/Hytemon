/*     */ package com.google.crypto.tink.hybrid;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Key;
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.internal.EllipticCurvesUtil;
/*     */ import com.google.crypto.tink.internal.OutputPrefixUtil;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import com.google.errorprone.annotations.RestrictedApi;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.ECPoint;
/*     */ import java.security.spec.EllipticCurve;
/*     */ import java.util.Objects;
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
/*     */ @Immutable
/*     */ public final class HpkePublicKey
/*     */   extends HybridPublicKey
/*     */ {
/*     */   private final HpkeParameters parameters;
/*     */   private final Bytes publicKeyBytes;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   private HpkePublicKey(HpkeParameters parameters, Bytes publicKeyBytes, Bytes outputPrefix, @Nullable Integer idRequirement) {
/*  46 */     this.parameters = parameters;
/*  47 */     this.publicKeyBytes = publicKeyBytes;
/*  48 */     this.outputPrefix = outputPrefix;
/*  49 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateIdRequirement(HpkeParameters.Variant variant, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  55 */     if (!variant.equals(HpkeParameters.Variant.NO_PREFIX) && idRequirement == null) {
/*  56 */       throw new GeneralSecurityException("'idRequirement' must be non-null for " + variant + " variant.");
/*     */     }
/*     */     
/*  59 */     if (variant.equals(HpkeParameters.Variant.NO_PREFIX) && idRequirement != null) {
/*  60 */       throw new GeneralSecurityException("'idRequirement' must be null for NO_PREFIX variant.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validatePublicKeyByteLength(HpkeParameters.KemId kemId, Bytes publicKeyBytes) throws GeneralSecurityException {
/*  67 */     int keyLengthInBytes = publicKeyBytes.size();
/*  68 */     String parameterizedErrorMessage = "Encoded public key byte length for " + kemId + " must be %d, not " + keyLengthInBytes;
/*     */     
/*  70 */     if (kemId == HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256) {
/*  71 */       if (keyLengthInBytes != 65) {
/*  72 */         throw new GeneralSecurityException(String.format(parameterizedErrorMessage, new Object[] { Integer.valueOf(65) }));
/*     */       }
/*     */       return;
/*     */     } 
/*  76 */     if (kemId == HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384) {
/*  77 */       if (keyLengthInBytes != 97) {
/*  78 */         throw new GeneralSecurityException(String.format(parameterizedErrorMessage, new Object[] { Integer.valueOf(97) }));
/*     */       }
/*     */       return;
/*     */     } 
/*  82 */     if (kemId == HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512) {
/*  83 */       if (keyLengthInBytes != 133) {
/*  84 */         throw new GeneralSecurityException(String.format(parameterizedErrorMessage, new Object[] { Integer.valueOf(133) }));
/*     */       }
/*     */       return;
/*     */     } 
/*  88 */     if (kemId == HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256) {
/*  89 */       if (keyLengthInBytes != 32) {
/*  90 */         throw new GeneralSecurityException(String.format(parameterizedErrorMessage, new Object[] { Integer.valueOf(32) }));
/*     */       }
/*     */       return;
/*     */     } 
/*  94 */     throw new GeneralSecurityException("Unable to validate public key length for " + kemId);
/*     */   }
/*     */   
/*     */   private static boolean isNistKem(HpkeParameters.KemId kemId) {
/*  98 */     return (kemId == HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256 || kemId == HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384 || kemId == HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static EllipticCurve getNistCurve(HpkeParameters.KemId kemId) {
/* 104 */     if (kemId == HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256) {
/* 105 */       return EllipticCurves.getNistP256Params().getCurve();
/*     */     }
/* 107 */     if (kemId == HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384) {
/* 108 */       return EllipticCurves.getNistP384Params().getCurve();
/*     */     }
/* 110 */     if (kemId == HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512) {
/* 111 */       return EllipticCurves.getNistP521Params().getCurve();
/*     */     }
/* 113 */     throw new IllegalArgumentException("Unable to determine NIST curve type for " + kemId);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void validatePublicKeyOnCurve(HpkeParameters.KemId kemId, Bytes publicKeyBytes) throws GeneralSecurityException {
/* 118 */     if (!isNistKem(kemId)) {
/*     */       return;
/*     */     }
/* 121 */     EllipticCurve curve = getNistCurve(kemId);
/*     */     
/* 123 */     ECPoint point = EllipticCurves.pointDecode(curve, EllipticCurves.PointFormatType.UNCOMPRESSED, publicKeyBytes
/* 124 */         .toByteArray());
/* 125 */     EllipticCurvesUtil.checkPointOnCurve(point, curve);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validatePublicKey(HpkeParameters.KemId kemId, Bytes publicKeyBytes) throws GeneralSecurityException {
/* 136 */     validatePublicKeyByteLength(kemId, publicKeyBytes);
/* 137 */     validatePublicKeyOnCurve(kemId, publicKeyBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Bytes createOutputPrefix(HpkeParameters.Variant variant, @Nullable Integer idRequirement) {
/* 142 */     if (variant == HpkeParameters.Variant.NO_PREFIX) {
/* 143 */       return OutputPrefixUtil.EMPTY_PREFIX;
/*     */     }
/* 145 */     if (idRequirement == null) {
/* 146 */       throw new IllegalStateException("idRequirement must be non-null for HpkeParameters.Variant " + variant);
/*     */     }
/*     */     
/* 149 */     if (variant == HpkeParameters.Variant.CRUNCHY) {
/* 150 */       return OutputPrefixUtil.getLegacyOutputPrefix(idRequirement.intValue());
/*     */     }
/* 152 */     if (variant == HpkeParameters.Variant.TINK) {
/* 153 */       return OutputPrefixUtil.getTinkOutputPrefix(idRequirement.intValue());
/*     */     }
/* 155 */     throw new IllegalStateException("Unknown HpkeParameters.Variant: " + variant);
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
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static HpkePublicKey create(HpkeParameters parameters, Bytes publicKeyBytes, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 175 */     validateIdRequirement(parameters.getVariant(), idRequirement);
/* 176 */     validatePublicKey(parameters.getKemId(), publicKeyBytes);
/* 177 */     Bytes prefix = createOutputPrefix(parameters.getVariant(), idRequirement);
/* 178 */     return new HpkePublicKey(parameters, publicKeyBytes, prefix, idRequirement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public Bytes getPublicKeyBytes() {
/* 187 */     return this.publicKeyBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/* 192 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public HpkeParameters getParameters() {
/* 197 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIdRequirementOrNull() {
/* 203 */     return this.idRequirement;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsKey(Key o) {
/* 208 */     if (!(o instanceof HpkePublicKey)) {
/* 209 */       return false;
/*     */     }
/* 211 */     HpkePublicKey other = (HpkePublicKey)o;
/*     */     
/* 213 */     return (this.parameters.equals(other.parameters) && this.publicKeyBytes
/* 214 */       .equals(other.publicKeyBytes) && 
/* 215 */       Objects.equals(this.idRequirement, other.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HpkePublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */