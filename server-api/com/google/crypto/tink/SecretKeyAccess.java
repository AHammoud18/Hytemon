/*    */ package com.google.crypto.tink;
/*    */ 
/*    */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*    */ import com.google.errorprone.annotations.CheckReturnValue;
/*    */ import com.google.errorprone.annotations.Immutable;
/*    */ import java.security.GeneralSecurityException;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CheckReturnValue
/*    */ @Immutable
/*    */ public final class SecretKeyAccess
/*    */ {
/* 50 */   private static final SecretKeyAccess INSTANCE = new SecretKeyAccess();
/*    */ 
/*    */   
/*    */   static SecretKeyAccess instance() {
/* 54 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @CanIgnoreReturnValue
/*    */   public static SecretKeyAccess requireAccess(@Nullable SecretKeyAccess access) throws GeneralSecurityException {
/* 71 */     if (access == null) {
/* 72 */       throw new GeneralSecurityException("SecretKeyAccess is required");
/*    */     }
/* 74 */     return access;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\SecretKeyAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */