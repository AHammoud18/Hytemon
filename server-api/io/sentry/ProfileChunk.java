/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.profilemeasurements.ProfileMeasurement;
/*     */ import io.sentry.protocol.DebugMeta;
/*     */ import io.sentry.protocol.SdkVersion;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.profiling.SentryProfile;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class ProfileChunk
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String PLATFORM_ANDROID = "android";
/*     */   public static final String PLATFORM_JAVA = "java";
/*     */   @Nullable
/*     */   private DebugMeta debugMeta;
/*     */   @NotNull
/*     */   private SentryId profilerId;
/*     */   @NotNull
/*     */   private SentryId chunkId;
/*     */   @Nullable
/*     */   private SdkVersion clientSdk;
/*     */   @Nullable
/*  40 */   private String sampledProfile = null; @NotNull private final Map<String, ProfileMeasurement> measurements; @NotNull
/*     */   private String platform; @NotNull
/*     */   private String release; @Nullable
/*     */   private String environment; @NotNull
/*     */   private String version; private double timestamp; @NotNull
/*     */   private final File traceFile; @Nullable
/*     */   private SentryProfile sentryProfile; @Nullable
/*  47 */   private Map<String, Object> unknown; public ProfileChunk() { this(SentryId.EMPTY_ID, SentryId.EMPTY_ID, new File("dummy"), new HashMap<>(), 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  52 */         Double.valueOf(0.0D), "android", 
/*     */         
/*  54 */         SentryOptions.empty()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProfileChunk(@NotNull SentryId profilerId, @NotNull SentryId chunkId, @NotNull File traceFile, @NotNull Map<String, ProfileMeasurement> measurements, @NotNull Double timestamp, @NotNull String platform, @NotNull SentryOptions options) {
/*  65 */     this.profilerId = profilerId;
/*  66 */     this.chunkId = chunkId;
/*  67 */     this.traceFile = traceFile;
/*  68 */     this.measurements = measurements;
/*  69 */     this.debugMeta = null;
/*  70 */     this.clientSdk = options.getSdkVersion();
/*  71 */     this.release = (options.getRelease() != null) ? options.getRelease() : "";
/*  72 */     this.environment = options.getEnvironment();
/*  73 */     this.platform = platform;
/*  74 */     this.version = "2";
/*  75 */     this.timestamp = timestamp.doubleValue();
/*     */   }
/*     */   @NotNull
/*     */   public Map<String, ProfileMeasurement> getMeasurements() {
/*  79 */     return this.measurements;
/*     */   }
/*     */   @Nullable
/*     */   public DebugMeta getDebugMeta() {
/*  83 */     return this.debugMeta;
/*     */   }
/*     */   
/*     */   public void setDebugMeta(@Nullable DebugMeta debugMeta) {
/*  87 */     this.debugMeta = debugMeta;
/*     */   }
/*     */   @Nullable
/*     */   public SdkVersion getClientSdk() {
/*  91 */     return this.clientSdk;
/*     */   }
/*     */   @NotNull
/*     */   public SentryId getChunkId() {
/*  95 */     return this.chunkId;
/*     */   }
/*     */   @Nullable
/*     */   public String getEnvironment() {
/*  99 */     return this.environment;
/*     */   }
/*     */   @NotNull
/*     */   public String getPlatform() {
/* 103 */     return this.platform;
/*     */   }
/*     */   @NotNull
/*     */   public SentryId getProfilerId() {
/* 107 */     return this.profilerId;
/*     */   }
/*     */   @NotNull
/*     */   public String getRelease() {
/* 111 */     return this.release;
/*     */   }
/*     */   @Nullable
/*     */   public String getSampledProfile() {
/* 115 */     return this.sampledProfile;
/*     */   }
/*     */   
/*     */   public void setSampledProfile(@Nullable String sampledProfile) {
/* 119 */     this.sampledProfile = sampledProfile;
/*     */   }
/*     */   @NotNull
/*     */   public File getTraceFile() {
/* 123 */     return this.traceFile;
/*     */   }
/*     */   
/*     */   public double getTimestamp() {
/* 127 */     return this.timestamp;
/*     */   }
/*     */   @NotNull
/*     */   public String getVersion() {
/* 131 */     return this.version;
/*     */   }
/*     */   @Nullable
/*     */   public SentryProfile getSentryProfile() {
/* 135 */     return this.sentryProfile;
/*     */   }
/*     */   
/*     */   public void setSentryProfile(@Nullable SentryProfile sentryProfile) {
/* 139 */     this.sentryProfile = sentryProfile;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 144 */     if (this == o) return true; 
/* 145 */     if (!(o instanceof ProfileChunk)) return false; 
/* 146 */     ProfileChunk that = (ProfileChunk)o;
/* 147 */     return (Objects.equals(this.debugMeta, that.debugMeta) && 
/* 148 */       Objects.equals(this.profilerId, that.profilerId) && 
/* 149 */       Objects.equals(this.chunkId, that.chunkId) && 
/* 150 */       Objects.equals(this.clientSdk, that.clientSdk) && 
/* 151 */       Objects.equals(this.measurements, that.measurements) && 
/* 152 */       Objects.equals(this.platform, that.platform) && 
/* 153 */       Objects.equals(this.release, that.release) && 
/* 154 */       Objects.equals(this.environment, that.environment) && 
/* 155 */       Objects.equals(this.version, that.version) && 
/* 156 */       Objects.equals(this.sampledProfile, that.sampledProfile) && 
/* 157 */       Objects.equals(this.unknown, that.unknown) && 
/* 158 */       Objects.equals(this.sentryProfile, that.sentryProfile));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 163 */     return Objects.hash(new Object[] { this.debugMeta, this.profilerId, this.chunkId, this.clientSdk, this.measurements, this.platform, this.release, this.environment, this.version, this.sampledProfile, this.sentryProfile, this.unknown });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     @NotNull
/*     */     private final SentryId profilerId;
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     private final SentryId chunkId;
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     private final Map<String, ProfileMeasurement> measurements;
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     private final File traceFile;
/*     */ 
/*     */     
/*     */     private final double timestamp;
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     private final String platform;
/*     */ 
/*     */     
/*     */     public Builder(@NotNull SentryId profilerId, @NotNull SentryId chunkId, @NotNull Map<String, ProfileMeasurement> measurements, @NotNull File traceFile, @NotNull SentryDate timestamp, @NotNull String platform) {
/* 194 */       this.profilerId = profilerId;
/* 195 */       this.chunkId = chunkId;
/* 196 */       this.measurements = new ConcurrentHashMap<>(measurements);
/* 197 */       this.traceFile = traceFile;
/* 198 */       this.timestamp = DateUtils.nanosToSeconds(timestamp.nanoTimestamp());
/* 199 */       this.platform = platform;
/*     */     }
/*     */     
/*     */     public ProfileChunk build(SentryOptions options) {
/* 203 */       return new ProfileChunk(this.profilerId, this.chunkId, this.traceFile, this.measurements, 
/* 204 */           Double.valueOf(this.timestamp), this.platform, options);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String DEBUG_META = "debug_meta";
/*     */     
/*     */     public static final String PROFILER_ID = "profiler_id";
/*     */     
/*     */     public static final String CHUNK_ID = "chunk_id";
/*     */     public static final String CLIENT_SDK = "client_sdk";
/*     */     public static final String MEASUREMENTS = "measurements";
/*     */     public static final String PLATFORM = "platform";
/*     */     public static final String RELEASE = "release";
/*     */     public static final String ENVIRONMENT = "environment";
/*     */     public static final String VERSION = "version";
/*     */     public static final String SAMPLED_PROFILE = "sampled_profile";
/*     */     public static final String TIMESTAMP = "timestamp";
/*     */     public static final String SENTRY_PROFILE = "profile";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 228 */     writer.beginObject();
/* 229 */     if (this.debugMeta != null) {
/* 230 */       writer.name("debug_meta").value(logger, this.debugMeta);
/*     */     }
/* 232 */     writer.name("profiler_id").value(logger, this.profilerId);
/* 233 */     writer.name("chunk_id").value(logger, this.chunkId);
/* 234 */     if (this.clientSdk != null) {
/* 235 */       writer.name("client_sdk").value(logger, this.clientSdk);
/*     */     }
/* 237 */     if (!this.measurements.isEmpty()) {
/*     */ 
/*     */       
/* 240 */       String prevIndent = writer.getIndent();
/* 241 */       writer.setIndent("");
/* 242 */       writer.name("measurements").value(logger, this.measurements);
/* 243 */       writer.setIndent(prevIndent);
/*     */     } 
/* 245 */     writer.name("platform").value(logger, this.platform);
/* 246 */     writer.name("release").value(logger, this.release);
/* 247 */     if (this.environment != null) {
/* 248 */       writer.name("environment").value(logger, this.environment);
/*     */     }
/* 250 */     writer.name("version").value(logger, this.version);
/* 251 */     if (this.sampledProfile != null) {
/* 252 */       writer.name("sampled_profile").value(logger, this.sampledProfile);
/*     */     }
/* 254 */     writer.name("timestamp").value(logger, doubleToBigDecimal(Double.valueOf(this.timestamp)));
/* 255 */     if (this.sentryProfile != null) {
/* 256 */       writer.name("profile").value(logger, this.sentryProfile);
/*     */     }
/* 258 */     if (this.unknown != null) {
/* 259 */       for (String key : this.unknown.keySet()) {
/* 260 */         Object value = this.unknown.get(key);
/* 261 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 264 */     writer.endObject();
/*     */   }
/*     */   @NotNull
/*     */   private BigDecimal doubleToBigDecimal(@NotNull Double value) {
/* 268 */     return BigDecimal.valueOf(value.doubleValue()).setScale(6, RoundingMode.DOWN);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 274 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 279 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<ProfileChunk>
/*     */   {
/*     */     @NotNull
/*     */     public ProfileChunk deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 287 */       reader.beginObject();
/* 288 */       ProfileChunk data = new ProfileChunk();
/* 289 */       Map<String, Object> unknown = null;
/*     */       
/* 291 */       while (reader.peek() == JsonToken.NAME) {
/* 292 */         DebugMeta debugMeta; SentryId profilerId, chunkId; SdkVersion clientSdk; Map<String, ProfileMeasurement> measurements; String platform, release, environment, version, sampledProfile; Double timestamp; SentryProfile sentryProfile; String nextName = reader.nextName();
/* 293 */         switch (nextName) {
/*     */           case "debug_meta":
/* 295 */             debugMeta = reader.<DebugMeta>nextOrNull(logger, (JsonDeserializer<DebugMeta>)new DebugMeta.Deserializer());
/* 296 */             if (debugMeta != null) {
/* 297 */               data.debugMeta = debugMeta;
/*     */             }
/*     */             continue;
/*     */           case "profiler_id":
/* 301 */             profilerId = reader.<SentryId>nextOrNull(logger, (JsonDeserializer<SentryId>)new SentryId.Deserializer());
/* 302 */             if (profilerId != null) {
/* 303 */               data.profilerId = profilerId;
/*     */             }
/*     */             continue;
/*     */           case "chunk_id":
/* 307 */             chunkId = reader.<SentryId>nextOrNull(logger, (JsonDeserializer<SentryId>)new SentryId.Deserializer());
/* 308 */             if (chunkId != null) {
/* 309 */               data.chunkId = chunkId;
/*     */             }
/*     */             continue;
/*     */           case "client_sdk":
/* 313 */             clientSdk = reader.<SdkVersion>nextOrNull(logger, (JsonDeserializer<SdkVersion>)new SdkVersion.Deserializer());
/* 314 */             if (clientSdk != null) {
/* 315 */               data.clientSdk = clientSdk;
/*     */             }
/*     */             continue;
/*     */           
/*     */           case "measurements":
/* 320 */             measurements = reader.nextMapOrNull(logger, (JsonDeserializer<ProfileMeasurement>)new ProfileMeasurement.Deserializer());
/* 321 */             if (measurements != null) {
/* 322 */               data.measurements.putAll(measurements);
/*     */             }
/*     */             continue;
/*     */           case "platform":
/* 326 */             platform = reader.nextStringOrNull();
/* 327 */             if (platform != null) {
/* 328 */               data.platform = platform;
/*     */             }
/*     */             continue;
/*     */           case "release":
/* 332 */             release = reader.nextStringOrNull();
/* 333 */             if (release != null) {
/* 334 */               data.release = release;
/*     */             }
/*     */             continue;
/*     */           case "environment":
/* 338 */             environment = reader.nextStringOrNull();
/* 339 */             if (environment != null) {
/* 340 */               data.environment = environment;
/*     */             }
/*     */             continue;
/*     */           case "version":
/* 344 */             version = reader.nextStringOrNull();
/* 345 */             if (version != null) {
/* 346 */               data.version = version;
/*     */             }
/*     */             continue;
/*     */           case "sampled_profile":
/* 350 */             sampledProfile = reader.nextStringOrNull();
/* 351 */             if (sampledProfile != null) {
/* 352 */               data.sampledProfile = sampledProfile;
/*     */             }
/*     */             continue;
/*     */           case "timestamp":
/* 356 */             timestamp = reader.nextDoubleOrNull();
/* 357 */             if (timestamp != null) {
/* 358 */               data.timestamp = timestamp.doubleValue();
/*     */             }
/*     */             continue;
/*     */           
/*     */           case "profile":
/* 363 */             sentryProfile = reader.<SentryProfile>nextOrNull(logger, (JsonDeserializer<SentryProfile>)new SentryProfile.Deserializer());
/* 364 */             if (sentryProfile != null) {
/* 365 */               data.sentryProfile = sentryProfile;
/*     */             }
/*     */             continue;
/*     */         } 
/* 369 */         if (unknown == null) {
/* 370 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 372 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 376 */       data.setUnknown(unknown);
/* 377 */       reader.endObject();
/* 378 */       return data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ProfileChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */