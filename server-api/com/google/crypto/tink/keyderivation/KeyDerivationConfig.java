/*    */ package com.google.crypto.tink.keyderivation;
/*    */ 
/*    */ import com.google.crypto.tink.config.TinkFips;
/*    */ import com.google.crypto.tink.keyderivation.internal.KeysetDeriverWrapper;
/*    */ import com.google.crypto.tink.keyderivation.internal.PrfBasedDeriverKeyManager;
/*    */ import com.google.crypto.tink.prf.HkdfPrfKeyManager;
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
/*    */ 
/*    */ 
/*    */ public final class KeyDerivationConfig
/*    */ {
/*    */   public static void register() throws GeneralSecurityException {
/* 45 */     KeysetDeriverWrapper.register();
/*    */     
/* 47 */     if (TinkFips.useOnlyFips()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 55 */     HkdfPrfKeyManager.register(true);
/*    */ 
/*    */     
/* 58 */     PrfBasedDeriverKeyManager.register(true);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\keyderivation\KeyDerivationConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */