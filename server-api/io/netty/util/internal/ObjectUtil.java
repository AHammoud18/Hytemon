/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectUtil
/*     */ {
/*     */   private static final float FLOAT_ZERO = 0.0F;
/*     */   private static final double DOUBLE_ZERO = 0.0D;
/*     */   private static final long LONG_ZERO = 0L;
/*     */   private static final int INT_ZERO = 0;
/*     */   private static final short SHORT_ZERO = 0;
/*     */   
/*     */   public static <T> T checkNotNull(T arg, String text) {
/*  39 */     if (arg == null) {
/*  40 */       throw new NullPointerException(text);
/*     */     }
/*  42 */     return arg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T[] deepCheckNotNull(String text, T... varargs) {
/*  53 */     if (varargs == null) {
/*  54 */       throw new NullPointerException(text);
/*     */     }
/*     */     
/*  57 */     for (T element : varargs) {
/*  58 */       if (element == null) {
/*  59 */         throw new NullPointerException(text);
/*     */       }
/*     */     } 
/*  62 */     return varargs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T checkNotNullWithIAE(T arg, String paramName) throws IllegalArgumentException {
/*  70 */     if (arg == null) {
/*  71 */       throw new IllegalArgumentException("Param '" + paramName + "' must not be null");
/*     */     }
/*  73 */     return arg;
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
/*     */   public static <T> T checkNotNullArrayParam(T value, int index, String name) throws IllegalArgumentException {
/*  88 */     if (value == null) {
/*  89 */       throw new IllegalArgumentException("Array index " + index + " of parameter '" + name + "' must not be null");
/*     */     }
/*     */     
/*  92 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int checkPositive(int i, String name) {
/* 100 */     if (i <= 0) {
/* 101 */       throw new IllegalArgumentException(name + " : " + i + " (expected: > 0)");
/*     */     }
/* 103 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long checkPositive(long l, String name) {
/* 111 */     if (l <= 0L) {
/* 112 */       throw new IllegalArgumentException(name + " : " + l + " (expected: > 0)");
/*     */     }
/* 114 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double checkPositive(double d, String name) {
/* 122 */     if (d <= 0.0D) {
/* 123 */       throw new IllegalArgumentException(name + " : " + d + " (expected: > 0)");
/*     */     }
/* 125 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float checkPositive(float f, String name) {
/* 133 */     if (f <= 0.0F) {
/* 134 */       throw new IllegalArgumentException(name + " : " + f + " (expected: > 0)");
/*     */     }
/* 136 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static short checkPositive(short s, String name) {
/* 144 */     if (s <= 0) {
/* 145 */       throw new IllegalArgumentException(name + " : " + s + " (expected: > 0)");
/*     */     }
/* 147 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int checkPositiveOrZero(int i, String name) {
/* 155 */     if (i < 0) {
/* 156 */       throw new IllegalArgumentException(name + " : " + i + " (expected: >= 0)");
/*     */     }
/* 158 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long checkPositiveOrZero(long l, String name) {
/* 166 */     if (l < 0L) {
/* 167 */       throw new IllegalArgumentException(name + " : " + l + " (expected: >= 0)");
/*     */     }
/* 169 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double checkPositiveOrZero(double d, String name) {
/* 177 */     if (d < 0.0D) {
/* 178 */       throw new IllegalArgumentException(name + " : " + d + " (expected: >= 0)");
/*     */     }
/* 180 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float checkPositiveOrZero(float f, String name) {
/* 188 */     if (f < 0.0F) {
/* 189 */       throw new IllegalArgumentException(name + " : " + f + " (expected: >= 0)");
/*     */     }
/* 191 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int checkInRange(int i, int start, int end, String name) {
/* 199 */     if (i < start || i > end) {
/* 200 */       throw new IllegalArgumentException(name + ": " + i + " (expected: " + start + "-" + end + ")");
/*     */     }
/* 202 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long checkInRange(long l, long start, long end, String name) {
/* 210 */     if (l < start || l > end) {
/* 211 */       throw new IllegalArgumentException(name + ": " + l + " (expected: " + start + "-" + end + ")");
/*     */     }
/* 213 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double checkInRange(double d, double start, double end, String name) {
/* 221 */     if (d < start || d > end) {
/* 222 */       throw new IllegalArgumentException(name + ": " + d + " (expected: " + start + "-" + end + ")");
/*     */     }
/* 224 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T[] checkNonEmpty(T[] array, String name) {
/* 234 */     if (((Object[])checkNotNull((T)array, name)).length == 0) {
/* 235 */       throw new IllegalArgumentException("Param '" + name + "' must not be empty");
/*     */     }
/* 237 */     return array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] checkNonEmpty(byte[] array, String name) {
/* 247 */     if (((byte[])checkNotNull((T)array, name)).length == 0) {
/* 248 */       throw new IllegalArgumentException("Param '" + name + "' must not be empty");
/*     */     }
/* 250 */     return array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char[] checkNonEmpty(char[] array, String name) {
/* 260 */     if (((char[])checkNotNull((T)array, name)).length == 0) {
/* 261 */       throw new IllegalArgumentException("Param '" + name + "' must not be empty");
/*     */     }
/* 263 */     return array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Collection<?>> T checkNonEmpty(T collection, String name) {
/* 273 */     if (((Collection)checkNotNull((Collection)collection, name)).isEmpty()) {
/* 274 */       throw new IllegalArgumentException("Param '" + name + "' must not be empty");
/*     */     }
/* 276 */     return collection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String checkNonEmpty(String value, String name) {
/* 285 */     if (((String)checkNotNull(value, name)).isEmpty()) {
/* 286 */       throw new IllegalArgumentException("Param '" + name + "' must not be empty");
/*     */     }
/* 288 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V, T extends Map<K, V>> T checkNonEmpty(T value, String name) {
/* 297 */     if (((Map)checkNotNull((Map)value, name)).isEmpty()) {
/* 298 */       throw new IllegalArgumentException("Param '" + name + "' must not be empty");
/*     */     }
/* 300 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CharSequence checkNonEmpty(CharSequence value, String name) {
/* 309 */     if (((CharSequence)checkNotNull(value, name)).length() == 0) {
/* 310 */       throw new IllegalArgumentException("Param '" + name + "' must not be empty");
/*     */     }
/* 312 */     return value;
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
/*     */   public static String checkNonEmptyAfterTrim(String value, String name) {
/* 327 */     String trimmed = ((String)checkNotNull(value, name)).trim();
/* 328 */     return checkNonEmpty(trimmed, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int intValue(Integer wrapper, int defaultValue) {
/* 338 */     return (wrapper != null) ? wrapper.intValue() : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long longValue(Long wrapper, long defaultValue) {
/* 348 */     return (wrapper != null) ? wrapper.longValue() : defaultValue;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\ObjectUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */