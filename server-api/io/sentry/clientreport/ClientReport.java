/*     */ package io.sentry.clientreport;
/*     */ 
/*     */ import io.sentry.DateUtils;
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class ClientReport
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @NotNull
/*     */   private final Date timestamp;
/*     */   
/*     */   public ClientReport(@NotNull Date timestamp, @NotNull List<DiscardedEvent> discardedEvents) {
/*  30 */     this.timestamp = timestamp;
/*  31 */     this.discardedEvents = discardedEvents; } @NotNull
/*     */   private final List<DiscardedEvent> discardedEvents; @Nullable
/*     */   private Map<String, Object> unknown; @NotNull
/*     */   public Date getTimestamp() {
/*  35 */     return this.timestamp;
/*     */   }
/*     */   @NotNull
/*     */   public List<DiscardedEvent> getDiscardedEvents() {
/*  39 */     return this.discardedEvents;
/*     */   }
/*     */   
/*     */   public static final class JsonKeys {
/*     */     public static final String TIMESTAMP = "timestamp";
/*     */     public static final String DISCARDED_EVENTS = "discarded_events";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  49 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  54 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  60 */     writer.beginObject();
/*     */     
/*  62 */     writer.name("timestamp").value(DateUtils.getTimestamp(this.timestamp));
/*  63 */     writer.name("discarded_events").value(logger, this.discardedEvents);
/*     */     
/*  65 */     if (this.unknown != null) {
/*  66 */       for (String key : this.unknown.keySet()) {
/*  67 */         Object value = this.unknown.get(key);
/*  68 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/*     */     
/*  72 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<ClientReport> {
/*     */     @NotNull
/*     */     public ClientReport deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/*  79 */       Date timestamp = null;
/*  80 */       List<DiscardedEvent> discardedEvents = new ArrayList<>();
/*  81 */       Map<String, Object> unknown = null;
/*     */       
/*  83 */       reader.beginObject();
/*  84 */       while (reader.peek() == JsonToken.NAME) {
/*  85 */         List<DiscardedEvent> deserializedDiscardedEvents; String nextName = reader.nextName();
/*  86 */         switch (nextName) {
/*     */           case "timestamp":
/*  88 */             timestamp = reader.nextDateOrNull(logger);
/*     */             continue;
/*     */           
/*     */           case "discarded_events":
/*  92 */             deserializedDiscardedEvents = reader.nextListOrNull(logger, new DiscardedEvent.Deserializer());
/*  93 */             discardedEvents.addAll(deserializedDiscardedEvents);
/*     */             continue;
/*     */         } 
/*  96 */         if (unknown == null) {
/*  97 */           unknown = new HashMap<>();
/*     */         }
/*  99 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 103 */       reader.endObject();
/*     */       
/* 105 */       if (timestamp == null) {
/* 106 */         throw missingRequiredFieldException("timestamp", logger);
/*     */       }
/* 108 */       if (discardedEvents.isEmpty()) {
/* 109 */         throw missingRequiredFieldException("discarded_events", logger);
/*     */       }
/*     */       
/* 112 */       ClientReport clientReport = new ClientReport(timestamp, discardedEvents);
/* 113 */       clientReport.setUnknown(unknown);
/* 114 */       return clientReport;
/*     */     }
/*     */     
/*     */     private Exception missingRequiredFieldException(String field, ILogger logger) {
/* 118 */       String message = "Missing required field \"" + field + "\"";
/* 119 */       Exception exception = new IllegalStateException(message);
/* 120 */       logger.log(SentryLevel.ERROR, message, exception);
/* 121 */       return exception;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\clientreport\ClientReport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */