/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ByteFunction;
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
/*     */ public interface Byte2ByteFunction
/*     */   extends Function<Byte, Byte>, IntUnaryOperator
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
/*     */   default byte put(byte key, byte value) {
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
/*     */   default byte getOrDefault(byte key, byte defaultValue) {
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
/*     */   default byte remove(byte key) {
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
/*     */   default Byte put(Byte key, Byte value) {
/* 133 */     byte k = key.byteValue();
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
/* 148 */     byte k = ((Byte)key).byteValue(); byte v; return ((
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
/* 162 */     byte k = ((Byte)key).byteValue();
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
/* 176 */     byte k = ((Byte)key).byteValue();
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
/*     */   static Byte2ByteFunction identity() {
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
/*     */   default <T> Function<T, Byte> compose(Function<? super T, ? extends Byte> before) {
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
/*     */   default <T> Function<Byte, T> andThen(Function<? super Byte, ? extends T> after) {
/* 256 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Byte2ByteFunction andThenByte(Byte2ByteFunction after) {
/* 260 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2ByteFunction composeByte(Byte2ByteFunction before) {
/* 264 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Byte2ShortFunction andThenShort(Byte2ShortFunction after) {
/* 268 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2ByteFunction composeShort(Short2ByteFunction before) {
/* 272 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Byte2IntFunction andThenInt(Byte2IntFunction after) {
/* 276 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2ByteFunction composeInt(Int2ByteFunction before) {
/* 280 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Byte2LongFunction andThenLong(Byte2LongFunction after) {
/* 284 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2ByteFunction composeLong(Long2ByteFunction before) {
/* 288 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Byte2CharFunction andThenChar(Byte2CharFunction after) {
/* 292 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2ByteFunction composeChar(Char2ByteFunction before) {
/* 296 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Byte2FloatFunction andThenFloat(Byte2FloatFunction after) {
/* 300 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2ByteFunction composeFloat(Float2ByteFunction before) {
/* 304 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Byte2DoubleFunction andThenDouble(Byte2DoubleFunction after) {
/* 308 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2ByteFunction composeDouble(Double2ByteFunction before) {
/* 312 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Byte2ObjectFunction<T> andThenObject(Byte2ObjectFunction<? extends T> after) {
/* 316 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ByteFunction<T> composeObject(Object2ByteFunction<? super T> before) {
/* 320 */     return k -> get(before.getByte(k));
/*     */   }
/*     */   
/*     */   default <T> Byte2ReferenceFunction<T> andThenReference(Byte2ReferenceFunction<? extends T> after) {
/* 324 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ByteFunction<T> composeReference(Reference2ByteFunction<? super T> before) {
/* 328 */     return k -> get(before.getByte(k));
/*     */   }
/*     */   
/*     */   byte get(byte paramByte);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2ByteFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */