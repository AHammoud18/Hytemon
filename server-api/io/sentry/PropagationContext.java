/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.exception.InvalidSentryTraceHeaderException;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.util.TracingUtils;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class PropagationContext
/*     */ {
/*     */   @NotNull
/*     */   private SentryId traceId;
/*     */   
/*     */   public static PropagationContext fromHeaders(@NotNull ILogger logger, @Nullable String sentryTraceHeader, @Nullable String baggageHeader) {
/*  19 */     return fromHeaders(logger, sentryTraceHeader, Arrays.asList(new String[] { baggageHeader }));
/*     */   }
/*     */   @NotNull
/*     */   private SpanId spanId; @Nullable
/*     */   private SpanId parentSpanId;
/*     */   @NotNull
/*     */   public static PropagationContext fromHeaders(@NotNull ILogger logger, @Nullable String sentryTraceHeaderString, @Nullable List<String> baggageHeaderStrings) {
/*  26 */     if (sentryTraceHeaderString == null) {
/*  27 */       return new PropagationContext();
/*     */     }
/*     */     
/*     */     try {
/*  31 */       SentryTraceHeader traceHeader = new SentryTraceHeader(sentryTraceHeaderString);
/*  32 */       Baggage baggage = Baggage.fromHeader(baggageHeaderStrings, logger);
/*  33 */       return fromHeaders(traceHeader, baggage, (SpanId)null);
/*  34 */     } catch (InvalidSentryTraceHeaderException e) {
/*  35 */       logger.log(SentryLevel.DEBUG, (Throwable)e, "Failed to parse Sentry trace header: %s", new Object[] { e.getMessage() });
/*  36 */       return new PropagationContext();
/*     */     } 
/*     */   }
/*     */   @Nullable
/*     */   private Boolean sampled;
/*     */   
/*     */   @NotNull
/*     */   public static PropagationContext fromHeaders(@NotNull SentryTraceHeader sentryTraceHeader, @Nullable Baggage baggage, @Nullable SpanId spanId) {
/*  44 */     SpanId spanIdToUse = (spanId == null) ? new SpanId() : spanId;
/*     */     
/*  46 */     return new PropagationContext(sentryTraceHeader
/*  47 */         .getTraceId(), spanIdToUse, sentryTraceHeader
/*     */         
/*  49 */         .getSpanId(), baggage, sentryTraceHeader
/*     */         
/*  51 */         .isSampled());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private final Baggage baggage;
/*     */   
/*     */   @NotNull
/*     */   public static PropagationContext fromExistingTrace(@NotNull String traceId, @NotNull String spanId, @Nullable Double decisionSampleRate, @Nullable Double decisionSampleRand) {
/*  59 */     return new PropagationContext(new SentryId(traceId), new SpanId(), new SpanId(spanId), 
/*     */ 
/*     */ 
/*     */         
/*  63 */         TracingUtils.ensureBaggage(null, null, decisionSampleRate, decisionSampleRand), null);
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
/*     */   public PropagationContext() {
/*  76 */     this(new SentryId(), new SpanId(), null, null, null);
/*     */   }
/*     */   
/*     */   public PropagationContext(@NotNull PropagationContext propagationContext) {
/*  80 */     this(propagationContext
/*  81 */         .getTraceId(), propagationContext
/*  82 */         .getSpanId(), propagationContext
/*  83 */         .getParentSpanId(), propagationContext
/*  84 */         .getBaggage(), propagationContext
/*  85 */         .isSampled());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropagationContext(@NotNull SentryId traceId, @NotNull SpanId spanId, @Nullable SpanId parentSpanId, @Nullable Baggage baggage, @Nullable Boolean sampled) {
/*  94 */     this.traceId = traceId;
/*  95 */     this.spanId = spanId;
/*  96 */     this.parentSpanId = parentSpanId;
/*  97 */     this.baggage = TracingUtils.ensureBaggage(baggage, sampled, null, null);
/*  98 */     this.sampled = sampled;
/*     */   }
/*     */   @NotNull
/*     */   public SentryId getTraceId() {
/* 102 */     return this.traceId;
/*     */   }
/*     */   
/*     */   public void setTraceId(@NotNull SentryId traceId) {
/* 106 */     this.traceId = traceId;
/*     */   }
/*     */   @NotNull
/*     */   public SpanId getSpanId() {
/* 110 */     return this.spanId;
/*     */   }
/*     */   
/*     */   public void setSpanId(@NotNull SpanId spanId) {
/* 114 */     this.spanId = spanId;
/*     */   }
/*     */   @Nullable
/*     */   public SpanId getParentSpanId() {
/* 118 */     return this.parentSpanId;
/*     */   }
/*     */   
/*     */   public void setParentSpanId(@Nullable SpanId parentSpanId) {
/* 122 */     this.parentSpanId = parentSpanId;
/*     */   }
/*     */   @NotNull
/*     */   public Baggage getBaggage() {
/* 126 */     return this.baggage;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isSampled() {
/* 130 */     return this.sampled;
/*     */   }
/*     */   
/*     */   public void setSampled(@Nullable Boolean sampled) {
/* 134 */     this.sampled = sampled;
/*     */   }
/*     */   @Nullable
/*     */   public TraceContext traceContext() {
/* 138 */     return this.baggage.toTraceContext();
/*     */   }
/*     */   @NotNull
/*     */   public SpanContext toSpanContext() {
/* 142 */     SpanContext spanContext = new SpanContext(this.traceId, this.spanId, "default", null, null);
/* 143 */     spanContext.setOrigin("auto");
/* 144 */     return spanContext;
/*     */   }
/*     */   @NotNull
/*     */   public Double getSampleRand() {
/* 148 */     Double sampleRand = this.baggage.getSampleRand();
/*     */     
/* 150 */     return Double.valueOf((sampleRand == null) ? 0.0D : sampleRand.doubleValue());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\PropagationContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */