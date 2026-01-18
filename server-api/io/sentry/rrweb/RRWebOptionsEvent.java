/*     */ package io.sentry.rrweb;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.ScreenshotStrategyType;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.SentryReplayOptions;
/*     */ import io.sentry.protocol.SdkVersion;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class RRWebOptionsEvent extends RRWebEvent implements JsonSerializable, JsonUnknown {
/*     */   public static final String EVENT_TAG = "options";
/*     */   @NotNull
/*     */   private String tag;
/*     */   @NotNull
/*  26 */   private Map<String, Object> optionsPayload = new HashMap<>();
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   @Nullable
/*     */   private Map<String, Object> dataUnknown;
/*     */   
/*     */   public RRWebOptionsEvent() {
/*  33 */     super(RRWebEventType.Custom);
/*  34 */     this.tag = "options";
/*     */   }
/*     */   
/*     */   public RRWebOptionsEvent(@NotNull SentryOptions options) {
/*  38 */     this();
/*  39 */     SdkVersion sdkVersion = options.getSdkVersion();
/*  40 */     if (sdkVersion != null) {
/*  41 */       this.optionsPayload.put("nativeSdkName", sdkVersion.getName());
/*  42 */       this.optionsPayload.put("nativeSdkVersion", sdkVersion.getVersion());
/*     */     } 
/*  44 */     SentryReplayOptions replayOptions = options.getSessionReplay();
/*  45 */     this.optionsPayload.put("errorSampleRate", replayOptions.getOnErrorSampleRate());
/*  46 */     this.optionsPayload.put("sessionSampleRate", replayOptions.getSessionSampleRate());
/*  47 */     this.optionsPayload.put("maskAllImages", 
/*     */         
/*  49 */         Boolean.valueOf(replayOptions.getMaskViewClasses().contains("android.widget.ImageView")));
/*  50 */     this.optionsPayload.put("maskAllText", 
/*     */         
/*  52 */         Boolean.valueOf(replayOptions.getMaskViewClasses().contains("android.widget.TextView")));
/*  53 */     this.optionsPayload.put("quality", replayOptions.getQuality().serializedName());
/*  54 */     this.optionsPayload.put("maskedViewClasses", replayOptions.getMaskViewClasses());
/*  55 */     this.optionsPayload.put("unmaskedViewClasses", replayOptions.getUnmaskViewClasses());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     String screenshotStrategy = (replayOptions.getScreenshotStrategy() == ScreenshotStrategyType.PIXEL_COPY) ? "pixelCopy" : "canvas";
/*  61 */     this.optionsPayload.put("screenshotStrategy", screenshotStrategy);
/*  62 */     this.optionsPayload.put("networkDetailHasUrls", 
/*  63 */         Boolean.valueOf(!replayOptions.getNetworkDetailAllowUrls().isEmpty()));
/*     */ 
/*     */     
/*  66 */     if (!replayOptions.getNetworkDetailAllowUrls().isEmpty()) {
/*  67 */       this.optionsPayload.put("networkDetailAllowUrls", replayOptions.getNetworkDetailAllowUrls());
/*     */       
/*  69 */       this.optionsPayload.put("networkRequestHeaders", replayOptions.getNetworkRequestHeaders());
/*  70 */       this.optionsPayload.put("networkResponseHeaders", replayOptions.getNetworkResponseHeaders());
/*  71 */       this.optionsPayload.put("networkCaptureBodies", Boolean.valueOf(replayOptions.isNetworkCaptureBodies()));
/*     */       
/*  73 */       if (!replayOptions.getNetworkDetailDenyUrls().isEmpty()) {
/*  74 */         this.optionsPayload.put("networkDetailDenyUrls", replayOptions.getNetworkDetailDenyUrls());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getTag() {
/*  81 */     return this.tag;
/*     */   }
/*     */   
/*     */   public void setTag(@NotNull String tag) {
/*  85 */     this.tag = tag;
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, Object> getOptionsPayload() {
/*  89 */     return this.optionsPayload;
/*     */   }
/*     */   
/*     */   public void setOptionsPayload(@NotNull Map<String, Object> optionsPayload) {
/*  93 */     this.optionsPayload = optionsPayload;
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, Object> getDataUnknown() {
/*  97 */     return this.dataUnknown;
/*     */   }
/*     */   
/*     */   public void setDataUnknown(@Nullable Map<String, Object> dataUnknown) {
/* 101 */     this.dataUnknown = dataUnknown;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 106 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 111 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String DATA = "data";
/*     */     public static final String PAYLOAD = "payload";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 122 */     writer.beginObject();
/* 123 */     (new RRWebEvent.Serializer()).serialize(this, writer, logger);
/* 124 */     writer.name("data");
/* 125 */     serializeData(writer, logger);
/* 126 */     if (this.unknown != null) {
/* 127 */       for (String key : this.unknown.keySet()) {
/* 128 */         Object value = this.unknown.get(key);
/* 129 */         writer.name(key);
/* 130 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 133 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializeData(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 138 */     writer.beginObject();
/* 139 */     writer.name("tag").value(this.tag);
/* 140 */     writer.name("payload");
/* 141 */     serializePayload(writer, logger);
/* 142 */     if (this.dataUnknown != null) {
/* 143 */       for (String key : this.dataUnknown.keySet()) {
/* 144 */         Object value = this.dataUnknown.get(key);
/* 145 */         writer.name(key);
/* 146 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 149 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializePayload(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 154 */     writer.beginObject();
/* 155 */     if (this.optionsPayload != null) {
/* 156 */       for (String key : this.optionsPayload.keySet()) {
/* 157 */         Object value = this.optionsPayload.get(key);
/* 158 */         writer.name(key);
/* 159 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 162 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<RRWebOptionsEvent>
/*     */   {
/*     */     @NotNull
/*     */     public RRWebOptionsEvent deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 170 */       reader.beginObject();
/* 171 */       Map<String, Object> unknown = null;
/*     */       
/* 173 */       RRWebOptionsEvent event = new RRWebOptionsEvent();
/* 174 */       RRWebEvent.Deserializer baseEventDeserializer = new RRWebEvent.Deserializer();
/*     */       
/* 176 */       while (reader.peek() == JsonToken.NAME) {
/* 177 */         String nextName = reader.nextName();
/* 178 */         switch (nextName) {
/*     */           case "data":
/* 180 */             deserializeData(event, reader, logger);
/*     */             continue;
/*     */         } 
/* 183 */         if (!baseEventDeserializer.deserializeValue(event, nextName, reader, logger)) {
/* 184 */           if (unknown == null) {
/* 185 */             unknown = new HashMap<>();
/*     */           }
/* 187 */           reader.nextUnknown(logger, unknown, nextName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 193 */       event.setUnknown(unknown);
/* 194 */       reader.endObject();
/* 195 */       return event;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void deserializeData(@NotNull RRWebOptionsEvent event, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 203 */       Map<String, Object> dataUnknown = null;
/*     */       
/* 205 */       reader.beginObject();
/* 206 */       while (reader.peek() == JsonToken.NAME) {
/* 207 */         String tag, nextName = reader.nextName();
/* 208 */         switch (nextName) {
/*     */           case "tag":
/* 210 */             tag = reader.nextStringOrNull();
/* 211 */             event.tag = (tag == null) ? "" : tag;
/*     */             continue;
/*     */           case "payload":
/* 214 */             deserializePayload(event, reader, logger);
/*     */             continue;
/*     */         } 
/* 217 */         if (dataUnknown == null) {
/* 218 */           dataUnknown = new ConcurrentHashMap<>();
/*     */         }
/* 220 */         reader.nextUnknown(logger, dataUnknown, nextName);
/*     */       } 
/*     */       
/* 223 */       event.setDataUnknown(dataUnknown);
/* 224 */       reader.endObject();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void deserializePayload(@NotNull RRWebOptionsEvent event, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 233 */       Map<String, Object> optionsPayload = null;
/*     */       
/* 235 */       reader.beginObject();
/* 236 */       while (reader.peek() == JsonToken.NAME) {
/* 237 */         String nextName = reader.nextName();
/* 238 */         if (optionsPayload == null) {
/* 239 */           optionsPayload = new HashMap<>();
/*     */         }
/* 241 */         reader.nextUnknown(logger, optionsPayload, nextName);
/*     */       } 
/* 243 */       if (optionsPayload != null) {
/* 244 */         event.setOptionsPayload(optionsPayload);
/*     */       }
/* 246 */       reader.endObject();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\rrweb\RRWebOptionsEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */