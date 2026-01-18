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
/*    */ 
/*    */ 
/*    */ public class DefaultEventExecutorGroup
/*    */   extends MultithreadEventExecutorGroup
/*    */ {
/*    */   public DefaultEventExecutorGroup(int nThreads) {
/* 30 */     this(nThreads, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DefaultEventExecutorGroup(int nThreads, ThreadFactory threadFactory) {
/* 40 */     this(nThreads, threadFactory, SingleThreadEventExecutor.DEFAULT_MAX_PENDING_EXECUTOR_TASKS, 
/* 41 */         RejectedExecutionHandlers.reject());
/*    */   }
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
/*    */   public DefaultEventExecutorGroup(int nThreads, ThreadFactory threadFactory, int maxPendingTasks, RejectedExecutionHandler rejectedHandler) {
/* 54 */     super(nThreads, threadFactory, new Object[] { Integer.valueOf(maxPendingTasks), rejectedHandler });
/*    */   }
/*    */ 
/*    */   
/*    */   protected EventExecutor newChild(Executor executor, Object... args) throws Exception {
/* 59 */     return new DefaultEventExecutor(this, executor, ((Integer)args[0]).intValue(), (RejectedExecutionHandler)args[1]);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\DefaultEventExecutorGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */