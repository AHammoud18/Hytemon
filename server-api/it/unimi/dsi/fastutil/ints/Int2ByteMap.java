/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteBinaryOperator;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
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
/*     */ public interface Int2ByteMap
/*     */   extends Int2ByteFunction, Map<Integer, Byte>
/*     */ {
/*     */   public static interface FastEntrySet
/*     */     extends ObjectSet<Entry>
/*     */   {
/*     */     ObjectIterator<Int2ByteMap.Entry> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Int2ByteMap.Entry> consumer) {
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
/*     */   default ObjectSet<Map.Entry<Integer, Byte>> entrySet() {
/* 150 */     return (ObjectSet)int2ByteEntrySet();
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
/*     */   default Byte put(Integer key, Byte value) {
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
/*     */   default Byte get(Object key) {
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
/*     */   default Byte remove(Object key) {
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
/* 254 */     return (value == null) ? false : containsValue(((Byte)value).byteValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEach(BiConsumer<? super Integer, ? super Byte> consumer) {
/* 260 */     ObjectSet<Entry> entrySet = int2ByteEntrySet();
/* 261 */     Consumer<Entry> wrappingConsumer = entry -> consumer.accept(Integer.valueOf(entry.getIntKey()), Byte.valueOf(entry.getByteValue()));
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
/*     */   default byte getOrDefault(int key, byte defaultValue) {
/*     */     byte v;
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
/*     */   default Byte getOrDefault(Object key, Byte defaultValue) {
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
/*     */   default byte putIfAbsent(int key, byte value) {
/* 317 */     byte v = get(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(int key, byte value) {
/* 335 */     byte curValue = get(key);
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
/*     */   default boolean replace(int key, byte oldValue, byte newValue) {
/* 354 */     byte curValue = get(key);
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
/*     */   default byte replace(int key, byte value) {
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
/*     */   default byte computeIfAbsent(int key, IntUnaryOperator mappingFunction) {
/* 401 */     Objects.requireNonNull(mappingFunction);
/* 402 */     byte v = get(key);
/* 403 */     if (v != defaultReturnValue() || containsKey(key)) return v; 
/* 404 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(key));
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
/*     */   default byte computeIfAbsentNullable(int key, IntFunction<? extends Byte> mappingFunction) {
/* 429 */     Objects.requireNonNull(mappingFunction);
/* 430 */     byte v = get(key), drv = defaultReturnValue();
/* 431 */     if (v != drv || containsKey(key)) return v; 
/* 432 */     Byte mappedValue = mappingFunction.apply(key);
/* 433 */     if (mappedValue == null) return drv; 
/* 434 */     byte newValue = mappedValue.byteValue();
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
/*     */   default byte computeIfAbsent(int key, Int2ByteFunction mappingFunction) {
/* 465 */     Objects.requireNonNull(mappingFunction);
/* 466 */     byte v = get(key), drv = defaultReturnValue();
/* 467 */     if (v != drv || containsKey(key)) return v; 
/* 468 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 469 */     byte newValue = mappingFunction.get(key);
/* 470 */     put(key, newValue);
/* 471 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default byte computeIfAbsentPartial(int key, Int2ByteFunction mappingFunction) {
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
/*     */   default byte computeIfPresent(int key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
/* 499 */     Objects.requireNonNull(remappingFunction);
/* 500 */     byte oldValue = get(key), drv = defaultReturnValue();
/* 501 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 502 */     Byte newValue = remappingFunction.apply(Integer.valueOf(key), Byte.valueOf(oldValue));
/* 503 */     if (newValue == null) {
/* 504 */       remove(key);
/* 505 */       return drv;
/*     */     } 
/* 507 */     byte newVal = newValue.byteValue();
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
/*     */   default byte compute(int key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
/* 531 */     Objects.requireNonNull(remappingFunction);
/* 532 */     byte oldValue = get(key), drv = defaultReturnValue();
/* 533 */     boolean contained = (oldValue != drv || containsKey(key));
/* 534 */     Byte newValue = remappingFunction.apply(Integer.valueOf(key), contained ? Byte.valueOf(oldValue) : null);
/* 535 */     if (newValue == null) {
/* 536 */       if (contained) remove(key); 
/* 537 */       return drv;
/*     */     } 
/* 539 */     byte newVal = newValue.byteValue();
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
/*     */   default byte merge(int key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*     */     byte newValue;
/* 564 */     Objects.requireNonNull(remappingFunction);
/*     */     
/* 566 */     byte oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 568 */     if (oldValue != drv || containsKey(key)) {
/* 569 */       Byte mergedValue = remappingFunction.apply(Byte.valueOf(oldValue), Byte.valueOf(value));
/* 570 */       if (mergedValue == null) {
/* 571 */         remove(key);
/* 572 */         return drv;
/*     */       } 
/* 574 */       newValue = mergedValue.byteValue();
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
/*     */   default byte mergeByte(int key, byte value, ByteBinaryOperator remappingFunction) {
/* 603 */     Objects.requireNonNull(remappingFunction);
/* 604 */     byte oldValue = get(key), drv = defaultReturnValue();
/* 605 */     byte newValue = (oldValue != drv || containsKey(key)) ? remappingFunction.apply(oldValue, value) : value;
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
/*     */   default byte mergeByte(int key, byte value, IntBinaryOperator remappingFunction) {
/* 634 */     return mergeByte(key, value, (remappingFunction instanceof ByteBinaryOperator) ? (ByteBinaryOperator)remappingFunction : ((x, y) -> SafeMath.safeIntToByte(remappingFunction.applyAsInt(x, y))));
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
/*     */   default Byte putIfAbsent(Integer key, Byte value) {
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
/*     */   default boolean replace(Integer key, Byte oldValue, Byte newValue) {
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
/*     */   default Byte replace(Integer key, Byte value) {
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
/*     */   default Byte computeIfAbsent(Integer key, Function<? super Integer, ? extends Byte> mappingFunction) {
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
/*     */   default Byte computeIfPresent(Integer key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
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
/*     */   default Byte compute(Integer key, BiFunction<? super Integer, ? super Byte, ? extends Byte> remappingFunction) {
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
/*     */   default Byte merge(Integer key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/* 738 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(byte paramByte);
/*     */   
/*     */   byte defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry> int2ByteEntrySet();
/*     */   
/*     */   IntSet keySet();
/*     */   
/*     */   ByteCollection values();
/*     */   
/*     */   boolean containsKey(int paramInt);
/*     */   
/*     */   boolean containsValue(byte paramByte);
/*     */   
/*     */   public static interface Entry
/*     */     extends Map.Entry<Integer, Byte>
/*     */   {
/*     */     @Deprecated
/*     */     default Integer getKey() {
/* 763 */       return Integer.valueOf(getIntKey());
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
/*     */     default Byte getValue() {
/* 788 */       return Byte.valueOf(getByteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     default Byte setValue(Byte value) {
/* 799 */       return Byte.valueOf(setValue(value.byteValue()));
/*     */     }
/*     */     
/*     */     int getIntKey();
/*     */     
/*     */     byte getByteValue();
/*     */     
/*     */     byte setValue(byte param1Byte);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\Int2ByteMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */