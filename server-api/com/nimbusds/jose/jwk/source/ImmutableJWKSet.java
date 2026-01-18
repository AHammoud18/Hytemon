/*    */ package com.nimbusds.jose.jwk.source;
/*    */ 
/*    */ import com.nimbusds.jose.jwk.JWK;
/*    */ import com.nimbusds.jose.jwk.JWKSelector;
/*    */ import com.nimbusds.jose.jwk.JWKSet;
/*    */ import com.nimbusds.jose.proc.SecurityContext;
/*    */ import com.nimbusds.jose.shaded.jcip.Immutable;
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
/*    */ @Immutable
/*    */ public class ImmutableJWKSet<C extends SecurityContext>
/*    */   implements JWKSource<C>
/*    */ {
/*    */   private final JWKSet jwkSet;
/*    */   
/*    */   public ImmutableJWKSet(JWKSet jwkSet) {
/* 54 */     this.jwkSet = Objects.<JWKSet>requireNonNull(jwkSet);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JWKSet getJWKSet() {
/* 64 */     return this.jwkSet;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<JWK> get(JWKSelector jwkSelector, C context) {
/* 74 */     return jwkSelector.select(this.jwkSet);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\source\ImmutableJWKSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */