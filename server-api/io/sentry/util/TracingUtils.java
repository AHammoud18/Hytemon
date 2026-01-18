/*     */ package io.sentry.util;
/*     */ 
/*     */ import io.sentry.Baggage;
/*     */ import io.sentry.BaggageHeader;
/*     */ import io.sentry.FilterString;
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.IScope;
/*     */ import io.sentry.IScopes;
/*     */ import io.sentry.ISpan;
/*     */ import io.sentry.NoOpLogger;
/*     */ import io.sentry.PropagationContext;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.SentryTraceHeader;
/*     */ import io.sentry.SpanContext;
/*     */ import io.sentry.TracesSamplingDecision;
/*     */ import io.sentry.W3CTraceparentHeader;
/*     */ import java.util.List;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class TracingUtils {
/*     */   public static void startNewTrace(@NotNull IScopes scopes) {
/*  24 */     scopes.configureScope(scope -> scope.withPropagationContext(()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setTrace(@NotNull IScopes scopes, @NotNull PropagationContext propagationContext) {
/*  35 */     scopes.configureScope(scope -> scope.withPropagationContext(()));
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
/*     */   @Nullable
/*     */   public static TracingHeaders traceIfAllowed(@NotNull IScopes scopes, @NotNull String requestUrl, @Nullable List<String> thirdPartyBaggageHeaders, @Nullable ISpan span) {
/*  49 */     SentryOptions sentryOptions = scopes.getOptions();
/*  50 */     if (sentryOptions.isTraceSampling() && shouldAttachTracingHeaders(requestUrl, sentryOptions)) {
/*  51 */       return trace(scopes, thirdPartyBaggageHeaders, span);
/*     */     }
/*     */     
/*  54 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static TracingHeaders trace(@NotNull IScopes scopes, @Nullable List<String> thirdPartyBaggageHeaders, @Nullable ISpan span) {
/*  61 */     SentryOptions sentryOptions = scopes.getOptions();
/*     */     
/*  63 */     if (span != null && !span.isNoOp()) {
/*  64 */       SentryTraceHeader sentryTraceHeader = span.toSentryTrace();
/*  65 */       BaggageHeader baggageHeader = span.toBaggageHeader(thirdPartyBaggageHeaders);
/*  66 */       W3CTraceparentHeader w3cTraceparentHeader = null;
/*     */       
/*  68 */       if (sentryOptions.isPropagateTraceparent()) {
/*  69 */         SpanContext spanContext = span.getSpanContext();
/*     */ 
/*     */         
/*  72 */         w3cTraceparentHeader = new W3CTraceparentHeader(spanContext.getTraceId(), spanContext.getSpanId(), sentryTraceHeader.isSampled());
/*     */       } 
/*     */       
/*  75 */       return new TracingHeaders(sentryTraceHeader, baggageHeader, w3cTraceparentHeader);
/*     */     } 
/*  77 */     PropagationContextHolder returnValue = new PropagationContextHolder();
/*  78 */     scopes.configureScope(scope -> returnValue.propagationContext = maybeUpdateBaggage(scope, sentryOptions));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     if (returnValue.propagationContext != null) {
/*  84 */       PropagationContext propagationContext = returnValue.propagationContext;
/*  85 */       Baggage baggage = propagationContext.getBaggage();
/*     */       
/*  87 */       BaggageHeader baggageHeader = BaggageHeader.fromBaggageAndOutgoingHeader(baggage, thirdPartyBaggageHeaders);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  93 */       SentryTraceHeader sentryTraceHeader = new SentryTraceHeader(propagationContext.getTraceId(), propagationContext.getSpanId(), propagationContext.isSampled());
/*     */       
/*  95 */       W3CTraceparentHeader w3cTraceparentHeader = null;
/*  96 */       if (sentryOptions.isPropagateTraceparent())
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 101 */         w3cTraceparentHeader = new W3CTraceparentHeader(propagationContext.getTraceId(), propagationContext.getSpanId(), propagationContext.isSampled());
/*     */       }
/*     */       
/* 104 */       return new TracingHeaders(sentryTraceHeader, baggageHeader, w3cTraceparentHeader);
/*     */     } 
/*     */     
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static PropagationContext maybeUpdateBaggage(@NotNull IScope scope, @NotNull SentryOptions sentryOptions) {
/* 113 */     return scope.withPropagationContext(propagationContext -> {
/*     */           Baggage baggage = propagationContext.getBaggage();
/*     */           if (baggage.isMutable()) {
/*     */             baggage.setValuesFromScope(scope, sentryOptions);
/*     */             baggage.freeze();
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean shouldAttachTracingHeaders(@NotNull String requestUrl, @NotNull SentryOptions sentryOptions) {
/* 125 */     return PropagationTargetsUtils.contain(sentryOptions.getTracePropagationTargets(), requestUrl);
/*     */   }
/*     */   
/*     */   private static final class PropagationContextHolder { @Nullable
/* 129 */     private PropagationContext propagationContext = null;
/*     */     private PropagationContextHolder() {} }
/*     */   
/*     */   public static final class TracingHeaders { @NotNull
/*     */     private final SentryTraceHeader sentryTraceHeader;
/*     */     @Nullable
/*     */     private final BaggageHeader baggageHeader;
/*     */     @Nullable
/*     */     private final W3CTraceparentHeader w3cTraceparentHeader;
/*     */     
/*     */     public TracingHeaders(@NotNull SentryTraceHeader sentryTraceHeader, @Nullable BaggageHeader baggageHeader) {
/* 140 */       this.sentryTraceHeader = sentryTraceHeader;
/* 141 */       this.baggageHeader = baggageHeader;
/* 142 */       this.w3cTraceparentHeader = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TracingHeaders(@NotNull SentryTraceHeader sentryTraceHeader, @Nullable BaggageHeader baggageHeader, @Nullable W3CTraceparentHeader w3cTraceparentHeader) {
/* 149 */       this.sentryTraceHeader = sentryTraceHeader;
/* 150 */       this.baggageHeader = baggageHeader;
/* 151 */       this.w3cTraceparentHeader = w3cTraceparentHeader;
/*     */     }
/*     */     @NotNull
/*     */     public SentryTraceHeader getSentryTraceHeader() {
/* 155 */       return this.sentryTraceHeader;
/*     */     }
/*     */     @Nullable
/*     */     public BaggageHeader getBaggageHeader() {
/* 159 */       return this.baggageHeader;
/*     */     }
/*     */     @Nullable
/*     */     public W3CTraceparentHeader getW3cTraceparentHeader() {
/* 163 */       return this.w3cTraceparentHeader;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public static boolean isIgnored(@Nullable List<FilterString> ignoredTransactions, @Nullable String transactionName) {
/* 172 */     if (transactionName == null) {
/* 173 */       return false;
/*     */     }
/* 175 */     if (ignoredTransactions == null || ignoredTransactions.isEmpty()) {
/* 176 */       return false;
/*     */     }
/*     */     
/* 179 */     for (FilterString ignoredTransaction : ignoredTransactions) {
/* 180 */       if (ignoredTransaction.getFilterString().equalsIgnoreCase(transactionName)) {
/* 181 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 185 */     for (FilterString ignoredTransaction : ignoredTransactions) {
/*     */       
/*     */       try {
/* 188 */         if (ignoredTransaction.matches(transactionName)) {
/* 189 */           return true;
/*     */         }
/* 191 */       } catch (Throwable throwable) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 196 */     return false;
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
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public static Baggage ensureBaggage(@Nullable Baggage incomingBaggage, @Nullable TracesSamplingDecision decision) {
/* 214 */     Boolean decisionSampled = (decision == null) ? null : decision.getSampled();
/* 215 */     Double decisionSampleRate = (decision == null) ? null : decision.getSampleRate();
/* 216 */     Double decisionSampleRand = (decision == null) ? null : decision.getSampleRand();
/*     */     
/* 218 */     return ensureBaggage(incomingBaggage, decisionSampled, decisionSampleRate, decisionSampleRand);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public static Baggage ensureBaggage(@Nullable Baggage incomingBaggage, @Nullable Boolean decisionSampled, @Nullable Double decisionSampleRate, @Nullable Double decisionSampleRand) {
/* 241 */     Baggage baggage = (incomingBaggage == null) ? new Baggage((ILogger)NoOpLogger.getInstance()) : incomingBaggage;
/*     */     
/* 243 */     if (baggage.getSampleRand() == null) {
/* 244 */       Double baggageSampleRate = baggage.getSampleRate();
/*     */       
/* 246 */       Double sampleRateMaybe = (baggageSampleRate == null) ? decisionSampleRate : baggageSampleRate;
/*     */       
/* 248 */       Double sampleRand = SampleRateUtils.backfilledSampleRand(decisionSampleRand, sampleRateMaybe, decisionSampled);
/*     */       
/* 250 */       baggage.setSampleRand(sampleRand);
/*     */     } 
/* 252 */     if (baggage.isMutable() && 
/* 253 */       baggage.isShouldFreeze()) {
/* 254 */       baggage.freeze();
/*     */     }
/*     */ 
/*     */     
/* 258 */     return baggage;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\TracingUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */