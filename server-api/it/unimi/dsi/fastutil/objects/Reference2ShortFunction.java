/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2CharFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2IntFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2LongFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ShortFunction;
/*     */ import java.util.function.Function;
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
/*     */ @FunctionalInterface
/*     */ public interface Reference2ShortFunction<K>
/*     */   extends Function<K, Short>, ToIntFunction<K>
/*     */ {
/*     */   default int applyAsInt(K operand) {
/*  60 */     return getShort(operand);
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
/*     */   default short put(K key, short value) {
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
/*     */   default short getOrDefault(Object key, short defaultValue) {
/*     */     short v;
/*  99 */     return ((v = getShort(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default short removeShort(Object key) {
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
/*     */   default Short put(K key, Short value) {
/* 122 */     K k = key;
/* 123 */     boolean containsKey = containsKey(k);
/* 124 */     short v = put(k, value.shortValue());
/* 125 */     return containsKey ? Short.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Short get(Object key) {
/* 136 */     Object k = key; short v; return ((
/*     */       
/* 138 */       v = getShort(k)) != defaultReturnValue() || containsKey(k)) ? Short.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Short getOrDefault(Object key, Short defaultValue) {
/* 149 */     Object k = key;
/* 150 */     short v = getShort(k);
/* 151 */     return (v != defaultReturnValue() || containsKey(k)) ? Short.valueOf(v) : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Short remove(Object key) {
/* 162 */     Object k = key;
/* 163 */     return containsKey(k) ? Short.valueOf(removeShort(k)) : null;
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
/*     */   default void defaultReturnValue(short rv) {
/* 177 */     throw new UnsupportedOperationException();
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
/*     */   default short defaultReturnValue() {
/* 190 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<K, T> andThen(Function<? super Short, ? extends T> after) {
/* 201 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Reference2ByteFunction<K> andThenByte(Short2ByteFunction after) {
/* 205 */     return k -> after.get(getShort(k));
/*     */   }
/*     */   
/*     */   default Byte2ShortFunction composeByte(Byte2ReferenceFunction<K> before) {
/* 209 */     return k -> getShort(before.get(k));
/*     */   }
/*     */   
/*     */   default Reference2ShortFunction<K> andThenShort(Short2ShortFunction after) {
/* 213 */     return k -> after.get(getShort(k));
/*     */   }
/*     */   
/*     */   default Short2ShortFunction composeShort(Short2ReferenceFunction<K> before) {
/* 217 */     return k -> getShort(before.get(k));
/*     */   }
/*     */   
/*     */   default Reference2IntFunction<K> andThenInt(Short2IntFunction after) {
/* 221 */     return k -> after.get(getShort(k));
/*     */   }
/*     */   
/*     */   default Int2ShortFunction composeInt(Int2ReferenceFunction<K> before) {
/* 225 */     return k -> getShort(before.get(k));
/*     */   }
/*     */   
/*     */   default Reference2LongFunction<K> andThenLong(Short2LongFunction after) {
/* 229 */     return k -> after.get(getShort(k));
/*     */   }
/*     */   
/*     */   default Long2ShortFunction composeLong(Long2ReferenceFunction<K> before) {
/* 233 */     return k -> getShort(before.get(k));
/*     */   }
/*     */   
/*     */   default Reference2CharFunction<K> andThenChar(Short2CharFunction after) {
/* 237 */     return k -> after.get(getShort(k));
/*     */   }
/*     */   
/*     */   default Char2ShortFunction composeChar(Char2ReferenceFunction<K> before) {
/* 241 */     return k -> getShort(before.get(k));
/*     */   }
/*     */   
/*     */   default Reference2FloatFunction<K> andThenFloat(Short2FloatFunction after) {
/* 245 */     return k -> after.get(getShort(k));
/*     */   }
/*     */   
/*     */   default Float2ShortFunction composeFloat(Float2ReferenceFunction<K> before) {
/* 249 */     return k -> getShort(before.get(k));
/*     */   }
/*     */   
/*     */   default Reference2DoubleFunction<K> andThenDouble(Short2DoubleFunction after) {
/* 253 */     return k -> after.get(getShort(k));
/*     */   }
/*     */   
/*     */   default Double2ShortFunction composeDouble(Double2ReferenceFunction<K> before) {
/* 257 */     return k -> getShort(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ObjectFunction<K, T> andThenObject(Short2ObjectFunction<? extends T> after) {
/* 261 */     return k -> after.get(getShort(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ShortFunction<T> composeObject(Object2ReferenceFunction<? super T, ? extends K> before) {
/* 265 */     return k -> getShort(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ReferenceFunction<K, T> andThenReference(Short2ReferenceFunction<? extends T> after) {
/* 269 */     return k -> after.get(getShort(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ShortFunction<T> composeReference(Reference2ReferenceFunction<? super T, ? extends K> before) {
/* 273 */     return k -> getShort(before.get(k));
/*     */   }
/*     */   
/*     */   short getShort(Object paramObject);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2ShortFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */