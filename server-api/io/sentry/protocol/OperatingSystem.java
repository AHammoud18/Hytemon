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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OperatingSystem
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String TYPE = "os";
/*     */   @Nullable
/*     */   private String name;
/*     */   @Nullable
/*     */   private String version;
/*     */   @Nullable
/*     */   private String rawDescription;
/*     */   @Nullable
/*     */   private String build;
/*     */   @Nullable
/*     */   private String kernelVersion;
/*     */   @Nullable
/*     */   private Boolean rooted;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public OperatingSystem() {}
/*     */   
/*     */   OperatingSystem(@NotNull OperatingSystem operatingSystem) {
/*  55 */     this.name = operatingSystem.name;
/*  56 */     this.version = operatingSystem.version;
/*  57 */     this.rawDescription = operatingSystem.rawDescription;
/*  58 */     this.build = operatingSystem.build;
/*  59 */     this.kernelVersion = operatingSystem.kernelVersion;
/*  60 */     this.rooted = operatingSystem.rooted;
/*  61 */     this.unknown = CollectionUtils.newConcurrentHashMap(operatingSystem.unknown);
/*     */   }
/*     */   @Nullable
/*     */   public String getName() {
/*  65 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(@Nullable String name) {
/*  69 */     this.name = name;
/*     */   }
/*     */   @Nullable
/*     */   public String getVersion() {
/*  73 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setVersion(@Nullable String version) {
/*  77 */     this.version = version;
/*     */   }
/*     */   @Nullable
/*     */   public String getRawDescription() {
/*  81 */     return this.rawDescription;
/*     */   }
/*     */   
/*     */   public void setRawDescription(@Nullable String rawDescription) {
/*  85 */     this.rawDescription = rawDescription;
/*     */   }
/*     */   @Nullable
/*     */   public String getBuild() {
/*  89 */     return this.build;
/*     */   }
/*     */   
/*     */   public void setBuild(@Nullable String build) {
/*  93 */     this.build = build;
/*     */   }
/*     */   @Nullable
/*     */   public String getKernelVersion() {
/*  97 */     return this.kernelVersion;
/*     */   }
/*     */   
/*     */   public void setKernelVersion(@Nullable String kernelVersion) {
/* 101 */     this.kernelVersion = kernelVersion;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isRooted() {
/* 105 */     return this.rooted;
/*     */   }
/*     */   
/*     */   public void setRooted(@Nullable Boolean rooted) {
/* 109 */     this.rooted = rooted;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 114 */     if (this == o) return true; 
/* 115 */     if (o == null || getClass() != o.getClass()) return false; 
/* 116 */     OperatingSystem that = (OperatingSystem)o;
/* 117 */     return (Objects.equals(this.name, that.name) && 
/* 118 */       Objects.equals(this.version, that.version) && 
/* 119 */       Objects.equals(this.rawDescription, that.rawDescription) && 
/* 120 */       Objects.equals(this.build, that.build) && 
/* 121 */       Objects.equals(this.kernelVersion, that.kernelVersion) && 
/* 122 */       Objects.equals(this.rooted, that.rooted));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 127 */     return Objects.hash(new Object[] { this.name, this.version, this.rawDescription, this.build, this.kernelVersion, this.rooted });
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
/*     */     public static final String BUILD = "build";
/*     */     public static final String KERNEL_VERSION = "kernel_version";
/*     */     public static final String ROOTED = "rooted";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 144 */     writer.beginObject();
/* 145 */     if (this.name != null) {
/* 146 */       writer.name("name").value(this.name);
/*     */     }
/* 148 */     if (this.version != null) {
/* 149 */       writer.name("version").value(this.version);
/*     */     }
/* 151 */     if (this.rawDescription != null) {
/* 152 */       writer.name("raw_description").value(this.rawDescription);
/*     */     }
/* 154 */     if (this.build != null) {
/* 155 */       writer.name("build").value(this.build);
/*     */     }
/* 157 */     if (this.kernelVersion != null) {
/* 158 */       writer.name("kernel_version").value(this.kernelVersion);
/*     */     }
/* 160 */     if (this.rooted != null) {
/* 161 */       writer.name("rooted").value(this.rooted);
/*     */     }
/* 163 */     if (this.unknown != null) {
/* 164 */       for (String key : this.unknown.keySet()) {
/* 165 */         Object value = this.unknown.get(key);
/* 166 */         writer.name(key);
/* 167 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 170 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 176 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 181 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<OperatingSystem>
/*     */   {
/*     */     @NotNull
/*     */     public OperatingSystem deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 189 */       reader.beginObject();
/* 190 */       OperatingSystem operatingSystem = new OperatingSystem();
/* 191 */       Map<String, Object> unknown = null;
/* 192 */       while (reader.peek() == JsonToken.NAME) {
/* 193 */         String nextName = reader.nextName();
/* 194 */         switch (nextName) {
/*     */           case "name":
/* 196 */             operatingSystem.name = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "version":
/* 199 */             operatingSystem.version = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "raw_description":
/* 202 */             operatingSystem.rawDescription = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "build":
/* 205 */             operatingSystem.build = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "kernel_version":
/* 208 */             operatingSystem.kernelVersion = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "rooted":
/* 211 */             operatingSystem.rooted = reader.nextBooleanOrNull();
/*     */             continue;
/*     */         } 
/* 214 */         if (unknown == null) {
/* 215 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 217 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 221 */       operatingSystem.setUnknown(unknown);
/* 222 */       reader.endObject();
/* 223 */       return operatingSystem;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\OperatingSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */