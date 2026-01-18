/*    */ package com.google.crypto.tink.streamingaead;
/*    */ 
/*    */ import com.google.crypto.tink.KeysetHandle;
/*    */ import com.google.crypto.tink.RegistryConfiguration;
/*    */ import com.google.crypto.tink.StreamingAead;
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
/*    */ @Deprecated
/*    */ public final class StreamingAeadFactory
/*    */ {
/*    */   public static StreamingAead getPrimitive(KeysetHandle keysetHandle) throws GeneralSecurityException {
/* 46 */     StreamingAeadWrapper.register();
/* 47 */     return (StreamingAead)keysetHandle.getPrimitive(RegistryConfiguration.get(), StreamingAead.class);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\streamingaead\StreamingAeadFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */