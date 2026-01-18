/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2CharFunction;
/*     */ import it.unimi.dsi.fastutil.doubles.Double2CharFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2CharFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2CharFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2CharFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2CharFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2CharFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2CharFunction;
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
/*     */ public interface Char2CharFunction
/*     */   extends Function<Character, Character>, IntUnaryOperator
/*     */ {
/*     */   @Deprecated
/*     */   default int applyAsInt(int operand) {
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
/*     */   default char put(char key, char value) {
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
/*     */   default char getOrDefault(char key, char defaultValue) {
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
/*     */   default char remove(char key) {
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
/*     */   default Character put(Character key, Character value) {
/* 133 */     char k = key.charValue();
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
/* 148 */     char k = ((Character)key).charValue(); char v; return ((
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
/* 162 */     char k = ((Character)key).charValue();
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
/* 176 */     char k = ((Character)key).charValue();
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
/*     */   static Char2CharFunction identity() {
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
/*     */   default <T> Function<T, Character> compose(Function<? super T, ? extends Character> before) {
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
/*     */   default <T> Function<Character, T> andThen(Function<? super Character, ? extends T> after) {
/* 256 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Char2ByteFunction andThenByte(Char2ByteFunction after) {
/* 260 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2CharFunction composeByte(Byte2CharFunction before) {
/* 264 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2ShortFunction andThenShort(Char2ShortFunction after) {
/* 268 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2CharFunction composeShort(Short2CharFunction before) {
/* 272 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2IntFunction andThenInt(Char2IntFunction after) {
/* 276 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2CharFunction composeInt(Int2CharFunction before) {
/* 280 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2LongFunction andThenLong(Char2LongFunction after) {
/* 284 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2CharFunction composeLong(Long2CharFunction before) {
/* 288 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2CharFunction andThenChar(Char2CharFunction after) {
/* 292 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2CharFunction composeChar(Char2CharFunction before) {
/* 296 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2FloatFunction andThenFloat(Char2FloatFunction after) {
/* 300 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2CharFunction composeFloat(Float2CharFunction before) {
/* 304 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Char2DoubleFunction andThenDouble(Char2DoubleFunction after) {
/* 308 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2CharFunction composeDouble(Double2CharFunction before) {
/* 312 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Char2ObjectFunction<T> andThenObject(Char2ObjectFunction<? extends T> after) {
/* 316 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2CharFunction<T> composeObject(Object2CharFunction<? super T> before) {
/* 320 */     return k -> get(before.getChar(k));
/*     */   }
/*     */   
/*     */   default <T> Char2ReferenceFunction<T> andThenReference(Char2ReferenceFunction<? extends T> after) {
/* 324 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2CharFunction<T> composeReference(Reference2CharFunction<? super T> before) {
/* 328 */     return k -> get(before.getChar(k));
/*     */   }
/*     */   
/*     */   char get(char paramChar);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2CharFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */