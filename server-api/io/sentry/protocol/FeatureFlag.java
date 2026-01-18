/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FeatureFlag
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @NotNull
/*     */   public static final String DATA_PREFIX = "flag.evaluation.";
/*     */   @NotNull
/*     */   private String flag;
/*     */   private boolean result;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public FeatureFlag(@NotNull String flag, boolean result) {
/*  32 */     this.flag = flag;
/*  33 */     this.result = result;
/*     */   }
/*     */   @NotNull
/*     */   public String getFlag() {
/*  37 */     return this.flag;
/*     */   }
/*     */   
/*     */   public void setFlag(@NotNull String flag) {
/*  41 */     this.flag = flag;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Boolean getResult() {
/*  46 */     return Boolean.valueOf(this.result);
/*     */   }
/*     */   
/*     */   public void setResult(@NotNull Boolean result) {
/*  50 */     this.result = result.booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  55 */     if (this == o) return true; 
/*  56 */     if (o == null || getClass() != o.getClass()) return false; 
/*  57 */     FeatureFlag otherFlag = (FeatureFlag)o;
/*  58 */     return (Objects.equals(this.flag, otherFlag.flag) && Objects.equals(Boolean.valueOf(this.result), Boolean.valueOf(otherFlag.result)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  63 */     return Objects.hash(new Object[] { this.flag, Boolean.valueOf(this.result) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  71 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  76 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String FLAG = "flag";
/*     */     public static final String RESULT = "result";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  87 */     writer.beginObject();
/*     */     
/*  89 */     writer.name("flag").value(this.flag);
/*  90 */     writer.name("result").value(this.result);
/*  91 */     if (this.unknown != null) {
/*  92 */       for (String key : this.unknown.keySet()) {
/*  93 */         Object value = this.unknown.get(key);
/*  94 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/*  97 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<FeatureFlag>
/*     */   {
/*     */     @NotNull
/*     */     public FeatureFlag deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 105 */       reader.beginObject();
/* 106 */       String flag = null;
/* 107 */       Boolean result = null;
/* 108 */       Map<String, Object> unknown = null;
/* 109 */       while (reader.peek() == JsonToken.NAME) {
/* 110 */         String nextName = reader.nextName();
/* 111 */         switch (nextName) {
/*     */           case "flag":
/* 113 */             flag = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "result":
/* 116 */             result = reader.nextBooleanOrNull();
/*     */             continue;
/*     */         } 
/* 119 */         if (unknown == null) {
/* 120 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 122 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 126 */       if (flag == null) {
/* 127 */         String message = "Missing required field \"flag\"";
/* 128 */         Exception exception = new IllegalStateException(message);
/* 129 */         logger.log(SentryLevel.ERROR, message, exception);
/* 130 */         throw exception;
/*     */       } 
/* 132 */       if (result == null) {
/* 133 */         String message = "Missing required field \"result\"";
/* 134 */         Exception exception = new IllegalStateException(message);
/* 135 */         logger.log(SentryLevel.ERROR, message, exception);
/* 136 */         throw exception;
/*     */       } 
/* 138 */       FeatureFlag app = new FeatureFlag(flag, result.booleanValue());
/* 139 */       app.setUnknown(unknown);
/* 140 */       reader.endObject();
/* 141 */       return app;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\FeatureFlag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */