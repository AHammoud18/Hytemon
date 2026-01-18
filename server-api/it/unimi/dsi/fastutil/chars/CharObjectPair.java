/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface CharObjectPair<V>
/*     */   extends Pair<Character, V>
/*     */ {
/*     */   @Deprecated
/*     */   default Character left() {
/*  38 */     return Character.valueOf(leftChar());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default CharObjectPair<V> left(char l) {
/*  49 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default CharObjectPair<V> left(Character l) {
/*  60 */     return left(l.charValue());
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
/*     */   default char firstChar() {
/*  72 */     return leftChar();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Character first() {
/*  83 */     return Character.valueOf(firstChar());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default CharObjectPair<V> first(char l) {
/*  94 */     return left(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default CharObjectPair<V> first(Character l) {
/* 105 */     return first(l.charValue());
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
/*     */   default char keyChar() {
/* 117 */     return firstChar();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Character key() {
/* 128 */     return Character.valueOf(keyChar());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default CharObjectPair<V> key(char l) {
/* 139 */     return left(l);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default CharObjectPair<V> key(Character l) {
/* 145 */     return key(l.charValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <V> CharObjectPair<V> of(char left, V right) {
/* 156 */     return new CharObjectImmutablePair<>(left, right);
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
/*     */   static <V> Comparator<CharObjectPair<V>> lexComparator() {
/* 171 */     return (x, y) -> {
/*     */         int t = Character.compare(x.leftChar(), y.leftChar());
/*     */         return (t != 0) ? t : ((Comparable<Object>)x.right()).compareTo(y.right());
/*     */       };
/*     */   }
/*     */   
/*     */   char leftChar();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharObjectPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */