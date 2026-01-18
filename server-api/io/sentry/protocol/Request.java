/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public final class Request
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private String url;
/*     */   @Nullable
/*     */   private String method;
/*     */   @Nullable
/*     */   private String queryString;
/*     */   @Nullable
/*     */   private Object data;
/*     */   @Nullable
/*     */   private String cookies;
/*     */   @Nullable
/*     */   private Map<String, String> headers;
/*     */   @Nullable
/*     */   private Map<String, String> env;
/*     */   @Nullable
/*     */   private Long bodySize;
/*     */   @Nullable
/*     */   private Map<String, String> other;
/*     */   @Nullable
/*     */   private String fragment;
/*     */   @Nullable
/*     */   private String apiTarget;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public Request() {}
/*     */   
/*     */   public Request(@NotNull Request request) {
/* 134 */     this.url = request.url;
/* 135 */     this.cookies = request.cookies;
/* 136 */     this.method = request.method;
/* 137 */     this.queryString = request.queryString;
/* 138 */     this.headers = CollectionUtils.newConcurrentHashMap(request.headers);
/* 139 */     this.env = CollectionUtils.newConcurrentHashMap(request.env);
/* 140 */     this.other = CollectionUtils.newConcurrentHashMap(request.other);
/* 141 */     this.unknown = CollectionUtils.newConcurrentHashMap(request.unknown);
/* 142 */     this.data = request.data;
/* 143 */     this.fragment = request.fragment;
/* 144 */     this.bodySize = request.bodySize;
/* 145 */     this.apiTarget = request.apiTarget;
/*     */   }
/*     */   @Nullable
/*     */   public String getUrl() {
/* 149 */     return this.url;
/*     */   }
/*     */   
/*     */   public void setUrl(@Nullable String url) {
/* 153 */     this.url = url;
/*     */   }
/*     */   @Nullable
/*     */   public String getMethod() {
/* 157 */     return this.method;
/*     */   }
/*     */   
/*     */   public void setMethod(@Nullable String method) {
/* 161 */     this.method = method;
/*     */   }
/*     */   @Nullable
/*     */   public String getQueryString() {
/* 165 */     return this.queryString;
/*     */   }
/*     */   
/*     */   public void setQueryString(@Nullable String queryString) {
/* 169 */     this.queryString = queryString;
/*     */   }
/*     */   @Nullable
/*     */   public Object getData() {
/* 173 */     return this.data;
/*     */   }
/*     */   
/*     */   public void setData(@Nullable Object data) {
/* 177 */     this.data = data;
/*     */   }
/*     */   @Nullable
/*     */   public String getCookies() {
/* 181 */     return this.cookies;
/*     */   }
/*     */   
/*     */   public void setCookies(@Nullable String cookies) {
/* 185 */     this.cookies = cookies;
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, String> getHeaders() {
/* 189 */     return this.headers;
/*     */   }
/*     */   
/*     */   public void setHeaders(@Nullable Map<String, String> headers) {
/* 193 */     this.headers = CollectionUtils.newConcurrentHashMap(headers);
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, String> getEnvs() {
/* 197 */     return this.env;
/*     */   }
/*     */   
/*     */   public void setEnvs(@Nullable Map<String, String> env) {
/* 201 */     this.env = CollectionUtils.newConcurrentHashMap(env);
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, String> getOthers() {
/* 205 */     return this.other;
/*     */   }
/*     */   
/*     */   public void setOthers(@Nullable Map<String, String> other) {
/* 209 */     this.other = CollectionUtils.newConcurrentHashMap(other);
/*     */   }
/*     */   @Nullable
/*     */   public String getFragment() {
/* 213 */     return this.fragment;
/*     */   }
/*     */   
/*     */   public void setFragment(@Nullable String fragment) {
/* 217 */     this.fragment = fragment;
/*     */   }
/*     */   @Nullable
/*     */   public Long getBodySize() {
/* 221 */     return this.bodySize;
/*     */   }
/*     */   
/*     */   public void setBodySize(@Nullable Long bodySize) {
/* 225 */     this.bodySize = bodySize;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 230 */     if (this == o) return true; 
/* 231 */     if (o == null || getClass() != o.getClass()) return false; 
/* 232 */     Request request = (Request)o;
/* 233 */     return (Objects.equals(this.url, request.url) && 
/* 234 */       Objects.equals(this.method, request.method) && 
/* 235 */       Objects.equals(this.queryString, request.queryString) && 
/* 236 */       Objects.equals(this.cookies, request.cookies) && 
/* 237 */       Objects.equals(this.headers, request.headers) && 
/* 238 */       Objects.equals(this.env, request.env) && 
/* 239 */       Objects.equals(this.bodySize, request.bodySize) && 
/* 240 */       Objects.equals(this.fragment, request.fragment) && 
/* 241 */       Objects.equals(this.apiTarget, request.apiTarget));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 246 */     return Objects.hash(new Object[] { this.url, this.method, this.queryString, this.cookies, this.headers, this.env, this.bodySize, this.fragment, this.apiTarget });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 255 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 260 */     this.unknown = unknown;
/*     */   }
/*     */   @Nullable
/*     */   public String getApiTarget() {
/* 264 */     return this.apiTarget;
/*     */   }
/*     */   
/*     */   public void setApiTarget(@Nullable String apiTarget) {
/* 268 */     this.apiTarget = apiTarget;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String URL = "url";
/*     */     public static final String METHOD = "method";
/*     */     public static final String QUERY_STRING = "query_string";
/*     */     public static final String DATA = "data";
/*     */     public static final String COOKIES = "cookies";
/*     */     public static final String HEADERS = "headers";
/*     */     public static final String ENV = "env";
/*     */     public static final String OTHER = "other";
/*     */     public static final String FRAGMENT = "fragment";
/*     */     public static final String BODY_SIZE = "body_size";
/*     */     public static final String API_TARGET = "api_target";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 288 */     writer.beginObject();
/* 289 */     if (this.url != null) {
/* 290 */       writer.name("url").value(this.url);
/*     */     }
/* 292 */     if (this.method != null) {
/* 293 */       writer.name("method").value(this.method);
/*     */     }
/* 295 */     if (this.queryString != null) {
/* 296 */       writer.name("query_string").value(this.queryString);
/*     */     }
/* 298 */     if (this.data != null) {
/* 299 */       writer.name("data").value(logger, this.data);
/*     */     }
/* 301 */     if (this.cookies != null) {
/* 302 */       writer.name("cookies").value(this.cookies);
/*     */     }
/* 304 */     if (this.headers != null) {
/* 305 */       writer.name("headers").value(logger, this.headers);
/*     */     }
/* 307 */     if (this.env != null) {
/* 308 */       writer.name("env").value(logger, this.env);
/*     */     }
/* 310 */     if (this.other != null) {
/* 311 */       writer.name("other").value(logger, this.other);
/*     */     }
/* 313 */     if (this.fragment != null) {
/* 314 */       writer.name("fragment").value(logger, this.fragment);
/*     */     }
/* 316 */     if (this.bodySize != null) {
/* 317 */       writer.name("body_size").value(logger, this.bodySize);
/*     */     }
/* 319 */     if (this.apiTarget != null) {
/* 320 */       writer.name("api_target").value(logger, this.apiTarget);
/*     */     }
/* 322 */     if (this.unknown != null) {
/* 323 */       for (String key : this.unknown.keySet()) {
/* 324 */         Object value = this.unknown.get(key);
/* 325 */         writer.name(key);
/* 326 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 329 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<Request>
/*     */   {
/*     */     @NotNull
/*     */     public Request deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 337 */       reader.beginObject();
/* 338 */       Request request = new Request();
/* 339 */       Map<String, Object> unknown = null;
/* 340 */       while (reader.peek() == JsonToken.NAME) {
/* 341 */         Map<String, String> deserializedHeaders, deserializedEnv, deserializedOther; String nextName = reader.nextName();
/* 342 */         switch (nextName) {
/*     */           case "url":
/* 344 */             request.url = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "method":
/* 347 */             request.method = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "query_string":
/* 350 */             request.queryString = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "data":
/* 353 */             request.data = reader.nextObjectOrNull();
/*     */             continue;
/*     */           case "cookies":
/* 356 */             request.cookies = reader.nextStringOrNull();
/*     */             continue;
/*     */           
/*     */           case "headers":
/* 360 */             deserializedHeaders = (Map<String, String>)reader.nextObjectOrNull();
/* 361 */             if (deserializedHeaders != null) {
/* 362 */               request.headers = CollectionUtils.newConcurrentHashMap(deserializedHeaders);
/*     */             }
/*     */             continue;
/*     */           case "env":
/* 366 */             deserializedEnv = (Map<String, String>)reader.nextObjectOrNull();
/* 367 */             if (deserializedEnv != null) {
/* 368 */               request.env = CollectionUtils.newConcurrentHashMap(deserializedEnv);
/*     */             }
/*     */             continue;
/*     */           case "other":
/* 372 */             deserializedOther = (Map<String, String>)reader.nextObjectOrNull();
/* 373 */             if (deserializedOther != null) {
/* 374 */               request.other = CollectionUtils.newConcurrentHashMap(deserializedOther);
/*     */             }
/*     */             continue;
/*     */           case "fragment":
/* 378 */             request.fragment = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "body_size":
/* 381 */             request.bodySize = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "api_target":
/* 384 */             request.apiTarget = reader.nextStringOrNull();
/*     */             continue;
/*     */         } 
/* 387 */         if (unknown == null) {
/* 388 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 390 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 394 */       request.setUnknown(unknown);
/* 395 */       reader.endObject();
/* 396 */       return request;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\Request.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */