/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryIntegrationPackageStorage;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
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
/*     */ public final class SdkVersion
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @NotNull
/*     */   private String name;
/*     */   @NotNull
/*     */   private String version;
/*     */   @Nullable
/*     */   private Set<SentryPackage> deserializedPackages;
/*     */   @Nullable
/*     */   private Set<String> deserializedIntegrations;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public SdkVersion(@NotNull String name, @NotNull String version) {
/*  74 */     this.name = (String)Objects.requireNonNull(name, "name is required.");
/*  75 */     this.version = (String)Objects.requireNonNull(version, "version is required.");
/*     */   }
/*     */   @NotNull
/*     */   public String getVersion() {
/*  79 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setVersion(@NotNull String version) {
/*  83 */     this.version = (String)Objects.requireNonNull(version, "version is required.");
/*     */   }
/*     */   @NotNull
/*     */   public String getName() {
/*  87 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(@NotNull String name) {
/*  91 */     this.name = (String)Objects.requireNonNull(name, "name is required.");
/*     */   }
/*     */   
/*     */   public void addPackage(@NotNull String name, @NotNull String version) {
/*  95 */     SentryIntegrationPackageStorage.getInstance().addPackage(name, version);
/*     */   }
/*     */   
/*     */   public void addIntegration(@NotNull String integration) {
/*  99 */     SentryIntegrationPackageStorage.getInstance().addIntegration(integration);
/*     */   }
/*     */   @NotNull
/*     */   public Set<SentryPackage> getPackageSet() {
/* 103 */     return (this.deserializedPackages != null) ? 
/* 104 */       this.deserializedPackages : 
/* 105 */       SentryIntegrationPackageStorage.getInstance().getPackages();
/*     */   }
/*     */   @NotNull
/*     */   public Set<String> getIntegrationSet() {
/* 109 */     return (this.deserializedIntegrations != null) ? 
/* 110 */       this.deserializedIntegrations : 
/* 111 */       SentryIntegrationPackageStorage.getInstance().getIntegrations();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static SdkVersion updateSdkVersion(@Nullable SdkVersion sdk, @NotNull String name, @NotNull String version) {
/* 124 */     Objects.requireNonNull(name, "name is required.");
/* 125 */     Objects.requireNonNull(version, "version is required.");
/*     */     
/* 127 */     if (sdk == null) {
/* 128 */       sdk = new SdkVersion(name, version);
/*     */     } else {
/* 130 */       sdk.setName(name);
/* 131 */       sdk.setVersion(version);
/*     */     } 
/* 133 */     return sdk;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 138 */     if (this == o) return true; 
/* 139 */     if (o == null || getClass() != o.getClass()) return false; 
/* 140 */     SdkVersion that = (SdkVersion)o;
/* 141 */     return (this.name.equals(that.name) && this.version.equals(that.version));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 146 */     return Objects.hash(new Object[] { this.name, this.version });
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String NAME = "name";
/*     */     
/*     */     public static final String VERSION = "version";
/*     */     
/*     */     public static final String PACKAGES = "packages";
/*     */     public static final String INTEGRATIONS = "integrations";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 162 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 167 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 175 */     writer.beginObject();
/* 176 */     writer.name("name").value(this.name);
/* 177 */     writer.name("version").value(this.version);
/* 178 */     Set<SentryPackage> packages = getPackageSet();
/* 179 */     Set<String> integrations = getIntegrationSet();
/* 180 */     if (!packages.isEmpty()) {
/* 181 */       writer.name("packages").value(logger, packages);
/*     */     }
/* 183 */     if (!integrations.isEmpty()) {
/* 184 */       writer.name("integrations").value(logger, integrations);
/*     */     }
/* 186 */     if (this.unknown != null) {
/* 187 */       for (String key : this.unknown.keySet()) {
/* 188 */         Object value = this.unknown.get(key);
/* 189 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 192 */     writer.endObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SdkVersion>
/*     */   {
/*     */     @NotNull
/*     */     public SdkVersion deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 203 */       String name = null;
/* 204 */       String version = null;
/* 205 */       List<SentryPackage> packages = new ArrayList<>();
/* 206 */       List<String> integrations = new ArrayList<>();
/* 207 */       Map<String, Object> unknown = null;
/*     */       
/* 209 */       reader.beginObject();
/* 210 */       while (reader.peek() == JsonToken.NAME) {
/* 211 */         List<SentryPackage> deserializedPackages; List<String> deserializedIntegrations; String nextName = reader.nextName();
/* 212 */         switch (nextName) {
/*     */           case "name":
/* 214 */             name = reader.nextString();
/*     */             continue;
/*     */           case "version":
/* 217 */             version = reader.nextString();
/*     */             continue;
/*     */           
/*     */           case "packages":
/* 221 */             deserializedPackages = reader.nextListOrNull(logger, new SentryPackage.Deserializer());
/* 222 */             if (deserializedPackages != null) {
/* 223 */               packages.addAll(deserializedPackages);
/*     */             }
/*     */             continue;
/*     */           case "integrations":
/* 227 */             deserializedIntegrations = (List<String>)reader.nextObjectOrNull();
/* 228 */             if (deserializedIntegrations != null) {
/* 229 */               integrations.addAll(deserializedIntegrations);
/*     */             }
/*     */             continue;
/*     */         } 
/* 233 */         if (unknown == null) {
/* 234 */           unknown = new HashMap<>();
/*     */         }
/* 236 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 240 */       reader.endObject();
/*     */       
/* 242 */       if (name == null) {
/* 243 */         String message = "Missing required field \"name\"";
/* 244 */         Exception exception = new IllegalStateException(message);
/* 245 */         logger.log(SentryLevel.ERROR, message, exception);
/* 246 */         throw exception;
/*     */       } 
/* 248 */       if (version == null) {
/* 249 */         String message = "Missing required field \"version\"";
/* 250 */         Exception exception = new IllegalStateException(message);
/* 251 */         logger.log(SentryLevel.ERROR, message, exception);
/* 252 */         throw exception;
/*     */       } 
/*     */       
/* 255 */       SdkVersion sdkVersion = new SdkVersion(name, version);
/* 256 */       sdkVersion.deserializedPackages = new CopyOnWriteArraySet<>(packages);
/* 257 */       sdkVersion.deserializedIntegrations = new CopyOnWriteArraySet<>(integrations);
/*     */       
/* 259 */       sdkVersion.setUnknown(unknown);
/* 260 */       return sdkVersion;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\SdkVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */