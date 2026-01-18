/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public final class ThreadPerTaskExecutor
/*    */   implements Executor
/*    */ {
/*    */   private final ThreadFactory threadFactory;
/*    */   
/*    */   public ThreadPerTaskExecutor(ThreadFactory threadFactory) {
/* 27 */     this.threadFactory = (ThreadFactory)ObjectUtil.checkNotNull(threadFactory, "threadFactory");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(Runnable command) {
/* 32 */     this.threadFactory.newThread(command).start();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\ThreadPerTaskExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */