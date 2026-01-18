/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.concurrent.FastThreadLocalThread;
/*     */ import io.netty.util.internal.ObjectPool;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Objects;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import org.jetbrains.annotations.VisibleForTesting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Recycler<T>
/*     */ {
/*  45 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Recycler.class);
/*     */ 
/*     */   
/*     */   private static final class LocalPoolHandle<T>
/*     */     extends EnhancedHandle<T>
/*     */   {
/*     */     private final Recycler.UnguardedLocalPool<T> pool;
/*     */ 
/*     */     
/*     */     private LocalPoolHandle(Recycler.UnguardedLocalPool<T> pool) {
/*  55 */       this.pool = pool;
/*     */     }
/*     */ 
/*     */     
/*     */     public void recycle(T object) {
/*  60 */       Recycler.UnguardedLocalPool<T> pool = this.pool;
/*  61 */       if (pool != null) {
/*  62 */         pool.release(object);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void unguardedRecycle(Object object) {
/*  68 */       Recycler.UnguardedLocalPool<T> pool = this.pool;
/*  69 */       if (pool != null) {
/*  70 */         pool.release((T)object);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*  75 */   private static final EnhancedHandle<?> NOOP_HANDLE = new LocalPoolHandle(null);
/*  76 */   private static final UnguardedLocalPool<?> NOOP_LOCAL_POOL = new UnguardedLocalPool(0);
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_INITIAL_MAX_CAPACITY_PER_THREAD = 4096;
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_MAX_CAPACITY_PER_THREAD;
/*     */   
/*     */   private static final int RATIO;
/*     */ 
/*     */   
/*     */   static {
/*  88 */     int maxCapacityPerThread = SystemPropertyUtil.getInt("io.netty.recycler.maxCapacityPerThread", 
/*  89 */         SystemPropertyUtil.getInt("io.netty.recycler.maxCapacity", 4096));
/*  90 */     if (maxCapacityPerThread < 0) {
/*  91 */       maxCapacityPerThread = 4096;
/*     */     }
/*     */     
/*  94 */     DEFAULT_MAX_CAPACITY_PER_THREAD = maxCapacityPerThread;
/*  95 */   } private static final int DEFAULT_QUEUE_CHUNK_SIZE_PER_THREAD = SystemPropertyUtil.getInt("io.netty.recycler.chunkSize", 32); private static final boolean BLOCKING_POOL; private static final boolean BATCH_FAST_TL_ONLY;
/*     */   private final LocalPool<?, T> localPool;
/*     */   private final FastThreadLocal<LocalPool<?, T>> threadLocalPool;
/*     */   
/*     */   static {
/* 100 */     RATIO = Math.max(0, SystemPropertyUtil.getInt("io.netty.recycler.ratio", 8));
/*     */     
/* 102 */     BLOCKING_POOL = SystemPropertyUtil.getBoolean("io.netty.recycler.blocking", false);
/* 103 */     BATCH_FAST_TL_ONLY = SystemPropertyUtil.getBoolean("io.netty.recycler.batchFastThreadLocalOnly", true);
/*     */     
/* 105 */     if (logger.isDebugEnabled()) {
/* 106 */       if (DEFAULT_MAX_CAPACITY_PER_THREAD == 0) {
/* 107 */         logger.debug("-Dio.netty.recycler.maxCapacityPerThread: disabled");
/* 108 */         logger.debug("-Dio.netty.recycler.ratio: disabled");
/* 109 */         logger.debug("-Dio.netty.recycler.chunkSize: disabled");
/* 110 */         logger.debug("-Dio.netty.recycler.blocking: disabled");
/* 111 */         logger.debug("-Dio.netty.recycler.batchFastThreadLocalOnly: disabled");
/*     */       } else {
/* 113 */         logger.debug("-Dio.netty.recycler.maxCapacityPerThread: {}", Integer.valueOf(DEFAULT_MAX_CAPACITY_PER_THREAD));
/* 114 */         logger.debug("-Dio.netty.recycler.ratio: {}", Integer.valueOf(RATIO));
/* 115 */         logger.debug("-Dio.netty.recycler.chunkSize: {}", Integer.valueOf(DEFAULT_QUEUE_CHUNK_SIZE_PER_THREAD));
/* 116 */         logger.debug("-Dio.netty.recycler.blocking: {}", Boolean.valueOf(BLOCKING_POOL));
/* 117 */         logger.debug("-Dio.netty.recycler.batchFastThreadLocalOnly: {}", Boolean.valueOf(BATCH_FAST_TL_ONLY));
/*     */       } 
/*     */     }
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected Recycler(int maxCapacity, boolean unguarded) {
/* 137 */     if (maxCapacity <= 0) {
/* 138 */       maxCapacity = 0;
/*     */     } else {
/* 140 */       maxCapacity = Math.max(4, maxCapacity);
/*     */     } 
/* 142 */     this.threadLocalPool = null;
/* 143 */     if (maxCapacity == 0) {
/* 144 */       this.localPool = (LocalPool)NOOP_LOCAL_POOL;
/*     */     } else {
/* 146 */       this.localPool = unguarded ? new UnguardedLocalPool(maxCapacity) : new GuardedLocalPool<>(maxCapacity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Recycler(boolean unguarded) {
/* 158 */     this(DEFAULT_MAX_CAPACITY_PER_THREAD, RATIO, DEFAULT_QUEUE_CHUNK_SIZE_PER_THREAD, unguarded);
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
/*     */   protected Recycler(Thread owner, boolean unguarded) {
/* 172 */     this(DEFAULT_MAX_CAPACITY_PER_THREAD, RATIO, DEFAULT_QUEUE_CHUNK_SIZE_PER_THREAD, owner, unguarded);
/*     */   }
/*     */   
/*     */   protected Recycler(int maxCapacityPerThread) {
/* 176 */     this(maxCapacityPerThread, RATIO, DEFAULT_QUEUE_CHUNK_SIZE_PER_THREAD);
/*     */   }
/*     */   
/*     */   protected Recycler() {
/* 180 */     this(DEFAULT_MAX_CAPACITY_PER_THREAD);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Recycler(int chunksSize, int maxCapacityPerThread, boolean unguarded) {
/* 189 */     this(maxCapacityPerThread, RATIO, chunksSize, unguarded);
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
/*     */   protected Recycler(int chunkSize, int maxCapacityPerThread, Thread owner, boolean unguarded) {
/* 201 */     this(maxCapacityPerThread, RATIO, chunkSize, owner, unguarded);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected Recycler(int maxCapacityPerThread, int maxSharedCapacityFactor) {
/* 211 */     this(maxCapacityPerThread, RATIO, DEFAULT_QUEUE_CHUNK_SIZE_PER_THREAD);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected Recycler(int maxCapacityPerThread, int maxSharedCapacityFactor, int ratio, int maxDelayedQueuesPerThread) {
/* 222 */     this(maxCapacityPerThread, ratio, DEFAULT_QUEUE_CHUNK_SIZE_PER_THREAD);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected Recycler(int maxCapacityPerThread, int maxSharedCapacityFactor, int ratio, int maxDelayedQueuesPerThread, int delayedQueueRatio) {
/* 233 */     this(maxCapacityPerThread, ratio, DEFAULT_QUEUE_CHUNK_SIZE_PER_THREAD);
/*     */   }
/*     */   
/*     */   protected Recycler(int maxCapacityPerThread, int interval, int chunkSize) {
/* 237 */     this(maxCapacityPerThread, interval, chunkSize, true, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Recycler(int maxCapacityPerThread, int interval, int chunkSize, boolean unguarded) {
/* 246 */     this(maxCapacityPerThread, interval, chunkSize, true, null, unguarded);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Recycler(int maxCapacityPerThread, int interval, int chunkSize, Thread owner, boolean unguarded) {
/* 255 */     this(maxCapacityPerThread, interval, chunkSize, false, owner, unguarded);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Recycler(int maxCapacityPerThread, int ratio, int chunkSize, boolean useThreadLocalStorage, Thread owner, final boolean unguarded) {
/* 261 */     final int interval = Math.max(0, ratio);
/* 262 */     if (maxCapacityPerThread <= 0) {
/* 263 */       maxCapacityPerThread = 0;
/* 264 */       chunkSize = 0;
/*     */     } else {
/* 266 */       maxCapacityPerThread = Math.max(4, maxCapacityPerThread);
/* 267 */       chunkSize = Math.max(2, Math.min(chunkSize, maxCapacityPerThread >> 1));
/*     */     } 
/* 269 */     if (maxCapacityPerThread > 0 && useThreadLocalStorage) {
/* 270 */       final int finalMaxCapacityPerThread = maxCapacityPerThread;
/* 271 */       final int finalChunkSize = chunkSize;
/* 272 */       this.threadLocalPool = new FastThreadLocal<LocalPool<?, T>>()
/*     */         {
/*     */           protected Recycler.LocalPool<?, T> initialValue() {
/* 275 */             return unguarded ? new Recycler.UnguardedLocalPool(finalMaxCapacityPerThread, interval, finalChunkSize) : 
/* 276 */               new Recycler.GuardedLocalPool<>(finalMaxCapacityPerThread, interval, finalChunkSize);
/*     */           }
/*     */ 
/*     */           
/*     */           protected void onRemoval(Recycler.LocalPool<?, T> value) throws Exception {
/* 281 */             super.onRemoval(value);
/* 282 */             MessagePassingQueue<?> handles = value.pooledHandles;
/* 283 */             value.pooledHandles = null;
/* 284 */             value.owner = null;
/* 285 */             if (handles != null) {
/* 286 */               handles.clear();
/*     */             }
/*     */           }
/*     */         };
/* 290 */       this.localPool = null;
/*     */     } else {
/* 292 */       this.threadLocalPool = null;
/* 293 */       if (maxCapacityPerThread == 0) {
/* 294 */         this.localPool = (LocalPool)NOOP_LOCAL_POOL;
/*     */       } else {
/* 296 */         Objects.requireNonNull(owner, "owner");
/* 297 */         this
/* 298 */           .localPool = unguarded ? new UnguardedLocalPool(owner, maxCapacityPerThread, interval, chunkSize) : new GuardedLocalPool<>(owner, maxCapacityPerThread, interval, chunkSize);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final T get() {
/* 305 */     if (this.localPool != null) {
/* 306 */       return this.localPool.getWith(this);
/*     */     }
/* 308 */     if (PlatformDependent.isVirtualThread(Thread.currentThread()) && 
/* 309 */       !FastThreadLocalThread.currentThreadHasFastThreadLocal()) {
/* 310 */       return newObject((Handle)NOOP_HANDLE);
/*     */     }
/* 312 */     return (T)((LocalPool)this.threadLocalPool.get()).getWith(this);
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
/*     */   public static void unpinOwner(Recycler<?> recycler) {
/* 325 */     if (recycler.localPool != null) {
/* 326 */       recycler.localPool.owner = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final boolean recycle(T o, Handle<T> handle) {
/* 335 */     if (handle == NOOP_HANDLE) {
/* 336 */       return false;
/*     */     }
/*     */     
/* 339 */     handle.recycle(o);
/* 340 */     return true;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   final int threadLocalSize() {
/* 345 */     if (this.localPool != null) {
/* 346 */       return this.localPool.size();
/*     */     }
/* 348 */     if (PlatformDependent.isVirtualThread(Thread.currentThread()) && 
/* 349 */       !FastThreadLocalThread.currentThreadHasFastThreadLocal()) {
/* 350 */       return 0;
/*     */     }
/* 352 */     LocalPool<?, T> pool = (LocalPool<?, T>)this.threadLocalPool.getIfExists();
/* 353 */     if (pool == null) {
/* 354 */       return 0;
/*     */     }
/* 356 */     return pool.size();
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract T newObject(Handle<T> paramHandle);
/*     */ 
/*     */   
/*     */   public static abstract class EnhancedHandle<T>
/*     */     implements Handle<T>
/*     */   {
/*     */     private EnhancedHandle() {}
/*     */     
/*     */     public abstract void unguardedRecycle(Object param1Object);
/*     */   }
/*     */   
/*     */   private static final class DefaultHandle<T>
/*     */     extends EnhancedHandle<T>
/*     */   {
/*     */     private static final int STATE_CLAIMED = 0;
/*     */     private static final int STATE_AVAILABLE = 1;
/*     */     private static final AtomicIntegerFieldUpdater<DefaultHandle<?>> STATE_UPDATER;
/*     */     private volatile int state;
/*     */     private final Recycler.GuardedLocalPool<T> localPool;
/*     */     private T value;
/*     */     
/*     */     static {
/* 382 */       AtomicIntegerFieldUpdater<?> updater = AtomicIntegerFieldUpdater.newUpdater(DefaultHandle.class, "state");
/*     */       
/* 384 */       STATE_UPDATER = (AtomicIntegerFieldUpdater)updater;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DefaultHandle(Recycler.GuardedLocalPool<T> localPool) {
/* 392 */       this.localPool = localPool;
/*     */     }
/*     */ 
/*     */     
/*     */     public void recycle(Object object) {
/* 397 */       if (object != this.value) {
/* 398 */         throw new IllegalArgumentException("object does not belong to handle");
/*     */       }
/* 400 */       toAvailable();
/* 401 */       this.localPool.release(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public void unguardedRecycle(Object object) {
/* 406 */       if (object != this.value) {
/* 407 */         throw new IllegalArgumentException("object does not belong to handle");
/*     */       }
/* 409 */       unguardedToAvailable();
/* 410 */       this.localPool.release(this);
/*     */     }
/*     */     
/*     */     T claim() {
/* 414 */       assert this.state == 1;
/* 415 */       STATE_UPDATER.lazySet(this, 0);
/* 416 */       return this.value;
/*     */     }
/*     */     
/*     */     void set(T value) {
/* 420 */       this.value = value;
/*     */     }
/*     */     
/*     */     private void toAvailable() {
/* 424 */       int prev = STATE_UPDATER.getAndSet(this, 1);
/* 425 */       if (prev == 1) {
/* 426 */         throw new IllegalStateException("Object has been recycled already.");
/*     */       }
/*     */     }
/*     */     
/*     */     private void unguardedToAvailable() {
/* 431 */       int prev = this.state;
/* 432 */       if (prev == 1) {
/* 433 */         throw new IllegalStateException("Object has been recycled already.");
/*     */       }
/* 435 */       STATE_UPDATER.lazySet(this, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class GuardedLocalPool<T>
/*     */     extends LocalPool<DefaultHandle<T>, T> {
/*     */     GuardedLocalPool(int maxCapacity) {
/* 442 */       super(maxCapacity);
/*     */     }
/*     */     
/*     */     GuardedLocalPool(Thread owner, int maxCapacity, int ratioInterval, int chunkSize) {
/* 446 */       super(owner, maxCapacity, ratioInterval, chunkSize);
/*     */     }
/*     */     
/*     */     GuardedLocalPool(int maxCapacity, int ratioInterval, int chunkSize) {
/* 450 */       super(maxCapacity, ratioInterval, chunkSize);
/*     */     }
/*     */     
/*     */     public T getWith(Recycler<T> recycler) {
/*     */       T obj;
/* 455 */       Recycler.DefaultHandle<T> handle = acquire();
/*     */       
/* 457 */       if (handle == null) {
/* 458 */         handle = canAllocatePooled() ? new Recycler.DefaultHandle<>(this) : null;
/* 459 */         if (handle != null) {
/* 460 */           obj = recycler.newObject(handle);
/* 461 */           handle.set(obj);
/*     */         } else {
/* 463 */           obj = recycler.newObject((Recycler.Handle)Recycler.NOOP_HANDLE);
/*     */         } 
/*     */       } else {
/* 466 */         obj = handle.claim();
/*     */       } 
/* 468 */       return obj;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class UnguardedLocalPool<T> extends LocalPool<T, T> {
/*     */     private final Recycler.EnhancedHandle<T> handle;
/*     */     
/*     */     UnguardedLocalPool(int maxCapacity) {
/* 476 */       super(maxCapacity);
/* 477 */       this.handle = (maxCapacity == 0) ? null : new Recycler.LocalPoolHandle<>(this);
/*     */     }
/*     */     
/*     */     UnguardedLocalPool(Thread owner, int maxCapacity, int ratioInterval, int chunkSize) {
/* 481 */       super(owner, maxCapacity, ratioInterval, chunkSize);
/* 482 */       this.handle = new Recycler.LocalPoolHandle<>(this);
/*     */     }
/*     */     
/*     */     UnguardedLocalPool(int maxCapacity, int ratioInterval, int chunkSize) {
/* 486 */       super(maxCapacity, ratioInterval, chunkSize);
/* 487 */       this.handle = new Recycler.LocalPoolHandle<>(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public T getWith(Recycler<T> recycler) {
/* 492 */       T obj = acquire();
/* 493 */       if (obj == null) {
/* 494 */         obj = recycler.newObject(canAllocatePooled() ? this.handle : (Recycler.Handle)Recycler.NOOP_HANDLE);
/*     */       }
/* 496 */       return obj;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class LocalPool<H, T>
/*     */   {
/*     */     private final int ratioInterval;
/*     */     
/*     */     private final H[] batch;
/*     */     private int batchSize;
/*     */     private Thread owner;
/*     */     private MessagePassingQueue<H> pooledHandles;
/*     */     private int ratioCounter;
/*     */     
/*     */     LocalPool(int maxCapacity) {
/* 512 */       this.ratioInterval = (maxCapacity == 0) ? -1 : 0;
/* 513 */       this.owner = null;
/* 514 */       this.batch = null;
/* 515 */       this.batchSize = 0;
/* 516 */       this.pooledHandles = createExternalMcPool(maxCapacity);
/* 517 */       this.ratioCounter = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     LocalPool(Thread owner, int maxCapacity, int ratioInterval, int chunkSize) {
/* 522 */       this.ratioInterval = ratioInterval;
/* 523 */       this.owner = owner;
/* 524 */       this.batch = (owner != null) ? (H[])new Object[chunkSize] : null;
/* 525 */       this.batchSize = 0;
/* 526 */       this.pooledHandles = createExternalScPool(chunkSize, maxCapacity);
/* 527 */       this.ratioCounter = ratioInterval;
/*     */     }
/*     */     
/*     */     private static <H> MessagePassingQueue<H> createExternalMcPool(int maxCapacity) {
/* 531 */       if (maxCapacity == 0) {
/* 532 */         return null;
/*     */       }
/* 534 */       if (Recycler.BLOCKING_POOL) {
/* 535 */         return new Recycler.BlockingMessageQueue<>(maxCapacity);
/*     */       }
/* 537 */       return (MessagePassingQueue<H>)PlatformDependent.newFixedMpmcQueue(maxCapacity);
/*     */     }
/*     */     
/*     */     private static <H> MessagePassingQueue<H> createExternalScPool(int chunkSize, int maxCapacity) {
/* 541 */       if (maxCapacity == 0) {
/* 542 */         return null;
/*     */       }
/* 544 */       if (Recycler.BLOCKING_POOL) {
/* 545 */         return new Recycler.BlockingMessageQueue<>(maxCapacity);
/*     */       }
/* 547 */       return (MessagePassingQueue<H>)PlatformDependent.newMpscQueue(chunkSize, maxCapacity);
/*     */     }
/*     */     
/*     */     LocalPool(int maxCapacity, int ratioInterval, int chunkSize) {
/* 551 */       this((!Recycler.BATCH_FAST_TL_ONLY || FastThreadLocalThread.currentThreadHasFastThreadLocal()) ? 
/* 552 */           Thread.currentThread() : null, maxCapacity, ratioInterval, chunkSize);
/*     */     }
/*     */     
/*     */     protected final H acquire() {
/* 556 */       int size = this.batchSize;
/* 557 */       if (size == 0) {
/*     */         
/* 559 */         MessagePassingQueue<H> handles = this.pooledHandles;
/* 560 */         if (handles == null) {
/* 561 */           return null;
/*     */         }
/* 563 */         return (H)handles.relaxedPoll();
/*     */       } 
/* 565 */       int top = size - 1;
/* 566 */       H h = this.batch[top];
/* 567 */       this.batchSize = top;
/* 568 */       this.batch[top] = null;
/* 569 */       return h;
/*     */     }
/*     */     
/*     */     protected final void release(H handle) {
/* 573 */       Thread owner = this.owner;
/* 574 */       if (owner != null && Thread.currentThread() == owner && this.batchSize < this.batch.length) {
/* 575 */         this.batch[this.batchSize] = handle;
/* 576 */         this.batchSize++;
/* 577 */       } else if (owner != null && isTerminated(owner)) {
/* 578 */         this.pooledHandles = null;
/* 579 */         this.owner = null;
/*     */       } else {
/* 581 */         MessagePassingQueue<H> handles = this.pooledHandles;
/* 582 */         if (handles != null) {
/* 583 */           handles.relaxedOffer(handle);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static boolean isTerminated(Thread owner) {
/* 591 */       return PlatformDependent.isJ9Jvm() ? (!owner.isAlive()) : ((owner.getState() == Thread.State.TERMINATED));
/*     */     }
/*     */     
/*     */     boolean canAllocatePooled() {
/* 595 */       if (this.ratioInterval < 0) {
/* 596 */         return false;
/*     */       }
/* 598 */       if (this.ratioInterval == 0) {
/* 599 */         return true;
/*     */       }
/* 601 */       if (++this.ratioCounter >= this.ratioInterval) {
/* 602 */         this.ratioCounter = 0;
/* 603 */         return true;
/*     */       } 
/* 605 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     int size() {
/* 611 */       MessagePassingQueue<H> handles = this.pooledHandles;
/* 612 */       int externalSize = (handles != null) ? handles.size() : 0;
/* 613 */       return externalSize + ((this.batch != null) ? this.batchSize : 0);
/*     */     }
/*     */ 
/*     */     
/*     */     abstract T getWith(Recycler<T> param1Recycler);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class BlockingMessageQueue<T>
/*     */     implements MessagePassingQueue<T>
/*     */   {
/*     */     private final Queue<T> deque;
/*     */     private final int maxCapacity;
/*     */     
/*     */     BlockingMessageQueue(int maxCapacity) {
/* 628 */       this.maxCapacity = maxCapacity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 638 */       this.deque = new ArrayDeque<>();
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized boolean offer(T e) {
/* 643 */       if (this.deque.size() == this.maxCapacity) {
/* 644 */         return false;
/*     */       }
/* 646 */       return this.deque.offer(e);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized T poll() {
/* 651 */       return this.deque.poll();
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized T peek() {
/* 656 */       return this.deque.peek();
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized int size() {
/* 661 */       return this.deque.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized void clear() {
/* 666 */       this.deque.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized boolean isEmpty() {
/* 671 */       return this.deque.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public int capacity() {
/* 676 */       return this.maxCapacity;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean relaxedOffer(T e) {
/* 681 */       return offer(e);
/*     */     }
/*     */ 
/*     */     
/*     */     public T relaxedPoll() {
/* 686 */       return poll();
/*     */     }
/*     */ 
/*     */     
/*     */     public T relaxedPeek() {
/* 691 */       return peek();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int drain(MessagePassingQueue.Consumer<T> c, int limit) {
/* 697 */       int i = 0; T obj;
/* 698 */       for (; i < limit && (obj = poll()) != null; i++) {
/* 699 */         c.accept(obj);
/*     */       }
/* 701 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public int fill(MessagePassingQueue.Supplier<T> s, int limit) {
/* 706 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int drain(MessagePassingQueue.Consumer<T> c) {
/* 711 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int fill(MessagePassingQueue.Supplier<T> s) {
/* 716 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void drain(MessagePassingQueue.Consumer<T> c, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 721 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void fill(MessagePassingQueue.Supplier<T> s, MessagePassingQueue.WaitStrategy wait, MessagePassingQueue.ExitCondition exit) {
/* 726 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Handle<T> extends ObjectPool.Handle<T> {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\Recycler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */