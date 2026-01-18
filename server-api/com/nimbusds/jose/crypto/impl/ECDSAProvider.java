/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWSAlgorithm;
/*     */ import com.nimbusds.jose.jwk.Curve;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ECDSAProvider
/*     */   extends BaseJWSProvider
/*     */ {
/*     */   public static final Set<JWSAlgorithm> SUPPORTED_ALGORITHMS;
/*     */   public static final Set<Curve> SUPPORTED_CURVES;
/*     */   
/*     */   static {
/*  64 */     Set<JWSAlgorithm> algs = new LinkedHashSet<>();
/*  65 */     algs.add(JWSAlgorithm.ES256);
/*  66 */     algs.add(JWSAlgorithm.ES256K);
/*  67 */     algs.add(JWSAlgorithm.ES384);
/*  68 */     algs.add(JWSAlgorithm.ES512);
/*  69 */     SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);
/*     */     
/*  71 */     Set<Curve> curves = new LinkedHashSet<>();
/*  72 */     curves.add(Curve.P_256);
/*  73 */     curves.add(Curve.SECP256K1);
/*  74 */     curves.add(Curve.P_384);
/*  75 */     curves.add(Curve.P_521);
/*  76 */     SUPPORTED_CURVES = Collections.unmodifiableSet(curves);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ECDSAProvider(JWSAlgorithm alg) throws JOSEException {
/*  92 */     super(Collections.singleton(alg));
/*     */     
/*  94 */     if (!SUPPORTED_ALGORITHMS.contains(alg)) {
/*  95 */       throw new JOSEException("Unsupported EC DSA algorithm: " + alg);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JWSAlgorithm supportedECDSAAlgorithm() {
/* 109 */     return supportedJWSAlgorithms().iterator().next();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\ECDSAProvider.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */