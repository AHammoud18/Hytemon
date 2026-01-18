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
/*     */ public interface Object2FloatMap<K>
/*     */   extends Object2FloatFunction<K>, Map<K, Float>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Object2FloatMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Object2FloatMap.Entry<K>> consumer) {
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
/*     */   default ObjectSet<Map.Entry<K, Float>> entrySet() {
/* 148 */     return (ObjectSet)object2FloatEntrySet();
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
/*     */   default Float get(Object key) {
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
/*     */   default Float remove(Object key) {
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
/* 238 */     return (value == null) ? false : containsValue(((Float)value).floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEach(BiConsumer<? super K, ? super Float> consumer) {
/* 244 */     ObjectSet<Entry<K>> entrySet = object2FloatEntrySet();
/* 245 */     Consumer<Entry<K>> wrappingConsumer = entry -> consumer.accept(entry.getKey(), Float.valueOf(entry.getFloatValue()));
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
/*     */   default float getOrDefault(Object key, float defaultValue) {
/*     */     float v;
/* 269 */     return ((v = getFloat(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default float putIfAbsent(K key, float value) {
/* 301 */     float v = getFloat(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(Object key, float value) {
/* 319 */     float curValue = getFloat(key);
/* 320 */     if (Float.floatToIntBits(curValue) != Float.floatToIntBits(value) || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 321 */     removeFloat(key);
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
/*     */   default boolean replace(K key, float oldValue, float newValue) {
/* 338 */     float curValue = getFloat(key);
/* 339 */     if (Float.floatToIntBits(curValue) != Float.floatToIntBits(oldValue) || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
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
/*     */   default float replace(K key, float value) {
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
/*     */   default float computeIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 385 */     Objects.requireNonNull(mappingFunction);
/* 386 */     float v = getFloat(key);
/* 387 */     if (v != defaultReturnValue() || containsKey(key)) return v; 
/* 388 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(key));
/* 389 */     put(key, newValue);
/* 390 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default float computeFloatIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
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
/*     */   default float computeIfAbsent(K key, Object2FloatFunction<? super K> mappingFunction) {
/* 427 */     Objects.requireNonNull(mappingFunction);
/* 428 */     float v = getFloat(key), drv = defaultReturnValue();
/* 429 */     if (v != drv || containsKey(key)) return v; 
/* 430 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 431 */     float newValue = mappingFunction.getFloat(key);
/* 432 */     put(key, newValue);
/* 433 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default float computeFloatIfAbsentPartial(K key, Object2FloatFunction<? super K> mappingFunction) {
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
/*     */   default float computeFloatIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 461 */     Objects.requireNonNull(remappingFunction);
/* 462 */     float oldValue = getFloat(key), drv = defaultReturnValue();
/* 463 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 464 */     Float newValue = remappingFunction.apply(key, Float.valueOf(oldValue));
/* 465 */     if (newValue == null) {
/* 466 */       removeFloat(key);
/* 467 */       return drv;
/*     */     } 
/* 469 */     float newVal = newValue.floatValue();
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
/*     */   default float computeFloat(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 493 */     Objects.requireNonNull(remappingFunction);
/* 494 */     float oldValue = getFloat(key), drv = defaultReturnValue();
/* 495 */     boolean contained = (oldValue != drv || containsKey(key));
/* 496 */     Float newValue = remappingFunction.apply(key, contained ? Float.valueOf(oldValue) : null);
/* 497 */     if (newValue == null) {
/* 498 */       if (contained) removeFloat(key); 
/* 499 */       return drv;
/*     */     } 
/* 501 */     float newVal = newValue.floatValue();
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
/*     */   default float merge(K key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*     */     float newValue;
/* 526 */     Objects.requireNonNull(remappingFunction);
/*     */     
/* 528 */     float oldValue = getFloat(key), drv = defaultReturnValue();
/*     */     
/* 530 */     if (oldValue != drv || containsKey(key)) {
/* 531 */       Float mergedValue = remappingFunction.apply(Float.valueOf(oldValue), Float.valueOf(value));
/* 532 */       if (mergedValue == null) {
/* 533 */         removeFloat(key);
/* 534 */         return drv;
/*     */       } 
/* 536 */       newValue = mergedValue.floatValue();
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
/*     */   default float mergeFloat(K key, float value, FloatBinaryOperator remappingFunction) {
/* 565 */     Objects.requireNonNull(remappingFunction);
/* 566 */     float oldValue = getFloat(key), drv = defaultReturnValue();
/* 567 */     float newValue = (oldValue != drv || containsKey(key)) ? remappingFunction.apply(oldValue, value) : value;
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
/*     */   default float mergeFloat(K key, float value, DoubleBinaryOperator remappingFunction) {
/* 596 */     return mergeFloat(key, value, (remappingFunction instanceof FloatBinaryOperator) ? (FloatBinaryOperator)remappingFunction : ((x, y) -> SafeMath.safeDoubleToFloat(remappingFunction.applyAsDouble(x, y))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default float mergeFloat(K key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 604 */     return merge(key, value, remappingFunction);
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
/* 617 */     return super.putIfAbsent(key, value);
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
/* 630 */     return super.remove(key, value);
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
/* 643 */     return super.replace(key, oldValue, newValue);
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
/* 656 */     return super.replace(key, value);
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
/* 669 */     return super.merge(key, value, remappingFunction);
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
/*     */   ObjectSet<Entry<K>> object2FloatEntrySet();
/*     */ 
/*     */   
/*     */   ObjectSet<K> keySet();
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
/* 701 */       return Float.valueOf(getFloatValue());
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
/* 712 */       return Float.valueOf(setValue(value.floatValue()));
/*     */     }
/*     */     
/*     */     float getFloatValue();
/*     */     
/*     */     float setValue(float param1Float);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2FloatMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */