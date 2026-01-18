/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.util.CollectionUtils;
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
/*     */ 
/*     */ public final class Mechanism
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private final transient Thread thread;
/*     */   @Nullable
/*     */   private String type;
/*     */   @Nullable
/*     */   private String description;
/*     */   @Nullable
/*     */   private String helpLink;
/*     */   @Nullable
/*     */   private Boolean handled;
/*     */   @Nullable
/*     */   private Map<String, Object> meta;
/*     */   @Nullable
/*     */   private Map<String, Object> data;
/*     */   @Nullable
/*     */   private Boolean synthetic;
/*     */   @Nullable
/*     */   private Integer exceptionId;
/*     */   @Nullable
/*     */   private Integer parentId;
/*     */   @Nullable
/*     */   private Boolean exceptionGroup;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public Mechanism() {
/*  97 */     this(null);
/*     */   }
/*     */   
/*     */   public Mechanism(@Nullable Thread thread) {
/* 101 */     this.thread = thread;
/*     */   }
/*     */   @Nullable
/*     */   public String getType() {
/* 105 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(@Nullable String type) {
/* 109 */     this.type = type;
/*     */   }
/*     */   @Nullable
/*     */   public String getDescription() {
/* 113 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(@Nullable String description) {
/* 117 */     this.description = description;
/*     */   }
/*     */   @Nullable
/*     */   public String getHelpLink() {
/* 121 */     return this.helpLink;
/*     */   }
/*     */   
/*     */   public void setHelpLink(@Nullable String helpLink) {
/* 125 */     this.helpLink = helpLink;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isHandled() {
/* 129 */     return this.handled;
/*     */   }
/*     */   
/*     */   public void setHandled(@Nullable Boolean handled) {
/* 133 */     this.handled = handled;
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, Object> getMeta() {
/* 137 */     return this.meta;
/*     */   }
/*     */   
/*     */   public void setMeta(@Nullable Map<String, Object> meta) {
/* 141 */     this.meta = CollectionUtils.newHashMap(meta);
/*     */   }
/*     */   @Nullable
/*     */   public Map<String, Object> getData() {
/* 145 */     return this.data;
/*     */   }
/*     */   
/*     */   public void setData(@Nullable Map<String, Object> data) {
/* 149 */     this.data = CollectionUtils.newHashMap(data);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   Thread getThread() {
/* 154 */     return this.thread;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean getSynthetic() {
/* 158 */     return this.synthetic;
/*     */   }
/*     */   
/*     */   public void setSynthetic(@Nullable Boolean synthetic) {
/* 162 */     this.synthetic = synthetic;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getExceptionId() {
/* 166 */     return this.exceptionId;
/*     */   }
/*     */   
/*     */   public void setExceptionId(@Nullable Integer exceptionId) {
/* 170 */     this.exceptionId = exceptionId;
/*     */   }
/*     */   @Nullable
/*     */   public Integer getParentId() {
/* 174 */     return this.parentId;
/*     */   }
/*     */   
/*     */   public void setParentId(@Nullable Integer parentId) {
/* 178 */     this.parentId = parentId;
/*     */   }
/*     */   @Nullable
/*     */   public Boolean isExceptionGroup() {
/* 182 */     return this.exceptionGroup;
/*     */   }
/*     */   
/*     */   public void setExceptionGroup(@Nullable Boolean exceptionGroup) {
/* 186 */     this.exceptionGroup = exceptionGroup;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String TYPE = "type";
/*     */     
/*     */     public static final String DESCRIPTION = "description";
/*     */     
/*     */     public static final String HELP_LINK = "help_link";
/*     */     public static final String HANDLED = "handled";
/*     */     public static final String META = "meta";
/*     */     public static final String DATA = "data";
/*     */     public static final String SYNTHETIC = "synthetic";
/*     */     public static final String EXCEPTION_ID = "exception_id";
/*     */     public static final String PARENT_ID = "parent_id";
/*     */     public static final String IS_EXCEPTION_GROUP = "is_exception_group";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 208 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 213 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 221 */     writer.beginObject();
/* 222 */     if (this.type != null) {
/* 223 */       writer.name("type").value(this.type);
/*     */     }
/* 225 */     if (this.description != null) {
/* 226 */       writer.name("description").value(this.description);
/*     */     }
/* 228 */     if (this.helpLink != null) {
/* 229 */       writer.name("help_link").value(this.helpLink);
/*     */     }
/* 231 */     if (this.handled != null) {
/* 232 */       writer.name("handled").value(this.handled);
/*     */     }
/* 234 */     if (this.meta != null) {
/* 235 */       writer.name("meta").value(logger, this.meta);
/*     */     }
/* 237 */     if (this.data != null) {
/* 238 */       writer.name("data").value(logger, this.data);
/*     */     }
/* 240 */     if (this.synthetic != null) {
/* 241 */       writer.name("synthetic").value(this.synthetic);
/*     */     }
/* 243 */     if (this.exceptionId != null) {
/* 244 */       writer.name("exception_id").value(logger, this.exceptionId);
/*     */     }
/* 246 */     if (this.parentId != null) {
/* 247 */       writer.name("parent_id").value(logger, this.parentId);
/*     */     }
/* 249 */     if (this.exceptionGroup != null) {
/* 250 */       writer.name("is_exception_group").value(this.exceptionGroup);
/*     */     }
/* 252 */     if (this.unknown != null) {
/* 253 */       for (String key : this.unknown.keySet()) {
/* 254 */         Object value = this.unknown.get(key);
/* 255 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/* 258 */     writer.endObject();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<Mechanism>
/*     */   {
/*     */     @NotNull
/*     */     public Mechanism deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 268 */       Mechanism mechanism = new Mechanism();
/* 269 */       Map<String, Object> unknown = null;
/* 270 */       reader.beginObject();
/* 271 */       while (reader.peek() == JsonToken.NAME) {
/* 272 */         String nextName = reader.nextName();
/* 273 */         switch (nextName) {
/*     */           case "type":
/* 275 */             mechanism.type = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "description":
/* 278 */             mechanism.description = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "help_link":
/* 281 */             mechanism.helpLink = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "handled":
/* 284 */             mechanism.handled = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "meta":
/* 287 */             mechanism.meta = 
/* 288 */               CollectionUtils.newConcurrentHashMap((Map)reader
/* 289 */                 .nextObjectOrNull());
/*     */             continue;
/*     */           case "data":
/* 292 */             mechanism.data = 
/* 293 */               CollectionUtils.newConcurrentHashMap((Map)reader
/* 294 */                 .nextObjectOrNull());
/*     */             continue;
/*     */           case "synthetic":
/* 297 */             mechanism.synthetic = reader.nextBooleanOrNull();
/*     */             continue;
/*     */           case "exception_id":
/* 300 */             mechanism.exceptionId = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "parent_id":
/* 303 */             mechanism.parentId = reader.nextIntegerOrNull();
/*     */             continue;
/*     */           case "is_exception_group":
/* 306 */             mechanism.exceptionGroup = reader.nextBooleanOrNull();
/*     */             continue;
/*     */         } 
/* 309 */         if (unknown == null) {
/* 310 */           unknown = new HashMap<>();
/*     */         }
/* 312 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 316 */       reader.endObject();
/* 317 */       mechanism.setUnknown(unknown);
/* 318 */       return mechanism;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\Mechanism.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */