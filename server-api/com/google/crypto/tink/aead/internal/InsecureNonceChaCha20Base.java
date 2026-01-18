/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
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
/*     */ abstract class InsecureNonceChaCha20Base
/*     */ {
/*     */   int[] key;
/*     */   private final int initialCounter;
/*     */   
/*     */   public InsecureNonceChaCha20Base(byte[] key, int initialCounter) throws InvalidKeyException {
/*  43 */     if (key.length != 32) {
/*  44 */       throw new InvalidKeyException("The key length in bytes must be 32.");
/*     */     }
/*  46 */     this.key = ChaCha20Util.toIntArray(key);
/*  47 */     this.initialCounter = initialCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract int[] createInitialState(int[] paramArrayOfint, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract int nonceSizeInBytes();
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encrypt(byte[] nonce, byte[] plaintext) throws GeneralSecurityException {
/*  63 */     ByteBuffer ciphertext = ByteBuffer.allocate(plaintext.length);
/*  64 */     encrypt(ciphertext, nonce, plaintext);
/*  65 */     return ciphertext.array();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void encrypt(ByteBuffer output, byte[] nonce, byte[] plaintext) throws GeneralSecurityException {
/*  71 */     if (output.remaining() < plaintext.length) {
/*  72 */       throw new IllegalArgumentException("Given ByteBuffer output is too small");
/*     */     }
/*  74 */     process(nonce, output, ByteBuffer.wrap(plaintext));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] nonce, byte[] ciphertext) throws GeneralSecurityException {
/*  80 */     return decrypt(nonce, ByteBuffer.wrap(ciphertext));
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] nonce, ByteBuffer ciphertext) throws GeneralSecurityException {
/*  85 */     ByteBuffer plaintext = ByteBuffer.allocate(ciphertext.remaining());
/*  86 */     process(nonce, plaintext, ciphertext);
/*  87 */     return plaintext.array();
/*     */   }
/*     */ 
/*     */   
/*     */   private void process(byte[] nonce, ByteBuffer output, ByteBuffer input) throws GeneralSecurityException {
/*  92 */     if (nonce.length != nonceSizeInBytes()) {
/*  93 */       throw new GeneralSecurityException("The nonce length (in bytes) must be " + 
/*  94 */           nonceSizeInBytes());
/*     */     }
/*  96 */     int length = input.remaining();
/*  97 */     int numBlocks = length / 64 + 1;
/*  98 */     for (int i = 0; i < numBlocks; i++) {
/*  99 */       ByteBuffer keyStreamBlock = chacha20Block(nonce, i + this.initialCounter);
/* 100 */       if (i == numBlocks - 1) {
/*     */         
/* 102 */         Bytes.xor(output, input, keyStreamBlock, length % 64);
/*     */       } else {
/* 104 */         Bytes.xor(output, input, keyStreamBlock, 64);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   ByteBuffer chacha20Block(byte[] nonce, int counter) {
/* 111 */     int[] state = createInitialState(ChaCha20Util.toIntArray(nonce), counter);
/* 112 */     int[] workingState = (int[])state.clone();
/* 113 */     ChaCha20Util.shuffleState(workingState);
/* 114 */     for (int i = 0; i < state.length; i++) {
/* 115 */       state[i] = state[i] + workingState[i];
/*     */     }
/*     */     
/* 118 */     ByteBuffer out = ByteBuffer.allocate(64).order(ByteOrder.LITTLE_ENDIAN);
/* 119 */     out.asIntBuffer().put(state, 0, 16);
/* 120 */     return out;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\InsecureNonceChaCha20Base.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */