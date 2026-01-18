/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.floats.FloatBinaryOperator;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleBinaryOperator;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Reference2FloatMap<K>
/*     */   extends Reference2FloatFunction<K>, Map<K, Float>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Reference2FloatMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
/*  82 */       forEach(consumer);
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
/* 105 */     throw new UnsupportedOperationException();
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
/*     */   default ObjectSet<Map.Entry<K, Float>> entrySet() {
/* 156 */     return (ObjectSet)reference2FloatEntrySet();
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
/*     */   default Float put(K key, Float value) {
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
/*     */   default Float get(Object key) {
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
/*     */   default Float remove(Object key) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean containsValue(Object value) {
/* 246 */     return (value == null) ? false : containsValue(((Float)value).floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEach(BiConsumer<? super K, ? super Float> consumer) {
/* 252 */     ObjectSet<Entry<K>> entrySet = reference2FloatEntrySet();
/* 253 */     Consumer<Entry<K>> wrappingConsumer = entry -> consumer.accept(entry.getKey(), Float.valueOf(entry.getFloatValue()));
/* 254 */     if (entrySet instanceof FastEntrySet) {
/* 255 */       ((FastEntrySet)entrySet).fastForEach(wrappingConsumer);
/*     */     } else {
/* 257 */       entrySet.forEach(wrappingConsumer);
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
/*     */   default float getOrDefault(Object key, float defaultValue) {
/*     */     float v;
/* 277 */     return ((v = getFloat(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default Float getOrDefault(Object key, Float defaultValue) {
/* 290 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default float putIfAbsent(K key, float value) {
/* 309 */     float v = getFloat(key), drv = defaultReturnValue();
/* 310 */     if (v != drv || containsKey(key)) return v; 
/* 311 */     put(key, value);
/* 312 */     return drv;
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
/*     */   default boolean remove(Object key, float value) {
/* 327 */     float curValue = getFloat(key);
/* 328 */     if (Float.floatToIntBits(curValue) != Float.floatToIntBits(value) || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 329 */     removeFloat(key);
/* 330 */     return true;
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
/*     */   default boolean replace(K key, float oldValue, float newValue) {
/* 346 */     float curValue = getFloat(key);
/* 347 */     if (Float.floatToIntBits(curValue) != Float.floatToIntBits(oldValue) || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 348 */     put(key, newValue);
/* 349 */     return true;
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
/*     */   default float replace(K key, float value) {
/* 366 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/*     */   default float computeIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 393 */     Objects.requireNonNull(mappingFunction);
/* 394 */     float v = getFloat(key);
/* 395 */     if (v != defaultReturnValue() || containsKey(key)) return v; 
/* 396 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(key));
/* 397 */     put(key, newValue);
/* 398 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default float computeFloatIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 406 */     return computeIfAbsent(key, mappingFunction);
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
/*     */   default float computeIfAbsent(K key, Reference2FloatFunction<? super K> mappingFunction) {
/* 435 */     Objects.requireNonNull(mappingFunction);
/* 436 */     float v = getFloat(key), drv = defaultReturnValue();
/* 437 */     if (v != drv || containsKey(key)) return v; 
/* 438 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 439 */     float newValue = mappingFunction.getFloat(key);
/* 440 */     put(key, newValue);
/* 441 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default float computeFloatIfAbsentPartial(K key, Reference2FloatFunction<? super K> mappingFunction) {
/* 449 */     return computeIfAbsent(key, mappingFunction);
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
/*     */   default float computeFloatIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 469 */     Objects.requireNonNull(remappingFunction);
/* 470 */     float oldValue = getFloat(key), drv = defaultReturnValue();
/* 471 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 472 */     Float newValue = remappingFunction.apply(key, Float.valueOf(oldValue));
/* 473 */     if (newValue == null) {
/* 474 */       removeFloat(key);
/* 475 */       return drv;
/*     */     } 
/* 477 */     float newVal = newValue.floatValue();
/* 478 */     put(key, newVal);
/* 479 */     return newVal;
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
/*     */   default float computeFloat(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 501 */     Objects.requireNonNull(remappingFunction);
/* 502 */     float oldValue = getFloat(key), drv = defaultReturnValue();
/* 503 */     boolean contained = (oldValue != drv || containsKey(key));
/* 504 */     Float newValue = remappingFunction.apply(key, contained ? Float.valueOf(oldValue) : null);
/* 505 */     if (newValue == null) {
/* 506 */       if (contained) removeFloat(key); 
/* 507 */       return drv;
/*     */     } 
/* 509 */     float newVal = newValue.floatValue();
/* 510 */     put(key, newVal);
/* 511 */     return newVal;
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
/*     */   default float merge(K key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*     */     float newValue;
/* 534 */     Objects.requireNonNull(remappingFunction);
/*     */     
/* 536 */     float oldValue = getFloat(key), drv = defaultReturnValue();
/*     */     
/* 538 */     if (oldValue != drv || containsKey(key)) {
/* 539 */       Float mergedValue = remappingFunction.apply(Float.valueOf(oldValue), Float.valueOf(value));
/* 540 */       if (mergedValue == null) {
/* 541 */         removeFloat(key);
/* 542 */         return drv;
/*     */       } 
/* 544 */       newValue = mergedValue.floatValue();
/*     */     } else {
/* 546 */       newValue = value;
/*     */     } 
/* 548 */     put(key, newValue);
/* 549 */     return newValue;
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
/*     */   default float mergeFloat(K key, float value, FloatBinaryOperator remappingFunction) {
/* 573 */     Objects.requireNonNull(remappingFunction);
/* 574 */     float oldValue = getFloat(key), drv = defaultReturnValue();
/* 575 */     float newValue = (oldValue != drv || containsKey(key)) ? remappingFunction.apply(oldValue, value) : value;
/* 576 */     put(key, newValue);
/* 577 */     return newValue;
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
/*     */   default float mergeFloat(K key, float value, DoubleBinaryOperator remappingFunction) {
/* 604 */     return mergeFloat(key, value, (remappingFunction instanceof FloatBinaryOperator) ? (FloatBinaryOperator)remappingFunction : ((x, y) -> SafeMath.safeDoubleToFloat(remappingFunction.applyAsDouble(x, y))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default float mergeFloat(K key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 612 */     return merge(key, value, remappingFunction);
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
/*     */   default Float putIfAbsent(K key, Float value) {
/* 625 */     return super.putIfAbsent(key, value);
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
/* 638 */     return super.remove(key, value);
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
/*     */   default boolean replace(K key, Float oldValue, Float newValue) {
/* 651 */     return super.replace(key, oldValue, newValue);
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
/*     */   default Float replace(K key, Float value) {
/* 664 */     return super.replace(key, value);
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
/*     */   default Float merge(K key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 677 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */   
/*     */   void defaultReturnValue(float paramFloat);
/*     */ 
/*     */   
/*     */   float defaultReturnValue();
/*     */ 
/*     */   
/*     */   ObjectSet<Entry<K>> reference2FloatEntrySet();
/*     */ 
/*     */   
/*     */   ReferenceSet<K> keySet();
/*     */ 
/*     */   
/*     */   FloatCollection values();
/*     */ 
/*     */   
/*     */   boolean containsKey(Object paramObject);
/*     */ 
/*     */   
/*     */   boolean containsValue(float paramFloat);
/*     */   
/*     */   public static interface Entry<K>
/*     */     extends Map.Entry<K, Float>
/*     */   {
/*     */     @Deprecated
/*     */     default Float getValue() {
/* 709 */       return Float.valueOf(getFloatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Float setValue(Float value) {
/* 720 */       return Float.valueOf(setValue(value.floatValue()));
/*     */     }
/*     */     
/*     */     float getFloatValue();
/*     */     
/*     */     float setValue(float param1Float);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2FloatMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */