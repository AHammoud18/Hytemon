/*    */ package com.google.crypto.tink.mac;
/*    */ 
/*    */ import com.google.crypto.tink.KeysetHandle;
/*    */ import com.google.crypto.tink.Mac;
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
/*    */ 
/*    */ @Deprecated
/*    */ public final class MacFactory
/*    */ {
/*    */   @Deprecated
/*    */   public static Mac getPrimitive(KeysetHandle keysetHandle) throws GeneralSecurityException {
/* 51 */     MacWrapper.register();
/* 52 */     return (Mac)keysetHandle.getPrimitive(RegistryConfiguration.get(), Mac.class);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\mac\MacFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */