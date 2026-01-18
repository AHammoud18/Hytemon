/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public interface ShortPredicate
/*     */   extends Predicate<Short>, IntPredicate
/*     */ {
/*     */   @Deprecated
/*     */   default boolean test(int t) {
/*  53 */     return test(SafeMath.safeIntToShort(t));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean test(Short t) {
/*  64 */     return test(t.shortValue());
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
/*     */   default ShortPredicate and(ShortPredicate other) {
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
/*     */   default ShortPredicate and(IntPredicate other) {
/*  92 */     Objects.requireNonNull(other); return and((other instanceof ShortPredicate) ? (ShortPredicate)other : other::test);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Predicate<Short> and(Predicate<? super Short> other) {
/* 103 */     return super.and(other);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default ShortPredicate negate() {
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
/*     */   default ShortPredicate or(ShortPredicate other) {
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
/*     */   default ShortPredicate or(IntPredicate other) {
/* 137 */     Objects.requireNonNull(other); return or((other instanceof ShortPredicate) ? (ShortPredicate)other : other::test);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Predicate<Short> or(Predicate<? super Short> other) {
/* 148 */     return super.or(other);
/*     */   }
/*     */   
/*     */   boolean test(short paramShort);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */