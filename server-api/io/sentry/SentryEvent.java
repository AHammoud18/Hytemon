/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.Message;
/*     */ import io.sentry.protocol.SentryException;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.SentryThread;
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.jetbrains.annotations.TestOnly;
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
/*     */ public final class SentryEvent
/*     */   extends SentryBaseEvent
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @NotNull
/*     */   private Date timestamp;
/*     */   @Nullable
/*     */   private Message message;
/*     */   @Nullable
/*     */   private String logger;
/*     */   @Nullable
/*     */   private SentryValues<SentryThread> threads;
/*     */   @Nullable
/*     */   private SentryValues<SentryException> exception;
/*     */   @Nullable
/*     */   private SentryLevel level;
/*     */   @Nullable
/*     */   private String transaction;
/*     */   @Nullable
/*     */   private List<String> fingerprint;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   @Nullable
/*     */   private Map<String, String> modules;
/*     */   
/*     */   SentryEvent(@NotNull SentryId eventId, @NotNull Date timestamp) {
/*  92 */     super(eventId);
/*  93 */     this.timestamp = timestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SentryEvent(@Nullable Throwable throwable) {
/* 102 */     this();
/* 103 */     this.throwable = throwable;
/*     */   }
/*     */   
/*     */   public SentryEvent() {
/* 107 */     this(new SentryId(), DateUtils.getCurrentDateTime());
/*     */   }
/*     */   
/*     */   @TestOnly
/*     */   public SentryEvent(@NotNull Date timestamp) {
/* 112 */     this(new SentryId(), timestamp);
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getTimestamp() {
/* 117 */     return (Date)this.timestamp.clone();
/*     */   }
/*     */   
/*     */   public void setTimestamp(@NotNull Date timestamp) {
/* 121 */     this.timestamp = timestamp;
/*     */   }
/*     */   @Nullable
/*     */   public Message getMessage() {
/* 125 */     return this.message;
/*     */   }
/*     */   
/*     */   public void setMessage(@Nullable Message message) {
/* 129 */     this.message = message;
/*     */   }
/*     */   @Nullable
/*     */   public String getLogger() {
/* 133 */     return this.logger;
/*     */   }
/*     */   
/*     */   public void setLogger(@Nullable String logger) {
/* 137 */     this.logger = logger;
/*     */   }
/*     */   @Nullable
/*     */   public List<SentryThread> getThreads() {
/* 141 */     if (this.threads != null) {
/* 142 */       return this.threads.getValues();
/*     */     }
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setThreads(@Nullable List<SentryThread> threads) {
/* 149 */     this.threads = new SentryValues<>(threads);
/*     */   }
/*     */   @Nullable
/*     */   public List<SentryException> getExceptions() {
/* 153 */     return (this.exception == null) ? null : this.exception.getValues();
/*     */   }
/*     */   
/*     */   public void setExceptions(@Nullable List<SentryException> exception) {
/* 157 */     this.exception = new SentryValues<>(exception);
/*     */   }
/*     */   @Nullable
/*     */   public SentryLevel getLevel() {
/* 161 */     return this.level;
/*     */   }
/*     */   
/*     */   public void setLevel(@Nullable SentryLevel level) {
/* 165 */     this.level = level;
/*     */   }
/*     */   @Nullable
/*     */   public String getTransaction() {
/* 169 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public void setTransaction(@Nullable String transaction) {
/* 173 */     this.transaction = transaction;
/*     */   }
/*     */   @Nullable
/*     */   public List<String> getFingerprints() {
/* 177 */     return this.fingerprint;
/*     */   }
/*     */   
/*     */   public void setFingerprints(@Nullable List<String> fingerprint) {
/* 181 */     this.fingerprint = (fingerprint != null) ? new ArrayList<>(fingerprint) : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   Map<String, String> getModules() {
/* 186 */     return this.modules;
/*     */   }
/*     */   
/*     */   public void setModules(@Nullable Map<String, String> modules) {
/* 190 */     this.modules = CollectionUtils.newHashMap(modules);
/*     */   }
/*     */   
/*     */   public void setModule(@NotNull String key, @NotNull String value) {
/* 194 */     if (this.modules == null) {
/* 195 */       this.modules = new HashMap<>();
/*     */     }
/* 197 */     this.modules.put(key, value);
/*     */   }
/*     */   
/*     */   public void removeModule(@NotNull String key) {
/* 201 */     if (this.modules != null)
/* 202 */       this.modules.remove(key); 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getModule(@NotNull String key) {
/* 207 */     if (this.modules != null) {
/* 208 */       return this.modules.get(key);
/*     */     }
/* 210 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCrashed() {
/* 219 */     return (getUnhandledException() != null);
/*     */   }
/*     */   @Nullable
/*     */   public SentryException getUnhandledException() {
/* 223 */     if (this.exception != null) {
/* 224 */       for (SentryException e : this.exception.getValues()) {
/* 225 */         if (e.getMechanism() != null && e
/* 226 */           .getMechanism().isHandled() != null && 
/* 227 */           !e.getMechanism().isHandled().booleanValue()) {
/* 228 */           return e;
/*     */         }
/*     */       } 
/*     */     }
/* 232 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isErrored() {
/* 241 */     return (this.exception != null && !this.exception.getValues().isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String TIMESTAMP = "timestamp";
/*     */     
/*     */     public static final String MESSAGE = "message";
/*     */     
/*     */     public static final String LOGGER = "logger";
/*     */     public static final String THREADS = "threads";
/*     */     public static final String EXCEPTION = "exception";
/*     */     public static final String LEVEL = "level";
/*     */     public static final String TRANSACTION = "transaction";
/*     */     public static final String FINGERPRINT = "fingerprint";
/*     */     public static final String MODULES = "modules";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 261 */     writer.beginObject();
/* 262 */     writer.name("timestamp").value(logger, this.timestamp);
/* 263 */     if (this.message != null) {
/* 264 */       writer.name("message").value(logger, this.message);
/*     */     }
/* 266 */     if (this.logger != null) {
/* 267 */       writer.name("logger").value(this.logger);
/*     */     }
/* 269 */     if (this.threads != null && !this.threads.getValues().isEmpty()) {
/* 270 */       writer.name("threads");
/* 271 */       writer.beginObject();
/* 272 */       writer.name("values").value(logger, this.threads.getValues());
/* 273 */       writer.endObject();
/*     */     } 
/* 275 */     if (this.exception != null && !this.exception.getValues().isEmpty()) {
/* 276 */       writer.name("exception");
/* 277 */       writer.beginObject();
/* 278 */       writer.name("values").value(logger, this.exception.getValues());
/* 279 */       writer.endObject();
/*     */     } 
/* 281 */     if (this.level != null) {
/* 282 */       writer.name("level").value(logger, this.level);
/*     */     }
/* 284 */     if (this.transaction != null) {
/* 285 */       writer.name("transaction").value(this.transaction);
/*     */     }
/* 287 */     if (this.fingerprint != null) {
/* 288 */       writer.name("fingerprint").value(logger, this.fingerprint);
/*     */     }
/* 290 */     if (this.modules != null) {
/* 291 */       writer.name("modules").value(logger, this.modules);
/*     */     }
/* 293 */     (new SentryBaseEvent.Serializer()).serialize(this, writer, logger);
/* 294 */     if (this.unknown != null) {
/* 295 */       for (String key : this.unknown.keySet()) {
/* 296 */         Object value = this.unknown.get(key);
/* 297 */         writer.name(key);
/* 298 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 301 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 307 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 312 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryEvent>
/*     */   {
/*     */     @NotNull
/*     */     public SentryEvent deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 321 */       reader.beginObject();
/* 322 */       SentryEvent event = new SentryEvent();
/* 323 */       Map<String, Object> unknown = null;
/*     */       
/* 325 */       SentryBaseEvent.Deserializer baseEventDeserializer = new SentryBaseEvent.Deserializer();
/*     */       
/* 327 */       while (reader.peek() == JsonToken.NAME) {
/* 328 */         Date deserializedTimestamp; List<String> deserializedFingerprint; Map<String, String> deserializedModules; String nextName = reader.nextName();
/* 329 */         switch (nextName) {
/*     */           case "timestamp":
/* 331 */             deserializedTimestamp = reader.nextDateOrNull(logger);
/* 332 */             if (deserializedTimestamp != null) {
/* 333 */               event.timestamp = deserializedTimestamp;
/*     */             }
/*     */             continue;
/*     */           case "message":
/* 337 */             event.message = reader.<Message>nextOrNull(logger, (JsonDeserializer<Message>)new Message.Deserializer());
/*     */             continue;
/*     */           case "logger":
/* 340 */             event.logger = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "threads":
/* 343 */             reader.beginObject();
/* 344 */             reader.nextName();
/* 345 */             event.threads = new SentryValues(reader
/* 346 */                 .nextListOrNull(logger, (JsonDeserializer<?>)new SentryThread.Deserializer()));
/* 347 */             reader.endObject();
/*     */             continue;
/*     */           case "exception":
/* 350 */             reader.beginObject();
/* 351 */             reader.nextName();
/* 352 */             event.exception = new SentryValues(reader
/*     */                 
/* 354 */                 .nextListOrNull(logger, (JsonDeserializer<?>)new SentryException.Deserializer()));
/* 355 */             reader.endObject();
/*     */             continue;
/*     */           case "level":
/* 358 */             event.level = reader.<SentryLevel>nextOrNull(logger, new SentryLevel.Deserializer());
/*     */             continue;
/*     */           case "transaction":
/* 361 */             event.transaction = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "fingerprint":
/* 364 */             deserializedFingerprint = (List<String>)reader.nextObjectOrNull();
/* 365 */             if (deserializedFingerprint != null) {
/* 366 */               event.fingerprint = deserializedFingerprint;
/*     */             }
/*     */             continue;
/*     */           
/*     */           case "modules":
/* 371 */             deserializedModules = (Map<String, String>)reader.nextObjectOrNull();
/* 372 */             event.modules = CollectionUtils.newConcurrentHashMap(deserializedModules);
/*     */             continue;
/*     */         } 
/* 375 */         if (!baseEventDeserializer.deserializeValue(event, nextName, reader, logger)) {
/* 376 */           if (unknown == null) {
/* 377 */             unknown = new ConcurrentHashMap<>();
/*     */           }
/* 379 */           reader.nextUnknown(logger, unknown, nextName);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 384 */       event.setUnknown(unknown);
/* 385 */       reader.endObject();
/* 386 */       return event;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */