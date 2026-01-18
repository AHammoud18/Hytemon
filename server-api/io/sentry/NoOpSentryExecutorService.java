/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.concurrent.FutureTask;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ final class NoOpSentryExecutorService implements ISentryExecutorService {
/*  9 */   private static final NoOpSentryExecutorService instance = new NoOpSentryExecutorService();
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static ISentryExecutorService getInstance() {
/* 14 */     return instance;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Future<?> submit(@NotNull Runnable runnable) {
/* 19 */     return new FutureTask(() -> null);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T> Future<T> submit(@NotNull Callable<T> callable) {
/* 24 */     return new FutureTask<>(() -> null);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Future<?> schedule(@NotNull Runnable runnable, long delayMillis) {
/* 29 */     return new FutureTask(() -> null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close(long timeoutMillis) {}
/*    */ 
/*    */   
/*    */   public boolean isClosed() {
/* 37 */     return false;
/*    */   }
/*    */   
/*    */   public void prewarm() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpSentryExecutorService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */