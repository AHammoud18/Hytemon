/*     */ package com.google.crypto.tink.aead;
/*     */ 
/*     */ import com.google.crypto.tink.config.TinkFips;
/*     */ import com.google.crypto.tink.mac.MacConfig;
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
/*     */ public final class AeadConfig
/*     */ {
/*  40 */   public static final String AES_CTR_HMAC_AEAD_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey");
/*     */   
/*  42 */   public static final String AES_GCM_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.AesGcmKey");
/*     */   
/*  44 */   public static final String AES_GCM_SIV_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.AesGcmSivKey");
/*     */   
/*  46 */   public static final String AES_EAX_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.AesEaxKey");
/*     */   
/*  48 */   public static final String KMS_AEAD_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.KmsAeadKey");
/*     */   
/*  50 */   public static final String KMS_ENVELOPE_AEAD_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.KmsEnvelopeAeadKey");
/*     */   
/*  52 */   public static final String CHACHA20_POLY1305_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.ChaCha20Poly1305Key");
/*     */   
/*  54 */   public static final String XCHACHA20_POLY1305_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.XChaCha20Poly1305Key");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  60 */   public static final RegistryConfig TINK_1_0_0 = RegistryConfig.getDefaultInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  67 */   public static final RegistryConfig TINK_1_1_0 = TINK_1_0_0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  74 */   public static final RegistryConfig LATEST = TINK_1_0_0;
/*     */   
/*     */   static {
/*     */     try {
/*  78 */       init();
/*  79 */     } catch (GeneralSecurityException e) {
/*  80 */       throw new ExceptionInInitializerError(e);
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
/* 102 */     return s;
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
/*     */   @Deprecated
/*     */   public static void init() throws GeneralSecurityException {
/* 117 */     register();
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
/*     */   public static void register() throws GeneralSecurityException {
/* 131 */     AeadWrapper.register();
/*     */     
/* 133 */     MacConfig.register();
/* 134 */     AesCtrHmacAeadKeyManager.register(true);
/* 135 */     AesGcmKeyManager.register(true);
/*     */     
/* 137 */     if (TinkFips.useOnlyFips()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 142 */     AesEaxKeyManager.register(true);
/* 143 */     AesGcmSivKeyManager.register(true);
/* 144 */     ChaCha20Poly1305KeyManager.register(true);
/* 145 */     KmsAeadKeyManager.register(true);
/* 146 */     KmsEnvelopeAeadKeyManager.register(true);
/* 147 */     XChaCha20Poly1305KeyManager.register(true);
/* 148 */     XAesGcmKeyManager.register(true);
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
/*     */   @Deprecated
/*     */   public static void registerStandardKeyTypes() throws GeneralSecurityException {
/* 162 */     register();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AeadConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */