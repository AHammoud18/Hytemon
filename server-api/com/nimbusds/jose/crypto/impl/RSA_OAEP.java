/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.crypto.opts.CipherMode;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Provider;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import javax.crypto.Cipher;
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
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public class RSA_OAEP
/*     */ {
/*     */   private static final String RSA_OEAP_JCA_ALG = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
/*     */   
/*     */   public static byte[] encryptCEK(RSAPublicKey pub, SecretKey cek, CipherMode mode, Provider provider) throws JOSEException {
/*  68 */     assert mode == CipherMode.WRAP_UNWRAP || mode == CipherMode.ENCRYPT_DECRYPT;
/*     */     
/*     */     try {
/*  71 */       Cipher cipher = CipherHelper.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", provider);
/*  72 */       cipher.init(mode.getForJWEEncrypter(), pub, new SecureRandom());
/*  73 */       if (mode == CipherMode.WRAP_UNWRAP) {
/*  74 */         return cipher.wrap(cek);
/*     */       }
/*     */       
/*  77 */       return cipher.doFinal(cek.getEncoded());
/*     */     
/*     */     }
/*  80 */     catch (InvalidKeyException e) {
/*  81 */       throw new JOSEException("RSA block size exception: The RSA key is too short, try a longer one", e);
/*  82 */     } catch (Exception e) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  87 */       throw new JOSEException(e.getMessage(), e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey decryptCEK(PrivateKey priv, byte[] encryptedCEK, CipherMode mode, Provider provider) throws JOSEException {
/* 110 */     assert mode == CipherMode.WRAP_UNWRAP || mode == CipherMode.ENCRYPT_DECRYPT;
/*     */     
/*     */     try {
/* 113 */       Cipher cipher = CipherHelper.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", provider);
/* 114 */       cipher.init(mode.getForJWEDecrypter(), priv);
/*     */       
/* 116 */       if (mode == CipherMode.WRAP_UNWRAP) {
/* 117 */         return (SecretKey)cipher.unwrap(encryptedCEK, "AES", 3);
/*     */       }
/*     */       
/* 120 */       return new SecretKeySpec(cipher.doFinal(encryptedCEK), "AES");
/*     */     
/*     */     }
/* 123 */     catch (Exception e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 129 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\RSA_OAEP.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */