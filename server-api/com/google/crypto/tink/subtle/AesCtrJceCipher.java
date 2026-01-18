/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ 
/*     */ 
/*     */ public final class AesCtrJceCipher
/*     */   implements IndCpaCipher
/*     */ {
/*  36 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_REQUIRES_BORINGCRYPTO;
/*     */ 
/*     */   
/*  39 */   private static final ThreadLocal<Cipher> localCipher = new ThreadLocal<Cipher>()
/*     */     {
/*     */       protected Cipher initialValue()
/*     */       {
/*     */         try {
/*  44 */           return EngineFactory.CIPHER.getInstance("AES/CTR/NoPadding");
/*  45 */         } catch (GeneralSecurityException ex) {
/*  46 */           throw new IllegalStateException(ex);
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String KEY_ALGORITHM = "AES";
/*     */ 
/*     */   
/*     */   private static final String CIPHER_ALGORITHM = "AES/CTR/NoPadding";
/*     */   
/*     */   private static final int MIN_IV_SIZE_IN_BYTES = 12;
/*     */   
/*     */   private final SecretKeySpec keySpec;
/*     */   
/*     */   private final int ivSize;
/*     */   
/*     */   private final int blockSize;
/*     */ 
/*     */   
/*     */   public AesCtrJceCipher(byte[] key, int ivSize) throws GeneralSecurityException {
/*  68 */     if (!FIPS.isCompatible()) {
/*  69 */       throw new GeneralSecurityException("Can not use AES-CTR in FIPS-mode, as BoringCrypto module is not available.");
/*     */     }
/*     */ 
/*     */     
/*  73 */     Validators.validateAesKeySize(key.length);
/*  74 */     this.keySpec = new SecretKeySpec(key, "AES");
/*  75 */     this.blockSize = ((Cipher)localCipher.get()).getBlockSize();
/*  76 */     if (ivSize < 12 || ivSize > this.blockSize) {
/*  77 */       throw new GeneralSecurityException("invalid IV size");
/*     */     }
/*  79 */     this.ivSize = ivSize;
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
/*     */   public byte[] encrypt(byte[] plaintext) throws GeneralSecurityException {
/*  91 */     if (plaintext.length > Integer.MAX_VALUE - this.ivSize) {
/*  92 */       throw new GeneralSecurityException("plaintext length can not exceed " + (Integer.MAX_VALUE - this.ivSize));
/*     */     }
/*     */     
/*  95 */     byte[] ciphertext = new byte[this.ivSize + plaintext.length];
/*  96 */     byte[] iv = Random.randBytes(this.ivSize);
/*  97 */     System.arraycopy(iv, 0, ciphertext, 0, this.ivSize);
/*  98 */     doCtr(plaintext, 0, plaintext.length, ciphertext, this.ivSize, iv, true);
/*  99 */     return ciphertext;
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
/*     */   public byte[] decrypt(byte[] ciphertext) throws GeneralSecurityException {
/* 111 */     if (ciphertext.length < this.ivSize) {
/* 112 */       throw new GeneralSecurityException("ciphertext too short");
/*     */     }
/* 114 */     byte[] iv = new byte[this.ivSize];
/* 115 */     System.arraycopy(ciphertext, 0, iv, 0, this.ivSize);
/* 116 */     byte[] plaintext = new byte[ciphertext.length - this.ivSize];
/* 117 */     doCtr(ciphertext, this.ivSize, ciphertext.length - this.ivSize, plaintext, 0, iv, false);
/* 118 */     return plaintext;
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
/*     */   private void doCtr(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset, byte[] iv, boolean encrypt) throws GeneralSecurityException {
/* 130 */     Cipher cipher = localCipher.get();
/*     */     
/* 132 */     byte[] counter = new byte[this.blockSize];
/* 133 */     System.arraycopy(iv, 0, counter, 0, this.ivSize);
/*     */     
/* 135 */     IvParameterSpec paramSpec = new IvParameterSpec(counter);
/* 136 */     if (encrypt) {
/* 137 */       cipher.init(1, this.keySpec, paramSpec);
/*     */     } else {
/* 139 */       cipher.init(2, this.keySpec, paramSpec);
/*     */     } 
/* 141 */     int numBytes = cipher.doFinal(input, inputOffset, inputLen, output, outputOffset);
/* 142 */     if (numBytes != inputLen)
/* 143 */       throw new GeneralSecurityException("stored output's length does not match input's length"); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\AesCtrJceCipher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */