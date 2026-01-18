/*     */ package io.netty.handler.codec.http.cors;
/*     */ 
/*     */ import io.netty.handler.codec.http.HttpHeaderNames;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
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
/*     */ public final class CorsConfigBuilder
/*     */ {
/*     */   final Set<String> origins;
/*     */   final boolean anyOrigin;
/*     */   boolean allowNullOrigin;
/*     */   
/*     */   public static CorsConfigBuilder forAnyOrigin() {
/*  44 */     return new CorsConfigBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CorsConfigBuilder forOrigin(String origin) {
/*  53 */     if ("*".equals(origin)) {
/*  54 */       return new CorsConfigBuilder();
/*     */     }
/*  56 */     return new CorsConfigBuilder(new String[] { origin });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CorsConfigBuilder forOrigins(String... origins) {
/*  65 */     return new CorsConfigBuilder(origins);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean enabled = true;
/*     */   
/*     */   boolean allowCredentials;
/*     */   
/*  73 */   final Set<String> exposeHeaders = new HashSet<>();
/*     */   long maxAge;
/*  75 */   final Set<HttpMethod> requestMethods = new HashSet<>();
/*  76 */   final Set<String> requestHeaders = new HashSet<>();
/*  77 */   final Map<CharSequence, Callable<?>> preflightHeaders = new HashMap<>();
/*     */ 
/*     */   
/*     */   private boolean noPreflightHeaders;
/*     */   
/*     */   boolean shortCircuit;
/*     */   
/*     */   boolean allowPrivateNetwork;
/*     */ 
/*     */   
/*     */   CorsConfigBuilder(String... origins) {
/*  88 */     this.origins = new LinkedHashSet<>(Arrays.asList(origins));
/*  89 */     this.anyOrigin = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CorsConfigBuilder() {
/*  98 */     this.anyOrigin = true;
/*  99 */     this.origins = Collections.emptySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CorsConfigBuilder allowNullOrigin() {
/* 110 */     this.allowNullOrigin = true;
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CorsConfigBuilder disable() {
/* 120 */     this.enabled = false;
/* 121 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CorsConfigBuilder exposeHeaders(String... headers) {
/* 150 */     this.exposeHeaders.addAll(Arrays.asList(headers));
/* 151 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CorsConfigBuilder exposeHeaders(CharSequence... headers) {
/* 180 */     for (CharSequence header : headers) {
/* 181 */       this.exposeHeaders.add(header.toString());
/*     */     }
/* 183 */     return this;
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
/*     */   public CorsConfigBuilder allowCredentials() {
/* 202 */     this.allowCredentials = true;
/* 203 */     return this;
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
/*     */   public CorsConfigBuilder maxAge(long max) {
/* 216 */     this.maxAge = max;
/* 217 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CorsConfigBuilder allowedRequestMethods(HttpMethod... methods) {
/* 228 */     this.requestMethods.addAll(Arrays.asList(methods));
/* 229 */     return this;
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
/*     */   public CorsConfigBuilder allowedRequestHeaders(String... headers) {
/* 249 */     this.requestHeaders.addAll(Arrays.asList(headers));
/* 250 */     return this;
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
/*     */   public CorsConfigBuilder allowedRequestHeaders(CharSequence... headers) {
/* 270 */     for (CharSequence header : headers) {
/* 271 */       this.requestHeaders.add(header.toString());
/*     */     }
/* 273 */     return this;
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
/*     */   public CorsConfigBuilder preflightResponseHeader(CharSequence name, Object... values) {
/* 287 */     if (values.length == 1) {
/* 288 */       this.preflightHeaders.put(name, new ConstantValueGenerator(values[0]));
/*     */     } else {
/* 290 */       preflightResponseHeader(name, Arrays.asList(values));
/*     */     } 
/* 292 */     return this;
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
/*     */   public <T> CorsConfigBuilder preflightResponseHeader(CharSequence name, Iterable<T> value) {
/* 307 */     this.preflightHeaders.put(name, new ConstantValueGenerator(value));
/* 308 */     return this;
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
/*     */   public <T> CorsConfigBuilder preflightResponseHeader(CharSequence name, Callable<T> valueGenerator) {
/* 327 */     this.preflightHeaders.put(name, valueGenerator);
/* 328 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CorsConfigBuilder noPreflightResponseHeaders() {
/* 337 */     this.noPreflightHeaders = true;
/* 338 */     return this;
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
/*     */   public CorsConfigBuilder shortCircuit() {
/* 352 */     this.shortCircuit = true;
/* 353 */     return this;
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
/*     */   public CorsConfigBuilder allowPrivateNetwork() {
/* 365 */     this.allowPrivateNetwork = true;
/* 366 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CorsConfig build() {
/* 375 */     if (this.preflightHeaders.isEmpty() && !this.noPreflightHeaders) {
/* 376 */       this.preflightHeaders.put(HttpHeaderNames.DATE, DateValueGenerator.INSTANCE);
/* 377 */       this.preflightHeaders.put(HttpHeaderNames.CONTENT_LENGTH, new ConstantValueGenerator("0"));
/*     */     } 
/* 379 */     return new CorsConfig(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ConstantValueGenerator
/*     */     implements Callable<Object>
/*     */   {
/*     */     private final Object value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ConstantValueGenerator(Object value) {
/* 397 */       this.value = ObjectUtil.checkNotNullWithIAE(value, "value");
/*     */     }
/*     */ 
/*     */     
/*     */     public Object call() {
/* 402 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class DateValueGenerator
/*     */     implements Callable<Date>
/*     */   {
/* 413 */     static final DateValueGenerator INSTANCE = new DateValueGenerator();
/*     */ 
/*     */     
/*     */     public Date call() throws Exception {
/* 417 */       return new Date();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\cors\CorsConfigBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */