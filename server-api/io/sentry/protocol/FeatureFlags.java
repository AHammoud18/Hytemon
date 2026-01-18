/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class FeatureFlags
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String TYPE = "flags";
/*     */   
/*     */   public FeatureFlags() {
/*  26 */     this.values = new ArrayList<>();
/*     */   } @NotNull
/*     */   private List<FeatureFlag> values; @Nullable
/*     */   private Map<String, Object> unknown; FeatureFlags(@NotNull FeatureFlags featureFlags) {
/*  30 */     this.values = featureFlags.values;
/*  31 */     this.unknown = CollectionUtils.newConcurrentHashMap(featureFlags.unknown);
/*     */   }
/*     */   
/*     */   public FeatureFlags(@NotNull List<FeatureFlag> values) {
/*  35 */     this.values = values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<FeatureFlag> getValues() {
/*  43 */     return this.values;
/*     */   }
/*     */   
/*     */   public void setValues(@NotNull List<FeatureFlag> values) {
/*  47 */     this.values = values;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  52 */     if (this == o) return true; 
/*  53 */     if (o == null || getClass() != o.getClass()) return false; 
/*  54 */     FeatureFlags flags = (FeatureFlags)o;
/*  55 */     return Objects.equals(this.values, flags.values);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  60 */     return Objects.hash(new Object[] { this.values });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  68 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  73 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String VALUES = "values";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  83 */     writer.beginObject();
/*     */     
/*  85 */     writer.name("values").value(logger, this.values);
/*     */     
/*  87 */     if (this.unknown != null) {
/*  88 */       for (String key : this.unknown.keySet()) {
/*  89 */         Object value = this.unknown.get(key);
/*  90 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/*  93 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<FeatureFlags>
/*     */   {
/*     */     @NotNull
/*     */     public FeatureFlags deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 101 */       reader.beginObject();
/* 102 */       List<FeatureFlag> values = null;
/* 103 */       Map<String, Object> unknown = null;
/* 104 */       while (reader.peek() == JsonToken.NAME) {
/* 105 */         String nextName = reader.nextName();
/* 106 */         switch (nextName) {
/*     */           case "values":
/* 108 */             values = reader.nextListOrNull(logger, new FeatureFlag.Deserializer());
/*     */             continue;
/*     */         } 
/* 111 */         if (unknown == null) {
/* 112 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 114 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 118 */       if (values == null) {
/* 119 */         values = new ArrayList<>();
/*     */       }
/* 121 */       FeatureFlags flags = new FeatureFlags(values);
/* 122 */       flags.setUnknown(unknown);
/* 123 */       reader.endObject();
/* 124 */       return flags;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\FeatureFlags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */