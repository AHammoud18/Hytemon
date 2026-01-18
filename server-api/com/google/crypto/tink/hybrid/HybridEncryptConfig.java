/*    */ package com.google.crypto.tink.hybrid;
/*    */ 
/*    */ import com.google.crypto.tink.Config;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public final class HybridEncryptConfig
/*    */ {
/*    */   @Deprecated
/*    */   public static void registerStandardKeyTypes() throws GeneralSecurityException {
/* 49 */     Config.register(HybridConfig.TINK_1_0_0);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HybridEncryptConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */