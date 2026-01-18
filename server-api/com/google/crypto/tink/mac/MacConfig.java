/*     */ package com.google.crypto.tink.mac;
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
/*     */ public final class MacConfig
/*     */ {
/*  42 */   public static final String HMAC_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.HmacKey");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  48 */   public static final RegistryConfig TINK_1_0_0 = RegistryConfig.getDefaultInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  55 */   public static final RegistryConfig TINK_1_1_0 = TINK_1_0_0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  62 */   public static final RegistryConfig LATEST = TINK_1_0_0;
/*     */   
/*     */   static {
/*     */     try {
/*  66 */       init();
/*  67 */     } catch (GeneralSecurityException e) {
/*  68 */       throw new ExceptionInInitializerError(e);
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
/*  90 */     return s;
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
/* 102 */     register();
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
/* 113 */     MacWrapper.register();
/* 114 */     ChunkedMacWrapper.register();
/* 115 */     HmacKeyManager.register(true);
/*     */     
/* 117 */     if (TinkFips.useOnlyFips()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 122 */     AesCmacKeyManager.register(true);
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
/* 136 */     register();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\MacConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */