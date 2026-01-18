/*     */ package io.sentry;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class MonitorSchedule implements JsonUnknown, JsonSerializable {
/*     */   @NotNull
/*     */   private String type;
/*     */   @NotNull
/*     */   private String value;
/*     */   
/*     */   @NotNull
/*     */   public static MonitorSchedule crontab(@NotNull String value) {
/*  14 */     return new MonitorSchedule(MonitorScheduleType.CRONTAB.apiName(), value, null);
/*     */   } @Nullable
/*     */   private String unit; @Nullable
/*     */   private Map<String, Object> unknown; @NotNull
/*     */   public static MonitorSchedule interval(@NotNull Integer value, @NotNull MonitorScheduleUnit unit) {
/*  19 */     return new MonitorSchedule(MonitorScheduleType.INTERVAL
/*  20 */         .apiName(), value.toString(), unit.apiName());
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
/*     */   @Internal
/*     */   public MonitorSchedule(@NotNull String type, @NotNull String value, @Nullable String unit) {
/*  36 */     this.type = type;
/*  37 */     this.value = value;
/*  38 */     this.unit = unit;
/*     */   }
/*     */   @NotNull
/*     */   public String getType() {
/*  42 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(@NotNull String type) {
/*  46 */     this.type = type;
/*     */   }
/*     */   @NotNull
/*     */   public String getValue() {
/*  50 */     return this.value;
/*     */   }
/*     */   
/*     */   public void setValue(@NotNull String value) {
/*  54 */     this.value = value;
/*     */   }
/*     */   
/*     */   public void setValue(@NotNull Integer value) {
/*  58 */     this.value = value.toString();
/*     */   }
/*     */   @Nullable
/*     */   public String getUnit() {
/*  62 */     return this.unit;
/*     */   }
/*     */   
/*     */   public void setUnit(@Nullable String unit) {
/*  66 */     this.unit = unit;
/*     */   }
/*     */   
/*     */   public void setUnit(@Nullable MonitorScheduleUnit unit) {
/*  70 */     this.unit = (unit == null) ? null : unit.apiName();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String TYPE = "type";
/*     */     
/*     */     public static final String VALUE = "value";
/*     */     
/*     */     public static final String UNIT = "unit";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  85 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  90 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  98 */     writer.beginObject();
/*  99 */     writer.name("type").value(this.type);
/* 100 */     if (MonitorScheduleType.INTERVAL.apiName().equalsIgnoreCase(this.type)) {
/*     */       try {
/* 102 */         writer.name("value").value(Integer.valueOf(this.value));
/* 103 */       } catch (Throwable t) {
/* 104 */         logger.log(SentryLevel.ERROR, "Unable to serialize monitor schedule value: %s", new Object[] { this.value });
/*     */       } 
/*     */     } else {
/* 107 */       writer.name("value").value(this.value);
/*     */     } 
/* 109 */     if (this.unit != null) {
/* 110 */       writer.name("unit").value(this.unit);
/*     */     }
/* 112 */     if (this.unknown != null) {
/* 113 */       for (String key : this.unknown.keySet()) {
/* 114 */         Object value = this.unknown.get(key);
/* 115 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 118 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<MonitorSchedule>
/*     */   {
/*     */     @NotNull
/*     */     public MonitorSchedule deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 127 */       String type = null;
/* 128 */       String value = null;
/* 129 */       String unit = null;
/* 130 */       Map<String, Object> unknown = null;
/*     */       
/* 132 */       reader.beginObject();
/* 133 */       while (reader.peek() == JsonToken.NAME) {
/* 134 */         String nextName = reader.nextName();
/* 135 */         switch (nextName) {
/*     */           case "type":
/* 137 */             type = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "value":
/* 140 */             value = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "unit":
/* 143 */             unit = reader.nextStringOrNull();
/*     */             continue;
/*     */         } 
/* 146 */         if (unknown == null) {
/* 147 */           unknown = new HashMap<>();
/*     */         }
/* 149 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 153 */       reader.endObject();
/*     */       
/* 155 */       if (type == null) {
/* 156 */         String message = "Missing required field \"type\"";
/* 157 */         Exception exception = new IllegalStateException(message);
/* 158 */         logger.log(SentryLevel.ERROR, message, exception);
/* 159 */         throw exception;
/*     */       } 
/*     */       
/* 162 */       if (value == null) {
/* 163 */         String message = "Missing required field \"value\"";
/* 164 */         Exception exception = new IllegalStateException(message);
/* 165 */         logger.log(SentryLevel.ERROR, message, exception);
/* 166 */         throw exception;
/*     */       } 
/*     */       
/* 169 */       MonitorSchedule monitorSchedule = new MonitorSchedule(type, value, unit);
/* 170 */       monitorSchedule.setUnknown(unknown);
/* 171 */       return monitorSchedule;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\MonitorSchedule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */