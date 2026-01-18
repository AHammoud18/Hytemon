/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.MathUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ReadOnlyBufferException;
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
/*     */ final class UnsafeByteBufUtil
/*     */ {
/*  35 */   private static final boolean UNALIGNED = PlatformDependent.isUnaligned();
/*     */   private static final byte ZERO = 0;
/*     */   private static final int MAX_HAND_ROLLED_SET_ZERO_BYTES = 64;
/*     */   
/*     */   static byte getByte(long address) {
/*  40 */     return PlatformDependent.getByte(address);
/*     */   }
/*     */   
/*     */   static short getShort(long address) {
/*  44 */     if (UNALIGNED) {
/*  45 */       short v = PlatformDependent.getShort(address);
/*  46 */       return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? v : Short.reverseBytes(v);
/*     */     } 
/*  48 */     return (short)(PlatformDependent.getByte(address) << 8 | PlatformDependent.getByte(address + 1L) & 0xFF);
/*     */   }
/*     */   
/*     */   static short getShortLE(long address) {
/*  52 */     if (UNALIGNED) {
/*  53 */       short v = PlatformDependent.getShort(address);
/*  54 */       return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(v) : v;
/*     */     } 
/*  56 */     return (short)(PlatformDependent.getByte(address) & 0xFF | PlatformDependent.getByte(address + 1L) << 8);
/*     */   }
/*     */   
/*     */   static int getUnsignedMedium(long address) {
/*  60 */     if (UNALIGNED) {
/*  61 */       return (PlatformDependent.getByte(address) & 0xFF) << 16 | (
/*  62 */         PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? PlatformDependent.getShort(address + 1L) : 
/*  63 */         Short.reverseBytes(PlatformDependent.getShort(address + 1L))) & 0xFFFF;
/*     */     }
/*  65 */     return (PlatformDependent.getByte(address) & 0xFF) << 16 | (
/*  66 */       PlatformDependent.getByte(address + 1L) & 0xFF) << 8 | 
/*  67 */       PlatformDependent.getByte(address + 2L) & 0xFF;
/*     */   }
/*     */   
/*     */   static int getUnsignedMediumLE(long address) {
/*  71 */     if (UNALIGNED) {
/*  72 */       return PlatformDependent.getByte(address) & 0xFF | ((
/*  73 */         PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(PlatformDependent.getShort(address + 1L)) : 
/*  74 */         PlatformDependent.getShort(address + 1L)) & 0xFFFF) << 8;
/*     */     }
/*  76 */     return PlatformDependent.getByte(address) & 0xFF | (
/*  77 */       PlatformDependent.getByte(address + 1L) & 0xFF) << 8 | (
/*  78 */       PlatformDependent.getByte(address + 2L) & 0xFF) << 16;
/*     */   }
/*     */   
/*     */   static int getInt(long address) {
/*  82 */     if (UNALIGNED) {
/*  83 */       int v = PlatformDependent.getInt(address);
/*  84 */       return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? v : Integer.reverseBytes(v);
/*     */     } 
/*  86 */     return PlatformDependent.getByte(address) << 24 | (
/*  87 */       PlatformDependent.getByte(address + 1L) & 0xFF) << 16 | (
/*  88 */       PlatformDependent.getByte(address + 2L) & 0xFF) << 8 | 
/*  89 */       PlatformDependent.getByte(address + 3L) & 0xFF;
/*     */   }
/*     */   
/*     */   static int getIntLE(long address) {
/*  93 */     if (UNALIGNED) {
/*  94 */       int v = PlatformDependent.getInt(address);
/*  95 */       return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(v) : v;
/*     */     } 
/*  97 */     return PlatformDependent.getByte(address) & 0xFF | (
/*  98 */       PlatformDependent.getByte(address + 1L) & 0xFF) << 8 | (
/*  99 */       PlatformDependent.getByte(address + 2L) & 0xFF) << 16 | 
/* 100 */       PlatformDependent.getByte(address + 3L) << 24;
/*     */   }
/*     */   
/*     */   static long getLong(long address) {
/* 104 */     if (UNALIGNED) {
/* 105 */       long v = PlatformDependent.getLong(address);
/* 106 */       return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? v : Long.reverseBytes(v);
/*     */     } 
/* 108 */     return PlatformDependent.getByte(address) << 56L | (
/* 109 */       PlatformDependent.getByte(address + 1L) & 0xFFL) << 48L | (
/* 110 */       PlatformDependent.getByte(address + 2L) & 0xFFL) << 40L | (
/* 111 */       PlatformDependent.getByte(address + 3L) & 0xFFL) << 32L | (
/* 112 */       PlatformDependent.getByte(address + 4L) & 0xFFL) << 24L | (
/* 113 */       PlatformDependent.getByte(address + 5L) & 0xFFL) << 16L | (
/* 114 */       PlatformDependent.getByte(address + 6L) & 0xFFL) << 8L | 
/* 115 */       PlatformDependent.getByte(address + 7L) & 0xFFL;
/*     */   }
/*     */   
/*     */   static long getLongLE(long address) {
/* 119 */     if (UNALIGNED) {
/* 120 */       long v = PlatformDependent.getLong(address);
/* 121 */       return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(v) : v;
/*     */     } 
/* 123 */     return PlatformDependent.getByte(address) & 0xFFL | (
/* 124 */       PlatformDependent.getByte(address + 1L) & 0xFFL) << 8L | (
/* 125 */       PlatformDependent.getByte(address + 2L) & 0xFFL) << 16L | (
/* 126 */       PlatformDependent.getByte(address + 3L) & 0xFFL) << 24L | (
/* 127 */       PlatformDependent.getByte(address + 4L) & 0xFFL) << 32L | (
/* 128 */       PlatformDependent.getByte(address + 5L) & 0xFFL) << 40L | (
/* 129 */       PlatformDependent.getByte(address + 6L) & 0xFFL) << 48L | 
/* 130 */       PlatformDependent.getByte(address + 7L) << 56L;
/*     */   }
/*     */   
/*     */   static void setByte(long address, int value) {
/* 134 */     PlatformDependent.putByte(address, (byte)value);
/*     */   }
/*     */   
/*     */   static void setShort(long address, int value) {
/* 138 */     if (UNALIGNED) {
/* 139 */       PlatformDependent.putShort(address, 
/* 140 */           PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short)value : Short.reverseBytes((short)value));
/*     */     } else {
/* 142 */       PlatformDependent.putByte(address, (byte)(value >>> 8));
/* 143 */       PlatformDependent.putByte(address + 1L, (byte)value);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setShortLE(long address, int value) {
/* 148 */     if (UNALIGNED) {
/* 149 */       PlatformDependent.putShort(address, 
/* 150 */           PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short)value) : (short)value);
/*     */     } else {
/* 152 */       PlatformDependent.putByte(address, (byte)value);
/* 153 */       PlatformDependent.putByte(address + 1L, (byte)(value >>> 8));
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setMedium(long address, int value) {
/* 158 */     PlatformDependent.putByte(address, (byte)(value >>> 16));
/* 159 */     if (UNALIGNED) {
/* 160 */       PlatformDependent.putShort(address + 1L, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short)value : 
/* 161 */           Short.reverseBytes((short)value));
/*     */     } else {
/* 163 */       PlatformDependent.putByte(address + 1L, (byte)(value >>> 8));
/* 164 */       PlatformDependent.putByte(address + 2L, (byte)value);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setMediumLE(long address, int value) {
/* 169 */     PlatformDependent.putByte(address, (byte)value);
/* 170 */     if (UNALIGNED) {
/* 171 */       PlatformDependent.putShort(address + 1L, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short)(value >>> 8)) : 
/* 172 */           (short)(value >>> 8));
/*     */     } else {
/* 174 */       PlatformDependent.putByte(address + 1L, (byte)(value >>> 8));
/* 175 */       PlatformDependent.putByte(address + 2L, (byte)(value >>> 16));
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setInt(long address, int value) {
/* 180 */     if (UNALIGNED) {
/* 181 */       PlatformDependent.putInt(address, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? value : Integer.reverseBytes(value));
/*     */     } else {
/* 183 */       PlatformDependent.putByte(address, (byte)(value >>> 24));
/* 184 */       PlatformDependent.putByte(address + 1L, (byte)(value >>> 16));
/* 185 */       PlatformDependent.putByte(address + 2L, (byte)(value >>> 8));
/* 186 */       PlatformDependent.putByte(address + 3L, (byte)value);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setIntLE(long address, int value) {
/* 191 */     if (UNALIGNED) {
/* 192 */       PlatformDependent.putInt(address, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(value) : value);
/*     */     } else {
/* 194 */       PlatformDependent.putByte(address, (byte)value);
/* 195 */       PlatformDependent.putByte(address + 1L, (byte)(value >>> 8));
/* 196 */       PlatformDependent.putByte(address + 2L, (byte)(value >>> 16));
/* 197 */       PlatformDependent.putByte(address + 3L, (byte)(value >>> 24));
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setLong(long address, long value) {
/* 202 */     if (UNALIGNED) {
/* 203 */       PlatformDependent.putLong(address, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? value : Long.reverseBytes(value));
/*     */     } else {
/* 205 */       PlatformDependent.putByte(address, (byte)(int)(value >>> 56L));
/* 206 */       PlatformDependent.putByte(address + 1L, (byte)(int)(value >>> 48L));
/* 207 */       PlatformDependent.putByte(address + 2L, (byte)(int)(value >>> 40L));
/* 208 */       PlatformDependent.putByte(address + 3L, (byte)(int)(value >>> 32L));
/* 209 */       PlatformDependent.putByte(address + 4L, (byte)(int)(value >>> 24L));
/* 210 */       PlatformDependent.putByte(address + 5L, (byte)(int)(value >>> 16L));
/* 211 */       PlatformDependent.putByte(address + 6L, (byte)(int)(value >>> 8L));
/* 212 */       PlatformDependent.putByte(address + 7L, (byte)(int)value);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setLongLE(long address, long value) {
/* 217 */     if (UNALIGNED) {
/* 218 */       PlatformDependent.putLong(address, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(value) : value);
/*     */     } else {
/* 220 */       PlatformDependent.putByte(address, (byte)(int)value);
/* 221 */       PlatformDependent.putByte(address + 1L, (byte)(int)(value >>> 8L));
/* 222 */       PlatformDependent.putByte(address + 2L, (byte)(int)(value >>> 16L));
/* 223 */       PlatformDependent.putByte(address + 3L, (byte)(int)(value >>> 24L));
/* 224 */       PlatformDependent.putByte(address + 4L, (byte)(int)(value >>> 32L));
/* 225 */       PlatformDependent.putByte(address + 5L, (byte)(int)(value >>> 40L));
/* 226 */       PlatformDependent.putByte(address + 6L, (byte)(int)(value >>> 48L));
/* 227 */       PlatformDependent.putByte(address + 7L, (byte)(int)(value >>> 56L));
/*     */     } 
/*     */   }
/*     */   
/*     */   static byte getByte(byte[] array, int index) {
/* 232 */     return PlatformDependent.getByte(array, index);
/*     */   }
/*     */   
/*     */   static short getShort(byte[] array, int index) {
/* 236 */     if (UNALIGNED) {
/* 237 */       short v = PlatformDependent.getShort(array, index);
/* 238 */       return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? v : Short.reverseBytes(v);
/*     */     } 
/* 240 */     return 
/* 241 */       (short)(PlatformDependent.getByte(array, index) << 8 | PlatformDependent.getByte(array, index + 1) & 0xFF);
/*     */   }
/*     */   
/*     */   static short getShortLE(byte[] array, int index) {
/* 245 */     if (UNALIGNED) {
/* 246 */       short v = PlatformDependent.getShort(array, index);
/* 247 */       return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(v) : v;
/*     */     } 
/* 249 */     return 
/* 250 */       (short)(PlatformDependent.getByte(array, index) & 0xFF | PlatformDependent.getByte(array, index + 1) << 8);
/*     */   }
/*     */   
/*     */   static int getUnsignedMedium(byte[] array, int index) {
/* 254 */     if (UNALIGNED) {
/* 255 */       return (PlatformDependent.getByte(array, index) & 0xFF) << 16 | (
/* 256 */         PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? PlatformDependent.getShort(array, index + 1) : 
/* 257 */         Short.reverseBytes(PlatformDependent.getShort(array, index + 1))) & 0xFFFF;
/*     */     }
/*     */     
/* 260 */     return (PlatformDependent.getByte(array, index) & 0xFF) << 16 | (
/* 261 */       PlatformDependent.getByte(array, index + 1) & 0xFF) << 8 | 
/* 262 */       PlatformDependent.getByte(array, index + 2) & 0xFF;
/*     */   }
/*     */   
/*     */   static int getUnsignedMediumLE(byte[] array, int index) {
/* 266 */     if (UNALIGNED) {
/* 267 */       return PlatformDependent.getByte(array, index) & 0xFF | ((
/* 268 */         PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(PlatformDependent.getShort(array, index + 1)) : 
/* 269 */         PlatformDependent.getShort(array, index + 1)) & 0xFFFF) << 8;
/*     */     }
/* 271 */     return PlatformDependent.getByte(array, index) & 0xFF | (
/* 272 */       PlatformDependent.getByte(array, index + 1) & 0xFF) << 8 | (
/* 273 */       PlatformDependent.getByte(array, index + 2) & 0xFF) << 16;
/*     */   }
/*     */   
/*     */   static int getInt(byte[] array, int index) {
/* 277 */     if (UNALIGNED) {
/* 278 */       int v = PlatformDependent.getInt(array, index);
/* 279 */       return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? v : Integer.reverseBytes(v);
/*     */     } 
/* 281 */     return PlatformDependent.getByte(array, index) << 24 | (
/* 282 */       PlatformDependent.getByte(array, index + 1) & 0xFF) << 16 | (
/* 283 */       PlatformDependent.getByte(array, index + 2) & 0xFF) << 8 | 
/* 284 */       PlatformDependent.getByte(array, index + 3) & 0xFF;
/*     */   }
/*     */   
/*     */   static int getIntLE(byte[] array, int index) {
/* 288 */     if (UNALIGNED) {
/* 289 */       int v = PlatformDependent.getInt(array, index);
/* 290 */       return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(v) : v;
/*     */     } 
/* 292 */     return PlatformDependent.getByte(array, index) & 0xFF | (
/* 293 */       PlatformDependent.getByte(array, index + 1) & 0xFF) << 8 | (
/* 294 */       PlatformDependent.getByte(array, index + 2) & 0xFF) << 16 | 
/* 295 */       PlatformDependent.getByte(array, index + 3) << 24;
/*     */   }
/*     */   
/*     */   static long getLong(byte[] array, int index) {
/* 299 */     if (UNALIGNED) {
/* 300 */       long v = PlatformDependent.getLong(array, index);
/* 301 */       return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? v : Long.reverseBytes(v);
/*     */     } 
/* 303 */     return PlatformDependent.getByte(array, index) << 56L | (
/* 304 */       PlatformDependent.getByte(array, index + 1) & 0xFFL) << 48L | (
/* 305 */       PlatformDependent.getByte(array, index + 2) & 0xFFL) << 40L | (
/* 306 */       PlatformDependent.getByte(array, index + 3) & 0xFFL) << 32L | (
/* 307 */       PlatformDependent.getByte(array, index + 4) & 0xFFL) << 24L | (
/* 308 */       PlatformDependent.getByte(array, index + 5) & 0xFFL) << 16L | (
/* 309 */       PlatformDependent.getByte(array, index + 6) & 0xFFL) << 8L | 
/* 310 */       PlatformDependent.getByte(array, index + 7) & 0xFFL;
/*     */   }
/*     */   
/*     */   static long getLongLE(byte[] array, int index) {
/* 314 */     if (UNALIGNED) {
/* 315 */       long v = PlatformDependent.getLong(array, index);
/* 316 */       return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(v) : v;
/*     */     } 
/* 318 */     return PlatformDependent.getByte(array, index) & 0xFFL | (
/* 319 */       PlatformDependent.getByte(array, index + 1) & 0xFFL) << 8L | (
/* 320 */       PlatformDependent.getByte(array, index + 2) & 0xFFL) << 16L | (
/* 321 */       PlatformDependent.getByte(array, index + 3) & 0xFFL) << 24L | (
/* 322 */       PlatformDependent.getByte(array, index + 4) & 0xFFL) << 32L | (
/* 323 */       PlatformDependent.getByte(array, index + 5) & 0xFFL) << 40L | (
/* 324 */       PlatformDependent.getByte(array, index + 6) & 0xFFL) << 48L | 
/* 325 */       PlatformDependent.getByte(array, index + 7) << 56L;
/*     */   }
/*     */   
/*     */   static void setByte(byte[] array, int index, int value) {
/* 329 */     PlatformDependent.putByte(array, index, (byte)value);
/*     */   }
/*     */   
/*     */   static void setShort(byte[] array, int index, int value) {
/* 333 */     if (UNALIGNED) {
/* 334 */       PlatformDependent.putShort(array, index, 
/* 335 */           PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short)value : Short.reverseBytes((short)value));
/*     */     } else {
/* 337 */       PlatformDependent.putByte(array, index, (byte)(value >>> 8));
/* 338 */       PlatformDependent.putByte(array, index + 1, (byte)value);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setShortLE(byte[] array, int index, int value) {
/* 343 */     if (UNALIGNED) {
/* 344 */       PlatformDependent.putShort(array, index, 
/* 345 */           PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short)value) : (short)value);
/*     */     } else {
/* 347 */       PlatformDependent.putByte(array, index, (byte)value);
/* 348 */       PlatformDependent.putByte(array, index + 1, (byte)(value >>> 8));
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setMedium(byte[] array, int index, int value) {
/* 353 */     PlatformDependent.putByte(array, index, (byte)(value >>> 16));
/* 354 */     if (UNALIGNED) {
/* 355 */       PlatformDependent.putShort(array, index + 1, 
/* 356 */           PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short)value : 
/* 357 */           Short.reverseBytes((short)value));
/*     */     } else {
/* 359 */       PlatformDependent.putByte(array, index + 1, (byte)(value >>> 8));
/* 360 */       PlatformDependent.putByte(array, index + 2, (byte)value);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setMediumLE(byte[] array, int index, int value) {
/* 365 */     PlatformDependent.putByte(array, index, (byte)value);
/* 366 */     if (UNALIGNED) {
/* 367 */       PlatformDependent.putShort(array, index + 1, 
/* 368 */           PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short)(value >>> 8)) : 
/* 369 */           (short)(value >>> 8));
/*     */     } else {
/* 371 */       PlatformDependent.putByte(array, index + 1, (byte)(value >>> 8));
/* 372 */       PlatformDependent.putByte(array, index + 2, (byte)(value >>> 16));
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setInt(byte[] array, int index, int value) {
/* 377 */     if (UNALIGNED) {
/* 378 */       PlatformDependent.putInt(array, index, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? value : Integer.reverseBytes(value));
/*     */     } else {
/* 380 */       PlatformDependent.putByte(array, index, (byte)(value >>> 24));
/* 381 */       PlatformDependent.putByte(array, index + 1, (byte)(value >>> 16));
/* 382 */       PlatformDependent.putByte(array, index + 2, (byte)(value >>> 8));
/* 383 */       PlatformDependent.putByte(array, index + 3, (byte)value);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setIntLE(byte[] array, int index, int value) {
/* 388 */     if (UNALIGNED) {
/* 389 */       PlatformDependent.putInt(array, index, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(value) : value);
/*     */     } else {
/* 391 */       PlatformDependent.putByte(array, index, (byte)value);
/* 392 */       PlatformDependent.putByte(array, index + 1, (byte)(value >>> 8));
/* 393 */       PlatformDependent.putByte(array, index + 2, (byte)(value >>> 16));
/* 394 */       PlatformDependent.putByte(array, index + 3, (byte)(value >>> 24));
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setLong(byte[] array, int index, long value) {
/* 399 */     if (UNALIGNED) {
/* 400 */       PlatformDependent.putLong(array, index, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? value : Long.reverseBytes(value));
/*     */     } else {
/* 402 */       PlatformDependent.putByte(array, index, (byte)(int)(value >>> 56L));
/* 403 */       PlatformDependent.putByte(array, index + 1, (byte)(int)(value >>> 48L));
/* 404 */       PlatformDependent.putByte(array, index + 2, (byte)(int)(value >>> 40L));
/* 405 */       PlatformDependent.putByte(array, index + 3, (byte)(int)(value >>> 32L));
/* 406 */       PlatformDependent.putByte(array, index + 4, (byte)(int)(value >>> 24L));
/* 407 */       PlatformDependent.putByte(array, index + 5, (byte)(int)(value >>> 16L));
/* 408 */       PlatformDependent.putByte(array, index + 6, (byte)(int)(value >>> 8L));
/* 409 */       PlatformDependent.putByte(array, index + 7, (byte)(int)value);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setLongLE(byte[] array, int index, long value) {
/* 414 */     if (UNALIGNED) {
/* 415 */       PlatformDependent.putLong(array, index, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(value) : value);
/*     */     } else {
/* 417 */       PlatformDependent.putByte(array, index, (byte)(int)value);
/* 418 */       PlatformDependent.putByte(array, index + 1, (byte)(int)(value >>> 8L));
/* 419 */       PlatformDependent.putByte(array, index + 2, (byte)(int)(value >>> 16L));
/* 420 */       PlatformDependent.putByte(array, index + 3, (byte)(int)(value >>> 24L));
/* 421 */       PlatformDependent.putByte(array, index + 4, (byte)(int)(value >>> 32L));
/* 422 */       PlatformDependent.putByte(array, index + 5, (byte)(int)(value >>> 40L));
/* 423 */       PlatformDependent.putByte(array, index + 6, (byte)(int)(value >>> 48L));
/* 424 */       PlatformDependent.putByte(array, index + 7, (byte)(int)(value >>> 56L));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void batchSetZero(byte[] data, int index, int length) {
/* 429 */     int longBatches = length >>> 3;
/* 430 */     for (int i = 0; i < longBatches; i++) {
/* 431 */       PlatformDependent.putLong(data, index, 0L);
/* 432 */       index += 8;
/*     */     } 
/* 434 */     int remaining = length & 0x7;
/* 435 */     for (int j = 0; j < remaining; j++) {
/* 436 */       PlatformDependent.putByte(data, index + j, (byte)0);
/*     */     }
/*     */   }
/*     */   
/*     */   static void setZero(byte[] array, int index, int length) {
/* 441 */     if (length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 445 */     if (UNALIGNED && length <= 64) {
/* 446 */       batchSetZero(array, index, length);
/*     */     } else {
/* 448 */       PlatformDependent.setMemory(array, index, length, (byte)0);
/*     */     } 
/*     */   }
/*     */   
/*     */   static ByteBuf copy(AbstractByteBuf buf, long addr, int index, int length) {
/* 453 */     buf.checkIndex(index, length);
/* 454 */     ByteBuf copy = buf.alloc().directBuffer(length, buf.maxCapacity());
/* 455 */     if (length != 0) {
/* 456 */       if (copy.hasMemoryAddress()) {
/* 457 */         PlatformDependent.copyMemory(addr, copy.memoryAddress(), length);
/* 458 */         copy.setIndex(0, length);
/*     */       } else {
/* 460 */         copy.writeBytes(buf, index, length);
/*     */       } 
/*     */     }
/* 463 */     return copy;
/*     */   }
/*     */   
/*     */   static int setBytes(AbstractByteBuf buf, long addr, int index, InputStream in, int length) throws IOException {
/* 467 */     buf.checkIndex(index, length);
/* 468 */     ByteBuf tmpBuf = buf.alloc().heapBuffer(length);
/*     */     try {
/* 470 */       byte[] tmp = tmpBuf.array();
/* 471 */       int offset = tmpBuf.arrayOffset();
/* 472 */       int readBytes = in.read(tmp, offset, length);
/* 473 */       if (readBytes > 0) {
/* 474 */         PlatformDependent.copyMemory(tmp, offset, addr, readBytes);
/*     */       }
/* 476 */       return readBytes;
/*     */     } finally {
/* 478 */       tmpBuf.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   static void getBytes(AbstractByteBuf buf, long addr, int index, ByteBuf dst, int dstIndex, int length) {
/* 483 */     buf.checkIndex(index, length);
/* 484 */     ObjectUtil.checkNotNull(dst, "dst");
/* 485 */     if (MathUtil.isOutOfBounds(dstIndex, length, dst.capacity())) {
/* 486 */       throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
/*     */     }
/*     */     
/* 489 */     if (dst.hasMemoryAddress()) {
/* 490 */       PlatformDependent.copyMemory(addr, dst.memoryAddress() + dstIndex, length);
/* 491 */     } else if (dst.hasArray()) {
/* 492 */       PlatformDependent.copyMemory(addr, dst.array(), dst.arrayOffset() + dstIndex, length);
/*     */     } else {
/* 494 */       dst.setBytes(dstIndex, buf, index, length);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void getBytes(AbstractByteBuf buf, long addr, int index, byte[] dst, int dstIndex, int length) {
/* 499 */     buf.checkIndex(index, length);
/* 500 */     ObjectUtil.checkNotNull(dst, "dst");
/* 501 */     if (MathUtil.isOutOfBounds(dstIndex, length, dst.length)) {
/* 502 */       throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
/*     */     }
/* 504 */     if (length != 0) {
/* 505 */       PlatformDependent.copyMemory(addr, dst, dstIndex, length);
/*     */     }
/*     */   }
/*     */   
/*     */   static void getBytes(AbstractByteBuf buf, long addr, int index, ByteBuffer dst) {
/* 510 */     buf.checkIndex(index, dst.remaining());
/* 511 */     if (dst.remaining() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 515 */     if (dst.isDirect()) {
/* 516 */       if (dst.isReadOnly())
/*     */       {
/* 518 */         throw new ReadOnlyBufferException();
/*     */       }
/*     */       
/* 521 */       long dstAddress = PlatformDependent.directBufferAddress(dst);
/* 522 */       PlatformDependent.copyMemory(addr, dstAddress + dst.position(), dst.remaining());
/* 523 */       dst.position(dst.position() + dst.remaining());
/* 524 */     } else if (dst.hasArray()) {
/*     */       
/* 526 */       PlatformDependent.copyMemory(addr, dst.array(), dst.arrayOffset() + dst.position(), dst.remaining());
/* 527 */       dst.position(dst.position() + dst.remaining());
/*     */     } else {
/* 529 */       dst.put(buf.nioBuffer());
/*     */     } 
/*     */   }
/*     */   
/*     */   static void setBytes(AbstractByteBuf buf, long addr, int index, ByteBuf src, int srcIndex, int length) {
/* 534 */     buf.checkIndex(index, length);
/* 535 */     ObjectUtil.checkNotNull(src, "src");
/* 536 */     if (MathUtil.isOutOfBounds(srcIndex, length, src.capacity())) {
/* 537 */       throw new IndexOutOfBoundsException("srcIndex: " + srcIndex);
/*     */     }
/*     */     
/* 540 */     if (length != 0) {
/* 541 */       if (src.hasMemoryAddress()) {
/* 542 */         PlatformDependent.copyMemory(src.memoryAddress() + srcIndex, addr, length);
/* 543 */       } else if (src.hasArray()) {
/* 544 */         PlatformDependent.copyMemory(src.array(), src.arrayOffset() + srcIndex, addr, length);
/*     */       } else {
/* 546 */         src.getBytes(srcIndex, buf, index, length);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static void setBytes(AbstractByteBuf buf, long addr, int index, byte[] src, int srcIndex, int length) {
/* 552 */     buf.checkIndex(index, length);
/*     */ 
/*     */     
/* 555 */     ObjectUtil.checkNotNull(src, "src");
/* 556 */     if (MathUtil.isOutOfBounds(srcIndex, length, src.length)) {
/* 557 */       throw new IndexOutOfBoundsException("srcIndex: " + srcIndex);
/*     */     }
/*     */     
/* 560 */     if (length != 0) {
/* 561 */       PlatformDependent.copyMemory(src, srcIndex, addr, length);
/*     */     }
/*     */   }
/*     */   
/*     */   static void setBytes(AbstractByteBuf buf, long addr, int index, ByteBuffer src) {
/* 566 */     int length = src.remaining();
/* 567 */     if (length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 571 */     if (src.isDirect()) {
/* 572 */       buf.checkIndex(index, length);
/*     */       
/* 574 */       long srcAddress = PlatformDependent.directBufferAddress(src);
/* 575 */       PlatformDependent.copyMemory(srcAddress + src.position(), addr, length);
/* 576 */       src.position(src.position() + length);
/* 577 */     } else if (src.hasArray()) {
/* 578 */       buf.checkIndex(index, length);
/*     */       
/* 580 */       PlatformDependent.copyMemory(src.array(), src.arrayOffset() + src.position(), addr, length);
/* 581 */       src.position(src.position() + length);
/*     */     }
/* 583 */     else if (length < 8) {
/* 584 */       setSingleBytes(buf, addr, index, src, length);
/*     */     } else {
/*     */       
/* 587 */       assert buf.nioBufferCount() == 1;
/* 588 */       ByteBuffer internalBuffer = buf.internalNioBuffer(index, length);
/* 589 */       internalBuffer.put(src);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setSingleBytes(AbstractByteBuf buf, long addr, int index, ByteBuffer src, int length) {
/* 596 */     buf.checkIndex(index, length);
/* 597 */     int srcPosition = src.position();
/* 598 */     int srcLimit = src.limit();
/* 599 */     long dstAddr = addr;
/* 600 */     for (int srcIndex = srcPosition; srcIndex < srcLimit; srcIndex++) {
/* 601 */       byte value = src.get(srcIndex);
/* 602 */       PlatformDependent.putByte(dstAddr, value);
/* 603 */       dstAddr++;
/*     */     } 
/* 605 */     src.position(srcLimit);
/*     */   }
/*     */   
/*     */   static void getBytes(AbstractByteBuf buf, long addr, int index, OutputStream out, int length) throws IOException {
/* 609 */     buf.checkIndex(index, length);
/* 610 */     if (length != 0) {
/* 611 */       int len = Math.min(length, 8192);
/* 612 */       if (len <= 1024 || !buf.alloc().isDirectBufferPooled()) {
/* 613 */         getBytes(addr, ByteBufUtil.threadLocalTempArray(len), 0, len, out, length);
/*     */       } else {
/*     */         
/* 616 */         ByteBuf tmpBuf = buf.alloc().heapBuffer(len);
/*     */         try {
/* 618 */           byte[] tmp = tmpBuf.array();
/* 619 */           int offset = tmpBuf.arrayOffset();
/* 620 */           getBytes(addr, tmp, offset, len, out, length);
/*     */         } finally {
/* 622 */           tmpBuf.release();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void getBytes(long inAddr, byte[] in, int inOffset, int inLen, OutputStream out, int outLen) throws IOException {
/*     */     do {
/* 631 */       int len = Math.min(inLen, outLen);
/* 632 */       PlatformDependent.copyMemory(inAddr, in, inOffset, len);
/* 633 */       out.write(in, inOffset, len);
/* 634 */       outLen -= len;
/* 635 */       inAddr += len;
/* 636 */     } while (outLen > 0);
/*     */   }
/*     */   
/*     */   private static void batchSetZero(long addr, int length) {
/* 640 */     int longBatches = length >>> 3;
/* 641 */     for (int i = 0; i < longBatches; i++) {
/* 642 */       PlatformDependent.putLong(addr, 0L);
/* 643 */       addr += 8L;
/*     */     } 
/* 645 */     int remaining = length & 0x7;
/* 646 */     for (int j = 0; j < remaining; j++) {
/* 647 */       PlatformDependent.putByte(addr + j, (byte)0);
/*     */     }
/*     */   }
/*     */   
/*     */   static void setZero(long addr, int length) {
/* 652 */     if (length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 656 */     if (length <= 64) {
/* 657 */       if (!UNALIGNED) {
/*     */         
/* 659 */         int bytesToGetAligned = zeroTillAligned(addr, length);
/* 660 */         addr += bytesToGetAligned;
/* 661 */         length -= bytesToGetAligned;
/* 662 */         if (length == 0) {
/*     */           return;
/*     */         }
/* 665 */         assert is8BytesAligned(addr);
/*     */       } 
/* 667 */       batchSetZero(addr, length);
/*     */     } else {
/* 669 */       PlatformDependent.setMemory(addr, length, (byte)0);
/*     */     } 
/*     */   }
/*     */   
/*     */   static long next8bytesAlignedAddr(long addr) {
/* 674 */     return addr + 7L & 0xFFFFFFFFFFFFFFF8L;
/*     */   }
/*     */   
/*     */   static boolean is8BytesAligned(long addr) {
/* 678 */     return ((addr & 0x7L) == 0L);
/*     */   }
/*     */   
/*     */   private static int zeroTillAligned(long addr, int length) {
/* 682 */     long alignedAddr = next8bytesAlignedAddr(addr);
/* 683 */     int bytesToGetAligned = (int)(alignedAddr - addr);
/* 684 */     int toZero = Math.min(bytesToGetAligned, length);
/* 685 */     for (int i = 0; i < toZero; i++) {
/* 686 */       PlatformDependent.putByte(addr + i, (byte)0);
/*     */     }
/* 688 */     return toZero;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static UnpooledUnsafeDirectByteBuf newUnsafeDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity, boolean allowSectionedInternalNioBufferAccess) {
/* 694 */     if (PlatformDependent.useDirectBufferNoCleaner()) {
/* 695 */       return new UnpooledUnsafeNoCleanerDirectByteBuf(alloc, initialCapacity, maxCapacity, allowSectionedInternalNioBufferAccess);
/*     */     }
/*     */     
/* 698 */     return new UnpooledUnsafeDirectByteBuf(alloc, initialCapacity, maxCapacity, allowSectionedInternalNioBufferAccess);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\UnsafeByteBufUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */