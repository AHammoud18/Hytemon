/*     */ package com.nimbusds.jose.crypto.factories;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWSAlgorithm;
/*     */ import com.nimbusds.jose.JWSSigner;
/*     */ import com.nimbusds.jose.crypto.ECDSASigner;
/*     */ import com.nimbusds.jose.crypto.Ed25519Signer;
/*     */ import com.nimbusds.jose.crypto.MACSigner;
/*     */ import com.nimbusds.jose.crypto.RSASSASigner;
/*     */ import com.nimbusds.jose.jca.JCAContext;
/*     */ import com.nimbusds.jose.jwk.ECKey;
/*     */ import com.nimbusds.jose.jwk.JWK;
/*     */ import com.nimbusds.jose.jwk.JWKException;
/*     */ import com.nimbusds.jose.jwk.KeyUse;
/*     */ import com.nimbusds.jose.jwk.OctetKeyPair;
/*     */ import com.nimbusds.jose.jwk.OctetSequenceKey;
/*     */ import com.nimbusds.jose.jwk.RSAKey;
/*     */ import com.nimbusds.jose.produce.JWSSignerFactory;
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
/*     */ public class DefaultJWSSignerFactory
/*     */   implements JWSSignerFactory
/*     */ {
/*  48 */   private final JCAContext jcaContext = new JCAContext();
/*     */ 
/*     */ 
/*     */   
/*     */   public static final Set<JWSAlgorithm> SUPPORTED_ALGORITHMS;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  57 */     Set<JWSAlgorithm> algs = new LinkedHashSet<>();
/*  58 */     algs.addAll(MACSigner.SUPPORTED_ALGORITHMS);
/*  59 */     algs.addAll(RSASSASigner.SUPPORTED_ALGORITHMS);
/*  60 */     algs.addAll(ECDSASigner.SUPPORTED_ALGORITHMS);
/*  61 */     algs.addAll(Ed25519Signer.SUPPORTED_ALGORITHMS);
/*  62 */     SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<JWSAlgorithm> supportedJWSAlgorithms() {
/*  67 */     return SUPPORTED_ALGORITHMS;
/*     */   }
/*     */ 
/*     */   
/*     */   public JCAContext getJCAContext() {
/*  72 */     return this.jcaContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public JWSSigner createJWSSigner(JWK key) throws JOSEException {
/*     */     Ed25519Signer ed25519Signer;
/*  78 */     if (!key.isPrivate()) {
/*  79 */       throw JWKException.expectedPrivate();
/*     */     }
/*     */     
/*  82 */     if (key.getKeyUse() != null && !KeyUse.SIGNATURE.equals(key.getKeyUse())) {
/*  83 */       throw new JWKException("The JWK use must be sig (signature) or unspecified");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     if (key instanceof OctetSequenceKey) {
/*  90 */       MACSigner mACSigner = new MACSigner((OctetSequenceKey)key);
/*  91 */     } else if (key instanceof RSAKey) {
/*  92 */       RSASSASigner rSASSASigner = new RSASSASigner((RSAKey)key);
/*  93 */     } else if (key instanceof ECKey && ECDSASigner.SUPPORTED_CURVES.contains(((ECKey)key).getCurve())) {
/*  94 */       ECDSASigner eCDSASigner = new ECDSASigner((ECKey)key);
/*  95 */     } else if (key instanceof OctetKeyPair && Ed25519Signer.SUPPORTED_CURVES.contains(((OctetKeyPair)key).getCurve())) {
/*  96 */       ed25519Signer = new Ed25519Signer((OctetKeyPair)key);
/*     */     } else {
/*  98 */       throw new JOSEException("Unsupported JWK type and / or curve");
/*     */     } 
/*     */ 
/*     */     
/* 102 */     ed25519Signer.getJCAContext().setSecureRandom(this.jcaContext.getSecureRandom());
/* 103 */     ed25519Signer.getJCAContext().setProvider(this.jcaContext.getProvider());
/*     */     
/* 105 */     return (JWSSigner)ed25519Signer;
/*     */   }
/*     */ 
/*     */   
/*     */   public JWSSigner createJWSSigner(JWK key, JWSAlgorithm alg) throws JOSEException {
/*     */     Ed25519Signer ed25519Signer;
/* 111 */     if (!key.isPrivate()) {
/* 112 */       throw JWKException.expectedPrivate();
/*     */     }
/*     */     
/* 115 */     if (key.getKeyUse() != null && !KeyUse.SIGNATURE.equals(key.getKeyUse())) {
/* 116 */       throw new JWKException("The JWK use must be sig (signature) or unspecified");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     if (MACSigner.SUPPORTED_ALGORITHMS
/* 123 */       .contains(alg) && key instanceof OctetSequenceKey) {
/*     */ 
/*     */       
/* 126 */       MACSigner mACSigner = new MACSigner((OctetSequenceKey)key);
/*     */     }
/* 128 */     else if (RSASSASigner.SUPPORTED_ALGORITHMS
/* 129 */       .contains(alg) && key instanceof RSAKey) {
/*     */ 
/*     */       
/* 132 */       RSASSASigner rSASSASigner = new RSASSASigner((RSAKey)key);
/*     */     }
/* 134 */     else if (ECDSASigner.SUPPORTED_ALGORITHMS
/* 135 */       .contains(alg) && key instanceof ECKey && ECDSASigner.SUPPORTED_CURVES
/*     */       
/* 137 */       .contains(((ECKey)key).getCurve())) {
/*     */       
/* 139 */       ECDSASigner eCDSASigner = new ECDSASigner((ECKey)key);
/*     */     }
/* 141 */     else if (Ed25519Signer.SUPPORTED_ALGORITHMS
/* 142 */       .contains(alg) && key instanceof OctetKeyPair && Ed25519Signer.SUPPORTED_CURVES
/*     */       
/* 144 */       .contains(((OctetKeyPair)key).getCurve())) {
/*     */       
/* 146 */       ed25519Signer = new Ed25519Signer((OctetKeyPair)key);
/*     */     } else {
/*     */       
/* 149 */       throw new JOSEException("Unsupported JWK type, JWK curve and / or JWS algorithm");
/*     */     } 
/*     */ 
/*     */     
/* 153 */     ed25519Signer.getJCAContext().setSecureRandom(this.jcaContext.getSecureRandom());
/* 154 */     ed25519Signer.getJCAContext().setProvider(this.jcaContext.getProvider());
/*     */     
/* 156 */     return (JWSSigner)ed25519Signer;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\factories\DefaultJWSSignerFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */