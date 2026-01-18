/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import io.sentry.util.SampleRateUtils;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public final class TracesSampler {
/*    */   @NotNull
/*    */   private final SentryOptions options;
/*    */   
/*    */   public TracesSampler(@NotNull SentryOptions options) {
/* 14 */     this.options = (SentryOptions)Objects.requireNonNull(options, "options are required");
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public TracesSamplingDecision sample(@NotNull SamplingContext samplingContext) {
/* 20 */     Double sampleRand = samplingContext.getSampleRand();
/*    */     
/* 22 */     TracesSamplingDecision samplingContextSamplingDecision = samplingContext.getTransactionContext().getSamplingDecision();
/* 23 */     if (samplingContextSamplingDecision != null) {
/* 24 */       return SampleRateUtils.backfilledSampleRand(samplingContextSamplingDecision);
/*    */     }
/*    */     
/* 27 */     Double profilesSampleRate = null;
/* 28 */     if (this.options.getProfilesSampler() != null) {
/*    */       try {
/* 30 */         profilesSampleRate = this.options.getProfilesSampler().sample(samplingContext);
/* 31 */       } catch (Throwable t) {
/* 32 */         this.options
/* 33 */           .getLogger()
/* 34 */           .log(SentryLevel.ERROR, "Error in the 'ProfilesSamplerCallback' callback.", t);
/*    */       } 
/*    */     }
/* 37 */     if (profilesSampleRate == null) {
/* 38 */       profilesSampleRate = this.options.getProfilesSampleRate();
/*    */     }
/* 40 */     Boolean profilesSampled = Boolean.valueOf((profilesSampleRate != null && sample(profilesSampleRate, sampleRand)));
/*    */     
/* 42 */     if (this.options.getTracesSampler() != null) {
/* 43 */       Double samplerResult = null;
/*    */       try {
/* 45 */         samplerResult = this.options.getTracesSampler().sample(samplingContext);
/* 46 */       } catch (Throwable t) {
/* 47 */         this.options
/* 48 */           .getLogger()
/* 49 */           .log(SentryLevel.ERROR, "Error in the 'TracesSamplerCallback' callback.", t);
/*    */       } 
/* 51 */       if (samplerResult != null) {
/* 52 */         return new TracesSamplingDecision(
/* 53 */             Boolean.valueOf(sample(samplerResult, sampleRand)), samplerResult, sampleRand, profilesSampled, profilesSampleRate);
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 62 */     TracesSamplingDecision parentSamplingDecision = samplingContext.getTransactionContext().getParentSamplingDecision();
/* 63 */     if (parentSamplingDecision != null) {
/* 64 */       return SampleRateUtils.backfilledSampleRand(parentSamplingDecision);
/*    */     }
/*    */     
/* 67 */     Double tracesSampleRateFromOptions = this.options.getTracesSampleRate();
/*    */     
/* 69 */     Double downsampleFactor = Double.valueOf(Math.pow(2.0D, this.options.getBackpressureMonitor().getDownsampleFactor()));
/*    */     
/* 71 */     Double downsampledTracesSampleRate = (tracesSampleRateFromOptions == null) ? null : Double.valueOf(tracesSampleRateFromOptions.doubleValue() / downsampleFactor.doubleValue());
/*    */     
/* 73 */     if (downsampledTracesSampleRate != null) {
/* 74 */       return new TracesSamplingDecision(
/* 75 */           Boolean.valueOf(sample(downsampledTracesSampleRate, sampleRand)), downsampledTracesSampleRate, sampleRand, profilesSampled, profilesSampleRate);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 82 */     return new TracesSamplingDecision(Boolean.valueOf(false), null, sampleRand, Boolean.valueOf(false), null);
/*    */   }
/*    */   
/*    */   public boolean sampleSessionProfile(double sampleRand) {
/* 86 */     Double sampling = this.options.getProfileSessionSampleRate();
/* 87 */     return (sampling != null && sample(sampling, Double.valueOf(sampleRand)));
/*    */   }
/*    */   
/*    */   private boolean sample(@NotNull Double sampleRate, @NotNull Double sampleRand) {
/* 91 */     return (sampleRate.doubleValue() >= sampleRand.doubleValue());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\TracesSampler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */