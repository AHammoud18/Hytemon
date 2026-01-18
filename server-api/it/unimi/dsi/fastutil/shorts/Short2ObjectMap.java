/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
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
/*     */ public interface Short2ObjectMap<V>
/*     */   extends Short2ObjectFunction<V>, Map<Short, V>
/*     */ {
/*     */   public static interface FastEntrySet<V>
/*     */     extends ObjectSet<Entry<V>>
/*     */   {
/*     */     ObjectIterator<Short2ObjectMap.Entry<V>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Short2ObjectMap.Entry<V>> consumer) {
/*  76 */       forEach(consumer);
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
/*  99 */     throw new UnsupportedOperationException();
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
/* 156 */     return (ObjectSet)short2ObjectEntrySet();
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
/* 170 */     return super.put(key, value);
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
/* 184 */     return super.get(key);
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
/* 198 */     return super.remove(key);
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
/* 242 */     return super.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEach(BiConsumer<? super Short, ? super V> consumer) {
/* 248 */     ObjectSet<Entry<V>> entrySet = short2ObjectEntrySet();
/* 249 */     Consumer<Entry<V>> wrappingConsumer = entry -> consumer.accept(Short.valueOf(entry.getShortKey()), entry.getValue());
/* 250 */     if (entrySet instanceof FastEntrySet) {
/* 251 */       ((FastEntrySet)entrySet).fastForEach(wrappingConsumer);
/*     */     } else {
/* 253 */       entrySet.forEach(wrappingConsumer);
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
/* 273 */     return ((v = get(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/* 286 */     return super.getOrDefault(key, defaultValue);
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
/* 305 */     V v = get(key), drv = defaultReturnValue();
/* 306 */     if (v != drv || containsKey(key)) return v; 
/* 307 */     put(key, value);
/* 308 */     return drv;
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
/* 323 */     V curValue = get(key);
/* 324 */     if (!Objects.equals(curValue, value) || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 325 */     remove(key);
/* 326 */     return true;
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
/* 342 */     V curValue = get(key);
/* 343 */     if (!Objects.equals(curValue, oldValue) || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 344 */     put(key, newValue);
/* 345 */     return true;
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
/* 362 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/* 389 */     Objects.requireNonNull(mappingFunction);
/* 390 */     V v = get(key);
/* 391 */     if (v != defaultReturnValue() || containsKey(key)) return v; 
/* 392 */     V newValue = mappingFunction.apply(key);
/* 393 */     put(key, newValue);
/* 394 */     return newValue;
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
/*     */   default V computeIfAbsent(short key, Short2ObjectFunction<? extends V> mappingFunction) {
/* 423 */     Objects.requireNonNull(mappingFunction);
/* 424 */     V v = get(key), drv = defaultReturnValue();
/* 425 */     if (v != drv || containsKey(key)) return v; 
/* 426 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 427 */     V newValue = mappingFunction.get(key);
/* 428 */     put(key, newValue);
/* 429 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default V computeIfAbsentPartial(short key, Short2ObjectFunction<? extends V> mappingFunction) {
/* 437 */     return computeIfAbsent(key, mappingFunction);
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
/* 457 */     Objects.requireNonNull(remappingFunction);
/* 458 */     V oldValue = get(key), drv = defaultReturnValue();
/* 459 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 460 */     V newValue = remappingFunction.apply(Short.valueOf(key), oldValue);
/* 461 */     if (newValue == null) {
/* 462 */       remove(key);
/* 463 */       return drv;
/*     */     } 
/* 465 */     put(key, newValue);
/* 466 */     return newValue;
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
/* 488 */     Objects.requireNonNull(remappingFunction);
/* 489 */     V oldValue = get(key), drv = defaultReturnValue();
/* 490 */     boolean contained = (oldValue != drv || containsKey(key));
/* 491 */     V newValue = remappingFunction.apply(Short.valueOf(key), contained ? oldValue : null);
/* 492 */     if (newValue == null) {
/* 493 */       if (contained) remove(key); 
/* 494 */       return drv;
/*     */     } 
/* 496 */     put(key, newValue);
/* 497 */     return newValue;
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
/* 520 */     Objects.requireNonNull(remappingFunction);
/* 521 */     Objects.requireNonNull(value);
/* 522 */     V oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 524 */     if (oldValue != drv || containsKey(key)) {
/* 525 */       V mergedValue = remappingFunction.apply(oldValue, value);
/* 526 */       if (mergedValue == null) {
/* 527 */         remove(key);
/* 528 */         return drv;
/*     */       } 
/* 530 */       newValue = mergedValue;
/*     */     } else {
/* 532 */       newValue = value;
/*     */     } 
/* 534 */     put(key, newValue);
/* 535 */     return newValue;
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
/*     */   ObjectSet<Entry<V>> short2ObjectEntrySet();
/*     */   
/*     */   ShortSet keySet();
/*     */   
/*     */   ObjectCollection<V> values();
/*     */   
/*     */   boolean containsKey(short paramShort);
/*     */   
/*     */   public static interface Entry<V>
/*     */     extends Map.Entry<Short, V>
/*     */   {
/*     */     @Deprecated
/*     */     default Short getKey() {
/* 560 */       return Short.valueOf(getShortKey());
/*     */     }
/*     */     
/*     */     short getShortKey();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2ObjectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */