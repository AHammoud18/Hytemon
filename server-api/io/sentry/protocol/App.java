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
/*     */ import java.util.Date;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public final class App
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String TYPE = "app";
/*     */   @Nullable
/*     */   private String appIdentifier;
/*     */   @Nullable
/*     */   private Date appStartTime;
/*     */   @Nullable
/*     */   private String deviceAppHash;
/*     */   @Nullable
/*     */   private String buildType;
/*     */   @Nullable
/*     */   private String appName;
/*     */   @Nullable
/*     */   private String appVersion;
/*     */   @Nullable
/*     */   private String appBuild;
/*     */   @Nullable
/*     */   private Map<String, String> permissions;
/*     */   @Nullable
/*     */   private List<String> viewNames;
/*     */   @Nullable
/*     */   private String startType;
/*     */   @Nullable
/*     */   private Boolean inForeground;
/*     */   @Nullable
/*     */   private Boolean isSplitApks;
/*     */   @Nullable
/*     */   private List<String> splitNames;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public App() {}
/*     */   
/*     */   App(@NotNull App app) {
/*  72 */     this.appBuild = app.appBuild;
/*  73 */     this.appIdentifier = app.appIdentifier;
/*  74 */     this.appName = app.appName;
/*  75 */     this.appStartTime = app.appStartTime;
/*  76 */     this.appVersion = app.appVersion;
/*  77 */     this.buildType = app.buildType;
/*  78 */     this.deviceAppHash = app.deviceAppHash;
/*  79 */     this.permissions = CollectionUtils.newConcurrentHashMap(app.permissions);
/*  80 */     this.inForeground = app.inForeground;
/*  81 */     this.viewNames = CollectionUtils.newArrayList(app.viewNames);
/*  82 */     this.startType = app.startType;
/*  83 */     this.isSplitApks = app.isSplitApks;
/*  84 */     this.splitNames = app.splitNames;
/*  85 */     this.unknown = CollectionUtils.newConcurrentHashMap(app.unknown);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getAppIdentifier() {
/*  92 */     return this.appIdentifier;
/*     */   }
/*     */   
/*     */   public void setAppIdentifier(@Nullable String appIdentifier) {
/*  96 */     this.appIdentifier = appIdentifier;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Date getAppStartTime() {
/* 101 */     Date appStartTimeRef = this.appStartTime;
/* 102 */     return (appStartTimeRef != null) ? (Date)appStartTimeRef.clone() : null;
/*     */   }
/*     */   
/*     */   public void setAppStartTime(@Nullable Date appStartTime) {
/* 106 */     this.appStartTime = appStartTime;
/*     */   }
/*     */   @Nullable
/*     */   public String getDeviceAppHash() {
/* 110 */     return this.deviceAppHash;
/*     */   }
/*     */   
/*     */   public void setDeviceAppHash(@Nullable String deviceAppHash) {
/* 114 */     this.deviceAppHash = deviceAppHash;
/*     */   }
/*     */   @Nullable
/*     */   public String getBuildType() {
/* 118 */     return this.buildType;
/*     */   }
/*     */   
/*     */   public void setBuildType(@Nullable String buildType) {
/* 122 */     this.buildType = buildType;
/*     */   }
/*     */   @Nullable
/*     */   public String getAppName() {
/* 126 */     return this.appName;
/*     */   }
/*     */   
/*     */   public void setAppName(@Nullable String appName) {
/* 130 */     this.appName = appName;
/*     */   }
/*     */   @Nullable
/*     */   public String getAppVersion() {
/* 134 */     return this.appVersion;
/*     */   }
/*     */   
/*     */   public void setAppVersion(@Nullable String appVersion) {
/* 138 */     this.appVersion = appVersion;
/*     */   }
/*     */   @Nullable
/*     */   public String getAppBuild() {
/* 142 */     return this.appBuild;
/*     */   }
/*     */   
/*     */   public void setAppBuild(@Nullable String appBuild) {
/* 146 */     this.appBuild = appBuild;
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, String> getPermissions() {
/* 150 */     return this.permissions;
/*     */   }
/*     */   
/*     */   public void setPermissions(@Nullable Map<String, String> permissions) {
/* 154 */     this.permissions = permissions;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Boolean getInForeground() {
/* 159 */     return this.inForeground;
/*     */   }
/*     */   
/*     */   public void setInForeground(@Nullable Boolean inForeground) {
/* 163 */     this.inForeground = inForeground;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public List<String> getViewNames() {
/* 168 */     return this.viewNames;
/*     */   }
/*     */   
/*     */   public void setViewNames(@Nullable List<String> viewNames) {
/* 172 */     this.viewNames = viewNames;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getStartType() {
/* 177 */     return this.startType;
/*     */   }
/*     */   
/*     */   public void setStartType(@Nullable String startType) {
/* 181 */     this.startType = startType;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean getSplitApks() {
/* 185 */     return this.isSplitApks;
/*     */   }
/*     */   
/*     */   public void setSplitApks(@Nullable Boolean splitApks) {
/* 189 */     this.isSplitApks = splitApks;
/*     */   }
/*     */   @Nullable
/*     */   public List<String> getSplitNames() {
/* 193 */     return this.splitNames;
/*     */   }
/*     */   
/*     */   public void setSplitNames(@Nullable List<String> splitNames) {
/* 197 */     this.splitNames = splitNames;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 202 */     if (this == o) return true; 
/* 203 */     if (o == null || getClass() != o.getClass()) return false; 
/* 204 */     App app = (App)o;
/* 205 */     return (Objects.equals(this.appIdentifier, app.appIdentifier) && 
/* 206 */       Objects.equals(this.appStartTime, app.appStartTime) && 
/* 207 */       Objects.equals(this.deviceAppHash, app.deviceAppHash) && 
/* 208 */       Objects.equals(this.buildType, app.buildType) && 
/* 209 */       Objects.equals(this.appName, app.appName) && 
/* 210 */       Objects.equals(this.appVersion, app.appVersion) && 
/* 211 */       Objects.equals(this.appBuild, app.appBuild) && 
/* 212 */       Objects.equals(this.permissions, app.permissions) && 
/* 213 */       Objects.equals(this.inForeground, app.inForeground) && 
/* 214 */       Objects.equals(this.viewNames, app.viewNames) && 
/* 215 */       Objects.equals(this.startType, app.startType) && 
/* 216 */       Objects.equals(this.isSplitApks, app.isSplitApks) && 
/* 217 */       Objects.equals(this.splitNames, app.splitNames));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 222 */     return Objects.hash(new Object[] { this.appIdentifier, this.appStartTime, this.deviceAppHash, this.buildType, this.appName, this.appVersion, this.appBuild, this.permissions, this.inForeground, this.viewNames, this.startType, this.isSplitApks, this.splitNames });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 243 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 248 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String APP_IDENTIFIER = "app_identifier";
/*     */     public static final String APP_START_TIME = "app_start_time";
/*     */     public static final String DEVICE_APP_HASH = "device_app_hash";
/*     */     public static final String BUILD_TYPE = "build_type";
/*     */     public static final String APP_NAME = "app_name";
/*     */     public static final String APP_VERSION = "app_version";
/*     */     public static final String APP_BUILD = "app_build";
/*     */     public static final String APP_PERMISSIONS = "permissions";
/*     */     public static final String IN_FOREGROUND = "in_foreground";
/*     */     public static final String VIEW_NAMES = "view_names";
/*     */     public static final String START_TYPE = "start_type";
/*     */     public static final String IS_SPLIT_APKS = "is_split_apks";
/*     */     public static final String SPLIT_NAMES = "split_names";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 270 */     writer.beginObject();
/* 271 */     if (this.appIdentifier != null) {
/* 272 */       writer.name("app_identifier").value(this.appIdentifier);
/*     */     }
/* 274 */     if (this.appStartTime != null) {
/* 275 */       writer.name("app_start_time").value(logger, this.appStartTime);
/*     */     }
/* 277 */     if (this.deviceAppHash != null) {
/* 278 */       writer.name("device_app_hash").value(this.deviceAppHash);
/*     */     }
/* 280 */     if (this.buildType != null) {
/* 281 */       writer.name("build_type").value(this.buildType);
/*     */     }
/* 283 */     if (this.appName != null) {
/* 284 */       writer.name("app_name").value(this.appName);
/*     */     }
/* 286 */     if (this.appVersion != null) {
/* 287 */       writer.name("app_version").value(this.appVersion);
/*     */     }
/* 289 */     if (this.appBuild != null) {
/* 290 */       writer.name("app_build").value(this.appBuild);
/*     */     }
/* 292 */     if (this.permissions != null && !this.permissions.isEmpty()) {
/* 293 */       writer.name("permissions").value(logger, this.permissions);
/*     */     }
/* 295 */     if (this.inForeground != null) {
/* 296 */       writer.name("in_foreground").value(this.inForeground);
/*     */     }
/* 298 */     if (this.viewNames != null) {
/* 299 */       writer.name("view_names").value(logger, this.viewNames);
/*     */     }
/* 301 */     if (this.startType != null) {
/* 302 */       writer.name("start_type").value(this.startType);
/*     */     }
/* 304 */     if (this.isSplitApks != null) {
/* 305 */       writer.name("is_split_apks").value(this.isSplitApks);
/*     */     }
/* 307 */     if (this.splitNames != null && !this.splitNames.isEmpty()) {
/* 308 */       writer.name("split_names").value(logger, this.splitNames);
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
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<App>
/*     */   {
/*     */     @NotNull
/*     */     public App deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 324 */       reader.beginObject();
/* 325 */       App app = new App();
/* 326 */       Map<String, Object> unknown = null;
/* 327 */       while (reader.peek() == JsonToken.NAME) {
/* 328 */         List<String> viewNames, splitNames; String nextName = reader.nextName();
/* 329 */         switch (nextName) {
/*     */           case "app_identifier":
/* 331 */             app.appIdentifier = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "app_start_time":
/* 334 */             app.appStartTime = reader.nextDateOrNull(logger);
/*     */             continue;
/*     */           case "device_app_hash":
/* 337 */             app.deviceAppHash = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "build_type":
/* 340 */             app.buildType = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "app_name":
/* 343 */             app.appName = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "app_version":
/* 346 */             app.appVersion = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "app_build":
/* 349 */             app.appBuild = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "permissions":
/* 352 */             app.permissions = 
/* 353 */               CollectionUtils.newConcurrentHashMap((Map)reader
/* 354 */                 .nextObjectOrNull());
/*     */             continue;
/*     */           case "in_foreground":
/* 357 */             app.inForeground = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "view_names":
/* 360 */             viewNames = (List<String>)reader.nextObjectOrNull();
/* 361 */             if (viewNames != null) {
/* 362 */               app.setViewNames(viewNames);
/*     */             }
/*     */             continue;
/*     */           case "start_type":
/* 366 */             app.startType = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "is_split_apks":
/* 369 */             app.isSplitApks = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "split_names":
/* 372 */             splitNames = (List<String>)reader.nextObjectOrNull();
/* 373 */             if (splitNames != null) {
/* 374 */               app.setSplitNames(splitNames);
/*     */             }
/*     */             continue;
/*     */         } 
/* 378 */         if (unknown == null) {
/* 379 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 381 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 385 */       app.setUnknown(unknown);
/* 386 */       reader.endObject();
/* 387 */       return app;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\App.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */