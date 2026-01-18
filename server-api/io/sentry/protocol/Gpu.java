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
/*     */ public final class Gpu
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String TYPE = "gpu";
/*     */   @Nullable
/*     */   private String name;
/*     */   @Nullable
/*     */   private Integer id;
/*     */   @Nullable
/*     */   private String vendorId;
/*     */   @Nullable
/*     */   private String vendorName;
/*     */   @Nullable
/*     */   private Integer memorySize;
/*     */   @Nullable
/*     */   private String apiType;
/*     */   @Nullable
/*     */   private Boolean multiThreadedRendering;
/*     */   @Nullable
/*     */   private String version;
/*     */   @Nullable
/*     */   private String npotSupport;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public Gpu() {}
/*     */   
/*     */   Gpu(@NotNull Gpu gpu) {
/*  58 */     this.name = gpu.name;
/*  59 */     this.id = gpu.id;
/*  60 */     this.vendorId = gpu.vendorId;
/*  61 */     this.vendorName = gpu.vendorName;
/*  62 */     this.memorySize = gpu.memorySize;
/*  63 */     this.apiType = gpu.apiType;
/*  64 */     this.multiThreadedRendering = gpu.multiThreadedRendering;
/*  65 */     this.version = gpu.version;
/*  66 */     this.npotSupport = gpu.npotSupport;
/*  67 */     this.unknown = CollectionUtils.newConcurrentHashMap(gpu.unknown);
/*     */   }
/*     */   @Nullable
/*     */   public String getName() {
/*  71 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  75 */     this.name = name;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getId() {
/*  79 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/*  83 */     this.id = id;
/*     */   }
/*     */   @Nullable
/*     */   public String getVendorId() {
/*  87 */     return this.vendorId;
/*     */   }
/*     */   
/*     */   public void setVendorId(@Nullable String vendorId) {
/*  91 */     this.vendorId = vendorId;
/*     */   }
/*     */   @Nullable
/*     */   public String getVendorName() {
/*  95 */     return this.vendorName;
/*     */   }
/*     */   
/*     */   public void setVendorName(@Nullable String vendorName) {
/*  99 */     this.vendorName = vendorName;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getMemorySize() {
/* 103 */     return this.memorySize;
/*     */   }
/*     */   
/*     */   public void setMemorySize(@Nullable Integer memorySize) {
/* 107 */     this.memorySize = memorySize;
/*     */   }
/*     */   @Nullable
/*     */   public String getApiType() {
/* 111 */     return this.apiType;
/*     */   }
/*     */   
/*     */   public void setApiType(@Nullable String apiType) {
/* 115 */     this.apiType = apiType;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isMultiThreadedRendering() {
/* 119 */     return this.multiThreadedRendering;
/*     */   }
/*     */   
/*     */   public void setMultiThreadedRendering(@Nullable Boolean multiThreadedRendering) {
/* 123 */     this.multiThreadedRendering = multiThreadedRendering;
/*     */   }
/*     */   @Nullable
/*     */   public String getVersion() {
/* 127 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setVersion(@Nullable String version) {
/* 131 */     this.version = version;
/*     */   }
/*     */   @Nullable
/*     */   public String getNpotSupport() {
/* 135 */     return this.npotSupport;
/*     */   }
/*     */   
/*     */   public void setNpotSupport(@Nullable String npotSupport) {
/* 139 */     this.npotSupport = npotSupport;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 144 */     if (this == o) return true; 
/* 145 */     if (o == null || getClass() != o.getClass()) return false; 
/* 146 */     Gpu gpu = (Gpu)o;
/* 147 */     return (Objects.equals(this.name, gpu.name) && 
/* 148 */       Objects.equals(this.id, gpu.id) && 
/* 149 */       Objects.equals(this.vendorId, gpu.vendorId) && 
/* 150 */       Objects.equals(this.vendorName, gpu.vendorName) && 
/* 151 */       Objects.equals(this.memorySize, gpu.memorySize) && 
/* 152 */       Objects.equals(this.apiType, gpu.apiType) && 
/* 153 */       Objects.equals(this.multiThreadedRendering, gpu.multiThreadedRendering) && 
/* 154 */       Objects.equals(this.version, gpu.version) && 
/* 155 */       Objects.equals(this.npotSupport, gpu.npotSupport));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 160 */     return Objects.hash(new Object[] { this.name, this.id, this.vendorId, this.vendorName, this.memorySize, this.apiType, this.multiThreadedRendering, this.version, this.npotSupport });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String NAME = "name";
/*     */ 
/*     */     
/*     */     public static final String ID = "id";
/*     */     
/*     */     public static final String VENDOR_ID = "vendor_id";
/*     */     
/*     */     public static final String VENDOR_NAME = "vendor_name";
/*     */     
/*     */     public static final String MEMORY_SIZE = "memory_size";
/*     */     
/*     */     public static final String API_TYPE = "api_type";
/*     */     
/*     */     public static final String MULTI_THREADED_RENDERING = "multi_threaded_rendering";
/*     */     
/*     */     public static final String VERSION = "version";
/*     */     
/*     */     public static final String NPOT_SUPPORT = "npot_support";
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 189 */     writer.beginObject();
/* 190 */     if (this.name != null) {
/* 191 */       writer.name("name").value(this.name);
/*     */     }
/* 193 */     if (this.id != null) {
/* 194 */       writer.name("id").value(this.id);
/*     */     }
/* 196 */     if (this.vendorId != null) {
/* 197 */       writer.name("vendor_id").value(this.vendorId);
/*     */     }
/* 199 */     if (this.vendorName != null) {
/* 200 */       writer.name("vendor_name").value(this.vendorName);
/*     */     }
/* 202 */     if (this.memorySize != null) {
/* 203 */       writer.name("memory_size").value(this.memorySize);
/*     */     }
/* 205 */     if (this.apiType != null) {
/* 206 */       writer.name("api_type").value(this.apiType);
/*     */     }
/* 208 */     if (this.multiThreadedRendering != null) {
/* 209 */       writer.name("multi_threaded_rendering").value(this.multiThreadedRendering);
/*     */     }
/* 211 */     if (this.version != null) {
/* 212 */       writer.name("version").value(this.version);
/*     */     }
/* 214 */     if (this.npotSupport != null) {
/* 215 */       writer.name("npot_support").value(this.npotSupport);
/*     */     }
/* 217 */     if (this.unknown != null) {
/* 218 */       for (String key : this.unknown.keySet()) {
/* 219 */         Object value = this.unknown.get(key);
/* 220 */         writer.name(key);
/* 221 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 224 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 230 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 235 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<Gpu> {
/*     */     @NotNull
/*     */     public Gpu deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 242 */       reader.beginObject();
/* 243 */       Gpu gpu = new Gpu();
/* 244 */       Map<String, Object> unknown = null;
/* 245 */       while (reader.peek() == JsonToken.NAME) {
/* 246 */         String nextName = reader.nextName();
/* 247 */         switch (nextName) {
/*     */           case "name":
/* 249 */             gpu.name = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "id":
/* 252 */             gpu.id = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "vendor_id":
/* 255 */             gpu.vendorId = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "vendor_name":
/* 258 */             gpu.vendorName = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "memory_size":
/* 261 */             gpu.memorySize = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "api_type":
/* 264 */             gpu.apiType = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "multi_threaded_rendering":
/* 267 */             gpu.multiThreadedRendering = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "version":
/* 270 */             gpu.version = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "npot_support":
/* 273 */             gpu.npotSupport = reader.nextStringOrNull();
/*     */             continue;
/*     */         } 
/* 276 */         if (unknown == null) {
/* 277 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 279 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 283 */       gpu.setUnknown(unknown);
/* 284 */       reader.endObject();
/* 285 */       return gpu;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\Gpu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */