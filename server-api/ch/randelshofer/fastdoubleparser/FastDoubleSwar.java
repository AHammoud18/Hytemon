/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   protected static boolean isDigit(char c) {
/*  39 */     return ((char)(c - 48) < '\n');
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
/*  52 */     return ((char)(c - 48) < '\n');
/*     */   }
/*     */   
/*     */   public static boolean isEightDigits(byte[] a, int offset) {
/*  56 */     return isEightDigitsUtf8(readLongLE(a, offset));
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
/*  69 */     long first = a[offset] | a[offset + 1] << 16L | a[offset + 2] << 32L | a[offset + 3] << 48L;
/*     */ 
/*     */ 
/*     */     
/*  73 */     long second = a[offset + 4] | a[offset + 5] << 16L | a[offset + 6] << 32L | a[offset + 7] << 48L;
/*     */ 
/*     */ 
/*     */     
/*  77 */     return isEightDigitsUtf16(first, second);
/*     */   }
/*     */   
/*     */   public static boolean isEightDigits(CharSequence a, int offset) {
/*  81 */     boolean success = true;
/*  82 */     for (int i = 0; i < 8; i++) {
/*  83 */       char ch = a.charAt(i + offset);
/*  84 */       success &= isDigit(ch);
/*     */     } 
/*  86 */     return success;
/*     */   }
/*     */   
/*     */   public static boolean isEightDigitsUtf16(long first, long second) {
/*  90 */     long fval = first - 13511005043687472L;
/*  91 */     long sval = second - 13511005043687472L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     long fpre = first + 19703549022044230L | fval;
/*  98 */     long spre = second + 19703549022044230L | sval;
/*  99 */     return (((fpre | spre) & 0xFF80FF80FF80FF80L) == 0L);
/*     */   }
/*     */   
/*     */   public static boolean isEightDigitsUtf8(long chunk) {
/* 103 */     long val = chunk - 3472328296227680304L;
/* 104 */     long predicate = (chunk + 5063812098665367110L | val) & 0x8080808080808080L;
/* 105 */     return (predicate == 0L);
/*     */   }
/*     */   
/*     */   public static boolean isEightZeroes(byte[] a, int offset) {
/* 109 */     return isEightZeroesUtf8(readLongLE(a, offset));
/*     */   }
/*     */   public static boolean isEightZeroes(CharSequence a, int offset) {
/*     */     int j;
/* 113 */     boolean success = true;
/* 114 */     for (int i = 0; i < 8; i++) {
/* 115 */       j = success & (('0' == a.charAt(i + offset)) ? 1 : 0);
/*     */     }
/* 117 */     return j;
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
/* 130 */     long first = a[offset] | a[offset + 1] << 16L | a[offset + 2] << 32L | a[offset + 3] << 48L;
/*     */ 
/*     */ 
/*     */     
/* 134 */     long second = a[offset + 4] | a[offset + 5] << 16L | a[offset + 6] << 32L | a[offset + 7] << 48L;
/*     */ 
/*     */ 
/*     */     
/* 138 */     return isEightZeroesUtf16(first, second);
/*     */   }
/*     */   
/*     */   public static boolean isEightZeroesUtf16(long first, long second) {
/* 142 */     return (first == 13511005043687472L && second == 13511005043687472L);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEightZeroesUtf8(long chunk) {
/* 147 */     return (chunk == 3472328296227680304L);
/*     */   }
/*     */   
/*     */   public static int readIntBE(byte[] a, int offset) {
/* 151 */     return (a[offset] & 0xFF) << 24 | (a[offset + 1] & 0xFF) << 16 | (a[offset + 2] & 0xFF) << 8 | a[offset + 3] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int readIntLE(byte[] a, int offset) {
/* 158 */     return (a[offset + 3] & 0xFF) << 24 | (a[offset + 2] & 0xFF) << 16 | (a[offset + 1] & 0xFF) << 8 | a[offset] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long readLongBE(byte[] a, int offset) {
/* 165 */     return (a[offset] & 0xFFL) << 56L | (a[offset + 1] & 0xFFL) << 48L | (a[offset + 2] & 0xFFL) << 40L | (a[offset + 3] & 0xFFL) << 32L | (a[offset + 4] & 0xFFL) << 24L | (a[offset + 5] & 0xFFL) << 16L | (a[offset + 6] & 0xFFL) << 8L | a[offset + 7] & 0xFFL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long readLongLE(byte[] a, int offset) {
/* 176 */     return (a[offset + 7] & 0xFFL) << 56L | (a[offset + 6] & 0xFFL) << 48L | (a[offset + 5] & 0xFFL) << 40L | (a[offset + 4] & 0xFFL) << 32L | (a[offset + 3] & 0xFFL) << 24L | (a[offset + 2] & 0xFFL) << 16L | (a[offset + 1] & 0xFFL) << 8L | a[offset] & 0xFFL;
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
/*     */   public static int tryToParseEightDigits(char[] a, int offset) {
/* 198 */     long first = a[offset] | a[offset + 1] << 16L | a[offset + 2] << 32L | a[offset + 3] << 48L;
/*     */ 
/*     */ 
/*     */     
/* 202 */     long second = a[offset + 4] | a[offset + 5] << 16L | a[offset + 6] << 32L | a[offset + 7] << 48L;
/*     */ 
/*     */ 
/*     */     
/* 206 */     return tryToParseEightDigitsUtf16(first, second);
/*     */   }
/*     */   
/*     */   public static int tryToParseEightDigits(byte[] a, int offset) {
/* 210 */     return tryToParseEightDigitsUtf8(readLongLE(a, offset));
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
/*     */   public static int tryToParseEightDigits(CharSequence str, int offset) {
/* 226 */     long first = str.charAt(offset) | str.charAt(offset + 1) << 16L | str.charAt(offset + 2) << 32L | str.charAt(offset + 3) << 48L;
/*     */ 
/*     */ 
/*     */     
/* 230 */     long second = str.charAt(offset + 4) | str.charAt(offset + 5) << 16L | str.charAt(offset + 6) << 32L | str.charAt(offset + 7) << 48L;
/* 231 */     return tryToParseEightDigitsUtf16(first, second);
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
/* 249 */     long fval = first - 13511005043687472L;
/* 250 */     long sval = second - 13511005043687472L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 256 */     long fpre = first + 19703549022044230L | fval;
/* 257 */     long spre = second + 19703549022044230L | sval;
/* 258 */     if (((fpre | spre) & 0xFF80FF80FF80FF80L) != 0L) {
/* 259 */       return -1;
/*     */     }
/*     */     
/* 262 */     return (int)(sval * 281475406208040961L >>> 48L) + (int)(fval * 281475406208040961L >>> 48L) * 10000;
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
/* 276 */     return tryToParseEightDigitsUtf8(readLongLE(a, offset));
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
/* 301 */     long val = chunk - 3472328296227680304L;
/*     */ 
/*     */ 
/*     */     
/* 305 */     long predicate = (chunk + 5063812098665367110L | val) & 0x8080808080808080L;
/* 306 */     if (predicate != 0L) {
/* 307 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 311 */     long mask = 1095216660735L;
/* 312 */     long mul1 = 4294967296000100L;
/* 313 */     long mul2 = 42949672960001L;
/* 314 */     val = val * 10L + (val >>> 8L);
/* 315 */     val = (val & mask) * mul1 + (val >>> 16L & mask) * mul2 >>> 32L;
/* 316 */     return (int)val;
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
/* 331 */     long first = str.charAt(offset) << 48L | str.charAt(offset + 1) << 32L | str.charAt(offset + 2) << 16L | str.charAt(offset + 3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     long second = str.charAt(offset + 4) << 48L | str.charAt(offset + 5) << 32L | str.charAt(offset + 6) << 16L | str.charAt(offset + 7);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static long tryToParseEightHexDigits(char[] chars, int offset) {
/* 353 */     long first = chars[offset] << 48L | chars[offset + 1] << 32L | chars[offset + 2] << 16L | chars[offset + 3];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     long second = chars[offset + 4] << 48L | chars[offset + 5] << 32L | chars[offset + 6] << 16L | chars[offset + 7];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 363 */     return tryToParseEightHexDigitsUtf16(first, second);
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
/* 375 */     return tryToParseEightHexDigitsUtf8(readLongBE(a, offset));
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
/* 401 */     if (((first | second) & 0xFF00FF00FF00FF00L) != 0L) {
/* 402 */       return -1L;
/*     */     }
/* 404 */     long f = first * 65792L;
/* 405 */     long s = second * 65792L;
/* 406 */     long utf8Bytes = f & 0xFFFF000000000000L | (f & 0xFFFF0000L) << 16L | (s & 0xFFFF000000000000L) >>> 32L | (s & 0xFFFF0000L) >>> 16L;
/*     */ 
/*     */ 
/*     */     
/* 410 */     return tryToParseEightHexDigitsUtf8(utf8Bytes);
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
/* 428 */     long lt_0 = chunk - 3472328296227680304L;
/* 429 */     lt_0 &= 0x8080808080808080L;
/*     */ 
/*     */     
/* 432 */     long gt_9 = chunk + 5063812098665367110L;
/* 433 */     gt_9 &= 0x8080808080808080L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 438 */     long vec = (chunk | 0x2020202020202020L) - 3472328296227680304L;
/*     */ 
/*     */     
/* 441 */     long ge_a = vec + 5714873654208057167L;
/* 442 */     ge_a &= 0x8080808080808080L;
/*     */ 
/*     */     
/* 445 */     long le_f = vec - 3978709506094217015L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 450 */     if ((lt_0 | gt_9) != (ge_a & le_f)) {
/* 451 */       return -1L;
/*     */     }
/*     */ 
/*     */     
/* 455 */     long gt_9mask = (gt_9 >>> 7L) * 255L;
/*     */ 
/*     */     
/* 458 */     long v = vec & (gt_9mask ^ 0xFFFFFFFFFFFFFFFFL) | vec - (0x2727272727272727L & gt_9mask);
/*     */ 
/*     */ 
/*     */     
/* 462 */     long v2 = v | v >>> 4L;
/* 463 */     long v3 = v2 & 0xFF00FF00FF00FFL;
/* 464 */     long v4 = v3 | v3 >>> 8L;
/* 465 */     long v5 = v4 >>> 16L & 0xFFFF0000L | v4 & 0xFFFFL;
/* 466 */     return v5;
/*     */   }
/*     */   
/*     */   public static int tryToParseFourDigits(char[] a, int offset) {
/* 470 */     long first = a[offset] | a[offset + 1] << 16L | a[offset + 2] << 32L | a[offset + 3] << 48L;
/*     */ 
/*     */ 
/*     */     
/* 474 */     return tryToParseFourDigitsUtf16(first);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int tryToParseFourDigits(CharSequence str, int offset) {
/* 481 */     long first = str.charAt(offset) | str.charAt(offset + 1) << 16L | str.charAt(offset + 2) << 32L | str.charAt(offset + 3) << 48L;
/*     */     
/* 483 */     return tryToParseFourDigitsUtf16(first);
/*     */   }
/*     */   
/*     */   public static int tryToParseFourDigits(byte[] a, int offset) {
/* 487 */     return tryToParseFourDigitsUtf8(readIntLE(a, offset));
/*     */   }
/*     */   
/*     */   public static int tryToParseFourDigitsUtf16(long first) {
/* 491 */     long fval = first - 13511005043687472L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 497 */     long fpre = first + 19703549022044230L | fval;
/* 498 */     if ((fpre & 0xFF80FF80FF80FF80L) != 0L) {
/* 499 */       return -1;
/*     */     }
/*     */     
/* 502 */     return (int)(fval * 281475406208040961L >>> 48L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int tryToParseFourDigitsUtf8(int chunk) {
/* 508 */     int val = chunk - 808464432;
/* 509 */     int predicate = (chunk + 1179010630 | val) & 0x80808080;
/* 510 */     if (predicate != 0L) {
/* 511 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 515 */     val = val * 2561 >>> 8;
/* 516 */     val = (val & 0xFF) * 100 + ((val & 0xFF0000) >> 16);
/* 517 */     return val;
/*     */   }
/*     */   
/*     */   public static int tryToParseUpTo7Digits(byte[] str, int from, int to) {
/* 521 */     int i, result = 0;
/* 522 */     boolean success = true;
/* 523 */     for (; from < to; from++) {
/* 524 */       byte ch = str[from];
/* 525 */       int digit = (char)(ch - 48);
/* 526 */       i = success & ((digit < 10) ? 1 : 0);
/* 527 */       result = 10 * result + digit;
/*     */     } 
/* 529 */     return (i != 0) ? result : -1;
/*     */   }
/*     */   
/*     */   public static int tryToParseUpTo7Digits(char[] str, int from, int to) {
/* 533 */     int i, result = 0;
/* 534 */     boolean success = true;
/* 535 */     for (; from < to; from++) {
/* 536 */       char ch = str[from];
/* 537 */       int digit = (char)(ch - 48);
/* 538 */       i = success & ((digit < 10) ? 1 : 0);
/* 539 */       result = 10 * result + digit;
/*     */     } 
/* 541 */     return (i != 0) ? result : -1;
/*     */   }
/*     */   
/*     */   public static int tryToParseUpTo7Digits(CharSequence str, int from, int to) {
/* 545 */     int i, result = 0;
/* 546 */     boolean success = true;
/* 547 */     for (; from < to; from++) {
/* 548 */       char ch = str.charAt(from);
/* 549 */       int digit = (char)(ch - 48);
/* 550 */       i = success & ((digit < 10) ? 1 : 0);
/* 551 */       result = 10 * result + digit;
/*     */     } 
/* 553 */     return (i != 0) ? result : -1;
/*     */   }
/*     */   
/*     */   public static void writeIntBE(byte[] a, int offset, int v) {
/* 557 */     a[offset] = (byte)(v >>> 24);
/* 558 */     a[offset + 1] = (byte)(v >>> 16);
/* 559 */     a[offset + 2] = (byte)(v >>> 8);
/* 560 */     a[offset + 3] = (byte)v;
/*     */   }
/*     */   
/*     */   public static void writeLongBE(byte[] a, int offset, long v) {
/* 564 */     a[offset] = (byte)(int)(v >>> 56L);
/* 565 */     a[offset + 1] = (byte)(int)(v >>> 48L);
/* 566 */     a[offset + 2] = (byte)(int)(v >>> 40L);
/* 567 */     a[offset + 3] = (byte)(int)(v >>> 32L);
/* 568 */     a[offset + 4] = (byte)(int)(v >>> 24L);
/* 569 */     a[offset + 5] = (byte)(int)(v >>> 16L);
/* 570 */     a[offset + 6] = (byte)(int)(v >>> 8L);
/* 571 */     a[offset + 7] = (byte)(int)v;
/*     */   }
/*     */   
/*     */   public static double fma(double a, double b, double c) {
/* 575 */     return a * b + c;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\FastDoubleSwar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */