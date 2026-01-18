/*    */ package com.nimbusds.jose.jwk.source;
/*    */ 
/*    */ import com.nimbusds.jose.jwk.JWKSet;
/*    */ import java.util.Objects;
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
/*    */ class ReferenceComparisonRefreshJWKSetEvaluator
/*    */   extends JWKSetCacheRefreshEvaluator
/*    */ {
/*    */   private final JWKSet jwkSet;
/*    */   
/*    */   public ReferenceComparisonRefreshJWKSetEvaluator(JWKSet jwkSet) {
/* 40 */     this.jwkSet = jwkSet;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean requiresRefresh(JWKSet jwkSet) {
/* 48 */     return (jwkSet == this.jwkSet);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 54 */     return Objects.hash(new Object[] { this.jwkSet });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 60 */     if (this == obj)
/* 61 */       return true; 
/* 62 */     if (obj == null)
/* 63 */       return false; 
/* 64 */     if (getClass() != obj.getClass())
/* 65 */       return false; 
/* 66 */     ReferenceComparisonRefreshJWKSetEvaluator other = (ReferenceComparisonRefreshJWKSetEvaluator)obj;
/* 67 */     return Objects.equals(this.jwkSet, other.jwkSet);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\ReferenceComparisonRefreshJWKSetEvaluator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */