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
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class SentryThreadMetadata
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private String name;
/*     */   
/*     */   @Nullable
/*     */   public String getName() {
/*  26 */     return this.name;
/*     */   } private int priority; @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   public void setName(@Nullable String name) {
/*  30 */     this.name = name;
/*     */   }
/*     */   
/*     */   public int getPriority() {
/*  34 */     return this.priority;
/*     */   }
/*     */   
/*     */   public void setPriority(int priority) {
/*  38 */     this.priority = priority;
/*     */   }
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String NAME = "name";
/*     */     public static final String PRIORITY = "priority";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  48 */     writer.beginObject();
/*  49 */     if (this.name != null) {
/*  50 */       writer.name("name").value(logger, this.name);
/*     */     }
/*  52 */     writer.name("priority").value(logger, Integer.valueOf(this.priority));
/*     */     
/*  54 */     if (this.unknown != null) {
/*  55 */       for (String key : this.unknown.keySet()) {
/*  56 */         Object value = this.unknown.get(key);
/*  57 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/*     */     
/*  61 */     writer.endObject();
/*     */   }
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
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryThreadMetadata>
/*     */   {
/*     */     @NotNull
/*     */     public SentryThreadMetadata deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/*  79 */       reader.beginObject();
/*  80 */       SentryThreadMetadata data = new SentryThreadMetadata();
/*  81 */       Map<String, Object> unknown = null;
/*     */       
/*  83 */       while (reader.peek() == JsonToken.NAME) {
/*  84 */         String nextName = reader.nextName();
/*  85 */         switch (nextName) {
/*     */           case "name":
/*  87 */             data.name = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "priority":
/*  90 */             data.priority = reader.nextInt();
/*     */             continue;
/*     */         } 
/*  93 */         if (unknown == null) {
/*  94 */           unknown = new HashMap<>();
/*     */         }
/*  96 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 100 */       data.setUnknown(unknown);
/* 101 */       reader.endObject();
/* 102 */       return data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\profiling\SentryThreadMetadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */