/*     */ package com.hypixel.hytale.server.core.permissions.provider;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import com.hypixel.hytale.server.core.util.io.BlockingDiskFile;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public final class HytalePermissionsProvider
/*     */   extends BlockingDiskFile
/*     */   implements PermissionProvider
/*     */ {
/*     */   @Nonnull
/*     */   public static final String DEFAULT_GROUP = "Default";
/*     */   @Nonnull
/*  33 */   public static final Set<String> DEFAULT_GROUP_LIST = Set.of("Default");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static final String OP_GROUP = "OP";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  45 */   public static final Map<String, Set<String>> DEFAULT_GROUPS = Map.ofEntries((Map.Entry<? extends String, ? extends Set<String>>[])new Map.Entry[] {
/*  46 */         Map.entry("OP", Set.of("*")), 
/*  47 */         Map.entry("Default", Set.of())
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  54 */   private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static final String PERMISSIONS_FILE_PATH = "permissions.json";
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  65 */   private final Map<UUID, Set<String>> userPermissions = (Map<UUID, Set<String>>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  71 */   private final Map<String, Set<String>> groupPermissions = (Map<String, Set<String>>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  77 */   private final Map<UUID, Set<String>> userGroups = (Map<UUID, Set<String>>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HytalePermissionsProvider() {
/*  84 */     super(Paths.get("permissions.json", new String[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getName() {
/*  90 */     return "HytalePermissionsProvider";
/*     */   }
/*     */ 
/*     */   
/*     */   public void addUserPermissions(@Nonnull UUID uuid, @Nonnull Set<String> permissions) {
/*  95 */     this.fileLock.writeLock().lock();
/*     */     try {
/*  97 */       Set<String> set = this.userPermissions.computeIfAbsent(uuid, k -> new HashSet());
/*  98 */       if (set.addAll(permissions)) {
/*  99 */         syncSave();
/*     */       }
/*     */     } finally {
/* 102 */       this.fileLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeUserPermissions(@Nonnull UUID uuid, @Nonnull Set<String> permissions) {
/* 108 */     this.fileLock.writeLock().lock();
/*     */     try {
/* 110 */       Set<String> set = this.userPermissions.get(uuid);
/* 111 */       if (set != null) {
/* 112 */         boolean hasChanges = set.removeAll(permissions);
/*     */         
/* 114 */         if (set.isEmpty()) {
/* 115 */           this.userPermissions.remove(uuid);
/*     */         }
/*     */ 
/*     */         
/* 119 */         if (hasChanges) {
/* 120 */           syncSave();
/*     */         }
/*     */       } 
/*     */     } finally {
/* 124 */       this.fileLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<String> getUserPermissions(@Nonnull UUID uuid) {
/* 131 */     this.fileLock.readLock().lock();
/*     */     try {
/* 133 */       Set<String> set = this.userPermissions.get(uuid);
/*     */       
/* 135 */       if (set == null) {
/* 136 */         return (Set)Collections.emptySet();
/*     */       }
/*     */       
/* 139 */       return Collections.unmodifiableSet(set);
/*     */     } finally {
/* 141 */       this.fileLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addGroupPermissions(@Nonnull String group, @Nonnull Set<String> permissions) {
/* 147 */     this.fileLock.writeLock().lock();
/*     */     try {
/* 149 */       Set<String> set = this.groupPermissions.computeIfAbsent(group, k -> new HashSet());
/* 150 */       if (set.addAll(permissions)) {
/* 151 */         syncSave();
/*     */       }
/*     */     } finally {
/* 154 */       this.fileLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeGroupPermissions(@Nonnull String group, @Nonnull Set<String> permissions) {
/* 160 */     this.fileLock.writeLock().lock();
/*     */     try {
/* 162 */       Set<String> set = this.groupPermissions.get(group);
/* 163 */       if (set != null) {
/* 164 */         boolean hasChanges = set.removeAll(permissions);
/*     */         
/* 166 */         if (set.isEmpty()) {
/* 167 */           this.groupPermissions.remove(group);
/*     */         }
/*     */ 
/*     */         
/* 171 */         if (hasChanges) {
/* 172 */           syncSave();
/*     */         }
/*     */       } 
/*     */     } finally {
/* 176 */       this.fileLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<String> getGroupPermissions(@Nonnull String group) {
/* 183 */     this.fileLock.readLock().lock();
/*     */     try {
/* 185 */       Set<String> set = this.groupPermissions.get(group);
/*     */       
/* 187 */       if (set == null) {
/* 188 */         return (Set)Collections.emptySet();
/*     */       }
/*     */       
/* 191 */       return Collections.unmodifiableSet(set);
/*     */     } finally {
/* 193 */       this.fileLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addUserToGroup(@Nonnull UUID uuid, @Nonnull String group) {
/* 199 */     this.fileLock.writeLock().lock();
/*     */     try {
/* 201 */       Set<String> list = this.userGroups.computeIfAbsent(uuid, k -> new HashSet());
/* 202 */       if (list.add(group)) {
/* 203 */         syncSave();
/*     */       }
/*     */     } finally {
/* 206 */       this.fileLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeUserFromGroup(@Nonnull UUID uuid, @Nonnull String group) {
/* 212 */     this.fileLock.writeLock().lock();
/*     */     try {
/* 214 */       Set<String> list = this.userGroups.get(uuid);
/* 215 */       if (list != null) {
/* 216 */         boolean hasChanges = list.remove(group);
/*     */         
/* 218 */         if (list.isEmpty()) {
/* 219 */           this.userGroups.remove(uuid);
/*     */         }
/*     */ 
/*     */         
/* 223 */         if (hasChanges) {
/* 224 */           syncSave();
/*     */         }
/*     */       } 
/*     */     } finally {
/* 228 */       this.fileLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<String> getGroupsForUser(@Nonnull UUID uuid) {
/* 235 */     this.fileLock.readLock().lock();
/*     */     try {
/* 237 */       Set<String> list = this.userGroups.get(uuid);
/*     */       
/* 239 */       if (list == null) {
/* 240 */         return DEFAULT_GROUP_LIST;
/*     */       }
/*     */       
/* 243 */       return Collections.unmodifiableSet(list);
/*     */     } finally {
/* 245 */       this.fileLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void read(@Nonnull BufferedReader fileReader) throws IOException {
/* 251 */     JsonReader jsonReader = new JsonReader(fileReader); 
/* 252 */     try { JsonObject root = JsonParser.parseReader(jsonReader).getAsJsonObject();
/*     */       
/* 254 */       this.userPermissions.clear();
/* 255 */       this.groupPermissions.clear();
/* 256 */       this.userGroups.clear();
/*     */       
/* 258 */       if (root.has("users")) {
/* 259 */         JsonObject users = root.getAsJsonObject("users");
/* 260 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)users.entrySet()) {
/* 261 */           UUID uuid = UUID.fromString(entry.getKey());
/* 262 */           JsonObject user = ((JsonElement)entry.getValue()).getAsJsonObject();
/*     */           
/* 264 */           if (user.has("permissions")) {
/* 265 */             Set<String> set = new HashSet<>();
/* 266 */             this.userPermissions.put(uuid, set);
/* 267 */             user.getAsJsonArray("permissions").forEach(e -> set.add(e.getAsString()));
/*     */           } 
/*     */           
/* 270 */           if (user.has("groups")) {
/* 271 */             Set<String> list = new HashSet<>();
/* 272 */             this.userGroups.put(uuid, list);
/* 273 */             user.getAsJsonArray("groups").forEach(e -> list.add(e.getAsString()));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 278 */       if (root.has("groups")) {
/* 279 */         JsonObject groups = root.getAsJsonObject("groups");
/* 280 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)groups.entrySet()) {
/* 281 */           Set<String> set = new HashSet<>();
/* 282 */           this.groupPermissions.put(entry.getKey(), set);
/* 283 */           ((JsonElement)entry.getValue()).getAsJsonArray().forEach(e -> set.add(e.getAsString()));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 288 */       for (Map.Entry<String, Set<String>> entry : DEFAULT_GROUPS.entrySet()) {
/* 289 */         this.groupPermissions.put(entry.getKey(), new HashSet<>(entry.getValue()));
/*     */       }
/* 291 */       jsonReader.close(); }
/*     */     catch (Throwable throwable) { try { jsonReader.close(); }
/*     */       catch (Throwable throwable1)
/*     */       { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 296 */      } protected void write(@Nonnull BufferedWriter fileWriter) throws IOException { JsonObject root = new JsonObject();
/*     */     
/* 298 */     JsonObject usersObj = new JsonObject();
/* 299 */     for (Map.Entry<UUID, Set<String>> entry : this.userPermissions.entrySet()) {
/* 300 */       JsonArray asArray = new JsonArray();
/* 301 */       Objects.requireNonNull(asArray); ((Set)entry.getValue()).forEach(asArray::add);
/* 302 */       String memberName = ((UUID)entry.getKey()).toString();
/* 303 */       if (!usersObj.has(memberName)) usersObj.add(memberName, (JsonElement)new JsonObject()); 
/* 304 */       usersObj.getAsJsonObject(memberName).add("permissions", (JsonElement)asArray);
/*     */     } 
/*     */     
/* 307 */     for (Map.Entry<UUID, Set<String>> entry : this.userGroups.entrySet()) {
/* 308 */       JsonArray asArray = new JsonArray();
/* 309 */       Objects.requireNonNull(asArray); ((Set)entry.getValue()).forEach(asArray::add);
/* 310 */       String memberName = ((UUID)entry.getKey()).toString();
/* 311 */       if (!usersObj.has(memberName)) usersObj.add(memberName, (JsonElement)new JsonObject()); 
/* 312 */       usersObj.getAsJsonObject(memberName).add("groups", (JsonElement)asArray);
/*     */     } 
/*     */     
/* 315 */     if (!usersObj.isEmpty()) root.add("users", (JsonElement)usersObj);
/*     */     
/* 317 */     JsonObject groupsObj = new JsonObject();
/* 318 */     for (Map.Entry<String, Set<String>> entry : this.groupPermissions.entrySet()) {
/* 319 */       JsonArray asArray = new JsonArray();
/* 320 */       Objects.requireNonNull(asArray); ((Set)entry.getValue()).forEach(asArray::add);
/* 321 */       groupsObj.add(entry.getKey(), (JsonElement)asArray);
/*     */     } 
/* 323 */     if (!groupsObj.isEmpty()) root.add("groups", (JsonElement)groupsObj);
/*     */     
/* 325 */     fileWriter.write(GSON.toJson((JsonElement)root)); }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void create(@Nonnull BufferedWriter fileWriter) throws IOException {
/* 330 */     JsonWriter jsonWriter = new JsonWriter(fileWriter); try {
/* 331 */       jsonWriter.beginObject();
/* 332 */       jsonWriter.endObject();
/* 333 */       jsonWriter.close();
/*     */     } catch (Throwable throwable) {
/*     */       try {
/*     */         jsonWriter.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       } 
/*     */       throw throwable;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\provider\HytalePermissionsProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */