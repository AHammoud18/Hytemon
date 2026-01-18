/*     */ package it.unimi.dsi.fastutil.booleans;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2CharFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2IntFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2LongFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ByteFunction;
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
/*     */ public interface Boolean2ByteFunction
/*     */   extends Function<Boolean, Byte>
/*     */ {
/*     */   default byte put(boolean key, byte value) {
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
/*     */   default byte getOrDefault(boolean key, byte defaultValue) {
/*     */     byte v;
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
/*     */   default byte remove(boolean key) {
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
/*     */   default Byte put(Boolean key, Byte value) {
/* 112 */     boolean k = key.booleanValue();
/* 113 */     boolean containsKey = containsKey(k);
/* 114 */     byte v = put(k, value.byteValue());
/* 115 */     return containsKey ? Byte.valueOf(v) : null;
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
/* 126 */     if (key == null) return null; 
/* 127 */     boolean k = ((Boolean)key).booleanValue(); byte v; return ((
/*     */       
/* 129 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Byte.valueOf(v) : null;
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
/* 140 */     if (key == null) return defaultValue; 
/* 141 */     boolean k = ((Boolean)key).booleanValue();
/* 142 */     byte v = get(k);
/* 143 */     return (v != defaultReturnValue() || containsKey(k)) ? Byte.valueOf(v) : defaultValue;
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
/* 154 */     if (key == null) return null; 
/* 155 */     boolean k = ((Boolean)key).booleanValue();
/* 156 */     return containsKey(k) ? Byte.valueOf(remove(k)) : null;
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
/*     */   default void defaultReturnValue(byte rv) {
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
/*     */   default byte defaultReturnValue() {
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
/*     */   default <T> Function<T, Byte> compose(Function<? super T, ? extends Boolean> before) {
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
/*     */   default <T> Function<Boolean, T> andThen(Function<? super Byte, ? extends T> after) {
/* 231 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Boolean2ByteFunction andThenByte(Byte2ByteFunction after) {
/* 235 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2ByteFunction composeByte(Byte2BooleanFunction before) {
/* 239 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2ShortFunction andThenShort(Byte2ShortFunction after) {
/* 243 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2ByteFunction composeShort(Short2BooleanFunction before) {
/* 247 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2IntFunction andThenInt(Byte2IntFunction after) {
/* 251 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2ByteFunction composeInt(Int2BooleanFunction before) {
/* 255 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2LongFunction andThenLong(Byte2LongFunction after) {
/* 259 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2ByteFunction composeLong(Long2BooleanFunction before) {
/* 263 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2CharFunction andThenChar(Byte2CharFunction after) {
/* 267 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2ByteFunction composeChar(Char2BooleanFunction before) {
/* 271 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2FloatFunction andThenFloat(Byte2FloatFunction after) {
/* 275 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2ByteFunction composeFloat(Float2BooleanFunction before) {
/* 279 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Boolean2DoubleFunction andThenDouble(Byte2DoubleFunction after) {
/* 283 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2ByteFunction composeDouble(Double2BooleanFunction before) {
/* 287 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Boolean2ObjectFunction<T> andThenObject(Byte2ObjectFunction<? extends T> after) {
/* 291 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ByteFunction<T> composeObject(Object2BooleanFunction<? super T> before) {
/* 295 */     return k -> get(before.getBoolean(k));
/*     */   }
/*     */   
/*     */   default <T> Boolean2ReferenceFunction<T> andThenReference(Byte2ReferenceFunction<? extends T> after) {
/* 299 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2ByteFunction<T> composeReference(Reference2BooleanFunction<? super T> before) {
/* 303 */     return k -> get(before.getBoolean(k));
/*     */   }
/*     */   
/*     */   byte get(boolean paramBoolean);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\Boolean2ByteFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */