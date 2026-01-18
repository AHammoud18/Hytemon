/*     */ package com.google.crypto.tink.aead.internal;
/*     */ 
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import java.security.GeneralSecurityException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Poly1305
/*     */ {
/*     */   public static final int MAC_TAG_SIZE_IN_BYTES = 16;
/*     */   public static final int MAC_KEY_SIZE_IN_BYTES = 32;
/*     */   
/*     */   private static long load32(byte[] in, int idx) {
/*  40 */     return (in[idx] & 0xFF | (in[idx + 1] & 0xFF) << 8 | (in[idx + 2] & 0xFF) << 16 | (in[idx + 3] & 0xFF) << 24) & 0xFFFFFFFFL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long load26(byte[] in, int idx, int shift) {
/*  48 */     return load32(in, idx) >> shift & 0x3FFFFFFL;
/*     */   }
/*     */   
/*     */   private static void toByteArray(byte[] output, long num, int idx) {
/*  52 */     for (int i = 0; i < 4; i++, num >>= 8L) {
/*  53 */       output[idx + i] = (byte)(int)(num & 0xFFL);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void copyBlockSize(byte[] output, byte[] in, int idx) {
/*  58 */     int copyCount = Math.min(16, in.length - idx);
/*  59 */     System.arraycopy(in, idx, output, 0, copyCount);
/*  60 */     output[copyCount] = 1;
/*  61 */     if (copyCount != 16) {
/*  62 */       Arrays.fill(output, copyCount + 1, output.length, (byte)0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] computeMac(byte[] key, byte[] data) {
/*  68 */     if (key.length != 32) {
/*  69 */       throw new IllegalArgumentException("The key length in bytes must be 32.");
/*     */     }
/*  71 */     long h0 = 0L;
/*  72 */     long h1 = 0L;
/*  73 */     long h2 = 0L;
/*  74 */     long h3 = 0L;
/*  75 */     long h4 = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     long r0 = load26(key, 0, 0) & 0x3FFFFFFL;
/*  85 */     long r1 = load26(key, 3, 2) & 0x3FFFF03L;
/*  86 */     long r2 = load26(key, 6, 4) & 0x3FFC0FFL;
/*  87 */     long r3 = load26(key, 9, 6) & 0x3F03FFFL;
/*  88 */     long r4 = load26(key, 12, 8) & 0xFFFFFL;
/*     */     
/*  90 */     long s1 = r1 * 5L;
/*  91 */     long s2 = r2 * 5L;
/*  92 */     long s3 = r3 * 5L;
/*  93 */     long s4 = r4 * 5L;
/*     */     
/*  95 */     byte[] buf = new byte[17];
/*  96 */     for (int i = 0; i < data.length; i += 16) {
/*  97 */       copyBlockSize(buf, data, i);
/*  98 */       h0 += load26(buf, 0, 0);
/*  99 */       h1 += load26(buf, 3, 2);
/* 100 */       h2 += load26(buf, 6, 4);
/* 101 */       h3 += load26(buf, 9, 6);
/* 102 */       h4 += load26(buf, 12, 8) | (buf[16] << 24);
/*     */ 
/*     */       
/* 105 */       long d0 = h0 * r0 + h1 * s4 + h2 * s3 + h3 * s2 + h4 * s1;
/* 106 */       long d1 = h0 * r1 + h1 * r0 + h2 * s4 + h3 * s3 + h4 * s2;
/* 107 */       long d2 = h0 * r2 + h1 * r1 + h2 * r0 + h3 * s4 + h4 * s3;
/* 108 */       long d3 = h0 * r3 + h1 * r2 + h2 * r1 + h3 * r0 + h4 * s4;
/* 109 */       long d4 = h0 * r4 + h1 * r3 + h2 * r2 + h3 * r1 + h4 * r0;
/*     */ 
/*     */       
/* 112 */       long l1 = d0 >> 26L;
/* 113 */       h0 = d0 & 0x3FFFFFFL;
/* 114 */       d1 += l1;
/* 115 */       l1 = d1 >> 26L;
/* 116 */       h1 = d1 & 0x3FFFFFFL;
/* 117 */       d2 += l1;
/* 118 */       l1 = d2 >> 26L;
/* 119 */       h2 = d2 & 0x3FFFFFFL;
/* 120 */       d3 += l1;
/* 121 */       l1 = d3 >> 26L;
/* 122 */       h3 = d3 & 0x3FFFFFFL;
/* 123 */       d4 += l1;
/* 124 */       l1 = d4 >> 26L;
/* 125 */       h4 = d4 & 0x3FFFFFFL;
/* 126 */       h0 += l1 * 5L;
/* 127 */       l1 = h0 >> 26L;
/* 128 */       h0 &= 0x3FFFFFFL;
/* 129 */       h1 += l1;
/*     */     } 
/*     */     
/* 132 */     long c = h1 >> 26L;
/* 133 */     h1 &= 0x3FFFFFFL;
/* 134 */     h2 += c;
/* 135 */     c = h2 >> 26L;
/* 136 */     h2 &= 0x3FFFFFFL;
/* 137 */     h3 += c;
/* 138 */     c = h3 >> 26L;
/* 139 */     h3 &= 0x3FFFFFFL;
/* 140 */     h4 += c;
/* 141 */     c = h4 >> 26L;
/* 142 */     h4 &= 0x3FFFFFFL;
/* 143 */     h0 += c * 5L;
/* 144 */     c = h0 >> 26L;
/* 145 */     h0 &= 0x3FFFFFFL;
/* 146 */     h1 += c;
/*     */ 
/*     */     
/* 149 */     long g0 = h0 + 5L;
/* 150 */     c = g0 >> 26L;
/* 151 */     g0 &= 0x3FFFFFFL;
/* 152 */     long g1 = h1 + c;
/* 153 */     c = g1 >> 26L;
/* 154 */     g1 &= 0x3FFFFFFL;
/* 155 */     long g2 = h2 + c;
/* 156 */     c = g2 >> 26L;
/* 157 */     g2 &= 0x3FFFFFFL;
/* 158 */     long g3 = h3 + c;
/* 159 */     c = g3 >> 26L;
/* 160 */     g3 &= 0x3FFFFFFL;
/* 161 */     long g4 = h4 + c - 67108864L;
/*     */ 
/*     */     
/* 164 */     long mask = g4 >> 63L;
/* 165 */     h0 &= mask;
/* 166 */     h1 &= mask;
/* 167 */     h2 &= mask;
/* 168 */     h3 &= mask;
/* 169 */     h4 &= mask;
/* 170 */     mask ^= 0xFFFFFFFFFFFFFFFFL;
/* 171 */     h0 |= g0 & mask;
/* 172 */     h1 |= g1 & mask;
/* 173 */     h2 |= g2 & mask;
/* 174 */     h3 |= g3 & mask;
/* 175 */     h4 |= g4 & mask;
/*     */ 
/*     */     
/* 178 */     h0 = (h0 | h1 << 26L) & 0xFFFFFFFFL;
/* 179 */     h1 = (h1 >> 6L | h2 << 20L) & 0xFFFFFFFFL;
/* 180 */     h2 = (h2 >> 12L | h3 << 14L) & 0xFFFFFFFFL;
/* 181 */     h3 = (h3 >> 18L | h4 << 8L) & 0xFFFFFFFFL;
/*     */ 
/*     */     
/* 184 */     c = h0 + load32(key, 16);
/* 185 */     h0 = c & 0xFFFFFFFFL;
/* 186 */     c = h1 + load32(key, 20) + (c >> 32L);
/* 187 */     h1 = c & 0xFFFFFFFFL;
/* 188 */     c = h2 + load32(key, 24) + (c >> 32L);
/* 189 */     h2 = c & 0xFFFFFFFFL;
/* 190 */     c = h3 + load32(key, 28) + (c >> 32L);
/* 191 */     h3 = c & 0xFFFFFFFFL;
/*     */     
/* 193 */     byte[] mac = new byte[16];
/* 194 */     toByteArray(mac, h0, 0);
/* 195 */     toByteArray(mac, h1, 4);
/* 196 */     toByteArray(mac, h2, 8);
/* 197 */     toByteArray(mac, h3, 12);
/*     */     
/* 199 */     return mac;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void verifyMac(byte[] key, byte[] data, byte[] mac) throws GeneralSecurityException {
/* 205 */     if (!Bytes.equal(computeMac(key, data), mac))
/* 206 */       throw new GeneralSecurityException("invalid MAC"); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\aead\internal\Poly1305.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */