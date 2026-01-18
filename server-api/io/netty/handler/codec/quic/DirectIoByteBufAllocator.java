/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.CompositeByteBuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DirectIoByteBufAllocator
/*     */   implements ByteBufAllocator
/*     */ {
/*     */   private final ByteBufAllocator wrapped;
/*     */   
/*     */   DirectIoByteBufAllocator(ByteBufAllocator wrapped) {
/*  27 */     if (wrapped instanceof DirectIoByteBufAllocator) {
/*  28 */       wrapped = ((DirectIoByteBufAllocator)wrapped).wrapped();
/*     */     }
/*  30 */     this.wrapped = wrapped;
/*     */   }
/*     */   
/*     */   ByteBufAllocator wrapped() {
/*  34 */     return this.wrapped;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf buffer() {
/*  39 */     return this.wrapped.buffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf buffer(int initialCapacity) {
/*  44 */     return this.wrapped.buffer(initialCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf buffer(int initialCapacity, int maxCapacity) {
/*  49 */     return this.wrapped.buffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ioBuffer() {
/*  54 */     return directBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ioBuffer(int initialCapacity) {
/*  59 */     return directBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ioBuffer(int initialCapacity, int maxCapacity) {
/*  64 */     return directBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf heapBuffer() {
/*  69 */     return this.wrapped.heapBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf heapBuffer(int initialCapacity) {
/*  74 */     return this.wrapped.heapBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf heapBuffer(int initialCapacity, int maxCapacity) {
/*  79 */     return this.wrapped.heapBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf directBuffer() {
/*  84 */     return this.wrapped.directBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf directBuffer(int initialCapacity) {
/*  89 */     return this.wrapped.directBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
/*  94 */     return this.wrapped.directBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeBuffer() {
/*  99 */     return this.wrapped.compositeBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeBuffer(int maxNumComponents) {
/* 104 */     return this.wrapped.compositeBuffer(maxNumComponents);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeHeapBuffer() {
/* 109 */     return this.wrapped.compositeHeapBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeHeapBuffer(int maxNumComponents) {
/* 114 */     return this.wrapped.compositeHeapBuffer(maxNumComponents);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeDirectBuffer() {
/* 119 */     return this.wrapped.compositeDirectBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeDirectBuffer(int maxNumComponents) {
/* 124 */     return this.wrapped.compositeDirectBuffer(maxNumComponents);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirectBufferPooled() {
/* 129 */     return this.wrapped.isDirectBufferPooled();
/*     */   }
/*     */ 
/*     */   
/*     */   public int calculateNewCapacity(int minNewCapacity, int maxCapacity) {
/* 134 */     return this.wrapped.calculateNewCapacity(minNewCapacity, maxCapacity);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\DirectIoByteBufAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */