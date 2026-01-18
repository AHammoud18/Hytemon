/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Objects;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface CharSpliterator
/*     */   extends Spliterator.OfPrimitive<Character, CharConsumer, CharSpliterator>
/*     */ {
/*     */   @Deprecated
/*     */   default boolean tryAdvance(Consumer<? super Character> action) {
/*  41 */     Objects.requireNonNull(action); return tryAdvance((action instanceof CharConsumer) ? (CharConsumer)action : action::accept);
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
/*     */   @Deprecated
/*     */   default void forEachRemaining(Consumer<? super Character> action) {
/*  58 */     Objects.requireNonNull(action); forEachRemaining((action instanceof CharConsumer) ? (CharConsumer)action : action::accept);
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
/*     */   default long skip(long n) {
/*  78 */     if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  79 */     long i = n;
/*  80 */     while (i-- != 0L && tryAdvance(unused -> {
/*     */         
/*     */         }));
/*  83 */     return n - i - 1L;
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
/*     */   default CharComparator getComparator() {
/* 103 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   CharSpliterator trySplit();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharSpliterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */