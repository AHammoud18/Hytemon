/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.ByteUtils;
/*     */ import com.nimbusds.jose.util.Container;
/*     */ import com.nimbusds.jose.util.KeyUtils;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.spec.InvalidParameterSpecException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.GCMParameterSpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class AESGCM
/*     */ {
/*     */   public static final int IV_BIT_LENGTH = 96;
/*     */   public static final int AUTH_TAG_BIT_LENGTH = 128;
/*     */   
/*     */   public static byte[] generateIV(SecureRandom randomGen) {
/*  73 */     byte[] bytes = new byte[12];
/*  74 */     randomGen.nextBytes(bytes);
/*  75 */     return bytes;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthenticatedCipherText encrypt(SecretKey secretKey, Container<byte[]> ivContainer, byte[] plainText, byte[] authData, Provider provider) throws JOSEException {
/*     */     Cipher cipher;
/*     */     byte[] cipherOutput;
/* 109 */     SecretKey aesKey = KeyUtils.toAESKey(secretKey);
/*     */ 
/*     */ 
/*     */     
/* 113 */     byte[] iv = (byte[])ivContainer.get();
/*     */     
/*     */     try {
/* 116 */       if (provider != null) {
/* 117 */         cipher = Cipher.getInstance("AES/GCM/NoPadding", provider);
/*     */       } else {
/* 119 */         cipher = Cipher.getInstance("AES/GCM/NoPadding");
/*     */       } 
/*     */       
/* 122 */       GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
/* 123 */       cipher.init(1, aesKey, gcmSpec);
/*     */     }
/* 125 */     catch (NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException|java.security.InvalidKeyException|java.security.InvalidAlgorithmParameterException e) {
/* 126 */       throw new JOSEException("Couldn't create AES/GCM/NoPadding cipher: " + e.getMessage(), e);
/*     */     } 
/*     */     
/* 129 */     cipher.updateAAD(authData);
/*     */ 
/*     */     
/*     */     try {
/* 133 */       cipherOutput = cipher.doFinal(plainText);
/* 134 */     } catch (IllegalBlockSizeException|javax.crypto.BadPaddingException e) {
/* 135 */       throw new JOSEException("Couldn't encrypt with AES/GCM/NoPadding: " + e.getMessage(), e);
/*     */     } 
/*     */     
/* 138 */     int tagPos = cipherOutput.length - ByteUtils.byteLength(128);
/*     */     
/* 140 */     byte[] cipherText = ByteUtils.subArray(cipherOutput, 0, tagPos);
/* 141 */     byte[] authTag = ByteUtils.subArray(cipherOutput, tagPos, ByteUtils.byteLength(128));
/*     */ 
/*     */     
/* 144 */     ivContainer.set(actualIVOf(cipher));
/*     */     
/* 146 */     return new AuthenticatedCipherText(cipherText, authTag);
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
/*     */   private static byte[] actualIVOf(Cipher cipher) throws JOSEException {
/* 168 */     GCMParameterSpec actualParams = actualParamsOf(cipher);
/*     */     
/* 170 */     byte[] iv = actualParams.getIV();
/* 171 */     int tLen = actualParams.getTLen();
/*     */     
/* 173 */     validate(iv, tLen);
/*     */     
/* 175 */     return iv;
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
/*     */   private static void validate(byte[] iv, int authTagLength) throws JOSEException {
/* 197 */     if (ByteUtils.safeBitLength(iv) != 96) {
/* 198 */       throw new JOSEException(String.format("IV length of %d bits is required, got %d", new Object[] { Integer.valueOf(96), Integer.valueOf(ByteUtils.safeBitLength(iv)) }));
/*     */     }
/*     */     
/* 201 */     if (authTagLength != 128) {
/* 202 */       throw new JOSEException(String.format("Authentication tag length of %d bits is required, got %d", new Object[] { Integer.valueOf(128), Integer.valueOf(authTagLength) }));
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
/*     */   private static GCMParameterSpec actualParamsOf(Cipher cipher) throws JOSEException {
/* 225 */     AlgorithmParameters algorithmParameters = cipher.getParameters();
/*     */     
/* 227 */     if (algorithmParameters == null) {
/* 228 */       throw new JOSEException("AES GCM ciphers are expected to make use of algorithm parameters");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 233 */       return algorithmParameters.<GCMParameterSpec>getParameterSpec(GCMParameterSpec.class);
/* 234 */     } catch (InvalidParameterSpecException shouldNotHappen) {
/* 235 */       throw new JOSEException(shouldNotHappen.getMessage(), shouldNotHappen);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decrypt(SecretKey secretKey, byte[] iv, byte[] cipherText, byte[] authData, byte[] authTag, Provider provider) throws JOSEException {
/*     */     Cipher cipher;
/* 265 */     SecretKey aesKey = KeyUtils.toAESKey(secretKey);
/*     */ 
/*     */     
/*     */     try {
/* 269 */       if (provider != null) {
/* 270 */         cipher = Cipher.getInstance("AES/GCM/NoPadding", provider);
/*     */       } else {
/* 272 */         cipher = Cipher.getInstance("AES/GCM/NoPadding");
/*     */       } 
/*     */       
/* 275 */       GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
/* 276 */       cipher.init(2, aesKey, gcmSpec);
/*     */     }
/* 278 */     catch (NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException|java.security.InvalidKeyException|java.security.InvalidAlgorithmParameterException e) {
/* 279 */       throw new JOSEException("Couldn't create AES/GCM/NoPadding cipher: " + e.getMessage(), e);
/*     */     } 
/*     */     
/* 282 */     cipher.updateAAD(authData);
/*     */     
/*     */     try {
/* 285 */       return cipher.doFinal(ByteUtils.concat(new byte[][] { cipherText, authTag }));
/* 286 */     } catch (IllegalBlockSizeException|javax.crypto.BadPaddingException e) {
/* 287 */       throw new JOSEException("AES/GCM/NoPadding decryption failed: " + e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\AESGCM.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */