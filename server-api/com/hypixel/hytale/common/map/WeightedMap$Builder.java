/*     */ package com.hypixel.hytale.common.map;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder<T>
/*     */ {
/*     */   private final T[] emptyKeys;
/*     */   private T[] keys;
/*     */   private double[] values;
/*     */   private int size;
/*     */   
/*     */   private Builder(T[] emptyKeys) {
/* 247 */     this.emptyKeys = emptyKeys;
/* 248 */     this.keys = emptyKeys;
/* 249 */     this.values = ArrayUtil.EMPTY_DOUBLE_ARRAY;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder<T> putAll(@Nullable IWeightedMap<T> map) {
/* 254 */     if (map != null) {
/* 255 */       ensureCapacity(map.size());
/* 256 */       map.forEachEntry(this::insert);
/*     */     } 
/*     */     
/* 259 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder<T> putAll(@Nullable T[] arr, @Nonnull ToDoubleFunction<T> weight) {
/* 264 */     if (arr == null || arr.length == 0) return this;
/*     */     
/* 266 */     ensureCapacity(arr.length);
/* 267 */     for (T t : arr) {
/* 268 */       insert(t, weight.applyAsDouble(t));
/*     */     }
/*     */     
/* 271 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Builder<T> put(T obj, double weight) {
/* 276 */     ensureCapacity(1);
/* 277 */     insert(obj, weight);
/*     */     
/* 279 */     return this;
/*     */   }
/*     */   
/*     */   public void ensureCapacity(int toAdd) {
/* 283 */     int minCapacity = this.size + toAdd;
/* 284 */     int allocated = allocated();
/* 285 */     if (minCapacity > allocated) {
/* 286 */       int newLength = Math.max(allocated + (allocated >> 1), minCapacity);
/* 287 */       resize(newLength);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resize(int newLength) {
/* 292 */     this.keys = Arrays.copyOf(this.keys, newLength);
/* 293 */     this.values = Arrays.copyOf(this.values, newLength);
/*     */   }
/*     */   
/*     */   private void insert(T key, double value) {
/* 297 */     this.keys[this.size] = key;
/* 298 */     this.values[this.size] = value;
/* 299 */     this.size++;
/*     */   }
/*     */   
/*     */   public int size() {
/* 303 */     return this.size;
/*     */   }
/*     */   
/*     */   private int allocated() {
/* 307 */     return this.keys.length;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 311 */     this.keys = this.emptyKeys;
/* 312 */     this.values = ArrayUtil.EMPTY_DOUBLE_ARRAY;
/* 313 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IWeightedMap<T> build() {
/* 319 */     if (this.size < allocated()) {
/* 320 */       resize(this.size);
/*     */     }
/*     */     
/* 323 */     if (this.keys.length == 0 || this.keys.length == 1) {
/* 324 */       return new WeightedMap.SingletonWeightedMap<>(this.keys);
/*     */     }
/* 326 */     double sum = 0.0D;
/* 327 */     for (double value : this.values) {
/* 328 */       sum += value;
/*     */     }
/* 330 */     return new WeightedMap<>(this.keys, this.values, sum);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\map\WeightedMap$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */