/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PromiseCombiner
/*     */ {
/*     */   private int expectedCount;
/*     */   private int doneCount;
/*     */   private Promise<Void> aggregatePromise;
/*     */   private Throwable cause;
/*     */   
/*  40 */   private final GenericFutureListener<Future<?>> listener = new GenericFutureListener<Future<?>>()
/*     */     {
/*     */       public void operationComplete(final Future<?> future) {
/*  43 */         if (PromiseCombiner.this.executor.inEventLoop()) {
/*  44 */           operationComplete0(future);
/*     */         } else {
/*  46 */           PromiseCombiner.this.executor.execute(new Runnable()
/*     */               {
/*     */                 public void run() {
/*  49 */                   PromiseCombiner.null.this.operationComplete0(future);
/*     */                 }
/*     */               });
/*     */         } 
/*     */       }
/*     */       
/*     */       private void operationComplete0(Future<?> future) {
/*  56 */         assert PromiseCombiner.this.executor.inEventLoop();
/*  57 */         ++PromiseCombiner.this.doneCount;
/*  58 */         if (!future.isSuccess() && PromiseCombiner.this.cause == null) {
/*  59 */           PromiseCombiner.this.cause = future.cause();
/*     */         }
/*  61 */         if (PromiseCombiner.this.doneCount == PromiseCombiner.this.expectedCount && PromiseCombiner.this.aggregatePromise != null) {
/*  62 */           PromiseCombiner.this.tryPromise();
/*     */         }
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   private final EventExecutor executor;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PromiseCombiner() {
/*  74 */     this(ImmediateEventExecutor.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PromiseCombiner(EventExecutor executor) {
/*  84 */     this.executor = (EventExecutor)ObjectUtil.checkNotNull(executor, "executor");
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
/*     */   @Deprecated
/*     */   public void add(Promise promise) {
/*  97 */     add(promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Future<?> future) {
/* 108 */     checkAddAllowed();
/* 109 */     checkInEventLoop();
/* 110 */     this.expectedCount++;
/* 111 */     future.addListener(this.listener);
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
/*     */   @Deprecated
/*     */   public void addAll(Promise... promises) {
/* 124 */     addAll((Future[])promises);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(Future... futures) {
/* 135 */     for (Future future : futures) {
/* 136 */       add(future);
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
/*     */   public void finish(Promise<Void> aggregatePromise) {
/* 152 */     ObjectUtil.checkNotNull(aggregatePromise, "aggregatePromise");
/* 153 */     checkInEventLoop();
/* 154 */     if (this.aggregatePromise != null) {
/* 155 */       throw new IllegalStateException("Already finished");
/*     */     }
/* 157 */     this.aggregatePromise = aggregatePromise;
/* 158 */     if (this.doneCount == this.expectedCount) {
/* 159 */       tryPromise();
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkInEventLoop() {
/* 164 */     if (!this.executor.inEventLoop()) {
/* 165 */       throw new IllegalStateException("Must be called from EventExecutor thread");
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean tryPromise() {
/* 170 */     return (this.cause == null) ? this.aggregatePromise.trySuccess(null) : this.aggregatePromise.tryFailure(this.cause);
/*     */   }
/*     */   
/*     */   private void checkAddAllowed() {
/* 174 */     if (this.aggregatePromise != null)
/* 175 */       throw new IllegalStateException("Adding promises is not allowed after finished adding"); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\PromiseCombiner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */