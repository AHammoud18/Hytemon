/*     */ package com.google.crypto.tink.subtle;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.MessageDigest;
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
/*     */ public final class Bytes
/*     */ {
/*     */   public static final boolean equal(byte[] x, byte[] y) {
/*  36 */     return MessageDigest.isEqual(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] concat(byte[]... chunks) throws GeneralSecurityException {
/*  46 */     int length = 0;
/*  47 */     for (byte[] chunk : chunks) {
/*  48 */       if (length > Integer.MAX_VALUE - chunk.length) {
/*  49 */         throw new GeneralSecurityException("exceeded size limit");
/*     */       }
/*  51 */       length += chunk.length;
/*     */     } 
/*  53 */     byte[] res = new byte[length];
/*  54 */     int pos = 0;
/*  55 */     for (byte[] chunk : chunks) {
/*  56 */       System.arraycopy(chunk, 0, res, pos, chunk.length);
/*  57 */       pos += chunk.length;
/*     */     } 
/*  59 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final byte[] xor(byte[] x, int offsetX, byte[] y, int offsetY, int len) {
/*  69 */     if (len < 0 || x.length - len < offsetX || y.length - len < offsetY) {
/*  70 */       throw new IllegalArgumentException("That combination of buffers, offsets and length to xor result in out-of-bond accesses.");
/*     */     }
/*     */     
/*  73 */     byte[] res = new byte[len];
/*  74 */     for (int i = 0; i < len; i++) {
/*  75 */       res[i] = (byte)(x[i + offsetX] ^ y[i + offsetY]);
/*     */     }
/*  77 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void xor(ByteBuffer output, ByteBuffer x, ByteBuffer y, int len) {
/*  85 */     if (len < 0 || x.remaining() < len || y.remaining() < len || output.remaining() < len) {
/*  86 */       throw new IllegalArgumentException("That combination of buffers, offsets and length to xor result in out-of-bond accesses.");
/*     */     }
/*     */     
/*  89 */     for (int i = 0; i < len; i++) {
/*  90 */       output.put((byte)(x.get() ^ y.get()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final byte[] xor(byte[] x, byte[] y) {
/* 100 */     if (x.length != y.length) {
/* 101 */       throw new IllegalArgumentException("The lengths of x and y should match.");
/*     */     }
/* 103 */     return xor(x, 0, y, 0, x.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final byte[] xorEnd(byte[] a, byte[] b) {
/* 112 */     if (a.length < b.length) {
/* 113 */       throw new IllegalArgumentException("xorEnd requires a.length >= b.length");
/*     */     }
/* 115 */     int paddingLength = a.length - b.length;
/* 116 */     byte[] res = Arrays.copyOf(a, a.length);
/* 117 */     for (int i = 0; i < b.length; i++) {
/* 118 */       res[paddingLength + i] = (byte)(res[paddingLength + i] ^ b[i]);
/*     */     }
/* 120 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] intToByteArray(int capacity, int value) {
/* 130 */     if (capacity > 4 || capacity < 0) {
/* 131 */       throw new IllegalArgumentException("capacity must be between 0 and 4");
/*     */     }
/*     */ 
/*     */     
/* 135 */     if (value < 0 || (capacity < 4 && value >= 1 << 8 * capacity)) {
/* 136 */       throw new IllegalArgumentException("value too large");
/*     */     }
/* 138 */     byte[] result = new byte[capacity];
/* 139 */     for (int i = 0; i < capacity; i++) {
/* 140 */       result[i] = (byte)(value >> 8 * i & 0xFF);
/*     */     }
/* 142 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int byteArrayToInt(byte[] bytes) {
/* 151 */     return byteArrayToInt(bytes, bytes.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int byteArrayToInt(byte[] bytes, int length) {
/* 161 */     return byteArrayToInt(bytes, 0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int byteArrayToInt(byte[] bytes, int offset, int length) {
/* 172 */     if (length > 4 || length < 0) {
/* 173 */       throw new IllegalArgumentException("length must be between 0 and 4");
/*     */     }
/* 175 */     if (offset < 0 || offset + length > bytes.length) {
/* 176 */       throw new IllegalArgumentException("offset and length are out of bounds");
/*     */     }
/* 178 */     int value = 0;
/* 179 */     for (int i = 0; i < length; i++) {
/* 180 */       value += (bytes[i + offset] & 0xFF) << i * 8;
/*     */     }
/* 182 */     return value;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\subtle\Bytes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */