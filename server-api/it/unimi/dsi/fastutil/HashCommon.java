/*     */ package it.unimi.dsi.fastutil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HashCommon
/*     */ {
/*     */   private static final int INT_PHI = -1640531527;
/*     */   private static final int INV_INT_PHI = 340573321;
/*     */   private static final long LONG_PHI = -7046029254386353131L;
/*     */   private static final long INV_LONG_PHI = -1018231460777725123L;
/*     */   
/*     */   public static int murmurHash3(int x) {
/*  43 */     x ^= x >>> 16;
/*  44 */     x *= -2048144789;
/*  45 */     x ^= x >>> 13;
/*  46 */     x *= -1028477387;
/*  47 */     x ^= x >>> 16;
/*  48 */     return x;
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
/*     */   public static long murmurHash3(long x) {
/*  61 */     x ^= x >>> 33L;
/*  62 */     x *= -49064778989728563L;
/*  63 */     x ^= x >>> 33L;
/*  64 */     x *= -4265267296055464877L;
/*  65 */     x ^= x >>> 33L;
/*  66 */     return x;
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
/*     */   public static int mix(int x) {
/*  81 */     int h = x * -1640531527;
/*  82 */     return h ^ h >>> 16;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int invMix(int x) {
/*  91 */     return (x ^ x >>> 16) * 340573321;
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
/*     */   public static long mix(long x) {
/* 105 */     long h = x * -7046029254386353131L;
/* 106 */     h ^= h >>> 32L;
/* 107 */     return h ^ h >>> 16L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long invMix(long x) {
/* 116 */     x ^= x >>> 32L;
/* 117 */     x ^= x >>> 16L;
/* 118 */     return (x ^ x >>> 32L) * -1018231460777725123L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int float2int(float f) {
/* 129 */     return Float.floatToRawIntBits(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int double2int(double d) {
/* 139 */     long l = Double.doubleToRawLongBits(d);
/* 140 */     return (int)(l ^ l >>> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int long2int(long l) {
/* 149 */     return (int)(l ^ l >>> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int nextPowerOfTwo(int x) {
/* 160 */     return 1 << 32 - Integer.numberOfLeadingZeros(x - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long nextPowerOfTwo(long x) {
/* 171 */     return 1L << 64 - Long.numberOfLeadingZeros(x - 1L);
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
/*     */   public static int maxFill(int n, float f) {
/* 184 */     return Math.min((int)Math.ceil((n * f)), n - 1);
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
/*     */   public static long maxFill(long n, float f) {
/* 196 */     return Math.min((long)Math.ceil(((float)n * f)), n - 1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int arraySize(int expected, float f) {
/* 207 */     long s = Math.max(2L, nextPowerOfTwo((long)Math.ceil((expected / f))));
/* 208 */     if (s > 1073741824L) throw new IllegalArgumentException("Too large (" + expected + " expected elements with load factor " + f + ")"); 
/* 209 */     return (int)s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long bigArraySize(long expected, float f) {
/* 219 */     return nextPowerOfTwo((long)Math.ceil(((float)expected / f)));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\HashCommon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */