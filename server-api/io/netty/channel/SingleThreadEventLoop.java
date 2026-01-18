/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.EventExecutorGroup;
/*     */ import io.netty.util.concurrent.RejectedExecutionHandler;
/*     */ import io.netty.util.concurrent.RejectedExecutionHandlers;
/*     */ import io.netty.util.concurrent.SingleThreadEventExecutor;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public abstract class SingleThreadEventLoop
/*     */   extends SingleThreadEventExecutor
/*     */   implements EventLoop
/*     */ {
/*  36 */   protected static final int DEFAULT_MAX_PENDING_TASKS = Math.max(16, 
/*  37 */       SystemPropertyUtil.getInt("io.netty.eventLoop.maxPendingTasks", 2147483647));
/*     */   
/*     */   private final Queue<Runnable> tailTasks;
/*     */   
/*     */   protected SingleThreadEventLoop(EventLoopGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
/*  42 */     this(parent, threadFactory, addTaskWakesUp, DEFAULT_MAX_PENDING_TASKS, RejectedExecutionHandlers.reject());
/*     */   }
/*     */ 
/*     */   
/*     */   protected SingleThreadEventLoop(EventLoopGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp, boolean supportSuspension) {
/*  47 */     this(parent, threadFactory, addTaskWakesUp, supportSuspension, DEFAULT_MAX_PENDING_TASKS, 
/*  48 */         RejectedExecutionHandlers.reject());
/*     */   }
/*     */   
/*     */   protected SingleThreadEventLoop(EventLoopGroup parent, Executor executor, boolean addTaskWakesUp) {
/*  52 */     this(parent, executor, addTaskWakesUp, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SingleThreadEventLoop(EventLoopGroup parent, Executor executor, boolean addTaskWakesUp, boolean supportSuspension) {
/*  57 */     this(parent, executor, addTaskWakesUp, supportSuspension, DEFAULT_MAX_PENDING_TASKS, 
/*  58 */         RejectedExecutionHandlers.reject());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SingleThreadEventLoop(EventLoopGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
/*  64 */     this(parent, threadFactory, addTaskWakesUp, false, maxPendingTasks, rejectedExecutionHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SingleThreadEventLoop(EventLoopGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp, boolean supportSuspension, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
/*  70 */     super(parent, threadFactory, addTaskWakesUp, supportSuspension, maxPendingTasks, rejectedExecutionHandler);
/*  71 */     this.tailTasks = newTaskQueue(maxPendingTasks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SingleThreadEventLoop(EventLoopGroup parent, Executor executor, boolean addTaskWakesUp, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
/*  77 */     this(parent, executor, addTaskWakesUp, false, maxPendingTasks, rejectedExecutionHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SingleThreadEventLoop(EventLoopGroup parent, Executor executor, boolean addTaskWakesUp, boolean supportSuspension, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
/*  83 */     super(parent, executor, addTaskWakesUp, supportSuspension, maxPendingTasks, rejectedExecutionHandler);
/*  84 */     this.tailTasks = newTaskQueue(maxPendingTasks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SingleThreadEventLoop(EventLoopGroup parent, Executor executor, boolean addTaskWakesUp, Queue<Runnable> taskQueue, Queue<Runnable> tailTaskQueue, RejectedExecutionHandler rejectedExecutionHandler) {
/*  90 */     this(parent, executor, addTaskWakesUp, false, taskQueue, tailTaskQueue, rejectedExecutionHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SingleThreadEventLoop(EventLoopGroup parent, Executor executor, boolean addTaskWakesUp, boolean supportSuspension, Queue<Runnable> taskQueue, Queue<Runnable> tailTaskQueue, RejectedExecutionHandler rejectedExecutionHandler) {
/*  97 */     super(parent, executor, addTaskWakesUp, supportSuspension, taskQueue, rejectedExecutionHandler);
/*  98 */     this.tailTasks = (Queue<Runnable>)ObjectUtil.checkNotNull(tailTaskQueue, "tailTaskQueue");
/*     */   }
/*     */ 
/*     */   
/*     */   public EventLoopGroup parent() {
/* 103 */     return (EventLoopGroup)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public EventLoop next() {
/* 108 */     return (EventLoop)super.next();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture register(Channel channel) {
/* 113 */     return register(new DefaultChannelPromise(channel, (EventExecutor)this));
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture register(ChannelPromise promise) {
/* 118 */     ObjectUtil.checkNotNull(promise, "promise");
/* 119 */     promise.channel().unsafe().register(this, promise);
/* 120 */     return promise;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ChannelFuture register(Channel channel, ChannelPromise promise) {
/* 126 */     ObjectUtil.checkNotNull(promise, "promise");
/* 127 */     ObjectUtil.checkNotNull(channel, "channel");
/* 128 */     channel.unsafe().register(this, promise);
/* 129 */     return promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void executeAfterEventLoopIteration(Runnable task) {
/* 138 */     ObjectUtil.checkNotNull(task, "task");
/* 139 */     if (isShutdown()) {
/* 140 */       reject();
/*     */     }
/*     */     
/* 143 */     if (!this.tailTasks.offer(task)) {
/* 144 */       reject(task);
/*     */     }
/*     */     
/* 147 */     if (wakesUpForTask(task)) {
/* 148 */       wakeup(inEventLoop());
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
/*     */   final boolean removeAfterEventLoopIterationTask(Runnable task) {
/* 160 */     return this.tailTasks.remove(ObjectUtil.checkNotNull(task, "task"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void afterRunningAllTasks() {
/* 165 */     runAllTasksFrom(this.tailTasks);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasTasks() {
/* 170 */     return (super.hasTasks() || !this.tailTasks.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public int pendingTasks() {
/* 175 */     return super.pendingTasks() + this.tailTasks.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int registeredChannels() {
/* 185 */     return -1;
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
/*     */   public Iterator<Channel> registeredChannelsIterator() {
/* 197 */     throw new UnsupportedOperationException("registeredChannelsIterator");
/*     */   }
/*     */   
/*     */   protected static final class ChannelsReadOnlyIterator<T extends Channel> implements Iterator<Channel> {
/*     */     private final Iterator<T> channelIterator;
/*     */     
/*     */     public ChannelsReadOnlyIterator(Iterable<T> channelIterable) {
/* 204 */       this
/* 205 */         .channelIterator = ((Iterable<T>)ObjectUtil.checkNotNull(channelIterable, "channelIterable")).iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 210 */       return this.channelIterator.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public Channel next() {
/* 215 */       return (Channel)this.channelIterator.next();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 220 */       throw new UnsupportedOperationException("remove");
/*     */     }
/*     */ 
/*     */     
/*     */     public static <T> Iterator<T> empty() {
/* 225 */       return (Iterator)EMPTY;
/*     */     }
/*     */     
/* 228 */     private static final Iterator<Object> EMPTY = new Iterator()
/*     */       {
/*     */         public boolean hasNext() {
/* 231 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public Object next() {
/* 236 */           throw new NoSuchElementException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 241 */           throw new UnsupportedOperationException("remove");
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\SingleThreadEventLoop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */