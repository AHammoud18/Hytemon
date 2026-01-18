/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2LongFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2LongFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2LongFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatFunction;
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
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2LongFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2FloatFunction;
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
/*     */ public interface Float2LongFunction
/*     */   extends Function<Float, Long>, DoubleToLongFunction
/*     */ {
/*     */   @Deprecated
/*     */   default long applyAsLong(double operand) {
/*  71 */     return get(SafeMath.safeDoubleToFloat(operand));
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
/*     */   default long put(float key, long value) {
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
/*     */   default long getOrDefault(float key, long defaultValue) {
/*     */     long v;
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
/*     */   default long remove(float key) {
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
/*     */   default Long put(Float key, Long value) {
/* 133 */     float k = key.floatValue();
/* 134 */     boolean containsKey = containsKey(k);
/* 135 */     long v = put(k, value.longValue());
/* 136 */     return containsKey ? Long.valueOf(v) : null;
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
/* 147 */     if (key == null) return null; 
/* 148 */     float k = ((Float)key).floatValue(); long v; return ((
/*     */       
/* 150 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Long.valueOf(v) : null;
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
/* 161 */     if (key == null) return defaultValue; 
/* 162 */     float k = ((Float)key).floatValue();
/* 163 */     long v = get(k);
/* 164 */     return (v != defaultReturnValue() || containsKey(k)) ? Long.valueOf(v) : defaultValue;
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
/* 175 */     if (key == null) return null; 
/* 176 */     float k = ((Float)key).floatValue();
/* 177 */     return containsKey(k) ? Long.valueOf(remove(k)) : null;
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
/*     */   default boolean containsKey(float key) {
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
/* 203 */     return (key == null) ? false : containsKey(((Float)key).floatValue());
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
/*     */   default long defaultReturnValue() {
/* 230 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, Long> compose(Function<? super T, ? extends Float> before) {
/* 241 */     return super.compose(before);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<Float, T> andThen(Function<? super Long, ? extends T> after) {
/* 252 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Float2ByteFunction andThenByte(Long2ByteFunction after) {
/* 256 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2LongFunction composeByte(Byte2FloatFunction before) {
/* 260 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2ShortFunction andThenShort(Long2ShortFunction after) {
/* 264 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2LongFunction composeShort(Short2FloatFunction before) {
/* 268 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2IntFunction andThenInt(Long2IntFunction after) {
/* 272 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2LongFunction composeInt(Int2FloatFunction before) {
/* 276 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2LongFunction andThenLong(Long2LongFunction after) {
/* 280 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2LongFunction composeLong(Long2FloatFunction before) {
/* 284 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2CharFunction andThenChar(Long2CharFunction after) {
/* 288 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2LongFunction composeChar(Char2FloatFunction before) {
/* 292 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2FloatFunction andThenFloat(Long2FloatFunction after) {
/* 296 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2LongFunction composeFloat(Float2FloatFunction before) {
/* 300 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2DoubleFunction andThenDouble(Long2DoubleFunction after) {
/* 304 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2LongFunction composeDouble(Double2FloatFunction before) {
/* 308 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Float2ObjectFunction<T> andThenObject(Long2ObjectFunction<? extends T> after) {
/* 312 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2LongFunction<T> composeObject(Object2FloatFunction<? super T> before) {
/* 316 */     return k -> get(before.getFloat(k));
/*     */   }
/*     */   
/*     */   default <T> Float2ReferenceFunction<T> andThenReference(Long2ReferenceFunction<? extends T> after) {
/* 320 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2LongFunction<T> composeReference(Reference2FloatFunction<? super T> before) {
/* 324 */     return k -> get(before.getFloat(k));
/*     */   }
/*     */   
/*     */   long get(float paramFloat);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2LongFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */