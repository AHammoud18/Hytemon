/*    */ package com.google.crypto.tink.hybrid;
/*    */ 
/*    */ import com.google.crypto.tink.HybridEncrypt;
/*    */ import com.google.crypto.tink.KeysetHandle;
/*    */ import com.google.crypto.tink.RegistryConfiguration;
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
/*    */ @Deprecated
/*    */ public final class HybridEncryptFactory
/*    */ {
/*    */   @Deprecated
/*    */   public static HybridEncrypt getPrimitive(KeysetHandle keysetHandle) throws GeneralSecurityException {
/* 49 */     HybridEncryptWrapper.register();
/* 50 */     return (HybridEncrypt)keysetHandle.getPrimitive(RegistryConfiguration.get(), HybridEncrypt.class);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\HybridEncryptFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */