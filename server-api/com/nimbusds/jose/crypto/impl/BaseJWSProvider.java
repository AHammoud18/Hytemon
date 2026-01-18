/*    */ package com.nimbusds.jose.crypto.impl;
/*    */ 
/*    */ import com.nimbusds.jose.JWSAlgorithm;
/*    */ import com.nimbusds.jose.JWSProvider;
/*    */ import com.nimbusds.jose.jca.JCAContext;
/*    */ import java.util.Collections;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
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
/*    */ public abstract class BaseJWSProvider
/*    */   implements JWSProvider
/*    */ {
/*    */   private final Set<JWSAlgorithm> algs;
/* 48 */   private final JCAContext jcaContext = new JCAContext();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BaseJWSProvider(Set<JWSAlgorithm> algs) {
/* 59 */     this.algs = Collections.unmodifiableSet(Objects.<Set<? extends JWSAlgorithm>>requireNonNull(algs));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<JWSAlgorithm> supportedJWSAlgorithms() {
/* 66 */     return this.algs;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public JCAContext getJCAContext() {
/* 73 */     return this.jcaContext;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\BaseJWSProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */