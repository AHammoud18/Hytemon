/*    */ package com.google.crypto.tink.jwt;
/*    */ 
/*    */ import com.google.crypto.tink.proto.RegistryConfig;
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
/*    */ public final class JwtSignatureConfig
/*    */ {
/* 36 */   public static final String JWT_ECDSA_PUBLIC_KEY_TYPE_URL = JwtEcdsaVerifyKeyManager.getKeyType();
/* 37 */   public static final String JWT_ECDSA_PRIVATE_KEY_TYPE_URL = JwtEcdsaSignKeyManager.getKeyType();
/*    */ 
/*    */   
/* 40 */   public static final String JWT_RSA_PKCS1_PRIVATE_KEY_TYPE_URL = JwtRsaSsaPkcs1SignKeyManager.getKeyType();
/*    */   
/* 42 */   public static final String JWT_RSA_PKCS1_PUBLIC_KEY_TYPE_URL = JwtRsaSsaPkcs1VerifyKeyManager.getKeyType();
/*    */ 
/*    */   
/* 45 */   public static final String JWT_RSA_PSS_PRIVATE_KEY_TYPE_URL = JwtRsaSsaPssSignKeyManager.getKeyType();
/*    */   
/* 47 */   public static final String JWT_RSA_PSS_PUBLIC_KEY_TYPE_URL = JwtRsaSsaPssVerifyKeyManager.getKeyType();
/*    */   
/* 49 */   public static final RegistryConfig LATEST = RegistryConfig.getDefaultInstance();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void register() throws GeneralSecurityException {
/* 57 */     JwtPublicKeySignWrapper.register();
/* 58 */     JwtPublicKeyVerifyWrapper.register();
/*    */     
/* 60 */     JwtEcdsaSignKeyManager.registerPair(true);
/* 61 */     JwtRsaSsaPkcs1SignKeyManager.registerPair(true);
/* 62 */     JwtRsaSsaPssSignKeyManager.registerPair(true);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\JwtSignatureConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */