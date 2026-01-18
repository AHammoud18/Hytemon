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
/*     */ public final class DebugImage
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String PROGUARD = "proguard";
/*     */   public static final String JVM = "jvm";
/*     */   @Nullable
/*     */   private String uuid;
/*     */   @Nullable
/*     */   private String type;
/*     */   @Nullable
/*     */   private String debugId;
/*     */   @Nullable
/*     */   private String debugFile;
/*     */   @Nullable
/*     */   private String codeId;
/*     */   @Nullable
/*     */   private String codeFile;
/*     */   @Nullable
/*     */   private String imageAddr;
/*     */   @Nullable
/*     */   private Long imageSize;
/*     */   @Nullable
/*     */   private String arch;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   @Nullable
/*     */   public String getUuid() {
/* 171 */     return this.uuid;
/*     */   }
/*     */   
/*     */   public void setUuid(@Nullable String uuid) {
/* 175 */     this.uuid = uuid;
/*     */   }
/*     */   @Nullable
/*     */   public String getType() {
/* 179 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(@Nullable String type) {
/* 183 */     this.type = type;
/*     */   }
/*     */   @Nullable
/*     */   public String getDebugId() {
/* 187 */     return this.debugId;
/*     */   }
/*     */   
/*     */   public void setDebugId(@Nullable String debugId) {
/* 191 */     this.debugId = debugId;
/*     */   }
/*     */   @Nullable
/*     */   public String getDebugFile() {
/* 195 */     return this.debugFile;
/*     */   }
/*     */   
/*     */   public void setDebugFile(@Nullable String debugFile) {
/* 199 */     this.debugFile = debugFile;
/*     */   }
/*     */   @Nullable
/*     */   public String getCodeFile() {
/* 203 */     return this.codeFile;
/*     */   }
/*     */   
/*     */   public void setCodeFile(@Nullable String codeFile) {
/* 207 */     this.codeFile = codeFile;
/*     */   }
/*     */   @Nullable
/*     */   public String getImageAddr() {
/* 211 */     return this.imageAddr;
/*     */   }
/*     */   
/*     */   public void setImageAddr(@Nullable String imageAddr) {
/* 215 */     this.imageAddr = imageAddr;
/*     */   }
/*     */   @Nullable
/*     */   public Long getImageSize() {
/* 219 */     return this.imageSize;
/*     */   }
/*     */   
/*     */   public void setImageSize(@Nullable Long imageSize) {
/* 223 */     this.imageSize = imageSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImageSize(long imageSize) {
/* 232 */     this.imageSize = Long.valueOf(imageSize);
/*     */   }
/*     */   @Nullable
/*     */   public String getArch() {
/* 236 */     return this.arch;
/*     */   }
/*     */   
/*     */   public void setArch(@Nullable String arch) {
/* 240 */     this.arch = arch;
/*     */   }
/*     */   @Nullable
/*     */   public String getCodeId() {
/* 244 */     return this.codeId;
/*     */   }
/*     */   
/*     */   public void setCodeId(@Nullable String codeId) {
/* 248 */     this.codeId = codeId;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String UUID = "uuid";
/*     */     
/*     */     public static final String TYPE = "type";
/*     */     
/*     */     public static final String DEBUG_ID = "debug_id";
/*     */     public static final String DEBUG_FILE = "debug_file";
/*     */     public static final String CODE_ID = "code_id";
/*     */     public static final String CODE_FILE = "code_file";
/*     */     public static final String IMAGE_ADDR = "image_addr";
/*     */     public static final String IMAGE_SIZE = "image_size";
/*     */     public static final String ARCH = "arch";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 269 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 274 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 282 */     writer.beginObject();
/* 283 */     if (this.uuid != null) {
/* 284 */       writer.name("uuid").value(this.uuid);
/*     */     }
/* 286 */     if (this.type != null) {
/* 287 */       writer.name("type").value(this.type);
/*     */     }
/* 289 */     if (this.debugId != null) {
/* 290 */       writer.name("debug_id").value(this.debugId);
/*     */     }
/* 292 */     if (this.debugFile != null) {
/* 293 */       writer.name("debug_file").value(this.debugFile);
/*     */     }
/* 295 */     if (this.codeId != null) {
/* 296 */       writer.name("code_id").value(this.codeId);
/*     */     }
/* 298 */     if (this.codeFile != null) {
/* 299 */       writer.name("code_file").value(this.codeFile);
/*     */     }
/* 301 */     if (this.imageAddr != null) {
/* 302 */       writer.name("image_addr").value(this.imageAddr);
/*     */     }
/* 304 */     if (this.imageSize != null) {
/* 305 */       writer.name("image_size").value(this.imageSize);
/*     */     }
/* 307 */     if (this.arch != null) {
/* 308 */       writer.name("arch").value(this.arch);
/*     */     }
/* 310 */     if (this.unknown != null) {
/* 311 */       for (String key : this.unknown.keySet()) {
/* 312 */         Object value = this.unknown.get(key);
/* 313 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 316 */     writer.endObject();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<DebugImage>
/*     */   {
/*     */     @NotNull
/*     */     public DebugImage deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 326 */       DebugImage debugImage = new DebugImage();
/* 327 */       Map<String, Object> unknown = null;
/*     */       
/* 329 */       reader.beginObject();
/* 330 */       while (reader.peek() == JsonToken.NAME) {
/* 331 */         String nextName = reader.nextName();
/* 332 */         switch (nextName) {
/*     */           case "uuid":
/* 334 */             debugImage.uuid = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "type":
/* 337 */             debugImage.type = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "debug_id":
/* 340 */             debugImage.debugId = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "debug_file":
/* 343 */             debugImage.debugFile = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "code_id":
/* 346 */             debugImage.codeId = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "code_file":
/* 349 */             debugImage.codeFile = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "image_addr":
/* 352 */             debugImage.imageAddr = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "image_size":
/* 355 */             debugImage.imageSize = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "arch":
/* 358 */             debugImage.arch = reader.nextStringOrNull();
/*     */             continue;
/*     */         } 
/* 361 */         if (unknown == null) {
/* 362 */           unknown = new HashMap<>();
/*     */         }
/* 364 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 368 */       reader.endObject();
/*     */       
/* 370 */       debugImage.setUnknown(unknown);
/* 371 */       return debugImage;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\DebugImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */