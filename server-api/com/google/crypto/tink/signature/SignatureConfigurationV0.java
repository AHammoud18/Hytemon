/*    */ package com.google.crypto.tink.signature;
/*    */ 
/*    */ import com.google.crypto.tink.Configuration;
/*    */ import com.google.crypto.tink.PublicKeySign;
/*    */ import com.google.crypto.tink.PublicKeyVerify;
/*    */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*    */ import com.google.crypto.tink.internal.InternalConfiguration;
/*    */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*    */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*    */ import com.google.crypto.tink.subtle.EcdsaSignJce;
/*    */ import com.google.crypto.tink.subtle.EcdsaVerifyJce;
/*    */ import com.google.crypto.tink.subtle.Ed25519Sign;
/*    */ import com.google.crypto.tink.subtle.Ed25519Verify;
/*    */ import com.google.crypto.tink.subtle.RsaSsaPkcs1SignJce;
/*    */ import com.google.crypto.tink.subtle.RsaSsaPkcs1VerifyJce;
/*    */ import com.google.crypto.tink.subtle.RsaSsaPssSignJce;
/*    */ import com.google.crypto.tink.subtle.RsaSsaPssVerifyJce;
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
/*    */ class SignatureConfigurationV0
/*    */ {
/* 49 */   private static final InternalConfiguration INTERNAL_CONFIGURATION = create();
/*    */   
/*    */   private static InternalConfiguration create() {
/*    */     try {
/* 53 */       PrimitiveRegistry.Builder builder = PrimitiveRegistry.builder();
/*    */ 
/*    */       
/* 56 */       PublicKeySignWrapper.registerToInternalPrimitiveRegistry(builder);
/* 57 */       PublicKeyVerifyWrapper.registerToInternalPrimitiveRegistry(builder);
/* 58 */       builder.registerPrimitiveConstructor(
/* 59 */           PrimitiveConstructor.create(EcdsaSignJce::create, EcdsaPrivateKey.class, PublicKeySign.class));
/*    */       
/* 61 */       builder.registerPrimitiveConstructor(
/* 62 */           PrimitiveConstructor.create(EcdsaVerifyJce::create, EcdsaPublicKey.class, PublicKeyVerify.class));
/*    */       
/* 64 */       builder.registerPrimitiveConstructor(
/* 65 */           PrimitiveConstructor.create(RsaSsaPssSignJce::create, RsaSsaPssPrivateKey.class, PublicKeySign.class));
/*    */       
/* 67 */       builder.registerPrimitiveConstructor(
/* 68 */           PrimitiveConstructor.create(RsaSsaPssVerifyJce::create, RsaSsaPssPublicKey.class, PublicKeyVerify.class));
/*    */       
/* 70 */       builder.registerPrimitiveConstructor(
/* 71 */           PrimitiveConstructor.create(RsaSsaPkcs1SignJce::create, RsaSsaPkcs1PrivateKey.class, PublicKeySign.class));
/*    */       
/* 73 */       builder.registerPrimitiveConstructor(
/* 74 */           PrimitiveConstructor.create(RsaSsaPkcs1VerifyJce::create, RsaSsaPkcs1PublicKey.class, PublicKeyVerify.class));
/*    */       
/* 76 */       builder.registerPrimitiveConstructor(
/* 77 */           PrimitiveConstructor.create(Ed25519Sign::create, Ed25519PrivateKey.class, PublicKeySign.class));
/*    */       
/* 79 */       builder.registerPrimitiveConstructor(
/* 80 */           PrimitiveConstructor.create(Ed25519Verify::create, Ed25519PublicKey.class, PublicKeyVerify.class));
/*    */ 
/*    */       
/* 83 */       return InternalConfiguration.createFromPrimitiveRegistry(builder
/* 84 */           .allowReparsingLegacyKeys().build());
/* 85 */     } catch (GeneralSecurityException e) {
/* 86 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static Configuration get() throws GeneralSecurityException {
/* 92 */     if (TinkFipsUtil.useOnlyFips()) {
/* 93 */       throw new GeneralSecurityException("Cannot use non-FIPS-compliant SignatureConfigurationV0 in FIPS mode");
/*    */     }
/*    */     
/* 96 */     return (Configuration)INTERNAL_CONFIGURATION;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\SignatureConfigurationV0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */