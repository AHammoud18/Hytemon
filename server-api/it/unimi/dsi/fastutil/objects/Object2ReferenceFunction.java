/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ReferenceFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @FunctionalInterface
/*     */ public interface Object2ReferenceFunction<K, V>
/*     */   extends Function<K, V>
/*     */ {
/*     */   default V put(K key, V value) {
/*  63 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default V getOrDefault(Object key, V defaultValue) {
/*     */     V v;
/*  89 */     return ((v = get(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default V remove(Object key) {
/* 101 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void defaultReturnValue(V rv) {
/* 115 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default V defaultReturnValue() {
/* 128 */     return null;
/*     */   }
/*     */   
/*     */   default Object2ByteFunction<K> andThenByte(Reference2ByteFunction<V> after) {
/* 132 */     return k -> after.getByte(get(k));
/*     */   }
/*     */   
/*     */   default Byte2ReferenceFunction<V> composeByte(Byte2ObjectFunction<K> before) {
/* 136 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2ShortFunction<K> andThenShort(Reference2ShortFunction<V> after) {
/* 140 */     return k -> after.getShort(get(k));
/*     */   }
/*     */   
/*     */   default Short2ReferenceFunction<V> composeShort(Short2ObjectFunction<K> before) {
/* 144 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2IntFunction<K> andThenInt(Reference2IntFunction<V> after) {
/* 148 */     return k -> after.getInt(get(k));
/*     */   }
/*     */   
/*     */   default Int2ReferenceFunction<V> composeInt(Int2ObjectFunction<K> before) {
/* 152 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2LongFunction<K> andThenLong(Reference2LongFunction<V> after) {
/* 156 */     return k -> after.getLong(get(k));
/*     */   }
/*     */   
/*     */   default Long2ReferenceFunction<V> composeLong(Long2ObjectFunction<K> before) {
/* 160 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2CharFunction<K> andThenChar(Reference2CharFunction<V> after) {
/* 164 */     return k -> after.getChar(get(k));
/*     */   }
/*     */   
/*     */   default Char2ReferenceFunction<V> composeChar(Char2ObjectFunction<K> before) {
/* 168 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2FloatFunction<K> andThenFloat(Reference2FloatFunction<V> after) {
/* 172 */     return k -> after.getFloat(get(k));
/*     */   }
/*     */   
/*     */   default Float2ReferenceFunction<V> composeFloat(Float2ObjectFunction<K> before) {
/* 176 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2DoubleFunction<K> andThenDouble(Reference2DoubleFunction<V> after) {
/* 180 */     return k -> after.getDouble(get(k));
/*     */   }
/*     */   
/*     */   default Double2ReferenceFunction<V> composeDouble(Double2ObjectFunction<K> before) {
/* 184 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ObjectFunction<K, T> andThenObject(Reference2ObjectFunction<? super V, ? extends T> after) {
/* 188 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ReferenceFunction<T, V> composeObject(Object2ObjectFunction<? super T, ? extends K> before) {
/* 192 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ReferenceFunction<K, T> andThenReference(Reference2ReferenceFunction<? super V, ? extends T> after) {
/* 196 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ReferenceFunction<T, V> composeReference(Reference2ObjectFunction<? super T, ? extends K> before) {
/* 200 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   V get(Object paramObject);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */