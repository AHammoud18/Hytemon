/*     */ package it.unimi.dsi.fastutil.objects;
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
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2CharFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2LongFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2LongFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ObjectFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToLongFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Object2LongFunction<K>
/*     */   extends Function<K, Long>, ToLongFunction<K>
/*     */ {
/*     */   default long applyAsLong(K operand) {
/*  60 */     return getLong(operand);
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
/*     */   default long put(K key, long value) {
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
/*     */   default long getOrDefault(Object key, long defaultValue) {
/*     */     long v;
/*  99 */     return ((v = getLong(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default long removeLong(Object key) {
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
/*     */   default Long put(K key, Long value) {
/* 122 */     K k = key;
/* 123 */     boolean containsKey = containsKey(k);
/* 124 */     long v = put(k, value.longValue());
/* 125 */     return containsKey ? Long.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long get(Object key) {
/* 136 */     Object k = key; long v; return ((
/*     */       
/* 138 */       v = getLong(k)) != defaultReturnValue() || containsKey(k)) ? Long.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long getOrDefault(Object key, Long defaultValue) {
/* 149 */     Object k = key;
/* 150 */     long v = getLong(k);
/* 151 */     return (v != defaultReturnValue() || containsKey(k)) ? Long.valueOf(v) : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Long remove(Object key) {
/* 162 */     Object k = key;
/* 163 */     return containsKey(k) ? Long.valueOf(removeLong(k)) : null;
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
/*     */   default void defaultReturnValue(long rv) {
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
/*     */   default long defaultReturnValue() {
/* 190 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<K, T> andThen(Function<? super Long, ? extends T> after) {
/* 201 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Object2ByteFunction<K> andThenByte(Long2ByteFunction after) {
/* 205 */     return k -> after.get(getLong(k));
/*     */   }
/*     */   
/*     */   default Byte2LongFunction composeByte(Byte2ObjectFunction<K> before) {
/* 209 */     return k -> getLong(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2ShortFunction<K> andThenShort(Long2ShortFunction after) {
/* 213 */     return k -> after.get(getLong(k));
/*     */   }
/*     */   
/*     */   default Short2LongFunction composeShort(Short2ObjectFunction<K> before) {
/* 217 */     return k -> getLong(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2IntFunction<K> andThenInt(Long2IntFunction after) {
/* 221 */     return k -> after.get(getLong(k));
/*     */   }
/*     */   
/*     */   default Int2LongFunction composeInt(Int2ObjectFunction<K> before) {
/* 225 */     return k -> getLong(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2LongFunction<K> andThenLong(Long2LongFunction after) {
/* 229 */     return k -> after.get(getLong(k));
/*     */   }
/*     */   
/*     */   default Long2LongFunction composeLong(Long2ObjectFunction<K> before) {
/* 233 */     return k -> getLong(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2CharFunction<K> andThenChar(Long2CharFunction after) {
/* 237 */     return k -> after.get(getLong(k));
/*     */   }
/*     */   
/*     */   default Char2LongFunction composeChar(Char2ObjectFunction<K> before) {
/* 241 */     return k -> getLong(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2FloatFunction<K> andThenFloat(Long2FloatFunction after) {
/* 245 */     return k -> after.get(getLong(k));
/*     */   }
/*     */   
/*     */   default Float2LongFunction composeFloat(Float2ObjectFunction<K> before) {
/* 249 */     return k -> getLong(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2DoubleFunction<K> andThenDouble(Long2DoubleFunction after) {
/* 253 */     return k -> after.get(getLong(k));
/*     */   }
/*     */   
/*     */   default Double2LongFunction composeDouble(Double2ObjectFunction<K> before) {
/* 257 */     return k -> getLong(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ObjectFunction<K, T> andThenObject(Long2ObjectFunction<? extends T> after) {
/* 261 */     return k -> after.get(getLong(k));
/*     */   }
/*     */   
/*     */   default <T> Object2LongFunction<T> composeObject(Object2ObjectFunction<? super T, ? extends K> before) {
/* 265 */     return k -> getLong(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ReferenceFunction<K, T> andThenReference(Long2ReferenceFunction<? extends T> after) {
/* 269 */     return k -> after.get(getLong(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2LongFunction<T> composeReference(Reference2ObjectFunction<? super T, ? extends K> before) {
/* 273 */     return k -> getLong(before.get(k));
/*     */   }
/*     */   
/*     */   long getLong(Object paramObject);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2LongFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */