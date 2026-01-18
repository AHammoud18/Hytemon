/*     */ package io.sentry.profilemeasurements;
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
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class ProfileMeasurement implements JsonUnknown, JsonSerializable {
/*     */   public static final String ID_FROZEN_FRAME_RENDERS = "frozen_frame_renders";
/*     */   public static final String ID_SLOW_FRAME_RENDERS = "slow_frame_renders";
/*     */   public static final String ID_SCREEN_FRAME_RATES = "screen_frame_rates";
/*     */   public static final String ID_CPU_USAGE = "cpu_usage";
/*     */   public static final String ID_MEMORY_FOOTPRINT = "memory_footprint";
/*     */   public static final String ID_MEMORY_NATIVE_FOOTPRINT = "memory_native_footprint";
/*     */   public static final String ID_UNKNOWN = "unknown";
/*     */   public static final String UNIT_HZ = "hz";
/*     */   public static final String UNIT_NANOSECONDS = "nanosecond";
/*     */   public static final String UNIT_BYTES = "byte";
/*     */   public static final String UNIT_PERCENT = "percent";
/*     */   public static final String UNIT_UNKNOWN = "unknown";
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   @NotNull
/*     */   private String unit;
/*     */   @NotNull
/*     */   private Collection<ProfileMeasurementValue> values;
/*     */   
/*     */   public ProfileMeasurement() {
/*  43 */     this("unknown", new ArrayList<>());
/*     */   }
/*     */ 
/*     */   
/*     */   public ProfileMeasurement(@NotNull String unit, @NotNull Collection<ProfileMeasurementValue> values) {
/*  48 */     this.unit = unit;
/*  49 */     this.values = values;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  54 */     if (this == o) return true; 
/*  55 */     if (o == null || getClass() != o.getClass()) return false; 
/*  56 */     ProfileMeasurement that = (ProfileMeasurement)o;
/*  57 */     return (Objects.equals(this.unknown, that.unknown) && this.unit
/*  58 */       .equals(that.unit) && (new ArrayList(this.values))
/*  59 */       .equals(new ArrayList<>(that.values)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  64 */     return Objects.hash(new Object[] { this.unknown, this.unit, this.values });
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String UNIT = "unit";
/*     */     
/*     */     public static final String VALUES = "values";
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  77 */     writer.beginObject();
/*  78 */     writer.name("unit").value(logger, this.unit);
/*  79 */     writer.name("values").value(logger, this.values);
/*  80 */     if (this.unknown != null) {
/*  81 */       for (String key : this.unknown.keySet()) {
/*  82 */         Object value = this.unknown.get(key);
/*  83 */         writer.name(key);
/*  84 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/*  87 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  93 */     return this.unknown;
/*     */   }
/*     */   @NotNull
/*     */   public String getUnit() {
/*  97 */     return this.unit;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 102 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public void setUnit(@NotNull String unit) {
/* 106 */     this.unit = unit;
/*     */   }
/*     */   @NotNull
/*     */   public Collection<ProfileMeasurementValue> getValues() {
/* 110 */     return this.values;
/*     */   }
/*     */   
/*     */   public void setValues(@NotNull Collection<ProfileMeasurementValue> values) {
/* 114 */     this.values = values;
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<ProfileMeasurement>
/*     */   {
/*     */     @NotNull
/*     */     public ProfileMeasurement deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 122 */       reader.beginObject();
/* 123 */       ProfileMeasurement data = new ProfileMeasurement();
/* 124 */       Map<String, Object> unknown = null;
/*     */       
/* 126 */       while (reader.peek() == JsonToken.NAME) {
/* 127 */         String unit; List<ProfileMeasurementValue> values; String nextName = reader.nextName();
/* 128 */         switch (nextName) {
/*     */           case "unit":
/* 130 */             unit = reader.nextStringOrNull();
/* 131 */             if (unit != null) {
/* 132 */               data.unit = unit;
/*     */             }
/*     */             continue;
/*     */           
/*     */           case "values":
/* 137 */             values = reader.nextListOrNull(logger, new ProfileMeasurementValue.Deserializer());
/* 138 */             if (values != null) {
/* 139 */               data.values = values;
/*     */             }
/*     */             continue;
/*     */         } 
/* 143 */         if (unknown == null) {
/* 144 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 146 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 150 */       data.setUnknown(unknown);
/* 151 */       reader.endObject();
/* 152 */       return data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\profilemeasurements\ProfileMeasurement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */