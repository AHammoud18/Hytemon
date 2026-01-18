/*    */ package com.nimbusds.jose.jwk.source;
/*    */ 
/*    */ import com.nimbusds.jose.KeySourceException;
/*    */ import com.nimbusds.jose.jwk.JWK;
/*    */ import com.nimbusds.jose.jwk.JWKSelector;
/*    */ import com.nimbusds.jose.proc.SecurityContext;
/*    */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*    */ import com.nimbusds.jose.util.IOUtils;
/*    */ import java.io.Closeable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @ThreadSafe
/*    */ public class JWKSourceWithFailover<C extends SecurityContext>
/*    */   implements JWKSource<C>, Closeable
/*    */ {
/*    */   private final JWKSource<C> jwkSource;
/*    */   private final JWKSource<C> failoverJWKSource;
/*    */   
/*    */   public JWKSourceWithFailover(JWKSource<C> jwkSource, JWKSource<C> failoverJWKSource) {
/* 58 */     Objects.requireNonNull(jwkSource, "The primary JWK source must not be null");
/* 59 */     this.jwkSource = jwkSource;
/* 60 */     this.failoverJWKSource = failoverJWKSource;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private List<JWK> failover(Exception exception, JWKSelector jwkSelector, C context) throws KeySourceException {
/*    */     try {
/* 71 */       return this.failoverJWKSource.get(jwkSelector, context);
/* 72 */     } catch (KeySourceException kse) {
/* 73 */       throw new KeySourceException(exception
/* 74 */           .getMessage() + "; Failover JWK source retrieval failed with: " + kse.getMessage(), kse);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<JWK> get(JWKSelector jwkSelector, C context) throws KeySourceException {
/*    */     try {
/* 85 */       return this.jwkSource.get(jwkSelector, context);
/* 86 */     } catch (Exception e) {
/* 87 */       return failover(e, jwkSelector, context);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {
/* 94 */     if (this.jwkSource instanceof Closeable) {
/* 95 */       IOUtils.closeSilently((Closeable)this.jwkSource);
/*    */     }
/* 97 */     if (this.failoverJWKSource instanceof Closeable)
/* 98 */       IOUtils.closeSilently((Closeable)this.failoverJWKSource); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\JWKSourceWithFailover.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */