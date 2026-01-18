/*     */ package com.hypixel.hytale.server.core.universe.world.worldmap;
/*     */ 
/*     */ import com.hypixel.fastutil.longs.Long2ObjectConcurrentHashMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapImage;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerConfigData;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
/*     */ import com.hypixel.hytale.server.core.universe.world.map.WorldMap;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.markers.DeathMarkerProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.markers.POIMarkerProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.markers.PlayerIconMarkerProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.markers.PlayerMarkersProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.markers.RespawnMarkerProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.markers.SpawnMarkerProvider;
/*     */ import com.hypixel.hytale.server.core.util.thread.TickingThread;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldMapManager
/*     */   extends TickingThread
/*     */ {
/*     */   private static final int IMAGE_KEEP_ALIVE = 60;
/*     */   private static final float DEFAULT_UNLOAD_DELAY = 1.0F;
/*     */   @Nonnull
/*     */   private final HytaleLogger logger;
/*     */   @Nonnull
/*     */   private final World world;
/*  61 */   private final Long2ObjectConcurrentHashMap<ImageEntry> images = new Long2ObjectConcurrentHashMap(true, ChunkUtil.indexChunk(-2147483648, -2147483648));
/*  62 */   private final Long2ObjectConcurrentHashMap<CompletableFuture<MapImage>> generating = new Long2ObjectConcurrentHashMap(true, ChunkUtil.indexChunk(-2147483648, -2147483648));
/*     */   
/*  64 */   private final Map<String, MarkerProvider> markerProviders = new ConcurrentHashMap<>();
/*  65 */   private final Map<String, MapMarker> pointsOfInterest = new ConcurrentHashMap<>();
/*     */   @Nonnull
/*  67 */   private WorldMapSettings worldMapSettings = WorldMapSettings.DISABLED;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private IWorldMap generator;
/*     */   
/*     */   @Nonnull
/*  74 */   private CompletableFuture<Void> generatorLoaded = new CompletableFuture<>();
/*     */ 
/*     */   
/*  77 */   private float unloadDelay = 1.0F;
/*     */   
/*     */   public WorldMapManager(@Nonnull World world) {
/*  80 */     super("WorldMap - " + world.getName(), 10, true);
/*  81 */     this.logger = HytaleLogger.get("World|" + world.getName() + "|M");
/*  82 */     this.world = world;
/*     */ 
/*     */     
/*  85 */     addMarkerProvider("spawn", (MarkerProvider)SpawnMarkerProvider.INSTANCE);
/*  86 */     addMarkerProvider("playerIcons", (MarkerProvider)PlayerIconMarkerProvider.INSTANCE);
/*  87 */     addMarkerProvider("death", (MarkerProvider)DeathMarkerProvider.INSTANCE);
/*  88 */     addMarkerProvider("respawn", (MarkerProvider)RespawnMarkerProvider.INSTANCE);
/*  89 */     addMarkerProvider("playerMarkers", (MarkerProvider)PlayerMarkersProvider.INSTANCE);
/*  90 */     addMarkerProvider("poi", (MarkerProvider)POIMarkerProvider.INSTANCE);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public IWorldMap getGenerator() {
/*  95 */     return this.generator;
/*     */   }
/*     */   
/*     */   public void setGenerator(@Nullable IWorldMap generator) {
/*  99 */     boolean before = shouldTick();
/*     */     
/* 101 */     if (this.generator != null) {
/* 102 */       this.generator.shutdown();
/*     */     }
/*     */ 
/*     */     
/* 106 */     this.generator = generator;
/*     */     
/* 108 */     if (generator != null) {
/* 109 */       this.logger.at(Level.INFO).log("Initializing world map generator: %s", generator.toString());
/*     */ 
/*     */       
/* 112 */       this.generatorLoaded.complete(null);
/* 113 */       this.generatorLoaded = new CompletableFuture<>();
/*     */       
/* 115 */       this.worldMapSettings = generator.getWorldMapSettings();
/*     */ 
/*     */       
/* 118 */       this.images.clear();
/* 119 */       this.generating.clear();
/* 120 */       for (Player worldPlayer : this.world.getPlayers()) {
/* 121 */         worldPlayer.getWorldMapTracker().clear();
/*     */       }
/*     */       
/* 124 */       updateTickingState(before);
/*     */       
/* 126 */       sendSettings();
/*     */       
/* 128 */       this.logger.at(Level.INFO).log("Generating Points of Interest...");
/* 129 */       CompletableFutureUtil._catch(generator.generatePointsOfInterest(this.world)
/* 130 */           .thenAcceptAsync(pointsOfInterest -> {
/*     */               this.pointsOfInterest.putAll(pointsOfInterest);
/*     */               this.logger.at(Level.INFO).log("Finished Generating Points of Interest!");
/*     */             }));
/*     */     } else {
/* 135 */       this.logger.at(Level.INFO).log("World map disabled!");
/* 136 */       this.worldMapSettings = WorldMapSettings.DISABLED;
/* 137 */       sendSettings();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isIdle() {
/* 143 */     return (this.world.getPlayerCount() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(float dt) {
/* 148 */     for (Player player : this.world.getPlayers()) {
/* 149 */       player.getWorldMapTracker().tick(dt);
/*     */     }
/*     */     
/* 152 */     this.unloadDelay -= dt;
/* 153 */     if (this.unloadDelay <= 0.0F) {
/* 154 */       this.unloadDelay = 1.0F;
/* 155 */       unloadImages();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onShutdown() {}
/*     */ 
/*     */   
/*     */   public void unloadImages() {
/* 164 */     int imagesCount = this.images.size();
/* 165 */     if (imagesCount == 0)
/*     */       return; 
/* 167 */     List<Player> players = this.world.getPlayers();
/*     */     
/* 169 */     LongOpenHashSet longOpenHashSet = new LongOpenHashSet();
/* 170 */     this.images.forEach((index, chunk) -> {
/*     */           if (isWorldMapEnabled() && isWorldMapImageVisibleToAnyPlayer(players, index, this.worldMapSettings)) {
/*     */             chunk.keepAlive.set(60);
/*     */             
/*     */             return;
/*     */           } 
/*     */           if (chunk.keepAlive.decrementAndGet() <= 0) {
/*     */             toRemove.add(index);
/*     */           }
/*     */         });
/* 180 */     if (!longOpenHashSet.isEmpty()) {
/* 181 */       longOpenHashSet.forEach(value -> {
/*     */             this.logger.at(Level.FINE).log("Unloading world map image: %s", value);
/*     */             
/*     */             this.images.remove(value);
/*     */           });
/*     */     }
/*     */     
/* 188 */     int toRemoveSize = longOpenHashSet.size();
/* 189 */     if (toRemoveSize > 0) {
/* 190 */       this.logger.at(Level.FINE).log("Cleaned %s world map images from memory, with %s images remaining in memory.", toRemoveSize, imagesCount - toRemoveSize);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isWorldMapEnabled() {
/* 195 */     return (this.worldMapSettings.getSettingsPacket()).enabled;
/*     */   }
/*     */   
/*     */   public static boolean isWorldMapImageVisibleToAnyPlayer(@Nonnull List<Player> players, long imageIndex, @Nonnull WorldMapSettings settings) {
/* 199 */     for (Player player : players) {
/* 200 */       int viewRadius = settings.getViewRadius(player.getViewRadius());
/* 201 */       if (player.getWorldMapTracker().shouldBeVisible(viewRadius, imageIndex)) return true; 
/*     */     } 
/* 203 */     return false;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public World getWorld() {
/* 208 */     return this.world;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public WorldMapSettings getWorldMapSettings() {
/* 213 */     return this.worldMapSettings;
/*     */   }
/*     */   
/*     */   public Map<String, MarkerProvider> getMarkerProviders() {
/* 217 */     return this.markerProviders;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMarkerProvider(@Nonnull String key, @Nonnull MarkerProvider provider) {
/* 222 */     this.markerProviders.put(key, provider);
/*     */   }
/*     */   
/*     */   public Map<String, MapMarker> getPointsOfInterest() {
/* 226 */     return this.pointsOfInterest;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MapImage getImageIfInMemory(int x, int z) {
/* 231 */     return getImageIfInMemory(ChunkUtil.indexChunk(x, z));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MapImage getImageIfInMemory(long index) {
/* 236 */     ImageEntry pair = (ImageEntry)this.images.get(index);
/* 237 */     return (pair != null) ? pair.image : null;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<MapImage> getImageAsync(int x, int z) {
/* 242 */     return getImageAsync(ChunkUtil.indexChunk(x, z));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CompletableFuture<MapImage> getImageAsync(long index) {
/* 247 */     ImageEntry pair = (ImageEntry)this.images.get(index);
/* 248 */     MapImage image = (pair != null) ? pair.image : null;
/* 249 */     if (image != null) return CompletableFuture.completedFuture(image);
/*     */     
/* 251 */     CompletableFuture<MapImage> gen = (CompletableFuture<MapImage>)this.generating.get(index);
/* 252 */     if (gen != null) return gen;
/*     */     
/* 254 */     int imageSize = MathUtil.fastFloor(32.0F * this.worldMapSettings.getImageScale());
/*     */     
/* 256 */     LongOpenHashSet longOpenHashSet = new LongOpenHashSet();
/* 257 */     longOpenHashSet.add(index);
/* 258 */     CompletableFuture<MapImage> future = CompletableFutureUtil._catch(this.generator
/* 259 */         .generate(this.world, imageSize, imageSize, (LongSet)longOpenHashSet)
/*     */ 
/*     */         
/* 262 */         .thenApplyAsync(worldMap -> {
/*     */             MapImage newImage = (MapImage)worldMap.getChunks().get(index);
/*     */             
/*     */             if (this.generating.remove(index) != null) {
/*     */               this.images.put(index, new ImageEntry(newImage));
/*     */             }
/*     */             
/*     */             return newImage;
/*     */           }));
/* 271 */     this.generating.put(index, future);
/* 272 */     return future;
/*     */   }
/*     */ 
/*     */   
/*     */   public void generate() {}
/*     */   
/*     */   public void sendSettings() {
/* 279 */     for (Player player : this.world.getPlayers()) {
/* 280 */       player.getWorldMapTracker().sendSettings(this.world);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean shouldTick() {
/* 285 */     return (this.world.getWorldConfig().isCompassUpdating() || isWorldMapEnabled());
/*     */   }
/*     */   
/*     */   public void updateTickingState(boolean before) {
/* 289 */     boolean after = shouldTick();
/* 290 */     if (before != after) {
/* 291 */       if (after) {
/* 292 */         start();
/*     */       } else {
/* 294 */         stop();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static {
/* 300 */     MarkerReference.CODEC.register("Player", PlayerMarkerReference.class, (Codec)PlayerMarkerReference.CODEC);
/*     */   }
/*     */   
/*     */   public void clearImages() {
/* 304 */     this.images.clear();
/* 305 */     this.generating.clear();
/*     */   }
/*     */   
/*     */   public void clearImagesInChunks(@Nonnull LongSet chunkIndices) {
/* 309 */     chunkIndices.forEach(index -> {
/*     */           this.images.remove(index);
/*     */           this.generating.remove(index);
/*     */         });
/*     */   }
/*     */   
/*     */   public static interface MarkerReference {
/* 316 */     public static final CodecMapCodec<MarkerReference> CODEC = new CodecMapCodec();
/*     */ 
/*     */ 
/*     */     
/*     */     String getMarkerId();
/*     */ 
/*     */     
/*     */     void remove();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PlayerMarkerReference
/*     */     implements MarkerReference
/*     */   {
/*     */     public static final BuilderCodec<PlayerMarkerReference> CODEC;
/*     */     
/*     */     private UUID player;
/*     */     
/*     */     private String world;
/*     */     
/*     */     private String markerId;
/*     */ 
/*     */     
/*     */     static {
/* 340 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PlayerMarkerReference.class, PlayerMarkerReference::new).addField(new KeyedCodec("Player", (Codec)Codec.UUID_BINARY), (playerMarkerReference, uuid) -> playerMarkerReference.player = uuid, playerMarkerReference -> playerMarkerReference.player)).addField(new KeyedCodec("World", (Codec)Codec.STRING), (playerMarkerReference, s) -> playerMarkerReference.world = s, playerMarkerReference -> playerMarkerReference.world)).addField(new KeyedCodec("MarkerId", (Codec)Codec.STRING), (playerMarkerReference, s) -> playerMarkerReference.markerId = s, playerMarkerReference -> playerMarkerReference.markerId)).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private PlayerMarkerReference() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public PlayerMarkerReference(@Nonnull UUID player, @Nonnull String world, @Nonnull String markerId) {
/* 350 */       this.player = player;
/* 351 */       this.world = world;
/* 352 */       this.markerId = markerId;
/*     */     }
/*     */     
/*     */     public UUID getPlayer() {
/* 356 */       return this.player;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getMarkerId() {
/* 361 */       return this.markerId;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 366 */       PlayerRef playerRef = Universe.get().getPlayer(this.player);
/* 367 */       if (playerRef != null) {
/*     */         
/* 369 */         Player playerComponent = (Player)playerRef.getComponent(Player.getComponentType());
/* 370 */         removeMarkerFromOnlinePlayer(playerComponent);
/*     */       } else {
/* 372 */         removeMarkerFromOfflinePlayer();
/*     */       } 
/*     */     }
/*     */     
/*     */     private void removeMarkerFromOnlinePlayer(@Nonnull Player player) {
/* 377 */       PlayerConfigData data = player.getPlayerConfigData();
/*     */ 
/*     */ 
/*     */       
/* 381 */       String world = this.world;
/* 382 */       if (world == null) world = player.getWorld().getName();
/*     */       
/* 384 */       removeMarkerFromData(data, world, this.markerId);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void removeMarkerFromOfflinePlayer() {
/* 391 */       Universe.get().getPlayerStorage().load(this.player)
/* 392 */         .thenApply(holder -> {
/*     */             Player player = (Player)holder.getComponent(Player.getComponentType());
/*     */             
/*     */             PlayerConfigData data = player.getPlayerConfigData();
/*     */             
/*     */             String world = this.world;
/*     */             
/*     */             if (world == null) {
/*     */               world = data.getWorld();
/*     */             }
/*     */             removeMarkerFromData(data, world, this.markerId);
/*     */             return holder;
/* 404 */           }).thenCompose(holder -> Universe.get().getPlayerStorage().save(this.player, holder));
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     private static MapMarker removeMarkerFromData(@Nonnull PlayerConfigData data, @Nonnull String worldName, @Nonnull String markerId) {
/* 409 */       PlayerWorldData perWorldData = data.getPerWorldData(worldName);
/* 410 */       MapMarker[] worldMapMarkers = perWorldData.getWorldMapMarkers();
/* 411 */       if (worldMapMarkers == null) return null;
/*     */       
/* 413 */       int index = -1;
/* 414 */       for (int i = 0; i < worldMapMarkers.length; i++) {
/* 415 */         if ((worldMapMarkers[i]).id.equals(markerId)) {
/* 416 */           index = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 421 */       if (index == -1) return null;
/*     */       
/* 423 */       MapMarker[] newWorldMapMarkers = new MapMarker[worldMapMarkers.length - 1];
/* 424 */       System.arraycopy(worldMapMarkers, 0, newWorldMapMarkers, 0, index);
/* 425 */       System.arraycopy(worldMapMarkers, index + 1, newWorldMapMarkers, index, newWorldMapMarkers.length - index);
/* 426 */       perWorldData.setWorldMapMarkers(newWorldMapMarkers);
/*     */       
/* 428 */       return worldMapMarkers[index];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static PlayerMarkerReference createPlayerMarker(@Nonnull Ref<EntityStore> playerRef, @Nonnull MapMarker marker, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 436 */     World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/*     */     
/* 438 */     Player playerComponent = (Player)componentAccessor.getComponent(playerRef, Player.getComponentType());
/* 439 */     assert playerComponent != null;
/*     */     
/* 441 */     UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(playerRef, UUIDComponent.getComponentType());
/* 442 */     assert uuidComponent != null;
/*     */     
/* 444 */     PlayerWorldData perWorldData = playerComponent.getPlayerConfigData().getPerWorldData(world.getName());
/* 445 */     MapMarker[] worldMapMarkers = perWorldData.getWorldMapMarkers();
/*     */     
/* 447 */     perWorldData.setWorldMapMarkers((MapMarker[])ArrayUtil.append((Object[])worldMapMarkers, marker));
/*     */     
/* 449 */     return new PlayerMarkerReference(uuidComponent.getUuid(), world.getName(), marker.id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ImageEntry
/*     */   {
/* 460 */     private final AtomicInteger keepAlive = new AtomicInteger();
/*     */     private final MapImage image;
/*     */     
/*     */     public ImageEntry(MapImage image) {
/* 464 */       this.image = image;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface MarkerProvider {
/*     */     void update(World param1World, GameplayConfig param1GameplayConfig, WorldMapTracker param1WorldMapTracker, int param1Int1, int param1Int2, int param1Int3);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\worldmap\WorldMapManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */