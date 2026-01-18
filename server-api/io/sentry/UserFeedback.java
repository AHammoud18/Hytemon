/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UserFeedback
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   private final SentryId eventId;
/*     */   @Nullable
/*     */   private String name;
/*     */   @Nullable
/*     */   private String email;
/*     */   @Nullable
/*     */   private String comments;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public UserFeedback(SentryId eventId) {
/*  27 */     this(eventId, null, null, null);
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
/*     */   public UserFeedback(SentryId eventId, @Nullable String name, @Nullable String email, @Nullable String comments) {
/*  40 */     this.eventId = eventId;
/*  41 */     this.name = name;
/*  42 */     this.email = email;
/*  43 */     this.comments = comments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SentryId getEventId() {
/*  52 */     return this.eventId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getName() {
/*  61 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/*  70 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getEmail() {
/*  79 */     return this.email;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmail(@Nullable String email) {
/*  88 */     this.email = email;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getComments() {
/*  97 */     return this.comments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setComments(@Nullable String comments) {
/* 106 */     this.comments = comments;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 111 */     return "UserFeedback{eventId=" + this.eventId + ", name='" + this.name + '\'' + ", email='" + this.email + '\'' + ", comments='" + this.comments + '\'' + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String EVENT_ID = "event_id";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String NAME = "name";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String EMAIL = "email";
/*     */ 
/*     */ 
/*     */     
/*     */     public static final String COMMENTS = "comments";
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 152 */     writer.beginObject();
/* 153 */     writer.name("event_id");
/* 154 */     this.eventId.serialize(writer, logger);
/* 155 */     if (this.name != null) {
/* 156 */       writer.name("name").value(this.name);
/*     */     }
/* 158 */     if (this.email != null) {
/* 159 */       writer.name("email").value(this.email);
/*     */     }
/* 161 */     if (this.comments != null) {
/* 162 */       writer.name("comments").value(this.comments);
/*     */     }
/* 164 */     if (this.unknown != null) {
/* 165 */       for (String key : this.unknown.keySet()) {
/* 166 */         Object value = this.unknown.get(key);
/* 167 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 170 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<UserFeedback>
/*     */   {
/*     */     @NotNull
/*     */     public UserFeedback deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 179 */       SentryId sentryId = null;
/* 180 */       String name = null;
/* 181 */       String email = null;
/* 182 */       String comments = null;
/* 183 */       Map<String, Object> unknown = null;
/*     */       
/* 185 */       reader.beginObject();
/* 186 */       while (reader.peek() == JsonToken.NAME) {
/* 187 */         String nextName = reader.nextName();
/* 188 */         switch (nextName) {
/*     */           case "event_id":
/* 190 */             sentryId = (new SentryId.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */           case "name":
/* 193 */             name = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "email":
/* 196 */             email = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "comments":
/* 199 */             comments = reader.nextStringOrNull();
/*     */             continue;
/*     */         } 
/* 202 */         if (unknown == null) {
/* 203 */           unknown = new HashMap<>();
/*     */         }
/* 205 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 209 */       reader.endObject();
/*     */       
/* 211 */       if (sentryId == null) {
/* 212 */         String message = "Missing required field \"event_id\"";
/* 213 */         Exception exception = new IllegalStateException(message);
/* 214 */         logger.log(SentryLevel.ERROR, message, exception);
/* 215 */         throw exception;
/*     */       } 
/*     */       
/* 218 */       UserFeedback userFeedback = new UserFeedback(sentryId, name, email, comments);
/* 219 */       userFeedback.setUnknown(unknown);
/* 220 */       return userFeedback;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\UserFeedback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */