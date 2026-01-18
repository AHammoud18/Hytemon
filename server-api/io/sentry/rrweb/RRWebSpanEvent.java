/*     */ package io.sentry.rrweb;
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
/*     */ import java.math.BigDecimal;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RRWebSpanEvent
/*     */   extends RRWebEvent
/*     */   implements JsonSerializable, JsonUnknown
/*     */ {
/*     */   public static final String EVENT_TAG = "performanceSpan";
/*     */   @NotNull
/*     */   private String tag;
/*     */   @Nullable
/*     */   private String op;
/*     */   @Nullable
/*     */   private String description;
/*     */   private double startTimestamp;
/*     */   
/*     */   public RRWebSpanEvent() {
/*  35 */     super(RRWebEventType.Custom);
/*  36 */     this.tag = "performanceSpan"; } private double endTimestamp; @Nullable
/*     */   private Map<String, Object> data; @Nullable
/*     */   private Map<String, Object> unknown; @Nullable
/*     */   private Map<String, Object> payloadUnknown; @Nullable
/*     */   private Map<String, Object> dataUnknown; @NotNull
/*  41 */   public String getTag() { return this.tag; }
/*     */ 
/*     */   
/*     */   public void setTag(@NotNull String tag) {
/*  45 */     this.tag = tag;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getOp() {
/*  50 */     return this.op;
/*     */   }
/*     */   
/*     */   public void setOp(@Nullable String op) {
/*  54 */     this.op = op;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getDescription() {
/*  59 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(@Nullable String description) {
/*  63 */     this.description = description;
/*     */   }
/*     */   
/*     */   public double getStartTimestamp() {
/*  67 */     return this.startTimestamp;
/*     */   }
/*     */   
/*     */   public void setStartTimestamp(double startTimestamp) {
/*  71 */     this.startTimestamp = startTimestamp;
/*     */   }
/*     */   
/*     */   public double getEndTimestamp() {
/*  75 */     return this.endTimestamp;
/*     */   }
/*     */   
/*     */   public void setEndTimestamp(double endTimestamp) {
/*  79 */     this.endTimestamp = endTimestamp;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getData() {
/*  84 */     return this.data;
/*     */   }
/*     */   
/*     */   public void setData(@Nullable Map<String, Object> data) {
/*  88 */     this.data = (data == null) ? null : new ConcurrentHashMap<>(data);
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, Object> getPayloadUnknown() {
/*  92 */     return this.payloadUnknown;
/*     */   }
/*     */   
/*     */   public void setPayloadUnknown(@Nullable Map<String, Object> payloadUnknown) {
/*  96 */     this.payloadUnknown = payloadUnknown;
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, Object> getDataUnknown() {
/* 100 */     return this.dataUnknown;
/*     */   }
/*     */   
/*     */   public void setDataUnknown(@Nullable Map<String, Object> dataUnknown) {
/* 104 */     this.dataUnknown = dataUnknown;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 109 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 114 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String DATA = "data";
/*     */     public static final String PAYLOAD = "payload";
/*     */     public static final String OP = "op";
/*     */     public static final String DESCRIPTION = "description";
/*     */     public static final String START_TIMESTAMP = "startTimestamp";
/*     */     public static final String END_TIMESTAMP = "endTimestamp";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 129 */     writer.beginObject();
/* 130 */     (new RRWebEvent.Serializer()).serialize(this, writer, logger);
/* 131 */     writer.name("data");
/* 132 */     serializeData(writer, logger);
/* 133 */     if (this.unknown != null) {
/* 134 */       for (String key : this.unknown.keySet()) {
/* 135 */         Object value = this.unknown.get(key);
/* 136 */         writer.name(key);
/* 137 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 140 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializeData(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 145 */     writer.beginObject();
/* 146 */     writer.name("tag").value(this.tag);
/* 147 */     writer.name("payload");
/* 148 */     serializePayload(writer, logger);
/* 149 */     if (this.dataUnknown != null) {
/* 150 */       for (String key : this.dataUnknown.keySet()) {
/* 151 */         Object value = this.dataUnknown.get(key);
/* 152 */         writer.name(key);
/* 153 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 156 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializePayload(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 161 */     writer.beginObject();
/* 162 */     if (this.op != null) {
/* 163 */       writer.name("op").value(this.op);
/*     */     }
/* 165 */     if (this.description != null) {
/* 166 */       writer.name("description").value(this.description);
/*     */     }
/* 168 */     writer.name("startTimestamp").value(logger, BigDecimal.valueOf(this.startTimestamp));
/* 169 */     writer.name("endTimestamp").value(logger, BigDecimal.valueOf(this.endTimestamp));
/* 170 */     if (this.data != null) {
/* 171 */       writer.name("data").value(logger, this.data);
/*     */     }
/* 173 */     if (this.payloadUnknown != null) {
/* 174 */       for (String key : this.payloadUnknown.keySet()) {
/* 175 */         Object value = this.payloadUnknown.get(key);
/* 176 */         writer.name(key);
/* 177 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 180 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<RRWebSpanEvent>
/*     */   {
/*     */     @NotNull
/*     */     public RRWebSpanEvent deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 188 */       reader.beginObject();
/* 189 */       Map<String, Object> unknown = null;
/*     */       
/* 191 */       RRWebSpanEvent event = new RRWebSpanEvent();
/* 192 */       RRWebEvent.Deserializer baseEventDeserializer = new RRWebEvent.Deserializer();
/*     */       
/* 194 */       while (reader.peek() == JsonToken.NAME) {
/* 195 */         String nextName = reader.nextName();
/* 196 */         switch (nextName) {
/*     */           case "data":
/* 198 */             deserializeData(event, reader, logger);
/*     */             continue;
/*     */         } 
/* 201 */         if (!baseEventDeserializer.deserializeValue(event, nextName, reader, logger)) {
/* 202 */           if (unknown == null) {
/* 203 */             unknown = new HashMap<>();
/*     */           }
/* 205 */           reader.nextUnknown(logger, unknown, nextName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 211 */       event.setUnknown(unknown);
/* 212 */       reader.endObject();
/* 213 */       return event;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void deserializeData(@NotNull RRWebSpanEvent event, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 221 */       Map<String, Object> dataUnknown = null;
/*     */       
/* 223 */       reader.beginObject();
/* 224 */       while (reader.peek() == JsonToken.NAME) {
/* 225 */         String tag, nextName = reader.nextName();
/* 226 */         switch (nextName) {
/*     */           case "tag":
/* 228 */             tag = reader.nextStringOrNull();
/* 229 */             event.tag = (tag == null) ? "" : tag;
/*     */             continue;
/*     */           case "payload":
/* 232 */             deserializePayload(event, reader, logger);
/*     */             continue;
/*     */         } 
/* 235 */         if (dataUnknown == null) {
/* 236 */           dataUnknown = new ConcurrentHashMap<>();
/*     */         }
/* 238 */         reader.nextUnknown(logger, dataUnknown, nextName);
/*     */       } 
/*     */       
/* 241 */       event.setDataUnknown(dataUnknown);
/* 242 */       reader.endObject();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void deserializePayload(@NotNull RRWebSpanEvent event, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 251 */       Map<String, Object> payloadUnknown = null;
/*     */       
/* 253 */       reader.beginObject();
/* 254 */       while (reader.peek() == JsonToken.NAME) {
/* 255 */         Map<String, Object> deserializedData; String nextName = reader.nextName();
/* 256 */         switch (nextName) {
/*     */           case "op":
/* 258 */             event.op = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "description":
/* 261 */             event.description = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "startTimestamp":
/* 264 */             event.startTimestamp = reader.nextDouble();
/*     */             continue;
/*     */           case "endTimestamp":
/* 267 */             event.endTimestamp = reader.nextDouble();
/*     */             continue;
/*     */           
/*     */           case "data":
/* 271 */             deserializedData = CollectionUtils.newConcurrentHashMap((Map)reader
/* 272 */                 .nextObjectOrNull());
/* 273 */             if (deserializedData != null) {
/* 274 */               event.data = deserializedData;
/*     */             }
/*     */             continue;
/*     */         } 
/* 278 */         if (payloadUnknown == null) {
/* 279 */           payloadUnknown = new ConcurrentHashMap<>();
/*     */         }
/* 281 */         reader.nextUnknown(logger, payloadUnknown, nextName);
/*     */       } 
/*     */       
/* 284 */       event.setPayloadUnknown(payloadUnknown);
/* 285 */       reader.endObject();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\rrweb\RRWebSpanEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */