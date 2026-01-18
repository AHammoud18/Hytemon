/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class PromiseAggregator<V, F extends Future<V>>
/*     */   implements GenericFutureListener<F>
/*     */ {
/*     */   private final Promise<?> aggregatePromise;
/*     */   private final boolean failPending;
/*     */   private Set<Promise<V>> pendingPromises;
/*     */   
/*     */   public PromiseAggregator(Promise<Void> aggregatePromise, boolean failPending) {
/*  48 */     this.aggregatePromise = (Promise)ObjectUtil.checkNotNull(aggregatePromise, "aggregatePromise");
/*  49 */     this.failPending = failPending;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PromiseAggregator(Promise<Void> aggregatePromise) {
/*  57 */     this(aggregatePromise, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SafeVarargs
/*     */   public final PromiseAggregator<V, F> add(Promise<V>... promises) {
/*  65 */     ObjectUtil.checkNotNull(promises, "promises");
/*  66 */     if (promises.length == 0) {
/*  67 */       return this;
/*     */     }
/*  69 */     synchronized (this) {
/*  70 */       if (this.pendingPromises == null) {
/*     */         int size;
/*  72 */         if (promises.length > 1) {
/*  73 */           size = promises.length;
/*     */         } else {
/*  75 */           size = 2;
/*     */         } 
/*  77 */         this.pendingPromises = new LinkedHashSet<>(size);
/*     */       } 
/*  79 */       for (Promise<V> p : promises) {
/*  80 */         if (p != null) {
/*     */ 
/*     */           
/*  83 */           this.pendingPromises.add(p);
/*  84 */           p.addListener(this);
/*     */         } 
/*     */       } 
/*  87 */     }  return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void operationComplete(F future) throws Exception {
/*  92 */     if (this.pendingPromises == null) {
/*  93 */       this.aggregatePromise.setSuccess(null);
/*     */     } else {
/*  95 */       this.pendingPromises.remove(future);
/*  96 */       if (!future.isSuccess()) {
/*  97 */         Throwable cause = future.cause();
/*  98 */         this.aggregatePromise.setFailure(cause);
/*  99 */         if (this.failPending) {
/* 100 */           for (Promise<V> pendingFuture : this.pendingPromises) {
/* 101 */             pendingFuture.setFailure(cause);
/*     */           }
/*     */         }
/*     */       }
/* 105 */       else if (this.pendingPromises.isEmpty()) {
/* 106 */         this.aggregatePromise.setSuccess(null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\PromiseAggregator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */