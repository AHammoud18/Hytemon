/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
/*    */ import io.netty.util.concurrent.FastThreadLocal;
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
/*    */ public final class ThreadExecutorMap
/*    */ {
/* 29 */   private static final FastThreadLocal<EventExecutor> mappings = new FastThreadLocal();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static EventExecutor currentExecutor() {
/* 37 */     return (EventExecutor)mappings.get();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static EventExecutor setCurrentExecutor(EventExecutor executor) {
/* 44 */     return (EventExecutor)mappings.getAndSet(executor);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Executor apply(final Executor executor, final EventExecutor eventExecutor) {
/* 52 */     ObjectUtil.checkNotNull(executor, "executor");
/* 53 */     ObjectUtil.checkNotNull(eventExecutor, "eventExecutor");
/* 54 */     return new Executor()
/*    */       {
/*    */         public void execute(Runnable command) {
/* 57 */           executor.execute(ThreadExecutorMap.apply(command, eventExecutor));
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Runnable apply(final Runnable command, final EventExecutor eventExecutor) {
/* 67 */     ObjectUtil.checkNotNull(command, "command");
/* 68 */     ObjectUtil.checkNotNull(eventExecutor, "eventExecutor");
/* 69 */     return new Runnable()
/*    */       {
/*    */         public void run() {
/* 72 */           EventExecutor old = ThreadExecutorMap.setCurrentExecutor(eventExecutor);
/*    */           try {
/* 74 */             command.run();
/*    */           } finally {
/* 76 */             ThreadExecutorMap.setCurrentExecutor(old);
/*    */           } 
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ThreadFactory apply(final ThreadFactory threadFactory, final EventExecutor eventExecutor) {
/* 87 */     ObjectUtil.checkNotNull(threadFactory, "threadFactory");
/* 88 */     ObjectUtil.checkNotNull(eventExecutor, "eventExecutor");
/* 89 */     return new ThreadFactory()
/*    */       {
/*    */         public Thread newThread(Runnable r) {
/* 92 */           return threadFactory.newThread(ThreadExecutorMap.apply(r, eventExecutor));
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\ThreadExecutorMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */