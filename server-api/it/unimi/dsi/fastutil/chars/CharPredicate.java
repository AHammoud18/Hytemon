/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.util.Objects;
/*     */ import java.util.function.IntPredicate;
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface CharPredicate
/*     */   extends Predicate<Character>, IntPredicate
/*     */ {
/*     */   @Deprecated
/*     */   default boolean test(int t) {
/*  53 */     return test(SafeMath.safeIntToChar(t));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean test(Character t) {
/*  64 */     return test(t.charValue());
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
/*     */   default CharPredicate and(CharPredicate other) {
/*  80 */     Objects.requireNonNull(other);
/*  81 */     return t -> (test(t) && other.test(t));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default CharPredicate and(IntPredicate other) {
/*  92 */     Objects.requireNonNull(other); return and((other instanceof CharPredicate) ? (CharPredicate)other : other::test);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Predicate<Character> and(Predicate<? super Character> other) {
/* 103 */     return super.and(other);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default CharPredicate negate() {
/* 109 */     return t -> !test(t);
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
/*     */   default CharPredicate or(CharPredicate other) {
/* 125 */     Objects.requireNonNull(other);
/* 126 */     return t -> (test(t) || other.test(t));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default CharPredicate or(IntPredicate other) {
/* 137 */     Objects.requireNonNull(other); return or((other instanceof CharPredicate) ? (CharPredicate)other : other::test);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Predicate<Character> or(Predicate<? super Character> other) {
/* 148 */     return super.or(other);
/*     */   }
/*     */   
/*     */   boolean test(char paramChar);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */