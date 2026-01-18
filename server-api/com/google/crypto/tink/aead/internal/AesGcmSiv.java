/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.AesGcmSivKey;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.subtle.Hex;
/*     */ import com.google.crypto.tink.subtle.Random;
/*     */ import com.google.crypto.tink.subtle.Validators;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.GCMParameterSpec;
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
/*     */ public final class AesGcmSiv
/*     */   implements Aead
/*     */ {
/*  50 */   private static final byte[] testPlaintext = Hex.decode("7a806c");
/*  51 */   private static final byte[] testAad = Hex.decode("46bb91c3c5");
/*  52 */   private static final byte[] testKey = Hex.decode("36864200e0eaf5284d884a0e77d31646");
/*  53 */   private static final byte[] testNounce = Hex.decode("bae8e37fc83441b16034566b");
/*  54 */   private static final byte[] testResult = Hex.decode("af60eb711bd85bc1e4d3e0a462e074eea428a8");
/*     */   
/*     */   private static final int IV_SIZE_IN_BYTES = 12;
/*     */   
/*     */   private static final int TAG_SIZE_IN_BYTES = 16;
/*     */   
/*     */   private final ThrowingSupplier<Cipher> cipherSupplier;
/*     */   private final SecretKey keySpec;
/*     */   private final byte[] outputPrefix;
/*     */   
/*     */   public static boolean isAesGcmSivCipher(Cipher cipher) {
/*     */     try {
/*  66 */       AlgorithmParameterSpec params = getParams(testNounce);
/*  67 */       cipher.init(2, new SecretKeySpec(testKey, "AES"), params);
/*  68 */       cipher.updateAAD(testAad);
/*  69 */       byte[] output = cipher.doFinal(testResult, 0, testResult.length);
/*  70 */       return Bytes.equal(output, testPlaintext);
/*  71 */     } catch (GeneralSecurityException ex) {
/*  72 */       return false;
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
/*     */   @AccessesPartialKey
/*     */   public static Aead create(AesGcmSivKey key, ThrowingSupplier<Cipher> cipherSupplier) throws GeneralSecurityException {
/*  99 */     if (!isAesGcmSivCipher(cipherSupplier.get())) {
/* 100 */       throw new IllegalStateException("Cipher does not implement AES GCM SIV.");
/*     */     }
/* 102 */     return new AesGcmSiv(key
/* 103 */         .getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), key
/* 104 */         .getOutputPrefix().toByteArray(), cipherSupplier);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AesGcmSiv(byte[] key, byte[] outputPrefix, ThrowingSupplier<Cipher> cipherSupplier) throws GeneralSecurityException {
/* 110 */     this.outputPrefix = outputPrefix;
/* 111 */     Validators.validateAesKeySize(key.length);
/* 112 */     this.keySpec = new SecretKeySpec(key, "AES");
/* 113 */     this.cipherSupplier = cipherSupplier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/* 123 */     Cipher cipher = this.cipherSupplier.get();
/*     */     
/* 125 */     if (plaintext.length > 2147483619 - this.outputPrefix.length)
/*     */     {
/* 127 */       throw new GeneralSecurityException("plaintext too long");
/*     */     }
/*     */     
/* 130 */     int ciphertextLen = this.outputPrefix.length + 12 + plaintext.length + 16;
/*     */     
/* 132 */     byte[] ciphertext = Arrays.copyOf(this.outputPrefix, ciphertextLen);
/* 133 */     byte[] iv = Random.randBytes(12);
/* 134 */     System.arraycopy(iv, 0, ciphertext, this.outputPrefix.length, 12);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     AlgorithmParameterSpec params = getParams(iv);
/* 142 */     cipher.init(1, this.keySpec, params);
/* 143 */     if (associatedData != null && associatedData.length != 0) {
/* 144 */       cipher.updateAAD(associatedData);
/*     */     }
/*     */     
/* 147 */     int written = cipher.doFinal(plaintext, 0, plaintext.length, ciphertext, this.outputPrefix.length + 12);
/*     */ 
/*     */     
/* 150 */     if (written != plaintext.length + 16) {
/* 151 */       int actualTagSize = written - plaintext.length;
/* 152 */       throw new GeneralSecurityException(
/* 153 */           String.format("encryption failed; AES-GCM-SIV tag must be %s bytes, but got only %s bytes", new Object[] {
/*     */               
/* 155 */               Integer.valueOf(16), Integer.valueOf(actualTagSize) }));
/*     */     } 
/* 157 */     return ciphertext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 167 */     if (ciphertext.length < this.outputPrefix.length + 12 + 16) {
/* 168 */       throw new GeneralSecurityException("ciphertext too short");
/*     */     }
/* 170 */     if (!Util.isPrefix(this.outputPrefix, ciphertext)) {
/* 171 */       throw new GeneralSecurityException("Decryption failed (OutputPrefix mismatch).");
/*     */     }
/* 173 */     Cipher cipher = this.cipherSupplier.get();
/* 174 */     AlgorithmParameterSpec params = getParams(ciphertext, this.outputPrefix.length, 12);
/* 175 */     cipher.init(2, this.keySpec, params);
/* 176 */     if (associatedData != null && associatedData.length != 0) {
/* 177 */       cipher.updateAAD(associatedData);
/*     */     }
/* 179 */     int offset = this.outputPrefix.length + 12;
/* 180 */     int len = ciphertext.length - this.outputPrefix.length - 12;
/* 181 */     return cipher.doFinal(ciphertext, offset, len);
/*     */   }
/*     */   
/*     */   private static AlgorithmParameterSpec getParams(byte[] iv) {
/* 185 */     return getParams(iv, 0, iv.length);
/*     */   }
/*     */   
/*     */   private static AlgorithmParameterSpec getParams(byte[] buf, int offset, int len) {
/* 189 */     return new GCMParameterSpec(128, buf, offset, len);
/*     */   }
/*     */   
/*     */   public static interface ThrowingSupplier<T> {
/*     */     T get() throws GeneralSecurityException;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\AesGcmSiv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */