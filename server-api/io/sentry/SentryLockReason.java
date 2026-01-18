/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.util.CollectionUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SentryLockReason
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   public static final int LOCKED = 1;
/*     */   public static final int WAITING = 2;
/*     */   public static final int SLEEPING = 4;
/*     */   public static final int BLOCKED = 8;
/*     */   public static final int ANY = 15;
/*     */   private int type;
/*     */   
/*     */   public SentryLockReason(@NotNull SentryLockReason other) {
/*  32 */     this.type = other.type;
/*  33 */     this.address = other.address;
/*  34 */     this.packageName = other.packageName;
/*  35 */     this.className = other.className;
/*  36 */     this.threadId = other.threadId;
/*  37 */     this.unknown = CollectionUtils.newConcurrentHashMap(other.unknown); } @Nullable
/*     */   private String address; @Nullable
/*     */   private String packageName; @Nullable
/*     */   private String className; @Nullable
/*     */   private Long threadId; @Nullable
/*  42 */   private Map<String, Object> unknown; public SentryLockReason() {} public int getType() { return this.type; }
/*     */ 
/*     */   
/*     */   public void setType(int type) {
/*  46 */     this.type = type;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getAddress() {
/*  51 */     return this.address;
/*     */   }
/*     */   
/*     */   public void setAddress(@Nullable String address) {
/*  55 */     this.address = address;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getPackageName() {
/*  60 */     return this.packageName;
/*     */   }
/*     */   
/*     */   public void setPackageName(@Nullable String packageName) {
/*  64 */     this.packageName = packageName;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getClassName() {
/*  69 */     return this.className;
/*     */   }
/*     */   
/*     */   public void setClassName(@Nullable String className) {
/*  73 */     this.className = className;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Long getThreadId() {
/*  78 */     return this.threadId;
/*     */   }
/*     */   
/*     */   public void setThreadId(@Nullable Long threadId) {
/*  82 */     this.threadId = threadId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  87 */     if (this == o) return true; 
/*  88 */     if (o == null || getClass() != o.getClass()) return false; 
/*  89 */     SentryLockReason that = (SentryLockReason)o;
/*  90 */     return Objects.equals(this.address, that.address);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  95 */     return Objects.hash(new Object[] { this.address });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 103 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 108 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String TYPE = "type";
/*     */     public static final String ADDRESS = "address";
/*     */     public static final String PACKAGE_NAME = "package_name";
/*     */     public static final String CLASS_NAME = "class_name";
/*     */     public static final String THREAD_ID = "thread_id";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 122 */     writer.beginObject();
/* 123 */     writer.name("type").value(this.type);
/* 124 */     if (this.address != null) {
/* 125 */       writer.name("address").value(this.address);
/*     */     }
/* 127 */     if (this.packageName != null) {
/* 128 */       writer.name("package_name").value(this.packageName);
/*     */     }
/* 130 */     if (this.className != null) {
/* 131 */       writer.name("class_name").value(this.className);
/*     */     }
/* 133 */     if (this.threadId != null) {
/* 134 */       writer.name("thread_id").value(this.threadId);
/*     */     }
/* 136 */     if (this.unknown != null) {
/* 137 */       for (String key : this.unknown.keySet()) {
/* 138 */         Object value = this.unknown.get(key);
/* 139 */         writer.name(key);
/* 140 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 143 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryLockReason>
/*     */   {
/*     */     @NotNull
/*     */     public SentryLockReason deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 151 */       SentryLockReason sentryLockReason = new SentryLockReason();
/* 152 */       Map<String, Object> unknown = null;
/* 153 */       reader.beginObject();
/* 154 */       while (reader.peek() == JsonToken.NAME) {
/* 155 */         String nextName = reader.nextName();
/* 156 */         switch (nextName) {
/*     */           case "type":
/* 158 */             sentryLockReason.type = reader.nextInt();
/*     */             continue;
/*     */           case "address":
/* 161 */             sentryLockReason.address = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "package_name":
/* 164 */             sentryLockReason.packageName = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "class_name":
/* 167 */             sentryLockReason.className = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "thread_id":
/* 170 */             sentryLockReason.threadId = reader.nextLongOrNull();
/*     */             continue;
/*     */         } 
/* 173 */         if (unknown == null) {
/* 174 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 176 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 180 */       sentryLockReason.setUnknown(unknown);
/* 181 */       reader.endObject();
/* 182 */       return sentryLockReason;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryLockReason.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */