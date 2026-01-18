/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.floats.FloatBinaryOperator;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleBinaryOperator;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.IntToDoubleFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Byte2FloatMap
/*     */   extends Byte2FloatFunction, Map<Byte, Float>
/*     */ {
/*     */   public static interface FastEntrySet
/*     */     extends ObjectSet<Entry>
/*     */   {
/*     */     ObjectIterator<Byte2FloatMap.Entry> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Byte2FloatMap.Entry> consumer) {
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
/*     */   @Deprecated
/*     */   default ObjectSet<Map.Entry<Byte, Float>> entrySet() {
/* 150 */     return (ObjectSet)byte2FloatEntrySet();
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
/*     */   default Float put(Byte key, Float value) {
/* 164 */     return super.put(key, value);
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
/* 178 */     return super.get(key);
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
/* 192 */     return super.remove(key);
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
/* 236 */     return super.containsKey(key);
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
/* 254 */     return (value == null) ? false : containsValue(((Float)value).floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEach(BiConsumer<? super Byte, ? super Float> consumer) {
/* 260 */     ObjectSet<Entry> entrySet = byte2FloatEntrySet();
/* 261 */     Consumer<Entry> wrappingConsumer = entry -> consumer.accept(Byte.valueOf(entry.getByteKey()), Float.valueOf(entry.getFloatValue()));
/* 262 */     if (entrySet instanceof FastEntrySet) {
/* 263 */       ((FastEntrySet)entrySet).fastForEach(wrappingConsumer);
/*     */     } else {
/* 265 */       entrySet.forEach(wrappingConsumer);
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
/*     */   default float getOrDefault(byte key, float defaultValue) {
/*     */     float v;
/* 285 */     return ((v = get(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/* 298 */     return super.getOrDefault(key, defaultValue);
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
/*     */   default float putIfAbsent(byte key, float value) {
/* 317 */     float v = get(key), drv = defaultReturnValue();
/* 318 */     if (v != drv || containsKey(key)) return v; 
/* 319 */     put(key, value);
/* 320 */     return drv;
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
/*     */   default boolean remove(byte key, float value) {
/* 335 */     float curValue = get(key);
/* 336 */     if (Float.floatToIntBits(curValue) != Float.floatToIntBits(value) || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 337 */     remove(key);
/* 338 */     return true;
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
/*     */   default boolean replace(byte key, float oldValue, float newValue) {
/* 354 */     float curValue = get(key);
/* 355 */     if (Float.floatToIntBits(curValue) != Float.floatToIntBits(oldValue) || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 356 */     put(key, newValue);
/* 357 */     return true;
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
/*     */   default float replace(byte key, float value) {
/* 374 */     return containsKey(key) ? put(key, value) : defaultReturnValue();
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
/*     */   default float computeIfAbsent(byte key, IntToDoubleFunction mappingFunction) {
/* 401 */     Objects.requireNonNull(mappingFunction);
/* 402 */     float v = get(key);
/* 403 */     if (v != defaultReturnValue() || containsKey(key)) return v; 
/* 404 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(key));
/* 405 */     put(key, newValue);
/* 406 */     return newValue;
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
/*     */   default float computeIfAbsentNullable(byte key, IntFunction<? extends Float> mappingFunction) {
/* 429 */     Objects.requireNonNull(mappingFunction);
/* 430 */     float v = get(key), drv = defaultReturnValue();
/* 431 */     if (v != drv || containsKey(key)) return v; 
/* 432 */     Float mappedValue = mappingFunction.apply(key);
/* 433 */     if (mappedValue == null) return drv; 
/* 434 */     float newValue = mappedValue.floatValue();
/* 435 */     put(key, newValue);
/* 436 */     return newValue;
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
/*     */   default float computeIfAbsent(byte key, Byte2FloatFunction mappingFunction) {
/* 465 */     Objects.requireNonNull(mappingFunction);
/* 466 */     float v = get(key), drv = defaultReturnValue();
/* 467 */     if (v != drv || containsKey(key)) return v; 
/* 468 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 469 */     float newValue = mappingFunction.get(key);
/* 470 */     put(key, newValue);
/* 471 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default float computeIfAbsentPartial(byte key, Byte2FloatFunction mappingFunction) {
/* 479 */     return computeIfAbsent(key, mappingFunction);
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
/*     */   default float computeIfPresent(byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
/* 499 */     Objects.requireNonNull(remappingFunction);
/* 500 */     float oldValue = get(key), drv = defaultReturnValue();
/* 501 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 502 */     Float newValue = remappingFunction.apply(Byte.valueOf(key), Float.valueOf(oldValue));
/* 503 */     if (newValue == null) {
/* 504 */       remove(key);
/* 505 */       return drv;
/*     */     } 
/* 507 */     float newVal = newValue.floatValue();
/* 508 */     put(key, newVal);
/* 509 */     return newVal;
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
/*     */   default float compute(byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
/* 531 */     Objects.requireNonNull(remappingFunction);
/* 532 */     float oldValue = get(key), drv = defaultReturnValue();
/* 533 */     boolean contained = (oldValue != drv || containsKey(key));
/* 534 */     Float newValue = remappingFunction.apply(Byte.valueOf(key), contained ? Float.valueOf(oldValue) : null);
/* 535 */     if (newValue == null) {
/* 536 */       if (contained) remove(key); 
/* 537 */       return drv;
/*     */     } 
/* 539 */     float newVal = newValue.floatValue();
/* 540 */     put(key, newVal);
/* 541 */     return newVal;
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
/*     */   default float merge(byte key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*     */     float newValue;
/* 564 */     Objects.requireNonNull(remappingFunction);
/*     */     
/* 566 */     float oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 568 */     if (oldValue != drv || containsKey(key)) {
/* 569 */       Float mergedValue = remappingFunction.apply(Float.valueOf(oldValue), Float.valueOf(value));
/* 570 */       if (mergedValue == null) {
/* 571 */         remove(key);
/* 572 */         return drv;
/*     */       } 
/* 574 */       newValue = mergedValue.floatValue();
/*     */     } else {
/* 576 */       newValue = value;
/*     */     } 
/* 578 */     put(key, newValue);
/* 579 */     return newValue;
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
/*     */   default float mergeFloat(byte key, float value, FloatBinaryOperator remappingFunction) {
/* 603 */     Objects.requireNonNull(remappingFunction);
/* 604 */     float oldValue = get(key), drv = defaultReturnValue();
/* 605 */     float newValue = (oldValue != drv || containsKey(key)) ? remappingFunction.apply(oldValue, value) : value;
/* 606 */     put(key, newValue);
/* 607 */     return newValue;
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
/*     */   default float mergeFloat(byte key, float value, DoubleBinaryOperator remappingFunction) {
/* 634 */     return mergeFloat(key, value, (remappingFunction instanceof FloatBinaryOperator) ? (FloatBinaryOperator)remappingFunction : ((x, y) -> SafeMath.safeDoubleToFloat(remappingFunction.applyAsDouble(x, y))));
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
/*     */   default Float putIfAbsent(Byte key, Float value) {
/* 647 */     return super.putIfAbsent(key, value);
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
/* 660 */     return super.remove(key, value);
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
/*     */   default boolean replace(Byte key, Float oldValue, Float newValue) {
/* 673 */     return super.replace(key, oldValue, newValue);
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
/*     */   default Float replace(Byte key, Float value) {
/* 686 */     return super.replace(key, value);
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
/*     */   default Float computeIfAbsent(Byte key, Function<? super Byte, ? extends Float> mappingFunction) {
/* 699 */     return super.computeIfAbsent(key, mappingFunction);
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
/*     */   default Float computeIfPresent(Byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
/* 712 */     return super.computeIfPresent(key, remappingFunction);
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
/*     */   default Float compute(Byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
/* 725 */     return super.compute(key, remappingFunction);
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
/*     */   default Float merge(Byte key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 738 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(float paramFloat);
/*     */   
/*     */   float defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry> byte2FloatEntrySet();
/*     */   
/*     */   ByteSet keySet();
/*     */   
/*     */   FloatCollection values();
/*     */   
/*     */   boolean containsKey(byte paramByte);
/*     */   
/*     */   boolean containsValue(float paramFloat);
/*     */   
/*     */   public static interface Entry
/*     */     extends Map.Entry<Byte, Float>
/*     */   {
/*     */     @Deprecated
/*     */     default Byte getKey() {
/* 763 */       return Byte.valueOf(getByteKey());
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
/*     */     default Float getValue() {
/* 788 */       return Float.valueOf(getFloatValue());
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
/* 799 */       return Float.valueOf(setValue(value.floatValue()));
/*     */     }
/*     */     
/*     */     byte getByteKey();
/*     */     
/*     */     float getFloatValue();
/*     */     
/*     */     float setValue(float param1Float);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2FloatMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */