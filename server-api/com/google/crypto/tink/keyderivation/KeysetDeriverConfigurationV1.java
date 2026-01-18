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
/*    */ class KeysetDeriverConfigurationV1
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
/* 56 */           PrimitiveConstructor.create(KeysetDeriverConfigurationV1::createHkdfPrfBasedKeyDeriver, PrfBasedKeyDerivationKey.class, KeyDeriver.class));
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 61 */       return InternalConfiguration.createFromPrimitiveRegistry(builder.build());
/* 62 */     } catch (GeneralSecurityException e) {
/* 63 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static PrimitiveRegistry createPrfRegistry() {
/*    */     try {
/* 69 */       return PrimitiveRegistry.builder()
/* 70 */         .registerPrimitiveConstructor(
/* 71 */           PrimitiveConstructor.create(HkdfStreamingPrf::create, HkdfPrfKey.class, StreamingPrf.class))
/*    */         
/* 73 */         .build();
/* 74 */     } catch (GeneralSecurityException e) {
/* 75 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static Configuration get() throws GeneralSecurityException {
/* 80 */     if (TinkFipsUtil.useOnlyFips()) {
/* 81 */       throw new GeneralSecurityException("Cannot use non-FIPS-compliant KeysetDeriverConfigurationV1 in FIPS mode");
/*    */     }
/*    */     
/* 84 */     return (Configuration)INTERNAL_CONFIGURATION;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static KeyDeriver createHkdfPrfBasedKeyDeriver(PrfBasedKeyDerivationKey key) throws GeneralSecurityException {
/* 91 */     KeyDeriver deriver = PrfBasedKeyDeriver.createWithPrfPrimitiveRegistry(PRF_REGISTRY, key);
/* 92 */     Object unused = deriver.deriveKey(new byte[] { 1 });
/* 93 */     return deriver;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\keyderivation\KeysetDeriverConfigurationV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */