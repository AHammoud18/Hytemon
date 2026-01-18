/*     */ package com.hypixel.hytale.server.core.universe.playerdata;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.common.util.PathUtil;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.server.core.Constants;
/*     */ import com.hypixel.hytale.server.core.Options;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bson.BsonDocument;
/*     */ 
/*     */ 
/*     */ public class DiskPlayerStorageProvider
/*     */   implements PlayerStorageProvider
/*     */ {
/*     */   public static final String ID = "Disk";
/*     */   public static final BuilderCodec<DiskPlayerStorageProvider> CODEC;
/*     */   
/*     */   static {
/*  34 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(DiskPlayerStorageProvider.class, DiskPlayerStorageProvider::new).append(new KeyedCodec("Path", (Codec)Codec.STRING), (o, s) -> o.path = PathUtil.get(s), o -> o.path.toString()).add()).build();
/*     */   } @Nonnull
/*  36 */   private Path path = Constants.UNIVERSE_PATH
/*  37 */     .resolve("players");
/*     */   
/*     */   @Nonnull
/*     */   public Path getPath() {
/*  41 */     return this.path;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PlayerStorage getPlayerStorage() {
/*  47 */     return new DiskPlayerStorage(this.path);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  53 */     return "DiskPlayerStorageProvider{path=" + String.valueOf(this.path) + "}";
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DiskPlayerStorage
/*     */     implements PlayerStorage
/*     */   {
/*     */     public static final String FILE_EXTENSION = ".json";
/*     */     @Nonnull
/*     */     private final Path path;
/*     */     
/*     */     public DiskPlayerStorage(@Nonnull Path path) {
/*  65 */       this.path = path;
/*  66 */       if (!Options.getOptionSet().has(Options.BARE)) {
/*     */         try {
/*  68 */           Files.createDirectories(path, (FileAttribute<?>[])new FileAttribute[0]);
/*  69 */         } catch (IOException e) {
/*  70 */           throw new RuntimeException("Failed to create players directory", e);
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CompletableFuture<Holder<EntityStore>> load(@Nonnull UUID uuid) {
/*  78 */       Path file = this.path.resolve(String.valueOf(uuid) + ".json");
/*     */       
/*  80 */       return BsonUtil.readDocument(file).thenApply(bsonDocument -> {
/*     */             if (bsonDocument == null)
/*     */               bsonDocument = new BsonDocument(); 
/*     */             return EntityStore.REGISTRY.deserialize(bsonDocument);
/*     */           });
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public CompletableFuture<Void> save(@Nonnull UUID uuid, @Nonnull Holder<EntityStore> holder) {
/*  89 */       Path file = this.path.resolve(String.valueOf(uuid) + ".json");
/*  90 */       BsonDocument document = EntityStore.REGISTRY.serialize(holder);
/*  91 */       return BsonUtil.writeDocument(file, document);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CompletableFuture<Void> remove(@Nonnull UUID uuid) {
/*  97 */       Path file = this.path.resolve(String.valueOf(uuid) + ".json");
/*     */       try {
/*  99 */         Files.deleteIfExists(file);
/* 100 */         return CompletableFuture.completedFuture(null);
/* 101 */       } catch (IOException e) {
/* 102 */         return CompletableFuture.failedFuture(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<UUID> getPlayers() throws IOException {
/* 109 */       Stream<Path> stream = Files.list(this.path);
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
/*     */       try {
/* 121 */         Set<UUID> set = (Set)stream.map(p -> { String fileName = p.getFileName().toString(); if (!fileName.endsWith(".json")) return null;  try { return UUID.fromString(fileName.substring(0, fileName.length() - ".json".length())); } catch (IllegalArgumentException e) { return null; }  }).filter(Objects::nonNull).collect(Collectors.toSet());
/* 122 */         if (stream != null) stream.close(); 
/*     */         return set;
/*     */       } catch (Throwable throwable) {
/*     */         if (stream != null)
/*     */           try {
/*     */             stream.close();
/*     */           } catch (Throwable throwable1) {
/*     */             throwable.addSuppressed(throwable1);
/*     */           }  
/*     */         throw throwable;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\playerdata\DiskPlayerStorageProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */