/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntBinaryOperator;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.IntUnaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Short2ShortMap
/*     */   extends Short2ShortFunction, Map<Short, Short>
/*     */ {
/*     */   public static interface FastEntrySet
/*     */     extends ObjectSet<Entry>
/*     */   {
/*     */     ObjectIterator<Short2ShortMap.Entry> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Short2ShortMap.Entry> consumer) {
/*  75 */       forEach(consumer);
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
/*  98 */     throw new UnsupportedOperationException();
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
/*     */   default ObjectSet<Map.Entry<Short, Short>> entrySet() {
/* 149 */     return (ObjectSet)short2ShortEntrySet();
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
/*     */   default Short put(Short key, Short value) {
/* 163 */     return super.put(key, value);
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
/*     */   default Short get(Object key) {
/* 177 */     return super.get(key);
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
/*     */   default Short remove(Object key) {
/* 191 */     return super.remove(key);
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
/* 235 */     return super.containsKey(key);
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
/*     */   @Deprecated
/*     */   default boolean containsValue(Object value) {
/* 253 */     return (value == null) ? false : containsValue(((Short)value).shortValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEach(BiConsumer<? super Short, ? super Short> consumer) {
/* 259 */     ObjectSet<Entry> entrySet = short2ShortEntrySet();
/* 260 */     Consumer<Entry> wrappingConsumer = entry -> consumer.accept(Short.valueOf(entry.getShortKey()), Short.valueOf(entry.getShortValue()));
/* 261 */     if (entrySet instanceof FastEntrySet) {
/* 262 */       ((FastEntrySet)entrySet).fastForEach(wrappingConsumer);
/*     */     } else {
/* 264 */       entrySet.forEach(wrappingConsumer);
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
/*     */   default short getOrDefault(short key, short defaultValue) {
/*     */     short v;
/* 284 */     return ((v = get(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default Short getOrDefault(Object key, Short defaultValue) {
/* 297 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default short putIfAbsent(short key, short value) {
/* 316 */     short v = get(key), drv = defaultReturnValue();
/* 317 */     if (v != drv || containsKey(key)) return v; 
/* 318 */     put(key, value);
/* 319 */     return drv;
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
/*     */   default boolean remove(short key, short value) {
/* 334 */     short curValue = get(key);
/* 335 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 336 */     remove(key);
/* 337 */     return true;
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
/*     */   default boolean replace(short key, short oldValue, short newValue) {
/* 353 */     short curValue = get(key);
/* 354 */     if (curValue != oldValue || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 355 */     put(key, newValue);
/* 356 */     return true;
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
/*     */   default short replace(short key, short value) {
/* 373 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/*     */   default short computeIfAbsent(short key, IntUnaryOperator mappingFunction) {
/* 400 */     Objects.requireNonNull(mappingFunction);
/* 401 */     short v = get(key);
/* 402 */     if (v != defaultReturnValue() || containsKey(key)) return v; 
/* 403 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(key));
/* 404 */     put(key, newValue);
/* 405 */     return newValue;
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
/*     */   default short computeIfAbsentNullable(short key, IntFunction<? extends Short> mappingFunction) {
/* 428 */     Objects.requireNonNull(mappingFunction);
/* 429 */     short v = get(key), drv = defaultReturnValue();
/* 430 */     if (v != drv || containsKey(key)) return v; 
/* 431 */     Short mappedValue = mappingFunction.apply(key);
/* 432 */     if (mappedValue == null) return drv; 
/* 433 */     short newValue = mappedValue.shortValue();
/* 434 */     put(key, newValue);
/* 435 */     return newValue;
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
/*     */   default short computeIfAbsent(short key, Short2ShortFunction mappingFunction) {
/* 464 */     Objects.requireNonNull(mappingFunction);
/* 465 */     short v = get(key), drv = defaultReturnValue();
/* 466 */     if (v != drv || containsKey(key)) return v; 
/* 467 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 468 */     short newValue = mappingFunction.get(key);
/* 469 */     put(key, newValue);
/* 470 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default short computeIfAbsentPartial(short key, Short2ShortFunction mappingFunction) {
/* 478 */     return computeIfAbsent(key, mappingFunction);
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
/*     */   default short computeIfPresent(short key, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/* 498 */     Objects.requireNonNull(remappingFunction);
/* 499 */     short oldValue = get(key), drv = defaultReturnValue();
/* 500 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 501 */     Short newValue = remappingFunction.apply(Short.valueOf(key), Short.valueOf(oldValue));
/* 502 */     if (newValue == null) {
/* 503 */       remove(key);
/* 504 */       return drv;
/*     */     } 
/* 506 */     short newVal = newValue.shortValue();
/* 507 */     put(key, newVal);
/* 508 */     return newVal;
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
/*     */   default short compute(short key, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/* 530 */     Objects.requireNonNull(remappingFunction);
/* 531 */     short oldValue = get(key), drv = defaultReturnValue();
/* 532 */     boolean contained = (oldValue != drv || containsKey(key));
/* 533 */     Short newValue = remappingFunction.apply(Short.valueOf(key), contained ? Short.valueOf(oldValue) : null);
/* 534 */     if (newValue == null) {
/* 535 */       if (contained) remove(key); 
/* 536 */       return drv;
/*     */     } 
/* 538 */     short newVal = newValue.shortValue();
/* 539 */     put(key, newVal);
/* 540 */     return newVal;
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
/*     */   default short merge(short key, short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*     */     short newValue;
/* 563 */     Objects.requireNonNull(remappingFunction);
/*     */     
/* 565 */     short oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 567 */     if (oldValue != drv || containsKey(key)) {
/* 568 */       Short mergedValue = remappingFunction.apply(Short.valueOf(oldValue), Short.valueOf(value));
/* 569 */       if (mergedValue == null) {
/* 570 */         remove(key);
/* 571 */         return drv;
/*     */       } 
/* 573 */       newValue = mergedValue.shortValue();
/*     */     } else {
/* 575 */       newValue = value;
/*     */     } 
/* 577 */     put(key, newValue);
/* 578 */     return newValue;
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
/*     */   default short mergeShort(short key, short value, ShortBinaryOperator remappingFunction) {
/* 602 */     Objects.requireNonNull(remappingFunction);
/* 603 */     short oldValue = get(key), drv = defaultReturnValue();
/* 604 */     short newValue = (oldValue != drv || containsKey(key)) ? remappingFunction.apply(oldValue, value) : value;
/* 605 */     put(key, newValue);
/* 606 */     return newValue;
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
/*     */   default short mergeShort(short key, short value, IntBinaryOperator remappingFunction) {
/* 633 */     return mergeShort(key, value, (remappingFunction instanceof ShortBinaryOperator) ? (ShortBinaryOperator)remappingFunction : ((x, y) -> SafeMath.safeIntToShort(remappingFunction.applyAsInt(x, y))));
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
/*     */   default Short putIfAbsent(Short key, Short value) {
/* 646 */     return super.putIfAbsent(key, value);
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
/* 659 */     return super.remove(key, value);
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
/*     */   default boolean replace(Short key, Short oldValue, Short newValue) {
/* 672 */     return super.replace(key, oldValue, newValue);
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
/*     */   default Short replace(Short key, Short value) {
/* 685 */     return super.replace(key, value);
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
/*     */   default Short computeIfAbsent(Short key, Function<? super Short, ? extends Short> mappingFunction) {
/* 698 */     return super.computeIfAbsent(key, mappingFunction);
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
/*     */   default Short computeIfPresent(Short key, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/* 711 */     return super.computeIfPresent(key, remappingFunction);
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
/*     */   default Short compute(Short key, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/* 724 */     return super.compute(key, remappingFunction);
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
/*     */   default Short merge(Short key, Short value, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/* 737 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(short paramShort);
/*     */   
/*     */   short defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry> short2ShortEntrySet();
/*     */   
/*     */   ShortSet keySet();
/*     */   
/*     */   ShortCollection values();
/*     */   
/*     */   boolean containsKey(short paramShort);
/*     */   
/*     */   boolean containsValue(short paramShort);
/*     */   
/*     */   public static interface Entry
/*     */     extends Map.Entry<Short, Short>
/*     */   {
/*     */     @Deprecated
/*     */     default Short getKey() {
/* 762 */       return Short.valueOf(getShortKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Short getValue() {
/* 787 */       return Short.valueOf(getShortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Short setValue(Short value) {
/* 798 */       return Short.valueOf(setValue(value.shortValue()));
/*     */     }
/*     */     
/*     */     short getShortKey();
/*     */     
/*     */     short getShortValue();
/*     */     
/*     */     short setValue(short param1Short);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2ShortMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */