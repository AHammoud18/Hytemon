/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryLockReason;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
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
/*     */ public final class SentryThread
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private Long id;
/*     */   @Nullable
/*     */   private Integer priority;
/*     */   @Nullable
/*     */   private String name;
/*     */   @Nullable
/*     */   private String state;
/*     */   @Nullable
/*     */   private Boolean crashed;
/*     */   @Nullable
/*     */   private Boolean current;
/*     */   @Nullable
/*     */   private Boolean daemon;
/*     */   @Nullable
/*     */   private Boolean main;
/*     */   @Nullable
/*     */   private SentryStackTrace stacktrace;
/*     */   @Nullable
/*     */   private Map<String, SentryLockReason> heldLocks;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   @Nullable
/*     */   public Long getId() {
/*  54 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(@Nullable Long id) {
/*  63 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getName() {
/*  72 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(@Nullable String name) {
/*  81 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Boolean isCrashed() {
/*  90 */     return this.crashed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCrashed(@Nullable Boolean crashed) {
/*  99 */     this.crashed = crashed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Boolean isCurrent() {
/* 108 */     return this.current;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrent(@Nullable Boolean current) {
/* 117 */     this.current = current;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SentryStackTrace getStacktrace() {
/* 126 */     return this.stacktrace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStacktrace(@Nullable SentryStackTrace stacktrace) {
/* 135 */     this.stacktrace = stacktrace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getPriority() {
/* 144 */     return this.priority;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPriority(@Nullable Integer priority) {
/* 153 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Boolean isDaemon() {
/* 162 */     return this.daemon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDaemon(@Nullable Boolean daemon) {
/* 171 */     this.daemon = daemon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Boolean isMain() {
/* 183 */     return this.main;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMain(@Nullable Boolean main) {
/* 194 */     this.main = main;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getState() {
/* 203 */     return this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(@Nullable String state) {
/* 212 */     this.state = state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, SentryLockReason> getHeldLocks() {
/* 221 */     return this.heldLocks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeldLocks(@Nullable Map<String, SentryLockReason> heldLocks) {
/* 230 */     this.heldLocks = heldLocks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 238 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 243 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String ID = "id";
/*     */     public static final String PRIORITY = "priority";
/*     */     public static final String NAME = "name";
/*     */     public static final String STATE = "state";
/*     */     public static final String CRASHED = "crashed";
/*     */     public static final String CURRENT = "current";
/*     */     public static final String DAEMON = "daemon";
/*     */     public static final String MAIN = "main";
/*     */     public static final String STACKTRACE = "stacktrace";
/*     */     public static final String HELD_LOCKS = "held_locks";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 262 */     writer.beginObject();
/* 263 */     if (this.id != null) {
/* 264 */       writer.name("id").value(this.id);
/*     */     }
/* 266 */     if (this.priority != null) {
/* 267 */       writer.name("priority").value(this.priority);
/*     */     }
/* 269 */     if (this.name != null) {
/* 270 */       writer.name("name").value(this.name);
/*     */     }
/* 272 */     if (this.state != null) {
/* 273 */       writer.name("state").value(this.state);
/*     */     }
/* 275 */     if (this.crashed != null) {
/* 276 */       writer.name("crashed").value(this.crashed);
/*     */     }
/* 278 */     if (this.current != null) {
/* 279 */       writer.name("current").value(this.current);
/*     */     }
/* 281 */     if (this.daemon != null) {
/* 282 */       writer.name("daemon").value(this.daemon);
/*     */     }
/* 284 */     if (this.main != null) {
/* 285 */       writer.name("main").value(this.main);
/*     */     }
/* 287 */     if (this.stacktrace != null) {
/* 288 */       writer.name("stacktrace").value(logger, this.stacktrace);
/*     */     }
/* 290 */     if (this.heldLocks != null) {
/* 291 */       writer.name("held_locks").value(logger, this.heldLocks);
/*     */     }
/* 293 */     if (this.unknown != null) {
/* 294 */       for (String key : this.unknown.keySet()) {
/* 295 */         Object value = this.unknown.get(key);
/* 296 */         writer.name(key);
/* 297 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 300 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryThread>
/*     */   {
/*     */     @NotNull
/*     */     public SentryThread deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 308 */       SentryThread sentryThread = new SentryThread();
/* 309 */       Map<String, Object> unknown = null;
/* 310 */       reader.beginObject();
/* 311 */       while (reader.peek() == JsonToken.NAME) {
/* 312 */         Map<String, SentryLockReason> heldLocks; String nextName = reader.nextName();
/* 313 */         switch (nextName) {
/*     */           case "id":
/* 315 */             sentryThread.id = reader.nextLongOrNull();
/*     */             continue;
/*     */           case "priority":
/* 318 */             sentryThread.priority = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "name":
/* 321 */             sentryThread.name = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "state":
/* 324 */             sentryThread.state = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "crashed":
/* 327 */             sentryThread.crashed = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "current":
/* 330 */             sentryThread.current = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "daemon":
/* 333 */             sentryThread.daemon = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "main":
/* 336 */             sentryThread.main = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "stacktrace":
/* 339 */             sentryThread.stacktrace = (SentryStackTrace)reader
/* 340 */               .nextOrNull(logger, new SentryStackTrace.Deserializer());
/*     */             continue;
/*     */           
/*     */           case "held_locks":
/* 344 */             heldLocks = reader.nextMapOrNull(logger, (JsonDeserializer)new SentryLockReason.Deserializer());
/* 345 */             if (heldLocks != null) {
/* 346 */               sentryThread.heldLocks = new HashMap<>(heldLocks);
/*     */             }
/*     */             continue;
/*     */         } 
/* 350 */         if (unknown == null) {
/* 351 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 353 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 357 */       sentryThread.setUnknown(unknown);
/* 358 */       reader.endObject();
/* 359 */       return sentryThread;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\SentryThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */