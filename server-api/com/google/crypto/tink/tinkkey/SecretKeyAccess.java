/*    */ package com.google.crypto.tink.tinkkey;
/*    */ 
/*    */ import com.google.errorprone.annotations.Immutable;
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
/*    */ @Immutable
/*    */ public final class SecretKeyAccess
/*    */ {
/*    */   public static KeyAccess insecureSecretAccess() {
/* 38 */     return KeyAccess.secretAccess();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\tinkkey\SecretKeyAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */