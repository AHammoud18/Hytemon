/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.Promise;
/*     */ import io.netty.util.concurrent.RejectedExecutionHandler;
/*     */ import io.netty.util.concurrent.ThreadAwareExecutor;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public class SingleThreadIoEventLoop
/*     */   extends SingleThreadEventLoop
/*     */   implements IoEventLoop
/*     */ {
/*  39 */   private static final long DEFAULT_MAX_TASK_PROCESSING_QUANTUM_NS = TimeUnit.MILLISECONDS.toNanos(Math.max(100, 
/*  40 */         SystemPropertyUtil.getInt("io.netty.eventLoop.maxTaskProcessingQuantumMs", 1000)));
/*     */   private final long maxTaskProcessingQuantumNs;
/*     */   
/*  43 */   private final IoHandlerContext context = new IoHandlerContext()
/*     */     {
/*     */       public boolean canBlock() {
/*  46 */         assert SingleThreadIoEventLoop.this.inEventLoop();
/*  47 */         return (!SingleThreadIoEventLoop.this.hasTasks() && !SingleThreadIoEventLoop.this.hasScheduledTasks());
/*     */       }
/*     */ 
/*     */       
/*     */       public long delayNanos(long currentTimeNanos) {
/*  52 */         assert SingleThreadIoEventLoop.this.inEventLoop();
/*  53 */         return SingleThreadIoEventLoop.this.delayNanos(currentTimeNanos);
/*     */       }
/*     */ 
/*     */       
/*     */       public long deadlineNanos() {
/*  58 */         assert SingleThreadIoEventLoop.this.inEventLoop();
/*  59 */         return SingleThreadIoEventLoop.this.deadlineNanos();
/*     */       }
/*     */ 
/*     */       
/*     */       public void reportActiveIoTime(long activeNanos) {
/*  64 */         SingleThreadIoEventLoop.this.reportActiveIoTime(activeNanos);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean shouldReportActiveIoTime() {
/*  69 */         return SingleThreadIoEventLoop.this.isSuspensionSupported();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private final IoHandler ioHandler;
/*  75 */   private final AtomicInteger numRegistrations = new AtomicInteger();
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
/*     */   public SingleThreadIoEventLoop(IoEventLoopGroup parent, ThreadFactory threadFactory, IoHandlerFactory ioHandlerFactory) {
/*  87 */     super(parent, threadFactory, false, (
/*  88 */         (IoHandlerFactory)ObjectUtil.checkNotNull(ioHandlerFactory, "ioHandlerFactory")).isChangingThreadSupported());
/*  89 */     this.maxTaskProcessingQuantumNs = DEFAULT_MAX_TASK_PROCESSING_QUANTUM_NS;
/*  90 */     this.ioHandler = ioHandlerFactory.newHandler((ThreadAwareExecutor)this);
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
/*     */   public SingleThreadIoEventLoop(IoEventLoopGroup parent, Executor executor, IoHandlerFactory ioHandlerFactory) {
/* 102 */     super(parent, executor, false, (
/* 103 */         (IoHandlerFactory)ObjectUtil.checkNotNull(ioHandlerFactory, "ioHandlerFactory")).isChangingThreadSupported());
/* 104 */     this.maxTaskProcessingQuantumNs = DEFAULT_MAX_TASK_PROCESSING_QUANTUM_NS;
/* 105 */     this.ioHandler = ioHandlerFactory.newHandler((ThreadAwareExecutor)this);
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
/*     */   public SingleThreadIoEventLoop(IoEventLoopGroup parent, ThreadFactory threadFactory, IoHandlerFactory ioHandlerFactory, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler, long maxTaskProcessingQuantumMs) {
/* 128 */     super(parent, threadFactory, false, (
/* 129 */         (IoHandlerFactory)ObjectUtil.checkNotNull(ioHandlerFactory, "ioHandlerFactory")).isChangingThreadSupported(), maxPendingTasks, rejectedExecutionHandler);
/*     */     
/* 131 */     this
/*     */ 
/*     */       
/* 134 */       .maxTaskProcessingQuantumNs = (ObjectUtil.checkPositiveOrZero(maxTaskProcessingQuantumMs, "maxTaskProcessingQuantumMs") == 0L) ? DEFAULT_MAX_TASK_PROCESSING_QUANTUM_NS : TimeUnit.MILLISECONDS.toNanos(maxTaskProcessingQuantumMs);
/* 135 */     this.ioHandler = ioHandlerFactory.newHandler((ThreadAwareExecutor)this);
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
/*     */   public SingleThreadIoEventLoop(IoEventLoopGroup parent, Executor executor, IoHandlerFactory ioHandlerFactory, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler, long maxTaskProcessingQuantumMs) {
/* 157 */     super(parent, executor, false, (
/* 158 */         (IoHandlerFactory)ObjectUtil.checkNotNull(ioHandlerFactory, "ioHandlerFactory")).isChangingThreadSupported(), maxPendingTasks, rejectedExecutionHandler);
/*     */     
/* 160 */     this
/*     */ 
/*     */       
/* 163 */       .maxTaskProcessingQuantumNs = (ObjectUtil.checkPositiveOrZero(maxTaskProcessingQuantumMs, "maxTaskProcessingQuantumMs") == 0L) ? DEFAULT_MAX_TASK_PROCESSING_QUANTUM_NS : TimeUnit.MILLISECONDS.toNanos(maxTaskProcessingQuantumMs);
/* 164 */     this.ioHandler = ioHandlerFactory.newHandler((ThreadAwareExecutor)this);
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
/*     */   protected SingleThreadIoEventLoop(IoEventLoopGroup parent, Executor executor, IoHandlerFactory ioHandlerFactory, Queue<Runnable> taskQueue, Queue<Runnable> tailTaskQueue, RejectedExecutionHandler rejectedExecutionHandler) {
/* 184 */     super(parent, executor, false, (
/* 185 */         (IoHandlerFactory)ObjectUtil.checkNotNull(ioHandlerFactory, "ioHandlerFactory")).isChangingThreadSupported(), taskQueue, tailTaskQueue, rejectedExecutionHandler);
/*     */     
/* 187 */     this.maxTaskProcessingQuantumNs = DEFAULT_MAX_TASK_PROCESSING_QUANTUM_NS;
/* 188 */     this.ioHandler = ioHandlerFactory.newHandler((ThreadAwareExecutor)this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void run() {
/* 193 */     assert inEventLoop();
/* 194 */     this.ioHandler.initialize();
/*     */     do {
/* 196 */       runIo();
/* 197 */       if (isShuttingDown()) {
/* 198 */         this.ioHandler.prepareToDestroy();
/*     */       }
/*     */       
/* 201 */       runAllTasks(this.maxTaskProcessingQuantumNs);
/*     */     
/*     */     }
/* 204 */     while (!confirmShutdown() && !canSuspend());
/*     */   }
/*     */   
/*     */   protected final IoHandler ioHandler() {
/* 208 */     return this.ioHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canSuspend(int state) {
/* 214 */     return (super.canSuspend(state) && this.numRegistrations.get() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int runIo() {
/* 224 */     assert inEventLoop();
/* 225 */     return this.ioHandler.run(this.context);
/*     */   }
/*     */ 
/*     */   
/*     */   public IoEventLoop next() {
/* 230 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Future<IoRegistration> register(IoHandle handle) {
/* 235 */     Promise<IoRegistration> promise = newPromise();
/* 236 */     if (inEventLoop()) {
/* 237 */       registerForIo0(handle, promise);
/*     */     } else {
/* 239 */       execute(() -> registerForIo0(handle, promise));
/*     */     } 
/*     */     
/* 242 */     return (Future<IoRegistration>)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getNumOfRegisteredChannels() {
/* 247 */     return this.numRegistrations.get();
/*     */   }
/*     */   private void registerForIo0(IoHandle handle, Promise<IoRegistration> promise) {
/*     */     IoRegistration registration;
/* 251 */     assert inEventLoop();
/*     */     
/*     */     try {
/* 254 */       registration = this.ioHandler.register(handle);
/* 255 */     } catch (Exception e) {
/* 256 */       promise.setFailure(e);
/*     */       return;
/*     */     } 
/* 259 */     this.numRegistrations.incrementAndGet();
/* 260 */     promise.setSuccess(new IoRegistrationWrapper(registration));
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void wakeup(boolean inEventLoop) {
/* 265 */     this.ioHandler.wakeup();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void cleanup() {
/* 270 */     assert inEventLoop();
/* 271 */     this.ioHandler.destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCompatible(Class<? extends IoHandle> handleType) {
/* 276 */     return this.ioHandler.isCompatible(handleType);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIoType(Class<? extends IoHandler> handlerType) {
/* 281 */     return this.ioHandler.getClass().equals(handlerType);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Queue<Runnable> newTaskQueue(int maxPendingTasks) {
/* 286 */     return newTaskQueue0(maxPendingTasks);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static Queue<Runnable> newTaskQueue0(int maxPendingTasks) {
/* 291 */     return (maxPendingTasks == Integer.MAX_VALUE) ? PlatformDependent.newMpscQueue() : 
/* 292 */       PlatformDependent.newMpscQueue(maxPendingTasks);
/*     */   }
/*     */   
/*     */   private final class IoRegistrationWrapper implements IoRegistration { private final IoRegistration registration;
/*     */     
/*     */     IoRegistrationWrapper(IoRegistration registration) {
/* 298 */       this.registration = registration;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T attachment() {
/* 303 */       return this.registration.attachment();
/*     */     }
/*     */ 
/*     */     
/*     */     public long submit(IoOps ops) {
/* 308 */       return this.registration.submit(ops);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isValid() {
/* 313 */       return this.registration.isValid();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean cancel() {
/* 318 */       if (this.registration.cancel()) {
/* 319 */         SingleThreadIoEventLoop.this.numRegistrations.decrementAndGet();
/* 320 */         return true;
/*     */       } 
/* 322 */       return false;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\SingleThreadIoEventLoop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */