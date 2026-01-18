/*     */ package io.sentry.profilemeasurements;
/*     */ 
/*     */ import io.sentry.DateUtils;
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class ProfileMeasurementValue implements JsonUnknown, JsonSerializable {
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   private double timestamp;
/*     */   @NotNull
/*     */   private String relativeStartNs;
/*     */   private double value;
/*     */   
/*     */   public ProfileMeasurementValue() {
/*  32 */     this(Long.valueOf(0L), Integer.valueOf(0), 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public ProfileMeasurementValue(@NotNull Long relativeStartNs, @NotNull Number value, long nanoTimestamp) {
/*  37 */     this.relativeStartNs = relativeStartNs.toString();
/*  38 */     this.value = value.doubleValue();
/*  39 */     this.timestamp = DateUtils.nanosToSeconds(nanoTimestamp);
/*     */   }
/*     */   
/*     */   public double getTimestamp() {
/*  43 */     return this.timestamp;
/*     */   }
/*     */   
/*     */   public double getValue() {
/*  47 */     return this.value;
/*     */   }
/*     */   @NotNull
/*     */   public String getRelativeStartNs() {
/*  51 */     return this.relativeStartNs;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  56 */     if (this == o) return true; 
/*  57 */     if (o == null || getClass() != o.getClass()) return false; 
/*  58 */     ProfileMeasurementValue that = (ProfileMeasurementValue)o;
/*  59 */     return (Objects.equals(this.unknown, that.unknown) && this.relativeStartNs
/*  60 */       .equals(that.relativeStartNs) && this.value == that.value && this.timestamp == that.timestamp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  67 */     return Objects.hash(new Object[] { this.unknown, this.relativeStartNs, Double.valueOf(this.value) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String VALUE = "value";
/*     */     
/*     */     public static final String START_NS = "elapsed_since_start_ns";
/*     */     
/*     */     public static final String TIMESTAMP = "timestamp";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  81 */     writer.beginObject();
/*  82 */     writer.name("value").value(logger, Double.valueOf(this.value));
/*  83 */     writer.name("elapsed_since_start_ns").value(logger, this.relativeStartNs);
/*  84 */     writer.name("timestamp").value(logger, doubleToBigDecimal(Double.valueOf(this.timestamp)));
/*  85 */     if (this.unknown != null) {
/*  86 */       for (String key : this.unknown.keySet()) {
/*  87 */         Object value = this.unknown.get(key);
/*  88 */         writer.name(key);
/*  89 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/*  92 */     writer.endObject();
/*     */   }
/*     */   @NotNull
/*     */   private BigDecimal doubleToBigDecimal(@NotNull Double value) {
/*  96 */     return BigDecimal.valueOf(value.doubleValue()).setScale(6, RoundingMode.DOWN);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 102 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 107 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<ProfileMeasurementValue>
/*     */   {
/*     */     @NotNull
/*     */     public ProfileMeasurementValue deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 115 */       reader.beginObject();
/* 116 */       ProfileMeasurementValue data = new ProfileMeasurementValue();
/* 117 */       Map<String, Object> unknown = null;
/*     */       
/* 119 */       while (reader.peek() == JsonToken.NAME) {
/* 120 */         Double value; String startNs; Double timestamp; String nextName = reader.nextName();
/* 121 */         switch (nextName) {
/*     */           case "value":
/* 123 */             value = reader.nextDoubleOrNull();
/* 124 */             if (value != null) {
/* 125 */               data.value = value.doubleValue();
/*     */             }
/*     */             continue;
/*     */           case "elapsed_since_start_ns":
/* 129 */             startNs = reader.nextStringOrNull();
/* 130 */             if (startNs != null) {
/* 131 */               data.relativeStartNs = startNs;
/*     */             }
/*     */             continue;
/*     */           
/*     */           case "timestamp":
/*     */             try {
/* 137 */               timestamp = reader.nextDoubleOrNull();
/* 138 */             } catch (NumberFormatException e) {
/* 139 */               Date date = reader.nextDateOrNull(logger);
/* 140 */               timestamp = (date != null) ? Double.valueOf(DateUtils.dateToSeconds(date)) : null;
/*     */             } 
/* 142 */             if (timestamp != null) {
/* 143 */               data.timestamp = timestamp.doubleValue();
/*     */             }
/*     */             continue;
/*     */         } 
/* 147 */         if (unknown == null) {
/* 148 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 150 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 154 */       data.setUnknown(unknown);
/* 155 */       reader.endObject();
/* 156 */       return data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\profilemeasurements\ProfileMeasurementValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */