/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import io.netty.util.internal.LongLongHashMap;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastThreadLocalThread
/*     */   extends Thread
/*     */ {
/*  30 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(FastThreadLocalThread.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   private static final AtomicReference<FallbackThreadSet> fallbackThreads = new AtomicReference<>(FallbackThreadSet.EMPTY);
/*     */ 
/*     */   
/*     */   private final boolean cleanupFastThreadLocals;
/*     */   
/*     */   private InternalThreadLocalMap threadLocalMap;
/*     */ 
/*     */   
/*     */   public FastThreadLocalThread() {
/*  44 */     this.cleanupFastThreadLocals = false;
/*     */   }
/*     */   
/*     */   public FastThreadLocalThread(Runnable target) {
/*  48 */     super(FastThreadLocalRunnable.wrap(target));
/*  49 */     this.cleanupFastThreadLocals = true;
/*     */   }
/*     */   
/*     */   public FastThreadLocalThread(ThreadGroup group, Runnable target) {
/*  53 */     super(group, FastThreadLocalRunnable.wrap(target));
/*  54 */     this.cleanupFastThreadLocals = true;
/*     */   }
/*     */   
/*     */   public FastThreadLocalThread(String name) {
/*  58 */     super(name);
/*  59 */     this.cleanupFastThreadLocals = false;
/*     */   }
/*     */   
/*     */   public FastThreadLocalThread(ThreadGroup group, String name) {
/*  63 */     super(group, name);
/*  64 */     this.cleanupFastThreadLocals = false;
/*     */   }
/*     */   
/*     */   public FastThreadLocalThread(Runnable target, String name) {
/*  68 */     super(FastThreadLocalRunnable.wrap(target), name);
/*  69 */     this.cleanupFastThreadLocals = true;
/*     */   }
/*     */   
/*     */   public FastThreadLocalThread(ThreadGroup group, Runnable target, String name) {
/*  73 */     super(group, FastThreadLocalRunnable.wrap(target), name);
/*  74 */     this.cleanupFastThreadLocals = true;
/*     */   }
/*     */   
/*     */   public FastThreadLocalThread(ThreadGroup group, Runnable target, String name, long stackSize) {
/*  78 */     super(group, FastThreadLocalRunnable.wrap(target), name, stackSize);
/*  79 */     this.cleanupFastThreadLocals = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final InternalThreadLocalMap threadLocalMap() {
/*  87 */     if (this != Thread.currentThread() && logger.isWarnEnabled()) {
/*  88 */       logger.warn(new RuntimeException("It's not thread-safe to get 'threadLocalMap' which doesn't belong to the caller thread"));
/*     */     }
/*     */     
/*  91 */     return this.threadLocalMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setThreadLocalMap(InternalThreadLocalMap threadLocalMap) {
/*  99 */     if (this != Thread.currentThread() && logger.isWarnEnabled()) {
/* 100 */       logger.warn(new RuntimeException("It's not thread-safe to set 'threadLocalMap' which doesn't belong to the caller thread"));
/*     */     }
/*     */     
/* 103 */     this.threadLocalMap = threadLocalMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean willCleanupFastThreadLocals() {
/* 113 */     return this.cleanupFastThreadLocals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean willCleanupFastThreadLocals(Thread thread) {
/* 123 */     return (thread instanceof FastThreadLocalThread && ((FastThreadLocalThread)thread)
/* 124 */       .willCleanupFastThreadLocals());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean currentThreadWillCleanupFastThreadLocals() {
/* 132 */     Thread currentThread = currentThread();
/* 133 */     if (currentThread instanceof FastThreadLocalThread) {
/* 134 */       return ((FastThreadLocalThread)currentThread).willCleanupFastThreadLocals();
/*     */     }
/* 136 */     return isFastThreadLocalVirtualThread();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean currentThreadHasFastThreadLocal() {
/* 144 */     return (currentThread() instanceof FastThreadLocalThread || isFastThreadLocalVirtualThread());
/*     */   }
/*     */   
/*     */   private static boolean isFastThreadLocalVirtualThread() {
/* 148 */     return ((FallbackThreadSet)fallbackThreads.get()).contains(currentThread().getId());
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
/*     */   public static void runWithFastThreadLocal(Runnable runnable) {
/* 164 */     Thread current = currentThread();
/* 165 */     if (current instanceof FastThreadLocalThread) {
/* 166 */       throw new IllegalStateException("Caller is a real FastThreadLocalThread");
/*     */     }
/* 168 */     long id = current.getId();
/* 169 */     fallbackThreads.updateAndGet(set -> {
/*     */           if (set.contains(id)) {
/*     */             throw new IllegalStateException("Reentrant call to run()");
/*     */           }
/*     */           
/*     */           return set.add(id);
/*     */         });
/*     */     try {
/* 177 */       runnable.run();
/*     */     } finally {
/* 179 */       fallbackThreads.getAndUpdate(set -> set.remove(id));
/* 180 */       FastThreadLocal.removeAll();
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
/*     */   public boolean permitBlockingCalls() {
/* 195 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class FallbackThreadSet
/*     */   {
/* 202 */     static final FallbackThreadSet EMPTY = new FallbackThreadSet();
/*     */     
/*     */     private static final long EMPTY_VALUE = 0L;
/*     */     private final LongLongHashMap map;
/*     */     
/*     */     private FallbackThreadSet() {
/* 208 */       this.map = new LongLongHashMap(0L);
/*     */     }
/*     */     
/*     */     private FallbackThreadSet(LongLongHashMap map) {
/* 212 */       this.map = map;
/*     */     }
/*     */     
/*     */     public boolean contains(long threadId) {
/* 216 */       long key = threadId >>> 6L;
/* 217 */       long bit = 1L << (int)(threadId & 0x3FL);
/*     */       
/* 219 */       long bitmap = this.map.get(key);
/* 220 */       return ((bitmap & bit) != 0L);
/*     */     }
/*     */     
/*     */     public FallbackThreadSet add(long threadId) {
/* 224 */       long key = threadId >>> 6L;
/* 225 */       long bit = 1L << (int)(threadId & 0x3FL);
/*     */       
/* 227 */       LongLongHashMap newMap = new LongLongHashMap(this.map);
/* 228 */       long oldBitmap = newMap.get(key);
/* 229 */       long newBitmap = oldBitmap | bit;
/* 230 */       newMap.put(key, newBitmap);
/*     */       
/* 232 */       return new FallbackThreadSet(newMap);
/*     */     }
/*     */     
/*     */     public FallbackThreadSet remove(long threadId) {
/* 236 */       long key = threadId >>> 6L;
/* 237 */       long bit = 1L << (int)(threadId & 0x3FL);
/*     */       
/* 239 */       long oldBitmap = this.map.get(key);
/* 240 */       if ((oldBitmap & bit) == 0L) {
/* 241 */         return this;
/*     */       }
/*     */       
/* 244 */       LongLongHashMap newMap = new LongLongHashMap(this.map);
/* 245 */       long newBitmap = oldBitmap & (bit ^ 0xFFFFFFFFFFFFFFFFL);
/*     */       
/* 247 */       if (newBitmap != 0L) {
/* 248 */         newMap.put(key, newBitmap);
/*     */       } else {
/* 250 */         newMap.remove(key);
/*     */       } 
/*     */       
/* 253 */       return new FallbackThreadSet(newMap);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\FastThreadLocalThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */