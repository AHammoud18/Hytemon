/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.NettyRuntime;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.concurrent.FastThreadLocalThread;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.ThreadExecutorMap;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public class PooledByteBufAllocator
/*     */   extends AbstractByteBufAllocator
/*     */   implements ByteBufAllocatorMetricProvider
/*     */ {
/*  40 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PooledByteBufAllocator.class);
/*     */   
/*     */   private static final int DEFAULT_NUM_HEAP_ARENA;
/*     */   
/*     */   private static final int DEFAULT_NUM_DIRECT_ARENA;
/*     */   
/*     */   private static final int DEFAULT_PAGE_SIZE;
/*     */   private static final int DEFAULT_MAX_ORDER;
/*     */   private static final int DEFAULT_SMALL_CACHE_SIZE;
/*     */   private static final int DEFAULT_NORMAL_CACHE_SIZE;
/*     */   static final int DEFAULT_MAX_CACHED_BUFFER_CAPACITY;
/*     */   private static final int DEFAULT_CACHE_TRIM_INTERVAL;
/*     */   private static final long DEFAULT_CACHE_TRIM_INTERVAL_MILLIS;
/*     */   private static final boolean DEFAULT_USE_CACHE_FOR_ALL_THREADS;
/*     */   private static final int DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT;
/*     */   static final int DEFAULT_MAX_CACHED_BYTEBUFFERS_PER_CHUNK;
/*     */   private static final boolean DEFAULT_DISABLE_CACHE_FINALIZERS_FOR_FAST_THREAD_LOCAL_THREADS;
/*     */   private static final int MIN_PAGE_SIZE = 4096;
/*     */   private static final int MAX_CHUNK_SIZE = 1073741824;
/*     */   private static final int CACHE_NOT_USED = 0;
/*     */   
/*  61 */   private final Runnable trimTask = new Runnable()
/*     */     {
/*     */       public void run() {
/*  64 */         PooledByteBufAllocator.this.trimCurrentThreadCache();
/*     */       }
/*     */     };
/*     */   public static final PooledByteBufAllocator DEFAULT;
/*     */   static {
/*  69 */     int defaultAlignment = SystemPropertyUtil.getInt("io.netty.allocator.directMemoryCacheAlignment", 0);
/*     */     
/*  71 */     int defaultPageSize = SystemPropertyUtil.getInt("io.netty.allocator.pageSize", 8192);
/*  72 */     Throwable pageSizeFallbackCause = null;
/*     */     try {
/*  74 */       validateAndCalculatePageShifts(defaultPageSize, defaultAlignment);
/*  75 */     } catch (Throwable t) {
/*  76 */       pageSizeFallbackCause = t;
/*  77 */       defaultPageSize = 8192;
/*  78 */       defaultAlignment = 0;
/*     */     } 
/*  80 */     DEFAULT_PAGE_SIZE = defaultPageSize;
/*  81 */     DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT = defaultAlignment;
/*     */     
/*  83 */     int defaultMaxOrder = SystemPropertyUtil.getInt("io.netty.allocator.maxOrder", 9);
/*  84 */     Throwable maxOrderFallbackCause = null;
/*     */     try {
/*  86 */       validateAndCalculateChunkSize(DEFAULT_PAGE_SIZE, defaultMaxOrder);
/*  87 */     } catch (Throwable t) {
/*  88 */       maxOrderFallbackCause = t;
/*  89 */       defaultMaxOrder = 9;
/*     */     } 
/*  91 */     DEFAULT_MAX_ORDER = defaultMaxOrder;
/*     */ 
/*     */ 
/*     */     
/*  95 */     Runtime runtime = Runtime.getRuntime();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     int defaultMinNumArena = NettyRuntime.availableProcessors() * 2;
/* 105 */     int defaultChunkSize = DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER;
/* 106 */     DEFAULT_NUM_HEAP_ARENA = Math.max(0, 
/* 107 */         SystemPropertyUtil.getInt("io.netty.allocator.numHeapArenas", 
/*     */           
/* 109 */           (int)Math.min(defaultMinNumArena, runtime
/*     */             
/* 111 */             .maxMemory() / defaultChunkSize / 2L / 3L)));
/* 112 */     DEFAULT_NUM_DIRECT_ARENA = Math.max(0, 
/* 113 */         SystemPropertyUtil.getInt("io.netty.allocator.numDirectArenas", 
/*     */           
/* 115 */           (int)Math.min(defaultMinNumArena, 
/*     */             
/* 117 */             PlatformDependent.maxDirectMemory() / defaultChunkSize / 2L / 3L)));
/*     */ 
/*     */     
/* 120 */     DEFAULT_SMALL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.smallCacheSize", 256);
/* 121 */     DEFAULT_NORMAL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.normalCacheSize", 64);
/*     */ 
/*     */ 
/*     */     
/* 125 */     DEFAULT_MAX_CACHED_BUFFER_CAPACITY = SystemPropertyUtil.getInt("io.netty.allocator.maxCachedBufferCapacity", 32768);
/*     */ 
/*     */ 
/*     */     
/* 129 */     DEFAULT_CACHE_TRIM_INTERVAL = SystemPropertyUtil.getInt("io.netty.allocator.cacheTrimInterval", 8192);
/*     */ 
/*     */     
/* 132 */     if (SystemPropertyUtil.contains("io.netty.allocation.cacheTrimIntervalMillis")) {
/* 133 */       logger.warn("-Dio.netty.allocation.cacheTrimIntervalMillis is deprecated, use -Dio.netty.allocator.cacheTrimIntervalMillis");
/*     */ 
/*     */       
/* 136 */       if (SystemPropertyUtil.contains("io.netty.allocator.cacheTrimIntervalMillis")) {
/*     */         
/* 138 */         DEFAULT_CACHE_TRIM_INTERVAL_MILLIS = SystemPropertyUtil.getLong("io.netty.allocator.cacheTrimIntervalMillis", 0L);
/*     */       } else {
/*     */         
/* 141 */         DEFAULT_CACHE_TRIM_INTERVAL_MILLIS = SystemPropertyUtil.getLong("io.netty.allocation.cacheTrimIntervalMillis", 0L);
/*     */       } 
/*     */     } else {
/*     */       
/* 145 */       DEFAULT_CACHE_TRIM_INTERVAL_MILLIS = SystemPropertyUtil.getLong("io.netty.allocator.cacheTrimIntervalMillis", 0L);
/*     */     } 
/*     */ 
/*     */     
/* 149 */     DEFAULT_USE_CACHE_FOR_ALL_THREADS = SystemPropertyUtil.getBoolean("io.netty.allocator.useCacheForAllThreads", false);
/*     */ 
/*     */     
/* 152 */     DEFAULT_DISABLE_CACHE_FINALIZERS_FOR_FAST_THREAD_LOCAL_THREADS = SystemPropertyUtil.getBoolean("io.netty.allocator.disableCacheFinalizersForFastThreadLocalThreads", false);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     DEFAULT_MAX_CACHED_BYTEBUFFERS_PER_CHUNK = SystemPropertyUtil.getInt("io.netty.allocator.maxCachedByteBuffersPerChunk", 1023);
/*     */ 
/*     */     
/* 160 */     if (logger.isDebugEnabled()) {
/* 161 */       logger.debug("-Dio.netty.allocator.numHeapArenas: {}", Integer.valueOf(DEFAULT_NUM_HEAP_ARENA));
/* 162 */       logger.debug("-Dio.netty.allocator.numDirectArenas: {}", Integer.valueOf(DEFAULT_NUM_DIRECT_ARENA));
/* 163 */       if (pageSizeFallbackCause == null) {
/* 164 */         logger.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE));
/*     */       } else {
/* 166 */         logger.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE), pageSizeFallbackCause);
/*     */       } 
/* 168 */       if (maxOrderFallbackCause == null) {
/* 169 */         logger.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(DEFAULT_MAX_ORDER));
/*     */       } else {
/* 171 */         logger.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(DEFAULT_MAX_ORDER), maxOrderFallbackCause);
/*     */       } 
/* 173 */       logger.debug("-Dio.netty.allocator.chunkSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER));
/* 174 */       logger.debug("-Dio.netty.allocator.smallCacheSize: {}", Integer.valueOf(DEFAULT_SMALL_CACHE_SIZE));
/* 175 */       logger.debug("-Dio.netty.allocator.normalCacheSize: {}", Integer.valueOf(DEFAULT_NORMAL_CACHE_SIZE));
/* 176 */       logger.debug("-Dio.netty.allocator.maxCachedBufferCapacity: {}", Integer.valueOf(DEFAULT_MAX_CACHED_BUFFER_CAPACITY));
/* 177 */       logger.debug("-Dio.netty.allocator.cacheTrimInterval: {}", Integer.valueOf(DEFAULT_CACHE_TRIM_INTERVAL));
/* 178 */       logger.debug("-Dio.netty.allocator.cacheTrimIntervalMillis: {}", Long.valueOf(DEFAULT_CACHE_TRIM_INTERVAL_MILLIS));
/* 179 */       logger.debug("-Dio.netty.allocator.useCacheForAllThreads: {}", Boolean.valueOf(DEFAULT_USE_CACHE_FOR_ALL_THREADS));
/* 180 */       logger.debug("-Dio.netty.allocator.maxCachedByteBuffersPerChunk: {}", 
/* 181 */           Integer.valueOf(DEFAULT_MAX_CACHED_BYTEBUFFERS_PER_CHUNK));
/* 182 */       logger.debug("-Dio.netty.allocator.disableCacheFinalizersForFastThreadLocalThreads: {}", 
/* 183 */           Boolean.valueOf(DEFAULT_DISABLE_CACHE_FINALIZERS_FOR_FAST_THREAD_LOCAL_THREADS));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 188 */     DEFAULT = new PooledByteBufAllocator(!PlatformDependent.isExplicitNoPreferDirect());
/*     */   }
/*     */   private final PoolArena<byte[]>[] heapArenas;
/*     */   private final PoolArena<ByteBuffer>[] directArenas;
/*     */   private final int smallCacheSize;
/*     */   private final int normalCacheSize;
/*     */   private final List<PoolArenaMetric> heapArenaMetrics;
/*     */   private final List<PoolArenaMetric> directArenaMetrics;
/*     */   private final PoolThreadLocalCache threadCache;
/*     */   private final int chunkSize;
/*     */   private final PooledByteBufAllocatorMetric metric;
/*     */   
/*     */   public PooledByteBufAllocator() {
/* 201 */     this(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public PooledByteBufAllocator(boolean preferDirect) {
/* 206 */     this(preferDirect, DEFAULT_NUM_HEAP_ARENA, DEFAULT_NUM_DIRECT_ARENA, DEFAULT_PAGE_SIZE, DEFAULT_MAX_ORDER);
/*     */   }
/*     */ 
/*     */   
/*     */   public PooledByteBufAllocator(int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
/* 211 */     this(false, nHeapArena, nDirectArena, pageSize, maxOrder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
/* 220 */     this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, 0, DEFAULT_SMALL_CACHE_SIZE, DEFAULT_NORMAL_CACHE_SIZE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize, int smallCacheSize, int normalCacheSize) {
/* 231 */     this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, smallCacheSize, normalCacheSize, DEFAULT_USE_CACHE_FOR_ALL_THREADS, DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT);
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
/*     */   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize, int smallCacheSize, int normalCacheSize, boolean useCacheForAllThreads) {
/* 244 */     this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, smallCacheSize, normalCacheSize, useCacheForAllThreads);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int smallCacheSize, int normalCacheSize, boolean useCacheForAllThreads) {
/* 253 */     this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, smallCacheSize, normalCacheSize, useCacheForAllThreads, DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT);
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
/*     */   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize, int smallCacheSize, int normalCacheSize, boolean useCacheForAllThreads, int directMemoryCacheAlignment) {
/* 266 */     this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, smallCacheSize, normalCacheSize, useCacheForAllThreads, directMemoryCacheAlignment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int smallCacheSize, int normalCacheSize, boolean useCacheForAllThreads, int directMemoryCacheAlignment) {
/* 274 */     super(preferDirect);
/* 275 */     this.threadCache = new PoolThreadLocalCache(useCacheForAllThreads);
/* 276 */     this.smallCacheSize = smallCacheSize;
/* 277 */     this.normalCacheSize = normalCacheSize;
/*     */     
/* 279 */     if (directMemoryCacheAlignment != 0) {
/* 280 */       if (!PlatformDependent.hasAlignDirectByteBuffer()) {
/* 281 */         throw new UnsupportedOperationException("Buffer alignment is not supported. Either Unsafe or ByteBuffer.alignSlice() must be available.");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 286 */       pageSize = (int)PlatformDependent.align(pageSize, directMemoryCacheAlignment);
/*     */     } 
/*     */     
/* 289 */     this.chunkSize = validateAndCalculateChunkSize(pageSize, maxOrder);
/*     */     
/* 291 */     ObjectUtil.checkPositiveOrZero(nHeapArena, "nHeapArena");
/* 292 */     ObjectUtil.checkPositiveOrZero(nDirectArena, "nDirectArena");
/*     */     
/* 294 */     ObjectUtil.checkPositiveOrZero(directMemoryCacheAlignment, "directMemoryCacheAlignment");
/* 295 */     if (directMemoryCacheAlignment > 0 && !isDirectMemoryCacheAlignmentSupported()) {
/* 296 */       throw new IllegalArgumentException("directMemoryCacheAlignment is not supported");
/*     */     }
/*     */     
/* 299 */     if ((directMemoryCacheAlignment & -directMemoryCacheAlignment) != directMemoryCacheAlignment) {
/* 300 */       throw new IllegalArgumentException("directMemoryCacheAlignment: " + directMemoryCacheAlignment + " (expected: power of two)");
/*     */     }
/*     */ 
/*     */     
/* 304 */     int pageShifts = validateAndCalculatePageShifts(pageSize, directMemoryCacheAlignment);
/*     */     
/* 306 */     if (nHeapArena > 0) {
/* 307 */       this.heapArenas = newArenaArray(nHeapArena);
/* 308 */       List<PoolArenaMetric> metrics = new ArrayList<>(this.heapArenas.length);
/* 309 */       SizeClasses sizeClasses = new SizeClasses(pageSize, pageShifts, this.chunkSize, 0);
/* 310 */       for (int i = 0; i < this.heapArenas.length; i++) {
/* 311 */         PoolArena.HeapArena arena = new PoolArena.HeapArena(this, sizeClasses);
/* 312 */         this.heapArenas[i] = arena;
/* 313 */         metrics.add(arena);
/*     */       } 
/* 315 */       this.heapArenaMetrics = Collections.unmodifiableList(metrics);
/*     */     } else {
/* 317 */       this.heapArenas = null;
/* 318 */       this.heapArenaMetrics = Collections.emptyList();
/*     */     } 
/*     */     
/* 321 */     if (nDirectArena > 0) {
/* 322 */       this.directArenas = newArenaArray(nDirectArena);
/* 323 */       List<PoolArenaMetric> metrics = new ArrayList<>(this.directArenas.length);
/* 324 */       SizeClasses sizeClasses = new SizeClasses(pageSize, pageShifts, this.chunkSize, directMemoryCacheAlignment);
/*     */       
/* 326 */       for (int i = 0; i < this.directArenas.length; i++) {
/* 327 */         PoolArena.DirectArena arena = new PoolArena.DirectArena(this, sizeClasses);
/* 328 */         this.directArenas[i] = arena;
/* 329 */         metrics.add(arena);
/*     */       } 
/* 331 */       this.directArenaMetrics = Collections.unmodifiableList(metrics);
/*     */     } else {
/* 333 */       this.directArenas = null;
/* 334 */       this.directArenaMetrics = Collections.emptyList();
/*     */     } 
/* 336 */     this.metric = new PooledByteBufAllocatorMetric(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> PoolArena<T>[] newArenaArray(int size) {
/* 341 */     return (PoolArena<T>[])new PoolArena[size];
/*     */   }
/*     */   
/*     */   private static int validateAndCalculatePageShifts(int pageSize, int alignment) {
/* 345 */     if (pageSize < 4096) {
/* 346 */       throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: " + 'က' + ')');
/*     */     }
/*     */     
/* 349 */     if ((pageSize & pageSize - 1) != 0) {
/* 350 */       throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: power of 2)");
/*     */     }
/*     */     
/* 353 */     if (pageSize < alignment) {
/* 354 */       throw new IllegalArgumentException("Alignment cannot be greater than page size. Alignment: " + alignment + ", page size: " + pageSize + '.');
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 359 */     return 31 - Integer.numberOfLeadingZeros(pageSize);
/*     */   }
/*     */   
/*     */   private static int validateAndCalculateChunkSize(int pageSize, int maxOrder) {
/* 363 */     if (maxOrder > 14) {
/* 364 */       throw new IllegalArgumentException("maxOrder: " + maxOrder + " (expected: 0-14)");
/*     */     }
/*     */ 
/*     */     
/* 368 */     int chunkSize = pageSize;
/* 369 */     for (int i = maxOrder; i > 0; i--) {
/* 370 */       if (chunkSize > 536870912)
/* 371 */         throw new IllegalArgumentException(String.format("pageSize (%d) << maxOrder (%d) must not exceed %d", new Object[] {
/* 372 */                 Integer.valueOf(pageSize), Integer.valueOf(maxOrder), Integer.valueOf(1073741824)
/*     */               })); 
/* 374 */       chunkSize <<= 1;
/*     */     } 
/* 376 */     return chunkSize;
/*     */   }
/*     */   
/*     */   protected ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
/*     */     AbstractByteBuf buf;
/* 381 */     PoolThreadCache cache = (PoolThreadCache)this.threadCache.get();
/* 382 */     PoolArena<byte[]> heapArena = cache.heapArena;
/*     */ 
/*     */     
/* 385 */     if (heapArena != null) {
/* 386 */       buf = heapArena.allocate(cache, initialCapacity, maxCapacity);
/*     */     }
/*     */     else {
/*     */       
/* 390 */       buf = PlatformDependent.hasUnsafe() ? new UnpooledUnsafeHeapByteBuf(this, initialCapacity, maxCapacity) : new UnpooledHeapByteBuf(this, initialCapacity, maxCapacity);
/* 391 */       onAllocateBuffer(buf, false, false);
/*     */     } 
/* 393 */     return toLeakAwareBuffer(buf);
/*     */   }
/*     */   
/*     */   protected ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
/*     */     AbstractByteBuf buf;
/* 398 */     PoolThreadCache cache = (PoolThreadCache)this.threadCache.get();
/* 399 */     PoolArena<ByteBuffer> directArena = cache.directArena;
/*     */ 
/*     */     
/* 402 */     if (directArena != null) {
/* 403 */       buf = directArena.allocate(cache, initialCapacity, maxCapacity);
/*     */     }
/*     */     else {
/*     */       
/* 407 */       buf = PlatformDependent.hasUnsafe() ? UnsafeByteBufUtil.newUnsafeDirectByteBuf(this, initialCapacity, maxCapacity, true) : new UnpooledDirectByteBuf(this, initialCapacity, maxCapacity, true);
/* 408 */       onAllocateBuffer(buf, false, false);
/*     */     } 
/* 410 */     return toLeakAwareBuffer(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int defaultNumHeapArena() {
/* 417 */     return DEFAULT_NUM_HEAP_ARENA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int defaultNumDirectArena() {
/* 424 */     return DEFAULT_NUM_DIRECT_ARENA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int defaultPageSize() {
/* 431 */     return DEFAULT_PAGE_SIZE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int defaultMaxOrder() {
/* 438 */     return DEFAULT_MAX_ORDER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean defaultDisableCacheFinalizersForFastThreadLocalThreads() {
/* 446 */     return DEFAULT_DISABLE_CACHE_FINALIZERS_FOR_FAST_THREAD_LOCAL_THREADS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean defaultUseCacheForAllThreads() {
/* 453 */     return DEFAULT_USE_CACHE_FOR_ALL_THREADS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean defaultPreferDirect() {
/* 460 */     return PlatformDependent.directBufferPreferred();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static int defaultTinyCacheSize() {
/* 470 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int defaultSmallCacheSize() {
/* 477 */     return DEFAULT_SMALL_CACHE_SIZE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int defaultNormalCacheSize() {
/* 484 */     return DEFAULT_NORMAL_CACHE_SIZE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDirectMemoryCacheAlignmentSupported() {
/* 491 */     return PlatformDependent.hasUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirectBufferPooled() {
/* 496 */     return (this.directArenas != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean hasThreadLocalCache() {
/* 506 */     return this.threadCache.isSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void freeThreadLocalCache() {
/* 515 */     this.threadCache.remove();
/*     */   }
/*     */   
/*     */   private final class PoolThreadLocalCache extends FastThreadLocal<PoolThreadCache> {
/*     */     private final boolean useCacheForAllThreads;
/*     */     
/*     */     PoolThreadLocalCache(boolean useCacheForAllThreads) {
/* 522 */       this.useCacheForAllThreads = useCacheForAllThreads;
/*     */     }
/*     */     
/*     */     protected synchronized PoolThreadCache initialValue()
/*     */     {
/* 527 */       PoolArena<byte[]> heapArena = (PoolArena)leastUsedArena((PoolArena[])PooledByteBufAllocator.this.heapArenas);
/* 528 */       PoolArena<ByteBuffer> directArena = leastUsedArena(PooledByteBufAllocator.this.directArenas);
/*     */       
/* 530 */       Thread current = Thread.currentThread();
/* 531 */       EventExecutor executor = ThreadExecutorMap.currentExecutor();
/*     */       
/* 533 */       if (!this.useCacheForAllThreads)
/*     */       {
/* 535 */         if (!FastThreadLocalThread.currentThreadHasFastThreadLocal() && executor == null)
/*     */         {
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
/* 552 */           return new PoolThreadCache(heapArena, directArena, 0, 0, 0, 0, false); }  } 
/*     */       PoolThreadCache cache = new PoolThreadCache(heapArena, directArena, PooledByteBufAllocator.this.smallCacheSize, PooledByteBufAllocator.this.normalCacheSize, PooledByteBufAllocator.DEFAULT_MAX_CACHED_BUFFER_CAPACITY, PooledByteBufAllocator.DEFAULT_CACHE_TRIM_INTERVAL, PooledByteBufAllocator.useCacheFinalizers());
/*     */       if (PooledByteBufAllocator.DEFAULT_CACHE_TRIM_INTERVAL_MILLIS > 0L && executor != null)
/*     */         executor.scheduleAtFixedRate(PooledByteBufAllocator.this.trimTask, PooledByteBufAllocator.DEFAULT_CACHE_TRIM_INTERVAL_MILLIS, PooledByteBufAllocator.DEFAULT_CACHE_TRIM_INTERVAL_MILLIS, TimeUnit.MILLISECONDS); 
/*     */       return cache; } protected void onRemoval(PoolThreadCache threadCache) {
/* 557 */       threadCache.free(false);
/*     */     }
/*     */     
/*     */     private <T> PoolArena<T> leastUsedArena(PoolArena<T>[] arenas) {
/* 561 */       if (arenas == null || arenas.length == 0) {
/* 562 */         return null;
/*     */       }
/*     */       
/* 565 */       PoolArena<T> minArena = arenas[0];
/*     */ 
/*     */       
/* 568 */       if (minArena.numThreadCaches.get() == 0) {
/* 569 */         return minArena;
/*     */       }
/* 571 */       for (int i = 1; i < arenas.length; i++) {
/* 572 */         PoolArena<T> arena = arenas[i];
/* 573 */         if (arena.numThreadCaches.get() < minArena.numThreadCaches.get()) {
/* 574 */           minArena = arena;
/*     */         }
/*     */       } 
/*     */       
/* 578 */       return minArena;
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean useCacheFinalizers() {
/* 583 */     if (!defaultDisableCacheFinalizersForFastThreadLocalThreads()) {
/* 584 */       return true;
/*     */     }
/* 586 */     return FastThreadLocalThread.currentThreadWillCleanupFastThreadLocals();
/*     */   }
/*     */ 
/*     */   
/*     */   public PooledByteBufAllocatorMetric metric() {
/* 591 */     return this.metric;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int numHeapArenas() {
/* 601 */     return this.heapArenaMetrics.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int numDirectArenas() {
/* 611 */     return this.directArenaMetrics.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<PoolArenaMetric> heapArenas() {
/* 621 */     return this.heapArenaMetrics;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<PoolArenaMetric> directArenas() {
/* 631 */     return this.directArenaMetrics;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int numThreadLocalCaches() {
/* 641 */     return Math.max(numThreadLocalCaches((PoolArena<?>[])this.heapArenas), numThreadLocalCaches((PoolArena<?>[])this.directArenas));
/*     */   }
/*     */   
/*     */   private static int numThreadLocalCaches(PoolArena<?>[] arenas) {
/* 645 */     if (arenas == null) {
/* 646 */       return 0;
/*     */     }
/*     */     
/* 649 */     int total = 0;
/* 650 */     for (PoolArena<?> arena : arenas) {
/* 651 */       total += arena.numThreadCaches.get();
/*     */     }
/*     */     
/* 654 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int tinyCacheSize() {
/* 664 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int smallCacheSize() {
/* 674 */     return this.smallCacheSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int normalCacheSize() {
/* 684 */     return this.normalCacheSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final int chunkSize() {
/* 694 */     return this.chunkSize;
/*     */   }
/*     */   
/*     */   final long usedHeapMemory() {
/* 698 */     return usedMemory((PoolArena<?>[])this.heapArenas);
/*     */   }
/*     */   
/*     */   final long usedDirectMemory() {
/* 702 */     return usedMemory((PoolArena<?>[])this.directArenas);
/*     */   }
/*     */   
/*     */   private static long usedMemory(PoolArena<?>[] arenas) {
/* 706 */     if (arenas == null) {
/* 707 */       return -1L;
/*     */     }
/* 709 */     long used = 0L;
/* 710 */     for (PoolArena<?> arena : arenas) {
/* 711 */       used += arena.numActiveBytes();
/* 712 */       if (used < 0L) {
/* 713 */         return Long.MAX_VALUE;
/*     */       }
/*     */     } 
/* 716 */     return used;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long pinnedHeapMemory() {
/* 726 */     return pinnedMemory((PoolArena<?>[])this.heapArenas);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long pinnedDirectMemory() {
/* 736 */     return pinnedMemory((PoolArena<?>[])this.directArenas);
/*     */   }
/*     */   
/*     */   private static long pinnedMemory(PoolArena<?>[] arenas) {
/* 740 */     if (arenas == null) {
/* 741 */       return -1L;
/*     */     }
/* 743 */     long used = 0L;
/* 744 */     for (PoolArena<?> arena : arenas) {
/* 745 */       used += arena.numPinnedBytes();
/* 746 */       if (used < 0L) {
/* 747 */         return Long.MAX_VALUE;
/*     */       }
/*     */     } 
/* 750 */     return used;
/*     */   }
/*     */   
/*     */   final PoolThreadCache threadCache() {
/* 754 */     PoolThreadCache cache = (PoolThreadCache)this.threadCache.get();
/* 755 */     assert cache != null;
/* 756 */     return cache;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean trimCurrentThreadCache() {
/* 766 */     PoolThreadCache cache = (PoolThreadCache)this.threadCache.getIfExists();
/* 767 */     if (cache != null) {
/* 768 */       cache.trim();
/* 769 */       return true;
/*     */     } 
/* 771 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String dumpStats() {
/* 779 */     int heapArenasLen = (this.heapArenas == null) ? 0 : this.heapArenas.length;
/*     */ 
/*     */ 
/*     */     
/* 783 */     StringBuilder buf = (new StringBuilder(512)).append(heapArenasLen).append(" heap arena(s):").append(StringUtil.NEWLINE);
/* 784 */     if (heapArenasLen > 0) {
/* 785 */       for (PoolArena<byte[]> a : this.heapArenas) {
/* 786 */         buf.append(a);
/*     */       }
/*     */     }
/*     */     
/* 790 */     int directArenasLen = (this.directArenas == null) ? 0 : this.directArenas.length;
/*     */     
/* 792 */     buf.append(directArenasLen)
/* 793 */       .append(" direct arena(s):")
/* 794 */       .append(StringUtil.NEWLINE);
/* 795 */     if (directArenasLen > 0) {
/* 796 */       for (PoolArena<ByteBuffer> a : this.directArenas) {
/* 797 */         buf.append(a);
/*     */       }
/*     */     }
/*     */     
/* 801 */     return buf.toString();
/*     */   }
/*     */   
/*     */   static void onAllocateBuffer(AbstractByteBuf buf, boolean pooled, boolean threadLocal) {
/* 805 */     if (PlatformDependent.isJfrEnabled() && AllocateBufferEvent.isEventEnabled()) {
/* 806 */       AllocateBufferEvent event = new AllocateBufferEvent();
/* 807 */       if (event.shouldCommit()) {
/* 808 */         event.fill(buf, (Class)PooledByteBufAllocator.class);
/* 809 */         event.chunkPooled = pooled;
/* 810 */         event.chunkThreadLocal = threadLocal;
/* 811 */         event.commit();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void onDeallocateBuffer(AbstractByteBuf buf) {
/* 817 */     if (PlatformDependent.isJfrEnabled() && FreeBufferEvent.isEventEnabled()) {
/* 818 */       FreeBufferEvent event = new FreeBufferEvent();
/* 819 */       if (event.shouldCommit()) {
/* 820 */         event.fill(buf, (Class)PooledByteBufAllocator.class);
/* 821 */         event.commit();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void onReallocateBuffer(AbstractByteBuf buf, int newCapacity) {
/* 827 */     if (PlatformDependent.isJfrEnabled() && ReallocateBufferEvent.isEventEnabled()) {
/* 828 */       ReallocateBufferEvent event = new ReallocateBufferEvent();
/* 829 */       if (event.shouldCommit()) {
/* 830 */         event.fill(buf, (Class)PooledByteBufAllocator.class);
/* 831 */         event.newCapacity = newCapacity;
/* 832 */         event.commit();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void onAllocateChunk(ChunkInfo chunk, boolean pooled) {
/* 838 */     if (PlatformDependent.isJfrEnabled() && AllocateChunkEvent.isEventEnabled()) {
/* 839 */       AllocateChunkEvent event = new AllocateChunkEvent();
/* 840 */       if (event.shouldCommit()) {
/* 841 */         event.fill(chunk, (Class)PooledByteBufAllocator.class);
/* 842 */         event.pooled = pooled;
/* 843 */         event.threadLocal = false;
/* 844 */         event.commit();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void onDeallocateChunk(ChunkInfo chunk, boolean pooled) {
/* 850 */     if (PlatformDependent.isJfrEnabled() && FreeChunkEvent.isEventEnabled()) {
/* 851 */       FreeChunkEvent event = new FreeChunkEvent();
/* 852 */       if (event.shouldCommit()) {
/* 853 */         event.fill(chunk, (Class)PooledByteBufAllocator.class);
/* 854 */         event.pooled = pooled;
/* 855 */         event.commit();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PooledByteBufAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */