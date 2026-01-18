/*     */ package io.netty.channel.unix;
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
/*     */ 
/*     */ public final class PreferredDirectByteBufAllocator
/*     */   implements ByteBufAllocator
/*     */ {
/*     */   private ByteBufAllocator allocator;
/*     */   
/*     */   public void updateAllocator(ByteBufAllocator allocator) {
/*  28 */     this.allocator = allocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf buffer() {
/*  33 */     return this.allocator.directBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf buffer(int initialCapacity) {
/*  38 */     return this.allocator.directBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf buffer(int initialCapacity, int maxCapacity) {
/*  43 */     return this.allocator.directBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ioBuffer() {
/*  48 */     return this.allocator.directBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ioBuffer(int initialCapacity) {
/*  53 */     return this.allocator.directBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ioBuffer(int initialCapacity, int maxCapacity) {
/*  58 */     return this.allocator.directBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf heapBuffer() {
/*  63 */     return this.allocator.heapBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf heapBuffer(int initialCapacity) {
/*  68 */     return this.allocator.heapBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf heapBuffer(int initialCapacity, int maxCapacity) {
/*  73 */     return this.allocator.heapBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf directBuffer() {
/*  78 */     return this.allocator.directBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf directBuffer(int initialCapacity) {
/*  83 */     return this.allocator.directBuffer(initialCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
/*  88 */     return this.allocator.directBuffer(initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeBuffer() {
/*  93 */     return this.allocator.compositeDirectBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeBuffer(int maxNumComponents) {
/*  98 */     return this.allocator.compositeDirectBuffer(maxNumComponents);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeHeapBuffer() {
/* 103 */     return this.allocator.compositeHeapBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeHeapBuffer(int maxNumComponents) {
/* 108 */     return this.allocator.compositeHeapBuffer(maxNumComponents);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeDirectBuffer() {
/* 113 */     return this.allocator.compositeDirectBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeDirectBuffer(int maxNumComponents) {
/* 118 */     return this.allocator.compositeDirectBuffer(maxNumComponents);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirectBufferPooled() {
/* 123 */     return this.allocator.isDirectBufferPooled();
/*     */   }
/*     */ 
/*     */   
/*     */   public int calculateNewCapacity(int minNewCapacity, int maxCapacity) {
/* 128 */     return this.allocator.calculateNewCapacity(minNewCapacity, maxCapacity);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\PreferredDirectByteBufAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */