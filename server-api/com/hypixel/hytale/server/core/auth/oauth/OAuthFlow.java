/*    */ package com.hypixel.hytale.server.core.auth.oauth;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ 
/*    */ abstract class OAuthFlow
/*    */ {
/*  7 */   private OAuthClient.TokenResponse tokenResponse = null;
/*  8 */   private final CompletableFuture<OAuthResult> future = new CompletableFuture<>();
/*  9 */   private OAuthResult result = OAuthResult.UNKNOWN;
/* 10 */   private String errorMessage = null;
/*    */   
/*    */   final void onSuccess(OAuthClient.TokenResponse tokenResponse) {
/* 13 */     if (this.future.isDone()) {
/*    */       return;
/*    */     }
/*    */     
/* 17 */     this.tokenResponse = tokenResponse;
/* 18 */     this.result = OAuthResult.SUCCESS;
/* 19 */     this.future.complete(this.result);
/*    */   }
/*    */   
/*    */   final void onFailure(String errorMessage) {
/* 23 */     if (this.future.isDone()) {
/*    */       return;
/*    */     }
/*    */     
/* 27 */     this.errorMessage = errorMessage;
/* 28 */     this.result = OAuthResult.FAILED;
/*    */   }
/*    */   
/*    */   public OAuthClient.TokenResponse getTokenResponse() {
/* 32 */     return this.tokenResponse;
/*    */   }
/*    */   
/*    */   public OAuthResult getResult() {
/* 36 */     return this.result;
/*    */   }
/*    */   
/*    */   public String getErrorMessage() {
/* 40 */     return this.errorMessage;
/*    */   }
/*    */   
/*    */   public CompletableFuture<OAuthResult> getFuture() {
/* 44 */     return this.future;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\auth\oauth\OAuthFlow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */