/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PromiseNotificationUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PromiseNotifier<V, F extends Future<V>>
/*     */   implements GenericFutureListener<F>
/*     */ {
/*  34 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PromiseNotifier.class);
/*     */ 
/*     */   
/*     */   private final Promise<? super V>[] promises;
/*     */ 
/*     */   
/*     */   private final boolean logNotifyFailure;
/*     */ 
/*     */   
/*     */   @SafeVarargs
/*     */   public PromiseNotifier(Promise<? super V>... promises) {
/*  45 */     this(true, promises);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SafeVarargs
/*     */   public PromiseNotifier(boolean logNotifyFailure, Promise<? super V>... promises) {
/*  56 */     ObjectUtil.checkNotNull(promises, "promises");
/*  57 */     for (Promise<? super V> promise : promises) {
/*  58 */       ObjectUtil.checkNotNullWithIAE(promise, "promise");
/*     */     }
/*  60 */     this.promises = (Promise<? super V>[])promises.clone();
/*  61 */     this.logNotifyFailure = logNotifyFailure;
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
/*     */   public static <V, F extends Future<V>> F cascade(F future, Promise<? super V> promise) {
/*  76 */     return cascade(true, future, promise);
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
/*     */   public static <V, F extends Future<V>> F cascade(boolean logNotifyFailure, final F future, final Promise<? super V> promise) {
/*  94 */     promise.addListener(new FutureListener()
/*     */         {
/*     */           public void operationComplete(Future f) {
/*  97 */             if (f.isCancelled()) {
/*  98 */               future.cancel(false);
/*     */             }
/*     */           }
/*     */         });
/* 102 */     future.addListener(new PromiseNotifier<Object, Future<?>>(logNotifyFailure, new Promise[] { promise })
/*     */         {
/*     */           public void operationComplete(Future f) throws Exception {
/* 105 */             if (promise.isCancelled() && f.isCancelled()) {
/*     */               return;
/*     */             }
/*     */             
/* 109 */             super.operationComplete(future);
/*     */           }
/*     */         });
/* 112 */     return future;
/*     */   }
/*     */ 
/*     */   
/*     */   public void operationComplete(F future) throws Exception {
/* 117 */     InternalLogger internalLogger = this.logNotifyFailure ? logger : null;
/* 118 */     if (future.isSuccess()) {
/* 119 */       V result = future.get();
/* 120 */       for (Promise<? super V> p : this.promises) {
/* 121 */         PromiseNotificationUtil.trySuccess(p, result, internalLogger);
/*     */       }
/* 123 */     } else if (future.isCancelled()) {
/* 124 */       for (Promise<? super V> p : this.promises) {
/* 125 */         PromiseNotificationUtil.tryCancel(p, internalLogger);
/*     */       }
/*     */     } else {
/* 128 */       Throwable cause = future.cause();
/* 129 */       for (Promise<? super V> p : this.promises)
/* 130 */         PromiseNotificationUtil.tryFailure(p, cause, internalLogger); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\PromiseNotifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */