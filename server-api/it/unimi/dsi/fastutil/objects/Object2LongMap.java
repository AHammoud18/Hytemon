/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.LongBinaryOperator;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ import java.util.function.ToLongFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Object2LongMap<K>
/*     */   extends Object2LongFunction<K>, Map<K, Long>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Object2LongMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
/*  74 */       forEach(consumer);
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
/*  97 */     throw new UnsupportedOperationException();
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
/*     */   @Deprecated
/*     */   default ObjectSet<Map.Entry<K, Long>> entrySet() {
/* 148 */     return (ObjectSet)object2LongEntrySet();
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
/*     */   default Long put(K key, Long value) {
/* 162 */     return super.put(key, value);
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
/*     */   default Long get(Object key) {
/* 176 */     return super.get(key);
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
/*     */   default Long remove(Object key) {
/* 190 */     return super.remove(key);
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
/*     */   @Deprecated
/*     */   default boolean containsValue(Object value) {
/* 238 */     return (value == null) ? false : containsValue(((Long)value).longValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEach(BiConsumer<? super K, ? super Long> consumer) {
/* 244 */     ObjectSet<Entry<K>> entrySet = object2LongEntrySet();
/* 245 */     Consumer<Entry<K>> wrappingConsumer = entry -> consumer.accept(entry.getKey(), Long.valueOf(entry.getLongValue()));
/* 246 */     if (entrySet instanceof FastEntrySet) {
/* 247 */       ((FastEntrySet)entrySet).fastForEach(wrappingConsumer);
/*     */     } else {
/* 249 */       entrySet.forEach(wrappingConsumer);
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
/*     */   default long getOrDefault(Object key, long defaultValue) {
/*     */     long v;
/* 269 */     return ((v = getLong(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default Long getOrDefault(Object key, Long defaultValue) {
/* 282 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default long putIfAbsent(K key, long value) {
/* 301 */     long v = getLong(key), drv = defaultReturnValue();
/* 302 */     if (v != drv || containsKey(key)) return v; 
/* 303 */     put(key, value);
/* 304 */     return drv;
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
/*     */   default boolean remove(Object key, long value) {
/* 319 */     long curValue = getLong(key);
/* 320 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 321 */     removeLong(key);
/* 322 */     return true;
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
/*     */   default boolean replace(K key, long oldValue, long newValue) {
/* 338 */     long curValue = getLong(key);
/* 339 */     if (curValue != oldValue || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 340 */     put(key, newValue);
/* 341 */     return true;
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
/*     */   default long replace(K key, long value) {
/* 358 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/*     */   default long computeIfAbsent(K key, ToLongFunction<? super K> mappingFunction) {
/* 385 */     Objects.requireNonNull(mappingFunction);
/* 386 */     long v = getLong(key);
/* 387 */     if (v != defaultReturnValue() || containsKey(key)) return v; 
/* 388 */     long newValue = mappingFunction.applyAsLong(key);
/* 389 */     put(key, newValue);
/* 390 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default long computeLongIfAbsent(K key, ToLongFunction<? super K> mappingFunction) {
/* 398 */     return computeIfAbsent(key, mappingFunction);
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
/*     */   default long computeIfAbsent(K key, Object2LongFunction<? super K> mappingFunction) {
/* 427 */     Objects.requireNonNull(mappingFunction);
/* 428 */     long v = getLong(key), drv = defaultReturnValue();
/* 429 */     if (v != drv || containsKey(key)) return v; 
/* 430 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 431 */     long newValue = mappingFunction.getLong(key);
/* 432 */     put(key, newValue);
/* 433 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default long computeLongIfAbsentPartial(K key, Object2LongFunction<? super K> mappingFunction) {
/* 441 */     return computeIfAbsent(key, mappingFunction);
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
/*     */   default long computeLongIfPresent(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/* 461 */     Objects.requireNonNull(remappingFunction);
/* 462 */     long oldValue = getLong(key), drv = defaultReturnValue();
/* 463 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 464 */     Long newValue = remappingFunction.apply(key, Long.valueOf(oldValue));
/* 465 */     if (newValue == null) {
/* 466 */       removeLong(key);
/* 467 */       return drv;
/*     */     } 
/* 469 */     long newVal = newValue.longValue();
/* 470 */     put(key, newVal);
/* 471 */     return newVal;
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
/*     */   default long computeLong(K key, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/* 493 */     Objects.requireNonNull(remappingFunction);
/* 494 */     long oldValue = getLong(key), drv = defaultReturnValue();
/* 495 */     boolean contained = (oldValue != drv || containsKey(key));
/* 496 */     Long newValue = remappingFunction.apply(key, contained ? Long.valueOf(oldValue) : null);
/* 497 */     if (newValue == null) {
/* 498 */       if (contained) removeLong(key); 
/* 499 */       return drv;
/*     */     } 
/* 501 */     long newVal = newValue.longValue();
/* 502 */     put(key, newVal);
/* 503 */     return newVal;
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
/*     */   default long merge(K key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*     */     long newValue;
/* 526 */     Objects.requireNonNull(remappingFunction);
/*     */     
/* 528 */     long oldValue = getLong(key), drv = defaultReturnValue();
/*     */     
/* 530 */     if (oldValue != drv || containsKey(key)) {
/* 531 */       Long mergedValue = remappingFunction.apply(Long.valueOf(oldValue), Long.valueOf(value));
/* 532 */       if (mergedValue == null) {
/* 533 */         removeLong(key);
/* 534 */         return drv;
/*     */       } 
/* 536 */       newValue = mergedValue.longValue();
/*     */     } else {
/* 538 */       newValue = value;
/*     */     } 
/* 540 */     put(key, newValue);
/* 541 */     return newValue;
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
/*     */   default long mergeLong(K key, long value, LongBinaryOperator remappingFunction) {
/* 565 */     Objects.requireNonNull(remappingFunction);
/* 566 */     long oldValue = getLong(key), drv = defaultReturnValue();
/* 567 */     long newValue = (oldValue != drv || containsKey(key)) ? remappingFunction.applyAsLong(oldValue, value) : value;
/* 568 */     put(key, newValue);
/* 569 */     return newValue;
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
/*     */   default long mergeLong(K key, long value, LongBinaryOperator remappingFunction) {
/* 608 */     return mergeLong(key, value, (LongBinaryOperator)remappingFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default long mergeLong(K key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 616 */     return merge(key, value, remappingFunction);
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
/*     */   default Long putIfAbsent(K key, Long value) {
/* 629 */     return super.putIfAbsent(key, value);
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
/*     */   default boolean remove(Object key, Object value) {
/* 642 */     return super.remove(key, value);
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
/*     */   default boolean replace(K key, Long oldValue, Long newValue) {
/* 655 */     return super.replace(key, oldValue, newValue);
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
/*     */   default Long replace(K key, Long value) {
/* 668 */     return super.replace(key, value);
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
/*     */   default Long merge(K key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 681 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */   
/*     */   void defaultReturnValue(long paramLong);
/*     */ 
/*     */   
/*     */   long defaultReturnValue();
/*     */ 
/*     */   
/*     */   ObjectSet<Entry<K>> object2LongEntrySet();
/*     */ 
/*     */   
/*     */   ObjectSet<K> keySet();
/*     */ 
/*     */   
/*     */   LongCollection values();
/*     */ 
/*     */   
/*     */   boolean containsKey(Object paramObject);
/*     */ 
/*     */   
/*     */   boolean containsValue(long paramLong);
/*     */   
/*     */   public static interface Entry<K>
/*     */     extends Map.Entry<K, Long>
/*     */   {
/*     */     @Deprecated
/*     */     default Long getValue() {
/* 713 */       return Long.valueOf(getLongValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Long setValue(Long value) {
/* 724 */       return Long.valueOf(setValue(value.longValue()));
/*     */     }
/*     */     
/*     */     long getLongValue();
/*     */     
/*     */     long setValue(long param1Long);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2LongMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */