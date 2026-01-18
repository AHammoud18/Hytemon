/*     */ package it.unimi.dsi.fastutil.booleans;
/*     */ 
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @FunctionalInterface
/*     */ public interface BooleanPredicate
/*     */   extends Predicate<Boolean>
/*     */ {
/*     */   static BooleanPredicate identity() {
/*  53 */     return b -> b;
/*     */   }
/*     */ 
/*     */   
/*     */   static BooleanPredicate negation() {
/*  58 */     return b -> !b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean test(Boolean t) {
/*  69 */     return test(t.booleanValue());
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
/*     */   default BooleanPredicate and(BooleanPredicate other) {
/*  85 */     Objects.requireNonNull(other);
/*  86 */     return t -> (test(t) && other.test(t));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Predicate<Boolean> and(Predicate<? super Boolean> other) {
/*  97 */     return super.and(other);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default BooleanPredicate negate() {
/* 103 */     return t -> !test(t);
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
/*     */   default BooleanPredicate or(BooleanPredicate other) {
/* 119 */     Objects.requireNonNull(other);
/* 120 */     return t -> (test(t) || other.test(t));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Predicate<Boolean> or(Predicate<? super Boolean> other) {
/* 131 */     return super.or(other);
/*     */   }
/*     */   
/*     */   boolean test(boolean paramBoolean);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */