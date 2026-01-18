/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public final class SentryException
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private String type;
/*     */   @Nullable
/*     */   private String value;
/*     */   @Nullable
/*     */   private String module;
/*     */   @Nullable
/*     */   private Long threadId;
/*     */   @Nullable
/*     */   private SentryStackTrace stacktrace;
/*     */   @Nullable
/*     */   private Mechanism mechanism;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   @Nullable
/*     */   public String getType() {
/*  67 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(@Nullable String type) {
/*  76 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getValue() {
/*  85 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(@Nullable String value) {
/*  94 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getModule() {
/* 103 */     return this.module;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModule(@Nullable String module) {
/* 112 */     this.module = module;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Long getThreadId() {
/* 121 */     return this.threadId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThreadId(@Nullable Long threadId) {
/* 130 */     this.threadId = threadId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SentryStackTrace getStacktrace() {
/* 139 */     return this.stacktrace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStacktrace(@Nullable SentryStackTrace stacktrace) {
/* 148 */     this.stacktrace = stacktrace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Mechanism getMechanism() {
/* 157 */     return this.mechanism;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMechanism(@Nullable Mechanism mechanism) {
/* 166 */     this.mechanism = mechanism;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String TYPE = "type";
/*     */     
/*     */     public static final String VALUE = "value";
/*     */     
/*     */     public static final String MODULE = "module";
/*     */     public static final String THREAD_ID = "thread_id";
/*     */     public static final String STACKTRACE = "stacktrace";
/*     */     public static final String MECHANISM = "mechanism";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 184 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 189 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 197 */     writer.beginObject();
/* 198 */     if (this.type != null) {
/* 199 */       writer.name("type").value(this.type);
/*     */     }
/* 201 */     if (this.value != null) {
/* 202 */       writer.name("value").value(this.value);
/*     */     }
/* 204 */     if (this.module != null) {
/* 205 */       writer.name("module").value(this.module);
/*     */     }
/* 207 */     if (this.threadId != null) {
/* 208 */       writer.name("thread_id").value(this.threadId);
/*     */     }
/* 210 */     if (this.stacktrace != null) {
/* 211 */       writer.name("stacktrace").value(logger, this.stacktrace);
/*     */     }
/* 213 */     if (this.mechanism != null) {
/* 214 */       writer.name("mechanism").value(logger, this.mechanism);
/*     */     }
/* 216 */     if (this.unknown != null) {
/* 217 */       for (String key : this.unknown.keySet()) {
/* 218 */         Object value = this.unknown.get(key);
/* 219 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 222 */     writer.endObject();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryException>
/*     */   {
/*     */     @NotNull
/*     */     public SentryException deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 232 */       SentryException sentryException = new SentryException();
/* 233 */       Map<String, Object> unknown = null;
/* 234 */       reader.beginObject();
/* 235 */       while (reader.peek() == JsonToken.NAME) {
/* 236 */         String nextName = reader.nextName();
/* 237 */         switch (nextName) {
/*     */           case "type":
/* 239 */             sentryException.type = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "value":
/* 242 */             sentryException.value = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "module":
/* 245 */             sentryException.module = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "thread_id":
/* 248 */             sentryException.threadId = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "stacktrace":
/* 251 */             sentryException.stacktrace = (SentryStackTrace)reader
/* 252 */               .nextOrNull(logger, new SentryStackTrace.Deserializer());
/*     */             continue;
/*     */           case "mechanism":
/* 255 */             sentryException.mechanism = (Mechanism)reader.nextOrNull(logger, new Mechanism.Deserializer());
/*     */             continue;
/*     */         } 
/* 258 */         if (unknown == null) {
/* 259 */           unknown = new HashMap<>();
/*     */         }
/* 261 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 265 */       reader.endObject();
/* 266 */       sentryException.setUnknown(unknown);
/* 267 */       return sentryException;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\SentryException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */