/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CompleteFuture<V>
/*     */   extends AbstractFuture<V>
/*     */ {
/*     */   private final EventExecutor executor;
/*     */   
/*     */   protected CompleteFuture(EventExecutor executor) {
/*  36 */     this.executor = executor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EventExecutor executor() {
/*  43 */     return this.executor;
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<V> addListener(GenericFutureListener<? extends Future<? super V>> listener) {
/*  48 */     DefaultPromise.notifyListener(executor(), this, (GenericFutureListener)ObjectUtil.checkNotNull(listener, "listener"));
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
/*  55 */     for (GenericFutureListener<? extends Future<? super V>> l : (GenericFutureListener[])ObjectUtil.checkNotNull(listeners, "listeners")) {
/*     */       
/*  57 */       if (l == null) {
/*     */         break;
/*     */       }
/*  60 */       DefaultPromise.notifyListener(executor(), this, l);
/*     */     } 
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener) {
/*  68 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<V> await() throws InterruptedException {
/*  79 */     if (Thread.interrupted()) {
/*  80 */       throw new InterruptedException();
/*     */     }
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
/*  87 */     if (Thread.interrupted()) {
/*  88 */       throw new InterruptedException();
/*     */     }
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<V> sync() throws InterruptedException {
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<V> syncUninterruptibly() {
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean await(long timeoutMillis) throws InterruptedException {
/* 105 */     if (Thread.interrupted()) {
/* 106 */       throw new InterruptedException();
/*     */     }
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<V> awaitUninterruptibly() {
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitUninterruptibly(long timeoutMillis) {
/* 123 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancellable() {
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 148 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\CompleteFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */