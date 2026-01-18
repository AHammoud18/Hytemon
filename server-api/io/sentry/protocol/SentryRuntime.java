/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SentryRuntime
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String TYPE = "runtime";
/*     */   @Nullable
/*     */   private String name;
/*     */   @Nullable
/*     */   private String version;
/*     */   @Nullable
/*     */   private String rawDescription;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public SentryRuntime() {}
/*     */   
/*     */   SentryRuntime(@NotNull SentryRuntime sentryRuntime) {
/*  38 */     this.name = sentryRuntime.name;
/*  39 */     this.version = sentryRuntime.version;
/*  40 */     this.rawDescription = sentryRuntime.rawDescription;
/*  41 */     this.unknown = CollectionUtils.newConcurrentHashMap(sentryRuntime.unknown);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getName() {
/*  48 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(@Nullable String name) {
/*  52 */     this.name = name;
/*     */   }
/*     */   @Nullable
/*     */   public String getVersion() {
/*  56 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setVersion(@Nullable String version) {
/*  60 */     this.version = version;
/*     */   }
/*     */   @Nullable
/*     */   public String getRawDescription() {
/*  64 */     return this.rawDescription;
/*     */   }
/*     */   
/*     */   public void setRawDescription(@Nullable String rawDescription) {
/*  68 */     this.rawDescription = rawDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String NAME = "name";
/*     */     
/*     */     public static final String VERSION = "version";
/*     */     
/*     */     public static final String RAW_DESCRIPTION = "raw_description";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  82 */     writer.beginObject();
/*  83 */     if (this.name != null) {
/*  84 */       writer.name("name").value(this.name);
/*     */     }
/*  86 */     if (this.version != null) {
/*  87 */       writer.name("version").value(this.version);
/*     */     }
/*  89 */     if (this.rawDescription != null) {
/*  90 */       writer.name("raw_description").value(this.rawDescription);
/*     */     }
/*  92 */     if (this.unknown != null) {
/*  93 */       for (String key : this.unknown.keySet()) {
/*  94 */         Object value = this.unknown.get(key);
/*  95 */         writer.name(key);
/*  96 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/*  99 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 105 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 110 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryRuntime> {
/*     */     @NotNull
/*     */     public SentryRuntime deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 117 */       reader.beginObject();
/* 118 */       SentryRuntime runtime = new SentryRuntime();
/* 119 */       Map<String, Object> unknown = null;
/* 120 */       while (reader.peek() == JsonToken.NAME) {
/* 121 */         String nextName = reader.nextName();
/* 122 */         switch (nextName) {
/*     */           case "name":
/* 124 */             runtime.name = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "version":
/* 127 */             runtime.version = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "raw_description":
/* 130 */             runtime.rawDescription = reader.nextStringOrNull();
/*     */             continue;
/*     */         } 
/* 133 */         if (unknown == null) {
/* 134 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 136 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 140 */       runtime.setUnknown(unknown);
/* 141 */       reader.endObject();
/* 142 */       return runtime;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\SentryRuntime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */