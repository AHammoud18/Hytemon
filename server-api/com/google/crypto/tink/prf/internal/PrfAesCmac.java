/*     */ package com.google.crypto.tink.prf.internal;
/*     */ 
/*     */ import com.google.crypto.tink.AccessesPartialKey;
/*     */ import com.google.crypto.tink.InsecureSecretKeyAccess;
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import com.google.crypto.tink.mac.internal.AesUtil;
/*     */ import com.google.crypto.tink.prf.AesCmacPrfKey;
/*     */ import com.google.crypto.tink.prf.Prf;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.subtle.EngineFactory;
/*     */ import com.google.crypto.tink.subtle.Validators;
/*     */ import com.google.errorprone.annotations.Immutable;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
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
/*     */ @Immutable
/*     */ @AccessesPartialKey
/*     */ public final class PrfAesCmac
/*     */   implements Prf
/*     */ {
/*  42 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */ 
/*     */   
/*     */   private final SecretKey keySpec;
/*     */ 
/*     */   
/*     */   private byte[] subKey1;
/*     */ 
/*     */   
/*     */   private byte[] subKey2;
/*     */ 
/*     */   
/*  54 */   private static final ThreadLocal<Cipher> localAesCipher = new ThreadLocal<Cipher>()
/*     */     {
/*     */       protected Cipher initialValue()
/*     */       {
/*     */         try {
/*  59 */           return (Cipher)EngineFactory.CIPHER.getInstance("AES/ECB/NoPadding");
/*  60 */         } catch (GeneralSecurityException ex) {
/*  61 */           throw new IllegalStateException(ex);
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/*     */   private static Cipher instance() throws GeneralSecurityException {
/*  67 */     if (!FIPS.isCompatible()) {
/*  68 */       throw new GeneralSecurityException("Can not use AES-CMAC in FIPS-mode.");
/*     */     }
/*  70 */     return localAesCipher.get();
/*     */   }
/*     */   
/*     */   private PrfAesCmac(byte[] key) throws GeneralSecurityException {
/*  74 */     Validators.validateAesKeySize(key.length);
/*     */     
/*  76 */     this.keySpec = new SecretKeySpec(key, "AES");
/*  77 */     generateSubKeys();
/*     */   }
/*     */   
/*     */   public static Prf create(AesCmacPrfKey key) throws GeneralSecurityException {
/*  81 */     return new PrfAesCmac(key.getKeyBytes().toByteArray(InsecureSecretKeyAccess.get()));
/*     */   }
/*     */ 
/*     */   
/*     */   static int calcN(int dataLength) {
/*  86 */     if (dataLength == 0) {
/*  87 */       return 1;
/*     */     }
/*  89 */     return (dataLength - 1) / 16 + 1;
/*     */   }
/*     */   
/*     */   private static void xorBlock(byte[] x, byte[] y, int offsetY, byte[] output) {
/*  93 */     for (int i = 0; i < 16; i++) {
/*  94 */       output[i] = (byte)(x[i] ^ y[i + offsetY]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] compute(byte[] data, int outputLength) throws GeneralSecurityException {
/*     */     byte[] mLast;
/* 101 */     if (outputLength > 16) {
/* 102 */       throw new InvalidAlgorithmParameterException("outputLength too large, max is 16 bytes");
/*     */     }
/*     */     
/* 105 */     Cipher aes = instance();
/* 106 */     aes.init(1, this.keySpec);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     int n = calcN(data.length);
/*     */ 
/*     */     
/* 114 */     boolean flag = (n * 16 == data.length);
/*     */ 
/*     */ 
/*     */     
/* 118 */     if (flag) {
/* 119 */       mLast = Bytes.xor(data, (n - 1) * 16, this.subKey1, 0, 16);
/*     */     } else {
/*     */       
/* 122 */       mLast = Bytes.xor(
/* 123 */           AesUtil.cmacPad(Arrays.copyOfRange(data, (n - 1) * 16, data.length)), this.subKey2);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 128 */     byte[] x = new byte[16];
/*     */ 
/*     */     
/* 131 */     byte[] y = new byte[16];
/* 132 */     for (int i = 0; i < n - 1; i++) {
/* 133 */       xorBlock(x, data, i * 16, y);
/* 134 */       int j = aes.doFinal(y, 0, 16, x);
/* 135 */       if (j != 16) {
/* 136 */         throw new IllegalStateException("Cipher didn't write full block");
/*     */       }
/*     */     } 
/* 139 */     xorBlock(x, mLast, 0, y);
/*     */ 
/*     */     
/* 142 */     int written = aes.doFinal(y, 0, 16, x);
/* 143 */     if (written != 16) {
/* 144 */       throw new IllegalStateException("Cipher didn't write full block");
/*     */     }
/* 146 */     if (x.length == outputLength) {
/* 147 */       return x;
/*     */     }
/* 149 */     return Arrays.copyOf(x, outputLength);
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateSubKeys() throws GeneralSecurityException {
/* 154 */     Cipher aes = instance();
/* 155 */     aes.init(1, this.keySpec);
/* 156 */     byte[] zeroes = new byte[16];
/* 157 */     byte[] l = aes.doFinal(zeroes);
/* 158 */     this.subKey1 = AesUtil.dbl(l);
/* 159 */     this.subKey2 = AesUtil.dbl(this.subKey1);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\prf\internal\PrfAesCmac.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */