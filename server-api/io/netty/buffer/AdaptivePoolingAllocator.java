/*      */ package io.netty.buffer;
/*      */ 
/*      */ import io.netty.util.ByteProcessor;
/*      */ import io.netty.util.CharsetUtil;
/*      */ import io.netty.util.IllegalReferenceCountException;
/*      */ import io.netty.util.NettyRuntime;
/*      */ import io.netty.util.Recycler;
/*      */ import io.netty.util.concurrent.FastThreadLocal;
/*      */ import io.netty.util.concurrent.FastThreadLocalThread;
/*      */ import io.netty.util.concurrent.MpscIntQueue;
/*      */ import io.netty.util.internal.ObjectPool;
/*      */ import io.netty.util.internal.ObjectUtil;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.RefCnt;
/*      */ import io.netty.util.internal.SystemPropertyUtil;
/*      */ import io.netty.util.internal.ThreadExecutorMap;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.nio.channels.GatheringByteChannel;
/*      */ import java.nio.channels.ScatteringByteChannel;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.Arrays;
/*      */ import java.util.Queue;
/*      */ import java.util.concurrent.ThreadLocalRandom;
/*      */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*      */ import java.util.concurrent.atomic.LongAdder;
/*      */ import java.util.concurrent.locks.StampedLock;
/*      */ import java.util.function.IntSupplier;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class AdaptivePoolingAllocator
/*      */ {
/*      */   private static final int LOW_MEM_THRESHOLD = 536870912;
/*   83 */   private static final boolean IS_LOW_MEM = (Runtime.getRuntime().maxMemory() <= 536870912L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   89 */   private static final boolean DISABLE_THREAD_LOCAL_MAGAZINES_ON_LOW_MEM = SystemPropertyUtil.getBoolean("io.netty.allocator.disableThreadLocalMagazinesOnLowMemory", true);
/*      */ 
/*      */   
/*      */   static final int MIN_CHUNK_SIZE = 131072;
/*      */ 
/*      */   
/*      */   private static final int EXPANSION_ATTEMPTS = 3;
/*      */ 
/*      */   
/*      */   private static final int INITIAL_MAGAZINES = 1;
/*      */ 
/*      */   
/*      */   private static final int RETIRE_CAPACITY = 256;
/*      */   
/*  103 */   private static final int MAX_STRIPES = IS_LOW_MEM ? 1 : (NettyRuntime.availableProcessors() * 2);
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int BUFS_PER_CHUNK = 8;
/*      */ 
/*      */ 
/*      */   
/*  111 */   private static final int MAX_CHUNK_SIZE = IS_LOW_MEM ? 
/*  112 */     2097152 : 
/*  113 */     8388608;
/*  114 */   private static final int MAX_POOLED_BUF_SIZE = MAX_CHUNK_SIZE / 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  121 */   private static final int CHUNK_REUSE_QUEUE = Math.max(2, SystemPropertyUtil.getInt("io.netty.allocator.chunkReuseQueueCapacity", 
/*  122 */         NettyRuntime.availableProcessors() * 2));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  128 */   private static final int MAGAZINE_BUFFER_QUEUE_CAPACITY = SystemPropertyUtil.getInt("io.netty.allocator.magazineBufferQueueCapacity", 1024);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  143 */   private static final int[] SIZE_CLASSES = new int[] { 32, 64, 128, 256, 512, 640, 1024, 1152, 2048, 2304, 4096, 4352, 8192, 8704, 16384, 16896, 32768, 65536 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  164 */   private static final int SIZE_CLASSES_COUNT = SIZE_CLASSES.length;
/*  165 */   private static final byte[] SIZE_INDEXES = new byte[SIZE_CLASSES[SIZE_CLASSES_COUNT - 1] / 32 + 1]; private final ChunkAllocator chunkAllocator;
/*      */   
/*      */   static {
/*  168 */     if (MAGAZINE_BUFFER_QUEUE_CAPACITY < 2) {
/*  169 */       throw new IllegalArgumentException("MAGAZINE_BUFFER_QUEUE_CAPACITY: " + MAGAZINE_BUFFER_QUEUE_CAPACITY + " (expected: >= " + '\002' + ')');
/*      */     }
/*      */     
/*  172 */     int lastIndex = 0;
/*  173 */     for (int i = 0; i < SIZE_CLASSES_COUNT; i++) {
/*  174 */       int sizeClass = SIZE_CLASSES[i];
/*      */       
/*  176 */       assert (sizeClass & 0x5) == 0 : "Size class must be a multiple of 32";
/*  177 */       int sizeIndex = sizeIndexOf(sizeClass);
/*  178 */       Arrays.fill(SIZE_INDEXES, lastIndex + 1, sizeIndex + 1, (byte)i);
/*  179 */       lastIndex = sizeIndex;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private final ChunkRegistry chunkRegistry;
/*      */   private final MagazineGroup[] sizeClassedMagazineGroups;
/*      */   private final MagazineGroup largeBufferMagazineGroup;
/*      */   private final FastThreadLocal<MagazineGroup[]> threadLocalGroup;
/*      */   
/*      */   AdaptivePoolingAllocator(ChunkAllocator chunkAllocator, final boolean useCacheForNonEventLoopThreads) {
/*  190 */     this.chunkAllocator = (ChunkAllocator)ObjectUtil.checkNotNull(chunkAllocator, "chunkAllocator");
/*  191 */     this.chunkRegistry = new ChunkRegistry();
/*  192 */     this.sizeClassedMagazineGroups = createMagazineGroupSizeClasses(this, false);
/*  193 */     this.largeBufferMagazineGroup = new MagazineGroup(this, chunkAllocator, new HistogramChunkControllerFactory(true), false);
/*      */ 
/*      */     
/*  196 */     boolean disableThreadLocalGroups = (IS_LOW_MEM && DISABLE_THREAD_LOCAL_MAGAZINES_ON_LOW_MEM);
/*  197 */     this.threadLocalGroup = disableThreadLocalGroups ? null : new FastThreadLocal<MagazineGroup[]>()
/*      */       {
/*      */         protected AdaptivePoolingAllocator.MagazineGroup[] initialValue() {
/*  200 */           if (useCacheForNonEventLoopThreads || ThreadExecutorMap.currentExecutor() != null) {
/*  201 */             return AdaptivePoolingAllocator.createMagazineGroupSizeClasses(AdaptivePoolingAllocator.this, true);
/*      */           }
/*  203 */           return null;
/*      */         }
/*      */ 
/*      */         
/*      */         protected void onRemoval(AdaptivePoolingAllocator.MagazineGroup[] groups) throws Exception {
/*  208 */           if (groups != null) {
/*  209 */             for (AdaptivePoolingAllocator.MagazineGroup group : groups) {
/*  210 */               group.free();
/*      */             }
/*      */           }
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   private static MagazineGroup[] createMagazineGroupSizeClasses(AdaptivePoolingAllocator allocator, boolean isThreadLocal) {
/*  219 */     MagazineGroup[] groups = new MagazineGroup[SIZE_CLASSES.length];
/*  220 */     for (int i = 0; i < SIZE_CLASSES.length; i++) {
/*  221 */       int segmentSize = SIZE_CLASSES[i];
/*  222 */       groups[i] = new MagazineGroup(allocator, allocator.chunkAllocator, new SizeClassChunkControllerFactory(segmentSize), isThreadLocal);
/*      */     } 
/*      */     
/*  225 */     return groups;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Queue<Chunk> createSharedChunkQueue() {
/*  249 */     return PlatformDependent.newFixedMpmcQueue(CHUNK_REUSE_QUEUE);
/*      */   }
/*      */   
/*      */   ByteBuf allocate(int size, int maxCapacity) {
/*  253 */     return allocate(size, maxCapacity, Thread.currentThread(), null);
/*      */   }
/*      */   
/*      */   private AdaptiveByteBuf allocate(int size, int maxCapacity, Thread currentThread, AdaptiveByteBuf buf) {
/*  257 */     AdaptiveByteBuf allocated = null;
/*  258 */     if (size <= MAX_POOLED_BUF_SIZE) {
/*  259 */       int index = sizeClassIndexOf(size);
/*      */       MagazineGroup[] magazineGroups;
/*  261 */       if (!FastThreadLocalThread.currentThreadWillCleanupFastThreadLocals() || IS_LOW_MEM || (
/*      */         
/*  263 */         magazineGroups = (MagazineGroup[])this.threadLocalGroup.get()) == null) {
/*  264 */         magazineGroups = this.sizeClassedMagazineGroups;
/*      */       }
/*  266 */       if (index < magazineGroups.length) {
/*  267 */         allocated = magazineGroups[index].allocate(size, maxCapacity, currentThread, buf);
/*  268 */       } else if (!IS_LOW_MEM) {
/*  269 */         allocated = this.largeBufferMagazineGroup.allocate(size, maxCapacity, currentThread, buf);
/*      */       } 
/*      */     } 
/*  272 */     if (allocated == null) {
/*  273 */       allocated = allocateFallback(size, maxCapacity, currentThread, buf);
/*      */     }
/*  275 */     return allocated;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int sizeIndexOf(int size) {
/*  280 */     return size + 31 >> 5;
/*      */   }
/*      */   
/*      */   static int sizeClassIndexOf(int size) {
/*  284 */     int sizeIndex = sizeIndexOf(size);
/*  285 */     if (sizeIndex < SIZE_INDEXES.length) {
/*  286 */       return SIZE_INDEXES[sizeIndex];
/*      */     }
/*  288 */     return SIZE_CLASSES_COUNT;
/*      */   }
/*      */   
/*      */   static int[] getSizeClasses() {
/*  292 */     return (int[])SIZE_CLASSES.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private AdaptiveByteBuf allocateFallback(int size, int maxCapacity, Thread currentThread, AdaptiveByteBuf buf) {
/*      */     Magazine magazine;
/*  299 */     if (buf != null) {
/*  300 */       Chunk chunk1 = buf.chunk;
/*  301 */       if (chunk1 == null || chunk1 == Magazine.MAGAZINE_FREED || (magazine = chunk1.currentMagazine()) == null) {
/*  302 */         magazine = getFallbackMagazine(currentThread);
/*      */       }
/*      */     } else {
/*  305 */       magazine = getFallbackMagazine(currentThread);
/*  306 */       buf = magazine.newBuffer();
/*      */     } 
/*      */     
/*  309 */     AbstractByteBuf innerChunk = this.chunkAllocator.allocate(size, maxCapacity);
/*  310 */     Chunk chunk = new Chunk(innerChunk, magazine, false, chunkSize -> true);
/*  311 */     this.chunkRegistry.add(chunk);
/*      */     try {
/*  313 */       chunk.readInitInto(buf, size, size, maxCapacity);
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/*  318 */       chunk.release();
/*      */     } 
/*  320 */     return buf;
/*      */   }
/*      */   
/*      */   private Magazine getFallbackMagazine(Thread currentThread) {
/*  324 */     Magazine[] mags = this.largeBufferMagazineGroup.magazines;
/*  325 */     return mags[(int)currentThread.getId() & mags.length - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void reallocate(int size, int maxCapacity, AdaptiveByteBuf into) {
/*  332 */     AdaptiveByteBuf result = allocate(size, maxCapacity, Thread.currentThread(), into);
/*  333 */     assert result == into : "Re-allocation created separate buffer instance";
/*      */   }
/*      */   
/*      */   long usedMemory() {
/*  337 */     return this.chunkRegistry.totalCapacity();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalize() throws Throwable {
/*      */     try {
/*  347 */       super.finalize();
/*      */     } finally {
/*  349 */       free();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void free() {
/*  354 */     this.largeBufferMagazineGroup.free();
/*      */   }
/*      */   
/*      */   static int sizeToBucket(int size) {
/*  358 */     return HistogramChunkController.sizeToBucket(size);
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class MagazineGroup
/*      */   {
/*      */     private final AdaptivePoolingAllocator allocator;
/*      */     
/*      */     private final AdaptivePoolingAllocator.ChunkAllocator chunkAllocator;
/*      */     private final AdaptivePoolingAllocator.ChunkControllerFactory chunkControllerFactory;
/*      */     private final Queue<AdaptivePoolingAllocator.Chunk> chunkReuseQueue;
/*      */     private final StampedLock magazineExpandLock;
/*      */     private final AdaptivePoolingAllocator.Magazine threadLocalMagazine;
/*      */     private volatile AdaptivePoolingAllocator.Magazine[] magazines;
/*      */     private volatile boolean freed;
/*      */     
/*      */     MagazineGroup(AdaptivePoolingAllocator allocator, AdaptivePoolingAllocator.ChunkAllocator chunkAllocator, AdaptivePoolingAllocator.ChunkControllerFactory chunkControllerFactory, boolean isThreadLocal) {
/*  375 */       this.allocator = allocator;
/*  376 */       this.chunkAllocator = chunkAllocator;
/*  377 */       this.chunkControllerFactory = chunkControllerFactory;
/*  378 */       this.chunkReuseQueue = AdaptivePoolingAllocator.createSharedChunkQueue();
/*  379 */       if (isThreadLocal) {
/*  380 */         this.magazineExpandLock = null;
/*  381 */         this.threadLocalMagazine = new AdaptivePoolingAllocator.Magazine(this, false, this.chunkReuseQueue, chunkControllerFactory.create(this));
/*      */       } else {
/*  383 */         this.magazineExpandLock = new StampedLock();
/*  384 */         this.threadLocalMagazine = null;
/*  385 */         AdaptivePoolingAllocator.Magazine[] mags = new AdaptivePoolingAllocator.Magazine[1];
/*  386 */         for (int i = 0; i < mags.length; i++) {
/*  387 */           mags[i] = new AdaptivePoolingAllocator.Magazine(this, true, this.chunkReuseQueue, chunkControllerFactory.create(this));
/*      */         }
/*  389 */         this.magazines = mags;
/*      */       } 
/*      */     }
/*      */     public AdaptivePoolingAllocator.AdaptiveByteBuf allocate(int size, int maxCapacity, Thread currentThread, AdaptivePoolingAllocator.AdaptiveByteBuf buf) {
/*      */       AdaptivePoolingAllocator.Magazine[] mags;
/*  394 */       boolean reallocate = (buf != null);
/*      */ 
/*      */       
/*  397 */       AdaptivePoolingAllocator.Magazine tlMag = this.threadLocalMagazine;
/*  398 */       if (tlMag != null) {
/*  399 */         if (buf == null) {
/*  400 */           buf = tlMag.newBuffer();
/*      */         }
/*  402 */         boolean allocated = tlMag.tryAllocate(size, maxCapacity, buf, reallocate);
/*  403 */         assert allocated : "Allocation of threadLocalMagazine must always succeed";
/*  404 */         return buf;
/*      */       } 
/*      */ 
/*      */       
/*  408 */       long threadId = currentThread.getId();
/*      */       
/*  410 */       int expansions = 0;
/*      */       do {
/*  412 */         mags = this.magazines;
/*  413 */         int mask = mags.length - 1;
/*  414 */         int index = (int)(threadId & mask);
/*  415 */         for (int i = 0, m = mags.length << 1; i < m; i++) {
/*  416 */           AdaptivePoolingAllocator.Magazine mag = mags[index + i & mask];
/*  417 */           if (buf == null) {
/*  418 */             buf = mag.newBuffer();
/*      */           }
/*  420 */           if (mag.tryAllocate(size, maxCapacity, buf, reallocate))
/*      */           {
/*  422 */             return buf;
/*      */           }
/*      */         } 
/*  425 */         ++expansions;
/*  426 */       } while (expansions <= 3 && tryExpandMagazines(mags.length));
/*      */ 
/*      */       
/*  429 */       if (!reallocate && buf != null) {
/*  430 */         buf.release();
/*      */       }
/*  432 */       return null;
/*      */     }
/*      */     
/*      */     private boolean tryExpandMagazines(int currentLength) {
/*  436 */       if (currentLength >= AdaptivePoolingAllocator.MAX_STRIPES) {
/*  437 */         return true;
/*      */       }
/*      */       
/*  440 */       long writeLock = this.magazineExpandLock.tryWriteLock();
/*  441 */       if (writeLock != 0L) {
/*      */         AdaptivePoolingAllocator.Magazine[] mags; try {
/*  443 */           mags = this.magazines;
/*  444 */           if (mags.length >= AdaptivePoolingAllocator.MAX_STRIPES || mags.length > currentLength || this.freed) {
/*  445 */             return true;
/*      */           }
/*  447 */           AdaptivePoolingAllocator.Magazine firstMagazine = mags[0];
/*  448 */           AdaptivePoolingAllocator.Magazine[] expanded = new AdaptivePoolingAllocator.Magazine[mags.length * 2];
/*  449 */           for (int i = 0, l = expanded.length; i < l; i++) {
/*  450 */             AdaptivePoolingAllocator.Magazine m = new AdaptivePoolingAllocator.Magazine(this, true, this.chunkReuseQueue, this.chunkControllerFactory.create(this));
/*  451 */             firstMagazine.initializeSharedStateIn(m);
/*  452 */             expanded[i] = m;
/*      */           } 
/*  454 */           this.magazines = expanded;
/*      */         } finally {
/*  456 */           this.magazineExpandLock.unlockWrite(writeLock);
/*      */         } 
/*  458 */         for (AdaptivePoolingAllocator.Magazine magazine : mags) {
/*  459 */           magazine.free();
/*      */         }
/*      */       } 
/*  462 */       return true;
/*      */     }
/*      */     
/*      */     boolean offerToQueue(AdaptivePoolingAllocator.Chunk buffer) {
/*  466 */       if (this.freed) {
/*  467 */         return false;
/*      */       }
/*      */       
/*  470 */       boolean isAdded = this.chunkReuseQueue.offer(buffer);
/*  471 */       if (this.freed && isAdded)
/*      */       {
/*  473 */         freeChunkReuseQueue();
/*      */       }
/*  475 */       return isAdded;
/*      */     }
/*      */     
/*      */     private void free() {
/*  479 */       this.freed = true;
/*  480 */       if (this.threadLocalMagazine != null) {
/*  481 */         this.threadLocalMagazine.free();
/*      */       } else {
/*  483 */         long stamp = this.magazineExpandLock.writeLock();
/*      */         try {
/*  485 */           AdaptivePoolingAllocator.Magazine[] mags = this.magazines;
/*  486 */           for (AdaptivePoolingAllocator.Magazine magazine : mags) {
/*  487 */             magazine.free();
/*      */           }
/*      */         } finally {
/*  490 */           this.magazineExpandLock.unlockWrite(stamp);
/*      */         } 
/*      */       } 
/*  493 */       freeChunkReuseQueue();
/*      */     }
/*      */     
/*      */     private void freeChunkReuseQueue() {
/*      */       while (true) {
/*  498 */         AdaptivePoolingAllocator.Chunk chunk = this.chunkReuseQueue.poll();
/*  499 */         if (chunk == null) {
/*      */           break;
/*      */         }
/*  502 */         chunk.release();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static interface ChunkControllerFactory
/*      */   {
/*      */     AdaptivePoolingAllocator.ChunkController create(AdaptivePoolingAllocator.MagazineGroup param1MagazineGroup);
/*      */   }
/*      */ 
/*      */   
/*      */   private static interface ChunkController
/*      */   {
/*      */     int computeBufferCapacity(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */ 
/*      */     
/*      */     void initializeSharedStateIn(ChunkController param1ChunkController);
/*      */ 
/*      */     
/*      */     AdaptivePoolingAllocator.Chunk newChunkAllocation(int param1Int, AdaptivePoolingAllocator.Magazine param1Magazine);
/*      */   }
/*      */ 
/*      */   
/*      */   private static interface ChunkReleasePredicate
/*      */   {
/*      */     boolean shouldReleaseChunk(int param1Int);
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class SizeClassChunkControllerFactory
/*      */     implements ChunkControllerFactory
/*      */   {
/*      */     private static final int MIN_SEGMENTS_PER_CHUNK = 32;
/*      */     
/*      */     private final int segmentSize;
/*      */     private final int chunkSize;
/*      */     private final int[] segmentOffsets;
/*      */     
/*      */     private SizeClassChunkControllerFactory(int segmentSize) {
/*  542 */       this.segmentSize = ObjectUtil.checkPositive(segmentSize, "segmentSize");
/*  543 */       this.chunkSize = Math.max(131072, segmentSize * 32);
/*  544 */       int segmentsCount = this.chunkSize / segmentSize;
/*  545 */       this.segmentOffsets = new int[segmentsCount];
/*  546 */       for (int i = 0; i < segmentsCount; i++) {
/*  547 */         this.segmentOffsets[i] = i * segmentSize;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public AdaptivePoolingAllocator.ChunkController create(AdaptivePoolingAllocator.MagazineGroup group) {
/*  553 */       return new AdaptivePoolingAllocator.SizeClassChunkController(group, this.segmentSize, this.chunkSize, this.segmentOffsets);
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class SizeClassChunkController
/*      */     implements ChunkController {
/*      */     private final AdaptivePoolingAllocator.ChunkAllocator chunkAllocator;
/*      */     private final int segmentSize;
/*      */     private final int chunkSize;
/*      */     private final AdaptivePoolingAllocator.ChunkRegistry chunkRegistry;
/*      */     private final int[] segmentOffsets;
/*      */     
/*      */     private SizeClassChunkController(AdaptivePoolingAllocator.MagazineGroup group, int segmentSize, int chunkSize, int[] segmentOffsets) {
/*  566 */       this.chunkAllocator = group.chunkAllocator;
/*  567 */       this.segmentSize = segmentSize;
/*  568 */       this.chunkSize = chunkSize;
/*  569 */       this.chunkRegistry = group.allocator.chunkRegistry;
/*  570 */       this.segmentOffsets = segmentOffsets;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int computeBufferCapacity(int requestedSize, int maxCapacity, boolean isReallocation) {
/*  576 */       return Math.min(this.segmentSize, maxCapacity);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void initializeSharedStateIn(AdaptivePoolingAllocator.ChunkController chunkController) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public AdaptivePoolingAllocator.Chunk newChunkAllocation(int promptingSize, AdaptivePoolingAllocator.Magazine magazine) {
/*  586 */       AbstractByteBuf chunkBuffer = this.chunkAllocator.allocate(this.chunkSize, this.chunkSize);
/*  587 */       assert chunkBuffer.capacity() == this.chunkSize;
/*  588 */       AdaptivePoolingAllocator.SizeClassedChunk chunk = new AdaptivePoolingAllocator.SizeClassedChunk(chunkBuffer, magazine, true, this.segmentSize, this.segmentOffsets, size -> false);
/*      */       
/*  590 */       this.chunkRegistry.add(chunk);
/*  591 */       return chunk;
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class HistogramChunkControllerFactory implements ChunkControllerFactory {
/*      */     private final boolean shareable;
/*      */     
/*      */     private HistogramChunkControllerFactory(boolean shareable) {
/*  599 */       this.shareable = shareable;
/*      */     }
/*      */ 
/*      */     
/*      */     public AdaptivePoolingAllocator.ChunkController create(AdaptivePoolingAllocator.MagazineGroup group) {
/*  604 */       return new AdaptivePoolingAllocator.HistogramChunkController(group, this.shareable);
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class HistogramChunkController implements ChunkController, ChunkReleasePredicate {
/*      */     private static final int MIN_DATUM_TARGET = 1024;
/*      */     private static final int MAX_DATUM_TARGET = 65534;
/*      */     private static final int INIT_DATUM_TARGET = 9;
/*      */     private static final int HISTO_BUCKET_COUNT = 16;
/*  613 */     private static final int[] HISTO_BUCKETS = new int[] { 16384, 24576, 32768, 49152, 65536, 98304, 131072, 196608, 262144, 393216, 524288, 786432, 1048576, 1835008, 2097152, 3145728 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final AdaptivePoolingAllocator.MagazineGroup group;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final boolean shareable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  634 */     private final short[][] histos = new short[][] { new short[16], new short[16], new short[16], new short[16] };
/*      */ 
/*      */     
/*      */     private final AdaptivePoolingAllocator.ChunkRegistry chunkRegistry;
/*      */     
/*  639 */     private short[] histo = this.histos[0];
/*  640 */     private final int[] sums = new int[16];
/*      */     
/*      */     private int histoIndex;
/*      */     private int datumCount;
/*  644 */     private int datumTarget = 9;
/*      */     private boolean hasHadRotation;
/*  646 */     private volatile int sharedPrefChunkSize = 131072;
/*  647 */     private volatile int localPrefChunkSize = 131072;
/*      */     private volatile int localUpperBufSize;
/*      */     
/*      */     private HistogramChunkController(AdaptivePoolingAllocator.MagazineGroup group, boolean shareable) {
/*  651 */       this.group = group;
/*  652 */       this.shareable = shareable;
/*  653 */       this.chunkRegistry = group.allocator.chunkRegistry;
/*      */     }
/*      */ 
/*      */     
/*      */     public int computeBufferCapacity(int requestedSize, int maxCapacity, boolean isReallocation) {
/*      */       int startCapLimits;
/*  659 */       if (!isReallocation)
/*      */       {
/*      */         
/*  662 */         recordAllocationSize(requestedSize);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  668 */       if (requestedSize <= 32768) {
/*  669 */         startCapLimits = 65536;
/*      */       } else {
/*  671 */         startCapLimits = requestedSize * 2;
/*      */       } 
/*  673 */       int startingCapacity = Math.min(startCapLimits, this.localUpperBufSize);
/*  674 */       startingCapacity = Math.max(requestedSize, Math.min(maxCapacity, startingCapacity));
/*  675 */       return startingCapacity;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void recordAllocationSize(int bufferSizeToRecord) {
/*  682 */       if (bufferSizeToRecord == 0) {
/*      */         return;
/*      */       }
/*  685 */       int bucket = sizeToBucket(bufferSizeToRecord);
/*  686 */       this.histo[bucket] = (short)(this.histo[bucket] + 1);
/*  687 */       if (this.datumCount++ == this.datumTarget) {
/*  688 */         rotateHistograms();
/*      */       }
/*      */     }
/*      */     
/*      */     static int sizeToBucket(int size) {
/*  693 */       int index = binarySearchInsertionPoint(Arrays.binarySearch(HISTO_BUCKETS, size));
/*  694 */       return (index >= HISTO_BUCKETS.length) ? (HISTO_BUCKETS.length - 1) : index;
/*      */     }
/*      */     
/*      */     private static int binarySearchInsertionPoint(int index) {
/*  698 */       if (index < 0) {
/*  699 */         index = -(index + 1);
/*      */       }
/*  701 */       return index;
/*      */     }
/*      */     
/*      */     static int bucketToSize(int sizeBucket) {
/*  705 */       return HISTO_BUCKETS[sizeBucket];
/*      */     }
/*      */     
/*      */     private void rotateHistograms() {
/*  709 */       short[][] hs = this.histos;
/*  710 */       for (int i = 0; i < 16; i++) {
/*  711 */         this.sums[i] = (hs[0][i] & 0xFFFF) + (hs[1][i] & 0xFFFF) + (hs[2][i] & 0xFFFF) + (hs[3][i] & 0xFFFF);
/*      */       }
/*  713 */       int sum = 0;
/*  714 */       for (int count : this.sums) {
/*  715 */         sum += count;
/*      */       }
/*  717 */       int targetPercentile = (int)(sum * 0.99D);
/*  718 */       int sizeBucket = 0;
/*  719 */       for (; sizeBucket < this.sums.length && 
/*  720 */         this.sums[sizeBucket] <= targetPercentile; sizeBucket++)
/*      */       {
/*      */         
/*  723 */         targetPercentile -= this.sums[sizeBucket];
/*      */       }
/*  725 */       this.hasHadRotation = true;
/*  726 */       int percentileSize = bucketToSize(sizeBucket);
/*  727 */       int prefChunkSize = Math.max(percentileSize * 8, 131072);
/*  728 */       this.localUpperBufSize = percentileSize;
/*  729 */       this.localPrefChunkSize = prefChunkSize;
/*  730 */       if (this.shareable) {
/*  731 */         for (AdaptivePoolingAllocator.Magazine mag : this.group.magazines) {
/*  732 */           HistogramChunkController statistics = (HistogramChunkController)mag.chunkController;
/*  733 */           prefChunkSize = Math.max(prefChunkSize, statistics.localPrefChunkSize);
/*      */         } 
/*      */       }
/*  736 */       if (this.sharedPrefChunkSize != prefChunkSize) {
/*      */         
/*  738 */         this.datumTarget = Math.max(this.datumTarget >> 1, 1024);
/*  739 */         this.sharedPrefChunkSize = prefChunkSize;
/*      */       } else {
/*      */         
/*  742 */         this.datumTarget = Math.min(this.datumTarget << 1, 65534);
/*      */       } 
/*      */       
/*  745 */       this.histoIndex = this.histoIndex + 1 & 0x3;
/*  746 */       this.histo = this.histos[this.histoIndex];
/*  747 */       this.datumCount = 0;
/*  748 */       Arrays.fill(this.histo, (short)0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int preferredChunkSize() {
/*  760 */       return this.sharedPrefChunkSize;
/*      */     }
/*      */ 
/*      */     
/*      */     public void initializeSharedStateIn(AdaptivePoolingAllocator.ChunkController chunkController) {
/*  765 */       HistogramChunkController statistics = (HistogramChunkController)chunkController;
/*  766 */       int sharedPrefChunkSize = this.sharedPrefChunkSize;
/*  767 */       statistics.localPrefChunkSize = sharedPrefChunkSize;
/*  768 */       statistics.sharedPrefChunkSize = sharedPrefChunkSize;
/*      */     }
/*      */ 
/*      */     
/*      */     public AdaptivePoolingAllocator.Chunk newChunkAllocation(int promptingSize, AdaptivePoolingAllocator.Magazine magazine) {
/*  773 */       int size = Math.max(promptingSize * 8, preferredChunkSize());
/*  774 */       int minChunks = size / 131072;
/*  775 */       if (131072 * minChunks < size)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  780 */         size = 131072 * (1 + minChunks);
/*      */       }
/*      */ 
/*      */       
/*  784 */       size = Math.min(size, AdaptivePoolingAllocator.MAX_CHUNK_SIZE);
/*      */ 
/*      */       
/*  787 */       if (!this.hasHadRotation && this.sharedPrefChunkSize == 131072) {
/*  788 */         this.sharedPrefChunkSize = size;
/*      */       }
/*      */       
/*  791 */       AdaptivePoolingAllocator.ChunkAllocator chunkAllocator = this.group.chunkAllocator;
/*  792 */       AdaptivePoolingAllocator.Chunk chunk = new AdaptivePoolingAllocator.Chunk(chunkAllocator.allocate(size, size), magazine, true, this);
/*  793 */       this.chunkRegistry.add(chunk);
/*  794 */       return chunk;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean shouldReleaseChunk(int chunkSize) {
/*  799 */       int preferredSize = preferredChunkSize();
/*  800 */       int givenChunks = chunkSize / 131072;
/*  801 */       int preferredChunks = preferredSize / 131072;
/*  802 */       int deviation = Math.abs(givenChunks - preferredChunks);
/*      */ 
/*      */       
/*  805 */       return (deviation != 0 && 
/*  806 */         ThreadLocalRandom.current().nextDouble() * 20.0D < deviation);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class Magazine
/*      */   {
/*  813 */     private static final AtomicReferenceFieldUpdater<Magazine, AdaptivePoolingAllocator.Chunk> NEXT_IN_LINE = AtomicReferenceFieldUpdater.newUpdater(Magazine.class, AdaptivePoolingAllocator.Chunk.class, "nextInLine");
/*      */     
/*  815 */     private static final AdaptivePoolingAllocator.Chunk MAGAZINE_FREED = new AdaptivePoolingAllocator.Chunk();
/*      */     
/*  817 */     private static final Recycler<AdaptivePoolingAllocator.AdaptiveByteBuf> EVENT_LOOP_LOCAL_BUFFER_POOL = new Recycler<AdaptivePoolingAllocator.AdaptiveByteBuf>()
/*      */       {
/*      */         protected AdaptivePoolingAllocator.AdaptiveByteBuf newObject(Recycler.Handle<AdaptivePoolingAllocator.AdaptiveByteBuf> handle) {
/*  820 */           return new AdaptivePoolingAllocator.AdaptiveByteBuf((ObjectPool.Handle<AdaptivePoolingAllocator.AdaptiveByteBuf>)handle);
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*      */     private AdaptivePoolingAllocator.Chunk current;
/*      */     
/*      */     private volatile AdaptivePoolingAllocator.Chunk nextInLine;
/*      */     private final AdaptivePoolingAllocator.MagazineGroup group;
/*      */     private final AdaptivePoolingAllocator.ChunkController chunkController;
/*      */     private final StampedLock allocationLock;
/*      */     private final Queue<AdaptivePoolingAllocator.AdaptiveByteBuf> bufferQueue;
/*      */     private final ObjectPool.Handle<AdaptivePoolingAllocator.AdaptiveByteBuf> handle;
/*      */     private final Queue<AdaptivePoolingAllocator.Chunk> sharedChunkQueue;
/*      */     
/*      */     Magazine(AdaptivePoolingAllocator.MagazineGroup group, boolean shareable, Queue<AdaptivePoolingAllocator.Chunk> sharedChunkQueue, AdaptivePoolingAllocator.ChunkController chunkController) {
/*  836 */       this.group = group;
/*  837 */       this.chunkController = chunkController;
/*      */       
/*  839 */       if (shareable) {
/*      */         
/*  841 */         this.allocationLock = new StampedLock();
/*  842 */         this.bufferQueue = PlatformDependent.newFixedMpmcQueue(AdaptivePoolingAllocator.MAGAZINE_BUFFER_QUEUE_CAPACITY);
/*  843 */         this.handle = new ObjectPool.Handle<AdaptivePoolingAllocator.AdaptiveByteBuf>()
/*      */           {
/*      */             public void recycle(AdaptivePoolingAllocator.AdaptiveByteBuf self) {
/*  846 */               AdaptivePoolingAllocator.Magazine.this.bufferQueue.offer(self);
/*      */             }
/*      */           };
/*      */       } else {
/*  850 */         this.allocationLock = null;
/*  851 */         this.bufferQueue = null;
/*  852 */         this.handle = null;
/*      */       } 
/*  854 */       this.sharedChunkQueue = sharedChunkQueue;
/*      */     }
/*      */     
/*      */     public boolean tryAllocate(int size, int maxCapacity, AdaptivePoolingAllocator.AdaptiveByteBuf buf, boolean reallocate) {
/*  858 */       if (this.allocationLock == null)
/*      */       {
/*  860 */         return allocate(size, maxCapacity, buf, reallocate);
/*      */       }
/*      */ 
/*      */       
/*  864 */       long writeLock = this.allocationLock.tryWriteLock();
/*  865 */       if (writeLock != 0L) {
/*      */         try {
/*  867 */           return allocate(size, maxCapacity, buf, reallocate);
/*      */         } finally {
/*  869 */           this.allocationLock.unlockWrite(writeLock);
/*      */         } 
/*      */       }
/*  872 */       return allocateWithoutLock(size, maxCapacity, buf);
/*      */     }
/*      */     
/*      */     private boolean allocateWithoutLock(int size, int maxCapacity, AdaptivePoolingAllocator.AdaptiveByteBuf buf) {
/*  876 */       AdaptivePoolingAllocator.Chunk curr = NEXT_IN_LINE.getAndSet(this, null);
/*  877 */       if (curr == MAGAZINE_FREED) {
/*      */         
/*  879 */         restoreMagazineFreed();
/*  880 */         return false;
/*      */       } 
/*  882 */       if (curr == null) {
/*  883 */         curr = this.sharedChunkQueue.poll();
/*  884 */         if (curr == null) {
/*  885 */           return false;
/*      */         }
/*  887 */         curr.attachToMagazine(this);
/*      */       } 
/*  889 */       boolean allocated = false;
/*  890 */       int remainingCapacity = curr.remainingCapacity();
/*  891 */       int startingCapacity = this.chunkController.computeBufferCapacity(size, maxCapacity, true);
/*      */       
/*  893 */       if (remainingCapacity >= size) {
/*  894 */         curr.readInitInto(buf, size, Math.min(remainingCapacity, startingCapacity), maxCapacity);
/*  895 */         allocated = true;
/*      */       } 
/*      */       try {
/*  898 */         if (remainingCapacity >= 256) {
/*  899 */           transferToNextInLineOrRelease(curr);
/*  900 */           curr = null;
/*      */         } 
/*      */       } finally {
/*  903 */         if (curr != null) {
/*  904 */           curr.releaseFromMagazine();
/*      */         }
/*      */       } 
/*  907 */       return allocated;
/*      */     }
/*      */     
/*      */     private boolean allocate(int size, int maxCapacity, AdaptivePoolingAllocator.AdaptiveByteBuf buf, boolean reallocate) {
/*  911 */       int startingCapacity = this.chunkController.computeBufferCapacity(size, maxCapacity, reallocate);
/*  912 */       AdaptivePoolingAllocator.Chunk curr = this.current;
/*  913 */       if (curr != null) {
/*      */         
/*  915 */         int remainingCapacity = curr.remainingCapacity();
/*  916 */         if (remainingCapacity > startingCapacity) {
/*  917 */           curr.readInitInto(buf, size, startingCapacity, maxCapacity);
/*      */           
/*  919 */           return true;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  924 */         this.current = null;
/*  925 */         if (remainingCapacity >= size) {
/*      */           try {
/*  927 */             curr.readInitInto(buf, size, remainingCapacity, maxCapacity);
/*  928 */             return true;
/*      */           } finally {
/*  930 */             curr.releaseFromMagazine();
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*  935 */         if (remainingCapacity < 256) {
/*  936 */           curr.releaseFromMagazine();
/*      */         }
/*      */         else {
/*      */           
/*  940 */           transferToNextInLineOrRelease(curr);
/*      */         } 
/*      */       } 
/*      */       
/*  944 */       assert this.current == null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  953 */       curr = NEXT_IN_LINE.getAndSet(this, null);
/*  954 */       if (curr != null) {
/*  955 */         if (curr == MAGAZINE_FREED) {
/*      */           
/*  957 */           restoreMagazineFreed();
/*  958 */           return false;
/*      */         } 
/*      */         
/*  961 */         int remainingCapacity = curr.remainingCapacity();
/*  962 */         if (remainingCapacity > startingCapacity) {
/*      */           
/*  964 */           curr.readInitInto(buf, size, startingCapacity, maxCapacity);
/*  965 */           this.current = curr;
/*  966 */           return true;
/*      */         } 
/*      */         
/*  969 */         if (remainingCapacity >= size) {
/*      */           
/*      */           try {
/*      */             
/*  973 */             curr.readInitInto(buf, size, remainingCapacity, maxCapacity);
/*  974 */             return true;
/*      */           }
/*      */           finally {
/*      */             
/*  978 */             curr.releaseFromMagazine();
/*      */           } 
/*      */         }
/*      */         
/*  982 */         curr.releaseFromMagazine();
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  987 */       curr = this.sharedChunkQueue.poll();
/*  988 */       if (curr == null) {
/*  989 */         curr = this.chunkController.newChunkAllocation(size, this);
/*      */       } else {
/*  991 */         curr.attachToMagazine(this);
/*      */         
/*  993 */         int remainingCapacity = curr.remainingCapacity();
/*  994 */         if (remainingCapacity == 0 || remainingCapacity < size) {
/*      */           
/*  996 */           if (remainingCapacity < 256) {
/*  997 */             curr.releaseFromMagazine();
/*      */           }
/*      */           else {
/*      */             
/* 1001 */             transferToNextInLineOrRelease(curr);
/*      */           } 
/* 1003 */           curr = this.chunkController.newChunkAllocation(size, this);
/*      */         } 
/*      */       } 
/*      */       
/* 1007 */       this.current = curr;
/*      */       try {
/* 1009 */         int remainingCapacity = curr.remainingCapacity();
/* 1010 */         assert remainingCapacity >= size;
/* 1011 */         if (remainingCapacity > startingCapacity) {
/* 1012 */           curr.readInitInto(buf, size, startingCapacity, maxCapacity);
/* 1013 */           curr = null;
/*      */         } else {
/* 1015 */           curr.readInitInto(buf, size, remainingCapacity, maxCapacity);
/*      */         } 
/*      */       } finally {
/* 1018 */         if (curr != null) {
/*      */ 
/*      */           
/* 1021 */           curr.releaseFromMagazine();
/* 1022 */           this.current = null;
/*      */         } 
/*      */       } 
/* 1025 */       return true;
/*      */     }
/*      */     
/*      */     private void restoreMagazineFreed() {
/* 1029 */       AdaptivePoolingAllocator.Chunk next = NEXT_IN_LINE.getAndSet(this, MAGAZINE_FREED);
/* 1030 */       if (next != null && next != MAGAZINE_FREED)
/*      */       {
/* 1032 */         next.releaseFromMagazine();
/*      */       }
/*      */     }
/*      */     
/*      */     private void transferToNextInLineOrRelease(AdaptivePoolingAllocator.Chunk chunk) {
/* 1037 */       if (NEXT_IN_LINE.compareAndSet(this, null, chunk)) {
/*      */         return;
/*      */       }
/*      */       
/* 1041 */       AdaptivePoolingAllocator.Chunk nextChunk = NEXT_IN_LINE.get(this);
/* 1042 */       if (nextChunk != null && nextChunk != MAGAZINE_FREED && chunk
/* 1043 */         .remainingCapacity() > nextChunk.remainingCapacity() && 
/* 1044 */         NEXT_IN_LINE.compareAndSet(this, nextChunk, chunk)) {
/* 1045 */         nextChunk.releaseFromMagazine();
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/* 1053 */       chunk.releaseFromMagazine();
/*      */     }
/*      */     
/*      */     boolean trySetNextInLine(AdaptivePoolingAllocator.Chunk chunk) {
/* 1057 */       return NEXT_IN_LINE.compareAndSet(this, null, chunk);
/*      */     }
/*      */ 
/*      */     
/*      */     void free() {
/* 1062 */       restoreMagazineFreed();
/* 1063 */       long stamp = (this.allocationLock != null) ? this.allocationLock.writeLock() : 0L;
/*      */       try {
/* 1065 */         if (this.current != null) {
/* 1066 */           this.current.releaseFromMagazine();
/* 1067 */           this.current = null;
/*      */         } 
/*      */       } finally {
/* 1070 */         if (this.allocationLock != null) {
/* 1071 */           this.allocationLock.unlockWrite(stamp);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public AdaptivePoolingAllocator.AdaptiveByteBuf newBuffer() {
/*      */       AdaptivePoolingAllocator.AdaptiveByteBuf buf;
/* 1078 */       if (this.handle == null) {
/* 1079 */         buf = (AdaptivePoolingAllocator.AdaptiveByteBuf)EVENT_LOOP_LOCAL_BUFFER_POOL.get();
/*      */       } else {
/* 1081 */         buf = this.bufferQueue.poll();
/* 1082 */         if (buf == null) {
/* 1083 */           buf = new AdaptivePoolingAllocator.AdaptiveByteBuf(this.handle);
/*      */         }
/*      */       } 
/* 1086 */       buf.resetRefCnt();
/* 1087 */       buf.discardMarks();
/* 1088 */       return buf;
/*      */     }
/*      */     
/*      */     boolean offerToQueue(AdaptivePoolingAllocator.Chunk chunk) {
/* 1092 */       return this.group.offerToQueue(chunk);
/*      */     }
/*      */     
/*      */     public void initializeSharedStateIn(Magazine other) {
/* 1096 */       this.chunkController.initializeSharedStateIn(other.chunkController);
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class ChunkRegistry {
/* 1101 */     private final LongAdder totalCapacity = new LongAdder();
/*      */     
/*      */     public long totalCapacity() {
/* 1104 */       return this.totalCapacity.sum();
/*      */     }
/*      */     
/*      */     public void add(AdaptivePoolingAllocator.Chunk chunk) {
/* 1108 */       this.totalCapacity.add(chunk.capacity());
/*      */     }
/*      */     
/*      */     public void remove(AdaptivePoolingAllocator.Chunk chunk) {
/* 1112 */       this.totalCapacity.add(-chunk.capacity());
/*      */     }
/*      */     
/*      */     private ChunkRegistry() {}
/*      */   }
/*      */   
/*      */   private static class Chunk implements ChunkInfo {
/*      */     protected final AbstractByteBuf delegate;
/*      */     protected AdaptivePoolingAllocator.Magazine magazine;
/*      */     private final AdaptivePoolingAllocator allocator;
/*      */     private final AdaptivePoolingAllocator.ChunkReleasePredicate chunkReleasePredicate;
/* 1123 */     private final RefCnt refCnt = new RefCnt();
/*      */     
/*      */     private final int capacity;
/*      */     private final boolean pooled;
/*      */     protected int allocatedBytes;
/*      */     
/*      */     Chunk() {
/* 1130 */       this.delegate = null;
/* 1131 */       this.magazine = null;
/* 1132 */       this.allocator = null;
/* 1133 */       this.chunkReleasePredicate = null;
/* 1134 */       this.capacity = 0;
/* 1135 */       this.pooled = false;
/*      */     }
/*      */ 
/*      */     
/*      */     Chunk(AbstractByteBuf delegate, AdaptivePoolingAllocator.Magazine magazine, boolean pooled, AdaptivePoolingAllocator.ChunkReleasePredicate chunkReleasePredicate) {
/* 1140 */       this.delegate = delegate;
/* 1141 */       this.pooled = pooled;
/* 1142 */       this.capacity = delegate.capacity();
/* 1143 */       attachToMagazine(magazine);
/*      */ 
/*      */       
/* 1146 */       this.allocator = magazine.group.allocator;
/*      */       
/* 1148 */       this.chunkReleasePredicate = chunkReleasePredicate;
/*      */       
/* 1150 */       if (PlatformDependent.isJfrEnabled() && AllocateChunkEvent.isEventEnabled()) {
/* 1151 */         AllocateChunkEvent event = new AllocateChunkEvent();
/* 1152 */         if (event.shouldCommit()) {
/* 1153 */           event.fill(this, (Class)AdaptiveByteBufAllocator.class);
/* 1154 */           event.pooled = pooled;
/* 1155 */           event.threadLocal = (magazine.allocationLock == null);
/* 1156 */           event.commit();
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     AdaptivePoolingAllocator.Magazine currentMagazine() {
/* 1162 */       return this.magazine;
/*      */     }
/*      */     
/*      */     void detachFromMagazine() {
/* 1166 */       if (this.magazine != null) {
/* 1167 */         this.magazine = null;
/*      */       }
/*      */     }
/*      */     
/*      */     void attachToMagazine(AdaptivePoolingAllocator.Magazine magazine) {
/* 1172 */       assert this.magazine == null;
/* 1173 */       this.magazine = magazine;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean releaseFromMagazine() {
/* 1180 */       return release();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean releaseSegment(int ignoredSegmentId) {
/* 1187 */       return release();
/*      */     }
/*      */     
/*      */     private void retain() {
/* 1191 */       RefCnt.retain(this.refCnt);
/*      */     }
/*      */     
/*      */     protected boolean release() {
/* 1195 */       boolean deallocate = RefCnt.release(this.refCnt);
/* 1196 */       if (deallocate) {
/* 1197 */         deallocate();
/*      */       }
/* 1199 */       return deallocate;
/*      */     }
/*      */     
/*      */     protected void deallocate() {
/* 1203 */       AdaptivePoolingAllocator.Magazine mag = this.magazine;
/* 1204 */       int chunkSize = this.delegate.capacity();
/* 1205 */       if (!this.pooled || this.chunkReleasePredicate.shouldReleaseChunk(chunkSize) || mag == null) {
/*      */ 
/*      */         
/* 1208 */         detachFromMagazine();
/* 1209 */         onRelease();
/* 1210 */         this.allocator.chunkRegistry.remove(this);
/* 1211 */         this.delegate.release();
/*      */       } else {
/* 1213 */         RefCnt.resetRefCnt(this.refCnt);
/* 1214 */         this.delegate.setIndex(0, 0);
/* 1215 */         this.allocatedBytes = 0;
/* 1216 */         if (!mag.trySetNextInLine(this)) {
/*      */           
/* 1218 */           detachFromMagazine();
/* 1219 */           if (!mag.offerToQueue(this)) {
/*      */ 
/*      */             
/* 1222 */             boolean released = RefCnt.release(this.refCnt);
/* 1223 */             onRelease();
/* 1224 */             this.allocator.chunkRegistry.remove(this);
/* 1225 */             this.delegate.release();
/* 1226 */             assert released;
/*      */           } else {
/* 1228 */             onReturn(false);
/*      */           } 
/*      */         } else {
/* 1231 */           onReturn(true);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void onReturn(boolean returnedToMagazine) {
/* 1237 */       if (PlatformDependent.isJfrEnabled() && ReturnChunkEvent.isEventEnabled()) {
/* 1238 */         ReturnChunkEvent event = new ReturnChunkEvent();
/* 1239 */         if (event.shouldCommit()) {
/* 1240 */           event.fill(this, (Class)AdaptiveByteBufAllocator.class);
/* 1241 */           event.returnedToMagazine = returnedToMagazine;
/* 1242 */           event.commit();
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void onRelease() {
/* 1248 */       if (PlatformDependent.isJfrEnabled() && FreeChunkEvent.isEventEnabled()) {
/* 1249 */         FreeChunkEvent event = new FreeChunkEvent();
/* 1250 */         if (event.shouldCommit()) {
/* 1251 */           event.fill(this, (Class)AdaptiveByteBufAllocator.class);
/* 1252 */           event.pooled = this.pooled;
/* 1253 */           event.commit();
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void readInitInto(AdaptivePoolingAllocator.AdaptiveByteBuf buf, int size, int startingCapacity, int maxCapacity) {
/* 1259 */       int startIndex = this.allocatedBytes;
/* 1260 */       this.allocatedBytes = startIndex + startingCapacity;
/* 1261 */       Chunk chunk = this;
/* 1262 */       chunk.retain();
/*      */       try {
/* 1264 */         buf.init(this.delegate, chunk, 0, 0, startIndex, size, startingCapacity, maxCapacity);
/* 1265 */         chunk = null;
/*      */       } finally {
/* 1267 */         if (chunk != null) {
/*      */ 
/*      */ 
/*      */           
/* 1271 */           this.allocatedBytes = startIndex;
/* 1272 */           chunk.release();
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int remainingCapacity() {
/* 1278 */       return this.capacity - this.allocatedBytes;
/*      */     }
/*      */ 
/*      */     
/*      */     public int capacity() {
/* 1283 */       return this.capacity;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isDirect() {
/* 1288 */       return this.delegate.isDirect();
/*      */     }
/*      */ 
/*      */     
/*      */     public long memoryAddress() {
/* 1293 */       return this.delegate._memoryAddress();
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class SizeClassedChunk
/*      */     extends Chunk {
/*      */     private static final int FREE_LIST_EMPTY = -1;
/*      */     private final int segmentSize;
/*      */     private final MpscIntQueue freeList;
/*      */     
/*      */     SizeClassedChunk(AbstractByteBuf delegate, AdaptivePoolingAllocator.Magazine magazine, boolean pooled, int segmentSize, final int[] segmentOffsets, AdaptivePoolingAllocator.ChunkReleasePredicate shouldReleaseChunk) {
/* 1304 */       super(delegate, magazine, pooled, shouldReleaseChunk);
/* 1305 */       this.segmentSize = segmentSize;
/* 1306 */       int segmentCount = segmentOffsets.length;
/* 1307 */       assert delegate.capacity() / segmentSize == segmentCount;
/* 1308 */       assert segmentCount > 0 : "Chunk must have a positive number of segments";
/* 1309 */       this.freeList = MpscIntQueue.create(segmentCount, -1);
/* 1310 */       this.freeList.fill(segmentCount, new IntSupplier() {
/*      */             int counter;
/*      */             
/*      */             public int getAsInt() {
/* 1314 */               return segmentOffsets[this.counter++];
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */     
/*      */     public void readInitInto(AdaptivePoolingAllocator.AdaptiveByteBuf buf, int size, int startingCapacity, int maxCapacity) {
/* 1321 */       int startIndex = this.freeList.poll();
/* 1322 */       if (startIndex == -1) {
/* 1323 */         throw new IllegalStateException("Free list is empty");
/*      */       }
/* 1325 */       this.allocatedBytes += this.segmentSize;
/* 1326 */       AdaptivePoolingAllocator.Chunk chunk = this;
/* 1327 */       chunk.retain();
/*      */       try {
/* 1329 */         buf.init(this.delegate, chunk, 0, 0, startIndex, size, startingCapacity, maxCapacity);
/* 1330 */         chunk = null;
/*      */       } finally {
/* 1332 */         if (chunk != null) {
/*      */ 
/*      */ 
/*      */           
/* 1336 */           this.allocatedBytes -= this.segmentSize;
/* 1337 */           chunk.releaseSegment(startIndex);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int remainingCapacity() {
/* 1344 */       int remainingCapacity = super.remainingCapacity();
/* 1345 */       if (remainingCapacity > this.segmentSize) {
/* 1346 */         return remainingCapacity;
/*      */       }
/* 1348 */       int updatedRemainingCapacity = this.freeList.size() * this.segmentSize;
/* 1349 */       if (updatedRemainingCapacity == remainingCapacity) {
/* 1350 */         return remainingCapacity;
/*      */       }
/*      */       
/* 1353 */       this.allocatedBytes = capacity() - updatedRemainingCapacity;
/* 1354 */       return updatedRemainingCapacity;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean releaseFromMagazine() {
/* 1361 */       AdaptivePoolingAllocator.Magazine mag = this.magazine;
/* 1362 */       detachFromMagazine();
/* 1363 */       if (!mag.offerToQueue(this)) {
/* 1364 */         return super.releaseFromMagazine();
/*      */       }
/* 1366 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean releaseSegment(int startIndex) {
/* 1371 */       boolean released = release();
/* 1372 */       boolean segmentReturned = this.freeList.offer(startIndex);
/* 1373 */       assert segmentReturned : "Unable to return segment " + startIndex + " to free list";
/* 1374 */       return released;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class AdaptiveByteBuf
/*      */     extends AbstractReferenceCountedByteBuf
/*      */   {
/*      */     private final ObjectPool.Handle<AdaptiveByteBuf> handle;
/*      */     private int startIndex;
/*      */     private AbstractByteBuf rootParent;
/*      */     AdaptivePoolingAllocator.Chunk chunk;
/*      */     private int length;
/*      */     private int maxFastCapacity;
/*      */     private ByteBuffer tmpNioBuf;
/*      */     private boolean hasArray;
/*      */     private boolean hasMemoryAddress;
/*      */     
/*      */     AdaptiveByteBuf(ObjectPool.Handle<AdaptiveByteBuf> recyclerHandle) {
/* 1393 */       super(0);
/* 1394 */       this.handle = (ObjectPool.Handle<AdaptiveByteBuf>)ObjectUtil.checkNotNull(recyclerHandle, "recyclerHandle");
/*      */     }
/*      */ 
/*      */     
/*      */     void init(AbstractByteBuf unwrapped, AdaptivePoolingAllocator.Chunk wrapped, int readerIndex, int writerIndex, int startIndex, int size, int capacity, int maxCapacity) {
/* 1399 */       this.startIndex = startIndex;
/* 1400 */       this.chunk = wrapped;
/* 1401 */       this.length = size;
/* 1402 */       this.maxFastCapacity = capacity;
/* 1403 */       maxCapacity(maxCapacity);
/* 1404 */       setIndex0(readerIndex, writerIndex);
/* 1405 */       this.hasArray = unwrapped.hasArray();
/* 1406 */       this.hasMemoryAddress = unwrapped.hasMemoryAddress();
/* 1407 */       this.rootParent = unwrapped;
/* 1408 */       this.tmpNioBuf = null;
/*      */       
/* 1410 */       if (PlatformDependent.isJfrEnabled() && AllocateBufferEvent.isEventEnabled()) {
/* 1411 */         AllocateBufferEvent event = new AllocateBufferEvent();
/* 1412 */         if (event.shouldCommit()) {
/* 1413 */           event.fill(this, (Class)AdaptiveByteBufAllocator.class);
/* 1414 */           event.chunkPooled = wrapped.pooled;
/* 1415 */           AdaptivePoolingAllocator.Magazine m = wrapped.magazine;
/* 1416 */           event.chunkThreadLocal = (m != null && m.allocationLock == null);
/* 1417 */           event.commit();
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private AbstractByteBuf rootParent() {
/* 1423 */       AbstractByteBuf rootParent = this.rootParent;
/* 1424 */       if (rootParent != null) {
/* 1425 */         return rootParent;
/*      */       }
/* 1427 */       throw new IllegalReferenceCountException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int capacity() {
/* 1432 */       return this.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public int maxFastWritableBytes() {
/* 1437 */       return Math.min(this.maxFastCapacity, maxCapacity()) - this.writerIndex;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf capacity(int newCapacity) {
/* 1442 */       if (this.length <= newCapacity && newCapacity <= this.maxFastCapacity) {
/* 1443 */         ensureAccessible();
/* 1444 */         this.length = newCapacity;
/* 1445 */         return this;
/*      */       } 
/* 1447 */       checkNewCapacity(newCapacity);
/* 1448 */       if (newCapacity < capacity()) {
/* 1449 */         this.length = newCapacity;
/* 1450 */         trimIndicesToCapacity(newCapacity);
/* 1451 */         return this;
/*      */       } 
/*      */       
/* 1454 */       if (PlatformDependent.isJfrEnabled() && ReallocateBufferEvent.isEventEnabled()) {
/* 1455 */         ReallocateBufferEvent event = new ReallocateBufferEvent();
/* 1456 */         if (event.shouldCommit()) {
/* 1457 */           event.fill(this, (Class)AdaptiveByteBufAllocator.class);
/* 1458 */           event.newCapacity = newCapacity;
/* 1459 */           event.commit();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1464 */       AdaptivePoolingAllocator.Chunk chunk = this.chunk;
/* 1465 */       AdaptivePoolingAllocator allocator = chunk.allocator;
/* 1466 */       int readerIndex = this.readerIndex;
/* 1467 */       int writerIndex = this.writerIndex;
/* 1468 */       int baseOldRootIndex = this.startIndex;
/* 1469 */       int oldCapacity = this.length;
/* 1470 */       AbstractByteBuf oldRoot = rootParent();
/* 1471 */       allocator.reallocate(newCapacity, maxCapacity(), this);
/* 1472 */       oldRoot.getBytes(baseOldRootIndex, this, 0, oldCapacity);
/* 1473 */       chunk.releaseSegment(baseOldRootIndex);
/* 1474 */       this.readerIndex = readerIndex;
/* 1475 */       this.writerIndex = writerIndex;
/* 1476 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBufAllocator alloc() {
/* 1481 */       return rootParent().alloc();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteOrder order() {
/* 1486 */       return rootParent().order();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf unwrap() {
/* 1491 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isDirect() {
/* 1496 */       return rootParent().isDirect();
/*      */     }
/*      */ 
/*      */     
/*      */     public int arrayOffset() {
/* 1501 */       return idx(rootParent().arrayOffset());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasMemoryAddress() {
/* 1506 */       return this.hasMemoryAddress;
/*      */     }
/*      */ 
/*      */     
/*      */     public long memoryAddress() {
/* 1511 */       ensureAccessible();
/* 1512 */       return _memoryAddress();
/*      */     }
/*      */ 
/*      */     
/*      */     long _memoryAddress() {
/* 1517 */       AbstractByteBuf root = this.rootParent;
/* 1518 */       return (root != null) ? (root._memoryAddress() + this.startIndex) : 0L;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuffer nioBuffer(int index, int length) {
/* 1523 */       checkIndex(index, length);
/* 1524 */       return rootParent().nioBuffer(idx(index), length);
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuffer internalNioBuffer(int index, int length) {
/* 1529 */       checkIndex(index, length);
/* 1530 */       return (ByteBuffer)internalNioBuffer().position(index).limit(index + length);
/*      */     }
/*      */     
/*      */     private ByteBuffer internalNioBuffer() {
/* 1534 */       if (this.tmpNioBuf == null) {
/* 1535 */         this.tmpNioBuf = rootParent().nioBuffer(this.startIndex, this.maxFastCapacity);
/*      */       }
/* 1537 */       return (ByteBuffer)this.tmpNioBuf.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuffer[] nioBuffers(int index, int length) {
/* 1542 */       checkIndex(index, length);
/* 1543 */       return rootParent().nioBuffers(idx(index), length);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasArray() {
/* 1548 */       return this.hasArray;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] array() {
/* 1553 */       ensureAccessible();
/* 1554 */       return rootParent().array();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf copy(int index, int length) {
/* 1559 */       checkIndex(index, length);
/* 1560 */       return rootParent().copy(idx(index), length);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nioBufferCount() {
/* 1565 */       return rootParent().nioBufferCount();
/*      */     }
/*      */ 
/*      */     
/*      */     protected byte _getByte(int index) {
/* 1570 */       return rootParent()._getByte(idx(index));
/*      */     }
/*      */ 
/*      */     
/*      */     protected short _getShort(int index) {
/* 1575 */       return rootParent()._getShort(idx(index));
/*      */     }
/*      */ 
/*      */     
/*      */     protected short _getShortLE(int index) {
/* 1580 */       return rootParent()._getShortLE(idx(index));
/*      */     }
/*      */ 
/*      */     
/*      */     protected int _getUnsignedMedium(int index) {
/* 1585 */       return rootParent()._getUnsignedMedium(idx(index));
/*      */     }
/*      */ 
/*      */     
/*      */     protected int _getUnsignedMediumLE(int index) {
/* 1590 */       return rootParent()._getUnsignedMediumLE(idx(index));
/*      */     }
/*      */ 
/*      */     
/*      */     protected int _getInt(int index) {
/* 1595 */       return rootParent()._getInt(idx(index));
/*      */     }
/*      */ 
/*      */     
/*      */     protected int _getIntLE(int index) {
/* 1600 */       return rootParent()._getIntLE(idx(index));
/*      */     }
/*      */ 
/*      */     
/*      */     protected long _getLong(int index) {
/* 1605 */       return rootParent()._getLong(idx(index));
/*      */     }
/*      */ 
/*      */     
/*      */     protected long _getLongLE(int index) {
/* 1610 */       return rootParent()._getLongLE(idx(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 1615 */       checkIndex(index, length);
/* 1616 */       rootParent().getBytes(idx(index), dst, dstIndex, length);
/* 1617 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 1622 */       checkIndex(index, length);
/* 1623 */       rootParent().getBytes(idx(index), dst, dstIndex, length);
/* 1624 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 1629 */       checkIndex(index, dst.remaining());
/* 1630 */       rootParent().getBytes(idx(index), dst);
/* 1631 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void _setByte(int index, int value) {
/* 1636 */       rootParent()._setByte(idx(index), value);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void _setShort(int index, int value) {
/* 1641 */       rootParent()._setShort(idx(index), value);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void _setShortLE(int index, int value) {
/* 1646 */       rootParent()._setShortLE(idx(index), value);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void _setMedium(int index, int value) {
/* 1651 */       rootParent()._setMedium(idx(index), value);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void _setMediumLE(int index, int value) {
/* 1656 */       rootParent()._setMediumLE(idx(index), value);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void _setInt(int index, int value) {
/* 1661 */       rootParent()._setInt(idx(index), value);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void _setIntLE(int index, int value) {
/* 1666 */       rootParent()._setIntLE(idx(index), value);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void _setLong(int index, long value) {
/* 1671 */       rootParent()._setLong(idx(index), value);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void _setLongLE(int index, long value) {
/* 1676 */       rootParent().setLongLE(idx(index), value);
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 1681 */       checkIndex(index, length);
/* 1682 */       ByteBuffer tmp = (ByteBuffer)internalNioBuffer().clear().position(index);
/* 1683 */       tmp.put(src, srcIndex, length);
/* 1684 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 1689 */       checkIndex(index, length);
/* 1690 */       ByteBuffer tmp = (ByteBuffer)internalNioBuffer().clear().position(index);
/* 1691 */       tmp.put(src.nioBuffer(srcIndex, length));
/* 1692 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf setBytes(int index, ByteBuffer src) {
/* 1697 */       checkIndex(index, src.remaining());
/* 1698 */       ByteBuffer tmp = (ByteBuffer)internalNioBuffer().clear().position(index);
/* 1699 */       tmp.put(src);
/* 1700 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 1706 */       checkIndex(index, length);
/* 1707 */       if (length != 0) {
/* 1708 */         ByteBufUtil.readBytes(alloc(), internalNioBuffer().duplicate(), index, length, out);
/*      */       }
/* 1710 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 1716 */       ByteBuffer buf = internalNioBuffer().duplicate();
/* 1717 */       buf.clear().position(index).limit(index + length);
/* 1718 */       return out.write(buf);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
/* 1724 */       ByteBuffer buf = internalNioBuffer().duplicate();
/* 1725 */       buf.clear().position(index).limit(index + length);
/* 1726 */       return out.write(buf, position);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int setBytes(int index, InputStream in, int length) throws IOException {
/* 1732 */       checkIndex(index, length);
/* 1733 */       AbstractByteBuf rootParent = rootParent();
/* 1734 */       if (rootParent.hasArray()) {
/* 1735 */         return rootParent.setBytes(idx(index), in, length);
/*      */       }
/* 1737 */       byte[] tmp = ByteBufUtil.threadLocalTempArray(length);
/* 1738 */       int readBytes = in.read(tmp, 0, length);
/* 1739 */       if (readBytes <= 0) {
/* 1740 */         return readBytes;
/*      */       }
/* 1742 */       setBytes(index, tmp, 0, readBytes);
/* 1743 */       return readBytes;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/*      */       try {
/* 1750 */         return in.read(internalNioBuffer(index, length));
/* 1751 */       } catch (ClosedChannelException ignored) {
/* 1752 */         return -1;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
/*      */       try {
/* 1760 */         return in.read(internalNioBuffer(index, length), position);
/* 1761 */       } catch (ClosedChannelException ignored) {
/* 1762 */         return -1;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int setCharSequence(int index, CharSequence sequence, Charset charset) {
/* 1768 */       return setCharSequence0(index, sequence, charset, false);
/*      */     }
/*      */     
/*      */     private int setCharSequence0(int index, CharSequence sequence, Charset charset, boolean expand) {
/* 1772 */       if (charset.equals(CharsetUtil.UTF_8)) {
/* 1773 */         int length = ByteBufUtil.utf8MaxBytes(sequence);
/* 1774 */         if (expand) {
/* 1775 */           ensureWritable0(length);
/* 1776 */           checkIndex0(index, length);
/*      */         } else {
/* 1778 */           checkIndex(index, length);
/*      */         } 
/* 1780 */         return ByteBufUtil.writeUtf8(this, index, length, sequence, sequence.length());
/*      */       } 
/* 1782 */       if (charset.equals(CharsetUtil.US_ASCII) || charset.equals(CharsetUtil.ISO_8859_1)) {
/* 1783 */         int length = sequence.length();
/* 1784 */         if (expand) {
/* 1785 */           ensureWritable0(length);
/* 1786 */           checkIndex0(index, length);
/*      */         } else {
/* 1788 */           checkIndex(index, length);
/*      */         } 
/* 1790 */         return ByteBufUtil.writeAscii(this, index, sequence, length);
/*      */       } 
/* 1792 */       byte[] bytes = sequence.toString().getBytes(charset);
/* 1793 */       if (expand) {
/* 1794 */         ensureWritable0(bytes.length);
/*      */       }
/*      */       
/* 1797 */       setBytes(index, bytes);
/* 1798 */       return bytes.length;
/*      */     }
/*      */ 
/*      */     
/*      */     public int writeCharSequence(CharSequence sequence, Charset charset) {
/* 1803 */       int written = setCharSequence0(this.writerIndex, sequence, charset, true);
/* 1804 */       this.writerIndex += written;
/* 1805 */       return written;
/*      */     }
/*      */ 
/*      */     
/*      */     public int forEachByte(int index, int length, ByteProcessor processor) {
/* 1810 */       checkIndex(index, length);
/* 1811 */       int ret = rootParent().forEachByte(idx(index), length, processor);
/* 1812 */       return forEachResult(ret);
/*      */     }
/*      */ 
/*      */     
/*      */     public int forEachByteDesc(int index, int length, ByteProcessor processor) {
/* 1817 */       checkIndex(index, length);
/* 1818 */       int ret = rootParent().forEachByteDesc(idx(index), length, processor);
/* 1819 */       return forEachResult(ret);
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf setZero(int index, int length) {
/* 1824 */       checkIndex(index, length);
/* 1825 */       rootParent().setZero(idx(index), length);
/* 1826 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf writeZero(int length) {
/* 1831 */       ensureWritable(length);
/* 1832 */       rootParent().setZero(idx(this.writerIndex), length);
/* 1833 */       this.writerIndex += length;
/* 1834 */       return this;
/*      */     }
/*      */     
/*      */     private int forEachResult(int ret) {
/* 1838 */       if (ret < this.startIndex) {
/* 1839 */         return -1;
/*      */       }
/* 1841 */       return ret - this.startIndex;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isContiguous() {
/* 1846 */       return rootParent().isContiguous();
/*      */     }
/*      */     
/*      */     private int idx(int index) {
/* 1850 */       return index + this.startIndex;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void deallocate() {
/* 1855 */       if (PlatformDependent.isJfrEnabled() && FreeBufferEvent.isEventEnabled()) {
/* 1856 */         FreeBufferEvent event = new FreeBufferEvent();
/* 1857 */         if (event.shouldCommit()) {
/* 1858 */           event.fill(this, (Class)AdaptiveByteBufAllocator.class);
/* 1859 */           event.commit();
/*      */         } 
/*      */       } 
/*      */       
/* 1863 */       if (this.chunk != null) {
/* 1864 */         this.chunk.releaseSegment(this.startIndex);
/*      */       }
/* 1866 */       this.tmpNioBuf = null;
/* 1867 */       this.chunk = null;
/* 1868 */       this.rootParent = null;
/* 1869 */       if (this.handle instanceof Recycler.EnhancedHandle) {
/* 1870 */         Recycler.EnhancedHandle<AdaptiveByteBuf> enhancedHandle = (Recycler.EnhancedHandle<AdaptiveByteBuf>)this.handle;
/* 1871 */         enhancedHandle.unguardedRecycle(this);
/*      */       } else {
/* 1873 */         this.handle.recycle(this);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static interface ChunkAllocator {
/*      */     AbstractByteBuf allocate(int param1Int1, int param1Int2);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\AdaptivePoolingAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */