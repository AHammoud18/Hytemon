/*    */ package com.nimbusds.jose.crypto.impl;
/*    */ 
/*    */ import com.nimbusds.jose.JWSAlgorithm;
/*    */ import com.nimbusds.jose.jwk.Curve;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
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
/*    */ public abstract class EdDSAProvider
/*    */   extends BaseJWSProvider
/*    */ {
/* 62 */   public static final Set<JWSAlgorithm> SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(new HashSet<>(
/*    */         
/* 64 */         Arrays.asList(new JWSAlgorithm[] { JWSAlgorithm.EdDSA, JWSAlgorithm.Ed25519 })));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public static final Set<Curve> SUPPORTED_CURVES = Collections.singleton(Curve.Ed25519);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected EdDSAProvider() {
/* 80 */     super(SUPPORTED_ALGORITHMS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\EdDSAProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */