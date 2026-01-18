/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.util.CollectionUtils;
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
/*     */ public final class Response
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String TYPE = "response";
/*     */   @Nullable
/*     */   private String cookies;
/*     */   @Nullable
/*     */   private Map<String, String> headers;
/*     */   @Nullable
/*     */   private Integer statusCode;
/*     */   @Nullable
/*     */   private Long bodySize;
/*     */   @Nullable
/*     */   private Object data;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public Response() {}
/*     */   
/*     */   public Response(@NotNull Response response) {
/*  55 */     this.cookies = response.cookies;
/*  56 */     this.headers = CollectionUtils.newConcurrentHashMap(response.headers);
/*  57 */     this.unknown = CollectionUtils.newConcurrentHashMap(response.unknown);
/*  58 */     this.statusCode = response.statusCode;
/*  59 */     this.bodySize = response.bodySize;
/*  60 */     this.data = response.data;
/*     */   }
/*     */   @Nullable
/*     */   public String getCookies() {
/*  64 */     return this.cookies;
/*     */   }
/*     */   
/*     */   public void setCookies(@Nullable String cookies) {
/*  68 */     this.cookies = cookies;
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, String> getHeaders() {
/*  72 */     return this.headers;
/*     */   }
/*     */   
/*     */   public void setHeaders(@Nullable Map<String, String> headers) {
/*  76 */     this.headers = CollectionUtils.newConcurrentHashMap(headers);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  82 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  87 */     this.unknown = unknown;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getStatusCode() {
/*  91 */     return this.statusCode;
/*     */   }
/*     */   
/*     */   public void setStatusCode(@Nullable Integer statusCode) {
/*  95 */     this.statusCode = statusCode;
/*     */   }
/*     */   @Nullable
/*     */   public Long getBodySize() {
/*  99 */     return this.bodySize;
/*     */   }
/*     */   
/*     */   public void setBodySize(@Nullable Long bodySize) {
/* 103 */     this.bodySize = bodySize;
/*     */   }
/*     */   @Nullable
/*     */   public Object getData() {
/* 107 */     return this.data;
/*     */   }
/*     */   
/*     */   public void setData(@Nullable Object data) {
/* 111 */     this.data = data;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String COOKIES = "cookies";
/*     */     
/*     */     public static final String HEADERS = "headers";
/*     */     
/*     */     public static final String STATUS_CODE = "status_code";
/*     */     public static final String BODY_SIZE = "body_size";
/*     */     public static final String DATA = "data";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 127 */     writer.beginObject();
/*     */     
/* 129 */     if (this.cookies != null) {
/* 130 */       writer.name("cookies").value(this.cookies);
/*     */     }
/* 132 */     if (this.headers != null) {
/* 133 */       writer.name("headers").value(logger, this.headers);
/*     */     }
/* 135 */     if (this.statusCode != null) {
/* 136 */       writer.name("status_code").value(logger, this.statusCode);
/*     */     }
/* 138 */     if (this.bodySize != null) {
/* 139 */       writer.name("body_size").value(logger, this.bodySize);
/*     */     }
/* 141 */     if (this.data != null) {
/* 142 */       writer.name("data").value(logger, this.data);
/*     */     }
/* 144 */     if (this.unknown != null) {
/* 145 */       for (String key : this.unknown.keySet()) {
/* 146 */         Object value = this.unknown.get(key);
/* 147 */         writer.name(key);
/* 148 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 151 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<Response>
/*     */   {
/*     */     @NotNull
/*     */     public Response deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 159 */       reader.beginObject();
/* 160 */       Response response = new Response();
/* 161 */       Map<String, Object> unknown = null;
/* 162 */       while (reader.peek() == JsonToken.NAME) {
/* 163 */         Map<String, String> deserializedHeaders; String nextName = reader.nextName();
/* 164 */         switch (nextName) {
/*     */           case "cookies":
/* 166 */             response.cookies = reader.nextStringOrNull();
/*     */             continue;
/*     */           
/*     */           case "headers":
/* 170 */             deserializedHeaders = (Map<String, String>)reader.nextObjectOrNull();
/* 171 */             if (deserializedHeaders != null) {
/* 172 */               response.headers = CollectionUtils.newConcurrentHashMap(deserializedHeaders);
/*     */             }
/*     */             continue;
/*     */           case "status_code":
/* 176 */             response.statusCode = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "body_size":
/* 179 */             response.bodySize = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "data":
/* 182 */             response.data = reader.nextObjectOrNull();
/*     */             continue;
/*     */         } 
/* 185 */         if (unknown == null) {
/* 186 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 188 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 192 */       response.setUnknown(unknown);
/* 193 */       reader.endObject();
/* 194 */       return response;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\Response.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */