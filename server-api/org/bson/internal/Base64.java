/*     */ package org.bson.internal;
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
/*     */ public final class Base64
/*     */ {
/*     */   private static final int BYTES_PER_UNENCODED_BLOCK = 3;
/*     */   private static final int BYTES_PER_ENCODED_BLOCK = 4;
/*     */   private static final int SIX_BIT_MASK = 63;
/*     */   private static final byte PAD = 61;
/*  46 */   private static final byte[] ENCODE_TABLE = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private static final int[] DECODE_TABLE = new int[128];
/*     */   
/*     */   static {
/*  56 */     for (int i = 0; i < ENCODE_TABLE.length; i++) {
/*  57 */       DECODE_TABLE[ENCODE_TABLE[i]] = i;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decode(String s) {
/*  68 */     int delta = s.endsWith("==") ? 2 : (s.endsWith("=") ? 1 : 0);
/*  69 */     byte[] buffer = new byte[s.length() * 3 / 4 - delta];
/*  70 */     int mask = 255;
/*  71 */     int pos = 0;
/*  72 */     for (int i = 0; i < s.length(); i += 4) {
/*  73 */       int c0 = DECODE_TABLE[s.charAt(i)];
/*  74 */       int c1 = DECODE_TABLE[s.charAt(i + 1)];
/*  75 */       buffer[pos++] = (byte)((c0 << 2 | c1 >> 4) & mask);
/*  76 */       if (pos >= buffer.length) {
/*  77 */         return buffer;
/*     */       }
/*  79 */       int c2 = DECODE_TABLE[s.charAt(i + 2)];
/*  80 */       buffer[pos++] = (byte)((c1 << 4 | c2 >> 2) & mask);
/*  81 */       if (pos >= buffer.length) {
/*  82 */         return buffer;
/*     */       }
/*  84 */       int c3 = DECODE_TABLE[s.charAt(i + 3)];
/*  85 */       buffer[pos++] = (byte)((c2 << 6 | c3) & mask);
/*     */     } 
/*  87 */     return buffer;
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
/*     */   public static String encode(byte[] in) {
/*  99 */     int modulus = 0;
/* 100 */     int bitWorkArea = 0;
/*     */     
/* 102 */     int numEncodedBytes = in.length / 3 * 4 + ((in.length % 3 == 0) ? 0 : 4);
/*     */     
/* 104 */     byte[] buffer = new byte[numEncodedBytes];
/* 105 */     int pos = 0;
/*     */     
/* 107 */     for (int b : in) {
/* 108 */       modulus = (modulus + 1) % 3;
/*     */       
/* 110 */       if (b < 0) {
/* 111 */         b += 256;
/*     */       }
/*     */       
/* 114 */       bitWorkArea = (bitWorkArea << 8) + b;
/* 115 */       if (0 == modulus) {
/* 116 */         buffer[pos++] = ENCODE_TABLE[bitWorkArea >> 18 & 0x3F];
/* 117 */         buffer[pos++] = ENCODE_TABLE[bitWorkArea >> 12 & 0x3F];
/* 118 */         buffer[pos++] = ENCODE_TABLE[bitWorkArea >> 6 & 0x3F];
/* 119 */         buffer[pos++] = ENCODE_TABLE[bitWorkArea & 0x3F];
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     switch (modulus) {
/*     */       case 1:
/* 125 */         buffer[pos++] = ENCODE_TABLE[bitWorkArea >> 2 & 0x3F];
/* 126 */         buffer[pos++] = ENCODE_TABLE[bitWorkArea << 4 & 0x3F];
/* 127 */         buffer[pos++] = 61;
/* 128 */         buffer[pos] = 61;
/*     */         break;
/*     */       
/*     */       case 2:
/* 132 */         buffer[pos++] = ENCODE_TABLE[bitWorkArea >> 10 & 0x3F];
/* 133 */         buffer[pos++] = ENCODE_TABLE[bitWorkArea >> 4 & 0x3F];
/* 134 */         buffer[pos++] = ENCODE_TABLE[bitWorkArea << 2 & 0x3F];
/* 135 */         buffer[pos] = 61;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 141 */     return byteArrayToString(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String byteArrayToString(byte[] buffer) {
/* 146 */     return new String(buffer, 0, 0, buffer.length);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\internal\Base64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */