/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2LongFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2LongFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2LongFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2LongFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2LongFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2LongFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2LongFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.LongUnaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Long2LongFunction
/*     */   extends Function<Long, Long>, LongUnaryOperator
/*     */ {
/*     */   default long applyAsLong(long operand) {
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
/*     */   default long put(long key, long value) {
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
/*     */   default long getOrDefault(long key, long defaultValue) {
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
/*     */   default long remove(long key) {
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
/*     */   default Long put(Long key, Long value) {
/* 122 */     long k = key.longValue();
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
/* 137 */     long k = ((Long)key).longValue(); long v; return ((
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
/* 151 */     long k = ((Long)key).longValue();
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
/* 165 */     long k = ((Long)key).longValue();
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
/*     */   static Long2LongFunction identity() {
/* 223 */     return k -> k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, Long> compose(Function<? super T, ? extends Long> before) {
/* 234 */     return super.compose(before);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<Long, T> andThen(Function<? super Long, ? extends T> after) {
/* 245 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Long2ByteFunction andThenByte(Long2ByteFunction after) {
/* 249 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2LongFunction composeByte(Byte2LongFunction before) {
/* 253 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2ShortFunction andThenShort(Long2ShortFunction after) {
/* 257 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2LongFunction composeShort(Short2LongFunction before) {
/* 261 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2IntFunction andThenInt(Long2IntFunction after) {
/* 265 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2LongFunction composeInt(Int2LongFunction before) {
/* 269 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2LongFunction andThenLong(Long2LongFunction after) {
/* 273 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2LongFunction composeLong(Long2LongFunction before) {
/* 277 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2CharFunction andThenChar(Long2CharFunction after) {
/* 281 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2LongFunction composeChar(Char2LongFunction before) {
/* 285 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2FloatFunction andThenFloat(Long2FloatFunction after) {
/* 289 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2LongFunction composeFloat(Float2LongFunction before) {
/* 293 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2DoubleFunction andThenDouble(Long2DoubleFunction after) {
/* 297 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2LongFunction composeDouble(Double2LongFunction before) {
/* 301 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Long2ObjectFunction<T> andThenObject(Long2ObjectFunction<? extends T> after) {
/* 305 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2LongFunction<T> composeObject(Object2LongFunction<? super T> before) {
/* 309 */     return k -> get(before.getLong(k));
/*     */   }
/*     */   
/*     */   default <T> Long2ReferenceFunction<T> andThenReference(Long2ReferenceFunction<? extends T> after) {
/* 313 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2LongFunction<T> composeReference(Reference2LongFunction<? super T> before) {
/* 317 */     return k -> get(before.getLong(k));
/*     */   }
/*     */   
/*     */   long get(long paramLong);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2LongFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */