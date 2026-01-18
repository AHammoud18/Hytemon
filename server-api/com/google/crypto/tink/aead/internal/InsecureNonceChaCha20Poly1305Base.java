/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.config.internal.TinkFipsUtil;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import javax.crypto.AEADBadTagException;
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
/*     */ abstract class InsecureNonceChaCha20Poly1305Base
/*     */ {
/*  37 */   public static final TinkFipsUtil.AlgorithmFipsCompatibility FIPS = TinkFipsUtil.AlgorithmFipsCompatibility.ALGORITHM_NOT_FIPS;
/*     */   
/*     */   private final InsecureNonceChaCha20Base chacha20;
/*     */   
/*     */   private final InsecureNonceChaCha20Base macKeyChaCha20;
/*     */ 
/*     */   
/*     */   public InsecureNonceChaCha20Poly1305Base(byte[] key) throws GeneralSecurityException {
/*  45 */     if (!FIPS.isCompatible()) {
/*  46 */       throw new GeneralSecurityException("Can not use ChaCha20Poly1305 in FIPS-mode.");
/*     */     }
/*     */     
/*  49 */     this.chacha20 = newChaCha20Instance(key, 1);
/*  50 */     this.macKeyChaCha20 = newChaCha20Instance(key, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract InsecureNonceChaCha20Base newChaCha20Instance(byte[] paramArrayOfbyte, int paramInt) throws InvalidKeyException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] nonce, byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*  70 */     if (plaintext.length > 2147483631) {
/*  71 */       throw new GeneralSecurityException("plaintext too long");
/*     */     }
/*  73 */     ByteBuffer ciphertext = ByteBuffer.allocate(plaintext.length + 16);
/*  74 */     encrypt(ciphertext, nonce, plaintext, associatedData);
/*  75 */     return ciphertext.array();
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
/*     */   
/*     */   public void encrypt(ByteBuffer output, byte[] nonce, byte[] plaintext, byte[] associatedData) throws GeneralSecurityException {
/*  93 */     if (output.remaining() < plaintext.length + 16) {
/*  94 */       throw new IllegalArgumentException("Given ByteBuffer output is too small");
/*     */     }
/*  96 */     int firstPosition = output.position();
/*  97 */     this.chacha20.encrypt(output, nonce, plaintext);
/*  98 */     output.position(firstPosition);
/*  99 */     output.limit(output.limit() - 16);
/* 100 */     byte[] aad = associatedData;
/* 101 */     if (aad == null) {
/* 102 */       aad = new byte[0];
/*     */     }
/* 104 */     byte[] tag = Poly1305.computeMac(getMacKey(nonce), macDataRfc8439(aad, output));
/* 105 */     output.limit(output.limit() + 16);
/* 106 */     output.put(tag);
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
/*     */   public byte[] decrypt(byte[] nonce, byte[] ciphertext, byte[] associatedData) throws GeneralSecurityException {
/* 122 */     return decrypt(ByteBuffer.wrap(ciphertext), nonce, associatedData);
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
/*     */   public byte[] decrypt(ByteBuffer ciphertext, byte[] nonce, byte[] associatedData) throws GeneralSecurityException {
/* 137 */     if (ciphertext.remaining() < 16) {
/* 138 */       throw new GeneralSecurityException("ciphertext too short");
/*     */     }
/* 140 */     int firstPosition = ciphertext.position();
/* 141 */     byte[] tag = new byte[16];
/* 142 */     ciphertext.position(ciphertext.limit() - 16);
/* 143 */     ciphertext.get(tag);
/*     */     
/* 145 */     ciphertext.position(firstPosition);
/* 146 */     ciphertext.limit(ciphertext.limit() - 16);
/* 147 */     byte[] aad = associatedData;
/* 148 */     if (aad == null) {
/* 149 */       aad = new byte[0];
/*     */     }
/*     */     try {
/* 152 */       Poly1305.verifyMac(getMacKey(nonce), macDataRfc8439(aad, ciphertext), tag);
/* 153 */     } catch (GeneralSecurityException ex) {
/* 154 */       throw new AEADBadTagException(ex.toString());
/*     */     } 
/*     */ 
/*     */     
/* 158 */     ciphertext.position(firstPosition);
/* 159 */     return this.chacha20.decrypt(nonce, ciphertext);
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] getMacKey(byte[] nonce) throws GeneralSecurityException {
/* 164 */     ByteBuffer firstBlock = this.macKeyChaCha20.chacha20Block(nonce, 0);
/* 165 */     byte[] result = new byte[32];
/* 166 */     firstBlock.get(result);
/* 167 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] macDataRfc8439(byte[] aad, ByteBuffer ciphertext) {
/* 172 */     int aadPaddedLen = (aad.length % 16 == 0) ? aad.length : (aad.length + 16 - aad.length % 16);
/* 173 */     int ciphertextLen = ciphertext.remaining();
/*     */     
/* 175 */     int ciphertextPaddedLen = (ciphertextLen % 16 == 0) ? ciphertextLen : (ciphertextLen + 16 - ciphertextLen % 16);
/*     */     
/* 177 */     ByteBuffer macData = ByteBuffer.allocate(aadPaddedLen + ciphertextPaddedLen + 16).order(ByteOrder.LITTLE_ENDIAN);
/* 178 */     macData.put(aad);
/* 179 */     macData.position(aadPaddedLen);
/* 180 */     macData.put(ciphertext);
/* 181 */     macData.position(aadPaddedLen + ciphertextPaddedLen);
/* 182 */     macData.putLong(aad.length);
/* 183 */     macData.putLong(ciphertextLen);
/* 184 */     return macData.array();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\InsecureNonceChaCha20Poly1305Base.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */