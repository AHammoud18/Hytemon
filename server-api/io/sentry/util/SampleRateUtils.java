/*    */ package io.sentry.util;
/*    */ 
/*    */ import io.sentry.TracesSamplingDecision;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Internal
/*    */ public final class SampleRateUtils
/*    */ {
/*    */   public static boolean isValidSampleRate(@Nullable Double sampleRate) {
/* 12 */     return isValidRate(sampleRate, true);
/*    */   }
/*    */   
/*    */   public static boolean isValidTracesSampleRate(@Nullable Double tracesSampleRate) {
/* 16 */     return isValidTracesSampleRate(tracesSampleRate, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isValidTracesSampleRate(@Nullable Double tracesSampleRate, boolean allowNull) {
/* 21 */     return isValidRate(tracesSampleRate, allowNull);
/*    */   }
/*    */   
/*    */   public static boolean isValidProfilesSampleRate(@Nullable Double profilesSampleRate) {
/* 25 */     return isValidRate(profilesSampleRate, true);
/*    */   }
/*    */   
/*    */   public static boolean isValidContinuousProfilesSampleRate(@Nullable Double profilesSampleRate) {
/* 29 */     return isValidRate(profilesSampleRate, true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static Double backfilledSampleRand(@Nullable Double sampleRand, @Nullable Double sampleRate, @Nullable Boolean sampled) {
/* 36 */     if (sampleRand != null) {
/* 37 */       return sampleRand;
/*    */     }
/*    */     
/* 40 */     double newSampleRand = SentryRandom.current().nextDouble();
/* 41 */     if (sampleRate != null && sampled != null) {
/* 42 */       if (sampled.booleanValue()) {
/* 43 */         return Double.valueOf(newSampleRand * sampleRate.doubleValue());
/*    */       }
/* 45 */       return Double.valueOf(sampleRate.doubleValue() + newSampleRand * (1.0D - sampleRate.doubleValue()));
/*    */     } 
/*    */ 
/*    */     
/* 49 */     return Double.valueOf(newSampleRand);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public static TracesSamplingDecision backfilledSampleRand(@NotNull TracesSamplingDecision samplingDecision) {
/* 54 */     if (samplingDecision.getSampleRand() != null) {
/* 55 */       return samplingDecision;
/*    */     }
/*    */ 
/*    */     
/* 59 */     Double sampleRand = backfilledSampleRand(null, samplingDecision.getSampleRate(), samplingDecision.getSampled());
/* 60 */     return new TracesSamplingDecision(samplingDecision
/* 61 */         .getSampled(), samplingDecision
/* 62 */         .getSampleRate(), sampleRand, samplingDecision
/*    */         
/* 64 */         .getProfileSampled(), samplingDecision
/* 65 */         .getProfileSampleRate());
/*    */   }
/*    */   
/*    */   private static boolean isValidRate(@Nullable Double rate, boolean allowNull) {
/* 69 */     if (rate == null) {
/* 70 */       return allowNull;
/*    */     }
/* 72 */     return (!rate.isNaN() && rate.doubleValue() >= 0.0D && rate.doubleValue() <= 1.0D);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\SampleRateUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */