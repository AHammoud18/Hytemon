/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.TransactionNameSource;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.util.TracingUtils;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class TransactionContext extends SpanContext {
/*     */   @NotNull
/*  13 */   private static final TransactionNameSource DEFAULT_NAME_SOURCE = TransactionNameSource.CUSTOM; @NotNull
/*     */   public static final String DEFAULT_TRANSACTION_NAME = "<unlabeled transaction>"; @NotNull
/*     */   private static final String DEFAULT_OPERATION = "default"; @NotNull
/*     */   private String name; @NotNull
/*     */   private TransactionNameSource transactionNameSource;
/*     */   @Nullable
/*     */   private TracesSamplingDecision parentSamplingDecision;
/*     */   private boolean isForNextAppStart = false;
/*     */   
/*     */   @Internal
/*     */   public static TransactionContext fromPropagationContext(@NotNull PropagationContext propagationContext) {
/*  24 */     Boolean parentSampled = propagationContext.isSampled();
/*  25 */     Baggage baggage = propagationContext.getBaggage();
/*  26 */     Double sampleRate = baggage.getSampleRate();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  31 */     TracesSamplingDecision samplingDecision = (parentSampled == null) ? null : new TracesSamplingDecision(parentSampled, sampleRate, propagationContext.getSampleRand());
/*     */     
/*  33 */     return new TransactionContext(propagationContext
/*  34 */         .getTraceId(), propagationContext
/*  35 */         .getSpanId(), propagationContext
/*  36 */         .getParentSpanId(), samplingDecision, baggage);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TransactionContext(@NotNull String name, @NotNull String operation) {
/*  42 */     this(name, operation, (TracesSamplingDecision)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public TransactionContext(@NotNull String name, @NotNull TransactionNameSource transactionNameSource, @NotNull String operation) {
/*  50 */     this(name, transactionNameSource, operation, (TracesSamplingDecision)null);
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
/*     */ 
/*     */   
/*     */   public TransactionContext(@NotNull String name, @NotNull String operation, @Nullable TracesSamplingDecision samplingDecision) {
/*  64 */     this(name, TransactionNameSource.CUSTOM, operation, samplingDecision);
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public TransactionContext(@NotNull String name, @NotNull TransactionNameSource transactionNameSource, @NotNull String operation, @Nullable TracesSamplingDecision samplingDecision) {
/*  80 */     super(operation);
/*  81 */     this.name = (String)Objects.requireNonNull(name, "name is required");
/*  82 */     this.transactionNameSource = transactionNameSource;
/*  83 */     setSamplingDecision(samplingDecision);
/*  84 */     this.baggage = TracingUtils.ensureBaggage(null, samplingDecision);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public TransactionContext(@NotNull SentryId traceId, @NotNull SpanId spanId, @Nullable SpanId parentSpanId, @Nullable TracesSamplingDecision parentSamplingDecision, @Nullable Baggage baggage) {
/*  94 */     super(traceId, spanId, "default", parentSpanId, null);
/*  95 */     this.name = "<unlabeled transaction>";
/*  96 */     this.parentSamplingDecision = parentSamplingDecision;
/*  97 */     this.transactionNameSource = DEFAULT_NAME_SOURCE;
/*  98 */     this.baggage = TracingUtils.ensureBaggage(baggage, parentSamplingDecision);
/*     */   }
/*     */   @NotNull
/*     */   public String getName() {
/* 102 */     return this.name;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean getParentSampled() {
/* 106 */     if (this.parentSamplingDecision == null) {
/* 107 */       return null;
/*     */     }
/*     */     
/* 110 */     return this.parentSamplingDecision.getSampled();
/*     */   }
/*     */   @Nullable
/*     */   public TracesSamplingDecision getParentSamplingDecision() {
/* 114 */     return this.parentSamplingDecision;
/*     */   }
/*     */   
/*     */   public void setParentSampled(@Nullable Boolean parentSampled) {
/* 118 */     if (parentSampled == null) {
/* 119 */       this.parentSamplingDecision = null;
/*     */     } else {
/* 121 */       this.parentSamplingDecision = new TracesSamplingDecision(parentSampled);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParentSampled(@Nullable Boolean parentSampled, @Nullable Boolean parentProfileSampled) {
/* 127 */     if (parentSampled == null) {
/* 128 */       this.parentSamplingDecision = null;
/* 129 */     } else if (parentProfileSampled == null) {
/* 130 */       this.parentSamplingDecision = new TracesSamplingDecision(parentSampled);
/*     */     } else {
/* 132 */       this.parentSamplingDecision = new TracesSamplingDecision(parentSampled, null, parentProfileSampled, null);
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public TransactionNameSource getTransactionNameSource() {
/* 138 */     return this.transactionNameSource;
/*     */   }
/*     */   
/*     */   public void setName(@NotNull String name) {
/* 142 */     this.name = (String)Objects.requireNonNull(name, "name is required");
/*     */   }
/*     */   
/*     */   public void setTransactionNameSource(@NotNull TransactionNameSource transactionNameSource) {
/* 146 */     this.transactionNameSource = transactionNameSource;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setForNextAppStart(boolean forNextAppStart) {
/* 151 */     this.isForNextAppStart = forNextAppStart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isForNextAppStart() {
/* 162 */     return this.isForNextAppStart;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\TransactionContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */