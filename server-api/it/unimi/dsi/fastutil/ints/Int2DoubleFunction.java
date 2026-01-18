/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2IntFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2IntFunction;
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
/*     */ import it.unimi.dsi.fastutil.floats.Float2IntFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2IntFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2IntFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntToDoubleFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Int2DoubleFunction
/*     */   extends Function<Integer, Double>, IntToDoubleFunction
/*     */ {
/*     */   default double applyAsDouble(int operand) {
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
/*     */   default double put(int key, double value) {
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
/*     */   default double getOrDefault(int key, double defaultValue) {
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
/*     */   default double remove(int key) {
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
/*     */   default Double put(Integer key, Double value) {
/* 122 */     int k = key.intValue();
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
/* 137 */     int k = ((Integer)key).intValue(); double v; return ((
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
/* 151 */     int k = ((Integer)key).intValue();
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
/* 165 */     int k = ((Integer)key).intValue();
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
/*     */   default boolean containsKey(int key) {
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
/* 192 */     return (key == null) ? false : containsKey(((Integer)key).intValue());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, Double> compose(Function<? super T, ? extends Integer> before) {
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
/*     */   default <T> Function<Integer, T> andThen(Function<? super Double, ? extends T> after) {
/* 241 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Int2ByteFunction andThenByte(Double2ByteFunction after) {
/* 245 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2DoubleFunction composeByte(Byte2IntFunction before) {
/* 249 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Int2ShortFunction andThenShort(Double2ShortFunction after) {
/* 253 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2DoubleFunction composeShort(Short2IntFunction before) {
/* 257 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Int2IntFunction andThenInt(Double2IntFunction after) {
/* 261 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2DoubleFunction composeInt(Int2IntFunction before) {
/* 265 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Int2LongFunction andThenLong(Double2LongFunction after) {
/* 269 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2DoubleFunction composeLong(Long2IntFunction before) {
/* 273 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Int2CharFunction andThenChar(Double2CharFunction after) {
/* 277 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2DoubleFunction composeChar(Char2IntFunction before) {
/* 281 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Int2FloatFunction andThenFloat(Double2FloatFunction after) {
/* 285 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2DoubleFunction composeFloat(Float2IntFunction before) {
/* 289 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Int2DoubleFunction andThenDouble(Double2DoubleFunction after) {
/* 293 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2DoubleFunction composeDouble(Double2IntFunction before) {
/* 297 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Int2ObjectFunction<T> andThenObject(Double2ObjectFunction<? extends T> after) {
/* 301 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2DoubleFunction<T> composeObject(Object2IntFunction<? super T> before) {
/* 305 */     return k -> get(before.getInt(k));
/*     */   }
/*     */   
/*     */   default <T> Int2ReferenceFunction<T> andThenReference(Double2ReferenceFunction<? extends T> after) {
/* 309 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2DoubleFunction<T> composeReference(Reference2IntFunction<? super T> before) {
/* 313 */     return k -> get(before.getInt(k));
/*     */   }
/*     */   
/*     */   double get(int paramInt);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\Int2DoubleFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */