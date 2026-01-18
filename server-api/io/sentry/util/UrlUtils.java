/*     */ package io.sentry.util;
/*     */ 
/*     */ import io.sentry.ISpan;
/*     */ import io.sentry.protocol.Request;
/*     */ import java.net.URI;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class UrlUtils {
/*     */   @NotNull
/*     */   public static final String SENSITIVE_DATA_SUBSTITUTE = "[Filtered]";
/*     */   
/*     */   @Nullable
/*     */   public static UrlDetails parseNullable(@Nullable String url) {
/*  17 */     return (url == null) ? null : parse(url);
/*     */   }
/*     */   @NotNull
/*     */   public static UrlDetails parse(@NotNull String url) {
/*     */     try {
/*  22 */       URI uri = new URI(url);
/*  23 */       if (uri.isAbsolute() && !isValidAbsoluteUrl(uri)) {
/*  24 */         return new UrlDetails(null, null, null);
/*     */       }
/*     */ 
/*     */       
/*  28 */       String schemeAndSeparator = (uri.getScheme() == null) ? "" : (uri.getScheme() + "://");
/*  29 */       String authority = (uri.getRawAuthority() == null) ? "" : uri.getRawAuthority();
/*  30 */       String path = (uri.getRawPath() == null) ? "" : uri.getRawPath();
/*  31 */       String query = uri.getRawQuery();
/*  32 */       String fragment = uri.getRawFragment();
/*     */       
/*  34 */       String filteredUrl = schemeAndSeparator + filterUserInfo(authority) + path;
/*     */       
/*  36 */       return new UrlDetails(filteredUrl, query, fragment);
/*  37 */     } catch (Exception e) {
/*  38 */       return new UrlDetails(null, null, null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isValidAbsoluteUrl(@NotNull URI uri) {
/*     */     try {
/*  44 */       uri.toURL();
/*  45 */     } catch (Exception e) {
/*  46 */       return false;
/*     */     } 
/*  48 */     return true;
/*     */   }
/*     */   @NotNull
/*     */   private static String filterUserInfo(@NotNull String url) {
/*  52 */     if (!url.contains("@")) {
/*  53 */       return url;
/*     */     }
/*  55 */     if (url.startsWith("@")) {
/*  56 */       return "[Filtered]" + url;
/*     */     }
/*  58 */     String userInfo = url.substring(0, url.indexOf('@'));
/*     */ 
/*     */ 
/*     */     
/*  62 */     String filteredUserInfo = userInfo.contains(":") ? "[Filtered]:[Filtered]" : "[Filtered]";
/*  63 */     return filteredUserInfo + url.substring(url.indexOf('@'));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class UrlDetails
/*     */   {
/*     */     @Nullable
/*     */     private final String url;
/*     */     
/*     */     public UrlDetails(@Nullable String url, @Nullable String query, @Nullable String fragment) {
/*  73 */       this.url = url;
/*  74 */       this.query = query;
/*  75 */       this.fragment = fragment; } @Nullable
/*     */     private final String query; @Nullable
/*     */     private final String fragment; @Nullable
/*     */     public String getUrl() {
/*  79 */       return this.url;
/*     */     }
/*     */     @NotNull
/*     */     public String getUrlOrFallback() {
/*  83 */       if (this.url == null) {
/*  84 */         return "unknown";
/*     */       }
/*  86 */       return this.url;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public String getQuery() {
/*  91 */       return this.query;
/*     */     }
/*     */     @Nullable
/*     */     public String getFragment() {
/*  95 */       return this.fragment;
/*     */     }
/*     */     
/*     */     public void applyToRequest(@Nullable Request request) {
/*  99 */       if (request == null) {
/*     */         return;
/*     */       }
/*     */       
/* 103 */       request.setUrl(this.url);
/* 104 */       request.setQueryString(this.query);
/* 105 */       request.setFragment(this.fragment);
/*     */     }
/*     */     
/*     */     public void applyToSpan(@Nullable ISpan span) {
/* 109 */       if (span == null) {
/*     */         return;
/*     */       }
/*     */       
/* 113 */       if (this.query != null) {
/* 114 */         span.setData("http.query", this.query);
/*     */       }
/* 116 */       if (this.fragment != null)
/* 117 */         span.setData("http.fragment", this.fragment); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\UrlUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */