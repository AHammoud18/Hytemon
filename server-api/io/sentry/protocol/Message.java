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
/*     */ public final class Message
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private String formatted;
/*     */   @Nullable
/*     */   private String message;
/*     */   @Nullable
/*     */   private List<String> params;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   @Nullable
/*     */   public String getFormatted() {
/*  62 */     return this.formatted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormatted(@Nullable String formatted) {
/*  71 */     this.formatted = formatted;
/*     */   }
/*     */   @Nullable
/*     */   public String getMessage() {
/*  75 */     return this.message;
/*     */   }
/*     */   
/*     */   public void setMessage(@Nullable String message) {
/*  79 */     this.message = message;
/*     */   }
/*     */   @Nullable
/*     */   public List<String> getParams() {
/*  83 */     return this.params;
/*     */   }
/*     */   
/*     */   public void setParams(@Nullable List<String> params) {
/*  87 */     this.params = CollectionUtils.newArrayList(params);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String FORMATTED = "formatted";
/*     */     
/*     */     public static final String MESSAGE = "message";
/*     */     
/*     */     public static final String PARAMS = "params";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 101 */     writer.beginObject();
/* 102 */     if (this.formatted != null) {
/* 103 */       writer.name("formatted").value(this.formatted);
/*     */     }
/* 105 */     if (this.message != null) {
/* 106 */       writer.name("message").value(this.message);
/*     */     }
/* 108 */     if (this.params != null && !this.params.isEmpty()) {
/* 109 */       writer.name("params").value(logger, this.params);
/*     */     }
/* 111 */     if (this.unknown != null) {
/* 112 */       for (String key : this.unknown.keySet()) {
/* 113 */         Object value = this.unknown.get(key);
/* 114 */         writer.name(key);
/* 115 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 118 */     writer.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 124 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 129 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<Message>
/*     */   {
/*     */     @NotNull
/*     */     public Message deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 138 */       reader.beginObject();
/* 139 */       Message message = new Message();
/* 140 */       Map<String, Object> unknown = null;
/* 141 */       while (reader.peek() == JsonToken.NAME) {
/* 142 */         List<String> deserializedParams; String nextName = reader.nextName();
/* 143 */         switch (nextName) {
/*     */           case "formatted":
/* 145 */             message.formatted = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "message":
/* 148 */             message.message = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "params":
/* 151 */             deserializedParams = (List<String>)reader.nextObjectOrNull();
/* 152 */             if (deserializedParams != null) {
/* 153 */               message.params = deserializedParams;
/*     */             }
/*     */             continue;
/*     */         } 
/* 157 */         if (unknown == null) {
/* 158 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 160 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 164 */       message.setUnknown(unknown);
/* 165 */       reader.endObject();
/* 166 */       return message;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\Message.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */