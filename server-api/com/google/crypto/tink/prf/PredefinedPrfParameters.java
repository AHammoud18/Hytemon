/*    */ package com.google.crypto.tink.prf;
/*    */ 
/*    */ import com.google.crypto.tink.internal.TinkBugException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PredefinedPrfParameters
/*    */ {
/* 41 */   public static final HkdfPrfParameters HKDF_SHA256 = (HkdfPrfParameters)TinkBugException.exceptionIsBug(() -> HkdfPrfParameters.builder().setKeySizeBytes(32).setHashType(HkdfPrfParameters.HashType.SHA256).build());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 58 */   public static final HmacPrfParameters HMAC_SHA256_PRF = (HmacPrfParameters)TinkBugException.exceptionIsBug(() -> HmacPrfParameters.builder().setKeySizeBytes(32).setHashType(HmacPrfParameters.HashType.SHA256).build());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 75 */   public static final HmacPrfParameters HMAC_SHA512_PRF = (HmacPrfParameters)TinkBugException.exceptionIsBug(() -> HmacPrfParameters.builder().setKeySizeBytes(64).setHashType(HmacPrfParameters.HashType.SHA512).build());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 91 */   public static final AesCmacPrfParameters AES_CMAC_PRF = (AesCmacPrfParameters)TinkBugException.exceptionIsBug(() -> AesCmacPrfParameters.create(32));
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\PredefinedPrfParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */