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
/*    */ class PrfConfigurationV0
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
/* 53 */           PrimitiveConstructor.create(PrfConfigurationV0::createHkdfPrf, HkdfPrfKey.class, Prf.class));
/*    */       
/* 55 */       builder.registerPrimitiveConstructor(
/* 56 */           PrimitiveConstructor.create(PrfConfigurationV0::createAesCmacPrf, AesCmacPrfKey.class, Prf.class));
/*    */ 
/*    */       
/* 59 */       return InternalConfiguration.createFromPrimitiveRegistry(builder
/* 60 */           .allowReparsingLegacyKeys().build());
/* 61 */     } catch (GeneralSecurityException e) {
/* 62 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */   private static final int MIN_HKDF_PRF_KEY_SIZE = 32;
/*    */   
/*    */   public static Configuration get() throws GeneralSecurityException {
/* 68 */     if (TinkFipsUtil.useOnlyFips()) {
/* 69 */       throw new GeneralSecurityException("Cannot use non-FIPS-compliant PrfConfigurationV0 in FIPS mode");
/*    */     }
/*    */     
/* 72 */     return (Configuration)INTERNAL_CONFIGURATION;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Prf createHkdfPrf(HkdfPrfKey key) throws GeneralSecurityException {
/* 81 */     if (key.getParameters().getKeySizeBytes() < 32) {
/* 82 */       throw new GeneralSecurityException("HkdfPrf key size must be at least 32");
/*    */     }
/*    */     
/* 85 */     if (key.getParameters().getHashType() != HkdfPrfParameters.HashType.SHA256 && key
/* 86 */       .getParameters().getHashType() != HkdfPrfParameters.HashType.SHA512) {
/* 87 */       throw new GeneralSecurityException("HkdfPrf hash type must be SHA256 or SHA512");
/*    */     }
/* 89 */     return (Prf)PrfImpl.wrap(HkdfStreamingPrf.create(key));
/*    */   }
/*    */   
/*    */   private static Prf createAesCmacPrf(AesCmacPrfKey key) throws GeneralSecurityException {
/* 93 */     if (key.getParameters().getKeySizeBytes() != 32) {
/* 94 */       throw new GeneralSecurityException("AesCmacPrf key size must be 32 bytes");
/*    */     }
/* 96 */     return PrfAesCmac.create(key);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\PrfConfigurationV0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */