/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.channel.DefaultSelectStrategyFactory;
/*     */ import io.netty.channel.EventLoopTaskQueueFactory;
/*     */ import io.netty.channel.IoEventLoop;
/*     */ import io.netty.channel.IoEventLoopGroup;
/*     */ import io.netty.channel.IoHandlerFactory;
/*     */ import io.netty.channel.MultiThreadIoEventLoopGroup;
/*     */ import io.netty.channel.SelectStrategyFactory;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.EventExecutorChooserFactory;
/*     */ import io.netty.util.concurrent.RejectedExecutionHandler;
/*     */ import io.netty.util.concurrent.RejectedExecutionHandlers;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.channels.spi.SelectorProvider;
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
/*     */ @Deprecated
/*     */ public class NioEventLoopGroup
/*     */   extends MultiThreadIoEventLoopGroup
/*     */   implements IoEventLoopGroup
/*     */ {
/*  47 */   private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(NioEventLoopGroup.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioEventLoopGroup() {
/*  54 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioEventLoopGroup(int nThreads) {
/*  62 */     this(nThreads, (Executor)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioEventLoopGroup(ThreadFactory threadFactory) {
/*  70 */     this(0, threadFactory, SelectorProvider.provider());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
/*  78 */     this(nThreads, threadFactory, SelectorProvider.provider());
/*     */   }
/*     */   
/*     */   public NioEventLoopGroup(int nThreads, Executor executor) {
/*  82 */     this(nThreads, executor, SelectorProvider.provider());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider) {
/*  91 */     this(nThreads, threadFactory, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
/*     */   }
/*     */ 
/*     */   
/*     */   public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
/*  96 */     super(nThreads, threadFactory, NioIoHandler.newFactory(selectorProvider, selectStrategyFactory), new Object[] {
/*  97 */           RejectedExecutionHandlers.reject()
/*     */         });
/*     */   }
/*     */   
/*     */   public NioEventLoopGroup(int nThreads, Executor executor, SelectorProvider selectorProvider) {
/* 102 */     this(nThreads, executor, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
/*     */   }
/*     */ 
/*     */   
/*     */   public NioEventLoopGroup(int nThreads, Executor executor, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
/* 107 */     super(nThreads, executor, NioIoHandler.newFactory(selectorProvider, selectStrategyFactory), new Object[] {
/* 108 */           RejectedExecutionHandlers.reject()
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
/* 114 */     super(nThreads, executor, NioIoHandler.newFactory(selectorProvider, selectStrategyFactory), chooserFactory, new Object[] {
/* 115 */           RejectedExecutionHandlers.reject()
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler) {
/* 122 */     super(nThreads, executor, NioIoHandler.newFactory(selectorProvider, selectStrategyFactory), chooserFactory, new Object[] { rejectedExecutionHandler });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler, EventLoopTaskQueueFactory taskQueueFactory) {
/* 131 */     super(nThreads, executor, NioIoHandler.newFactory(selectorProvider, selectStrategyFactory), chooserFactory, new Object[] { rejectedExecutionHandler, taskQueueFactory });
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
/*     */ 
/*     */ 
/*     */   
/*     */   public NioEventLoopGroup(int nThreads, Executor executor, EventExecutorChooserFactory chooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler, EventLoopTaskQueueFactory taskQueueFactory, EventLoopTaskQueueFactory tailTaskQueueFactory) {
/* 155 */     super(nThreads, executor, NioIoHandler.newFactory(selectorProvider, selectStrategyFactory), chooserFactory, new Object[] { rejectedExecutionHandler, taskQueueFactory, tailTaskQueueFactory });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setIoRatio(int ioRatio) {
/* 166 */     LOGGER.debug("NioEventLoopGroup.setIoRatio(int) logic was removed, this is a no-op");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rebuildSelectors() {
/* 174 */     for (EventExecutor e : this) {
/* 175 */       ((NioEventLoop)e).rebuildSelector();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected IoEventLoop newChild(Executor executor, IoHandlerFactory ioHandlerFactory, Object... args) {
/* 181 */     RejectedExecutionHandler rejectedExecutionHandler = (RejectedExecutionHandler)args[0];
/* 182 */     EventLoopTaskQueueFactory taskQueueFactory = null;
/* 183 */     EventLoopTaskQueueFactory tailTaskQueueFactory = null;
/*     */     
/* 185 */     int argsLength = args.length;
/* 186 */     if (argsLength > 1) {
/* 187 */       taskQueueFactory = (EventLoopTaskQueueFactory)args[1];
/*     */     }
/* 189 */     if (argsLength > 2) {
/* 190 */       tailTaskQueueFactory = (EventLoopTaskQueueFactory)args[2];
/*     */     }
/* 192 */     return (IoEventLoop)new NioEventLoop(this, executor, ioHandlerFactory, taskQueueFactory, tailTaskQueueFactory, rejectedExecutionHandler);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\nio\NioEventLoopGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */