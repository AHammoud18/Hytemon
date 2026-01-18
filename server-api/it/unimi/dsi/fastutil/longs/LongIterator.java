/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.PrimitiveIterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.LongConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface LongIterator
/*     */   extends PrimitiveIterator.OfLong
/*     */ {
/*     */   @Deprecated
/*     */   default Long next() {
/*  46 */     return Long.valueOf(nextLong());
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
/*     */   default void forEachRemaining(LongConsumer action) {
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
/*     */   default void forEachRemaining(Consumer<? super Long> action) {
/*  88 */     Objects.requireNonNull(action); forEachRemaining((action instanceof LongConsumer) ? (LongConsumer)action : action::accept);
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
/* 105 */     for (; i-- != 0 && hasNext(); nextLong());
/* 106 */     return n - i - 1;
/*     */   }
/*     */   
/*     */   long nextLong();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */