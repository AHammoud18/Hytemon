/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Function;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2CharFunction;
/*     */ import it.unimi.dsi.fastutil.bytes.Byte2ObjectFunction;
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
/*     */ import it.unimi.dsi.fastutil.doubles.Double2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2CharFunction;
/*     */ import it.unimi.dsi.fastutil.floats.Float2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2CharFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2CharFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2CharFunction;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ObjectFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToIntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface Object2CharFunction<K>
/*     */   extends Function<K, Character>, ToIntFunction<K>
/*     */ {
/*     */   default int applyAsInt(K operand) {
/*  60 */     return getChar(operand);
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
/*     */   default char put(K key, char value) {
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
/*     */   default char getOrDefault(Object key, char defaultValue) {
/*     */     char v;
/*  99 */     return ((v = getChar(key)) != defaultReturnValue() || containsKey(key)) ? v : defaultValue;
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
/*     */   default char removeChar(Object key) {
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
/*     */   default Character put(K key, Character value) {
/* 122 */     K k = key;
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
/* 136 */     Object k = key; char v; return ((
/*     */       
/* 138 */       v = getChar(k)) != defaultReturnValue() || containsKey(k)) ? Character.valueOf(v) : null;
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
/* 149 */     Object k = key;
/* 150 */     char v = getChar(k);
/* 151 */     return (v != defaultReturnValue() || containsKey(k)) ? Character.valueOf(v) : defaultValue;
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
/* 162 */     Object k = key;
/* 163 */     return containsKey(k) ? Character.valueOf(removeChar(k)) : null;
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
/* 177 */     throw new UnsupportedOperationException();
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
/* 190 */     return Character.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default <T> Function<K, T> andThen(Function<? super Character, ? extends T> after) {
/* 201 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   default Object2ByteFunction<K> andThenByte(Char2ByteFunction after) {
/* 205 */     return k -> after.get(getChar(k));
/*     */   }
/*     */   
/*     */   default Byte2CharFunction composeByte(Byte2ObjectFunction<K> before) {
/* 209 */     return k -> getChar(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2ShortFunction<K> andThenShort(Char2ShortFunction after) {
/* 213 */     return k -> after.get(getChar(k));
/*     */   }
/*     */   
/*     */   default Short2CharFunction composeShort(Short2ObjectFunction<K> before) {
/* 217 */     return k -> getChar(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2IntFunction<K> andThenInt(Char2IntFunction after) {
/* 221 */     return k -> after.get(getChar(k));
/*     */   }
/*     */   
/*     */   default Int2CharFunction composeInt(Int2ObjectFunction<K> before) {
/* 225 */     return k -> getChar(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2LongFunction<K> andThenLong(Char2LongFunction after) {
/* 229 */     return k -> after.get(getChar(k));
/*     */   }
/*     */   
/*     */   default Long2CharFunction composeLong(Long2ObjectFunction<K> before) {
/* 233 */     return k -> getChar(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2CharFunction<K> andThenChar(Char2CharFunction after) {
/* 237 */     return k -> after.get(getChar(k));
/*     */   }
/*     */   
/*     */   default Char2CharFunction composeChar(Char2ObjectFunction<K> before) {
/* 241 */     return k -> getChar(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2FloatFunction<K> andThenFloat(Char2FloatFunction after) {
/* 245 */     return k -> after.get(getChar(k));
/*     */   }
/*     */   
/*     */   default Float2CharFunction composeFloat(Float2ObjectFunction<K> before) {
/* 249 */     return k -> getChar(before.get(k));
/*     */   }
/*     */   
/*     */   default Object2DoubleFunction<K> andThenDouble(Char2DoubleFunction after) {
/* 253 */     return k -> after.get(getChar(k));
/*     */   }
/*     */   
/*     */   default Double2CharFunction composeDouble(Double2ObjectFunction<K> before) {
/* 257 */     return k -> getChar(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ObjectFunction<K, T> andThenObject(Char2ObjectFunction<? extends T> after) {
/* 261 */     return k -> after.get(getChar(k));
/*     */   }
/*     */   
/*     */   default <T> Object2CharFunction<T> composeObject(Object2ObjectFunction<? super T, ? extends K> before) {
/* 265 */     return k -> getChar(before.get(k));
/*     */   }
/*     */   
/*     */   default <T> Object2ReferenceFunction<K, T> andThenReference(Char2ReferenceFunction<? extends T> after) {
/* 269 */     return k -> after.get(getChar(k));
/*     */   }
/*     */   
/*     */   default <T> Reference2CharFunction<T> composeReference(Reference2ObjectFunction<? super T, ? extends K> before) {
/* 273 */     return k -> getChar(before.get(k));
/*     */   }
/*     */   
/*     */   char getChar(Object paramObject);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2CharFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */