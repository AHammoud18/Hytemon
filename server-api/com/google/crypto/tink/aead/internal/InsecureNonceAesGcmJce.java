/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InsecureNonceAesGcmJce
/*     */ {
/*  30 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */   
/*     */   public static final int IV_SIZE_IN_BYTES = 12;
/*     */   
/*     */   public static final int TAG_SIZE_IN_BYTES = 16;
/*     */   
/*     */   private final SecretKey keySpec;
/*     */ 
/*     */   
/*     */   public InsecureNonceAesGcmJce(byte[] key) throws GeneralSecurityException {
/*  40 */     if (!FIPS.isCompatible()) {
/*  41 */       throw new GeneralSecurityException("Can not use AES-GCM in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/*  44 */     this.keySpec = AesGcmJceUtil.getSecretKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] iv, byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*  50 */     return encrypt(iv, plaintext, 0, associatedData);
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
/*     */   public byte[] encrypt(byte[] iv, byte[] plaintext, int ciphertextOffset, byte[] associatedData) throws GeneralSecurityException {
/*  62 */     if (iv.length != 12) {
/*  63 */       throw new GeneralSecurityException("iv is wrong size");
/*     */     }
/*  65 */     AlgorithmParameterSpec params = AesGcmJceUtil.getParams(iv);
/*  66 */     Cipher localCipher = AesGcmJceUtil.getThreadLocalCipher();
/*  67 */     localCipher.init(1, this.keySpec, params);
/*  68 */     if (associatedData != null && associatedData.length != 0) {
/*  69 */       localCipher.updateAAD(associatedData);
/*     */     }
/*  71 */     int ciphertextSize = localCipher.getOutputSize(plaintext.length);
/*     */     
/*  73 */     if (ciphertextSize > Integer.MAX_VALUE - ciphertextOffset) {
/*  74 */       throw new GeneralSecurityException("plaintext too long");
/*     */     }
/*  76 */     int outputSize = ciphertextOffset + ciphertextSize;
/*  77 */     byte[] output = new byte[outputSize];
/*  78 */     int written = localCipher.doFinal(plaintext, 0, plaintext.length, output, ciphertextOffset);
/*  79 */     if (written != ciphertextSize) {
/*  80 */       throw new GeneralSecurityException("not enough data written");
/*     */     }
/*  82 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] iv, byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/*  88 */     return decrypt(iv, ciphertext, 0, associatedData);
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
/*     */   public byte[] decrypt(byte[] iv, byte[] ciphertextWithPrefix, int ciphertextOffset, byte[] associatedData) throws GeneralSecurityException {
/* 103 */     if (iv.length != 12) {
/* 104 */       throw new GeneralSecurityException("iv is wrong size");
/*     */     }
/* 106 */     if (ciphertextWithPrefix.length < 16 + ciphertextOffset) {
/* 107 */       throw new GeneralSecurityException("ciphertext too short");
/*     */     }
/* 109 */     AlgorithmParameterSpec params = AesGcmJceUtil.getParams(iv);
/* 110 */     Cipher localCipher = AesGcmJceUtil.getThreadLocalCipher();
/* 111 */     localCipher.init(2, this.keySpec, params);
/* 112 */     if (associatedData != null && associatedData.length != 0) {
/* 113 */       localCipher.updateAAD(associatedData);
/*     */     }
/* 115 */     return localCipher.doFinal(ciphertextWithPrefix, ciphertextOffset, ciphertextWithPrefix.length - ciphertextOffset);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\InsecureNonceAesGcmJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */