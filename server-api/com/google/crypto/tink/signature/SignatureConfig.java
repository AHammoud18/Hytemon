/*     */ package com.google.crypto.tink.signature;
/*     */ 
/*     */ import com.google.crypto.tink.config.TinkFips;
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
/*     */ public final class SignatureConfig
/*     */ {
/*  43 */   public static final String ECDSA_PUBLIC_KEY_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.EcdsaPublicKey");
/*     */   
/*  45 */   public static final String ECDSA_PRIVATE_KEY_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.EcdsaPrivateKey");
/*     */   
/*  47 */   public static final String ED25519_PUBLIC_KEY_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.Ed25519PublicKey");
/*     */   
/*  49 */   public static final String ED25519_PRIVATE_KEY_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.Ed25519PrivateKey");
/*     */   
/*  51 */   public static final String RSA_PKCS1_PRIVATE_KEY_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PrivateKey");
/*     */   
/*  53 */   public static final String RSA_PKCS1_PUBLIC_KEY_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.RsaSsaPkcs1PublicKey");
/*     */   
/*  55 */   public static final String RSA_PSS_PRIVATE_KEY_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.RsaSsaPssPrivateKey");
/*     */   
/*  57 */   public static final String RSA_PSS_PUBLIC_KEY_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.RsaSsaPssPublicKey");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  63 */   public static final RegistryConfig TINK_1_0_0 = RegistryConfig.getDefaultInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  69 */   public static final RegistryConfig TINK_1_1_0 = RegistryConfig.getDefaultInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  75 */   public static final RegistryConfig LATEST = RegistryConfig.getDefaultInstance();
/*     */   
/*     */   static {
/*     */     try {
/*  79 */       init();
/*  80 */     } catch (GeneralSecurityException e) {
/*  81 */       throw new ExceptionInInitializerError(e);
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
/* 103 */     return s;
/*     */   }
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
/* 115 */     register();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 126 */     PublicKeySignWrapper.register();
/* 127 */     PublicKeyVerifyWrapper.register();
/*     */     
/* 129 */     EcdsaSignKeyManager.registerPair(true);
/* 130 */     RsaSsaPkcs1SignKeyManager.registerPair(true);
/* 131 */     RsaSsaPssSignKeyManager.registerPair(true);
/*     */     
/* 133 */     if (TinkFips.useOnlyFips()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 138 */     Ed25519PrivateKeyManager.registerPair(true);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\SignatureConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */