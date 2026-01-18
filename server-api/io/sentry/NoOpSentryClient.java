/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.protocol.Feedback;
/*    */ import io.sentry.protocol.SentryId;
/*    */ import io.sentry.protocol.SentryTransaction;
/*    */ import io.sentry.transport.RateLimiter;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ final class NoOpSentryClient
/*    */   implements ISentryClient {
/* 13 */   private static final NoOpSentryClient instance = new NoOpSentryClient();
/*    */ 
/*    */ 
/*    */   
/*    */   public static NoOpSentryClient getInstance() {
/* 18 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public SentryId captureEvent(@NotNull SentryEvent event, @Nullable IScope scope, @Nullable Hint hint) {
/* 29 */     return SentryId.EMPTY_ID;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close(boolean isRestarting) {}
/*    */ 
/*    */   
/*    */   public void close() {}
/*    */ 
/*    */   
/*    */   public void flush(long timeoutMillis) {}
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public SentryId captureFeedback(@NotNull Feedback feedback, @Nullable Hint hint, @NotNull IScope scope) {
/* 44 */     return SentryId.EMPTY_ID;
/*    */   }
/*    */ 
/*    */   
/*    */   public void captureUserFeedback(@NotNull UserFeedback userFeedback) {}
/*    */ 
/*    */   
/*    */   public void captureSession(@NotNull Session session, @Nullable Hint hint) {}
/*    */ 
/*    */   
/*    */   public SentryId captureEnvelope(@NotNull SentryEnvelope envelope, @Nullable Hint hint) {
/* 55 */     return SentryId.EMPTY_ID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public SentryId captureTransaction(@NotNull SentryTransaction transaction, @Nullable TraceContext traceContext, @Nullable IScope scope, @Nullable Hint hint, @Nullable ProfilingTraceData profilingTraceData) {
/* 65 */     return SentryId.EMPTY_ID;
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public SentryId captureProfileChunk(@NotNull ProfileChunk profileChunk, @Nullable IScope scope) {
/* 71 */     return SentryId.EMPTY_ID;
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public SentryId captureCheckIn(@NotNull CheckIn checkIn, @Nullable IScope scope, @Nullable Hint hint) {
/* 77 */     return SentryId.EMPTY_ID;
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public SentryId captureReplayEvent(@NotNull SentryReplayEvent event, @Nullable IScope scope, @Nullable Hint hint) {
/* 83 */     return SentryId.EMPTY_ID;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void captureLog(@NotNull SentryLogEvent logEvent, @Nullable IScope scope) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @Internal
/*    */   public void captureBatchedLogEvents(@NotNull SentryLogEvents logEvents) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public RateLimiter getRateLimiter() {
/* 99 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpSentryClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */