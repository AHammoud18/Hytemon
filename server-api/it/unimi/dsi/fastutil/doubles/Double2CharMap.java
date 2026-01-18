/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.chars.CharBinaryOperator;
/*     */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleFunction;
/*     */ import java.util.function.DoubleToIntFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntBinaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Double2CharMap
/*     */   extends Double2CharFunction, Map<Double, Character>
/*     */ {
/*     */   public static interface FastEntrySet
/*     */     extends ObjectSet<Entry>
/*     */   {
/*     */     ObjectIterator<Double2CharMap.Entry> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Double2CharMap.Entry> consumer) {
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
/*     */   default ObjectSet<Map.Entry<Double, Character>> entrySet() {
/* 150 */     return (ObjectSet)double2CharEntrySet();
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
/*     */   default Character put(Double key, Character value) {
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
/*     */   default Character get(Object key) {
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
/*     */   default Character remove(Object key) {
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
/* 254 */     return (value == null) ? false : containsValue(((Character)value).charValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEach(BiConsumer<? super Double, ? super Character> consumer) {
/* 260 */     ObjectSet<Entry> entrySet = double2CharEntrySet();
/* 261 */     Consumer<Entry> wrappingConsumer = entry -> consumer.accept(Double.valueOf(entry.getDoubleKey()), Character.valueOf(entry.getCharValue()));
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
/*     */   default char getOrDefault(double key, char defaultValue) {
/*     */     char v;
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
/*     */   default Character getOrDefault(Object key, Character defaultValue) {
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
/*     */   default char putIfAbsent(double key, char value) {
/* 317 */     char v = get(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(double key, char value) {
/* 335 */     char curValue = get(key);
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
/*     */   default boolean replace(double key, char oldValue, char newValue) {
/* 354 */     char curValue = get(key);
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
/*     */   default char replace(double key, char value) {
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
/*     */   default char computeIfAbsent(double key, DoubleToIntFunction mappingFunction) {
/* 401 */     Objects.requireNonNull(mappingFunction);
/* 402 */     char v = get(key);
/* 403 */     if (v != defaultReturnValue() || containsKey(key)) return v; 
/* 404 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(key));
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
/*     */   default char computeIfAbsentNullable(double key, DoubleFunction<? extends Character> mappingFunction) {
/* 429 */     Objects.requireNonNull(mappingFunction);
/* 430 */     char v = get(key), drv = defaultReturnValue();
/* 431 */     if (v != drv || containsKey(key)) return v; 
/* 432 */     Character mappedValue = mappingFunction.apply(key);
/* 433 */     if (mappedValue == null) return drv; 
/* 434 */     char newValue = mappedValue.charValue();
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
/*     */   default char computeIfAbsent(double key, Double2CharFunction mappingFunction) {
/* 465 */     Objects.requireNonNull(mappingFunction);
/* 466 */     char v = get(key), drv = defaultReturnValue();
/* 467 */     if (v != drv || containsKey(key)) return v; 
/* 468 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 469 */     char newValue = mappingFunction.get(key);
/* 470 */     put(key, newValue);
/* 471 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default char computeIfAbsentPartial(double key, Double2CharFunction mappingFunction) {
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
/*     */   default char computeIfPresent(double key, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
/* 499 */     Objects.requireNonNull(remappingFunction);
/* 500 */     char oldValue = get(key), drv = defaultReturnValue();
/* 501 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 502 */     Character newValue = remappingFunction.apply(Double.valueOf(key), Character.valueOf(oldValue));
/* 503 */     if (newValue == null) {
/* 504 */       remove(key);
/* 505 */       return drv;
/*     */     } 
/* 507 */     char newVal = newValue.charValue();
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
/*     */   default char compute(double key, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
/* 531 */     Objects.requireNonNull(remappingFunction);
/* 532 */     char oldValue = get(key), drv = defaultReturnValue();
/* 533 */     boolean contained = (oldValue != drv || containsKey(key));
/* 534 */     Character newValue = remappingFunction.apply(Double.valueOf(key), contained ? Character.valueOf(oldValue) : null);
/* 535 */     if (newValue == null) {
/* 536 */       if (contained) remove(key); 
/* 537 */       return drv;
/*     */     } 
/* 539 */     char newVal = newValue.charValue();
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
/*     */   default char merge(double key, char value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*     */     char newValue;
/* 564 */     Objects.requireNonNull(remappingFunction);
/*     */     
/* 566 */     char oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 568 */     if (oldValue != drv || containsKey(key)) {
/* 569 */       Character mergedValue = remappingFunction.apply(Character.valueOf(oldValue), Character.valueOf(value));
/* 570 */       if (mergedValue == null) {
/* 571 */         remove(key);
/* 572 */         return drv;
/*     */       } 
/* 574 */       newValue = mergedValue.charValue();
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
/*     */   default char mergeChar(double key, char value, CharBinaryOperator remappingFunction) {
/* 603 */     Objects.requireNonNull(remappingFunction);
/* 604 */     char oldValue = get(key), drv = defaultReturnValue();
/* 605 */     char newValue = (oldValue != drv || containsKey(key)) ? remappingFunction.apply(oldValue, value) : value;
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
/*     */   default char mergeChar(double key, char value, IntBinaryOperator remappingFunction) {
/* 634 */     return mergeChar(key, value, (remappingFunction instanceof CharBinaryOperator) ? (CharBinaryOperator)remappingFunction : ((x, y) -> SafeMath.safeIntToChar(remappingFunction.applyAsInt(x, y))));
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
/*     */   default Character putIfAbsent(Double key, Character value) {
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
/*     */   default boolean replace(Double key, Character oldValue, Character newValue) {
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
/*     */   default Character replace(Double key, Character value) {
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
/*     */   default Character computeIfAbsent(Double key, Function<? super Double, ? extends Character> mappingFunction) {
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
/*     */   default Character computeIfPresent(Double key, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
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
/*     */   default Character compute(Double key, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
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
/*     */   default Character merge(Double key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/* 738 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(char paramChar);
/*     */   
/*     */   char defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry> double2CharEntrySet();
/*     */   
/*     */   DoubleSet keySet();
/*     */   
/*     */   CharCollection values();
/*     */   
/*     */   boolean containsKey(double paramDouble);
/*     */   
/*     */   boolean containsValue(char paramChar);
/*     */   
/*     */   public static interface Entry
/*     */     extends Map.Entry<Double, Character>
/*     */   {
/*     */     @Deprecated
/*     */     default Double getKey() {
/* 763 */       return Double.valueOf(getDoubleKey());
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
/*     */     default Character getValue() {
/* 788 */       return Character.valueOf(getCharValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Character setValue(Character value) {
/* 799 */       return Character.valueOf(setValue(value.charValue()));
/*     */     }
/*     */     
/*     */     double getDoubleKey();
/*     */     
/*     */     char getCharValue();
/*     */     
/*     */     char setValue(char param1Char);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2CharMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */