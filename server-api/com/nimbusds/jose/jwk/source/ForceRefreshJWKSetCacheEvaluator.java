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
/*    */ class ForceRefreshJWKSetCacheEvaluator
/*    */   extends JWKSetCacheRefreshEvaluator
/*    */ {
/* 34 */   private static final ForceRefreshJWKSetCacheEvaluator INSTANCE = new ForceRefreshJWKSetCacheEvaluator();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ForceRefreshJWKSetCacheEvaluator getInstance() {
/* 43 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean requiresRefresh(JWKSet jwkSet) {
/* 52 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 58 */     return obj instanceof ForceRefreshJWKSetCacheEvaluator;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 64 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\ForceRefreshJWKSetCacheEvaluator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */