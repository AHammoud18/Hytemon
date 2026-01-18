/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ public final class TracesSamplingDecision
/*    */ {
/*    */   @NotNull
/*    */   private final Boolean sampled;
/*    */   @Nullable
/*    */   private final Double sampleRate;
/*    */   
/*    */   public TracesSamplingDecision(@NotNull Boolean sampled) {
/* 15 */     this(sampled, null); } @Nullable
/*    */   private final Double sampleRand; @NotNull
/*    */   private final Boolean profileSampled; @Nullable
/*    */   private final Double profileSampleRate; public TracesSamplingDecision(@NotNull Boolean sampled, @Nullable Double sampleRate) {
/* 19 */     this(sampled, sampleRate, null, Boolean.valueOf(false), null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TracesSamplingDecision(@NotNull Boolean sampled, @Nullable Double sampleRate, @Nullable Double sampleRand) {
/* 26 */     this(sampled, sampleRate, sampleRand, Boolean.valueOf(false), null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TracesSamplingDecision(@NotNull Boolean sampled, @Nullable Double sampleRate, @NotNull Boolean profileSampled, @Nullable Double profileSampleRate) {
/* 34 */     this(sampled, sampleRate, null, profileSampled, profileSampleRate);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TracesSamplingDecision(@NotNull Boolean sampled, @Nullable Double sampleRate, @Nullable Double sampleRand, @NotNull Boolean profileSampled, @Nullable Double profileSampleRate) {
/* 43 */     this.sampled = sampled;
/* 44 */     this.sampleRate = sampleRate;
/* 45 */     this.sampleRand = sampleRand;
/*    */     
/* 47 */     this.profileSampled = Boolean.valueOf((sampled.booleanValue() && profileSampled.booleanValue()));
/* 48 */     this.profileSampleRate = profileSampleRate;
/*    */   }
/*    */   @NotNull
/*    */   public Boolean getSampled() {
/* 52 */     return this.sampled;
/*    */   }
/*    */   @Nullable
/*    */   public Double getSampleRate() {
/* 56 */     return this.sampleRate;
/*    */   }
/*    */   @Nullable
/*    */   public Double getSampleRand() {
/* 60 */     return this.sampleRand;
/*    */   }
/*    */   @NotNull
/*    */   public Boolean getProfileSampled() {
/* 64 */     return this.profileSampled;
/*    */   }
/*    */   @Nullable
/*    */   public Double getProfileSampleRate() {
/* 68 */     return this.profileSampleRate;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\TracesSamplingDecision.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */