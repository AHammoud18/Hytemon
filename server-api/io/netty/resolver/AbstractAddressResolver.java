/*     */ package io.netty.resolver;
/*     */ 
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.TypeParameterMatcher;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.UnsupportedAddressTypeException;
/*     */ import java.util.Collections;
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
/*     */ public abstract class AbstractAddressResolver<T extends SocketAddress>
/*     */   implements AddressResolver<T>
/*     */ {
/*     */   private final EventExecutor executor;
/*     */   private final TypeParameterMatcher matcher;
/*     */   
/*     */   protected AbstractAddressResolver(EventExecutor executor) {
/*  44 */     this.executor = (EventExecutor)ObjectUtil.checkNotNull(executor, "executor");
/*  45 */     this.matcher = TypeParameterMatcher.find(this, AbstractAddressResolver.class, "T");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractAddressResolver(EventExecutor executor, Class<? extends T> addressType) {
/*  54 */     this.executor = (EventExecutor)ObjectUtil.checkNotNull(executor, "executor");
/*  55 */     this.matcher = TypeParameterMatcher.get(addressType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EventExecutor executor() {
/*  63 */     return this.executor;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupported(SocketAddress address) {
/*  68 */     return this.matcher.match(address);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isResolved(SocketAddress address) {
/*  73 */     if (!isSupported(address)) {
/*  74 */       throw new UnsupportedAddressTypeException();
/*     */     }
/*     */ 
/*     */     
/*  78 */     SocketAddress socketAddress = address;
/*  79 */     return doIsResolved((T)socketAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean doIsResolved(T paramT);
/*     */ 
/*     */ 
/*     */   
/*     */   public final Future<T> resolve(SocketAddress address) {
/*  90 */     if (!isSupported((SocketAddress)ObjectUtil.checkNotNull(address, "address")))
/*     */     {
/*  92 */       return executor().newFailedFuture(new UnsupportedAddressTypeException());
/*     */     }
/*     */     
/*  95 */     if (isResolved(address)) {
/*     */ 
/*     */       
/*  98 */       SocketAddress socketAddress = address;
/*  99 */       return this.executor.newSucceededFuture(socketAddress);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 104 */       SocketAddress socketAddress = address;
/* 105 */       Promise<T> promise = executor().newPromise();
/* 106 */       doResolve((T)socketAddress, promise);
/* 107 */       return (Future<T>)promise;
/* 108 */     } catch (Exception e) {
/* 109 */       return executor().newFailedFuture(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final Future<T> resolve(SocketAddress address, Promise<T> promise) {
/* 115 */     ObjectUtil.checkNotNull(address, "address");
/* 116 */     ObjectUtil.checkNotNull(promise, "promise");
/*     */     
/* 118 */     if (!isSupported(address))
/*     */     {
/* 120 */       return (Future<T>)promise.setFailure(new UnsupportedAddressTypeException());
/*     */     }
/*     */     
/* 123 */     if (isResolved(address)) {
/*     */ 
/*     */       
/* 126 */       SocketAddress socketAddress = address;
/* 127 */       return (Future<T>)promise.setSuccess(socketAddress);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 132 */       SocketAddress socketAddress = address;
/* 133 */       doResolve((T)socketAddress, promise);
/* 134 */       return (Future<T>)promise;
/* 135 */     } catch (Exception e) {
/* 136 */       return (Future<T>)promise.setFailure(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final Future<List<T>> resolveAll(SocketAddress address) {
/* 142 */     if (!isSupported((SocketAddress)ObjectUtil.checkNotNull(address, "address")))
/*     */     {
/* 144 */       return executor().newFailedFuture(new UnsupportedAddressTypeException());
/*     */     }
/*     */     
/* 147 */     if (isResolved(address)) {
/*     */ 
/*     */       
/* 150 */       SocketAddress socketAddress = address;
/* 151 */       return this.executor.newSucceededFuture(Collections.singletonList(socketAddress));
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 156 */       SocketAddress socketAddress = address;
/* 157 */       Promise<List<T>> promise = executor().newPromise();
/* 158 */       doResolveAll((T)socketAddress, promise);
/* 159 */       return (Future<List<T>>)promise;
/* 160 */     } catch (Exception e) {
/* 161 */       return executor().newFailedFuture(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final Future<List<T>> resolveAll(SocketAddress address, Promise<List<T>> promise) {
/* 167 */     ObjectUtil.checkNotNull(address, "address");
/* 168 */     ObjectUtil.checkNotNull(promise, "promise");
/*     */     
/* 170 */     if (!isSupported(address))
/*     */     {
/* 172 */       return (Future<List<T>>)promise.setFailure(new UnsupportedAddressTypeException());
/*     */     }
/*     */     
/* 175 */     if (isResolved(address)) {
/*     */ 
/*     */       
/* 178 */       SocketAddress socketAddress = address;
/* 179 */       return (Future<List<T>>)promise.setSuccess(Collections.singletonList(socketAddress));
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 184 */       SocketAddress socketAddress = address;
/* 185 */       doResolveAll((T)socketAddress, promise);
/* 186 */       return (Future<List<T>>)promise;
/* 187 */     } catch (Exception e) {
/* 188 */       return (Future<List<T>>)promise.setFailure(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void doResolve(T paramT, Promise<T> paramPromise) throws Exception;
/*     */   
/*     */   protected abstract void doResolveAll(T paramT, Promise<List<T>> paramPromise) throws Exception;
/*     */   
/*     */   public void close() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\AbstractAddressResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */