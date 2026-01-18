/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2CharFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2IntFunction;
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
/*     */ import it.unimi.dsi.fastutil.doubles.Double2IntFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2CharFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2IntFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2CharFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2CharFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2CharFunction;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2IntFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2CharFunction;
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
/*     */ @FunctionalInterface
/*     */ public interface Int2CharFunction
/*     */   extends Function<Integer, Character>, IntUnaryOperator
/*     */ {
/*     */   default int applyAsInt(int operand) {
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
/*     */   default char put(int key, char value) {
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
/*     */   default char getOrDefault(int key, char defaultValue) {
/*     */     char v;
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
/*     */   default char remove(int key) {
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
/*     */   default Character put(Integer key, Character value) {
/* 122 */     int k = key.intValue();
/* 123 */     boolean containsKey = containsKey(k);
/* 124 */     char v = put(k, value.charValue());
/* 125 */     return containsKey ? Character.valueOf(v) : null;
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
/* 136 */     if (key == null) return null; 
/* 137 */     int k = ((Integer)key).intValue(); char v; return ((
/*     */       
/* 139 */       v = get(k)) != defaultReturnValue() || containsKey(k)) ? Character.valueOf(v) : null;
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
/* 150 */     if (key == null) return defaultValue; 
/* 151 */     int k = ((Integer)key).intValue();
/* 152 */     char v = get(k);
/* 153 */     return (v != defaultReturnValue() || containsKey(k)) ? Character.valueOf(v) : defaultValue;
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
/* 164 */     if (key == null) return null; 
/* 165 */     int k = ((Integer)key).intValue();
/* 166 */     return containsKey(k) ? Character.valueOf(remove(k)) : null;
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
/*     */   default boolean containsKey(int key) {
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
/* 192 */     return (key == null) ? false : containsKey(((Integer)key).intValue());
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
/*     */   default char defaultReturnValue() {
/* 219 */     return Character.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<T, Character> compose(Function<? super T, ? extends Integer> before) {
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
/*     */   default <T> Function<Integer, T> andThen(Function<? super Character, ? extends T> after) {
/* 241 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Int2ByteFunction andThenByte(Char2ByteFunction after) {
/* 245 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Byte2CharFunction composeByte(Byte2IntFunction before) {
/* 249 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Int2ShortFunction andThenShort(Char2ShortFunction after) {
/* 253 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Short2CharFunction composeShort(Short2IntFunction before) {
/* 257 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Int2IntFunction andThenInt(Char2IntFunction after) {
/* 261 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Int2CharFunction composeInt(Int2IntFunction before) {
/* 265 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Int2LongFunction andThenLong(Char2LongFunction after) {
/* 269 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Long2CharFunction composeLong(Long2IntFunction before) {
/* 273 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Int2CharFunction andThenChar(Char2CharFunction after) {
/* 277 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Char2CharFunction composeChar(Char2IntFunction before) {
/* 281 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Int2FloatFunction andThenFloat(Char2FloatFunction after) {
/* 285 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Float2CharFunction composeFloat(Float2IntFunction before) {
/* 289 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default Int2DoubleFunction andThenDouble(Char2DoubleFunction after) {
/* 293 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default Double2CharFunction composeDouble(Double2IntFunction before) {
/* 297 */     return k -> get(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Int2ObjectFunction<T> andThenObject(Char2ObjectFunction<? extends T> after) {
/* 301 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2CharFunction<T> composeObject(Object2IntFunction<? super T> before) {
/* 305 */     return k -> get(before.getInt(k));
/*     */   }
/*     */   
/*     */   default <T> Int2ReferenceFunction<T> andThenReference(Char2ReferenceFunction<? extends T> after) {
/* 309 */     return k -> after.get(get(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2CharFunction<T> composeReference(Reference2IntFunction<? super T> before) {
/* 313 */     return k -> get(before.getInt(k));
/*     */   }
/*     */   
/*     */   char get(int paramInt);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\Int2CharFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */