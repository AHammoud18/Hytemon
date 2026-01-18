/*     */ package io.netty.resolver;
/*     */ 
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.FutureListener;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ public final class CompositeNameResolver<T>
/*     */   extends SimpleNameResolver<T>
/*     */ {
/*     */   private final NameResolver<T>[] resolvers;
/*     */   
/*     */   public CompositeNameResolver(EventExecutor executor, NameResolver<T>... resolvers) {
/*  44 */     super(executor);
/*  45 */     ObjectUtil.checkNotNull(resolvers, "resolvers");
/*  46 */     for (int i = 0; i < resolvers.length; i++) {
/*  47 */       ObjectUtil.checkNotNull(resolvers[i], "resolvers[" + i + ']');
/*     */     }
/*  49 */     if (resolvers.length < 2) {
/*  50 */       throw new IllegalArgumentException("resolvers: " + Arrays.asList(resolvers) + " (expected: at least 2 resolvers)");
/*     */     }
/*     */     
/*  53 */     this.resolvers = (NameResolver<T>[])resolvers.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doResolve(String inetHost, Promise<T> promise) throws Exception {
/*  58 */     doResolveRec(inetHost, promise, 0, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doResolveRec(final String inetHost, final Promise<T> promise, final int resolverIndex, Throwable lastFailure) throws Exception {
/*  65 */     if (resolverIndex >= this.resolvers.length) {
/*  66 */       promise.setFailure(lastFailure);
/*     */     } else {
/*  68 */       NameResolver<T> resolver = this.resolvers[resolverIndex];
/*  69 */       resolver.resolve(inetHost).addListener((GenericFutureListener)new FutureListener<T>()
/*     */           {
/*     */             public void operationComplete(Future<T> future) throws Exception {
/*  72 */               if (future.isSuccess()) {
/*  73 */                 promise.setSuccess(future.getNow());
/*     */               } else {
/*  75 */                 CompositeNameResolver.this.doResolveRec(inetHost, promise, resolverIndex + 1, future.cause());
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doResolveAll(String inetHost, Promise<List<T>> promise) throws Exception {
/*  84 */     doResolveAllRec(inetHost, promise, 0, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doResolveAllRec(final String inetHost, final Promise<List<T>> promise, final int resolverIndex, Throwable lastFailure) throws Exception {
/*  91 */     if (resolverIndex >= this.resolvers.length) {
/*  92 */       promise.setFailure(lastFailure);
/*     */     } else {
/*  94 */       NameResolver<T> resolver = this.resolvers[resolverIndex];
/*  95 */       resolver.resolveAll(inetHost).addListener((GenericFutureListener)new FutureListener<List<T>>()
/*     */           {
/*     */             public void operationComplete(Future<List<T>> future) throws Exception {
/*  98 */               if (future.isSuccess()) {
/*  99 */                 promise.setSuccess(future.getNow());
/*     */               } else {
/* 101 */                 CompositeNameResolver.this.doResolveAllRec(inetHost, promise, resolverIndex + 1, future.cause());
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\CompositeNameResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */