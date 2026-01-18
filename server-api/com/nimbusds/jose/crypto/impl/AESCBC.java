/*     */ package com.nimbusds.jose.crypto.impl;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEException;
/*     */ import com.nimbusds.jose.JWEHeader;
/*     */ import com.nimbusds.jose.crypto.utils.ConstantTimeUtils;
/*     */ import com.nimbusds.jose.shaded.jcip.ThreadSafe;
/*     */ import com.nimbusds.jose.util.Base64URL;
/*     */ import com.nimbusds.jose.util.ByteUtils;
/*     */ import com.nimbusds.jose.util.StandardCharset;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.Provider;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.IvParameterSpec;
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
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ public class AESCBC
/*     */ {
/*     */   public static final int IV_BIT_LENGTH = 128;
/*     */   
/*     */   public static byte[] generateIV(SecureRandom randomGen) {
/*  74 */     byte[] bytes = new byte[ByteUtils.byteLength(128)];
/*  75 */     randomGen.nextBytes(bytes);
/*  76 */     return bytes;
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
/*     */   private static Cipher createAESCBCCipher(SecretKey secretKey, boolean forEncryption, byte[] iv, Provider provider) throws JOSEException {
/*     */     Cipher cipher;
/*     */     try {
/* 102 */       cipher = CipherHelper.getInstance("AES/CBC/PKCS5Padding", provider);
/*     */       
/* 104 */       SecretKeySpec keyspec = new SecretKeySpec(secretKey.getEncoded(), "AES");
/*     */       
/* 106 */       IvParameterSpec ivSpec = new IvParameterSpec(iv);
/*     */       
/* 108 */       if (forEncryption)
/*     */       {
/* 110 */         cipher.init(1, keyspec, ivSpec);
/*     */       }
/*     */       else
/*     */       {
/* 114 */         cipher.init(2, keyspec, ivSpec);
/*     */       }
/*     */     
/* 117 */     } catch (Exception e) {
/*     */       
/* 119 */       throw new JOSEException(e.getMessage(), e);
/*     */     } 
/*     */     
/* 122 */     return cipher;
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
/*     */   public static byte[] encrypt(SecretKey secretKey, byte[] iv, byte[] plainText, Provider provider) throws JOSEException {
/* 145 */     Cipher cipher = createAESCBCCipher(secretKey, true, iv, provider);
/*     */     
/*     */     try {
/* 148 */       return cipher.doFinal(plainText);
/*     */     }
/* 150 */     catch (Exception e) {
/*     */       
/* 152 */       throw new JOSEException(e.getMessage(), e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthenticatedCipherText encryptAuthenticated(SecretKey secretKey, byte[] iv, byte[] plainText, byte[] aad, Provider ceProvider, Provider macProvider) throws JOSEException {
/* 190 */     CompositeKey compositeKey = new CompositeKey(secretKey);
/*     */ 
/*     */     
/* 193 */     byte[] cipherText = encrypt(compositeKey.getAESKey(), iv, plainText, ceProvider);
/*     */ 
/*     */     
/* 196 */     byte[] al = AAD.computeLength(aad);
/*     */ 
/*     */     
/* 199 */     int hmacInputLength = aad.length + iv.length + cipherText.length + al.length;
/* 200 */     byte[] hmacInput = ByteBuffer.allocate(hmacInputLength).put(aad).put(iv).put(cipherText).put(al).array();
/* 201 */     byte[] hmac = HMAC.compute(compositeKey.getMACKey(), hmacInput, macProvider);
/* 202 */     byte[] authTag = Arrays.copyOf(hmac, compositeKey.getTruncatedMACByteLength());
/*     */     
/* 204 */     return new AuthenticatedCipherText(cipherText, authTag);
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
/*     */   
/*     */   public static AuthenticatedCipherText encryptWithConcatKDF(JWEHeader header, SecretKey secretKey, Base64URL encryptedKey, byte[] iv, byte[] plainText, Provider ceProvider, Provider macProvider) throws JOSEException {
/* 237 */     byte[] epu = null;
/*     */     
/* 239 */     if (header.getCustomParam("epu") instanceof String)
/*     */     {
/* 241 */       epu = (new Base64URL((String)header.getCustomParam("epu"))).decode();
/*     */     }
/*     */     
/* 244 */     byte[] epv = null;
/*     */     
/* 246 */     if (header.getCustomParam("epv") instanceof String)
/*     */     {
/* 248 */       epv = (new Base64URL((String)header.getCustomParam("epv"))).decode();
/*     */     }
/*     */ 
/*     */     
/* 252 */     SecretKey altCEK = LegacyConcatKDF.generateCEK(secretKey, header.getEncryptionMethod(), epu, epv);
/*     */     
/* 254 */     byte[] cipherText = encrypt(altCEK, iv, plainText, ceProvider);
/*     */ 
/*     */     
/* 257 */     SecretKey cik = LegacyConcatKDF.generateCIK(secretKey, header.getEncryptionMethod(), epu, epv);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 262 */     String macInput = header.toBase64URL() + "." + encryptedKey + "." + Base64URL.encode(iv) + "." + Base64URL.encode(cipherText);
/*     */     
/* 264 */     byte[] mac = HMAC.compute(cik, macInput.getBytes(StandardCharset.UTF_8), macProvider);
/*     */     
/* 266 */     return new AuthenticatedCipherText(cipherText, mac);
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
/*     */   public static byte[] decrypt(SecretKey secretKey, byte[] iv, byte[] cipherText, Provider provider) throws JOSEException {
/* 289 */     Cipher cipher = createAESCBCCipher(secretKey, false, iv, provider);
/*     */     
/*     */     try {
/* 292 */       return cipher.doFinal(cipherText);
/*     */     }
/* 294 */     catch (Exception e) {
/*     */       
/* 296 */       throw new JOSEException(e.getMessage(), e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decryptAuthenticated(SecretKey secretKey, byte[] iv, byte[] cipherText, byte[] aad, byte[] authTag, Provider ceProvider, Provider macProvider) throws JOSEException {
/* 337 */     CompositeKey compositeKey = new CompositeKey(secretKey);
/*     */ 
/*     */     
/* 340 */     byte[] al = AAD.computeLength(aad);
/*     */ 
/*     */     
/* 343 */     int hmacInputLength = aad.length + iv.length + cipherText.length + al.length;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     byte[] hmacInput = ByteBuffer.allocate(hmacInputLength).put(aad).put(iv).put(cipherText).put(al).array();
/* 350 */     byte[] hmac = HMAC.compute(compositeKey.getMACKey(), hmacInput, macProvider);
/*     */     
/* 352 */     byte[] expectedAuthTag = Arrays.copyOf(hmac, compositeKey.getTruncatedMACByteLength());
/*     */     
/* 354 */     if (!ConstantTimeUtils.areEqual(expectedAuthTag, authTag)) {
/* 355 */       throw new JOSEException("MAC check failed");
/*     */     }
/*     */     
/* 358 */     return decrypt(compositeKey.getAESKey(), iv, cipherText, ceProvider);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decryptWithConcatKDF(JWEHeader header, SecretKey secretKey, Base64URL encryptedKey, Base64URL iv, Base64URL cipherText, Base64URL authTag, Provider ceProvider, Provider macProvider) throws JOSEException {
/* 393 */     byte[] epu = null;
/*     */     
/* 395 */     if (header.getCustomParam("epu") instanceof String)
/*     */     {
/* 397 */       epu = (new Base64URL((String)header.getCustomParam("epu"))).decode();
/*     */     }
/*     */     
/* 400 */     byte[] epv = null;
/*     */     
/* 402 */     if (header.getCustomParam("epv") instanceof String)
/*     */     {
/* 404 */       epv = (new Base64URL((String)header.getCustomParam("epv"))).decode();
/*     */     }
/*     */     
/* 407 */     SecretKey cik = LegacyConcatKDF.generateCIK(secretKey, header.getEncryptionMethod(), epu, epv);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 412 */     String macInput = header.toBase64URL().toString() + "." + encryptedKey.toString() + "." + iv.toString() + "." + cipherText.toString();
/*     */     
/* 414 */     byte[] mac = HMAC.compute(cik, macInput.getBytes(StandardCharset.UTF_8), macProvider);
/*     */     
/* 416 */     if (!ConstantTimeUtils.areEqual(authTag.decode(), mac)) {
/* 417 */       throw new JOSEException("MAC check failed");
/*     */     }
/*     */     
/* 420 */     SecretKey cekAlt = LegacyConcatKDF.generateCEK(secretKey, header.getEncryptionMethod(), epu, epv);
/*     */     
/* 422 */     return decrypt(cekAlt, iv.decode(), cipherText.decode(), ceProvider);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\crypto\impl\AESCBC.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */