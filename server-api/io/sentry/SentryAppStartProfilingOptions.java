/*     */ package io.sentry;
/*     */ import io.sentry.util.SentryRandom;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class SentryAppStartProfilingOptions implements JsonUnknown, JsonSerializable {
/*     */   boolean profileSampled;
/*     */   @Nullable
/*     */   Double profileSampleRate;
/*     */   boolean traceSampled;
/*     */   @Nullable
/*     */   Double traceSampleRate;
/*     */   @Nullable
/*     */   String profilingTracesDirPath;
/*     */   boolean isProfilingEnabled;
/*     */   boolean isContinuousProfilingEnabled;
/*     */   int profilingTracesHz;
/*     */   boolean continuousProfileSampled;
/*     */   boolean isEnableAppStartProfiling;
/*     */   boolean isStartProfilerOnAppStart;
/*     */   @NotNull
/*     */   ProfileLifecycle profileLifecycle;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   @VisibleForTesting
/*     */   public SentryAppStartProfilingOptions() {
/*  33 */     this.traceSampled = false;
/*  34 */     this.traceSampleRate = null;
/*  35 */     this.profileSampled = false;
/*  36 */     this.profileSampleRate = null;
/*  37 */     this.continuousProfileSampled = false;
/*  38 */     this.profilingTracesDirPath = null;
/*  39 */     this.isProfilingEnabled = false;
/*  40 */     this.isContinuousProfilingEnabled = false;
/*  41 */     this.profileLifecycle = ProfileLifecycle.MANUAL;
/*  42 */     this.profilingTracesHz = 0;
/*  43 */     this.isEnableAppStartProfiling = true;
/*  44 */     this.isStartProfilerOnAppStart = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SentryAppStartProfilingOptions(@NotNull SentryOptions options, @NotNull TracesSamplingDecision samplingDecision) {
/*  50 */     this.traceSampled = samplingDecision.getSampled().booleanValue();
/*  51 */     this.traceSampleRate = samplingDecision.getSampleRate();
/*  52 */     this.profileSampled = samplingDecision.getProfileSampled().booleanValue();
/*  53 */     this.profileSampleRate = samplingDecision.getProfileSampleRate();
/*  54 */     this
/*     */ 
/*     */       
/*  57 */       .continuousProfileSampled = options.getInternalTracesSampler().sampleSessionProfile(SentryRandom.current().nextDouble());
/*  58 */     this.profilingTracesDirPath = options.getProfilingTracesDirPath();
/*  59 */     this.isProfilingEnabled = options.isProfilingEnabled();
/*  60 */     this.isContinuousProfilingEnabled = options.isContinuousProfilingEnabled();
/*  61 */     this.profileLifecycle = options.getProfileLifecycle();
/*  62 */     this.profilingTracesHz = options.getProfilingTracesHz();
/*  63 */     this.isEnableAppStartProfiling = options.isEnableAppStartProfiling();
/*  64 */     this.isStartProfilerOnAppStart = options.isStartProfilerOnAppStart();
/*     */   }
/*     */   
/*     */   public void setProfileSampled(boolean profileSampled) {
/*  68 */     this.profileSampled = profileSampled;
/*     */   }
/*     */   
/*     */   public boolean isProfileSampled() {
/*  72 */     return this.profileSampled;
/*     */   }
/*     */   
/*     */   public void setContinuousProfileSampled(boolean continuousProfileSampled) {
/*  76 */     this.continuousProfileSampled = continuousProfileSampled;
/*     */   }
/*     */   
/*     */   public boolean isContinuousProfileSampled() {
/*  80 */     return this.continuousProfileSampled;
/*     */   }
/*     */   
/*     */   public void setProfileLifecycle(@NotNull ProfileLifecycle profileLifecycle) {
/*  84 */     this.profileLifecycle = profileLifecycle;
/*     */   }
/*     */   @NotNull
/*     */   public ProfileLifecycle getProfileLifecycle() {
/*  88 */     return this.profileLifecycle;
/*     */   }
/*     */   
/*     */   public void setProfileSampleRate(@Nullable Double profileSampleRate) {
/*  92 */     this.profileSampleRate = profileSampleRate;
/*     */   }
/*     */   @Nullable
/*     */   public Double getProfileSampleRate() {
/*  96 */     return this.profileSampleRate;
/*     */   }
/*     */   
/*     */   public void setTraceSampled(boolean traceSampled) {
/* 100 */     this.traceSampled = traceSampled;
/*     */   }
/*     */   
/*     */   public boolean isTraceSampled() {
/* 104 */     return this.traceSampled;
/*     */   }
/*     */   
/*     */   public void setTraceSampleRate(@Nullable Double traceSampleRate) {
/* 108 */     this.traceSampleRate = traceSampleRate;
/*     */   }
/*     */   @Nullable
/*     */   public Double getTraceSampleRate() {
/* 112 */     return this.traceSampleRate;
/*     */   }
/*     */   
/*     */   public void setProfilingTracesDirPath(@Nullable String profilingTracesDirPath) {
/* 116 */     this.profilingTracesDirPath = profilingTracesDirPath;
/*     */   }
/*     */   @Nullable
/*     */   public String getProfilingTracesDirPath() {
/* 120 */     return this.profilingTracesDirPath;
/*     */   }
/*     */   
/*     */   public void setProfilingEnabled(boolean profilingEnabled) {
/* 124 */     this.isProfilingEnabled = profilingEnabled;
/*     */   }
/*     */   
/*     */   public boolean isProfilingEnabled() {
/* 128 */     return this.isProfilingEnabled;
/*     */   }
/*     */   
/*     */   public void setContinuousProfilingEnabled(boolean continuousProfilingEnabled) {
/* 132 */     this.isContinuousProfilingEnabled = continuousProfilingEnabled;
/*     */   }
/*     */   
/*     */   public boolean isContinuousProfilingEnabled() {
/* 136 */     return this.isContinuousProfilingEnabled;
/*     */   }
/*     */   
/*     */   public void setProfilingTracesHz(int profilingTracesHz) {
/* 140 */     this.profilingTracesHz = profilingTracesHz;
/*     */   }
/*     */   
/*     */   public int getProfilingTracesHz() {
/* 144 */     return this.profilingTracesHz;
/*     */   }
/*     */   
/*     */   public void setEnableAppStartProfiling(boolean enableAppStartProfiling) {
/* 148 */     this.isEnableAppStartProfiling = enableAppStartProfiling;
/*     */   }
/*     */   
/*     */   public boolean isEnableAppStartProfiling() {
/* 152 */     return this.isEnableAppStartProfiling;
/*     */   }
/*     */   
/*     */   public void setStartProfilerOnAppStart(boolean startProfilerOnAppStart) {
/* 156 */     this.isStartProfilerOnAppStart = startProfilerOnAppStart;
/*     */   }
/*     */   
/*     */   public boolean isStartProfilerOnAppStart() {
/* 160 */     return this.isStartProfilerOnAppStart;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String PROFILE_SAMPLED = "profile_sampled";
/*     */     
/*     */     public static final String PROFILE_SAMPLE_RATE = "profile_sample_rate";
/*     */     
/*     */     public static final String CONTINUOUS_PROFILE_SAMPLED = "continuous_profile_sampled";
/*     */     public static final String TRACE_SAMPLED = "trace_sampled";
/*     */     public static final String TRACE_SAMPLE_RATE = "trace_sample_rate";
/*     */     public static final String PROFILING_TRACES_DIR_PATH = "profiling_traces_dir_path";
/*     */     public static final String IS_PROFILING_ENABLED = "is_profiling_enabled";
/*     */     public static final String IS_CONTINUOUS_PROFILING_ENABLED = "is_continuous_profiling_enabled";
/*     */     public static final String PROFILE_LIFECYCLE = "profile_lifecycle";
/*     */     public static final String PROFILING_TRACES_HZ = "profiling_traces_hz";
/*     */     public static final String IS_ENABLE_APP_START_PROFILING = "is_enable_app_start_profiling";
/*     */     public static final String IS_START_PROFILER_ON_APP_START = "is_start_profiler_on_app_start";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 183 */     writer.beginObject();
/* 184 */     writer.name("profile_sampled").value(logger, Boolean.valueOf(this.profileSampled));
/* 185 */     writer.name("profile_sample_rate").value(logger, this.profileSampleRate);
/* 186 */     writer.name("continuous_profile_sampled").value(logger, Boolean.valueOf(this.continuousProfileSampled));
/* 187 */     writer.name("trace_sampled").value(logger, Boolean.valueOf(this.traceSampled));
/* 188 */     writer.name("trace_sample_rate").value(logger, this.traceSampleRate);
/* 189 */     writer.name("profiling_traces_dir_path").value(logger, this.profilingTracesDirPath);
/* 190 */     writer.name("is_profiling_enabled").value(logger, Boolean.valueOf(this.isProfilingEnabled));
/* 191 */     writer
/* 192 */       .name("is_continuous_profiling_enabled")
/* 193 */       .value(logger, Boolean.valueOf(this.isContinuousProfilingEnabled));
/* 194 */     writer.name("profile_lifecycle").value(logger, this.profileLifecycle.name());
/* 195 */     writer.name("profiling_traces_hz").value(logger, Integer.valueOf(this.profilingTracesHz));
/* 196 */     writer.name("is_enable_app_start_profiling").value(logger, Boolean.valueOf(this.isEnableAppStartProfiling));
/* 197 */     writer.name("is_start_profiler_on_app_start").value(logger, Boolean.valueOf(this.isStartProfilerOnAppStart));
/*     */     
/* 199 */     if (this.unknown != null) {
/* 200 */       for (String key : this.unknown.keySet()) {
/* 201 */         Object value = this.unknown.get(key);
/* 202 */         writer.name(key);
/* 203 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 206 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 212 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 217 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryAppStartProfilingOptions>
/*     */   {
/*     */     @NotNull
/*     */     public SentryAppStartProfilingOptions deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 226 */       reader.beginObject();
/* 227 */       SentryAppStartProfilingOptions options = new SentryAppStartProfilingOptions();
/* 228 */       Map<String, Object> unknown = null;
/*     */       
/* 230 */       while (reader.peek() == JsonToken.NAME) {
/* 231 */         Boolean profileSampled; Double profileSampleRate; Boolean continuousProfileSampled, traceSampled; Double traceSampleRate; String profilingTracesDirPath; Boolean isProfilingEnabled, isContinuousProfilingEnabled; String profileLifecycle; Integer profilingTracesHz; Boolean isEnableAppStartProfiling, isStartProfilerOnAppStart; String nextName = reader.nextName();
/* 232 */         switch (nextName) {
/*     */           case "profile_sampled":
/* 234 */             profileSampled = reader.nextBooleanOrNull();
/* 235 */             if (profileSampled != null) {
/* 236 */               options.profileSampled = profileSampled.booleanValue();
/*     */             }
/*     */             continue;
/*     */           case "profile_sample_rate":
/* 240 */             profileSampleRate = reader.nextDoubleOrNull();
/* 241 */             if (profileSampleRate != null) {
/* 242 */               options.profileSampleRate = profileSampleRate;
/*     */             }
/*     */             continue;
/*     */           case "continuous_profile_sampled":
/* 246 */             continuousProfileSampled = reader.nextBooleanOrNull();
/* 247 */             if (continuousProfileSampled != null) {
/* 248 */               options.continuousProfileSampled = continuousProfileSampled.booleanValue();
/*     */             }
/*     */             continue;
/*     */           case "trace_sampled":
/* 252 */             traceSampled = reader.nextBooleanOrNull();
/* 253 */             if (traceSampled != null) {
/* 254 */               options.traceSampled = traceSampled.booleanValue();
/*     */             }
/*     */             continue;
/*     */           case "trace_sample_rate":
/* 258 */             traceSampleRate = reader.nextDoubleOrNull();
/* 259 */             if (traceSampleRate != null) {
/* 260 */               options.traceSampleRate = traceSampleRate;
/*     */             }
/*     */             continue;
/*     */           case "profiling_traces_dir_path":
/* 264 */             profilingTracesDirPath = reader.nextStringOrNull();
/* 265 */             if (profilingTracesDirPath != null) {
/* 266 */               options.profilingTracesDirPath = profilingTracesDirPath;
/*     */             }
/*     */             continue;
/*     */           case "is_profiling_enabled":
/* 270 */             isProfilingEnabled = reader.nextBooleanOrNull();
/* 271 */             if (isProfilingEnabled != null) {
/* 272 */               options.isProfilingEnabled = isProfilingEnabled.booleanValue();
/*     */             }
/*     */             continue;
/*     */           case "is_continuous_profiling_enabled":
/* 276 */             isContinuousProfilingEnabled = reader.nextBooleanOrNull();
/* 277 */             if (isContinuousProfilingEnabled != null) {
/* 278 */               options.isContinuousProfilingEnabled = isContinuousProfilingEnabled.booleanValue();
/*     */             }
/*     */             continue;
/*     */           case "profile_lifecycle":
/* 282 */             profileLifecycle = reader.nextStringOrNull();
/* 283 */             if (profileLifecycle != null) {
/*     */               try {
/* 285 */                 options.profileLifecycle = ProfileLifecycle.valueOf(profileLifecycle);
/* 286 */               } catch (IllegalArgumentException e) {
/* 287 */                 logger.log(SentryLevel.ERROR, "Error when deserializing ProfileLifecycle: " + profileLifecycle, new Object[0]);
/*     */               } 
/*     */             }
/*     */             continue;
/*     */ 
/*     */           
/*     */           case "profiling_traces_hz":
/* 294 */             profilingTracesHz = reader.nextIntegerOrNull();
/* 295 */             if (profilingTracesHz != null) {
/* 296 */               options.profilingTracesHz = profilingTracesHz.intValue();
/*     */             }
/*     */             continue;
/*     */           case "is_enable_app_start_profiling":
/* 300 */             isEnableAppStartProfiling = reader.nextBooleanOrNull();
/* 301 */             if (isEnableAppStartProfiling != null) {
/* 302 */               options.isEnableAppStartProfiling = isEnableAppStartProfiling.booleanValue();
/*     */             }
/*     */             continue;
/*     */           case "is_start_profiler_on_app_start":
/* 306 */             isStartProfilerOnAppStart = reader.nextBooleanOrNull();
/* 307 */             if (isStartProfilerOnAppStart != null) {
/* 308 */               options.isStartProfilerOnAppStart = isStartProfilerOnAppStart.booleanValue();
/*     */             }
/*     */             continue;
/*     */         } 
/* 312 */         if (unknown == null) {
/* 313 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 315 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 319 */       options.setUnknown(unknown);
/* 320 */       reader.endObject();
/* 321 */       return options;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryAppStartProfilingOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */