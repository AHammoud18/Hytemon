/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.Configuration;
/*     */ import com.google.crypto.tink.PublicKeySign;
/*     */ import com.google.crypto.tink.PublicKeyVerify;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.InternalConfiguration;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*     */ import com.google.crypto.tink.signature.internal.MlDsaSignConscrypt;
/*     */ import com.google.crypto.tink.signature.internal.MlDsaVerifyConscrypt;
/*     */ import com.google.crypto.tink.signature.internal.SlhDsaSignConscrypt;
/*     */ import com.google.crypto.tink.signature.internal.SlhDsaVerifyConscrypt;
/*     */ import com.google.crypto.tink.subtle.EcdsaSignJce;
/*     */ import com.google.crypto.tink.subtle.EcdsaVerifyJce;
/*     */ import com.google.crypto.tink.subtle.Ed25519Sign;
/*     */ import com.google.crypto.tink.subtle.Ed25519Verify;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPkcs1SignJce;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPkcs1VerifyJce;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPssSignJce;
/*     */ import com.google.crypto.tink.subtle.RsaSsaPssVerifyJce;
/*     */ import java.security.GeneralSecurityException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SignatureConfigurationV1
/*     */ {
/*  54 */   private static final InternalConfiguration INTERNAL_CONFIGURATION = create();
/*     */   
/*     */   private static InternalConfiguration create() {
/*     */     try {
/*  58 */       PrimitiveRegistry.Builder builder = PrimitiveRegistry.builder();
/*     */ 
/*     */       
/*  61 */       PublicKeySignWrapper.registerToInternalPrimitiveRegistry(builder);
/*  62 */       PublicKeyVerifyWrapper.registerToInternalPrimitiveRegistry(builder);
/*  63 */       builder.registerPrimitiveConstructor(
/*  64 */           PrimitiveConstructor.create(EcdsaSignJce::create, EcdsaPrivateKey.class, PublicKeySign.class));
/*     */       
/*  66 */       builder.registerPrimitiveConstructor(
/*  67 */           PrimitiveConstructor.create(EcdsaVerifyJce::create, EcdsaPublicKey.class, PublicKeyVerify.class));
/*     */       
/*  69 */       builder.registerPrimitiveConstructor(
/*  70 */           PrimitiveConstructor.create(RsaSsaPssSignJce::create, RsaSsaPssPrivateKey.class, PublicKeySign.class));
/*     */       
/*  72 */       builder.registerPrimitiveConstructor(
/*  73 */           PrimitiveConstructor.create(RsaSsaPssVerifyJce::create, RsaSsaPssPublicKey.class, PublicKeyVerify.class));
/*     */       
/*  75 */       builder.registerPrimitiveConstructor(
/*  76 */           PrimitiveConstructor.create(RsaSsaPkcs1SignJce::create, RsaSsaPkcs1PrivateKey.class, PublicKeySign.class));
/*     */       
/*  78 */       builder.registerPrimitiveConstructor(
/*  79 */           PrimitiveConstructor.create(RsaSsaPkcs1VerifyJce::create, RsaSsaPkcs1PublicKey.class, PublicKeyVerify.class));
/*     */       
/*  81 */       builder.registerPrimitiveConstructor(
/*  82 */           PrimitiveConstructor.create(Ed25519Sign::create, Ed25519PrivateKey.class, PublicKeySign.class));
/*     */       
/*  84 */       builder.registerPrimitiveConstructor(
/*  85 */           PrimitiveConstructor.create(Ed25519Verify::create, Ed25519PublicKey.class, PublicKeyVerify.class));
/*     */       
/*  87 */       builder.registerPrimitiveConstructor(
/*  88 */           PrimitiveConstructor.create(MlDsaSignConscrypt::create, MlDsaPrivateKey.class, PublicKeySign.class));
/*     */       
/*  90 */       builder.registerPrimitiveConstructor(
/*  91 */           PrimitiveConstructor.create(MlDsaVerifyConscrypt::create, MlDsaPublicKey.class, PublicKeyVerify.class));
/*     */       
/*  93 */       builder.registerPrimitiveConstructor(
/*  94 */           PrimitiveConstructor.create(SlhDsaSignConscrypt::create, SlhDsaPrivateKey.class, PublicKeySign.class));
/*     */       
/*  96 */       builder.registerPrimitiveConstructor(
/*  97 */           PrimitiveConstructor.create(SlhDsaVerifyConscrypt::create, SlhDsaPublicKey.class, PublicKeyVerify.class));
/*     */ 
/*     */       
/* 100 */       return InternalConfiguration.createFromPrimitiveRegistry(builder.build());
/* 101 */     } catch (GeneralSecurityException e) {
/* 102 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Configuration get() throws GeneralSecurityException {
/* 108 */     if (TinkFipsUtil.useOnlyFips()) {
/* 109 */       throw new GeneralSecurityException("Cannot use non-FIPS-compliant SignatureConfigurationV1 in FIPS mode");
/*     */     }
/*     */     
/* 112 */     return (Configuration)INTERNAL_CONFIGURATION;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\SignatureConfigurationV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */