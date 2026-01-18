/*     */ package io.netty.resolver;
/*     */ 
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.FutureListener;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.Closeable;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
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
/*     */ public abstract class AddressResolverGroup<T extends SocketAddress>
/*     */   implements Closeable
/*     */ {
/*  38 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AddressResolverGroup.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private final Map<EventExecutor, AddressResolver<T>> resolvers = new IdentityHashMap<>();
/*     */ 
/*     */   
/*  46 */   private final Map<EventExecutor, GenericFutureListener<Future<Object>>> executorTerminationListeners = new IdentityHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AddressResolver<T> getResolver(final EventExecutor executor) {
/*     */     AddressResolver<T> r;
/*  58 */     ObjectUtil.checkNotNull(executor, "executor");
/*     */     
/*  60 */     if (executor.isShuttingDown()) {
/*  61 */       throw new IllegalStateException("executor not accepting a task");
/*     */     }
/*     */ 
/*     */     
/*  65 */     synchronized (this.resolvers) {
/*  66 */       r = this.resolvers.get(executor);
/*  67 */       if (r == null) {
/*     */         final AddressResolver<T> newResolver;
/*     */         try {
/*  70 */           newResolver = newResolver(executor);
/*  71 */         } catch (Exception e) {
/*  72 */           throw new IllegalStateException("failed to create a new resolver", e);
/*     */         } 
/*     */         
/*  75 */         this.resolvers.put(executor, newResolver);
/*     */         
/*  77 */         FutureListener<Object> terminationListener = new FutureListener<Object>()
/*     */           {
/*     */             public void operationComplete(Future<Object> future) {
/*  80 */               synchronized (AddressResolverGroup.this.resolvers) {
/*  81 */                 AddressResolverGroup.this.resolvers.remove(executor);
/*  82 */                 AddressResolverGroup.this.executorTerminationListeners.remove(executor);
/*     */               } 
/*  84 */               newResolver.close();
/*     */             }
/*     */           };
/*     */         
/*  88 */         this.executorTerminationListeners.put(executor, terminationListener);
/*  89 */         executor.terminationFuture().addListener((GenericFutureListener)terminationListener);
/*     */         
/*  91 */         r = newResolver;
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     return r;
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
/*     */   public void close() {
/*     */     AddressResolver[] arrayOfAddressResolver;
/*     */     Map.Entry[] arrayOfEntry;
/* 112 */     synchronized (this.resolvers) {
/* 113 */       arrayOfAddressResolver = (AddressResolver[])this.resolvers.values().toArray((Object[])new AddressResolver[0]);
/* 114 */       this.resolvers.clear();
/* 115 */       arrayOfEntry = (Map.Entry[])this.executorTerminationListeners.entrySet().toArray((Object[])new Map.Entry[0]);
/* 116 */       this.executorTerminationListeners.clear();
/*     */     } 
/*     */     
/* 119 */     for (Map.Entry<EventExecutor, GenericFutureListener<Future<Object>>> entry : arrayOfEntry) {
/* 120 */       ((EventExecutor)entry.getKey()).terminationFuture().removeListener(entry.getValue());
/*     */     }
/*     */     
/* 123 */     for (AddressResolver<T> r : arrayOfAddressResolver) {
/*     */       try {
/* 125 */         r.close();
/* 126 */       } catch (Throwable t) {
/* 127 */         logger.warn("Failed to close a resolver:", t);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract AddressResolver<T> newResolver(EventExecutor paramEventExecutor) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\AddressResolverGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */