/*    */ package com.hypixel.hytale.server.core.auth;
/*    */ public final class OAuthTokens extends Record { @Nullable
/*    */   private final String accessToken;
/*    */   @Nullable
/*    */   private final String refreshToken;
/*    */   @Nullable
/*    */   private final Instant accessTokenExpiresAt;
/*    */   
/*  9 */   public OAuthTokens(@Nullable String accessToken, @Nullable String refreshToken, @Nullable Instant accessTokenExpiresAt) { this.accessToken = accessToken; this.refreshToken = refreshToken; this.accessTokenExpiresAt = accessTokenExpiresAt; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/auth/IAuthCredentialStore$OAuthTokens;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/auth/IAuthCredentialStore$OAuthTokens; } @Nullable public String accessToken() { return this.accessToken; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/auth/IAuthCredentialStore$OAuthTokens;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/auth/IAuthCredentialStore$OAuthTokens; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/auth/IAuthCredentialStore$OAuthTokens;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/auth/IAuthCredentialStore$OAuthTokens;
/*  9 */     //   0	8	1	o	Ljava/lang/Object; } @Nullable public String refreshToken() { return this.refreshToken; } @Nullable public Instant accessTokenExpiresAt() { return this.accessTokenExpiresAt; }
/*    */    public boolean isValid() {
/* 11 */     return (this.refreshToken != null);
/*    */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\auth\IAuthCredentialStore$OAuthTokens.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */