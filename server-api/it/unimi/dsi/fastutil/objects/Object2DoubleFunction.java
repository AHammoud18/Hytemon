/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2CharFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2IntFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2LongFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ObjectFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Object2DoubleFunction<K>
/*     */   extends Function<K, Double>, ToDoubleFunction<K>
/*     */ {
/*     */   default double applyAsDouble(K operand) {
/*  60 */     return getDouble(operand);
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
/*     */   default double put(K key, double value) {
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
/*     */   default double getOrDefault(Object key, double defaultValue) {
/*     */     double v;
/*  99 */     return ((v = getDouble(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default double removeDouble(Object key) {
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
/*     */   default Double put(K key, Double value) {
/* 122 */     K k = key;
/* 123 */     boolean containsKey = containsKey(k);
/* 124 */     double v = put(k, value.doubleValue());
/* 125 */     return containsKey ? Double.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Double get(Object key) {
/* 136 */     Object k = key; double v; return ((
/*     */       
/* 138 */       v = getDouble(k)) != defaultReturnValue() || containsKey(k)) ? Double.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Double getOrDefault(Object key, Double defaultValue) {
/* 149 */     Object k = key;
/* 150 */     double v = getDouble(k);
/* 151 */     return (v != defaultReturnValue() || containsKey(k)) ? Double.valueOf(v) : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Double remove(Object key) {
/* 162 */     Object k = key;
/* 163 */     return containsKey(k) ? Double.valueOf(removeDouble(k)) : null;
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
/*     */   default void defaultReturnValue(double rv) {
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
/*     */   default double defaultReturnValue() {
/* 190 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<K, T> andThen(Function<? super Double, ? extends T> after) {
/* 201 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Object2ByteFunction<K> andThenByte(Double2ByteFunction after) {
/* 205 */     return k -> after.get(getDouble(k));
/*     */   }
/*     */   
/*     */   default Byte2DoubleFunction composeByte(Byte2ObjectFunction<K> before) {
/* 209 */     return k -> getDouble(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2ShortFunction<K> andThenShort(Double2ShortFunction after) {
/* 213 */     return k -> after.get(getDouble(k));
/*     */   }
/*     */   
/*     */   default Short2DoubleFunction composeShort(Short2ObjectFunction<K> before) {
/* 217 */     return k -> getDouble(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2IntFunction<K> andThenInt(Double2IntFunction after) {
/* 221 */     return k -> after.get(getDouble(k));
/*     */   }
/*     */   
/*     */   default Int2DoubleFunction composeInt(Int2ObjectFunction<K> before) {
/* 225 */     return k -> getDouble(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2LongFunction<K> andThenLong(Double2LongFunction after) {
/* 229 */     return k -> after.get(getDouble(k));
/*     */   }
/*     */   
/*     */   default Long2DoubleFunction composeLong(Long2ObjectFunction<K> before) {
/* 233 */     return k -> getDouble(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2CharFunction<K> andThenChar(Double2CharFunction after) {
/* 237 */     return k -> after.get(getDouble(k));
/*     */   }
/*     */   
/*     */   default Char2DoubleFunction composeChar(Char2ObjectFunction<K> before) {
/* 241 */     return k -> getDouble(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2FloatFunction<K> andThenFloat(Double2FloatFunction after) {
/* 245 */     return k -> after.get(getDouble(k));
/*     */   }
/*     */   
/*     */   default Float2DoubleFunction composeFloat(Float2ObjectFunction<K> before) {
/* 249 */     return k -> getDouble(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2DoubleFunction<K> andThenDouble(Double2DoubleFunction after) {
/* 253 */     return k -> after.get(getDouble(k));
/*     */   }
/*     */   
/*     */   default Double2DoubleFunction composeDouble(Double2ObjectFunction<K> before) {
/* 257 */     return k -> getDouble(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ObjectFunction<K, T> andThenObject(Double2ObjectFunction<? extends T> after) {
/* 261 */     return k -> after.get(getDouble(k));
/*     */   }
/*     */   
/*     */   default <T> Object2DoubleFunction<T> composeObject(Object2ObjectFunction<? super T, ? extends K> before) {
/* 265 */     return k -> getDouble(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ReferenceFunction<K, T> andThenReference(Double2ReferenceFunction<? extends T> after) {
/* 269 */     return k -> after.get(getDouble(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2DoubleFunction<T> composeReference(Reference2ObjectFunction<? super T, ? extends K> before) {
/* 273 */     return k -> getDouble(before.get(k));
/*     */   }
/*     */   
/*     */   double getDouble(Object paramObject);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2DoubleFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */