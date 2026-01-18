/*    */ package com.google.crypto.tink.streamingaead;
/*    */ 
/*    */ import com.google.crypto.tink.Configuration;
/*    */ import com.google.crypto.tink.StreamingAead;
/*    */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*    */ import com.google.crypto.tink.internal.InternalConfiguration;
/*    */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*    */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*    */ import com.google.crypto.tink.subtle.AesCtrHmacStreaming;
/*    */ import com.google.crypto.tink.subtle.AesGcmHkdfStreaming;
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
/*    */ class StreamingAeadConfigurationV0
/*    */ {
/* 40 */   private static final InternalConfiguration INTERNAL_CONFIGURATION = create();
/*    */   
/*    */   private static InternalConfiguration create() {
/*    */     try {
/* 44 */       PrimitiveRegistry.Builder builder = PrimitiveRegistry.builder();
/*    */ 
/*    */       
/* 47 */       StreamingAeadWrapper.registerToInternalPrimitiveRegistry(builder);
/* 48 */       builder.registerPrimitiveConstructor(
/* 49 */           PrimitiveConstructor.create(AesGcmHkdfStreaming::create, AesGcmHkdfStreamingKey.class, StreamingAead.class));
/*    */       
/* 51 */       builder.registerPrimitiveConstructor(
/* 52 */           PrimitiveConstructor.create(AesCtrHmacStreaming::create, AesCtrHmacStreamingKey.class, StreamingAead.class));
/*    */ 
/*    */       
/* 55 */       return InternalConfiguration.createFromPrimitiveRegistry(builder
/* 56 */           .allowReparsingLegacyKeys().build());
/* 57 */     } catch (GeneralSecurityException e) {
/* 58 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static Configuration get() throws GeneralSecurityException {
/* 64 */     if (TinkFipsUtil.useOnlyFips()) {
/* 65 */       throw new GeneralSecurityException("Cannot use non-FIPS-compliant StreamingAead in FIPS mode");
/*    */     }
/*    */     
/* 68 */     return (Configuration)INTERNAL_CONFIGURATION;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\StreamingAeadConfigurationV0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */