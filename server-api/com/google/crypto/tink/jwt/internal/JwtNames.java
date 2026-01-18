/*    */ package com.google.crypto.tink.jwt.internal;
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
/*    */ public final class JwtNames
/*    */ {
/*    */   public static final String CLAIM_ISSUER = "iss";
/*    */   public static final String CLAIM_SUBJECT = "sub";
/*    */   public static final String CLAIM_AUDIENCE = "aud";
/*    */   public static final String CLAIM_EXPIRATION = "exp";
/*    */   public static final String CLAIM_NOT_BEFORE = "nbf";
/*    */   public static final String CLAIM_ISSUED_AT = "iat";
/*    */   public static final String CLAIM_JWT_ID = "jti";
/*    */   public static final String HEADER_ALGORITHM = "alg";
/*    */   public static final String HEADER_KEY_ID = "kid";
/*    */   public static final String HEADER_TYPE = "typ";
/*    */   public static final String HEADER_CRITICAL = "crit";
/*    */   
/*    */   public static void validate(String name) {
/* 44 */     if (isRegisteredName(name)) {
/* 45 */       throw new IllegalArgumentException(
/* 46 */           String.format("claim '%s' is invalid because it's a registered name; use the corresponding setter method.", new Object[] { name }));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isRegisteredName(String name) {
/* 54 */     return (name.equals("iss") || name
/* 55 */       .equals("sub") || name
/* 56 */       .equals("aud") || name
/* 57 */       .equals("exp") || name
/* 58 */       .equals("nbf") || name
/* 59 */       .equals("iat") || name
/* 60 */       .equals("jti"));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\jwt\internal\JwtNames.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */