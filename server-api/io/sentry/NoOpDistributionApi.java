/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.TimeoutException;
/*    */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Experimental
/*    */ public final class NoOpDistributionApi
/*    */   implements IDistributionApi
/*    */ {
/* 14 */   private static final NoOpDistributionApi instance = new NoOpDistributionApi();
/*    */ 
/*    */ 
/*    */   
/*    */   public static NoOpDistributionApi getInstance() {
/* 19 */     return instance;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public UpdateStatus checkForUpdateBlocking() {
/* 24 */     return UpdateStatus.UpToDate.getInstance();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Future<UpdateStatus> checkForUpdate() {
/* 29 */     return new CompletedFuture<>(UpdateStatus.UpToDate.getInstance());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void downloadUpdate(@NotNull UpdateInfo info) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   private static final class CompletedFuture<T>
/*    */     implements Future<T>
/*    */   {
/*    */     private final T result;
/*    */ 
/*    */     
/*    */     CompletedFuture(T result) {
/* 50 */       this.result = result;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean cancel(boolean mayInterruptIfRunning) {
/* 55 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isCancelled() {
/* 60 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isDone() {
/* 65 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public T get() throws ExecutionException {
/* 70 */       return this.result;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public T get(long timeout, @NotNull TimeUnit unit) throws ExecutionException, TimeoutException {
/* 76 */       return this.result;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpDistributionApi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */