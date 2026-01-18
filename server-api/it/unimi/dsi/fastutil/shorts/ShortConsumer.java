/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public interface ShortConsumer
/*     */   extends Consumer<Short>, IntConsumer
/*     */ {
/*     */   @Deprecated
/*     */   default void accept(int t) {
/*  52 */     accept(SafeMath.safeIntToShort(t));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void accept(Short t) {
/*  63 */     accept(t.shortValue());
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
/*     */   default ShortConsumer andThen(ShortConsumer after) {
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
/*     */   default ShortConsumer andThen(IntConsumer after) {
/*  94 */     Objects.requireNonNull(after); return andThen((after instanceof ShortConsumer) ? (ShortConsumer)after : after::accept);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Consumer<Short> andThen(Consumer<? super Short> after) {
/* 105 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   void accept(short paramShort);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */