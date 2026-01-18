/*     */ package io.sentry.util;
/*     */ 
/*     */ import io.sentry.IContinuousProfiler;
/*     */ import io.sentry.IProfileConverter;
/*     */ import io.sentry.IVersionDetector;
/*     */ import io.sentry.ManifestVersionDetector;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.profiling.ProfilingServiceLoader;
/*     */ import java.io.File;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class InitUtil
/*     */ {
/*     */   public static boolean shouldInit(@Nullable SentryOptions previousOptions, @NotNull SentryOptions newOptions, boolean isEnabled) {
/*  23 */     if (Platform.isJvm() && newOptions.getVersionDetector() instanceof io.sentry.NoopVersionDetector) {
/*  24 */       newOptions.setVersionDetector((IVersionDetector)new ManifestVersionDetector(newOptions));
/*     */     }
/*  26 */     if (newOptions.getVersionDetector().checkForMixedVersions()) {
/*  27 */       newOptions
/*  28 */         .getLogger()
/*  29 */         .log(SentryLevel.ERROR, "Not initializing Sentry because mixed SDK versions have been detected.", new Object[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  35 */       String docsUrl = Platform.isAndroid() ? "https://docs.sentry.io/platforms/android/troubleshooting/mixed-versions" : "https://docs.sentry.io/platforms/java/troubleshooting/mixed-versions";
/*  36 */       throw new IllegalStateException("Sentry SDK has detected a mix of versions. This is not supported and likely leads to crashes. Please always use the same version of all SDK modules (dependencies). See " + docsUrl + " for more details.");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  41 */     if (!isEnabled) {
/*  42 */       return true;
/*     */     }
/*     */     
/*  45 */     if (previousOptions == null) {
/*  46 */       return true;
/*     */     }
/*     */     
/*  49 */     if (newOptions.isForceInit()) {
/*  50 */       return true;
/*     */     }
/*     */     
/*  53 */     return (previousOptions.getInitPriority().ordinal() <= newOptions.getInitPriority().ordinal());
/*     */   }
/*     */   
/*     */   public static IContinuousProfiler initializeProfiler(@NotNull SentryOptions options) {
/*  57 */     if (!shouldInitializeProfiler(options)) {
/*  58 */       return options.getContinuousProfiler();
/*     */     }
/*     */     
/*     */     try {
/*  62 */       String profilingTracesDirPath = getOrCreateProfilingTracesDir(options);
/*     */       
/*  64 */       IContinuousProfiler profiler = ProfilingServiceLoader.loadContinuousProfiler(options
/*  65 */           .getLogger(), profilingTracesDirPath, options
/*     */           
/*  67 */           .getProfilingTracesHz(), options
/*  68 */           .getExecutorService());
/*     */       
/*  70 */       if (profiler instanceof io.sentry.NoOpContinuousProfiler) {
/*  71 */         options
/*  72 */           .getLogger()
/*  73 */           .log(SentryLevel.WARNING, "Could not load profiler, profiling will be disabled. If you are using Spring or Spring Boot with the OTEL Agent profiler init will be retried.", new Object[0]);
/*     */       }
/*     */       else {
/*     */         
/*  77 */         options.setContinuousProfiler(profiler);
/*  78 */         options.getLogger().log(SentryLevel.INFO, "Successfully loaded profiler", new Object[0]);
/*     */       } 
/*  80 */     } catch (Exception e) {
/*  81 */       options
/*  82 */         .getLogger()
/*  83 */         .log(SentryLevel.ERROR, "Failed to create default profiling traces directory", e);
/*     */     } 
/*     */     
/*  86 */     return options.getContinuousProfiler();
/*     */   }
/*     */   
/*     */   public static IProfileConverter initializeProfileConverter(@NotNull SentryOptions options) {
/*  90 */     if (!shouldInitializeProfileConverter(options)) {
/*  91 */       return options.getProfilerConverter();
/*     */     }
/*     */     
/*  94 */     IProfileConverter converter = ProfilingServiceLoader.loadProfileConverter();
/*     */     
/*  96 */     if (converter instanceof io.sentry.NoOpProfileConverter) {
/*  97 */       options
/*  98 */         .getLogger()
/*  99 */         .log(SentryLevel.WARNING, "Could not load profile converter. If you are using Spring or Spring Boot with the OTEL Agent, profile converter init will be retried.", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 103 */       options.setProfilerConverter(converter);
/* 104 */       options.getLogger().log(SentryLevel.INFO, "Successfully loaded profile converter", new Object[0]);
/*     */     } 
/*     */     
/* 107 */     return options.getProfilerConverter();
/*     */   }
/*     */   
/*     */   private static boolean shouldInitializeProfiler(@NotNull SentryOptions options) {
/* 111 */     return (Platform.isJvm() && options
/* 112 */       .isContinuousProfilingEnabled() && options
/* 113 */       .getContinuousProfiler() instanceof io.sentry.NoOpContinuousProfiler);
/*     */   }
/*     */   
/*     */   private static boolean shouldInitializeProfileConverter(@NotNull SentryOptions options) {
/* 117 */     return (Platform.isJvm() && options
/* 118 */       .isContinuousProfilingEnabled() && options
/* 119 */       .getProfilerConverter() instanceof io.sentry.NoOpProfileConverter);
/*     */   }
/*     */   
/*     */   private static String getOrCreateProfilingTracesDir(@NotNull SentryOptions options) {
/* 123 */     String profilingTracesDirPath = options.getProfilingTracesDirPath();
/* 124 */     if (profilingTracesDirPath != null) {
/* 125 */       return profilingTracesDirPath;
/*     */     }
/*     */     
/* 128 */     File tempDir = new File(System.getProperty("java.io.tmpdir"), "sentry_profiling_traces");
/* 129 */     if (!tempDir.mkdirs() && !tempDir.exists()) {
/* 130 */       throw new IllegalArgumentException("Creating a fallback directory for profiling failed in " + tempDir
/* 131 */           .getAbsolutePath());
/*     */     }
/*     */     
/* 134 */     profilingTracesDirPath = tempDir.getAbsolutePath();
/* 135 */     options.setProfilingTracesDirPath(profilingTracesDirPath);
/* 136 */     return profilingTracesDirPath;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\InitUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */