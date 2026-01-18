/*     */ package io.sentry.util.network;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.jetbrains.annotations.VisibleForTesting;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NetworkDetailCaptureUtils
/*     */ {
/*     */   @Nullable
/*     */   public static NetworkRequestData initializeForUrl(@NotNull String url, @Nullable String method, @Nullable List<String> networkDetailAllowUrls, @Nullable List<String> networkDetailDenyUrls) {
/*  39 */     if (!shouldCaptureUrl(url, networkDetailAllowUrls, networkDetailDenyUrls)) {
/*  40 */       return null;
/*     */     }
/*     */     
/*  43 */     return new NetworkRequestData(method);
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
/*     */   @NotNull
/*     */   public static <T> ReplayNetworkRequestOrResponse createRequest(@NotNull T httpObject, @Nullable Long bodySize, boolean networkCaptureBodies, @NotNull NetworkBodyExtractor<T> bodyExtractor, @NotNull List<String> networkRequestHeaders, @NotNull NetworkHeaderExtractor<T> headerExtractor) {
/*  58 */     return createRequestOrResponseInternal(httpObject, bodySize, networkCaptureBodies, bodyExtractor, networkRequestHeaders, headerExtractor);
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
/*     */   @NotNull
/*     */   public static <T> ReplayNetworkRequestOrResponse createResponse(@NotNull T httpObject, @Nullable Long bodySize, boolean networkCaptureBodies, @NotNull NetworkBodyExtractor<T> bodyExtractor, @NotNull List<String> networkResponseHeaders, @NotNull NetworkHeaderExtractor<T> headerExtractor) {
/*  75 */     return createRequestOrResponseInternal(httpObject, bodySize, networkCaptureBodies, bodyExtractor, networkResponseHeaders, headerExtractor);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean shouldCaptureUrl(@NotNull String url, @Nullable List<String> networkDetailAllowUrls, @Nullable List<String> networkDetailDenyUrls) {
/* 100 */     if (networkDetailDenyUrls != null) {
/* 101 */       for (String pattern : networkDetailDenyUrls) {
/* 102 */         if (pattern != null && url.matches(pattern)) {
/* 103 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 109 */     if (networkDetailAllowUrls == null) {
/* 110 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 114 */     for (String pattern : networkDetailAllowUrls) {
/* 115 */       if (pattern != null && url.matches(pattern)) {
/* 116 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 120 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   @NotNull
/*     */   static Map<String, String> getCaptureHeaders(@Nullable Map<String, String> allHeaders, @NotNull List<String> allowedHeaders) {
/* 127 */     Map<String, String> capturedHeaders = new LinkedHashMap<>();
/* 128 */     if (allHeaders == null) {
/* 129 */       return capturedHeaders;
/*     */     }
/*     */ 
/*     */     
/* 133 */     Set<String> normalizedAllowed = new HashSet<>();
/* 134 */     for (String header : allowedHeaders) {
/* 135 */       if (header != null) {
/* 136 */         normalizedAllowed.add(header.toLowerCase(Locale.ROOT));
/*     */       }
/*     */     } 
/*     */     
/* 140 */     for (Map.Entry<String, String> entry : allHeaders.entrySet()) {
/* 141 */       if (normalizedAllowed.contains(((String)entry.getKey()).toLowerCase(Locale.ROOT))) {
/* 142 */         capturedHeaders.put(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/*     */     
/* 146 */     return capturedHeaders;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static <T> ReplayNetworkRequestOrResponse createRequestOrResponseInternal(@NotNull T httpObject, @Nullable Long bodySize, boolean networkCaptureBodies, @NotNull NetworkBodyExtractor<T> bodyExtractor, @NotNull List<String> allowedHeaders, @NotNull NetworkHeaderExtractor<T> headerExtractor) {
/* 157 */     NetworkBody body = null;
/*     */     
/* 159 */     if (networkCaptureBodies) {
/* 160 */       body = bodyExtractor.extract(httpObject);
/*     */     }
/*     */ 
/*     */     
/* 164 */     Map<String, String> headers = getCaptureHeaders(headerExtractor.extract(httpObject), allowedHeaders);
/*     */     
/* 166 */     return new ReplayNetworkRequestOrResponse(bodySize, body, headers);
/*     */   }
/*     */   
/*     */   public static interface NetworkBodyExtractor<T> {
/*     */     @Nullable
/*     */     NetworkBody extract(@NotNull T param1T);
/*     */   }
/*     */   
/*     */   public static interface NetworkHeaderExtractor<T> {
/*     */     @NotNull
/*     */     Map<String, String> extract(@NotNull T param1T);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\network\NetworkDetailCaptureUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */