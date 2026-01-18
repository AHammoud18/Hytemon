/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.channel.unix.Buffer;
/*     */ import io.netty.channel.unix.Limits;
/*     */ import io.netty.util.internal.CleanableDirectBuffer;
/*     */ import io.netty.util.internal.ObjectUtil;
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
/*     */ final class NativeLongArray
/*     */ {
/*     */   private CleanableDirectBuffer memoryCleanable;
/*     */   private ByteBuffer memory;
/*     */   private long memoryAddress;
/*     */   private int capacity;
/*     */   private int size;
/*     */   
/*     */   NativeLongArray(int capacity) {
/*  35 */     this.capacity = ObjectUtil.checkPositive(capacity, "capacity");
/*  36 */     this.memoryCleanable = Buffer.allocateDirectBufferWithNativeOrder(calculateBufferCapacity(capacity));
/*  37 */     this.memory = this.memoryCleanable.buffer();
/*  38 */     this.memoryAddress = Buffer.memoryAddress(this.memory);
/*     */   }
/*     */   
/*     */   private static int idx(int index) {
/*  42 */     return index * Limits.SIZEOF_JLONG;
/*     */   }
/*     */   
/*     */   private static int calculateBufferCapacity(int capacity) {
/*  46 */     return capacity * Limits.SIZEOF_JLONG;
/*     */   }
/*     */   
/*     */   void add(long value) {
/*  50 */     reallocIfNeeded();
/*  51 */     if (PlatformDependent.hasUnsafe()) {
/*  52 */       PlatformDependent.putLong(memoryOffset(this.size), value);
/*     */     } else {
/*  54 */       this.memory.putLong(idx(this.size), value);
/*     */     } 
/*  56 */     this.size++;
/*     */   }
/*     */   
/*     */   void clear() {
/*  60 */     this.size = 0;
/*     */   }
/*     */   
/*     */   boolean isEmpty() {
/*  64 */     return (this.size == 0);
/*     */   }
/*     */   
/*     */   int size() {
/*  68 */     return this.size;
/*     */   }
/*     */   
/*     */   void free() {
/*  72 */     this.memoryCleanable.clean();
/*  73 */     this.memoryAddress = 0L;
/*     */   }
/*     */   
/*     */   long memoryAddress() {
/*  77 */     return this.memoryAddress;
/*     */   }
/*     */   
/*     */   long memoryAddressEnd() {
/*  81 */     return memoryOffset(this.size);
/*     */   }
/*     */   
/*     */   private long memoryOffset(int index) {
/*  85 */     return this.memoryAddress + idx(index);
/*     */   }
/*     */   
/*     */   private void reallocIfNeeded() {
/*  89 */     if (this.size == this.capacity) {
/*     */       
/*  91 */       int newLength = (this.capacity <= 65536) ? (this.capacity << 1) : (this.capacity + this.capacity >> 1);
/*  92 */       int newCapacity = calculateBufferCapacity(newLength);
/*  93 */       CleanableDirectBuffer buffer = Buffer.allocateDirectBufferWithNativeOrder(newCapacity);
/*     */ 
/*     */       
/*  96 */       this.memory.position(0).limit(this.size);
/*  97 */       buffer.buffer().put(this.memory);
/*  98 */       buffer.buffer().position(0);
/*     */       
/* 100 */       this.memoryCleanable.clean();
/* 101 */       this.memoryCleanable = buffer;
/* 102 */       this.memory = buffer.buffer();
/* 103 */       this.memoryAddress = Buffer.memoryAddress(this.memory);
/* 104 */       this.capacity = newLength;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     return "memoryAddress: " + this.memoryAddress + " capacity: " + this.capacity + " size: " + this.size;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\NativeLongArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */