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
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SdkInfo
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private String sdkName;
/*     */   @Nullable
/*     */   private Integer versionMajor;
/*     */   @Nullable
/*     */   private Integer versionMinor;
/*     */   @Nullable
/*     */   private Integer versionPatchlevel;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   @Nullable
/*     */   public String getSdkName() {
/*  39 */     return this.sdkName;
/*     */   }
/*     */   
/*     */   public void setSdkName(@Nullable String sdkName) {
/*  43 */     this.sdkName = sdkName;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getVersionMajor() {
/*  47 */     return this.versionMajor;
/*     */   }
/*     */   
/*     */   public void setVersionMajor(@Nullable Integer versionMajor) {
/*  51 */     this.versionMajor = versionMajor;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getVersionMinor() {
/*  55 */     return this.versionMinor;
/*     */   }
/*     */   
/*     */   public void setVersionMinor(@Nullable Integer versionMinor) {
/*  59 */     this.versionMinor = versionMinor;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getVersionPatchlevel() {
/*  63 */     return this.versionPatchlevel;
/*     */   }
/*     */   
/*     */   public void setVersionPatchlevel(@Nullable Integer versionPatchlevel) {
/*  67 */     this.versionPatchlevel = versionPatchlevel;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String SDK_NAME = "sdk_name";
/*     */     
/*     */     public static final String VERSION_MAJOR = "version_major";
/*     */     
/*     */     public static final String VERSION_MINOR = "version_minor";
/*     */     public static final String VERSION_PATCHLEVEL = "version_patchlevel";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  83 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  88 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  96 */     writer.beginObject();
/*  97 */     if (this.sdkName != null) {
/*  98 */       writer.name("sdk_name").value(this.sdkName);
/*     */     }
/* 100 */     if (this.versionMajor != null) {
/* 101 */       writer.name("version_major").value(this.versionMajor);
/*     */     }
/* 103 */     if (this.versionMinor != null) {
/* 104 */       writer.name("version_minor").value(this.versionMinor);
/*     */     }
/* 106 */     if (this.versionPatchlevel != null) {
/* 107 */       writer.name("version_patchlevel").value(this.versionPatchlevel);
/*     */     }
/* 109 */     if (this.unknown != null) {
/* 110 */       for (String key : this.unknown.keySet()) {
/* 111 */         Object value = this.unknown.get(key);
/* 112 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 115 */     writer.endObject();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SdkInfo>
/*     */   {
/*     */     @NotNull
/*     */     public SdkInfo deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 125 */       SdkInfo sdkInfo = new SdkInfo();
/* 126 */       Map<String, Object> unknown = null;
/*     */       
/* 128 */       reader.beginObject();
/* 129 */       while (reader.peek() == JsonToken.NAME) {
/* 130 */         String nextName = reader.nextName();
/* 131 */         switch (nextName) {
/*     */           case "sdk_name":
/* 133 */             sdkInfo.sdkName = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "version_major":
/* 136 */             sdkInfo.versionMajor = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "version_minor":
/* 139 */             sdkInfo.versionMinor = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "version_patchlevel":
/* 142 */             sdkInfo.versionPatchlevel = reader.nextIntegerOrNull();
/*     */             continue;
/*     */         } 
/* 145 */         if (unknown == null) {
/* 146 */           unknown = new HashMap<>();
/*     */         }
/* 148 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 152 */       reader.endObject();
/*     */       
/* 154 */       sdkInfo.setUnknown(unknown);
/* 155 */       return sdkInfo;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\SdkInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */