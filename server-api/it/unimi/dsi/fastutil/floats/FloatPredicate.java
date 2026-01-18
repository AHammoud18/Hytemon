/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.util.Objects;
/*     */ import java.util.function.DoublePredicate;
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
/*     */ public interface FloatPredicate
/*     */   extends Predicate<Float>, DoublePredicate
/*     */ {
/*     */   @Deprecated
/*     */   default boolean test(double t) {
/*  53 */     return test(SafeMath.safeDoubleToFloat(t));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean test(Float t) {
/*  64 */     return test(t.floatValue());
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
/*     */   default FloatPredicate and(FloatPredicate other) {
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
/*     */   default FloatPredicate and(DoublePredicate other) {
/*  92 */     Objects.requireNonNull(other); return and((other instanceof FloatPredicate) ? (FloatPredicate)other : other::test);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Predicate<Float> and(Predicate<? super Float> other) {
/* 103 */     return super.and(other);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default FloatPredicate negate() {
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
/*     */   default FloatPredicate or(FloatPredicate other) {
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
/*     */   default FloatPredicate or(DoublePredicate other) {
/* 137 */     Objects.requireNonNull(other); return or((other instanceof FloatPredicate) ? (FloatPredicate)other : other::test);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Predicate<Float> or(Predicate<? super Float> other) {
/* 148 */     return super.or(other);
/*     */   }
/*     */   
/*     */   boolean test(float paramFloat);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */