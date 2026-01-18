/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryLevel;
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
/*     */ @Internal
/*     */ public final class MeasurementValue
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String KEY_APP_START_COLD = "app_start_cold";
/*     */   public static final String KEY_APP_START_WARM = "app_start_warm";
/*     */   public static final String KEY_FRAMES_TOTAL = "frames_total";
/*     */   public static final String KEY_FRAMES_SLOW = "frames_slow";
/*     */   public static final String KEY_FRAMES_FROZEN = "frames_frozen";
/*     */   public static final String KEY_FRAMES_DELAY = "frames_delay";
/*     */   public static final String KEY_TIME_TO_INITIAL_DISPLAY = "time_to_initial_display";
/*     */   public static final String KEY_TIME_TO_FULL_DISPLAY = "time_to_full_display";
/*     */   @NotNull
/*     */   private final Number value;
/*     */   @Nullable
/*     */   private final String unit;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public MeasurementValue(@NotNull Number value, @Nullable String unit) {
/*  40 */     this.value = value;
/*  41 */     this.unit = unit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @TestOnly
/*     */   public MeasurementValue(@NotNull Number value, @Nullable String unit, @Nullable Map<String, Object> unknown) {
/*  49 */     this.value = value;
/*  50 */     this.unit = unit;
/*  51 */     this.unknown = unknown;
/*     */   }
/*     */   @TestOnly
/*     */   @NotNull
/*     */   public Number getValue() {
/*  56 */     return this.value;
/*     */   }
/*     */   @Nullable
/*     */   public String getUnit() {
/*  60 */     return this.unit;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  66 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  71 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String VALUE = "value";
/*     */     
/*     */     public static final String UNIT = "unit";
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  84 */     writer.beginObject();
/*  85 */     writer.name("value").value(this.value);
/*     */     
/*  87 */     if (this.unit != null) {
/*  88 */       writer.name("unit").value(this.unit);
/*     */     }
/*     */     
/*  91 */     if (this.unknown != null) {
/*  92 */       for (String key : this.unknown.keySet()) {
/*  93 */         Object value = this.unknown.get(key);
/*  94 */         writer.name(key);
/*  95 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/*     */     
/*  99 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<MeasurementValue> {
/*     */     @NotNull
/*     */     public MeasurementValue deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 106 */       reader.beginObject();
/*     */       
/* 108 */       String unit = null;
/* 109 */       Number value = null;
/* 110 */       Map<String, Object> unknown = null;
/*     */       
/* 112 */       while (reader.peek() == JsonToken.NAME) {
/* 113 */         String nextName = reader.nextName();
/* 114 */         switch (nextName) {
/*     */           case "value":
/* 116 */             value = (Number)reader.nextObjectOrNull();
/*     */             continue;
/*     */           case "unit":
/* 119 */             unit = reader.nextStringOrNull();
/*     */             continue;
/*     */         } 
/* 122 */         if (unknown == null) {
/* 123 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 125 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 130 */       reader.endObject();
/*     */       
/* 132 */       if (value == null) {
/* 133 */         String message = "Missing required field \"value\"";
/* 134 */         Exception ex = new IllegalStateException("Missing required field \"value\"");
/* 135 */         logger.log(SentryLevel.ERROR, "Missing required field \"value\"", ex);
/* 136 */         throw ex;
/*     */       } 
/*     */       
/* 139 */       MeasurementValue measurement = new MeasurementValue(value, unit);
/* 140 */       measurement.setUnknown(unknown);
/*     */       
/* 142 */       return measurement;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\MeasurementValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */