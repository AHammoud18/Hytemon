/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
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
/*     */ final class ChaCha20Util
/*     */ {
/*     */   static final int BLOCK_SIZE_IN_INTS = 16;
/*     */   static final int KEY_SIZE_IN_INTS = 8;
/*     */   static final int BLOCK_SIZE_IN_BYTES = 64;
/*     */   static final int KEY_SIZE_IN_BYTES = 32;
/*  35 */   private static final int[] sigma = toIntArray(new byte[] { 
/*     */         101, 120, 112, 97, 110, 100, 32, 51, 50, 45, 
/*     */         98, 121, 116, 101, 32, 107 });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void setSigmaAndKey(int[] state, int[] key) {
/*  45 */     System.arraycopy(sigma, 0, state, 0, sigma.length);
/*  46 */     System.arraycopy(key, 0, state, sigma.length, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void shuffleState(int[] state) {
/*  54 */     for (int i = 0; i < 10; i++) {
/*  55 */       quarterRound(state, 0, 4, 8, 12);
/*  56 */       quarterRound(state, 1, 5, 9, 13);
/*  57 */       quarterRound(state, 2, 6, 10, 14);
/*  58 */       quarterRound(state, 3, 7, 11, 15);
/*  59 */       quarterRound(state, 0, 5, 10, 15);
/*  60 */       quarterRound(state, 1, 6, 11, 12);
/*  61 */       quarterRound(state, 2, 7, 8, 13);
/*  62 */       quarterRound(state, 3, 4, 9, 14);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void quarterRound(int[] x, int a, int b, int c, int d) {
/*  71 */     x[a] = x[a] + x[b];
/*  72 */     x[d] = rotateLeft(x[d] ^ x[a], 16);
/*  73 */     x[c] = x[c] + x[d];
/*  74 */     x[b] = rotateLeft(x[b] ^ x[c], 12);
/*  75 */     x[a] = x[a] + x[b];
/*  76 */     x[d] = rotateLeft(x[d] ^ x[a], 8);
/*  77 */     x[c] = x[c] + x[d];
/*  78 */     x[b] = rotateLeft(x[b] ^ x[c], 7);
/*     */   }
/*     */ 
/*     */   
/*     */   static int[] toIntArray(byte[] input) {
/*  83 */     if (input.length % 4 != 0) {
/*  84 */       throw new IllegalArgumentException("invalid input length");
/*     */     }
/*  86 */     IntBuffer intBuffer = ByteBuffer.wrap(input).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
/*  87 */     int[] ret = new int[intBuffer.remaining()];
/*  88 */     intBuffer.get(ret);
/*  89 */     return ret;
/*     */   }
/*     */   
/*     */   static byte[] toByteArray(int[] input) {
/*  93 */     ByteBuffer byteBuffer = ByteBuffer.allocate(input.length * 4).order(ByteOrder.LITTLE_ENDIAN);
/*  94 */     byteBuffer.asIntBuffer().put(input);
/*  95 */     return byteBuffer.array();
/*     */   }
/*     */ 
/*     */   
/*     */   static int[] hChaCha20(int[] key, int[] nonce) {
/* 100 */     int[] state = new int[16];
/* 101 */     setSigmaAndKey(state, key);
/* 102 */     state[12] = nonce[0];
/* 103 */     state[13] = nonce[1];
/* 104 */     state[14] = nonce[2];
/* 105 */     state[15] = nonce[3];
/* 106 */     shuffleState(state);
/*     */     
/* 108 */     state[4] = state[12];
/* 109 */     state[5] = state[13];
/* 110 */     state[6] = state[14];
/* 111 */     state[7] = state[15];
/* 112 */     return Arrays.copyOf(state, 8);
/*     */   }
/*     */   
/*     */   static byte[] hChaCha20(byte[] key, byte[] nonce) {
/* 116 */     return toByteArray(hChaCha20(toIntArray(key), toIntArray(nonce)));
/*     */   }
/*     */   
/*     */   private static int rotateLeft(int x, int y) {
/* 120 */     return x << y | x >>> -y;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\ChaCha20Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */