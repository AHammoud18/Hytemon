/*     */ package io.sentry.clientreport;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class DiscardedEvent
/*     */   implements JsonUnknown, JsonSerializable {
/*     */   @NotNull
/*     */   private final String reason;
/*     */   @NotNull
/*     */   private final String category;
/*     */   
/*     */   public DiscardedEvent(@NotNull String reason, @NotNull String category, @NotNull Long quantity) {
/*  27 */     this.reason = reason;
/*  28 */     this.category = category;
/*  29 */     this.quantity = quantity; } @NotNull
/*     */   private final Long quantity; @Nullable
/*     */   private Map<String, Object> unknown; @NotNull
/*     */   public String getReason() {
/*  33 */     return this.reason;
/*     */   }
/*     */   @NotNull
/*     */   public String getCategory() {
/*  37 */     return this.category;
/*     */   }
/*     */   @NotNull
/*     */   public Long getQuantity() {
/*  41 */     return this.quantity;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  46 */     return "DiscardedEvent{reason='" + this.reason + '\'' + ", category='" + this.category + '\'' + ", quantity=" + this.quantity + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String REASON = "reason";
/*     */ 
/*     */     
/*     */     public static final String CATEGORY = "category";
/*     */ 
/*     */     
/*     */     public static final String QUANTITY = "quantity";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  66 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  71 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  77 */     writer.beginObject();
/*     */     
/*  79 */     writer.name("reason").value(this.reason);
/*  80 */     writer.name("category").value(this.category);
/*  81 */     writer.name("quantity").value(this.quantity);
/*     */     
/*  83 */     if (this.unknown != null) {
/*  84 */       for (String key : this.unknown.keySet()) {
/*  85 */         Object value = this.unknown.get(key);
/*  86 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/*     */     
/*  90 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<DiscardedEvent> {
/*     */     @NotNull
/*     */     public DiscardedEvent deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/*  97 */       String reason = null;
/*  98 */       String category = null;
/*  99 */       Long quanity = null;
/* 100 */       Map<String, Object> unknown = null;
/*     */       
/* 102 */       reader.beginObject();
/* 103 */       while (reader.peek() == JsonToken.NAME) {
/* 104 */         String nextName = reader.nextName();
/* 105 */         switch (nextName) {
/*     */           case "reason":
/* 107 */             reason = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "category":
/* 110 */             category = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "quantity":
/* 113 */             quanity = reader.nextLongOrNull();
/*     */             continue;
/*     */         } 
/* 116 */         if (unknown == null) {
/* 117 */           unknown = new HashMap<>();
/*     */         }
/* 119 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 123 */       reader.endObject();
/*     */       
/* 125 */       if (reason == null) {
/* 126 */         throw missingRequiredFieldException("reason", logger);
/*     */       }
/* 128 */       if (category == null) {
/* 129 */         throw missingRequiredFieldException("category", logger);
/*     */       }
/* 131 */       if (quanity == null) {
/* 132 */         throw missingRequiredFieldException("quantity", logger);
/*     */       }
/*     */       
/* 135 */       DiscardedEvent discardedEvent = new DiscardedEvent(reason, category, quanity);
/* 136 */       discardedEvent.setUnknown(unknown);
/* 137 */       return discardedEvent;
/*     */     }
/*     */     
/*     */     private Exception missingRequiredFieldException(String field, ILogger logger) {
/* 141 */       String message = "Missing required field \"" + field + "\"";
/* 142 */       Exception exception = new IllegalStateException(message);
/* 143 */       logger.log(SentryLevel.ERROR, message, exception);
/* 144 */       return exception;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\clientreport\DiscardedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */