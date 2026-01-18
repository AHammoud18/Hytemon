/*    */ package io.sentry.opentelemetry;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.NoOpLogger;
/*    */ import io.sentry.SentryLevel;
/*    */ import io.sentry.SentryOpenTelemetryMode;
/*    */ import io.sentry.SentryOptions;
/*    */ import io.sentry.util.LoadClass;
/*    */ import io.sentry.util.Platform;
/*    */ import io.sentry.util.SpanUtils;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public final class OpenTelemetryUtil {
/*    */   @Internal
/*    */   public static void applyIgnoredSpanOrigins(@NotNull SentryOptions options) {
/* 20 */     if (Platform.isJvm()) {
/* 21 */       List<String> ignored = ignoredSpanOrigins(options);
/* 22 */       for (String origin : ignored) {
/* 23 */         options.addIgnoredSpanOrigin(origin);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Internal
/*    */   public static void updateOpenTelemetryModeIfAuto(@NotNull SentryOptions options, @NotNull LoadClass loadClass) {
/* 31 */     if (!Platform.isJvm()) {
/*    */       return;
/*    */     }
/*    */     
/* 35 */     SentryOpenTelemetryMode openTelemetryMode = options.getOpenTelemetryMode();
/* 36 */     if (SentryOpenTelemetryMode.AUTO.equals(openTelemetryMode)) {
/* 37 */       if (loadClass.isClassAvailable("io.sentry.opentelemetry.agent.AgentMarker", 
/* 38 */           (ILogger)NoOpLogger.getInstance())) {
/* 39 */         options
/* 40 */           .getLogger()
/* 41 */           .log(SentryLevel.DEBUG, "openTelemetryMode has been inferred from AUTO to AGENT", new Object[0]);
/* 42 */         options.setOpenTelemetryMode(SentryOpenTelemetryMode.AGENT);
/*    */         return;
/*    */       } 
/* 45 */       if (loadClass.isClassAvailable("io.sentry.opentelemetry.agent.AgentlessMarker", 
/* 46 */           (ILogger)NoOpLogger.getInstance())) {
/* 47 */         options
/* 48 */           .getLogger()
/* 49 */           .log(SentryLevel.DEBUG, "openTelemetryMode has been inferred from AUTO to AGENTLESS", new Object[0]);
/* 50 */         options.setOpenTelemetryMode(SentryOpenTelemetryMode.AGENTLESS);
/*    */         return;
/*    */       } 
/* 53 */       if (loadClass.isClassAvailable("io.sentry.opentelemetry.agent.AgentlessSpringMarker", 
/* 54 */           (ILogger)NoOpLogger.getInstance())) {
/* 55 */         options
/* 56 */           .getLogger()
/* 57 */           .log(SentryLevel.DEBUG, "openTelemetryMode has been inferred from AUTO to AGENTLESS_SPRING", new Object[0]);
/*    */ 
/*    */         
/* 60 */         options.setOpenTelemetryMode(SentryOpenTelemetryMode.AGENTLESS_SPRING);
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   @NotNull
/*    */   private static List<String> ignoredSpanOrigins(@NotNull SentryOptions options) {
/* 67 */     SentryOpenTelemetryMode openTelemetryMode = options.getOpenTelemetryMode();
/*    */     
/* 69 */     if (SentryOpenTelemetryMode.OFF.equals(openTelemetryMode)) {
/* 70 */       return Collections.emptyList();
/*    */     }
/*    */     
/* 73 */     return SpanUtils.ignoredSpanOriginsForOpenTelemetry(openTelemetryMode);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\opentelemetry\OpenTelemetryUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */