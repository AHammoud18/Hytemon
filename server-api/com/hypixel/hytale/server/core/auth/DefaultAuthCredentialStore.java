/*    */ package com.hypixel.hytale.server.core.auth;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class DefaultAuthCredentialStore
/*    */   implements IAuthCredentialStore {
/*  9 */   private IAuthCredentialStore.OAuthTokens tokens = new IAuthCredentialStore.OAuthTokens(null, null, null);
/*    */   
/*    */   @Nullable
/*    */   private UUID profile;
/*    */   
/*    */   public void setTokens(@Nonnull IAuthCredentialStore.OAuthTokens tokens) {
/* 15 */     this.tokens = tokens;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public IAuthCredentialStore.OAuthTokens getTokens() {
/* 21 */     return this.tokens;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setProfile(@Nullable UUID uuid) {
/* 26 */     this.profile = uuid;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public UUID getProfile() {
/* 32 */     return this.profile;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 37 */     this.tokens = new IAuthCredentialStore.OAuthTokens(null, null, null);
/* 38 */     this.profile = null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\auth\DefaultAuthCredentialStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */