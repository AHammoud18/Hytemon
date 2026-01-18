/*    */ package io.netty.channel.epoll;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.EventLoopTaskQueueFactory;
/*    */ import io.netty.channel.IoEventLoopGroup;
/*    */ import io.netty.channel.IoHandlerFactory;
/*    */ import io.netty.channel.SingleThreadIoEventLoop;
/*    */ import io.netty.util.concurrent.RejectedExecutionHandler;
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ import java.util.Iterator;
/*    */ import java.util.Queue;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.concurrent.ThreadFactory;
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
/*    */ @Deprecated
/*    */ public class EpollEventLoop
/*    */   extends SingleThreadIoEventLoop
/*    */ {
/* 38 */   private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(EpollEventLoop.class);
/*    */   
/*    */   EpollEventLoop(IoEventLoopGroup parent, ThreadFactory threadFactory, IoHandlerFactory ioHandlerFactory) {
/* 41 */     super(parent, threadFactory, ioHandlerFactory);
/*    */   }
/*    */   
/*    */   EpollEventLoop(IoEventLoopGroup parent, Executor executor, IoHandlerFactory ioHandlerFactory) {
/* 45 */     super(parent, executor, ioHandlerFactory);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   EpollEventLoop(IoEventLoopGroup parent, Executor executor, IoHandlerFactory ioHandlerFactory, EventLoopTaskQueueFactory taskQueueFactory, EventLoopTaskQueueFactory tailTaskQueueFactory, RejectedExecutionHandler rejectedExecutionHandler) {
/* 52 */     super(parent, executor, ioHandlerFactory, newTaskQueue(taskQueueFactory), newTaskQueue(tailTaskQueueFactory), rejectedExecutionHandler);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static Queue<Runnable> newTaskQueue(EventLoopTaskQueueFactory queueFactory) {
/* 58 */     if (queueFactory == null) {
/* 59 */       return newTaskQueue0(DEFAULT_MAX_PENDING_TASKS);
/*    */     }
/* 61 */     return queueFactory.newTaskQueue(DEFAULT_MAX_PENDING_TASKS);
/*    */   }
/*    */ 
/*    */   
/*    */   public int registeredChannels() {
/* 66 */     return ((EpollIoHandler)ioHandler()).numRegisteredChannels();
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<Channel> registeredChannelsIterator() {
/* 71 */     return ((EpollIoHandler)ioHandler()).registeredChannelsList().iterator();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIoRatio() {
/* 78 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void setIoRatio(int ioRatio) {
/* 88 */     LOGGER.debug("EpollEventLoop.setIoRatio(int) logic was removed, this is a no-op");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollEventLoop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */