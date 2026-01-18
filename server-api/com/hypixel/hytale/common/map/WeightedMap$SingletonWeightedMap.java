/*     */ package com.hypixel.hytale.common.map;
/*     */ 
/*     */ import com.hypixel.hytale.function.function.BiDoubleToDoubleFunction;
/*     */ import com.hypixel.hytale.function.function.BiIntToDoubleFunction;
/*     */ import com.hypixel.hytale.function.function.BiLongToDoubleFunction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleSupplier;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.ObjDoubleConsumer;
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
/*     */ class SingletonWeightedMap<T>
/*     */   implements IWeightedMap<T>
/*     */ {
/*     */   @Nonnull
/*     */   protected final T[] keys;
/*     */   protected final T key;
/*     */   
/*     */   private SingletonWeightedMap(@Nonnull T[] keys) {
/* 152 */     this.keys = keys;
/* 153 */     this.key = (keys.length > 0) ? keys[0] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public T get(double value) {
/* 158 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public T get(DoubleSupplier supplier) {
/* 163 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public T get(Random random) {
/* 168 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public T get(int x, int z, BiIntToDoubleFunction supplier) {
/* 173 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public T get(long x, long z, BiLongToDoubleFunction supplier) {
/* 178 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public T get(double x, double z, BiDoubleToDoubleFunction supplier) {
/* 183 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public <K> T get(int seed, int x, int z, IWeightedMap.SeedCoordinateFunction<K> supplier, K k) {
/* 188 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 193 */     return this.keys.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(@Nullable T obj) {
/* 198 */     return (obj != null && (obj == this.key || obj.equals(this.key)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(@Nonnull Consumer<T> consumer) {
/* 203 */     if (this.key != null) consumer.accept(this.key);
/*     */   
/*     */   }
/*     */   
/*     */   public void forEachEntry(@Nonnull ObjDoubleConsumer<T> consumer) {
/* 208 */     if (this.key != null) consumer.accept(this.key, 1.0D);
/*     */   
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public T[] internalKeys() {
/* 214 */     return this.keys;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public T[] toArray() {
/* 220 */     return Arrays.copyOf(this.keys, this.keys.length);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <K> IWeightedMap<K> resolveKeys(@Nonnull Function<T, K> mapper, @Nonnull IntFunction<K[]> arraySupplier) {
/* 226 */     K[] array = arraySupplier.apply(1);
/* 227 */     array[0] = mapper.apply(this.key);
/* 228 */     return new SingletonWeightedMap((T[])array);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 234 */     return "SingletonWeightedMap{key=" + String.valueOf(this.key) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\map\WeightedMap$SingletonWeightedMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */