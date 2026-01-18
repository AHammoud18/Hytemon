/*     */ package io.sentry.util;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UUIDStringUtils
/*     */ {
/*     */   private static final int SENTRY_UUID_STRING_LENGTH = 32;
/*     */   private static final int SENTRY_SPAN_UUID_STRING_LENGTH = 16;
/*  41 */   private static final char[] HEX_DIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */ 
/*     */   
/*  44 */   private static final long[] HEX_VALUES = new long[128];
/*     */   
/*     */   static {
/*  47 */     Arrays.fill(HEX_VALUES, -1L);
/*     */     
/*  49 */     HEX_VALUES[48] = 0L;
/*  50 */     HEX_VALUES[49] = 1L;
/*  51 */     HEX_VALUES[50] = 2L;
/*  52 */     HEX_VALUES[51] = 3L;
/*  53 */     HEX_VALUES[52] = 4L;
/*  54 */     HEX_VALUES[53] = 5L;
/*  55 */     HEX_VALUES[54] = 6L;
/*  56 */     HEX_VALUES[55] = 7L;
/*  57 */     HEX_VALUES[56] = 8L;
/*  58 */     HEX_VALUES[57] = 9L;
/*     */     
/*  60 */     HEX_VALUES[97] = 10L;
/*  61 */     HEX_VALUES[98] = 11L;
/*  62 */     HEX_VALUES[99] = 12L;
/*  63 */     HEX_VALUES[100] = 13L;
/*  64 */     HEX_VALUES[101] = 14L;
/*  65 */     HEX_VALUES[102] = 15L;
/*     */     
/*  67 */     HEX_VALUES[65] = 10L;
/*  68 */     HEX_VALUES[66] = 11L;
/*  69 */     HEX_VALUES[67] = 12L;
/*  70 */     HEX_VALUES[68] = 13L;
/*  71 */     HEX_VALUES[69] = 14L;
/*  72 */     HEX_VALUES[70] = 15L;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toSentryIdString(UUID uuid) {
/*  77 */     long mostSignificantBits = uuid.getMostSignificantBits();
/*  78 */     long leastSignificantBits = uuid.getLeastSignificantBits();
/*     */     
/*  80 */     return toSentryIdString(mostSignificantBits, leastSignificantBits);
/*     */   }
/*     */   
/*     */   public static String toSentryIdString(long mostSignificantBits, long leastSignificantBits) {
/*  84 */     char[] uuidChars = new char[32];
/*     */     
/*  86 */     fillMostSignificantBits(uuidChars, mostSignificantBits);
/*     */     
/*  88 */     uuidChars[16] = HEX_DIGITS[(int)((leastSignificantBits & 0xF000000000000000L) >>> 60L)];
/*  89 */     uuidChars[17] = HEX_DIGITS[(int)((leastSignificantBits & 0xF00000000000000L) >>> 56L)];
/*  90 */     uuidChars[18] = HEX_DIGITS[(int)((leastSignificantBits & 0xF0000000000000L) >>> 52L)];
/*  91 */     uuidChars[19] = HEX_DIGITS[(int)((leastSignificantBits & 0xF000000000000L) >>> 48L)];
/*  92 */     uuidChars[20] = HEX_DIGITS[(int)((leastSignificantBits & 0xF00000000000L) >>> 44L)];
/*  93 */     uuidChars[21] = HEX_DIGITS[(int)((leastSignificantBits & 0xF0000000000L) >>> 40L)];
/*  94 */     uuidChars[22] = HEX_DIGITS[(int)((leastSignificantBits & 0xF000000000L) >>> 36L)];
/*  95 */     uuidChars[23] = HEX_DIGITS[(int)((leastSignificantBits & 0xF00000000L) >>> 32L)];
/*  96 */     uuidChars[24] = HEX_DIGITS[(int)((leastSignificantBits & 0xF0000000L) >>> 28L)];
/*  97 */     uuidChars[25] = HEX_DIGITS[(int)((leastSignificantBits & 0xF000000L) >>> 24L)];
/*  98 */     uuidChars[26] = HEX_DIGITS[(int)((leastSignificantBits & 0xF00000L) >>> 20L)];
/*  99 */     uuidChars[27] = HEX_DIGITS[(int)((leastSignificantBits & 0xF0000L) >>> 16L)];
/* 100 */     uuidChars[28] = HEX_DIGITS[(int)((leastSignificantBits & 0xF000L) >>> 12L)];
/* 101 */     uuidChars[29] = HEX_DIGITS[(int)((leastSignificantBits & 0xF00L) >>> 8L)];
/* 102 */     uuidChars[30] = HEX_DIGITS[(int)((leastSignificantBits & 0xF0L) >>> 4L)];
/* 103 */     uuidChars[31] = HEX_DIGITS[(int)(leastSignificantBits & 0xFL)];
/*     */     
/* 105 */     return new String(uuidChars);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toSentrySpanIdString(UUID uuid) {
/* 110 */     long mostSignificantBits = uuid.getMostSignificantBits();
/* 111 */     return toSentrySpanIdString(mostSignificantBits);
/*     */   }
/*     */   
/*     */   public static String toSentrySpanIdString(long mostSignificantBits) {
/* 115 */     char[] uuidChars = new char[16];
/*     */     
/* 117 */     fillMostSignificantBits(uuidChars, mostSignificantBits);
/*     */     
/* 119 */     return new String(uuidChars);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void fillMostSignificantBits(char[] uuidChars, long mostSignificantBits) {
/* 124 */     uuidChars[0] = HEX_DIGITS[(int)((mostSignificantBits & 0xF000000000000000L) >>> 60L)];
/* 125 */     uuidChars[1] = HEX_DIGITS[(int)((mostSignificantBits & 0xF00000000000000L) >>> 56L)];
/* 126 */     uuidChars[2] = HEX_DIGITS[(int)((mostSignificantBits & 0xF0000000000000L) >>> 52L)];
/* 127 */     uuidChars[3] = HEX_DIGITS[(int)((mostSignificantBits & 0xF000000000000L) >>> 48L)];
/* 128 */     uuidChars[4] = HEX_DIGITS[(int)((mostSignificantBits & 0xF00000000000L) >>> 44L)];
/* 129 */     uuidChars[5] = HEX_DIGITS[(int)((mostSignificantBits & 0xF0000000000L) >>> 40L)];
/* 130 */     uuidChars[6] = HEX_DIGITS[(int)((mostSignificantBits & 0xF000000000L) >>> 36L)];
/* 131 */     uuidChars[7] = HEX_DIGITS[(int)((mostSignificantBits & 0xF00000000L) >>> 32L)];
/* 132 */     uuidChars[8] = HEX_DIGITS[(int)((mostSignificantBits & 0xF0000000L) >>> 28L)];
/* 133 */     uuidChars[9] = HEX_DIGITS[(int)((mostSignificantBits & 0xF000000L) >>> 24L)];
/* 134 */     uuidChars[10] = HEX_DIGITS[(int)((mostSignificantBits & 0xF00000L) >>> 20L)];
/* 135 */     uuidChars[11] = HEX_DIGITS[(int)((mostSignificantBits & 0xF0000L) >>> 16L)];
/* 136 */     uuidChars[12] = HEX_DIGITS[(int)((mostSignificantBits & 0xF000L) >>> 12L)];
/* 137 */     uuidChars[13] = HEX_DIGITS[(int)((mostSignificantBits & 0xF00L) >>> 8L)];
/* 138 */     uuidChars[14] = HEX_DIGITS[(int)((mostSignificantBits & 0xF0L) >>> 4L)];
/* 139 */     uuidChars[15] = HEX_DIGITS[(int)(mostSignificantBits & 0xFL)];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\UUIDStringUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */