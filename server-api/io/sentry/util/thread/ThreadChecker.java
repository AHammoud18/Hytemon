/*    */ package io.sentry.util.thread;
/*    */ 
/*    */ import io.sentry.protocol.SentryThread;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class ThreadChecker
/*    */   implements IThreadChecker
/*    */ {
/* 17 */   private static final long mainThreadId = Thread.currentThread().getId();
/* 18 */   private static final ThreadChecker instance = new ThreadChecker();
/*    */   
/*    */   public static ThreadChecker getInstance() {
/* 21 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isMainThread(long threadId) {
/* 28 */     return (mainThreadId == threadId);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMainThread(@NotNull Thread thread) {
/* 33 */     return isMainThread(thread.getId());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMainThread() {
/* 38 */     return isMainThread(Thread.currentThread());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMainThread(@NotNull SentryThread sentryThread) {
/* 43 */     Long threadId = sentryThread.getId();
/* 44 */     return (threadId != null && isMainThread(threadId.longValue()));
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String getCurrentThreadName() {
/* 49 */     return Thread.currentThread().getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public long currentThreadSystemId() {
/* 54 */     return Thread.currentThread().getId();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\thread\ThreadChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */