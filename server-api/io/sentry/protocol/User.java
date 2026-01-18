/*     */ package io.sentry.protocol;
/*     */ 
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.JsonDeserializer;
/*     */ import io.sentry.JsonSerializable;
/*     */ import io.sentry.JsonUnknown;
/*     */ import io.sentry.ObjectReader;
/*     */ import io.sentry.ObjectWriter;
/*     */ import io.sentry.SentryLevel;
/*     */ import io.sentry.SentryOptions;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class User
/*     */   implements JsonUnknown, JsonSerializable
/*     */ {
/*     */   @Nullable
/*     */   private String email;
/*     */   @Nullable
/*     */   private String id;
/*     */   @Nullable
/*     */   private String username;
/*     */   @Nullable
/*     */   private String ipAddress;
/*     */   @Deprecated
/*     */   @Nullable
/*     */   private String name;
/*     */   @Nullable
/*     */   private Geo geo;
/*     */   @Nullable
/*     */   private Map<String, String> data;
/*     */   @Nullable
/*     */   private Map<String, Object> unknown;
/*     */   
/*     */   public User() {}
/*     */   
/*     */   public User(@NotNull User user) {
/*  57 */     this.email = user.email;
/*  58 */     this.username = user.username;
/*  59 */     this.id = user.id;
/*  60 */     this.ipAddress = user.ipAddress;
/*  61 */     this.name = user.name;
/*  62 */     this.geo = user.geo;
/*  63 */     this.data = CollectionUtils.newConcurrentHashMap(user.data);
/*  64 */     this.unknown = CollectionUtils.newConcurrentHashMap(user.unknown);
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
/*     */   public static User fromMap(@NotNull Map<String, Object> map, @NotNull SentryOptions options) {
/*  79 */     User user = new User();
/*  80 */     Map<String, Object> unknown = null;
/*     */     
/*  82 */     for (Map.Entry<String, Object> entry : map.entrySet()) {
/*  83 */       Map<Object, Object> geo, data; Object value = entry.getValue();
/*  84 */       switch ((String)entry.getKey()) {
/*     */         case "email":
/*  86 */           user.email = (value instanceof String) ? (String)value : null;
/*     */           continue;
/*     */         case "id":
/*  89 */           user.id = (value instanceof String) ? (String)value : null;
/*     */           continue;
/*     */         case "username":
/*  92 */           user.username = (value instanceof String) ? (String)value : null;
/*     */           continue;
/*     */         case "ip_address":
/*  95 */           user.ipAddress = (value instanceof String) ? (String)value : null;
/*     */           continue;
/*     */         case "name":
/*  98 */           user.name = (value instanceof String) ? (String)value : null;
/*     */           continue;
/*     */         
/*     */         case "geo":
/* 102 */           geo = (value instanceof Map) ? (Map<Object, Object>)value : null;
/* 103 */           if (geo != null) {
/* 104 */             ConcurrentHashMap<String, Object> geoData = new ConcurrentHashMap<>();
/* 105 */             for (Map.Entry<Object, Object> geoEntry : geo.entrySet()) {
/* 106 */               if (geoEntry.getKey() instanceof String && geoEntry.getValue() != null) {
/* 107 */                 geoData.put((String)geoEntry.getKey(), geoEntry.getValue()); continue;
/*     */               } 
/* 109 */               options.getLogger().log(SentryLevel.WARNING, "Invalid key type in gep map.", new Object[0]);
/*     */             } 
/*     */             
/* 112 */             user.geo = Geo.fromMap(geoData);
/*     */           } 
/*     */           continue;
/*     */         
/*     */         case "data":
/* 117 */           data = (value instanceof Map) ? (Map<Object, Object>)value : null;
/* 118 */           if (data != null) {
/* 119 */             ConcurrentHashMap<String, String> userData = new ConcurrentHashMap<>();
/* 120 */             for (Map.Entry<Object, Object> dataEntry : data.entrySet()) {
/* 121 */               if (dataEntry.getKey() instanceof String && dataEntry.getValue() != null) {
/* 122 */                 userData.put((String)dataEntry.getKey(), dataEntry.getValue().toString()); continue;
/*     */               } 
/* 124 */               options
/* 125 */                 .getLogger()
/* 126 */                 .log(SentryLevel.WARNING, "Invalid key or null value in data map.", new Object[0]);
/*     */             } 
/*     */             
/* 129 */             user.data = userData;
/*     */           } 
/*     */           continue;
/*     */       } 
/* 133 */       if (unknown == null) {
/* 134 */         unknown = new ConcurrentHashMap<>();
/*     */       }
/* 136 */       unknown.put(entry.getKey(), entry.getValue());
/*     */     } 
/*     */ 
/*     */     
/* 140 */     user.unknown = unknown;
/* 141 */     return user;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getEmail() {
/* 150 */     return this.email;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmail(@Nullable String email) {
/* 159 */     this.email = email;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getId() {
/* 168 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setId(@Nullable String id) {
/* 177 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getUsername() {
/* 186 */     return this.username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsername(@Nullable String username) {
/* 195 */     this.username = username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getIpAddress() {
/* 204 */     return this.ipAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIpAddress(@Nullable String ipAddress) {
/* 213 */     this.ipAddress = ipAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public String getName() {
/* 224 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setName(@Nullable String name) {
/* 235 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Geo getGeo() {
/* 244 */     return this.geo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGeo(@Nullable Geo geo) {
/* 253 */     this.geo = geo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, String> getData() {
/* 262 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(@Nullable Map<String, String> data) {
/* 271 */     this.data = CollectionUtils.newConcurrentHashMap(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 276 */     if (this == o) return true; 
/* 277 */     if (o == null || getClass() != o.getClass()) return false; 
/* 278 */     User user = (User)o;
/* 279 */     return (Objects.equals(this.email, user.email) && 
/* 280 */       Objects.equals(this.id, user.id) && 
/* 281 */       Objects.equals(this.username, user.username) && 
/* 282 */       Objects.equals(this.ipAddress, user.ipAddress));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 287 */     return Objects.hash(new Object[] { this.email, this.id, this.username, this.ipAddress });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Object> getUnknown() {
/* 295 */     return this.unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnknown(@Nullable Map<String, Object> unknown) {
/* 300 */     this.unknown = unknown;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class JsonKeys
/*     */   {
/*     */     public static final String EMAIL = "email";
/*     */     public static final String ID = "id";
/*     */     public static final String USERNAME = "username";
/*     */     public static final String IP_ADDRESS = "ip_address";
/*     */     public static final String NAME = "name";
/*     */     public static final String GEO = "geo";
/*     */     public static final String DATA = "data";
/*     */   }
/*     */   
/*     */   public void serialize(@NotNull ObjectWriter writer, @NotNull ILogger logger) throws IOException {
/* 316 */     writer.beginObject();
/* 317 */     if (this.email != null) {
/* 318 */       writer.name("email").value(this.email);
/*     */     }
/* 320 */     if (this.id != null) {
/* 321 */       writer.name("id").value(this.id);
/*     */     }
/* 323 */     if (this.username != null) {
/* 324 */       writer.name("username").value(this.username);
/*     */     }
/* 326 */     if (this.ipAddress != null) {
/* 327 */       writer.name("ip_address").value(this.ipAddress);
/*     */     }
/* 329 */     if (this.name != null) {
/* 330 */       writer.name("name").value(this.name);
/*     */     }
/* 332 */     if (this.geo != null) {
/* 333 */       writer.name("geo");
/* 334 */       this.geo.serialize(writer, logger);
/*     */     } 
/* 336 */     if (this.data != null) {
/* 337 */       writer.name("data").value(logger, this.data);
/*     */     }
/* 339 */     if (this.unknown != null) {
/* 340 */       for (String key : this.unknown.keySet()) {
/* 341 */         Object value = this.unknown.get(key);
/* 342 */         writer.name(key);
/* 343 */         writer.value(logger, value);
/*     */       } 
/*     */     }
/* 346 */     writer.endObject();
/*     */   }
/*     */   
/*     */   public static final class Deserializer
/*     */     implements JsonDeserializer<User>
/*     */   {
/*     */     @NotNull
/*     */     public User deserialize(@NotNull ObjectReader reader, @NotNull ILogger logger) throws Exception {
/* 354 */       reader.beginObject();
/* 355 */       User user = new User();
/* 356 */       Map<String, Object> unknown = null;
/* 357 */       while (reader.peek() == JsonToken.NAME) {
/* 358 */         String nextName = reader.nextName();
/* 359 */         switch (nextName) {
/*     */           case "email":
/* 361 */             user.email = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "id":
/* 364 */             user.id = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "username":
/* 367 */             user.username = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "ip_address":
/* 370 */             user.ipAddress = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "name":
/* 373 */             user.name = reader.nextStringOrNull();
/*     */             continue;
/*     */           case "geo":
/* 376 */             user.geo = (new Geo.Deserializer()).deserialize(reader, logger);
/*     */             continue;
/*     */           case "data":
/* 379 */             user.data = 
/* 380 */               CollectionUtils.newConcurrentHashMap((Map)reader
/* 381 */                 .nextObjectOrNull());
/*     */             continue;
/*     */         } 
/* 384 */         if (unknown == null) {
/* 385 */           unknown = new ConcurrentHashMap<>();
/*     */         }
/* 387 */         reader.nextUnknown(logger, unknown, nextName);
/*     */       } 
/*     */ 
/*     */       
/* 391 */       user.setUnknown(unknown);
/* 392 */       reader.endObject();
/* 393 */       return user;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\protocol\User.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */