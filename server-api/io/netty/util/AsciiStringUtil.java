/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SWARUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class AsciiStringUtil
/*     */ {
/*     */   static AsciiString toLowerCase(AsciiString string) {
/*  33 */     byte[] byteArray = string.array();
/*  34 */     int offset = string.arrayOffset();
/*  35 */     int length = string.length();
/*  36 */     if (!containsUpperCase(byteArray, offset, length)) {
/*  37 */       return string;
/*     */     }
/*  39 */     byte[] newByteArray = PlatformDependent.allocateUninitializedArray(length);
/*  40 */     toLowerCase(byteArray, offset, newByteArray);
/*  41 */     return new AsciiString(newByteArray, false);
/*     */   }
/*     */   
/*     */   private static boolean containsUpperCase(byte[] byteArray, int offset, int length) {
/*  45 */     if (!PlatformDependent.isUnaligned()) {
/*  46 */       return linearContainsUpperCase(byteArray, offset, length);
/*     */     }
/*     */     
/*  49 */     int longCount = length >>> 3;
/*  50 */     for (int i = 0; i < longCount; i++) {
/*  51 */       long word = PlatformDependent.getLong(byteArray, offset);
/*  52 */       if (SWARUtil.containsUpperCase(word)) {
/*  53 */         return true;
/*     */       }
/*  55 */       offset += 8;
/*     */     } 
/*  57 */     return unrolledContainsUpperCase(byteArray, offset, length & 0x7);
/*     */   }
/*     */   
/*     */   private static boolean linearContainsUpperCase(byte[] byteArray, int offset, int length) {
/*  61 */     int end = offset + length;
/*  62 */     for (int idx = offset; idx < end; idx++) {
/*  63 */       if (isUpperCase(byteArray[idx])) {
/*  64 */         return true;
/*     */       }
/*     */     } 
/*  67 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean unrolledContainsUpperCase(byte[] byteArray, int offset, int byteCount) {
/*  71 */     assert byteCount >= 0 && byteCount < 8;
/*  72 */     if ((byteCount & 0x4) != 0) {
/*  73 */       int word = PlatformDependent.getInt(byteArray, offset);
/*  74 */       if (SWARUtil.containsUpperCase(word)) {
/*  75 */         return true;
/*     */       }
/*  77 */       offset += 4;
/*     */     } 
/*  79 */     if ((byteCount & 0x2) != 0) {
/*  80 */       if (isUpperCase(PlatformDependent.getByte(byteArray, offset))) {
/*  81 */         return true;
/*     */       }
/*  83 */       if (isUpperCase(PlatformDependent.getByte(byteArray, offset + 1))) {
/*  84 */         return true;
/*     */       }
/*  86 */       offset += 2;
/*     */     } 
/*  88 */     if ((byteCount & 0x1) != 0) {
/*  89 */       return isUpperCase(PlatformDependent.getByte(byteArray, offset));
/*     */     }
/*  91 */     return false;
/*     */   }
/*     */   
/*     */   private static void toLowerCase(byte[] src, int srcOffset, byte[] dst) {
/*  95 */     if (!PlatformDependent.isUnaligned()) {
/*  96 */       linearToLowerCase(src, srcOffset, dst);
/*     */       
/*     */       return;
/*     */     } 
/* 100 */     int length = dst.length;
/* 101 */     int longCount = length >>> 3;
/* 102 */     int offset = 0;
/* 103 */     for (int i = 0; i < longCount; i++) {
/* 104 */       long word = PlatformDependent.getLong(src, srcOffset + offset);
/* 105 */       PlatformDependent.putLong(dst, offset, SWARUtil.toLowerCase(word));
/* 106 */       offset += 8;
/*     */     } 
/* 108 */     unrolledToLowerCase(src, srcOffset + offset, dst, offset, length & 0x7);
/*     */   }
/*     */   
/*     */   private static void linearToLowerCase(byte[] src, int srcOffset, byte[] dst) {
/* 112 */     for (int i = 0; i < dst.length; i++) {
/* 113 */       dst[i] = toLowerCase(src[srcOffset + i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void unrolledToLowerCase(byte[] src, int srcPos, byte[] dst, int dstOffset, int byteCount) {
/* 119 */     assert byteCount >= 0 && byteCount < 8;
/* 120 */     int offset = 0;
/* 121 */     if ((byteCount & 0x4) != 0) {
/* 122 */       int word = PlatformDependent.getInt(src, srcPos + offset);
/* 123 */       PlatformDependent.putInt(dst, dstOffset + offset, SWARUtil.toLowerCase(word));
/* 124 */       offset += 4;
/*     */     } 
/*     */     
/* 127 */     if ((byteCount & 0x2) != 0) {
/* 128 */       short word = PlatformDependent.getShort(src, srcPos + offset);
/* 129 */       short result = (short)(toLowerCase((byte)(word >>> 8)) << 8 | toLowerCase((byte)word));
/* 130 */       PlatformDependent.putShort(dst, dstOffset + offset, result);
/* 131 */       offset += 2;
/*     */     } 
/*     */ 
/*     */     
/* 135 */     if ((byteCount & 0x1) != 0) {
/* 136 */       PlatformDependent.putByte(dst, dstOffset + offset, 
/* 137 */           toLowerCase(PlatformDependent.getByte(src, srcPos + offset)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static AsciiString toUpperCase(AsciiString string) {
/* 148 */     byte[] byteArray = string.array();
/* 149 */     int offset = string.arrayOffset();
/* 150 */     int length = string.length();
/* 151 */     if (!containsLowerCase(byteArray, offset, length)) {
/* 152 */       return string;
/*     */     }
/* 154 */     byte[] newByteArray = PlatformDependent.allocateUninitializedArray(length);
/* 155 */     toUpperCase(byteArray, offset, newByteArray);
/* 156 */     return new AsciiString(newByteArray, false);
/*     */   }
/*     */   
/*     */   private static boolean containsLowerCase(byte[] byteArray, int offset, int length) {
/* 160 */     if (!PlatformDependent.isUnaligned()) {
/* 161 */       return linearContainsLowerCase(byteArray, offset, length);
/*     */     }
/*     */     
/* 164 */     int longCount = length >>> 3;
/* 165 */     for (int i = 0; i < longCount; i++) {
/* 166 */       long word = PlatformDependent.getLong(byteArray, offset);
/* 167 */       if (SWARUtil.containsLowerCase(word)) {
/* 168 */         return true;
/*     */       }
/* 170 */       offset += 8;
/*     */     } 
/* 172 */     return unrolledContainsLowerCase(byteArray, offset, length & 0x7);
/*     */   }
/*     */   
/*     */   private static boolean linearContainsLowerCase(byte[] byteArray, int offset, int length) {
/* 176 */     int end = offset + length;
/* 177 */     for (int idx = offset; idx < end; idx++) {
/* 178 */       if (isLowerCase(byteArray[idx])) {
/* 179 */         return true;
/*     */       }
/*     */     } 
/* 182 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean unrolledContainsLowerCase(byte[] byteArray, int offset, int byteCount) {
/* 186 */     assert byteCount >= 0 && byteCount < 8;
/* 187 */     if ((byteCount & 0x4) != 0) {
/* 188 */       int word = PlatformDependent.getInt(byteArray, offset);
/* 189 */       if (SWARUtil.containsLowerCase(word)) {
/* 190 */         return true;
/*     */       }
/* 192 */       offset += 4;
/*     */     } 
/* 194 */     if ((byteCount & 0x2) != 0) {
/* 195 */       if (isLowerCase(PlatformDependent.getByte(byteArray, offset))) {
/* 196 */         return true;
/*     */       }
/* 198 */       if (isLowerCase(PlatformDependent.getByte(byteArray, offset + 1))) {
/* 199 */         return true;
/*     */       }
/* 201 */       offset += 2;
/*     */     } 
/* 203 */     if ((byteCount & 0x1) != 0) {
/* 204 */       return isLowerCase(PlatformDependent.getByte(byteArray, offset));
/*     */     }
/* 206 */     return false;
/*     */   }
/*     */   
/*     */   private static void toUpperCase(byte[] src, int srcOffset, byte[] dst) {
/* 210 */     if (!PlatformDependent.isUnaligned()) {
/* 211 */       linearToUpperCase(src, srcOffset, dst);
/*     */       
/*     */       return;
/*     */     } 
/* 215 */     int length = dst.length;
/* 216 */     int longCount = length >>> 3;
/* 217 */     int offset = 0;
/* 218 */     for (int i = 0; i < longCount; i++) {
/* 219 */       long word = PlatformDependent.getLong(src, srcOffset + offset);
/* 220 */       PlatformDependent.putLong(dst, offset, SWARUtil.toUpperCase(word));
/* 221 */       offset += 8;
/*     */     } 
/* 223 */     unrolledToUpperCase(src, srcOffset + offset, dst, offset, length & 0x7);
/*     */   }
/*     */   
/*     */   private static void linearToUpperCase(byte[] src, int srcOffset, byte[] dst) {
/* 227 */     for (int i = 0; i < dst.length; i++) {
/* 228 */       dst[i] = toUpperCase(src[srcOffset + i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void unrolledToUpperCase(byte[] src, int srcOffset, byte[] dst, int dstOffset, int byteCount) {
/* 234 */     assert byteCount >= 0 && byteCount < 8;
/* 235 */     int offset = 0;
/* 236 */     if ((byteCount & 0x4) != 0) {
/* 237 */       int word = PlatformDependent.getInt(src, srcOffset + offset);
/* 238 */       PlatformDependent.putInt(dst, dstOffset + offset, SWARUtil.toUpperCase(word));
/* 239 */       offset += 4;
/*     */     } 
/* 241 */     if ((byteCount & 0x2) != 0) {
/* 242 */       short word = PlatformDependent.getShort(src, srcOffset + offset);
/* 243 */       short result = (short)(toUpperCase((byte)(word >>> 8)) << 8 | toUpperCase((byte)word));
/* 244 */       PlatformDependent.putShort(dst, dstOffset + offset, result);
/* 245 */       offset += 2;
/*     */     } 
/*     */     
/* 248 */     if ((byteCount & 0x1) != 0) {
/* 249 */       PlatformDependent.putByte(dst, dstOffset + offset, 
/* 250 */           toUpperCase(PlatformDependent.getByte(src, srcOffset + offset)));
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isLowerCase(byte value) {
/* 255 */     return (value >= 97 && value <= 122);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isUpperCase(byte value) {
/* 265 */     return (value >= 65 && value <= 90);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte toLowerCase(byte value) {
/* 275 */     return isUpperCase(value) ? (byte)(value + 32) : value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte toUpperCase(byte value) {
/* 285 */     return isLowerCase(value) ? (byte)(value - 32) : value;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\AsciiStringUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */