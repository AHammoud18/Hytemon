/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DebugMeta
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private SdkInfo sdkInfo;
/*     */   @Nullable
/*     */   private List<DebugImage> images;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   @Nullable
/*     */   public List<DebugImage> getImages() {
/*  42 */     return this.images;
/*     */   }
/*     */   
/*     */   public void setImages(@Nullable List<DebugImage> images) {
/*  46 */     this.images = (images != null) ? new ArrayList<>(images) : null;
/*     */   }
/*     */   @Nullable
/*     */   public SdkInfo getSdkInfo() {
/*  50 */     return this.sdkInfo;
/*     */   }
/*     */   
/*     */   public void setSdkInfo(@Nullable SdkInfo sdkInfo) {
/*  54 */     this.sdkInfo = sdkInfo;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public static DebugMeta buildDebugMeta(@Nullable DebugMeta eventDebugMeta, @NotNull SentryOptions options) {
/*  60 */     List<DebugImage> debugImages = new ArrayList<>();
/*     */     
/*  62 */     if (options.getProguardUuid() != null) {
/*  63 */       DebugImage proguardMappingImage = new DebugImage();
/*  64 */       proguardMappingImage.setType("proguard");
/*  65 */       proguardMappingImage.setUuid(options.getProguardUuid());
/*  66 */       debugImages.add(proguardMappingImage);
/*     */     } 
/*     */     
/*  69 */     for (String bundleId : options.getBundleIds()) {
/*  70 */       DebugImage sourceBundleImage = new DebugImage();
/*  71 */       sourceBundleImage.setType("jvm");
/*  72 */       sourceBundleImage.setDebugId(bundleId);
/*  73 */       debugImages.add(sourceBundleImage);
/*     */     } 
/*     */     
/*  76 */     if (!debugImages.isEmpty()) {
/*  77 */       DebugMeta debugMeta = eventDebugMeta;
/*     */       
/*  79 */       if (debugMeta == null) {
/*  80 */         debugMeta = new DebugMeta();
/*     */       }
/*  82 */       if (debugMeta.getImages() == null) {
/*  83 */         debugMeta.setImages(debugImages);
/*     */       } else {
/*  85 */         debugMeta.getImages().addAll(debugImages);
/*     */       } 
/*     */       
/*  88 */       return debugMeta;
/*     */     } 
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String SDK_INFO = "sdk_info";
/*     */     
/*     */     public static final String IMAGES = "images";
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 104 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 109 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 117 */     writer.beginObject();
/* 118 */     if (this.sdkInfo != null) {
/* 119 */       writer.name("sdk_info").value(logger, this.sdkInfo);
/*     */     }
/* 121 */     if (this.images != null) {
/* 122 */       writer.name("images").value(logger, this.images);
/*     */     }
/* 124 */     if (this.unknown != null) {
/* 125 */       for (String key : this.unknown.keySet()) {
/* 126 */         Object value = this.unknown.get(key);
/* 127 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 130 */     writer.endObject();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<DebugMeta>
/*     */   {
/*     */     @NotNull
/*     */     public DebugMeta deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 140 */       DebugMeta debugMeta = new DebugMeta();
/* 141 */       Map<String, Object> unknown = null;
/*     */       
/* 143 */       reader.beginObject();
/* 144 */       while (reader.peek() == JsonToken.NAME) {
/* 145 */         String nextName = reader.nextName();
/* 146 */         switch (nextName) {
/*     */           case "sdk_info":
/* 148 */             debugMeta.sdkInfo = (SdkInfo)reader.nextOrNull(logger, new SdkInfo.Deserializer());
/*     */             continue;
/*     */           case "images":
/* 151 */             debugMeta.images = reader.nextListOrNull(logger, new DebugImage.Deserializer());
/*     */             continue;
/*     */         } 
/* 154 */         if (unknown == null) {
/* 155 */           unknown = new HashMap<>();
/*     */         }
/* 157 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 161 */       reader.endObject();
/*     */       
/* 163 */       debugMeta.setUnknown(unknown);
/* 164 */       return debugMeta;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\DebugMeta.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */