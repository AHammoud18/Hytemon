/*     */ package io.netty.handler.codec.base64;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.ByteProcessor;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.nio.ByteOrder;
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
/*     */   private static final int MAX_LINE_LENGTH = 76;
/*     */   private static final byte EQUALS_SIGN = 61;
/*     */   private static final byte NEW_LINE = 10;
/*     */   private static final byte WHITE_SPACE_ENC = -5;
/*     */   private static final byte EQUALS_SIGN_ENC = -1;
/*     */   
/*     */   private static byte[] alphabet(Base64Dialect dialect) {
/*  55 */     return ((Base64Dialect)ObjectUtil.checkNotNull(dialect, "dialect")).alphabet;
/*     */   }
/*     */   
/*     */   private static byte[] decodabet(Base64Dialect dialect) {
/*  59 */     return ((Base64Dialect)ObjectUtil.checkNotNull(dialect, "dialect")).decodabet;
/*     */   }
/*     */   
/*     */   private static boolean breakLines(Base64Dialect dialect) {
/*  63 */     return ((Base64Dialect)ObjectUtil.checkNotNull(dialect, "dialect")).breakLinesByDefault;
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf src) {
/*  67 */     return encode(src, Base64Dialect.STANDARD);
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf src, Base64Dialect dialect) {
/*  71 */     return encode(src, breakLines(dialect), dialect);
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf src, Base64Dialect dialect, boolean addPadding) {
/*  75 */     return encode(src, breakLines(dialect), dialect, addPadding);
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf src, boolean breakLines) {
/*  79 */     return encode(src, breakLines, Base64Dialect.STANDARD);
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf src, boolean breakLines, boolean addPadding) {
/*  83 */     return encode(src, breakLines, Base64Dialect.STANDARD, addPadding);
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf src, boolean breakLines, Base64Dialect dialect) {
/*  87 */     ObjectUtil.checkNotNull(src, "src");
/*     */     
/*  89 */     ByteBuf dest = encode(src, src.readerIndex(), src.readableBytes(), breakLines, dialect);
/*  90 */     src.readerIndex(src.writerIndex());
/*  91 */     return dest;
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf src, boolean breakLines, Base64Dialect dialect, boolean addPadding) {
/*  95 */     ObjectUtil.checkNotNull(src, "src");
/*     */     
/*  97 */     ByteBuf dest = encode(src, src.readerIndex(), src.readableBytes(), breakLines, dialect, addPadding);
/*  98 */     src.readerIndex(src.writerIndex());
/*  99 */     return dest;
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf src, int off, int len) {
/* 103 */     return encode(src, off, len, Base64Dialect.STANDARD);
/*     */   }
/*     */   
/*     */   public static ByteBuf encode(ByteBuf src, int off, int len, Base64Dialect dialect) {
/* 107 */     return encode(src, off, len, breakLines(dialect), dialect);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ByteBuf encode(ByteBuf src, int off, int len, boolean breakLines) {
/* 112 */     return encode(src, off, len, breakLines, Base64Dialect.STANDARD);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ByteBuf encode(ByteBuf src, int off, int len, boolean breakLines, Base64Dialect dialect) {
/* 117 */     return encode(src, off, len, breakLines, dialect, src.alloc(), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ByteBuf encode(ByteBuf src, int off, int len, boolean breakLines, Base64Dialect dialect, boolean addPadding) {
/* 122 */     return encode(src, off, len, breakLines, dialect, src.alloc(), addPadding);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ByteBuf encode(ByteBuf src, int off, int len, boolean breakLines, Base64Dialect dialect, ByteBufAllocator allocator) {
/* 127 */     return encode(src, off, len, breakLines, dialect, allocator, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ByteBuf encode(ByteBuf src, int off, int len, boolean breakLines, Base64Dialect dialect, ByteBufAllocator allocator, boolean addPadding) {
/* 133 */     ObjectUtil.checkNotNull(src, "src");
/* 134 */     ObjectUtil.checkNotNull(dialect, "dialect");
/*     */     
/* 136 */     int capacity = encodedBufferSize(len, breakLines);
/* 137 */     ByteBuf destBuf = allocator.buffer(capacity).order(src.order());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     ByteBuf dest = (destBuf.unwrap() == null || !destBuf.isContiguous()) ? destBuf : (destBuf.hasArray() ? Unpooled.wrappedBuffer(destBuf.array(), destBuf.arrayOffset(), capacity).order(src.order()) : Unpooled.wrappedBuffer(destBuf.internalNioBuffer(0, capacity)).order(src.order()));
/*     */     
/* 144 */     byte[] alphabet = alphabet(dialect);
/* 145 */     int d = 0;
/* 146 */     int e = 0;
/* 147 */     int len2 = len - 2;
/* 148 */     int lineLength = 0;
/* 149 */     for (; d < len2; d += 3, e += 4) {
/* 150 */       encode3to4(src, d + off, 3, dest, e, alphabet, addPadding);
/*     */       
/* 152 */       lineLength += 4;
/*     */       
/* 154 */       if (breakLines && lineLength == 76) {
/* 155 */         dest.setByte(e + 4, 10);
/* 156 */         e++;
/* 157 */         lineLength = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     if (d < len) {
/* 162 */       e += encode3to4(src, d + off, len - d, dest, e, alphabet, addPadding);
/*     */     }
/*     */ 
/*     */     
/* 166 */     if (e > 1 && dest.getByte(e - 1) == 10) {
/* 167 */       e--;
/*     */     }
/* 169 */     if (dest != destBuf) {
/* 170 */       dest.release();
/*     */     }
/* 172 */     return destBuf.setIndex(0, e);
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
/*     */   private static int encode3to4(ByteBuf src, int srcOffset, int numSigBytes, ByteBuf dest, int destOffset, byte[] alphabet, boolean addPadding) {
/* 189 */     if (src.order() == ByteOrder.BIG_ENDIAN) {
/*     */       
/* 191 */       switch (numSigBytes)
/*     */       { case 1:
/* 193 */           i = toInt(src.getByte(srcOffset));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 202 */           return encode3to4BigEndian(i, numSigBytes, dest, destOffset, alphabet, addPadding);case 2: i = toIntBE(src.getShort(srcOffset)); return encode3to4BigEndian(i, numSigBytes, dest, destOffset, alphabet, addPadding); }  int i = (numSigBytes <= 0) ? 0 : toIntBE(src.getMedium(srcOffset)); return encode3to4BigEndian(i, numSigBytes, dest, destOffset, alphabet, addPadding);
/*     */     } 
/*     */     
/* 205 */     switch (numSigBytes)
/*     */     { case 1:
/* 207 */         inBuff = toInt(src.getByte(srcOffset));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 216 */         return encode3to4LittleEndian(inBuff, numSigBytes, dest, destOffset, alphabet, addPadding);case 2: inBuff = toIntLE(src.getShort(srcOffset)); return encode3to4LittleEndian(inBuff, numSigBytes, dest, destOffset, alphabet, addPadding); }  int inBuff = (numSigBytes <= 0) ? 0 : toIntLE(src.getMedium(srcOffset)); return encode3to4LittleEndian(inBuff, numSigBytes, dest, destOffset, alphabet, addPadding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int encodedBufferSize(int len, boolean breakLines) {
/* 223 */     long len43 = (len << 2L) / 3L;
/*     */ 
/*     */     
/* 226 */     long ret = len43 + 3L & 0xFFFFFFFFFFFFFFFCL;
/*     */     
/* 228 */     if (breakLines) {
/* 229 */       ret += len43 / 76L;
/*     */     }
/*     */     
/* 232 */     return (ret < 2147483647L) ? (int)ret : Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   private static int toInt(byte value) {
/* 236 */     return (value & 0xFF) << 16;
/*     */   }
/*     */   
/*     */   private static int toIntBE(short value) {
/* 240 */     return (value & 0xFF00) << 8 | (value & 0xFF) << 8;
/*     */   }
/*     */   
/*     */   private static int toIntLE(short value) {
/* 244 */     return (value & 0xFF) << 16 | value & 0xFF00;
/*     */   }
/*     */   
/*     */   private static int toIntBE(int mediumValue) {
/* 248 */     return mediumValue & 0xFF0000 | mediumValue & 0xFF00 | mediumValue & 0xFF;
/*     */   }
/*     */   
/*     */   private static int toIntLE(int mediumValue) {
/* 252 */     return (mediumValue & 0xFF) << 16 | mediumValue & 0xFF00 | (mediumValue & 0xFF0000) >>> 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int encode3to4BigEndian(int inBuff, int numSigBytes, ByteBuf dest, int destOffset, byte[] alphabet, boolean addPadding) {
/* 258 */     switch (numSigBytes) {
/*     */       case 3:
/* 260 */         dest.setInt(destOffset, alphabet[inBuff >>> 18] << 24 | alphabet[inBuff >>> 12 & 0x3F] << 16 | alphabet[inBuff >>> 6 & 0x3F] << 8 | alphabet[inBuff & 0x3F]);
/*     */ 
/*     */ 
/*     */         
/* 264 */         return 4;
/*     */       case 2:
/* 266 */         if (addPadding) {
/* 267 */           dest.setInt(destOffset, alphabet[inBuff >>> 18] << 24 | alphabet[inBuff >>> 12 & 0x3F] << 16 | alphabet[inBuff >>> 6 & 0x3F] << 8 | 0x3D);
/*     */ 
/*     */ 
/*     */           
/* 271 */           return 4;
/*     */         } 
/* 273 */         dest.setMedium(destOffset, alphabet[inBuff >>> 18] << 16 | alphabet[inBuff >>> 12 & 0x3F] << 8 | alphabet[inBuff >>> 6 & 0x3F]);
/*     */ 
/*     */         
/* 276 */         return 3;
/*     */       
/*     */       case 1:
/* 279 */         if (addPadding) {
/* 280 */           dest.setInt(destOffset, alphabet[inBuff >>> 18] << 24 | alphabet[inBuff >>> 12 & 0x3F] << 16 | 0x3D00 | 0x3D);
/*     */ 
/*     */ 
/*     */           
/* 284 */           return 4;
/*     */         } 
/* 286 */         dest.setShort(destOffset, alphabet[inBuff >>> 18] << 8 | alphabet[inBuff >>> 12 & 0x3F]);
/*     */         
/* 288 */         return 2;
/*     */     } 
/*     */ 
/*     */     
/* 292 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int encode3to4LittleEndian(int inBuff, int numSigBytes, ByteBuf dest, int destOffset, byte[] alphabet, boolean addPadding) {
/* 299 */     switch (numSigBytes) {
/*     */       case 3:
/* 301 */         dest.setInt(destOffset, alphabet[inBuff >>> 18] | alphabet[inBuff >>> 12 & 0x3F] << 8 | alphabet[inBuff >>> 6 & 0x3F] << 16 | alphabet[inBuff & 0x3F] << 24);
/*     */ 
/*     */ 
/*     */         
/* 305 */         return 4;
/*     */       case 2:
/* 307 */         if (addPadding) {
/* 308 */           dest.setInt(destOffset, alphabet[inBuff >>> 18] | alphabet[inBuff >>> 12 & 0x3F] << 8 | alphabet[inBuff >>> 6 & 0x3F] << 16 | 0x3D000000);
/*     */ 
/*     */ 
/*     */           
/* 312 */           return 4;
/*     */         } 
/* 314 */         dest.setMedium(destOffset, alphabet[inBuff >>> 18] | alphabet[inBuff >>> 12 & 0x3F] << 8 | alphabet[inBuff >>> 6 & 0x3F] << 16);
/*     */ 
/*     */         
/* 317 */         return 3;
/*     */       
/*     */       case 1:
/* 320 */         if (addPadding) {
/* 321 */           dest.setInt(destOffset, alphabet[inBuff >>> 18] | alphabet[inBuff >>> 12 & 0x3F] << 8 | 0x3D0000 | 0x3D000000);
/*     */ 
/*     */ 
/*     */           
/* 325 */           return 4;
/*     */         } 
/* 327 */         dest.setShort(destOffset, alphabet[inBuff >>> 18] | alphabet[inBuff >>> 12 & 0x3F] << 8);
/*     */         
/* 329 */         return 2;
/*     */     } 
/*     */ 
/*     */     
/* 333 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ByteBuf decode(ByteBuf src) {
/* 338 */     return decode(src, Base64Dialect.STANDARD);
/*     */   }
/*     */   
/*     */   public static ByteBuf decode(ByteBuf src, Base64Dialect dialect) {
/* 342 */     ObjectUtil.checkNotNull(src, "src");
/*     */     
/* 344 */     ByteBuf dest = decode(src, src.readerIndex(), src.readableBytes(), dialect);
/* 345 */     src.readerIndex(src.writerIndex());
/* 346 */     return dest;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ByteBuf decode(ByteBuf src, int off, int len) {
/* 351 */     return decode(src, off, len, Base64Dialect.STANDARD);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ByteBuf decode(ByteBuf src, int off, int len, Base64Dialect dialect) {
/* 356 */     return decode(src, off, len, dialect, src.alloc());
/*     */   }
/*     */ 
/*     */   
/*     */   public static ByteBuf decode(ByteBuf src, int off, int len, Base64Dialect dialect, ByteBufAllocator allocator) {
/* 361 */     ObjectUtil.checkNotNull(src, "src");
/* 362 */     ObjectUtil.checkNotNull(dialect, "dialect");
/*     */ 
/*     */     
/* 365 */     return (new Decoder()).decode(src, off, len, allocator, dialect);
/*     */   }
/*     */ 
/*     */   
/*     */   static int decodedBufferSize(int len) {
/* 370 */     return len - (len >>> 2);
/*     */   }
/*     */   
/*     */   private static final class Decoder implements ByteProcessor {
/* 374 */     private final byte[] b4 = new byte[4];
/*     */     private int b4Posn;
/*     */     private byte[] decodabet;
/*     */     private int outBuffPosn;
/*     */     private ByteBuf dest;
/*     */     
/*     */     ByteBuf decode(ByteBuf src, int off, int len, ByteBufAllocator allocator, Base64Dialect dialect) {
/* 381 */       this.dest = allocator.buffer(Base64.decodedBufferSize(len)).order(src.order());
/*     */       
/* 383 */       this.decodabet = Base64.decodabet(dialect);
/*     */       try {
/* 385 */         src.forEachByte(off, len, this);
/*     */ 
/*     */         
/* 388 */         if (this.b4Posn == 1) {
/* 389 */           throw new IllegalArgumentException("Invalid Base64 input, single remaining character implies incorrect length or padding");
/*     */         }
/* 391 */         if (this.b4Posn == 2) {
/* 392 */           this.b4[2] = 61;
/* 393 */           this.b4[3] = 61;
/* 394 */           this.outBuffPosn += decode4to3(this.b4, this.dest, this.outBuffPosn, this.decodabet);
/* 395 */         } else if (this.b4Posn == 3) {
/* 396 */           this.b4[3] = 61;
/* 397 */           this.outBuffPosn += decode4to3(this.b4, this.dest, this.outBuffPosn, this.decodabet);
/*     */         } 
/*     */         
/* 400 */         return this.dest.slice(0, this.outBuffPosn);
/* 401 */       } catch (Throwable cause) {
/* 402 */         this.dest.release();
/* 403 */         PlatformDependent.throwException(cause);
/* 404 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean process(byte value) throws Exception {
/* 410 */       if (value > 0) {
/* 411 */         byte sbiDecode = this.decodabet[value];
/* 412 */         if (sbiDecode >= -5) {
/* 413 */           if (sbiDecode >= -1) {
/* 414 */             this.b4[this.b4Posn++] = value;
/* 415 */             if (this.b4Posn > 3) {
/* 416 */               this.outBuffPosn += decode4to3(this.b4, this.dest, this.outBuffPosn, this.decodabet);
/* 417 */               this.b4Posn = 0;
/*     */ 
/*     */               
/* 420 */               return (value != 61);
/*     */             } 
/*     */           } 
/* 423 */           return true;
/*     */         } 
/*     */       } 
/* 426 */       throw new IllegalArgumentException("invalid Base64 input character: " + (short)(value & 0xFF) + " (decimal)");
/*     */     }
/*     */     
/*     */     private static int decode4to3(byte[] src, ByteBuf dest, int destOffset, byte[] decodabet) {
/*     */       int decodedValue;
/* 431 */       byte src0 = src[0];
/* 432 */       byte src1 = src[1];
/* 433 */       byte src2 = src[2];
/*     */       
/* 435 */       if (src2 == 61) {
/*     */         
/*     */         try {
/* 438 */           decodedValue = (decodabet[src0] & 0xFF) << 2 | (decodabet[src1] & 0xFF) >>> 4;
/* 439 */         } catch (IndexOutOfBoundsException ignored) {
/* 440 */           throw new IllegalArgumentException("not encoded in Base64");
/*     */         } 
/* 442 */         dest.setByte(destOffset, decodedValue);
/* 443 */         return 1;
/*     */       } 
/*     */       
/* 446 */       byte src3 = src[3];
/* 447 */       if (src3 == 61) {
/*     */         
/* 449 */         byte b1 = decodabet[src1];
/*     */         
/*     */         try {
/* 452 */           if (dest.order() == ByteOrder.BIG_ENDIAN)
/*     */           {
/*     */             
/* 455 */             decodedValue = ((decodabet[src0] & 0x3F) << 2 | (b1 & 0xF0) >> 4) << 8 | (b1 & 0xF) << 4 | (decodabet[src2] & 0xFC) >>> 2;
/*     */           }
/*     */           else
/*     */           {
/* 459 */             decodedValue = (decodabet[src0] & 0x3F) << 2 | (b1 & 0xF0) >> 4 | ((b1 & 0xF) << 4 | (decodabet[src2] & 0xFC) >>> 2) << 8;
/*     */           }
/*     */         
/* 462 */         } catch (IndexOutOfBoundsException ignored) {
/* 463 */           throw new IllegalArgumentException("not encoded in Base64");
/*     */         } 
/* 465 */         dest.setShort(destOffset, decodedValue);
/* 466 */         return 2;
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 471 */         if (dest.order() == ByteOrder.BIG_ENDIAN) {
/* 472 */           decodedValue = (decodabet[src0] & 0x3F) << 18 | (decodabet[src1] & 0xFF) << 12 | (decodabet[src2] & 0xFF) << 6 | decodabet[src3] & 0xFF;
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 477 */           byte b1 = decodabet[src1];
/* 478 */           byte b2 = decodabet[src2];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 484 */           decodedValue = (decodabet[src0] & 0x3F) << 2 | (b1 & 0xF) << 12 | (b1 & 0xF0) >>> 4 | (b2 & 0x3) << 22 | (b2 & 0xFC) << 6 | (decodabet[src3] & 0xFF) << 16;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 495 */       catch (IndexOutOfBoundsException ignored) {
/* 496 */         throw new IllegalArgumentException("not encoded in Base64");
/*     */       } 
/* 498 */       dest.setMedium(destOffset, decodedValue);
/* 499 */       return 3;
/*     */     }
/*     */     
/*     */     private Decoder() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\base64\Base64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */