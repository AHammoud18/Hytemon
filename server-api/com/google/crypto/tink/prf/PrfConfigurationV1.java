/*    */ package com.google.crypto.tink.prf;
/*    */ 
/*    */ import com.google.crypto.tink.Configuration;
/*    */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*    */ import com.google.crypto.tink.internal.InternalConfiguration;
/*    */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*    */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*    */ import com.google.crypto.tink.subtle.PrfAesCmac;
/*    */ import com.google.crypto.tink.subtle.PrfHmacJce;
/*    */ import com.google.crypto.tink.subtle.prf.HkdfStreamingPrf;
/*    */ import com.google.crypto.tink.subtle.prf.PrfImpl;
/*    */ import java.security.GeneralSecurityException;
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
/*    */ class PrfConfigurationV1
/*    */ {
/* 42 */   private static final InternalConfiguration INTERNAL_CONFIGURATION = create();
/*    */   
/*    */   private static InternalConfiguration create() {
/*    */     try {
/* 46 */       PrimitiveRegistry.Builder builder = PrimitiveRegistry.builder();
/*    */ 
/*    */       
/* 49 */       PrfSetWrapper.registerToInternalPrimitiveRegistry(builder);
/* 50 */       builder.registerPrimitiveConstructor(
/* 51 */           PrimitiveConstructor.create(PrfHmacJce::create, HmacPrfKey.class, Prf.class));
/* 52 */       builder.registerPrimitiveConstructor(
/* 53 */           PrimitiveConstructor.create(PrfConfigurationV1::createHkdfPrf, HkdfPrfKey.class, Prf.class));
/*    */       
/* 55 */       builder.registerPrimitiveConstructor(
/* 56 */           PrimitiveConstructor.create(PrfConfigurationV1::createAesCmacPrf, AesCmacPrfKey.class, Prf.class));
/*    */ 
/*    */       
/* 59 */       return InternalConfiguration.createFromPrimitiveRegistry(builder.build());
/* 60 */     } catch (GeneralSecurityException e) {
/* 61 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */   private static final int MIN_HKDF_PRF_KEY_SIZE = 32;
/*    */   
/*    */   public static Configuration get() throws GeneralSecurityException {
/* 67 */     if (TinkFipsUtil.useOnlyFips()) {
/* 68 */       throw new GeneralSecurityException("Cannot use non-FIPS-compliant PrfConfigurationV1 in FIPS mode");
/*    */     }
/*    */     
/* 71 */     return (Configuration)INTERNAL_CONFIGURATION;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Prf createHkdfPrf(HkdfPrfKey key) throws GeneralSecurityException {
/* 80 */     if (key.getParameters().getKeySizeBytes() < 32) {
/* 81 */       throw new GeneralSecurityException("HkdfPrf key size must be at least 32");
/*    */     }
/*    */     
/* 84 */     if (key.getParameters().getHashType() != HkdfPrfParameters.HashType.SHA256 && key
/* 85 */       .getParameters().getHashType() != HkdfPrfParameters.HashType.SHA512) {
/* 86 */       throw new GeneralSecurityException("HkdfPrf hash type must be SHA256 or SHA512");
/*    */     }
/* 88 */     return (Prf)PrfImpl.wrap(HkdfStreamingPrf.create(key));
/*    */   }
/*    */   
/*    */   private static Prf createAesCmacPrf(AesCmacPrfKey key) throws GeneralSecurityException {
/* 92 */     if (key.getParameters().getKeySizeBytes() != 32) {
/* 93 */       throw new GeneralSecurityException("AesCmacPrf key size must be 32 bytes");
/*    */     }
/* 95 */     return PrfAesCmac.create(key);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\PrfConfigurationV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */