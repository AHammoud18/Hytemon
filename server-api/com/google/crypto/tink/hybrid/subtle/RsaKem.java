/*     */ package com.google.crypto.tink.hybrid.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.internal.BigIntegerEncoding;
/*     */ import java.math.BigInteger;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Random;
/*     */ import javax.crypto.Cipher;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RsaKem
/*     */ {
/*  32 */   static final byte[] EMPTY_AAD = new byte[0];
/*     */   
/*     */   static final int MIN_RSA_KEY_LENGTH_BITS = 2048;
/*     */ 
/*     */   
/*     */   static void validateRsaModulus(BigInteger mod) throws GeneralSecurityException {
/*  38 */     if (mod.bitLength() < 2048)
/*  39 */       throw new GeneralSecurityException(
/*  40 */           String.format("RSA key must be of at least size %d bits, but got %d", new Object[] {
/*     */               
/*  42 */               Integer.valueOf(2048), Integer.valueOf(mod.bitLength())
/*     */             })); 
/*     */   }
/*     */   
/*     */   static int bigIntSizeInBytes(BigInteger mod) {
/*  47 */     return (mod.bitLength() + 7) / 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] rsaEncrypt(PublicKey publicKey, byte[] x) throws GeneralSecurityException {
/*  57 */     Cipher rsaCipher = Cipher.getInstance("RSA/ECB/NoPadding");
/*  58 */     rsaCipher.init(1, publicKey);
/*     */     try {
/*  60 */       return rsaCipher.doFinal(x);
/*  61 */     } catch (RuntimeException e) {
/*     */ 
/*     */       
/*  64 */       throw new GeneralSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] rsaDecrypt(PrivateKey privateKey, byte[] x) throws GeneralSecurityException {
/*  75 */     Cipher rsaCipher = Cipher.getInstance("RSA/ECB/NoPadding");
/*  76 */     rsaCipher.init(2, privateKey);
/*     */     try {
/*  78 */       return rsaCipher.doFinal(x);
/*  79 */     } catch (RuntimeException e) {
/*     */ 
/*     */       
/*  82 */       throw new GeneralSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] generateSecret(BigInteger max) {
/*     */     BigInteger r;
/*  91 */     int maxSizeInBytes = bigIntSizeInBytes(max);
/*  92 */     Random rand = new SecureRandom();
/*     */     
/*     */     do {
/*  95 */       r = new BigInteger(max.bitLength(), rand);
/*  96 */     } while (r.signum() <= 0 || r.compareTo(max) >= 0);
/*     */     try {
/*  98 */       return BigIntegerEncoding.toBigEndianBytesOfFixedLength(r, maxSizeInBytes);
/*  99 */     } catch (GeneralSecurityException e) {
/*     */       
/* 101 */       throw new IllegalStateException("Unable to convert BigInteger to byte array", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   static KeyPair generateRsaKeyPair(int keySize) {
/*     */     KeyPairGenerator rsaGenerator;
/*     */     try {
/* 108 */       rsaGenerator = KeyPairGenerator.getInstance("RSA");
/* 109 */       rsaGenerator.initialize(keySize);
/* 110 */     } catch (NoSuchAlgorithmException e) {
/* 111 */       throw new IllegalStateException("No support for RSA algorithm.", e);
/*     */     } 
/* 113 */     return rsaGenerator.generateKeyPair();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\hybrid\subtle\RsaKem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */