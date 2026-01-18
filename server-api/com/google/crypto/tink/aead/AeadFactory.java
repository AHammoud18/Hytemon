/*    */ package com.google.crypto.tink.aead;
/*    */ 
/*    */ import com.google.crypto.tink.Aead;
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
/*    */ 
/*    */ @Deprecated
/*    */ public final class AeadFactory
/*    */ {
/*    */   @Deprecated
/*    */   public static Aead getPrimitive(KeysetHandle keysetHandle) throws GeneralSecurityException {
/* 50 */     AeadWrapper.register();
/* 51 */     return (Aead)keysetHandle.getPrimitive(RegistryConfiguration.get(), Aead.class);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\AeadFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */