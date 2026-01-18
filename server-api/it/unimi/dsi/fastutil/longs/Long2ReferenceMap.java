/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.LongFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Long2ReferenceMap<V>
/*     */   extends Long2ReferenceFunction<V>, Map<Long, V>
/*     */ {
/*     */   public static interface FastEntrySet<V>
/*     */     extends ObjectSet<Entry<V>>
/*     */   {
/*     */     ObjectIterator<Long2ReferenceMap.Entry<V>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Long2ReferenceMap.Entry<V>> consumer) {
/*  84 */       forEach(consumer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void clear() {
/* 107 */     throw new UnsupportedOperationException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default ObjectSet<Map.Entry<Long, V>> entrySet() {
/* 164 */     return (ObjectSet)long2ReferenceEntrySet();
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
/*     */   @Deprecated
/*     */   default V put(Long key, V value) {
/* 178 */     return super.put(key, value);
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
/*     */   @Deprecated
/*     */   default V get(Object key) {
/* 192 */     return super.get(key);
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
/*     */   @Deprecated
/*     */   default V remove(Object key) {
/* 206 */     return super.remove(key);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean containsKey(Object key) {
/* 250 */     return super.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEach(BiConsumer<? super Long, ? super V> consumer) {
/* 256 */     ObjectSet<Entry<V>> entrySet = long2ReferenceEntrySet();
/* 257 */     Consumer<Entry<V>> wrappingConsumer = entry -> consumer.accept(Long.valueOf(entry.getLongKey()), entry.getValue());
/* 258 */     if (entrySet instanceof FastEntrySet) {
/* 259 */       ((FastEntrySet)entrySet).fastForEach(wrappingConsumer);
/*     */     } else {
/* 261 */       entrySet.forEach(wrappingConsumer);
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
/*     */ 
/*     */   
/*     */   default V getOrDefault(long key, V defaultValue) {
/*     */     V v;
/* 281 */     return ((v = get(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   @Deprecated
/*     */   default V getOrDefault(Object key, V defaultValue) {
/* 294 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default V putIfAbsent(long key, V value) {
/* 313 */     V v = get(key), drv = defaultReturnValue();
/* 314 */     if (v != drv || containsKey(key)) return v; 
/* 315 */     put(key, value);
/* 316 */     return drv;
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
/*     */   default boolean remove(long key, Object value) {
/* 331 */     V curValue = get(key);
/* 332 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 333 */     remove(key);
/* 334 */     return true;
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
/*     */   default boolean replace(long key, V oldValue, V newValue) {
/* 350 */     V curValue = get(key);
/* 351 */     if (curValue != oldValue || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 352 */     put(key, newValue);
/* 353 */     return true;
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
/*     */   default V replace(long key, V value) {
/* 370 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/*     */   default V computeIfAbsent(long key, LongFunction<? extends V> mappingFunction) {
/* 397 */     Objects.requireNonNull(mappingFunction);
/* 398 */     V v = get(key);
/* 399 */     if (v != defaultReturnValue() || containsKey(key)) return v; 
/* 400 */     V newValue = mappingFunction.apply(key);
/* 401 */     put(key, newValue);
/* 402 */     return newValue;
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
/*     */   default V computeIfAbsent(long key, Long2ReferenceFunction<? extends V> mappingFunction) {
/* 431 */     Objects.requireNonNull(mappingFunction);
/* 432 */     V v = get(key), drv = defaultReturnValue();
/* 433 */     if (v != drv || containsKey(key)) return v; 
/* 434 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 435 */     V newValue = mappingFunction.get(key);
/* 436 */     put(key, newValue);
/* 437 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default V computeIfAbsentPartial(long key, Long2ReferenceFunction<? extends V> mappingFunction) {
/* 445 */     return computeIfAbsent(key, mappingFunction);
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
/*     */   default V computeIfPresent(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/* 465 */     Objects.requireNonNull(remappingFunction);
/* 466 */     V oldValue = get(key), drv = defaultReturnValue();
/* 467 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 468 */     V newValue = remappingFunction.apply(Long.valueOf(key), oldValue);
/* 469 */     if (newValue == null) {
/* 470 */       remove(key);
/* 471 */       return drv;
/*     */     } 
/* 473 */     put(key, newValue);
/* 474 */     return newValue;
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
/*     */   default V compute(long key, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/* 496 */     Objects.requireNonNull(remappingFunction);
/* 497 */     V oldValue = get(key), drv = defaultReturnValue();
/* 498 */     boolean contained = (oldValue != drv || containsKey(key));
/* 499 */     V newValue = remappingFunction.apply(Long.valueOf(key), contained ? oldValue : null);
/* 500 */     if (newValue == null) {
/* 501 */       if (contained) remove(key); 
/* 502 */       return drv;
/*     */     } 
/* 504 */     put(key, newValue);
/* 505 */     return newValue;
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
/*     */   default V merge(long key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*     */     V newValue;
/* 528 */     Objects.requireNonNull(remappingFunction);
/* 529 */     Objects.requireNonNull(value);
/* 530 */     V oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 532 */     if (oldValue != drv || containsKey(key)) {
/* 533 */       V mergedValue = remappingFunction.apply(oldValue, value);
/* 534 */       if (mergedValue == null) {
/* 535 */         remove(key);
/* 536 */         return drv;
/*     */       } 
/* 538 */       newValue = mergedValue;
/*     */     } else {
/* 540 */       newValue = value;
/*     */     } 
/* 542 */     put(key, newValue);
/* 543 */     return newValue;
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */   
/*     */   void defaultReturnValue(V paramV);
/*     */ 
/*     */   
/*     */   V defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry<V>> long2ReferenceEntrySet();
/*     */   
/*     */   LongSet keySet();
/*     */   
/*     */   ReferenceCollection<V> values();
/*     */   
/*     */   boolean containsKey(long paramLong);
/*     */   
/*     */   public static interface Entry<V>
/*     */     extends Map.Entry<Long, V>
/*     */   {
/*     */     @Deprecated
/*     */     default Long getKey() {
/* 568 */       return Long.valueOf(getLongKey());
/*     */     }
/*     */     
/*     */     long getLongKey();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2ReferenceMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */