/*     */ package com.google.crypto.tink.streamingaead;
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
/*     */ public final class StreamingAeadConfig
/*     */ {
/*  42 */   public static final String AES_CTR_HMAC_STREAMINGAEAD_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.AesCtrHmacStreamingKey");
/*     */   
/*  44 */   public static final String AES_GCM_HKDF_STREAMINGAEAD_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.AesGcmHkdfStreamingKey");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  50 */   public static final RegistryConfig TINK_1_1_0 = RegistryConfig.getDefaultInstance();
/*     */ 
/*     */   
/*  53 */   public static final RegistryConfig LATEST = RegistryConfig.getDefaultInstance();
/*     */   
/*     */   static {
/*     */     try {
/*  57 */       init();
/*  58 */     } catch (GeneralSecurityException e) {
/*  59 */       throw new ExceptionInInitializerError(e);
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
/*  81 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void init() throws GeneralSecurityException {
/*  92 */     register();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() throws GeneralSecurityException {
/* 102 */     StreamingAeadWrapper.register();
/*     */     
/* 104 */     if (TinkFips.useOnlyFips()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     AesCtrHmacStreamingKeyManager.register(true);
/* 112 */     AesGcmHkdfStreamingKeyManager.register(true);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\StreamingAeadConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */