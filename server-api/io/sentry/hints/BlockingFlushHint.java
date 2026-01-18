/*    */ package io.sentry.hints;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.SentryLevel;
/*    */ import java.util.concurrent.CountDownLatch;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public abstract class BlockingFlushHint
/*    */   implements DiskFlushNotification, Flushable {
/*    */   private final CountDownLatch latch;
/*    */   private final long flushTimeoutMillis;
/*    */   @NotNull
/*    */   private final ILogger logger;
/*    */   
/*    */   public BlockingFlushHint(long flushTimeoutMillis, @NotNull ILogger logger) {
/* 19 */     this.flushTimeoutMillis = flushTimeoutMillis;
/* 20 */     this.latch = new CountDownLatch(1);
/* 21 */     this.logger = logger;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean waitFlush() {
/*    */     try {
/* 27 */       return this.latch.await(this.flushTimeoutMillis, TimeUnit.MILLISECONDS);
/* 28 */     } catch (InterruptedException e) {
/* 29 */       Thread.currentThread().interrupt();
/* 30 */       this.logger.log(SentryLevel.ERROR, "Exception while awaiting for flush in BlockingFlushHint", e);
/*    */       
/* 32 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void markFlushed() {
/* 37 */     this.latch.countDown();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\hints\BlockingFlushHint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */