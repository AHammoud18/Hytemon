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
/*    */ public abstract class JWKSetCacheRefreshEvaluator
/*    */ {
/*    */   public static JWKSetCacheRefreshEvaluator forceRefresh() {
/* 22 */     return ForceRefreshJWKSetCacheEvaluator.getInstance();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static JWKSetCacheRefreshEvaluator noRefresh() {
/* 32 */     return NoRefreshJWKSetCacheEvaluator.getInstance();
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
/*    */   public static JWKSetCacheRefreshEvaluator referenceComparison(JWKSet jwtSet) {
/* 44 */     return new ReferenceComparisonRefreshJWKSetEvaluator(jwtSet);
/*    */   }
/*    */   
/*    */   public abstract boolean requiresRefresh(JWKSet paramJWKSet);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\JWKSetCacheRefreshEvaluator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */