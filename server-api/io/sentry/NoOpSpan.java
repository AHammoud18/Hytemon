/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.Contexts;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class NoOpSpan
/*     */   implements ISpan {
/*  11 */   private static final NoOpSpan instance = new NoOpSpan();
/*     */ 
/*     */ 
/*     */   
/*     */   public static NoOpSpan getInstance() {
/*  16 */     return instance;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation) {
/*  21 */     return getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation, @Nullable String description, @NotNull SpanOptions spanOptions) {
/*  27 */     return getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull SpanContext spanContext, @NotNull SpanOptions spanOptions) {
/*  33 */     return getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation, @Nullable String description, @Nullable SentryDate timestamp, @NotNull Instrumenter instrumenter) {
/*  42 */     return getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation, @Nullable String description, @Nullable SentryDate timestamp, @NotNull Instrumenter instrumenter, @NotNull SpanOptions spanOptions) {
/*  52 */     return getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation, @Nullable String description) {
/*  58 */     return getInstance();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryTraceHeader toSentryTrace() {
/*  63 */     return new SentryTraceHeader(SentryId.EMPTY_ID, SpanId.EMPTY_ID, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TraceContext traceContext() {
/*  68 */     return new TraceContext(SentryId.EMPTY_ID, "");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BaggageHeader toBaggageHeader(@Nullable List<String> thirdPartyBaggageHeaders) {
/*  73 */     return null;
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
/*  90 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDescription(@Nullable String description) {}
/*     */   
/*     */   @Nullable
/*     */   public String getDescription() {
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(@Nullable SpanStatus status) {}
/*     */   
/*     */   @Nullable
/*     */   public SpanStatus getStatus() {
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setThrowable(@Nullable Throwable throwable) {}
/*     */   
/*     */   @Nullable
/*     */   public Throwable getThrowable() {
/* 114 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SpanContext getSpanContext() {
/* 119 */     return new SpanContext(SentryId.EMPTY_ID, SpanId.EMPTY_ID, "op", null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTag(@Nullable String key, @Nullable String value) {}
/*     */   
/*     */   @Nullable
/*     */   public String getTag(@Nullable String key) {
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setData(@Nullable String key, @Nullable Object value) {}
/*     */   
/*     */   @Nullable
/*     */   public Object getData(@Nullable String key) {
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMeasurement(@NotNull String name, @NotNull Number value) {}
/*     */ 
/*     */   
/*     */   public void setMeasurement(@NotNull String name, @NotNull Number value, @NotNull MeasurementUnit unit) {}
/*     */ 
/*     */   
/*     */   public boolean updateEndDate(@NotNull SentryDate date) {
/* 152 */     return false;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryDate getStartDate() {
/* 157 */     return new SentryNanotimeDate();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryDate getFinishDate() {
/* 162 */     return new SentryNanotimeDate();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNoOp() {
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContext(@Nullable String key, @Nullable Object context) {}
/*     */   
/*     */   @NotNull
/*     */   public Contexts getContexts() {
/* 175 */     return new Contexts();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Boolean isSampled() {
/* 180 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TracesSamplingDecision getSamplingDecision() {
/* 185 */     return null;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken makeCurrent() {
/* 190 */     return NoOpScopesLifecycleToken.getInstance();
/*     */   }
/*     */   
/*     */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpSpan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */