/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.util.JsonSerializationUtils;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ @Internal
/*     */ public final class JsonReflectionObjectSerializer
/*     */ {
/*  33 */   private final Set<Object> visiting = new HashSet();
/*     */   private final int maxDepth;
/*     */   
/*     */   JsonReflectionObjectSerializer(int maxDepth) {
/*  37 */     this.maxDepth = maxDepth;
/*     */   }
/*     */   @Nullable
/*     */   public Object serialize(@Nullable Object object, @NotNull ILogger logger) throws Exception {
/*     */     Map<String, Object> map;
/*  42 */     if (object == null) {
/*  43 */       return null;
/*     */     }
/*  45 */     if (object instanceof Character)
/*  46 */       return object.toString(); 
/*  47 */     if (object instanceof Number)
/*  48 */       return object; 
/*  49 */     if (object instanceof Boolean)
/*  50 */       return object; 
/*  51 */     if (object instanceof String)
/*  52 */       return object; 
/*  53 */     if (object instanceof java.util.Locale)
/*  54 */       return object.toString(); 
/*  55 */     if (object instanceof AtomicIntegerArray)
/*  56 */       return JsonSerializationUtils.atomicIntegerArrayToList((AtomicIntegerArray)object); 
/*  57 */     if (object instanceof AtomicBoolean)
/*  58 */       return Boolean.valueOf(((AtomicBoolean)object).get()); 
/*  59 */     if (object instanceof java.net.URI)
/*  60 */       return object.toString(); 
/*  61 */     if (object instanceof java.net.InetAddress)
/*  62 */       return object.toString(); 
/*  63 */     if (object instanceof java.util.UUID)
/*  64 */       return object.toString(); 
/*  65 */     if (object instanceof java.util.Currency)
/*  66 */       return object.toString(); 
/*  67 */     if (object instanceof Calendar)
/*  68 */       return JsonSerializationUtils.calendarToMap((Calendar)object); 
/*  69 */     if (object.getClass().isEnum()) {
/*  70 */       return object.toString();
/*     */     }
/*  72 */     if (this.visiting.contains(object)) {
/*  73 */       logger.log(SentryLevel.INFO, "Cyclic reference detected. Calling toString() on object.", new Object[0]);
/*  74 */       return object.toString();
/*     */     } 
/*  76 */     this.visiting.add(object);
/*     */     
/*  78 */     if (this.visiting.size() > this.maxDepth) {
/*  79 */       this.visiting.remove(object);
/*  80 */       logger.log(SentryLevel.INFO, "Max depth exceeded. Calling toString() on object.", new Object[0]);
/*  81 */       return object.toString();
/*     */     } 
/*     */     
/*  84 */     Object<Object> serializedObject = null;
/*     */     try {
/*  86 */       if (object.getClass().isArray()) {
/*  87 */         serializedObject = (Object<Object>)list((Object[])object, logger);
/*  88 */       } else if (object instanceof Collection) {
/*  89 */         serializedObject = (Object<Object>)list((Collection)object, logger);
/*  90 */       } else if (object instanceof Map) {
/*  91 */         serializedObject = (Object)map((Map<?, ?>)object, logger);
/*     */       } else {
/*  93 */         Map<String, Object> objectAsMap = serializeObject(object, logger);
/*  94 */         if (objectAsMap.isEmpty()) {
/*  95 */           serializedObject = (Object<Object>)object.toString();
/*     */         } else {
/*  97 */           map = objectAsMap;
/*     */         } 
/*     */       } 
/* 100 */     } catch (Exception exception) {
/* 101 */       logger.log(SentryLevel.INFO, "Not serializing object due to throwing sub-path.", exception);
/*     */     } finally {
/* 103 */       this.visiting.remove(object);
/*     */     } 
/* 105 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Map<String, Object> serializeObject(@NotNull Object object, @NotNull ILogger logger) throws Exception {
/* 111 */     Field[] fields = object.getClass().getDeclaredFields();
/*     */     
/* 113 */     Map<String, Object> map = new HashMap<>();
/*     */     
/* 115 */     for (Field field : fields) {
/* 116 */       if (!Modifier.isTransient(field.getModifiers()))
/*     */       {
/*     */         
/* 119 */         if (!Modifier.isStatic(field.getModifiers())) {
/*     */ 
/*     */           
/* 122 */           String fieldName = field.getName();
/*     */           try {
/* 124 */             field.setAccessible(true);
/* 125 */             Object fieldObject = field.get(object);
/* 126 */             map.put(fieldName, serialize(fieldObject, logger));
/* 127 */             field.setAccessible(false);
/* 128 */           } catch (Exception exception) {
/* 129 */             logger.log(SentryLevel.INFO, "Cannot access field " + fieldName + ".", new Object[0]);
/*     */           } 
/*     */         }  } 
/*     */     } 
/* 133 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private List<Object> list(@NotNull Object[] objectArray, @NotNull ILogger logger) throws Exception {
/* 140 */     List<Object> list = new ArrayList();
/* 141 */     for (Object object : objectArray) {
/* 142 */       list.add(serialize(object, logger));
/*     */     }
/* 144 */     return list;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private List<Object> list(@NotNull Collection<?> collection, @NotNull ILogger logger) throws Exception {
/* 149 */     List<Object> list = new ArrayList();
/* 150 */     for (Object object : collection) {
/* 151 */       list.add(serialize(object, logger));
/*     */     }
/* 153 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private Map<String, Object> map(@NotNull Map<?, ?> map, @NotNull ILogger logger) throws Exception {
/* 159 */     Map<String, Object> hashMap = new HashMap<>();
/* 160 */     for (Object key : map.keySet()) {
/* 161 */       Object object = map.get(key);
/* 162 */       if (object != null) {
/* 163 */         hashMap.put(key.toString(), serialize(object, logger)); continue;
/*     */       } 
/* 165 */       hashMap.put(key.toString(), null);
/*     */     } 
/*     */     
/* 168 */     return hashMap;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\JsonReflectionObjectSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */