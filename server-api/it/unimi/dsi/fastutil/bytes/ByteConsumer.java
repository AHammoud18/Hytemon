/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public interface ByteConsumer
/*     */   extends Consumer<Byte>, IntConsumer
/*     */ {
/*     */   @Deprecated
/*     */   default void accept(int t) {
/*  52 */     accept(SafeMath.safeIntToByte(t));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void accept(Byte t) {
/*  63 */     accept(t.byteValue());
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
/*     */   default ByteConsumer andThen(ByteConsumer after) {
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
/*     */   default ByteConsumer andThen(IntConsumer after) {
/*  94 */     Objects.requireNonNull(after); return andThen((after instanceof ByteConsumer) ? (ByteConsumer)after : after::accept);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Consumer<Byte> andThen(Consumer<? super Byte> after) {
/* 105 */     return super.andThen(after);
/*     */   }
/*     */   
/*     */   void accept(byte paramByte);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */