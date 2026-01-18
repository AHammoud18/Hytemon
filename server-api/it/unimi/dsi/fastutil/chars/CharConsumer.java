/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @FunctionalInterface
/*     */ public interface CharConsumer
/*     */   extends Consumer<Character>, IntConsumer
/*     */ {
/*     */   @Deprecated
/*     */   default void accept(int t) {
/*  52 */     accept(SafeMath.safeIntToChar(t));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void accept(Character t) {
/*  63 */     accept(t.charValue());
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
/*     */   default CharConsumer andThen(CharConsumer after) {
/*  79 */     Objects.requireNonNull(after);
/*  80 */     return t -> {
/*     */         accept(t);
/*     */         after.accept(t);
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default CharConsumer andThen(IntConsumer after) {
/*  94 */     Objects.requireNonNull(after); return andThen((after instanceof CharConsumer) ? (CharConsumer)after : after::accept);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Consumer<Character> andThen(Consumer<? super Character> after) {
/* 105 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   void accept(char paramChar);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */