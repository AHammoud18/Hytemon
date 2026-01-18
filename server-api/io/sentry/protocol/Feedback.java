/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Feedback
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String TYPE = "feedback";
/*     */   @NotNull
/*     */   private String message;
/*     */   @Nullable
/*     */   private String contactEmail;
/*     */   @Nullable
/*     */   private String name;
/*     */   
/*     */   public Feedback(@NotNull String message) {
/*  34 */     setMessage(message); } @Nullable
/*     */   private SentryId associatedEventId; @Nullable
/*     */   private SentryId replayId; @Nullable
/*     */   private String url; @Nullable
/*  38 */   private Map<String, Object> unknown; public Feedback(@NotNull Feedback feedback) { this.message = feedback.message;
/*  39 */     this.contactEmail = feedback.contactEmail;
/*  40 */     this.name = feedback.name;
/*  41 */     this.associatedEventId = feedback.associatedEventId;
/*  42 */     this.replayId = feedback.replayId;
/*  43 */     this.url = feedback.url;
/*  44 */     this.unknown = CollectionUtils.newConcurrentHashMap(feedback.unknown); }
/*     */   
/*     */   @Nullable
/*     */   public String getContactEmail() {
/*  48 */     return this.contactEmail;
/*     */   }
/*     */   
/*     */   public void setContactEmail(@Nullable String contactEmail) {
/*  52 */     this.contactEmail = contactEmail;
/*     */   }
/*     */   @Nullable
/*     */   public String getName() {
/*  56 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(@Nullable String name) {
/*  60 */     this.name = name;
/*     */   }
/*     */   @Nullable
/*     */   public SentryId getAssociatedEventId() {
/*  64 */     return this.associatedEventId;
/*     */   }
/*     */   
/*     */   public void setAssociatedEventId(@NotNull SentryId associatedEventId) {
/*  68 */     this.associatedEventId = associatedEventId;
/*     */   }
/*     */   @Nullable
/*     */   public SentryId getReplayId() {
/*  72 */     return this.replayId;
/*     */   }
/*     */   
/*     */   public void setReplayId(@NotNull SentryId replayId) {
/*  76 */     this.replayId = replayId;
/*     */   }
/*     */   @Nullable
/*     */   public String getUrl() {
/*  80 */     return this.url;
/*     */   }
/*     */   
/*     */   public void setUrl(@Nullable String url) {
/*  84 */     this.url = url;
/*     */   }
/*     */   @NotNull
/*     */   public String getMessage() {
/*  88 */     return this.message;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMessage(@NotNull String message) {
/*  93 */     if (message.length() > 4096) {
/*  94 */       this.message = message.substring(0, 4096);
/*     */     } else {
/*  96 */       this.message = message;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 102 */     if (this == o) return true; 
/* 103 */     if (!(o instanceof Feedback)) return false; 
/* 104 */     Feedback feedback = (Feedback)o;
/* 105 */     return (Objects.equals(this.message, feedback.message) && 
/* 106 */       Objects.equals(this.contactEmail, feedback.contactEmail) && 
/* 107 */       Objects.equals(this.name, feedback.name) && 
/* 108 */       Objects.equals(this.associatedEventId, feedback.associatedEventId) && 
/* 109 */       Objects.equals(this.replayId, feedback.replayId) && 
/* 110 */       Objects.equals(this.url, feedback.url) && 
/* 111 */       Objects.equals(this.unknown, feedback.unknown));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     return "Feedback{message='" + this.message + '\'' + ", contactEmail='" + this.contactEmail + '\'' + ", name='" + this.name + '\'' + ", associatedEventId=" + this.associatedEventId + ", replayId=" + this.replayId + ", url='" + this.url + '\'' + ", unknown=" + this.unknown + '}';
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
/*     */   public int hashCode() {
/* 140 */     return Objects.hash(new Object[] { this.message, this.contactEmail, this.name, this.associatedEventId, this.replayId, this.url, this.unknown });
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String MESSAGE = "message";
/*     */     
/*     */     public static final String CONTACT_EMAIL = "contact_email";
/*     */     
/*     */     public static final String NAME = "name";
/*     */     public static final String ASSOCIATED_EVENT_ID = "associated_event_id";
/*     */     public static final String REPLAY_ID = "replay_id";
/*     */     public static final String URL = "url";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 158 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 163 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 171 */     writer.beginObject();
/* 172 */     writer.name("message").value(this.message);
/* 173 */     if (this.contactEmail != null) {
/* 174 */       writer.name("contact_email").value(this.contactEmail);
/*     */     }
/* 176 */     if (this.name != null) {
/* 177 */       writer.name("name").value(this.name);
/*     */     }
/* 179 */     if (this.associatedEventId != null) {
/* 180 */       writer.name("associated_event_id");
/* 181 */       this.associatedEventId.serialize(writer, logger);
/*     */     } 
/* 183 */     if (this.replayId != null) {
/* 184 */       writer.name("replay_id");
/* 185 */       this.replayId.serialize(writer, logger);
/*     */     } 
/* 187 */     if (this.url != null) {
/* 188 */       writer.name("url").value(this.url);
/*     */     }
/* 190 */     if (this.unknown != null) {
/* 191 */       for (String key : this.unknown.keySet()) {
/* 192 */         Object value = this.unknown.get(key);
/* 193 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 196 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<Feedback>
/*     */   {
/*     */     @NotNull
/*     */     public Feedback deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 205 */       String message = null;
/* 206 */       String contactEmail = null;
/* 207 */       String name = null;
/* 208 */       SentryId associatedEventId = null;
/* 209 */       SentryId replayId = null;
/* 210 */       String url = null;
/* 211 */       Map<String, Object> unknown = null;
/*     */       
/* 213 */       reader.beginObject();
/* 214 */       while (reader.peek() == JsonToken.NAME) {
/* 215 */         String nextName = reader.nextName();
/* 216 */         switch (nextName) {
/*     */           case "message":
/* 218 */             message = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "contact_email":
/* 221 */             contactEmail = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "name":
/* 224 */             name = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "associated_event_id":
/* 227 */             associatedEventId = (new SentryId.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */           case "replay_id":
/* 230 */             replayId = (new SentryId.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */           case "url":
/* 233 */             url = reader.nextStringOrNull();
/*     */             continue;
/*     */         } 
/* 236 */         if (unknown == null) {
/* 237 */           unknown = new HashMap<>();
/*     */         }
/* 239 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 243 */       reader.endObject();
/*     */       
/* 245 */       if (message == null) {
/* 246 */         String errorMessage = "Missing required field \"message\"";
/* 247 */         Exception exception = new IllegalStateException(errorMessage);
/* 248 */         logger.log(SentryLevel.ERROR, errorMessage, exception);
/* 249 */         throw exception;
/*     */       } 
/*     */       
/* 252 */       Feedback feedback = new Feedback(message);
/* 253 */       feedback.contactEmail = contactEmail;
/* 254 */       feedback.name = name;
/* 255 */       feedback.associatedEventId = associatedEventId;
/* 256 */       feedback.replayId = replayId;
/* 257 */       feedback.url = url;
/* 258 */       feedback.unknown = unknown;
/* 259 */       return feedback;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\Feedback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */