/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2CharFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2CharFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2CharFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2CharFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2CharFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2CharFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2CharFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2IntFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2LongFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2CharFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ReferenceFunction;
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
/*     */ public interface Char2ReferenceFunction<V>
/*     */   extends Function<Character, V>, IntFunction<V>
/*     */ {
/*     */   @Deprecated
/*     */   default V apply(int operand) {
/*  71 */     return get(SafeMath.safeIntToChar(operand));
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
/*     */   default V put(char key, V value) {
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
/*     */   default V getOrDefault(char key, V defaultValue) {
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
/*     */   default V remove(char key) {
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
/*     */   default V put(Character key, V value) {
/* 133 */     char k = key.charValue();
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
/* 148 */     char k = ((Character)key).charValue(); V v; return ((
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
/* 162 */     char k = ((Character)key).charValue();
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
/* 176 */     char k = ((Character)key).charValue();
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
/*     */   default boolean containsKey(char key) {
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
/* 203 */     return (key == null) ? false : containsKey(((Character)key).charValue());
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
/*     */   default <T> Function<T, V> compose(Function<? super T, ? extends Character> before) {
/* 241 */     return super.compose(before);
/*     */   }
/*     */   
/*     */   default Char2ByteFunction andThenByte(Reference2ByteFunction<V> after) {
/* 245 */     return k -> after.getByte(get(k));
/*     */   }
/*     */   
/*     */   default Byte2ReferenceFunction<V> composeByte(Byte2CharFunction before) {
/* 249 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2ShortFunction andThenShort(Reference2ShortFunction<V> after) {
/* 253 */     return k -> after.getShort(get(k));
/*     */   }
/*     */   
/*     */   default Short2ReferenceFunction<V> composeShort(Short2CharFunction before) {
/* 257 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2IntFunction andThenInt(Reference2IntFunction<V> after) {
/* 261 */     return k -> after.getInt(get(k));
/*     */   }
/*     */   
/*     */   default Int2ReferenceFunction<V> composeInt(Int2CharFunction before) {
/* 265 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2LongFunction andThenLong(Reference2LongFunction<V> after) {
/* 269 */     return k -> after.getLong(get(k));
/*     */   }
/*     */   
/*     */   default Long2ReferenceFunction<V> composeLong(Long2CharFunction before) {
/* 273 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2CharFunction andThenChar(Reference2CharFunction<V> after) {
/* 277 */     return k -> after.getChar(get(k));
/*     */   }
/*     */   
/*     */   default Char2ReferenceFunction<V> composeChar(Char2CharFunction before) {
/* 281 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2FloatFunction andThenFloat(Reference2FloatFunction<V> after) {
/* 285 */     return k -> after.getFloat(get(k));
/*     */   }
/*     */   
/*     */   default Float2ReferenceFunction<V> composeFloat(Float2CharFunction before) {
/* 289 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2DoubleFunction andThenDouble(Reference2DoubleFunction<V> after) {
/* 293 */     return k -> after.getDouble(get(k));
/*     */   }
/*     */   
/*     */   default Double2ReferenceFunction<V> composeDouble(Double2CharFunction before) {
/* 297 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Char2ObjectFunction<T> andThenObject(Reference2ObjectFunction<? super V, ? extends T> after) {
/* 301 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ReferenceFunction<T, V> composeObject(Object2CharFunction<? super T> before) {
/* 305 */     return k -> get(before.getChar(k));
/*     */   }
/*     */   
/*     */   default <T> Char2ReferenceFunction<T> andThenReference(Reference2ReferenceFunction<? super V, ? extends T> after) {
/* 309 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ReferenceFunction<T, V> composeReference(Reference2CharFunction<? super T> before) {
/* 313 */     return k -> get(before.getChar(k));
/*     */   }
/*     */   
/*     */   V get(char paramChar);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2ReferenceFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */