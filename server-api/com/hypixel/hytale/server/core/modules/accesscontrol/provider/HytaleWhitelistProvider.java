/*     */ package com.hypixel.hytale.server.core.modules.accesscontrol.provider;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import com.hypixel.hytale.server.core.util.io.BlockingDiskFile;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.Collections;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class HytaleWhitelistProvider extends BlockingDiskFile implements AccessProvider {
/*  24 */   private final ReadWriteLock lock = new ReentrantReadWriteLock();
/*  25 */   private final Set<UUID> whitelist = new HashSet<>();
/*     */   
/*     */   private boolean isEnabled;
/*     */   
/*     */   public HytaleWhitelistProvider() {
/*  30 */     super(Paths.get("whitelist.json", new String[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void read(@Nonnull BufferedReader fileReader) {
/*  35 */     JsonElement element = JsonParser.parseReader(fileReader);
/*  36 */     if (element instanceof JsonObject) { JsonObject jsonObject = (JsonObject)element;
/*  37 */       this.isEnabled = jsonObject.get("enabled").getAsBoolean();
/*  38 */       jsonObject.get("list").getAsJsonArray().forEach(entry -> this.whitelist.add(UUID.fromString(entry.getAsString()))); }
/*     */     else
/*  40 */     { throw new JsonParseException("element is not JsonObject!"); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected void write(@Nonnull BufferedWriter fileWriter) throws IOException {
/*  46 */     JsonObject jsonObject = new JsonObject();
/*     */     
/*  48 */     jsonObject.addProperty("enabled", Boolean.valueOf(this.isEnabled));
/*     */ 
/*     */     
/*  51 */     JsonArray jsonArray = new JsonArray();
/*     */     
/*  53 */     for (UUID uuid : this.whitelist) {
/*  54 */       jsonArray.add(uuid.toString());
/*     */     }
/*     */     
/*  57 */     jsonObject.add("list", (JsonElement)jsonArray);
/*     */ 
/*     */     
/*  60 */     fileWriter.write(jsonObject.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void create(@Nonnull BufferedWriter fileWriter) throws IOException {
/*  65 */     JsonWriter jsonWriter = new JsonWriter(fileWriter); 
/*  66 */     try { jsonWriter.beginObject()
/*  67 */         .name("enabled").value(false)
/*  68 */         .name("list")
/*  69 */         .beginArray()
/*  70 */         .endArray()
/*  71 */         .endObject();
/*  72 */       jsonWriter.close(); }
/*     */     catch (Throwable throwable) { try {
/*     */         jsonWriter.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       }  throw throwable; }
/*  78 */      } @Nonnull public CompletableFuture<Optional<String>> getDisconnectReason(UUID uuid) { this.lock.readLock().lock();
/*     */     
/*     */     try {
/*  81 */       if (this.isEnabled && !this.whitelist.contains(uuid)) {
/*  82 */         return (CompletableFuture)CompletableFuture.completedFuture(Optional.of("You are not whitelisted!"));
/*     */       }
/*     */     } finally {
/*  85 */       this.lock.readLock().unlock();
/*     */     } 
/*     */     
/*  88 */     return CompletableFuture.completedFuture(Optional.empty()); }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean isEnabled) {
/*  92 */     this.lock.writeLock().lock();
/*     */     
/*     */     try {
/*  95 */       this.isEnabled = isEnabled;
/*     */     } finally {
/*  97 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modify(@Nonnull Function<Set<UUID>, Boolean> consumer) {
/*     */     boolean result;
/* 104 */     this.lock.writeLock().lock();
/*     */     
/*     */     try {
/* 107 */       result = ((Boolean)consumer.apply(this.whitelist)).booleanValue();
/*     */     } finally {
/* 109 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */ 
/*     */     
/* 113 */     if (result) {
/* 114 */       syncSave();
/*     */     }
/*     */     
/* 117 */     return result;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Set<UUID> getList() {
/* 122 */     this.lock.readLock().lock();
/*     */     
/*     */     try {
/* 125 */       return Collections.unmodifiableSet(this.whitelist);
/*     */     } finally {
/* 127 */       this.lock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 132 */     this.lock.readLock().lock();
/*     */     
/*     */     try {
/* 135 */       return this.isEnabled;
/*     */     } finally {
/* 137 */       this.lock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\provider\HytaleWhitelistProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */