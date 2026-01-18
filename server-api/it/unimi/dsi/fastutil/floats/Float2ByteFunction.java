/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2CharFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2IntFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2LongFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2FloatFunction;
/*     */ import java.util.function.DoubleToIntFunction;
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
/*     */ public interface Float2ByteFunction
/*     */   extends Function<Float, Byte>, DoubleToIntFunction
/*     */ {
/*     */   @Deprecated
/*     */   default int applyAsInt(double operand) {
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
/*     */   default byte put(float key, byte value) {
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
/*     */   default byte getOrDefault(float key, byte defaultValue) {
/*     */     byte v;
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
/*     */   default byte remove(float key) {
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
/*     */   default Byte put(Float key, Byte value) {
/* 133 */     float k = key.floatValue();
/* 134 */     boolean containsKey = containsKey(k);
/* 135 */     byte v = put(k, value.byteValue());
/* 136 */     return containsKey ? Byte.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Byte get(Object key) {
/* 147 */     if (key == null) return null; 
/* 148 */     float k = ((Float)key).floatValue(); byte v; return ((
/*     */       
/* 150 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Byte.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Byte getOrDefault(Object key, Byte defaultValue) {
/* 161 */     if (key == null) return defaultValue; 
/* 162 */     float k = ((Float)key).floatValue();
/* 163 */     byte v = get(k);
/* 164 */     return (v != defaultReturnValue() || containsKey(k)) ? Byte.valueOf(v) : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Byte remove(Object key) {
/* 175 */     if (key == null) return null; 
/* 176 */     float k = ((Float)key).floatValue();
/* 177 */     return containsKey(k) ? Byte.valueOf(remove(k)) : null;
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
/*     */   default void defaultReturnValue(byte rv) {
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
/*     */   default byte defaultReturnValue() {
/* 230 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, Byte> compose(Function<? super T, ? extends Float> before) {
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
/*     */   default <T> Function<Float, T> andThen(Function<? super Byte, ? extends T> after) {
/* 252 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Float2ByteFunction andThenByte(Byte2ByteFunction after) {
/* 256 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2ByteFunction composeByte(Byte2FloatFunction before) {
/* 260 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2ShortFunction andThenShort(Byte2ShortFunction after) {
/* 264 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2ByteFunction composeShort(Short2FloatFunction before) {
/* 268 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2IntFunction andThenInt(Byte2IntFunction after) {
/* 272 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2ByteFunction composeInt(Int2FloatFunction before) {
/* 276 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2LongFunction andThenLong(Byte2LongFunction after) {
/* 280 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2ByteFunction composeLong(Long2FloatFunction before) {
/* 284 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2CharFunction andThenChar(Byte2CharFunction after) {
/* 288 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2ByteFunction composeChar(Char2FloatFunction before) {
/* 292 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2FloatFunction andThenFloat(Byte2FloatFunction after) {
/* 296 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2ByteFunction composeFloat(Float2FloatFunction before) {
/* 300 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2DoubleFunction andThenDouble(Byte2DoubleFunction after) {
/* 304 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2ByteFunction composeDouble(Double2FloatFunction before) {
/* 308 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Float2ObjectFunction<T> andThenObject(Byte2ObjectFunction<? extends T> after) {
/* 312 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ByteFunction<T> composeObject(Object2FloatFunction<? super T> before) {
/* 316 */     return k -> get(before.getFloat(k));
/*     */   }
/*     */   
/*     */   default <T> Float2ReferenceFunction<T> andThenReference(Byte2ReferenceFunction<? extends T> after) {
/* 320 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ByteFunction<T> composeReference(Reference2FloatFunction<? super T> before) {
/* 324 */     return k -> get(before.getFloat(k));
/*     */   }
/*     */   
/*     */   byte get(float paramFloat);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2ByteFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */