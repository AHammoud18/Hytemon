/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class SentryLogEvent
/*     */   implements JsonUnknown, JsonSerializable {
/*     */   @NotNull
/*     */   private SentryId traceId;
/*     */   @NotNull
/*     */   private Double timestamp;
/*     */   @NotNull
/*     */   private String body;
/*     */   @NotNull
/*     */   private SentryLogLevel level;
/*     */   @Nullable
/*     */   private Integer severityNumber;
/*     */   @Nullable
/*     */   private Map<String, SentryLogEventAttributeValue> attributes;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public SentryLogEvent(@NotNull SentryId traceId, @NotNull SentryDate timestamp, @NotNull String body, @NotNull SentryLogLevel level) {
/*  29 */     this(traceId, Double.valueOf(DateUtils.nanosToSeconds(timestamp.nanoTimestamp())), body, level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SentryLogEvent(@NotNull SentryId traceId, @NotNull Double timestamp, @NotNull String body, @NotNull SentryLogLevel level) {
/*  37 */     this.traceId = traceId;
/*  38 */     this.timestamp = timestamp;
/*  39 */     this.body = body;
/*  40 */     this.level = level;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Double getTimestamp() {
/*  45 */     return this.timestamp;
/*     */   }
/*     */   
/*     */   public void setTimestamp(@NotNull Double timestamp) {
/*  49 */     this.timestamp = timestamp;
/*     */   }
/*     */   @NotNull
/*     */   public String getBody() {
/*  53 */     return this.body;
/*     */   }
/*     */   
/*     */   public void setBody(@NotNull String body) {
/*  57 */     this.body = body;
/*     */   }
/*     */   @NotNull
/*     */   public SentryLogLevel getLevel() {
/*  61 */     return this.level;
/*     */   }
/*     */   
/*     */   public void setLevel(@NotNull SentryLogLevel level) {
/*  65 */     this.level = level;
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, SentryLogEventAttributeValue> getAttributes() {
/*  69 */     return this.attributes;
/*     */   }
/*     */   
/*     */   public void setAttributes(@Nullable Map<String, SentryLogEventAttributeValue> attributes) {
/*  73 */     this.attributes = attributes;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttribute(@Nullable String key, @Nullable SentryLogEventAttributeValue value) {
/*  78 */     if (key == null) {
/*     */       return;
/*     */     }
/*  81 */     if (this.attributes == null) {
/*  82 */       this.attributes = new HashMap<>();
/*     */     }
/*  84 */     this.attributes.put(key, value);
/*     */   }
/*     */   @Nullable
/*     */   public Integer getSeverityNumber() {
/*  88 */     return this.severityNumber;
/*     */   }
/*     */   
/*     */   public void setSeverityNumber(@Nullable Integer severityNumber) {
/*  92 */     this.severityNumber = severityNumber;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String TIMESTAMP = "timestamp";
/*     */     
/*     */     public static final String TRACE_ID = "trace_id";
/*     */     
/*     */     public static final String LEVEL = "level";
/*     */     public static final String SEVERITY_NUMBER = "severity_number";
/*     */     public static final String BODY = "body";
/*     */     public static final String ATTRIBUTES = "attributes";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 109 */     writer.beginObject();
/* 110 */     writer.name("timestamp").value(logger, DateUtils.doubleToBigDecimal(this.timestamp));
/* 111 */     writer.name("trace_id").value(logger, this.traceId);
/* 112 */     writer.name("body").value(this.body);
/* 113 */     writer.name("level").value(logger, this.level);
/* 114 */     if (this.severityNumber != null) {
/* 115 */       writer.name("severity_number").value(logger, this.severityNumber);
/*     */     }
/* 117 */     if (this.attributes != null) {
/* 118 */       writer.name("attributes").value(logger, this.attributes);
/*     */     }
/*     */     
/* 121 */     if (this.unknown != null) {
/* 122 */       for (String key : this.unknown.keySet()) {
/* 123 */         Object value = this.unknown.get(key);
/* 124 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 127 */     writer.endObject();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 132 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 137 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryLogEvent>
/*     */   {
/*     */     @NotNull
/*     */     public SentryLogEvent deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 146 */       Map<String, Object> unknown = null;
/* 147 */       SentryId traceId = null;
/* 148 */       Double timestamp = null;
/* 149 */       String body = null;
/* 150 */       SentryLogLevel level = null;
/* 151 */       Integer severityNumber = null;
/* 152 */       Map<String, SentryLogEventAttributeValue> attributes = null;
/*     */       
/* 154 */       reader.beginObject();
/* 155 */       while (reader.peek() == JsonToken.NAME) {
/* 156 */         String nextName = reader.nextName();
/* 157 */         switch (nextName) {
/*     */           case "trace_id":
/* 159 */             traceId = reader.<SentryId>nextOrNull(logger, (JsonDeserializer<SentryId>)new SentryId.Deserializer());
/*     */             continue;
/*     */           case "timestamp":
/* 162 */             timestamp = reader.nextDoubleOrNull();
/*     */             continue;
/*     */           case "body":
/* 165 */             body = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "level":
/* 168 */             level = reader.<SentryLogLevel>nextOrNull(logger, new SentryLogLevel.Deserializer());
/*     */             continue;
/*     */           case "severity_number":
/* 171 */             severityNumber = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           
/*     */           case "attributes":
/* 175 */             attributes = reader.nextMapOrNull(logger, new SentryLogEventAttributeValue.Deserializer());
/*     */             continue;
/*     */         } 
/* 178 */         if (unknown == null) {
/* 179 */           unknown = new HashMap<>();
/*     */         }
/* 181 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 185 */       reader.endObject();
/*     */       
/* 187 */       if (traceId == null) {
/* 188 */         String message = "Missing required field \"trace_id\"";
/* 189 */         Exception exception = new IllegalStateException(message);
/* 190 */         logger.log(SentryLevel.ERROR, message, exception);
/* 191 */         throw exception;
/*     */       } 
/*     */       
/* 194 */       if (timestamp == null) {
/* 195 */         String message = "Missing required field \"timestamp\"";
/* 196 */         Exception exception = new IllegalStateException(message);
/* 197 */         logger.log(SentryLevel.ERROR, message, exception);
/* 198 */         throw exception;
/*     */       } 
/*     */       
/* 201 */       if (body == null) {
/* 202 */         String message = "Missing required field \"body\"";
/* 203 */         Exception exception = new IllegalStateException(message);
/* 204 */         logger.log(SentryLevel.ERROR, message, exception);
/* 205 */         throw exception;
/*     */       } 
/*     */       
/* 208 */       if (level == null) {
/* 209 */         String message = "Missing required field \"level\"";
/* 210 */         Exception exception = new IllegalStateException(message);
/* 211 */         logger.log(SentryLevel.ERROR, message, exception);
/* 212 */         throw exception;
/*     */       } 
/*     */       
/* 215 */       SentryLogEvent logEvent = new SentryLogEvent(traceId, timestamp, body, level);
/*     */       
/* 217 */       logEvent.setAttributes(attributes);
/* 218 */       logEvent.setSeverityNumber(severityNumber);
/* 219 */       logEvent.setUnknown(unknown);
/*     */       
/* 221 */       return logEvent;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryLogEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */