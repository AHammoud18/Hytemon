/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public interface BytePredicate
/*     */   extends Predicate<Byte>, IntPredicate
/*     */ {
/*     */   @Deprecated
/*     */   default boolean test(int t) {
/*  53 */     return test(SafeMath.safeIntToByte(t));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default boolean test(Byte t) {
/*  64 */     return test(t.byteValue());
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
/*     */   default BytePredicate and(BytePredicate other) {
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
/*     */   default BytePredicate and(IntPredicate other) {
/*  92 */     Objects.requireNonNull(other); return and((other instanceof BytePredicate) ? (BytePredicate)other : other::test);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Predicate<Byte> and(Predicate<? super Byte> other) {
/* 103 */     return super.and(other);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default BytePredicate negate() {
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
/*     */   default BytePredicate or(BytePredicate other) {
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
/*     */   default BytePredicate or(IntPredicate other) {
/* 137 */     Objects.requireNonNull(other); return or((other instanceof BytePredicate) ? (BytePredicate)other : other::test);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Predicate<Byte> or(Predicate<? super Byte> other) {
/* 148 */     return super.or(other);
/*     */   }
/*     */   
/*     */   boolean test(byte paramByte);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\BytePredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */