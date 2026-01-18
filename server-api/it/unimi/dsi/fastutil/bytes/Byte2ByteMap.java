/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public interface Byte2ByteMap
/*     */   extends Byte2ByteFunction, Map<Byte, Byte>
/*     */ {
/*     */   public static interface FastEntrySet
/*     */     extends ObjectSet<Entry>
/*     */   {
/*     */     ObjectIterator<Byte2ByteMap.Entry> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Byte2ByteMap.Entry> consumer) {
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
/*     */   default ObjectSet<Map.Entry<Byte, Byte>> entrySet() {
/* 149 */     return (ObjectSet)byte2ByteEntrySet();
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
/*     */   default Byte put(Byte key, Byte value) {
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
/*     */   default Byte get(Object key) {
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
/*     */   default Byte remove(Object key) {
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
/* 253 */     return (value == null) ? false : containsValue(((Byte)value).byteValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEach(BiConsumer<? super Byte, ? super Byte> consumer) {
/* 259 */     ObjectSet<Entry> entrySet = byte2ByteEntrySet();
/* 260 */     Consumer<Entry> wrappingConsumer = entry -> consumer.accept(Byte.valueOf(entry.getByteKey()), Byte.valueOf(entry.getByteValue()));
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
/*     */   default byte getOrDefault(byte key, byte defaultValue) {
/*     */     byte v;
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
/*     */   default Byte getOrDefault(Object key, Byte defaultValue) {
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
/*     */   default byte putIfAbsent(byte key, byte value) {
/* 316 */     byte v = get(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(byte key, byte value) {
/* 334 */     byte curValue = get(key);
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
/*     */   default boolean replace(byte key, byte oldValue, byte newValue) {
/* 353 */     byte curValue = get(key);
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
/*     */   default byte replace(byte key, byte value) {
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
/*     */   default byte computeIfAbsent(byte key, IntUnaryOperator mappingFunction) {
/* 400 */     Objects.requireNonNull(mappingFunction);
/* 401 */     byte v = get(key);
/* 402 */     if (v != defaultReturnValue() || containsKey(key)) return v; 
/* 403 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(key));
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
/*     */   default byte computeIfAbsentNullable(byte key, IntFunction<? extends Byte> mappingFunction) {
/* 428 */     Objects.requireNonNull(mappingFunction);
/* 429 */     byte v = get(key), drv = defaultReturnValue();
/* 430 */     if (v != drv || containsKey(key)) return v; 
/* 431 */     Byte mappedValue = mappingFunction.apply(key);
/* 432 */     if (mappedValue == null) return drv; 
/* 433 */     byte newValue = mappedValue.byteValue();
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
/*     */   default byte computeIfAbsent(byte key, Byte2ByteFunction mappingFunction) {
/* 464 */     Objects.requireNonNull(mappingFunction);
/* 465 */     byte v = get(key), drv = defaultReturnValue();
/* 466 */     if (v != drv || containsKey(key)) return v; 
/* 467 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 468 */     byte newValue = mappingFunction.get(key);
/* 469 */     put(key, newValue);
/* 470 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default byte computeIfAbsentPartial(byte key, Byte2ByteFunction mappingFunction) {
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
/*     */   default byte computeIfPresent(byte key, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/* 498 */     Objects.requireNonNull(remappingFunction);
/* 499 */     byte oldValue = get(key), drv = defaultReturnValue();
/* 500 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 501 */     Byte newValue = remappingFunction.apply(Byte.valueOf(key), Byte.valueOf(oldValue));
/* 502 */     if (newValue == null) {
/* 503 */       remove(key);
/* 504 */       return drv;
/*     */     } 
/* 506 */     byte newVal = newValue.byteValue();
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
/*     */   default byte compute(byte key, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/* 530 */     Objects.requireNonNull(remappingFunction);
/* 531 */     byte oldValue = get(key), drv = defaultReturnValue();
/* 532 */     boolean contained = (oldValue != drv || containsKey(key));
/* 533 */     Byte newValue = remappingFunction.apply(Byte.valueOf(key), contained ? Byte.valueOf(oldValue) : null);
/* 534 */     if (newValue == null) {
/* 535 */       if (contained) remove(key); 
/* 536 */       return drv;
/*     */     } 
/* 538 */     byte newVal = newValue.byteValue();
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
/*     */   default byte merge(byte key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*     */     byte newValue;
/* 563 */     Objects.requireNonNull(remappingFunction);
/*     */     
/* 565 */     byte oldValue = get(key), drv = defaultReturnValue();
/*     */     
/* 567 */     if (oldValue != drv || containsKey(key)) {
/* 568 */       Byte mergedValue = remappingFunction.apply(Byte.valueOf(oldValue), Byte.valueOf(value));
/* 569 */       if (mergedValue == null) {
/* 570 */         remove(key);
/* 571 */         return drv;
/*     */       } 
/* 573 */       newValue = mergedValue.byteValue();
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
/*     */   default byte mergeByte(byte key, byte value, ByteBinaryOperator remappingFunction) {
/* 602 */     Objects.requireNonNull(remappingFunction);
/* 603 */     byte oldValue = get(key), drv = defaultReturnValue();
/* 604 */     byte newValue = (oldValue != drv || containsKey(key)) ? remappingFunction.apply(oldValue, value) : value;
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
/*     */   default byte mergeByte(byte key, byte value, IntBinaryOperator remappingFunction) {
/* 633 */     return mergeByte(key, value, (remappingFunction instanceof ByteBinaryOperator) ? (ByteBinaryOperator)remappingFunction : ((x, y) -> SafeMath.safeIntToByte(remappingFunction.applyAsInt(x, y))));
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
/*     */   default Byte putIfAbsent(Byte key, Byte value) {
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
/*     */   default boolean replace(Byte key, Byte oldValue, Byte newValue) {
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
/*     */   default Byte replace(Byte key, Byte value) {
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
/*     */   default Byte computeIfAbsent(Byte key, Function<? super Byte, ? extends Byte> mappingFunction) {
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
/*     */   default Byte computeIfPresent(Byte key, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
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
/*     */   default Byte compute(Byte key, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
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
/*     */   default Byte merge(Byte key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/* 737 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */   
/*     */   void defaultReturnValue(byte paramByte);
/*     */   
/*     */   byte defaultReturnValue();
/*     */   
/*     */   ObjectSet<Entry> byte2ByteEntrySet();
/*     */   
/*     */   ByteSet keySet();
/*     */   
/*     */   ByteCollection values();
/*     */   
/*     */   boolean containsKey(byte paramByte);
/*     */   
/*     */   boolean containsValue(byte paramByte);
/*     */   
/*     */   public static interface Entry
/*     */     extends Map.Entry<Byte, Byte>
/*     */   {
/*     */     @Deprecated
/*     */     default Byte getKey() {
/* 762 */       return Byte.valueOf(getByteKey());
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
/* 787 */       return Byte.valueOf(getByteValue());
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
/* 798 */       return Byte.valueOf(setValue(value.byteValue()));
/*     */     }
/*     */     
/*     */     byte getByteKey();
/*     */     
/*     */     byte getByteValue();
/*     */     
/*     */     byte setValue(byte param1Byte);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2ByteMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */