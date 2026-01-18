/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class SentryPackage
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @NotNull
/*     */   private String name;
/*     */   @NotNull
/*     */   private String version;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public SentryPackage(@NotNull String name, @NotNull String version) {
/*  30 */     this.name = (String)Objects.requireNonNull(name, "name is required.");
/*  31 */     this.version = (String)Objects.requireNonNull(version, "version is required.");
/*     */   }
/*     */   @NotNull
/*     */   public String getName() {
/*  35 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(@NotNull String name) {
/*  39 */     this.name = (String)Objects.requireNonNull(name, "name is required.");
/*     */   }
/*     */   @NotNull
/*     */   public String getVersion() {
/*  43 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setVersion(@NotNull String version) {
/*  47 */     this.version = (String)Objects.requireNonNull(version, "version is required.");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  52 */     if (this == o) return true; 
/*  53 */     if (o == null || getClass() != o.getClass()) return false; 
/*  54 */     SentryPackage that = (SentryPackage)o;
/*  55 */     return (Objects.equals(this.name, that.name) && 
/*  56 */       Objects.equals(this.version, that.version));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  61 */     return Objects.hash(new Object[] { this.name, this.version });
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String NAME = "name";
/*     */     
/*     */     public static final String VERSION = "version";
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  75 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  80 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  88 */     writer.beginObject();
/*  89 */     writer.name("name").value(this.name);
/*  90 */     writer.name("version").value(this.version);
/*  91 */     if (this.unknown != null) {
/*  92 */       for (String key : this.unknown.keySet()) {
/*  93 */         Object value = this.unknown.get(key);
/*  94 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/*  97 */     writer.endObject();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryPackage>
/*     */   {
/*     */     @NotNull
/*     */     public SentryPackage deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 107 */       String name = null;
/* 108 */       String version = null;
/* 109 */       Map<String, Object> unknown = null;
/*     */       
/* 111 */       reader.beginObject();
/* 112 */       while (reader.peek() == JsonToken.NAME) {
/* 113 */         String nextName = reader.nextName();
/* 114 */         switch (nextName) {
/*     */           case "name":
/* 116 */             name = reader.nextString();
/*     */             continue;
/*     */           case "version":
/* 119 */             version = reader.nextString();
/*     */             continue;
/*     */         } 
/* 122 */         if (unknown == null) {
/* 123 */           unknown = new HashMap<>();
/*     */         }
/* 125 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 129 */       reader.endObject();
/*     */       
/* 131 */       if (name == null) {
/* 132 */         String message = "Missing required field \"name\"";
/* 133 */         Exception exception = new IllegalStateException(message);
/* 134 */         logger.log(SentryLevel.ERROR, message, exception);
/* 135 */         throw exception;
/*     */       } 
/* 137 */       if (version == null) {
/* 138 */         String message = "Missing required field \"version\"";
/* 139 */         Exception exception = new IllegalStateException(message);
/* 140 */         logger.log(SentryLevel.ERROR, message, exception);
/* 141 */         throw exception;
/*     */       } 
/*     */       
/* 144 */       SentryPackage sentryPackage = new SentryPackage(name, version);
/* 145 */       sentryPackage.setUnknown(unknown);
/* 146 */       return sentryPackage;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\SentryPackage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */