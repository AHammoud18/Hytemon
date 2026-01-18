/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2IntFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2IntFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2IntFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2IntFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2CharFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2LongFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2IntFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ReferenceFunction;
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
/*     */ public interface Reference2IntFunction<K>
/*     */   extends Function<K, Integer>, ToIntFunction<K>
/*     */ {
/*     */   default int applyAsInt(K operand) {
/*  60 */     return getInt(operand);
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
/*     */   default int put(K key, int value) {
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
/*     */   default int getOrDefault(Object key, int defaultValue) {
/*     */     int v;
/*  99 */     return ((v = getInt(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default int removeInt(Object key) {
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
/*     */   default Integer put(K key, Integer value) {
/* 122 */     K k = key;
/* 123 */     boolean containsKey = containsKey(k);
/* 124 */     int v = put(k, value.intValue());
/* 125 */     return containsKey ? Integer.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Integer get(Object key) {
/* 136 */     Object k = key; int v; return ((
/*     */       
/* 138 */       v = getInt(k)) != defaultReturnValue() || containsKey(k)) ? Integer.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Integer getOrDefault(Object key, Integer defaultValue) {
/* 149 */     Object k = key;
/* 150 */     int v = getInt(k);
/* 151 */     return (v != defaultReturnValue() || containsKey(k)) ? Integer.valueOf(v) : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Integer remove(Object key) {
/* 162 */     Object k = key;
/* 163 */     return containsKey(k) ? Integer.valueOf(removeInt(k)) : null;
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
/*     */   default void defaultReturnValue(int rv) {
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
/*     */   default int defaultReturnValue() {
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
/*     */   default <T> Function<K, T> andThen(Function<? super Integer, ? extends T> after) {
/* 201 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Reference2ByteFunction<K> andThenByte(Int2ByteFunction after) {
/* 205 */     return k -> after.get(getInt(k));
/*     */   }
/*     */   
/*     */   default Byte2IntFunction composeByte(Byte2ReferenceFunction<K> before) {
/* 209 */     return k -> getInt(before.get(k));
/*     */   }
/*     */   
/*     */   default Reference2ShortFunction<K> andThenShort(Int2ShortFunction after) {
/* 213 */     return k -> after.get(getInt(k));
/*     */   }
/*     */   
/*     */   default Short2IntFunction composeShort(Short2ReferenceFunction<K> before) {
/* 217 */     return k -> getInt(before.get(k));
/*     */   }
/*     */   
/*     */   default Reference2IntFunction<K> andThenInt(Int2IntFunction after) {
/* 221 */     return k -> after.get(getInt(k));
/*     */   }
/*     */   
/*     */   default Int2IntFunction composeInt(Int2ReferenceFunction<K> before) {
/* 225 */     return k -> getInt(before.get(k));
/*     */   }
/*     */   
/*     */   default Reference2LongFunction<K> andThenLong(Int2LongFunction after) {
/* 229 */     return k -> after.get(getInt(k));
/*     */   }
/*     */   
/*     */   default Long2IntFunction composeLong(Long2ReferenceFunction<K> before) {
/* 233 */     return k -> getInt(before.get(k));
/*     */   }
/*     */   
/*     */   default Reference2CharFunction<K> andThenChar(Int2CharFunction after) {
/* 237 */     return k -> after.get(getInt(k));
/*     */   }
/*     */   
/*     */   default Char2IntFunction composeChar(Char2ReferenceFunction<K> before) {
/* 241 */     return k -> getInt(before.get(k));
/*     */   }
/*     */   
/*     */   default Reference2FloatFunction<K> andThenFloat(Int2FloatFunction after) {
/* 245 */     return k -> after.get(getInt(k));
/*     */   }
/*     */   
/*     */   default Float2IntFunction composeFloat(Float2ReferenceFunction<K> before) {
/* 249 */     return k -> getInt(before.get(k));
/*     */   }
/*     */   
/*     */   default Reference2DoubleFunction<K> andThenDouble(Int2DoubleFunction after) {
/* 253 */     return k -> after.get(getInt(k));
/*     */   }
/*     */   
/*     */   default Double2IntFunction composeDouble(Double2ReferenceFunction<K> before) {
/* 257 */     return k -> getInt(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ObjectFunction<K, T> andThenObject(Int2ObjectFunction<? extends T> after) {
/* 261 */     return k -> after.get(getInt(k));
/*     */   }
/*     */   
/*     */   default <T> Object2IntFunction<T> composeObject(Object2ReferenceFunction<? super T, ? extends K> before) {
/* 265 */     return k -> getInt(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ReferenceFunction<K, T> andThenReference(Int2ReferenceFunction<? extends T> after) {
/* 269 */     return k -> after.get(getInt(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2IntFunction<T> composeReference(Reference2ReferenceFunction<? super T, ? extends K> before) {
/* 273 */     return k -> getInt(before.get(k));
/*     */   }
/*     */   
/*     */   int getInt(Object paramObject);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2IntFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */