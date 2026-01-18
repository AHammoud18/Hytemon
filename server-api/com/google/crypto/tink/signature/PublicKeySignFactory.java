/*    */ package com.google.crypto.tink.signature;
/*    */ 
/*    */ import com.google.crypto.tink.KeysetHandle;
/*    */ import com.google.crypto.tink.PublicKeySign;
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
/*    */ public final class PublicKeySignFactory
/*    */ {
/*    */   @Deprecated
/*    */   public static PublicKeySign getPrimitive(KeysetHandle keysetHandle) throws GeneralSecurityException {
/* 49 */     PublicKeySignWrapper.register();
/* 50 */     return (PublicKeySign)keysetHandle.getPrimitive(RegistryConfiguration.get(), PublicKeySign.class);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\signature\PublicKeySignFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */