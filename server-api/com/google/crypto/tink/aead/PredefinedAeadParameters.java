/*     */ package com.google.crypto.tink.aead;
/*     */ 
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
/*     */ public final class PredefinedAeadParameters
/*     */ {
/*  42 */   public static final AesGcmParameters AES128_GCM = (AesGcmParameters)TinkBugException.exceptionIsBug(() -> AesGcmParameters.builder().setIvSizeBytes(12).setKeySizeBytes(16).setTagSizeBytes(16).setVariant(AesGcmParameters.Variant.TINK).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static final AesGcmParameters AES256_GCM = (AesGcmParameters)TinkBugException.exceptionIsBug(() -> AesGcmParameters.builder().setIvSizeBytes(12).setKeySizeBytes(32).setTagSizeBytes(16).setVariant(AesGcmParameters.Variant.TINK).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public static final AesEaxParameters AES128_EAX = (AesEaxParameters)TinkBugException.exceptionIsBug(() -> AesEaxParameters.builder().setIvSizeBytes(16).setKeySizeBytes(16).setTagSizeBytes(16).setVariant(AesEaxParameters.Variant.TINK).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static final AesEaxParameters AES256_EAX = (AesEaxParameters)TinkBugException.exceptionIsBug(() -> AesEaxParameters.builder().setIvSizeBytes(16).setKeySizeBytes(32).setTagSizeBytes(16).setVariant(AesEaxParameters.Variant.TINK).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public static final AesCtrHmacAeadParameters AES128_CTR_HMAC_SHA256 = (AesCtrHmacAeadParameters)TinkBugException.exceptionIsBug(() -> AesCtrHmacAeadParameters.builder().setAesKeySizeBytes(16).setHmacKeySizeBytes(32).setTagSizeBytes(16).setIvSizeBytes(16).setHashType(AesCtrHmacAeadParameters.HashType.SHA256).setVariant(AesCtrHmacAeadParameters.Variant.TINK).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   public static final AesCtrHmacAeadParameters AES256_CTR_HMAC_SHA256 = (AesCtrHmacAeadParameters)TinkBugException.exceptionIsBug(() -> AesCtrHmacAeadParameters.builder().setAesKeySizeBytes(32).setHmacKeySizeBytes(32).setTagSizeBytes(32).setIvSizeBytes(16).setHashType(AesCtrHmacAeadParameters.HashType.SHA256).setVariant(AesCtrHmacAeadParameters.Variant.TINK).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 164 */   public static final ChaCha20Poly1305Parameters CHACHA20_POLY1305 = ChaCha20Poly1305Parameters.create(ChaCha20Poly1305Parameters.Variant.TINK);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 171 */   public static final XChaCha20Poly1305Parameters XCHACHA20_POLY1305 = XChaCha20Poly1305Parameters.create(XChaCha20Poly1305Parameters.Variant.TINK);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 187 */   public static final XAesGcmParameters XAES_256_GCM_192_BIT_NONCE = (XAesGcmParameters)TinkBugException.exceptionIsBug(() -> XAesGcmParameters.create(XAesGcmParameters.Variant.TINK, 12));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 204 */   public static final XAesGcmParameters XAES_256_GCM_192_BIT_NONCE_NO_PREFIX = (XAesGcmParameters)TinkBugException.exceptionIsBug(() -> XAesGcmParameters.create(XAesGcmParameters.Variant.NO_PREFIX, 12));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 225 */   public static final XAesGcmParameters XAES_256_GCM_160_BIT_NONCE_NO_PREFIX = (XAesGcmParameters)TinkBugException.exceptionIsBug(() -> XAesGcmParameters.create(XAesGcmParameters.Variant.NO_PREFIX, 8));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/* 234 */   public static final XAesGcmParameters X_AES_GCM_8_BYTE_SALT_NO_PREFIX = XAES_256_GCM_160_BIT_NONCE_NO_PREFIX;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\PredefinedAeadParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */