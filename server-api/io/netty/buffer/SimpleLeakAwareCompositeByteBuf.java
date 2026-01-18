/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.ResourceLeakTracker;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.nio.ByteOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SimpleLeakAwareCompositeByteBuf
/*     */   extends WrappedCompositeByteBuf
/*     */ {
/*     */   final ResourceLeakTracker<ByteBuf> leak;
/*     */   
/*     */   SimpleLeakAwareCompositeByteBuf(CompositeByteBuf wrapped, ResourceLeakTracker<ByteBuf> leak) {
/*  29 */     super(wrapped);
/*  30 */     this.leak = (ResourceLeakTracker<ByteBuf>)ObjectUtil.checkNotNull(leak, "leak");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean release() {
/*  37 */     ByteBuf unwrapped = unwrap();
/*  38 */     if (super.release()) {
/*  39 */       closeLeak(unwrapped);
/*  40 */       return true;
/*     */     } 
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/*  49 */     ByteBuf unwrapped = unwrap();
/*  50 */     if (super.release(decrement)) {
/*  51 */       closeLeak(unwrapped);
/*  52 */       return true;
/*     */     } 
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeLeak(ByteBuf trackedByteBuf) {
/*  60 */     boolean closed = this.leak.close(trackedByteBuf);
/*  61 */     assert closed;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf order(ByteOrder endianness) {
/*  66 */     if (order() == endianness) {
/*  67 */       return this;
/*     */     }
/*  69 */     return newLeakAwareByteBuf(super.order(endianness));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuf slice() {
/*  75 */     return newLeakAwareByteBuf(super.slice());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedSlice() {
/*  80 */     return newLeakAwareByteBuf(super.retainedSlice());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice(int index, int length) {
/*  85 */     return newLeakAwareByteBuf(super.slice(index, length));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedSlice(int index, int length) {
/*  90 */     return newLeakAwareByteBuf(super.retainedSlice(index, length));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf duplicate() {
/*  95 */     return newLeakAwareByteBuf(super.duplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retainedDuplicate() {
/* 100 */     return newLeakAwareByteBuf(super.retainedDuplicate());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readSlice(int length) {
/* 105 */     return newLeakAwareByteBuf(super.readSlice(length));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readRetainedSlice(int length) {
/* 110 */     return newLeakAwareByteBuf(super.readRetainedSlice(length));
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf asReadOnly() {
/* 115 */     return newLeakAwareByteBuf(super.asReadOnly());
/*     */   }
/*     */   
/*     */   private SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf wrapped) {
/* 119 */     return newLeakAwareByteBuf(wrapped, unwrap(), this.leak);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf wrapped, ByteBuf trackedByteBuf, ResourceLeakTracker<ByteBuf> leakTracker) {
/* 124 */     return new SimpleLeakAwareByteBuf(wrapped, trackedByteBuf, leakTracker);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\SimpleLeakAwareCompositeByteBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */