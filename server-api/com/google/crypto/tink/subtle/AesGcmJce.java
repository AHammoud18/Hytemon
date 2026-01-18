/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.AesGcmKey;
/*     */ import com.google.crypto.tink.aead.internal.AesGcmJceUtil;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ @Immutable
/*     */ public final class AesGcmJce
/*     */   implements Aead
/*     */ {
/*  42 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*     */   private static final int IV_SIZE_IN_BYTES = 12;
/*     */   
/*     */   private static final int TAG_SIZE_IN_BYTES = 16;
/*     */   
/*     */   private final SecretKey keySpec;
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */ 
/*     */   
/*     */   private AesGcmJce(byte[] key, Bytes outputPrefix) throws GeneralSecurityException {
/*  55 */     if (!FIPS.isCompatible()) {
/*  56 */       throw new GeneralSecurityException("Can not use AES-GCM in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */     
/*  59 */     this.keySpec = AesGcmJceUtil.getSecretKey(key);
/*  60 */     this.outputPrefix = outputPrefix.toByteArray();
/*     */   }
/*     */   
/*     */   public AesGcmJce(byte[] key) throws GeneralSecurityException {
/*  64 */     this(key, Bytes.copyFrom(new byte[0]));
/*     */   }
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static Aead create(AesGcmKey key) throws GeneralSecurityException {
/*  69 */     if (key.getParameters().getIvSizeBytes() != 12) {
/*  70 */       throw new GeneralSecurityException("Expected IV Size 12, got " + key
/*  71 */           .getParameters().getIvSizeBytes());
/*     */     }
/*  73 */     if (key.getParameters().getTagSizeBytes() != 16) {
/*  74 */       throw new GeneralSecurityException("Expected tag Size 16, got " + key
/*  75 */           .getParameters().getTagSizeBytes());
/*     */     }
/*     */     
/*  78 */     return new AesGcmJce(key
/*  79 */         .getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), key.getOutputPrefix());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*  89 */     if (plaintext == null) {
/*  90 */       throw new NullPointerException("plaintext is null");
/*     */     }
/*  92 */     byte[] nonce = Random.randBytes(12);
/*  93 */     AlgorithmParameterSpec params = AesGcmJceUtil.getParams(nonce);
/*  94 */     Cipher cipher = AesGcmJceUtil.getThreadLocalCipher();
/*  95 */     cipher.init(1, this.keySpec, params);
/*  96 */     if (associatedData != null && associatedData.length != 0) {
/*  97 */       cipher.updateAAD(associatedData);
/*     */     }
/*  99 */     int outputSize = cipher.getOutputSize(plaintext.length);
/* 100 */     if (outputSize > Integer.MAX_VALUE - this.outputPrefix.length - 12) {
/* 101 */       throw new GeneralSecurityException("plaintext too long");
/*     */     }
/* 103 */     int len = this.outputPrefix.length + 12 + outputSize;
/* 104 */     byte[] output = Arrays.copyOf(this.outputPrefix, len);
/* 105 */     System.arraycopy(nonce, 0, output, this.outputPrefix.length, 12);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     int written = cipher.doFinal(plaintext, 0, plaintext.length, output, this.outputPrefix.length + 12);
/*     */     
/* 114 */     if (written != outputSize) {
/* 115 */       throw new GeneralSecurityException("not enough data written");
/*     */     }
/* 117 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 123 */     if (ciphertext == null) {
/* 124 */       throw new NullPointerException("ciphertext is null");
/*     */     }
/* 126 */     if (ciphertext.length < this.outputPrefix.length + 12 + 16) {
/* 127 */       throw new GeneralSecurityException("ciphertext too short");
/*     */     }
/* 129 */     if (!Util.isPrefix(this.outputPrefix, ciphertext)) {
/* 130 */       throw new GeneralSecurityException("Decryption failed (OutputPrefix mismatch).");
/*     */     }
/*     */ 
/*     */     
/* 134 */     AlgorithmParameterSpec params = AesGcmJceUtil.getParams(ciphertext, this.outputPrefix.length, 12);
/* 135 */     Cipher cipher = AesGcmJceUtil.getThreadLocalCipher();
/* 136 */     cipher.init(2, this.keySpec, params);
/* 137 */     if (associatedData != null && associatedData.length != 0) {
/* 138 */       cipher.updateAAD(associatedData);
/*     */     }
/* 140 */     int offset = this.outputPrefix.length + 12;
/* 141 */     int len = ciphertext.length - this.outputPrefix.length - 12;
/* 142 */     return cipher.doFinal(ciphertext, offset, len);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\AesGcmJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */