/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.ThrowableUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.concurrent.CompletionException;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultPromise<V>
/*     */   extends AbstractFuture<V>
/*     */   implements Promise<V>
/*     */ {
/*     */   public static final String PROPERTY_MAX_LISTENER_STACK_DEPTH = "io.netty.defaultPromise.maxListenerStackDepth";
/*  49 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultPromise.class);
/*     */   
/*  51 */   private static final InternalLogger rejectedExecutionLogger = InternalLoggerFactory.getInstance(DefaultPromise.class.getName() + ".rejectedExecution");
/*  52 */   private static final int MAX_LISTENER_STACK_DEPTH = Math.min(8, 
/*  53 */       SystemPropertyUtil.getInt("io.netty.defaultPromise.maxListenerStackDepth", 8));
/*     */ 
/*     */   
/*  56 */   private static final AtomicReferenceFieldUpdater<DefaultPromise, Object> RESULT_UPDATER = AtomicReferenceFieldUpdater.newUpdater(DefaultPromise.class, Object.class, "result");
/*  57 */   private static final Object SUCCESS = new Object();
/*  58 */   private static final Object UNCANCELLABLE = new Object();
/*  59 */   private static final CauseHolder CANCELLATION_CAUSE_HOLDER = new CauseHolder(
/*  60 */       StacklessCancellationException.newInstance(DefaultPromise.class, "cancel(...)"));
/*  61 */   private static final StackTraceElement[] CANCELLATION_STACK = CANCELLATION_CAUSE_HOLDER.cause.getStackTrace();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile Object result;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final EventExecutor executor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GenericFutureListener<? extends Future<?>> listener;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DefaultFutureListeners listeners;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private short waiters;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean notifyingListeners;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultPromise(EventExecutor executor) {
/*  98 */     this.executor = (EventExecutor)ObjectUtil.checkNotNull(executor, "executor");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DefaultPromise() {
/* 106 */     this.executor = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> setSuccess(V result) {
/* 111 */     if (setSuccess0(result)) {
/* 112 */       return this;
/*     */     }
/* 114 */     throw new IllegalStateException("complete already: " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean trySuccess(V result) {
/* 119 */     return setSuccess0(result);
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> setFailure(Throwable cause) {
/* 124 */     if (setFailure0(cause)) {
/* 125 */       return this;
/*     */     }
/* 127 */     throw new IllegalStateException("complete already: " + this, cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tryFailure(Throwable cause) {
/* 132 */     return setFailure0(cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setUncancellable() {
/* 137 */     if (RESULT_UPDATER.compareAndSet(this, null, UNCANCELLABLE)) {
/* 138 */       return true;
/*     */     }
/* 140 */     Object result = this.result;
/* 141 */     return (!isDone0(result) || !isCancelled0(result));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSuccess() {
/* 146 */     Object result = this.result;
/* 147 */     return (result != null && result != UNCANCELLABLE && !(result instanceof CauseHolder));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancellable() {
/* 152 */     return (this.result == null);
/*     */   }
/*     */   
/*     */   private static final class LeanCancellationException extends CancellationException {
/*     */     private static final long serialVersionUID = 2794674970981187807L;
/*     */     
/*     */     private LeanCancellationException() {}
/*     */     
/*     */     public Throwable fillInStackTrace() {
/* 161 */       setStackTrace(DefaultPromise.CANCELLATION_STACK);
/* 162 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 167 */       return CancellationException.class.getName();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Throwable cause() {
/* 173 */     return cause0(this.result);
/*     */   }
/*     */   
/*     */   private Throwable cause0(Object result) {
/* 177 */     if (!(result instanceof CauseHolder)) {
/* 178 */       return null;
/*     */     }
/* 180 */     if (result == CANCELLATION_CAUSE_HOLDER) {
/* 181 */       CancellationException ce = new LeanCancellationException();
/* 182 */       if (RESULT_UPDATER.compareAndSet(this, CANCELLATION_CAUSE_HOLDER, new CauseHolder(ce))) {
/* 183 */         return ce;
/*     */       }
/* 185 */       result = this.result;
/*     */     } 
/* 187 */     return ((CauseHolder)result).cause;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> listener) {
/* 192 */     ObjectUtil.checkNotNull(listener, "listener");
/*     */     
/* 194 */     synchronized (this) {
/* 195 */       addListener0(listener);
/*     */     } 
/*     */     
/* 198 */     if (isDone()) {
/* 199 */       notifyListeners();
/*     */     }
/*     */     
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
/* 207 */     ObjectUtil.checkNotNull(listeners, "listeners");
/*     */     
/* 209 */     synchronized (this) {
/* 210 */       for (GenericFutureListener<? extends Future<? super V>> listener : listeners) {
/* 211 */         if (listener == null) {
/*     */           break;
/*     */         }
/* 214 */         addListener0(listener);
/*     */       } 
/*     */     } 
/*     */     
/* 218 */     if (isDone()) {
/* 219 */       notifyListeners();
/*     */     }
/*     */     
/* 222 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener) {
/* 227 */     ObjectUtil.checkNotNull(listener, "listener");
/*     */     
/* 229 */     synchronized (this) {
/* 230 */       removeListener0(listener);
/*     */     } 
/*     */     
/* 233 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
/* 238 */     ObjectUtil.checkNotNull(listeners, "listeners");
/*     */     
/* 240 */     synchronized (this) {
/* 241 */       for (GenericFutureListener<? extends Future<? super V>> listener : listeners) {
/* 242 */         if (listener == null) {
/*     */           break;
/*     */         }
/* 245 */         removeListener0(listener);
/*     */       } 
/*     */     } 
/*     */     
/* 249 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> await() throws InterruptedException {
/* 254 */     if (isDone()) {
/* 255 */       return this;
/*     */     }
/*     */     
/* 258 */     if (Thread.interrupted()) {
/* 259 */       throw new InterruptedException(toString());
/*     */     }
/*     */     
/* 262 */     checkDeadLock();
/*     */     
/* 264 */     synchronized (this) {
/* 265 */       while (!isDone()) {
/* 266 */         incWaiters();
/*     */         try {
/* 268 */           wait();
/*     */         } finally {
/* 270 */           decWaiters();
/*     */         } 
/*     */       } 
/*     */     } 
/* 274 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> awaitUninterruptibly() {
/* 279 */     if (isDone()) {
/* 280 */       return this;
/*     */     }
/*     */     
/* 283 */     checkDeadLock();
/*     */     
/* 285 */     boolean interrupted = false;
/* 286 */     synchronized (this) {
/* 287 */       while (!isDone()) {
/* 288 */         incWaiters();
/*     */         try {
/* 290 */           wait();
/* 291 */         } catch (InterruptedException e) {
/*     */           
/* 293 */           interrupted = true;
/*     */         } finally {
/* 295 */           decWaiters();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 300 */     if (interrupted) {
/* 301 */       Thread.currentThread().interrupt();
/*     */     }
/*     */     
/* 304 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
/* 309 */     return await0(unit.toNanos(timeout), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean await(long timeoutMillis) throws InterruptedException {
/* 314 */     return await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
/*     */     try {
/* 320 */       return await0(unit.toNanos(timeout), false);
/* 321 */     } catch (InterruptedException e) {
/*     */       
/* 323 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitUninterruptibly(long timeoutMillis) {
/*     */     try {
/* 330 */       return await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), false);
/* 331 */     } catch (InterruptedException e) {
/*     */       
/* 333 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V getNow() {
/* 340 */     Object result = this.result;
/* 341 */     if (result instanceof CauseHolder || result == SUCCESS || result == UNCANCELLABLE) {
/* 342 */       return null;
/*     */     }
/* 344 */     return (V)result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V get() throws InterruptedException, ExecutionException {
/* 350 */     Object result = this.result;
/* 351 */     if (!isDone0(result)) {
/* 352 */       await();
/* 353 */       result = this.result;
/*     */     } 
/* 355 */     if (result == SUCCESS || result == UNCANCELLABLE) {
/* 356 */       return null;
/*     */     }
/* 358 */     Throwable cause = cause0(result);
/* 359 */     if (cause == null) {
/* 360 */       return (V)result;
/*     */     }
/* 362 */     if (cause instanceof CancellationException) {
/* 363 */       throw (CancellationException)cause;
/*     */     }
/* 365 */     throw new ExecutionException(cause);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 371 */     Object result = this.result;
/* 372 */     if (!isDone0(result)) {
/* 373 */       if (!await(timeout, unit)) {
/* 374 */         throw new TimeoutException("timeout after " + timeout + " " + unit.name().toLowerCase(Locale.ENGLISH));
/*     */       }
/* 376 */       result = this.result;
/*     */     } 
/* 378 */     if (result == SUCCESS || result == UNCANCELLABLE) {
/* 379 */       return null;
/*     */     }
/* 381 */     Throwable cause = cause0(result);
/* 382 */     if (cause == null) {
/* 383 */       return (V)result;
/*     */     }
/* 385 */     if (cause instanceof CancellationException) {
/* 386 */       throw (CancellationException)cause;
/*     */     }
/* 388 */     throw new ExecutionException(cause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 398 */     if (RESULT_UPDATER.compareAndSet(this, null, CANCELLATION_CAUSE_HOLDER)) {
/* 399 */       if (checkNotifyWaiters()) {
/* 400 */         notifyListeners();
/*     */       }
/* 402 */       return true;
/*     */     } 
/* 404 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/* 409 */     return isCancelled0(this.result);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/* 414 */     return isDone0(this.result);
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> sync() throws InterruptedException {
/* 419 */     await();
/* 420 */     rethrowIfFailed();
/* 421 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> syncUninterruptibly() {
/* 426 */     awaitUninterruptibly();
/* 427 */     rethrowIfFailed();
/* 428 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 433 */     return toStringBuilder().toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StringBuilder toStringBuilder() {
/* 440 */     StringBuilder buf = (new StringBuilder(64)).append(StringUtil.simpleClassName(this)).append('@').append(Integer.toHexString(hashCode()));
/*     */     
/* 442 */     Object result = this.result;
/* 443 */     if (result == SUCCESS) {
/* 444 */       buf.append("(success)");
/* 445 */     } else if (result == UNCANCELLABLE) {
/* 446 */       buf.append("(uncancellable)");
/* 447 */     } else if (result instanceof CauseHolder) {
/* 448 */       buf.append("(failure: ")
/* 449 */         .append(((CauseHolder)result).cause)
/* 450 */         .append(')');
/* 451 */     } else if (result != null) {
/* 452 */       buf.append("(success: ")
/* 453 */         .append(result)
/* 454 */         .append(')');
/*     */     } else {
/* 456 */       buf.append("(incomplete)");
/*     */     } 
/*     */     
/* 459 */     return buf;
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
/*     */   protected EventExecutor executor() {
/* 471 */     return this.executor;
/*     */   }
/*     */   
/*     */   protected void checkDeadLock() {
/* 475 */     EventExecutor e = executor();
/* 476 */     if (e != null && e.inEventLoop()) {
/* 477 */       throw new BlockingOperationException(toString());
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
/*     */   protected static void notifyListener(EventExecutor eventExecutor, Future<?> future, GenericFutureListener<?> listener) {
/* 492 */     notifyListenerWithStackOverFlowProtection(
/* 493 */         (EventExecutor)ObjectUtil.checkNotNull(eventExecutor, "eventExecutor"), 
/* 494 */         (Future)ObjectUtil.checkNotNull(future, "future"), 
/* 495 */         (GenericFutureListener)ObjectUtil.checkNotNull(listener, "listener"));
/*     */   }
/*     */   
/*     */   private void notifyListeners() {
/* 499 */     EventExecutor executor = executor();
/* 500 */     if (executor.inEventLoop()) {
/* 501 */       InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
/* 502 */       int stackDepth = threadLocals.futureListenerStackDepth();
/* 503 */       if (stackDepth < MAX_LISTENER_STACK_DEPTH) {
/* 504 */         threadLocals.setFutureListenerStackDepth(stackDepth + 1);
/*     */         try {
/* 506 */           notifyListenersNow();
/*     */         } finally {
/* 508 */           threadLocals.setFutureListenerStackDepth(stackDepth);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 514 */     safeExecute(executor, new Runnable()
/*     */         {
/*     */           public void run() {
/* 517 */             DefaultPromise.this.notifyListenersNow();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void notifyListenerWithStackOverFlowProtection(EventExecutor executor, final Future<?> future, final GenericFutureListener<?> listener) {
/* 530 */     if (executor.inEventLoop()) {
/* 531 */       InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
/* 532 */       int stackDepth = threadLocals.futureListenerStackDepth();
/* 533 */       if (stackDepth < MAX_LISTENER_STACK_DEPTH) {
/* 534 */         threadLocals.setFutureListenerStackDepth(stackDepth + 1);
/*     */         try {
/* 536 */           notifyListener0(future, listener);
/*     */         } finally {
/* 538 */           threadLocals.setFutureListenerStackDepth(stackDepth);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 544 */     safeExecute(executor, new Runnable()
/*     */         {
/*     */           public void run() {
/* 547 */             DefaultPromise.notifyListener0(future, listener);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void notifyListenersNow() {
/*     */     GenericFutureListener<? extends Future<?>> listener;
/*     */     DefaultFutureListeners listeners;
/* 555 */     synchronized (this) {
/* 556 */       listener = this.listener;
/* 557 */       listeners = this.listeners;
/*     */       
/* 559 */       if (this.notifyingListeners || (listener == null && listeners == null)) {
/*     */         return;
/*     */       }
/* 562 */       this.notifyingListeners = true;
/* 563 */       if (listener != null) {
/* 564 */         this.listener = null;
/*     */       } else {
/* 566 */         this.listeners = null;
/*     */       } 
/*     */     } 
/*     */     while (true) {
/* 570 */       if (listener != null) {
/* 571 */         notifyListener0(this, listener);
/*     */       } else {
/* 573 */         notifyListeners0(listeners);
/*     */       } 
/* 575 */       synchronized (this) {
/* 576 */         if (this.listener == null && this.listeners == null) {
/*     */ 
/*     */           
/* 579 */           this.notifyingListeners = false;
/*     */           return;
/*     */         } 
/* 582 */         GenericFutureListener<? extends Future<?>> genericFutureListener = this.listener;
/* 583 */         listeners = this.listeners;
/* 584 */         if (genericFutureListener != null) {
/* 585 */           this.listener = null;
/*     */         } else {
/* 587 */           this.listeners = null;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void notifyListeners0(DefaultFutureListeners listeners) {
/* 594 */     GenericFutureListener[] arrayOfGenericFutureListener = (GenericFutureListener[])listeners.listeners();
/* 595 */     int size = listeners.size();
/* 596 */     for (int i = 0; i < size; i++) {
/* 597 */       notifyListener0(this, arrayOfGenericFutureListener[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void notifyListener0(Future future, GenericFutureListener<Future> l) {
/*     */     try {
/* 604 */       l.operationComplete(future);
/* 605 */     } catch (Throwable t) {
/* 606 */       if (logger.isWarnEnabled()) {
/* 607 */         logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationComplete()", t);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addListener0(GenericFutureListener<? extends Future<? super V>> listener) {
/* 613 */     if (this.listener == null) {
/* 614 */       if (this.listeners == null) {
/* 615 */         this.listener = listener;
/*     */       } else {
/* 617 */         this.listeners.add(listener);
/*     */       } 
/*     */     } else {
/* 620 */       assert this.listeners == null;
/* 621 */       this.listeners = new DefaultFutureListeners(this.listener, listener);
/* 622 */       this.listener = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeListener0(GenericFutureListener<? extends Future<? super V>> toRemove) {
/* 627 */     if (this.listener == toRemove) {
/* 628 */       this.listener = null;
/* 629 */     } else if (this.listeners != null) {
/* 630 */       this.listeners.remove(toRemove);
/*     */       
/* 632 */       if (this.listeners.size() == 0) {
/* 633 */         this.listeners = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean setSuccess0(V result) {
/* 639 */     return setValue0((result == null) ? SUCCESS : result);
/*     */   }
/*     */   
/*     */   private boolean setFailure0(Throwable cause) {
/* 643 */     return setValue0(new CauseHolder((Throwable)ObjectUtil.checkNotNull(cause, "cause")));
/*     */   }
/*     */   
/*     */   private boolean setValue0(Object objResult) {
/* 647 */     if (RESULT_UPDATER.compareAndSet(this, null, objResult) || RESULT_UPDATER
/* 648 */       .compareAndSet(this, UNCANCELLABLE, objResult)) {
/* 649 */       if (checkNotifyWaiters()) {
/* 650 */         notifyListeners();
/*     */       }
/* 652 */       return true;
/*     */     } 
/* 654 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized boolean checkNotifyWaiters() {
/* 662 */     if (this.waiters > 0) {
/* 663 */       notifyAll();
/*     */     }
/* 665 */     return (this.listener != null || this.listeners != null);
/*     */   }
/*     */   
/*     */   private void incWaiters() {
/* 669 */     if (this.waiters == Short.MAX_VALUE) {
/* 670 */       throw new IllegalStateException("too many waiters: " + this);
/*     */     }
/* 672 */     this.waiters = (short)(this.waiters + 1);
/*     */   }
/*     */   
/*     */   private void decWaiters() {
/* 676 */     this.waiters = (short)(this.waiters - 1);
/*     */   }
/*     */   
/*     */   private void rethrowIfFailed() {
/* 680 */     Throwable cause = cause();
/* 681 */     if (cause == null) {
/*     */       return;
/*     */     }
/*     */     
/* 685 */     if (!(cause instanceof CancellationException) && (cause.getSuppressed()).length == 0) {
/* 686 */       cause.addSuppressed(new CompletionException("Rethrowing promise failure cause", null));
/*     */     }
/* 688 */     PlatformDependent.throwException(cause);
/*     */   }
/*     */   
/*     */   private boolean await0(long timeoutNanos, boolean interruptable) throws InterruptedException {
/* 692 */     if (isDone()) {
/* 693 */       return true;
/*     */     }
/*     */     
/* 696 */     if (timeoutNanos <= 0L) {
/* 697 */       return isDone();
/*     */     }
/*     */     
/* 700 */     if (interruptable && Thread.interrupted()) {
/* 701 */       throw new InterruptedException(toString());
/*     */     }
/*     */     
/* 704 */     checkDeadLock();
/*     */ 
/*     */ 
/*     */     
/* 708 */     long startTime = System.nanoTime();
/* 709 */     synchronized (this) {
/* 710 */       boolean interrupted = false;
/*     */       try {
/* 712 */         long waitTime = timeoutNanos;
/* 713 */         while (!isDone() && waitTime > 0L) {
/* 714 */           incWaiters();
/*     */           try {
/* 716 */             wait(waitTime / 1000000L, (int)(waitTime % 1000000L));
/* 717 */           } catch (InterruptedException e) {
/* 718 */             if (interruptable) {
/* 719 */               throw e;
/*     */             }
/* 721 */             interrupted = true;
/*     */           } finally {
/*     */             
/* 724 */             decWaiters();
/*     */           } 
/*     */           
/* 727 */           if (isDone()) {
/* 728 */             return true;
/*     */           }
/*     */ 
/*     */           
/* 732 */           waitTime = timeoutNanos - System.nanoTime() - startTime;
/*     */         } 
/* 734 */         return isDone();
/*     */       } finally {
/* 736 */         if (interrupted) {
/* 737 */           Thread.currentThread().interrupt();
/*     */         }
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
/*     */   void notifyProgressiveListeners(final long progress, final long total) {
/* 755 */     Object listeners = progressiveListeners();
/* 756 */     if (listeners == null) {
/*     */       return;
/*     */     }
/*     */     
/* 760 */     final ProgressiveFuture<V> self = (ProgressiveFuture<V>)this;
/*     */     
/* 762 */     EventExecutor executor = executor();
/* 763 */     if (executor.inEventLoop()) {
/* 764 */       if (listeners instanceof GenericProgressiveFutureListener[]) {
/* 765 */         notifyProgressiveListeners0(self, (GenericProgressiveFutureListener<?>[])listeners, progress, total);
/*     */       } else {
/*     */         
/* 768 */         notifyProgressiveListener0(self, (GenericProgressiveFutureListener)listeners, progress, total);
/*     */       }
/*     */     
/*     */     }
/* 772 */     else if (listeners instanceof GenericProgressiveFutureListener[]) {
/* 773 */       final GenericProgressiveFutureListener[] array = (GenericProgressiveFutureListener[])listeners;
/*     */       
/* 775 */       safeExecute(executor, new Runnable()
/*     */           {
/*     */             public void run() {
/* 778 */               DefaultPromise.notifyProgressiveListeners0(self, (GenericProgressiveFutureListener<?>[])array, progress, total);
/*     */             }
/*     */           });
/*     */     } else {
/* 782 */       final GenericProgressiveFutureListener<ProgressiveFuture<V>> l = (GenericProgressiveFutureListener<ProgressiveFuture<V>>)listeners;
/*     */       
/* 784 */       safeExecute(executor, new Runnable()
/*     */           {
/*     */             public void run() {
/* 787 */               DefaultPromise.notifyProgressiveListener0(self, l, progress, total);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized Object progressiveListeners() {
/* 799 */     GenericFutureListener<? extends Future<?>> listener = this.listener;
/* 800 */     DefaultFutureListeners listeners = this.listeners;
/* 801 */     if (listener == null && listeners == null)
/*     */     {
/* 803 */       return null;
/*     */     }
/*     */     
/* 806 */     if (listeners != null) {
/*     */       
/* 808 */       DefaultFutureListeners dfl = listeners;
/* 809 */       int progressiveSize = dfl.progressiveSize();
/* 810 */       switch (progressiveSize) {
/*     */         case 0:
/* 812 */           return null;
/*     */         case 1:
/* 814 */           for (GenericFutureListener<?> l : dfl.listeners()) {
/* 815 */             if (l instanceof GenericProgressiveFutureListener) {
/* 816 */               return l;
/*     */             }
/*     */           } 
/* 819 */           return null;
/*     */       } 
/*     */       
/* 822 */       GenericFutureListener[] arrayOfGenericFutureListener = (GenericFutureListener[])dfl.listeners();
/* 823 */       GenericProgressiveFutureListener[] arrayOfGenericProgressiveFutureListener = new GenericProgressiveFutureListener[progressiveSize];
/* 824 */       for (int i = 0, j = 0; j < progressiveSize; i++) {
/* 825 */         GenericFutureListener<?> l = arrayOfGenericFutureListener[i];
/* 826 */         if (l instanceof GenericProgressiveFutureListener) {
/* 827 */           arrayOfGenericProgressiveFutureListener[j++] = (GenericProgressiveFutureListener)l;
/*     */         }
/*     */       } 
/*     */       
/* 831 */       return arrayOfGenericProgressiveFutureListener;
/* 832 */     }  if (listener instanceof GenericProgressiveFutureListener) {
/* 833 */       return listener;
/*     */     }
/*     */     
/* 836 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void notifyProgressiveListeners0(ProgressiveFuture<?> future, GenericProgressiveFutureListener<?>[] listeners, long progress, long total) {
/* 842 */     for (GenericProgressiveFutureListener<?> l : listeners) {
/* 843 */       if (l == null) {
/*     */         break;
/*     */       }
/* 846 */       notifyProgressiveListener0(future, l, progress, total);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void notifyProgressiveListener0(ProgressiveFuture future, GenericProgressiveFutureListener<ProgressiveFuture> l, long progress, long total) {
/*     */     try {
/* 854 */       l.operationProgressed(future, progress, total);
/* 855 */     } catch (Throwable t) {
/* 856 */       if (logger.isWarnEnabled()) {
/* 857 */         logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationProgressed()", t);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isCancelled0(Object result) {
/* 863 */     return (result instanceof CauseHolder && ((CauseHolder)result).cause instanceof CancellationException);
/*     */   }
/*     */   
/*     */   private static boolean isDone0(Object result) {
/* 867 */     return (result != null && result != UNCANCELLABLE);
/*     */   }
/*     */   
/*     */   private static final class CauseHolder { final Throwable cause;
/*     */     
/*     */     CauseHolder(Throwable cause) {
/* 873 */       this.cause = cause;
/*     */     } }
/*     */ 
/*     */   
/*     */   private static void safeExecute(EventExecutor executor, Runnable task) {
/*     */     try {
/* 879 */       executor.execute(task);
/* 880 */     } catch (Throwable t) {
/* 881 */       rejectedExecutionLogger.error("Failed to submit a listener notification task. Event loop shut down?", t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class StacklessCancellationException
/*     */     extends CancellationException
/*     */   {
/*     */     private static final long serialVersionUID = -2974906711413716191L;
/*     */ 
/*     */ 
/*     */     
/*     */     public Throwable fillInStackTrace() {
/* 895 */       return this;
/*     */     }
/*     */     
/*     */     static StacklessCancellationException newInstance(Class<?> clazz, String method) {
/* 899 */       return (StacklessCancellationException)ThrowableUtil.unknownStackTrace(new StacklessCancellationException(), clazz, method);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\DefaultPromise.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */