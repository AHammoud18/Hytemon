/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class SentryLogEventAttributeValue
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @NotNull
/*     */   private String type;
/*     */   
/*     */   public SentryLogEventAttributeValue(@NotNull String type, @Nullable Object value) {
/*  17 */     this.type = type;
/*  18 */     if (value != null && type.equals("string")) {
/*  19 */       this.value = value.toString();
/*     */     } else {
/*  21 */       this.value = value;
/*     */     } 
/*     */   } @Nullable
/*     */   private Object value; @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   public SentryLogEventAttributeValue(@NotNull SentryAttributeType type, @Nullable Object value) {
/*  27 */     this(type.apiName(), value);
/*     */   }
/*     */   @NotNull
/*     */   public String getType() {
/*  31 */     return this.type;
/*     */   }
/*     */   @Nullable
/*     */   public Object getValue() {
/*  35 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String TYPE = "type";
/*     */     
/*     */     public static final String VALUE = "value";
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  48 */     writer.beginObject();
/*  49 */     writer.name("type").value(logger, this.type);
/*  50 */     writer.name("value").value(logger, this.value);
/*     */     
/*  52 */     if (this.unknown != null) {
/*  53 */       for (String key : this.unknown.keySet()) {
/*  54 */         Object value = this.unknown.get(key);
/*  55 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/*  58 */     writer.endObject();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  63 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  68 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryLogEventAttributeValue>
/*     */   {
/*     */     @NotNull
/*     */     public SentryLogEventAttributeValue deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/*  77 */       Map<String, Object> unknown = null;
/*  78 */       String type = null;
/*  79 */       Object value = null;
/*     */       
/*  81 */       reader.beginObject();
/*  82 */       while (reader.peek() == JsonToken.NAME) {
/*  83 */         String nextName = reader.nextName();
/*  84 */         switch (nextName) {
/*     */           case "type":
/*  86 */             type = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "value":
/*  89 */             value = reader.nextObjectOrNull();
/*     */             continue;
/*     */         } 
/*  92 */         if (unknown == null) {
/*  93 */           unknown = new HashMap<>();
/*     */         }
/*  95 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/*  99 */       reader.endObject();
/*     */       
/* 101 */       if (type == null) {
/* 102 */         String message = "Missing required field \"type\"";
/* 103 */         Exception exception = new IllegalStateException(message);
/* 104 */         logger.log(SentryLevel.ERROR, message, exception);
/* 105 */         throw exception;
/*     */       } 
/*     */       
/* 108 */       SentryLogEventAttributeValue logEvent = new SentryLogEventAttributeValue(type, value);
/*     */       
/* 110 */       logEvent.setUnknown(unknown);
/*     */       
/* 112 */       return logEvent;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryLogEventAttributeValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */