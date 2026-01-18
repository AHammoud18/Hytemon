/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.CleanableDirectBuffer;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.concurrent.atomic.LongAdder;
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
/*     */ public final class UnpooledByteBufAllocator
/*     */   extends AbstractByteBufAllocator
/*     */   implements ByteBufAllocatorMetricProvider
/*     */ {
/*  30 */   private final UnpooledByteBufAllocatorMetric metric = new UnpooledByteBufAllocatorMetric();
/*     */ 
/*     */   
/*     */   private final boolean disableLeakDetector;
/*     */   
/*     */   private final boolean noCleaner;
/*     */   
/*  37 */   public static final UnpooledByteBufAllocator DEFAULT = new UnpooledByteBufAllocator(
/*  38 */       PlatformDependent.directBufferPreferred());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnpooledByteBufAllocator(boolean preferDirect) {
/*  47 */     this(preferDirect, false);
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
/*     */   public UnpooledByteBufAllocator(boolean preferDirect, boolean disableLeakDetector) {
/*  60 */     this(preferDirect, disableLeakDetector, PlatformDependent.useDirectBufferNoCleaner());
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
/*     */   public UnpooledByteBufAllocator(boolean preferDirect, boolean disableLeakDetector, boolean tryNoCleaner) {
/*  75 */     super(preferDirect);
/*  76 */     this.disableLeakDetector = disableLeakDetector;
/*  77 */     this
/*  78 */       .noCleaner = (tryNoCleaner && PlatformDependent.hasUnsafe() && PlatformDependent.hasDirectBufferNoCleanerConstructor());
/*     */   }
/*     */ 
/*     */   
/*     */   protected ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
/*  83 */     return PlatformDependent.hasUnsafe() ? 
/*  84 */       new InstrumentedUnpooledUnsafeHeapByteBuf(this, initialCapacity, maxCapacity) : 
/*  85 */       new InstrumentedUnpooledHeapByteBuf(this, initialCapacity, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
/*     */     ByteBuf buf;
/*  91 */     if (PlatformDependent.hasUnsafe()) {
/*     */       
/*  93 */       buf = this.noCleaner ? new InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf(this, initialCapacity, maxCapacity) : new InstrumentedUnpooledUnsafeDirectByteBuf(this, initialCapacity, maxCapacity);
/*     */     } else {
/*  95 */       buf = new InstrumentedUnpooledDirectByteBuf(this, initialCapacity, maxCapacity);
/*     */     } 
/*  97 */     return this.disableLeakDetector ? buf : toLeakAwareBuffer(buf);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeHeapBuffer(int maxNumComponents) {
/* 102 */     CompositeByteBuf buf = new CompositeByteBuf(this, false, maxNumComponents);
/* 103 */     return this.disableLeakDetector ? buf : toLeakAwareBuffer(buf);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeByteBuf compositeDirectBuffer(int maxNumComponents) {
/* 108 */     CompositeByteBuf buf = new CompositeByteBuf(this, true, maxNumComponents);
/* 109 */     return this.disableLeakDetector ? buf : toLeakAwareBuffer(buf);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirectBufferPooled() {
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocatorMetric metric() {
/* 119 */     return this.metric;
/*     */   }
/*     */   
/*     */   void incrementDirect(int amount) {
/* 123 */     this.metric.directCounter.add(amount);
/*     */   }
/*     */   
/*     */   void decrementDirect(int amount) {
/* 127 */     this.metric.directCounter.add(-amount);
/*     */   }
/*     */   
/*     */   void incrementHeap(int amount) {
/* 131 */     this.metric.heapCounter.add(amount);
/*     */   }
/*     */   
/*     */   void decrementHeap(int amount) {
/* 135 */     this.metric.heapCounter.add(-amount);
/*     */   }
/*     */   
/*     */   private static final class InstrumentedUnpooledUnsafeHeapByteBuf extends UnpooledUnsafeHeapByteBuf {
/*     */     InstrumentedUnpooledUnsafeHeapByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
/* 140 */       super(alloc, initialCapacity, maxCapacity);
/*     */     }
/*     */ 
/*     */     
/*     */     protected byte[] allocateArray(int initialCapacity) {
/* 145 */       byte[] bytes = super.allocateArray(initialCapacity);
/* 146 */       ((UnpooledByteBufAllocator)alloc()).incrementHeap(bytes.length);
/* 147 */       return bytes;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void freeArray(byte[] array) {
/* 152 */       int length = array.length;
/* 153 */       super.freeArray(array);
/* 154 */       ((UnpooledByteBufAllocator)alloc()).decrementHeap(length);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class InstrumentedUnpooledHeapByteBuf extends UnpooledHeapByteBuf {
/*     */     InstrumentedUnpooledHeapByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
/* 160 */       super(alloc, initialCapacity, maxCapacity);
/*     */     }
/*     */ 
/*     */     
/*     */     protected byte[] allocateArray(int initialCapacity) {
/* 165 */       byte[] bytes = super.allocateArray(initialCapacity);
/* 166 */       ((UnpooledByteBufAllocator)alloc()).incrementHeap(bytes.length);
/* 167 */       return bytes;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void freeArray(byte[] array) {
/* 172 */       int length = array.length;
/* 173 */       super.freeArray(array);
/* 174 */       ((UnpooledByteBufAllocator)alloc()).decrementHeap(length);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf
/*     */     extends UnpooledUnsafeNoCleanerDirectByteBuf
/*     */   {
/*     */     InstrumentedUnpooledUnsafeNoCleanerDirectByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
/* 182 */       super(alloc, initialCapacity, maxCapacity, true);
/*     */     }
/*     */ 
/*     */     
/*     */     protected CleanableDirectBuffer allocateDirectBuffer(int capacity) {
/* 187 */       CleanableDirectBuffer buffer = super.allocateDirectBuffer(capacity);
/* 188 */       return new UnpooledByteBufAllocator.DecrementingCleanableDirectBuffer(alloc(), buffer);
/*     */     }
/*     */ 
/*     */     
/*     */     CleanableDirectBuffer reallocateDirect(CleanableDirectBuffer oldBuffer, int initialCapacity) {
/* 193 */       int capacity = oldBuffer.buffer().capacity();
/* 194 */       CleanableDirectBuffer buffer = super.reallocateDirect(oldBuffer, initialCapacity);
/* 195 */       return new UnpooledByteBufAllocator.DecrementingCleanableDirectBuffer(alloc(), buffer, buffer.buffer().capacity() - capacity);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class InstrumentedUnpooledUnsafeDirectByteBuf
/*     */     extends UnpooledUnsafeDirectByteBuf {
/*     */     InstrumentedUnpooledUnsafeDirectByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
/* 202 */       super(alloc, initialCapacity, maxCapacity);
/*     */     }
/*     */ 
/*     */     
/*     */     protected CleanableDirectBuffer allocateDirectBuffer(int capacity) {
/* 207 */       CleanableDirectBuffer buffer = super.allocateDirectBuffer(capacity);
/* 208 */       return new UnpooledByteBufAllocator.DecrementingCleanableDirectBuffer(alloc(), buffer);
/*     */     }
/*     */ 
/*     */     
/*     */     protected ByteBuffer allocateDirect(int initialCapacity) {
/* 213 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void freeDirect(ByteBuffer buffer) {
/* 218 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class InstrumentedUnpooledDirectByteBuf
/*     */     extends UnpooledDirectByteBuf {
/*     */     InstrumentedUnpooledDirectByteBuf(UnpooledByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
/* 225 */       super(alloc, initialCapacity, maxCapacity);
/*     */     }
/*     */ 
/*     */     
/*     */     protected CleanableDirectBuffer allocateDirectBuffer(int initialCapacity) {
/* 230 */       CleanableDirectBuffer buffer = super.allocateDirectBuffer(initialCapacity);
/* 231 */       return new UnpooledByteBufAllocator.DecrementingCleanableDirectBuffer(alloc(), buffer);
/*     */     }
/*     */ 
/*     */     
/*     */     protected ByteBuffer allocateDirect(int initialCapacity) {
/* 236 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void freeDirect(ByteBuffer buffer) {
/* 241 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DecrementingCleanableDirectBuffer
/*     */     implements CleanableDirectBuffer {
/*     */     private final UnpooledByteBufAllocator alloc;
/*     */     private final CleanableDirectBuffer delegate;
/*     */     
/*     */     private DecrementingCleanableDirectBuffer(ByteBufAllocator alloc, CleanableDirectBuffer delegate) {
/* 251 */       this(alloc, delegate, delegate.buffer().capacity());
/*     */     }
/*     */ 
/*     */     
/*     */     private DecrementingCleanableDirectBuffer(ByteBufAllocator alloc, CleanableDirectBuffer delegate, int capacityConsumed) {
/* 256 */       this.alloc = (UnpooledByteBufAllocator)alloc;
/* 257 */       this.alloc.incrementDirect(capacityConsumed);
/* 258 */       this.delegate = delegate;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteBuffer buffer() {
/* 263 */       return this.delegate.buffer();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clean() {
/* 268 */       int capacity = this.delegate.buffer().capacity();
/* 269 */       this.delegate.clean();
/* 270 */       this.alloc.decrementDirect(capacity);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasMemoryAddress() {
/* 275 */       return this.delegate.hasMemoryAddress();
/*     */     }
/*     */ 
/*     */     
/*     */     public long memoryAddress() {
/* 280 */       return this.delegate.memoryAddress();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class UnpooledByteBufAllocatorMetric implements ByteBufAllocatorMetric {
/* 285 */     final LongAdder directCounter = new LongAdder();
/* 286 */     final LongAdder heapCounter = new LongAdder();
/*     */ 
/*     */     
/*     */     public long usedHeapMemory() {
/* 290 */       return this.heapCounter.sum();
/*     */     }
/*     */ 
/*     */     
/*     */     public long usedDirectMemory() {
/* 295 */       return this.directCounter.sum();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 300 */       return StringUtil.simpleClassName(this) + "(usedHeapMemory: " + 
/* 301 */         usedHeapMemory() + "; usedDirectMemory: " + usedDirectMemory() + ')';
/*     */     }
/*     */     
/*     */     private UnpooledByteBufAllocatorMetric() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\UnpooledByteBufAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */