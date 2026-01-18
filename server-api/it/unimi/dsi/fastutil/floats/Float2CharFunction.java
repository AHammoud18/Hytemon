/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2CharFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2CharFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2IntFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2LongFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2CharFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2CharFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2CharFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2CharFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2CharFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2CharFunction;
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
/*     */ public interface Float2CharFunction
/*     */   extends Function<Float, Character>, DoubleToIntFunction
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
/*     */   default char put(float key, char value) {
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
/*     */   default char getOrDefault(float key, char defaultValue) {
/*     */     char v;
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
/*     */   default char remove(float key) {
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
/*     */   default Character put(Float key, Character value) {
/* 133 */     float k = key.floatValue();
/* 134 */     boolean containsKey = containsKey(k);
/* 135 */     char v = put(k, value.charValue());
/* 136 */     return containsKey ? Character.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Character get(Object key) {
/* 147 */     if (key == null) return null; 
/* 148 */     float k = ((Float)key).floatValue(); char v; return ((
/*     */       
/* 150 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Character.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Character getOrDefault(Object key, Character defaultValue) {
/* 161 */     if (key == null) return defaultValue; 
/* 162 */     float k = ((Float)key).floatValue();
/* 163 */     char v = get(k);
/* 164 */     return (v != defaultReturnValue() || containsKey(k)) ? Character.valueOf(v) : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Character remove(Object key) {
/* 175 */     if (key == null) return null; 
/* 176 */     float k = ((Float)key).floatValue();
/* 177 */     return containsKey(k) ? Character.valueOf(remove(k)) : null;
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
/*     */   default void defaultReturnValue(char rv) {
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
/*     */   default char defaultReturnValue() {
/* 230 */     return Character.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, Character> compose(Function<? super T, ? extends Float> before) {
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
/*     */   default <T> Function<Float, T> andThen(Function<? super Character, ? extends T> after) {
/* 252 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Float2ByteFunction andThenByte(Char2ByteFunction after) {
/* 256 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2CharFunction composeByte(Byte2FloatFunction before) {
/* 260 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2ShortFunction andThenShort(Char2ShortFunction after) {
/* 264 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2CharFunction composeShort(Short2FloatFunction before) {
/* 268 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2IntFunction andThenInt(Char2IntFunction after) {
/* 272 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2CharFunction composeInt(Int2FloatFunction before) {
/* 276 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2LongFunction andThenLong(Char2LongFunction after) {
/* 280 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2CharFunction composeLong(Long2FloatFunction before) {
/* 284 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2CharFunction andThenChar(Char2CharFunction after) {
/* 288 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2CharFunction composeChar(Char2FloatFunction before) {
/* 292 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2FloatFunction andThenFloat(Char2FloatFunction after) {
/* 296 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2CharFunction composeFloat(Float2FloatFunction before) {
/* 300 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Float2DoubleFunction andThenDouble(Char2DoubleFunction after) {
/* 304 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2CharFunction composeDouble(Double2FloatFunction before) {
/* 308 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Float2ObjectFunction<T> andThenObject(Char2ObjectFunction<? extends T> after) {
/* 312 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2CharFunction<T> composeObject(Object2FloatFunction<? super T> before) {
/* 316 */     return k -> get(before.getFloat(k));
/*     */   }
/*     */   
/*     */   default <T> Float2ReferenceFunction<T> andThenReference(Char2ReferenceFunction<? extends T> after) {
/* 320 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2CharFunction<T> composeReference(Reference2FloatFunction<? super T> before) {
/* 324 */     return k -> get(before.getFloat(k));
/*     */   }
/*     */   
/*     */   char get(float paramFloat);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2CharFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */