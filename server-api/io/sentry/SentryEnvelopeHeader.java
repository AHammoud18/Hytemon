/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.SdkVersion;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class SentryEnvelopeHeader
/*     */   implements JsonSerializable, JsonUnknown
/*     */ {
/*     */   @Nullable
/*     */   private final SentryId eventId;
/*     */   @Nullable
/*     */   private final SdkVersion sdkVersion;
/*     */   @Nullable
/*     */   private final TraceContext traceContext;
/*     */   @Nullable
/*     */   private Date sentAt;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public SentryEnvelopeHeader(@Nullable SentryId eventId, @Nullable SdkVersion sdkVersion) {
/*  30 */     this(eventId, sdkVersion, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SentryEnvelopeHeader(@Nullable SentryId eventId, @Nullable SdkVersion sdkVersion, @Nullable TraceContext traceContext) {
/*  37 */     this.eventId = eventId;
/*  38 */     this.sdkVersion = sdkVersion;
/*  39 */     this.traceContext = traceContext;
/*     */   }
/*     */   
/*     */   public SentryEnvelopeHeader(@Nullable SentryId eventId) {
/*  43 */     this(eventId, null);
/*     */   }
/*     */   
/*     */   public SentryEnvelopeHeader() {
/*  47 */     this(new SentryId());
/*     */   }
/*     */   @Nullable
/*     */   public SentryId getEventId() {
/*  51 */     return this.eventId;
/*     */   }
/*     */   @Nullable
/*     */   public SdkVersion getSdkVersion() {
/*  55 */     return this.sdkVersion;
/*     */   }
/*     */   @Nullable
/*     */   public TraceContext getTraceContext() {
/*  59 */     return this.traceContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Date getSentAt() {
/*  67 */     return this.sentAt;
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
/*     */   public void setSentAt(@Nullable Date sentAt) {
/*  79 */     this.sentAt = sentAt;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String EVENT_ID = "event_id";
/*     */     
/*     */     public static final String SDK = "sdk";
/*     */     
/*     */     public static final String TRACE = "trace";
/*     */     public static final String SENT_AT = "sent_at";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  94 */     writer.beginObject();
/*  95 */     if (this.eventId != null) {
/*  96 */       writer.name("event_id").value(logger, this.eventId);
/*     */     }
/*  98 */     if (this.sdkVersion != null) {
/*  99 */       writer.name("sdk").value(logger, this.sdkVersion);
/*     */     }
/* 101 */     if (this.traceContext != null) {
/* 102 */       writer.name("trace").value(logger, this.traceContext);
/*     */     }
/* 104 */     if (this.sentAt != null) {
/* 105 */       writer.name("sent_at").value(logger, DateUtils.getTimestamp(this.sentAt));
/*     */     }
/* 107 */     if (this.unknown != null) {
/* 108 */       for (String key : this.unknown.keySet()) {
/* 109 */         Object value = this.unknown.get(key);
/* 110 */         writer.name(key);
/* 111 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 114 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryEnvelopeHeader> {
/*     */     @NotNull
/*     */     public SentryEnvelopeHeader deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 121 */       reader.beginObject();
/*     */       
/* 123 */       SentryId eventId = null;
/* 124 */       SdkVersion sdkVersion = null;
/* 125 */       TraceContext traceContext = null;
/* 126 */       Date sentAt = null;
/* 127 */       Map<String, Object> unknown = null;
/*     */       
/* 129 */       while (reader.peek() == JsonToken.NAME) {
/* 130 */         String nextName = reader.nextName();
/* 131 */         switch (nextName) {
/*     */           case "event_id":
/* 133 */             eventId = reader.<SentryId>nextOrNull(logger, (JsonDeserializer<SentryId>)new SentryId.Deserializer());
/*     */             continue;
/*     */           case "sdk":
/* 136 */             sdkVersion = reader.<SdkVersion>nextOrNull(logger, (JsonDeserializer<SdkVersion>)new SdkVersion.Deserializer());
/*     */             continue;
/*     */           case "trace":
/* 139 */             traceContext = reader.<TraceContext>nextOrNull(logger, new TraceContext.Deserializer());
/*     */             continue;
/*     */           case "sent_at":
/* 142 */             sentAt = reader.nextDateOrNull(logger);
/*     */             continue;
/*     */         } 
/* 145 */         if (unknown == null) {
/* 146 */           unknown = new HashMap<>();
/*     */         }
/* 148 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 152 */       SentryEnvelopeHeader sentryEnvelopeHeader = new SentryEnvelopeHeader(eventId, sdkVersion, traceContext);
/*     */       
/* 154 */       sentryEnvelopeHeader.setSentAt(sentAt);
/* 155 */       sentryEnvelopeHeader.setUnknown(unknown);
/* 156 */       reader.endObject();
/* 157 */       return sentryEnvelopeHeader;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 166 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 171 */     this.unknown = unknown;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryEnvelopeHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */