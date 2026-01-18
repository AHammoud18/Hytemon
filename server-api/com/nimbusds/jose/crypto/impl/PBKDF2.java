/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEAlgorithm;
/*     */ import com.nimbusds.jose.util.ByteUtils;
/*     */ import com.nimbusds.jose.util.IntegerUtils;
/*     */ import com.nimbusds.jose.util.StandardCharset;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import javax.crypto.Mac;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.PBEKeySpec;
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
/*     */ public class PBKDF2
/*     */ {
/*     */   public static final int MIN_SALT_LENGTH = 8;
/*  60 */   static final byte[] ZERO_BYTE = new byte[] { 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final long MAX_DERIVED_KEY_LENGTH = 4294967295L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] formatSalt(JWEAlgorithm alg, byte[] salt) throws JOSEException {
/*  86 */     byte[] algBytes = alg.toString().getBytes(StandardCharset.UTF_8);
/*     */     
/*  88 */     if (salt == null) {
/*  89 */       throw new JOSEException("The salt must not be null");
/*     */     }
/*     */     
/*  92 */     if (salt.length < 8) {
/*  93 */       throw new JOSEException("The salt must be at least 8 bytes long");
/*     */     }
/*     */     
/*  96 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/*     */     try {
/*  98 */       out.write(algBytes);
/*  99 */       out.write(ZERO_BYTE);
/* 100 */       out.write(salt);
/* 101 */     } catch (IOException e) {
/* 102 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */     
/* 105 */     return out.toByteArray();
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
/*     */ 
/*     */   
/*     */   public static SecretKey deriveKey(byte[] password, byte[] formattedSalt, int iterationCount, PRFParams prfParams, Provider jcaProvider) throws JOSEException {
/* 133 */     if (formattedSalt == null) {
/* 134 */       throw new JOSEException("The formatted salt must not be null");
/*     */     }
/*     */     
/* 137 */     if (iterationCount < 1) {
/* 138 */       throw new JOSEException("The iteration count must be greater than 0");
/*     */     }
/* 140 */     int keyLengthInBits = ByteUtils.bitLength(prfParams.getDerivedKeyByteLength());
/* 141 */     PBEKeySpec spec = new PBEKeySpec((new String(password, StandardCharsets.UTF_8)).toCharArray(), formattedSalt, iterationCount, keyLengthInBits);
/*     */     try {
/*     */       SecretKeyFactory skf;
/* 144 */       if (jcaProvider != null) {
/* 145 */         skf = SecretKeyFactory.getInstance("PBKDF2With" + prfParams.getMACAlgorithm(), jcaProvider);
/*     */       } else {
/* 147 */         skf = SecretKeyFactory.getInstance("PBKDF2With" + prfParams.getMACAlgorithm());
/*     */       } 
/* 149 */       return new SecretKeySpec(skf.generateSecret(spec).getEncoded(), "AES");
/* 150 */     } catch (NoSuchAlgorithmException|java.security.spec.InvalidKeySpecException ex) {
/* 151 */       throw new JOSEException(ex.getLocalizedMessage(), ex);
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
/*     */   static byte[] extractBlock(byte[] formattedSalt, int iterationCount, int blockIndex, Mac prf) throws JOSEException {
/* 173 */     if (formattedSalt == null) {
/* 174 */       throw new JOSEException("The formatted salt must not be null");
/*     */     }
/*     */     
/* 177 */     if (iterationCount < 1) {
/* 178 */       throw new JOSEException("The iteration count must be greater than 0");
/*     */     }
/*     */ 
/*     */     
/* 182 */     byte[] lastU = null;
/* 183 */     byte[] xorU = null;
/*     */     
/* 185 */     for (int i = 1; i <= iterationCount; i++) {
/*     */       byte[] currentU;
/*     */       
/* 188 */       if (i == 1) {
/*     */         
/* 190 */         byte[] inputBytes = ByteUtils.concat(new byte[][] { formattedSalt, IntegerUtils.toBytes(blockIndex) });
/* 191 */         currentU = prf.doFinal(inputBytes);
/* 192 */         xorU = currentU;
/*     */       }
/*     */       else {
/*     */         
/* 196 */         currentU = prf.doFinal(lastU);
/* 197 */         for (int j = 0; j < currentU.length; j++)
/*     */         {
/* 199 */           xorU[j] = (byte)(currentU[j] ^ xorU[j]);
/*     */         }
/*     */       } 
/*     */       
/* 203 */       lastU = currentU;
/*     */     } 
/* 205 */     return xorU;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\PBKDF2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */