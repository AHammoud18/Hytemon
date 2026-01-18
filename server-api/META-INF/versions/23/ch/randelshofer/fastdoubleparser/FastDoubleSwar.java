/*     */ package META-INF.versions.23.ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import java.lang.foreign.MemorySegment;
/*     */ import java.lang.foreign.ValueLayout;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.VarHandle;
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
/*     */ final class FastDoubleSwar
/*     */ {
/*  36 */   private static final VarHandle readLongLE = MethodHandles.byteArrayViewVarHandle(long[].class, ByteOrder.LITTLE_ENDIAN);
/*     */   
/*  38 */   private static final VarHandle readIntLE = MethodHandles.byteArrayViewVarHandle(int[].class, ByteOrder.LITTLE_ENDIAN);
/*     */   
/*  40 */   private static final VarHandle readIntBE = MethodHandles.byteArrayViewVarHandle(int[].class, ByteOrder.BIG_ENDIAN);
/*     */   
/*  42 */   private static final VarHandle readLongBE = MethodHandles.byteArrayViewVarHandle(long[].class, ByteOrder.BIG_ENDIAN);
/*  43 */   private static final ValueLayout.OfLong CHAR_ALIGNED_LONG = ValueLayout.OfLong.JAVA_LONG
/*  44 */     .withByteAlignment(2L);
/*     */   
/*     */   public static int countUpToEightDigitsUtf8(long chunk) {
/*  47 */     long val = chunk - 3472328296227680304L;
/*  48 */     long predicate = (chunk + 5063812098665367110L | val) & 0x8080808080808080L;
/*  49 */     return (predicate == 0L) ? 8 : (Long.numberOfTrailingZeros(predicate) >> 3);
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
/*     */   protected static boolean isDigit(char c) {
/*  61 */     return ((char)(c - 48) < '\n');
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
/*     */   protected static boolean isDigit(byte c) {
/*  74 */     return ((char)(c - 48) < '\n');
/*     */   }
/*     */   
/*     */   public static boolean isEightDigits(byte[] a, int offset) {
/*  78 */     return isEightDigitsUtf8(readLongLE(a, offset));
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
/*     */   public static boolean isEightDigits(char[] a, int offset) {
/*  91 */     MemorySegment seg = MemorySegment.ofArray(a);
/*  92 */     long first = seg.get(CHAR_ALIGNED_LONG, offset << 1L);
/*  93 */     long second = seg.get(CHAR_ALIGNED_LONG, (offset << 1L) + 8L);
/*  94 */     return isEightDigitsUtf16(first, second);
/*     */   }
/*     */   
/*     */   public static boolean isEightDigits(CharSequence a, int offset) {
/*  98 */     boolean success = true;
/*  99 */     for (int i = 0; i < 8; i++) {
/* 100 */       char ch = a.charAt(i + offset);
/* 101 */       success &= isDigit(ch);
/*     */     } 
/* 103 */     return success;
/*     */   }
/*     */   
/*     */   public static boolean isEightDigitsUtf16(long first, long second) {
/* 107 */     long fval = first - 13511005043687472L;
/* 108 */     long sval = second - 13511005043687472L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     long fpre = first + 19703549022044230L | fval;
/* 115 */     long spre = second + 19703549022044230L | sval;
/* 116 */     return (((fpre | spre) & 0xFF80FF80FF80FF80L) == 0L);
/*     */   }
/*     */   
/*     */   public static boolean isEightDigitsUtf8(long chunk) {
/* 120 */     long val = chunk - 3472328296227680304L;
/* 121 */     long predicate = (chunk + 5063812098665367110L | val) & 0x8080808080808080L;
/* 122 */     return (predicate == 0L);
/*     */   }
/*     */   
/*     */   public static boolean isEightZeroes(byte[] a, int offset) {
/* 126 */     return isEightZeroesUtf8(readLongLE.get(a, offset));
/*     */   }
/*     */   public static boolean isEightZeroes(CharSequence a, int offset) {
/*     */     int j;
/* 130 */     boolean success = true;
/* 131 */     for (int i = 0; i < 8; i++) {
/* 132 */       j = success & (('0' == a.charAt(i + offset)) ? 1 : 0);
/*     */     }
/* 134 */     return j;
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
/*     */   public static boolean isEightZeroes(char[] a, int offset) {
/* 147 */     MemorySegment seg = MemorySegment.ofArray(a);
/* 148 */     long first = seg.get(CHAR_ALIGNED_LONG, offset << 1L);
/* 149 */     long second = seg.get(CHAR_ALIGNED_LONG, (offset << 1L) + 8L);
/* 150 */     return isEightZeroesUtf16(first, second);
/*     */   }
/*     */   
/*     */   public static boolean isEightZeroesUtf16(long first, long second) {
/* 154 */     return (first == 13511005043687472L && second == 13511005043687472L);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEightZeroesUtf8(long chunk) {
/* 159 */     return (chunk == 3472328296227680304L);
/*     */   }
/*     */   
/*     */   public static int readIntBE(byte[] a, int offset) {
/* 163 */     return readIntBE.get(a, offset);
/*     */   }
/*     */   
/*     */   public static long readLongBE(byte[] a, int offset) {
/* 167 */     return readLongBE.get(a, offset);
/*     */   }
/*     */   
/*     */   public static long readLongLE(byte[] a, int offset) {
/* 171 */     return readLongLE.get(a, offset);
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
/*     */   public static int tryToParseEightDigits(char[] a, int offset) {
/* 185 */     MemorySegment seg = MemorySegment.ofArray(a);
/* 186 */     long first = seg.get(CHAR_ALIGNED_LONG, offset << 1L);
/* 187 */     long second = seg.get(CHAR_ALIGNED_LONG, (offset << 1L) + 8L);
/* 188 */     return tryToParseEightDigitsUtf16(first, second);
/*     */   }
/*     */   
/*     */   public static int tryToParseEightDigits(byte[] a, int offset) {
/* 192 */     return tryToParseEightDigitsUtf8(readLongLE.get(a, offset));
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
/*     */   public static int tryToParseEightDigits(CharSequence str, int offset) {
/* 207 */     long first = str.charAt(offset) | str.charAt(offset + 1) << 16L | str.charAt(offset + 2) << 32L | str.charAt(offset + 3) << 48L;
/*     */ 
/*     */ 
/*     */     
/* 211 */     long second = str.charAt(offset + 4) | str.charAt(offset + 5) << 16L | str.charAt(offset + 6) << 32L | str.charAt(offset + 7) << 48L;
/* 212 */     return tryToParseEightDigitsUtf16(first, second);
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
/*     */   public static int tryToParseEightDigitsUtf16(long first, long second) {
/* 230 */     long fval = first - 13511005043687472L;
/* 231 */     long sval = second - 13511005043687472L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     long fpre = first + 19703549022044230L | fval;
/* 238 */     long spre = second + 19703549022044230L | sval;
/* 239 */     if (((fpre | spre) & 0xFF80FF80FF80FF80L) != 0L) {
/* 240 */       return -1;
/*     */     }
/*     */     
/* 243 */     return (int)(sval * 281475406208040961L >>> 48L) + (int)(fval * 281475406208040961L >>> 48L) * 10000;
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
/*     */   public static int tryToParseEightDigitsUtf8(byte[] a, int offset) {
/* 257 */     return tryToParseEightDigitsUtf8(readLongLE(a, offset));
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
/*     */   public static int tryToParseEightDigitsUtf8(long chunk) {
/* 282 */     long val = chunk - 3472328296227680304L;
/*     */ 
/*     */ 
/*     */     
/* 286 */     long predicate = (chunk + 5063812098665367110L | val) & 0x8080808080808080L;
/* 287 */     if (predicate != 0L) {
/* 288 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 292 */     long mask = 1095216660735L;
/* 293 */     long mul1 = 4294967296000100L;
/* 294 */     long mul2 = 42949672960001L;
/* 295 */     val = val * 10L + (val >>> 8L);
/* 296 */     val = (val & mask) * mul1 + (val >>> 16L & mask) * mul2 >>> 32L;
/* 297 */     return (int)val;
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
/*     */   public static long tryToParseEightHexDigits(CharSequence str, int offset) {
/* 312 */     long first = str.charAt(offset) << 48L | str.charAt(offset + 1) << 32L | str.charAt(offset + 2) << 16L | str.charAt(offset + 3);
/*     */ 
/*     */ 
/*     */     
/* 316 */     long second = str.charAt(offset + 4) << 48L | str.charAt(offset + 5) << 32L | str.charAt(offset + 6) << 16L | str.charAt(offset + 7);
/* 317 */     return tryToParseEightHexDigitsUtf16(first, second);
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
/*     */   public static long tryToParseEightHexDigits(char[] chars, int offset) {
/* 330 */     long first = chars[offset] << 48L | chars[offset + 1] << 32L | chars[offset + 2] << 16L | chars[offset + 3];
/*     */ 
/*     */ 
/*     */     
/* 334 */     long second = chars[offset + 4] << 48L | chars[offset + 5] << 32L | chars[offset + 6] << 16L | chars[offset + 7];
/*     */ 
/*     */ 
/*     */     
/* 338 */     return tryToParseEightHexDigitsUtf16(first, second);
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
/*     */   public static long tryToParseEightHexDigits(byte[] a, int offset) {
/* 350 */     return tryToParseEightHexDigitsUtf8(readLongBE.get(a, offset));
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
/*     */   public static long tryToParseEightHexDigitsUtf16(long first, long second) {
/* 388 */     if (((first | second) & 0xFF80FF80FF80FF80L) != 0L) {
/* 389 */       return -1L;
/*     */     }
/* 391 */     long f = first * 65792L;
/* 392 */     long s = second * 65792L;
/* 393 */     long utf8Bytes = f & 0xFFFF000000000000L | (f & 0xFFFF0000L) << 16L | (s & 0xFFFF000000000000L) >>> 32L | (s & 0xFFFF0000L) >>> 16L;
/*     */ 
/*     */ 
/*     */     
/* 397 */     return tryToParseEightHexDigitsUtf8(utf8Bytes);
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
/*     */   public static long tryToParseEightHexDigitsUtf8(long chunk) {
/* 415 */     long lt_0 = chunk - 3472328296227680304L;
/* 416 */     lt_0 &= 0x8080808080808080L;
/*     */ 
/*     */     
/* 419 */     long gt_9 = chunk + 5063812098665367110L;
/* 420 */     gt_9 &= 0x8080808080808080L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 425 */     long vec = (chunk | 0x2020202020202020L) - 3472328296227680304L;
/*     */ 
/*     */     
/* 428 */     long ge_a = vec + 5714873654208057167L;
/* 429 */     ge_a &= 0x8080808080808080L;
/*     */ 
/*     */     
/* 432 */     long le_f = vec - 3978709506094217015L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 437 */     if ((lt_0 | gt_9) != (ge_a & le_f)) {
/* 438 */       return -1L;
/*     */     }
/*     */ 
/*     */     
/* 442 */     long gt_9mask = (gt_9 >>> 7L) * 255L;
/*     */ 
/*     */     
/* 445 */     long v = vec & (gt_9mask ^ 0xFFFFFFFFFFFFFFFFL) | vec - (0x2727272727272727L & gt_9mask);
/*     */ 
/*     */ 
/*     */     
/* 449 */     long v2 = v | v >>> 4L;
/* 450 */     long v3 = v2 & 0xFF00FF00FF00FFL;
/* 451 */     long v4 = v3 | v3 >>> 8L;
/* 452 */     long v5 = v4 >>> 16L & 0xFFFF0000L | v4 & 0xFFFFL;
/* 453 */     return v5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int tryToParseFourDigits(char[] a, int offset) {
/* 459 */     MemorySegment seg = MemorySegment.ofArray(a);
/* 460 */     long first = seg.get(CHAR_ALIGNED_LONG, (offset << 1));
/* 461 */     return tryToParseFourDigitsUtf16(first);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int tryToParseFourDigits(CharSequence str, int offset) {
/* 468 */     long first = str.charAt(offset) | str.charAt(offset + 1) << 16L | str.charAt(offset + 2) << 32L | str.charAt(offset + 3) << 48L;
/*     */     
/* 470 */     return tryToParseFourDigitsUtf16(first);
/*     */   }
/*     */   
/*     */   public static int tryToParseFourDigits(byte[] a, int offset) {
/* 474 */     return tryToParseFourDigitsUtf8(readIntLE.get(a, offset));
/*     */   }
/*     */   
/*     */   public static int tryToParseFourDigitsUtf16(long first) {
/* 478 */     long fval = first - 13511005043687472L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 484 */     long fpre = first + 19703549022044230L | fval;
/* 485 */     if ((fpre & 0xFF80FF80FF80FF80L) != 0L) {
/* 486 */       return -1;
/*     */     }
/*     */     
/* 489 */     return (int)(fval * 281475406208040961L >>> 48L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int tryToParseFourDigitsUtf8(int chunk) {
/* 495 */     int val = chunk - 808464432;
/* 496 */     int predicate = (chunk + 1179010630 | val) & 0x80808080;
/* 497 */     if (predicate != 0L) {
/* 498 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 502 */     val = val * 2561 >>> 8;
/* 503 */     val = (val & 0xFF) * 100 + ((val & 0xFF0000) >> 16);
/* 504 */     return val;
/*     */   }
/*     */   
/*     */   public static int tryToParseUpTo7Digits(byte[] str, int from, int to) {
/* 508 */     int i, result = 0;
/* 509 */     boolean success = true;
/* 510 */     for (; from < to; from++) {
/* 511 */       byte ch = str[from];
/* 512 */       int digit = (char)(ch - 48);
/* 513 */       i = success & ((digit < 10) ? 1 : 0);
/* 514 */       result = 10 * result + digit;
/*     */     } 
/* 516 */     return (i != 0) ? result : -1;
/*     */   }
/*     */   
/*     */   public static int tryToParseUpTo7Digits(char[] str, int from, int to) {
/* 520 */     int i, result = 0;
/* 521 */     boolean success = true;
/* 522 */     for (; from < to; from++) {
/* 523 */       char ch = str[from];
/* 524 */       int digit = (char)(ch - 48);
/* 525 */       i = success & ((digit < 10) ? 1 : 0);
/* 526 */       result = 10 * result + digit;
/*     */     } 
/* 528 */     return (i != 0) ? result : -1;
/*     */   }
/*     */   
/*     */   public static int tryToParseUpTo7Digits(CharSequence str, int from, int to) {
/* 532 */     int i, result = 0;
/* 533 */     boolean success = true;
/* 534 */     for (; from < to; from++) {
/* 535 */       char ch = str.charAt(from);
/* 536 */       int digit = (char)(ch - 48);
/* 537 */       i = success & ((digit < 10) ? 1 : 0);
/* 538 */       result = 10 * result + digit;
/*     */     } 
/* 540 */     return (i != 0) ? result : -1;
/*     */   }
/*     */   
/*     */   public static void writeIntBE(byte[] a, int offset, int value) {
/* 544 */     readIntBE.set(a, offset, value);
/*     */   }
/*     */   
/*     */   public static void writeLongBE(byte[] a, int offset, long value) {
/* 548 */     readLongBE.set(a, offset, value);
/*     */   }
/*     */   
/*     */   public static double fma(double a, double b, double c) {
/* 552 */     return Math.fma(a, b, c);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\23\ch\randelshofer\fastdoubleparser\FastDoubleSwar.class
 * Java compiler version: 23 (67.0)
 * JD-Core Version:       1.1.3
 */