/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ShortFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntUnaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Short2ShortFunction
/*     */   extends Function<Short, Short>, IntUnaryOperator
/*     */ {
/*     */   @Deprecated
/*     */   default int applyAsInt(int operand) {
/*  71 */     return get(SafeMath.safeIntToShort(operand));
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
/*     */   default short put(short key, short value) {
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
/*     */   default short getOrDefault(short key, short defaultValue) {
/*     */     short v;
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
/*     */   default short remove(short key) {
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
/*     */   default Short put(Short key, Short value) {
/* 133 */     short k = key.shortValue();
/* 134 */     boolean containsKey = containsKey(k);
/* 135 */     short v = put(k, value.shortValue());
/* 136 */     return containsKey ? Short.valueOf(v) : null;
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
/* 147 */     if (key == null) return null; 
/* 148 */     short k = ((Short)key).shortValue(); short v; return ((
/*     */       
/* 150 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Short.valueOf(v) : null;
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
/* 161 */     if (key == null) return defaultValue; 
/* 162 */     short k = ((Short)key).shortValue();
/* 163 */     short v = get(k);
/* 164 */     return (v != defaultReturnValue() || containsKey(k)) ? Short.valueOf(v) : defaultValue;
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
/* 175 */     if (key == null) return null; 
/* 176 */     short k = ((Short)key).shortValue();
/* 177 */     return containsKey(k) ? Short.valueOf(remove(k)) : null;
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
/*     */   default boolean containsKey(short key) {
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
/* 203 */     return (key == null) ? false : containsKey(((Short)key).shortValue());
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
/*     */   default short defaultReturnValue() {
/* 230 */     return 0;
/*     */   }
/*     */   
/*     */   static Short2ShortFunction identity() {
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
/*     */   default <T> Function<T, Short> compose(Function<? super T, ? extends Short> before) {
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
/*     */   default <T> Function<Short, T> andThen(Function<? super Short, ? extends T> after) {
/* 256 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Short2ByteFunction andThenByte(Short2ByteFunction after) {
/* 260 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2ShortFunction composeByte(Byte2ShortFunction before) {
/* 264 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Short2ShortFunction andThenShort(Short2ShortFunction after) {
/* 268 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2ShortFunction composeShort(Short2ShortFunction before) {
/* 272 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Short2IntFunction andThenInt(Short2IntFunction after) {
/* 276 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2ShortFunction composeInt(Int2ShortFunction before) {
/* 280 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Short2LongFunction andThenLong(Short2LongFunction after) {
/* 284 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2ShortFunction composeLong(Long2ShortFunction before) {
/* 288 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Short2CharFunction andThenChar(Short2CharFunction after) {
/* 292 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2ShortFunction composeChar(Char2ShortFunction before) {
/* 296 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Short2FloatFunction andThenFloat(Short2FloatFunction after) {
/* 300 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2ShortFunction composeFloat(Float2ShortFunction before) {
/* 304 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Short2DoubleFunction andThenDouble(Short2DoubleFunction after) {
/* 308 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2ShortFunction composeDouble(Double2ShortFunction before) {
/* 312 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Short2ObjectFunction<T> andThenObject(Short2ObjectFunction<? extends T> after) {
/* 316 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ShortFunction<T> composeObject(Object2ShortFunction<? super T> before) {
/* 320 */     return k -> get(before.getShort(k));
/*     */   }
/*     */   
/*     */   default <T> Short2ReferenceFunction<T> andThenReference(Short2ReferenceFunction<? extends T> after) {
/* 324 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ShortFunction<T> composeReference(Reference2ShortFunction<? super T> before) {
/* 328 */     return k -> get(before.getShort(k));
/*     */   }
/*     */   
/*     */   short get(short paramShort);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2ShortFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */