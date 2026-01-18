/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.Aead;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.aead.AesEaxKey;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.internal.Util;
/*     */ import com.google.crypto.tink.prf.AesCmacPrfKey;
/*     */ import com.google.crypto.tink.prf.AesCmacPrfParameters;
/*     */ import com.google.crypto.tink.prf.Prf;
/*     */ import com.google.crypto.tink.util.SecretBytes;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.AEADBadTagException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AesEaxJce
/*     */   implements Aead
/*     */ {
/*  53 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */ 
/*     */   
/*  56 */   private static final ThreadLocal<Cipher> localCtrCipher = new ThreadLocal<Cipher>()
/*     */     {
/*     */       protected Cipher initialValue()
/*     */       {
/*     */         try {
/*  61 */           return EngineFactory.CIPHER.getInstance("AES/CTR/NOPADDING");
/*  62 */         } catch (GeneralSecurityException ex) {
/*  63 */           throw new IllegalStateException(ex);
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   static final int BLOCK_SIZE_IN_BYTES = 16;
/*     */   
/*     */   static final int TAG_SIZE_IN_BYTES = 16;
/*     */   
/*     */   private final byte[] outputPrefix;
/*     */   private final Prf cmac;
/*     */   private final SecretKeySpec keySpec;
/*     */   private final int ivSizeInBytes;
/*     */   
/*     */   @AccessesPartialKey
/*     */   public static Aead create(AesEaxKey key) throws GeneralSecurityException {
/*  80 */     if (!FIPS.isCompatible()) {
/*  81 */       throw new GeneralSecurityException("Can not use AES-EAX in FIPS-mode.");
/*     */     }
/*  83 */     if (key.getParameters().getTagSizeBytes() != 16) {
/*  84 */       throw new GeneralSecurityException("AesEaxJce only supports 16 byte tag size, not " + key
/*  85 */           .getParameters().getTagSizeBytes());
/*     */     }
/*  87 */     return new AesEaxJce(key
/*  88 */         .getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), key
/*  89 */         .getParameters().getIvSizeBytes(), key
/*  90 */         .getOutputPrefix().toByteArray());
/*     */   }
/*     */   
/*     */   @AccessesPartialKey
/*     */   private static Prf createCmac(byte[] key) throws GeneralSecurityException {
/*  95 */     return PrfAesCmac.create(
/*  96 */         AesCmacPrfKey.create(
/*  97 */           AesCmacPrfParameters.create(key.length), 
/*  98 */           SecretBytes.copyFrom(key, InsecureSecretKeyAccess.get())));
/*     */   }
/*     */ 
/*     */   
/*     */   private AesEaxJce(byte[] key, int ivSizeInBytes, byte[] outputPrefix) throws GeneralSecurityException {
/* 103 */     if (!FIPS.isCompatible()) {
/* 104 */       throw new GeneralSecurityException("Can not use AES-EAX in FIPS-mode.");
/*     */     }
/*     */     
/* 107 */     if (ivSizeInBytes != 12 && ivSizeInBytes != 16) {
/* 108 */       throw new IllegalArgumentException("IV size should be either 12 or 16 bytes");
/*     */     }
/* 110 */     this.ivSizeInBytes = ivSizeInBytes;
/* 111 */     Validators.validateAesKeySize(key.length);
/* 112 */     this.keySpec = new SecretKeySpec(key, "AES");
/* 113 */     this.cmac = createCmac(key);
/* 114 */     this.outputPrefix = outputPrefix;
/*     */   }
/*     */   
/*     */   public AesEaxJce(byte[] key, int ivSizeInBytes) throws GeneralSecurityException {
/* 118 */     this(key, ivSizeInBytes, new byte[0]);
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
/*     */   private byte[] omac(int tag, byte[] data, int offset, int length) throws GeneralSecurityException {
/* 135 */     byte[] input = new byte[length + 16];
/* 136 */     input[15] = (byte)tag;
/* 137 */     System.arraycopy(data, offset, input, 16, length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     return this.cmac.compute(input, 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/* 151 */     if (plaintext.length > Integer.MAX_VALUE - this.outputPrefix.length - this.ivSizeInBytes - 16)
/*     */     {
/* 153 */       throw new GeneralSecurityException("plaintext too long");
/*     */     }
/*     */     
/* 156 */     byte[] ciphertext = Arrays.copyOf(this.outputPrefix, this.outputPrefix.length + this.ivSizeInBytes + plaintext.length + 16);
/*     */ 
/*     */     
/* 159 */     byte[] iv = Random.randBytes(this.ivSizeInBytes);
/* 160 */     System.arraycopy(iv, 0, ciphertext, this.outputPrefix.length, this.ivSizeInBytes);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     byte[] n = omac(0, iv, 0, iv.length);
/* 167 */     byte[] aad = associatedData;
/* 168 */     if (aad == null) {
/* 169 */       aad = new byte[0];
/*     */     }
/* 171 */     byte[] h = omac(1, aad, 0, aad.length);
/* 172 */     Cipher ctr = localCtrCipher.get();
/* 173 */     ctr.init(1, this.keySpec, new IvParameterSpec(n));
/* 174 */     ctr.doFinal(plaintext, 0, plaintext.length, ciphertext, this.outputPrefix.length + this.ivSizeInBytes);
/* 175 */     byte[] t = omac(2, ciphertext, this.outputPrefix.length + this.ivSizeInBytes, plaintext.length);
/* 176 */     int offset = this.outputPrefix.length + plaintext.length + this.ivSizeInBytes;
/* 177 */     for (int i = 0; i < 16; i++) {
/* 178 */       ciphertext[offset + i] = (byte)(h[i] ^ n[i] ^ t[i]);
/*     */     }
/* 180 */     return ciphertext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 187 */     int plaintextLength = ciphertext.length - this.outputPrefix.length - this.ivSizeInBytes - 16;
/*     */     
/* 189 */     if (plaintextLength < 0) {
/* 190 */       throw new GeneralSecurityException("ciphertext too short");
/*     */     }
/* 192 */     if (!Util.isPrefix(this.outputPrefix, ciphertext)) {
/* 193 */       throw new GeneralSecurityException("Decryption failed (OutputPrefix mismatch).");
/*     */     }
/* 195 */     byte[] n = omac(0, ciphertext, this.outputPrefix.length, this.ivSizeInBytes);
/* 196 */     byte[] aad = associatedData;
/* 197 */     if (aad == null) {
/* 198 */       aad = new byte[0];
/*     */     }
/* 200 */     byte[] h = omac(1, aad, 0, aad.length);
/* 201 */     byte[] t = omac(2, ciphertext, this.outputPrefix.length + this.ivSizeInBytes, plaintextLength);
/* 202 */     byte res = 0;
/* 203 */     int offset = ciphertext.length - 16;
/* 204 */     for (int i = 0; i < 16; i++) {
/* 205 */       res = (byte)(res | ciphertext[offset + i] ^ h[i] ^ n[i] ^ t[i]);
/*     */     }
/* 207 */     if (res != 0) {
/* 208 */       throw new AEADBadTagException("tag mismatch");
/*     */     }
/* 210 */     Cipher ctr = localCtrCipher.get();
/* 211 */     ctr.init(1, this.keySpec, new IvParameterSpec(n));
/* 212 */     return ctr.doFinal(ciphertext, this.outputPrefix.length + this.ivSizeInBytes, plaintextLength);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\AesEaxJce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */