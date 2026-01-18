/*    */ package com.nimbusds.jose.crypto.bc;
/*    */ 
/*    */ import java.security.Provider;
/*    */ import org.bouncycastle.jce.provider.BouncyCastleProvider;
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
/*    */ 
/*    */ public final class BouncyCastleProviderSingleton
/*    */ {
/*    */   private static Provider bouncyCastleProvider;
/*    */   
/*    */   public static Provider getInstance() {
/* 71 */     if (bouncyCastleProvider == null) {
/* 72 */       bouncyCastleProvider = (Provider)new BouncyCastleProvider();
/*    */     }
/* 74 */     return bouncyCastleProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\bc\BouncyCastleProviderSingleton.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */