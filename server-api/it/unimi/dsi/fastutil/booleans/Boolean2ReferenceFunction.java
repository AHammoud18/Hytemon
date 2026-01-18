/*     */ package it.unimi.dsi.fastutil.booleans;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2CharFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2IntFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2LongFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ReferenceFunction;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Boolean2ReferenceFunction<V>
/*     */   extends Function<Boolean, V>
/*     */ {
/*     */   default V put(boolean key, V value) {
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
/*     */   default V getOrDefault(boolean key, V defaultValue) {
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
/*     */   default V remove(boolean key) {
/* 101 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default V put(Boolean key, V value) {
/* 112 */     boolean k = key.booleanValue();
/* 113 */     boolean containsKey = containsKey(k);
/* 114 */     V v = put(k, value);
/* 115 */     return containsKey ? v : null;
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
/* 126 */     if (key == null) return null; 
/* 127 */     boolean k = ((Boolean)key).booleanValue(); V v; return ((
/*     */       
/* 129 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? v : null;
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
/* 140 */     if (key == null) return defaultValue; 
/* 141 */     boolean k = ((Boolean)key).booleanValue();
/* 142 */     V v = get(k);
/* 143 */     return (v != defaultReturnValue() || containsKey(k)) ? v : defaultValue;
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
/* 154 */     if (key == null) return null; 
/* 155 */     boolean k = ((Boolean)key).booleanValue();
/* 156 */     return containsKey(k) ? remove(k) : null;
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
/*     */   default boolean containsKey(boolean key) {
/* 171 */     return true;
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
/* 182 */     return (key == null) ? false : containsKey(((Boolean)key).booleanValue());
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
/* 196 */     throw new UnsupportedOperationException();
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
/* 209 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, V> compose(Function<? super T, ? extends Boolean> before) {
/* 220 */     return super.compose(before);
/*     */   }
/*     */   
/*     */   default Boolean2ByteFunction andThenByte(Reference2ByteFunction<V> after) {
/* 224 */     return k -> after.getByte(get(k));
/*     */   }
/*     */   
/*     */   default Byte2ReferenceFunction<V> composeByte(Byte2BooleanFunction before) {
/* 228 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2ShortFunction andThenShort(Reference2ShortFunction<V> after) {
/* 232 */     return k -> after.getShort(get(k));
/*     */   }
/*     */   
/*     */   default Short2ReferenceFunction<V> composeShort(Short2BooleanFunction before) {
/* 236 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2IntFunction andThenInt(Reference2IntFunction<V> after) {
/* 240 */     return k -> after.getInt(get(k));
/*     */   }
/*     */   
/*     */   default Int2ReferenceFunction<V> composeInt(Int2BooleanFunction before) {
/* 244 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2LongFunction andThenLong(Reference2LongFunction<V> after) {
/* 248 */     return k -> after.getLong(get(k));
/*     */   }
/*     */   
/*     */   default Long2ReferenceFunction<V> composeLong(Long2BooleanFunction before) {
/* 252 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2CharFunction andThenChar(Reference2CharFunction<V> after) {
/* 256 */     return k -> after.getChar(get(k));
/*     */   }
/*     */   
/*     */   default Char2ReferenceFunction<V> composeChar(Char2BooleanFunction before) {
/* 260 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2FloatFunction andThenFloat(Reference2FloatFunction<V> after) {
/* 264 */     return k -> after.getFloat(get(k));
/*     */   }
/*     */   
/*     */   default Float2ReferenceFunction<V> composeFloat(Float2BooleanFunction before) {
/* 268 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2DoubleFunction andThenDouble(Reference2DoubleFunction<V> after) {
/* 272 */     return k -> after.getDouble(get(k));
/*     */   }
/*     */   
/*     */   default Double2ReferenceFunction<V> composeDouble(Double2BooleanFunction before) {
/* 276 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Boolean2ObjectFunction<T> andThenObject(Reference2ObjectFunction<? super V, ? extends T> after) {
/* 280 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ReferenceFunction<T, V> composeObject(Object2BooleanFunction<? super T> before) {
/* 284 */     return k -> get(before.getBoolean(k));
/*     */   }
/*     */   
/*     */   default <T> Boolean2ReferenceFunction<T> andThenReference(Reference2ReferenceFunction<? super V, ? extends T> after) {
/* 288 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ReferenceFunction<T, V> composeReference(Reference2BooleanFunction<? super T> before) {
/* 292 */     return k -> get(before.getBoolean(k));
/*     */   }
/*     */   
/*     */   V get(boolean paramBoolean);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\Boolean2ReferenceFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */