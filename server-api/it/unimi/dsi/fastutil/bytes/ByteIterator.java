/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public interface ByteIterator
/*     */   extends PrimitiveIterator<Byte, ByteConsumer>
/*     */ {
/*     */   @Deprecated
/*     */   default Byte next() {
/*  46 */     return Byte.valueOf(nextByte());
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
/*     */   default void forEachRemaining(ByteConsumer action) {
/*  62 */     Objects.requireNonNull(action);
/*  63 */     while (hasNext()) {
/*  64 */       action.accept(nextByte());
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
/*  81 */     Objects.requireNonNull(action); forEachRemaining((action instanceof ByteConsumer) ? (ByteConsumer)action : action::accept);
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
/*     */   default void forEachRemaining(Consumer<? super Byte> action) {
/*  94 */     Objects.requireNonNull(action); forEachRemaining((action instanceof ByteConsumer) ? (ByteConsumer)action : action::accept);
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
/* 111 */     for (; i-- != 0 && hasNext(); nextByte());
/* 112 */     return n - i - 1;
/*     */   }
/*     */   
/*     */   byte nextByte();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */