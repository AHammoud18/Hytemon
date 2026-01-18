/*     */ package com.google.crypto.tink.hybrid;
/*     */ 
/*     */ import com.google.crypto.tink.Parameters;
/*     */ import com.google.crypto.tink.aead.AesCtrHmacAeadParameters;
/*     */ import com.google.crypto.tink.aead.AesGcmParameters;
/*     */ import com.google.crypto.tink.internal.TinkBugException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PredefinedHybridParameters
/*     */ {
/*  46 */   public static final EciesParameters ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM = (EciesParameters)TinkBugException.exceptionIsBug(() -> EciesParameters.builder().setCurveType(EciesParameters.CurveType.NIST_P256).setHashType(EciesParameters.HashType.SHA256).setNistCurvePointFormat(EciesParameters.PointFormat.UNCOMPRESSED).setVariant(EciesParameters.Variant.TINK).setDemParameters((Parameters)AesGcmParameters.builder().setIvSizeBytes(12).setKeySizeBytes(16).setTagSizeBytes(16).setVariant(AesGcmParameters.Variant.NO_PREFIX).build()).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final EciesParameters ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM_COMPRESSED_WITHOUT_PREFIX = (EciesParameters)TinkBugException.exceptionIsBug(() -> EciesParameters.builder().setCurveType(EciesParameters.CurveType.NIST_P256).setHashType(EciesParameters.HashType.SHA256).setNistCurvePointFormat(EciesParameters.PointFormat.COMPRESSED).setVariant(EciesParameters.Variant.NO_PREFIX).setDemParameters((Parameters)AesGcmParameters.builder().setIvSizeBytes(12).setKeySizeBytes(16).setTagSizeBytes(16).setVariant(AesGcmParameters.Variant.NO_PREFIX).build()).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   public static final EciesParameters ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256 = (EciesParameters)TinkBugException.exceptionIsBug(() -> EciesParameters.builder().setCurveType(EciesParameters.CurveType.NIST_P256).setHashType(EciesParameters.HashType.SHA256).setNistCurvePointFormat(EciesParameters.PointFormat.UNCOMPRESSED).setVariant(EciesParameters.Variant.TINK).setDemParameters((Parameters)AesCtrHmacAeadParameters.builder().setAesKeySizeBytes(16).setHmacKeySizeBytes(32).setTagSizeBytes(16).setIvSizeBytes(16).setHashType(AesCtrHmacAeadParameters.HashType.SHA256).setVariant(AesCtrHmacAeadParameters.Variant.NO_PREFIX).build()).build());
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\PredefinedHybridParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */