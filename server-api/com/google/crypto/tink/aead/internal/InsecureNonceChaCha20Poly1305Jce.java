/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Provider;
/*     */ import java.security.spec.AlgorithmParameterSpec;
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
/*     */ @Immutable
/*     */ public final class InsecureNonceChaCha20Poly1305Jce
/*     */ {
/*  43 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */   
/*     */   private static final int NONCE_SIZE_IN_BYTES = 12;
/*     */   
/*     */   private static final int TAG_SIZE_IN_BYTES = 16;
/*     */   
/*     */   private static final int KEY_SIZE_IN_BYTES = 32;
/*     */   
/*     */   private static final String KEY_NAME = "ChaCha20";
/*     */   
/*     */   private final SecretKey keySpec;
/*     */   
/*     */   private final Provider provider;
/*     */ 
/*     */   
/*     */   private InsecureNonceChaCha20Poly1305Jce(byte[] key, Provider provider) throws GeneralSecurityException {
/*  59 */     if (!FIPS.isCompatible()) {
/*  60 */       throw new GeneralSecurityException("Can not use ChaCha20Poly1305 in FIPS-mode.");
/*     */     }
/*  62 */     if (key.length != 32) {
/*  63 */       throw new InvalidKeyException("The key length in bytes must be 32.");
/*     */     }
/*  65 */     this.keySpec = new SecretKeySpec(key, "ChaCha20");
/*  66 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static InsecureNonceChaCha20Poly1305Jce create(byte[] key) throws GeneralSecurityException {
/*  72 */     Cipher cipher = ChaCha20Poly1305Jce.getValidCipherInstance();
/*  73 */     return new InsecureNonceChaCha20Poly1305Jce(key, cipher.getProvider());
/*     */   }
/*     */   
/*     */   public static boolean isSupported() {
/*  77 */     return ChaCha20Poly1305Jce.isSupported();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] nonce, byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*  83 */     return encrypt(nonce, plaintext, 0, associatedData);
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
/*     */   public byte[] encrypt(byte[] nonce, byte[] plaintext, int ciphertextOffset, byte[] associatedData) throws GeneralSecurityException {
/*  95 */     if (plaintext == null) {
/*  96 */       throw new NullPointerException("plaintext is null");
/*     */     }
/*  98 */     if (nonce.length != 12) {
/*  99 */       throw new GeneralSecurityException("nonce length must be 12 bytes.");
/*     */     }
/* 101 */     AlgorithmParameterSpec params = new IvParameterSpec(nonce);
/* 102 */     Cipher cipher = ChaCha20Poly1305Jce.getCipherInstance(this.provider);
/* 103 */     cipher.init(1, this.keySpec, params);
/* 104 */     if (associatedData != null && associatedData.length != 0) {
/* 105 */       cipher.updateAAD(associatedData);
/*     */     }
/* 107 */     int ciphertextSize = cipher.getOutputSize(plaintext.length);
/* 108 */     if (ciphertextSize > Integer.MAX_VALUE - ciphertextOffset) {
/* 109 */       throw new GeneralSecurityException("plaintext too long");
/*     */     }
/* 111 */     int outputSize = ciphertextOffset + ciphertextSize;
/* 112 */     byte[] output = new byte[outputSize];
/* 113 */     int written = cipher.doFinal(plaintext, 0, plaintext.length, output, ciphertextOffset);
/* 114 */     if (written != ciphertextSize) {
/* 115 */       throw new GeneralSecurityException("not enough data written");
/*     */     }
/* 117 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] nonce, byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 123 */     return decrypt(nonce, ciphertext, 0, associatedData);
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
/*     */   public byte[] decrypt(byte[] nonce, byte[] ciphertextWithPrefix, int ciphertextOffset, byte[] associatedData) throws GeneralSecurityException {
/* 138 */     if (ciphertextWithPrefix == null) {
/* 139 */       throw new NullPointerException("ciphertext is null");
/*     */     }
/* 141 */     if (nonce.length != 12) {
/* 142 */       throw new GeneralSecurityException("nonce length must be 12 bytes.");
/*     */     }
/* 144 */     if (ciphertextWithPrefix.length < ciphertextOffset + 16) {
/* 145 */       throw new GeneralSecurityException("ciphertext too short");
/*     */     }
/* 147 */     AlgorithmParameterSpec params = new IvParameterSpec(nonce);
/*     */     
/* 149 */     Cipher cipher = ChaCha20Poly1305Jce.getCipherInstance(this.provider);
/* 150 */     cipher.init(2, this.keySpec, params);
/* 151 */     if (associatedData != null && associatedData.length != 0) {
/* 152 */       cipher.updateAAD(associatedData);
/*     */     }
/* 154 */     return cipher.doFinal(ciphertextWithPrefix, ciphertextOffset, ciphertextWithPrefix.length - ciphertextOffset);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\InsecureNonceChaCha20Poly1305Jce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */