/*     */ package io.netty.handler.codec.http.cors;
/*     */ 
/*     */ import io.netty.handler.codec.http.DefaultHttpHeaders;
/*     */ import io.netty.handler.codec.http.EmptyHttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
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
/*     */ public final class CorsConfig
/*     */ {
/*     */   private final Set<String> origins;
/*     */   private final boolean anyOrigin;
/*     */   private final boolean enabled;
/*     */   private final Set<String> exposeHeaders;
/*     */   private final boolean allowCredentials;
/*     */   private final long maxAge;
/*     */   private final Set<HttpMethod> allowedRequestMethods;
/*     */   private final Set<String> allowedRequestHeaders;
/*     */   private final boolean allowNullOrigin;
/*     */   private final Map<CharSequence, Callable<?>> preflightHeaders;
/*     */   private final boolean shortCircuit;
/*     */   private final boolean allowPrivateNetwork;
/*     */   
/*     */   CorsConfig(CorsConfigBuilder builder) {
/*  51 */     this.origins = new LinkedHashSet<>(builder.origins);
/*  52 */     this.anyOrigin = builder.anyOrigin;
/*  53 */     this.enabled = builder.enabled;
/*  54 */     this.exposeHeaders = builder.exposeHeaders;
/*  55 */     this.allowCredentials = builder.allowCredentials;
/*  56 */     this.maxAge = builder.maxAge;
/*  57 */     this.allowedRequestMethods = builder.requestMethods;
/*  58 */     this.allowedRequestHeaders = builder.requestHeaders;
/*  59 */     this.allowNullOrigin = builder.allowNullOrigin;
/*  60 */     this.preflightHeaders = builder.preflightHeaders;
/*  61 */     this.shortCircuit = builder.shortCircuit;
/*  62 */     this.allowPrivateNetwork = builder.allowPrivateNetwork;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCorsSupportEnabled() {
/*  71 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnyOriginSupported() {
/*  80 */     return this.anyOrigin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String origin() {
/*  89 */     return this.origins.isEmpty() ? "*" : this.origins.iterator().next();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> origins() {
/*  98 */     return this.origins;
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
/*     */   public boolean isNullOriginAllowed() {
/* 111 */     return this.allowNullOrigin;
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
/*     */   public boolean isPrivateNetworkAllowed() {
/* 125 */     return this.allowPrivateNetwork;
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
/*     */   public Set<String> exposedHeaders() {
/* 151 */     return Collections.unmodifiableSet(this.exposeHeaders);
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
/*     */   public boolean isCredentialsAllowed() {
/* 172 */     return this.allowCredentials;
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
/*     */   public long maxAge() {
/* 186 */     return this.maxAge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<HttpMethod> allowedRequestMethods() {
/* 196 */     return Collections.unmodifiableSet(this.allowedRequestMethods);
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
/*     */   public Set<String> allowedRequestHeaders() {
/* 208 */     return Collections.unmodifiableSet(this.allowedRequestHeaders);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpHeaders preflightResponseHeaders() {
/* 217 */     if (this.preflightHeaders.isEmpty()) {
/* 218 */       return (HttpHeaders)EmptyHttpHeaders.INSTANCE;
/*     */     }
/* 220 */     DefaultHttpHeaders defaultHttpHeaders = new DefaultHttpHeaders();
/* 221 */     for (Map.Entry<CharSequence, Callable<?>> entry : this.preflightHeaders.entrySet()) {
/* 222 */       Object value = getValue(entry.getValue());
/* 223 */       if (value instanceof Iterable) {
/* 224 */         defaultHttpHeaders.add(entry.getKey(), (Iterable)value); continue;
/*     */       } 
/* 226 */       defaultHttpHeaders.add(entry.getKey(), value);
/*     */     } 
/*     */     
/* 229 */     return (HttpHeaders)defaultHttpHeaders;
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
/*     */   public boolean isShortCircuit() {
/* 243 */     return this.shortCircuit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean isShortCurcuit() {
/* 251 */     return isShortCircuit();
/*     */   }
/*     */   
/*     */   private static <T> T getValue(Callable<T> callable) {
/*     */     try {
/* 256 */       return callable.call();
/* 257 */     } catch (Exception e) {
/* 258 */       throw new IllegalStateException("Could not generate value for callable [" + callable + ']', e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 264 */     return StringUtil.simpleClassName(this) + "[enabled=" + this.enabled + ", origins=" + this.origins + ", anyOrigin=" + this.anyOrigin + ", exposedHeaders=" + this.exposeHeaders + ", isCredentialsAllowed=" + this.allowCredentials + ", maxAge=" + this.maxAge + ", allowedRequestMethods=" + this.allowedRequestMethods + ", allowedRequestHeaders=" + this.allowedRequestHeaders + ", preflightHeaders=" + this.preflightHeaders + ", isPrivateNetworkAllowed=" + this.allowPrivateNetwork + ']';
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
/*     */   @Deprecated
/*     */   public static Builder withAnyOrigin() {
/* 281 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Builder withOrigin(String origin) {
/* 289 */     if ("*".equals(origin)) {
/* 290 */       return new Builder();
/*     */     }
/* 292 */     return new Builder(new String[] { origin });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Builder withOrigins(String... origins) {
/* 300 */     return new Builder(origins);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static class Builder
/*     */   {
/*     */     private final CorsConfigBuilder builder;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder(String... origins) {
/* 316 */       this.builder = new CorsConfigBuilder(origins);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder() {
/* 324 */       this.builder = new CorsConfigBuilder();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder allowNullOrigin() {
/* 332 */       this.builder.allowNullOrigin();
/* 333 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder disable() {
/* 341 */       this.builder.disable();
/* 342 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder exposeHeaders(String... headers) {
/* 350 */       this.builder.exposeHeaders(headers);
/* 351 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder allowCredentials() {
/* 359 */       this.builder.allowCredentials();
/* 360 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder maxAge(long max) {
/* 368 */       this.builder.maxAge(max);
/* 369 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder allowedRequestMethods(HttpMethod... methods) {
/* 377 */       this.builder.allowedRequestMethods(methods);
/* 378 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder allowedRequestHeaders(String... headers) {
/* 386 */       this.builder.allowedRequestHeaders(headers);
/* 387 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder preflightResponseHeader(CharSequence name, Object... values) {
/* 395 */       this.builder.preflightResponseHeader(name, values);
/* 396 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public <T> Builder preflightResponseHeader(CharSequence name, Iterable<T> value) {
/* 404 */       this.builder.preflightResponseHeader(name, value);
/* 405 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public <T> Builder preflightResponseHeader(String name, Callable<T> valueGenerator) {
/* 413 */       this.builder.preflightResponseHeader(name, valueGenerator);
/* 414 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder noPreflightResponseHeaders() {
/* 422 */       this.builder.noPreflightResponseHeaders();
/* 423 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CorsConfig build() {
/* 431 */       return this.builder.build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Builder shortCurcuit() {
/* 439 */       this.builder.shortCircuit();
/* 440 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static final class DateValueGenerator
/*     */     implements Callable<Date>
/*     */   {
/*     */     public Date call() throws Exception {
/* 452 */       return new Date();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\cors\CorsConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */