/*     */ package com.google.crypto.tink.daead;
/*     */ 
/*     */ import com.google.crypto.tink.config.TinkFips;
/*     */ import com.google.crypto.tink.proto.RegistryConfig;
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import com.google.errorprone.annotations.InlineMe;
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
/*     */ public final class DeterministicAeadConfig
/*     */ {
/*  43 */   public static final String AES_SIV_TYPE_URL = initializeClassReturnInput("type.googleapis.com/google.crypto.tink.AesSivKey");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  49 */   public static final RegistryConfig TINK_1_1_0 = RegistryConfig.getDefaultInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  56 */   public static final RegistryConfig LATEST = RegistryConfig.getDefaultInstance();
/*     */   
/*     */   static {
/*     */     try {
/*  60 */       register();
/*  61 */     } catch (GeneralSecurityException e) {
/*  62 */       throw new ExceptionInInitializerError(e);
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
/*  84 */     return s;
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
/*     */   @Deprecated
/*     */   @InlineMe(replacement = "DeterministicAeadConfig.register()", imports = {"com.google.crypto.tink.daead.DeterministicAeadConfig"})
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
/* 113 */     DeterministicAeadWrapper.register();
/*     */     
/* 115 */     if (TinkFips.useOnlyFips()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 121 */     AesSivKeyManager.register(true);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\daead\DeterministicAeadConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */