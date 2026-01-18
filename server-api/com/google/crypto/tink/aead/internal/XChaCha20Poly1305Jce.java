/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.XChaCha20Poly1305Key;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.subtle.Random;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Provider;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
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
/*     */ @Immutable
/*     */ public final class XChaCha20Poly1305Jce
/*     */   implements Aead
/*     */ {
/*  46 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */ 
/*     */   
/*     */   private static final int NONCE_SIZE_IN_BYTES = 24;
/*     */ 
/*     */   
/*     */   private static final int TAG_SIZE_IN_BYTES = 16;
/*     */   
/*     */   private static final int KEY_SIZE_IN_BYTES = 32;
/*     */   
/*     */   private static final String KEY_NAME = "ChaCha20";
/*     */   
/*     */   private final byte[] key;
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */   
/*     */   private final Provider provider;
/*     */ 
/*     */   
/*     */   private XChaCha20Poly1305Jce(byte[] key, byte[] outputPrefix, Provider provider) throws GeneralSecurityException {
/*  66 */     if (!FIPS.isCompatible()) {
/*  67 */       throw new GeneralSecurityException("Can not use ChaCha20Poly1305 in FIPS-mode.");
/*     */     }
/*  69 */     if (key.length != 32) {
/*  70 */       throw new InvalidKeyException("The key length in bytes must be 32.");
/*     */     }
/*  72 */     this.key = key;
/*  73 */     this.outputPrefix = outputPrefix;
/*  74 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static Aead create(XChaCha20Poly1305Key key) throws GeneralSecurityException {
/*  80 */     Cipher cipher = ChaCha20Poly1305Jce.getValidCipherInstance();
/*  81 */     return new XChaCha20Poly1305Jce(key
/*  82 */         .getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), key
/*  83 */         .getOutputPrefix().toByteArray(), cipher
/*  84 */         .getProvider());
/*     */   }
/*     */   
/*     */   public static boolean isSupported() {
/*  88 */     return ChaCha20Poly1305Jce.isSupported();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*  94 */     if (plaintext == null) {
/*  95 */       throw new NullPointerException("plaintext is null");
/*     */     }
/*  97 */     byte[] nonce = Random.randBytes(24);
/*  98 */     byte[] subkey = ChaCha20Util.hChaCha20(this.key, nonce);
/*  99 */     SecretKeySpec keySpec = new SecretKeySpec(subkey, "ChaCha20");
/* 100 */     AlgorithmParameterSpec params = new IvParameterSpec(getChaCha20Nonce(nonce));
/* 101 */     Cipher cipher = ChaCha20Poly1305Jce.getCipherInstance(this.provider);
/* 102 */     cipher.init(1, keySpec, params);
/* 103 */     if (associatedData != null && associatedData.length != 0) {
/* 104 */       cipher.updateAAD(associatedData);
/*     */     }
/* 106 */     int outputSize = cipher.getOutputSize(plaintext.length);
/* 107 */     if (outputSize > Integer.MAX_VALUE - this.outputPrefix.length - 24) {
/* 108 */       throw new GeneralSecurityException("plaintext too long");
/*     */     }
/* 110 */     int len = this.outputPrefix.length + 24 + outputSize;
/* 111 */     byte[] output = Arrays.copyOf(this.outputPrefix, len);
/* 112 */     System.arraycopy(nonce, 0, output, this.outputPrefix.length, 24);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     int written = cipher.doFinal(plaintext, 0, plaintext.length, output, this.outputPrefix.length + 24);
/*     */     
/* 121 */     if (written != outputSize) {
/* 122 */       throw new GeneralSecurityException("not enough data written");
/*     */     }
/* 124 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 130 */     if (ciphertext == null) {
/* 131 */       throw new NullPointerException("ciphertext is null");
/*     */     }
/* 133 */     if (ciphertext.length < this.outputPrefix.length + 24 + 16) {
/* 134 */       throw new GeneralSecurityException("ciphertext too short");
/*     */     }
/* 136 */     if (!Util.isPrefix(this.outputPrefix, ciphertext)) {
/* 137 */       throw new GeneralSecurityException("Decryption failed (OutputPrefix mismatch).");
/*     */     }
/* 139 */     byte[] nonce = new byte[24];
/* 140 */     System.arraycopy(ciphertext, this.outputPrefix.length, nonce, 0, 24);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     byte[] subkey = ChaCha20Util.hChaCha20(this.key, nonce);
/* 147 */     SecretKeySpec keySpec = new SecretKeySpec(subkey, "ChaCha20");
/* 148 */     AlgorithmParameterSpec params = new IvParameterSpec(getChaCha20Nonce(nonce));
/* 149 */     Cipher cipher = ChaCha20Poly1305Jce.getCipherInstance(this.provider);
/* 150 */     cipher.init(2, keySpec, params);
/* 151 */     if (associatedData != null && associatedData.length != 0) {
/* 152 */       cipher.updateAAD(associatedData);
/*     */     }
/* 154 */     int offset = this.outputPrefix.length + 24;
/* 155 */     int len = ciphertext.length - this.outputPrefix.length - 24;
/* 156 */     return cipher.doFinal(ciphertext, offset, len);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] getChaCha20Nonce(byte[] nonce) {
/* 162 */     byte[] chacha20Nonce = new byte[12];
/* 163 */     System.arraycopy(nonce, 16, chacha20Nonce, 4, 8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     return chacha20Nonce;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\XChaCha20Poly1305Jce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */