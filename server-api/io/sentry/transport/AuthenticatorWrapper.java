/*    */ package io.sentry.transport;
/*    */ 
/*    */ import java.net.Authenticator;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ final class AuthenticatorWrapper
/*    */ {
/*  8 */   private static final AuthenticatorWrapper instance = new AuthenticatorWrapper();
/*    */   
/*    */   public static AuthenticatorWrapper getInstance() {
/* 11 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDefault(@NotNull Authenticator authenticator) {
/* 17 */     Authenticator.setDefault(authenticator);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\AuthenticatorWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */