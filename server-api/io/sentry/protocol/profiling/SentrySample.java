/*     */ package io.sentry.protocol.profiling;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class SentrySample
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   private double timestamp;
/*     */   private int stackId;
/*     */   @Nullable
/*     */   private String threadId;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public double getTimestamp() {
/*  31 */     return this.timestamp;
/*     */   }
/*     */   
/*     */   public void setTimestamp(double timestamp) {
/*  35 */     this.timestamp = timestamp;
/*     */   }
/*     */   
/*     */   public int getStackId() {
/*  39 */     return this.stackId;
/*     */   }
/*     */   
/*     */   public void setStackId(int stackId) {
/*  43 */     this.stackId = stackId;
/*     */   }
/*     */   @Nullable
/*     */   public String getThreadId() {
/*  47 */     return this.threadId;
/*     */   }
/*     */   
/*     */   public void setThreadId(@Nullable String threadId) {
/*  51 */     this.threadId = threadId;
/*     */   }
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String TIMESTAMP = "timestamp";
/*     */     public static final String STACK_ID = "stack_id";
/*     */     public static final String THREAD_ID = "thread_id";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  62 */     writer.beginObject();
/*     */     
/*  64 */     writer.name("timestamp").value(logger, doubleToBigDecimal(Double.valueOf(this.timestamp)));
/*  65 */     writer.name("stack_id").value(logger, Integer.valueOf(this.stackId));
/*     */     
/*  67 */     if (this.threadId != null) {
/*  68 */       writer.name("thread_id").value(logger, this.threadId);
/*     */     }
/*     */     
/*  71 */     if (this.unknown != null) {
/*  72 */       for (String key : this.unknown.keySet()) {
/*  73 */         Object value = this.unknown.get(key);
/*  74 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/*     */     
/*  78 */     writer.endObject();
/*     */   }
/*     */   @NotNull
/*     */   private BigDecimal doubleToBigDecimal(@NotNull Double value) {
/*  82 */     return BigDecimal.valueOf(value.doubleValue()).setScale(6, RoundingMode.DOWN);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  88 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  93 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentrySample>
/*     */   {
/*     */     @NotNull
/*     */     public SentrySample deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 101 */       reader.beginObject();
/* 102 */       SentrySample data = new SentrySample();
/* 103 */       Map<String, Object> unknown = null;
/*     */       
/* 105 */       while (reader.peek() == JsonToken.NAME) {
/* 106 */         String nextName = reader.nextName();
/* 107 */         switch (nextName) {
/*     */           case "timestamp":
/* 109 */             data.timestamp = reader.nextDouble();
/*     */             continue;
/*     */           case "stack_id":
/* 112 */             data.stackId = reader.nextInt();
/*     */             continue;
/*     */           case "thread_id":
/* 115 */             data.threadId = reader.nextStringOrNull();
/*     */             continue;
/*     */         } 
/* 118 */         if (unknown == null) {
/* 119 */           unknown = new HashMap<>();
/*     */         }
/* 121 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 125 */       data.setUnknown(unknown);
/* 126 */       reader.endObject();
/* 127 */       return data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\profiling\SentrySample.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */