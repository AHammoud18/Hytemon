/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.CleanableDirectBuffer;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.concurrent.atomic.LongAdder;
/*     */ import java.util.concurrent.locks.ReentrantLock;
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
/*     */ abstract class PoolArena<T>
/*     */   implements PoolArenaMetric
/*     */ {
/*     */   final PooledByteBufAllocator parent;
/*     */   final PoolSubpage<T>[] smallSubpagePools;
/*     */   private final PoolChunkList<T> q050;
/*     */   private final PoolChunkList<T> q025;
/*     */   private final PoolChunkList<T> q000;
/*  36 */   private static final boolean HAS_UNSAFE = PlatformDependent.hasUnsafe(); private final PoolChunkList<T> qInit; private final PoolChunkList<T> q075; private final PoolChunkList<T> q100; private final List<PoolChunkListMetric> chunkListMetrics;
/*     */   private long allocationsNormal;
/*     */   
/*  39 */   enum SizeClass { Small,
/*  40 */     Normal; }
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
/*  59 */   private final LongAdder allocationsSmall = new LongAdder();
/*  60 */   private final LongAdder allocationsHuge = new LongAdder();
/*  61 */   private final LongAdder activeBytesHuge = new LongAdder();
/*     */   
/*     */   private long deallocationsSmall;
/*     */   
/*     */   private long deallocationsNormal;
/*     */   
/*     */   private long pooledChunkAllocations;
/*     */   
/*     */   private long pooledChunkDeallocations;
/*  70 */   private final LongAdder deallocationsHuge = new LongAdder();
/*     */ 
/*     */   
/*  73 */   final AtomicInteger numThreadCaches = new AtomicInteger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private final ReentrantLock lock = new ReentrantLock();
/*     */   
/*     */   final SizeClasses sizeClass;
/*     */   
/*     */   protected PoolArena(PooledByteBufAllocator parent, SizeClasses sizeClass) {
/*  83 */     assert null != sizeClass;
/*  84 */     this.parent = parent;
/*  85 */     this.sizeClass = sizeClass;
/*  86 */     this.smallSubpagePools = newSubpagePoolArray(sizeClass.nSubpages);
/*  87 */     for (int i = 0; i < this.smallSubpagePools.length; i++) {
/*  88 */       this.smallSubpagePools[i] = newSubpagePoolHead(i);
/*     */     }
/*     */     
/*  91 */     this.q100 = new PoolChunkList<>(this, null, 100, 2147483647, sizeClass.chunkSize);
/*  92 */     this.q075 = new PoolChunkList<>(this, this.q100, 75, 100, sizeClass.chunkSize);
/*  93 */     this.q050 = new PoolChunkList<>(this, this.q100, 50, 100, sizeClass.chunkSize);
/*  94 */     this.q025 = new PoolChunkList<>(this, this.q050, 25, 75, sizeClass.chunkSize);
/*  95 */     this.q000 = new PoolChunkList<>(this, this.q025, 1, 50, sizeClass.chunkSize);
/*  96 */     this.qInit = new PoolChunkList<>(this, this.q000, -2147483648, 25, sizeClass.chunkSize);
/*     */     
/*  98 */     this.q100.prevList(this.q075);
/*  99 */     this.q075.prevList(this.q050);
/* 100 */     this.q050.prevList(this.q025);
/* 101 */     this.q025.prevList(this.q000);
/* 102 */     this.q000.prevList(null);
/* 103 */     this.qInit.prevList(this.qInit);
/*     */     
/* 105 */     List<PoolChunkListMetric> metrics = new ArrayList<>(6);
/* 106 */     metrics.add(this.qInit);
/* 107 */     metrics.add(this.q000);
/* 108 */     metrics.add(this.q025);
/* 109 */     metrics.add(this.q050);
/* 110 */     metrics.add(this.q075);
/* 111 */     metrics.add(this.q100);
/* 112 */     this.chunkListMetrics = Collections.unmodifiableList(metrics);
/*     */   }
/*     */   
/*     */   private PoolSubpage<T> newSubpagePoolHead(int index) {
/* 116 */     PoolSubpage<T> head = new PoolSubpage<>(index);
/* 117 */     head.prev = head;
/* 118 */     head.next = head;
/* 119 */     return head;
/*     */   }
/*     */ 
/*     */   
/*     */   private PoolSubpage<T>[] newSubpagePoolArray(int size) {
/* 124 */     return (PoolSubpage<T>[])new PoolSubpage[size];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   PooledByteBuf<T> allocate(PoolThreadCache cache, int reqCapacity, int maxCapacity) {
/* 130 */     PooledByteBuf<T> buf = newByteBuf(maxCapacity);
/* 131 */     allocate(cache, buf, reqCapacity);
/* 132 */     return buf;
/*     */   }
/*     */   
/*     */   private void allocate(PoolThreadCache cache, PooledByteBuf<T> buf, int reqCapacity) {
/* 136 */     int sizeIdx = this.sizeClass.size2SizeIdx(reqCapacity);
/*     */     
/* 138 */     if (sizeIdx <= this.sizeClass.smallMaxSizeIdx) {
/* 139 */       tcacheAllocateSmall(cache, buf, reqCapacity, sizeIdx);
/* 140 */     } else if (sizeIdx < this.sizeClass.nSizes) {
/* 141 */       tcacheAllocateNormal(cache, buf, reqCapacity, sizeIdx);
/*     */     } else {
/*     */       
/* 144 */       int normCapacity = (this.sizeClass.directMemoryCacheAlignment > 0) ? this.sizeClass.normalizeSize(reqCapacity) : reqCapacity;
/*     */       
/* 146 */       allocateHuge(buf, normCapacity);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void tcacheAllocateSmall(PoolThreadCache cache, PooledByteBuf<T> buf, int reqCapacity, int sizeIdx) {
/*     */     boolean needsNormalAllocation;
/* 153 */     if (cache.allocateSmall(this, buf, reqCapacity, sizeIdx)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     PoolSubpage<T> head = this.smallSubpagePools[sizeIdx];
/*     */     
/* 164 */     head.lock();
/*     */     try {
/* 166 */       PoolSubpage<T> s = head.next;
/* 167 */       needsNormalAllocation = (s == head);
/* 168 */       if (!needsNormalAllocation) {
/* 169 */         assert s.doNotDestroy && s.elemSize == this.sizeClass.sizeIdx2size(sizeIdx) : "doNotDestroy=" + s.doNotDestroy + ", elemSize=" + s.elemSize + ", sizeIdx=" + sizeIdx;
/*     */         
/* 171 */         long handle = s.allocate();
/* 172 */         assert handle >= 0L;
/* 173 */         s.chunk.initBufWithSubpage(buf, null, handle, reqCapacity, cache, false);
/*     */       } 
/*     */     } finally {
/* 176 */       head.unlock();
/*     */     } 
/*     */     
/* 179 */     if (needsNormalAllocation) {
/* 180 */       lock();
/*     */       try {
/* 182 */         allocateNormal(buf, reqCapacity, sizeIdx, cache);
/*     */       } finally {
/* 184 */         unlock();
/*     */       } 
/*     */     } 
/*     */     
/* 188 */     incSmallAllocation();
/*     */   }
/*     */ 
/*     */   
/*     */   private void tcacheAllocateNormal(PoolThreadCache cache, PooledByteBuf<T> buf, int reqCapacity, int sizeIdx) {
/* 193 */     if (cache.allocateNormal(this, buf, reqCapacity, sizeIdx)) {
/*     */       return;
/*     */     }
/*     */     
/* 197 */     lock();
/*     */     try {
/* 199 */       allocateNormal(buf, reqCapacity, sizeIdx, cache);
/* 200 */       this.allocationsNormal++;
/*     */     } finally {
/* 202 */       unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void allocateNormal(PooledByteBuf<T> buf, int reqCapacity, int sizeIdx, PoolThreadCache threadCache) {
/* 207 */     assert this.lock.isHeldByCurrentThread();
/* 208 */     if (this.q050.allocate(buf, reqCapacity, sizeIdx, threadCache) || this.q025
/* 209 */       .allocate(buf, reqCapacity, sizeIdx, threadCache) || this.q000
/* 210 */       .allocate(buf, reqCapacity, sizeIdx, threadCache) || this.qInit
/* 211 */       .allocate(buf, reqCapacity, sizeIdx, threadCache) || this.q075
/* 212 */       .allocate(buf, reqCapacity, sizeIdx, threadCache)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 217 */     PoolChunk<T> c = newChunk(this.sizeClass.pageSize, this.sizeClass.nPSizes, this.sizeClass.pageShifts, this.sizeClass.chunkSize);
/* 218 */     PooledByteBufAllocator.onAllocateChunk(c, true);
/* 219 */     boolean success = c.allocate(buf, reqCapacity, sizeIdx, threadCache);
/* 220 */     assert success;
/* 221 */     this.qInit.add(c);
/* 222 */     this.pooledChunkAllocations++;
/*     */   }
/*     */   
/*     */   private void incSmallAllocation() {
/* 226 */     this.allocationsSmall.increment();
/*     */   }
/*     */   
/*     */   private void allocateHuge(PooledByteBuf<T> buf, int reqCapacity) {
/* 230 */     PoolChunk<T> chunk = newUnpooledChunk(reqCapacity);
/* 231 */     PooledByteBufAllocator.onAllocateChunk(chunk, false);
/* 232 */     this.activeBytesHuge.add(chunk.chunkSize());
/* 233 */     buf.initUnpooled(chunk, reqCapacity);
/* 234 */     this.allocationsHuge.increment();
/*     */   }
/*     */   
/*     */   void free(PoolChunk<T> chunk, ByteBuffer nioBuffer, long handle, int normCapacity, PoolThreadCache cache) {
/* 238 */     chunk.decrementPinnedMemory(normCapacity);
/* 239 */     if (chunk.unpooled) {
/* 240 */       int size = chunk.chunkSize();
/* 241 */       destroyChunk(chunk);
/* 242 */       this.activeBytesHuge.add(-size);
/* 243 */       this.deallocationsHuge.increment();
/*     */     } else {
/* 245 */       SizeClass sizeClass = sizeClass(handle);
/* 246 */       if (cache != null && cache.add(this, chunk, nioBuffer, handle, normCapacity, sizeClass)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 251 */       freeChunk(chunk, handle, normCapacity, sizeClass, nioBuffer, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static SizeClass sizeClass(long handle) {
/* 256 */     return PoolChunk.isSubpage(handle) ? SizeClass.Small : SizeClass.Normal;
/*     */   }
/*     */   
/*     */   void freeChunk(PoolChunk<T> chunk, long handle, int normCapacity, SizeClass sizeClass, ByteBuffer nioBuffer, boolean finalizer)
/*     */   {
/*     */     boolean destroyChunk;
/* 262 */     lock();
/*     */ 
/*     */     
/*     */     try {
/* 266 */       if (!finalizer) {
/* 267 */         switch (sizeClass) {
/*     */           case Normal:
/* 269 */             this.deallocationsNormal++;
/*     */             break;
/*     */           case Small:
/* 272 */             this.deallocationsSmall++;
/*     */             break;
/*     */           default:
/* 275 */             throw new Error("Unexpected size class: " + sizeClass);
/*     */         } 
/*     */       }
/* 278 */       destroyChunk = !chunk.parent.free(chunk, handle, normCapacity, nioBuffer);
/* 279 */       if (destroyChunk)
/*     */       {
/* 281 */         this.pooledChunkDeallocations++;
/*     */       }
/*     */     } finally {
/* 284 */       unlock();
/*     */     } 
/* 286 */     if (destroyChunk)
/*     */     {
/* 288 */       destroyChunk(chunk); }  } void reallocate(PooledByteBuf<T> buf, int newCapacity) { int oldCapacity; PoolChunk<T> oldChunk; ByteBuffer oldNioBuffer; long oldHandle;
/*     */     T oldMemory;
/*     */     int oldOffset, oldMaxLength;
/*     */     PoolThreadCache oldCache;
/*     */     int bytesToCopy;
/* 293 */     assert newCapacity >= 0 && newCapacity <= buf.maxCapacity();
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
/* 313 */     synchronized (buf) {
/* 314 */       oldCapacity = buf.length;
/* 315 */       if (oldCapacity == newCapacity) {
/*     */         return;
/*     */       }
/*     */       
/* 319 */       oldChunk = buf.chunk;
/* 320 */       oldNioBuffer = buf.tmpNioBuf;
/* 321 */       oldHandle = buf.handle;
/* 322 */       oldMemory = buf.memory;
/* 323 */       oldOffset = buf.offset;
/* 324 */       oldMaxLength = buf.maxLength;
/* 325 */       oldCache = buf.cache;
/*     */ 
/*     */       
/* 328 */       allocate(this.parent.threadCache(), buf, newCapacity);
/*     */     } 
/*     */     
/* 331 */     if (newCapacity > oldCapacity) {
/* 332 */       bytesToCopy = oldCapacity;
/*     */     } else {
/* 334 */       buf.trimIndicesToCapacity(newCapacity);
/* 335 */       bytesToCopy = newCapacity;
/*     */     } 
/* 337 */     memoryCopy(oldMemory, oldOffset, buf, bytesToCopy);
/* 338 */     free(oldChunk, oldNioBuffer, oldHandle, oldMaxLength, oldCache); }
/*     */ 
/*     */ 
/*     */   
/*     */   public int numThreadCaches() {
/* 343 */     return this.numThreadCaches.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public int numTinySubpages() {
/* 348 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int numSmallSubpages() {
/* 353 */     return this.smallSubpagePools.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int numChunkLists() {
/* 358 */     return this.chunkListMetrics.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PoolSubpageMetric> tinySubpages() {
/* 363 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PoolSubpageMetric> smallSubpages() {
/* 368 */     return subPageMetricList((PoolSubpage<?>[])this.smallSubpagePools);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PoolChunkListMetric> chunkLists() {
/* 373 */     return this.chunkListMetrics;
/*     */   }
/*     */   
/*     */   private static List<PoolSubpageMetric> subPageMetricList(PoolSubpage<?>[] pages) {
/* 377 */     List<PoolSubpageMetric> metrics = new ArrayList<>();
/* 378 */     for (PoolSubpage<?> head : pages) {
/* 379 */       if (head.next != head) {
/*     */ 
/*     */         
/* 382 */         PoolSubpage<?> s = head.next;
/*     */         do {
/* 384 */           metrics.add(s);
/* 385 */           s = s.next;
/* 386 */         } while (s != head);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 391 */     return metrics;
/*     */   }
/*     */ 
/*     */   
/*     */   public long numAllocations() {
/*     */     long allocsNormal;
/* 397 */     lock();
/*     */     try {
/* 399 */       allocsNormal = this.allocationsNormal;
/*     */     } finally {
/* 401 */       unlock();
/*     */     } 
/* 403 */     return this.allocationsSmall.sum() + allocsNormal + this.allocationsHuge.sum();
/*     */   }
/*     */ 
/*     */   
/*     */   public long numTinyAllocations() {
/* 408 */     return 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long numSmallAllocations() {
/* 413 */     return this.allocationsSmall.sum();
/*     */   }
/*     */ 
/*     */   
/*     */   public long numNormalAllocations() {
/* 418 */     lock();
/*     */     try {
/* 420 */       return this.allocationsNormal;
/*     */     } finally {
/* 422 */       unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long numChunkAllocations() {
/* 428 */     lock();
/*     */     try {
/* 430 */       return this.pooledChunkAllocations;
/*     */     } finally {
/* 432 */       unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long numDeallocations() {
/*     */     long deallocs;
/* 439 */     lock();
/*     */     try {
/* 441 */       deallocs = this.deallocationsSmall + this.deallocationsNormal;
/*     */     } finally {
/* 443 */       unlock();
/*     */     } 
/* 445 */     return deallocs + this.deallocationsHuge.sum();
/*     */   }
/*     */ 
/*     */   
/*     */   public long numTinyDeallocations() {
/* 450 */     return 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long numSmallDeallocations() {
/* 455 */     lock();
/*     */     try {
/* 457 */       return this.deallocationsSmall;
/*     */     } finally {
/* 459 */       unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long numNormalDeallocations() {
/* 465 */     lock();
/*     */     try {
/* 467 */       return this.deallocationsNormal;
/*     */     } finally {
/* 469 */       unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long numChunkDeallocations() {
/* 475 */     lock();
/*     */     try {
/* 477 */       return this.pooledChunkDeallocations;
/*     */     } finally {
/* 479 */       unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long numHugeAllocations() {
/* 485 */     return this.allocationsHuge.sum();
/*     */   }
/*     */ 
/*     */   
/*     */   public long numHugeDeallocations() {
/* 490 */     return this.deallocationsHuge.sum();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long numActiveAllocations() {
/* 496 */     long val = this.allocationsSmall.sum() + this.allocationsHuge.sum() - this.deallocationsHuge.sum();
/* 497 */     lock();
/*     */     try {
/* 499 */       val += this.allocationsNormal - this.deallocationsSmall + this.deallocationsNormal;
/*     */     } finally {
/* 501 */       unlock();
/*     */     } 
/* 503 */     return Math.max(val, 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long numActiveTinyAllocations() {
/* 508 */     return 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public long numActiveSmallAllocations() {
/* 513 */     return Math.max(numSmallAllocations() - numSmallDeallocations(), 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long numActiveNormalAllocations() {
/*     */     long val;
/* 519 */     lock();
/*     */     try {
/* 521 */       val = this.allocationsNormal - this.deallocationsNormal;
/*     */     } finally {
/* 523 */       unlock();
/*     */     } 
/* 525 */     return Math.max(val, 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long numActiveChunks() {
/*     */     long val;
/* 531 */     lock();
/*     */     try {
/* 533 */       val = this.pooledChunkAllocations - this.pooledChunkDeallocations;
/*     */     } finally {
/* 535 */       unlock();
/*     */     } 
/* 537 */     return Math.max(val, 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long numActiveHugeAllocations() {
/* 542 */     return Math.max(numHugeAllocations() - numHugeDeallocations(), 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long numActiveBytes() {
/* 547 */     long val = this.activeBytesHuge.sum();
/* 548 */     lock();
/*     */     try {
/* 550 */       for (int i = 0; i < this.chunkListMetrics.size(); i++) {
/* 551 */         for (PoolChunkMetric m : this.chunkListMetrics.get(i)) {
/* 552 */           val += m.chunkSize();
/*     */         }
/*     */       } 
/*     */     } finally {
/* 556 */       unlock();
/*     */     } 
/* 558 */     return Math.max(0L, val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long numPinnedBytes() {
/* 566 */     long val = this.activeBytesHuge.sum();
/* 567 */     for (int i = 0; i < this.chunkListMetrics.size(); i++) {
/* 568 */       for (PoolChunkMetric m : this.chunkListMetrics.get(i)) {
/* 569 */         val += ((PoolChunk)m).pinnedBytes();
/*     */       }
/*     */     } 
/* 572 */     return Math.max(0L, val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 583 */     lock();
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
/*     */     try {
/* 610 */       StringBuilder buf = (new StringBuilder()).append("Chunk(s) at 0~25%:").append(StringUtil.NEWLINE).append(this.qInit).append(StringUtil.NEWLINE).append("Chunk(s) at 0~50%:").append(StringUtil.NEWLINE).append(this.q000).append(StringUtil.NEWLINE).append("Chunk(s) at 25~75%:").append(StringUtil.NEWLINE).append(this.q025).append(StringUtil.NEWLINE).append("Chunk(s) at 50~100%:").append(StringUtil.NEWLINE).append(this.q050).append(StringUtil.NEWLINE).append("Chunk(s) at 75~100%:").append(StringUtil.NEWLINE).append(this.q075).append(StringUtil.NEWLINE).append("Chunk(s) at 100%:").append(StringUtil.NEWLINE).append(this.q100).append(StringUtil.NEWLINE).append("small subpages:");
/* 611 */       appendPoolSubPages(buf, (PoolSubpage<?>[])this.smallSubpagePools);
/* 612 */       buf.append(StringUtil.NEWLINE);
/* 613 */       return buf.toString();
/*     */     } finally {
/* 615 */       unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void appendPoolSubPages(StringBuilder buf, PoolSubpage<?>[] subpages) {
/* 620 */     for (int i = 0; i < subpages.length; i++) {
/* 621 */       PoolSubpage<?> head = subpages[i];
/* 622 */       if (head.next != head && head.next != null) {
/*     */ 
/*     */ 
/*     */         
/* 626 */         buf.append(StringUtil.NEWLINE)
/* 627 */           .append(i)
/* 628 */           .append(": ");
/* 629 */         PoolSubpage<?> s = head.next;
/* 630 */         while (s != null) {
/* 631 */           buf.append(s);
/* 632 */           s = s.next;
/* 633 */           if (s == head) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void finalize() throws Throwable {
/*     */     try {
/* 643 */       super.finalize();
/*     */     } finally {
/* 645 */       destroyPoolSubPages((PoolSubpage<?>[])this.smallSubpagePools);
/* 646 */       destroyPoolChunkLists((PoolChunkList<T>[])new PoolChunkList[] { this.qInit, this.q000, this.q025, this.q050, this.q075, this.q100 });
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void destroyPoolSubPages(PoolSubpage<?>[] pages) {
/* 651 */     for (PoolSubpage<?> page : pages) {
/* 652 */       page.destroy();
/*     */     }
/*     */   }
/*     */   
/*     */   private void destroyPoolChunkLists(PoolChunkList<T>... chunkLists) {
/* 657 */     for (PoolChunkList<T> chunkList : chunkLists)
/* 658 */       chunkList.destroy(this); 
/*     */   }
/*     */   
/*     */   static final class HeapArena
/*     */     extends PoolArena<byte[]> {
/*     */     private final AtomicReference<PoolChunk<byte[]>> lastDestroyedChunk;
/*     */     
/*     */     HeapArena(PooledByteBufAllocator parent, SizeClasses sizeClass) {
/* 666 */       super(parent, sizeClass);
/* 667 */       this.lastDestroyedChunk = new AtomicReference<>();
/*     */     }
/*     */     
/*     */     private static byte[] newByteArray(int size) {
/* 671 */       return PlatformDependent.allocateUninitializedArray(size);
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isDirect() {
/* 676 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected PoolChunk<byte[]> newChunk(int pageSize, int maxPageIdx, int pageShifts, int chunkSize) {
/* 681 */       PoolChunk<byte[]> chunk = this.lastDestroyedChunk.getAndSet(null);
/* 682 */       if (chunk != null) {
/* 683 */         assert chunk.chunkSize == chunkSize && chunk.pageSize == pageSize && chunk.maxPageIdx == maxPageIdx && chunk.pageShifts == pageShifts;
/*     */ 
/*     */ 
/*     */         
/* 687 */         return chunk;
/*     */       } 
/* 689 */       return (PoolChunk)new PoolChunk<>(this, null, null, 
/* 690 */           newByteArray(chunkSize), pageSize, pageShifts, chunkSize, maxPageIdx);
/*     */     }
/*     */ 
/*     */     
/*     */     protected PoolChunk<byte[]> newUnpooledChunk(int capacity) {
/* 695 */       return (PoolChunk)new PoolChunk<>(this, null, null, newByteArray(capacity), capacity);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void destroyChunk(PoolChunk<byte[]> chunk) {
/* 700 */       PooledByteBufAllocator.onDeallocateChunk(chunk, !chunk.unpooled);
/*     */       
/* 702 */       if (!chunk.unpooled && this.lastDestroyedChunk.get() == null) {
/* 703 */         this.lastDestroyedChunk.set(chunk);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected PooledByteBuf<byte[]> newByteBuf(int maxCapacity) {
/* 709 */       return PoolArena.HAS_UNSAFE ? PooledUnsafeHeapByteBuf.newUnsafeInstance(maxCapacity) : 
/* 710 */         PooledHeapByteBuf.newInstance(maxCapacity);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void memoryCopy(byte[] src, int srcOffset, PooledByteBuf<byte[]> dst, int length) {
/* 715 */       if (length == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 719 */       System.arraycopy(src, srcOffset, dst.memory, dst.offset, length);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class DirectArena
/*     */     extends PoolArena<ByteBuffer> {
/*     */     DirectArena(PooledByteBufAllocator parent, SizeClasses sizeClass) {
/* 726 */       super(parent, sizeClass);
/*     */     }
/*     */ 
/*     */     
/*     */     boolean isDirect() {
/* 731 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected PoolChunk<ByteBuffer> newChunk(int pageSize, int maxPageIdx, int pageShifts, int chunkSize) {
/* 736 */       if (this.sizeClass.directMemoryCacheAlignment == 0) {
/* 737 */         CleanableDirectBuffer cleanableDirectBuffer1 = allocateDirect(chunkSize);
/* 738 */         ByteBuffer byteBuffer = cleanableDirectBuffer1.buffer();
/* 739 */         return new PoolChunk<>(this, cleanableDirectBuffer1, byteBuffer, byteBuffer, pageSize, pageShifts, chunkSize, maxPageIdx);
/*     */       } 
/*     */ 
/*     */       
/* 743 */       CleanableDirectBuffer cleanableDirectBuffer = allocateDirect(chunkSize + this.sizeClass.directMemoryCacheAlignment);
/*     */       
/* 745 */       ByteBuffer base = cleanableDirectBuffer.buffer();
/* 746 */       ByteBuffer memory = PlatformDependent.alignDirectBuffer(base, this.sizeClass.directMemoryCacheAlignment);
/* 747 */       return new PoolChunk<>(this, cleanableDirectBuffer, base, memory, pageSize, pageShifts, chunkSize, maxPageIdx);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected PoolChunk<ByteBuffer> newUnpooledChunk(int capacity) {
/* 753 */       if (this.sizeClass.directMemoryCacheAlignment == 0) {
/* 754 */         CleanableDirectBuffer cleanableDirectBuffer1 = allocateDirect(capacity);
/* 755 */         ByteBuffer byteBuffer = cleanableDirectBuffer1.buffer();
/* 756 */         return new PoolChunk<>(this, cleanableDirectBuffer1, byteBuffer, byteBuffer, capacity);
/*     */       } 
/*     */       
/* 759 */       CleanableDirectBuffer cleanableDirectBuffer = allocateDirect(capacity + this.sizeClass.directMemoryCacheAlignment);
/*     */       
/* 761 */       ByteBuffer base = cleanableDirectBuffer.buffer();
/* 762 */       ByteBuffer memory = PlatformDependent.alignDirectBuffer(base, this.sizeClass.directMemoryCacheAlignment);
/* 763 */       return new PoolChunk<>(this, cleanableDirectBuffer, base, memory, capacity);
/*     */     }
/*     */     
/*     */     private static CleanableDirectBuffer allocateDirect(int capacity) {
/* 767 */       return PlatformDependent.allocateDirect(capacity);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void destroyChunk(PoolChunk<ByteBuffer> chunk) {
/* 772 */       PooledByteBufAllocator.onDeallocateChunk(chunk, !chunk.unpooled);
/* 773 */       chunk.cleanable.clean();
/*     */     }
/*     */ 
/*     */     
/*     */     protected PooledByteBuf<ByteBuffer> newByteBuf(int maxCapacity) {
/* 778 */       if (PoolArena.HAS_UNSAFE) {
/* 779 */         return PooledUnsafeDirectByteBuf.newInstance(maxCapacity);
/*     */       }
/* 781 */       return PooledDirectByteBuf.newInstance(maxCapacity);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void memoryCopy(ByteBuffer src, int srcOffset, PooledByteBuf<ByteBuffer> dstBuf, int length) {
/* 787 */       if (length == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 791 */       if (PoolArena.HAS_UNSAFE) {
/* 792 */         PlatformDependent.copyMemory(
/* 793 */             PlatformDependent.directBufferAddress(src) + srcOffset, 
/* 794 */             PlatformDependent.directBufferAddress((ByteBuffer)dstBuf.memory) + dstBuf.offset, length);
/*     */       } else {
/*     */         
/* 797 */         src = src.duplicate();
/* 798 */         ByteBuffer dst = dstBuf.internalNioBuffer();
/* 799 */         src.position(srcOffset).limit(srcOffset + length);
/* 800 */         dst.position(dstBuf.offset);
/* 801 */         dst.put(src);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   void lock() {
/* 807 */     this.lock.lock();
/*     */   }
/*     */   
/*     */   void unlock() {
/* 811 */     this.lock.unlock();
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeIdx2size(int sizeIdx) {
/* 816 */     return this.sizeClass.sizeIdx2size(sizeIdx);
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeIdx2sizeCompute(int sizeIdx) {
/* 821 */     return this.sizeClass.sizeIdx2sizeCompute(sizeIdx);
/*     */   }
/*     */ 
/*     */   
/*     */   public long pageIdx2size(int pageIdx) {
/* 826 */     return this.sizeClass.pageIdx2size(pageIdx);
/*     */   }
/*     */ 
/*     */   
/*     */   public long pageIdx2sizeCompute(int pageIdx) {
/* 831 */     return this.sizeClass.pageIdx2sizeCompute(pageIdx);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size2SizeIdx(int size) {
/* 836 */     return this.sizeClass.size2SizeIdx(size);
/*     */   }
/*     */ 
/*     */   
/*     */   public int pages2pageIdx(int pages) {
/* 841 */     return this.sizeClass.pages2pageIdx(pages);
/*     */   }
/*     */ 
/*     */   
/*     */   public int pages2pageIdxFloor(int pages) {
/* 846 */     return this.sizeClass.pages2pageIdxFloor(pages);
/*     */   }
/*     */ 
/*     */   
/*     */   public int normalizeSize(int size) {
/* 851 */     return this.sizeClass.normalizeSize(size);
/*     */   }
/*     */   
/*     */   abstract boolean isDirect();
/*     */   
/*     */   protected abstract PoolChunk<T> newChunk(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   protected abstract PoolChunk<T> newUnpooledChunk(int paramInt);
/*     */   
/*     */   protected abstract PooledByteBuf<T> newByteBuf(int paramInt);
/*     */   
/*     */   protected abstract void memoryCopy(T paramT, int paramInt1, PooledByteBuf<T> paramPooledByteBuf, int paramInt2);
/*     */   
/*     */   protected abstract void destroyChunk(PoolChunk<T> paramPoolChunk);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PoolArena.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */