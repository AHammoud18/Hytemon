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
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ public final class Browser
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String TYPE = "browser";
/*     */   @Nullable
/*     */   private String name;
/*     */   @Nullable
/*     */   private String version;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public Browser() {}
/*     */   
/*     */   Browser(@NotNull Browser browser) {
/*  33 */     this.name = browser.name;
/*  34 */     this.version = browser.version;
/*  35 */     this.unknown = CollectionUtils.newConcurrentHashMap(browser.unknown);
/*     */   }
/*     */   @Nullable
/*     */   public String getName() {
/*  39 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(@Nullable String name) {
/*  43 */     this.name = name;
/*     */   }
/*     */   @Nullable
/*     */   public String getVersion() {
/*  47 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setVersion(@Nullable String version) {
/*  51 */     this.version = version;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  56 */     if (this == o) return true; 
/*  57 */     if (o == null || getClass() != o.getClass()) return false; 
/*  58 */     Browser browser = (Browser)o;
/*  59 */     return (Objects.equals(this.name, browser.name) && Objects.equals(this.version, browser.version));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  64 */     return Objects.hash(new Object[] { this.name, this.version });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  72 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  77 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String NAME = "name";
/*     */     public static final String VERSION = "version";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  88 */     writer.beginObject();
/*  89 */     if (this.name != null) {
/*  90 */       writer.name("name").value(this.name);
/*     */     }
/*  92 */     if (this.version != null) {
/*  93 */       writer.name("version").value(this.version);
/*     */     }
/*  95 */     if (this.unknown != null) {
/*  96 */       for (String key : this.unknown.keySet()) {
/*  97 */         Object value = this.unknown.get(key);
/*  98 */         writer.name(key);
/*  99 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 102 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<Browser> {
/*     */     @NotNull
/*     */     public Browser deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 109 */       reader.beginObject();
/* 110 */       Browser browser = new Browser();
/* 111 */       Map<String, Object> unknown = null;
/* 112 */       while (reader.peek() == JsonToken.NAME) {
/* 113 */         String nextName = reader.nextName();
/* 114 */         switch (nextName) {
/*     */           case "name":
/* 116 */             browser.name = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "version":
/* 119 */             browser.version = reader.nextStringOrNull();
/*     */             continue;
/*     */         } 
/* 122 */         if (unknown == null) {
/* 123 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 125 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 129 */       browser.setUnknown(unknown);
/* 130 */       reader.endObject();
/* 131 */       return browser;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\Browser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */