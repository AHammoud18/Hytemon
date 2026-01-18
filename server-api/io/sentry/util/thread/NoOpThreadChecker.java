/*    */ package io.sentry.util.thread;
/*    */ 
/*    */ import io.sentry.protocol.SentryThread;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public final class NoOpThreadChecker
/*    */   implements IThreadChecker {
/* 10 */   private static final NoOpThreadChecker instance = new NoOpThreadChecker();
/*    */   
/*    */   public static NoOpThreadChecker getInstance() {
/* 13 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMainThread(long threadId) {
/* 18 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMainThread(@NotNull Thread thread) {
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMainThread() {
/* 28 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMainThread(@NotNull SentryThread sentryThread) {
/* 33 */     return false;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String getCurrentThreadName() {
/* 38 */     return "";
/*    */   }
/*    */ 
/*    */   
/*    */   public long currentThreadSystemId() {
/* 43 */     return 0L;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\thread\NoOpThreadChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */