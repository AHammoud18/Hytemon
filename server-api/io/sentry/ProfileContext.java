/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.SentryId;
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class ProfileContext
/*     */   implements JsonUnknown, JsonSerializable {
/*     */   public static final String TYPE = "profile";
/*     */   @NotNull
/*     */   private SentryId profilerId;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public ProfileContext() {
/*  22 */     this(SentryId.EMPTY_ID);
/*     */   }
/*     */   
/*     */   public ProfileContext(@NotNull SentryId profilerId) {
/*  26 */     this.profilerId = profilerId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProfileContext(@NotNull ProfileContext profileContext) {
/*  35 */     this.profilerId = profileContext.profilerId;
/*     */     
/*  37 */     Map<String, Object> copiedUnknown = CollectionUtils.newConcurrentHashMap(profileContext.unknown);
/*  38 */     if (copiedUnknown != null) {
/*  39 */       this.unknown = copiedUnknown;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  45 */     if (this == o) return true; 
/*  46 */     if (!(o instanceof ProfileContext)) return false; 
/*  47 */     ProfileContext that = (ProfileContext)o;
/*  48 */     return this.profilerId.equals(that.profilerId);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  53 */     return Objects.hash(new Object[] { this.profilerId });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String PROFILER_ID = "profiler_id";
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  65 */     writer.beginObject();
/*  66 */     writer.name("profiler_id").value(logger, this.profilerId);
/*  67 */     if (this.unknown != null) {
/*  68 */       for (String key : this.unknown.keySet()) {
/*  69 */         Object value = this.unknown.get(key);
/*  70 */         writer.name(key).value(logger, value);
/*     */       } 
/*     */     }
/*  73 */     writer.endObject();
/*     */   }
/*     */   @NotNull
/*     */   public SentryId getProfilerId() {
/*  77 */     return this.profilerId;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  83 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  88 */     this.unknown = unknown;
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<ProfileContext> {
/*     */     @NotNull
/*     */     public ProfileContext deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/*  95 */       reader.beginObject();
/*  96 */       ProfileContext data = new ProfileContext();
/*  97 */       Map<String, Object> unknown = null;
/*  98 */       while (reader.peek() == JsonToken.NAME) {
/*  99 */         SentryId profilerId; String nextName = reader.nextName();
/* 100 */         switch (nextName) {
/*     */           case "profiler_id":
/* 102 */             profilerId = reader.<SentryId>nextOrNull(logger, (JsonDeserializer<SentryId>)new SentryId.Deserializer());
/* 103 */             if (profilerId != null) {
/* 104 */               data.profilerId = profilerId;
/*     */             }
/*     */             continue;
/*     */         } 
/* 108 */         if (unknown == null) {
/* 109 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 111 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 115 */       data.setUnknown(unknown);
/* 116 */       reader.endObject();
/* 117 */       return data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\ProfileContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */