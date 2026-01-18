/*    */ package io.sentry.util;
/*    */ 
/*    */ import io.sentry.FilterString;
/*    */ import io.sentry.SentryOpenTelemetryMode;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SpanUtils
/*    */ {
/*    */   @NotNull
/*    */   public static List<String> ignoredSpanOriginsForOpenTelemetry(@NotNull SentryOpenTelemetryMode mode) {
/* 22 */     List<String> origins = new ArrayList<>();
/*    */     
/* 24 */     if (SentryOpenTelemetryMode.AGENT == mode || SentryOpenTelemetryMode.AGENTLESS_SPRING == mode) {
/* 25 */       origins.add("auto.http.spring_jakarta.webmvc");
/* 26 */       origins.add("auto.http.spring.webmvc");
/* 27 */       origins.add("auto.http.spring7.webmvc");
/* 28 */       origins.add("auto.spring_jakarta.webflux");
/* 29 */       origins.add("auto.spring.webflux");
/* 30 */       origins.add("auto.spring7.webflux");
/* 31 */       origins.add("auto.db.jdbc");
/* 32 */       origins.add("auto.http.spring_jakarta.webclient");
/* 33 */       origins.add("auto.http.spring.webclient");
/* 34 */       origins.add("auto.http.spring7.webclient");
/* 35 */       origins.add("auto.http.spring_jakarta.restclient");
/* 36 */       origins.add("auto.http.spring.restclient");
/* 37 */       origins.add("auto.http.spring7.restclient");
/* 38 */       origins.add("auto.http.spring_jakarta.resttemplate");
/* 39 */       origins.add("auto.http.spring.resttemplate");
/* 40 */       origins.add("auto.http.spring7.resttemplate");
/* 41 */       origins.add("auto.http.openfeign");
/* 42 */       origins.add("auto.http.ktor-client");
/*    */     } 
/*    */     
/* 45 */     if (SentryOpenTelemetryMode.AGENT == mode) {
/* 46 */       origins.add("auto.graphql.graphql");
/* 47 */       origins.add("auto.graphql.graphql22");
/*    */     } 
/*    */     
/* 50 */     return origins;
/*    */   }
/*    */   
/* 53 */   private static final Map<String, Boolean> ignoredSpanDecisionsCache = new ConcurrentHashMap<>();
/*    */ 
/*    */ 
/*    */   
/*    */   @Internal
/*    */   public static boolean isIgnored(@Nullable List<FilterString> ignoredOrigins, @Nullable String origin) {
/* 59 */     if (origin == null || ignoredOrigins == null || ignoredOrigins.isEmpty()) {
/* 60 */       return false;
/*    */     }
/*    */     
/* 63 */     if (ignoredSpanDecisionsCache.containsKey(origin)) {
/* 64 */       return ((Boolean)ignoredSpanDecisionsCache.get(origin)).booleanValue();
/*    */     }
/*    */     
/* 67 */     for (FilterString ignoredOrigin : ignoredOrigins) {
/* 68 */       if (ignoredOrigin.getFilterString().equalsIgnoreCase(origin)) {
/* 69 */         ignoredSpanDecisionsCache.put(origin, Boolean.valueOf(true));
/* 70 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 74 */     for (FilterString ignoredOrigin : ignoredOrigins) {
/*    */       try {
/* 76 */         if (ignoredOrigin.matches(origin)) {
/* 77 */           ignoredSpanDecisionsCache.put(origin, Boolean.valueOf(true));
/* 78 */           return true;
/*    */         } 
/* 80 */       } catch (Throwable throwable) {}
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 85 */     ignoredSpanDecisionsCache.put(origin, Boolean.valueOf(false));
/* 86 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\SpanUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */