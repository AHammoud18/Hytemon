/*     */ package io.sentry.util;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Deque;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public final class MapObjectReader
/*     */   implements ObjectReader
/*     */ {
/*     */   private final Deque<Map.Entry<String, Object>> stack;
/*     */   
/*     */   public MapObjectReader(Map<String, Object> root) {
/*  27 */     this.stack = new ArrayDeque<>();
/*  28 */     this.stack.addLast(new AbstractMap.SimpleEntry<>(null, root));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextUnknown(@NotNull ILogger logger, Map<String, Object> unknown, String name) {
/*     */     try {
/*  35 */       unknown.put(name, nextObjectOrNull());
/*  36 */     } catch (Exception exception) {
/*  37 */       logger.log(SentryLevel.ERROR, exception, "Error deserializing unknown key: %s", new Object[] { name });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> List<T> nextListOrNull(@NotNull ILogger logger, @NotNull JsonDeserializer<T> deserializer) throws IOException {
/*  46 */     if (peek() == JsonToken.NULL) {
/*  47 */       nextNull();
/*  48 */       return null;
/*     */     } 
/*     */     try {
/*  51 */       beginArray();
/*  52 */       List<T> list = new ArrayList<>();
/*  53 */       if (hasNext()) {
/*     */         do {
/*     */           try {
/*  56 */             list.add((T)deserializer.deserialize(this, logger));
/*  57 */           } catch (Exception e) {
/*  58 */             logger.log(SentryLevel.WARNING, "Failed to deserialize object in list.", e);
/*     */           } 
/*  60 */         } while (peek() == JsonToken.BEGIN_OBJECT);
/*     */       }
/*  62 */       endArray();
/*  63 */       return list;
/*  64 */     } catch (Exception e) {
/*  65 */       throw new IOException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> Map<String, T> nextMapOrNull(@NotNull ILogger logger, @NotNull JsonDeserializer<T> deserializer) throws IOException {
/*  74 */     if (peek() == JsonToken.NULL) {
/*  75 */       nextNull();
/*  76 */       return null;
/*     */     } 
/*     */     try {
/*  79 */       beginObject();
/*  80 */       Map<String, T> map = new HashMap<>();
/*  81 */       if (hasNext()) {
/*     */         do {
/*     */           try {
/*  84 */             String key = nextName();
/*  85 */             map.put(key, (T)deserializer.deserialize(this, logger));
/*  86 */           } catch (Exception e) {
/*  87 */             logger.log(SentryLevel.WARNING, "Failed to deserialize object in map.", e);
/*     */           } 
/*  89 */         } while (peek() == JsonToken.BEGIN_OBJECT || peek() == JsonToken.NAME);
/*     */       }
/*  91 */       endObject();
/*  92 */       return map;
/*  93 */     } catch (Exception e) {
/*  94 */       throw new IOException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> Map<String, List<T>> nextMapOfListOrNull(@NotNull ILogger logger, @NotNull JsonDeserializer<T> deserializer) throws IOException {
/* 101 */     if (peek() == JsonToken.NULL) {
/* 102 */       nextNull();
/* 103 */       return null;
/*     */     } 
/* 105 */     Map<String, List<T>> result = new HashMap<>();
/*     */     
/*     */     try {
/* 108 */       beginObject();
/* 109 */       if (hasNext()) {
/*     */         do {
/* 111 */           String key = nextName();
/* 112 */           List<T> list = nextListOrNull(logger, deserializer);
/* 113 */           if (list == null)
/* 114 */             continue;  result.put(key, list);
/*     */         }
/* 116 */         while (peek() == JsonToken.BEGIN_OBJECT || peek() == JsonToken.NAME);
/*     */       }
/* 118 */       endObject();
/* 119 */       return result;
/* 120 */     } catch (Exception e) {
/* 121 */       throw new IOException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T nextOrNull(@NotNull ILogger logger, @NotNull JsonDeserializer<T> deserializer) throws Exception {
/* 130 */     return nextValueOrNull(logger, deserializer);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Date nextDateOrNull(@NotNull ILogger logger) throws IOException {
/* 136 */     String dateString = nextStringOrNull();
/* 137 */     return ObjectReader.dateOrNull(dateString, logger);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TimeZone nextTimeZoneOrNull(@NotNull ILogger logger) throws IOException {
/* 143 */     String timeZoneId = nextStringOrNull();
/* 144 */     return (timeZoneId != null) ? TimeZone.getTimeZone(timeZoneId) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Object nextObjectOrNull() throws IOException {
/* 150 */     return nextValueOrNull();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public JsonToken peek() throws IOException {
/* 156 */     if (this.stack.isEmpty()) {
/* 157 */       return JsonToken.END_DOCUMENT;
/*     */     }
/*     */     
/* 160 */     Map.Entry<String, Object> currentEntry = this.stack.peekLast();
/* 161 */     if (currentEntry == null) {
/* 162 */       return JsonToken.END_DOCUMENT;
/*     */     }
/*     */     
/* 165 */     if (currentEntry.getKey() != null) {
/* 166 */       return JsonToken.NAME;
/*     */     }
/*     */     
/* 169 */     Object value = currentEntry.getValue();
/*     */     
/* 171 */     if (value instanceof Map)
/* 172 */       return JsonToken.BEGIN_OBJECT; 
/* 173 */     if (value instanceof List)
/* 174 */       return JsonToken.BEGIN_ARRAY; 
/* 175 */     if (value instanceof String)
/* 176 */       return JsonToken.STRING; 
/* 177 */     if (value instanceof Number)
/* 178 */       return JsonToken.NUMBER; 
/* 179 */     if (value instanceof Boolean)
/* 180 */       return JsonToken.BOOLEAN; 
/* 181 */     if (value instanceof JsonToken) {
/* 182 */       return (JsonToken)value;
/*     */     }
/* 184 */     return JsonToken.END_DOCUMENT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String nextName() throws IOException {
/* 191 */     Map.Entry<String, Object> currentEntry = this.stack.peekLast();
/* 192 */     if (currentEntry != null && currentEntry.getKey() != null) {
/* 193 */       return currentEntry.getKey();
/*     */     }
/* 195 */     throw new IOException("Expected a name but was " + peek());
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginObject() throws IOException {
/* 200 */     Map.Entry<String, Object> currentEntry = this.stack.removeLast();
/* 201 */     if (currentEntry == null) {
/* 202 */       throw new IOException("No more entries");
/*     */     }
/* 204 */     Object value = currentEntry.getValue();
/* 205 */     if (value instanceof Map) {
/*     */       
/* 207 */       this.stack.addLast(new AbstractMap.SimpleEntry<>(null, JsonToken.END_OBJECT));
/*     */       
/* 209 */       for (Map.Entry<String, Object> entry : (Iterable<Map.Entry<String, Object>>)((Map)value).entrySet()) {
/* 210 */         this.stack.addLast(entry);
/*     */       }
/*     */     } else {
/* 213 */       throw new IOException("Current token is not an object");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void endObject() throws IOException {
/* 219 */     if (this.stack.size() > 1) {
/* 220 */       this.stack.removeLast();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginArray() throws IOException {
/* 226 */     Map.Entry<String, Object> currentEntry = this.stack.removeLast();
/* 227 */     if (currentEntry == null) {
/* 228 */       throw new IOException("No more entries");
/*     */     }
/* 230 */     Object value = currentEntry.getValue();
/* 231 */     if (value instanceof List) {
/*     */       
/* 233 */       this.stack.addLast(new AbstractMap.SimpleEntry<>(null, JsonToken.END_ARRAY));
/*     */       
/* 235 */       for (int i = ((List)value).size() - 1; i >= 0; i--) {
/* 236 */         Object entry = ((List)value).get(i);
/* 237 */         this.stack.addLast(new AbstractMap.SimpleEntry<>(null, entry));
/*     */       } 
/*     */     } else {
/* 240 */       throw new IOException("Current token is not an object");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void endArray() throws IOException {
/* 246 */     if (this.stack.size() > 1) {
/* 247 */       this.stack.removeLast();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNext() throws IOException {
/* 253 */     return !this.stack.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt() throws IOException {
/* 258 */     Object value = nextValueOrNull();
/* 259 */     if (value instanceof Number) {
/* 260 */       return ((Number)value).intValue();
/*     */     }
/* 262 */     throw new IOException("Expected int");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer nextIntegerOrNull() throws IOException {
/* 269 */     Object value = nextValueOrNull();
/* 270 */     if (value instanceof Number) {
/* 271 */       return Integer.valueOf(((Number)value).intValue());
/*     */     }
/* 273 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextLong() throws IOException {
/* 278 */     Object value = nextValueOrNull();
/* 279 */     if (value instanceof Number) {
/* 280 */       return ((Number)value).longValue();
/*     */     }
/* 282 */     throw new IOException("Expected long");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Long nextLongOrNull() throws IOException {
/* 289 */     Object value = nextValueOrNull();
/* 290 */     if (value instanceof Number) {
/* 291 */       return Long.valueOf(((Number)value).longValue());
/*     */     }
/* 293 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String nextString() throws IOException {
/* 298 */     String value = nextValueOrNull();
/* 299 */     if (value != null) {
/* 300 */       return value;
/*     */     }
/* 302 */     throw new IOException("Expected string");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String nextStringOrNull() throws IOException {
/* 309 */     return nextValueOrNull();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean nextBoolean() throws IOException {
/* 314 */     Boolean value = nextValueOrNull();
/* 315 */     if (value != null) {
/* 316 */       return value.booleanValue();
/*     */     }
/* 318 */     throw new IOException("Expected boolean");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Boolean nextBooleanOrNull() throws IOException {
/* 325 */     return nextValueOrNull();
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextDouble() throws IOException {
/* 330 */     Object value = nextValueOrNull();
/* 331 */     if (value instanceof Number) {
/* 332 */       return ((Number)value).doubleValue();
/*     */     }
/* 334 */     throw new IOException("Expected double");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Double nextDoubleOrNull() throws IOException {
/* 341 */     Object value = nextValueOrNull();
/* 342 */     if (value instanceof Number) {
/* 343 */       return Double.valueOf(((Number)value).doubleValue());
/*     */     }
/* 345 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Float nextFloatOrNull() throws IOException {
/* 351 */     Object value = nextValueOrNull();
/* 352 */     if (value instanceof Number) {
/* 353 */       return Float.valueOf(((Number)value).floatValue());
/*     */     }
/* 355 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public float nextFloat() throws IOException {
/* 360 */     Object value = nextValueOrNull();
/* 361 */     if (value instanceof Number) {
/* 362 */       return ((Number)value).floatValue();
/*     */     }
/* 364 */     throw new IOException("Expected float");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextNull() throws IOException {
/* 370 */     Object value = nextValueOrNull();
/* 371 */     if (value != null) {
/* 372 */       throw new IOException("Expected null but was " + peek());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLenient(boolean lenient) {}
/*     */ 
/*     */   
/*     */   public void skipValue() throws IOException {}
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private <T> T nextValueOrNull() throws IOException {
/*     */     try {
/* 386 */       return nextValueOrNull(null, null);
/* 387 */     } catch (Exception e) {
/* 388 */       throw new IOException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private <T> T nextValueOrNull(@Nullable ILogger logger, @Nullable JsonDeserializer<T> deserializer) throws Exception {
/* 397 */     Map.Entry<String, Object> currentEntry = this.stack.peekLast();
/* 398 */     if (currentEntry == null) {
/* 399 */       return null;
/*     */     }
/* 401 */     T value = (T)currentEntry.getValue();
/* 402 */     if (deserializer != null && logger != null) {
/* 403 */       return (T)deserializer.deserialize(this, logger);
/*     */     }
/* 405 */     this.stack.removeLast();
/* 406 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 411 */     this.stack.clear();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\MapObjectReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */