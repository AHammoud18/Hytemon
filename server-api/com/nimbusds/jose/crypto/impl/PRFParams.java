/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import java.security.Provider;
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public final class PRFParams
/*     */ {
/*     */   private final String jcaMacAlg;
/*     */   private final Provider macProvider;
/*     */   private final int dkLen;
/*     */   
/*     */   public PRFParams(String jcaMacAlg, Provider macProvider, int dkLen) {
/*  69 */     this.jcaMacAlg = Objects.<String>requireNonNull(jcaMacAlg);
/*  70 */     this.macProvider = macProvider;
/*  71 */     this.dkLen = dkLen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMACAlgorithm() {
/*  82 */     return this.jcaMacAlg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Provider getMacProvider() {
/*  93 */     return this.macProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDerivedKeyByteLength() {
/* 104 */     return this.dkLen;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PRFParams resolve(JWEAlgorithm alg, Provider macProvider) throws JOSEException {
/*     */     String jcaMagAlg;
/*     */     int dkLen;
/* 127 */     if (JWEAlgorithm.PBES2_HS256_A128KW.equals(alg)) {
/* 128 */       jcaMagAlg = "HmacSHA256";
/* 129 */       dkLen = 16;
/* 130 */     } else if (JWEAlgorithm.PBES2_HS384_A192KW.equals(alg)) {
/* 131 */       jcaMagAlg = "HmacSHA384";
/* 132 */       dkLen = 24;
/* 133 */     } else if (JWEAlgorithm.PBES2_HS512_A256KW.equals(alg)) {
/* 134 */       jcaMagAlg = "HmacSHA512";
/* 135 */       dkLen = 32;
/*     */     } else {
/* 137 */       throw new JOSEException(AlgorithmSupportMessage.unsupportedJWEAlgorithm(alg, PasswordBasedCryptoProvider.SUPPORTED_ALGORITHMS));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 142 */     return new PRFParams(jcaMagAlg, macProvider, dkLen);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\PRFParams.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */