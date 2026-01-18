/*     */ package io.sentry.util;
/*     */ 
/*     */ import io.sentry.DateUtils;
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryLevel;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicIntegerArray;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*     */ @Internal
/*     */ public final class MapObjectWriter
/*     */   implements ObjectWriter
/*     */ {
/*     */   @NotNull
/*     */   final Map<String, Object> root;
/*     */   @NotNull
/*     */   final ArrayDeque<Object> stack;
/*     */   
/*     */   public MapObjectWriter(@NotNull Map<String, Object> root) {
/*  64 */     this.root = root;
/*  65 */     this.stack = new ArrayDeque();
/*  66 */     this.stack.addLast(root);
/*     */   }
/*     */ 
/*     */   
/*     */   public MapObjectWriter name(@NotNull String name) throws IOException {
/*  71 */     this.stack.add(name);
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MapObjectWriter value(@NotNull ILogger logger, @Nullable Object object) throws IOException {
/*  78 */     if (object == null) {
/*  79 */       nullValue();
/*  80 */     } else if (object instanceof Character) {
/*  81 */       value(Character.toString(((Character)object).charValue()));
/*  82 */     } else if (object instanceof String) {
/*  83 */       value((String)object);
/*  84 */     } else if (object instanceof Boolean) {
/*  85 */       value(((Boolean)object).booleanValue());
/*  86 */     } else if (object instanceof Number) {
/*  87 */       value((Number)object);
/*  88 */     } else if (object instanceof Date) {
/*  89 */       serializeDate(logger, (Date)object);
/*  90 */     } else if (object instanceof TimeZone) {
/*  91 */       serializeTimeZone(logger, (TimeZone)object);
/*  92 */     } else if (object instanceof JsonSerializable) {
/*  93 */       ((JsonSerializable)object).serialize(this, logger);
/*  94 */     } else if (object instanceof Collection) {
/*  95 */       serializeCollection(logger, (Collection)object);
/*  96 */     } else if (object.getClass().isArray()) {
/*  97 */       serializeCollection(logger, Arrays.asList((Object[])object));
/*  98 */     } else if (object instanceof Map) {
/*  99 */       serializeMap(logger, (Map<?, ?>)object);
/* 100 */     } else if (object instanceof java.util.Locale) {
/* 101 */       value(object.toString());
/* 102 */     } else if (object instanceof AtomicIntegerArray) {
/* 103 */       serializeCollection(logger, JsonSerializationUtils.atomicIntegerArrayToList((AtomicIntegerArray)object));
/* 104 */     } else if (object instanceof AtomicBoolean) {
/* 105 */       value(((AtomicBoolean)object).get());
/* 106 */     } else if (object instanceof java.net.URI) {
/* 107 */       value(object.toString());
/* 108 */     } else if (object instanceof java.net.InetAddress) {
/* 109 */       value(object.toString());
/* 110 */     } else if (object instanceof java.util.UUID) {
/* 111 */       value(object.toString());
/* 112 */     } else if (object instanceof java.util.Currency) {
/* 113 */       value(object.toString());
/* 114 */     } else if (object instanceof Calendar) {
/* 115 */       serializeMap(logger, JsonSerializationUtils.calendarToMap((Calendar)object));
/* 116 */     } else if (object.getClass().isEnum()) {
/* 117 */       value(object.toString());
/*     */     } else {
/* 119 */       logger.log(SentryLevel.WARNING, "Failed serializing unknown object.", new Object[] { object });
/*     */     } 
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLenient(boolean lenient) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndent(@Nullable String indent) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getIndent() {
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapObjectWriter beginArray() throws IOException {
/* 141 */     this.stack.add(new ArrayList());
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapObjectWriter endArray() throws IOException {
/* 147 */     endObject();
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapObjectWriter beginObject() throws IOException {
/* 153 */     this.stack.addLast(new HashMap<>());
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapObjectWriter endObject() throws IOException {
/* 159 */     Object value = this.stack.removeLast();
/* 160 */     postValue(value);
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapObjectWriter value(@Nullable String value) throws IOException {
/* 166 */     postValue(value);
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectWriter jsonValue(@Nullable String value) throws IOException {
/* 173 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapObjectWriter nullValue() throws IOException {
/* 178 */     postValue(null);
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapObjectWriter value(boolean value) throws IOException {
/* 184 */     postValue(Boolean.valueOf(value));
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapObjectWriter value(@Nullable Boolean value) throws IOException {
/* 190 */     postValue(value);
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapObjectWriter value(double value) throws IOException {
/* 196 */     postValue(Double.valueOf(value));
/* 197 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapObjectWriter value(long value) throws IOException {
/* 202 */     postValue(Long.valueOf(value));
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public MapObjectWriter value(@Nullable Number value) throws IOException {
/* 208 */     postValue(value);
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializeDate(@NotNull ILogger logger, @NotNull Date date) throws IOException {
/*     */     try {
/* 215 */       value(DateUtils.getTimestamp(date));
/* 216 */     } catch (Exception e) {
/* 217 */       logger.log(SentryLevel.ERROR, "Error when serializing Date", e);
/* 218 */       nullValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializeTimeZone(@NotNull ILogger logger, @NotNull TimeZone timeZone) throws IOException {
/*     */     try {
/* 225 */       value(timeZone.getID());
/* 226 */     } catch (Exception e) {
/* 227 */       logger.log(SentryLevel.ERROR, "Error when serializing TimeZone", e);
/* 228 */       nullValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializeCollection(@NotNull ILogger logger, @NotNull Collection<?> collection) throws IOException {
/* 234 */     beginArray();
/* 235 */     for (Object object : collection) {
/* 236 */       value(logger, object);
/*     */     }
/* 238 */     endArray();
/*     */   }
/*     */ 
/*     */   
/*     */   private void serializeMap(@NotNull ILogger logger, @NotNull Map<?, ?> map) throws IOException {
/* 243 */     beginObject();
/* 244 */     for (Object key : map.keySet()) {
/* 245 */       if (key instanceof String) {
/* 246 */         name((String)key);
/* 247 */         value(logger, map.get(key));
/*     */       } 
/*     */     } 
/* 250 */     endObject();
/*     */   }
/*     */ 
/*     */   
/*     */   private void postValue(@Nullable Object value) {
/* 255 */     Object topStackElement = this.stack.peekLast();
/* 256 */     if (topStackElement instanceof List) {
/*     */       
/* 258 */       ((List<Object>)topStackElement).add(value);
/* 259 */     } else if (topStackElement instanceof String) {
/*     */ 
/*     */       
/* 262 */       String key = (String)this.stack.removeLast();
/* 263 */       peekObject().put(key, value);
/*     */     } else {
/* 265 */       throw new IllegalStateException("Invalid stack state, expected array or string on top");
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private Map<String, Object> peekObject() {
/* 271 */     Object item = this.stack.peekLast();
/* 272 */     if (item == null)
/* 273 */       throw new IllegalStateException("Stack is empty."); 
/* 274 */     if (item instanceof Map) {
/* 275 */       return (Map<String, Object>)item;
/*     */     }
/* 277 */     throw new IllegalStateException("Stack element is not a Map.");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\MapObjectWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */