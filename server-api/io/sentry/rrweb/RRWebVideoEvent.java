/*     */ package io.sentry.rrweb;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class RRWebVideoEvent extends RRWebEvent implements JsonUnknown, JsonSerializable {
/*     */   public static final String EVENT_TAG = "video";
/*     */   public static final String REPLAY_ENCODING = "h264";
/*     */   public static final String REPLAY_CONTAINER = "mp4";
/*     */   public static final String REPLAY_FRAME_RATE_TYPE_CONSTANT = "constant";
/*     */   public static final String REPLAY_FRAME_RATE_TYPE_VARIABLE = "variable";
/*     */   @NotNull
/*     */   private String tag;
/*     */   private int segmentId;
/*     */   private long size;
/*     */   private long durationMs;
/*     */   @NotNull
/*  30 */   private String encoding = "h264"; @NotNull
/*  31 */   private String container = "mp4"; private int height;
/*     */   private int width;
/*     */   private int frameCount;
/*     */   @NotNull
/*  35 */   private String frameRateType = "constant"; private int frameRate;
/*     */   private int left;
/*     */   private int top;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   @Nullable
/*     */   private Map<String, Object> payloadUnknown;
/*     */   @Nullable
/*     */   private Map<String, Object> dataUnknown;
/*     */   
/*     */   public RRWebVideoEvent() {
/*  46 */     super(RRWebEventType.Custom);
/*  47 */     this.tag = "video";
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getTag() {
/*  52 */     return this.tag;
/*     */   }
/*     */   
/*     */   public void setTag(@NotNull String tag) {
/*  56 */     this.tag = tag;
/*     */   }
/*     */   
/*     */   public int getSegmentId() {
/*  60 */     return this.segmentId;
/*     */   }
/*     */   
/*     */   public void setSegmentId(int segmentId) {
/*  64 */     this.segmentId = segmentId;
/*     */   }
/*     */   
/*     */   public long getSize() {
/*  68 */     return this.size;
/*     */   }
/*     */   
/*     */   public void setSize(long size) {
/*  72 */     this.size = size;
/*     */   }
/*     */   
/*     */   public long getDurationMs() {
/*  76 */     return this.durationMs;
/*     */   }
/*     */   
/*     */   public void setDurationMs(long durationMs) {
/*  80 */     this.durationMs = durationMs;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getEncoding() {
/*  85 */     return this.encoding;
/*     */   }
/*     */   
/*     */   public void setEncoding(@NotNull String encoding) {
/*  89 */     this.encoding = encoding;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getContainer() {
/*  94 */     return this.container;
/*     */   }
/*     */   
/*     */   public void setContainer(@NotNull String container) {
/*  98 */     this.container = container;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 102 */     return this.height;
/*     */   }
/*     */   
/*     */   public void setHeight(int height) {
/* 106 */     this.height = height;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 110 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(int width) {
/* 114 */     this.width = width;
/*     */   }
/*     */   
/*     */   public int getFrameCount() {
/* 118 */     return this.frameCount;
/*     */   }
/*     */   
/*     */   public void setFrameCount(int frameCount) {
/* 122 */     this.frameCount = frameCount;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getFrameRateType() {
/* 127 */     return this.frameRateType;
/*     */   }
/*     */   
/*     */   public void setFrameRateType(@NotNull String frameRateType) {
/* 131 */     this.frameRateType = frameRateType;
/*     */   }
/*     */   
/*     */   public int getFrameRate() {
/* 135 */     return this.frameRate;
/*     */   }
/*     */   
/*     */   public void setFrameRate(int frameRate) {
/* 139 */     this.frameRate = frameRate;
/*     */   }
/*     */   
/*     */   public int getLeft() {
/* 143 */     return this.left;
/*     */   }
/*     */   
/*     */   public void setLeft(int left) {
/* 147 */     this.left = left;
/*     */   }
/*     */   
/*     */   public int getTop() {
/* 151 */     return this.top;
/*     */   }
/*     */   
/*     */   public void setTop(int top) {
/* 155 */     this.top = top;
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, Object> getPayloadUnknown() {
/* 159 */     return this.payloadUnknown;
/*     */   }
/*     */   
/*     */   public void setPayloadUnknown(@Nullable Map<String, Object> payloadUnknown) {
/* 163 */     this.payloadUnknown = payloadUnknown;
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, Object> getDataUnknown() {
/* 167 */     return this.dataUnknown;
/*     */   }
/*     */   
/*     */   public void setDataUnknown(@Nullable Map<String, Object> dataUnknown) {
/* 171 */     this.dataUnknown = dataUnknown;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 176 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 181 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 186 */     if (this == o) return true; 
/* 187 */     if (o == null || getClass() != o.getClass()) return false; 
/* 188 */     if (!super.equals(o)) return false; 
/* 189 */     RRWebVideoEvent that = (RRWebVideoEvent)o;
/* 190 */     return (this.segmentId == that.segmentId && this.size == that.size && this.durationMs == that.durationMs && this.height == that.height && this.width == that.width && this.frameCount == that.frameCount && this.frameRate == that.frameRate && this.left == that.left && this.top == that.top && 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 199 */       Objects.equals(this.tag, that.tag) && 
/* 200 */       Objects.equals(this.encoding, that.encoding) && 
/* 201 */       Objects.equals(this.container, that.container) && 
/* 202 */       Objects.equals(this.frameRateType, that.frameRateType));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 207 */     return Objects.hash(new Object[] { 
/* 208 */           Integer.valueOf(super.hashCode()), this.tag, 
/*     */           
/* 210 */           Integer.valueOf(this.segmentId), 
/* 211 */           Long.valueOf(this.size), 
/* 212 */           Long.valueOf(this.durationMs), this.encoding, this.container, 
/*     */ 
/*     */           
/* 215 */           Integer.valueOf(this.height), 
/* 216 */           Integer.valueOf(this.width), 
/* 217 */           Integer.valueOf(this.frameCount), this.frameRateType, 
/*     */           
/* 219 */           Integer.valueOf(this.frameRate), 
/* 220 */           Integer.valueOf(this.left), 
/* 221 */           Integer.valueOf(this.top) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String DATA = "data";
/*     */     
/*     */     public static final String PAYLOAD = "payload";
/*     */     
/*     */     public static final String SEGMENT_ID = "segmentId";
/*     */     
/*     */     public static final String SIZE = "size";
/*     */     public static final String DURATION = "duration";
/*     */     public static final String ENCODING = "encoding";
/*     */     public static final String CONTAINER = "container";
/*     */     public static final String HEIGHT = "height";
/*     */     public static final String WIDTH = "width";
/*     */     public static final String FRAME_COUNT = "frameCount";
/*     */     public static final String FRAME_RATE_TYPE = "frameRateType";
/*     */     public static final String FRAME_RATE = "frameRate";
/*     */     public static final String LEFT = "left";
/*     */     public static final String TOP = "top";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 247 */     writer.beginObject();
/* 248 */     (new RRWebEvent.Serializer()).serialize(this, writer, logger);
/* 249 */     writer.name("data");
/* 250 */     serializeData(writer, logger);
/* 251 */     if (this.unknown != null) {
/* 252 */       for (String key : this.unknown.keySet()) {
/* 253 */         Object value = this.unknown.get(key);
/* 254 */         writer.name(key);
/* 255 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 258 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializeData(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 263 */     writer.beginObject();
/* 264 */     writer.name("tag").value(this.tag);
/* 265 */     writer.name("payload");
/* 266 */     serializePayload(writer, logger);
/* 267 */     if (this.dataUnknown != null) {
/* 268 */       for (String key : this.dataUnknown.keySet()) {
/* 269 */         Object value = this.dataUnknown.get(key);
/* 270 */         writer.name(key);
/* 271 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 274 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializePayload(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 279 */     writer.beginObject();
/* 280 */     writer.name("segmentId").value(this.segmentId);
/* 281 */     writer.name("size").value(this.size);
/* 282 */     writer.name("duration").value(this.durationMs);
/* 283 */     writer.name("encoding").value(this.encoding);
/* 284 */     writer.name("container").value(this.container);
/* 285 */     writer.name("height").value(this.height);
/* 286 */     writer.name("width").value(this.width);
/* 287 */     writer.name("frameCount").value(this.frameCount);
/* 288 */     writer.name("frameRate").value(this.frameRate);
/* 289 */     writer.name("frameRateType").value(this.frameRateType);
/* 290 */     writer.name("left").value(this.left);
/* 291 */     writer.name("top").value(this.top);
/* 292 */     if (this.payloadUnknown != null) {
/* 293 */       for (String key : this.payloadUnknown.keySet()) {
/* 294 */         Object value = this.payloadUnknown.get(key);
/* 295 */         writer.name(key);
/* 296 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 299 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<RRWebVideoEvent>
/*     */   {
/*     */     @NotNull
/*     */     public RRWebVideoEvent deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 308 */       reader.beginObject();
/* 309 */       Map<String, Object> unknown = null;
/*     */       
/* 311 */       RRWebVideoEvent event = new RRWebVideoEvent();
/* 312 */       RRWebEvent.Deserializer baseEventDeserializer = new RRWebEvent.Deserializer();
/*     */       
/* 314 */       while (reader.peek() == JsonToken.NAME) {
/* 315 */         String nextName = reader.nextName();
/* 316 */         switch (nextName) {
/*     */           case "data":
/* 318 */             deserializeData(event, reader, logger);
/*     */             continue;
/*     */         } 
/* 321 */         if (!baseEventDeserializer.deserializeValue(event, nextName, reader, logger)) {
/* 322 */           if (unknown == null) {
/* 323 */             unknown = new HashMap<>();
/*     */           }
/* 325 */           reader.nextUnknown(logger, unknown, nextName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 330 */       event.setUnknown(unknown);
/* 331 */       reader.endObject();
/* 332 */       return event;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void deserializeData(@NotNull RRWebVideoEvent event, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 340 */       Map<String, Object> dataUnknown = null;
/*     */       
/* 342 */       reader.beginObject();
/* 343 */       while (reader.peek() == JsonToken.NAME) {
/* 344 */         String tag, nextName = reader.nextName();
/* 345 */         switch (nextName) {
/*     */           case "tag":
/* 347 */             tag = reader.nextStringOrNull();
/* 348 */             event.tag = (tag == null) ? "" : tag;
/*     */             continue;
/*     */           case "payload":
/* 351 */             deserializePayload(event, reader, logger);
/*     */             continue;
/*     */         } 
/* 354 */         if (dataUnknown == null) {
/* 355 */           dataUnknown = new ConcurrentHashMap<>();
/*     */         }
/* 357 */         reader.nextUnknown(logger, dataUnknown, nextName);
/*     */       } 
/*     */       
/* 360 */       event.setDataUnknown(dataUnknown);
/* 361 */       reader.endObject();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void deserializePayload(@NotNull RRWebVideoEvent event, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 369 */       Map<String, Object> payloadUnknown = null;
/*     */       
/* 371 */       reader.beginObject();
/* 372 */       while (reader.peek() == JsonToken.NAME) {
/* 373 */         Long size; String container, encoding; Integer height, width, frameCount, frameRate; String frameRateType; Integer left, top; String nextName = reader.nextName();
/* 374 */         switch (nextName) {
/*     */           case "segmentId":
/* 376 */             event.segmentId = reader.nextInt();
/*     */             continue;
/*     */           case "size":
/* 379 */             size = reader.nextLongOrNull();
/* 380 */             event.size = (size == null) ? 0L : size.longValue();
/*     */             continue;
/*     */           case "duration":
/* 383 */             event.durationMs = reader.nextLong();
/*     */             continue;
/*     */           case "container":
/* 386 */             container = reader.nextStringOrNull();
/* 387 */             event.container = (container == null) ? "" : container;
/*     */             continue;
/*     */           case "encoding":
/* 390 */             encoding = reader.nextStringOrNull();
/* 391 */             event.encoding = (encoding == null) ? "" : encoding;
/*     */             continue;
/*     */           case "height":
/* 394 */             height = reader.nextIntegerOrNull();
/* 395 */             event.height = (height == null) ? 0 : height.intValue();
/*     */             continue;
/*     */           case "width":
/* 398 */             width = reader.nextIntegerOrNull();
/* 399 */             event.width = (width == null) ? 0 : width.intValue();
/*     */             continue;
/*     */           case "frameCount":
/* 402 */             frameCount = reader.nextIntegerOrNull();
/* 403 */             event.frameCount = (frameCount == null) ? 0 : frameCount.intValue();
/*     */             continue;
/*     */           case "frameRate":
/* 406 */             frameRate = reader.nextIntegerOrNull();
/* 407 */             event.frameRate = (frameRate == null) ? 0 : frameRate.intValue();
/*     */             continue;
/*     */           case "frameRateType":
/* 410 */             frameRateType = reader.nextStringOrNull();
/* 411 */             event.frameRateType = (frameRateType == null) ? "" : frameRateType;
/*     */             continue;
/*     */           case "left":
/* 414 */             left = reader.nextIntegerOrNull();
/* 415 */             event.left = (left == null) ? 0 : left.intValue();
/*     */             continue;
/*     */           case "top":
/* 418 */             top = reader.nextIntegerOrNull();
/* 419 */             event.top = (top == null) ? 0 : top.intValue();
/*     */             continue;
/*     */         } 
/* 422 */         if (payloadUnknown == null) {
/* 423 */           payloadUnknown = new ConcurrentHashMap<>();
/*     */         }
/* 425 */         reader.nextUnknown(logger, payloadUnknown, nextName);
/*     */       } 
/*     */       
/* 428 */       event.setPayloadUnknown(payloadUnknown);
/* 429 */       reader.endObject();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\rrweb\RRWebVideoEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */