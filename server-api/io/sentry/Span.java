/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.Contexts;
/*     */ import io.sentry.protocol.MeasurementValue;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.util.Objects;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class Span
/*     */   implements ISpan
/*     */ {
/*     */   @NotNull
/*     */   private SentryDate startTimestamp;
/*     */   @Nullable
/*     */   private SentryDate timestamp;
/*     */   @NotNull
/*     */   private final SpanContext context;
/*     */   @NotNull
/*     */   private final SentryTracer transaction;
/*     */   @Nullable
/*     */   private Throwable throwable;
/*     */   @NotNull
/*     */   private final IScopes scopes;
/*     */   private boolean finished = false;
/*     */   @NotNull
/*  41 */   private final AtomicBoolean isFinishing = new AtomicBoolean(false);
/*     */   @NotNull
/*     */   private final SpanOptions options;
/*     */   @Nullable
/*     */   private SpanFinishedCallback spanFinishedCallback;
/*     */   @NotNull
/*  47 */   private final Map<String, Object> data = new ConcurrentHashMap<>(); @NotNull
/*  48 */   private final Map<String, MeasurementValue> measurements = new ConcurrentHashMap<>();
/*     */   @NotNull
/*  50 */   private final Contexts contexts = new Contexts();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Span(@NotNull SentryTracer transaction, @NotNull IScopes scopes, @NotNull SpanContext spanContext, @NotNull SpanOptions options, @Nullable SpanFinishedCallback spanFinishedCallback) {
/*  58 */     this.context = spanContext;
/*  59 */     this.context.setOrigin(options.getOrigin());
/*  60 */     this.transaction = (SentryTracer)Objects.requireNonNull(transaction, "transaction is required");
/*  61 */     this.scopes = (IScopes)Objects.requireNonNull(scopes, "Scopes are required");
/*  62 */     this.options = options;
/*  63 */     this.spanFinishedCallback = spanFinishedCallback;
/*  64 */     SentryDate startTimestamp = options.getStartTimestamp();
/*  65 */     if (startTimestamp != null) {
/*  66 */       this.startTimestamp = startTimestamp;
/*     */     } else {
/*  68 */       this.startTimestamp = scopes.getOptions().getDateProvider().now();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Span(@NotNull TransactionContext context, @NotNull SentryTracer sentryTracer, @NotNull IScopes scopes, @NotNull SpanOptions options) {
/*  77 */     this.context = (SpanContext)Objects.requireNonNull(context, "context is required");
/*  78 */     this.context.setOrigin(options.getOrigin());
/*  79 */     this.transaction = (SentryTracer)Objects.requireNonNull(sentryTracer, "sentryTracer is required");
/*  80 */     this.scopes = (IScopes)Objects.requireNonNull(scopes, "scopes are required");
/*  81 */     this.spanFinishedCallback = null;
/*  82 */     SentryDate startTimestamp = options.getStartTimestamp();
/*  83 */     if (startTimestamp != null) {
/*  84 */       this.startTimestamp = startTimestamp;
/*     */     } else {
/*  86 */       this.startTimestamp = scopes.getOptions().getDateProvider().now();
/*     */     } 
/*  88 */     this.options = options;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryDate getStartDate() {
/*  93 */     return this.startTimestamp;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryDate getFinishDate() {
/*  98 */     return this.timestamp;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation) {
/* 103 */     return startChild(operation, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation, @Nullable String description, @Nullable SentryDate timestamp, @NotNull Instrumenter instrumenter, @NotNull SpanOptions spanOptions) {
/* 113 */     if (this.finished) {
/* 114 */       return NoOpSpan.getInstance();
/*     */     }
/*     */     
/* 117 */     return this.transaction.startChild(this.context
/* 118 */         .getSpanId(), operation, description, timestamp, instrumenter, spanOptions);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation, @Nullable String description) {
/* 124 */     if (this.finished) {
/* 125 */       return NoOpSpan.getInstance();
/*     */     }
/*     */     
/* 128 */     return this.transaction.startChild(this.context.getSpanId(), operation, description);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation, @Nullable String description, @NotNull SpanOptions spanOptions) {
/* 134 */     if (this.finished) {
/* 135 */       return NoOpSpan.getInstance();
/*     */     }
/* 137 */     return this.transaction.startChild(this.context.getSpanId(), operation, description, spanOptions);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull SpanContext spanContext, @NotNull SpanOptions spanOptions) {
/* 143 */     return this.transaction.startChild(spanContext, spanOptions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ISpan startChild(@NotNull String operation, @Nullable String description, @Nullable SentryDate timestamp, @NotNull Instrumenter instrumenter) {
/* 152 */     return startChild(operation, description, timestamp, instrumenter, new SpanOptions());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryTraceHeader toSentryTrace() {
/* 157 */     return new SentryTraceHeader(this.context.getTraceId(), this.context.getSpanId(), this.context.getSampled());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TraceContext traceContext() {
/* 162 */     return this.transaction.traceContext();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BaggageHeader toBaggageHeader(@Nullable List<String> thirdPartyBaggageHeaders) {
/* 167 */     return this.transaction.toBaggageHeader(thirdPartyBaggageHeaders);
/*     */   }
/*     */ 
/*     */   
/*     */   public void finish() {
/* 172 */     finish(this.context.getStatus());
/*     */   }
/*     */ 
/*     */   
/*     */   public void finish(@Nullable SpanStatus status) {
/* 177 */     finish(status, this.scopes.getOptions().getDateProvider().now());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finish(@Nullable SpanStatus status, @Nullable SentryDate timestamp) {
/* 189 */     if (this.finished || !this.isFinishing.compareAndSet(false, true)) {
/*     */       return;
/*     */     }
/*     */     
/* 193 */     this.context.setStatus(status);
/* 194 */     this.timestamp = (timestamp == null) ? this.scopes.getOptions().getDateProvider().now() : timestamp;
/* 195 */     if (this.options.isTrimStart() || this.options.isTrimEnd()) {
/* 196 */       SentryDate minChildStart = null;
/* 197 */       SentryDate maxChildEnd = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 204 */       List<Span> children = this.transaction.getRoot().getSpanId().equals(getSpanId()) ? this.transaction.getChildren() : getDirectChildren();
/* 205 */       for (Span child : children) {
/* 206 */         if (minChildStart == null || child.getStartDate().isBefore(minChildStart)) {
/* 207 */           minChildStart = child.getStartDate();
/*     */         }
/* 209 */         if (maxChildEnd == null || (child
/* 210 */           .getFinishDate() != null && child.getFinishDate().isAfter(maxChildEnd))) {
/* 211 */           maxChildEnd = child.getFinishDate();
/*     */         }
/*     */       } 
/* 214 */       if (this.options.isTrimStart() && minChildStart != null && this.startTimestamp
/*     */         
/* 216 */         .isBefore(minChildStart)) {
/* 217 */         updateStartDate(minChildStart);
/*     */       }
/* 219 */       if (this.options.isTrimEnd() && maxChildEnd != null && (this.timestamp == null || this.timestamp
/*     */         
/* 221 */         .isAfter(maxChildEnd))) {
/* 222 */         updateEndDate(maxChildEnd);
/*     */       }
/*     */     } 
/*     */     
/* 226 */     if (this.throwable != null) {
/* 227 */       this.scopes.setSpanContext(this.throwable, this, this.transaction.getName());
/*     */     }
/* 229 */     if (this.spanFinishedCallback != null) {
/* 230 */       this.spanFinishedCallback.execute(this);
/*     */     }
/* 232 */     this.finished = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOperation(@NotNull String operation) {
/* 237 */     this.context.setOperation(operation);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getOperation() {
/* 242 */     return this.context.getOperation();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDescription(@Nullable String description) {
/* 247 */     this.context.setDescription(description);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getDescription() {
/* 252 */     return this.context.getDescription();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(@Nullable SpanStatus status) {
/* 257 */     this.context.setStatus(status);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SpanStatus getStatus() {
/* 262 */     return this.context.getStatus();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SpanContext getSpanContext() {
/* 267 */     return this.context;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTag(@Nullable String key, @Nullable String value) {
/* 272 */     this.context.setTag(key, value);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getTag(@Nullable String key) {
/* 277 */     if (key == null) {
/* 278 */       return null;
/*     */     }
/* 280 */     return this.context.getTags().get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/* 285 */     return this.finished;
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, Object> getData() {
/* 289 */     return this.data;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Boolean isSampled() {
/* 294 */     return this.context.getSampled();
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isProfileSampled() {
/* 298 */     return this.context.getProfileSampled();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TracesSamplingDecision getSamplingDecision() {
/* 303 */     return this.context.getSamplingDecision();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setThrowable(@Nullable Throwable throwable) {
/* 308 */     this.throwable = throwable;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Throwable getThrowable() {
/* 313 */     return this.throwable;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId getTraceId() {
/* 318 */     return this.context.getTraceId();
/*     */   }
/*     */   @NotNull
/*     */   public SpanId getSpanId() {
/* 322 */     return this.context.getSpanId();
/*     */   }
/*     */   @Nullable
/*     */   public SpanId getParentSpanId() {
/* 326 */     return this.context.getParentSpanId();
/*     */   }
/*     */   
/*     */   public Map<String, String> getTags() {
/* 330 */     return this.context.getTags();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setData(@Nullable String key, @Nullable Object value) {
/* 335 */     if (key == null) {
/*     */       return;
/*     */     }
/* 338 */     if (value == null) {
/* 339 */       this.data.remove(key);
/*     */     } else {
/* 341 */       this.data.put(key, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object getData(@Nullable String key) {
/* 347 */     if (key == null) {
/* 348 */       return null;
/*     */     }
/* 350 */     return this.data.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMeasurement(@NotNull String name, @NotNull Number value) {
/* 355 */     if (isFinished()) {
/* 356 */       this.scopes
/* 357 */         .getOptions()
/* 358 */         .getLogger()
/* 359 */         .log(SentryLevel.DEBUG, "The span is already finished. Measurement %s cannot be set", new Object[] { name });
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 365 */     this.measurements.put(name, new MeasurementValue(value, null));
/*     */ 
/*     */     
/* 368 */     if (this.transaction.getRoot() != this) {
/* 369 */       this.transaction.setMeasurementFromChild(name, value);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMeasurement(@NotNull String name, @NotNull Number value, @NotNull MeasurementUnit unit) {
/* 378 */     if (isFinished()) {
/* 379 */       this.scopes
/* 380 */         .getOptions()
/* 381 */         .getLogger()
/* 382 */         .log(SentryLevel.DEBUG, "The span is already finished. Measurement %s cannot be set", new Object[] { name });
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 388 */     this.measurements.put(name, new MeasurementValue(value, unit.apiName()));
/*     */ 
/*     */     
/* 391 */     if (this.transaction.getRoot() != this) {
/* 392 */       this.transaction.setMeasurementFromChild(name, value, unit);
/*     */     }
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Map<String, MeasurementValue> getMeasurements() {
/* 398 */     return this.measurements;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean updateEndDate(@NotNull SentryDate date) {
/* 403 */     if (this.timestamp != null) {
/* 404 */       this.timestamp = date;
/* 405 */       return true;
/*     */     } 
/* 407 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNoOp() {
/* 412 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContext(@Nullable String key, @Nullable Object context) {
/* 417 */     this.contexts.put(key, context);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Contexts getContexts() {
/* 422 */     return this.contexts;
/*     */   }
/*     */   
/*     */   void setSpanFinishedCallback(@Nullable SpanFinishedCallback callback) {
/* 426 */     this.spanFinishedCallback = callback;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   SpanFinishedCallback getSpanFinishedCallback() {
/* 431 */     return this.spanFinishedCallback;
/*     */   }
/*     */   
/*     */   private void updateStartDate(@NotNull SentryDate date) {
/* 435 */     this.startTimestamp = date;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   SpanOptions getOptions() {
/* 440 */     return this.options;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private List<Span> getDirectChildren() {
/* 445 */     List<Span> children = new ArrayList<>();
/* 446 */     Iterator<Span> iterator = this.transaction.getSpans().iterator();
/*     */     
/* 448 */     while (iterator.hasNext()) {
/* 449 */       Span span = iterator.next();
/* 450 */       if (span.getParentSpanId() != null && span.getParentSpanId().equals(getSpanId())) {
/* 451 */         children.add(span);
/*     */       }
/*     */     } 
/* 454 */     return children;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ISentryLifecycleToken makeCurrent() {
/* 459 */     return NoOpScopesLifecycleToken.getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {
/* 464 */     this.context.addFeatureFlag(flag, result);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\Span.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */