/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public final class AdaptiveByteBufAllocator
/*     */   extends AbstractByteBufAllocator
/*     */   implements ByteBufAllocatorMetricProvider, ByteBufAllocatorMetric
/*     */ {
/*  33 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AdaptiveByteBufAllocator.class);
/*     */ 
/*     */ 
/*     */   
/*  37 */   private static final boolean DEFAULT_USE_CACHED_MAGAZINES_FOR_NON_EVENT_LOOP_THREADS = SystemPropertyUtil.getBoolean("io.netty.allocator.useCachedMagazinesForNonEventLoopThreads", false);
/*     */   static {
/*  39 */     logger.debug("-Dio.netty.allocator.useCachedMagazinesForNonEventLoopThreads: {}", 
/*  40 */         Boolean.valueOf(DEFAULT_USE_CACHED_MAGAZINES_FOR_NON_EVENT_LOOP_THREADS));
/*     */   }
/*     */   
/*     */   private final AdaptivePoolingAllocator direct;
/*     */   private final AdaptivePoolingAllocator heap;
/*     */   
/*     */   public AdaptiveByteBufAllocator() {
/*  47 */     this(!PlatformDependent.isExplicitNoPreferDirect());
/*     */   }
/*     */   
/*     */   public AdaptiveByteBufAllocator(boolean preferDirect) {
/*  51 */     this(preferDirect, DEFAULT_USE_CACHED_MAGAZINES_FOR_NON_EVENT_LOOP_THREADS);
/*     */   }
/*     */   
/*     */   public AdaptiveByteBufAllocator(boolean preferDirect, boolean useCacheForNonEventLoopThreads) {
/*  55 */     super(preferDirect);
/*  56 */     this.direct = new AdaptivePoolingAllocator(new DirectChunkAllocator(this), useCacheForNonEventLoopThreads);
/*  57 */     this.heap = new AdaptivePoolingAllocator(new HeapChunkAllocator(this), useCacheForNonEventLoopThreads);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
/*  62 */     return toLeakAwareBuffer(this.heap.allocate(initialCapacity, maxCapacity));
/*     */   }
/*     */ 
/*     */   
/*     */   protected ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
/*  67 */     return toLeakAwareBuffer(this.direct.allocate(initialCapacity, maxCapacity));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirectBufferPooled() {
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public long usedHeapMemory() {
/*  77 */     return this.heap.usedMemory();
/*     */   }
/*     */ 
/*     */   
/*     */   public long usedDirectMemory() {
/*  82 */     return this.direct.usedMemory();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBufAllocatorMetric metric() {
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   private static final class HeapChunkAllocator implements AdaptivePoolingAllocator.ChunkAllocator {
/*     */     private final ByteBufAllocator allocator;
/*     */     
/*     */     private HeapChunkAllocator(ByteBufAllocator allocator) {
/*  94 */       this.allocator = allocator;
/*     */     }
/*     */ 
/*     */     
/*     */     public AbstractByteBuf allocate(int initialCapacity, int maxCapacity) {
/*  99 */       return PlatformDependent.hasUnsafe() ? 
/* 100 */         new UnpooledUnsafeHeapByteBuf(this.allocator, initialCapacity, maxCapacity) : 
/* 101 */         new UnpooledHeapByteBuf(this.allocator, initialCapacity, maxCapacity);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DirectChunkAllocator implements AdaptivePoolingAllocator.ChunkAllocator {
/*     */     private final ByteBufAllocator allocator;
/*     */     
/*     */     private DirectChunkAllocator(ByteBufAllocator allocator) {
/* 109 */       this.allocator = allocator;
/*     */     }
/*     */ 
/*     */     
/*     */     public AbstractByteBuf allocate(int initialCapacity, int maxCapacity) {
/* 114 */       return PlatformDependent.hasUnsafe() ? 
/* 115 */         UnsafeByteBufUtil.newUnsafeDirectByteBuf(this.allocator, initialCapacity, maxCapacity, false) : 
/* 116 */         new UnpooledDirectByteBuf(this.allocator, initialCapacity, maxCapacity, false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\AdaptiveByteBufAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */