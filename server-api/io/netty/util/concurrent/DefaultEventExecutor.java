/*    */ package io.netty.util.concurrent;
/*    */ 
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
/*    */ public final class DefaultEventExecutor
/*    */   extends SingleThreadEventExecutor
/*    */ {
/*    */   public DefaultEventExecutor() {
/* 28 */     this((EventExecutorGroup)null);
/*    */   }
/*    */   
/*    */   public DefaultEventExecutor(ThreadFactory threadFactory) {
/* 32 */     this((EventExecutorGroup)null, threadFactory);
/*    */   }
/*    */   
/*    */   public DefaultEventExecutor(Executor executor) {
/* 36 */     this((EventExecutorGroup)null, executor);
/*    */   }
/*    */   
/*    */   public DefaultEventExecutor(EventExecutorGroup parent) {
/* 40 */     this(parent, new DefaultThreadFactory(DefaultEventExecutor.class));
/*    */   }
/*    */   
/*    */   public DefaultEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory) {
/* 44 */     super(parent, threadFactory, true);
/*    */   }
/*    */   
/*    */   public DefaultEventExecutor(EventExecutorGroup parent, Executor executor) {
/* 48 */     super(parent, executor, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public DefaultEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
/* 53 */     super(parent, threadFactory, true, maxPendingTasks, rejectedExecutionHandler);
/*    */   }
/*    */ 
/*    */   
/*    */   public DefaultEventExecutor(EventExecutorGroup parent, Executor executor, int maxPendingTasks, RejectedExecutionHandler rejectedExecutionHandler) {
/* 58 */     super(parent, executor, true, maxPendingTasks, rejectedExecutionHandler);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void run() {
/*    */     do {
/* 64 */       Runnable task = takeTask();
/* 65 */       if (task == null)
/* 66 */         continue;  runTask(task);
/* 67 */       updateLastExecutionTime();
/*    */     
/*    */     }
/* 70 */     while (!confirmShutdown());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\DefaultEventExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */