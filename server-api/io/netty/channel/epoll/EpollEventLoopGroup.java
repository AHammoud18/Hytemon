/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.channel.DefaultSelectStrategyFactory;
/*     */ import io.netty.channel.EventLoopTaskQueueFactory;
/*     */ import io.netty.channel.IoEventLoop;
/*     */ import io.netty.channel.IoEventLoopGroup;
/*     */ import io.netty.channel.IoHandlerFactory;
/*     */ import io.netty.channel.MultiThreadIoEventLoopGroup;
/*     */ import io.netty.channel.SelectStrategyFactory;
/*     */ import io.netty.util.concurrent.EventExecutorChooserFactory;
/*     */ import io.netty.util.concurrent.RejectedExecutionHandler;
/*     */ import io.netty.util.concurrent.RejectedExecutionHandlers;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.ThreadFactory;
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
/*     */ public final class EpollEventLoopGroup
/*     */   extends MultiThreadIoEventLoopGroup
/*     */ {
/*  50 */   private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(EpollEventLoopGroup.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollEventLoopGroup() {
/*  56 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollEventLoopGroup(int nThreads) {
/*  63 */     this(nThreads, (ThreadFactory)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollEventLoopGroup(ThreadFactory threadFactory) {
/*  71 */     this(0, threadFactory, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollEventLoopGroup(int nThreads, SelectStrategyFactory selectStrategyFactory) {
/*  79 */     this(nThreads, (ThreadFactory)null, selectStrategyFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
/*  87 */     this(nThreads, threadFactory, 0);
/*     */   }
/*     */   
/*     */   public EpollEventLoopGroup(int nThreads, Executor executor) {
/*  91 */     this(nThreads, executor, DefaultSelectStrategyFactory.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectStrategyFactory selectStrategyFactory) {
/*  99 */     this(nThreads, threadFactory, 0, selectStrategyFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory, int maxEventsAtOnce) {
/* 110 */     this(nThreads, threadFactory, maxEventsAtOnce, DefaultSelectStrategyFactory.INSTANCE);
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
/*     */   public EpollEventLoopGroup(int nThreads, ThreadFactory threadFactory, int maxEventsAtOnce, SelectStrategyFactory selectStrategyFactory) {
/* 123 */     super(nThreads, threadFactory, EpollIoHandler.newFactory(maxEventsAtOnce, selectStrategyFactory), new Object[] {
/* 124 */           RejectedExecutionHandlers.reject() });
/*     */     Epoll.ensureAvailability();
/*     */   }
/*     */   public EpollEventLoopGroup(int nThreads, Executor executor, SelectStrategyFactory selectStrategyFactory) {
/* 128 */     super(nThreads, executor, EpollIoHandler.newFactory(0, selectStrategyFactory), new Object[] {
/* 129 */           RejectedExecutionHandlers.reject() });
/*     */     Epoll.ensureAvailability();
/*     */   }
/*     */   
/*     */   public EpollEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory) {
/* 134 */     super(nThreads, executor, EpollIoHandler.newFactory(0, selectStrategyFactory), chooserFactory, new Object[] {
/* 135 */           RejectedExecutionHandlers.reject()
/*     */         });
/*     */     Epoll.ensureAvailability();
/*     */   }
/*     */   
/*     */   public EpollEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler) {
/* 141 */     super(nThreads, executor, EpollIoHandler.newFactory(0, selectStrategyFactory), chooserFactory, new Object[] { rejectedExecutionHandler });
/*     */     Epoll.ensureAvailability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler, EventLoopTaskQueueFactory queueFactory) {
/* 149 */     super(nThreads, executor, EpollIoHandler.newFactory(0, selectStrategyFactory), chooserFactory, new Object[] { rejectedExecutionHandler, queueFactory });
/*     */     Epoll.ensureAvailability();
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
/*     */   public EpollEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler, EventLoopTaskQueueFactory taskQueueFactory, EventLoopTaskQueueFactory tailTaskQueueFactory) {
/* 171 */     super(nThreads, executor, EpollIoHandler.newFactory(0, selectStrategyFactory), chooserFactory, new Object[] { rejectedExecutionHandler, taskQueueFactory, tailTaskQueueFactory });
/*     */     Epoll.ensureAvailability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setIoRatio(int ioRatio) {
/* 182 */     LOGGER.debug("EpollEventLoopGroup.setIoRatio(int) logic was removed, this is a no-op");
/*     */   }
/*     */ 
/*     */   
/*     */   protected IoEventLoop newChild(Executor executor, IoHandlerFactory ioHandlerFactory, Object... args) {
/* 187 */     RejectedExecutionHandler rejectedExecutionHandler = (RejectedExecutionHandler)args[0];
/* 188 */     EventLoopTaskQueueFactory taskQueueFactory = null;
/* 189 */     EventLoopTaskQueueFactory tailTaskQueueFactory = null;
/*     */     
/* 191 */     int argsLength = args.length;
/* 192 */     if (argsLength > 1) {
/* 193 */       taskQueueFactory = (EventLoopTaskQueueFactory)args[1];
/*     */     }
/* 195 */     if (argsLength > 2) {
/* 196 */       tailTaskQueueFactory = (EventLoopTaskQueueFactory)args[2];
/*     */     }
/* 198 */     return (IoEventLoop)new EpollEventLoop((IoEventLoopGroup)this, executor, ioHandlerFactory, taskQueueFactory, tailTaskQueueFactory, rejectedExecutionHandler);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollEventLoopGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */