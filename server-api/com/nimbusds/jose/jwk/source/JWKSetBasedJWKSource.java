/*    */ package com.nimbusds.jose.jwk.source;
/*    */ 
/*    */ import com.nimbusds.jose.KeySourceException;
/*    */ import com.nimbusds.jose.jwk.JWK;
/*    */ import com.nimbusds.jose.jwk.JWKSelector;
/*    */ import com.nimbusds.jose.jwk.JWKSet;
/*    */ import com.nimbusds.jose.proc.SecurityContext;
/*    */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ThreadSafe
/*    */ public class JWKSetBasedJWKSource<C extends SecurityContext>
/*    */   implements JWKSource<C>, Closeable
/*    */ {
/*    */   private final JWKSetSource<C> source;
/*    */   
/*    */   public JWKSetBasedJWKSource(JWKSetSource<C> source) {
/* 55 */     Objects.requireNonNull(source);
/* 56 */     this.source = source;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<JWK> get(JWKSelector jwkSelector, C context) throws KeySourceException {
/* 63 */     long currentTime = System.currentTimeMillis();
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
/* 76 */     JWKSet jwkSet = this.source.getJWKSet(JWKSetCacheRefreshEvaluator.noRefresh(), currentTime, context);
/*    */     
/* 78 */     List<JWK> select = jwkSelector.select(jwkSet);
/* 79 */     if (select.isEmpty()) {
/* 80 */       JWKSet recentJwkSet = this.source.getJWKSet(JWKSetCacheRefreshEvaluator.referenceComparison(jwkSet), currentTime, context);
/* 81 */       select = jwkSelector.select(recentJwkSet);
/*    */     } 
/* 83 */     return select;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JWKSetSource<C> getJWKSetSource() {
/* 92 */     return this.source;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 98 */     this.source.close();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\JWKSetBasedJWKSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */