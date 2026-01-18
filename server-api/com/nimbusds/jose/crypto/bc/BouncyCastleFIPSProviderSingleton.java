/*    */ package com.nimbusds.jose.crypto.bc;
/*    */ 
/*    */ import java.security.Provider;
/*    */ import java.security.Security;
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
/*    */ public final class BouncyCastleFIPSProviderSingleton
/*    */ {
/*    */   private static Provider bouncyCastleFIPSProvider;
/*    */   
/*    */   public static Provider getInstance() {
/* 70 */     if (bouncyCastleFIPSProvider == null) {
/* 71 */       bouncyCastleFIPSProvider = Security.getProvider("BCFIPS");
/*    */     }
/* 73 */     return bouncyCastleFIPSProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\bc\BouncyCastleFIPSProviderSingleton.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */