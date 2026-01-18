/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.DateUtils;
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryBaseEvent;
/*     */ import io.sentry.SentryTracer;
/*     */ import io.sentry.Span;
/*     */ import io.sentry.SpanContext;
/*     */ import io.sentry.SpanStatus;
/*     */ import io.sentry.TracesSamplingDecision;
/*     */ import io.sentry.featureflags.IFeatureFlagBuffer;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SentryTransaction
/*     */   extends SentryBaseEvent
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private String transaction;
/*     */   @NotNull
/*     */   private Double startTimestamp;
/*     */   @Nullable
/*     */   private Double timestamp;
/*     */   @NotNull
/*  45 */   private final List<SentrySpan> spans = new ArrayList<>();
/*     */   
/*     */   @NotNull
/*  48 */   private final String type = "transaction";
/*     */   
/*     */   @NotNull
/*  51 */   private final Map<String, MeasurementValue> measurements = new HashMap<>();
/*     */   
/*     */   @NotNull
/*     */   private TransactionInfo transactionInfo;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public SentryTransaction(@NotNull SentryTracer sentryTracer) {
/*  59 */     super(sentryTracer.getEventId());
/*  60 */     Objects.requireNonNull(sentryTracer, "sentryTracer is required");
/*     */     
/*  62 */     this.startTimestamp = Double.valueOf(DateUtils.nanosToSeconds(sentryTracer.getStartDate().nanoTimestamp()));
/*     */     
/*  64 */     this
/*  65 */       .timestamp = Double.valueOf(DateUtils.nanosToSeconds(sentryTracer
/*     */           
/*  67 */           .getStartDate()
/*  68 */           .laterDateNanosTimestampByDiff(sentryTracer.getFinishDate())));
/*  69 */     this.transaction = sentryTracer.getName();
/*  70 */     for (Span span : sentryTracer.getChildren()) {
/*  71 */       if (Boolean.TRUE.equals(span.isSampled())) {
/*  72 */         this.spans.add(new SentrySpan(span));
/*     */       }
/*     */     } 
/*  75 */     Contexts contexts = getContexts();
/*     */     
/*  77 */     contexts.putAll(sentryTracer.getContexts());
/*     */     
/*  79 */     SpanContext tracerContext = sentryTracer.getSpanContext();
/*  80 */     Map<String, Object> data = sentryTracer.getData();
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
/*  91 */     SpanContext tracerContextToSend = new SpanContext(tracerContext.getTraceId(), tracerContext.getSpanId(), tracerContext.getParentSpanId(), tracerContext.getOperation(), tracerContext.getDescription(), tracerContext.getSamplingDecision(), tracerContext.getStatus(), tracerContext.getOrigin());
/*     */     
/*  93 */     for (Map.Entry<String, String> tag : (Iterable<Map.Entry<String, String>>)tracerContext.getTags().entrySet()) {
/*  94 */       setTag(tag.getKey(), tag.getValue());
/*     */     }
/*     */     
/*  97 */     if (data != null) {
/*  98 */       for (Map.Entry<String, Object> tag : data.entrySet()) {
/*  99 */         tracerContextToSend.setData(tag.getKey(), tag.getValue());
/*     */       }
/*     */     }
/*     */     
/* 103 */     IFeatureFlagBuffer featureFlagBuffer = tracerContext.getFeatureFlagBuffer();
/* 104 */     FeatureFlags featureFlags = featureFlagBuffer.getFeatureFlags();
/* 105 */     if (featureFlags != null) {
/* 106 */       for (FeatureFlag featureFlag : featureFlags.getValues()) {
/* 107 */         tracerContextToSend.setData("flag.evaluation." + featureFlag
/* 108 */             .getFlag(), featureFlag.getResult());
/*     */       }
/*     */     }
/*     */     
/* 112 */     contexts.setTrace(tracerContextToSend);
/*     */     
/* 114 */     this.transactionInfo = new TransactionInfo(sentryTracer.getTransactionNameSource().apiName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public SentryTransaction(@Nullable String transaction, @NotNull Double startTimestamp, @Nullable Double timestamp, @NotNull List<SentrySpan> spans, @NotNull Map<String, MeasurementValue> measurements, @NotNull TransactionInfo transactionInfo) {
/* 125 */     this.transaction = transaction;
/* 126 */     this.startTimestamp = startTimestamp;
/* 127 */     this.timestamp = timestamp;
/* 128 */     this.spans.addAll(spans);
/* 129 */     this.measurements.putAll(measurements);
/* 130 */     for (SentrySpan span : spans) {
/* 131 */       this.measurements.putAll(span.getMeasurements());
/*     */     }
/* 133 */     this.transactionInfo = transactionInfo;
/*     */   }
/*     */   @NotNull
/*     */   public List<SentrySpan> getSpans() {
/* 137 */     return this.spans;
/*     */   }
/*     */   
/*     */   public boolean isFinished() {
/* 141 */     return (this.timestamp != null);
/*     */   }
/*     */   @Nullable
/*     */   public String getTransaction() {
/* 145 */     return this.transaction;
/*     */   }
/*     */   @NotNull
/*     */   public Double getStartTimestamp() {
/* 149 */     return this.startTimestamp;
/*     */   }
/*     */   @Nullable
/*     */   public Double getTimestamp() {
/* 153 */     return this.timestamp;
/*     */   }
/*     */   @NotNull
/*     */   public String getType() {
/* 157 */     return "transaction";
/*     */   }
/*     */   @Nullable
/*     */   public SpanStatus getStatus() {
/* 161 */     SpanContext trace = getContexts().getTrace();
/* 162 */     return (trace != null) ? trace.getStatus() : null;
/*     */   }
/*     */   
/*     */   public boolean isSampled() {
/* 166 */     TracesSamplingDecision samplingDecsion = getSamplingDecision();
/* 167 */     if (samplingDecsion == null) {
/* 168 */       return false;
/*     */     }
/*     */     
/* 171 */     return samplingDecsion.getSampled().booleanValue();
/*     */   }
/*     */   @Nullable
/*     */   public TracesSamplingDecision getSamplingDecision() {
/* 175 */     SpanContext trace = getContexts().getTrace();
/* 176 */     if (trace == null) {
/* 177 */       return null;
/*     */     }
/*     */     
/* 180 */     return trace.getSamplingDecision();
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, MeasurementValue> getMeasurements() {
/* 184 */     return this.measurements;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String TRANSACTION = "transaction";
/*     */     
/*     */     public static final String START_TIMESTAMP = "start_timestamp";
/*     */     
/*     */     public static final String TIMESTAMP = "timestamp";
/*     */     public static final String SPANS = "spans";
/*     */     public static final String TYPE = "type";
/*     */     public static final String MEASUREMENTS = "measurements";
/*     */     public static final String TRANSACTION_INFO = "transaction_info";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 202 */     writer.beginObject();
/* 203 */     if (this.transaction != null) {
/* 204 */       writer.name("transaction").value(this.transaction);
/*     */     }
/* 206 */     writer.name("start_timestamp").value(logger, DateUtils.doubleToBigDecimal(this.startTimestamp));
/* 207 */     if (this.timestamp != null) {
/* 208 */       writer.name("timestamp").value(logger, DateUtils.doubleToBigDecimal(this.timestamp));
/*     */     }
/* 210 */     if (!this.spans.isEmpty()) {
/* 211 */       writer.name("spans").value(logger, this.spans);
/*     */     }
/* 213 */     writer.name("type").value("transaction");
/* 214 */     if (!this.measurements.isEmpty()) {
/* 215 */       writer.name("measurements").value(logger, this.measurements);
/*     */     }
/* 217 */     writer.name("transaction_info").value(logger, this.transactionInfo);
/* 218 */     (new SentryBaseEvent.Serializer()).serialize(this, writer, logger);
/* 219 */     if (this.unknown != null) {
/* 220 */       for (String key : this.unknown.keySet()) {
/* 221 */         Object value = this.unknown.get(key);
/* 222 */         writer.name(key);
/* 223 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 226 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 232 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 237 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryTransaction>
/*     */   {
/*     */     @NotNull
/*     */     public SentryTransaction deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 246 */       reader.beginObject();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 256 */       SentryTransaction transaction = new SentryTransaction("", Double.valueOf(0.0D), null, new ArrayList<>(), new HashMap<>(), new TransactionInfo(TransactionNameSource.CUSTOM.apiName()));
/* 257 */       Map<String, Object> unknown = null;
/*     */       
/* 259 */       SentryBaseEvent.Deserializer baseEventDeserializer = new SentryBaseEvent.Deserializer();
/*     */       
/* 261 */       while (reader.peek() == JsonToken.NAME) {
/* 262 */         List<SentrySpan> deserializedSpans; Map<String, MeasurementValue> deserializedMeasurements; String nextName = reader.nextName();
/* 263 */         switch (nextName) {
/*     */           case "transaction":
/* 265 */             transaction.transaction = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "start_timestamp":
/*     */             try {
/* 269 */               Double deserializedStartTimestamp = reader.nextDoubleOrNull();
/* 270 */               if (deserializedStartTimestamp != null) {
/* 271 */                 transaction.startTimestamp = deserializedStartTimestamp;
/*     */               }
/* 273 */             } catch (NumberFormatException e) {
/* 274 */               Date date = reader.nextDateOrNull(logger);
/* 275 */               if (date != null) {
/* 276 */                 transaction.startTimestamp = Double.valueOf(DateUtils.dateToSeconds(date));
/*     */               }
/*     */             } 
/*     */             continue;
/*     */           case "timestamp":
/*     */             try {
/* 282 */               Double deserializedTimestamp = reader.nextDoubleOrNull();
/* 283 */               if (deserializedTimestamp != null) {
/* 284 */                 transaction.timestamp = deserializedTimestamp;
/*     */               }
/* 286 */             } catch (NumberFormatException e) {
/* 287 */               Date date = reader.nextDateOrNull(logger);
/* 288 */               if (date != null) {
/* 289 */                 transaction.timestamp = Double.valueOf(DateUtils.dateToSeconds(date));
/*     */               }
/*     */             } 
/*     */             continue;
/*     */           
/*     */           case "spans":
/* 295 */             deserializedSpans = reader.nextListOrNull(logger, new SentrySpan.Deserializer());
/* 296 */             if (deserializedSpans != null) {
/* 297 */               transaction.spans.addAll(deserializedSpans);
/*     */             }
/*     */             continue;
/*     */           case "type":
/* 301 */             reader.nextString();
/*     */             continue;
/*     */           
/*     */           case "measurements":
/* 305 */             deserializedMeasurements = reader.nextMapOrNull(logger, new MeasurementValue.Deserializer());
/* 306 */             if (deserializedMeasurements != null) {
/* 307 */               transaction.measurements.putAll(deserializedMeasurements);
/*     */             }
/*     */             continue;
/*     */           case "transaction_info":
/* 311 */             transaction.transactionInfo = (new TransactionInfo.Deserializer())
/* 312 */               .deserialize(reader, logger);
/*     */             continue;
/*     */         } 
/* 315 */         if (!baseEventDeserializer.deserializeValue(transaction, nextName, reader, logger)) {
/* 316 */           if (unknown == null) {
/* 317 */             unknown = new ConcurrentHashMap<>();
/*     */           }
/* 319 */           reader.nextUnknown(logger, unknown, nextName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 324 */       transaction.setUnknown(unknown);
/* 325 */       reader.endObject();
/* 326 */       return transaction;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\SentryTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */