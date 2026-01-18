/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public interface CharIterator
/*     */   extends PrimitiveIterator<Character, CharConsumer>
/*     */ {
/*     */   @Deprecated
/*     */   default Character next() {
/*  46 */     return Character.valueOf(nextChar());
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
/*     */   default void forEachRemaining(CharConsumer action) {
/*  62 */     Objects.requireNonNull(action);
/*  63 */     while (hasNext()) {
/*  64 */       action.accept(nextChar());
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
/*  81 */     Objects.requireNonNull(action); forEachRemaining((action instanceof CharConsumer) ? (CharConsumer)action : action::accept);
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
/*     */   default void forEachRemaining(Consumer<? super Character> action) {
/*  94 */     Objects.requireNonNull(action); forEachRemaining((action instanceof CharConsumer) ? (CharConsumer)action : action::accept);
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
/* 111 */     for (; i-- != 0 && hasNext(); nextChar());
/* 112 */     return n - i - 1;
/*     */   }
/*     */   
/*     */   char nextChar();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */