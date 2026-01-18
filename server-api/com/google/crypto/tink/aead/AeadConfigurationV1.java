/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.Configuration;
/*     */ import com.google.crypto.tink.aead.internal.ChaCha20Poly1305Jce;
/*     */ import com.google.crypto.tink.aead.internal.XAesGcm;
/*     */ import com.google.crypto.tink.aead.internal.XChaCha20Poly1305Jce;
/*     */ import com.google.crypto.tink.aead.subtle.AesGcmSiv;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.InternalConfiguration;
/*     */ import com.google.crypto.tink.internal.PrimitiveConstructor;
/*     */ import com.google.crypto.tink.internal.PrimitiveRegistry;
/*     */ import com.google.crypto.tink.subtle.AesEaxJce;
/*     */ import com.google.crypto.tink.subtle.AesGcmJce;
/*     */ import com.google.crypto.tink.subtle.ChaCha20Poly1305;
/*     */ import com.google.crypto.tink.subtle.EncryptThenAuthenticate;
/*     */ import com.google.crypto.tink.subtle.XChaCha20Poly1305;
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
/*     */ 
/*     */ 
/*     */ class AeadConfigurationV1
/*     */ {
/*  52 */   private static final InternalConfiguration INTERNAL_CONFIGURATION = create();
/*     */   
/*     */   private static InternalConfiguration create() {
/*     */     try {
/*  56 */       PrimitiveRegistry.Builder builder = PrimitiveRegistry.builder();
/*     */ 
/*     */       
/*  59 */       AeadWrapper.registerToInternalPrimitiveRegistry(builder);
/*  60 */       builder.registerPrimitiveConstructor(
/*  61 */           PrimitiveConstructor.create(EncryptThenAuthenticate::create, AesCtrHmacAeadKey.class, Aead.class));
/*     */       
/*  63 */       builder.registerPrimitiveConstructor(
/*  64 */           PrimitiveConstructor.create(AesGcmJce::create, AesGcmKey.class, Aead.class));
/*  65 */       builder.registerPrimitiveConstructor(
/*  66 */           PrimitiveConstructor.create(AesGcmSiv::create, AesGcmSivKey.class, Aead.class));
/*  67 */       builder.registerPrimitiveConstructor(
/*  68 */           PrimitiveConstructor.create(AesEaxJce::create, AesEaxKey.class, Aead.class));
/*  69 */       builder.registerPrimitiveConstructor(
/*  70 */           PrimitiveConstructor.create(AeadConfigurationV1::createChaCha20Poly1305, ChaCha20Poly1305Key.class, Aead.class));
/*     */ 
/*     */ 
/*     */       
/*  74 */       builder.registerPrimitiveConstructor(
/*  75 */           PrimitiveConstructor.create(AeadConfigurationV1::createXChaCha20Poly1305, XChaCha20Poly1305Key.class, Aead.class));
/*     */ 
/*     */ 
/*     */       
/*  79 */       builder.registerPrimitiveConstructor(
/*  80 */           PrimitiveConstructor.create(XAesGcm::create, XAesGcmKey.class, Aead.class));
/*     */       
/*  82 */       return InternalConfiguration.createFromPrimitiveRegistry(builder.build());
/*  83 */     } catch (GeneralSecurityException e) {
/*  84 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Configuration get() throws GeneralSecurityException {
/*  90 */     if (TinkFipsUtil.useOnlyFips()) {
/*  91 */       throw new GeneralSecurityException("Cannot use non-FIPS-compliant AeadConfigurationV1 in FIPS mode");
/*     */     }
/*     */     
/*  94 */     return (Configuration)INTERNAL_CONFIGURATION;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Aead createChaCha20Poly1305(ChaCha20Poly1305Key key) throws GeneralSecurityException {
/*  99 */     if (ChaCha20Poly1305Jce.isSupported()) {
/* 100 */       return ChaCha20Poly1305Jce.create(key);
/*     */     }
/* 102 */     return ChaCha20Poly1305.create(key);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Aead createXChaCha20Poly1305(XChaCha20Poly1305Key key) throws GeneralSecurityException {
/* 107 */     if (XChaCha20Poly1305Jce.isSupported()) {
/* 108 */       return XChaCha20Poly1305Jce.create(key);
/*     */     }
/* 110 */     return XChaCha20Poly1305.create(key);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AeadConfigurationV1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */