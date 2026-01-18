/*    */ package com.google.crypto.tink.config;
/*    */ 
/*    */ import com.google.crypto.tink.daead.DeterministicAeadConfig;
/*    */ import com.google.crypto.tink.hybrid.HybridConfig;
/*    */ import com.google.crypto.tink.prf.PrfConfig;
/*    */ import com.google.crypto.tink.proto.RegistryConfig;
/*    */ import com.google.crypto.tink.signature.SignatureConfig;
/*    */ import com.google.crypto.tink.streamingaead.StreamingAeadConfig;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TinkConfig
/*    */ {
/*    */   @Deprecated
/* 45 */   public static final RegistryConfig TINK_1_0_0 = RegistryConfig.getDefaultInstance();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/* 52 */   public static final RegistryConfig TINK_1_1_0 = RegistryConfig.getDefaultInstance();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/* 59 */   public static final RegistryConfig LATEST = RegistryConfig.getDefaultInstance();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static void init() throws GeneralSecurityException {
/* 70 */     register();
/*    */   }
/*    */   
/*    */   static {
/*    */     try {
/* 75 */       init();
/* 76 */     } catch (GeneralSecurityException e) {
/* 77 */       throw new ExceptionInInitializerError(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void register() throws GeneralSecurityException {
/* 89 */     DeterministicAeadConfig.register();
/* 90 */     HybridConfig.register();
/* 91 */     PrfConfig.register();
/* 92 */     SignatureConfig.register();
/* 93 */     StreamingAeadConfig.register();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\config\TinkConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */