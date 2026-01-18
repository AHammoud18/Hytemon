/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.vendor.gson.stream.JsonToken;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class SentryLogEvents
/*    */   implements JsonUnknown, JsonSerializable {
/*    */   @NotNull
/*    */   private List<SentryLogEvent> items;
/*    */   
/*    */   public SentryLogEvents(@NotNull List<SentryLogEvent> items) {
/* 17 */     this.items = items;
/*    */   } @Nullable
/*    */   private Map<String, Object> unknown; @NotNull
/*    */   public List<SentryLogEvent> getItems() {
/* 21 */     return this.items;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final class JsonKeys
/*    */   {
/*    */     public static final String ITEMS = "items";
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 33 */     writer.beginObject();
/*    */     
/* 35 */     writer.name("items").value(logger, this.items);
/*    */     
/* 37 */     if (this.unknown != null) {
/* 38 */       for (String key : this.unknown.keySet()) {
/* 39 */         Object value = this.unknown.get(key);
/* 40 */         writer.name(key).value(logger, value);
/*    */       } 
/*    */     }
/*    */     
/* 44 */     writer.endObject();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Map<String, Object> getUnknown() {
/* 49 */     return this.unknown;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 54 */     this.unknown = unknown;
/*    */   }
/*    */ 
/*    */   
/*    */   public static final class Deserializer
/*    */     implements JsonDeserializer<SentryLogEvents>
/*    */   {
/*    */     @NotNull
/*    */     public SentryLogEvents deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 63 */       Map<String, Object> unknown = null;
/* 64 */       List<SentryLogEvent> items = null;
/*    */       
/* 66 */       reader.beginObject();
/* 67 */       while (reader.peek() == JsonToken.NAME) {
/* 68 */         String nextName = reader.nextName();
/* 69 */         switch (nextName) {
/*    */           case "items":
/* 71 */             items = reader.nextListOrNull(logger, new SentryLogEvent.Deserializer());
/*    */             continue;
/*    */         } 
/* 74 */         if (unknown == null) {
/* 75 */           unknown = new HashMap<>();
/*    */         }
/* 77 */         reader.nextUnknown(logger, unknown, nextName);
/*    */       } 
/*    */ 
/*    */       
/* 81 */       reader.endObject();
/*    */       
/* 83 */       if (items == null) {
/* 84 */         String message = "Missing required field \"items\"";
/* 85 */         Exception exception = new IllegalStateException(message);
/* 86 */         logger.log(SentryLevel.ERROR, message, exception);
/* 87 */         throw exception;
/*    */       } 
/*    */       
/* 90 */       SentryLogEvents logEvent = new SentryLogEvents(items);
/*    */       
/* 92 */       logEvent.setUnknown(unknown);
/*    */       
/* 94 */       return logEvent;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryLogEvents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */