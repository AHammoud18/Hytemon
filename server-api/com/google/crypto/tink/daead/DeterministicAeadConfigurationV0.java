/*    */ package com.google.crypto.tink.daead;
/*    */ 
/*    */ import com.google.crypto.tink.Configuration;
/*    */ import com.google.crypto.tink.DeterministicAead;
/*    */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*    */ import com.google.crypto.tink.internal.InternalConfiguration;
/*    */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*    */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*    */ import com.google.crypto.tink.subtle.AesSiv;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.InvalidAlgorithmParameterException;
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
/*    */ class DeterministicAeadConfigurationV0
/*    */ {
/* 39 */   private static final InternalConfiguration INTERNAL_CONFIGURATION = create();
/*    */   
/*    */   private static InternalConfiguration create() {
/*    */     try {
/* 43 */       PrimitiveRegistry.Builder builder = PrimitiveRegistry.builder();
/*    */ 
/*    */       
/* 46 */       DeterministicAeadWrapper.registerToInternalPrimitiveRegistry(builder);
/* 47 */       builder.registerPrimitiveConstructor(
/* 48 */           PrimitiveConstructor.create(DeterministicAeadConfigurationV0::createAesSiv, AesSivKey.class, DeterministicAead.class));
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 53 */       return InternalConfiguration.createFromPrimitiveRegistry(builder
/* 54 */           .allowReparsingLegacyKeys().build());
/* 55 */     } catch (GeneralSecurityException e) {
/* 56 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */   private static final int KEY_SIZE_IN_BYTES = 64;
/*    */   public static Configuration get() throws GeneralSecurityException {
/* 61 */     if (TinkFipsUtil.useOnlyFips()) {
/* 62 */       throw new GeneralSecurityException("Cannot use non-FIPS-compliant DeterministicAeadConfigurationV0 in FIPS mode");
/*    */     }
/*    */     
/* 65 */     return (Configuration)INTERNAL_CONFIGURATION;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static DeterministicAead createAesSiv(AesSivKey key) throws GeneralSecurityException {
/* 74 */     if (key.getParameters().getKeySizeBytes() != 64) {
/* 75 */       throw new InvalidAlgorithmParameterException("invalid key size: " + key
/*    */           
/* 77 */           .getParameters().getKeySizeBytes() + ". Valid keys must have " + '@' + " bytes.");
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 82 */     return (DeterministicAead)AesSiv.create(key);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\daead\DeterministicAeadConfigurationV0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */