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
/*     */ public final class SWARUtil
/*     */ {
/*     */   public static long compilePattern(byte byteToFind) {
/*  27 */     return (byteToFind & 0xFFL) * 72340172838076673L;
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
/*     */   public static long applyPattern(long word, long pattern) {
/*  39 */     long input = word ^ pattern;
/*  40 */     long tmp = (input & 0x7F7F7F7F7F7F7F7FL) + 9187201950435737471L;
/*  41 */     return (tmp | input | 0x7F7F7F7F7F7F7F7FL) ^ 0xFFFFFFFFFFFFFFFFL;
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
/*     */   public static int getIndex(long word, boolean isBigEndian) {
/*  55 */     int zeros = isBigEndian ? Long.numberOfLeadingZeros(word) : Long.numberOfTrailingZeros(word);
/*  56 */     return zeros >>> 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long applyUpperCasePattern(long word) {
/*  64 */     long rotated = word & 0x7F7F7F7F7F7F7F7FL;
/*  65 */     rotated += 2676586395008836901L;
/*  66 */     rotated &= 0x7F7F7F7F7F7F7F7FL;
/*  67 */     rotated += 1880844493789993498L;
/*  68 */     rotated &= word ^ 0xFFFFFFFFFFFFFFFFL;
/*  69 */     rotated &= 0x8080808080808080L;
/*  70 */     return rotated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int applyUpperCasePattern(int word) {
/*  77 */     int rotated = word & 0x7F7F7F7F;
/*  78 */     rotated += 623191333;
/*  79 */     rotated &= 0x7F7F7F7F;
/*  80 */     rotated += 437918234;
/*  81 */     rotated &= word ^ 0xFFFFFFFF;
/*  82 */     rotated &= 0x80808080;
/*  83 */     return rotated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long applyLowerCasePattern(long word) {
/*  90 */     long rotated = word & 0x7F7F7F7F7F7F7F7FL;
/*  91 */     rotated += 361700864190383365L;
/*  92 */     rotated &= 0x7F7F7F7F7F7F7F7FL;
/*  93 */     rotated += 1880844493789993498L;
/*  94 */     rotated &= word ^ 0xFFFFFFFFFFFFFFFFL;
/*  95 */     rotated &= 0x8080808080808080L;
/*  96 */     return rotated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int applyLowerCasePattern(int word) {
/* 103 */     int rotated = word & 0x7F7F7F7F;
/* 104 */     rotated += 84215045;
/* 105 */     rotated &= 0x7F7F7F7F;
/* 106 */     rotated += 437918234;
/* 107 */     rotated &= word ^ 0xFFFFFFFF;
/* 108 */     rotated &= 0x80808080;
/* 109 */     return rotated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean containsUpperCase(long word) {
/* 116 */     return (applyUpperCasePattern(word) != 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean containsUpperCase(int word) {
/* 123 */     return (applyUpperCasePattern(word) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean containsLowerCase(long word) {
/* 130 */     return (applyLowerCasePattern(word) != 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean containsLowerCase(int word) {
/* 137 */     return (applyLowerCasePattern(word) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long toLowerCase(long word) {
/* 144 */     long mask = applyUpperCasePattern(word) >>> 2L;
/* 145 */     return word | mask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int toLowerCase(int word) {
/* 152 */     int mask = applyUpperCasePattern(word) >>> 2;
/* 153 */     return word | mask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long toUpperCase(long word) {
/* 160 */     long mask = applyLowerCasePattern(word) >>> 2L;
/* 161 */     return word & (mask ^ 0xFFFFFFFFFFFFFFFFL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int toUpperCase(int word) {
/* 168 */     int mask = applyLowerCasePattern(word) >>> 2;
/* 169 */     return word & (mask ^ 0xFFFFFFFF);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\SWARUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */