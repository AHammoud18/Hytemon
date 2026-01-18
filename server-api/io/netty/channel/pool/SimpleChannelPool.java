/*     */ package io.netty.channel.pool;
/*     */ 
/*     */ import io.netty.bootstrap.Bootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.FutureListener;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.GlobalEventExecutor;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.util.Deque;
/*     */ import java.util.concurrent.Callable;
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
/*     */ public class SimpleChannelPool
/*     */   implements ChannelPool
/*     */ {
/*  45 */   private static final AttributeKey<SimpleChannelPool> POOL_KEY = AttributeKey.newInstance("io.netty.channel.pool.SimpleChannelPool");
/*  46 */   private final Deque<Channel> deque = PlatformDependent.newConcurrentDeque();
/*     */   
/*     */   private final ChannelPoolHandler handler;
/*     */   
/*     */   private final ChannelHealthChecker healthCheck;
/*     */   
/*     */   private final Bootstrap bootstrap;
/*     */   
/*     */   private final boolean releaseHealthCheck;
/*     */   
/*     */   private final boolean lastRecentUsed;
/*     */ 
/*     */   
/*     */   public SimpleChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler) {
/*  60 */     this(bootstrap, handler, ChannelHealthChecker.ACTIVE);
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
/*     */   public SimpleChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck) {
/*  72 */     this(bootstrap, handler, healthCheck, true);
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
/*     */   public SimpleChannelPool(Bootstrap bootstrap, ChannelPoolHandler handler, ChannelHealthChecker healthCheck, boolean releaseHealthCheck) {
/*  87 */     this(bootstrap, handler, healthCheck, releaseHealthCheck, true);
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
/*     */   public SimpleChannelPool(Bootstrap bootstrap, final ChannelPoolHandler handler, ChannelHealthChecker healthCheck, boolean releaseHealthCheck, boolean lastRecentUsed) {
/* 103 */     this.handler = (ChannelPoolHandler)ObjectUtil.checkNotNull(handler, "handler");
/* 104 */     this.healthCheck = (ChannelHealthChecker)ObjectUtil.checkNotNull(healthCheck, "healthCheck");
/* 105 */     this.releaseHealthCheck = releaseHealthCheck;
/*     */     
/* 107 */     this.bootstrap = ((Bootstrap)ObjectUtil.checkNotNull(bootstrap, "bootstrap")).clone();
/* 108 */     this.bootstrap.handler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */         {
/*     */           protected void initChannel(Channel ch) throws Exception {
/* 111 */             assert ch.eventLoop().inEventLoop();
/* 112 */             handler.channelCreated(ch);
/*     */           }
/*     */         });
/* 115 */     this.lastRecentUsed = lastRecentUsed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Bootstrap bootstrap() {
/* 124 */     return this.bootstrap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ChannelPoolHandler handler() {
/* 133 */     return this.handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ChannelHealthChecker healthChecker() {
/* 142 */     return this.healthCheck;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean releaseHealthCheck() {
/* 152 */     return this.releaseHealthCheck;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Future<Channel> acquire() {
/* 157 */     return acquire(this.bootstrap.config().group().next().newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<Channel> acquire(Promise<Channel> promise) {
/* 162 */     return acquireHealthyFromPoolOrNew((Promise<Channel>)ObjectUtil.checkNotNull(promise, "promise"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Future<Channel> acquireHealthyFromPoolOrNew(final Promise<Channel> promise) {
/*     */     try {
/* 172 */       final Channel ch = pollChannel();
/* 173 */       if (ch == null) {
/*     */         
/* 175 */         Bootstrap bs = this.bootstrap.clone();
/* 176 */         bs.attr(POOL_KEY, this);
/* 177 */         ChannelFuture f = connectChannel(bs);
/* 178 */         if (f.isDone()) {
/* 179 */           notifyConnect(f, promise);
/*     */         } else {
/* 181 */           f.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */               {
/*     */                 public void operationComplete(ChannelFuture future) throws Exception {
/* 184 */                   SimpleChannelPool.this.notifyConnect(future, promise);
/*     */                 }
/*     */               });
/*     */         } 
/*     */       } else {
/* 189 */         EventLoop loop = ch.eventLoop();
/* 190 */         if (loop.inEventLoop()) {
/* 191 */           doHealthCheck(ch, promise);
/*     */         } else {
/* 193 */           loop.execute(new Runnable()
/*     */               {
/*     */                 public void run() {
/* 196 */                   SimpleChannelPool.this.doHealthCheck(ch, promise);
/*     */                 }
/*     */               });
/*     */         } 
/*     */       } 
/* 201 */     } catch (Throwable cause) {
/* 202 */       promise.tryFailure(cause);
/*     */     } 
/* 204 */     return (Future<Channel>)promise;
/*     */   }
/*     */   
/*     */   private void notifyConnect(ChannelFuture future, Promise<Channel> promise) {
/* 208 */     Channel channel = null;
/*     */     try {
/* 210 */       if (future.isSuccess()) {
/* 211 */         channel = future.channel();
/* 212 */         this.handler.channelAcquired(channel);
/* 213 */         if (!promise.trySuccess(channel))
/*     */         {
/* 215 */           release(channel);
/*     */         }
/*     */       } else {
/* 218 */         promise.tryFailure(future.cause());
/*     */       } 
/* 220 */     } catch (Throwable cause) {
/* 221 */       closeAndFail(channel, cause, promise);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void doHealthCheck(final Channel channel, final Promise<Channel> promise) {
/*     */     try {
/* 227 */       assert channel.eventLoop().inEventLoop();
/* 228 */       Future<Boolean> f = this.healthCheck.isHealthy(channel);
/* 229 */       if (f.isDone()) {
/* 230 */         notifyHealthCheck(f, channel, promise);
/*     */       } else {
/* 232 */         f.addListener((GenericFutureListener)new FutureListener<Boolean>()
/*     */             {
/*     */               public void operationComplete(Future<Boolean> future) {
/* 235 */                 SimpleChannelPool.this.notifyHealthCheck(future, channel, promise);
/*     */               }
/*     */             });
/*     */       } 
/* 239 */     } catch (Throwable cause) {
/* 240 */       closeAndFail(channel, cause, promise);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void notifyHealthCheck(Future<Boolean> future, Channel channel, Promise<Channel> promise) {
/*     */     try {
/* 246 */       assert channel.eventLoop().inEventLoop();
/* 247 */       if (future.isSuccess() && ((Boolean)future.getNow()).booleanValue()) {
/* 248 */         channel.attr(POOL_KEY).set(this);
/* 249 */         this.handler.channelAcquired(channel);
/* 250 */         promise.setSuccess(channel);
/*     */       } else {
/* 252 */         closeChannel(channel);
/* 253 */         acquireHealthyFromPoolOrNew(promise);
/*     */       } 
/* 255 */     } catch (Throwable cause) {
/* 256 */       closeAndFail(channel, cause, promise);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ChannelFuture connectChannel(Bootstrap bs) {
/* 267 */     return bs.connect();
/*     */   }
/*     */ 
/*     */   
/*     */   public final Future<Void> release(Channel channel) {
/* 272 */     return release(channel, channel.eventLoop().newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<Void> release(final Channel channel, final Promise<Void> promise) {
/*     */     try {
/* 278 */       ObjectUtil.checkNotNull(channel, "channel");
/* 279 */       ObjectUtil.checkNotNull(promise, "promise");
/* 280 */       EventLoop loop = channel.eventLoop();
/* 281 */       if (loop.inEventLoop()) {
/* 282 */         doReleaseChannel(channel, promise);
/*     */       } else {
/* 284 */         loop.execute(new Runnable()
/*     */             {
/*     */               public void run() {
/* 287 */                 SimpleChannelPool.this.doReleaseChannel(channel, promise);
/*     */               }
/*     */             });
/*     */       } 
/* 291 */     } catch (Throwable cause) {
/* 292 */       closeAndFail(channel, cause, promise);
/*     */     } 
/* 294 */     return (Future<Void>)promise;
/*     */   }
/*     */   
/*     */   private void doReleaseChannel(Channel channel, Promise<Void> promise) {
/*     */     try {
/* 299 */       assert channel.eventLoop().inEventLoop();
/*     */       
/* 301 */       if (channel.attr(POOL_KEY).getAndSet(null) != this) {
/* 302 */         closeAndFail(channel, new IllegalArgumentException("Channel " + channel + " was not acquired from this ChannelPool"), promise);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 308 */       else if (this.releaseHealthCheck) {
/* 309 */         doHealthCheckOnRelease(channel, promise);
/*     */       } else {
/* 311 */         releaseAndOffer(channel, promise);
/*     */       }
/*     */     
/* 314 */     } catch (Throwable cause) {
/* 315 */       closeAndFail(channel, cause, promise);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void doHealthCheckOnRelease(final Channel channel, final Promise<Void> promise) throws Exception {
/* 320 */     final Future<Boolean> f = this.healthCheck.isHealthy(channel);
/* 321 */     if (f.isDone()) {
/* 322 */       releaseAndOfferIfHealthy(channel, promise, f);
/*     */     } else {
/* 324 */       f.addListener((GenericFutureListener)new FutureListener<Boolean>()
/*     */           {
/*     */             public void operationComplete(Future<Boolean> future) throws Exception {
/* 327 */               SimpleChannelPool.this.releaseAndOfferIfHealthy(channel, promise, f);
/*     */             }
/*     */           });
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
/*     */   private void releaseAndOfferIfHealthy(Channel channel, Promise<Void> promise, Future<Boolean> future) {
/*     */     try {
/* 342 */       if (((Boolean)future.getNow()).booleanValue()) {
/* 343 */         releaseAndOffer(channel, promise);
/*     */       } else {
/* 345 */         this.handler.channelReleased(channel);
/* 346 */         promise.setSuccess(null);
/*     */       } 
/* 348 */     } catch (Throwable cause) {
/* 349 */       closeAndFail(channel, cause, promise);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void releaseAndOffer(Channel channel, Promise<Void> promise) throws Exception {
/* 354 */     if (offerChannel(channel)) {
/* 355 */       this.handler.channelReleased(channel);
/* 356 */       promise.setSuccess(null);
/*     */     } else {
/* 358 */       closeAndFail(channel, new ChannelPoolFullException(), promise);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void closeChannel(Channel channel) throws Exception {
/* 363 */     channel.attr(POOL_KEY).getAndSet(null);
/* 364 */     channel.close();
/*     */   }
/*     */   
/*     */   private void closeAndFail(Channel channel, Throwable cause, Promise<?> promise) {
/* 368 */     if (channel != null) {
/*     */       try {
/* 370 */         closeChannel(channel);
/* 371 */       } catch (Throwable t) {
/* 372 */         promise.tryFailure(t);
/*     */       } 
/*     */     }
/* 375 */     promise.tryFailure(cause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Channel pollChannel() {
/* 386 */     return this.lastRecentUsed ? this.deque.pollLast() : this.deque.pollFirst();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean offerChannel(Channel channel) {
/* 397 */     return this.deque.offer(channel);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     while (true) {
/* 403 */       Channel channel = pollChannel();
/* 404 */       if (channel == null) {
/*     */         break;
/*     */       }
/*     */       
/* 408 */       channel.close().awaitUninterruptibly();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<Void> closeAsync() {
/* 419 */     return GlobalEventExecutor.INSTANCE.submit(new Callable<Void>()
/*     */         {
/*     */           public Void call() throws Exception {
/* 422 */             SimpleChannelPool.this.close();
/* 423 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static final class ChannelPoolFullException
/*     */     extends IllegalStateException {
/*     */     private ChannelPoolFullException() {
/* 431 */       super("ChannelPool full");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Throwable fillInStackTrace() {
/* 437 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\pool\SimpleChannelPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */