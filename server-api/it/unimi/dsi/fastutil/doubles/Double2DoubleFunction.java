/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2DoubleFunction;
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
/*     */ @FunctionalInterface
/*     */ public interface Double2DoubleFunction
/*     */   extends Function<Double, Double>, DoubleUnaryOperator
/*     */ {
/*     */   default double applyAsDouble(double operand) {
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
/*     */   default double put(double key, double value) {
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
/*     */   default double getOrDefault(double key, double defaultValue) {
/*     */     double v;
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
/*     */   default double remove(double key) {
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
/*     */   default Double put(Double key, Double value) {
/* 122 */     double k = key.doubleValue();
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
/* 136 */     if (key == null) return null; 
/* 137 */     double k = ((Double)key).doubleValue(); double v; return ((
/*     */       
/* 139 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Double.valueOf(v) : null;
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
/* 150 */     if (key == null) return defaultValue; 
/* 151 */     double k = ((Double)key).doubleValue();
/* 152 */     double v = get(k);
/* 153 */     return (v != defaultReturnValue() || containsKey(k)) ? Double.valueOf(v) : defaultValue;
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
/* 164 */     if (key == null) return null; 
/* 165 */     double k = ((Double)key).doubleValue();
/* 166 */     return containsKey(k) ? Double.valueOf(remove(k)) : null;
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
/*     */   default void defaultReturnValue(double rv) {
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
/*     */   default double defaultReturnValue() {
/* 219 */     return 0.0D;
/*     */   }
/*     */   
/*     */   static Double2DoubleFunction identity() {
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
/*     */   default <T> Function<T, Double> compose(Function<? super T, ? extends Double> before) {
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
/*     */   default <T> Function<Double, T> andThen(Function<? super Double, ? extends T> after) {
/* 245 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Double2ByteFunction andThenByte(Double2ByteFunction after) {
/* 249 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2DoubleFunction composeByte(Byte2DoubleFunction before) {
/* 253 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Double2ShortFunction andThenShort(Double2ShortFunction after) {
/* 257 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2DoubleFunction composeShort(Short2DoubleFunction before) {
/* 261 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Double2IntFunction andThenInt(Double2IntFunction after) {
/* 265 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2DoubleFunction composeInt(Int2DoubleFunction before) {
/* 269 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Double2LongFunction andThenLong(Double2LongFunction after) {
/* 273 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2DoubleFunction composeLong(Long2DoubleFunction before) {
/* 277 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Double2CharFunction andThenChar(Double2CharFunction after) {
/* 281 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2DoubleFunction composeChar(Char2DoubleFunction before) {
/* 285 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Double2FloatFunction andThenFloat(Double2FloatFunction after) {
/* 289 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2DoubleFunction composeFloat(Float2DoubleFunction before) {
/* 293 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Double2DoubleFunction andThenDouble(Double2DoubleFunction after) {
/* 297 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2DoubleFunction composeDouble(Double2DoubleFunction before) {
/* 301 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Double2ObjectFunction<T> andThenObject(Double2ObjectFunction<? extends T> after) {
/* 305 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2DoubleFunction<T> composeObject(Object2DoubleFunction<? super T> before) {
/* 309 */     return k -> get(before.getDouble(k));
/*     */   }
/*     */   
/*     */   default <T> Double2ReferenceFunction<T> andThenReference(Double2ReferenceFunction<? extends T> after) {
/* 313 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2DoubleFunction<T> composeReference(Reference2DoubleFunction<? super T> before) {
/* 317 */     return k -> get(before.getDouble(k));
/*     */   }
/*     */   
/*     */   double get(double paramDouble);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2DoubleFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */