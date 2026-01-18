/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface EventExecutor
/*     */   extends EventExecutorGroup, ThreadAwareExecutor
/*     */ {
/*     */   EventExecutorGroup parent();
/*     */   
/*     */   default boolean isExecutorThread(Thread thread) {
/*  36 */     return inEventLoop(thread);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean inEventLoop() {
/*  43 */     return inEventLoop(Thread.currentThread());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean inEventLoop(Thread paramThread);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default <V> Promise<V> newPromise() {
/*  56 */     return new DefaultPromise<>(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default <V> ProgressivePromise<V> newProgressivePromise() {
/*  63 */     return new DefaultProgressivePromise<>(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default <V> Future<V> newSucceededFuture(V result) {
/*  72 */     return new SucceededFuture<>(this, result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default <V> Future<V> newFailedFuture(Throwable cause) {
/*  81 */     return new FailedFuture<>(this, cause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean isSuspended() {
/*  90 */     return false;
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
/*     */   default boolean trySuspend() {
/* 112 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\EventExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */