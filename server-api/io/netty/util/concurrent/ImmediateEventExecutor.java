/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
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
/*     */ public final class ImmediateEventExecutor
/*     */   extends AbstractEventExecutor
/*     */ {
/*  34 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ImmediateEventExecutor.class);
/*  35 */   public static final ImmediateEventExecutor INSTANCE = new ImmediateEventExecutor();
/*     */ 
/*     */ 
/*     */   
/*  39 */   private static final FastThreadLocal<Queue<Runnable>> DELAYED_RUNNABLES = new FastThreadLocal<Queue<Runnable>>()
/*     */     {
/*     */       protected Queue<Runnable> initialValue() throws Exception {
/*  42 */         return new ArrayDeque<>();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*  48 */   private static final FastThreadLocal<Boolean> RUNNING = new FastThreadLocal<Boolean>()
/*     */     {
/*     */       protected Boolean initialValue() throws Exception {
/*  51 */         return Boolean.valueOf(false);
/*     */       }
/*     */     };
/*     */   
/*  55 */   private final Future<?> terminationFuture = new FailedFuture(GlobalEventExecutor.INSTANCE, new UnsupportedOperationException());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean inEventLoop() {
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop(Thread thread) {
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/*  72 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/*  77 */     return this.terminationFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {}
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) {
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(Runnable command) {
/* 106 */     ObjectUtil.checkNotNull(command, "command");
/* 107 */     if (!((Boolean)RUNNING.get()).booleanValue()) {
/* 108 */       RUNNING.set(Boolean.valueOf(true));
/*     */       try {
/* 110 */         command.run();
/* 111 */       } catch (Throwable cause) {
/* 112 */         logger.info("Throwable caught while executing Runnable {}", command, cause);
/*     */       } finally {
/* 114 */         Queue<Runnable> delayedRunnables = DELAYED_RUNNABLES.get();
/*     */         Runnable runnable;
/* 116 */         while ((runnable = delayedRunnables.poll()) != null) {
/*     */           try {
/* 118 */             runnable.run();
/* 119 */           } catch (Throwable cause) {
/* 120 */             logger.info("Throwable caught while executing Runnable {}", runnable, cause);
/*     */           } 
/*     */         } 
/* 123 */         RUNNING.set(Boolean.valueOf(false));
/*     */       } 
/*     */     } else {
/* 126 */       ((Queue<Runnable>)DELAYED_RUNNABLES.get()).add(command);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> Promise<V> newPromise() {
/* 132 */     return new ImmediatePromise<>(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> ProgressivePromise<V> newProgressivePromise() {
/* 137 */     return new ImmediateProgressivePromise<>(this);
/*     */   }
/*     */   
/*     */   static class ImmediatePromise<V> extends DefaultPromise<V> {
/*     */     ImmediatePromise(EventExecutor executor) {
/* 142 */       super(executor);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void checkDeadLock() {}
/*     */   }
/*     */   
/*     */   static class ImmediateProgressivePromise<V>
/*     */     extends DefaultProgressivePromise<V>
/*     */   {
/*     */     ImmediateProgressivePromise(EventExecutor executor) {
/* 153 */       super(executor);
/*     */     }
/*     */     
/*     */     protected void checkDeadLock() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\ImmediateEventExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */