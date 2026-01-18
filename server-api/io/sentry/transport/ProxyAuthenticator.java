/*    */ package io.sentry.transport;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import java.net.Authenticator;
/*    */ import java.net.PasswordAuthentication;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ProxyAuthenticator
/*    */   extends Authenticator
/*    */ {
/*    */   @NotNull
/*    */   private final String user;
/*    */   @NotNull
/*    */   private final String password;
/*    */   
/*    */   ProxyAuthenticator(@NotNull String user, @NotNull String password) {
/* 20 */     this.user = (String)Objects.requireNonNull(user, "user is required");
/* 21 */     this.password = (String)Objects.requireNonNull(password, "password is required");
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected PasswordAuthentication getPasswordAuthentication() {
/* 26 */     if (getRequestorType() == Authenticator.RequestorType.PROXY) {
/* 27 */       return new PasswordAuthentication(this.user, this.password.toCharArray());
/*    */     }
/* 29 */     return null;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   String getUser() {
/* 34 */     return this.user;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   String getPassword() {
/* 39 */     return this.password;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\ProxyAuthenticator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */