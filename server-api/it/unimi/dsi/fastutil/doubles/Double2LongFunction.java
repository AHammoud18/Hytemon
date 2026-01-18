/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2LongFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2LongFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2LongFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2LongFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2CharFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2LongFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2LongFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2LongFunction;
/*     */ import java.util.function.DoubleToLongFunction;
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
/*     */ @FunctionalInterface
/*     */ public interface Double2LongFunction
/*     */   extends Function<Double, Long>, DoubleToLongFunction
/*     */ {
/*     */   default long applyAsLong(double operand) {
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
/*     */   default long put(double key, long value) {
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
/*     */   default long getOrDefault(double key, long defaultValue) {
/*     */     long v;
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
/*     */   default long remove(double key) {
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
/*     */   default Long put(Double key, Long value) {
/* 122 */     double k = key.doubleValue();
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
/* 136 */     if (key == null) return null; 
/* 137 */     double k = ((Double)key).doubleValue(); long v; return ((
/*     */       
/* 139 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Long.valueOf(v) : null;
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
/* 150 */     if (key == null) return defaultValue; 
/* 151 */     double k = ((Double)key).doubleValue();
/* 152 */     long v = get(k);
/* 153 */     return (v != defaultReturnValue() || containsKey(k)) ? Long.valueOf(v) : defaultValue;
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
/* 164 */     if (key == null) return null; 
/* 165 */     double k = ((Double)key).doubleValue();
/* 166 */     return containsKey(k) ? Long.valueOf(remove(k)) : null;
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
/*     */   default boolean containsKey(double key) {
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
/* 192 */     return (key == null) ? false : containsKey(((Double)key).doubleValue());
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
/*     */   default long defaultReturnValue() {
/* 219 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, Long> compose(Function<? super T, ? extends Double> before) {
/* 230 */     return super.compose(before);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<Double, T> andThen(Function<? super Long, ? extends T> after) {
/* 241 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Double2ByteFunction andThenByte(Long2ByteFunction after) {
/* 245 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2LongFunction composeByte(Byte2DoubleFunction before) {
/* 249 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Double2ShortFunction andThenShort(Long2ShortFunction after) {
/* 253 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2LongFunction composeShort(Short2DoubleFunction before) {
/* 257 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Double2IntFunction andThenInt(Long2IntFunction after) {
/* 261 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2LongFunction composeInt(Int2DoubleFunction before) {
/* 265 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Double2LongFunction andThenLong(Long2LongFunction after) {
/* 269 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2LongFunction composeLong(Long2DoubleFunction before) {
/* 273 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Double2CharFunction andThenChar(Long2CharFunction after) {
/* 277 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2LongFunction composeChar(Char2DoubleFunction before) {
/* 281 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Double2FloatFunction andThenFloat(Long2FloatFunction after) {
/* 285 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2LongFunction composeFloat(Float2DoubleFunction before) {
/* 289 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Double2DoubleFunction andThenDouble(Long2DoubleFunction after) {
/* 293 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2LongFunction composeDouble(Double2DoubleFunction before) {
/* 297 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Double2ObjectFunction<T> andThenObject(Long2ObjectFunction<? extends T> after) {
/* 301 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2LongFunction<T> composeObject(Object2DoubleFunction<? super T> before) {
/* 305 */     return k -> get(before.getDouble(k));
/*     */   }
/*     */   
/*     */   default <T> Double2ReferenceFunction<T> andThenReference(Long2ReferenceFunction<? extends T> after) {
/* 309 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2LongFunction<T> composeReference(Reference2DoubleFunction<? super T> before) {
/* 313 */     return k -> get(before.getDouble(k));
/*     */   }
/*     */   
/*     */   long get(double paramDouble);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2LongFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */