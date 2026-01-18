/*     */ package it.unimi.dsi.fastutil.booleans;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2IntFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2IntFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2IntFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2IntFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2CharFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2LongFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2IntFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2IntFunction;
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
/*     */ public interface Boolean2IntFunction
/*     */   extends Function<Boolean, Integer>
/*     */ {
/*     */   default int put(boolean key, int value) {
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
/*     */   default int getOrDefault(boolean key, int defaultValue) {
/*     */     int v;
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
/*     */   default int remove(boolean key) {
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
/*     */   default Integer put(Boolean key, Integer value) {
/* 112 */     boolean k = key.booleanValue();
/* 113 */     boolean containsKey = containsKey(k);
/* 114 */     int v = put(k, value.intValue());
/* 115 */     return containsKey ? Integer.valueOf(v) : null;
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
/* 126 */     if (key == null) return null; 
/* 127 */     boolean k = ((Boolean)key).booleanValue(); int v; return ((
/*     */       
/* 129 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Integer.valueOf(v) : null;
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
/* 140 */     if (key == null) return defaultValue; 
/* 141 */     boolean k = ((Boolean)key).booleanValue();
/* 142 */     int v = get(k);
/* 143 */     return (v != defaultReturnValue() || containsKey(k)) ? Integer.valueOf(v) : defaultValue;
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
/* 154 */     if (key == null) return null; 
/* 155 */     boolean k = ((Boolean)key).booleanValue();
/* 156 */     return containsKey(k) ? Integer.valueOf(remove(k)) : null;
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
/*     */   default void defaultReturnValue(int rv) {
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
/*     */   default int defaultReturnValue() {
/* 209 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, Integer> compose(Function<? super T, ? extends Boolean> before) {
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
/*     */   default <T> Function<Boolean, T> andThen(Function<? super Integer, ? extends T> after) {
/* 231 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Boolean2ByteFunction andThenByte(Int2ByteFunction after) {
/* 235 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2IntFunction composeByte(Byte2BooleanFunction before) {
/* 239 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2ShortFunction andThenShort(Int2ShortFunction after) {
/* 243 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2IntFunction composeShort(Short2BooleanFunction before) {
/* 247 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2IntFunction andThenInt(Int2IntFunction after) {
/* 251 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2IntFunction composeInt(Int2BooleanFunction before) {
/* 255 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2LongFunction andThenLong(Int2LongFunction after) {
/* 259 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2IntFunction composeLong(Long2BooleanFunction before) {
/* 263 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2CharFunction andThenChar(Int2CharFunction after) {
/* 267 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2IntFunction composeChar(Char2BooleanFunction before) {
/* 271 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2FloatFunction andThenFloat(Int2FloatFunction after) {
/* 275 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2IntFunction composeFloat(Float2BooleanFunction before) {
/* 279 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2DoubleFunction andThenDouble(Int2DoubleFunction after) {
/* 283 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2IntFunction composeDouble(Double2BooleanFunction before) {
/* 287 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Boolean2ObjectFunction<T> andThenObject(Int2ObjectFunction<? extends T> after) {
/* 291 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2IntFunction<T> composeObject(Object2BooleanFunction<? super T> before) {
/* 295 */     return k -> get(before.getBoolean(k));
/*     */   }
/*     */   
/*     */   default <T> Boolean2ReferenceFunction<T> andThenReference(Int2ReferenceFunction<? extends T> after) {
/* 299 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2IntFunction<T> composeReference(Reference2BooleanFunction<? super T> before) {
/* 303 */     return k -> get(before.getBoolean(k));
/*     */   }
/*     */   
/*     */   int get(boolean paramBoolean);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\Boolean2IntFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */