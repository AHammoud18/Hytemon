/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import javax.crypto.Mac;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
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
/*     */ @ThreadSafe
/*     */ public class HMAC
/*     */ {
/*     */   public static Mac getInitMac(SecretKey secretKey, Provider provider) throws JOSEException {
/*  61 */     return getInitMac(secretKey.getAlgorithm(), secretKey, provider);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static Mac getInitMac(String alg, SecretKey secretKey, Provider provider) throws JOSEException {
/*     */     Mac mac;
/*     */     try {
/*  87 */       if (provider != null) {
/*  88 */         mac = Mac.getInstance(alg, provider);
/*     */       } else {
/*  90 */         mac = Mac.getInstance(alg);
/*     */       } 
/*     */       
/*  93 */       mac.init(secretKey);
/*     */     }
/*  95 */     catch (NoSuchAlgorithmException e) {
/*  96 */       throw new JOSEException("Unsupported HMAC algorithm: " + e.getMessage(), e);
/*  97 */     } catch (InvalidKeyException e) {
/*  98 */       throw new JOSEException("Invalid HMAC key: " + e.getMessage(), e);
/*     */     } 
/*     */     
/* 101 */     return mac;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static byte[] compute(String alg, byte[] secret, byte[] message, Provider provider) throws JOSEException {
/* 127 */     return compute(alg, new SecretKeySpec(secret, alg), message, provider);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] compute(String alg, SecretKey secretKey, byte[] message, Provider provider) throws JOSEException {
/* 153 */     Mac mac = getInitMac(alg, secretKey, provider);
/* 154 */     mac.update(message);
/* 155 */     return mac.doFinal();
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
/*     */ 
/*     */   
/*     */   public static byte[] compute(SecretKey secretKey, byte[] message, Provider provider) throws JOSEException {
/* 178 */     return compute(secretKey.getAlgorithm(), secretKey, message, provider);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\HMAC.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */