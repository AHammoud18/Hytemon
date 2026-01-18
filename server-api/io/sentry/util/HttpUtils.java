/*     */ package io.sentry.util;
/*     */ 
/*     */ import io.sentry.HttpStatusCodeRange;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class HttpUtils
/*     */ {
/*     */   public static final String COOKIE_HEADER_NAME = "Cookie";
/*  22 */   private static final List<String> SENSITIVE_HEADERS = Arrays.asList(new String[] { 
/*     */         "X-FORWARDED-FOR", "AUTHORIZATION", "COOKIE", "SET-COOKIE", "X-API-KEY", "X-REAL-IP", "REMOTE-ADDR", "FORWARDED", "PROXY-AUTHORIZATION", "X-CSRF-TOKEN", 
/*     */         "X-CSRFTOKEN", "X-XSRF-TOKEN" });
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
/*  37 */   private static final List<String> SECURITY_COOKIES = Arrays.asList(new String[] { "JSESSIONID", "JSESSIONIDSSO", "JSSOSESSIONID", "SESSIONID", "SID", "CSRFTOKEN", "XSRF-TOKEN" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private static final HttpStatusCodeRange CLIENT_ERROR_STATUS_CODES = new HttpStatusCodeRange(400, 499);
/*     */ 
/*     */   
/*  49 */   private static final HttpStatusCodeRange SEVER_ERROR_STATUS_CODES = new HttpStatusCodeRange(500, 599);
/*     */ 
/*     */   
/*     */   public static boolean containsSensitiveHeader(@NotNull String header) {
/*  53 */     return SENSITIVE_HEADERS.contains(header.toUpperCase(Locale.ROOT));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static List<String> filterOutSecurityCookiesFromHeader(@Nullable Enumeration<String> headers, @Nullable String headerName, @Nullable List<String> additionalCookieNamesToFilter) {
/*  60 */     if (headers == null) {
/*  61 */       return null;
/*     */     }
/*     */     
/*  64 */     return filterOutSecurityCookiesFromHeader(
/*  65 */         Collections.list(headers), headerName, additionalCookieNamesToFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static List<String> filterOutSecurityCookiesFromHeader(@Nullable List<String> headers, @Nullable String headerName, @Nullable List<String> additionalCookieNamesToFilter) {
/*  72 */     if (headers == null) {
/*  73 */       return null;
/*     */     }
/*     */     
/*  76 */     if (headerName != null && !"Cookie".equalsIgnoreCase(headerName)) {
/*  77 */       return headers;
/*     */     }
/*     */     
/*  80 */     ArrayList<String> filteredHeaders = new ArrayList<>();
/*     */     
/*  82 */     for (String header : headers) {
/*  83 */       filteredHeaders.add(
/*  84 */           filterOutSecurityCookies(header, additionalCookieNamesToFilter));
/*     */     }
/*     */     
/*  87 */     return filteredHeaders;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static String filterOutSecurityCookies(@Nullable String cookieString, @Nullable List<String> additionalCookieNamesToFilter) {
/*  93 */     if (cookieString == null) {
/*  94 */       return null;
/*     */     }
/*     */     try {
/*  97 */       String[] cookies = cookieString.split(";", -1);
/*  98 */       StringBuilder filteredCookieString = new StringBuilder();
/*  99 */       boolean isFirst = true;
/*     */       
/* 101 */       for (String cookie : cookies) {
/* 102 */         if (!isFirst) {
/* 103 */           filteredCookieString.append(";");
/*     */         }
/*     */         
/* 106 */         String[] cookieParts = cookie.split("=", -1);
/* 107 */         String cookieName = cookieParts[0];
/* 108 */         if (isSecurityCookie(cookieName.trim(), additionalCookieNamesToFilter)) {
/* 109 */           filteredCookieString.append(cookieName + "=" + "[Filtered]");
/*     */         } else {
/* 111 */           filteredCookieString.append(cookie);
/*     */         } 
/* 113 */         isFirst = false;
/*     */       } 
/*     */       
/* 116 */       return filteredCookieString.toString();
/* 117 */     } catch (Throwable t) {
/* 118 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSecurityCookie(@NotNull String cookieName, @Nullable List<String> additionalCookieNamesToFilter) {
/* 125 */     String cookieNameToSearchFor = cookieName.toUpperCase(Locale.ROOT);
/* 126 */     if (SECURITY_COOKIES.contains(cookieNameToSearchFor)) {
/* 127 */       return true;
/*     */     }
/*     */     
/* 130 */     if (additionalCookieNamesToFilter != null) {
/* 131 */       for (String additionalCookieName : additionalCookieNamesToFilter) {
/* 132 */         if (additionalCookieName.toUpperCase(Locale.ROOT).equals(cookieNameToSearchFor)) {
/* 133 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 138 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isHttpClientError(int statusCode) {
/* 142 */     return CLIENT_ERROR_STATUS_CODES.isInRange(statusCode);
/*     */   }
/*     */   
/*     */   public static boolean isHttpServerError(int statusCode) {
/* 146 */     return SEVER_ERROR_STATUS_CODES.isInRange(statusCode);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\HttpUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */