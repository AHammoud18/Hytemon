/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.featureflags.IFeatureFlagBuffer;
/*     */ import io.sentry.featureflags.SpanFeatureFlagBuffer;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.util.thread.IThreadChecker;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.jetbrains.annotations.TestOnly;
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
/*     */ public class SpanContext
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String TYPE = "trace";
/*     */   public static final String DEFAULT_ORIGIN = "manual";
/*     */   @NotNull
/*     */   private final SentryId traceId;
/*     */   @NotNull
/*     */   private final SpanId spanId;
/*     */   @Nullable
/*     */   private SpanId parentSpanId;
/*     */   @Nullable
/*     */   private transient TracesSamplingDecision samplingDecision;
/*     */   @NotNull
/*     */   protected String op;
/*     */   @Nullable
/*     */   protected String description;
/*     */   @Nullable
/*     */   protected SpanStatus status;
/*     */   @NotNull
/*  48 */   protected Map<String, String> tags = new ConcurrentHashMap<>();
/*     */   @Nullable
/*  50 */   protected String origin = "manual";
/*     */   @NotNull
/*  52 */   protected Map<String, Object> data = new ConcurrentHashMap<>();
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   @NotNull
/*  56 */   private Instrumenter instrumenter = Instrumenter.SENTRY;
/*     */   @Nullable
/*     */   protected Baggage baggage;
/*     */   @NotNull
/*  60 */   protected IFeatureFlagBuffer featureFlags = SpanFeatureFlagBuffer.create();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*  66 */   private SentryId profilerId = SentryId.EMPTY_ID;
/*     */ 
/*     */   
/*     */   public SpanContext(@NotNull String operation, @Nullable TracesSamplingDecision samplingDecision) {
/*  70 */     this(new SentryId(), new SpanId(), operation, null, samplingDecision);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpanContext(@NotNull String operation) {
/*  79 */     this(new SentryId(), new SpanId(), operation, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpanContext(@NotNull SentryId traceId, @NotNull SpanId spanId, @NotNull String operation, @Nullable SpanId parentSpanId, @Nullable TracesSamplingDecision samplingDecision) {
/*  88 */     this(traceId, spanId, parentSpanId, operation, null, samplingDecision, null, "manual");
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
/*     */   @Internal
/*     */   public SpanContext(@NotNull SentryId traceId, @NotNull SpanId spanId, @Nullable SpanId parentSpanId, @NotNull String operation, @Nullable String description, @Nullable TracesSamplingDecision samplingDecision, @Nullable SpanStatus status, @Nullable String origin) {
/* 101 */     this.traceId = (SentryId)Objects.requireNonNull(traceId, "traceId is required");
/* 102 */     this.spanId = (SpanId)Objects.requireNonNull(spanId, "spanId is required");
/* 103 */     this.op = (String)Objects.requireNonNull(operation, "operation is required");
/* 104 */     this.parentSpanId = parentSpanId;
/* 105 */     this.description = description;
/* 106 */     this.status = status;
/* 107 */     this.origin = origin;
/* 108 */     setSamplingDecision(samplingDecision);
/*     */     
/* 110 */     IThreadChecker threadChecker = ScopesAdapter.getInstance().getOptions().getThreadChecker();
/* 111 */     this.data.put("thread.id", 
/* 112 */         String.valueOf(threadChecker.currentThreadSystemId()));
/* 113 */     this.data.put("thread.name", threadChecker.getCurrentThreadName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpanContext(@NotNull SpanContext spanContext) {
/* 122 */     this.traceId = spanContext.traceId;
/* 123 */     this.spanId = spanContext.spanId;
/* 124 */     this.parentSpanId = spanContext.parentSpanId;
/* 125 */     setSamplingDecision(spanContext.samplingDecision);
/* 126 */     this.op = spanContext.op;
/* 127 */     this.description = spanContext.description;
/* 128 */     this.status = spanContext.status;
/* 129 */     Map<String, String> copiedTags = CollectionUtils.newConcurrentHashMap(spanContext.tags);
/* 130 */     if (copiedTags != null) {
/* 131 */       this.tags = copiedTags;
/*     */     }
/*     */     
/* 134 */     Map<String, Object> copiedUnknown = CollectionUtils.newConcurrentHashMap(spanContext.unknown);
/* 135 */     if (copiedUnknown != null) {
/* 136 */       this.unknown = copiedUnknown;
/*     */     }
/* 138 */     this.baggage = spanContext.baggage;
/* 139 */     Map<String, Object> copiedData = CollectionUtils.newConcurrentHashMap(spanContext.data);
/* 140 */     if (copiedData != null) {
/* 141 */       this.data = copiedData;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setOperation(@NotNull String operation) {
/* 146 */     this.op = (String)Objects.requireNonNull(operation, "operation is required");
/*     */   }
/*     */   
/*     */   public void setTag(@Nullable String name, @Nullable String value) {
/* 150 */     if (name == null) {
/*     */       return;
/*     */     }
/* 153 */     if (value == null) {
/* 154 */       this.tags.remove(name);
/*     */     } else {
/* 156 */       this.tags.put(name, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDescription(@Nullable String description) {
/* 161 */     this.description = description;
/*     */   }
/*     */   
/*     */   public void setStatus(@Nullable SpanStatus status) {
/* 165 */     this.status = status;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId getTraceId() {
/* 170 */     return this.traceId;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SpanId getSpanId() {
/* 175 */     return this.spanId;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @TestOnly
/*     */   public SpanId getParentSpanId() {
/* 181 */     return this.parentSpanId;
/*     */   }
/*     */   @NotNull
/*     */   public String getOperation() {
/* 185 */     return this.op;
/*     */   }
/*     */   @Nullable
/*     */   public String getDescription() {
/* 189 */     return this.description;
/*     */   }
/*     */   @Nullable
/*     */   public SpanStatus getStatus() {
/* 193 */     return this.status;
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, String> getTags() {
/* 197 */     return this.tags;
/*     */   }
/*     */   @Nullable
/*     */   public TracesSamplingDecision getSamplingDecision() {
/* 201 */     return this.samplingDecision;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean getSampled() {
/* 205 */     if (this.samplingDecision == null) {
/* 206 */       return null;
/*     */     }
/*     */     
/* 209 */     return this.samplingDecision.getSampled();
/*     */   }
/*     */   @Nullable
/*     */   public Boolean getProfileSampled() {
/* 213 */     if (this.samplingDecision == null) {
/* 214 */       return null;
/*     */     }
/*     */     
/* 217 */     return this.samplingDecision.getProfileSampled();
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setSampled(@Nullable Boolean sampled) {
/* 222 */     if (sampled == null) {
/* 223 */       setSamplingDecision(null);
/*     */     } else {
/* 225 */       setSamplingDecision(new TracesSamplingDecision(sampled));
/*     */     } 
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setSampled(@Nullable Boolean sampled, @Nullable Boolean profileSampled) {
/* 231 */     if (sampled == null) {
/* 232 */       setSamplingDecision(null);
/* 233 */     } else if (profileSampled == null) {
/* 234 */       setSamplingDecision(new TracesSamplingDecision(sampled));
/*     */     } else {
/* 236 */       setSamplingDecision(new TracesSamplingDecision(sampled, null, profileSampled, null));
/*     */     } 
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setSamplingDecision(@Nullable TracesSamplingDecision samplingDecision) {
/* 242 */     this.samplingDecision = samplingDecision;
/* 243 */     if (this.baggage != null)
/* 244 */       this.baggage.setValuesFromSamplingDecision(this.samplingDecision); 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getOrigin() {
/* 249 */     return this.origin;
/*     */   }
/*     */   
/*     */   public void setOrigin(@Nullable String origin) {
/* 253 */     this.origin = origin;
/*     */   }
/*     */   @NotNull
/*     */   public Instrumenter getInstrumenter() {
/* 257 */     return this.instrumenter;
/*     */   }
/*     */   
/*     */   public void setInstrumenter(@NotNull Instrumenter instrumenter) {
/* 261 */     this.instrumenter = instrumenter;
/*     */   }
/*     */   @Nullable
/*     */   public Baggage getBaggage() {
/* 265 */     return this.baggage;
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, Object> getData() {
/* 269 */     return this.data;
/*     */   }
/*     */   
/*     */   public void setData(@Nullable String key, @Nullable Object value) {
/* 273 */     if (key == null) {
/*     */       return;
/*     */     }
/* 276 */     if (value == null) {
/* 277 */       this.data.remove(key);
/*     */     } else {
/* 279 */       this.data.put(key, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public SpanContext copyForChild(@NotNull String operation, @Nullable SpanId parentSpanId, @Nullable SpanId spanId) {
/* 288 */     return new SpanContext(this.traceId, 
/*     */         
/* 290 */         (spanId == null) ? new SpanId() : spanId, parentSpanId, operation, null, this.samplingDecision, null, "manual");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 301 */     if (this == o) return true; 
/* 302 */     if (!(o instanceof SpanContext)) return false; 
/* 303 */     SpanContext that = (SpanContext)o;
/* 304 */     return (this.traceId.equals(that.traceId) && this.spanId
/* 305 */       .equals(that.spanId) && 
/* 306 */       Objects.equals(this.parentSpanId, that.parentSpanId) && this.op
/* 307 */       .equals(that.op) && 
/* 308 */       Objects.equals(this.description, that.description) && 
/* 309 */       getStatus() == that.getStatus());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 314 */     return Objects.hash(new Object[] { this.traceId, this.spanId, this.parentSpanId, this.op, this.description, getStatus() });
/*     */   }
/*     */   @Internal
/*     */   @NotNull
/*     */   public SentryId getProfilerId() {
/* 319 */     return this.profilerId;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setProfilerId(@NotNull SentryId profilerId) {
/* 324 */     this.profilerId = profilerId;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void addFeatureFlag(@Nullable String flag, @Nullable Boolean result) {
/* 329 */     this.featureFlags.add(flag, result);
/*     */   }
/*     */   @Internal
/*     */   @NotNull
/*     */   public IFeatureFlagBuffer getFeatureFlagBuffer() {
/* 334 */     return this.featureFlags;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String TRACE_ID = "trace_id";
/*     */     
/*     */     public static final String SPAN_ID = "span_id";
/*     */     
/*     */     public static final String PARENT_SPAN_ID = "parent_span_id";
/*     */     public static final String OP = "op";
/*     */     public static final String DESCRIPTION = "description";
/*     */     public static final String STATUS = "status";
/*     */     public static final String TAGS = "tags";
/*     */     public static final String ORIGIN = "origin";
/*     */     public static final String DATA = "data";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 354 */     writer.beginObject();
/* 355 */     writer.name("trace_id");
/* 356 */     this.traceId.serialize(writer, logger);
/* 357 */     writer.name("span_id");
/* 358 */     this.spanId.serialize(writer, logger);
/* 359 */     if (this.parentSpanId != null) {
/* 360 */       writer.name("parent_span_id");
/* 361 */       this.parentSpanId.serialize(writer, logger);
/*     */     } 
/* 363 */     writer.name("op").value(this.op);
/* 364 */     if (this.description != null) {
/* 365 */       writer.name("description").value(this.description);
/*     */     }
/* 367 */     if (getStatus() != null) {
/* 368 */       writer.name("status").value(logger, getStatus());
/*     */     }
/* 370 */     if (this.origin != null) {
/* 371 */       writer.name("origin").value(logger, this.origin);
/*     */     }
/* 373 */     if (!this.tags.isEmpty()) {
/* 374 */       writer.name("tags").value(logger, this.tags);
/*     */     }
/* 376 */     if (!this.data.isEmpty()) {
/* 377 */       writer.name("data").value(logger, this.data);
/*     */     }
/* 379 */     if (this.unknown != null) {
/* 380 */       for (String key : this.unknown.keySet()) {
/* 381 */         Object value = this.unknown.get(key);
/* 382 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 385 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 391 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 396 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SpanContext>
/*     */   {
/*     */     @NotNull
/*     */     public SpanContext deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 404 */       reader.beginObject();
/* 405 */       SentryId traceId = null;
/* 406 */       SpanId spanId = null;
/* 407 */       SpanId parentSpanId = null;
/* 408 */       String op = null;
/* 409 */       String description = null;
/* 410 */       SpanStatus status = null;
/* 411 */       String origin = null;
/* 412 */       Map<String, String> tags = null;
/* 413 */       Map<String, Object> data = null;
/*     */       
/* 415 */       Map<String, Object> unknown = null;
/* 416 */       while (reader.peek() == JsonToken.NAME) {
/* 417 */         String nextName = reader.nextName();
/* 418 */         switch (nextName) {
/*     */           case "trace_id":
/* 420 */             traceId = (new SentryId.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */           case "span_id":
/* 423 */             spanId = (new SpanId.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */           case "parent_span_id":
/* 426 */             parentSpanId = reader.<SpanId>nextOrNull(logger, new SpanId.Deserializer());
/*     */             continue;
/*     */           case "op":
/* 429 */             op = reader.nextString();
/*     */             continue;
/*     */           case "description":
/* 432 */             description = reader.nextString();
/*     */             continue;
/*     */           case "status":
/* 435 */             status = reader.<SpanStatus>nextOrNull(logger, new SpanStatus.Deserializer());
/*     */             continue;
/*     */           case "origin":
/* 438 */             origin = reader.nextString();
/*     */             continue;
/*     */           
/*     */           case "tags":
/* 442 */             tags = CollectionUtils.newConcurrentHashMap((Map)reader
/* 443 */                 .nextObjectOrNull());
/*     */             continue;
/*     */           case "data":
/* 446 */             data = (Map<String, Object>)reader.nextObjectOrNull();
/*     */             continue;
/*     */         } 
/* 449 */         if (unknown == null) {
/* 450 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 452 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 457 */       if (traceId == null) {
/* 458 */         String message = "Missing required field \"trace_id\"";
/* 459 */         Exception exception = new IllegalStateException(message);
/* 460 */         logger.log(SentryLevel.ERROR, message, exception);
/* 461 */         throw exception;
/*     */       } 
/*     */       
/* 464 */       if (spanId == null) {
/* 465 */         String message = "Missing required field \"span_id\"";
/* 466 */         Exception exception = new IllegalStateException(message);
/* 467 */         logger.log(SentryLevel.ERROR, message, exception);
/* 468 */         throw exception;
/*     */       } 
/*     */       
/* 471 */       if (op == null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 478 */         op = "";
/*     */       }
/*     */       
/* 481 */       SpanContext spanContext = new SpanContext(traceId, spanId, op, parentSpanId, null);
/* 482 */       spanContext.setDescription(description);
/* 483 */       spanContext.setStatus(status);
/* 484 */       spanContext.setOrigin(origin);
/*     */       
/* 486 */       if (tags != null) {
/* 487 */         spanContext.tags = tags;
/*     */       }
/*     */       
/* 490 */       if (data != null) {
/* 491 */         spanContext.data = data;
/*     */       }
/*     */       
/* 494 */       spanContext.setUnknown(unknown);
/* 495 */       reader.endObject();
/* 496 */       return spanContext;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SpanContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */