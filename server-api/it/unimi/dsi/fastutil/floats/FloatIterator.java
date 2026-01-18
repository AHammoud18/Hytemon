/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public interface FloatIterator
/*     */   extends PrimitiveIterator<Float, FloatConsumer>
/*     */ {
/*     */   @Deprecated
/*     */   default Float next() {
/*  46 */     return Float.valueOf(nextFloat());
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
/*     */   default void forEachRemaining(FloatConsumer action) {
/*  62 */     Objects.requireNonNull(action);
/*  63 */     while (hasNext()) {
/*  64 */       action.accept(nextFloat());
/*     */     }
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
/*     */   default void forEachRemaining(DoubleConsumer action) {
/*  80 */     Objects.requireNonNull(action);
/*  81 */     Objects.requireNonNull(action); forEachRemaining((action instanceof FloatConsumer) ? (FloatConsumer)action : action::accept);
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
/*     */   default void forEachRemaining(Consumer<? super Float> action) {
/*  94 */     Objects.requireNonNull(action); forEachRemaining((action instanceof FloatConsumer) ? (FloatConsumer)action : action::accept);
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
/* 109 */     if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 110 */     int i = n;
/* 111 */     for (; i-- != 0 && hasNext(); nextFloat());
/* 112 */     return n - i - 1;
/*     */   }
/*     */   
/*     */   float nextFloat();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */