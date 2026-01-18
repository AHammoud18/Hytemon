/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2LongFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2LongFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2LongFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2LongFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2LongFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2CharFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2LongFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2LongFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ObjectFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.LongFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Long2ObjectFunction<V>
/*     */   extends Function<Long, V>, LongFunction<V>
/*     */ {
/*     */   default V apply(long operand) {
/*  60 */     return get(operand);
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
/*     */   default V put(long key, V value) {
/*  73 */     throw new UnsupportedOperationException();
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
/*     */   default V getOrDefault(long key, V defaultValue) {
/*     */     V v;
/*  99 */     return ((v = get(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default V remove(long key) {
/* 111 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default V put(Long key, V value) {
/* 122 */     long k = key.longValue();
/* 123 */     boolean containsKey = containsKey(k);
/* 124 */     V v = put(k, value);
/* 125 */     return containsKey ? v : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default V get(Object key) {
/* 136 */     if (key == null) return null; 
/* 137 */     long k = ((Long)key).longValue(); V v; return ((
/*     */       
/* 139 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? v : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default V getOrDefault(Object key, V defaultValue) {
/* 150 */     if (key == null) return defaultValue; 
/* 151 */     long k = ((Long)key).longValue();
/* 152 */     V v = get(k);
/* 153 */     return (v != defaultReturnValue() || containsKey(k)) ? v : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default V remove(Object key) {
/* 164 */     if (key == null) return null; 
/* 165 */     long k = ((Long)key).longValue();
/* 166 */     return containsKey(k) ? remove(k) : null;
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
/*     */   default boolean containsKey(long key) {
/* 181 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean containsKey(Object key) {
/* 192 */     return (key == null) ? false : containsKey(((Long)key).longValue());
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
/* 206 */     throw new UnsupportedOperationException();
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
/* 219 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, V> compose(Function<? super T, ? extends Long> before) {
/* 230 */     return super.compose(before);
/*     */   }
/*     */   
/*     */   default Long2ByteFunction andThenByte(Object2ByteFunction<V> after) {
/* 234 */     return k -> after.getByte(get(k));
/*     */   }
/*     */   
/*     */   default Byte2ObjectFunction<V> composeByte(Byte2LongFunction before) {
/* 238 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2ShortFunction andThenShort(Object2ShortFunction<V> after) {
/* 242 */     return k -> after.getShort(get(k));
/*     */   }
/*     */   
/*     */   default Short2ObjectFunction<V> composeShort(Short2LongFunction before) {
/* 246 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2IntFunction andThenInt(Object2IntFunction<V> after) {
/* 250 */     return k -> after.getInt(get(k));
/*     */   }
/*     */   
/*     */   default Int2ObjectFunction<V> composeInt(Int2LongFunction before) {
/* 254 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2LongFunction andThenLong(Object2LongFunction<V> after) {
/* 258 */     return k -> after.getLong(get(k));
/*     */   }
/*     */   
/*     */   default Long2ObjectFunction<V> composeLong(Long2LongFunction before) {
/* 262 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2CharFunction andThenChar(Object2CharFunction<V> after) {
/* 266 */     return k -> after.getChar(get(k));
/*     */   }
/*     */   
/*     */   default Char2ObjectFunction<V> composeChar(Char2LongFunction before) {
/* 270 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2FloatFunction andThenFloat(Object2FloatFunction<V> after) {
/* 274 */     return k -> after.getFloat(get(k));
/*     */   }
/*     */   
/*     */   default Float2ObjectFunction<V> composeFloat(Float2LongFunction before) {
/* 278 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2DoubleFunction andThenDouble(Object2DoubleFunction<V> after) {
/* 282 */     return k -> after.getDouble(get(k));
/*     */   }
/*     */   
/*     */   default Double2ObjectFunction<V> composeDouble(Double2LongFunction before) {
/* 286 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Long2ObjectFunction<T> andThenObject(Object2ObjectFunction<? super V, ? extends T> after) {
/* 290 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ObjectFunction<T, V> composeObject(Object2LongFunction<? super T> before) {
/* 294 */     return k -> get(before.getLong(k));
/*     */   }
/*     */   
/*     */   default <T> Long2ReferenceFunction<T> andThenReference(Object2ReferenceFunction<? super V, ? extends T> after) {
/* 298 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ObjectFunction<T, V> composeReference(Reference2LongFunction<? super T> before) {
/* 302 */     return k -> get(before.getLong(k));
/*     */   }
/*     */   
/*     */   V get(long paramLong);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2ObjectFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */