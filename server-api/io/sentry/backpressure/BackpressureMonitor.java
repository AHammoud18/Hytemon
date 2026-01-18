/*    */ package io.sentry.backpressure;
/*    */ import io.sentry.IScopes;
/*    */ import io.sentry.ISentryExecutorService;
/*    */ import io.sentry.ISentryLifecycleToken;
/*    */ import io.sentry.SentryLevel;
/*    */ import io.sentry.SentryOptions;
/*    */ import io.sentry.util.AutoClosableReentrantLock;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.concurrent.RejectedExecutionException;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class BackpressureMonitor implements IBackpressureMonitor, Runnable {
/*    */   static final int MAX_DOWNSAMPLE_FACTOR = 10;
/*    */   private static final int INITIAL_CHECK_DELAY_IN_MS = 500;
/*    */   private static final int CHECK_INTERVAL_IN_MS = 10000;
/*    */   @NotNull
/*    */   private final SentryOptions sentryOptions;
/*    */   @NotNull
/*    */   private final IScopes scopes;
/* 21 */   private int downsampleFactor = 0; @Nullable
/* 22 */   private volatile Future<?> latestScheduledRun = null; @NotNull
/* 23 */   private final AutoClosableReentrantLock lock = new AutoClosableReentrantLock();
/*    */ 
/*    */   
/*    */   public BackpressureMonitor(@NotNull SentryOptions sentryOptions, @NotNull IScopes scopes) {
/* 27 */     this.sentryOptions = sentryOptions;
/* 28 */     this.scopes = scopes;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 33 */     reschedule(500);
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 38 */     checkHealth();
/* 39 */     reschedule(10000);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDownsampleFactor() {
/* 44 */     return this.downsampleFactor;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 49 */     Future<?> currentRun = this.latestScheduledRun;
/* 50 */     if (currentRun != null) {
/* 51 */       ISentryLifecycleToken ignored = this.lock.acquire(); 
/* 52 */       try { currentRun.cancel(true);
/* 53 */         if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*    */           try { ignored.close(); }
/*    */           catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */             throw throwable; }
/*    */     
/* 58 */     }  } void checkHealth() { if (isHealthy()) {
/* 59 */       if (this.downsampleFactor > 0) {
/* 60 */         this.sentryOptions
/* 61 */           .getLogger()
/* 62 */           .log(SentryLevel.DEBUG, "Health check positive, reverting to normal sampling.", new Object[0]);
/*    */       }
/* 64 */       this.downsampleFactor = 0;
/*    */     }
/* 66 */     else if (this.downsampleFactor < 10) {
/* 67 */       this.downsampleFactor++;
/* 68 */       this.sentryOptions
/* 69 */         .getLogger()
/* 70 */         .log(SentryLevel.DEBUG, "Health check negative, downsampling with a factor of %d", new Object[] {
/*    */ 
/*    */             
/* 73 */             Integer.valueOf(this.downsampleFactor)
/*    */           });
/*    */     }  }
/*    */ 
/*    */ 
/*    */   
/*    */   private void reschedule(int delay) {
/* 80 */     ISentryExecutorService executorService = this.sentryOptions.getExecutorService();
/* 81 */     if (!executorService.isClosed()) {
/* 82 */       ISentryLifecycleToken ignored = this.lock.acquire(); 
/*    */       try { try {
/* 84 */           this.latestScheduledRun = executorService.schedule(this, delay);
/* 85 */         } catch (RejectedExecutionException e) {
/* 86 */           this.sentryOptions
/* 87 */             .getLogger()
/* 88 */             .log(SentryLevel.WARNING, "Backpressure monitor reschedule task rejected", e);
/*    */         } 
/* 90 */         if (ignored != null) ignored.close();  } catch (Throwable throwable) { if (ignored != null)
/*    */           try { ignored.close(); }
/*    */           catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */             throw throwable; }
/*    */     
/* 95 */     }  } private boolean isHealthy() { return this.scopes.isHealthy(); }
/*    */ 
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\backpressure\BackpressureMonitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */