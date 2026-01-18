/*     */ package com.hypixel.hytale.server.core.universe.world.path;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.event.IBaseEvent;
/*     */ import com.hypixel.hytale.event.IEventDispatcher;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.util.BsonUtil;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldPathConfig
/*     */ {
/*     */   public static final BuilderCodec<WorldPathConfig> CODEC;
/*     */   
/*     */   static {
/*  38 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(WorldPathConfig.class, WorldPathConfig::new).addField(new KeyedCodec("Paths", (Codec)new MapCodec((Codec)WorldPath.CODEC, ConcurrentHashMap::new, false)), (config, paths) -> config.paths = paths, config -> config.paths)).build();
/*     */   }
/*     */   
/*  41 */   protected Map<String, WorldPath> paths = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldPath getPath(String name) {
/*  49 */     return this.paths.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, WorldPath> getPaths() {
/*  57 */     return Collections.unmodifiableMap(this.paths);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WorldPath putPath(@Nonnull WorldPath worldPath) {
/*  68 */     Objects.requireNonNull(worldPath);
/*  69 */     IEventDispatcher<WorldPathChangedEvent, WorldPathChangedEvent> dispatcher = HytaleServer.get().getEventBus().dispatchFor(WorldPathChangedEvent.class);
/*  70 */     if (dispatcher.hasListener()) dispatcher.dispatch((IBaseEvent)new WorldPathChangedEvent(worldPath)); 
/*  71 */     return this.paths.put(worldPath.getName(), worldPath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldPath removePath(String path) {
/*  81 */     Objects.requireNonNull(path);
/*  82 */     return this.paths.remove(path);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<Void> save(World world) {
/*  87 */     BsonValue bsonValue = CODEC.encode(this);
/*  88 */     return BsonUtil.writeDocument(world.getSavePath().resolve("paths.json"), bsonValue.asDocument());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CompletableFuture<WorldPathConfig> load(World world) {
/*  93 */     Path oldPath = world.getSavePath().resolve("paths.bson");
/*  94 */     Path path = world.getSavePath().resolve("paths.json");
/*     */     
/*  96 */     if (Files.exists(oldPath, new java.nio.file.LinkOption[0]) && !Files.exists(path, new java.nio.file.LinkOption[0])) {
/*     */       try {
/*  98 */         Files.move(oldPath, path, new java.nio.file.CopyOption[0]);
/*  99 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */     
/* 103 */     return CompletableFuture.supplyAsync(() -> {
/*     */           WorldPathConfig config = (WorldPathConfig)RawJsonReader.readSyncWithBak(path, (Codec)CODEC, HytaleLogger.getLogger());
/*     */           return (config != null) ? config : new WorldPathConfig();
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 112 */     return "WorldPathConfig{paths=" + String.valueOf(this.paths) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\path\WorldPathConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */