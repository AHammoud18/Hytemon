/*    */ package com.nimbusds.jose.jwk.source;
/*    */ 
/*    */ import com.nimbusds.jose.jwk.JWKSet;
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
/*    */ class NoRefreshJWKSetCacheEvaluator
/*    */   extends JWKSetCacheRefreshEvaluator
/*    */ {
/* 34 */   private static final NoRefreshJWKSetCacheEvaluator INSTANCE = new NoRefreshJWKSetCacheEvaluator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static NoRefreshJWKSetCacheEvaluator getInstance() {
/* 43 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean requiresRefresh(JWKSet jwkSet) {
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 58 */     return obj instanceof NoRefreshJWKSetCacheEvaluator;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 64 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\NoRefreshJWKSetCacheEvaluator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */