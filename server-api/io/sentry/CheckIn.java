/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class CheckIn
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @NotNull
/*     */   private final SentryId checkInId;
/*     */   @NotNull
/*     */   private String monitorSlug;
/*     */   @NotNull
/*     */   private String status;
/*     */   @NotNull
/*  22 */   private final MonitorContexts contexts = new MonitorContexts(); @Nullable
/*     */   private Double duration; @Nullable
/*     */   private String release; @Nullable
/*     */   private String environment; @Nullable
/*     */   private MonitorConfig monitorConfig; @Nullable
/*     */   private Map<String, Object> unknown; public CheckIn(@NotNull String monitorSlug, @NotNull CheckInStatus status) {
/*  28 */     this((SentryId)null, monitorSlug, status.apiName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CheckIn(@Nullable SentryId id, @NotNull String monitorSlug, @NotNull CheckInStatus status) {
/*  35 */     this(id, monitorSlug, status.apiName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public CheckIn(@Nullable SentryId checkInId, @NotNull String monitorSlug, @NotNull String status) {
/*  43 */     this.checkInId = (checkInId == null) ? new SentryId() : checkInId;
/*  44 */     this.monitorSlug = monitorSlug;
/*  45 */     this.status = status;
/*     */   }
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String CHECK_IN_ID = "check_in_id";
/*     */     public static final String MONITOR_SLUG = "monitor_slug";
/*     */     public static final String STATUS = "status";
/*     */     public static final String DURATION = "duration";
/*     */     public static final String RELEASE = "release";
/*     */     public static final String ENVIRONMENT = "environment";
/*     */     public static final String CONTEXTS = "contexts";
/*     */     public static final String MONITOR_CONFIG = "monitor_config";
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public SentryId getCheckInId() {
/*  62 */     return this.checkInId;
/*     */   }
/*     */   @NotNull
/*     */   public String getMonitorSlug() {
/*  66 */     return this.monitorSlug;
/*     */   }
/*     */   
/*     */   public void setMonitorSlug(@NotNull String monitorSlug) {
/*  70 */     this.monitorSlug = monitorSlug;
/*     */   }
/*     */   @NotNull
/*     */   public String getStatus() {
/*  74 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(@NotNull String status) {
/*  78 */     this.status = status;
/*     */   }
/*     */   
/*     */   public void setStatus(@NotNull CheckInStatus status) {
/*  82 */     this.status = status.apiName();
/*     */   }
/*     */   @Nullable
/*     */   public Double getDuration() {
/*  86 */     return this.duration;
/*     */   }
/*     */   
/*     */   public void setDuration(@Nullable Double duration) {
/*  90 */     this.duration = duration;
/*     */   }
/*     */   @Nullable
/*     */   public String getRelease() {
/*  94 */     return this.release;
/*     */   }
/*     */   
/*     */   public void setRelease(@Nullable String release) {
/*  98 */     this.release = release;
/*     */   }
/*     */   @Nullable
/*     */   public String getEnvironment() {
/* 102 */     return this.environment;
/*     */   }
/*     */   
/*     */   public void setEnvironment(@Nullable String environment) {
/* 106 */     this.environment = environment;
/*     */   }
/*     */   @Nullable
/*     */   public MonitorConfig getMonitorConfig() {
/* 110 */     return this.monitorConfig;
/*     */   }
/*     */   
/*     */   public void setMonitorConfig(@Nullable MonitorConfig monitorConfig) {
/* 114 */     this.monitorConfig = monitorConfig;
/*     */   }
/*     */   @NotNull
/*     */   public MonitorContexts getContexts() {
/* 118 */     return this.contexts;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 125 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 130 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 138 */     writer.beginObject();
/* 139 */     writer.name("check_in_id");
/* 140 */     this.checkInId.serialize(writer, logger);
/* 141 */     writer.name("monitor_slug").value(this.monitorSlug);
/* 142 */     writer.name("status").value(this.status);
/* 143 */     if (this.duration != null) {
/* 144 */       writer.name("duration").value(this.duration);
/*     */     }
/* 146 */     if (this.release != null) {
/* 147 */       writer.name("release").value(this.release);
/*     */     }
/* 149 */     if (this.environment != null) {
/* 150 */       writer.name("environment").value(this.environment);
/*     */     }
/* 152 */     if (this.monitorConfig != null) {
/* 153 */       writer.name("monitor_config");
/* 154 */       this.monitorConfig.serialize(writer, logger);
/*     */     } 
/* 156 */     if (this.contexts != null) {
/* 157 */       writer.name("contexts");
/* 158 */       this.contexts.serialize(writer, logger);
/*     */     } 
/* 160 */     if (this.unknown != null) {
/* 161 */       for (String key : this.unknown.keySet()) {
/* 162 */         Object value = this.unknown.get(key);
/* 163 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 166 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<CheckIn>
/*     */   {
/*     */     @NotNull
/*     */     public CheckIn deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 175 */       SentryId sentryId = null;
/* 176 */       MonitorConfig monitorConfig = null;
/* 177 */       String monitorSlug = null;
/* 178 */       String status = null;
/* 179 */       Double duration = null;
/* 180 */       String release = null;
/* 181 */       String environment = null;
/* 182 */       MonitorContexts contexts = null;
/* 183 */       Map<String, Object> unknown = null;
/*     */       
/* 185 */       reader.beginObject();
/* 186 */       while (reader.peek() == JsonToken.NAME) {
/* 187 */         String nextName = reader.nextName();
/* 188 */         switch (nextName) {
/*     */           case "check_in_id":
/* 190 */             sentryId = (new SentryId.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */           case "monitor_slug":
/* 193 */             monitorSlug = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "status":
/* 196 */             status = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "duration":
/* 199 */             duration = reader.nextDoubleOrNull();
/*     */             continue;
/*     */           case "release":
/* 202 */             release = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "environment":
/* 205 */             environment = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "monitor_config":
/* 208 */             monitorConfig = (new MonitorConfig.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */           case "contexts":
/* 211 */             contexts = (new MonitorContexts.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */         } 
/* 214 */         if (unknown == null) {
/* 215 */           unknown = new HashMap<>();
/*     */         }
/* 217 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 221 */       reader.endObject();
/*     */       
/* 223 */       if (sentryId == null) {
/* 224 */         String message = "Missing required field \"check_in_id\"";
/* 225 */         Exception exception = new IllegalStateException(message);
/* 226 */         logger.log(SentryLevel.ERROR, message, exception);
/* 227 */         throw exception;
/*     */       } 
/*     */       
/* 230 */       if (monitorSlug == null) {
/* 231 */         String message = "Missing required field \"monitor_slug\"";
/* 232 */         Exception exception = new IllegalStateException(message);
/* 233 */         logger.log(SentryLevel.ERROR, message, exception);
/* 234 */         throw exception;
/*     */       } 
/*     */       
/* 237 */       if (status == null) {
/* 238 */         String message = "Missing required field \"status\"";
/* 239 */         Exception exception = new IllegalStateException(message);
/* 240 */         logger.log(SentryLevel.ERROR, message, exception);
/* 241 */         throw exception;
/*     */       } 
/*     */       
/* 244 */       CheckIn checkIn = new CheckIn(sentryId, monitorSlug, status);
/* 245 */       checkIn.setDuration(duration);
/* 246 */       checkIn.setRelease(release);
/* 247 */       checkIn.setEnvironment(environment);
/* 248 */       checkIn.setMonitorConfig(monitorConfig);
/* 249 */       checkIn.getContexts().putAll(contexts);
/* 250 */       checkIn.setUnknown(unknown);
/* 251 */       return checkIn;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\CheckIn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */