/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.util.JsonSerializationUtils;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
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
/*     */ @Internal
/*     */ public final class JsonObjectSerializer
/*     */ {
/*     */   public static final String OBJECT_PLACEHOLDER = "[OBJECT]";
/*     */   public final JsonReflectionObjectSerializer jsonReflectionObjectSerializer;
/*     */   
/*     */   public JsonObjectSerializer(int maxDepth) {
/*  34 */     this.jsonReflectionObjectSerializer = new JsonReflectionObjectSerializer(maxDepth);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger, @Nullable Object object) throws IOException {
/*  40 */     if (object == null) {
/*  41 */       writer.nullValue();
/*  42 */     } else if (object instanceof Character) {
/*  43 */       writer.value(Character.toString(((Character)object).charValue()));
/*  44 */     } else if (object instanceof String) {
/*  45 */       writer.value((String)object);
/*  46 */     } else if (object instanceof Boolean) {
/*  47 */       writer.value(((Boolean)object).booleanValue());
/*  48 */     } else if (object instanceof Number) {
/*  49 */       writer.value((Number)object);
/*  50 */     } else if (object instanceof Date) {
/*  51 */       serializeDate(writer, logger, (Date)object);
/*  52 */     } else if (object instanceof TimeZone) {
/*  53 */       serializeTimeZone(writer, logger, (TimeZone)object);
/*  54 */     } else if (object instanceof JsonSerializable) {
/*  55 */       ((JsonSerializable)object).serialize(writer, logger);
/*  56 */     } else if (object instanceof Collection) {
/*  57 */       serializeCollection(writer, logger, (Collection)object);
/*  58 */     } else if (object instanceof boolean[]) {
/*  59 */       List<Boolean> bools = new ArrayList<>(((boolean[])object).length);
/*  60 */       for (boolean b : (boolean[])object) {
/*  61 */         bools.add(Boolean.valueOf(b));
/*     */       }
/*  63 */       serializeCollection(writer, logger, bools);
/*  64 */     } else if (object instanceof byte[]) {
/*  65 */       List<Byte> bytes = new ArrayList<>(((byte[])object).length);
/*  66 */       for (byte b : (byte[])object) {
/*  67 */         bytes.add(Byte.valueOf(b));
/*     */       }
/*  69 */       serializeCollection(writer, logger, bytes);
/*  70 */     } else if (object instanceof short[]) {
/*  71 */       List<Short> shorts = new ArrayList<>(((short[])object).length);
/*  72 */       for (short s : (short[])object) {
/*  73 */         shorts.add(Short.valueOf(s));
/*     */       }
/*  75 */       serializeCollection(writer, logger, shorts);
/*  76 */     } else if (object instanceof char[]) {
/*  77 */       List<Character> chars = new ArrayList<>(((char[])object).length);
/*  78 */       for (char s : (char[])object) {
/*  79 */         chars.add(Character.valueOf(s));
/*     */       }
/*  81 */       serializeCollection(writer, logger, chars);
/*  82 */     } else if (object instanceof int[]) {
/*  83 */       List<Integer> ints = new ArrayList<>(((int[])object).length);
/*  84 */       for (int i : (int[])object) {
/*  85 */         ints.add(Integer.valueOf(i));
/*     */       }
/*  87 */       serializeCollection(writer, logger, ints);
/*  88 */     } else if (object instanceof long[]) {
/*  89 */       List<Long> longs = new ArrayList<>(((long[])object).length);
/*  90 */       for (long l : (long[])object) {
/*  91 */         longs.add(Long.valueOf(l));
/*     */       }
/*  93 */       serializeCollection(writer, logger, longs);
/*  94 */     } else if (object instanceof float[]) {
/*  95 */       List<Float> floats = new ArrayList<>(((float[])object).length);
/*  96 */       for (float f : (float[])object) {
/*  97 */         floats.add(Float.valueOf(f));
/*     */       }
/*  99 */       serializeCollection(writer, logger, floats);
/* 100 */     } else if (object instanceof double[]) {
/* 101 */       List<Double> doubles = new ArrayList<>(((double[])object).length);
/* 102 */       for (double d : (double[])object) {
/* 103 */         doubles.add(Double.valueOf(d));
/*     */       }
/* 105 */       serializeCollection(writer, logger, doubles);
/* 106 */     } else if (object.getClass().isArray()) {
/* 107 */       serializeCollection(writer, logger, Arrays.asList((Object[])object));
/* 108 */     } else if (object instanceof Map) {
/* 109 */       serializeMap(writer, logger, (Map<?, ?>)object);
/* 110 */     } else if (object instanceof java.util.Locale) {
/* 111 */       writer.value(object.toString());
/* 112 */     } else if (object instanceof AtomicIntegerArray) {
/* 113 */       serializeCollection(writer, logger, JsonSerializationUtils.atomicIntegerArrayToList((AtomicIntegerArray)object));
/* 114 */     } else if (object instanceof AtomicBoolean) {
/* 115 */       writer.value(((AtomicBoolean)object).get());
/* 116 */     } else if (object instanceof java.net.URI) {
/* 117 */       writer.value(object.toString());
/* 118 */     } else if (object instanceof java.net.InetAddress) {
/* 119 */       writer.value(object.toString());
/* 120 */     } else if (object instanceof java.util.UUID) {
/* 121 */       writer.value(object.toString());
/* 122 */     } else if (object instanceof java.util.Currency) {
/* 123 */       writer.value(object.toString());
/* 124 */     } else if (object instanceof Calendar) {
/* 125 */       serializeMap(writer, logger, JsonSerializationUtils.calendarToMap((Calendar)object));
/* 126 */     } else if (object.getClass().isEnum()) {
/* 127 */       writer.value(object.toString());
/*     */     } else {
/*     */       try {
/* 130 */         Object serializableObject = this.jsonReflectionObjectSerializer.serialize(object, logger);
/* 131 */         serialize(writer, logger, serializableObject);
/* 132 */       } catch (Exception exception) {
/* 133 */         logger.log(SentryLevel.ERROR, "Failed serializing unknown object.", exception);
/* 134 */         writer.value("[OBJECT]");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void serializeDate(@NotNull ObjectWriter writer, @NotNull ILogger logger, @NotNull Date date) throws IOException {
/*     */     try {
/* 145 */       writer.value(DateUtils.getTimestamp(date));
/* 146 */     } catch (Exception e) {
/* 147 */       logger.log(SentryLevel.ERROR, "Error when serializing Date", e);
/* 148 */       writer.nullValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void serializeTimeZone(@NotNull ObjectWriter writer, @NotNull ILogger logger, @NotNull TimeZone timeZone) throws IOException {
/*     */     try {
/* 156 */       writer.value(timeZone.getID());
/* 157 */     } catch (Exception e) {
/* 158 */       logger.log(SentryLevel.ERROR, "Error when serializing TimeZone", e);
/* 159 */       writer.nullValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void serializeCollection(@NotNull ObjectWriter writer, @NotNull ILogger logger, @NotNull Collection<?> collection) throws IOException {
/* 166 */     writer.beginArray();
/* 167 */     for (Object object : collection) {
/* 168 */       serialize(writer, logger, object);
/*     */     }
/* 170 */     writer.endArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void serializeMap(@NotNull ObjectWriter writer, @NotNull ILogger logger, @NotNull Map<?, ?> map) throws IOException {
/* 176 */     writer.beginObject();
/* 177 */     for (Object key : map.keySet()) {
/* 178 */       if (key instanceof String) {
/* 179 */         writer.name((String)key);
/* 180 */         serialize(writer, logger, map.get(key));
/*     */       } 
/*     */     } 
/* 183 */     writer.endObject();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\JsonObjectSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */