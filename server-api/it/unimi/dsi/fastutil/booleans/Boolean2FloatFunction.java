/*     */ package it.unimi.dsi.fastutil.booleans;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2CharFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2IntFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2LongFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2FloatFunction;
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
/*     */ @FunctionalInterface
/*     */ public interface Boolean2FloatFunction
/*     */   extends Function<Boolean, Float>
/*     */ {
/*     */   default float put(boolean key, float value) {
/*  63 */     throw new UnsupportedOperationException();
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
/*     */   default float getOrDefault(boolean key, float defaultValue) {
/*     */     float v;
/*  89 */     return ((v = get(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default float remove(boolean key) {
/* 101 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Float put(Boolean key, Float value) {
/* 112 */     boolean k = key.booleanValue();
/* 113 */     boolean containsKey = containsKey(k);
/* 114 */     float v = put(k, value.floatValue());
/* 115 */     return containsKey ? Float.valueOf(v) : null;
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
/* 126 */     if (key == null) return null; 
/* 127 */     boolean k = ((Boolean)key).booleanValue(); float v; return ((
/*     */       
/* 129 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Float.valueOf(v) : null;
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
/* 140 */     if (key == null) return defaultValue; 
/* 141 */     boolean k = ((Boolean)key).booleanValue();
/* 142 */     float v = get(k);
/* 143 */     return (v != defaultReturnValue() || containsKey(k)) ? Float.valueOf(v) : defaultValue;
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
/* 154 */     if (key == null) return null; 
/* 155 */     boolean k = ((Boolean)key).booleanValue();
/* 156 */     return containsKey(k) ? Float.valueOf(remove(k)) : null;
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
/*     */   default boolean containsKey(boolean key) {
/* 171 */     return true;
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
/* 182 */     return (key == null) ? false : containsKey(((Boolean)key).booleanValue());
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
/* 196 */     throw new UnsupportedOperationException();
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
/* 209 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, Float> compose(Function<? super T, ? extends Boolean> before) {
/* 220 */     return super.compose(before);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<Boolean, T> andThen(Function<? super Float, ? extends T> after) {
/* 231 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Boolean2ByteFunction andThenByte(Float2ByteFunction after) {
/* 235 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2FloatFunction composeByte(Byte2BooleanFunction before) {
/* 239 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2ShortFunction andThenShort(Float2ShortFunction after) {
/* 243 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2FloatFunction composeShort(Short2BooleanFunction before) {
/* 247 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2IntFunction andThenInt(Float2IntFunction after) {
/* 251 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2FloatFunction composeInt(Int2BooleanFunction before) {
/* 255 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2LongFunction andThenLong(Float2LongFunction after) {
/* 259 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2FloatFunction composeLong(Long2BooleanFunction before) {
/* 263 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2CharFunction andThenChar(Float2CharFunction after) {
/* 267 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2FloatFunction composeChar(Char2BooleanFunction before) {
/* 271 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2FloatFunction andThenFloat(Float2FloatFunction after) {
/* 275 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2FloatFunction composeFloat(Float2BooleanFunction before) {
/* 279 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2DoubleFunction andThenDouble(Float2DoubleFunction after) {
/* 283 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2FloatFunction composeDouble(Double2BooleanFunction before) {
/* 287 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Boolean2ObjectFunction<T> andThenObject(Float2ObjectFunction<? extends T> after) {
/* 291 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2FloatFunction<T> composeObject(Object2BooleanFunction<? super T> before) {
/* 295 */     return k -> get(before.getBoolean(k));
/*     */   }
/*     */   
/*     */   default <T> Boolean2ReferenceFunction<T> andThenReference(Float2ReferenceFunction<? extends T> after) {
/* 299 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2FloatFunction<T> composeReference(Reference2BooleanFunction<? super T> before) {
/* 303 */     return k -> get(before.getBoolean(k));
/*     */   }
/*     */   
/*     */   float get(boolean paramBoolean);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\Boolean2FloatFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */