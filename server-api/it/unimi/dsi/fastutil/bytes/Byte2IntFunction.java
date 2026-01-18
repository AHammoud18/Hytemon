/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2IntFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2IntFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2IntFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2CharFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2LongFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2IntFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2IntFunction;
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
/*     */ @FunctionalInterface
/*     */ public interface Byte2IntFunction
/*     */   extends Function<Byte, Integer>, IntUnaryOperator
/*     */ {
/*     */   @Deprecated
/*     */   default int applyAsInt(int operand) {
/*  71 */     return get(SafeMath.safeIntToByte(operand));
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
/*     */   default int put(byte key, int value) {
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
/*     */   default int getOrDefault(byte key, int defaultValue) {
/*     */     int v;
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
/*     */   default int remove(byte key) {
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
/*     */   default Integer put(Byte key, Integer value) {
/* 133 */     byte k = key.byteValue();
/* 134 */     boolean containsKey = containsKey(k);
/* 135 */     int v = put(k, value.intValue());
/* 136 */     return containsKey ? Integer.valueOf(v) : null;
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
/* 147 */     if (key == null) return null; 
/* 148 */     byte k = ((Byte)key).byteValue(); int v; return ((
/*     */       
/* 150 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Integer.valueOf(v) : null;
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
/* 161 */     if (key == null) return defaultValue; 
/* 162 */     byte k = ((Byte)key).byteValue();
/* 163 */     int v = get(k);
/* 164 */     return (v != defaultReturnValue() || containsKey(k)) ? Integer.valueOf(v) : defaultValue;
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
/* 175 */     if (key == null) return null; 
/* 176 */     byte k = ((Byte)key).byteValue();
/* 177 */     return containsKey(k) ? Integer.valueOf(remove(k)) : null;
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
/*     */   default boolean containsKey(byte key) {
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
/* 203 */     return (key == null) ? false : containsKey(((Byte)key).byteValue());
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
/*     */   default int defaultReturnValue() {
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
/*     */   default <T> Function<T, Integer> compose(Function<? super T, ? extends Byte> before) {
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
/*     */   default <T> Function<Byte, T> andThen(Function<? super Integer, ? extends T> after) {
/* 252 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Byte2ByteFunction andThenByte(Int2ByteFunction after) {
/* 256 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2IntFunction composeByte(Byte2ByteFunction before) {
/* 260 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Byte2ShortFunction andThenShort(Int2ShortFunction after) {
/* 264 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2IntFunction composeShort(Short2ByteFunction before) {
/* 268 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Byte2IntFunction andThenInt(Int2IntFunction after) {
/* 272 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2IntFunction composeInt(Int2ByteFunction before) {
/* 276 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Byte2LongFunction andThenLong(Int2LongFunction after) {
/* 280 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2IntFunction composeLong(Long2ByteFunction before) {
/* 284 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Byte2CharFunction andThenChar(Int2CharFunction after) {
/* 288 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2IntFunction composeChar(Char2ByteFunction before) {
/* 292 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Byte2FloatFunction andThenFloat(Int2FloatFunction after) {
/* 296 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2IntFunction composeFloat(Float2ByteFunction before) {
/* 300 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Byte2DoubleFunction andThenDouble(Int2DoubleFunction after) {
/* 304 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2IntFunction composeDouble(Double2ByteFunction before) {
/* 308 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Byte2ObjectFunction<T> andThenObject(Int2ObjectFunction<? extends T> after) {
/* 312 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2IntFunction<T> composeObject(Object2ByteFunction<? super T> before) {
/* 316 */     return k -> get(before.getByte(k));
/*     */   }
/*     */   
/*     */   default <T> Byte2ReferenceFunction<T> andThenReference(Int2ReferenceFunction<? extends T> after) {
/* 320 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2IntFunction<T> composeReference(Reference2ByteFunction<? super T> before) {
/* 324 */     return k -> get(before.getByte(k));
/*     */   }
/*     */   
/*     */   int get(byte paramByte);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2IntFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */