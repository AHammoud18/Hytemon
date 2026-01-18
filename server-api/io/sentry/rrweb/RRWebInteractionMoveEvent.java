/*     */ package io.sentry.rrweb;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class RRWebInteractionMoveEvent
/*     */   extends RRWebIncrementalSnapshotEvent
/*     */   implements JsonSerializable, JsonUnknown {
/*     */   private int pointerId;
/*     */   @Nullable
/*     */   private List<Position> positions;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   @Nullable
/*     */   private Map<String, Object> dataUnknown;
/*     */   
/*     */   public static final class Position
/*     */     implements JsonSerializable, JsonUnknown {
/*     */     private int id;
/*     */     private float x;
/*     */     
/*     */     public int getId() {
/*  34 */       return this.id;
/*     */     } private float y; private long timeOffset; @Nullable
/*     */     private Map<String, Object> unknown;
/*     */     public void setId(int id) {
/*  38 */       this.id = id;
/*     */     }
/*     */     
/*     */     public float getX() {
/*  42 */       return this.x;
/*     */     }
/*     */     
/*     */     public void setX(float x) {
/*  46 */       this.x = x;
/*     */     }
/*     */     
/*     */     public float getY() {
/*  50 */       return this.y;
/*     */     }
/*     */     
/*     */     public void setY(float y) {
/*  54 */       this.y = y;
/*     */     }
/*     */     
/*     */     public long getTimeOffset() {
/*  58 */       return this.timeOffset;
/*     */     }
/*     */     
/*     */     public void setTimeOffset(long timeOffset) {
/*  62 */       this.timeOffset = timeOffset;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public Map<String, Object> getUnknown() {
/*  67 */       return this.unknown;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  72 */       this.unknown = unknown;
/*     */     }
/*     */ 
/*     */     
/*     */     public static final class JsonKeys
/*     */     {
/*     */       public static final String ID = "id";
/*     */       
/*     */       public static final String X = "x";
/*     */       
/*     */       public static final String Y = "y";
/*     */       
/*     */       public static final String TIME_OFFSET = "timeOffset";
/*     */     }
/*     */     
/*     */     public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  88 */       writer.beginObject();
/*  89 */       writer.name("id").value(this.id);
/*  90 */       writer.name("x").value(this.x);
/*  91 */       writer.name("y").value(this.y);
/*  92 */       writer.name("timeOffset").value(this.timeOffset);
/*  93 */       if (this.unknown != null) {
/*  94 */         for (String key : this.unknown.keySet()) {
/*  95 */           Object value = this.unknown.get(key);
/*  96 */           writer.name(key);
/*  97 */           writer.value(logger, value);
/*     */         } 
/*     */       }
/* 100 */       writer.endObject();
/*     */     }
/*     */     
/*     */     public static final class Deserializer
/*     */       implements JsonDeserializer<Position>
/*     */     {
/*     */       @NotNull
/*     */       public RRWebInteractionMoveEvent.Position deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 108 */         reader.beginObject();
/* 109 */         Map<String, Object> unknown = null;
/*     */         
/* 111 */         RRWebInteractionMoveEvent.Position position = new RRWebInteractionMoveEvent.Position();
/*     */         
/* 113 */         while (reader.peek() == JsonToken.NAME) {
/* 114 */           String nextName = reader.nextName();
/* 115 */           switch (nextName) {
/*     */             case "id":
/* 117 */               position.id = reader.nextInt();
/*     */               continue;
/*     */             case "x":
/* 120 */               position.x = reader.nextFloat();
/*     */               continue;
/*     */             case "y":
/* 123 */               position.y = reader.nextFloat();
/*     */               continue;
/*     */             case "timeOffset":
/* 126 */               position.timeOffset = reader.nextLong();
/*     */               continue;
/*     */           } 
/* 129 */           if (unknown == null) {
/* 130 */             unknown = new HashMap<>();
/*     */           }
/* 132 */           reader.nextUnknown(logger, unknown, nextName);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 137 */         position.setUnknown(unknown);
/* 138 */         reader.endObject();
/* 139 */         return position;
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
/*     */   public RRWebInteractionMoveEvent() {
/* 153 */     super(RRWebIncrementalSnapshotEvent.IncrementalSource.TouchMove);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getDataUnknown() {
/* 158 */     return this.dataUnknown;
/*     */   }
/*     */   
/*     */   public void setDataUnknown(@Nullable Map<String, Object> dataUnknown) {
/* 162 */     this.dataUnknown = dataUnknown;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 167 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 172 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<Position> getPositions() {
/* 177 */     return this.positions;
/*     */   }
/*     */   
/*     */   public void setPositions(@Nullable List<Position> positions) {
/* 181 */     this.positions = positions;
/*     */   }
/*     */   
/*     */   public int getPointerId() {
/* 185 */     return this.pointerId;
/*     */   }
/*     */   
/*     */   public void setPointerId(int pointerId) {
/* 189 */     this.pointerId = pointerId;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String DATA = "data";
/*     */     
/*     */     public static final String POSITIONS = "positions";
/*     */     
/*     */     public static final String POINTER_ID = "pointerId";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 203 */     writer.beginObject();
/* 204 */     (new RRWebEvent.Serializer()).serialize(this, writer, logger);
/* 205 */     writer.name("data");
/* 206 */     serializeData(writer, logger);
/* 207 */     if (this.unknown != null) {
/* 208 */       for (String key : this.unknown.keySet()) {
/* 209 */         Object value = this.unknown.get(key);
/* 210 */         writer.name(key);
/* 211 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 214 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializeData(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 219 */     writer.beginObject();
/* 220 */     (new RRWebIncrementalSnapshotEvent.Serializer()).serialize(this, writer, logger);
/* 221 */     if (this.positions != null && !this.positions.isEmpty()) {
/* 222 */       writer.name("positions").value(logger, this.positions);
/*     */     }
/* 224 */     writer.name("pointerId").value(this.pointerId);
/* 225 */     if (this.dataUnknown != null) {
/* 226 */       for (String key : this.dataUnknown.keySet()) {
/* 227 */         Object value = this.dataUnknown.get(key);
/* 228 */         writer.name(key);
/* 229 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 232 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<RRWebInteractionMoveEvent>
/*     */   {
/*     */     @NotNull
/*     */     public RRWebInteractionMoveEvent deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 240 */       reader.beginObject();
/* 241 */       Map<String, Object> unknown = null;
/*     */       
/* 243 */       RRWebInteractionMoveEvent event = new RRWebInteractionMoveEvent();
/* 244 */       RRWebEvent.Deserializer baseEventDeserializer = new RRWebEvent.Deserializer();
/*     */       
/* 246 */       while (reader.peek() == JsonToken.NAME) {
/* 247 */         String nextName = reader.nextName();
/* 248 */         switch (nextName) {
/*     */           case "data":
/* 250 */             deserializeData(event, reader, logger);
/*     */             continue;
/*     */         } 
/* 253 */         if (!baseEventDeserializer.deserializeValue(event, nextName, reader, logger)) {
/* 254 */           if (unknown == null) {
/* 255 */             unknown = new HashMap<>();
/*     */           }
/* 257 */           reader.nextUnknown(logger, unknown, nextName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 263 */       event.setUnknown(unknown);
/* 264 */       reader.endObject();
/* 265 */       return event;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void deserializeData(@NotNull RRWebInteractionMoveEvent event, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 273 */       Map<String, Object> dataUnknown = null;
/*     */       
/* 275 */       RRWebIncrementalSnapshotEvent.Deserializer baseEventDeserializer = new RRWebIncrementalSnapshotEvent.Deserializer();
/*     */ 
/*     */       
/* 278 */       reader.beginObject();
/* 279 */       while (reader.peek() == JsonToken.NAME) {
/* 280 */         String nextName = reader.nextName();
/* 281 */         switch (nextName) {
/*     */           case "positions":
/* 283 */             event.positions = reader.nextListOrNull(logger, new RRWebInteractionMoveEvent.Position.Deserializer());
/*     */             continue;
/*     */           case "pointerId":
/* 286 */             event.pointerId = reader.nextInt();
/*     */             continue;
/*     */         } 
/* 289 */         if (!baseEventDeserializer.deserializeValue(event, nextName, reader, logger)) {
/* 290 */           if (dataUnknown == null) {
/* 291 */             dataUnknown = new HashMap<>();
/*     */           }
/* 293 */           reader.nextUnknown(logger, dataUnknown, nextName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 298 */       event.setDataUnknown(dataUnknown);
/* 299 */       reader.endObject();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\rrweb\RRWebInteractionMoveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */