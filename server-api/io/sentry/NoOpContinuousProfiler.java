/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.protocol.SentryId;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public final class NoOpContinuousProfiler
/*    */   implements IContinuousProfiler {
/*  8 */   private static final NoOpContinuousProfiler instance = new NoOpContinuousProfiler();
/*    */ 
/*    */ 
/*    */   
/*    */   public static NoOpContinuousProfiler getInstance() {
/* 13 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public void stopProfiler(@NotNull ProfileLifecycle profileLifecycle) {}
/*    */ 
/*    */   
/*    */   public boolean isRunning() {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startProfiler(@NotNull ProfileLifecycle profileLifecycle, @NotNull TracesSampler tracesSampler) {}
/*    */ 
/*    */   
/*    */   public void close(boolean isTerminating) {}
/*    */ 
/*    */   
/*    */   public void reevaluateSampling() {}
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public SentryId getProfilerId() {
/* 37 */     return SentryId.EMPTY_ID;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public SentryId getChunkId() {
/* 42 */     return SentryId.EMPTY_ID;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpContinuousProfiler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */