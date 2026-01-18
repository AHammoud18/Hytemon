/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWSAlgorithm;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.Signature;
/*     */ import java.security.spec.MGF1ParameterSpec;
/*     */ import java.security.spec.PSSParameterSpec;
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
/*     */ public class RSASSA
/*     */ {
/*     */   public static Signature getSignerAndVerifier(JWSAlgorithm alg, Provider provider) throws JOSEException {
/*     */     Signature signature;
/*  58 */     if (alg.equals(JWSAlgorithm.RS256) && (
/*  59 */       signature = getSignerAndVerifier("SHA256withRSA", provider)) != null)
/*     */     {
/*  61 */       return signature;
/*     */     }
/*  63 */     if (alg.equals(JWSAlgorithm.RS384) && (
/*  64 */       signature = getSignerAndVerifier("SHA384withRSA", provider)) != null)
/*     */     {
/*  66 */       return signature;
/*     */     }
/*  68 */     if (alg.equals(JWSAlgorithm.RS512) && (
/*  69 */       signature = getSignerAndVerifier("SHA512withRSA", provider)) != null)
/*     */     {
/*  71 */       return signature;
/*     */     }
/*  73 */     if (alg.equals(JWSAlgorithm.PS256) && (
/*  74 */       signature = getSignerAndVerifier("RSASSA-PSS", provider, new PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1))) != null)
/*     */     {
/*  76 */       return signature;
/*     */     }
/*  78 */     if (alg.equals(JWSAlgorithm.PS256) && (
/*  79 */       signature = getSignerAndVerifier("SHA256withRSA/PSS", provider)) != null)
/*     */     {
/*  81 */       return signature;
/*     */     }
/*  83 */     if (alg.equals(JWSAlgorithm.PS256) && (
/*  84 */       signature = getSignerAndVerifier("SHA256withRSAandMGF1", provider)) != null)
/*     */     {
/*  86 */       return signature;
/*     */     }
/*  88 */     if (alg.equals(JWSAlgorithm.PS384) && (
/*  89 */       signature = getSignerAndVerifier("RSASSA-PSS", provider, new PSSParameterSpec("SHA-384", "MGF1", MGF1ParameterSpec.SHA384, 48, 1))) != null)
/*     */     {
/*  91 */       return signature;
/*     */     }
/*  93 */     if (alg.equals(JWSAlgorithm.PS384) && (
/*  94 */       signature = getSignerAndVerifier("SHA384withRSA/PSS", provider)) != null)
/*     */     {
/*  96 */       return signature;
/*     */     }
/*  98 */     if (alg.equals(JWSAlgorithm.PS384) && (
/*  99 */       signature = getSignerAndVerifier("SHA384withRSAandMGF1", provider)) != null)
/*     */     {
/* 101 */       return signature;
/*     */     }
/* 103 */     if (alg.equals(JWSAlgorithm.PS512) && (
/* 104 */       signature = getSignerAndVerifier("RSASSA-PSS", provider, new PSSParameterSpec("SHA-512", "MGF1", MGF1ParameterSpec.SHA512, 64, 1))) != null)
/*     */     {
/* 106 */       return signature;
/*     */     }
/* 108 */     if (alg.equals(JWSAlgorithm.PS512) && (
/* 109 */       signature = getSignerAndVerifier("SHA512withRSA/PSS", provider)) != null)
/*     */     {
/* 111 */       return signature;
/*     */     }
/* 113 */     if (alg.equals(JWSAlgorithm.PS512) && (
/* 114 */       signature = getSignerAndVerifier("SHA512withRSAandMGF1", provider)) != null)
/*     */     {
/* 116 */       return signature;
/*     */     }
/*     */ 
/*     */     
/* 120 */     throw new JOSEException(AlgorithmSupportMessage.unsupportedJWSAlgorithm(alg, RSASSAProvider.SUPPORTED_ALGORITHMS));
/*     */   }
/*     */ 
/*     */   
/*     */   private static Signature getSignerAndVerifier(String jcaAlg, Provider provider) throws JOSEException {
/* 125 */     return getSignerAndVerifier(jcaAlg, provider, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Signature getSignerAndVerifier(String jcaAlg, Provider provider, PSSParameterSpec pssSpec) throws JOSEException {
/*     */     Signature signature;
/*     */     try {
/* 133 */       if (provider != null) {
/* 134 */         signature = Signature.getInstance(jcaAlg, provider);
/*     */       } else {
/* 136 */         signature = Signature.getInstance(jcaAlg);
/*     */       } 
/* 138 */     } catch (NoSuchAlgorithmException ignore) {
/* 139 */       return null;
/*     */     } 
/*     */     
/* 142 */     if (pssSpec != null) {
/*     */       try {
/* 144 */         signature.setParameter(pssSpec);
/* 145 */       } catch (InvalidAlgorithmParameterException e) {
/* 146 */         throw new JOSEException("Invalid RSASSA-PSS salt length parameter: " + e.getMessage(), e);
/*     */       } 
/*     */     }
/*     */     
/* 150 */     return signature;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\RSASSA.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */