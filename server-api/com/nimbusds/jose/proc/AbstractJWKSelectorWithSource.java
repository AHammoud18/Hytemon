/*    */ package com.nimbusds.jose.proc;
/*    */ 
/*    */ import com.nimbusds.jose.jwk.source.JWKSource;
/*    */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
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
/*    */ @ThreadSafe
/*    */ abstract class AbstractJWKSelectorWithSource<C extends SecurityContext>
/*    */ {
/*    */   private final JWKSource<C> jwkSource;
/*    */   
/*    */   public AbstractJWKSelectorWithSource(JWKSource<C> jwkSource) {
/* 49 */     this.jwkSource = Objects.<JWKSource<C>>requireNonNull(jwkSource);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JWKSource<C> getJWKSource() {
/* 59 */     return this.jwkSource;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\proc\AbstractJWKSelectorWithSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */