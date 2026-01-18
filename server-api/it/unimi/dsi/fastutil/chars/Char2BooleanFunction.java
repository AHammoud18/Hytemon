/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
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
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2CharFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2CharFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2CharFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2CharFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2CharFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2CharFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2CharFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2BooleanFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2CharFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntPredicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Char2BooleanFunction
/*     */   extends Function<Character, Boolean>, IntPredicate
/*     */ {
/*     */   @Deprecated
/*     */   default boolean test(int operand) {
/*  71 */     return get(SafeMath.safeIntToChar(operand));
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
/*     */   default boolean put(char key, boolean value) {
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
/*     */   default boolean getOrDefault(char key, boolean defaultValue) {
/*     */     boolean v;
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
/*     */   default boolean remove(char key) {
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
/*     */   default Boolean put(Character key, Boolean value) {
/* 133 */     char k = key.charValue();
/* 134 */     boolean containsKey = containsKey(k);
/* 135 */     boolean v = put(k, value.booleanValue());
/* 136 */     return containsKey ? Boolean.valueOf(v) : null;
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
/* 147 */     if (key == null) return null; 
/* 148 */     char k = ((Character)key).charValue(); boolean v; return ((
/*     */       
/* 150 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Boolean.valueOf(v) : null;
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
/* 161 */     if (key == null) return defaultValue; 
/* 162 */     char k = ((Character)key).charValue();
/* 163 */     boolean v = get(k);
/* 164 */     return (v != defaultReturnValue() || containsKey(k)) ? Boolean.valueOf(v) : defaultValue;
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
/* 175 */     if (key == null) return null; 
/* 176 */     char k = ((Character)key).charValue();
/* 177 */     return containsKey(k) ? Boolean.valueOf(remove(k)) : null;
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
/*     */   default boolean containsKey(char key) {
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
/* 203 */     return (key == null) ? false : containsKey(((Character)key).charValue());
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
/*     */   default boolean defaultReturnValue() {
/* 230 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, Boolean> compose(Function<? super T, ? extends Character> before) {
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
/*     */   default <T> Function<Character, T> andThen(Function<? super Boolean, ? extends T> after) {
/* 252 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Char2ByteFunction andThenByte(Boolean2ByteFunction after) {
/* 256 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2BooleanFunction composeByte(Byte2CharFunction before) {
/* 260 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2ShortFunction andThenShort(Boolean2ShortFunction after) {
/* 264 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2BooleanFunction composeShort(Short2CharFunction before) {
/* 268 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2IntFunction andThenInt(Boolean2IntFunction after) {
/* 272 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2BooleanFunction composeInt(Int2CharFunction before) {
/* 276 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2LongFunction andThenLong(Boolean2LongFunction after) {
/* 280 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2BooleanFunction composeLong(Long2CharFunction before) {
/* 284 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2CharFunction andThenChar(Boolean2CharFunction after) {
/* 288 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2BooleanFunction composeChar(Char2CharFunction before) {
/* 292 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2FloatFunction andThenFloat(Boolean2FloatFunction after) {
/* 296 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2BooleanFunction composeFloat(Float2CharFunction before) {
/* 300 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2DoubleFunction andThenDouble(Boolean2DoubleFunction after) {
/* 304 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2BooleanFunction composeDouble(Double2CharFunction before) {
/* 308 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Char2ObjectFunction<T> andThenObject(Boolean2ObjectFunction<? extends T> after) {
/* 312 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2BooleanFunction<T> composeObject(Object2CharFunction<? super T> before) {
/* 316 */     return k -> get(before.getChar(k));
/*     */   }
/*     */   
/*     */   default <T> Char2ReferenceFunction<T> andThenReference(Boolean2ReferenceFunction<? extends T> after) {
/* 320 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2BooleanFunction<T> composeReference(Reference2CharFunction<? super T> before) {
/* 324 */     return k -> get(before.getChar(k));
/*     */   }
/*     */   
/*     */   boolean get(char paramChar);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2BooleanFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */