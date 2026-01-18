/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class SentryEnvelopeItemHeader
/*     */   implements JsonSerializable, JsonUnknown
/*     */ {
/*     */   @Nullable
/*     */   private final String contentType;
/*     */   @Nullable
/*     */   private final Integer itemCount;
/*     */   @Nullable
/*     */   private final String fileName;
/*     */   @Nullable
/*     */   private final String platform;
/*     */   
/*     */   @NotNull
/*     */   public SentryItemType getType() {
/*  28 */     return this.type; } @NotNull
/*     */   private final SentryItemType type; private final int length; @Nullable
/*     */   private final Callable<Integer> getLength; @Nullable
/*     */   private final String attachmentType; @Nullable
/*  32 */   private Map<String, Object> unknown; public int getLength() { if (this.getLength != null) {
/*     */       try {
/*  34 */         return ((Integer)this.getLength.call()).intValue();
/*  35 */       } catch (Throwable ignored) {
/*     */         
/*  37 */         return -1;
/*     */       } 
/*     */     }
/*  40 */     return this.length; }
/*     */   
/*     */   @Nullable
/*     */   public String getContentType() {
/*  44 */     return this.contentType;
/*     */   }
/*     */   @Nullable
/*     */   public String getFileName() {
/*  48 */     return this.fileName;
/*     */   }
/*     */   @Nullable
/*     */   public String getPlatform() {
/*  52 */     return this.platform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public SentryEnvelopeItemHeader(@NotNull SentryItemType type, int length, @Nullable String contentType, @Nullable String fileName, @Nullable String attachmentType, @Nullable String platform, @Nullable Integer itemCount) {
/*  64 */     this.type = (SentryItemType)Objects.requireNonNull(type, "type is required");
/*  65 */     this.contentType = contentType;
/*  66 */     this.length = length;
/*  67 */     this.fileName = fileName;
/*  68 */     this.getLength = null;
/*  69 */     this.attachmentType = attachmentType;
/*  70 */     this.platform = platform;
/*  71 */     this.itemCount = itemCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SentryEnvelopeItemHeader(@NotNull SentryItemType type, @Nullable Callable<Integer> getLength, @Nullable String contentType, @Nullable String fileName, @Nullable String attachmentType) {
/*  80 */     this(type, getLength, contentType, fileName, attachmentType, (String)null, (Integer)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SentryEnvelopeItemHeader(@NotNull SentryItemType type, @Nullable Callable<Integer> getLength, @Nullable String contentType, @Nullable String fileName, @Nullable String attachmentType, @Nullable String platform, @Nullable Integer itemCount) {
/*  91 */     this.type = (SentryItemType)Objects.requireNonNull(type, "type is required");
/*  92 */     this.contentType = contentType;
/*  93 */     this.length = -1;
/*  94 */     this.fileName = fileName;
/*  95 */     this.getLength = getLength;
/*  96 */     this.attachmentType = attachmentType;
/*  97 */     this.platform = platform;
/*  98 */     this.itemCount = itemCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SentryEnvelopeItemHeader(@NotNull SentryItemType type, @Nullable Callable<Integer> getLength, @Nullable String contentType, @Nullable String fileName) {
/* 106 */     this(type, getLength, contentType, fileName, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getAttachmentType() {
/* 115 */     return this.attachmentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String CONTENT_TYPE = "content_type";
/*     */     
/*     */     public static final String FILENAME = "filename";
/*     */     
/*     */     public static final String TYPE = "type";
/*     */     public static final String ATTACHMENT_TYPE = "attachment_type";
/*     */     public static final String LENGTH = "length";
/*     */     public static final String PLATFORM = "platform";
/*     */     public static final String ITEM_COUNT = "item_count";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 133 */     writer.beginObject();
/* 134 */     if (this.contentType != null) {
/* 135 */       writer.name("content_type").value(this.contentType);
/*     */     }
/* 137 */     if (this.fileName != null) {
/* 138 */       writer.name("filename").value(this.fileName);
/*     */     }
/* 140 */     writer.name("type").value(logger, this.type);
/* 141 */     if (this.attachmentType != null) {
/* 142 */       writer.name("attachment_type").value(this.attachmentType);
/*     */     }
/* 144 */     if (this.platform != null) {
/* 145 */       writer.name("platform").value(this.platform);
/*     */     }
/* 147 */     if (this.itemCount != null) {
/* 148 */       writer.name("item_count").value(this.itemCount);
/*     */     }
/* 150 */     writer.name("length").value(getLength());
/* 151 */     if (this.unknown != null) {
/* 152 */       for (String key : this.unknown.keySet()) {
/* 153 */         Object value = this.unknown.get(key);
/* 154 */         writer.name(key);
/* 155 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 158 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<SentryEnvelopeItemHeader> {
/*     */     @NotNull
/*     */     public SentryEnvelopeItemHeader deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 165 */       reader.beginObject();
/*     */       
/* 167 */       String contentType = null;
/* 168 */       String fileName = null;
/* 169 */       SentryItemType type = null;
/* 170 */       int length = 0;
/* 171 */       String attachmentType = null;
/* 172 */       String platform = null;
/* 173 */       Integer itemCount = null;
/* 174 */       Map<String, Object> unknown = null;
/*     */       
/* 176 */       while (reader.peek() == JsonToken.NAME) {
/* 177 */         String nextName = reader.nextName();
/* 178 */         switch (nextName) {
/*     */           case "content_type":
/* 180 */             contentType = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "filename":
/* 183 */             fileName = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "type":
/* 186 */             type = reader.<SentryItemType>nextOrNull(logger, new SentryItemType.Deserializer());
/*     */             continue;
/*     */           case "length":
/* 189 */             length = reader.nextInt();
/*     */             continue;
/*     */           case "attachment_type":
/* 192 */             attachmentType = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "platform":
/* 195 */             platform = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "item_count":
/* 198 */             itemCount = reader.nextIntegerOrNull();
/*     */             continue;
/*     */         } 
/* 201 */         if (unknown == null) {
/* 202 */           unknown = new HashMap<>();
/*     */         }
/* 204 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 208 */       if (type == null) {
/* 209 */         throw missingRequiredFieldException("type", logger);
/*     */       }
/* 211 */       SentryEnvelopeItemHeader sentryEnvelopeItemHeader = new SentryEnvelopeItemHeader(type, length, contentType, fileName, attachmentType, platform, itemCount);
/*     */ 
/*     */       
/* 214 */       sentryEnvelopeItemHeader.setUnknown(unknown);
/* 215 */       reader.endObject();
/* 216 */       return sentryEnvelopeItemHeader;
/*     */     }
/*     */ 
/*     */     
/*     */     private Exception missingRequiredFieldException(String field, ILogger logger) {
/* 221 */       String message = "Missing required field \"" + field + "\"";
/* 222 */       Exception exception = new IllegalStateException(message);
/* 223 */       logger.log(SentryLevel.ERROR, message, exception);
/* 224 */       return exception;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 233 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 238 */     this.unknown = unknown;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryEnvelopeItemHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */