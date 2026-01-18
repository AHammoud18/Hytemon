/*     */ package io.netty.channel.pool;
/*     */ 
/*     */ import io.netty.bootstrap.Bootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.FutureListener;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.GlobalEventExecutor;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FixedChannelPool
/*     */   extends SimpleChannelPool
/*     */ {
/*     */   private final EventExecutor executor;
/*     */   private final long acquireTimeoutNanos;
/*     */   private final Runnable timeoutTask;
/*     */   
/*     */   public enum AcquireTimeoutAction
/*     */   {
/*  48 */     NEW,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     FAIL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private final Queue<AcquireTask> pendingAcquireQueue = new ArrayDeque<>();
/*     */   private final int maxConnections;
/*     */   private final int maxPendingAcquires;
/*  65 */   private final AtomicInteger acquiredChannelCount = new AtomicInteger();
/*     */ 
/*     */ 
/*     */   
/*     */   private int pendingAcquireCount;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean closed;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections) {
/*  79 */     this(bootstrap, handler, maxConnections, 2147483647);
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
/*     */   public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, int maxConnections, int maxPendingAcquires) {
/*  95 */     this(bootstrap, handler, ChannelHealthChecker.ACTIVE, null, -1L, maxConnections, maxPendingAcquires);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, long acquireTimeoutMillis, int maxConnections, int maxPendingAcquires) {
/* 120 */     this(bootstrap, handler, healthCheck, action, acquireTimeoutMillis, maxConnections, maxPendingAcquires, true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, long acquireTimeoutMillis, int maxConnections, int maxPendingAcquires, boolean releaseHealthCheck) {
/* 147 */     this(bootstrap, handler, healthCheck, action, acquireTimeoutMillis, maxConnections, maxPendingAcquires, releaseHealthCheck, true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, AcquireTimeoutAction action, long acquireTimeoutMillis, int maxConnections, int maxPendingAcquires, boolean releaseHealthCheck, boolean lastRecentUsed) {
/* 177 */     super(bootstrap, handler, healthCheck, releaseHealthCheck, lastRecentUsed);
/* 178 */     ObjectUtil.checkPositive(maxConnections, "maxConnections");
/* 179 */     ObjectUtil.checkPositive(maxPendingAcquires, "maxPendingAcquires");
/* 180 */     if (action == null && acquireTimeoutMillis == -1L)
/* 181 */     { this.timeoutTask = null;
/* 182 */       this.acquireTimeoutNanos = -1L; }
/* 183 */     else { if (action == null && acquireTimeoutMillis != -1L)
/* 184 */         throw new NullPointerException("action"); 
/* 185 */       if (action != null && acquireTimeoutMillis < 0L) {
/* 186 */         throw new IllegalArgumentException("acquireTimeoutMillis: " + acquireTimeoutMillis + " (expected: >= 0)");
/*     */       }
/* 188 */       this.acquireTimeoutNanos = TimeUnit.MILLISECONDS.toNanos(acquireTimeoutMillis);
/* 189 */       switch (action) {
/*     */         case FAIL:
/* 191 */           this.timeoutTask = new TimeoutTask()
/*     */             {
/*     */               public void onTimeout(FixedChannelPool.AcquireTask task)
/*     */               {
/* 195 */                 task.promise.setFailure(new FixedChannelPool.AcquireTimeoutException());
/*     */               }
/*     */             };
/*     */           break;
/*     */         case NEW:
/* 200 */           this.timeoutTask = new TimeoutTask()
/*     */             {
/*     */               
/*     */               public void onTimeout(FixedChannelPool.AcquireTask task)
/*     */               {
/* 205 */                 task.acquired();
/*     */                 
/* 207 */                 FixedChannelPool.this.acquire(task.promise);
/*     */               }
/*     */             };
/*     */           break;
/*     */         default:
/* 212 */           throw new Error("Unexpected AcquireTimeoutAction: " + action);
/*     */       }  }
/*     */     
/* 215 */     this.executor = (EventExecutor)bootstrap.config().group().next();
/* 216 */     this.maxConnections = maxConnections;
/* 217 */     this.maxPendingAcquires = maxPendingAcquires;
/*     */   }
/*     */ 
/*     */   
/*     */   public int acquiredChannelCount() {
/* 222 */     return this.acquiredChannelCount.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<Channel> acquire(final Promise<Channel> promise) {
/*     */     try {
/* 228 */       if (this.executor.inEventLoop()) {
/* 229 */         acquire0(promise);
/*     */       } else {
/* 231 */         this.executor.execute(new Runnable()
/*     */             {
/*     */               public void run() {
/* 234 */                 FixedChannelPool.this.acquire0(promise);
/*     */               }
/*     */             });
/*     */       } 
/* 238 */     } catch (Throwable cause) {
/* 239 */       promise.tryFailure(cause);
/*     */     } 
/* 241 */     return (Future<Channel>)promise;
/*     */   }
/*     */   
/*     */   private void acquire0(Promise<Channel> promise) {
/*     */     try {
/* 246 */       assert this.executor.inEventLoop();
/*     */       
/* 248 */       if (this.closed) {
/* 249 */         promise.setFailure(new IllegalStateException("FixedChannelPool was closed"));
/*     */         return;
/*     */       } 
/* 252 */       if (this.acquiredChannelCount.get() < this.maxConnections) {
/* 253 */         assert this.acquiredChannelCount.get() >= 0;
/*     */ 
/*     */ 
/*     */         
/* 257 */         Promise<Channel> p = this.executor.newPromise();
/* 258 */         AcquireListener l = new AcquireListener(promise);
/* 259 */         l.acquired();
/* 260 */         p.addListener((GenericFutureListener)l);
/* 261 */         super.acquire(p);
/*     */       } else {
/* 263 */         if (this.pendingAcquireCount >= this.maxPendingAcquires) {
/* 264 */           tooManyOutstanding(promise);
/*     */         } else {
/* 266 */           AcquireTask task = new AcquireTask(promise);
/* 267 */           if (this.pendingAcquireQueue.offer(task)) {
/* 268 */             this.pendingAcquireCount++;
/*     */             
/* 270 */             if (this.timeoutTask != null) {
/* 271 */               task.timeoutFuture = (ScheduledFuture<?>)this.executor.schedule(this.timeoutTask, this.acquireTimeoutNanos, TimeUnit.NANOSECONDS);
/*     */             }
/*     */           } else {
/*     */             
/* 275 */             tooManyOutstanding(promise);
/*     */           } 
/*     */         } 
/*     */         
/* 279 */         assert this.pendingAcquireCount > 0;
/*     */       } 
/* 281 */     } catch (Throwable cause) {
/* 282 */       promise.tryFailure(cause);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void tooManyOutstanding(Promise<?> promise) {
/* 287 */     promise.setFailure(new IllegalStateException("Too many outstanding acquire operations"));
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<Void> release(final Channel channel, final Promise<Void> promise) {
/* 292 */     ObjectUtil.checkNotNull(promise, "promise");
/* 293 */     Promise<Void> p = this.executor.newPromise();
/* 294 */     super.release(channel, p.addListener((GenericFutureListener)new FutureListener<Void>()
/*     */           {
/*     */             public void operationComplete(Future<Void> future)
/*     */             {
/*     */               try {
/* 299 */                 assert FixedChannelPool.this.executor.inEventLoop();
/*     */                 
/* 301 */                 if (FixedChannelPool.this.closed) {
/*     */                   
/* 303 */                   channel.close();
/* 304 */                   promise.setFailure(new IllegalStateException("FixedChannelPool was closed"));
/*     */                   
/*     */                   return;
/*     */                 } 
/* 308 */                 if (future.isSuccess()) {
/* 309 */                   FixedChannelPool.this.decrementAndRunTaskQueue();
/* 310 */                   promise.setSuccess(null);
/*     */                 } else {
/* 312 */                   Throwable cause = future.cause();
/*     */                   
/* 314 */                   if (!(cause instanceof IllegalArgumentException)) {
/* 315 */                     FixedChannelPool.this.decrementAndRunTaskQueue();
/*     */                   }
/* 317 */                   promise.setFailure(future.cause());
/*     */                 } 
/* 319 */               } catch (Throwable cause) {
/* 320 */                 promise.tryFailure(cause);
/*     */               } 
/*     */             }
/*     */           }));
/* 324 */     return (Future<Void>)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   private void decrementAndRunTaskQueue() {
/* 329 */     int currentCount = this.acquiredChannelCount.decrementAndGet();
/* 330 */     assert currentCount >= 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     runTaskQueue();
/*     */   }
/*     */   
/*     */   private void runTaskQueue() {
/* 340 */     while (this.acquiredChannelCount.get() < this.maxConnections) {
/* 341 */       AcquireTask task = this.pendingAcquireQueue.poll();
/* 342 */       if (task == null) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 347 */       ScheduledFuture<?> timeoutFuture = task.timeoutFuture;
/* 348 */       if (timeoutFuture != null) {
/* 349 */         timeoutFuture.cancel(false);
/*     */       }
/*     */       
/* 352 */       this.pendingAcquireCount--;
/* 353 */       task.acquired();
/*     */       
/* 355 */       super.acquire(task.promise);
/*     */     } 
/*     */ 
/*     */     
/* 359 */     assert this.pendingAcquireCount >= 0;
/* 360 */     assert this.acquiredChannelCount.get() >= 0;
/*     */   }
/*     */   
/*     */   private final class AcquireTask
/*     */     extends AcquireListener {
/*     */     final Promise<Channel> promise;
/* 366 */     final long expireNanoTime = System.nanoTime() + FixedChannelPool.this.acquireTimeoutNanos;
/*     */     ScheduledFuture<?> timeoutFuture;
/*     */     
/*     */     AcquireTask(Promise<Channel> promise) {
/* 370 */       super(promise);
/*     */ 
/*     */       
/* 373 */       this.promise = FixedChannelPool.this.executor.newPromise().addListener((GenericFutureListener)this);
/*     */     } }
/*     */   
/*     */   private abstract class TimeoutTask implements Runnable {
/*     */     private TimeoutTask() {}
/*     */     
/*     */     public final void run() {
/* 380 */       assert FixedChannelPool.this.executor.inEventLoop();
/* 381 */       long nanoTime = System.nanoTime();
/*     */       while (true) {
/* 383 */         FixedChannelPool.AcquireTask task = FixedChannelPool.this.pendingAcquireQueue.peek();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 388 */         if (task == null || nanoTime - task.expireNanoTime < 0L) {
/*     */           break;
/*     */         }
/* 391 */         FixedChannelPool.this.pendingAcquireQueue.remove();
/*     */         
/* 393 */         --FixedChannelPool.this.pendingAcquireCount;
/* 394 */         onTimeout(task);
/*     */       } 
/*     */     }
/*     */     
/*     */     public abstract void onTimeout(FixedChannelPool.AcquireTask param1AcquireTask);
/*     */   }
/*     */   
/*     */   private class AcquireListener implements FutureListener<Channel> {
/*     */     private final Promise<Channel> originalPromise;
/*     */     protected boolean acquired;
/*     */     
/*     */     AcquireListener(Promise<Channel> originalPromise) {
/* 406 */       this.originalPromise = originalPromise;
/*     */     }
/*     */ 
/*     */     
/*     */     public void operationComplete(Future<Channel> future) throws Exception {
/*     */       try {
/* 412 */         assert FixedChannelPool.this.executor.inEventLoop();
/*     */         
/* 414 */         if (FixedChannelPool.this.closed) {
/* 415 */           if (future.isSuccess())
/*     */           {
/* 417 */             ((Channel)future.getNow()).close();
/*     */           }
/* 419 */           this.originalPromise.setFailure(new IllegalStateException("FixedChannelPool was closed"));
/*     */           
/*     */           return;
/*     */         } 
/* 423 */         if (future.isSuccess()) {
/* 424 */           this.originalPromise.setSuccess(future.getNow());
/*     */         } else {
/* 426 */           if (this.acquired) {
/* 427 */             FixedChannelPool.this.decrementAndRunTaskQueue();
/*     */           } else {
/* 429 */             FixedChannelPool.this.runTaskQueue();
/*     */           } 
/*     */           
/* 432 */           this.originalPromise.setFailure(future.cause());
/*     */         } 
/* 434 */       } catch (Throwable cause) {
/* 435 */         this.originalPromise.tryFailure(cause);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void acquired() {
/* 440 */       if (this.acquired) {
/*     */         return;
/*     */       }
/* 443 */       FixedChannelPool.this.acquiredChannelCount.incrementAndGet();
/* 444 */       this.acquired = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     try {
/* 451 */       closeAsync().await();
/* 452 */     } catch (InterruptedException e) {
/* 453 */       Thread.currentThread().interrupt();
/* 454 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<Void> closeAsync() {
/* 465 */     if (this.executor.inEventLoop()) {
/* 466 */       return close0();
/*     */     }
/* 468 */     final Promise<Void> closeComplete = this.executor.newPromise();
/* 469 */     this.executor.execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 472 */             FixedChannelPool.this.close0().addListener((GenericFutureListener)new FutureListener<Void>()
/*     */                 {
/*     */                   public void operationComplete(Future<Void> f) throws Exception {
/* 475 */                     if (f.isSuccess()) {
/* 476 */                       closeComplete.setSuccess(null);
/*     */                     } else {
/* 478 */                       closeComplete.setFailure(f.cause());
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/* 484 */     return (Future<Void>)closeComplete;
/*     */   }
/*     */ 
/*     */   
/*     */   private Future<Void> close0() {
/* 489 */     assert this.executor.inEventLoop();
/*     */     
/* 491 */     if (!this.closed) {
/* 492 */       this.closed = true;
/*     */       while (true) {
/* 494 */         AcquireTask task = this.pendingAcquireQueue.poll();
/* 495 */         if (task == null) {
/*     */           break;
/*     */         }
/* 498 */         ScheduledFuture<?> f = task.timeoutFuture;
/* 499 */         if (f != null) {
/* 500 */           f.cancel(false);
/*     */         }
/* 502 */         task.promise.setFailure(new ClosedChannelException());
/*     */       } 
/* 504 */       this.acquiredChannelCount.set(0);
/* 505 */       this.pendingAcquireCount = 0;
/*     */ 
/*     */ 
/*     */       
/* 509 */       return GlobalEventExecutor.INSTANCE.submit(new Callable<Void>()
/*     */           {
/*     */             public Void call() throws Exception {
/* 512 */               FixedChannelPool.this.close();
/* 513 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 518 */     return GlobalEventExecutor.INSTANCE.newSucceededFuture(null);
/*     */   }
/*     */   
/*     */   private static final class AcquireTimeoutException
/*     */     extends TimeoutException {
/*     */     private AcquireTimeoutException() {
/* 524 */       super("Acquire operation took longer then configured maximum time");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Throwable fillInStackTrace() {
/* 530 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\pool\FixedChannelPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */