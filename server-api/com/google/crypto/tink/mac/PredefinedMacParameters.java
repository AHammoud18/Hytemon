/*     */ package com.google.crypto.tink.mac;
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
/*     */ public final class PredefinedMacParameters
/*     */ {
/*  39 */   public static final HmacParameters HMAC_SHA256_128BITTAG = (HmacParameters)TinkBugException.exceptionIsBug(() -> HmacParameters.builder().setKeySizeBytes(32).setTagSizeBytes(16).setVariant(HmacParameters.Variant.TINK).setHashType(HmacParameters.HashType.SHA256).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static final HmacParameters HMAC_SHA256_256BITTAG = (HmacParameters)TinkBugException.exceptionIsBug(() -> HmacParameters.builder().setKeySizeBytes(32).setTagSizeBytes(32).setVariant(HmacParameters.Variant.TINK).setHashType(HmacParameters.HashType.SHA256).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static final HmacParameters HMAC_SHA512_256BITTAG = (HmacParameters)TinkBugException.exceptionIsBug(() -> HmacParameters.builder().setKeySizeBytes(64).setTagSizeBytes(32).setVariant(HmacParameters.Variant.TINK).setHashType(HmacParameters.HashType.SHA512).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static final HmacParameters HMAC_SHA512_512BITTAG = (HmacParameters)TinkBugException.exceptionIsBug(() -> HmacParameters.builder().setKeySizeBytes(64).setTagSizeBytes(64).setVariant(HmacParameters.Variant.TINK).setHashType(HmacParameters.HashType.SHA512).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public static final AesCmacParameters AES_CMAC = (AesCmacParameters)TinkBugException.exceptionIsBug(() -> AesCmacParameters.builder().setKeySizeBytes(32).setTagSizeBytes(16).setVariant(AesCmacParameters.Variant.TINK).build());
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\PredefinedMacParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */