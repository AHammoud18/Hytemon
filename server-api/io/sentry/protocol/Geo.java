/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Geo
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private String city;
/*     */   @Nullable
/*     */   private String countryCode;
/*     */   @Nullable
/*     */   private String region;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public Geo() {}
/*     */   
/*     */   public Geo(@NotNull Geo geo) {
/*  33 */     this.city = geo.city;
/*  34 */     this.countryCode = geo.countryCode;
/*  35 */     this.region = geo.region;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Geo fromMap(@NotNull Map<String, Object> map) {
/*  45 */     Geo geo = new Geo();
/*  46 */     for (Map.Entry<String, Object> entry : map.entrySet()) {
/*  47 */       Object value = entry.getValue();
/*  48 */       switch ((String)entry.getKey()) {
/*     */         case "city":
/*  50 */           geo.city = (value instanceof String) ? (String)value : null;
/*     */         
/*     */         case "country_code":
/*  53 */           geo.countryCode = (value instanceof String) ? (String)value : null;
/*     */         
/*     */         case "region":
/*  56 */           geo.region = (value instanceof String) ? (String)value : null;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/*  62 */     return geo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getCity() {
/*  71 */     return this.city;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCity(@Nullable String city) {
/*  80 */     this.city = city;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getCountryCode() {
/*  89 */     return this.countryCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCountryCode(@Nullable String countryCode) {
/*  98 */     this.countryCode = countryCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getRegion() {
/* 107 */     return this.region;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRegion(@Nullable String region) {
/* 116 */     this.region = region;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String CITY = "city";
/*     */     
/*     */     public static final String COUNTRY_CODE = "country_code";
/*     */     public static final String REGION = "region";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 130 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 135 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 141 */     writer.beginObject();
/* 142 */     if (this.city != null) {
/* 143 */       writer.name("city").value(this.city);
/*     */     }
/* 145 */     if (this.countryCode != null) {
/* 146 */       writer.name("country_code").value(this.countryCode);
/*     */     }
/* 148 */     if (this.region != null) {
/* 149 */       writer.name("region").value(this.region);
/*     */     }
/* 151 */     if (this.unknown != null) {
/* 152 */       for (String key : this.unknown.keySet()) {
/* 153 */         Object value = this.unknown.get(key);
/* 154 */         writer.name(key);
/* 155 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 158 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<Geo>
/*     */   {
/*     */     @NotNull
/*     */     public Geo deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 166 */       reader.beginObject();
/* 167 */       Geo geo = new Geo();
/* 168 */       Map<String, Object> unknown = null;
/* 169 */       while (reader.peek() == JsonToken.NAME) {
/* 170 */         String nextName = reader.nextName();
/* 171 */         switch (nextName) {
/*     */           case "city":
/* 173 */             geo.city = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "country_code":
/* 176 */             geo.countryCode = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "region":
/* 179 */             geo.region = reader.nextStringOrNull();
/*     */             continue;
/*     */         } 
/* 182 */         if (unknown == null) {
/* 183 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 185 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 189 */       geo.setUnknown(unknown);
/* 190 */       reader.endObject();
/* 191 */       return geo;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\Geo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */