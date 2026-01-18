/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.protocol.User;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ @Experimental
/*     */ public final class TraceContext
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @NotNull
/*     */   private final SentryId traceId;
/*     */   @NotNull
/*     */   private final String publicKey;
/*     */   @Nullable
/*     */   private final String release;
/*     */   @Nullable
/*     */   private final String environment;
/*     */   @Nullable
/*     */   private final String userId;
/*     */   
/*     */   TraceContext(@NotNull SentryId traceId, @NotNull String publicKey) {
/*  30 */     this(traceId, publicKey, null, null, null, null, null, null, null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private final String transaction;
/*     */   @Nullable
/*     */   private final String sampleRate;
/*     */   @Nullable
/*     */   private final String sampleRand;
/*     */   @Nullable
/*     */   private final String sampled;
/*     */   @Nullable
/*     */   private final SentryId replayId;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   @Deprecated
/*     */   TraceContext(@NotNull SentryId traceId, @NotNull String publicKey, @Nullable String release, @Nullable String environment, @Nullable String userId, @Nullable String transaction, @Nullable String sampleRate, @Nullable String sampled, @Nullable SentryId replayId) {
/*  48 */     this(traceId, publicKey, release, environment, userId, transaction, sampleRate, sampled, replayId, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TraceContext(@NotNull SentryId traceId, @NotNull String publicKey, @Nullable String release, @Nullable String environment, @Nullable String userId, @Nullable String transaction, @Nullable String sampleRate, @Nullable String sampled, @Nullable SentryId replayId, @Nullable String sampleRand) {
/*  72 */     this.traceId = traceId;
/*  73 */     this.publicKey = publicKey;
/*  74 */     this.release = release;
/*  75 */     this.environment = environment;
/*  76 */     this.userId = userId;
/*  77 */     this.transaction = transaction;
/*  78 */     this.sampleRate = sampleRate;
/*  79 */     this.sampled = sampled;
/*  80 */     this.replayId = replayId;
/*  81 */     this.sampleRand = sampleRand;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static String getUserId(@NotNull SentryOptions options, @Nullable User user) {
/*  87 */     if (options.isSendDefaultPii() && user != null) {
/*  88 */       return user.getId();
/*     */     }
/*     */     
/*  91 */     return null;
/*     */   }
/*     */   @NotNull
/*     */   public SentryId getTraceId() {
/*  95 */     return this.traceId;
/*     */   }
/*     */   @NotNull
/*     */   public String getPublicKey() {
/*  99 */     return this.publicKey;
/*     */   }
/*     */   @Nullable
/*     */   public String getRelease() {
/* 103 */     return this.release;
/*     */   }
/*     */   @Nullable
/*     */   public String getEnvironment() {
/* 107 */     return this.environment;
/*     */   }
/*     */   @Nullable
/*     */   public String getUserId() {
/* 111 */     return this.userId;
/*     */   }
/*     */   @Nullable
/*     */   public String getTransaction() {
/* 115 */     return this.transaction;
/*     */   }
/*     */   @Nullable
/*     */   public String getSampleRate() {
/* 119 */     return this.sampleRate;
/*     */   }
/*     */   @Nullable
/*     */   public String getSampleRand() {
/* 123 */     return this.sampleRand;
/*     */   }
/*     */   @Nullable
/*     */   public String getSampled() {
/* 127 */     return this.sampled;
/*     */   }
/*     */   @Nullable
/*     */   public SentryId getReplayId() {
/* 131 */     return this.replayId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 139 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 144 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String TRACE_ID = "trace_id";
/*     */     public static final String PUBLIC_KEY = "public_key";
/*     */     public static final String RELEASE = "release";
/*     */     public static final String ENVIRONMENT = "environment";
/*     */     public static final String USER_ID = "user_id";
/*     */     public static final String TRANSACTION = "transaction";
/*     */     public static final String SAMPLE_RATE = "sample_rate";
/*     */     public static final String SAMPLE_RAND = "sample_rand";
/*     */     public static final String SAMPLED = "sampled";
/*     */     public static final String REPLAY_ID = "replay_id";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 163 */     writer.beginObject();
/* 164 */     writer.name("trace_id").value(logger, this.traceId);
/* 165 */     writer.name("public_key").value(this.publicKey);
/* 166 */     if (this.release != null) {
/* 167 */       writer.name("release").value(this.release);
/*     */     }
/* 169 */     if (this.environment != null) {
/* 170 */       writer.name("environment").value(this.environment);
/*     */     }
/* 172 */     if (this.userId != null) {
/* 173 */       writer.name("user_id").value(this.userId);
/*     */     }
/* 175 */     if (this.transaction != null) {
/* 176 */       writer.name("transaction").value(this.transaction);
/*     */     }
/* 178 */     if (this.sampleRate != null) {
/* 179 */       writer.name("sample_rate").value(this.sampleRate);
/*     */     }
/* 181 */     if (this.sampleRand != null) {
/* 182 */       writer.name("sample_rand").value(this.sampleRand);
/*     */     }
/* 184 */     if (this.sampled != null) {
/* 185 */       writer.name("sampled").value(this.sampled);
/*     */     }
/* 187 */     if (this.replayId != null) {
/* 188 */       writer.name("replay_id").value(logger, this.replayId);
/*     */     }
/* 190 */     if (this.unknown != null) {
/* 191 */       for (String key : this.unknown.keySet()) {
/* 192 */         Object value = this.unknown.get(key);
/* 193 */         writer.name(key);
/* 194 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 197 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<TraceContext> {
/*     */     @NotNull
/*     */     public TraceContext deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 204 */       reader.beginObject();
/*     */       
/* 206 */       SentryId traceId = null;
/* 207 */       String publicKey = null;
/* 208 */       String release = null;
/* 209 */       String environment = null;
/* 210 */       String userId = null;
/* 211 */       String transaction = null;
/* 212 */       String sampleRate = null;
/* 213 */       String sampleRand = null;
/* 214 */       String sampled = null;
/* 215 */       SentryId replayId = null;
/*     */       
/* 217 */       Map<String, Object> unknown = null;
/* 218 */       while (reader.peek() == JsonToken.NAME) {
/* 219 */         String nextName = reader.nextName();
/* 220 */         switch (nextName) {
/*     */           case "trace_id":
/* 222 */             traceId = (new SentryId.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */           case "public_key":
/* 225 */             publicKey = reader.nextString();
/*     */             continue;
/*     */           case "release":
/* 228 */             release = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "environment":
/* 231 */             environment = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "user_id":
/* 234 */             userId = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "transaction":
/* 237 */             transaction = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "sample_rate":
/* 240 */             sampleRate = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "sample_rand":
/* 243 */             sampleRand = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "sampled":
/* 246 */             sampled = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "replay_id":
/* 249 */             replayId = (new SentryId.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */         } 
/* 252 */         if (unknown == null) {
/* 253 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 255 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 259 */       if (traceId == null) {
/* 260 */         throw missingRequiredFieldException("trace_id", logger);
/*     */       }
/* 262 */       if (publicKey == null) {
/* 263 */         throw missingRequiredFieldException("public_key", logger);
/*     */       }
/* 265 */       TraceContext traceContext = new TraceContext(traceId, publicKey, release, environment, userId, transaction, sampleRate, sampled, replayId, sampleRand);
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
/* 277 */       traceContext.setUnknown(unknown);
/* 278 */       reader.endObject();
/* 279 */       return traceContext;
/*     */     }
/*     */     
/*     */     private Exception missingRequiredFieldException(String field, ILogger logger) {
/* 283 */       String message = "Missing required field \"" + field + "\"";
/* 284 */       Exception exception = new IllegalStateException(message);
/* 285 */       logger.log(SentryLevel.ERROR, message, exception);
/* 286 */       return exception;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\TraceContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */