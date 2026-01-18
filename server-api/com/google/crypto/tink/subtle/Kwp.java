/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import com.google.crypto.tink.KeyWrap;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.util.Arrays;
/*     */ import javax.crypto.BadPaddingException;
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
/*     */ @Deprecated
/*     */ public class Kwp
/*     */   implements KeyWrap
/*     */ {
/*     */   private final SecretKey aesKey;
/*     */   static final int MIN_WRAP_KEY_SIZE = 16;
/*     */   static final int MAX_WRAP_KEY_SIZE = 4096;
/*     */   static final int ROUNDS = 6;
/*  56 */   static final byte[] PREFIX = new byte[] { -90, 89, 89, -90 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Kwp(byte[] key) throws GeneralSecurityException {
/*  64 */     if (key.length != 16 && key.length != 32) {
/*  65 */       throw new GeneralSecurityException("Unsupported key length");
/*     */     }
/*  67 */     this.aesKey = new SecretKeySpec(key, "AES");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int wrappingSize(int inputSize) {
/*  75 */     int paddingSize = 7 - (inputSize + 7) % 8;
/*  76 */     return inputSize + paddingSize + 8;
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
/*     */   private byte[] computeW(byte[] iv, byte[] key) throws GeneralSecurityException {
/*  93 */     if (key.length <= 8 || key.length > 2147483631 || iv.length != 8) {
/*  94 */       throw new GeneralSecurityException("computeW called with invalid parameters");
/*     */     }
/*  96 */     byte[] data = new byte[wrappingSize(key.length)];
/*  97 */     System.arraycopy(iv, 0, data, 0, iv.length);
/*  98 */     System.arraycopy(key, 0, data, 8, key.length);
/*  99 */     int blocks = data.length / 8 - 1;
/* 100 */     Cipher aes = EngineFactory.CIPHER.getInstance("AES/ECB/NoPadding");
/* 101 */     aes.init(1, this.aesKey);
/* 102 */     byte[] block = new byte[16];
/* 103 */     System.arraycopy(data, 0, block, 0, 8);
/* 104 */     for (int i = 0; i < 6; i++) {
/* 105 */       for (int j = 0; j < blocks; j++) {
/* 106 */         System.arraycopy(data, 8 * (j + 1), block, 8, 8);
/* 107 */         int length = aes.doFinal(block, 0, 16, block);
/* 108 */         assert length == 16;
/*     */         
/* 110 */         int roundConst = i * blocks + j + 1;
/* 111 */         for (int b = 0; b < 4; b++) {
/* 112 */           block[7 - b] = (byte)(block[7 - b] ^ (byte)(roundConst & 0xFF));
/* 113 */           roundConst >>>= 8;
/*     */         } 
/* 115 */         System.arraycopy(block, 8, data, 8 * (j + 1), 8);
/*     */       } 
/*     */     } 
/* 118 */     System.arraycopy(block, 0, data, 0, 8);
/* 119 */     return data;
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
/*     */   private byte[] invertW(byte[] wrapped) throws GeneralSecurityException {
/* 132 */     if (wrapped.length < 24 || wrapped.length % 8 != 0) {
/* 133 */       throw new GeneralSecurityException("Incorrect data size");
/*     */     }
/* 135 */     byte[] data = Arrays.copyOf(wrapped, wrapped.length);
/* 136 */     int blocks = data.length / 8 - 1;
/* 137 */     Cipher aes = EngineFactory.CIPHER.getInstance("AES/ECB/NoPadding");
/* 138 */     aes.init(2, this.aesKey);
/* 139 */     byte[] block = new byte[16];
/* 140 */     System.arraycopy(data, 0, block, 0, 8);
/* 141 */     for (int i = 5; i >= 0; i--) {
/* 142 */       for (int j = blocks - 1; j >= 0; j--) {
/* 143 */         System.arraycopy(data, 8 * (j + 1), block, 8, 8);
/*     */         
/* 145 */         int roundConst = i * blocks + j + 1;
/* 146 */         for (int b = 0; b < 4; b++) {
/* 147 */           block[7 - b] = (byte)(block[7 - b] ^ (byte)(roundConst & 0xFF));
/* 148 */           roundConst >>>= 8;
/*     */         } 
/*     */         
/* 151 */         int length = aes.doFinal(block, 0, 16, block);
/* 152 */         assert length == 16;
/* 153 */         System.arraycopy(block, 8, data, 8 * (j + 1), 8);
/*     */       } 
/*     */     } 
/* 156 */     System.arraycopy(block, 0, data, 0, 8);
/* 157 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] wrap(byte[] data) throws GeneralSecurityException {
/* 168 */     if (data.length < 16) {
/* 169 */       throw new GeneralSecurityException("Key size of key to wrap too small");
/*     */     }
/* 171 */     if (data.length > 4096) {
/* 172 */       throw new GeneralSecurityException("Key size of key to wrap too large");
/*     */     }
/* 174 */     byte[] iv = new byte[8];
/* 175 */     System.arraycopy(PREFIX, 0, iv, 0, PREFIX.length);
/* 176 */     for (int i = 0; i < 4; i++) {
/* 177 */       iv[4 + i] = (byte)(data.length >> 8 * (3 - i) & 0xFF);
/*     */     }
/* 179 */     return computeW(iv, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] unwrap(byte[] data) throws GeneralSecurityException {
/* 189 */     if (data.length < wrappingSize(16)) {
/* 190 */       throw new GeneralSecurityException("Wrapped key size is too small");
/*     */     }
/* 192 */     if (data.length > wrappingSize(4096)) {
/* 193 */       throw new GeneralSecurityException("Wrapped key size is too large");
/*     */     }
/* 195 */     if (data.length % 8 != 0) {
/* 196 */       throw new GeneralSecurityException("Wrapped key size must be a multiple of 8 bytes");
/*     */     }
/*     */     
/* 199 */     byte[] unwrapped = invertW(data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     boolean ok = true;
/* 206 */     for (int i = 0; i < 4; i++) {
/* 207 */       if (PREFIX[i] != unwrapped[i]) {
/* 208 */         ok = false;
/*     */       }
/*     */     } 
/* 211 */     int encodedSize = 0;
/* 212 */     for (int j = 4; j < 8; j++) {
/* 213 */       encodedSize = (encodedSize << 8) + (unwrapped[j] & 0xFF);
/*     */     }
/* 215 */     if (wrappingSize(encodedSize) != unwrapped.length) {
/* 216 */       ok = false;
/*     */     } else {
/* 218 */       for (int k = 8 + encodedSize; k < unwrapped.length; k++) {
/* 219 */         if (unwrapped[k] != 0) {
/* 220 */           ok = false;
/*     */         }
/*     */       } 
/*     */     } 
/* 224 */     if (ok) {
/* 225 */       return Arrays.copyOfRange(unwrapped, 8, 8 + encodedSize);
/*     */     }
/* 227 */     throw new BadPaddingException("Invalid padding");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\Kwp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */