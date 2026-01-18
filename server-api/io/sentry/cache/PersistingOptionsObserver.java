/*     */ package io.sentry.cache;
/*     */ 
/*     */ import io.sentry.IOptionsObserver;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.SentryOptions;
/*     */ import io.sentry.protocol.SdkVersion;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class PersistingOptionsObserver implements IOptionsObserver {
/*     */   public static final String OPTIONS_CACHE = ".options-cache";
/*     */   public static final String RELEASE_FILENAME = "release.json";
/*     */   public static final String PROGUARD_UUID_FILENAME = "proguard-uuid.json";
/*     */   public static final String SDK_VERSION_FILENAME = "sdk-version.json";
/*     */   public static final String ENVIRONMENT_FILENAME = "environment.json";
/*     */   public static final String DIST_FILENAME = "dist.json";
/*     */   public static final String TAGS_FILENAME = "tags.json";
/*     */   public static final String REPLAY_ERROR_SAMPLE_RATE_FILENAME = "replay-error-sample-rate.json";
/*     */   @NotNull
/*     */   private final SentryOptions options;
/*     */   
/*     */   public PersistingOptionsObserver(@NotNull SentryOptions options) {
/*  24 */     this.options = options;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRelease(@Nullable String release) {
/*  29 */     if (release == null) {
/*  30 */       delete("release.json");
/*     */     } else {
/*  32 */       store(release, "release.json");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProguardUuid(@Nullable String proguardUuid) {
/*  38 */     if (proguardUuid == null) {
/*  39 */       delete("proguard-uuid.json");
/*     */     } else {
/*  41 */       store(proguardUuid, "proguard-uuid.json");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSdkVersion(@Nullable SdkVersion sdkVersion) {
/*  47 */     if (sdkVersion == null) {
/*  48 */       delete("sdk-version.json");
/*     */     } else {
/*  50 */       store(sdkVersion, "sdk-version.json");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDist(@Nullable String dist) {
/*  56 */     if (dist == null) {
/*  57 */       delete("dist.json");
/*     */     } else {
/*  59 */       store(dist, "dist.json");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnvironment(@Nullable String environment) {
/*  65 */     if (environment == null) {
/*  66 */       delete("environment.json");
/*     */     } else {
/*  68 */       store(environment, "environment.json");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTags(@NotNull Map<String, String> tags) {
/*  74 */     store(tags, "tags.json");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReplayErrorSampleRate(@Nullable Double replayErrorSampleRate) {
/*  79 */     if (replayErrorSampleRate == null) {
/*  80 */       delete("replay-error-sample-rate.json");
/*     */     } else {
/*  82 */       store(replayErrorSampleRate.toString(), "replay-error-sample-rate.json");
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T> void store(@NotNull T entity, @NotNull String fileName) {
/*  87 */     CacheUtils.store(this.options, entity, ".options-cache", fileName);
/*     */   }
/*     */   
/*     */   private void delete(@NotNull String fileName) {
/*  91 */     CacheUtils.delete(this.options, ".options-cache", fileName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <T> T read(@NotNull SentryOptions options, @NotNull String fileName, @NotNull Class<T> clazz) {
/*  98 */     return read(options, fileName, clazz, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <T, R> T read(@NotNull SentryOptions options, @NotNull String fileName, @NotNull Class<T> clazz, @Nullable JsonDeserializer<R> elementDeserializer) {
/* 106 */     return CacheUtils.read(options, ".options-cache", fileName, clazz, elementDeserializer);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\cache\PersistingOptionsObserver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */