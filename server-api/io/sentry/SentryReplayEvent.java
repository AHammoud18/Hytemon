/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class SentryReplayEvent
/*     */   extends SentryBaseEvent
/*     */   implements JsonUnknown, JsonSerializable {
/*     */   public enum ReplayType implements JsonSerializable {
/*  21 */     SESSION,
/*  22 */     BUFFER;
/*     */ 
/*     */ 
/*     */     
/*     */     public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  27 */       writer.value(name().toLowerCase(Locale.ROOT));
/*     */     }
/*     */     
/*     */     public static final class Deserializer
/*     */       implements JsonDeserializer<ReplayType> {
/*     */       @NotNull
/*     */       public SentryReplayEvent.ReplayType deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/*  34 */         return SentryReplayEvent.ReplayType.valueOf(reader.nextString().toUpperCase(Locale.ROOT));
/*     */       }
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*  56 */   private SentryId replayId = new SentryId(); @NotNull
/*  57 */   private String type = "replay_event"; @NotNull
/*  58 */   private ReplayType replayType = ReplayType.SESSION; @Nullable
/*  59 */   private List<String> errorIds = new ArrayList<>(); @Nullable
/*  60 */   private List<String> traceIds = new ArrayList<>(); @Nullable
/*  61 */   private List<String> urls = new ArrayList<>(); @NotNull
/*  62 */   private Date timestamp = DateUtils.getCurrentDateTime(); public static final long REPLAY_VIDEO_MAX_SIZE = 10485760L; public static final String REPLAY_EVENT_TYPE = "replay_event"; @Nullable
/*     */   private File videoFile;
/*     */   
/*     */   @Nullable
/*     */   public File getVideoFile() {
/*  67 */     return this.videoFile;
/*     */   } private int segmentId; @Nullable
/*     */   private Date replayStartTimestamp; @Nullable
/*     */   private Map<String, Object> unknown; public void setVideoFile(@Nullable File videoFile) {
/*  71 */     this.videoFile = videoFile;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getType() {
/*  76 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(@NotNull String type) {
/*  80 */     this.type = type;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SentryId getReplayId() {
/*  85 */     return this.replayId;
/*     */   }
/*     */   
/*     */   public void setReplayId(@Nullable SentryId replayId) {
/*  89 */     this.replayId = replayId;
/*     */   }
/*     */   
/*     */   public int getSegmentId() {
/*  93 */     return this.segmentId;
/*     */   }
/*     */   
/*     */   public void setSegmentId(int segmentId) {
/*  97 */     this.segmentId = segmentId;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Date getTimestamp() {
/* 102 */     return this.timestamp;
/*     */   }
/*     */   
/*     */   public void setTimestamp(@NotNull Date timestamp) {
/* 106 */     this.timestamp = timestamp;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Date getReplayStartTimestamp() {
/* 111 */     return this.replayStartTimestamp;
/*     */   }
/*     */   
/*     */   public void setReplayStartTimestamp(@Nullable Date replayStartTimestamp) {
/* 115 */     this.replayStartTimestamp = replayStartTimestamp;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<String> getUrls() {
/* 120 */     return this.urls;
/*     */   }
/*     */   
/*     */   public void setUrls(@Nullable List<String> urls) {
/* 124 */     this.urls = urls;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<String> getErrorIds() {
/* 129 */     return this.errorIds;
/*     */   }
/*     */   
/*     */   public void setErrorIds(@Nullable List<String> errorIds) {
/* 133 */     this.errorIds = errorIds;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<String> getTraceIds() {
/* 138 */     return this.traceIds;
/*     */   }
/*     */   
/*     */   public void setTraceIds(@Nullable List<String> traceIds) {
/* 142 */     this.traceIds = traceIds;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ReplayType getReplayType() {
/* 147 */     return this.replayType;
/*     */   }
/*     */   
/*     */   public void setReplayType(@NotNull ReplayType replayType) {
/* 151 */     this.replayType = replayType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 156 */     if (this == o) return true; 
/* 157 */     if (o == null || getClass() != o.getClass()) return false; 
/* 158 */     SentryReplayEvent that = (SentryReplayEvent)o;
/* 159 */     return (this.segmentId == that.segmentId && 
/* 160 */       Objects.equals(this.type, that.type) && this.replayType == that.replayType && 
/*     */       
/* 162 */       Objects.equals(this.replayId, that.replayId) && 
/* 163 */       Objects.equals(this.urls, that.urls) && 
/* 164 */       Objects.equals(this.errorIds, that.errorIds) && 
/* 165 */       Objects.equals(this.traceIds, that.traceIds));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 170 */     return Objects.hash(new Object[] { this.type, this.replayType, this.replayId, Integer.valueOf(this.segmentId), this.urls, this.errorIds, this.traceIds });
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String TYPE = "type";
/*     */     
/*     */     public static final String REPLAY_TYPE = "replay_type";
/*     */     
/*     */     public static final String REPLAY_ID = "replay_id";
/*     */     public static final String SEGMENT_ID = "segment_id";
/*     */     public static final String TIMESTAMP = "timestamp";
/*     */     public static final String REPLAY_START_TIMESTAMP = "replay_start_timestamp";
/*     */     public static final String URLS = "urls";
/*     */     public static final String ERROR_IDS = "error_ids";
/*     */     public static final String TRACE_IDS = "trace_ids";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 190 */     writer.beginObject();
/* 191 */     writer.name("type").value(this.type);
/* 192 */     writer.name("replay_type").value(logger, this.replayType);
/* 193 */     writer.name("segment_id").value(this.segmentId);
/* 194 */     writer.name("timestamp").value(logger, this.timestamp);
/* 195 */     if (this.replayId != null) {
/* 196 */       writer.name("replay_id").value(logger, this.replayId);
/*     */     }
/* 198 */     if (this.replayStartTimestamp != null) {
/* 199 */       writer.name("replay_start_timestamp").value(logger, this.replayStartTimestamp);
/*     */     }
/* 201 */     if (this.urls != null) {
/* 202 */       writer.name("urls").value(logger, this.urls);
/*     */     }
/* 204 */     if (this.errorIds != null) {
/* 205 */       writer.name("error_ids").value(logger, this.errorIds);
/*     */     }
/* 207 */     if (this.traceIds != null) {
/* 208 */       writer.name("trace_ids").value(logger, this.traceIds);
/*     */     }
/*     */     
/* 211 */     (new SentryBaseEvent.Serializer()).serialize(this, writer, logger);
/*     */     
/* 213 */     if (this.unknown != null) {
/* 214 */       for (String key : this.unknown.keySet()) {
/* 215 */         Object value = this.unknown.get(key);
/* 216 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 219 */     writer.endObject();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 224 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 229 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryReplayEvent>
/*     */   {
/*     */     @NotNull
/*     */     public SentryReplayEvent deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 239 */       SentryBaseEvent.Deserializer baseEventDeserializer = new SentryBaseEvent.Deserializer();
/*     */       
/* 241 */       SentryReplayEvent replay = new SentryReplayEvent();
/*     */       
/* 243 */       Map<String, Object> unknown = null;
/* 244 */       String type = null;
/* 245 */       SentryReplayEvent.ReplayType replayType = null;
/* 246 */       SentryId replayId = null;
/* 247 */       Integer segmentId = null;
/* 248 */       Date timestamp = null;
/* 249 */       Date replayStartTimestamp = null;
/* 250 */       List<String> urls = null;
/* 251 */       List<String> errorIds = null;
/* 252 */       List<String> traceIds = null;
/*     */       
/* 254 */       reader.beginObject();
/* 255 */       while (reader.peek() == JsonToken.NAME) {
/* 256 */         String nextName = reader.nextName();
/* 257 */         switch (nextName) {
/*     */           case "type":
/* 259 */             type = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "replay_type":
/* 262 */             replayType = reader.<SentryReplayEvent.ReplayType>nextOrNull(logger, new SentryReplayEvent.ReplayType.Deserializer());
/*     */             continue;
/*     */           case "replay_id":
/* 265 */             replayId = reader.<SentryId>nextOrNull(logger, (JsonDeserializer<SentryId>)new SentryId.Deserializer());
/*     */             continue;
/*     */           case "segment_id":
/* 268 */             segmentId = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "timestamp":
/* 271 */             timestamp = reader.nextDateOrNull(logger);
/*     */             continue;
/*     */           case "replay_start_timestamp":
/* 274 */             replayStartTimestamp = reader.nextDateOrNull(logger);
/*     */             continue;
/*     */           case "urls":
/* 277 */             urls = (List<String>)reader.nextObjectOrNull();
/*     */             continue;
/*     */           case "error_ids":
/* 280 */             errorIds = (List<String>)reader.nextObjectOrNull();
/*     */             continue;
/*     */           case "trace_ids":
/* 283 */             traceIds = (List<String>)reader.nextObjectOrNull();
/*     */             continue;
/*     */         } 
/* 286 */         if (!baseEventDeserializer.deserializeValue(replay, nextName, reader, logger)) {
/* 287 */           if (unknown == null) {
/* 288 */             unknown = new HashMap<>();
/*     */           }
/* 290 */           reader.nextUnknown(logger, unknown, nextName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 295 */       reader.endObject();
/*     */       
/* 297 */       if (type != null) {
/* 298 */         replay.setType(type);
/*     */       }
/* 300 */       if (replayType != null) {
/* 301 */         replay.setReplayType(replayType);
/*     */       }
/* 303 */       if (segmentId != null) {
/* 304 */         replay.setSegmentId(segmentId.intValue());
/*     */       }
/* 306 */       if (timestamp != null) {
/* 307 */         replay.setTimestamp(timestamp);
/*     */       }
/* 309 */       replay.setReplayId(replayId);
/* 310 */       replay.setReplayStartTimestamp(replayStartTimestamp);
/* 311 */       replay.setUrls(urls);
/* 312 */       replay.setErrorIds(errorIds);
/* 313 */       replay.setTraceIds(traceIds);
/* 314 */       replay.setUnknown(unknown);
/* 315 */       return replay;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryReplayEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */