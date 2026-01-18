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
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Spring
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final String TYPE = "spring";
/*     */   @Nullable
/*     */   private String[] activeProfiles;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public Spring() {}
/*     */   
/*     */   public Spring(@NotNull Spring spring) {
/*  34 */     this.activeProfiles = spring.activeProfiles;
/*  35 */     this.unknown = CollectionUtils.newConcurrentHashMap(spring.unknown);
/*     */   }
/*     */   @Nullable
/*     */   public String[] getActiveProfiles() {
/*  39 */     return this.activeProfiles;
/*     */   }
/*     */   
/*     */   public void setActiveProfiles(@Nullable String[] activeProfiles) {
/*  43 */     this.activeProfiles = activeProfiles;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/*  49 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/*  54 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  59 */     if (this == o) return true; 
/*  60 */     if (o == null || getClass() != o.getClass()) return false; 
/*  61 */     Spring spring = (Spring)o;
/*  62 */     return Arrays.equals((Object[])this.activeProfiles, (Object[])spring.activeProfiles);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  67 */     return Arrays.hashCode((Object[])this.activeProfiles);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String ACTIVE_PROFILES = "active_profiles";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/*  77 */     writer.beginObject();
/*  78 */     if (this.activeProfiles != null) {
/*  79 */       writer.name("active_profiles").value(logger, this.activeProfiles);
/*     */     }
/*  81 */     if (this.unknown != null) {
/*  82 */       for (String key : this.unknown.keySet()) {
/*  83 */         Object value = this.unknown.get(key);
/*  84 */         writer.name(key);
/*  85 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/*  88 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<Spring> {
/*     */     @NotNull
/*     */     public Spring deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/*  95 */       reader.beginObject();
/*  96 */       Spring spring = new Spring();
/*  97 */       Map<String, Object> unknown = null;
/*  98 */       while (reader.peek() == JsonToken.NAME) {
/*  99 */         List<?> activeProfilesList; String nextName = reader.nextName();
/* 100 */         switch (nextName) {
/*     */           case "active_profiles":
/* 102 */             activeProfilesList = (List)reader.nextObjectOrNull();
/* 103 */             if (activeProfilesList != null) {
/* 104 */               String[] arrayOfString = new String[activeProfilesList.size()];
/* 105 */               activeProfilesList.toArray(arrayOfString);
/* 106 */               spring.activeProfiles = arrayOfString;
/*     */             } 
/*     */             continue;
/*     */         } 
/* 110 */         if (unknown == null) {
/* 111 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 113 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 117 */       spring.setUnknown(unknown);
/* 118 */       reader.endObject();
/* 119 */       return spring;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\Spring.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */