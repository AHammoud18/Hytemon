/*    */ package com.google.crypto.tink.hybrid;
/*    */ 
/*    */ import com.google.crypto.tink.Configuration;
/*    */ import com.google.crypto.tink.HybridDecrypt;
/*    */ import com.google.crypto.tink.HybridEncrypt;
/*    */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*    */ import com.google.crypto.tink.hybrid.internal.HpkeDecrypt;
/*    */ import com.google.crypto.tink.hybrid.internal.HpkeEncrypt;
/*    */ import com.google.crypto.tink.internal.InternalConfiguration;
/*    */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*    */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*    */ import com.google.crypto.tink.subtle.EciesAeadHkdfHybridDecrypt;
/*    */ import com.google.crypto.tink.subtle.EciesAeadHkdfHybridEncrypt;
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
/*    */ class HybridConfigurationV0
/*    */ {
/* 43 */   private static final InternalConfiguration INTERNAL_CONFIGURATION = create();
/*    */   
/*    */   private static InternalConfiguration create() {
/*    */     try {
/* 47 */       PrimitiveRegistry.Builder builder = PrimitiveRegistry.builder();
/*    */ 
/*    */       
/* 50 */       HybridEncryptWrapper.registerToInternalPrimitiveRegistry(builder);
/* 51 */       builder.registerPrimitiveConstructor(
/* 52 */           PrimitiveConstructor.create(EciesAeadHkdfHybridEncrypt::create, EciesPublicKey.class, HybridEncrypt.class));
/*    */       
/* 54 */       builder.registerPrimitiveConstructor(
/* 55 */           PrimitiveConstructor.create(HpkeEncrypt::create, HpkePublicKey.class, HybridEncrypt.class));
/*    */ 
/*    */ 
/*    */       
/* 59 */       HybridDecryptWrapper.registerToInternalPrimitiveRegistry(builder);
/* 60 */       builder.registerPrimitiveConstructor(
/* 61 */           PrimitiveConstructor.create(EciesAeadHkdfHybridDecrypt::create, EciesPrivateKey.class, HybridDecrypt.class));
/*    */       
/* 63 */       builder.registerPrimitiveConstructor(
/* 64 */           PrimitiveConstructor.create(HpkeDecrypt::create, HpkePrivateKey.class, HybridDecrypt.class));
/*    */ 
/*    */       
/* 67 */       return InternalConfiguration.createFromPrimitiveRegistry(builder
/* 68 */           .allowReparsingLegacyKeys().build());
/* 69 */     } catch (GeneralSecurityException e) {
/* 70 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static Configuration get() throws GeneralSecurityException {
/* 76 */     if (TinkFipsUtil.useOnlyFips()) {
/* 77 */       throw new GeneralSecurityException("Cannot use non-FIPS-compliant HybridConfigurationV0 in FIPS mode");
/*    */     }
/*    */     
/* 80 */     return (Configuration)INTERNAL_CONFIGURATION;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HybridConfigurationV0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */