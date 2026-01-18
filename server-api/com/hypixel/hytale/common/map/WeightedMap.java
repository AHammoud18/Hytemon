/*     */ package com.hypixel.hytale.common.map;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.function.function.BiDoubleToDoubleFunction;
/*     */ import com.hypixel.hytale.function.function.BiIntToDoubleFunction;
/*     */ import com.hypixel.hytale.function.function.BiLongToDoubleFunction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleSupplier;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.ObjDoubleConsumer;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class WeightedMap<T> implements IWeightedMap<T> {
/*     */   @Nonnull
/*     */   public static <T> Builder<T> builder(T[] emptyKeys) {
/*  23 */     return new Builder<>(emptyKeys);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final double EPSILON = 0.99999D;
/*     */   
/*     */   public static final double ONE_MINUS_EPSILON = 9.99999999995449E-6D;
/*     */   @Nonnull
/*     */   private final Set<T> keySet;
/*     */   
/*     */   private WeightedMap(@Nonnull T[] keys, double[] values, double sum) {
/*  34 */     this.keySet = new HashSet<>();
/*  35 */     Collections.addAll(this.keySet, keys);
/*  36 */     this.keys = keys;
/*  37 */     this.values = values;
/*  38 */     this.sum = sum;
/*     */   }
/*     */   @Nonnull
/*     */   private final T[] keys; private final double[] values; private final double sum;
/*     */   @Nullable
/*     */   public T get(double value) {
/*  44 */     double weightPercentSum = Math.min(value, 0.99999D) * this.sum;
/*  45 */     for (int i = 0; i < this.keys.length; i++) {
/*  46 */       if ((weightPercentSum -= this.values[i]) <= 9.99999999995449E-6D) {
/*  47 */         return this.keys[i];
/*     */       }
/*     */     } 
/*  50 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T get(@Nonnull DoubleSupplier supplier) {
/*  56 */     return get(supplier.getAsDouble());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T get(@Nonnull Random random) {
/*  62 */     return get(random.nextDouble());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T get(int x, int z, @Nonnull BiIntToDoubleFunction supplier) {
/*  68 */     return get(supplier.apply(x, z));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T get(long x, long z, @Nonnull BiLongToDoubleFunction supplier) {
/*  74 */     return get(supplier.apply(x, z));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T get(double x, double z, @Nonnull BiDoubleToDoubleFunction supplier) {
/*  80 */     return get(supplier.apply(x, z));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <K> T get(int seed, int x, int z, @Nonnull IWeightedMap.SeedCoordinateFunction<K> supplier, K k) {
/*  86 */     return get(supplier.apply(seed, x, z, k));
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  91 */     return this.keys.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(T obj) {
/*  96 */     return this.keySet.contains(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(@Nonnull Consumer<T> consumer) {
/* 101 */     for (T o : this.keys) {
/* 102 */       consumer.accept(o);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachEntry(@Nonnull ObjDoubleConsumer<T> consumer) {
/* 108 */     for (int i = 0; i < this.keys.length; i++) {
/* 109 */       consumer.accept(this.keys[i], this.values[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public T[] internalKeys() {
/* 116 */     return this.keys;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public T[] toArray() {
/* 122 */     return Arrays.copyOf(this.keys, this.keys.length);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <K> IWeightedMap<K> resolveKeys(@Nonnull Function<T, K> mapper, @Nonnull IntFunction<K[]> arraySupplier) {
/* 128 */     K[] array = arraySupplier.apply(this.keys.length);
/* 129 */     for (int i = 0; i < this.keys.length; i++) {
/* 130 */       array[i] = mapper.apply(this.keys[i]);
/*     */     }
/* 132 */     return new WeightedMap((T[])array, this.values, this.sum);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 138 */     return "WeightedMap{keySet=" + String.valueOf(this.keySet) + ", sum=" + this.sum + ", keys=" + 
/*     */ 
/*     */       
/* 141 */       Arrays.toString((Object[])this.keys) + ", values=" + 
/* 142 */       Arrays.toString(this.values) + "}";
/*     */   }
/*     */   
/*     */   private static class SingletonWeightedMap<T>
/*     */     implements IWeightedMap<T> {
/*     */     @Nonnull
/*     */     protected final T[] keys;
/*     */     protected final T key;
/*     */     
/*     */     private SingletonWeightedMap(@Nonnull T[] keys) {
/* 152 */       this.keys = keys;
/* 153 */       this.key = (keys.length > 0) ? keys[0] : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public T get(double value) {
/* 158 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public T get(DoubleSupplier supplier) {
/* 163 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public T get(Random random) {
/* 168 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public T get(int x, int z, BiIntToDoubleFunction supplier) {
/* 173 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public T get(long x, long z, BiLongToDoubleFunction supplier) {
/* 178 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public T get(double x, double z, BiDoubleToDoubleFunction supplier) {
/* 183 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public <K> T get(int seed, int x, int z, IWeightedMap.SeedCoordinateFunction<K> supplier, K k) {
/* 188 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 193 */       return this.keys.length;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(@Nullable T obj) {
/* 198 */       return (obj != null && (obj == this.key || obj.equals(this.key)));
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(@Nonnull Consumer<T> consumer) {
/* 203 */       if (this.key != null) consumer.accept(this.key);
/*     */     
/*     */     }
/*     */     
/*     */     public void forEachEntry(@Nonnull ObjDoubleConsumer<T> consumer) {
/* 208 */       if (this.key != null) consumer.accept(this.key, 1.0D);
/*     */     
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public T[] internalKeys() {
/* 214 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public T[] toArray() {
/* 220 */       return Arrays.copyOf(this.keys, this.keys.length);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public <K> IWeightedMap<K> resolveKeys(@Nonnull Function<T, K> mapper, @Nonnull IntFunction<K[]> arraySupplier) {
/* 226 */       K[] array = arraySupplier.apply(1);
/* 227 */       array[0] = mapper.apply(this.key);
/* 228 */       return new SingletonWeightedMap((T[])array);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 234 */       return "SingletonWeightedMap{key=" + String.valueOf(this.key) + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Builder<T>
/*     */   {
/*     */     private final T[] emptyKeys;
/*     */     private T[] keys;
/*     */     private double[] values;
/*     */     private int size;
/*     */     
/*     */     private Builder(T[] emptyKeys) {
/* 247 */       this.emptyKeys = emptyKeys;
/* 248 */       this.keys = emptyKeys;
/* 249 */       this.values = ArrayUtil.EMPTY_DOUBLE_ARRAY;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder<T> putAll(@Nullable IWeightedMap<T> map) {
/* 254 */       if (map != null) {
/* 255 */         ensureCapacity(map.size());
/* 256 */         map.forEachEntry(this::insert);
/*     */       } 
/*     */       
/* 259 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder<T> putAll(@Nullable T[] arr, @Nonnull ToDoubleFunction<T> weight) {
/* 264 */       if (arr == null || arr.length == 0) return this;
/*     */       
/* 266 */       ensureCapacity(arr.length);
/* 267 */       for (T t : arr) {
/* 268 */         insert(t, weight.applyAsDouble(t));
/*     */       }
/*     */       
/* 271 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Builder<T> put(T obj, double weight) {
/* 276 */       ensureCapacity(1);
/* 277 */       insert(obj, weight);
/*     */       
/* 279 */       return this;
/*     */     }
/*     */     
/*     */     public void ensureCapacity(int toAdd) {
/* 283 */       int minCapacity = this.size + toAdd;
/* 284 */       int allocated = allocated();
/* 285 */       if (minCapacity > allocated) {
/* 286 */         int newLength = Math.max(allocated + (allocated >> 1), minCapacity);
/* 287 */         resize(newLength);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void resize(int newLength) {
/* 292 */       this.keys = Arrays.copyOf(this.keys, newLength);
/* 293 */       this.values = Arrays.copyOf(this.values, newLength);
/*     */     }
/*     */     
/*     */     private void insert(T key, double value) {
/* 297 */       this.keys[this.size] = key;
/* 298 */       this.values[this.size] = value;
/* 299 */       this.size++;
/*     */     }
/*     */     
/*     */     public int size() {
/* 303 */       return this.size;
/*     */     }
/*     */     
/*     */     private int allocated() {
/* 307 */       return this.keys.length;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 311 */       this.keys = this.emptyKeys;
/* 312 */       this.values = ArrayUtil.EMPTY_DOUBLE_ARRAY;
/* 313 */       this.size = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public IWeightedMap<T> build() {
/* 319 */       if (this.size < allocated()) {
/* 320 */         resize(this.size);
/*     */       }
/*     */       
/* 323 */       if (this.keys.length == 0 || this.keys.length == 1) {
/* 324 */         return new WeightedMap.SingletonWeightedMap<>(this.keys);
/*     */       }
/* 326 */       double sum = 0.0D;
/* 327 */       for (double value : this.values) {
/* 328 */         sum += value;
/*     */       }
/* 330 */       return new WeightedMap<>(this.keys, this.values, sum);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\map\WeightedMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */