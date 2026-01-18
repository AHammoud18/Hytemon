/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.channel.unix.Buffer;
/*     */ import io.netty.util.internal.CleanableDirectBuffer;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EpollEventArray
/*     */ {
/*  46 */   private static final int EPOLL_EVENT_SIZE = Native.sizeofEpollEvent();
/*     */   
/*  48 */   private static final int EPOLL_DATA_OFFSET = Native.offsetofEpollData();
/*     */   
/*     */   private CleanableDirectBuffer cleanable;
/*     */   private ByteBuffer memory;
/*     */   private long memoryAddress;
/*     */   private int length;
/*     */   
/*     */   EpollEventArray(int length) {
/*  56 */     if (length < 1) {
/*  57 */       throw new IllegalArgumentException("length must be >= 1 but was " + length);
/*     */     }
/*  59 */     this.length = length;
/*  60 */     this.cleanable = Buffer.allocateDirectBufferWithNativeOrder(calculateBufferCapacity(length));
/*  61 */     this.memory = this.cleanable.buffer();
/*  62 */     this.memoryAddress = Buffer.memoryAddress(this.memory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long memoryAddress() {
/*  69 */     return this.memoryAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int length() {
/*  77 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void increase() {
/*  85 */     this.length <<= 1;
/*     */     
/*  87 */     CleanableDirectBuffer buffer = Buffer.allocateDirectBufferWithNativeOrder(calculateBufferCapacity(this.length));
/*  88 */     this.cleanable.clean();
/*  89 */     this.cleanable = buffer;
/*  90 */     this.memory = buffer.buffer();
/*  91 */     this.memoryAddress = Buffer.memoryAddress(buffer.buffer());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void free() {
/*  98 */     this.cleanable.clean();
/*  99 */     this.memoryAddress = 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int events(int index) {
/* 106 */     return getInt(index, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int fd(int index) {
/* 113 */     return getInt(index, EPOLL_DATA_OFFSET);
/*     */   }
/*     */   
/*     */   private int getInt(int index, int offset) {
/* 117 */     if (PlatformDependent.hasUnsafe()) {
/* 118 */       long n = index * EPOLL_EVENT_SIZE;
/* 119 */       return PlatformDependent.getInt(this.memoryAddress + n + offset);
/*     */     } 
/* 121 */     return this.memory.getInt(index * EPOLL_EVENT_SIZE + offset);
/*     */   }
/*     */   
/*     */   private static int calculateBufferCapacity(int capacity) {
/* 125 */     return capacity * EPOLL_EVENT_SIZE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollEventArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */