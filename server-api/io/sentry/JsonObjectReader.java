/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.vendor.gson.stream.JsonReader;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ @Internal
/*     */ public final class JsonObjectReader implements ObjectReader {
/*     */   @NotNull
/*     */   private final JsonReader jsonReader;
/*     */   
/*     */   public JsonObjectReader(Reader in) {
/*  23 */     this.jsonReader = new JsonReader(in);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String nextStringOrNull() throws IOException {
/*  28 */     if (this.jsonReader.peek() == JsonToken.NULL) {
/*  29 */       this.jsonReader.nextNull();
/*  30 */       return null;
/*     */     } 
/*  32 */     return this.jsonReader.nextString();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Double nextDoubleOrNull() throws IOException {
/*  37 */     if (this.jsonReader.peek() == JsonToken.NULL) {
/*  38 */       this.jsonReader.nextNull();
/*  39 */       return null;
/*     */     } 
/*  41 */     return Double.valueOf(this.jsonReader.nextDouble());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Float nextFloatOrNull() throws IOException {
/*  46 */     if (this.jsonReader.peek() == JsonToken.NULL) {
/*  47 */       this.jsonReader.nextNull();
/*  48 */       return null;
/*     */     } 
/*  50 */     return Float.valueOf(nextFloat());
/*     */   }
/*     */ 
/*     */   
/*     */   public float nextFloat() throws IOException {
/*  55 */     return (float)this.jsonReader.nextDouble();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Long nextLongOrNull() throws IOException {
/*  60 */     if (this.jsonReader.peek() == JsonToken.NULL) {
/*  61 */       this.jsonReader.nextNull();
/*  62 */       return null;
/*     */     } 
/*  64 */     return Long.valueOf(this.jsonReader.nextLong());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Integer nextIntegerOrNull() throws IOException {
/*  69 */     if (this.jsonReader.peek() == JsonToken.NULL) {
/*  70 */       this.jsonReader.nextNull();
/*  71 */       return null;
/*     */     } 
/*  73 */     return Integer.valueOf(this.jsonReader.nextInt());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Boolean nextBooleanOrNull() throws IOException {
/*  78 */     if (this.jsonReader.peek() == JsonToken.NULL) {
/*  79 */       this.jsonReader.nextNull();
/*  80 */       return null;
/*     */     } 
/*  82 */     return Boolean.valueOf(this.jsonReader.nextBoolean());
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextUnknown(ILogger logger, Map<String, Object> unknown, String name) {
/*     */     try {
/*  88 */       unknown.put(name, nextObjectOrNull());
/*  89 */     } catch (Exception exception) {
/*  90 */       logger.log(SentryLevel.ERROR, exception, "Error deserializing unknown key: %s", new Object[] { name });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> List<T> nextListOrNull(@NotNull ILogger logger, @NotNull JsonDeserializer<T> deserializer) throws IOException {
/*  97 */     if (this.jsonReader.peek() == JsonToken.NULL) {
/*  98 */       this.jsonReader.nextNull();
/*  99 */       return null;
/*     */     } 
/* 101 */     this.jsonReader.beginArray();
/* 102 */     List<T> list = new ArrayList<>();
/* 103 */     if (this.jsonReader.hasNext()) {
/*     */       do {
/*     */         try {
/* 106 */           list.add(deserializer.deserialize(this, logger));
/* 107 */         } catch (Exception e) {
/* 108 */           logger.log(SentryLevel.WARNING, "Failed to deserialize object in list.", e);
/*     */         } 
/* 110 */       } while (this.jsonReader.peek() == JsonToken.BEGIN_OBJECT);
/*     */     }
/* 112 */     this.jsonReader.endArray();
/* 113 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> Map<String, T> nextMapOrNull(@NotNull ILogger logger, @NotNull JsonDeserializer<T> deserializer) throws IOException {
/* 119 */     if (this.jsonReader.peek() == JsonToken.NULL) {
/* 120 */       this.jsonReader.nextNull();
/* 121 */       return null;
/*     */     } 
/* 123 */     this.jsonReader.beginObject();
/* 124 */     Map<String, T> map = new HashMap<>();
/* 125 */     if (this.jsonReader.hasNext()) {
/*     */       do {
/*     */         try {
/* 128 */           String key = this.jsonReader.nextName();
/* 129 */           map.put(key, deserializer.deserialize(this, logger));
/* 130 */         } catch (Exception e) {
/* 131 */           logger.log(SentryLevel.WARNING, "Failed to deserialize object in map.", e);
/*     */         } 
/* 133 */       } while (this.jsonReader.peek() == JsonToken.BEGIN_OBJECT || this.jsonReader.peek() == JsonToken.NAME);
/*     */     }
/*     */     
/* 136 */     this.jsonReader.endObject();
/* 137 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> Map<String, List<T>> nextMapOfListOrNull(@NotNull ILogger logger, @NotNull JsonDeserializer<T> deserializer) throws IOException {
/* 144 */     if (peek() == JsonToken.NULL) {
/* 145 */       nextNull();
/* 146 */       return null;
/*     */     } 
/* 148 */     Map<String, List<T>> result = new HashMap<>();
/*     */     
/* 150 */     beginObject();
/* 151 */     if (hasNext()) {
/*     */       do {
/* 153 */         String key = nextName();
/* 154 */         List<T> list = nextListOrNull(logger, deserializer);
/* 155 */         if (list == null)
/* 156 */           continue;  result.put(key, list);
/*     */       }
/* 158 */       while (peek() == JsonToken.BEGIN_OBJECT || peek() == JsonToken.NAME);
/*     */     }
/* 160 */     endObject();
/*     */     
/* 162 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T nextOrNull(@NotNull ILogger logger, @NotNull JsonDeserializer<T> deserializer) throws Exception {
/* 168 */     if (this.jsonReader.peek() == JsonToken.NULL) {
/* 169 */       this.jsonReader.nextNull();
/* 170 */       return null;
/*     */     } 
/* 172 */     return deserializer.deserialize(this, logger);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Date nextDateOrNull(ILogger logger) throws IOException {
/* 177 */     if (this.jsonReader.peek() == JsonToken.NULL) {
/* 178 */       this.jsonReader.nextNull();
/* 179 */       return null;
/*     */     } 
/* 181 */     return ObjectReader.dateOrNull(this.jsonReader.nextString(), logger);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TimeZone nextTimeZoneOrNull(ILogger logger) throws IOException {
/* 186 */     if (this.jsonReader.peek() == JsonToken.NULL) {
/* 187 */       this.jsonReader.nextNull();
/* 188 */       return null;
/*     */     } 
/*     */     try {
/* 191 */       return TimeZone.getTimeZone(this.jsonReader.nextString());
/* 192 */     } catch (Exception e) {
/* 193 */       logger.log(SentryLevel.ERROR, "Error when deserializing TimeZone", e);
/*     */       
/* 195 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Object nextObjectOrNull() throws IOException {
/* 206 */     return (new JsonObjectDeserializer()).deserialize(this);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public JsonToken peek() throws IOException {
/* 211 */     return this.jsonReader.peek();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String nextName() throws IOException {
/* 216 */     return this.jsonReader.nextName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginObject() throws IOException {
/* 221 */     this.jsonReader.beginObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endObject() throws IOException {
/* 226 */     this.jsonReader.endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginArray() throws IOException {
/* 231 */     this.jsonReader.beginArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endArray() throws IOException {
/* 236 */     this.jsonReader.endArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNext() throws IOException {
/* 241 */     return this.jsonReader.hasNext();
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt() throws IOException {
/* 246 */     return this.jsonReader.nextInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextLong() throws IOException {
/* 251 */     return this.jsonReader.nextLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public String nextString() throws IOException {
/* 256 */     return this.jsonReader.nextString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean nextBoolean() throws IOException {
/* 261 */     return this.jsonReader.nextBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextDouble() throws IOException {
/* 266 */     return this.jsonReader.nextDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextNull() throws IOException {
/* 271 */     this.jsonReader.nextNull();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLenient(boolean lenient) {
/* 276 */     this.jsonReader.setLenient(lenient);
/*     */   }
/*     */ 
/*     */   
/*     */   public void skipValue() throws IOException {
/* 281 */     this.jsonReader.skipValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 286 */     this.jsonReader.close();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\JsonObjectReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */