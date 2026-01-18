/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.booleans.Boolean2ByteFunction;
/*     */ import it.unimi.dsi.fastutil.booleans.Boolean2CharFunction;
/*     */ import it.unimi.dsi.fastutil.booleans.Boolean2DoubleFunction;
/*     */ import it.unimi.dsi.fastutil.booleans.Boolean2FloatFunction;
/*     */ import it.unimi.dsi.fastutil.booleans.Boolean2IntFunction;
/*     */ import it.unimi.dsi.fastutil.booleans.Boolean2LongFunction;
/*     */ import it.unimi.dsi.fastutil.booleans.Boolean2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.booleans.Boolean2ReferenceFunction;
/*     */ import it.unimi.dsi.fastutil.booleans.Boolean2ShortFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2LongFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.chars.Char2LongFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2LongFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2LongFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2LongFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2LongFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2LongFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.LongPredicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Long2BooleanFunction
/*     */   extends Function<Long, Boolean>, LongPredicate
/*     */ {
/*     */   default boolean test(long operand) {
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
/*     */   default boolean put(long key, boolean value) {
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
/*     */   default boolean getOrDefault(long key, boolean defaultValue) {
/*     */     boolean v;
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
/*     */   default boolean remove(long key) {
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
/*     */   default Boolean put(Long key, Boolean value) {
/* 122 */     long k = key.longValue();
/* 123 */     boolean containsKey = containsKey(k);
/* 124 */     boolean v = put(k, value.booleanValue());
/* 125 */     return containsKey ? Boolean.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Boolean get(Object key) {
/* 136 */     if (key == null) return null; 
/* 137 */     long k = ((Long)key).longValue(); boolean v; return ((
/*     */       
/* 139 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Boolean.valueOf(v) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Boolean getOrDefault(Object key, Boolean defaultValue) {
/* 150 */     if (key == null) return defaultValue; 
/* 151 */     long k = ((Long)key).longValue();
/* 152 */     boolean v = get(k);
/* 153 */     return (v != defaultReturnValue() || containsKey(k)) ? Boolean.valueOf(v) : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Boolean remove(Object key) {
/* 164 */     if (key == null) return null; 
/* 165 */     long k = ((Long)key).longValue();
/* 166 */     return containsKey(k) ? Boolean.valueOf(remove(k)) : null;
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
/*     */   default boolean containsKey(long key) {
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
/* 192 */     return (key == null) ? false : containsKey(((Long)key).longValue());
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
/*     */   default void defaultReturnValue(boolean rv) {
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
/*     */   default boolean defaultReturnValue() {
/* 219 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, Boolean> compose(Function<? super T, ? extends Long> before) {
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
/*     */   default <T> Function<Long, T> andThen(Function<? super Boolean, ? extends T> after) {
/* 241 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Long2ByteFunction andThenByte(Boolean2ByteFunction after) {
/* 245 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2BooleanFunction composeByte(Byte2LongFunction before) {
/* 249 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2ShortFunction andThenShort(Boolean2ShortFunction after) {
/* 253 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2BooleanFunction composeShort(Short2LongFunction before) {
/* 257 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2IntFunction andThenInt(Boolean2IntFunction after) {
/* 261 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2BooleanFunction composeInt(Int2LongFunction before) {
/* 265 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2LongFunction andThenLong(Boolean2LongFunction after) {
/* 269 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2BooleanFunction composeLong(Long2LongFunction before) {
/* 273 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2CharFunction andThenChar(Boolean2CharFunction after) {
/* 277 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2BooleanFunction composeChar(Char2LongFunction before) {
/* 281 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2FloatFunction andThenFloat(Boolean2FloatFunction after) {
/* 285 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2BooleanFunction composeFloat(Float2LongFunction before) {
/* 289 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Long2DoubleFunction andThenDouble(Boolean2DoubleFunction after) {
/* 293 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2BooleanFunction composeDouble(Double2LongFunction before) {
/* 297 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Long2ObjectFunction<T> andThenObject(Boolean2ObjectFunction<? extends T> after) {
/* 301 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2BooleanFunction<T> composeObject(Object2LongFunction<? super T> before) {
/* 305 */     return k -> get(before.getLong(k));
/*     */   }
/*     */   
/*     */   default <T> Long2ReferenceFunction<T> andThenReference(Boolean2ReferenceFunction<? extends T> after) {
/* 309 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2BooleanFunction<T> composeReference(Reference2LongFunction<? super T> before) {
/* 313 */     return k -> get(before.getLong(k));
/*     */   }
/*     */   
/*     */   boolean get(long paramLong);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2BooleanFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */