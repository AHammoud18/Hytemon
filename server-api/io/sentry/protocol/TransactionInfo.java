/*    */ package io.sentry.protocol;
/*    */ 
/*    */ import io.sentry.ILogger;
/*    */ import io.sentry.JsonDeserializer;
/*    */ import io.sentry.JsonSerializable;
/*    */ import io.sentry.JsonUnknown;
/*    */ import io.sentry.ObjectReader;
/*    */ import io.sentry.ObjectWriter;
/*    */ import io.sentry.vendor.gson.stream.JsonToken;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Internal
/*    */ public final class TransactionInfo
/*    */   implements JsonSerializable, JsonUnknown {
/*    */   @Nullable
/*    */   private final String source;
/*    */   
/*    */   public TransactionInfo(@Nullable String source) {
/* 24 */     this.source = source;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private Map<String, Object> unknown;
/*    */   
/*    */   public static final class JsonKeys
/*    */   {
/*    */     public static final String SOURCE = "source";
/*    */   }
/*    */   
/*    */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 36 */     writer.beginObject();
/* 37 */     if (this.source != null) {
/* 38 */       writer.name("source").value(logger, this.source);
/*    */     }
/* 40 */     if (this.unknown != null) {
/* 41 */       for (String key : this.unknown.keySet()) {
/* 42 */         Object value = this.unknown.get(key);
/* 43 */         writer.name(key);
/* 44 */         writer.value(logger, value);
/*    */       } 
/*    */     }
/* 47 */     writer.endObject();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Map<String, Object> getUnknown() {
/* 53 */     return this.unknown;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 58 */     this.unknown = unknown;
/*    */   }
/*    */   
/*    */   public static final class Deserializer
/*    */     implements JsonDeserializer<TransactionInfo>
/*    */   {
/*    */     @NotNull
/*    */     public TransactionInfo deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 66 */       reader.beginObject();
/*    */       
/* 68 */       String source = null;
/* 69 */       Map<String, Object> unknown = null;
/*    */       
/* 71 */       while (reader.peek() == JsonToken.NAME) {
/* 72 */         String nextName = reader.nextName();
/* 73 */         switch (nextName) {
/*    */           case "source":
/* 75 */             source = reader.nextStringOrNull();
/*    */             continue;
/*    */         } 
/* 78 */         if (unknown == null) {
/* 79 */           unknown = new ConcurrentHashMap<>();
/*    */         }
/* 81 */         reader.nextUnknown(logger, unknown, nextName);
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 86 */       TransactionInfo transactionInfo = new TransactionInfo(source);
/* 87 */       transactionInfo.setUnknown(unknown);
/* 88 */       reader.endObject();
/* 89 */       return transactionInfo;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\TransactionInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */