/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.util.CollectionUtils;
/*     */ import io.sentry.util.HttpUtils;
/*     */ import io.sentry.util.Objects;
/*     */ import io.sentry.util.UrlUtils;
/*     */ import io.sentry.vendor.gson.stream.JsonToken;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Breadcrumb
/*     */   implements JsonUnknown, JsonSerializable, Comparable<Breadcrumb>
/*     */ {
/*     */   @Nullable
/*     */   private final Long timestampMs;
/*     */   @Nullable
/*     */   private Date timestamp;
/*     */   @NotNull
/*     */   private final Long nanos;
/*     */   @Nullable
/*     */   private String message;
/*     */   @Nullable
/*     */   private String type;
/*     */   @NotNull
/*  38 */   private Map<String, Object> data = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String category;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String origin;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private SentryLevel level;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */ 
/*     */ 
/*     */   
/*     */   public Breadcrumb(@NotNull Date timestamp) {
/*  62 */     this.nanos = Long.valueOf(System.nanoTime());
/*  63 */     this.timestamp = timestamp;
/*  64 */     this.timestampMs = null;
/*     */   }
/*     */   
/*     */   public Breadcrumb(long timestamp) {
/*  68 */     this.nanos = Long.valueOf(System.nanoTime());
/*  69 */     this.timestampMs = Long.valueOf(timestamp);
/*  70 */     this.timestamp = null;
/*     */   }
/*     */   
/*     */   Breadcrumb(@NotNull Breadcrumb breadcrumb) {
/*  74 */     this.nanos = Long.valueOf(System.nanoTime());
/*  75 */     this.timestamp = breadcrumb.timestamp;
/*  76 */     this.timestampMs = breadcrumb.timestampMs;
/*  77 */     this.message = breadcrumb.message;
/*  78 */     this.type = breadcrumb.type;
/*  79 */     this.category = breadcrumb.category;
/*  80 */     this.origin = breadcrumb.origin;
/*  81 */     Map<String, Object> dataClone = CollectionUtils.newConcurrentHashMap(breadcrumb.data);
/*  82 */     if (dataClone != null) {
/*  83 */       this.data = dataClone;
/*     */     }
/*  85 */     this.unknown = CollectionUtils.newConcurrentHashMap(breadcrumb.unknown);
/*  86 */     this.level = breadcrumb.level;
/*     */   }
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
/*     */   public static Breadcrumb fromMap(@NotNull Map<String, Object> map, @NotNull SentryOptions options) {
/* 100 */     Date timestamp = DateUtils.getCurrentDateTime();
/* 101 */     String message = null;
/* 102 */     String type = null;
/* 103 */     Map<String, Object> data = new ConcurrentHashMap<>();
/* 104 */     String category = null;
/* 105 */     String origin = null;
/* 106 */     SentryLevel level = null;
/* 107 */     Map<String, Object> unknown = null;
/*     */     
/* 109 */     for (Map.Entry<String, Object> entry : map.entrySet()) {
/* 110 */       Map<Object, Object> untypedData; String levelString; Object value = entry.getValue();
/* 111 */       switch ((String)entry.getKey()) {
/*     */         case "timestamp":
/* 113 */           if (value instanceof String) {
/* 114 */             Date deserializedDate = ObjectReader.dateOrNull((String)value, options.getLogger());
/* 115 */             if (deserializedDate != null) {
/* 116 */               timestamp = deserializedDate;
/*     */             }
/*     */           } 
/*     */           continue;
/*     */         case "message":
/* 121 */           message = (value instanceof String) ? (String)value : null;
/*     */           continue;
/*     */         case "type":
/* 124 */           type = (value instanceof String) ? (String)value : null;
/*     */           continue;
/*     */         
/*     */         case "data":
/* 128 */           untypedData = (value instanceof Map) ? (Map<Object, Object>)value : null;
/* 129 */           if (untypedData != null) {
/* 130 */             for (Map.Entry<Object, Object> dataEntry : untypedData.entrySet()) {
/* 131 */               if (dataEntry.getKey() instanceof String && dataEntry.getValue() != null) {
/* 132 */                 data.put((String)dataEntry.getKey(), dataEntry.getValue()); continue;
/*     */               } 
/* 134 */               options
/* 135 */                 .getLogger()
/* 136 */                 .log(SentryLevel.WARNING, "Invalid key or null value in data map.", new Object[0]);
/*     */             } 
/*     */           }
/*     */           continue;
/*     */         
/*     */         case "category":
/* 142 */           category = (value instanceof String) ? (String)value : null;
/*     */           continue;
/*     */         case "origin":
/* 145 */           origin = (value instanceof String) ? (String)value : null;
/*     */           continue;
/*     */         case "level":
/* 148 */           levelString = (value instanceof String) ? (String)value : null;
/* 149 */           if (levelString != null) {
/*     */             try {
/* 151 */               level = SentryLevel.valueOf(levelString.toUpperCase(Locale.ROOT));
/* 152 */             } catch (Exception exception) {}
/*     */           }
/*     */           continue;
/*     */       } 
/*     */ 
/*     */       
/* 158 */       if (unknown == null) {
/* 159 */         unknown = new ConcurrentHashMap<>();
/*     */       }
/* 161 */       unknown.put(entry.getKey(), entry.getValue());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 166 */     Breadcrumb breadcrumb = new Breadcrumb(timestamp);
/* 167 */     breadcrumb.message = message;
/* 168 */     breadcrumb.type = type;
/* 169 */     breadcrumb.data = data;
/* 170 */     breadcrumb.category = category;
/* 171 */     breadcrumb.origin = origin;
/* 172 */     breadcrumb.level = level;
/*     */     
/* 174 */     breadcrumb.setUnknown(unknown);
/* 175 */     return breadcrumb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Breadcrumb http(@NotNull String url, @NotNull String method) {
/* 186 */     Breadcrumb breadcrumb = new Breadcrumb();
/* 187 */     UrlUtils.UrlDetails urlDetails = UrlUtils.parse(url);
/* 188 */     breadcrumb.setType("http");
/* 189 */     breadcrumb.setCategory("http");
/* 190 */     if (urlDetails.getUrl() != null) {
/* 191 */       breadcrumb.setData("url", urlDetails.getUrl());
/*     */     }
/* 193 */     breadcrumb.setData("method", method.toUpperCase(Locale.ROOT));
/* 194 */     if (urlDetails.getQuery() != null) {
/* 195 */       breadcrumb.setData("http.query", urlDetails.getQuery());
/*     */     }
/* 197 */     if (urlDetails.getFragment() != null) {
/* 198 */       breadcrumb.setData("http.fragment", urlDetails.getFragment());
/*     */     }
/* 200 */     return breadcrumb;
/*     */   }
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
/*     */   @NotNull
/*     */   public static Breadcrumb http(@NotNull String url, @NotNull String method, @Nullable Integer code) {
/* 214 */     Breadcrumb breadcrumb = http(url, method);
/* 215 */     if (code != null) {
/* 216 */       breadcrumb.setData("status_code", code);
/* 217 */       breadcrumb.setLevel(levelFromHttpStatusCode(code));
/*     */     } 
/* 219 */     return breadcrumb;
/*     */   }
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
/*     */   @NotNull
/*     */   public static Breadcrumb graphqlOperation(@Nullable String operationName, @Nullable String operationType, @Nullable String operationId) {
/* 234 */     Breadcrumb breadcrumb = new Breadcrumb();
/*     */     
/* 236 */     breadcrumb.setType("graphql");
/*     */     
/* 238 */     if (operationName != null) {
/* 239 */       breadcrumb.setData("operation_name", operationName);
/*     */     }
/* 241 */     if (operationType != null) {
/* 242 */       breadcrumb.setData("operation_type", operationType);
/* 243 */       breadcrumb.setCategory(operationType);
/*     */     } else {
/* 245 */       breadcrumb.setCategory("graphql.operation");
/*     */     } 
/* 247 */     if (operationId != null) {
/* 248 */       breadcrumb.setData("operation_id", operationId);
/*     */     }
/*     */     
/* 251 */     return breadcrumb;
/*     */   }
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
/*     */   @NotNull
/*     */   public static Breadcrumb graphqlDataFetcher(@Nullable String path, @Nullable String field, @Nullable String type, @Nullable String objectType) {
/* 268 */     Breadcrumb breadcrumb = new Breadcrumb();
/*     */     
/* 270 */     breadcrumb.setType("graphql");
/* 271 */     breadcrumb.setCategory("graphql.fetcher");
/*     */     
/* 273 */     if (path != null) {
/* 274 */       breadcrumb.setData("path", path);
/*     */     }
/* 276 */     if (field != null) {
/* 277 */       breadcrumb.setData("field", field);
/*     */     }
/* 279 */     if (type != null) {
/* 280 */       breadcrumb.setData("type", type);
/*     */     }
/* 282 */     if (objectType != null) {
/* 283 */       breadcrumb.setData("object_type", objectType);
/*     */     }
/*     */     
/* 286 */     return breadcrumb;
/*     */   }
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
/*     */   @NotNull
/*     */   public static Breadcrumb graphqlDataLoader(@NotNull Iterable<?> keys, @Nullable Class<?> keyType, @Nullable Class<?> valueType, @Nullable String name) {
/* 303 */     Breadcrumb breadcrumb = new Breadcrumb();
/*     */     
/* 305 */     breadcrumb.setType("graphql");
/* 306 */     breadcrumb.setCategory("graphql.data_loader");
/*     */     
/* 308 */     List<String> serializedKeys = new ArrayList<>();
/* 309 */     for (Object key : keys) {
/* 310 */       serializedKeys.add(key.toString());
/*     */     }
/* 312 */     breadcrumb.setData("keys", serializedKeys);
/*     */     
/* 314 */     if (keyType != null) {
/* 315 */       breadcrumb.setData("key_type", keyType.getName());
/*     */     }
/*     */     
/* 318 */     if (valueType != null) {
/* 319 */       breadcrumb.setData("value_type", valueType.getName());
/*     */     }
/*     */     
/* 322 */     if (name != null) {
/* 323 */       breadcrumb.setData("name", name);
/*     */     }
/*     */     
/* 326 */     return breadcrumb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Breadcrumb navigation(@NotNull String from, @NotNull String to) {
/* 339 */     Breadcrumb breadcrumb = new Breadcrumb();
/* 340 */     breadcrumb.setCategory("navigation");
/* 341 */     breadcrumb.setType("navigation");
/* 342 */     breadcrumb.setData("from", from);
/* 343 */     breadcrumb.setData("to", to);
/* 344 */     return breadcrumb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Breadcrumb transaction(@NotNull String message) {
/* 354 */     Breadcrumb breadcrumb = new Breadcrumb();
/* 355 */     breadcrumb.setType("default");
/* 356 */     breadcrumb.setCategory("sentry.transaction");
/* 357 */     breadcrumb.setMessage(message);
/* 358 */     return breadcrumb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Breadcrumb debug(@NotNull String message) {
/* 369 */     Breadcrumb breadcrumb = new Breadcrumb();
/* 370 */     breadcrumb.setType("debug");
/* 371 */     breadcrumb.setMessage(message);
/* 372 */     breadcrumb.setLevel(SentryLevel.DEBUG);
/* 373 */     return breadcrumb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Breadcrumb error(@NotNull String message) {
/* 383 */     Breadcrumb breadcrumb = new Breadcrumb();
/* 384 */     breadcrumb.setType("error");
/* 385 */     breadcrumb.setMessage(message);
/* 386 */     breadcrumb.setLevel(SentryLevel.ERROR);
/* 387 */     return breadcrumb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Breadcrumb info(@NotNull String message) {
/* 398 */     Breadcrumb breadcrumb = new Breadcrumb();
/* 399 */     breadcrumb.setType("info");
/* 400 */     breadcrumb.setMessage(message);
/* 401 */     breadcrumb.setLevel(SentryLevel.INFO);
/* 402 */     return breadcrumb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Breadcrumb query(@NotNull String message) {
/* 412 */     Breadcrumb breadcrumb = new Breadcrumb();
/* 413 */     breadcrumb.setType("query");
/* 414 */     breadcrumb.setMessage(message);
/* 415 */     return breadcrumb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Breadcrumb ui(@NotNull String category, @NotNull String message) {
/* 427 */     Breadcrumb breadcrumb = new Breadcrumb();
/* 428 */     breadcrumb.setType("default");
/* 429 */     breadcrumb.setCategory("ui." + category);
/* 430 */     breadcrumb.setMessage(message);
/* 431 */     return breadcrumb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Breadcrumb user(@NotNull String category, @NotNull String message) {
/* 442 */     Breadcrumb breadcrumb = new Breadcrumb();
/* 443 */     breadcrumb.setType("user");
/* 444 */     breadcrumb.setCategory(category);
/* 445 */     breadcrumb.setMessage(message);
/* 446 */     return breadcrumb;
/*     */   }
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
/*     */   @NotNull
/*     */   public static Breadcrumb userInteraction(@NotNull String subCategory, @Nullable String viewId, @Nullable String viewClass) {
/* 463 */     return userInteraction(subCategory, viewId, viewClass, Collections.emptyMap());
/*     */   }
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
/*     */   @NotNull
/*     */   public static Breadcrumb userInteraction(@NotNull String subCategory, @Nullable String viewId, @Nullable String viewClass, @Nullable String viewTag, @NotNull Map<String, Object> additionalData) {
/* 484 */     Breadcrumb breadcrumb = new Breadcrumb();
/* 485 */     breadcrumb.setType("user");
/* 486 */     breadcrumb.setCategory("ui." + subCategory);
/* 487 */     if (viewId != null) {
/* 488 */       breadcrumb.setData("view.id", viewId);
/*     */     }
/* 490 */     if (viewClass != null) {
/* 491 */       breadcrumb.setData("view.class", viewClass);
/*     */     }
/* 493 */     if (viewTag != null) {
/* 494 */       breadcrumb.setData("view.tag", viewTag);
/*     */     }
/* 496 */     for (Map.Entry<String, Object> entry : additionalData.entrySet()) {
/* 497 */       breadcrumb.getData().put(entry.getKey(), entry.getValue());
/*     */     }
/* 499 */     breadcrumb.setLevel(SentryLevel.INFO);
/* 500 */     return breadcrumb;
/*     */   }
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
/*     */   @NotNull
/*     */   public static Breadcrumb userInteraction(@NotNull String subCategory, @Nullable String viewId, @Nullable String viewClass, @NotNull Map<String, Object> additionalData) {
/* 520 */     return userInteraction(subCategory, viewId, viewClass, null, additionalData);
/*     */   }
/*     */   @Nullable
/*     */   private static SentryLevel levelFromHttpStatusCode(@NotNull Integer code) {
/* 524 */     if (HttpUtils.isHttpClientError(code.intValue()))
/* 525 */       return SentryLevel.WARNING; 
/* 526 */     if (HttpUtils.isHttpServerError(code.intValue())) {
/* 527 */       return SentryLevel.ERROR;
/*     */     }
/* 529 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Breadcrumb() {
/* 535 */     this(System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Breadcrumb(@Nullable String message) {
/* 544 */     this();
/* 545 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Date getTimestamp() {
/* 555 */     if (this.timestamp != null)
/* 556 */       return (Date)this.timestamp.clone(); 
/* 557 */     if (this.timestampMs != null) {
/*     */       
/* 559 */       this.timestamp = DateUtils.getDateTime(this.timestampMs.longValue());
/* 560 */       return this.timestamp;
/*     */     } 
/* 562 */     throw new IllegalStateException("No timestamp set for breadcrumb");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getMessage() {
/* 571 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessage(@Nullable String message) {
/* 580 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getType() {
/* 589 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(@Nullable String type) {
/* 598 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public Map<String, Object> getData() {
/* 609 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Object getData(@Nullable String key) {
/* 620 */     if (key == null) {
/* 621 */       return null;
/*     */     }
/* 623 */     return this.data.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(@Nullable String key, @Nullable Object value) {
/* 633 */     if (key == null) {
/*     */       return;
/*     */     }
/* 636 */     if (value == null) {
/* 637 */       removeData(key);
/*     */     } else {
/* 639 */       this.data.put(key, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeData(@Nullable String key) {
/* 649 */     if (key == null) {
/*     */       return;
/*     */     }
/* 652 */     this.data.remove(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getCategory() {
/* 661 */     return this.category;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCategory(@Nullable String category) {
/* 670 */     this.category = category;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getOrigin() {
/* 679 */     return this.origin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOrigin(@Nullable String origin) {
/* 688 */     this.origin = origin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SentryLevel getLevel() {
/* 697 */     return this.level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLevel(@Nullable SentryLevel level) {
/* 706 */     this.level = level;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 711 */     if (this == o) return true; 
/* 712 */     if (o == null || getClass() != o.getClass()) return false; 
/* 713 */     Breadcrumb that = (Breadcrumb)o;
/*     */     
/* 715 */     if ("http".equals(this.type)) {
/* 716 */       return httpBreadcrumbEquals(this, that);
/*     */     }
/* 718 */     return breadcrumbEquals(this, that);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 724 */     if ("http".equals(this.type)) {
/* 725 */       return httpBreadcrumbHashCode(this);
/*     */     }
/* 727 */     return breadcrumbHashCode(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean breadcrumbEquals(@NotNull Breadcrumb a, @NotNull Breadcrumb b) {
/* 738 */     return (a.getTimestamp().getTime() == b.getTimestamp().getTime() && 
/* 739 */       Objects.equals(a.message, b.message) && 
/* 740 */       Objects.equals(a.type, b.type) && 
/* 741 */       Objects.equals(a.category, b.category) && 
/* 742 */       Objects.equals(a.origin, b.origin) && a.level == b.level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean httpBreadcrumbEquals(@NotNull Breadcrumb a, @NotNull Breadcrumb b) {
/* 753 */     return (breadcrumbEquals(a, b) && 
/* 754 */       Objects.equals(a.getData("status_code"), b.getData("status_code")) && 
/* 755 */       Objects.equals(a.getData("url"), b.getData("url")) && 
/* 756 */       Objects.equals(a.getData("method"), b.getData("method")) && 
/* 757 */       Objects.equals(a.getData("http.fragment"), b.getData("http.fragment")) && 
/* 758 */       Objects.equals(a.getData("http.query"), b.getData("http.query")));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int breadcrumbHashCode(@NotNull Breadcrumb breadcrumb) {
/* 764 */     return Objects.hash(new Object[] {
/* 765 */           Long.valueOf(breadcrumb.getTimestamp().getTime()), breadcrumb.message, breadcrumb.type, breadcrumb.category, breadcrumb.origin, breadcrumb.level
/*     */         });
/*     */   }
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
/*     */   private static int httpBreadcrumbHashCode(@NotNull Breadcrumb breadcrumb) {
/* 779 */     return Objects.hash(new Object[] { 
/* 780 */           Long.valueOf(breadcrumb.getTimestamp().getTime()), breadcrumb.message, breadcrumb.type, breadcrumb.category, breadcrumb.origin, breadcrumb.level, breadcrumb
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 786 */           .getData("status_code"), breadcrumb
/* 787 */           .getData("url"), breadcrumb
/* 788 */           .getData("method"), breadcrumb
/* 789 */           .getData("http.fragment"), breadcrumb
/* 790 */           .getData("http.query") });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 798 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 803 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(@NotNull Breadcrumb o) {
/* 809 */     return this.nanos.compareTo(o.nanos);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String TIMESTAMP = "timestamp";
/*     */     public static final String MESSAGE = "message";
/*     */     public static final String TYPE = "type";
/*     */     public static final String DATA = "data";
/*     */     public static final String CATEGORY = "category";
/*     */     public static final String ORIGIN = "origin";
/*     */     public static final String LEVEL = "level";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 825 */     writer.beginObject();
/* 826 */     writer.name("timestamp").value(logger, getTimestamp());
/* 827 */     if (this.message != null) {
/* 828 */       writer.name("message").value(this.message);
/*     */     }
/* 830 */     if (this.type != null) {
/* 831 */       writer.name("type").value(this.type);
/*     */     }
/* 833 */     writer.name("data").value(logger, this.data);
/* 834 */     if (this.category != null) {
/* 835 */       writer.name("category").value(this.category);
/*     */     }
/* 837 */     if (this.origin != null) {
/* 838 */       writer.name("origin").value(this.origin);
/*     */     }
/* 840 */     if (this.level != null) {
/* 841 */       writer.name("level").value(logger, this.level);
/*     */     }
/* 843 */     if (this.unknown != null) {
/* 844 */       for (String key : this.unknown.keySet()) {
/* 845 */         Object value = this.unknown.get(key);
/* 846 */         writer.name(key);
/* 847 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 850 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<Breadcrumb>
/*     */   {
/*     */     @NotNull
/*     */     public Breadcrumb deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 858 */       reader.beginObject();
/* 859 */       Date timestamp = DateUtils.getCurrentDateTime();
/* 860 */       String message = null;
/* 861 */       String type = null;
/* 862 */       Map<String, Object> data = new ConcurrentHashMap<>();
/* 863 */       String category = null;
/* 864 */       String origin = null;
/* 865 */       SentryLevel level = null;
/*     */       
/* 867 */       Map<String, Object> unknown = null;
/* 868 */       while (reader.peek() == JsonToken.NAME) {
/* 869 */         Date deserializedDate; Map<String, Object> deserializedData; String nextName = reader.nextName();
/* 870 */         switch (nextName) {
/*     */           case "timestamp":
/* 872 */             deserializedDate = reader.nextDateOrNull(logger);
/* 873 */             if (deserializedDate != null) {
/* 874 */               timestamp = deserializedDate;
/*     */             }
/*     */             continue;
/*     */           case "message":
/* 878 */             message = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "type":
/* 881 */             type = reader.nextStringOrNull();
/*     */             continue;
/*     */           
/*     */           case "data":
/* 885 */             deserializedData = CollectionUtils.newConcurrentHashMap((Map)reader
/* 886 */                 .nextObjectOrNull());
/* 887 */             if (deserializedData != null) {
/* 888 */               data = deserializedData;
/*     */             }
/*     */             continue;
/*     */           case "category":
/* 892 */             category = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "origin":
/* 895 */             origin = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "level":
/*     */             try {
/* 899 */               level = (new SentryLevel.Deserializer()).deserialize(reader, logger);
/* 900 */             } catch (Exception exception) {
/* 901 */               logger.log(SentryLevel.ERROR, exception, "Error when deserializing SentryLevel", new Object[0]);
/*     */             } 
/*     */             continue;
/*     */         } 
/* 905 */         if (unknown == null) {
/* 906 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 908 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 913 */       Breadcrumb breadcrumb = new Breadcrumb(timestamp);
/* 914 */       breadcrumb.message = message;
/* 915 */       breadcrumb.type = type;
/* 916 */       breadcrumb.data = data;
/* 917 */       breadcrumb.category = category;
/* 918 */       breadcrumb.origin = origin;
/* 919 */       breadcrumb.level = level;
/*     */       
/* 921 */       breadcrumb.setUnknown(unknown);
/* 922 */       reader.endObject();
/* 923 */       return breadcrumb;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\Breadcrumb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */