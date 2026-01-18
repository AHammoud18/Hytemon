/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteBinaryOperator;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
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
/*     */ public interface Object2ByteMap<K>
/*     */   extends Object2ByteFunction<K>, Map<K, Byte>
/*     */ {
/*     */   public static interface FastEntrySet<K>
/*     */     extends ObjectSet<Entry<K>>
/*     */   {
/*     */     ObjectIterator<Object2ByteMap.Entry<K>> fastIterator();
/*     */     
/*     */     default void fastForEach(Consumer<? super Object2ByteMap.Entry<K>> consumer) {
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
/*     */   default ObjectSet<Map.Entry<K, Byte>> entrySet() {
/* 148 */     return (ObjectSet)object2ByteEntrySet();
/*     */   }
/*     */ 
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
/*     */   default Byte put(K key, Byte value) {
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
/*     */   default Byte get(Object key) {
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
/*     */   default Byte remove(Object key) {
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
/* 238 */     return (value == null) ? false : containsValue(((Byte)value).byteValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEach(BiConsumer<? super K, ? super Byte> consumer) {
/* 244 */     ObjectSet<Entry<K>> entrySet = object2ByteEntrySet();
/* 245 */     Consumer<Entry<K>> wrappingConsumer = entry -> consumer.accept(entry.getKey(), Byte.valueOf(entry.getByteValue()));
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
/*     */   default byte getOrDefault(Object key, byte defaultValue) {
/*     */     byte v;
/* 269 */     return ((v = getByte(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
/*     */   }
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
/*     */   default byte putIfAbsent(K key, byte value) {
/* 301 */     byte v = getByte(key), drv = defaultReturnValue();
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
/*     */   default boolean remove(Object key, byte value) {
/* 319 */     byte curValue = getByte(key);
/* 320 */     if (curValue != value || (curValue == defaultReturnValue() && !containsKey(key))) return false; 
/* 321 */     removeByte(key);
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
/*     */   default boolean replace(K key, byte oldValue, byte newValue) {
/* 338 */     byte curValue = getByte(key);
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
/*     */   default byte replace(K key, byte value) {
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
/*     */   default byte computeIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 385 */     Objects.requireNonNull(mappingFunction);
/* 386 */     byte v = getByte(key);
/* 387 */     if (v != defaultReturnValue() || containsKey(key)) return v; 
/* 388 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(key));
/* 389 */     put(key, newValue);
/* 390 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default byte computeByteIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
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
/*     */   default byte computeIfAbsent(K key, Object2ByteFunction<? super K> mappingFunction) {
/* 427 */     Objects.requireNonNull(mappingFunction);
/* 428 */     byte v = getByte(key), drv = defaultReturnValue();
/* 429 */     if (v != drv || containsKey(key)) return v; 
/* 430 */     if (!mappingFunction.containsKey(key)) return drv; 
/* 431 */     byte newValue = mappingFunction.getByte(key);
/* 432 */     put(key, newValue);
/* 433 */     return newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default byte computeByteIfAbsentPartial(K key, Object2ByteFunction<? super K> mappingFunction) {
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
/*     */   default byte computeByteIfPresent(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 461 */     Objects.requireNonNull(remappingFunction);
/* 462 */     byte oldValue = getByte(key), drv = defaultReturnValue();
/* 463 */     if (oldValue == drv && !containsKey(key)) return drv; 
/* 464 */     Byte newValue = remappingFunction.apply(key, Byte.valueOf(oldValue));
/* 465 */     if (newValue == null) {
/* 466 */       removeByte(key);
/* 467 */       return drv;
/*     */     } 
/* 469 */     byte newVal = newValue.byteValue();
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
/*     */   default byte computeByte(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 493 */     Objects.requireNonNull(remappingFunction);
/* 494 */     byte oldValue = getByte(key), drv = defaultReturnValue();
/* 495 */     boolean contained = (oldValue != drv || containsKey(key));
/* 496 */     Byte newValue = remappingFunction.apply(key, contained ? Byte.valueOf(oldValue) : null);
/* 497 */     if (newValue == null) {
/* 498 */       if (contained) removeByte(key); 
/* 499 */       return drv;
/*     */     } 
/* 501 */     byte newVal = newValue.byteValue();
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
/*     */   default byte merge(K key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*     */     byte newValue;
/* 526 */     Objects.requireNonNull(remappingFunction);
/*     */     
/* 528 */     byte oldValue = getByte(key), drv = defaultReturnValue();
/*     */     
/* 530 */     if (oldValue != drv || containsKey(key)) {
/* 531 */       Byte mergedValue = remappingFunction.apply(Byte.valueOf(oldValue), Byte.valueOf(value));
/* 532 */       if (mergedValue == null) {
/* 533 */         removeByte(key);
/* 534 */         return drv;
/*     */       } 
/* 536 */       newValue = mergedValue.byteValue();
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
/*     */   default byte mergeByte(K key, byte value, ByteBinaryOperator remappingFunction) {
/* 565 */     Objects.requireNonNull(remappingFunction);
/* 566 */     byte oldValue = getByte(key), drv = defaultReturnValue();
/* 567 */     byte newValue = (oldValue != drv || containsKey(key)) ? remappingFunction.apply(oldValue, value) : value;
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
/*     */   default byte mergeByte(K key, byte value, IntBinaryOperator remappingFunction) {
/* 596 */     return mergeByte(key, value, (remappingFunction instanceof ByteBinaryOperator) ? (ByteBinaryOperator)remappingFunction : ((x, y) -> SafeMath.safeIntToByte(remappingFunction.applyAsInt(x, y))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default byte mergeByte(K key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
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
/*     */   default Byte putIfAbsent(K key, Byte value) {
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
/*     */   default boolean replace(K key, Byte oldValue, Byte newValue) {
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
/*     */   default Byte replace(K key, Byte value) {
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
/*     */   default Byte merge(K key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/* 669 */     return super.merge(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */   
/*     */   void defaultReturnValue(byte paramByte);
/*     */ 
/*     */   
/*     */   byte defaultReturnValue();
/*     */ 
/*     */   
/*     */   ObjectSet<Entry<K>> object2ByteEntrySet();
/*     */ 
/*     */   
/*     */   ObjectSet<K> keySet();
/*     */ 
/*     */   
/*     */   ByteCollection values();
/*     */ 
/*     */   
/*     */   boolean containsKey(Object paramObject);
/*     */ 
/*     */   
/*     */   boolean containsValue(byte paramByte);
/*     */   
/*     */   public static interface Entry<K>
/*     */     extends Map.Entry<K, Byte>
/*     */   {
/*     */     @Deprecated
/*     */     default Byte getValue() {
/* 701 */       return Byte.valueOf(getByteValue());
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
/* 712 */       return Byte.valueOf(setValue(value.byteValue()));
/*     */     }
/*     */     
/*     */     byte getByteValue();
/*     */     
/*     */     byte setValue(byte param1Byte);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ByteMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */