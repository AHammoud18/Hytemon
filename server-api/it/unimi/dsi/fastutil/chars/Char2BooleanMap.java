/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
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
/*     */ import java.util.function.IntPredicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Char2BooleanMap
/*     */   extends Char2BooleanFunction, Map<Character, Boolean>
/*     */ {
/*     */   public static interface FastEntrySet
/*     */     extends ObjectSet<Entry>
/*     */   {
/*     */     ObjectIterator<Char2BooleanMap.Entry> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Char2BooleanMap.Entry> consumer) {
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
/*     */   default ObjectSet<Map.Entry<Character, Boolean>> entrySet() {
/* 150 */     return (ObjectSet)char2BooleanEntrySet();
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
/*     */   default Boolean put(Character key, Boolean value) {
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
/*     */   default Boolean get(Object key) {
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
/*     */   default Boolean remove(Object key) {
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
/* 254 */     return (value == null) ? false : containsValue(((Boolean)value).booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEach(BiConsumer<? super Character, ? super Boolean> consumer) {
/* 260 */     ObjectSet<Entry> entrySet = char2BooleanEntrySet();
/* 261 */     Consumer<Entry> wrappingConsumer = entry -> consumer.accept(Character.valueOf(entry.getCharKey()), Boolean.valueOf(entry.getBooleanValue()));
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
/*     */   default boolean getOrDefault(char key, boolean defaultValue) {
/*     */     boolean v;
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
/*     */   default Boolean getOrDefault(Object key, Boolean defaultValue) {
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
/*     */   default boolean putIfAbsent(char key, boolean value) {
/* 317 */     boolean v = get(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(char key, boolean value) {
/* 335 */     boolean curValue = get(key);
/* 336 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
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
/*     */   default boolean replace(char key, boolean oldValue, boolean newValue) {
/* 354 */     boolean curValue = get(key);
/* 355 */     if (curValue != oldValue || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
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
/*     */   default boolean replace(char key, boolean value) {
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
/*     */   default boolean computeIfAbsent(char key, IntPredicate mappingFunction) {
/* 401 */     Objects.requireNonNull(mappingFunction);
/* 402 */     boolean v = get(key);
/* 403 */     if (v != defaultReturnValue() || containsKey(key)) return v; 
/* 404 */     boolean newValue = mappingFunction.test(key);
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
/*     */   default boolean computeIfAbsentNullable(char key, IntFunction<? extends Boolean> mappingFunction) {
/* 429 */     Objects.requireNonNull(mappingFunction);
/* 430 */     boolean v = get(key), drv = defaultReturnValue();
/* 431 */     if (v != drv || containsKey(key)) return v; 
/* 432 */     Boolean mappedValue = mappingFunction.apply(key);
/* 433 */     if (mappedValue == null) return drv; 
/* 434 */     boolean newValue = mappedValue.booleanValue();
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
/*     */   default boolean computeIfAbsent(char key, Char2BooleanFunction mappingFunction) {
/* 465 */     Objects.requireNonNull(mappingFunction);
/* 466 */     boolean v = get(key), drv = defaultReturnValue();
/* 467 */     if (v != drv || containsKey(key)) return v; 
/* 468 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 469 */     boolean newValue = mappingFunction.get(key);
/* 470 */     put(key, newValue);
/* 471 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean computeIfAbsentPartial(char key, Char2BooleanFunction mappingFunction) {
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
/*     */   default boolean computeIfPresent(char key, BiFunction<? super Character, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 499 */     Objects.requireNonNull(remappingFunction);
/* 500 */     boolean oldValue = get(key), drv = defaultReturnValue();
/* 501 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 502 */     Boolean newValue = remappingFunction.apply(Character.valueOf(key), Boolean.valueOf(oldValue));
/* 503 */     if (newValue == null) {
/* 504 */       remove(key);
/* 505 */       return drv;
/*     */     } 
/* 507 */     boolean newVal = newValue.booleanValue();
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
/*     */   default boolean compute(char key, BiFunction<? super Character, ? super Boolean, ? extends Boolean> remappingFunction) {
/* 531 */     Objects.requireNonNull(remappingFunction);
/* 532 */     boolean oldValue = get(key), drv = defaultReturnValue();
/* 533 */     boolean contained = (oldValue != drv || containsKey(key));
/* 534 */     Boolean newValue = remappingFunction.apply(Character.valueOf(key), contained ? Boolean.valueOf(oldValue) : null);
/* 535 */     if (newValue == null) {
/* 536 */       if (contained) remove(key); 
/* 537 */       return drv;
/*     */     } 
/* 539 */     boolean newVal = newValue.booleanValue();
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
/*     */   default boolean merge(char key, boolean value, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*     */     boolean newValue;
/* 564 */     Objects.requireNonNull(remappingFunction);
/*     */     
/* 566 */     boolean oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 568 */     if (oldValue != drv || containsKey(key)) {
/* 569 */       Boolean mergedValue = remappingFunction.apply(Boolean.valueOf(oldValue), Boolean.valueOf(value));
/* 570 */       if (mergedValue == null) {
/* 571 */         remove(key);
/* 572 */         return drv;
/*     */       } 
/* 574 */       newValue = mergedValue.booleanValue();
/*     */     } else {
/* 576 */       newValue = value;
/*     */     } 
/* 578 */     put(key, newValue);
/* 579 */     return newValue;
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(boolean paramBoolean);
/*     */   
/*     */   boolean defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry> char2BooleanEntrySet();
/*     */   
/*     */   CharSet keySet();
/*     */   
/*     */   BooleanCollection values();
/*     */   
/*     */   boolean containsKey(char paramChar);
/*     */   
/*     */   boolean containsValue(boolean paramBoolean);
/*     */   
/*     */   public static interface Entry
/*     */     extends Map.Entry<Character, Boolean>
/*     */   {
/*     */     @Deprecated
/*     */     default Character getKey() {
/* 604 */       return Character.valueOf(getCharKey());
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
/*     */     default Boolean getValue() {
/* 629 */       return Boolean.valueOf(getBooleanValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Boolean setValue(Boolean value) {
/* 640 */       return Boolean.valueOf(setValue(value.booleanValue()));
/*     */     }
/*     */     
/*     */     char getCharKey();
/*     */     
/*     */     boolean getBooleanValue();
/*     */     
/*     */     boolean setValue(boolean param1Boolean);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2BooleanMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */