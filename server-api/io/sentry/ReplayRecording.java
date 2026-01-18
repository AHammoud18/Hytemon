/*     */ package io.sentry;
/*     */ import io.sentry.rrweb.RRWebBreadcrumbEvent;
/*     */ import io.sentry.rrweb.RRWebEvent;
/*     */ import io.sentry.rrweb.RRWebEventType;
/*     */ import io.sentry.rrweb.RRWebIncrementalSnapshotEvent;
/*     */ import io.sentry.rrweb.RRWebInteractionEvent;
/*     */ import io.sentry.rrweb.RRWebInteractionMoveEvent;
/*     */ import io.sentry.rrweb.RRWebMetaEvent;
/*     */ import io.sentry.rrweb.RRWebSpanEvent;
/*     */ import io.sentry.rrweb.RRWebVideoEvent;
/*     */ import io.sentry.util.MapObjectReader;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class ReplayRecording implements JsonUnknown, JsonSerializable {
/*     */   @Nullable
/*     */   private Integer segmentId;
/*     */   @Nullable
/*     */   private List<? extends RRWebEvent> payload;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public static final class JsonKeys {
/*     */     public static final String SEGMENT_ID = "segment_id";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Integer getSegmentId() {
/*  36 */     return this.segmentId;
/*     */   }
/*     */   
/*     */   public void setSegmentId(@Nullable Integer segmentId) {
/*  40 */     this.segmentId = segmentId;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<? extends RRWebEvent> getPayload() {
/*  45 */     return this.payload;
/*     */   }
/*     */   
/*     */   public void setPayload(@Nullable List<? extends RRWebEvent> payload) {
/*  49 */     this.payload = payload;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  54 */     if (this == o) return true; 
/*  55 */     if (o == null || getClass() != o.getClass()) return false; 
/*  56 */     ReplayRecording that = (ReplayRecording)o;
/*  57 */     return (Objects.equals(this.segmentId, that.segmentId) && Objects.equals(this.payload, that.payload));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  62 */     return Objects.hash(new Object[] { this.segmentId, this.payload });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  68 */     writer.beginObject();
/*  69 */     if (this.segmentId != null) {
/*  70 */       writer.name("segment_id").value(this.segmentId);
/*     */     }
/*     */     
/*  73 */     if (this.unknown != null) {
/*  74 */       for (String key : this.unknown.keySet()) {
/*  75 */         Object value = this.unknown.get(key);
/*  76 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/*  79 */     writer.endObject();
/*     */ 
/*     */ 
/*     */     
/*  83 */     writer.setLenient(true);
/*  84 */     if (this.segmentId != null) {
/*  85 */       writer.jsonValue("\n");
/*     */     }
/*  87 */     if (this.payload != null) {
/*  88 */       writer.value(logger, this.payload);
/*     */     }
/*  90 */     writer.setLenient(false);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  95 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 100 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<ReplayRecording>
/*     */   {
/*     */     @NotNull
/*     */     public ReplayRecording deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 110 */       ReplayRecording replay = new ReplayRecording();
/*     */       
/* 112 */       Map<String, Object> unknown = null;
/* 113 */       Integer segmentId = null;
/* 114 */       List<RRWebEvent> payload = null;
/*     */       
/* 116 */       reader.beginObject();
/* 117 */       while (reader.peek() == JsonToken.NAME) {
/* 118 */         String nextName = reader.nextName();
/* 119 */         switch (nextName) {
/*     */           case "segment_id":
/* 121 */             segmentId = reader.nextIntegerOrNull();
/*     */             continue;
/*     */         } 
/* 124 */         if (unknown == null) {
/* 125 */           unknown = new HashMap<>();
/*     */         }
/* 127 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 131 */       reader.endObject();
/*     */ 
/*     */ 
/*     */       
/* 135 */       reader.setLenient(true);
/* 136 */       List<Object> events = (List<Object>)reader.nextObjectOrNull();
/* 137 */       reader.setLenient(false);
/*     */ 
/*     */       
/* 140 */       if (events != null) {
/* 141 */         payload = new ArrayList<>(events.size());
/* 142 */         for (Object event : events) {
/* 143 */           if (event instanceof Map) {
/* 144 */             Map<String, Object> eventMap = (Map<String, Object>)event;
/* 145 */             MapObjectReader mapObjectReader = new MapObjectReader(eventMap);
/* 146 */             for (Map.Entry<String, Object> entry : eventMap.entrySet()) {
/* 147 */               String key = entry.getKey();
/* 148 */               Object value = entry.getValue();
/* 149 */               if (key.equals("type")) {
/* 150 */                 Map<String, Object> incrementalData; Integer sourceInt; RRWebMetaEvent rRWebMetaEvent; Map<String, Object> customData; String tag; RRWebEventType type = RRWebEventType.values()[((Integer)value).intValue()];
/* 151 */                 switch (type) {
/*     */ 
/*     */                   
/*     */                   case IncrementalSnapshot:
/* 155 */                     incrementalData = (Map<String, Object>)eventMap.get("data");
/* 156 */                     if (incrementalData == null) {
/* 157 */                       incrementalData = Collections.emptyMap();
/*     */                     }
/*     */ 
/*     */                     
/* 161 */                     sourceInt = (Integer)incrementalData.get("source");
/* 162 */                     if (sourceInt != null) {
/*     */                       RRWebInteractionEvent interactionEvent; RRWebInteractionMoveEvent interactionMoveEvent;
/* 164 */                       RRWebIncrementalSnapshotEvent.IncrementalSource source = RRWebIncrementalSnapshotEvent.IncrementalSource.values()[sourceInt.intValue()];
/* 165 */                       switch (source) {
/*     */ 
/*     */                         
/*     */                         case IncrementalSnapshot:
/* 169 */                           interactionEvent = (new RRWebInteractionEvent.Deserializer()).deserialize((ObjectReader)mapObjectReader, logger);
/* 170 */                           payload.add(interactionEvent);
/*     */                           continue;
/*     */ 
/*     */                         
/*     */                         case Meta:
/* 175 */                           interactionMoveEvent = (new RRWebInteractionMoveEvent.Deserializer()).deserialize((ObjectReader)mapObjectReader, logger);
/* 176 */                           payload.add(interactionMoveEvent);
/*     */                           continue;
/*     */                       } 
/* 179 */                       logger.log(SentryLevel.DEBUG, "Unsupported rrweb incremental snapshot type %s", new Object[] { source });
/*     */                     } 
/*     */                     continue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   case Meta:
/* 189 */                     rRWebMetaEvent = (new RRWebMetaEvent.Deserializer()).deserialize((ObjectReader)mapObjectReader, logger);
/* 190 */                     payload.add(rRWebMetaEvent);
/*     */                     continue;
/*     */                   
/*     */                   case Custom:
/* 194 */                     customData = (Map<String, Object>)eventMap.get("data");
/* 195 */                     if (customData == null) {
/* 196 */                       customData = Collections.emptyMap();
/*     */                     }
/* 198 */                     tag = (String)customData.get("tag");
/* 199 */                     if (tag != null) {
/* 200 */                       RRWebVideoEvent rRWebVideoEvent; RRWebBreadcrumbEvent rRWebBreadcrumbEvent; RRWebSpanEvent rRWebSpanEvent; switch (tag) {
/*     */                         
/*     */                         case "video":
/* 203 */                           rRWebVideoEvent = (new RRWebVideoEvent.Deserializer()).deserialize((ObjectReader)mapObjectReader, logger);
/* 204 */                           payload.add(rRWebVideoEvent);
/*     */                           continue;
/*     */ 
/*     */                         
/*     */                         case "breadcrumb":
/* 209 */                           rRWebBreadcrumbEvent = (new RRWebBreadcrumbEvent.Deserializer()).deserialize((ObjectReader)mapObjectReader, logger);
/* 210 */                           payload.add(rRWebBreadcrumbEvent);
/*     */                           continue;
/*     */                         
/*     */                         case "performanceSpan":
/* 214 */                           rRWebSpanEvent = (new RRWebSpanEvent.Deserializer()).deserialize((ObjectReader)mapObjectReader, logger);
/* 215 */                           payload.add(rRWebSpanEvent);
/*     */                           continue;
/*     */                       } 
/* 218 */                       logger.log(SentryLevel.DEBUG, "Unsupported rrweb event type %s", new Object[] { type });
/*     */                     } 
/*     */                     continue;
/*     */                 } 
/*     */ 
/*     */                 
/* 224 */                 logger.log(SentryLevel.DEBUG, "Unsupported rrweb event type %s", new Object[] { type });
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 233 */       replay.setSegmentId(segmentId);
/* 234 */       replay.setPayload(payload);
/* 235 */       replay.setUnknown(unknown);
/* 236 */       return replay;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ReplayRecording.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */