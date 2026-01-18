/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.PrimitiveIterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface DoubleIterator
/*     */   extends PrimitiveIterator.OfDouble
/*     */ {
/*     */   @Deprecated
/*     */   default Double next() {
/*  46 */     return Double.valueOf(nextDouble());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void forEachRemaining(DoubleConsumer action) {
/*  75 */     forEachRemaining(action);
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
/*     */   @Deprecated
/*     */   default void forEachRemaining(Consumer<? super Double> action) {
/*  88 */     Objects.requireNonNull(action); forEachRemaining((action instanceof DoubleConsumer) ? (DoubleConsumer)action : action::accept);
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
/*     */   default int skip(int n) {
/* 103 */     if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 104 */     int i = n;
/* 105 */     for (; i-- != 0 && hasNext(); nextDouble());
/* 106 */     return n - i - 1;
/*     */   }
/*     */   
/*     */   double nextDouble();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */