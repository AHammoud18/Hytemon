/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.internal.MathUtil;
/*     */ import io.netty.util.internal.ObjectPool;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PoolThreadCache
/*     */ {
/*  46 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PoolThreadCache.class);
/*     */   
/*     */   private static final int INTEGER_SIZE_MINUS_ONE = 31;
/*     */   
/*     */   final PoolArena<byte[]> heapArena;
/*     */   
/*     */   final PoolArena<ByteBuffer> directArena;
/*     */   
/*     */   private final MemoryRegionCache<byte[]>[] smallSubPageHeapCaches;
/*     */   private final MemoryRegionCache<ByteBuffer>[] smallSubPageDirectCaches;
/*     */   private final MemoryRegionCache<byte[]>[] normalHeapCaches;
/*     */   private final MemoryRegionCache<ByteBuffer>[] normalDirectCaches;
/*     */   private final int freeSweepAllocationThreshold;
/*  59 */   private final AtomicBoolean freed = new AtomicBoolean();
/*     */ 
/*     */ 
/*     */   
/*     */   private final FreeOnFinalize freeOnFinalize;
/*     */ 
/*     */   
/*     */   private int allocations;
/*     */ 
/*     */ 
/*     */   
/*     */   PoolThreadCache(PoolArena<byte[]> heapArena, PoolArena<ByteBuffer> directArena, int smallCacheSize, int normalCacheSize, int maxCachedBufferCapacity, int freeSweepAllocationThreshold, boolean useFinalizer) {
/*  71 */     ObjectUtil.checkPositiveOrZero(maxCachedBufferCapacity, "maxCachedBufferCapacity");
/*  72 */     this.freeSweepAllocationThreshold = freeSweepAllocationThreshold;
/*  73 */     this.heapArena = heapArena;
/*  74 */     this.directArena = directArena;
/*  75 */     if (directArena != null) {
/*  76 */       this.smallSubPageDirectCaches = createSubPageCaches(smallCacheSize, directArena.sizeClass.nSubpages);
/*  77 */       this.normalDirectCaches = createNormalCaches(normalCacheSize, maxCachedBufferCapacity, directArena);
/*  78 */       directArena.numThreadCaches.getAndIncrement();
/*     */     } else {
/*     */       
/*  81 */       this.smallSubPageDirectCaches = null;
/*  82 */       this.normalDirectCaches = null;
/*     */     } 
/*  84 */     if (heapArena != null) {
/*     */       
/*  86 */       this.smallSubPageHeapCaches = createSubPageCaches(smallCacheSize, heapArena.sizeClass.nSubpages);
/*  87 */       this.normalHeapCaches = createNormalCaches(normalCacheSize, maxCachedBufferCapacity, (PoolArena)heapArena);
/*  88 */       heapArena.numThreadCaches.getAndIncrement();
/*     */     } else {
/*     */       
/*  91 */       this.smallSubPageHeapCaches = null;
/*  92 */       this.normalHeapCaches = null;
/*     */     } 
/*     */ 
/*     */     
/*  96 */     if ((this.smallSubPageDirectCaches != null || this.normalDirectCaches != null || this.smallSubPageHeapCaches != null || this.normalHeapCaches != null) && freeSweepAllocationThreshold < 1)
/*     */     {
/*     */       
/*  99 */       throw new IllegalArgumentException("freeSweepAllocationThreshold: " + freeSweepAllocationThreshold + " (expected: > 0)");
/*     */     }
/*     */     
/* 102 */     this.freeOnFinalize = useFinalizer ? new FreeOnFinalize(this) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> MemoryRegionCache<T>[] createSubPageCaches(int cacheSize, int numCaches) {
/* 107 */     if (cacheSize > 0 && numCaches > 0) {
/*     */       
/* 109 */       MemoryRegionCache[] arrayOfMemoryRegionCache = new MemoryRegionCache[numCaches];
/* 110 */       for (int i = 0; i < arrayOfMemoryRegionCache.length; i++)
/*     */       {
/* 112 */         arrayOfMemoryRegionCache[i] = new SubPageMemoryRegionCache(cacheSize);
/*     */       }
/* 114 */       return (MemoryRegionCache<T>[])arrayOfMemoryRegionCache;
/*     */     } 
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> MemoryRegionCache<T>[] createNormalCaches(int cacheSize, int maxCachedBufferCapacity, PoolArena<T> area) {
/* 123 */     if (cacheSize > 0 && maxCachedBufferCapacity > 0) {
/* 124 */       int max = Math.min(area.sizeClass.chunkSize, maxCachedBufferCapacity);
/*     */ 
/*     */       
/* 127 */       List<MemoryRegionCache<T>> cache = new ArrayList<>();
/* 128 */       for (int idx = area.sizeClass.nSubpages; idx < area.sizeClass.nSizes && area.sizeClass
/* 129 */         .sizeIdx2size(idx) <= max; idx++) {
/* 130 */         cache.add(new NormalMemoryRegionCache<>(cacheSize));
/*     */       }
/* 132 */       return cache.<MemoryRegionCache<T>>toArray((MemoryRegionCache<T>[])new MemoryRegionCache[0]);
/*     */     } 
/* 134 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int log2(int val) {
/* 140 */     return 31 - Integer.numberOfLeadingZeros(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean allocateSmall(PoolArena<?> area, PooledByteBuf<?> buf, int reqCapacity, int sizeIdx) {
/* 147 */     return allocate(cacheForSmall(area, sizeIdx), buf, reqCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean allocateNormal(PoolArena<?> area, PooledByteBuf<?> buf, int reqCapacity, int sizeIdx) {
/* 154 */     return allocate(cacheForNormal(area, sizeIdx), buf, reqCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean allocate(MemoryRegionCache<?> cache, PooledByteBuf<?> buf, int reqCapacity) {
/* 159 */     if (cache == null)
/*     */     {
/* 161 */       return false;
/*     */     }
/* 163 */     boolean allocated = cache.allocate(buf, reqCapacity, this);
/* 164 */     if (++this.allocations >= this.freeSweepAllocationThreshold) {
/* 165 */       this.allocations = 0;
/* 166 */       trim();
/*     */     } 
/* 168 */     return allocated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean add(PoolArena<?> area, PoolChunk<?> chunk, ByteBuffer nioBuffer, long handle, int normCapacity, PoolArena.SizeClass sizeClass) {
/* 178 */     int sizeIdx = area.sizeClass.size2SizeIdx(normCapacity);
/* 179 */     MemoryRegionCache<?> cache = cache(area, sizeIdx, sizeClass);
/* 180 */     if (cache == null) {
/* 181 */       return false;
/*     */     }
/* 183 */     if (this.freed.get()) {
/* 184 */       return false;
/*     */     }
/* 186 */     return cache.add(chunk, nioBuffer, handle, normCapacity);
/*     */   }
/*     */   
/*     */   private MemoryRegionCache<?> cache(PoolArena<?> area, int sizeIdx, PoolArena.SizeClass sizeClass) {
/* 190 */     switch (sizeClass) {
/*     */       case Normal:
/* 192 */         return cacheForNormal(area, sizeIdx);
/*     */       case Small:
/* 194 */         return cacheForSmall(area, sizeIdx);
/*     */     } 
/* 196 */     throw new Error("Unexpected size class: " + sizeClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void free(boolean finalizer) {
/* 206 */     if (this.freed.compareAndSet(false, true)) {
/* 207 */       if (this.freeOnFinalize != null)
/*     */       {
/* 209 */         this.freeOnFinalize.cache = null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 214 */       int numFreed = free((MemoryRegionCache<?>[])this.smallSubPageDirectCaches, finalizer) + free((MemoryRegionCache<?>[])this.normalDirectCaches, finalizer) + free((MemoryRegionCache<?>[])this.smallSubPageHeapCaches, finalizer) + free((MemoryRegionCache<?>[])this.normalHeapCaches, finalizer);
/*     */       
/* 216 */       if (numFreed > 0 && logger.isDebugEnabled()) {
/* 217 */         logger.debug("Freed {} thread-local buffer(s) from thread: {}", Integer.valueOf(numFreed), 
/* 218 */             Thread.currentThread().getName());
/*     */       }
/*     */       
/* 221 */       if (this.directArena != null) {
/* 222 */         this.directArena.numThreadCaches.getAndDecrement();
/*     */       }
/*     */       
/* 225 */       if (this.heapArena != null) {
/* 226 */         this.heapArena.numThreadCaches.getAndDecrement();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int free(MemoryRegionCache<?>[] caches, boolean finalizer) {
/* 232 */     if (caches == null) {
/* 233 */       return 0;
/*     */     }
/*     */     
/* 236 */     int numFreed = 0;
/* 237 */     for (MemoryRegionCache<?> c : caches) {
/* 238 */       numFreed += free(c, finalizer);
/*     */     }
/* 240 */     return numFreed;
/*     */   }
/*     */   
/*     */   private static int free(MemoryRegionCache<?> cache, boolean finalizer) {
/* 244 */     if (cache == null) {
/* 245 */       return 0;
/*     */     }
/* 247 */     return cache.free(finalizer);
/*     */   }
/*     */   
/*     */   void trim() {
/* 251 */     trim((MemoryRegionCache<?>[])this.smallSubPageDirectCaches);
/* 252 */     trim((MemoryRegionCache<?>[])this.normalDirectCaches);
/* 253 */     trim((MemoryRegionCache<?>[])this.smallSubPageHeapCaches);
/* 254 */     trim((MemoryRegionCache<?>[])this.normalHeapCaches);
/*     */   }
/*     */   
/*     */   private static void trim(MemoryRegionCache<?>[] caches) {
/* 258 */     if (caches == null) {
/*     */       return;
/*     */     }
/* 261 */     for (MemoryRegionCache<?> c : caches) {
/* 262 */       trim(c);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void trim(MemoryRegionCache<?> cache) {
/* 267 */     if (cache == null) {
/*     */       return;
/*     */     }
/* 270 */     cache.trim();
/*     */   }
/*     */   
/*     */   private MemoryRegionCache<?> cacheForSmall(PoolArena<?> area, int sizeIdx) {
/* 274 */     if (area.isDirect()) {
/* 275 */       return cache((MemoryRegionCache<?>[])this.smallSubPageDirectCaches, sizeIdx);
/*     */     }
/* 277 */     return cache((MemoryRegionCache<?>[])this.smallSubPageHeapCaches, sizeIdx);
/*     */   }
/*     */ 
/*     */   
/*     */   private MemoryRegionCache<?> cacheForNormal(PoolArena<?> area, int sizeIdx) {
/* 282 */     int idx = sizeIdx - area.sizeClass.nSubpages;
/* 283 */     if (area.isDirect()) {
/* 284 */       return cache((MemoryRegionCache<?>[])this.normalDirectCaches, idx);
/*     */     }
/* 286 */     return cache((MemoryRegionCache<?>[])this.normalHeapCaches, idx);
/*     */   }
/*     */   
/*     */   private static <T> MemoryRegionCache<T> cache(MemoryRegionCache<T>[] cache, int sizeIdx) {
/* 290 */     if (cache == null || sizeIdx >= cache.length) {
/* 291 */       return null;
/*     */     }
/* 293 */     return cache[sizeIdx];
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class SubPageMemoryRegionCache<T>
/*     */     extends MemoryRegionCache<T>
/*     */   {
/*     */     SubPageMemoryRegionCache(int size) {
/* 301 */       super(size, PoolArena.SizeClass.Small);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void initBuf(PoolChunk<T> chunk, ByteBuffer nioBuffer, long handle, PooledByteBuf<T> buf, int reqCapacity, PoolThreadCache threadCache) {
/* 308 */       chunk.initBufWithSubpage(buf, nioBuffer, handle, reqCapacity, threadCache, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class NormalMemoryRegionCache<T>
/*     */     extends MemoryRegionCache<T>
/*     */   {
/*     */     NormalMemoryRegionCache(int size) {
/* 317 */       super(size, PoolArena.SizeClass.Normal);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void initBuf(PoolChunk<T> chunk, ByteBuffer nioBuffer, long handle, PooledByteBuf<T> buf, int reqCapacity, PoolThreadCache threadCache) {
/* 324 */       chunk.initBuf(buf, nioBuffer, handle, reqCapacity, threadCache, true);
/*     */     }
/*     */   }
/*     */   
/*     */   private static abstract class MemoryRegionCache<T> {
/*     */     private final int size;
/*     */     private final Queue<Entry<T>> queue;
/*     */     private final PoolArena.SizeClass sizeClass;
/*     */     private int allocations;
/*     */     
/*     */     MemoryRegionCache(int size, PoolArena.SizeClass sizeClass) {
/* 335 */       this.size = MathUtil.safeFindNextPositivePowerOfTwo(size);
/* 336 */       this.queue = PlatformDependent.newFixedMpscUnpaddedQueue(this.size);
/* 337 */       this.sizeClass = sizeClass;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract void initBuf(PoolChunk<T> param1PoolChunk, ByteBuffer param1ByteBuffer, long param1Long, PooledByteBuf<T> param1PooledByteBuf, int param1Int, PoolThreadCache param1PoolThreadCache);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean add(PoolChunk<T> chunk, ByteBuffer nioBuffer, long handle, int normCapacity) {
/* 351 */       Entry<T> entry = newEntry(chunk, nioBuffer, handle, normCapacity);
/* 352 */       boolean queued = this.queue.offer(entry);
/* 353 */       if (!queued)
/*     */       {
/* 355 */         entry.unguardedRecycle();
/*     */       }
/*     */       
/* 358 */       return queued;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean allocate(PooledByteBuf<T> buf, int reqCapacity, PoolThreadCache threadCache) {
/* 365 */       Entry<T> entry = this.queue.poll();
/* 366 */       if (entry == null) {
/* 367 */         return false;
/*     */       }
/* 369 */       initBuf(entry.chunk, entry.nioBuffer, entry.handle, buf, reqCapacity, threadCache);
/* 370 */       entry.unguardedRecycle();
/*     */ 
/*     */       
/* 373 */       this.allocations++;
/* 374 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int free(boolean finalizer) {
/* 381 */       return free(2147483647, finalizer);
/*     */     }
/*     */     
/*     */     private int free(int max, boolean finalizer) {
/* 385 */       int numFreed = 0;
/* 386 */       for (; numFreed < max; numFreed++) {
/* 387 */         Entry<T> entry = this.queue.poll();
/* 388 */         if (entry != null) {
/* 389 */           freeEntry(entry, finalizer);
/*     */         } else {
/*     */           
/* 392 */           return numFreed;
/*     */         } 
/*     */       } 
/* 395 */       return numFreed;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void trim() {
/* 402 */       int free = this.size - this.allocations;
/* 403 */       this.allocations = 0;
/*     */ 
/*     */       
/* 406 */       if (free > 0) {
/* 407 */         free(free, false);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void freeEntry(Entry entry, boolean finalizer) {
/* 414 */       PoolChunk chunk = entry.chunk;
/* 415 */       long handle = entry.handle;
/* 416 */       ByteBuffer nioBuffer = entry.nioBuffer;
/* 417 */       int normCapacity = entry.normCapacity;
/*     */       
/* 419 */       if (!finalizer)
/*     */       {
/*     */         
/* 422 */         entry.recycle();
/*     */       }
/*     */       
/* 425 */       chunk.arena.freeChunk(chunk, handle, normCapacity, this.sizeClass, nioBuffer, finalizer);
/*     */     }
/*     */     
/*     */     static final class Entry<T> {
/*     */       final Recycler.EnhancedHandle<Entry<?>> recyclerHandle;
/*     */       PoolChunk<T> chunk;
/*     */       ByteBuffer nioBuffer;
/* 432 */       long handle = -1L;
/*     */       int normCapacity;
/*     */       
/*     */       Entry(ObjectPool.Handle<Entry<?>> recyclerHandle) {
/* 436 */         this.recyclerHandle = (Recycler.EnhancedHandle<Entry<?>>)recyclerHandle;
/*     */       }
/*     */       
/*     */       void recycle() {
/* 440 */         this.chunk = null;
/* 441 */         this.nioBuffer = null;
/* 442 */         this.handle = -1L;
/* 443 */         this.recyclerHandle.recycle(this);
/*     */       }
/*     */       
/*     */       void unguardedRecycle() {
/* 447 */         this.chunk = null;
/* 448 */         this.nioBuffer = null;
/* 449 */         this.handle = -1L;
/* 450 */         this.recyclerHandle.unguardedRecycle(this);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private static Entry newEntry(PoolChunk<?> chunk, ByteBuffer nioBuffer, long handle, int normCapacity) {
/* 456 */       Entry entry = (Entry)RECYCLER.get();
/* 457 */       entry.chunk = chunk;
/* 458 */       entry.nioBuffer = nioBuffer;
/* 459 */       entry.handle = handle;
/* 460 */       entry.normCapacity = normCapacity;
/* 461 */       return entry;
/*     */     }
/*     */ 
/*     */     
/* 465 */     private static final Recycler<Entry> RECYCLER = new Recycler<Entry>()
/*     */       {
/*     */         protected PoolThreadCache.MemoryRegionCache.Entry newObject(Recycler.Handle<PoolThreadCache.MemoryRegionCache.Entry> handle)
/*     */         {
/* 469 */           return new PoolThreadCache.MemoryRegionCache.Entry((ObjectPool.Handle)handle);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static final class FreeOnFinalize
/*     */   {
/*     */     private volatile PoolThreadCache cache;
/*     */     
/*     */     private FreeOnFinalize(PoolThreadCache cache) {
/* 479 */       this.cache = cache;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void finalize() throws Throwable {
/*     */       try {
/* 487 */         super.finalize();
/*     */       } finally {
/* 489 */         PoolThreadCache cache = this.cache;
/*     */ 
/*     */         
/* 492 */         this.cache = null;
/* 493 */         if (cache != null)
/* 494 */           cache.free(true); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\PoolThreadCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */