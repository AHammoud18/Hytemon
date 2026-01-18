/*    */ package com.google.crypto.tink.daead;
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
/*    */ public final class PredefinedDeterministicAeadParameters
/*    */ {
/* 29 */   public static final AesSivParameters AES256_SIV = (AesSivParameters)TinkBugException.exceptionIsBug(() -> AesSivParameters.builder().setKeySizeBytes(64).setVariant(AesSivParameters.Variant.TINK).build());
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\daead\PredefinedDeterministicAeadParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */