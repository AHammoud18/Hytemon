/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.CleanableDirectBuffer;
/*     */ import io.netty.util.internal.LongLongHashMap;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
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
/*     */ 
/*     */ 
/*     */ final class PoolChunk<T>
/*     */   implements PoolChunkMetric, ChunkInfo
/*     */ {
/*     */   private static final int SIZE_BIT_LENGTH = 15;
/*     */   private static final int INUSED_BIT_LENGTH = 1;
/*     */   private static final int SUBPAGE_BIT_LENGTH = 1;
/*     */   private static final int BITMAP_IDX_BIT_LENGTH = 32;
/* 145 */   private static final boolean trackPinnedMemory = SystemPropertyUtil.getBoolean("io.netty.trackPinnedMemory", true);
/*     */ 
/*     */   
/*     */   static final int IS_SUBPAGE_SHIFT = 32;
/*     */ 
/*     */   
/*     */   static final int IS_USED_SHIFT = 33;
/*     */ 
/*     */   
/*     */   static final int SIZE_SHIFT = 34;
/*     */ 
/*     */   
/*     */   static final int RUN_OFFSET_SHIFT = 49;
/*     */ 
/*     */   
/*     */   final PoolArena<T> arena;
/*     */ 
/*     */   
/*     */   final CleanableDirectBuffer cleanable;
/*     */   
/*     */   final Object base;
/*     */   
/*     */   final T memory;
/*     */   
/*     */   final boolean unpooled;
/*     */   
/*     */   private final LongLongHashMap runsAvailMap;
/*     */   
/*     */   private final IntPriorityQueue[] runsAvail;
/*     */   
/*     */   private final ReentrantLock runsAvailLock;
/*     */   
/*     */   private final PoolSubpage<T>[] subpages;
/*     */   
/*     */   private final LongAdder pinnedBytes;
/*     */   
/*     */   final int pageSize;
/*     */   
/*     */   final int pageShifts;
/*     */   
/*     */   final int chunkSize;
/*     */   
/*     */   final int maxPageIdx;
/*     */   
/*     */   private final Deque<ByteBuffer> cachedNioBuffers;
/*     */   
/*     */   int freeBytes;
/*     */   
/*     */   PoolChunkList<T> parent;
/*     */   
/*     */   PoolChunk<T> prev;
/*     */   
/*     */   PoolChunk<T> next;
/*     */ 
/*     */   
/*     */   PoolChunk(PoolArena<T> arena, CleanableDirectBuffer cleanable, Object base, T memory, int pageSize, int pageShifts, int chunkSize, int maxPageIdx) {
/* 201 */     this.unpooled = false;
/* 202 */     this.arena = arena;
/* 203 */     this.cleanable = cleanable;
/* 204 */     this.base = base;
/* 205 */     this.memory = memory;
/* 206 */     this.pageSize = pageSize;
/* 207 */     this.pageShifts = pageShifts;
/* 208 */     this.chunkSize = chunkSize;
/* 209 */     this.maxPageIdx = maxPageIdx;
/* 210 */     this.freeBytes = chunkSize;
/*     */     
/* 212 */     this.runsAvail = newRunsAvailqueueArray(maxPageIdx);
/* 213 */     this.runsAvailLock = new ReentrantLock();
/* 214 */     this.runsAvailMap = new LongLongHashMap(-1L);
/* 215 */     this.subpages = (PoolSubpage<T>[])new PoolSubpage[chunkSize >> pageShifts];
/*     */ 
/*     */     
/* 218 */     int pages = chunkSize >> pageShifts;
/* 219 */     long initHandle = pages << 34L;
/* 220 */     insertAvailRun(0, pages, initHandle);
/*     */     
/* 222 */     this.cachedNioBuffers = new ArrayDeque<>(8);
/* 223 */     this.pinnedBytes = trackPinnedMemory ? new LongAdder() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   PoolChunk(PoolArena<T> arena, CleanableDirectBuffer cleanable, Object base, T memory, int size) {
/* 228 */     this.unpooled = true;
/* 229 */     this.arena = arena;
/* 230 */     this.cleanable = cleanable;
/* 231 */     this.base = base;
/* 232 */     this.memory = memory;
/* 233 */     this.pageSize = 0;
/* 234 */     this.pageShifts = 0;
/* 235 */     this.maxPageIdx = 0;
/* 236 */     this.runsAvailMap = null;
/* 237 */     this.runsAvail = null;
/* 238 */     this.runsAvailLock = null;
/* 239 */     this.subpages = null;
/* 240 */     this.chunkSize = size;
/* 241 */     this.cachedNioBuffers = null;
/* 242 */     this.pinnedBytes = trackPinnedMemory ? new LongAdder() : null;
/*     */   }
/*     */   
/*     */   private static IntPriorityQueue[] newRunsAvailqueueArray(int size) {
/* 246 */     IntPriorityQueue[] queueArray = new IntPriorityQueue[size];
/* 247 */     for (int i = 0; i < queueArray.length; i++) {
/* 248 */       queueArray[i] = new IntPriorityQueue();
/*     */     }
/* 250 */     return queueArray;
/*     */   }
/*     */   
/*     */   private void insertAvailRun(int runOffset, int pages, long handle) {
/* 254 */     int pageIdxFloor = this.arena.sizeClass.pages2pageIdxFloor(pages);
/* 255 */     IntPriorityQueue queue = this.runsAvail[pageIdxFloor];
/* 256 */     assert isRun(handle);
/* 257 */     queue.offer((int)(handle >> 32L));
/*     */ 
/*     */     
/* 260 */     insertAvailRun0(runOffset, handle);
/* 261 */     if (pages > 1)
/*     */     {
/* 263 */       insertAvailRun0(lastPage(runOffset, pages), handle);
/*     */     }
/*     */   }
/*     */   
/*     */   private void insertAvailRun0(int runOffset, long handle) {
/* 268 */     long pre = this.runsAvailMap.put(runOffset, handle);
/* 269 */     assert pre == -1L;
/*     */   }
/*     */   
/*     */   private void removeAvailRun(long handle) {
/* 273 */     int pageIdxFloor = this.arena.sizeClass.pages2pageIdxFloor(runPages(handle));
/* 274 */     this.runsAvail[pageIdxFloor].remove((int)(handle >> 32L));
/* 275 */     removeAvailRun0(handle);
/*     */   }
/*     */   
/*     */   private void removeAvailRun0(long handle) {
/* 279 */     int runOffset = runOffset(handle);
/* 280 */     int pages = runPages(handle);
/*     */     
/* 282 */     this.runsAvailMap.remove(runOffset);
/* 283 */     if (pages > 1)
/*     */     {
/* 285 */       this.runsAvailMap.remove(lastPage(runOffset, pages));
/*     */     }
/*     */   }
/*     */   
/*     */   private static int lastPage(int runOffset, int pages) {
/* 290 */     return runOffset + pages - 1;
/*     */   }
/*     */   
/*     */   private long getAvailRunByOffset(int runOffset) {
/* 294 */     return this.runsAvailMap.get(runOffset);
/*     */   }
/*     */ 
/*     */   
/*     */   public int usage() {
/*     */     int freeBytes;
/* 300 */     if (this.unpooled) {
/* 301 */       freeBytes = this.freeBytes;
/*     */     } else {
/* 303 */       this.runsAvailLock.lock();
/*     */       try {
/* 305 */         freeBytes = this.freeBytes;
/*     */       } finally {
/* 307 */         this.runsAvailLock.unlock();
/*     */       } 
/*     */     } 
/* 310 */     return usage(freeBytes);
/*     */   }
/*     */   
/*     */   private int usage(int freeBytes) {
/* 314 */     if (freeBytes == 0) {
/* 315 */       return 100;
/*     */     }
/*     */     
/* 318 */     int freePercentage = (int)(freeBytes * 100L / this.chunkSize);
/* 319 */     if (freePercentage == 0) {
/* 320 */       return 99;
/*     */     }
/* 322 */     return 100 - freePercentage;
/*     */   }
/*     */   
/*     */   boolean allocate(PooledByteBuf<T> buf, int reqCapacity, int sizeIdx, PoolThreadCache cache) {
/*     */     long handle;
/* 327 */     if (sizeIdx <= this.arena.sizeClass.smallMaxSizeIdx) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 332 */       PoolSubpage<T> head = this.arena.smallSubpagePools[sizeIdx];
/* 333 */       head.lock();
/*     */       try {
/* 335 */         PoolSubpage<T> nextSub = head.next;
/* 336 */         if (nextSub != head) {
/* 337 */           assert nextSub.doNotDestroy && nextSub.elemSize == this.arena.sizeClass.sizeIdx2size(sizeIdx) : "doNotDestroy=" + nextSub.doNotDestroy + ", elemSize=" + nextSub.elemSize + ", sizeIdx=" + sizeIdx;
/*     */ 
/*     */           
/* 340 */           long l = nextSub.allocate();
/* 341 */           assert l >= 0L;
/* 342 */           assert isSubpage(l);
/* 343 */           nextSub.chunk.initBufWithSubpage(buf, null, l, reqCapacity, cache, false);
/* 344 */           return true;
/*     */         } 
/* 346 */         handle = allocateSubpage(sizeIdx, head);
/* 347 */         if (handle < 0L) {
/* 348 */           return false;
/*     */         }
/* 350 */         assert isSubpage(handle);
/*     */       } finally {
/* 352 */         head.unlock();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 357 */       int runSize = this.arena.sizeClass.sizeIdx2size(sizeIdx);
/* 358 */       handle = allocateRun(runSize);
/* 359 */       if (handle < 0L) {
/* 360 */         return false;
/*     */       }
/* 362 */       assert !isSubpage(handle);
/*     */     } 
/*     */     
/* 365 */     ByteBuffer nioBuffer = (this.cachedNioBuffers != null) ? this.cachedNioBuffers.pollLast() : null;
/* 366 */     initBuf(buf, nioBuffer, handle, reqCapacity, cache, false);
/* 367 */     return true;
/*     */   }
/*     */   
/*     */   private long allocateRun(int runSize) {
/* 371 */     int pages = runSize >> this.pageShifts;
/* 372 */     int pageIdx = this.arena.sizeClass.pages2pageIdx(pages);
/*     */     
/* 374 */     this.runsAvailLock.lock();
/*     */     
/*     */     try {
/* 377 */       int queueIdx = runFirstBestFit(pageIdx);
/* 378 */       if (queueIdx == -1) {
/* 379 */         return -1L;
/*     */       }
/*     */ 
/*     */       
/* 383 */       IntPriorityQueue queue = this.runsAvail[queueIdx];
/* 384 */       long handle = queue.poll();
/* 385 */       assert handle != -1L;
/* 386 */       handle <<= 32L;
/* 387 */       assert !isUsed(handle) : "invalid handle: " + handle;
/*     */       
/* 389 */       removeAvailRun0(handle);
/*     */       
/* 391 */       handle = splitLargeRun(handle, pages);
/*     */       
/* 393 */       int pinnedSize = runSize(this.pageShifts, handle);
/* 394 */       this.freeBytes -= pinnedSize;
/* 395 */       return handle;
/*     */     } finally {
/* 397 */       this.runsAvailLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private int calculateRunSize(int sizeIdx) {
/* 402 */     int nElements, maxElements = 1 << this.pageShifts - 4;
/* 403 */     int runSize = 0;
/*     */ 
/*     */     
/* 406 */     int elemSize = this.arena.sizeClass.sizeIdx2size(sizeIdx);
/*     */ 
/*     */     
/*     */     do {
/* 410 */       runSize += this.pageSize;
/* 411 */       nElements = runSize / elemSize;
/* 412 */     } while (nElements < maxElements && runSize != nElements * elemSize);
/*     */     
/* 414 */     while (nElements > maxElements) {
/* 415 */       runSize -= this.pageSize;
/* 416 */       nElements = runSize / elemSize;
/*     */     } 
/*     */     
/* 419 */     assert nElements > 0;
/* 420 */     assert runSize <= this.chunkSize;
/* 421 */     assert runSize >= elemSize;
/*     */     
/* 423 */     return runSize;
/*     */   }
/*     */   
/*     */   private int runFirstBestFit(int pageIdx) {
/* 427 */     if (this.freeBytes == this.chunkSize) {
/* 428 */       return this.arena.sizeClass.nPSizes - 1;
/*     */     }
/* 430 */     for (int i = pageIdx; i < this.arena.sizeClass.nPSizes; i++) {
/* 431 */       IntPriorityQueue queue = this.runsAvail[i];
/* 432 */       if (queue != null && !queue.isEmpty()) {
/* 433 */         return i;
/*     */       }
/*     */     } 
/* 436 */     return -1;
/*     */   }
/*     */   
/*     */   private long splitLargeRun(long handle, int needPages) {
/* 440 */     assert needPages > 0;
/*     */     
/* 442 */     int totalPages = runPages(handle);
/* 443 */     assert needPages <= totalPages;
/*     */     
/* 445 */     int remPages = totalPages - needPages;
/*     */     
/* 447 */     if (remPages > 0) {
/* 448 */       int runOffset = runOffset(handle);
/*     */ 
/*     */       
/* 451 */       int availOffset = runOffset + needPages;
/* 452 */       long availRun = toRunHandle(availOffset, remPages, 0);
/* 453 */       insertAvailRun(availOffset, remPages, availRun);
/*     */ 
/*     */       
/* 456 */       return toRunHandle(runOffset, needPages, 1);
/*     */     } 
/*     */ 
/*     */     
/* 460 */     handle |= 0x200000000L;
/* 461 */     return handle;
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
/*     */   private long allocateSubpage(int sizeIdx, PoolSubpage<T> head) {
/* 475 */     int runSize = calculateRunSize(sizeIdx);
/*     */     
/* 477 */     long runHandle = allocateRun(runSize);
/* 478 */     if (runHandle < 0L) {
/* 479 */       return -1L;
/*     */     }
/*     */     
/* 482 */     int runOffset = runOffset(runHandle);
/* 483 */     assert this.subpages[runOffset] == null;
/* 484 */     int elemSize = this.arena.sizeClass.sizeIdx2size(sizeIdx);
/*     */ 
/*     */     
/* 487 */     PoolSubpage<T> subpage = new PoolSubpage<>(head, this, this.pageShifts, runOffset, runSize(this.pageShifts, runHandle), elemSize);
/*     */     
/* 489 */     this.subpages[runOffset] = subpage;
/* 490 */     return subpage.allocate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void free(long handle, int normCapacity, ByteBuffer nioBuffer) {
/* 501 */     if (isSubpage(handle)) {
/* 502 */       int sIdx = runOffset(handle);
/* 503 */       PoolSubpage<T> subpage = this.subpages[sIdx];
/* 504 */       assert subpage != null;
/* 505 */       PoolSubpage<T> head = subpage.chunk.arena.smallSubpagePools[subpage.headIndex];
/*     */ 
/*     */       
/* 508 */       head.lock();
/*     */       try {
/* 510 */         assert subpage.doNotDestroy;
/* 511 */         if (subpage.free(head, bitmapIdx(handle))) {
/*     */           return;
/*     */         }
/*     */         
/* 515 */         assert !subpage.doNotDestroy;
/*     */         
/* 517 */         this.subpages[sIdx] = null;
/*     */       } finally {
/* 519 */         head.unlock();
/*     */       } 
/*     */     } 
/*     */     
/* 523 */     int runSize = runSize(this.pageShifts, handle);
/*     */     
/* 525 */     this.runsAvailLock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 529 */       long finalRun = collapseRuns(handle);
/*     */ 
/*     */       
/* 532 */       finalRun &= 0xFFFFFFFDFFFFFFFFL;
/*     */       
/* 534 */       finalRun &= 0xFFFFFFFEFFFFFFFFL;
/*     */       
/* 536 */       insertAvailRun(runOffset(finalRun), runPages(finalRun), finalRun);
/* 537 */       this.freeBytes += runSize;
/*     */     } finally {
/* 539 */       this.runsAvailLock.unlock();
/*     */     } 
/*     */     
/* 542 */     if (nioBuffer != null && this.cachedNioBuffers != null && this.cachedNioBuffers
/* 543 */       .size() < PooledByteBufAllocator.DEFAULT_MAX_CACHED_BYTEBUFFERS_PER_CHUNK) {
/* 544 */       this.cachedNioBuffers.offer(nioBuffer);
/*     */     }
/*     */   }
/*     */   
/*     */   private long collapseRuns(long handle) {
/* 549 */     return collapseNext(collapsePast(handle));
/*     */   }
/*     */   
/*     */   private long collapsePast(long handle) {
/*     */     while (true) {
/* 554 */       int runOffset = runOffset(handle);
/* 555 */       int runPages = runPages(handle);
/*     */       
/* 557 */       long pastRun = getAvailRunByOffset(runOffset - 1);
/* 558 */       if (pastRun == -1L) {
/* 559 */         return handle;
/*     */       }
/*     */       
/* 562 */       int pastOffset = runOffset(pastRun);
/* 563 */       int pastPages = runPages(pastRun);
/*     */ 
/*     */       
/* 566 */       if (pastRun != handle && pastOffset + pastPages == runOffset) {
/*     */         
/* 568 */         removeAvailRun(pastRun);
/* 569 */         handle = toRunHandle(pastOffset, pastPages + runPages, 0); continue;
/*     */       }  break;
/* 571 */     }  return handle;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private long collapseNext(long handle) {
/*     */     while (true) {
/* 578 */       int runOffset = runOffset(handle);
/* 579 */       int runPages = runPages(handle);
/*     */       
/* 581 */       long nextRun = getAvailRunByOffset(runOffset + runPages);
/* 582 */       if (nextRun == -1L) {
/* 583 */         return handle;
/*     */       }
/*     */       
/* 586 */       int nextOffset = runOffset(nextRun);
/* 587 */       int nextPages = runPages(nextRun);
/*     */ 
/*     */       
/* 590 */       if (nextRun != handle && runOffset + runPages == nextOffset) {
/*     */         
/* 592 */         removeAvailRun(nextRun);
/* 593 */         handle = toRunHandle(runOffset, runPages + nextPages, 0); continue;
/*     */       }  break;
/* 595 */     }  return handle;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static long toRunHandle(int runOffset, int runPages, int inUsed) {
/* 601 */     return runOffset << 49L | runPages << 34L | inUsed << 33L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void initBuf(PooledByteBuf<T> buf, ByteBuffer nioBuffer, long handle, int reqCapacity, PoolThreadCache threadCache, boolean threadLocal) {
/* 608 */     if (isSubpage(handle)) {
/* 609 */       initBufWithSubpage(buf, nioBuffer, handle, reqCapacity, threadCache, threadLocal);
/*     */     } else {
/* 611 */       int maxLength = runSize(this.pageShifts, handle);
/* 612 */       buf.init(this, nioBuffer, handle, runOffset(handle) << this.pageShifts, reqCapacity, maxLength, this.arena.parent
/* 613 */           .threadCache(), threadLocal);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void initBufWithSubpage(PooledByteBuf<T> buf, ByteBuffer nioBuffer, long handle, int reqCapacity, PoolThreadCache threadCache, boolean threadLocal) {
/* 619 */     int runOffset = runOffset(handle);
/* 620 */     int bitmapIdx = bitmapIdx(handle);
/*     */     
/* 622 */     PoolSubpage<T> s = this.subpages[runOffset];
/* 623 */     assert s.isDoNotDestroy();
/* 624 */     assert reqCapacity <= s.elemSize : reqCapacity + "<=" + s.elemSize;
/*     */     
/* 626 */     int offset = (runOffset << this.pageShifts) + bitmapIdx * s.elemSize;
/* 627 */     buf.init(this, nioBuffer, handle, offset, reqCapacity, s.elemSize, threadCache, threadLocal);
/*     */   }
/*     */   
/*     */   void incrementPinnedMemory(int delta) {
/* 631 */     assert delta > 0;
/* 632 */     if (this.pinnedBytes != null) {
/* 633 */       this.pinnedBytes.add(delta);
/*     */     }
/*     */   }
/*     */   
/*     */   void decrementPinnedMemory(int delta) {
/* 638 */     assert delta > 0;
/* 639 */     if (this.pinnedBytes != null) {
/* 640 */       this.pinnedBytes.add(-delta);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int chunkSize() {
/* 646 */     return this.chunkSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public int freeBytes() {
/* 651 */     if (this.unpooled) {
/* 652 */       return this.freeBytes;
/*     */     }
/* 654 */     this.runsAvailLock.lock();
/*     */     try {
/* 656 */       return this.freeBytes;
/*     */     } finally {
/* 658 */       this.runsAvailLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int pinnedBytes() {
/* 663 */     return (this.pinnedBytes == null) ? 0 : (int)this.pinnedBytes.sum();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     int freeBytes;
/* 669 */     if (this.unpooled) {
/* 670 */       freeBytes = this.freeBytes;
/*     */     } else {
/* 672 */       this.runsAvailLock.lock();
/*     */       try {
/* 674 */         freeBytes = this.freeBytes;
/*     */       } finally {
/* 676 */         this.runsAvailLock.unlock();
/*     */       } 
/*     */     } 
/*     */     
/* 680 */     return "Chunk(" + 
/*     */       
/* 682 */       Integer.toHexString(System.identityHashCode(this)) + ": " + 
/*     */       
/* 684 */       usage(freeBytes) + "%, " + (
/* 685 */       this.chunkSize - freeBytes) + 
/* 686 */       '/' + 
/* 687 */       this.chunkSize + 
/* 688 */       ')';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void destroy() {
/* 694 */     this.arena.destroyChunk(this);
/*     */   }
/*     */   
/*     */   static int runOffset(long handle) {
/* 698 */     return (int)(handle >> 49L);
/*     */   }
/*     */   
/*     */   static int runSize(int pageShifts, long handle) {
/* 702 */     return runPages(handle) << pageShifts;
/*     */   }
/*     */   
/*     */   static int runPages(long handle) {
/* 706 */     return (int)(handle >> 34L & 0x7FFFL);
/*     */   }
/*     */   
/*     */   static boolean isUsed(long handle) {
/* 710 */     return ((handle >> 33L & 0x1L) == 1L);
/*     */   }
/*     */   
/*     */   static boolean isRun(long handle) {
/* 714 */     return !isSubpage(handle);
/*     */   }
/*     */   
/*     */   static boolean isSubpage(long handle) {
/* 718 */     return ((handle >> 32L & 0x1L) == 1L);
/*     */   }
/*     */   
/*     */   static int bitmapIdx(long handle) {
/* 722 */     return (int)handle;
/*     */   }
/*     */ 
/*     */   
/*     */   public int capacity() {
/* 727 */     return this.chunkSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/* 732 */     return (this.cleanable != null && this.cleanable.buffer().isDirect());
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/* 737 */     return (this.cleanable != null && this.cleanable.hasMemoryAddress()) ? this.cleanable.memoryAddress() : 0L;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PoolChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */