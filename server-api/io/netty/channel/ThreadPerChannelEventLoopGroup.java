/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.concurrent.AbstractEventExecutorGroup;
/*     */ import io.netty.util.concurrent.DefaultPromise;
/*     */ import io.netty.util.concurrent.DefaultThreadFactory;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.FutureListener;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.concurrent.GlobalEventExecutor;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.concurrent.ThreadPerTaskExecutor;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.ReadOnlyIterator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public class ThreadPerChannelEventLoopGroup
/*     */   extends AbstractEventExecutorGroup
/*     */   implements EventLoopGroup
/*     */ {
/*     */   private final Object[] childArgs;
/*     */   private final int maxChannels;
/*     */   final Executor executor;
/*     */   final Set<EventLoop> activeChildren;
/*     */   final Queue<EventLoop> idleChildren;
/*     */   private final ChannelException tooManyChannels;
/*     */   private volatile boolean shuttingDown;
/*     */   private final Promise<?> terminationFuture;
/*     */   private final FutureListener<Object> childTerminationListener;
/*     */   
/*     */   protected ThreadPerChannelEventLoopGroup() {
/*  74 */     this(0);
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
/*     */   protected ThreadPerChannelEventLoopGroup(int maxChannels) {
/*  87 */     this(maxChannels, (ThreadFactory)null, new Object[0]);
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
/*     */   protected ThreadPerChannelEventLoopGroup(int maxChannels, ThreadFactory threadFactory, Object... args) {
/* 103 */     this(maxChannels, (threadFactory == null) ? null : (Executor)new ThreadPerTaskExecutor(threadFactory), args);
/*     */   }
/*     */   
/*     */   protected ThreadPerChannelEventLoopGroup(int maxChannels, Executor executor, Object... args) {
/*     */     ThreadPerTaskExecutor threadPerTaskExecutor;
/*     */     this.activeChildren = ConcurrentHashMap.newKeySet();
/*     */     this.idleChildren = new ConcurrentLinkedQueue<>();
/*     */     this.terminationFuture = (Promise<?>)new DefaultPromise((EventExecutor)GlobalEventExecutor.INSTANCE);
/*     */     this.childTerminationListener = new FutureListener<Object>()
/*     */       {
/*     */         public void operationComplete(Future<Object> future) throws Exception {
/*     */           if (ThreadPerChannelEventLoopGroup.this.isTerminated()) {
/*     */             ThreadPerChannelEventLoopGroup.this.terminationFuture.trySuccess(null);
/*     */           }
/*     */         }
/*     */       };
/* 119 */     ObjectUtil.checkPositiveOrZero(maxChannels, "maxChannels");
/* 120 */     if (executor == null) {
/* 121 */       threadPerTaskExecutor = new ThreadPerTaskExecutor((ThreadFactory)new DefaultThreadFactory(getClass()));
/*     */     }
/*     */     
/* 124 */     if (args == null) {
/* 125 */       this.childArgs = EmptyArrays.EMPTY_OBJECTS;
/*     */     } else {
/* 127 */       this.childArgs = (Object[])args.clone();
/*     */     } 
/*     */     
/* 130 */     this.maxChannels = maxChannels;
/* 131 */     this.executor = (Executor)threadPerTaskExecutor;
/*     */     
/* 133 */     this
/* 134 */       .tooManyChannels = ChannelException.newStatic("too many channels (max: " + maxChannels + ')', ThreadPerChannelEventLoopGroup.class, "nextChild()");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EventLoop newChild(Object... args) throws Exception {
/* 142 */     return new ThreadPerChannelEventLoop(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<EventExecutor> iterator() {
/* 147 */     return (Iterator<EventExecutor>)new ReadOnlyIterator(this.activeChildren.iterator());
/*     */   }
/*     */ 
/*     */   
/*     */   public EventLoop next() {
/* 152 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/* 157 */     this.shuttingDown = true;
/*     */     
/* 159 */     for (EventLoop l : this.activeChildren) {
/* 160 */       l.shutdownGracefully(quietPeriod, timeout, unit);
/*     */     }
/* 162 */     for (EventLoop l : this.idleChildren) {
/* 163 */       l.shutdownGracefully(quietPeriod, timeout, unit);
/*     */     }
/*     */ 
/*     */     
/* 167 */     if (isTerminated()) {
/* 168 */       this.terminationFuture.trySuccess(null);
/*     */     }
/*     */     
/* 171 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/* 176 */     return (Future<?>)this.terminationFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {
/* 182 */     this.shuttingDown = true;
/*     */     
/* 184 */     for (EventLoop l : this.activeChildren) {
/* 185 */       l.shutdown();
/*     */     }
/* 187 */     for (EventLoop l : this.idleChildren) {
/* 188 */       l.shutdown();
/*     */     }
/*     */ 
/*     */     
/* 192 */     if (isTerminated()) {
/* 193 */       this.terminationFuture.trySuccess(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 199 */     for (EventLoop l : this.activeChildren) {
/* 200 */       if (!l.isShuttingDown()) {
/* 201 */         return false;
/*     */       }
/*     */     } 
/* 204 */     for (EventLoop l : this.idleChildren) {
/* 205 */       if (!l.isShuttingDown()) {
/* 206 */         return false;
/*     */       }
/*     */     } 
/* 209 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 214 */     for (EventLoop l : this.activeChildren) {
/* 215 */       if (!l.isShutdown()) {
/* 216 */         return false;
/*     */       }
/*     */     } 
/* 219 */     for (EventLoop l : this.idleChildren) {
/* 220 */       if (!l.isShutdown()) {
/* 221 */         return false;
/*     */       }
/*     */     } 
/* 224 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 229 */     for (EventLoop l : this.activeChildren) {
/* 230 */       if (!l.isTerminated()) {
/* 231 */         return false;
/*     */       }
/*     */     } 
/* 234 */     for (EventLoop l : this.idleChildren) {
/* 235 */       if (!l.isTerminated()) {
/* 236 */         return false;
/*     */       }
/*     */     } 
/* 239 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/* 245 */     long deadline = System.nanoTime() + unit.toNanos(timeout);
/* 246 */     label26: for (EventLoop l : this.activeChildren) {
/*     */       while (true) {
/* 248 */         long timeLeft = deadline - System.nanoTime();
/* 249 */         if (timeLeft <= 0L) {
/* 250 */           return isTerminated();
/*     */         }
/* 252 */         if (l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS)) {
/*     */           continue label26;
/*     */         }
/*     */       } 
/*     */     } 
/* 257 */     label27: for (EventLoop l : this.idleChildren) {
/*     */       while (true) {
/* 259 */         long timeLeft = deadline - System.nanoTime();
/* 260 */         if (timeLeft <= 0L) {
/* 261 */           return isTerminated();
/*     */         }
/* 263 */         if (l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS)) {
/*     */           continue label27;
/*     */         }
/*     */       } 
/*     */     } 
/* 268 */     return isTerminated();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture register(Channel channel) {
/* 273 */     ObjectUtil.checkNotNull(channel, "channel");
/*     */     try {
/* 275 */       EventLoop l = nextChild();
/* 276 */       return l.register(new DefaultChannelPromise(channel, (EventExecutor)l));
/* 277 */     } catch (Throwable t) {
/* 278 */       return new FailedChannelFuture(channel, (EventExecutor)GlobalEventExecutor.INSTANCE, t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture register(ChannelPromise promise) {
/*     */     try {
/* 285 */       return nextChild().register(promise);
/* 286 */     } catch (Throwable t) {
/* 287 */       promise.setFailure(t);
/* 288 */       return promise;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ChannelFuture register(Channel channel, ChannelPromise promise) {
/* 295 */     ObjectUtil.checkNotNull(channel, "channel");
/*     */     try {
/* 297 */       return nextChild().register(channel, promise);
/* 298 */     } catch (Throwable t) {
/* 299 */       promise.setFailure(t);
/* 300 */       return promise;
/*     */     } 
/*     */   }
/*     */   
/*     */   private EventLoop nextChild() throws Exception {
/* 305 */     if (this.shuttingDown) {
/* 306 */       throw new RejectedExecutionException("shutting down");
/*     */     }
/*     */     
/* 309 */     EventLoop loop = this.idleChildren.poll();
/* 310 */     if (loop == null) {
/* 311 */       if (this.maxChannels > 0 && this.activeChildren.size() >= this.maxChannels) {
/* 312 */         throw this.tooManyChannels;
/*     */       }
/* 314 */       loop = newChild(this.childArgs);
/* 315 */       loop.terminationFuture().addListener((GenericFutureListener)this.childTerminationListener);
/*     */     } 
/* 317 */     this.activeChildren.add(loop);
/* 318 */     return loop;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ThreadPerChannelEventLoopGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */