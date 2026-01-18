/*     */ package META-INF.versions.17.ch.randelshofer.fastdoubleparser;
/*     */ 
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
/*  34 */   private static final VarHandle readLongLE = MethodHandles.byteArrayViewVarHandle(long[].class, ByteOrder.LITTLE_ENDIAN).withInvokeExactBehavior();
/*     */   
/*  36 */   private static final VarHandle readIntLE = MethodHandles.byteArrayViewVarHandle(int[].class, ByteOrder.LITTLE_ENDIAN).withInvokeExactBehavior();
/*     */   
/*  38 */   private static final VarHandle readIntBE = MethodHandles.byteArrayViewVarHandle(int[].class, ByteOrder.BIG_ENDIAN).withInvokeExactBehavior();
/*     */   
/*  40 */   private static final VarHandle readLongBE = MethodHandles.byteArrayViewVarHandle(long[].class, ByteOrder.BIG_ENDIAN).withInvokeExactBehavior();
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
/*  51 */     return ((char)(c - 48) < '\n');
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
/*  64 */     return ((char)(c - 48) < '\n');
/*     */   }
/*     */   
/*     */   public static boolean isEightDigits(byte[] a, int offset) {
/*  68 */     return isEightDigitsUtf8(readLongLE.get(a, offset));
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
/*  81 */     long first = a[offset] | a[offset + 1] << 16L | a[offset + 2] << 32L | a[offset + 3] << 48L;
/*     */ 
/*     */ 
/*     */     
/*  85 */     long second = a[offset + 4] | a[offset + 5] << 16L | a[offset + 6] << 32L | a[offset + 7] << 48L;
/*     */ 
/*     */ 
/*     */     
/*  89 */     return isEightDigitsUtf16(first, second);
/*     */   }
/*     */   
/*     */   public static boolean isEightDigits(CharSequence a, int offset) {
/*  93 */     boolean success = true;
/*  94 */     for (int i = 0; i < 8; i++) {
/*  95 */       char ch = a.charAt(i + offset);
/*  96 */       success &= isDigit(ch);
/*     */     } 
/*  98 */     return success;
/*     */   }
/*     */   
/*     */   public static boolean isEightDigitsUtf16(long first, long second) {
/* 102 */     long fval = first - 13511005043687472L;
/* 103 */     long sval = second - 13511005043687472L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     long fpre = first + 19703549022044230L | fval;
/* 110 */     long spre = second + 19703549022044230L | sval;
/* 111 */     return (((fpre | spre) & 0xFF80FF80FF80FF80L) == 0L);
/*     */   }
/*     */   
/*     */   public static boolean isEightDigitsUtf8(long chunk) {
/* 115 */     long val = chunk - 3472328296227680304L;
/* 116 */     long predicate = (chunk + 5063812098665367110L | val) & 0x8080808080808080L;
/* 117 */     return (predicate == 0L);
/*     */   }
/*     */   
/*     */   public static boolean isEightZeroes(byte[] a, int offset) {
/* 121 */     return isEightZeroesUtf8(readLongLE.get(a, offset));
/*     */   }
/*     */   public static boolean isEightZeroes(CharSequence a, int offset) {
/*     */     int j;
/* 125 */     boolean success = true;
/* 126 */     for (int i = 0; i < 8; i++) {
/* 127 */       j = success & (('0' == a.charAt(i + offset)) ? 1 : 0);
/*     */     }
/* 129 */     return j;
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
/* 142 */     long first = a[offset] | a[offset + 1] << 16L | a[offset + 2] << 32L | a[offset + 3] << 48L;
/*     */ 
/*     */ 
/*     */     
/* 146 */     long second = a[offset + 4] | a[offset + 5] << 16L | a[offset + 6] << 32L | a[offset + 7] << 48L;
/*     */ 
/*     */ 
/*     */     
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
/*     */   
/*     */   public static int tryToParseEightDigits(char[] a, int offset) {
/* 186 */     long first = a[offset] | a[offset + 1] << 16L | a[offset + 2] << 32L | a[offset + 3] << 48L;
/*     */ 
/*     */ 
/*     */     
/* 190 */     long second = a[offset + 4] | a[offset + 5] << 16L | a[offset + 6] << 32L | a[offset + 7] << 48L;
/*     */ 
/*     */ 
/*     */     
/* 194 */     return tryToParseEightDigitsUtf16(first, second);
/*     */   }
/*     */   
/*     */   public static int tryToParseEightDigits(byte[] a, int offset) {
/* 198 */     return tryToParseEightDigitsUtf8(readLongLE.get(a, offset));
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
/* 213 */     long first = str.charAt(offset) | str.charAt(offset + 1) << 16L | str.charAt(offset + 2) << 32L | str.charAt(offset + 3) << 48L;
/*     */ 
/*     */ 
/*     */     
/* 217 */     long second = str.charAt(offset + 4) | str.charAt(offset + 5) << 16L | str.charAt(offset + 6) << 32L | str.charAt(offset + 7) << 48L;
/* 218 */     return tryToParseEightDigitsUtf16(first, second);
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
/* 236 */     long fval = first - 13511005043687472L;
/* 237 */     long sval = second - 13511005043687472L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     long fpre = first + 19703549022044230L | fval;
/* 244 */     long spre = second + 19703549022044230L | sval;
/* 245 */     if (((fpre | spre) & 0xFF80FF80FF80FF80L) != 0L) {
/* 246 */       return -1;
/*     */     }
/*     */     
/* 249 */     return (int)(sval * 281475406208040961L >>> 48L) + (int)(fval * 281475406208040961L >>> 48L) * 10000;
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
/* 263 */     return tryToParseEightDigitsUtf8(readLongLE.get(a, offset));
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
/* 288 */     long val = chunk - 3472328296227680304L;
/*     */ 
/*     */ 
/*     */     
/* 292 */     long predicate = (chunk + 5063812098665367110L | val) & 0x8080808080808080L;
/* 293 */     if (predicate != 0L) {
/* 294 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 298 */     long mask = 1095216660735L;
/* 299 */     long mul1 = 4294967296000100L;
/* 300 */     long mul2 = 42949672960001L;
/* 301 */     val = val * 10L + (val >>> 8L);
/* 302 */     val = (val & mask) * mul1 + (val >>> 16L & mask) * mul2 >>> 32L;
/* 303 */     return (int)val;
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
/* 318 */     long first = str.charAt(offset) << 48L | str.charAt(offset + 1) << 32L | str.charAt(offset + 2) << 16L | str.charAt(offset + 3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 323 */     long second = str.charAt(offset + 4) << 48L | str.charAt(offset + 5) << 32L | str.charAt(offset + 6) << 16L | str.charAt(offset + 7);
/*     */     
/* 325 */     return tryToParseEightHexDigitsUtf16(first, second);
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
/*     */   public static long tryToParseEightHexDigits(char[] chars, int offset) {
/* 340 */     long first = chars[offset] << 48L | chars[offset + 1] << 32L | chars[offset + 2] << 16L | chars[offset + 3];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 345 */     long second = chars[offset + 4] << 48L | chars[offset + 5] << 32L | chars[offset + 6] << 16L | chars[offset + 7];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 350 */     return tryToParseEightHexDigitsUtf16(first, second);
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
/* 362 */     return tryToParseEightHexDigitsUtf8(readLongBE.get(a, offset));
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
/*     */   public static int tryToParseFourDigits(char[] a, int offset) {
/* 458 */     long first = a[offset] | a[offset + 1] << 16L | a[offset + 2] << 32L | a[offset + 3] << 48L;
/*     */ 
/*     */ 
/*     */     
/* 462 */     return tryToParseFourDigitsUtf16(first);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int tryToParseFourDigits(CharSequence str, int offset) {
/* 469 */     long first = str.charAt(offset) | str.charAt(offset + 1) << 16L | str.charAt(offset + 2) << 32L | str.charAt(offset + 3) << 48L;
/*     */     
/* 471 */     return tryToParseFourDigitsUtf16(first);
/*     */   }
/*     */   
/*     */   public static int tryToParseFourDigits(byte[] a, int offset) {
/* 475 */     return tryToParseFourDigitsUtf8(readIntLE.get(a, offset));
/*     */   }
/*     */   
/*     */   public static int tryToParseFourDigitsUtf16(long first) {
/* 479 */     long fval = first - 13511005043687472L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 485 */     long fpre = first + 19703549022044230L | fval;
/* 486 */     if ((fpre & 0xFF80FF80FF80FF80L) != 0L) {
/* 487 */       return -1;
/*     */     }
/*     */     
/* 490 */     return (int)(fval * 281475406208040961L >>> 48L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int tryToParseFourDigitsUtf8(int chunk) {
/* 496 */     int val = chunk - 808464432;
/* 497 */     int predicate = (chunk + 1179010630 | val) & 0x80808080;
/* 498 */     if (predicate != 0L) {
/* 499 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 503 */     val = val * 2561 >>> 8;
/* 504 */     val = (val & 0xFF) * 100 + ((val & 0xFF0000) >> 16);
/* 505 */     return val;
/*     */   }
/*     */   
/*     */   public static int tryToParseUpTo7Digits(byte[] str, int from, int to) {
/* 509 */     int i, result = 0;
/* 510 */     boolean success = true;
/* 511 */     for (; from < to; from++) {
/* 512 */       byte ch = str[from];
/* 513 */       int digit = (char)(ch - 48);
/* 514 */       i = success & ((digit < 10) ? 1 : 0);
/* 515 */       result = 10 * result + digit;
/*     */     } 
/* 517 */     return (i != 0) ? result : -1;
/*     */   }
/*     */   
/*     */   public static int tryToParseUpTo7Digits(char[] str, int from, int to) {
/* 521 */     int i, result = 0;
/* 522 */     boolean success = true;
/* 523 */     for (; from < to; from++) {
/* 524 */       char ch = str[from];
/* 525 */       int digit = (char)(ch - 48);
/* 526 */       i = success & ((digit < 10) ? 1 : 0);
/* 527 */       result = 10 * result + digit;
/*     */     } 
/* 529 */     return (i != 0) ? result : -1;
/*     */   }
/*     */   
/*     */   public static int tryToParseUpTo7Digits(CharSequence str, int from, int to) {
/* 533 */     int i, result = 0;
/* 534 */     boolean success = true;
/* 535 */     for (; from < to; from++) {
/* 536 */       char ch = str.charAt(from);
/* 537 */       int digit = (char)(ch - 48);
/* 538 */       i = success & ((digit < 10) ? 1 : 0);
/* 539 */       result = 10 * result + digit;
/*     */     } 
/* 541 */     return (i != 0) ? result : -1;
/*     */   }
/*     */   
/*     */   public static void writeIntBE(byte[] a, int offset, int value) {
/* 545 */     readIntBE.set(a, offset, value);
/*     */   }
/*     */   
/*     */   public static void writeLongBE(byte[] a, int offset, long value) {
/* 549 */     readLongBE.set(a, offset, value);
/*     */   }
/*     */   
/*     */   public static double fma(double a, double b, double c) {
/* 553 */     return Math.fma(a, b, c);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\META-INF\versions\17\ch\randelshofer\fastdoubleparser\FastDoubleSwar.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */