/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2CharFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ShortFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Short2ObjectFunction<V>
/*     */   extends Function<Short, V>, IntFunction<V>
/*     */ {
/*     */   @Deprecated
/*     */   default V apply(int operand) {
/*  71 */     return get(SafeMath.safeIntToShort(operand));
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
/*     */   default V put(short key, V value) {
/*  84 */     throw new UnsupportedOperationException();
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
/*     */   default V getOrDefault(short key, V defaultValue) {
/*     */     V v;
/* 110 */     return ((v = get(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default V remove(short key) {
/* 122 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default V put(Short key, V value) {
/* 133 */     short k = key.shortValue();
/* 134 */     boolean containsKey = containsKey(k);
/* 135 */     V v = put(k, value);
/* 136 */     return containsKey ? v : null;
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
/* 147 */     if (key == null) return null; 
/* 148 */     short k = ((Short)key).shortValue(); V v; return ((
/*     */       
/* 150 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? v : null;
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
/* 161 */     if (key == null) return defaultValue; 
/* 162 */     short k = ((Short)key).shortValue();
/* 163 */     V v = get(k);
/* 164 */     return (v != defaultReturnValue() || containsKey(k)) ? v : defaultValue;
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
/* 175 */     if (key == null) return null; 
/* 176 */     short k = ((Short)key).shortValue();
/* 177 */     return containsKey(k) ? remove(k) : null;
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
/*     */   default boolean containsKey(short key) {
/* 192 */     return true;
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
/* 203 */     return (key == null) ? false : containsKey(((Short)key).shortValue());
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
/* 217 */     throw new UnsupportedOperationException();
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
/* 230 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, V> compose(Function<? super T, ? extends Short> before) {
/* 241 */     return super.compose(before);
/*     */   }
/*     */   
/*     */   default Short2ByteFunction andThenByte(Object2ByteFunction<V> after) {
/* 245 */     return k -> after.getByte(get(k));
/*     */   }
/*     */   
/*     */   default Byte2ObjectFunction<V> composeByte(Byte2ShortFunction before) {
/* 249 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Short2ShortFunction andThenShort(Object2ShortFunction<V> after) {
/* 253 */     return k -> after.getShort(get(k));
/*     */   }
/*     */   
/*     */   default Short2ObjectFunction<V> composeShort(Short2ShortFunction before) {
/* 257 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Short2IntFunction andThenInt(Object2IntFunction<V> after) {
/* 261 */     return k -> after.getInt(get(k));
/*     */   }
/*     */   
/*     */   default Int2ObjectFunction<V> composeInt(Int2ShortFunction before) {
/* 265 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Short2LongFunction andThenLong(Object2LongFunction<V> after) {
/* 269 */     return k -> after.getLong(get(k));
/*     */   }
/*     */   
/*     */   default Long2ObjectFunction<V> composeLong(Long2ShortFunction before) {
/* 273 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Short2CharFunction andThenChar(Object2CharFunction<V> after) {
/* 277 */     return k -> after.getChar(get(k));
/*     */   }
/*     */   
/*     */   default Char2ObjectFunction<V> composeChar(Char2ShortFunction before) {
/* 281 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Short2FloatFunction andThenFloat(Object2FloatFunction<V> after) {
/* 285 */     return k -> after.getFloat(get(k));
/*     */   }
/*     */   
/*     */   default Float2ObjectFunction<V> composeFloat(Float2ShortFunction before) {
/* 289 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Short2DoubleFunction andThenDouble(Object2DoubleFunction<V> after) {
/* 293 */     return k -> after.getDouble(get(k));
/*     */   }
/*     */   
/*     */   default Double2ObjectFunction<V> composeDouble(Double2ShortFunction before) {
/* 297 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Short2ObjectFunction<T> andThenObject(Object2ObjectFunction<? super V, ? extends T> after) {
/* 301 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ObjectFunction<T, V> composeObject(Object2ShortFunction<? super T> before) {
/* 305 */     return k -> get(before.getShort(k));
/*     */   }
/*     */   
/*     */   default <T> Short2ReferenceFunction<T> andThenReference(Object2ReferenceFunction<? super V, ? extends T> after) {
/* 309 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ObjectFunction<T, V> composeReference(Reference2ShortFunction<? super T> before) {
/* 313 */     return k -> get(before.getShort(k));
/*     */   }
/*     */   
/*     */   V get(short paramShort);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2ObjectFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */