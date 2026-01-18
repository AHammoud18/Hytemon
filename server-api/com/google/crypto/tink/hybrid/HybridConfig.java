/*     */ package com.google.crypto.tink.hybrid;
/*     */ 
/*     */ import com.google.crypto.tink.aead.AeadConfig;
/*     */ import com.google.crypto.tink.config.TinkFips;
/*     */ import com.google.crypto.tink.daead.DeterministicAeadConfig;
/*     */ import com.google.crypto.tink.hybrid.internal.HpkePrivateKeyManager;
/*     */ import com.google.crypto.tink.proto.RegistryConfig;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HybridConfig
/*     */ {
/*  46 */   public static final String ECIES_AEAD_HKDF_PUBLIC_KEY_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.EciesAeadHkdfPublicKey");
/*     */   
/*  48 */   public static final String ECIES_AEAD_HKDF_PRIVATE_KEY_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  54 */   public static final RegistryConfig TINK_1_0_0 = RegistryConfig.getDefaultInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  60 */   public static final RegistryConfig TINK_1_1_0 = RegistryConfig.getDefaultInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  67 */   public static final RegistryConfig LATEST = RegistryConfig.getDefaultInstance();
/*     */   
/*     */   static {
/*     */     try {
/*  71 */       init();
/*  72 */     } catch (GeneralSecurityException e) {
/*  73 */       throw new ExceptionInInitializerError(e);
/*     */     } 
/*     */   }
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
/*     */   @CanIgnoreReturnValue
/*     */   private static String initializeClassReturnInput(String s) {
/*  95 */     return s;
/*     */   }
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
/*     */   @Deprecated
/*     */   public static void init() throws GeneralSecurityException {
/* 111 */     register();
/*     */   }
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
/*     */   public static void register() throws GeneralSecurityException {
/* 126 */     HybridDecryptWrapper.register();
/* 127 */     HybridEncryptWrapper.register();
/*     */     
/* 129 */     AeadConfig.register();
/* 130 */     DeterministicAeadConfig.register();
/*     */     
/* 132 */     if (TinkFips.useOnlyFips()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     EciesAeadHkdfPrivateKeyManager.registerPair(true);
/* 140 */     HpkePrivateKeyManager.registerPair(true);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HybridConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */