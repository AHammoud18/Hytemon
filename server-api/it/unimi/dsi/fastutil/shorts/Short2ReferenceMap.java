/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ import java.util.function.IntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Short2ReferenceMap<V>
/*     */   extends Short2ReferenceFunction<V>, Map<Short, V>
/*     */ {
/*     */   public static interface FastEntrySet<V>
/*     */     extends ObjectSet<Entry<V>>
/*     */   {
/*     */     ObjectIterator<Short2ReferenceMap.Entry<V>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Short2ReferenceMap.Entry<V>> consumer) {
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
/*     */   default ObjectSet<Map.Entry<Short, V>> entrySet() {
/* 164 */     return (ObjectSet)short2ReferenceEntrySet();
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
/*     */   default V put(Short key, V value) {
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
/*     */   default void forEach(BiConsumer<? super Short, ? super V> consumer) {
/* 256 */     ObjectSet<Entry<V>> entrySet = short2ReferenceEntrySet();
/* 257 */     Consumer<Entry<V>> wrappingConsumer = entry -> consumer.accept(Short.valueOf(entry.getShortKey()), entry.getValue());
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
/*     */   default V getOrDefault(short key, V defaultValue) {
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
/*     */   default V putIfAbsent(short key, V value) {
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
/*     */   default boolean remove(short key, Object value) {
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
/*     */   default boolean replace(short key, V oldValue, V newValue) {
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
/*     */   default V replace(short key, V value) {
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
/*     */   default V computeIfAbsent(short key, IntFunction<? extends V> mappingFunction) {
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
/*     */   default V computeIfAbsent(short key, Short2ReferenceFunction<? extends V> mappingFunction) {
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
/*     */   default V computeIfAbsentPartial(short key, Short2ReferenceFunction<? extends V> mappingFunction) {
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
/*     */   default V computeIfPresent(short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
/* 465 */     Objects.requireNonNull(remappingFunction);
/* 466 */     V oldValue = get(key), drv = defaultReturnValue();
/* 467 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 468 */     V newValue = remappingFunction.apply(Short.valueOf(key), oldValue);
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
/*     */   default V compute(short key, BiFunction<? super Short, ? super V, ? extends V> remappingFunction) {
/* 496 */     Objects.requireNonNull(remappingFunction);
/* 497 */     V oldValue = get(key), drv = defaultReturnValue();
/* 498 */     boolean contained = (oldValue != drv || containsKey(key));
/* 499 */     V newValue = remappingFunction.apply(Short.valueOf(key), contained ? oldValue : null);
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
/*     */   default V merge(short key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
/*     */   ObjectSet<Entry<V>> short2ReferenceEntrySet();
/*     */   
/*     */   ShortSet keySet();
/*     */   
/*     */   ReferenceCollection<V> values();
/*     */   
/*     */   boolean containsKey(short paramShort);
/*     */   
/*     */   public static interface Entry<V>
/*     */     extends Map.Entry<Short, V>
/*     */   {
/*     */     @Deprecated
/*     */     default Short getKey() {
/* 568 */       return Short.valueOf(getShortKey());
/*     */     }
/*     */     
/*     */     short getShortKey();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2ReferenceMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */