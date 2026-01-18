/*     */ package io.sentry.rrweb;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RRWebBreadcrumbEvent
/*     */   extends RRWebEvent
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String EVENT_TAG = "breadcrumb";
/*     */   @NotNull
/*     */   private String tag;
/*     */   private double breadcrumbTimestamp;
/*     */   @Nullable
/*     */   private String breadcrumbType;
/*     */   @Nullable
/*     */   private String category;
/*     */   
/*     */   public RRWebBreadcrumbEvent() {
/*  38 */     super(RRWebEventType.Custom);
/*  39 */     this.tag = "breadcrumb"; } @Nullable private String message; @Nullable private SentryLevel level; @Nullable
/*     */   private Map<String, Object> data; @Nullable
/*     */   private Map<String, Object> unknown; @Nullable
/*     */   private Map<String, Object> payloadUnknown; @Nullable
/*     */   private Map<String, Object> dataUnknown; @NotNull
/*  44 */   public String getTag() { return this.tag; }
/*     */ 
/*     */   
/*     */   public void setTag(@NotNull String tag) {
/*  48 */     this.tag = tag;
/*     */   }
/*     */   
/*     */   public double getBreadcrumbTimestamp() {
/*  52 */     return this.breadcrumbTimestamp;
/*     */   }
/*     */   
/*     */   public void setBreadcrumbTimestamp(double breadcrumbTimestamp) {
/*  56 */     this.breadcrumbTimestamp = breadcrumbTimestamp;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getBreadcrumbType() {
/*  61 */     return this.breadcrumbType;
/*     */   }
/*     */   
/*     */   public void setBreadcrumbType(@Nullable String breadcrumbType) {
/*  65 */     this.breadcrumbType = breadcrumbType;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getCategory() {
/*  70 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setCategory(@Nullable String category) {
/*  74 */     this.category = category;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getMessage() {
/*  79 */     return this.message;
/*     */   }
/*     */   
/*     */   public void setMessage(@Nullable String message) {
/*  83 */     this.message = message;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryLevel getLevel() {
/*  88 */     return this.level;
/*     */   }
/*     */   
/*     */   public void setLevel(@Nullable SentryLevel level) {
/*  92 */     this.level = level;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getData() {
/*  97 */     return this.data;
/*     */   }
/*     */   
/*     */   public void setData(@Nullable Map<String, Object> data) {
/* 101 */     this.data = (data == null) ? null : new ConcurrentHashMap<>(data);
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, Object> getPayloadUnknown() {
/* 105 */     return this.payloadUnknown;
/*     */   }
/*     */   
/*     */   public void setPayloadUnknown(@Nullable Map<String, Object> payloadUnknown) {
/* 109 */     this.payloadUnknown = payloadUnknown;
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, Object> getDataUnknown() {
/* 113 */     return this.dataUnknown;
/*     */   }
/*     */   
/*     */   public void setDataUnknown(@Nullable Map<String, Object> dataUnknown) {
/* 117 */     this.dataUnknown = dataUnknown;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 122 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 127 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String DATA = "data";
/*     */     
/*     */     public static final String PAYLOAD = "payload";
/*     */     
/*     */     public static final String TIMESTAMP = "timestamp";
/*     */     public static final String TYPE = "type";
/*     */     public static final String CATEGORY = "category";
/*     */     public static final String MESSAGE = "message";
/*     */     public static final String LEVEL = "level";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 145 */     writer.beginObject();
/* 146 */     (new RRWebEvent.Serializer()).serialize(this, writer, logger);
/* 147 */     writer.name("data");
/* 148 */     serializeData(writer, logger);
/* 149 */     if (this.unknown != null) {
/* 150 */       for (String key : this.unknown.keySet()) {
/* 151 */         Object value = this.unknown.get(key);
/* 152 */         writer.name(key);
/* 153 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 156 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializeData(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 161 */     writer.beginObject();
/* 162 */     writer.name("tag").value(this.tag);
/* 163 */     writer.name("payload");
/* 164 */     serializePayload(writer, logger);
/* 165 */     if (this.dataUnknown != null) {
/* 166 */       for (String key : this.dataUnknown.keySet()) {
/* 167 */         Object value = this.dataUnknown.get(key);
/* 168 */         writer.name(key);
/* 169 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 172 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializePayload(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 177 */     writer.beginObject();
/* 178 */     if (this.breadcrumbType != null) {
/* 179 */       writer.name("type").value(this.breadcrumbType);
/*     */     }
/* 181 */     writer.name("timestamp").value(logger, BigDecimal.valueOf(this.breadcrumbTimestamp));
/* 182 */     if (this.category != null) {
/* 183 */       writer.name("category").value(this.category);
/*     */     }
/* 185 */     if (this.message != null) {
/* 186 */       writer.name("message").value(this.message);
/*     */     }
/* 188 */     if (this.level != null) {
/* 189 */       writer.name("level").value(logger, this.level);
/*     */     }
/* 191 */     if (this.data != null) {
/* 192 */       writer.name("data").value(logger, this.data);
/*     */     }
/* 194 */     if (this.payloadUnknown != null) {
/* 195 */       for (String key : this.payloadUnknown.keySet()) {
/* 196 */         Object value = this.payloadUnknown.get(key);
/* 197 */         writer.name(key);
/* 198 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 201 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<RRWebBreadcrumbEvent>
/*     */   {
/*     */     @NotNull
/*     */     public RRWebBreadcrumbEvent deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 209 */       reader.beginObject();
/* 210 */       Map<String, Object> unknown = null;
/*     */       
/* 212 */       RRWebBreadcrumbEvent event = new RRWebBreadcrumbEvent();
/* 213 */       RRWebEvent.Deserializer baseEventDeserializer = new RRWebEvent.Deserializer();
/*     */       
/* 215 */       while (reader.peek() == JsonToken.NAME) {
/* 216 */         String nextName = reader.nextName();
/* 217 */         switch (nextName) {
/*     */           case "data":
/* 219 */             deserializeData(event, reader, logger);
/*     */             continue;
/*     */         } 
/* 222 */         if (!baseEventDeserializer.deserializeValue(event, nextName, reader, logger)) {
/* 223 */           if (unknown == null) {
/* 224 */             unknown = new HashMap<>();
/*     */           }
/* 226 */           reader.nextUnknown(logger, unknown, nextName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 232 */       event.setUnknown(unknown);
/* 233 */       reader.endObject();
/* 234 */       return event;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void deserializeData(@NotNull RRWebBreadcrumbEvent event, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 242 */       Map<String, Object> dataUnknown = null;
/*     */       
/* 244 */       reader.beginObject();
/* 245 */       while (reader.peek() == JsonToken.NAME) {
/* 246 */         String tag, nextName = reader.nextName();
/* 247 */         switch (nextName) {
/*     */           case "tag":
/* 249 */             tag = reader.nextStringOrNull();
/* 250 */             event.tag = (tag == null) ? "" : tag;
/*     */             continue;
/*     */           case "payload":
/* 253 */             deserializePayload(event, reader, logger);
/*     */             continue;
/*     */         } 
/* 256 */         if (dataUnknown == null) {
/* 257 */           dataUnknown = new ConcurrentHashMap<>();
/*     */         }
/* 259 */         reader.nextUnknown(logger, dataUnknown, nextName);
/*     */       } 
/*     */       
/* 262 */       event.setDataUnknown(dataUnknown);
/* 263 */       reader.endObject();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void deserializePayload(@NotNull RRWebBreadcrumbEvent event, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 272 */       Map<String, Object> payloadUnknown = null;
/*     */       
/* 274 */       reader.beginObject();
/* 275 */       while (reader.peek() == JsonToken.NAME) {
/* 276 */         Map<String, Object> deserializedData; String nextName = reader.nextName();
/* 277 */         switch (nextName) {
/*     */           case "type":
/* 279 */             event.breadcrumbType = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "timestamp":
/* 282 */             event.breadcrumbTimestamp = reader.nextDouble();
/*     */             continue;
/*     */           case "category":
/* 285 */             event.category = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "message":
/* 288 */             event.message = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "level":
/*     */             try {
/* 292 */               event.level = (new SentryLevel.Deserializer()).deserialize(reader, logger);
/* 293 */             } catch (Exception exception) {
/* 294 */               logger.log(SentryLevel.DEBUG, exception, "Error when deserializing SentryLevel", new Object[0]);
/*     */             } 
/*     */             continue;
/*     */           
/*     */           case "data":
/* 299 */             deserializedData = CollectionUtils.newConcurrentHashMap((Map)reader
/* 300 */                 .nextObjectOrNull());
/* 301 */             if (deserializedData != null) {
/* 302 */               event.data = deserializedData;
/*     */             }
/*     */             continue;
/*     */         } 
/* 306 */         if (payloadUnknown == null) {
/* 307 */           payloadUnknown = new ConcurrentHashMap<>();
/*     */         }
/* 309 */         reader.nextUnknown(logger, payloadUnknown, nextName);
/*     */       } 
/*     */       
/* 312 */       event.setPayloadUnknown(payloadUnknown);
/* 313 */       reader.endObject();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\rrweb\RRWebBreadcrumbEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */