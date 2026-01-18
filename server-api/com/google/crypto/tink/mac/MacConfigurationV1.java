/*    */ package com.google.crypto.tink.mac;
/*    */ 
/*    */ import com.google.crypto.tink.Configuration;
/*    */ import com.google.crypto.tink.Mac;
/*    */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*    */ import com.google.crypto.tink.internal.InternalConfiguration;
/*    */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*    */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*    */ import com.google.crypto.tink.mac.internal.ChunkedAesCmacImpl;
/*    */ import com.google.crypto.tink.subtle.PrfMac;
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
/*    */ class MacConfigurationV1
/*    */ {
/* 41 */   private static final InternalConfiguration INTERNAL_CONFIGURATION = create();
/*    */   
/*    */   private static InternalConfiguration create() {
/*    */     try {
/* 45 */       PrimitiveRegistry.Builder builder = PrimitiveRegistry.builder();
/*    */ 
/*    */       
/* 48 */       MacWrapper.registerToInternalPrimitiveRegistry(builder);
/* 49 */       ChunkedMacWrapper.registerToInternalPrimitiveRegistry(builder);
/* 50 */       builder.registerPrimitiveConstructor(
/* 51 */           PrimitiveConstructor.create(MacConfigurationV1::createAesCmac, AesCmacKey.class, Mac.class));
/*    */       
/* 53 */       builder.registerPrimitiveConstructor(
/* 54 */           PrimitiveConstructor.create(PrfMac::create, HmacKey.class, Mac.class));
/* 55 */       builder.registerPrimitiveConstructor(
/* 56 */           PrimitiveConstructor.create(MacConfigurationV1::createChunkedAesCmac, AesCmacKey.class, ChunkedMac.class));
/*    */       
/* 58 */       builder.registerPrimitiveConstructor(
/* 59 */           PrimitiveConstructor.create(com.google.crypto.tink.mac.internal.ChunkedHmacImpl::new, HmacKey.class, ChunkedMac.class));
/*    */       
/* 61 */       return InternalConfiguration.createFromPrimitiveRegistry(builder.build());
/* 62 */     } catch (GeneralSecurityException e) {
/* 63 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */   private static final int AES_CMAC_KEY_SIZE_BYTES = 32;
/*    */   public static Configuration get() throws GeneralSecurityException {
/* 68 */     if (TinkFipsUtil.useOnlyFips()) {
/* 69 */       throw new GeneralSecurityException("Cannot use non-FIPS-compliant MacConfigurationV1 in FIPS mode");
/*    */     }
/*    */     
/* 72 */     return (Configuration)INTERNAL_CONFIGURATION;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static ChunkedMac createChunkedAesCmac(AesCmacKey key) throws GeneralSecurityException {
/* 79 */     if (key.getParameters().getKeySizeBytes() != 32) {
/* 80 */       throw new GeneralSecurityException("AesCmac key size is not 32 bytes");
/*    */     }
/* 82 */     return ChunkedAesCmacImpl.create(key);
/*    */   }
/*    */   
/*    */   private static Mac createAesCmac(AesCmacKey key) throws GeneralSecurityException {
/* 86 */     if (key.getParameters().getKeySizeBytes() != 32) {
/* 87 */       throw new GeneralSecurityException("AesCmac key size is not 32 bytes");
/*    */     }
/* 89 */     return PrfMac.create(key);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\MacConfigurationV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */