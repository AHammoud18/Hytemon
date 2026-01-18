/*    */ package com.google.crypto.tink.keyderivation;
/*    */ 
/*    */ import com.google.crypto.tink.Configuration;
/*    */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*    */ import com.google.crypto.tink.internal.InternalConfiguration;
/*    */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*    */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*    */ import com.google.crypto.tink.keyderivation.internal.KeyDeriver;
/*    */ import com.google.crypto.tink.keyderivation.internal.KeysetDeriverWrapper;
/*    */ import com.google.crypto.tink.keyderivation.internal.PrfBasedKeyDeriver;
/*    */ import com.google.crypto.tink.prf.HkdfPrfKey;
/*    */ import com.google.crypto.tink.subtle.prf.HkdfStreamingPrf;
/*    */ import com.google.crypto.tink.subtle.prf.StreamingPrf;
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
/*    */ 
/*    */ class KeysetDeriverConfigurationV0
/*    */ {
/* 45 */   private static final InternalConfiguration INTERNAL_CONFIGURATION = create();
/* 46 */   private static final PrimitiveRegistry PRF_REGISTRY = createPrfRegistry();
/*    */   
/*    */   private static InternalConfiguration create() {
/*    */     try {
/* 50 */       PrimitiveRegistry.Builder builder = PrimitiveRegistry.builder();
/*    */       
/* 52 */       KeysetDeriverWrapper.registerToInternalPrimitiveRegistry(builder);
/*    */ 
/*    */       
/* 55 */       builder.registerPrimitiveConstructor(
/* 56 */           PrimitiveConstructor.create(KeysetDeriverConfigurationV0::createHkdfPrfBasedKeyDeriver, PrfBasedKeyDerivationKey.class, KeyDeriver.class));
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 61 */       return InternalConfiguration.createFromPrimitiveRegistry(builder
/* 62 */           .allowReparsingLegacyKeys().build());
/* 63 */     } catch (GeneralSecurityException e) {
/* 64 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static PrimitiveRegistry createPrfRegistry() {
/*    */     try {
/* 70 */       return PrimitiveRegistry.builder()
/* 71 */         .registerPrimitiveConstructor(
/* 72 */           PrimitiveConstructor.create(HkdfStreamingPrf::create, HkdfPrfKey.class, StreamingPrf.class))
/*    */         
/* 74 */         .build();
/* 75 */     } catch (GeneralSecurityException e) {
/* 76 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static Configuration get() throws GeneralSecurityException {
/* 81 */     if (TinkFipsUtil.useOnlyFips()) {
/* 82 */       throw new GeneralSecurityException("Cannot use non-FIPS-compliant KeysetDeriverConfigurationV0 in FIPS mode");
/*    */     }
/*    */     
/* 85 */     return (Configuration)INTERNAL_CONFIGURATION;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static KeyDeriver createHkdfPrfBasedKeyDeriver(PrfBasedKeyDerivationKey key) throws GeneralSecurityException {
/* 92 */     KeyDeriver deriver = PrfBasedKeyDeriver.createWithPrfPrimitiveRegistry(PRF_REGISTRY, key);
/* 93 */     Object unused = deriver.deriveKey(new byte[] { 1 });
/* 94 */     return deriver;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\keyderivation\KeysetDeriverConfigurationV0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */