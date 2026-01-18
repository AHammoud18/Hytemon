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
/*     */ public interface Object2ObjectMap<K, V>
/*     */   extends Object2ObjectFunction<K, V>, Map<K, V>
/*     */ {
/*     */   public static interface FastEntrySet<K, V>
/*     */     extends ObjectSet<Entry<K, V>>
/*     */   {
/*     */     ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
/*  73 */       forEach(consumer);
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
/*  96 */     throw new UnsupportedOperationException();
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
/* 151 */     return (ObjectSet)object2ObjectEntrySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default V put(K key, V value) {
/* 161 */     return super.put(key, value);
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
/* 172 */     return super.remove(key);
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
/* 208 */     ObjectSet<Entry<K, V>> entrySet = object2ObjectEntrySet();
/* 209 */     Consumer<Entry<K, V>> wrappingConsumer = entry -> consumer.accept(entry.getKey(), entry.getValue());
/* 210 */     if (entrySet instanceof FastEntrySet) {
/* 211 */       ((FastEntrySet)entrySet).fastForEach(wrappingConsumer);
/*     */     } else {
/* 213 */       entrySet.forEach(wrappingConsumer);
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
/* 233 */     return ((v = get(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/* 252 */     V v = get(key), drv = defaultReturnValue();
/* 253 */     if (v != drv || containsKey(key)) return v; 
/* 254 */     put(key, value);
/* 255 */     return drv;
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
/* 270 */     V curValue = get(key);
/* 271 */     if (!Objects.equals(curValue, value) || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 272 */     remove(key);
/* 273 */     return true;
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
/* 289 */     V curValue = get(key);
/* 290 */     if (!Objects.equals(curValue, oldValue) || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 291 */     put(key, newValue);
/* 292 */     return true;
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
/* 309 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/*     */   default V computeIfAbsent(K key, Object2ObjectFunction<? super K, ? extends V> mappingFunction) {
/* 338 */     Objects.requireNonNull(mappingFunction);
/* 339 */     V v = get(key), drv = defaultReturnValue();
/* 340 */     if (v != drv || containsKey(key)) return v; 
/* 341 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 342 */     V newValue = mappingFunction.get(key);
/* 343 */     put(key, newValue);
/* 344 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default V computeObjectIfAbsentPartial(K key, Object2ObjectFunction<? super K, ? extends V> mappingFunction) {
/* 352 */     return computeIfAbsent(key, mappingFunction);
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
/* 372 */     Objects.requireNonNull(remappingFunction);
/* 373 */     V oldValue = get(key), drv = defaultReturnValue();
/* 374 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 375 */     V newValue = remappingFunction.apply(key, oldValue);
/* 376 */     if (newValue == null) {
/* 377 */       remove(key);
/* 378 */       return drv;
/*     */     } 
/* 380 */     put(key, newValue);
/* 381 */     return newValue;
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
/* 403 */     Objects.requireNonNull(remappingFunction);
/* 404 */     V oldValue = get(key), drv = defaultReturnValue();
/* 405 */     boolean contained = (oldValue != drv || containsKey(key));
/* 406 */     V newValue = remappingFunction.apply(key, contained ? oldValue : null);
/* 407 */     if (newValue == null) {
/* 408 */       if (contained) remove(key); 
/* 409 */       return drv;
/*     */     } 
/* 411 */     put(key, newValue);
/* 412 */     return newValue;
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
/* 435 */     Objects.requireNonNull(remappingFunction);
/* 436 */     Objects.requireNonNull(value);
/* 437 */     V oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 439 */     if (oldValue != drv || containsKey(key)) {
/* 440 */       V mergedValue = remappingFunction.apply(oldValue, value);
/* 441 */       if (mergedValue == null) {
/* 442 */         remove(key);
/* 443 */         return drv;
/*     */       } 
/* 445 */       newValue = mergedValue;
/*     */     } else {
/* 447 */       newValue = value;
/*     */     } 
/* 449 */     put(key, newValue);
/* 450 */     return newValue;
/*     */   }
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(V paramV);
/*     */   
/*     */   V defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry<K, V>> object2ObjectEntrySet();
/*     */   
/*     */   ObjectSet<K> keySet();
/*     */   
/*     */   ObjectCollection<V> values();
/*     */   
/*     */   boolean containsKey(Object paramObject);
/*     */   
/*     */   public static interface Entry<K, V> extends Map.Entry<K, V> {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ObjectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */