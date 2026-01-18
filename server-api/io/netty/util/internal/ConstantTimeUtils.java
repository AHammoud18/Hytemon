/*     */ package io.netty.util.internal;
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
/*     */ public final class ConstantTimeUtils
/*     */ {
/*     */   public static int equalsConstantTime(int x, int y) {
/*  37 */     int z = x ^ y ^ 0xFFFFFFFF;
/*  38 */     z &= z >> 16;
/*  39 */     z &= z >> 8;
/*  40 */     z &= z >> 4;
/*  41 */     z &= z >> 2;
/*  42 */     z &= z >> 1;
/*  43 */     return z & 0x1;
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
/*     */   
/*     */   public static int equalsConstantTime(long x, long y) {
/*  62 */     long z = x ^ y ^ 0xFFFFFFFFFFFFFFFFL;
/*  63 */     z &= z >> 32L;
/*  64 */     z &= z >> 16L;
/*  65 */     z &= z >> 8L;
/*  66 */     z &= z >> 4L;
/*  67 */     z &= z >> 2L;
/*  68 */     z &= z >> 1L;
/*  69 */     return (int)(z & 0x1L);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int equalsConstantTime(byte[] bytes1, int startPos1, byte[] bytes2, int startPos2, int length) {
/*  96 */     int b = 0;
/*  97 */     int end = startPos1 + length;
/*  98 */     for (; startPos1 < end; startPos1++, startPos2++) {
/*  99 */       b |= bytes1[startPos1] ^ bytes2[startPos2];
/*     */     }
/* 101 */     return equalsConstantTime(b, 0);
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
/*     */   
/*     */   public static int equalsConstantTime(CharSequence s1, CharSequence s2) {
/* 120 */     if (s1.length() != s2.length()) {
/* 121 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 125 */     int c = 0;
/* 126 */     for (int i = 0; i < s1.length(); i++) {
/* 127 */       c |= s1.charAt(i) ^ s2.charAt(i);
/*     */     }
/* 129 */     return equalsConstantTime(c, 0);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\ConstantTimeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */