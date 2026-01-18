/*     */ package com.google.crypto.tink.hybrid.internal;
/*     */ 
/*     */ import com.google.crypto.tink.hybrid.HpkeParameters;
/*     */ import com.google.crypto.tink.subtle.EllipticCurves;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HpkePrimitiveFactory
/*     */ {
/*     */   public static HpkeKem createKem(byte[] kemId) throws GeneralSecurityException {
/*  31 */     if (Arrays.equals(kemId, HpkeUtil.X25519_HKDF_SHA256_KEM_ID))
/*  32 */       return new X25519HpkeKem(new HkdfHpkeKdf("HmacSha256")); 
/*  33 */     if (Arrays.equals(kemId, HpkeUtil.P256_HKDF_SHA256_KEM_ID))
/*  34 */       return NistCurvesHpkeKem.fromCurve(EllipticCurves.CurveType.NIST_P256); 
/*  35 */     if (Arrays.equals(kemId, HpkeUtil.P384_HKDF_SHA384_KEM_ID))
/*  36 */       return NistCurvesHpkeKem.fromCurve(EllipticCurves.CurveType.NIST_P384); 
/*  37 */     if (Arrays.equals(kemId, HpkeUtil.P521_HKDF_SHA512_KEM_ID)) {
/*  38 */       return NistCurvesHpkeKem.fromCurve(EllipticCurves.CurveType.NIST_P521);
/*     */     }
/*  40 */     throw new IllegalArgumentException("Unrecognized HPKE KEM identifier");
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkeKem createKem(HpkeParameters.KemId kemId) throws GeneralSecurityException {
/*  45 */     if (kemId == HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256)
/*  46 */       return new X25519HpkeKem(new HkdfHpkeKdf("HmacSha256")); 
/*  47 */     if (kemId == HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256)
/*  48 */       return NistCurvesHpkeKem.fromCurve(EllipticCurves.CurveType.NIST_P256); 
/*  49 */     if (kemId == HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384)
/*  50 */       return NistCurvesHpkeKem.fromCurve(EllipticCurves.CurveType.NIST_P384); 
/*  51 */     if (kemId == HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512) {
/*  52 */       return NistCurvesHpkeKem.fromCurve(EllipticCurves.CurveType.NIST_P521);
/*     */     }
/*  54 */     throw new IllegalArgumentException("Unrecognized HPKE KEM identifier");
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkeKdf createKdf(byte[] kdfId) {
/*  59 */     if (Arrays.equals(kdfId, HpkeUtil.HKDF_SHA256_KDF_ID))
/*  60 */       return new HkdfHpkeKdf("HmacSha256"); 
/*  61 */     if (Arrays.equals(kdfId, HpkeUtil.HKDF_SHA384_KDF_ID))
/*  62 */       return new HkdfHpkeKdf("HmacSha384"); 
/*  63 */     if (Arrays.equals(kdfId, HpkeUtil.HKDF_SHA512_KDF_ID)) {
/*  64 */       return new HkdfHpkeKdf("HmacSha512");
/*     */     }
/*  66 */     throw new IllegalArgumentException("Unrecognized HPKE KDF identifier");
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkeKdf createKdf(HpkeParameters.KdfId kdfId) {
/*  71 */     if (kdfId == HpkeParameters.KdfId.HKDF_SHA256)
/*  72 */       return new HkdfHpkeKdf("HmacSha256"); 
/*  73 */     if (kdfId == HpkeParameters.KdfId.HKDF_SHA384)
/*  74 */       return new HkdfHpkeKdf("HmacSha384"); 
/*  75 */     if (kdfId == HpkeParameters.KdfId.HKDF_SHA512) {
/*  76 */       return new HkdfHpkeKdf("HmacSha512");
/*     */     }
/*  78 */     throw new IllegalArgumentException("Unrecognized HPKE KDF identifier");
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkeAead createAead(byte[] aeadId) throws GeneralSecurityException {
/*  83 */     if (Arrays.equals(aeadId, HpkeUtil.AES_128_GCM_AEAD_ID))
/*  84 */       return new AesGcmHpkeAead(16); 
/*  85 */     if (Arrays.equals(aeadId, HpkeUtil.AES_256_GCM_AEAD_ID))
/*  86 */       return new AesGcmHpkeAead(32); 
/*  87 */     if (Arrays.equals(aeadId, HpkeUtil.CHACHA20_POLY1305_AEAD_ID)) {
/*  88 */       return new ChaCha20Poly1305HpkeAead();
/*     */     }
/*  90 */     throw new IllegalArgumentException("Unrecognized HPKE AEAD identifier");
/*     */   }
/*     */ 
/*     */   
/*     */   public static HpkeAead createAead(HpkeParameters.AeadId aeadId) throws GeneralSecurityException {
/*  95 */     if (aeadId == HpkeParameters.AeadId.AES_128_GCM)
/*  96 */       return new AesGcmHpkeAead(16); 
/*  97 */     if (aeadId == HpkeParameters.AeadId.AES_256_GCM)
/*  98 */       return new AesGcmHpkeAead(32); 
/*  99 */     if (aeadId == HpkeParameters.AeadId.CHACHA20_POLY1305) {
/* 100 */       return new ChaCha20Poly1305HpkeAead();
/*     */     }
/* 102 */     throw new IllegalArgumentException("Unrecognized HPKE AEAD identifier");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\internal\HpkePrimitiveFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */