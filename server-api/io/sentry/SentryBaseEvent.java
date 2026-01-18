/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.exception.ExceptionMechanismException;
/*     */ import io.sentry.protocol.Contexts;
/*     */ import io.sentry.protocol.DebugMeta;
/*     */ import io.sentry.protocol.Request;
/*     */ import io.sentry.protocol.SdkVersion;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.User;
/*     */ import io.sentry.util.CollectionUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SentryBaseEvent
/*     */ {
/*     */   public static final String DEFAULT_PLATFORM = "java";
/*     */   @Nullable
/*     */   private SentryId eventId;
/*     */   @NotNull
/*  45 */   private final Contexts contexts = new Contexts();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private SdkVersion sdk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Request request;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Map<String, String> tags;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String release;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String environment;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String platform;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private User user;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected transient Throwable throwable;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String serverName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String dist;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private List<Breadcrumb> breadcrumbs;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private DebugMeta debugMeta;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Map<String, Object> extra;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SentryBaseEvent(@NotNull SentryId eventId) {
/* 126 */     this.eventId = eventId;
/*     */   }
/*     */   
/*     */   protected SentryBaseEvent() {
/* 130 */     this(new SentryId());
/*     */   }
/*     */   @Nullable
/*     */   public SentryId getEventId() {
/* 134 */     return this.eventId;
/*     */   }
/*     */   
/*     */   public void setEventId(@Nullable SentryId eventId) {
/* 138 */     this.eventId = eventId;
/*     */   }
/*     */   @NotNull
/*     */   public Contexts getContexts() {
/* 142 */     return this.contexts;
/*     */   }
/*     */   @Nullable
/*     */   public SdkVersion getSdk() {
/* 146 */     return this.sdk;
/*     */   }
/*     */   
/*     */   public void setSdk(@Nullable SdkVersion sdk) {
/* 150 */     this.sdk = sdk;
/*     */   }
/*     */   @Nullable
/*     */   public Request getRequest() {
/* 154 */     return this.request;
/*     */   }
/*     */   
/*     */   public void setRequest(@Nullable Request request) {
/* 158 */     this.request = request;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Throwable getThrowable() {
/* 168 */     Throwable ex = this.throwable;
/* 169 */     if (ex instanceof ExceptionMechanismException) {
/* 170 */       return ((ExceptionMechanismException)ex).getThrowable();
/*     */     }
/* 172 */     return ex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public Throwable getThrowableMechanism() {
/* 185 */     return this.throwable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThrowable(@Nullable Throwable throwable) {
/* 194 */     this.throwable = throwable;
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public Map<String, String> getTags() {
/* 199 */     return this.tags;
/*     */   }
/*     */   
/*     */   public void setTags(@Nullable Map<String, String> tags) {
/* 203 */     this.tags = CollectionUtils.newHashMap(tags);
/*     */   }
/*     */   
/*     */   public void removeTag(@Nullable String key) {
/* 207 */     if (this.tags != null && key != null)
/* 208 */       this.tags.remove(key); 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getTag(@Nullable String key) {
/* 213 */     if (this.tags != null && key != null) {
/* 214 */       return this.tags.get(key);
/*     */     }
/* 216 */     return null;
/*     */   }
/*     */   
/*     */   public void setTag(@Nullable String key, @Nullable String value) {
/* 220 */     if (this.tags == null) {
/* 221 */       this.tags = new HashMap<>();
/*     */     }
/* 223 */     if (key == null) {
/*     */       return;
/*     */     }
/* 226 */     if (value == null) {
/* 227 */       removeTag(key);
/*     */     } else {
/* 229 */       this.tags.put(key, value);
/*     */     } 
/*     */   }
/*     */   @Nullable
/*     */   public String getRelease() {
/* 234 */     return this.release;
/*     */   }
/*     */   
/*     */   public void setRelease(@Nullable String release) {
/* 238 */     this.release = release;
/*     */   }
/*     */   @Nullable
/*     */   public String getEnvironment() {
/* 242 */     return this.environment;
/*     */   }
/*     */   
/*     */   public void setEnvironment(@Nullable String environment) {
/* 246 */     this.environment = environment;
/*     */   }
/*     */   @Nullable
/*     */   public String getPlatform() {
/* 250 */     return this.platform;
/*     */   }
/*     */   
/*     */   public void setPlatform(@Nullable String platform) {
/* 254 */     this.platform = platform;
/*     */   }
/*     */   @Nullable
/*     */   public String getServerName() {
/* 258 */     return this.serverName;
/*     */   }
/*     */   
/*     */   public void setServerName(@Nullable String serverName) {
/* 262 */     this.serverName = serverName;
/*     */   }
/*     */   @Nullable
/*     */   public String getDist() {
/* 266 */     return this.dist;
/*     */   }
/*     */   
/*     */   public void setDist(@Nullable String dist) {
/* 270 */     this.dist = dist;
/*     */   }
/*     */   @Nullable
/*     */   public User getUser() {
/* 274 */     return this.user;
/*     */   }
/*     */   
/*     */   public void setUser(@Nullable User user) {
/* 278 */     this.user = user;
/*     */   }
/*     */   @Nullable
/*     */   public List<Breadcrumb> getBreadcrumbs() {
/* 282 */     return this.breadcrumbs;
/*     */   }
/*     */   
/*     */   public void setBreadcrumbs(@Nullable List<Breadcrumb> breadcrumbs) {
/* 286 */     this.breadcrumbs = CollectionUtils.newArrayList(breadcrumbs);
/*     */   }
/*     */   
/*     */   public void addBreadcrumb(@NotNull Breadcrumb breadcrumb) {
/* 290 */     if (this.breadcrumbs == null) {
/* 291 */       this.breadcrumbs = new ArrayList<>();
/*     */     }
/* 293 */     this.breadcrumbs.add(breadcrumb);
/*     */   }
/*     */   @Nullable
/*     */   public DebugMeta getDebugMeta() {
/* 297 */     return this.debugMeta;
/*     */   }
/*     */   
/*     */   public void setDebugMeta(@Nullable DebugMeta debugMeta) {
/* 301 */     this.debugMeta = debugMeta;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getExtras() {
/* 306 */     return this.extra;
/*     */   }
/*     */   
/*     */   public void setExtras(@Nullable Map<String, Object> extra) {
/* 310 */     this.extra = CollectionUtils.newHashMap(extra);
/*     */   }
/*     */   
/*     */   public void setExtra(@Nullable String key, @Nullable Object value) {
/* 314 */     if (this.extra == null) {
/* 315 */       this.extra = new HashMap<>();
/*     */     }
/* 317 */     if (key == null) {
/*     */       return;
/*     */     }
/* 320 */     if (value == null) {
/* 321 */       removeExtra(key);
/*     */     } else {
/* 323 */       this.extra.put(key, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeExtra(@Nullable String key) {
/* 328 */     if (this.extra != null && key != null)
/* 329 */       this.extra.remove(key); 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object getExtra(@Nullable String key) {
/* 334 */     if (this.extra != null && key != null) {
/* 335 */       return this.extra.get(key);
/*     */     }
/* 337 */     return null;
/*     */   }
/*     */   
/*     */   public void addBreadcrumb(@Nullable String message) {
/* 341 */     addBreadcrumb(new Breadcrumb(message));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String EVENT_ID = "event_id";
/*     */     
/*     */     public static final String CONTEXTS = "contexts";
/*     */     
/*     */     public static final String SDK = "sdk";
/*     */     
/*     */     public static final String REQUEST = "request";
/*     */     public static final String TAGS = "tags";
/*     */     public static final String RELEASE = "release";
/*     */     public static final String ENVIRONMENT = "environment";
/*     */     public static final String PLATFORM = "platform";
/*     */     public static final String USER = "user";
/*     */     public static final String SERVER_NAME = "server_name";
/*     */     public static final String DIST = "dist";
/*     */     public static final String BREADCRUMBS = "breadcrumbs";
/*     */     public static final String DEBUG_META = "debug_meta";
/*     */     public static final String EXTRA = "extra";
/*     */   }
/*     */   
/*     */   public static final class Serializer
/*     */   {
/*     */     public void serialize(@NotNull SentryBaseEvent baseEvent, @NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 369 */       if (baseEvent.eventId != null) {
/* 370 */         writer.name("event_id").value(logger, baseEvent.eventId);
/*     */       }
/* 372 */       writer.name("contexts").value(logger, baseEvent.contexts);
/* 373 */       if (baseEvent.sdk != null) {
/* 374 */         writer.name("sdk").value(logger, baseEvent.sdk);
/*     */       }
/* 376 */       if (baseEvent.request != null) {
/* 377 */         writer.name("request").value(logger, baseEvent.request);
/*     */       }
/* 379 */       if (baseEvent.tags != null && !baseEvent.tags.isEmpty()) {
/* 380 */         writer.name("tags").value(logger, baseEvent.tags);
/*     */       }
/* 382 */       if (baseEvent.release != null) {
/* 383 */         writer.name("release").value(baseEvent.release);
/*     */       }
/* 385 */       if (baseEvent.environment != null) {
/* 386 */         writer.name("environment").value(baseEvent.environment);
/*     */       }
/* 388 */       if (baseEvent.platform != null) {
/* 389 */         writer.name("platform").value(baseEvent.platform);
/*     */       }
/* 391 */       if (baseEvent.user != null) {
/* 392 */         writer.name("user").value(logger, baseEvent.user);
/*     */       }
/* 394 */       if (baseEvent.serverName != null) {
/* 395 */         writer.name("server_name").value(baseEvent.serverName);
/*     */       }
/* 397 */       if (baseEvent.dist != null) {
/* 398 */         writer.name("dist").value(baseEvent.dist);
/*     */       }
/* 400 */       if (baseEvent.breadcrumbs != null && !baseEvent.breadcrumbs.isEmpty()) {
/* 401 */         writer.name("breadcrumbs").value(logger, baseEvent.breadcrumbs);
/*     */       }
/* 403 */       if (baseEvent.debugMeta != null) {
/* 404 */         writer.name("debug_meta").value(logger, baseEvent.debugMeta);
/*     */       }
/* 406 */       if (baseEvent.extra != null && !baseEvent.extra.isEmpty()) {
/* 407 */         writer.name("extra").value(logger, baseEvent.extra);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */   {
/*     */     public boolean deserializeValue(@NotNull SentryBaseEvent baseEvent, @NotNull String nextName, @NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/*     */       Contexts deserializedContexts;
/*     */       Map<String, String> deserializedTags;
/*     */       Map<String, Object> deserializedExtra;
/* 420 */       switch (nextName) {
/*     */         case "event_id":
/* 422 */           baseEvent.eventId = reader.<SentryId>nextOrNull(logger, (JsonDeserializer<SentryId>)new SentryId.Deserializer());
/* 423 */           return true;
/*     */         case "contexts":
/* 425 */           deserializedContexts = (new Contexts.Deserializer()).deserialize(reader, logger);
/* 426 */           baseEvent.contexts.putAll(deserializedContexts);
/* 427 */           return true;
/*     */         case "sdk":
/* 429 */           baseEvent.sdk = reader.<SdkVersion>nextOrNull(logger, (JsonDeserializer<SdkVersion>)new SdkVersion.Deserializer());
/* 430 */           return true;
/*     */         case "request":
/* 432 */           baseEvent.request = reader.<Request>nextOrNull(logger, (JsonDeserializer<Request>)new Request.Deserializer());
/* 433 */           return true;
/*     */         case "tags":
/* 435 */           deserializedTags = (Map<String, String>)reader.nextObjectOrNull();
/* 436 */           baseEvent.tags = CollectionUtils.newConcurrentHashMap(deserializedTags);
/* 437 */           return true;
/*     */         case "release":
/* 439 */           baseEvent.release = reader.nextStringOrNull();
/* 440 */           return true;
/*     */         case "environment":
/* 442 */           baseEvent.environment = reader.nextStringOrNull();
/* 443 */           return true;
/*     */         case "platform":
/* 445 */           baseEvent.platform = reader.nextStringOrNull();
/* 446 */           return true;
/*     */         case "user":
/* 448 */           baseEvent.user = reader.<User>nextOrNull(logger, (JsonDeserializer<User>)new User.Deserializer());
/* 449 */           return true;
/*     */         case "server_name":
/* 451 */           baseEvent.serverName = reader.nextStringOrNull();
/* 452 */           return true;
/*     */         case "dist":
/* 454 */           baseEvent.dist = reader.nextStringOrNull();
/* 455 */           return true;
/*     */         case "breadcrumbs":
/* 457 */           baseEvent.breadcrumbs = reader.nextListOrNull(logger, new Breadcrumb.Deserializer());
/* 458 */           return true;
/*     */         case "debug_meta":
/* 460 */           baseEvent.debugMeta = reader.<DebugMeta>nextOrNull(logger, (JsonDeserializer<DebugMeta>)new DebugMeta.Deserializer());
/* 461 */           return true;
/*     */         case "extra":
/* 463 */           deserializedExtra = (Map<String, Object>)reader.nextObjectOrNull();
/* 464 */           baseEvent.extra = CollectionUtils.newConcurrentHashMap(deserializedExtra);
/* 465 */           return true;
/*     */       } 
/* 467 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryBaseEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */