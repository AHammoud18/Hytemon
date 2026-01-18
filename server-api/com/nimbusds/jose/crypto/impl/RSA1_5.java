/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.ByteUtils;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Provider;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
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
/*     */ @ThreadSafe
/*     */ public class RSA1_5
/*     */ {
/*     */   public static byte[] encryptCEK(RSAPublicKey pub, SecretKey cek, Provider provider) throws JOSEException {
/*     */     try {
/*  61 */       Cipher cipher = CipherHelper.getInstance("RSA/ECB/PKCS1Padding", provider);
/*  62 */       cipher.init(1, pub);
/*  63 */       return cipher.doFinal(cek.getEncoded());
/*     */     }
/*  65 */     catch (IllegalBlockSizeException e) {
/*  66 */       throw new JOSEException("RSA block size exception: The RSA key is too short, use a longer one", e);
/*  67 */     } catch (Exception e) {
/*     */ 
/*     */       
/*  70 */       throw new JOSEException("Couldn't encrypt Content Encryption Key (CEK): " + e.getMessage(), e);
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
/*     */ 
/*     */   
/*     */   public static SecretKey decryptCEK(PrivateKey priv, byte[] encryptedCEK, int keyLength, Provider provider) throws JOSEException {
/*     */     try {
/*  96 */       Cipher cipher = CipherHelper.getInstance("RSA/ECB/PKCS1Padding", provider);
/*  97 */       cipher.init(2, priv);
/*  98 */       byte[] secretKeyBytes = cipher.doFinal(encryptedCEK);
/*     */       
/* 100 */       if (ByteUtils.safeBitLength(secretKeyBytes) != keyLength)
/*     */       {
/* 102 */         return null;
/*     */       }
/*     */       
/* 105 */       return new SecretKeySpec(secretKeyBytes, "AES");
/*     */     }
/* 107 */     catch (Exception e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 113 */       throw new JOSEException("Couldn't decrypt Content Encryption Key (CEK): " + e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\RSA1_5.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */