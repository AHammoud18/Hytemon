/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2FloatFunction;
/*     */ import java.util.function.DoubleUnaryOperator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Float2FloatFunction
/*     */   extends Function<Float, Float>, DoubleUnaryOperator
/*     */ {
/*     */   @Deprecated
/*     */   default double applyAsDouble(double operand) {
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
/*     */   default float put(float key, float value) {
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
/*     */   default float getOrDefault(float key, float defaultValue) {
/*     */     float v;
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
/*     */   default float remove(float key) {
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
/*     */   default Float put(Float key, Float value) {
/* 133 */     float k = key.floatValue();
/* 134 */     boolean containsKey = containsKey(k);
/* 135 */     float v = put(k, value.floatValue());
/* 136 */     return containsKey ? Float.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Float get(Object key) {
/* 147 */     if (key == null) return null; 
/* 148 */     float k = ((Float)key).floatValue(); float v; return ((
/*     */       
/* 150 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Float.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Float getOrDefault(Object key, Float defaultValue) {
/* 161 */     if (key == null) return defaultValue; 
/* 162 */     float k = ((Float)key).floatValue();
/* 163 */     float v = get(k);
/* 164 */     return (v != defaultReturnValue() || containsKey(k)) ? Float.valueOf(v) : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Float remove(Object key) {
/* 175 */     if (key == null) return null; 
/* 176 */     float k = ((Float)key).floatValue();
/* 177 */     return containsKey(k) ? Float.valueOf(remove(k)) : null;
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
/*     */   default void defaultReturnValue(float rv) {
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
/*     */   default float defaultReturnValue() {
/* 230 */     return 0.0F;
/*     */   }
/*     */   
/*     */   static Float2FloatFunction identity() {
/* 234 */     return k -> k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, Float> compose(Function<? super T, ? extends Float> before) {
/* 245 */     return super.compose(before);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<Float, T> andThen(Function<? super Float, ? extends T> after) {
/* 256 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Float2ByteFunction andThenByte(Float2ByteFunction after) {
/* 260 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2FloatFunction composeByte(Byte2FloatFunction before) {
/* 264 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2ShortFunction andThenShort(Float2ShortFunction after) {
/* 268 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2FloatFunction composeShort(Short2FloatFunction before) {
/* 272 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2IntFunction andThenInt(Float2IntFunction after) {
/* 276 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2FloatFunction composeInt(Int2FloatFunction before) {
/* 280 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2LongFunction andThenLong(Float2LongFunction after) {
/* 284 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2FloatFunction composeLong(Long2FloatFunction before) {
/* 288 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2CharFunction andThenChar(Float2CharFunction after) {
/* 292 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2FloatFunction composeChar(Char2FloatFunction before) {
/* 296 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2FloatFunction andThenFloat(Float2FloatFunction after) {
/* 300 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2FloatFunction composeFloat(Float2FloatFunction before) {
/* 304 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2DoubleFunction andThenDouble(Float2DoubleFunction after) {
/* 308 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2FloatFunction composeDouble(Double2FloatFunction before) {
/* 312 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Float2ObjectFunction<T> andThenObject(Float2ObjectFunction<? extends T> after) {
/* 316 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2FloatFunction<T> composeObject(Object2FloatFunction<? super T> before) {
/* 320 */     return k -> get(before.getFloat(k));
/*     */   }
/*     */   
/*     */   default <T> Float2ReferenceFunction<T> andThenReference(Float2ReferenceFunction<? extends T> after) {
/* 324 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2FloatFunction<T> composeReference(Reference2FloatFunction<? super T> before) {
/* 328 */     return k -> get(before.getFloat(k));
/*     */   }
/*     */   
/*     */   float get(float paramFloat);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2FloatFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */