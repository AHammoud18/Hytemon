/*     */ package com.hypixel.hytale.math.random;
/*     */ 
/*     */ import com.hypixel.hytale.function.function.TriFunction;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import java.time.Duration;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.ToDoubleBiFunction;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ import java.util.function.ToIntFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RandomExtra
/*     */ {
/*     */   public static double randomBinomial() {
/*  24 */     ThreadLocalRandom random = ThreadLocalRandom.current();
/*  25 */     double a = random.nextDouble();
/*  26 */     double b = random.nextDouble();
/*  27 */     return a - b;
/*     */   }
/*     */   
/*     */   public static double randomRange(@Nonnull double[] range) {
/*  31 */     return randomRange(range[0], range[1]);
/*     */   }
/*     */   
/*     */   public static double randomRange(double from, double to) {
/*  35 */     return from + ThreadLocalRandom.current().nextDouble() * (to - from);
/*     */   }
/*     */   
/*     */   public static float randomRange(@Nonnull float[] range) {
/*  39 */     return randomRange(range[0], range[1]);
/*     */   }
/*     */   
/*     */   public static float randomRange(float from, float to) {
/*  43 */     return from + ThreadLocalRandom.current().nextFloat() * (to - from);
/*     */   }
/*     */   
/*     */   public static int randomRange(int bound) {
/*  47 */     return ThreadLocalRandom.current().nextInt(bound);
/*     */   }
/*     */   
/*     */   public static int randomRange(@Nonnull int[] range) {
/*  51 */     return randomRange(range[0], range[1]);
/*     */   }
/*     */   
/*     */   public static int randomRange(int from, int to) {
/*  55 */     return ThreadLocalRandom.current().nextInt(to - from + 1) + from;
/*     */   }
/*     */   
/*     */   public static long randomRange(long from, long to) {
/*  59 */     return ThreadLocalRandom.current().nextLong(to - from + 1L) + from;
/*     */   }
/*     */   
/*     */   public static Duration randomDuration(@Nonnull Duration from, @Nonnull Duration to) {
/*  63 */     return Duration.ofNanos(randomRange(from.toNanos(), to.toNanos()));
/*     */   }
/*     */   
/*     */   public static boolean randomBoolean() {
/*  67 */     return ThreadLocalRandom.current().nextBoolean();
/*     */   }
/*     */   
/*     */   public static <T> T randomElement(@Nonnull List<T> collection) {
/*  71 */     return collection.get(randomRange(collection.size()));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3d jitter(@Nonnull Vector3d vec, double maxRange) {
/*  76 */     ThreadLocalRandom current = ThreadLocalRandom.current();
/*  77 */     vec.x += current.nextDouble() * maxRange;
/*  78 */     vec.y += current.nextDouble() * maxRange;
/*  79 */     vec.z += current.nextDouble() * maxRange;
/*  80 */     return vec;
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
/*     */   @Nullable
/*     */   public static <T> T randomWeightedElement(@Nonnull Collection<? extends T> elements, @Nonnull ToDoubleFunction<T> weight) {
/*  93 */     Iterator<? extends T> i = elements.iterator();
/*  94 */     double sumWeights = 0.0D;
/*     */     
/*  96 */     while (i.hasNext()) {
/*  97 */       sumWeights += weight.applyAsDouble(i.next());
/*     */     }
/*  99 */     return randomWeightedElement(elements, weight, sumWeights);
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
/*     */   @Nullable
/*     */   public static <T> T randomWeightedElement(@Nonnull Collection<? extends T> elements, @Nonnull ToDoubleFunction<T> weight, double sumWeights) {
/* 115 */     if (sumWeights == 0.0D) {
/* 116 */       return null;
/*     */     }
/* 118 */     Iterator<? extends T> i = elements.iterator();
/* 119 */     T result = null;
/* 120 */     sumWeights *= ThreadLocalRandom.current().nextDouble();
/* 121 */     while (i.hasNext()) {
/* 122 */       result = i.next();
/* 123 */       sumWeights -= weight.applyAsDouble(result);
/* 124 */       if (sumWeights < 0.0D)
/*     */         break; 
/* 126 */     }  return result;
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
/*     */   @Nullable
/*     */   public static <T> T randomIntWeightedElement(@Nonnull Collection<? extends T> elements, @Nonnull ToIntFunction<T> weight) {
/* 139 */     Iterator<? extends T> i = elements.iterator();
/* 140 */     int sumWeights = 0;
/*     */     
/* 142 */     while (i.hasNext()) {
/* 143 */       sumWeights += weight.applyAsInt(i.next());
/*     */     }
/* 145 */     return randomIntWeightedElement(elements, weight, sumWeights);
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
/*     */   @Nullable
/*     */   public static <T> T randomIntWeightedElement(@Nonnull Collection<? extends T> elements, @Nonnull ToIntFunction<T> weight, int sumWeights) {
/* 161 */     if (sumWeights == 0) {
/* 162 */       return null;
/*     */     }
/* 164 */     Iterator<? extends T> i = elements.iterator();
/* 165 */     T result = null;
/* 166 */     sumWeights = randomRange(sumWeights);
/* 167 */     while (i.hasNext()) {
/* 168 */       result = i.next();
/* 169 */       sumWeights -= weight.applyAsInt(result);
/* 170 */       if (sumWeights < 0)
/*     */         break; 
/* 172 */     }  return result;
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
/*     */   @Nullable
/*     */   public static <T> T randomWeightedElementFiltered(@Nonnull Collection<? extends T> elements, @Nonnull Predicate<T> filter, @Nonnull ToIntFunction<T> weight) {
/* 186 */     Iterator<? extends T> i = elements.iterator();
/* 187 */     int sumWeights = 0;
/*     */     
/* 189 */     while (i.hasNext()) {
/* 190 */       T t = i.next();
/* 191 */       if (filter.test(t)) {
/* 192 */         sumWeights += weight.applyAsInt(t);
/*     */       }
/*     */     } 
/* 195 */     return randomWeightedElementFiltered(elements, filter, weight, sumWeights);
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
/*     */   @Nullable
/*     */   public static <T> T randomWeightedElementFiltered(@Nonnull Collection<? extends T> elements, @Nonnull Predicate<T> filter, @Nonnull ToIntFunction<T> weight, int sumWeights) {
/* 212 */     if (sumWeights == 0) {
/* 213 */       return null;
/*     */     }
/* 215 */     Iterator<? extends T> i = elements.iterator();
/* 216 */     T result = null;
/* 217 */     sumWeights = randomRange(sumWeights);
/* 218 */     while (i.hasNext()) {
/* 219 */       result = i.next();
/* 220 */       if (filter.test(result)) {
/* 221 */         sumWeights -= weight.applyAsInt(result);
/* 222 */         if (sumWeights < 0)
/*     */           break; 
/*     */       } 
/* 225 */     }  return result;
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
/*     */   @Nullable
/*     */   public static <T> T randomWeightedElement(@Nonnull Collection<? extends T> elements, @Nonnull Predicate<T> filter, @Nonnull ToDoubleFunction<T> weight) {
/* 239 */     Iterator<? extends T> i = elements.iterator();
/* 240 */     double sumWeights = 0.0D;
/*     */     
/* 242 */     while (i.hasNext()) {
/* 243 */       T t = i.next();
/* 244 */       if (filter.test(t)) {
/* 245 */         sumWeights += weight.applyAsDouble(t);
/*     */       }
/*     */     } 
/* 248 */     return randomWeightedElement(elements, filter, weight, sumWeights);
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
/*     */   @Nullable
/*     */   public static <T> T randomWeightedElement(@Nonnull Collection<? extends T> elements, @Nonnull Predicate<T> filter, @Nonnull ToDoubleFunction<T> weight, double sumWeights) {
/* 265 */     if (sumWeights == 0.0D) {
/* 266 */       return null;
/*     */     }
/* 268 */     Iterator<? extends T> i = elements.iterator();
/* 269 */     T result = null;
/* 270 */     sumWeights *= ThreadLocalRandom.current().nextDouble();
/* 271 */     while (i.hasNext()) {
/* 272 */       result = i.next();
/* 273 */       if (filter.test(result)) {
/* 274 */         sumWeights -= weight.applyAsDouble(result);
/* 275 */         if (sumWeights < 0.0D)
/*     */           break; 
/*     */       } 
/* 278 */     }  return result;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static <T, U> T randomWeightedElement(@Nonnull Collection<? extends T> elements, @Nonnull BiPredicate<T, U> filter, @Nonnull ToDoubleBiFunction<T, U> weight, double sumWeights, U meta) {
/* 283 */     if (sumWeights == 0.0D) {
/* 284 */       return null;
/*     */     }
/* 286 */     Iterator<? extends T> i = elements.iterator();
/* 287 */     T result = null;
/* 288 */     sumWeights *= ThreadLocalRandom.current().nextDouble();
/* 289 */     while (i.hasNext()) {
/* 290 */       result = i.next();
/* 291 */       if (filter.test(result, meta)) {
/* 292 */         sumWeights -= weight.applyAsDouble(result, meta);
/* 293 */         if (sumWeights < 0.0D)
/*     */           break; 
/*     */       } 
/* 296 */     }  return result;
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
/*     */   public static <T> void reservoirSample(@Nonnull List<T> input, @Nonnull Predicate<T> matcher, int count, @Nonnull List<T> picked) {
/* 313 */     int selected = 0;
/* 314 */     for (int i = 0; i < input.size(); i++) {
/* 315 */       T element = input.get(i);
/* 316 */       if (matcher.test(element)) {
/*     */         
/* 318 */         if (selected < count) {
/* 319 */           picked.add(element);
/*     */         } else {
/* 321 */           int j = randomRange(selected + 1);
/* 322 */           if (j < count) {
/* 323 */             picked.set(j, element);
/*     */           }
/*     */         } 
/*     */         
/* 327 */         selected++;
/*     */       } 
/*     */     } 
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
/*     */   public static <E, S extends List<E>, F, T extends List<F>, G, H> void reservoirSample(@Nonnull S input, @Nonnull TriFunction<E, G, H, F> filter, int count, @Nonnull T picked, G g, H h) {
/* 345 */     int selected = 0;
/* 346 */     for (int i = 0; i < input.size(); i++) {
/* 347 */       F f = (F)filter.apply(input.get(i), g, h);
/* 348 */       if (f != null) {
/*     */         
/* 350 */         if (selected < count) {
/* 351 */           picked.add(f);
/*     */         } else {
/* 353 */           int j = randomRange(selected + 1);
/* 354 */           if (j < count) {
/* 355 */             picked.set(j, f);
/*     */           }
/*     */         } 
/*     */         
/* 359 */         selected++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E, T extends List<E>> void reservoirSample(E element, int count, @Nonnull T picked) {
/* 372 */     if (picked.size() < count) {
/* 373 */       picked.add(element);
/*     */     } else {
/* 375 */       int i = randomRange(count + 1);
/* 376 */       if (i < count) picked.set(i, element);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int pickWeightedIndex(@Nonnull double[] weights) {
/* 386 */     double sum = 0.0D;
/* 387 */     for (double weight : weights) {
/* 388 */       sum += weight;
/*     */     }
/*     */     
/* 391 */     double randomWeight = ThreadLocalRandom.current().nextDouble(sum);
/* 392 */     for (int i = 0; i < weights.length - 1; i++) {
/* 393 */       randomWeight -= weights[i];
/* 394 */       if (randomWeight <= 0.0D) {
/* 395 */         return i;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 400 */     return weights.length - 1;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\random\RandomExtra.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */