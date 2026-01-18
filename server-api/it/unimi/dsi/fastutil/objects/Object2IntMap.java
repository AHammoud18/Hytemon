/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntBinaryOperator;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.IntBinaryOperator;
/*     */ import java.util.function.ToIntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Object2IntMap<K>
/*     */   extends Object2IntFunction<K>, Map<K, Integer>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Object2IntMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
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
/*     */   default ObjectSet<Map.Entry<K, Integer>> entrySet() {
/* 148 */     return (ObjectSet)object2IntEntrySet();
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
/*     */   default Integer put(K key, Integer value) {
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
/*     */   default Integer get(Object key) {
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
/*     */   default Integer remove(Object key) {
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
/* 238 */     return (value == null) ? false : containsValue(((Integer)value).intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEach(BiConsumer<? super K, ? super Integer> consumer) {
/* 244 */     ObjectSet<Entry<K>> entrySet = object2IntEntrySet();
/* 245 */     Consumer<Entry<K>> wrappingConsumer = entry -> consumer.accept(entry.getKey(), Integer.valueOf(entry.getIntValue()));
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
/*     */   default int getOrDefault(Object key, int defaultValue) {
/*     */     int v;
/* 269 */     return ((v = getInt(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default Integer getOrDefault(Object key, Integer defaultValue) {
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
/*     */   default int putIfAbsent(K key, int value) {
/* 301 */     int v = getInt(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(Object key, int value) {
/* 319 */     int curValue = getInt(key);
/* 320 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 321 */     removeInt(key);
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
/*     */   default boolean replace(K key, int oldValue, int newValue) {
/* 338 */     int curValue = getInt(key);
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
/*     */   default int replace(K key, int value) {
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
/*     */   default int computeIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 385 */     Objects.requireNonNull(mappingFunction);
/* 386 */     int v = getInt(key);
/* 387 */     if (v != defaultReturnValue() || containsKey(key)) return v; 
/* 388 */     int newValue = mappingFunction.applyAsInt(key);
/* 389 */     put(key, newValue);
/* 390 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default int computeIntIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
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
/*     */   default int computeIfAbsent(K key, Object2IntFunction<? super K> mappingFunction) {
/* 427 */     Objects.requireNonNull(mappingFunction);
/* 428 */     int v = getInt(key), drv = defaultReturnValue();
/* 429 */     if (v != drv || containsKey(key)) return v; 
/* 430 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 431 */     int newValue = mappingFunction.getInt(key);
/* 432 */     put(key, newValue);
/* 433 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default int computeIntIfAbsentPartial(K key, Object2IntFunction<? super K> mappingFunction) {
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
/*     */   default int computeIntIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 461 */     Objects.requireNonNull(remappingFunction);
/* 462 */     int oldValue = getInt(key), drv = defaultReturnValue();
/* 463 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 464 */     Integer newValue = remappingFunction.apply(key, Integer.valueOf(oldValue));
/* 465 */     if (newValue == null) {
/* 466 */       removeInt(key);
/* 467 */       return drv;
/*     */     } 
/* 469 */     int newVal = newValue.intValue();
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
/*     */   default int computeInt(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 493 */     Objects.requireNonNull(remappingFunction);
/* 494 */     int oldValue = getInt(key), drv = defaultReturnValue();
/* 495 */     boolean contained = (oldValue != drv || containsKey(key));
/* 496 */     Integer newValue = remappingFunction.apply(key, contained ? Integer.valueOf(oldValue) : null);
/* 497 */     if (newValue == null) {
/* 498 */       if (contained) removeInt(key); 
/* 499 */       return drv;
/*     */     } 
/* 501 */     int newVal = newValue.intValue();
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
/*     */   default int merge(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*     */     int newValue;
/* 526 */     Objects.requireNonNull(remappingFunction);
/*     */     
/* 528 */     int oldValue = getInt(key), drv = defaultReturnValue();
/*     */     
/* 530 */     if (oldValue != drv || containsKey(key)) {
/* 531 */       Integer mergedValue = remappingFunction.apply(Integer.valueOf(oldValue), Integer.valueOf(value));
/* 532 */       if (mergedValue == null) {
/* 533 */         removeInt(key);
/* 534 */         return drv;
/*     */       } 
/* 536 */       newValue = mergedValue.intValue();
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
/*     */   default int mergeInt(K key, int value, IntBinaryOperator remappingFunction) {
/* 565 */     Objects.requireNonNull(remappingFunction);
/* 566 */     int oldValue = getInt(key), drv = defaultReturnValue();
/* 567 */     int newValue = (oldValue != drv || containsKey(key)) ? remappingFunction.applyAsInt(oldValue, value) : value;
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
/*     */   default int mergeInt(K key, int value, IntBinaryOperator remappingFunction) {
/* 608 */     return mergeInt(key, value, (IntBinaryOperator)remappingFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default int mergeInt(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */   default Integer putIfAbsent(K key, Integer value) {
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
/*     */   default boolean replace(K key, Integer oldValue, Integer newValue) {
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
/*     */   default Integer replace(K key, Integer value) {
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
/*     */   default Integer merge(K key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 681 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */   
/*     */   void defaultReturnValue(int paramInt);
/*     */ 
/*     */   
/*     */   int defaultReturnValue();
/*     */ 
/*     */   
/*     */   ObjectSet<Entry<K>> object2IntEntrySet();
/*     */ 
/*     */   
/*     */   ObjectSet<K> keySet();
/*     */ 
/*     */   
/*     */   IntCollection values();
/*     */ 
/*     */   
/*     */   boolean containsKey(Object paramObject);
/*     */ 
/*     */   
/*     */   boolean containsValue(int paramInt);
/*     */   
/*     */   public static interface Entry<K>
/*     */     extends Map.Entry<K, Integer>
/*     */   {
/*     */     @Deprecated
/*     */     default Integer getValue() {
/* 713 */       return Integer.valueOf(getIntValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Integer setValue(Integer value) {
/* 724 */       return Integer.valueOf(setValue(value.intValue()));
/*     */     }
/*     */     
/*     */     int getIntValue();
/*     */     
/*     */     int setValue(int param1Int);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2IntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */