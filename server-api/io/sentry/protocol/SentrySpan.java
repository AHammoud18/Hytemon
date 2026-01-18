/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.DateUtils;
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.Span;
/*     */ import io.sentry.SpanId;
/*     */ import io.sentry.SpanStatus;
/*     */ import io.sentry.featureflags.IFeatureFlagBuffer;
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class SentrySpan
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @NotNull
/*     */   private final Double startTimestamp;
/*     */   @Nullable
/*     */   private final Double timestamp;
/*     */   @NotNull
/*     */   private final SentryId traceId;
/*     */   @NotNull
/*     */   private final SpanId spanId;
/*     */   @Nullable
/*     */   private final SpanId parentSpanId;
/*     */   @NotNull
/*     */   private final String op;
/*     */   
/*     */   public SentrySpan(@NotNull Span span) {
/*  50 */     this(span, span.getData()); } @Nullable private final String description; @Nullable private final SpanStatus status; @Nullable private final String origin; @NotNull
/*     */   private final Map<String, String> tags; @Nullable
/*     */   private Map<String, Object> data; @NotNull
/*     */   private final Map<String, MeasurementValue> measurements; @Nullable
/*     */   private Map<String, Object> unknown; @Internal
/*  55 */   public SentrySpan(@NotNull Span span, @Nullable Map<String, Object> data) { Objects.requireNonNull(span, "span is required");
/*  56 */     this.description = span.getDescription();
/*  57 */     this.op = span.getOperation();
/*  58 */     this.spanId = span.getSpanId();
/*  59 */     this.parentSpanId = span.getParentSpanId();
/*  60 */     this.traceId = span.getTraceId();
/*  61 */     this.status = span.getStatus();
/*  62 */     this.origin = span.getSpanContext().getOrigin();
/*  63 */     Map<String, String> tagsCopy = CollectionUtils.newConcurrentHashMap(span.getTags());
/*  64 */     this.tags = (tagsCopy != null) ? tagsCopy : new ConcurrentHashMap<>();
/*     */     
/*  66 */     Map<String, MeasurementValue> measurementsCopy = CollectionUtils.newConcurrentHashMap(span.getMeasurements());
/*  67 */     this.measurements = (measurementsCopy != null) ? measurementsCopy : new ConcurrentHashMap<>();
/*     */     
/*  69 */     this
/*     */ 
/*     */       
/*  72 */       .timestamp = (span.getFinishDate() == null) ? null : Double.valueOf(DateUtils.nanosToSeconds(span
/*  73 */           .getStartDate().laterDateNanosTimestampByDiff(span.getFinishDate())));
/*     */     
/*  75 */     this.startTimestamp = Double.valueOf(DateUtils.nanosToSeconds(span.getStartDate().nanoTimestamp()));
/*  76 */     this.data = data;
/*     */     
/*  78 */     IFeatureFlagBuffer featureFlagBuffer = span.getSpanContext().getFeatureFlagBuffer();
/*  79 */     FeatureFlags featureFlags = featureFlagBuffer.getFeatureFlags();
/*  80 */     if (featureFlags != null) {
/*  81 */       if (this.data == null) {
/*  82 */         this.data = new HashMap<>();
/*     */       }
/*  84 */       for (FeatureFlag featureFlag : featureFlags.getValues()) {
/*  85 */         this.data.put("flag.evaluation." + featureFlag.getFlag(), featureFlag.getResult());
/*     */       }
/*     */     }  }
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
/*     */   @Internal
/*     */   public SentrySpan(@NotNull Double startTimestamp, @Nullable Double timestamp, @NotNull SentryId traceId, @NotNull SpanId spanId, @Nullable SpanId parentSpanId, @NotNull String op, @Nullable String description, @Nullable SpanStatus status, @Nullable String origin, @NotNull Map<String, String> tags, @NotNull Map<String, MeasurementValue> measurements, @Nullable Map<String, Object> data) {
/* 104 */     this.startTimestamp = startTimestamp;
/* 105 */     this.timestamp = timestamp;
/* 106 */     this.traceId = traceId;
/* 107 */     this.spanId = spanId;
/* 108 */     this.parentSpanId = parentSpanId;
/* 109 */     this.op = op;
/* 110 */     this.description = description;
/* 111 */     this.status = status;
/* 112 */     this.origin = origin;
/* 113 */     this.tags = tags;
/* 114 */     this.measurements = measurements;
/* 115 */     this.data = data;
/*     */   }
/*     */   
/*     */   public boolean isFinished() {
/* 119 */     return (this.timestamp != null);
/*     */   }
/*     */   @NotNull
/*     */   public Double getStartTimestamp() {
/* 123 */     return this.startTimestamp;
/*     */   }
/*     */   @Nullable
/*     */   public Double getTimestamp() {
/* 127 */     return this.timestamp;
/*     */   }
/*     */   @NotNull
/*     */   public SentryId getTraceId() {
/* 131 */     return this.traceId;
/*     */   }
/*     */   @NotNull
/*     */   public SpanId getSpanId() {
/* 135 */     return this.spanId;
/*     */   }
/*     */   @Nullable
/*     */   public SpanId getParentSpanId() {
/* 139 */     return this.parentSpanId;
/*     */   }
/*     */   @NotNull
/*     */   public String getOp() {
/* 143 */     return this.op;
/*     */   }
/*     */   @Nullable
/*     */   public String getDescription() {
/* 147 */     return this.description;
/*     */   }
/*     */   @Nullable
/*     */   public SpanStatus getStatus() {
/* 151 */     return this.status;
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, String> getTags() {
/* 155 */     return this.tags;
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, Object> getData() {
/* 159 */     return this.data;
/*     */   }
/*     */   
/*     */   public void setData(@Nullable Map<String, Object> data) {
/* 163 */     this.data = data;
/*     */   }
/*     */   @Nullable
/*     */   public String getOrigin() {
/* 167 */     return this.origin;
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, MeasurementValue> getMeasurements() {
/* 171 */     return this.measurements;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String START_TIMESTAMP = "start_timestamp";
/*     */     
/*     */     public static final String TIMESTAMP = "timestamp";
/*     */     
/*     */     public static final String TRACE_ID = "trace_id";
/*     */     public static final String SPAN_ID = "span_id";
/*     */     public static final String PARENT_SPAN_ID = "parent_span_id";
/*     */     public static final String OP = "op";
/*     */     public static final String DESCRIPTION = "description";
/*     */     public static final String STATUS = "status";
/*     */     public static final String ORIGIN = "origin";
/*     */     public static final String TAGS = "tags";
/*     */     public static final String MEASUREMENTS = "measurements";
/*     */     public static final String DATA = "data";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 194 */     writer.beginObject();
/* 195 */     writer.name("start_timestamp").value(logger, doubleToBigDecimal(this.startTimestamp));
/* 196 */     if (this.timestamp != null) {
/* 197 */       writer.name("timestamp").value(logger, doubleToBigDecimal(this.timestamp));
/*     */     }
/* 199 */     writer.name("trace_id").value(logger, this.traceId);
/* 200 */     writer.name("span_id").value(logger, this.spanId);
/* 201 */     if (this.parentSpanId != null) {
/* 202 */       writer.name("parent_span_id").value(logger, this.parentSpanId);
/*     */     }
/* 204 */     writer.name("op").value(this.op);
/* 205 */     if (this.description != null) {
/* 206 */       writer.name("description").value(this.description);
/*     */     }
/* 208 */     if (this.status != null) {
/* 209 */       writer.name("status").value(logger, this.status);
/*     */     }
/* 211 */     if (this.origin != null) {
/* 212 */       writer.name("origin").value(logger, this.origin);
/*     */     }
/* 214 */     if (!this.tags.isEmpty()) {
/* 215 */       writer.name("tags").value(logger, this.tags);
/*     */     }
/* 217 */     if (this.data != null) {
/* 218 */       writer.name("data").value(logger, this.data);
/*     */     }
/* 220 */     if (!this.measurements.isEmpty()) {
/* 221 */       writer.name("measurements").value(logger, this.measurements);
/*     */     }
/* 223 */     if (this.unknown != null) {
/* 224 */       for (String key : this.unknown.keySet()) {
/* 225 */         Object value = this.unknown.get(key);
/* 226 */         writer.name(key);
/* 227 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 230 */     writer.endObject();
/*     */   }
/*     */   @NotNull
/*     */   private BigDecimal doubleToBigDecimal(@NotNull Double value) {
/* 234 */     return BigDecimal.valueOf(value.doubleValue()).setScale(6, RoundingMode.DOWN);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 240 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 245 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentrySpan>
/*     */   {
/*     */     @NotNull
/*     */     public SentrySpan deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 254 */       reader.beginObject();
/*     */       
/* 256 */       Double startTimestamp = null;
/* 257 */       Double timestamp = null;
/* 258 */       SentryId traceId = null;
/* 259 */       SpanId spanId = null;
/* 260 */       SpanId parentSpanId = null;
/* 261 */       String op = null;
/* 262 */       String description = null;
/* 263 */       SpanStatus status = null;
/* 264 */       String origin = null;
/* 265 */       Map<String, String> tags = null;
/* 266 */       Map<String, MeasurementValue> measurements = null;
/* 267 */       Map<String, Object> data = null;
/*     */       
/* 269 */       Map<String, Object> unknown = null;
/* 270 */       while (reader.peek() == JsonToken.NAME) {
/* 271 */         String nextName = reader.nextName();
/* 272 */         switch (nextName) {
/*     */           case "start_timestamp":
/*     */             try {
/* 275 */               startTimestamp = reader.nextDoubleOrNull();
/* 276 */             } catch (NumberFormatException e) {
/* 277 */               Date date = reader.nextDateOrNull(logger);
/* 278 */               startTimestamp = (date != null) ? Double.valueOf(DateUtils.dateToSeconds(date)) : null;
/*     */             } 
/*     */             continue;
/*     */           case "timestamp":
/*     */             try {
/* 283 */               timestamp = reader.nextDoubleOrNull();
/* 284 */             } catch (NumberFormatException e) {
/* 285 */               Date date = reader.nextDateOrNull(logger);
/* 286 */               timestamp = (date != null) ? Double.valueOf(DateUtils.dateToSeconds(date)) : null;
/*     */             } 
/*     */             continue;
/*     */           case "trace_id":
/* 290 */             traceId = (new SentryId.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */           case "span_id":
/* 293 */             spanId = (new SpanId.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */           case "parent_span_id":
/* 296 */             parentSpanId = (SpanId)reader.nextOrNull(logger, (JsonDeserializer)new SpanId.Deserializer());
/*     */             continue;
/*     */           case "op":
/* 299 */             op = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "description":
/* 302 */             description = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "status":
/* 305 */             status = (SpanStatus)reader.nextOrNull(logger, (JsonDeserializer)new SpanStatus.Deserializer());
/*     */             continue;
/*     */           case "origin":
/* 308 */             origin = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "tags":
/* 311 */             tags = (Map<String, String>)reader.nextObjectOrNull();
/*     */             continue;
/*     */           case "data":
/* 314 */             data = (Map<String, Object>)reader.nextObjectOrNull();
/*     */             continue;
/*     */           case "measurements":
/* 317 */             measurements = reader.nextMapOrNull(logger, new MeasurementValue.Deserializer());
/*     */             continue;
/*     */         } 
/* 320 */         if (unknown == null) {
/* 321 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 323 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 327 */       if (startTimestamp == null) {
/* 328 */         throw missingRequiredFieldException("start_timestamp", logger);
/*     */       }
/* 330 */       if (traceId == null) {
/* 331 */         throw missingRequiredFieldException("trace_id", logger);
/*     */       }
/* 333 */       if (spanId == null) {
/* 334 */         throw missingRequiredFieldException("span_id", logger);
/*     */       }
/* 336 */       if (op == null) {
/* 337 */         throw missingRequiredFieldException("op", logger);
/*     */       }
/* 339 */       if (tags == null) {
/* 340 */         tags = new HashMap<>();
/*     */       }
/* 342 */       if (measurements == null) {
/* 343 */         measurements = new HashMap<>();
/*     */       }
/* 345 */       SentrySpan sentrySpan = new SentrySpan(startTimestamp, timestamp, traceId, spanId, parentSpanId, op, description, status, origin, tags, measurements, data);
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
/* 359 */       sentrySpan.setUnknown(unknown);
/* 360 */       reader.endObject();
/* 361 */       return sentrySpan;
/*     */     }
/*     */     
/*     */     private Exception missingRequiredFieldException(String field, ILogger logger) {
/* 365 */       String message = "Missing required field \"" + field + "\"";
/* 366 */       Exception exception = new IllegalStateException(message);
/* 367 */       logger.log(SentryLevel.ERROR, message, exception);
/* 368 */       return exception;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\SentrySpan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */