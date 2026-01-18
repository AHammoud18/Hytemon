/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.XAesGcmKey;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.prf.AesCmacPrfKey;
/*     */ import com.google.crypto.tink.prf.AesCmacPrfParameters;
/*     */ import com.google.crypto.tink.prf.Prf;
/*     */ import com.google.crypto.tink.subtle.PrfAesCmac;
/*     */ import com.google.crypto.tink.subtle.Random;
/*     */ import com.google.crypto.tink.util.Bytes;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class XAesGcm
/*     */   implements Aead
/*     */ {
/*     */   private static final int IV_SIZE_IN_BYTES = 12;
/*     */   private static final int TAG_SIZE_IN_BYTES = 16;
/*     */   private static final int DERIVED_KEY_SIZE_IN_BYTES = 32;
/*     */   private static final int MIN_SALT_SIZE_IN_BYTES = 8;
/*     */   private static final int MAX_SALT_SIZE_IN_BYTES = 12;
/*     */   private final byte[] outputPrefix;
/*     */   private final int saltSize;
/*     */   private final Prf cmac;
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static Prf createCmac(byte[] key) throws GeneralSecurityException {
/*  60 */     return PrfAesCmac.create(
/*  61 */         AesCmacPrfKey.create(
/*  62 */           AesCmacPrfParameters.create(key.length), 
/*  63 */           SecretBytes.copyFrom(key, InsecureSecretKeyAccess.get())));
/*     */   }
/*     */ 
/*     */   
/*     */   private XAesGcm(byte[] key, Bytes outputPrefix, int saltSize) throws GeneralSecurityException {
/*  68 */     this.cmac = createCmac(key);
/*  69 */     this.outputPrefix = outputPrefix.toByteArray();
/*  70 */     this.saltSize = saltSize;
/*     */   }
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static Aead create(XAesGcmKey key) throws GeneralSecurityException {
/*  75 */     if (key.getParameters().getSaltSizeBytes() < 8 || key
/*  76 */       .getParameters().getSaltSizeBytes() > 12) {
/*  77 */       throw new GeneralSecurityException("invalid salt size");
/*     */     }
/*  79 */     return new XAesGcm(key
/*  80 */         .getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), key
/*  81 */         .getOutputPrefix(), key
/*  82 */         .getParameters().getSaltSizeBytes());
/*     */   }
/*     */   
/*     */   private byte[] derivePerMessageKey(byte[] salt) throws GeneralSecurityException {
/*  86 */     byte[] derivationBlock1 = { 0, 1, 88, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*  87 */     byte[] derivationBlock2 = { 0, 2, 88, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*  88 */     if (salt.length > 12 || salt.length < 8) {
/*  89 */       throw new GeneralSecurityException("invalid salt size");
/*     */     }
/*  91 */     System.arraycopy(salt, 0, derivationBlock1, 4, salt.length);
/*  92 */     System.arraycopy(salt, 0, derivationBlock2, 4, salt.length);
/*     */     
/*  94 */     byte[] key = new byte[32];
/*  95 */     System.arraycopy(this.cmac.compute(derivationBlock1, 16), 0, key, 0, 16);
/*  96 */     System.arraycopy(this.cmac.compute(derivationBlock2, 16), 0, key, 16, 16);
/*  97 */     return key;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/* 103 */     if (plaintext == null) {
/* 104 */       throw new NullPointerException("plaintext is null");
/*     */     }
/* 106 */     byte[] saltAndIv = Random.randBytes(this.saltSize + 12);
/* 107 */     byte[] salt = Arrays.copyOf(saltAndIv, this.saltSize);
/* 108 */     byte[] iv = Arrays.copyOfRange(saltAndIv, this.saltSize, this.saltSize + 12);
/*     */     
/* 110 */     InsecureNonceAesGcmJce gcm = new InsecureNonceAesGcmJce(derivePerMessageKey(salt));
/*     */ 
/*     */     
/* 113 */     byte[] ciphertext = gcm.encrypt(iv, plaintext, this.outputPrefix.length + this.saltSize + iv.length, associatedData);
/*     */ 
/*     */     
/* 116 */     System.arraycopy(this.outputPrefix, 0, ciphertext, 0, this.outputPrefix.length);
/* 117 */     System.arraycopy(saltAndIv, 0, ciphertext, this.outputPrefix.length, saltAndIv.length);
/*     */     
/* 119 */     return ciphertext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 125 */     if (ciphertext == null) {
/* 126 */       throw new NullPointerException("ciphertext is null");
/*     */     }
/* 128 */     if (ciphertext.length < this.outputPrefix.length + this.saltSize + 12 + 16) {
/* 129 */       throw new GeneralSecurityException("ciphertext too short");
/*     */     }
/* 131 */     if (!Util.isPrefix(this.outputPrefix, ciphertext)) {
/* 132 */       throw new GeneralSecurityException("Decryption failed (OutputPrefix mismatch).");
/*     */     }
/* 134 */     int prefixAndSaltSize = this.outputPrefix.length + this.saltSize;
/*     */ 
/*     */     
/* 137 */     InsecureNonceAesGcmJce gcm = new InsecureNonceAesGcmJce(derivePerMessageKey(
/* 138 */           Arrays.copyOfRange(ciphertext, this.outputPrefix.length, prefixAndSaltSize)));
/* 139 */     return gcm.decrypt(
/* 140 */         Arrays.copyOfRange(ciphertext, prefixAndSaltSize, prefixAndSaltSize + 12), ciphertext, prefixAndSaltSize + 12, associatedData);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\XAesGcm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */