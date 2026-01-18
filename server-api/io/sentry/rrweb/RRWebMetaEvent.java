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
/*     */ public final class RRWebMetaEvent extends RRWebEvent implements JsonUnknown, JsonSerializable {
/*     */   @NotNull
/*     */   private String href;
/*     */   private int height;
/*     */   private int width;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   @Nullable
/*     */   private Map<String, Object> dataUnknown;
/*     */   
/*     */   public RRWebMetaEvent() {
/*  29 */     super(RRWebEventType.Meta);
/*  30 */     this.href = "";
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getHref() {
/*  35 */     return this.href;
/*     */   }
/*     */   
/*     */   public void setHref(@NotNull String href) {
/*  39 */     this.href = href;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/*  43 */     return this.height;
/*     */   }
/*     */   
/*     */   public void setHeight(int height) {
/*  47 */     this.height = height;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  51 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(int width) {
/*  55 */     this.width = width;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getDataUnknown() {
/*  60 */     return this.dataUnknown;
/*     */   }
/*     */   
/*     */   public void setDataUnknown(@Nullable Map<String, Object> dataUnknown) {
/*  64 */     this.dataUnknown = dataUnknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  69 */     if (this == o) return true; 
/*  70 */     if (o == null || getClass() != o.getClass()) return false; 
/*  71 */     if (!super.equals(o)) return false; 
/*  72 */     RRWebMetaEvent metaEvent = (RRWebMetaEvent)o;
/*  73 */     return (this.height == metaEvent.height && this.width == metaEvent.width && 
/*     */       
/*  75 */       Objects.equals(this.href, metaEvent.href));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  80 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.href, Integer.valueOf(this.height), Integer.valueOf(this.width) });
/*     */   }
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String DATA = "data";
/*     */     public static final String HREF = "href";
/*     */     public static final String HEIGHT = "height";
/*     */     public static final String WIDTH = "width";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  92 */     writer.beginObject();
/*  93 */     (new RRWebEvent.Serializer()).serialize(this, writer, logger);
/*  94 */     writer.name("data");
/*  95 */     serializeData(writer, logger);
/*  96 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializeData(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 101 */     writer.beginObject();
/* 102 */     writer.name("href").value(this.href);
/* 103 */     writer.name("height").value(this.height);
/* 104 */     writer.name("width").value(this.width);
/* 105 */     if (this.unknown != null) {
/* 106 */       for (String key : this.unknown.keySet()) {
/* 107 */         Object value = this.unknown.get(key);
/* 108 */         writer.name(key);
/* 109 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 112 */     writer.endObject();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 117 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 122 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<RRWebMetaEvent>
/*     */   {
/*     */     @NotNull
/*     */     public RRWebMetaEvent deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 131 */       reader.beginObject();
/* 132 */       Map<String, Object> unknown = null;
/* 133 */       RRWebMetaEvent event = new RRWebMetaEvent();
/* 134 */       RRWebEvent.Deserializer baseEventDeserializer = new RRWebEvent.Deserializer();
/*     */       
/* 136 */       while (reader.peek() == JsonToken.NAME) {
/* 137 */         String nextName = reader.nextName();
/* 138 */         switch (nextName) {
/*     */           case "data":
/* 140 */             deserializeData(event, reader, logger);
/*     */             continue;
/*     */         } 
/* 143 */         if (!baseEventDeserializer.deserializeValue(event, nextName, reader, logger)) {
/* 144 */           if (unknown == null) {
/* 145 */             unknown = new HashMap<>();
/*     */           }
/* 147 */           reader.nextUnknown(logger, unknown, nextName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 152 */       event.setUnknown(unknown);
/* 153 */       reader.endObject();
/* 154 */       return event;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void deserializeData(@NotNull RRWebMetaEvent event, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 162 */       Map<String, Object> unknown = null;
/*     */       
/* 164 */       reader.beginObject();
/* 165 */       while (reader.peek() == JsonToken.NAME) {
/* 166 */         String href; Integer height, width; String nextName = reader.nextName();
/* 167 */         switch (nextName) {
/*     */           case "href":
/* 169 */             href = reader.nextStringOrNull();
/* 170 */             event.href = (href == null) ? "" : href;
/*     */             continue;
/*     */           case "height":
/* 173 */             height = reader.nextIntegerOrNull();
/* 174 */             event.height = (height == null) ? 0 : height.intValue();
/*     */             continue;
/*     */           case "width":
/* 177 */             width = reader.nextIntegerOrNull();
/* 178 */             event.width = (width == null) ? 0 : width.intValue();
/*     */             continue;
/*     */         } 
/* 181 */         if (unknown == null) {
/* 182 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 184 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */       
/* 187 */       event.setDataUnknown(unknown);
/* 188 */       reader.endObject();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\rrweb\RRWebMetaEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */