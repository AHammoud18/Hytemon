/*    */ package com.google.crypto.tink;
/*    */ 
/*    */ import com.google.crypto.tink.internal.MutableParametersRegistry;
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
/*    */ public final class KeyTemplates
/*    */ {
/*    */   public static KeyTemplate get(String name) throws GeneralSecurityException {
/* 37 */     Parameters result = MutableParametersRegistry.globalInstance().get(name);
/* 38 */     return KeyTemplate.createFrom(result);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\KeyTemplates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */