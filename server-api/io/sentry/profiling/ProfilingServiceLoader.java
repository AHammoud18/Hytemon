/*    */ package io.sentry.profiling;
/*    */ 
/*    */ import io.sentry.IContinuousProfiler;
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.IProfileConverter;
/*    */ import io.sentry.ISentryExecutorService;
/*    */ import io.sentry.NoOpContinuousProfiler;
/*    */ import io.sentry.NoOpProfileConverter;
/*    */ import io.sentry.ScopesAdapter;
/*    */ import io.sentry.SentryLevel;
/*    */ import java.util.Iterator;
/*    */ import java.util.ServiceLoader;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class ProfilingServiceLoader
/*    */ {
/*    */   @NotNull
/*    */   public static IContinuousProfiler loadContinuousProfiler(ILogger logger, String profilingTracesDirPath, int profilingTracesHz, ISentryExecutorService executorService) {
/*    */     try {
/* 27 */       JavaContinuousProfilerProvider provider = loadSingleProvider(JavaContinuousProfilerProvider.class);
/*    */       
/* 29 */       if (provider != null) {
/* 30 */         logger.log(SentryLevel.DEBUG, "Loaded continuous profiler from provider: %s", new Object[] { provider
/*    */ 
/*    */               
/* 33 */               .getClass().getName() });
/* 34 */         return provider.getContinuousProfiler(logger, profilingTracesDirPath, profilingTracesHz, executorService);
/*    */       } 
/*    */ 
/*    */       
/* 38 */       logger.log(SentryLevel.DEBUG, "No continuous profiler provider found, using NoOpContinuousProfiler", new Object[0]);
/*    */       
/* 40 */       return (IContinuousProfiler)NoOpContinuousProfiler.getInstance();
/* 41 */     } catch (Throwable t) {
/* 42 */       logger.log(SentryLevel.ERROR, "Failed to load continuous profiler provider, using NoOpContinuousProfiler", t);
/*    */ 
/*    */ 
/*    */       
/* 46 */       return (IContinuousProfiler)NoOpContinuousProfiler.getInstance();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static IProfileConverter loadProfileConverter() {
/* 56 */     ILogger logger = ScopesAdapter.getInstance().getGlobalScope().getOptions().getLogger();
/*    */     
/*    */     try {
/* 59 */       JavaProfileConverterProvider provider = loadSingleProvider(JavaProfileConverterProvider.class);
/* 60 */       if (provider != null) {
/* 61 */         logger.log(SentryLevel.DEBUG, "Loaded profile converter from provider: %s", new Object[] { provider
/*    */ 
/*    */               
/* 64 */               .getClass().getName() });
/* 65 */         return provider.getProfileConverter();
/*    */       } 
/* 67 */       logger.log(SentryLevel.DEBUG, "No profile converter provider found, using NoOpProfileConverter", new Object[0]);
/*    */       
/* 69 */       return (IProfileConverter)NoOpProfileConverter.getInstance();
/*    */     }
/* 71 */     catch (Throwable t) {
/* 72 */       logger.log(SentryLevel.ERROR, "Failed to load profile converter provider, using NoOpProfileConverter", t);
/*    */ 
/*    */ 
/*    */       
/* 76 */       return (IProfileConverter)NoOpProfileConverter.getInstance();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private static <T> T loadSingleProvider(Class<T> clazz) {
/* 82 */     ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
/* 83 */     Iterator<T> iterator = serviceLoader.iterator();
/*    */     
/* 85 */     if (iterator.hasNext()) {
/* 86 */       return iterator.next();
/*    */     }
/* 88 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\profiling\ProfilingServiceLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */