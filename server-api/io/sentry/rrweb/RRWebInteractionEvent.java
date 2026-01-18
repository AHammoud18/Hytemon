/*     */ package io.sentry.rrweb;
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class RRWebInteractionEvent extends RRWebIncrementalSnapshotEvent implements JsonSerializable, JsonUnknown {
/*     */   private static final int POINTER_TYPE_TOUCH = 2;
/*     */   @Nullable
/*     */   private InteractionType interactionType;
/*     */   private int id;
/*     */   private float x;
/*     */   private float y;
/*     */   
/*     */   public enum InteractionType implements JsonSerializable {
/*  21 */     MouseUp,
/*  22 */     MouseDown,
/*  23 */     Click,
/*  24 */     ContextMenu,
/*  25 */     DblClick,
/*  26 */     Focus,
/*  27 */     Blur,
/*  28 */     TouchStart,
/*  29 */     TouchMove_Departed,
/*  30 */     TouchEnd,
/*  31 */     TouchCancel;
/*     */ 
/*     */ 
/*     */     
/*     */     public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  36 */       writer.value(ordinal());
/*     */     }
/*     */     
/*     */     public static final class Deserializer
/*     */       implements JsonDeserializer<InteractionType> {
/*     */       @NotNull
/*     */       public RRWebInteractionEvent.InteractionType deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/*  43 */         return RRWebInteractionEvent.InteractionType.values()[reader.nextInt()];
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
/*  58 */   private int pointerType = 2;
/*     */   
/*     */   private int pointerId;
/*     */   
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   @Nullable
/*     */   private Map<String, Object> dataUnknown;
/*     */   
/*     */   public RRWebInteractionEvent() {
/*  68 */     super(RRWebIncrementalSnapshotEvent.IncrementalSource.MouseInteraction);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public InteractionType getInteractionType() {
/*  73 */     return this.interactionType;
/*     */   }
/*     */   
/*     */   public void setInteractionType(@Nullable InteractionType type) {
/*  77 */     this.interactionType = type;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  81 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  85 */     this.id = id;
/*     */   }
/*     */   
/*     */   public float getX() {
/*  89 */     return this.x;
/*     */   }
/*     */   
/*     */   public void setX(float x) {
/*  93 */     this.x = x;
/*     */   }
/*     */   
/*     */   public float getY() {
/*  97 */     return this.y;
/*     */   }
/*     */   
/*     */   public void setY(float y) {
/* 101 */     this.y = y;
/*     */   }
/*     */   
/*     */   public int getPointerType() {
/* 105 */     return this.pointerType;
/*     */   }
/*     */   
/*     */   public void setPointerType(int pointerType) {
/* 109 */     this.pointerType = pointerType;
/*     */   }
/*     */   
/*     */   public int getPointerId() {
/* 113 */     return this.pointerId;
/*     */   }
/*     */   
/*     */   public void setPointerId(int pointerId) {
/* 117 */     this.pointerId = pointerId;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getDataUnknown() {
/* 122 */     return this.dataUnknown;
/*     */   }
/*     */   
/*     */   public void setDataUnknown(@Nullable Map<String, Object> dataUnknown) {
/* 126 */     this.dataUnknown = dataUnknown;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 131 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 136 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String DATA = "data";
/*     */     
/*     */     public static final String TYPE = "type";
/*     */     
/*     */     public static final String ID = "id";
/*     */     public static final String X = "x";
/*     */     public static final String Y = "y";
/*     */     public static final String POINTER_TYPE = "pointerType";
/*     */     public static final String POINTER_ID = "pointerId";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 154 */     writer.beginObject();
/* 155 */     (new RRWebEvent.Serializer()).serialize(this, writer, logger);
/* 156 */     writer.name("data");
/* 157 */     serializeData(writer, logger);
/* 158 */     if (this.unknown != null) {
/* 159 */       for (String key : this.unknown.keySet()) {
/* 160 */         Object value = this.unknown.get(key);
/* 161 */         writer.name(key);
/* 162 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 165 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializeData(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 170 */     writer.beginObject();
/* 171 */     (new RRWebIncrementalSnapshotEvent.Serializer()).serialize(this, writer, logger);
/* 172 */     writer.name("type").value(logger, this.interactionType);
/* 173 */     writer.name("id").value(this.id);
/* 174 */     writer.name("x").value(this.x);
/* 175 */     writer.name("y").value(this.y);
/* 176 */     writer.name("pointerType").value(this.pointerType);
/* 177 */     writer.name("pointerId").value(this.pointerId);
/* 178 */     if (this.dataUnknown != null) {
/* 179 */       for (String key : this.dataUnknown.keySet()) {
/* 180 */         Object value = this.dataUnknown.get(key);
/* 181 */         writer.name(key);
/* 182 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 185 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<RRWebInteractionEvent>
/*     */   {
/*     */     @NotNull
/*     */     public RRWebInteractionEvent deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 193 */       reader.beginObject();
/* 194 */       Map<String, Object> unknown = null;
/*     */       
/* 196 */       RRWebInteractionEvent event = new RRWebInteractionEvent();
/* 197 */       RRWebEvent.Deserializer baseEventDeserializer = new RRWebEvent.Deserializer();
/*     */       
/* 199 */       while (reader.peek() == JsonToken.NAME) {
/* 200 */         String nextName = reader.nextName();
/* 201 */         switch (nextName) {
/*     */           case "data":
/* 203 */             deserializeData(event, reader, logger);
/*     */             continue;
/*     */         } 
/* 206 */         if (!baseEventDeserializer.deserializeValue(event, nextName, reader, logger)) {
/* 207 */           if (unknown == null) {
/* 208 */             unknown = new HashMap<>();
/*     */           }
/* 210 */           reader.nextUnknown(logger, unknown, nextName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 216 */       event.setUnknown(unknown);
/* 217 */       reader.endObject();
/* 218 */       return event;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void deserializeData(@NotNull RRWebInteractionEvent event, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 226 */       Map<String, Object> dataUnknown = null;
/*     */       
/* 228 */       RRWebIncrementalSnapshotEvent.Deserializer baseEventDeserializer = new RRWebIncrementalSnapshotEvent.Deserializer();
/*     */ 
/*     */       
/* 231 */       reader.beginObject();
/* 232 */       while (reader.peek() == JsonToken.NAME) {
/* 233 */         String nextName = reader.nextName();
/* 234 */         switch (nextName) {
/*     */           case "type":
/* 236 */             event.interactionType = (RRWebInteractionEvent.InteractionType)reader.nextOrNull(logger, new RRWebInteractionEvent.InteractionType.Deserializer());
/*     */             continue;
/*     */           case "id":
/* 239 */             event.id = reader.nextInt();
/*     */             continue;
/*     */           case "x":
/* 242 */             event.x = reader.nextFloat();
/*     */             continue;
/*     */           case "y":
/* 245 */             event.y = reader.nextFloat();
/*     */             continue;
/*     */           case "pointerType":
/* 248 */             event.pointerType = reader.nextInt();
/*     */             continue;
/*     */           case "pointerId":
/* 251 */             event.pointerId = reader.nextInt();
/*     */             continue;
/*     */         } 
/* 254 */         if (!baseEventDeserializer.deserializeValue(event, nextName, reader, logger)) {
/* 255 */           if (dataUnknown == null) {
/* 256 */             dataUnknown = new HashMap<>();
/*     */           }
/* 258 */           reader.nextUnknown(logger, dataUnknown, nextName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 263 */       event.setDataUnknown(dataUnknown);
/* 264 */       reader.endObject();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\rrweb\RRWebInteractionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */