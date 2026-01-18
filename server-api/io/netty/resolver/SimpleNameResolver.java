/*    */ package io.netty.resolver;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
/*    */ import io.netty.util.concurrent.Future;
/*    */ import io.netty.util.concurrent.Promise;
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SimpleNameResolver<T>
/*    */   implements NameResolver<T>
/*    */ {
/*    */   private final EventExecutor executor;
/*    */   
/*    */   protected SimpleNameResolver(EventExecutor executor) {
/* 39 */     this.executor = (EventExecutor)ObjectUtil.checkNotNull(executor, "executor");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected EventExecutor executor() {
/* 47 */     return this.executor;
/*    */   }
/*    */ 
/*    */   
/*    */   public final Future<T> resolve(String inetHost) {
/* 52 */     Promise<T> promise = executor().newPromise();
/* 53 */     return resolve(inetHost, promise);
/*    */   }
/*    */ 
/*    */   
/*    */   public Future<T> resolve(String inetHost, Promise<T> promise) {
/* 58 */     ObjectUtil.checkNotNull(promise, "promise");
/*    */     
/*    */     try {
/* 61 */       doResolve(inetHost, promise);
/* 62 */       return (Future<T>)promise;
/* 63 */     } catch (Exception e) {
/* 64 */       return (Future<T>)promise.setFailure(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public final Future<List<T>> resolveAll(String inetHost) {
/* 70 */     Promise<List<T>> promise = executor().newPromise();
/* 71 */     return resolveAll(inetHost, promise);
/*    */   }
/*    */ 
/*    */   
/*    */   public Future<List<T>> resolveAll(String inetHost, Promise<List<T>> promise) {
/* 76 */     ObjectUtil.checkNotNull(promise, "promise");
/*    */     
/*    */     try {
/* 79 */       doResolveAll(inetHost, promise);
/* 80 */       return (Future<List<T>>)promise;
/* 81 */     } catch (Exception e) {
/* 82 */       return (Future<List<T>>)promise.setFailure(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void doResolve(String paramString, Promise<T> paramPromise) throws Exception;
/*    */   
/*    */   protected abstract void doResolveAll(String paramString, Promise<List<T>> paramPromise) throws Exception;
/*    */   
/*    */   public void close() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\SimpleNameResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */