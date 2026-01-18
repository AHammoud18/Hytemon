/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.DefaultSelectStrategyFactory;
/*     */ import io.netty.channel.EventLoopTaskQueueFactory;
/*     */ import io.netty.channel.IoEventLoop;
/*     */ import io.netty.channel.IoEventLoopGroup;
/*     */ import io.netty.channel.IoHandlerFactory;
/*     */ import io.netty.channel.MultiThreadIoEventLoopGroup;
/*     */ import io.netty.channel.SelectStrategyFactory;
/*     */ import io.netty.channel.SingleThreadIoEventLoop;
/*     */ import io.netty.util.concurrent.EventExecutorChooserFactory;
/*     */ import io.netty.util.concurrent.RejectedExecutionHandler;
/*     */ import io.netty.util.concurrent.RejectedExecutionHandlers;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Iterator;
/*     */ import java.util.Queue;
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
/*     */ public final class KQueueEventLoopGroup
/*     */   extends MultiThreadIoEventLoopGroup
/*     */ {
/*  54 */   private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(KQueueEventLoopGroup.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KQueueEventLoopGroup() {
/*  60 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KQueueEventLoopGroup(int nThreads) {
/*  67 */     this(nThreads, (ThreadFactory)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KQueueEventLoopGroup(ThreadFactory threadFactory) {
/*  75 */     this(0, threadFactory, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KQueueEventLoopGroup(int nThreads, SelectStrategyFactory selectStrategyFactory) {
/*  83 */     this(nThreads, (ThreadFactory)null, selectStrategyFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KQueueEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
/*  91 */     this(nThreads, threadFactory, 0);
/*     */   }
/*     */   
/*     */   public KQueueEventLoopGroup(int nThreads, Executor executor) {
/*  95 */     this(nThreads, executor, DefaultSelectStrategyFactory.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KQueueEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectStrategyFactory selectStrategyFactory) {
/* 104 */     this(nThreads, threadFactory, 0, selectStrategyFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KQueueEventLoopGroup(int nThreads, ThreadFactory threadFactory, int maxEventsAtOnce) {
/* 115 */     this(nThreads, threadFactory, maxEventsAtOnce, DefaultSelectStrategyFactory.INSTANCE);
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
/*     */   public KQueueEventLoopGroup(int nThreads, ThreadFactory threadFactory, int maxEventsAtOnce, SelectStrategyFactory selectStrategyFactory) {
/* 128 */     super(nThreads, threadFactory, KQueueIoHandler.newFactory(maxEventsAtOnce, selectStrategyFactory), new Object[] {
/* 129 */           RejectedExecutionHandlers.reject() });
/*     */     KQueue.ensureAvailability();
/*     */   }
/*     */   public KQueueEventLoopGroup(int nThreads, Executor executor, SelectStrategyFactory selectStrategyFactory) {
/* 133 */     super(nThreads, executor, KQueueIoHandler.newFactory(0, selectStrategyFactory), new Object[] {
/* 134 */           RejectedExecutionHandlers.reject() });
/*     */     KQueue.ensureAvailability();
/*     */   }
/*     */   
/*     */   public KQueueEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory) {
/* 139 */     super(nThreads, executor, KQueueIoHandler.newFactory(0, selectStrategyFactory), chooserFactory, new Object[] {
/* 140 */           RejectedExecutionHandlers.reject()
/*     */         });
/*     */     KQueue.ensureAvailability();
/*     */   }
/*     */   
/*     */   public KQueueEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler) {
/* 146 */     super(nThreads, executor, KQueueIoHandler.newFactory(0, selectStrategyFactory), chooserFactory, new Object[] { rejectedExecutionHandler });
/*     */     KQueue.ensureAvailability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KQueueEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler, EventLoopTaskQueueFactory queueFactory) {
/* 154 */     super(nThreads, executor, KQueueIoHandler.newFactory(0, selectStrategyFactory), chooserFactory, new Object[] { rejectedExecutionHandler, queueFactory });
/*     */     KQueue.ensureAvailability();
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
/*     */   public KQueueEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler, EventLoopTaskQueueFactory taskQueueFactory, EventLoopTaskQueueFactory tailTaskQueueFactory) {
/* 176 */     super(nThreads, executor, KQueueIoHandler.newFactory(0, selectStrategyFactory), chooserFactory, new Object[] { rejectedExecutionHandler, taskQueueFactory, tailTaskQueueFactory });
/*     */     KQueue.ensureAvailability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setIoRatio(int ioRatio) {
/* 187 */     LOGGER.debug("EpollEventLoopGroup.setIoRatio(int) logic was removed, this is a no-op");
/*     */   }
/*     */ 
/*     */   
/*     */   protected IoEventLoop newChild(Executor executor, IoHandlerFactory ioHandlerFactory, Object... args) {
/* 192 */     RejectedExecutionHandler rejectedExecutionHandler = null;
/* 193 */     EventLoopTaskQueueFactory taskQueueFactory = null;
/* 194 */     EventLoopTaskQueueFactory tailTaskQueueFactory = null;
/* 195 */     int argsLength = args.length;
/* 196 */     if (argsLength > 0) {
/* 197 */       rejectedExecutionHandler = (RejectedExecutionHandler)args[0];
/*     */     }
/* 199 */     if (argsLength > 1) {
/* 200 */       taskQueueFactory = (EventLoopTaskQueueFactory)args[2];
/*     */     }
/* 202 */     if (argsLength > 2) {
/* 203 */       tailTaskQueueFactory = (EventLoopTaskQueueFactory)args[1];
/*     */     }
/* 205 */     return (IoEventLoop)new KQueueEventLoop((IoEventLoopGroup)this, executor, ioHandlerFactory, 
/* 206 */         KQueueEventLoop.newTaskQueue(taskQueueFactory), 
/* 207 */         KQueueEventLoop.newTaskQueue(tailTaskQueueFactory), rejectedExecutionHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class KQueueEventLoop
/*     */     extends SingleThreadIoEventLoop
/*     */   {
/*     */     KQueueEventLoop(IoEventLoopGroup parent, Executor executor, IoHandlerFactory ioHandlerFactory, Queue<Runnable> taskQueue, Queue<Runnable> tailTaskQueue, RejectedExecutionHandler rejectedExecutionHandler) {
/* 215 */       super(parent, executor, ioHandlerFactory, taskQueue, tailTaskQueue, rejectedExecutionHandler);
/*     */     }
/*     */ 
/*     */     
/*     */     static Queue<Runnable> newTaskQueue(EventLoopTaskQueueFactory queueFactory) {
/* 220 */       if (queueFactory == null) {
/* 221 */         return newTaskQueue0(DEFAULT_MAX_PENDING_TASKS);
/*     */       }
/* 223 */       return queueFactory.newTaskQueue(DEFAULT_MAX_PENDING_TASKS);
/*     */     }
/*     */ 
/*     */     
/*     */     public int registeredChannels() {
/* 228 */       assert inEventLoop();
/* 229 */       return ((KQueueIoHandler)ioHandler()).numRegisteredChannels();
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<Channel> registeredChannelsIterator() {
/* 234 */       assert inEventLoop();
/* 235 */       return ((KQueueIoHandler)ioHandler()).registeredChannelsList().iterator();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueEventLoopGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */