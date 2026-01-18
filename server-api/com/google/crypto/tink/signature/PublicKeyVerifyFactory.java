/*    */ package com.google.crypto.tink.signature;
/*    */ 
/*    */ import com.google.crypto.tink.KeysetHandle;
/*    */ import com.google.crypto.tink.PublicKeyVerify;
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
/*    */ public final class PublicKeyVerifyFactory
/*    */ {
/*    */   @Deprecated
/*    */   public static PublicKeyVerify getPrimitive(KeysetHandle keysetHandle) throws GeneralSecurityException {
/* 49 */     PublicKeyVerifyWrapper.register();
/* 50 */     return (PublicKeyVerify)keysetHandle.getPrimitive(RegistryConfiguration.get(), PublicKeyVerify.class);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\PublicKeyVerifyFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */