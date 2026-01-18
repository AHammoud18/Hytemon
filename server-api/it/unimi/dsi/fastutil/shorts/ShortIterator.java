/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.PrimitiveIterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.IntConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ShortIterator
/*     */   extends PrimitiveIterator<Short, ShortConsumer>
/*     */ {
/*     */   @Deprecated
/*     */   default Short next() {
/*  46 */     return Short.valueOf(nextShort());
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
/*     */   default void forEachRemaining(ShortConsumer action) {
/*  62 */     Objects.requireNonNull(action);
/*  63 */     while (hasNext()) {
/*  64 */       action.accept(nextShort());
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
/*     */   default void forEachRemaining(IntConsumer action) {
/*  80 */     Objects.requireNonNull(action);
/*  81 */     Objects.requireNonNull(action); forEachRemaining((action instanceof ShortConsumer) ? (ShortConsumer)action : action::accept);
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
/*     */   default void forEachRemaining(Consumer<? super Short> action) {
/*  94 */     Objects.requireNonNull(action); forEachRemaining((action instanceof ShortConsumer) ? (ShortConsumer)action : action::accept);
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
/* 111 */     for (; i-- != 0 && hasNext(); nextShort());
/* 112 */     return n - i - 1;
/*     */   }
/*     */   
/*     */   short nextShort();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */