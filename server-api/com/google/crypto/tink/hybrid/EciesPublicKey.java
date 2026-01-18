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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public final class EciesPublicKey
/*     */   extends HybridPublicKey
/*     */ {
/*     */   private final EciesParameters parameters;
/*     */   @Nullable
/*     */   private final ECPoint nistPublicPoint;
/*     */   @Nullable
/*     */   private final Bytes x25519PublicPointBytes;
/*     */   private final Bytes outputPrefix;
/*     */   @Nullable
/*     */   private final Integer idRequirement;
/*     */   
/*     */   private EciesPublicKey(EciesParameters parameters, @Nullable ECPoint nistPublicPoint, @Nullable Bytes x25519PublicPointBytes, Bytes outputPrefix, @Nullable Integer idRequirement) {
/*  55 */     this.parameters = parameters;
/*  56 */     this.nistPublicPoint = nistPublicPoint;
/*  57 */     this.x25519PublicPointBytes = x25519PublicPointBytes;
/*  58 */     this.outputPrefix = outputPrefix;
/*  59 */     this.idRequirement = idRequirement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateIdRequirement(EciesParameters.Variant variant, @Nullable Integer idRequirement) throws GeneralSecurityException {
/*  65 */     if (!variant.equals(EciesParameters.Variant.NO_PREFIX) && idRequirement == null) {
/*  66 */       throw new GeneralSecurityException("'idRequirement' must be non-null for " + variant + " variant.");
/*     */     }
/*     */     
/*  69 */     if (variant.equals(EciesParameters.Variant.NO_PREFIX) && idRequirement != null) {
/*  70 */       throw new GeneralSecurityException("'idRequirement' must be null for NO_PREFIX variant.");
/*     */     }
/*     */   }
/*     */   
/*     */   private static EllipticCurve getParameterSpecNistCurve(EciesParameters.CurveType curveType) {
/*  75 */     if (curveType == EciesParameters.CurveType.NIST_P256) {
/*  76 */       return EllipticCurves.getNistP256Params().getCurve();
/*     */     }
/*  78 */     if (curveType == EciesParameters.CurveType.NIST_P384) {
/*  79 */       return EllipticCurves.getNistP384Params().getCurve();
/*     */     }
/*  81 */     if (curveType == EciesParameters.CurveType.NIST_P521) {
/*  82 */       return EllipticCurves.getNistP521Params().getCurve();
/*     */     }
/*  84 */     throw new IllegalArgumentException("Unable to determine NIST curve type for " + curveType);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Bytes createOutputPrefix(EciesParameters.Variant variant, @Nullable Integer idRequirement) {
/*  89 */     if (variant == EciesParameters.Variant.NO_PREFIX) {
/*  90 */       return OutputPrefixUtil.EMPTY_PREFIX;
/*     */     }
/*  92 */     if (idRequirement == null) {
/*  93 */       throw new IllegalStateException("idRequirement must be non-null for EciesParameters.Variant: " + variant);
/*     */     }
/*     */     
/*  96 */     if (variant == EciesParameters.Variant.CRUNCHY) {
/*  97 */       return OutputPrefixUtil.getLegacyOutputPrefix(idRequirement.intValue());
/*     */     }
/*  99 */     if (variant == EciesParameters.Variant.TINK) {
/* 100 */       return OutputPrefixUtil.getTinkOutputPrefix(idRequirement.intValue());
/*     */     }
/* 102 */     throw new IllegalStateException("Unknown EciesParameters.Variant: " + variant);
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
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public static EciesPublicKey createForCurveX25519(EciesParameters parameters, Bytes publicPointBytes, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 121 */     if (!parameters.getCurveType().equals(EciesParameters.CurveType.X25519)) {
/* 122 */       throw new GeneralSecurityException("createForCurveX25519 may only be called with parameters for curve X25519");
/*     */     }
/*     */     
/* 125 */     validateIdRequirement(parameters.getVariant(), idRequirement);
/* 126 */     if (publicPointBytes.size() != 32) {
/* 127 */       throw new GeneralSecurityException("Encoded public point byte length for X25519 curve must be 32");
/*     */     }
/*     */ 
/*     */     
/* 131 */     Bytes prefix = createOutputPrefix(parameters.getVariant(), idRequirement);
/*     */     
/* 133 */     return new EciesPublicKey(parameters, null, publicPointBytes, prefix, idRequirement);
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
/*     */   public static EciesPublicKey createForNistCurve(EciesParameters parameters, ECPoint publicPoint, @Nullable Integer idRequirement) throws GeneralSecurityException {
/* 147 */     if (parameters.getCurveType().equals(EciesParameters.CurveType.X25519)) {
/* 148 */       throw new GeneralSecurityException("createForNistCurve may only be called with parameters for NIST curve");
/*     */     }
/*     */     
/* 151 */     validateIdRequirement(parameters.getVariant(), idRequirement);
/* 152 */     EllipticCurvesUtil.checkPointOnCurve(publicPoint, 
/* 153 */         getParameterSpecNistCurve(parameters.getCurveType()));
/*     */     
/* 155 */     Bytes prefix = createOutputPrefix(parameters.getVariant(), idRequirement);
/*     */     
/* 157 */     return new EciesPublicKey(parameters, publicPoint, null, prefix, idRequirement);
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
/*     */   @Nullable
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public ECPoint getNistCurvePoint() {
/* 172 */     return this.nistPublicPoint;
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
/*     */   @Nullable
/*     */   @RestrictedApi(explanation = "Accessing parts of keys can produce unexpected incompatibilities, annotate the function with @AccessesPartialKey", link = "https://developers.google.com/tink/design/access_control#accessing_partial_keys", allowedOnPath = ".*Test\\.java", allowlistAnnotations = {AccessesPartialKey.class})
/*     */   public Bytes getX25519CurvePointBytes() {
/* 187 */     return this.x25519PublicPointBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bytes getOutputPrefix() {
/* 192 */     return this.outputPrefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public EciesParameters getParameters() {
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
/* 208 */     if (!(o instanceof EciesPublicKey)) {
/* 209 */       return false;
/*     */     }
/* 211 */     EciesPublicKey other = (EciesPublicKey)o;
/*     */     
/* 213 */     return (this.parameters.equals(other.parameters) && 
/* 214 */       Objects.equals(this.x25519PublicPointBytes, other.x25519PublicPointBytes) && 
/* 215 */       Objects.equals(this.nistPublicPoint, other.nistPublicPoint) && 
/* 216 */       Objects.equals(this.idRequirement, other.idRequirement));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\EciesPublicKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */