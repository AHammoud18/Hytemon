/*     */ package com.hypixel.hytale.server.core.modules.accesscontrol.provider;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import com.hypixel.hytale.server.core.modules.accesscontrol.AccessControlModule;
/*     */ import com.hypixel.hytale.server.core.modules.accesscontrol.ban.Ban;
/*     */ import com.hypixel.hytale.server.core.util.io.BlockingDiskFile;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class HytaleBanProvider
/*     */   extends BlockingDiskFile
/*     */   implements AccessProvider
/*     */ {
/*  29 */   private final Map<UUID, Ban> bans = (Map<UUID, Ban>)new Object2ObjectOpenHashMap();
/*     */   
/*     */   public HytaleBanProvider() {
/*  32 */     super(Paths.get("bans.json", new String[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<Optional<String>> getDisconnectReason(UUID uuid) {
/*  38 */     Ban ban = this.bans.get(uuid);
/*     */ 
/*     */ 
/*     */     
/*  42 */     if (ban != null && !ban.isInEffect()) {
/*  43 */       this.bans.remove(uuid);
/*     */       
/*  45 */       ban = null;
/*     */     } 
/*     */     
/*  48 */     if (ban != null) {
/*  49 */       return ban.getDisconnectReason(uuid);
/*     */     }
/*     */     
/*  52 */     return CompletableFuture.completedFuture(Optional.empty());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void read(@Nonnull BufferedReader fileReader) {
/*  57 */     JsonParser.parseReader(fileReader).getAsJsonArray().forEach(entry -> {
/*     */           JsonObject jsonObject = entry.getAsJsonObject();
/*     */ 
/*     */           
/*     */           try {
/*     */             Ban ban = AccessControlModule.get().parseBan(jsonObject.get("type").getAsString(), jsonObject);
/*     */             
/*     */             Objects.requireNonNull(ban.getBy(), "Ban has null getBy");
/*     */             
/*     */             Objects.requireNonNull(ban.getTarget(), "Ban has null getTarget");
/*     */             
/*     */             if (ban.isInEffect()) {
/*     */               this.bans.put(ban.getTarget(), ban);
/*     */             }
/*  71 */           } catch (Exception ex) {
/*     */             throw new RuntimeException("Failed to parse ban!", ex);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void write(@Nonnull BufferedWriter fileWriter) throws IOException {
/*  79 */     JsonArray array = new JsonArray();
/*     */     
/*  81 */     this.bans.forEach((key, value) -> array.add((JsonElement)value.toJsonObject()));
/*     */     
/*  83 */     fileWriter.write(array.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void create(@Nonnull BufferedWriter fileWriter) throws IOException {
/*  88 */     JsonWriter jsonWriter = new JsonWriter(fileWriter); 
/*  89 */     try { jsonWriter.beginArray().endArray();
/*  90 */       jsonWriter.close(); }
/*     */     catch (Throwable throwable) { try { jsonWriter.close(); }
/*     */       catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/*  94 */      } public boolean hasBan(UUID uuid) { this.fileLock.readLock().lock();
/*     */     
/*     */     try {
/*  97 */       return this.bans.containsKey(uuid);
/*     */     } finally {
/*  99 */       this.fileLock.readLock().unlock();
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean modify(@Nonnull Function<Map<UUID, Ban>, Boolean> function) {
/*     */     boolean modified;
/* 106 */     this.fileLock.writeLock().lock();
/*     */     
/*     */     try {
/* 109 */       modified = ((Boolean)function.apply(this.bans)).booleanValue();
/*     */     } finally {
/* 111 */       this.fileLock.writeLock().unlock();
/*     */     } 
/*     */ 
/*     */     
/* 115 */     if (modified) {
/* 116 */       syncSave();
/*     */     }
/*     */     
/* 119 */     return modified;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\provider\HytaleBanProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */