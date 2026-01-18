/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.ChaCha20Poly1305Key;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import com.google.crypto.tink.subtle.Hex;
/*     */ import com.google.crypto.tink.subtle.Random;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Provider;
/*     */ import java.security.spec.AlgorithmParameterSpec;
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
/*     */ @Immutable
/*     */ public final class ChaCha20Poly1305Jce
/*     */   implements Aead
/*     */ {
/*  49 */   private static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */   
/*     */   private static final int NONCE_SIZE_IN_BYTES = 12;
/*     */   
/*     */   private static final int TAG_SIZE_IN_BYTES = 16;
/*     */   
/*     */   private static final int KEY_SIZE_IN_BYTES = 32;
/*     */   
/*     */   private static final String CIPHER_NAME = "ChaCha20-Poly1305";
/*     */   
/*     */   private static final String KEY_NAME = "ChaCha20";
/*  60 */   private static final byte[] testKey = Hex.decode("808182838485868788898a8b8c8d8e8f909192939495969798999a9b9c9d9e9f");
/*  61 */   private static final byte[] testNonce = Hex.decode("070000004041424344454647");
/*     */   
/*  63 */   private static final byte[] testCiphertextOfEmpty = Hex.decode("a0784d7a4716f3feb4f64e7f4b39bf04");
/*     */   
/*     */   private static boolean isValid(Cipher cipher) {
/*     */     try {
/*  67 */       AlgorithmParameterSpec params = new IvParameterSpec(testNonce);
/*  68 */       cipher.init(2, new SecretKeySpec(testKey, "ChaCha20"), params);
/*  69 */       byte[] output = cipher.doFinal(testCiphertextOfEmpty);
/*  70 */       if (output.length != 0) {
/*  71 */         return false;
/*     */       }
/*     */       
/*  74 */       cipher.init(2, new SecretKeySpec(testKey, "ChaCha20"), params);
/*  75 */       byte[] output2 = cipher.doFinal(testCiphertextOfEmpty);
/*  76 */       if (output2.length != 0) {
/*  77 */         return false;
/*     */       }
/*  79 */       return true;
/*  80 */     } catch (GeneralSecurityException ex) {
/*  81 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final SecretKey keySpec;
/*     */ 
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */   
/*     */   private final Provider provider;
/*     */ 
/*     */   
/*     */   private ChaCha20Poly1305Jce(byte[] key, byte[] outputPrefix, Provider provider) throws GeneralSecurityException {
/*  96 */     if (!FIPS.isCompatible()) {
/*  97 */       throw new GeneralSecurityException("Can not use ChaCha20Poly1305 in FIPS-mode.");
/*     */     }
/*  99 */     if (key.length != 32) {
/* 100 */       throw new InvalidKeyException("The key length in bytes must be 32.");
/*     */     }
/* 102 */     this.keySpec = new SecretKeySpec(key, "ChaCha20");
/* 103 */     this.outputPrefix = outputPrefix;
/* 104 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static Aead create(ChaCha20Poly1305Key key) throws GeneralSecurityException {
/* 110 */     Cipher cipher = getValidCipherInstance();
/* 111 */     return new ChaCha20Poly1305Jce(key
/* 112 */         .getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), key
/* 113 */         .getOutputPrefix().toByteArray(), cipher
/* 114 */         .getProvider());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Cipher getValidCipherInstance() throws GeneralSecurityException {
/* 123 */     Cipher cipher = (Cipher)EngineFactory.CIPHER.getInstance("ChaCha20-Poly1305");
/* 124 */     if (!isValid(cipher)) {
/* 125 */       throw new GeneralSecurityException("JCE does not support algorithm: ChaCha20-Poly1305");
/*     */     }
/* 127 */     return cipher;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Cipher getCipherInstance(Provider provider) throws GeneralSecurityException {
/* 137 */     return Cipher.getInstance("ChaCha20-Poly1305", provider);
/*     */   }
/*     */   
/*     */   public static boolean isSupported() {
/*     */     try {
/* 142 */       Cipher unused = getValidCipherInstance();
/* 143 */       return true;
/* 144 */     } catch (GeneralSecurityException ex) {
/* 145 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/* 152 */     if (plaintext == null) {
/* 153 */       throw new NullPointerException("plaintext is null");
/*     */     }
/* 155 */     byte[] nonce = Random.randBytes(12);
/* 156 */     AlgorithmParameterSpec params = new IvParameterSpec(nonce);
/* 157 */     Cipher cipher = getCipherInstance(this.provider);
/* 158 */     cipher.init(1, this.keySpec, params);
/* 159 */     if (associatedData != null && associatedData.length != 0) {
/* 160 */       cipher.updateAAD(associatedData);
/*     */     }
/* 162 */     int outputSize = cipher.getOutputSize(plaintext.length);
/* 163 */     if (outputSize > Integer.MAX_VALUE - this.outputPrefix.length - 12) {
/* 164 */       throw new GeneralSecurityException("plaintext too long");
/*     */     }
/* 166 */     int len = this.outputPrefix.length + 12 + outputSize;
/* 167 */     byte[] output = Arrays.copyOf(this.outputPrefix, len);
/* 168 */     System.arraycopy(nonce, 0, output, this.outputPrefix.length, 12);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     int written = cipher.doFinal(plaintext, 0, plaintext.length, output, this.outputPrefix.length + 12);
/*     */     
/* 177 */     if (written != outputSize) {
/* 178 */       throw new GeneralSecurityException("not enough data written");
/*     */     }
/* 180 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 186 */     if (ciphertext == null) {
/* 187 */       throw new NullPointerException("ciphertext is null");
/*     */     }
/* 189 */     if (ciphertext.length < this.outputPrefix.length + 12 + 16) {
/* 190 */       throw new GeneralSecurityException("ciphertext too short");
/*     */     }
/* 192 */     if (!Util.isPrefix(this.outputPrefix, ciphertext)) {
/* 193 */       throw new GeneralSecurityException("Decryption failed (OutputPrefix mismatch).");
/*     */     }
/*     */     
/* 196 */     byte[] nonce = new byte[12];
/* 197 */     System.arraycopy(ciphertext, this.outputPrefix.length, nonce, 0, 12);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     AlgorithmParameterSpec params = new IvParameterSpec(nonce);
/*     */     
/* 205 */     Cipher cipher = getCipherInstance(this.provider);
/* 206 */     cipher.init(2, this.keySpec, params);
/* 207 */     if (associatedData != null && associatedData.length != 0) {
/* 208 */       cipher.updateAAD(associatedData);
/*     */     }
/* 210 */     int offset = this.outputPrefix.length + 12;
/* 211 */     int len = ciphertext.length - this.outputPrefix.length - 12;
/* 212 */     return cipher.doFinal(ciphertext, offset, len);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\ChaCha20Poly1305Jce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */