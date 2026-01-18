/*     */ package io.sentry.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class Random
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4257915988930727506L;
/*  42 */   static final AtomicLong UNIQUE_SEED = new AtomicLong(System.nanoTime());
/*     */ 
/*     */   
/*     */   private static final long MULT_64 = 6364136223846793005L;
/*     */ 
/*     */   
/*     */   private static final double DOUBLE_MASK = 9.007199254740992E15D;
/*     */ 
/*     */   
/*     */   private static final float FLOAT_UNIT = 1.6777216E7F;
/*     */ 
/*     */   
/*     */   private static final long INTEGER_MASK = 4294967295L;
/*     */ 
/*     */   
/*     */   private long state;
/*     */ 
/*     */   
/*     */   private long inc;
/*     */ 
/*     */   
/*     */   private boolean gausAvailable;
/*     */ 
/*     */   
/*     */   private double nextGaus;
/*     */ 
/*     */   
/*     */   public Random() {
/*  70 */     this(getRandomSeed(), getRandomSeed());
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
/*     */   public Random(long seed, long streamNumber) {
/*  89 */     setSeed(seed, streamNumber);
/*     */   }
/*     */   
/*     */   private Random(long initialState, long increment, boolean dummy) {
/*  93 */     setState(initialState);
/*  94 */     setInc(increment);
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
/*     */   public void setSeed(long seed, long streamNumber) {
/* 111 */     this.state = 0L;
/* 112 */     this.inc = streamNumber << 1L | 0x1L;
/* 113 */     this.state = this.state * 6364136223846793005L + this.inc;
/* 114 */     this.state += seed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte nextByte() {
/* 122 */     this.state = this.state * 6364136223846793005L + this.inc;
/* 123 */     return (byte)(int)((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) >>> 24L);
/*     */   }
/*     */   
/*     */   public void nextBytes(byte[] b) {
/* 127 */     for (int i = 0; i < b.length; i++) {
/* 128 */       this.state = this.state * 6364136223846793005L + this.inc;
/* 129 */       b[i] = (byte)(int)((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) >>> 24L);
/*     */     } 
/*     */   }
/*     */   
/*     */   public char nextChar() {
/* 134 */     this.state = this.state * 6364136223846793005L + this.inc;
/*     */     
/* 136 */     return (char)(int)((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) >>> 16L);
/*     */   }
/*     */   
/*     */   public short nextShort() {
/* 140 */     this.state = this.state * 6364136223846793005L + this.inc;
/* 141 */     return (short)(int)((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) >>> 16L);
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
/*     */   public int nextInt() {
/* 156 */     this.state = this.state * 6364136223846793005L + this.inc;
/*     */     
/* 158 */     return (int)((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L));
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
/*     */   public int nextInt(int n) {
/* 170 */     this.state = this.state * 6364136223846793005L + this.inc;
/* 171 */     int r = (int)((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L)) >>> 1;
/* 172 */     int m = n - 1;
/* 173 */     if ((n & m) == 0) {
/* 174 */       r = (int)(n * r >> 31L);
/*     */     } else {
/* 176 */       int u; for (u = r; u - (r = u % n) + m < 0; ) {
/* 177 */         this.state = this.state * 6364136223846793005L + this.inc;
/* 178 */         u = (int)((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L)) >>> 1;
/*     */       } 
/*     */     } 
/* 181 */     return r;
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
/*     */   public boolean nextBoolean() {
/* 197 */     this.state = this.state * 6364136223846793005L + this.inc;
/* 198 */     return (((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) & 0xFFFFFFFFL) >>> 31L != 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean nextBoolean(double probability) {
/* 203 */     if (probability < 0.0D || probability > 1.0D) {
/* 204 */       throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
/*     */     }
/*     */     
/* 207 */     if (probability == 0.0D) return false; 
/* 208 */     if (probability == 1.0D) return true;
/*     */     
/* 210 */     this.state = this.state * 6364136223846793005L + this.inc;
/* 211 */     long l = (this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) & 0xFFFFFFFFL;
/*     */     
/* 213 */     this.state = this.state * 6364136223846793005L + this.inc;
/*     */     
/* 215 */     return (((l >>> 6L << 27L) + (((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) & 0xFFFFFFFFL) >>> 5L)) / 9.007199254740992E15D < probability);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long nextLong() {
/* 223 */     this.state = this.state * 6364136223846793005L + this.inc;
/*     */     
/* 225 */     long l = (this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L);
/*     */     
/* 227 */     this.state = this.state * 6364136223846793005L + this.inc;
/* 228 */     long j = (this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     return (l << 32L) + (int)j;
/*     */   }
/*     */   
/*     */   public long nextLong(long n) {
/* 237 */     if (n == 0L) throw new IllegalArgumentException("n has to be greater than 0");
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 242 */       this.state = this.state * 6364136223846793005L + this.inc;
/*     */       
/* 244 */       long l = (this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L);
/*     */       
/* 246 */       this.state = this.state * 6364136223846793005L + this.inc;
/* 247 */       long j = (this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L);
/*     */       
/* 249 */       long bits = (l << 32L) + (int)j >>> 1L;
/* 250 */       long val = bits % n;
/* 251 */       if (bits - val + n - 1L >= 0L)
/* 252 */         return val; 
/*     */     } 
/*     */   }
/*     */   public double nextDouble() {
/* 256 */     this.state = this.state * 6364136223846793005L + this.inc;
/* 257 */     long l = (this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) & 0xFFFFFFFFL;
/* 258 */     this.state = this.state * 6364136223846793005L + this.inc;
/* 259 */     return ((l >>> 6L << 27L) + (((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) & 0xFFFFFFFFL) >>> 5L)) / 9.007199254740992E15D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextDouble(boolean includeZero, boolean includeOne) {
/* 265 */     double d = 0.0D;
/*     */     do {
/* 267 */       this.state = this.state * 6364136223846793005L + this.inc;
/* 268 */       long l = (this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) & 0xFFFFFFFFL;
/* 269 */       this.state = this.state * 6364136223846793005L + this.inc;
/* 270 */       d = ((l >>> 6L << 27L) + (((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) & 0xFFFFFFFFL) >>> 5L)) / 9.007199254740992E15D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 276 */       if (!includeOne)
/*     */         continue; 
/* 278 */       this.state = this.state * 6364136223846793005L + this.inc;
/* 279 */       if (((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) & 0xFFFFFFFFL) >>> 31L == 0L)
/* 280 */         continue;  d++;
/*     */ 
/*     */     
/*     */     }
/* 284 */     while (d > 1.0D || (!includeZero && d == 0.0D));
/*     */ 
/*     */     
/* 287 */     return d;
/*     */   }
/*     */   
/*     */   public float nextFloat() {
/* 291 */     this.state = this.state * 6364136223846793005L + this.inc;
/* 292 */     return (float)(((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) & 0xFFFFFFFFL) >>> 8L) / 1.6777216E7F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float nextFloat(boolean includeZero, boolean includeOne) {
/* 297 */     float d = 0.0F;
/*     */     do {
/* 299 */       this.state = this.state * 6364136223846793005L + this.inc;
/* 300 */       d = (float)(((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) & 0xFFFFFFFFL) >>> 8L) / 1.6777216E7F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 309 */       if (!includeOne)
/*     */         continue; 
/* 311 */       this.state = this.state * 6364136223846793005L + this.inc;
/* 312 */       if (((this.state >>> 22L ^ this.state) >>> (int)((this.state >>> 61L) + 22L) & 0xFFFFFFFFL) >>> 31L == 0L)
/* 313 */         continue;  d++;
/*     */     
/*     */     }
/* 316 */     while (d > 1.0F || (!includeZero && d == 0.0F));
/*     */ 
/*     */     
/* 319 */     return d;
/*     */   }
/*     */   
/*     */   private void setInc(long increment) {
/* 323 */     if (increment == 0L || increment % 2L == 0L) {
/* 324 */       throw new IllegalArgumentException("Increment may not be 0 or even. Value: " + increment);
/*     */     }
/* 326 */     this.inc = increment;
/*     */   }
/*     */   
/*     */   private void setState(long state) {
/* 330 */     this.state = state;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long getRandomSeed() {
/*     */     while (true) {
/* 336 */       long current = UNIQUE_SEED.get();
/* 337 */       long next = current;
/* 338 */       next ^= next >> 12L;
/* 339 */       next ^= next << 25L;
/* 340 */       next ^= next >> 27L;
/* 341 */       next *= 2685821657736338717L;
/* 342 */       if (UNIQUE_SEED.compareAndSet(current, next)) return next; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\Random.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */