/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Object2ReferenceMap<K, V>
/*     */   extends Object2ReferenceFunction<K, V>, Map<K, V>
/*     */ {
/*     */   public static interface FastEntrySet<K, V>
/*     */     extends ObjectSet<Entry<K, V>>
/*     */   {
/*     */     ObjectIterator<Object2ReferenceMap.Entry<K, V>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
/*  81 */       forEach(consumer);
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
/* 104 */     throw new UnsupportedOperationException();
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
/*     */   default ObjectSet<Map.Entry<K, V>> entrySet() {
/* 159 */     return (ObjectSet)object2ReferenceEntrySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default V put(K key, V value) {
/* 169 */     return super.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default V remove(Object key) {
/* 180 */     return super.remove(key);
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
/*     */   default void forEach(BiConsumer<? super K, ? super V> consumer) {
/* 216 */     ObjectSet<Entry<K, V>> entrySet = object2ReferenceEntrySet();
/* 217 */     Consumer<Entry<K, V>> wrappingConsumer = entry -> consumer.accept(entry.getKey(), entry.getValue());
/* 218 */     if (entrySet instanceof FastEntrySet) {
/* 219 */       ((FastEntrySet)entrySet).fastForEach(wrappingConsumer);
/*     */     } else {
/* 221 */       entrySet.forEach(wrappingConsumer);
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
/*     */   default V getOrDefault(Object key, V defaultValue) {
/*     */     V v;
/* 241 */     return ((v = get(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default V putIfAbsent(K key, V value) {
/* 260 */     V v = get(key), drv = defaultReturnValue();
/* 261 */     if (v != drv || containsKey(key)) return v; 
/* 262 */     put(key, value);
/* 263 */     return drv;
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
/*     */   default boolean remove(Object key, Object value) {
/* 278 */     V curValue = get(key);
/* 279 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 280 */     remove(key);
/* 281 */     return true;
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
/*     */   default boolean replace(K key, V oldValue, V newValue) {
/* 297 */     V curValue = get(key);
/* 298 */     if (curValue != oldValue || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 299 */     put(key, newValue);
/* 300 */     return true;
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
/*     */   default V replace(K key, V value) {
/* 317 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/*     */   default V computeIfAbsent(K key, Object2ReferenceFunction<? super K, ? extends V> mappingFunction) {
/* 346 */     Objects.requireNonNull(mappingFunction);
/* 347 */     V v = get(key), drv = defaultReturnValue();
/* 348 */     if (v != drv || containsKey(key)) return v; 
/* 349 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 350 */     V newValue = mappingFunction.get(key);
/* 351 */     put(key, newValue);
/* 352 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default V computeReferenceIfAbsentPartial(K key, Object2ReferenceFunction<? super K, ? extends V> mappingFunction) {
/* 360 */     return computeIfAbsent(key, mappingFunction);
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
/*     */   default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 380 */     Objects.requireNonNull(remappingFunction);
/* 381 */     V oldValue = get(key), drv = defaultReturnValue();
/* 382 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 383 */     V newValue = remappingFunction.apply(key, oldValue);
/* 384 */     if (newValue == null) {
/* 385 */       remove(key);
/* 386 */       return drv;
/*     */     } 
/* 388 */     put(key, newValue);
/* 389 */     return newValue;
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
/*     */   default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 411 */     Objects.requireNonNull(remappingFunction);
/* 412 */     V oldValue = get(key), drv = defaultReturnValue();
/* 413 */     boolean contained = (oldValue != drv || containsKey(key));
/* 414 */     V newValue = remappingFunction.apply(key, contained ? oldValue : null);
/* 415 */     if (newValue == null) {
/* 416 */       if (contained) remove(key); 
/* 417 */       return drv;
/*     */     } 
/* 419 */     put(key, newValue);
/* 420 */     return newValue;
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
/*     */   default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*     */     V newValue;
/* 443 */     Objects.requireNonNull(remappingFunction);
/* 444 */     Objects.requireNonNull(value);
/* 445 */     V oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 447 */     if (oldValue != drv || containsKey(key)) {
/* 448 */       V mergedValue = remappingFunction.apply(oldValue, value);
/* 449 */       if (mergedValue == null) {
/* 450 */         remove(key);
/* 451 */         return drv;
/*     */       } 
/* 453 */       newValue = mergedValue;
/*     */     } else {
/* 455 */       newValue = value;
/*     */     } 
/* 457 */     put(key, newValue);
/* 458 */     return newValue;
/*     */   }
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(V paramV);
/*     */   
/*     */   V defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry<K, V>> object2ReferenceEntrySet();
/*     */   
/*     */   ObjectSet<K> keySet();
/*     */   
/*     */   ReferenceCollection<V> values();
/*     */   
/*     */   boolean containsKey(Object paramObject);
/*     */   
/*     */   public static interface Entry<K, V> extends Map.Entry<K, V> {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */