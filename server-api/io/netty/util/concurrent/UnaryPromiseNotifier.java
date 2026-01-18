/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public final class UnaryPromiseNotifier<T>
/*    */   implements FutureListener<T>
/*    */ {
/* 28 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(UnaryPromiseNotifier.class);
/*    */   private final Promise<? super T> promise;
/*    */   
/*    */   public UnaryPromiseNotifier(Promise<? super T> promise) {
/* 32 */     this.promise = (Promise<? super T>)ObjectUtil.checkNotNull(promise, "promise");
/*    */   }
/*    */ 
/*    */   
/*    */   public void operationComplete(Future<T> future) throws Exception {
/* 37 */     cascadeTo(future, this.promise);
/*    */   }
/*    */   
/*    */   public static <X> void cascadeTo(Future<X> completedFuture, Promise<? super X> promise) {
/* 41 */     if (completedFuture.isSuccess()) {
/* 42 */       if (!promise.trySuccess(completedFuture.getNow())) {
/* 43 */         logger.warn("Failed to mark a promise as success because it is done already: {}", promise);
/*    */       }
/* 45 */     } else if (completedFuture.isCancelled()) {
/* 46 */       if (!promise.cancel(false)) {
/* 47 */         logger.warn("Failed to cancel a promise because it is done already: {}", promise);
/*    */       }
/*    */     }
/* 50 */     else if (!promise.tryFailure(completedFuture.cause())) {
/* 51 */       logger.warn("Failed to mark a promise as failure because it's done already: {}", promise, completedFuture
/* 52 */           .cause());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\UnaryPromiseNotifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */