/*    */ package com.google.crypto.tink.prf;
/*    */ 
/*    */ import com.google.crypto.tink.config.TinkFips;
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
/*    */ public final class PrfConfig
/*    */ {
/* 27 */   public static final String PRF_TYPE_URL = HkdfPrfKeyManager.getKeyType();
/* 28 */   public static final String HMAC_PRF_TYPE_URL = HmacPrfKeyManager.getKeyType();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void register() throws GeneralSecurityException {
/* 35 */     PrfSetWrapper.register();
/* 36 */     HmacPrfKeyManager.register(true);
/*    */     
/* 38 */     if (TinkFips.useOnlyFips()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 43 */     AesCmacPrfKeyManager.register(true);
/* 44 */     HkdfPrfKeyManager.register(true);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\PrfConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */