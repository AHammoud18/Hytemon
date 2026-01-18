/*     */ package io.sentry.util;
/*     */ 
/*     */ import io.sentry.CheckIn;
/*     */ import io.sentry.CheckInStatus;
/*     */ import io.sentry.DateUtils;
/*     */ import io.sentry.FilterString;
/*     */ import io.sentry.IScopes;
/*     */ import io.sentry.ISentryLifecycleToken;
/*     */ import io.sentry.MonitorConfig;
/*     */ import io.sentry.Sentry;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.jetbrains.annotations.ApiStatus.Experimental;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Experimental
/*     */ public final class CheckInUtils
/*     */ {
/*     */   public static <U> U withCheckIn(@NotNull String monitorSlug, @Nullable String environment, @Nullable MonitorConfig monitorConfig, @NotNull Callable<U> callable) throws Exception {
/*  38 */     ISentryLifecycleToken ignored = Sentry.forkedScopes("CheckInUtils").makeCurrent(); try {
/*  39 */       IScopes scopes = Sentry.getCurrentScopes();
/*  40 */       long startTime = System.currentTimeMillis();
/*  41 */       boolean didError = false;
/*     */       
/*  43 */       TracingUtils.startNewTrace(scopes);
/*     */       
/*  45 */       CheckIn inProgressCheckIn = new CheckIn(monitorSlug, CheckInStatus.IN_PROGRESS);
/*  46 */       if (monitorConfig != null) {
/*  47 */         inProgressCheckIn.setMonitorConfig(monitorConfig);
/*     */       }
/*  49 */       if (environment != null) {
/*  50 */         inProgressCheckIn.setEnvironment(environment);
/*     */       }
/*  52 */       SentryId checkInId = scopes.captureCheckIn(inProgressCheckIn);
/*     */       
/*  54 */       try { U u = callable.call();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  59 */         CheckInStatus status = didError ? CheckInStatus.ERROR : CheckInStatus.OK; return u; } catch (Throwable t) { didError = true; throw t; } finally { CheckInStatus status = didError ? CheckInStatus.ERROR : CheckInStatus.OK;
/*  60 */         CheckIn checkIn = new CheckIn(checkInId, monitorSlug, status);
/*  61 */         if (environment != null) {
/*  62 */           checkIn.setEnvironment(environment);
/*     */         }
/*  64 */         checkIn.setDuration(Double.valueOf(DateUtils.millisToSeconds((System.currentTimeMillis() - startTime))));
/*  65 */         scopes.captureCheckIn(checkIn); }
/*     */     
/*     */     } catch (Throwable throwable) {
/*     */       if (ignored != null) {
/*     */         try {
/*     */           ignored.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         } 
/*     */       }
/*     */       throw throwable;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <U> U withCheckIn(@NotNull String monitorSlug, @Nullable MonitorConfig monitorConfig, @NotNull Callable<U> callable) throws Exception {
/*  84 */     return withCheckIn(monitorSlug, null, monitorConfig, callable);
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
/*     */   public static <U> U withCheckIn(@NotNull String monitorSlug, @Nullable String environment, @NotNull Callable<U> callable) throws Exception {
/* 101 */     return withCheckIn(monitorSlug, environment, null, callable);
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
/*     */   public static <U> U withCheckIn(@NotNull String monitorSlug, @NotNull Callable<U> callable) throws Exception {
/* 114 */     return withCheckIn(monitorSlug, null, null, callable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public static boolean isIgnored(@Nullable List<FilterString> ignoredSlugs, @NotNull String slug) {
/* 121 */     if (ignoredSlugs == null || ignoredSlugs.isEmpty()) {
/* 122 */       return false;
/*     */     }
/*     */     
/* 125 */     for (FilterString ignoredSlug : ignoredSlugs) {
/* 126 */       if (ignoredSlug.getFilterString().equalsIgnoreCase(slug)) {
/* 127 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 131 */     for (FilterString ignoredSlug : ignoredSlugs) {
/*     */       try {
/* 133 */         if (ignoredSlug.matches(slug)) {
/* 134 */           return true;
/*     */         }
/* 136 */       } catch (Throwable throwable) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 141 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\CheckInUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */