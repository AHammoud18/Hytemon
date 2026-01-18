/*    */ package com.nimbusds.jose.jwk.source;
/*    */ 
/*    */ import com.nimbusds.jose.jwk.JWKSet;
/*    */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*    */ import java.util.Date;
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
/*    */ @Deprecated
/*    */ @Immutable
/*    */ public final class JWKSetWithTimestamp
/*    */ {
/*    */   private final JWKSet jwkSet;
/*    */   private final Date timestamp;
/*    */   
/*    */   public JWKSetWithTimestamp(JWKSet jwkSet) {
/* 50 */     this(jwkSet, new Date());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JWKSetWithTimestamp(JWKSet jwkSet, Date timestamp) {
/* 61 */     this.jwkSet = Objects.<JWKSet>requireNonNull(jwkSet);
/* 62 */     this.timestamp = Objects.<Date>requireNonNull(timestamp);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JWKSet getJWKSet() {
/* 72 */     return this.jwkSet;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Date getDate() {
/* 82 */     return this.timestamp;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\JWKSetWithTimestamp.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */