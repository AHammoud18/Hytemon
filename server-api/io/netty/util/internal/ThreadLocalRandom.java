/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public final class ThreadLocalRandom
/*     */   extends Random
/*     */ {
/*  66 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ThreadLocalRandom.class);
/*     */   
/*  68 */   private static final AtomicLong seedUniquifier = new AtomicLong();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private static volatile long initialSeedUniquifier = SystemPropertyUtil.getLong("io.netty.initialSeedUniquifier", 0L); private static final Thread seedGeneratorThread; private static final BlockingQueue<Long> seedQueue; private static final long seedGeneratorStartTime; private static volatile long seedGeneratorEndTime; private static final long multiplier = 25214903917L; private static final long addend = 11L; private static final long mask = 281474976710655L; private long rnd; boolean initialized; static {
/*  79 */     if (initialSeedUniquifier == 0L) {
/*  80 */       boolean secureRandom = SystemPropertyUtil.getBoolean("java.util.secureRandomSeed", false);
/*  81 */       if (secureRandom) {
/*  82 */         seedQueue = new LinkedBlockingQueue<>();
/*  83 */         seedGeneratorStartTime = System.nanoTime();
/*     */ 
/*     */ 
/*     */         
/*  87 */         seedGeneratorThread = new Thread("initialSeedUniquifierGenerator")
/*     */           {
/*     */             public void run() {
/*  90 */               SecureRandom random = new SecureRandom();
/*  91 */               byte[] seed = random.generateSeed(8);
/*  92 */               ThreadLocalRandom.seedGeneratorEndTime = System.nanoTime();
/*  93 */               long s = (seed[0] & 0xFFL) << 56L | (seed[1] & 0xFFL) << 48L | (seed[2] & 0xFFL) << 40L | (seed[3] & 0xFFL) << 32L | (seed[4] & 0xFFL) << 24L | (seed[5] & 0xFFL) << 16L | (seed[6] & 0xFFL) << 8L | seed[7] & 0xFFL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 101 */               ThreadLocalRandom.seedQueue.add(Long.valueOf(s));
/*     */             }
/*     */           };
/* 104 */         seedGeneratorThread.setDaemon(true);
/* 105 */         seedGeneratorThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
/*     */             {
/*     */               public void uncaughtException(Thread t, Throwable e) {
/* 108 */                 ThreadLocalRandom.logger.debug("An exception has been raised by {}", t.getName(), e);
/*     */               }
/*     */             });
/* 111 */         seedGeneratorThread.start();
/*     */       } else {
/* 113 */         initialSeedUniquifier = mix64(System.currentTimeMillis()) ^ mix64(System.nanoTime());
/* 114 */         seedGeneratorThread = null;
/* 115 */         seedQueue = null;
/* 116 */         seedGeneratorStartTime = 0L;
/*     */       } 
/*     */     } else {
/* 119 */       seedGeneratorThread = null;
/* 120 */       seedQueue = null;
/* 121 */       seedGeneratorStartTime = 0L;
/*     */     } 
/*     */   }
/*     */   private long pad0; private long pad1; private long pad2; private long pad3; private long pad4; private long pad5; private long pad6; private long pad7; private static final long serialVersionUID = -5851777807851030925L;
/*     */   public static void setInitialSeedUniquifier(long initialSeedUniquifier) {
/* 126 */     ThreadLocalRandom.initialSeedUniquifier = initialSeedUniquifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getInitialSeedUniquifier() {
/* 131 */     long initialSeedUniquifier = ThreadLocalRandom.initialSeedUniquifier;
/* 132 */     if (initialSeedUniquifier != 0L) {
/* 133 */       return initialSeedUniquifier;
/*     */     }
/*     */     
/* 136 */     synchronized (ThreadLocalRandom.class) {
/* 137 */       initialSeedUniquifier = ThreadLocalRandom.initialSeedUniquifier;
/* 138 */       if (initialSeedUniquifier != 0L) {
/* 139 */         return initialSeedUniquifier;
/*     */       }
/*     */ 
/*     */       
/* 143 */       long timeoutSeconds = 3L;
/* 144 */       long deadLine = seedGeneratorStartTime + TimeUnit.SECONDS.toNanos(3L);
/* 145 */       boolean interrupted = false;
/*     */       while (true) {
/* 147 */         long waitTime = deadLine - System.nanoTime();
/*     */         try {
/*     */           Long seed;
/* 150 */           if (waitTime <= 0L) {
/* 151 */             seed = seedQueue.poll();
/*     */           } else {
/* 153 */             seed = seedQueue.poll(waitTime, TimeUnit.NANOSECONDS);
/*     */           } 
/*     */           
/* 156 */           if (seed != null) {
/* 157 */             initialSeedUniquifier = seed.longValue();
/*     */             break;
/*     */           } 
/* 160 */         } catch (InterruptedException e) {
/* 161 */           interrupted = true;
/* 162 */           logger.warn("Failed to generate a seed from SecureRandom due to an InterruptedException.");
/*     */           
/*     */           break;
/*     */         } 
/* 166 */         if (waitTime <= 0L) {
/* 167 */           seedGeneratorThread.interrupt();
/* 168 */           logger.warn("Failed to generate a seed from SecureRandom within {} seconds. Not enough entropy?", 
/*     */               
/* 170 */               Long.valueOf(3L));
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 177 */       initialSeedUniquifier ^= 0x3255ECDC33BAE119L;
/* 178 */       initialSeedUniquifier ^= Long.reverse(System.nanoTime());
/*     */       
/* 180 */       ThreadLocalRandom.initialSeedUniquifier = initialSeedUniquifier;
/*     */       
/* 182 */       if (interrupted) {
/*     */         
/* 184 */         Thread.currentThread().interrupt();
/*     */ 
/*     */ 
/*     */         
/* 188 */         seedGeneratorThread.interrupt();
/*     */       } 
/*     */       
/* 191 */       if (seedGeneratorEndTime == 0L) {
/* 192 */         seedGeneratorEndTime = System.nanoTime();
/*     */       }
/*     */       
/* 195 */       return initialSeedUniquifier;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static long newSeed() {
/*     */     while (true) {
/* 201 */       long current = seedUniquifier.get();
/* 202 */       long actualCurrent = (current != 0L) ? current : getInitialSeedUniquifier();
/*     */ 
/*     */       
/* 205 */       long next = actualCurrent * 181783497276652981L;
/*     */       
/* 207 */       if (seedUniquifier.compareAndSet(current, next)) {
/* 208 */         if (current == 0L && logger.isDebugEnabled()) {
/* 209 */           if (seedGeneratorEndTime != 0L) {
/* 210 */             logger.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x (took %d ms)", new Object[] {
/*     */                     
/* 212 */                     Long.valueOf(actualCurrent), 
/* 213 */                     Long.valueOf(TimeUnit.NANOSECONDS.toMillis(seedGeneratorEndTime - seedGeneratorStartTime)) }));
/*     */           } else {
/* 215 */             logger.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x", new Object[] { Long.valueOf(actualCurrent) }));
/*     */           } 
/*     */         }
/* 218 */         return next ^ System.nanoTime();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static long mix64(long z) {
/* 226 */     z = (z ^ z >>> 33L) * -49064778989728563L;
/* 227 */     z = (z ^ z >>> 33L) * -4265267296055464877L;
/* 228 */     return z ^ z >>> 33L;
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
/*     */   ThreadLocalRandom() {
/* 258 */     super(newSeed());
/* 259 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ThreadLocalRandom current() {
/* 268 */     return InternalThreadLocalMap.get().random();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(long seed) {
/* 279 */     if (this.initialized) {
/* 280 */       throw new UnsupportedOperationException();
/*     */     }
/* 282 */     this.rnd = (seed ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int next(int bits) {
/* 287 */     this.rnd = this.rnd * 25214903917L + 11L & 0xFFFFFFFFFFFFL;
/* 288 */     return (int)(this.rnd >>> 48 - bits);
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
/*     */   public int nextInt(int least, int bound) {
/* 302 */     if (least >= bound) {
/* 303 */       throw new IllegalArgumentException();
/*     */     }
/* 305 */     return nextInt(bound - least) + least;
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
/*     */   public long nextLong(long n) {
/* 318 */     ObjectUtil.checkPositive(n, "n");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     long offset = 0L;
/* 326 */     while (n >= 2147483647L) {
/* 327 */       int bits = next(2);
/* 328 */       long half = n >>> 1L;
/* 329 */       long nextn = ((bits & 0x2) == 0) ? half : (n - half);
/* 330 */       if ((bits & 0x1) == 0) {
/* 331 */         offset += n - nextn;
/*     */       }
/* 333 */       n = nextn;
/*     */     } 
/* 335 */     return offset + nextInt((int)n);
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
/*     */   public long nextLong(long least, long bound) {
/* 349 */     if (least >= bound) {
/* 350 */       throw new IllegalArgumentException();
/*     */     }
/* 352 */     return nextLong(bound - least) + least;
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
/*     */   public double nextDouble(double n) {
/* 365 */     ObjectUtil.checkPositive(n, "n");
/* 366 */     return nextDouble() * n;
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
/*     */   public double nextDouble(double least, double bound) {
/* 380 */     if (least >= bound) {
/* 381 */       throw new IllegalArgumentException();
/*     */     }
/* 383 */     return nextDouble() * (bound - least) + least;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\ThreadLocalRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */