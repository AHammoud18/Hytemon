/*     */ package io.netty.channel.kqueue;
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
/*     */ final class KQueueEventArray
/*     */ {
/*  39 */   private static final int KQUEUE_EVENT_SIZE = Native.sizeofKEvent();
/*  40 */   private static final int KQUEUE_IDENT_OFFSET = Native.offsetofKEventIdent();
/*  41 */   private static final int KQUEUE_FILTER_OFFSET = Native.offsetofKEventFilter();
/*  42 */   private static final int KQUEUE_FFLAGS_OFFSET = Native.offsetofKEventFFlags();
/*  43 */   private static final int KQUEUE_FLAGS_OFFSET = Native.offsetofKEventFlags();
/*  44 */   private static final int KQUEUE_DATA_OFFSET = Native.offsetofKeventData();
/*  45 */   private static final int KQUEUE_UDATA_OFFSET = Native.offsetofKeventUdata();
/*     */   
/*     */   private CleanableDirectBuffer memoryCleanable;
/*     */   private ByteBuffer memory;
/*     */   private long memoryAddress;
/*     */   private int size;
/*     */   private int capacity;
/*     */   
/*     */   KQueueEventArray(int capacity) {
/*  54 */     if (capacity < 1) {
/*  55 */       throw new IllegalArgumentException("capacity must be >= 1 but was " + capacity);
/*     */     }
/*  57 */     this.memoryCleanable = Buffer.allocateDirectBufferWithNativeOrder(calculateBufferCapacity(capacity));
/*  58 */     this.memory = this.memoryCleanable.buffer();
/*  59 */     this.memoryAddress = Buffer.memoryAddress(this.memory);
/*  60 */     this.capacity = capacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long memoryAddress() {
/*  67 */     return this.memoryAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int capacity() {
/*  75 */     return this.capacity;
/*     */   }
/*     */   
/*     */   int size() {
/*  79 */     return this.size;
/*     */   }
/*     */   
/*     */   void clear() {
/*  83 */     this.size = 0;
/*     */   }
/*     */   
/*     */   void evSet(int ident, short filter, short flags, int fflags, long data, long udata) {
/*  87 */     reallocIfNeeded();
/*  88 */     evSet(getKEventOffset(this.size++) + this.memoryAddress, ident, filter, flags, fflags, data, udata);
/*     */   }
/*     */   
/*     */   private void reallocIfNeeded() {
/*  92 */     if (this.size == this.capacity) {
/*  93 */       realloc(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void realloc(boolean throwIfFail) {
/* 102 */     int newLength = (this.capacity <= 65536) ? (this.capacity << 1) : (this.capacity + this.capacity >> 1);
/*     */     
/*     */     try {
/* 105 */       int newCapacity = calculateBufferCapacity(newLength);
/* 106 */       CleanableDirectBuffer buffer = Buffer.allocateDirectBufferWithNativeOrder(newCapacity);
/*     */ 
/*     */       
/* 109 */       this.memory.position(0).limit(this.size);
/* 110 */       buffer.buffer().put(this.memory);
/* 111 */       buffer.buffer().position(0);
/*     */       
/* 113 */       this.memoryCleanable.clean();
/* 114 */       this.memoryCleanable = buffer;
/* 115 */       this.memory = buffer.buffer();
/* 116 */       this.memoryAddress = Buffer.memoryAddress(this.memory);
/* 117 */     } catch (OutOfMemoryError e) {
/* 118 */       if (throwIfFail) {
/* 119 */         OutOfMemoryError error = new OutOfMemoryError("unable to allocate " + newLength + " new bytes! Existing capacity is: " + this.capacity);
/*     */         
/* 121 */         error.initCause(e);
/* 122 */         throw error;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void free() {
/* 131 */     this.memoryCleanable.clean();
/* 132 */     this.memoryAddress = (this.size = this.capacity = 0);
/*     */   }
/*     */   
/*     */   private static int getKEventOffset(int index) {
/* 136 */     return index * KQUEUE_EVENT_SIZE;
/*     */   }
/*     */   
/*     */   private long getKEventOffsetAddress(int index) {
/* 140 */     return getKEventOffset(index) + this.memoryAddress;
/*     */   }
/*     */   
/*     */   private short getShort(int index, int offset) {
/* 144 */     if (PlatformDependent.hasUnsafe()) {
/* 145 */       return PlatformDependent.getShort(getKEventOffsetAddress(index) + offset);
/*     */     }
/* 147 */     return this.memory.getShort(getKEventOffset(index) + offset);
/*     */   }
/*     */   
/*     */   short flags(int index) {
/* 151 */     return getShort(index, KQUEUE_FLAGS_OFFSET);
/*     */   }
/*     */   
/*     */   short filter(int index) {
/* 155 */     return getShort(index, KQUEUE_FILTER_OFFSET);
/*     */   }
/*     */   
/*     */   short fflags(int index) {
/* 159 */     return getShort(index, KQUEUE_FFLAGS_OFFSET);
/*     */   }
/*     */   
/*     */   int ident(int index) {
/* 163 */     if (PlatformDependent.hasUnsafe()) {
/* 164 */       return PlatformDependent.getInt(getKEventOffsetAddress(index) + KQUEUE_IDENT_OFFSET);
/*     */     }
/* 166 */     return this.memory.getInt(getKEventOffset(index) + KQUEUE_IDENT_OFFSET);
/*     */   }
/*     */   
/*     */   long data(int index) {
/* 170 */     return getLong(index, KQUEUE_DATA_OFFSET);
/*     */   }
/*     */   
/*     */   long udata(int index) {
/* 174 */     return getLong(index, KQUEUE_UDATA_OFFSET);
/*     */   }
/*     */   
/*     */   private long getLong(int index, int offset) {
/* 178 */     if (PlatformDependent.hasUnsafe()) {
/* 179 */       return PlatformDependent.getLong(getKEventOffsetAddress(index) + offset);
/*     */     }
/* 181 */     return this.memory.getLong(getKEventOffset(index) + offset);
/*     */   }
/*     */   
/*     */   private static int calculateBufferCapacity(int capacity) {
/* 185 */     return capacity * KQUEUE_EVENT_SIZE;
/*     */   }
/*     */   
/*     */   private static native void evSet(long paramLong1, int paramInt1, short paramShort1, short paramShort2, int paramInt2, long paramLong2, long paramLong3);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueEventArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */