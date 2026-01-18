/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.Contexts;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.TransactionNameSource;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class NoOpTransaction
/*     */   implements ITransaction {
/*  14 */   private static final NoOpTransaction instance = new NoOpTransaction();
/*     */ 
/*     */ 
/*     */   
/*     */   public static NoOpTransaction getInstance() {
/*  19 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(@NotNull String name) {}
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void setName(@NotNull String name, @NotNull TransactionNameSource transactionNameSource) {}
/*     */   
/*     */   @NotNull
/*     */   public String getName() {
/*  31 */     return "";
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TransactionNameSource getTransactionNameSource() {
/*  36 */     return TransactionNameSource.CUSTOM;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation) {
/*  41 */     return NoOpSpan.getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation, @Nullable String description, @NotNull SpanOptions spanOptions) {
/*  47 */     return NoOpSpan.getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull SpanContext spanContext, @NotNull SpanOptions spanOptions) {
/*  53 */     return NoOpSpan.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation, @Nullable String description, @Nullable SentryDate timestamp, @NotNull Instrumenter instrumenter) {
/*  62 */     return NoOpSpan.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation, @Nullable String description, @Nullable SentryDate timestamp, @NotNull Instrumenter instrumenter, @NotNull SpanOptions spanOptions) {
/*  72 */     return NoOpSpan.getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation, @Nullable String description) {
/*  78 */     return NoOpSpan.getInstance();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getDescription() {
/*  83 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public List<Span> getSpans() {
/*  88 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation, @Nullable String description, @Nullable SentryDate timestamp) {
/*  94 */     return NoOpSpan.getInstance();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ISpan getLatestActiveSpan() {
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId getEventId() {
/* 104 */     return SentryId.EMPTY_ID;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken makeCurrent() {
/* 109 */     return NoOpScopesLifecycleToken.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scheduleFinish() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void forceFinish(@NotNull SpanStatus status, boolean dropIfNoChildren, @Nullable Hint hint) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void finish(@Nullable SpanStatus status, @Nullable SentryDate timestamp, boolean dropIfNoChildren, @Nullable Hint hint) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/* 128 */     return true;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryTraceHeader toSentryTrace() {
/* 133 */     return new SentryTraceHeader(SentryId.EMPTY_ID, SpanId.EMPTY_ID, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TraceContext traceContext() {
/* 138 */     return new TraceContext(SentryId.EMPTY_ID, "");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BaggageHeader toBaggageHeader(@Nullable List<String> thirdPartyBaggageHeaders) {
/* 143 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void finish() {}
/*     */ 
/*     */   
/*     */   public void finish(@Nullable SpanStatus status) {}
/*     */ 
/*     */   
/*     */   public void finish(@Nullable SpanStatus status, @Nullable SentryDate timestamp) {}
/*     */ 
/*     */   
/*     */   public void setOperation(@NotNull String operation) {}
/*     */   
/*     */   @NotNull
/*     */   public String getOperation() {
/* 160 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDescription(@Nullable String description) {}
/*     */ 
/*     */   
/*     */   public void setStatus(@Nullable SpanStatus status) {}
/*     */   
/*     */   @Nullable
/*     */   public SpanStatus getStatus() {
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setThrowable(@Nullable Throwable throwable) {}
/*     */   
/*     */   @Nullable
/*     */   public Throwable getThrowable() {
/* 179 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SpanContext getSpanContext() {
/* 184 */     return new SpanContext(SentryId.EMPTY_ID, SpanId.EMPTY_ID, "op", null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTag(@Nullable String key, @Nullable String value) {}
/*     */   
/*     */   @Nullable
/*     */   public String getTag(@Nullable String key) {
/* 192 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Boolean isSampled() {
/* 197 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Boolean isProfileSampled() {
/* 202 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TracesSamplingDecision getSamplingDecision() {
/* 207 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setData(@Nullable String key, @Nullable Object value) {}
/*     */   
/*     */   @Nullable
/*     */   public Object getData(@Nullable String key) {
/* 215 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMeasurement(@NotNull String name, @NotNull Number value) {}
/*     */ 
/*     */   
/*     */   public void setMeasurement(@NotNull String name, @NotNull Number value, @NotNull MeasurementUnit unit) {}
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void setContext(@Nullable String key, @Nullable Object context) {}
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public Contexts getContexts() {
/* 232 */     return new Contexts();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean updateEndDate(@NotNull SentryDate date) {
/* 237 */     return false;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryDate getStartDate() {
/* 242 */     return new SentryNanotimeDate();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryDate getFinishDate() {
/* 247 */     return new SentryNanotimeDate();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNoOp() {
/* 252 */     return true;
/*     */   }
/*     */   
/*     */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */