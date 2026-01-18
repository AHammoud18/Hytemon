/*     */ package com.hypixel.hytale.server.core.universe.world;
/*     */ import com.hypixel.hytale.common.fastutil.HLongOpenHashSet;
/*     */ import com.hypixel.hytale.common.fastutil.HLongSet;
/*     */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.system.EcsEvent;
/*     */ import com.hypixel.hytale.function.function.TriFunction;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.iterator.CircleSpiralIterator;
/*     */ import com.hypixel.hytale.math.shape.Box2D;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Position;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.ClearWorldMap;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapChunk;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapImage;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.UpdateWorldMap;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.UpdateWorldMapSettings;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.event.events.ecs.DiscoverZoneEvent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapManager;
/*     */ import com.hypixel.hytale.server.core.universe.world.worldmap.WorldMapSettings;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class WorldMapTracker implements Tickable {
/*  52 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float UPDATE_SPEED = 1.0F;
/*     */ 
/*     */   
/*     */   public static final float MIN_PLAYER_MARKER_UPDATE_SPEED = 10.0F;
/*     */ 
/*     */   
/*     */   public static final int RADIUS_MAX = 512;
/*     */ 
/*     */   
/*     */   public static final int EMPTY_UPDATE_WORLD_MAP_SIZE = 13;
/*     */ 
/*     */   
/*     */   private static final int EMPTY_MAP_CHUNK_SIZE = 10;
/*     */ 
/*     */   
/*     */   private static final int FULL_MAP_CHUNK_SIZE = 23;
/*     */ 
/*     */   
/*     */   public static final int MAX_IMAGE_GENERATION = 20;
/*     */ 
/*     */   
/*     */   public static final int MAX_FRAME = 2621440;
/*     */ 
/*     */   
/*     */   private final Player player;
/*     */ 
/*     */   
/*  83 */   private final CircleSpiralIterator spiralIterator = new CircleSpiralIterator();
/*     */   
/*  85 */   private final ReentrantReadWriteLock loadedLock = new ReentrantReadWriteLock();
/*     */   
/*  87 */   private final HLongSet loaded = (HLongSet)new HLongOpenHashSet();
/*  88 */   private final HLongSet pendingReloadChunks = (HLongSet)new HLongOpenHashSet();
/*     */   
/*  90 */   private final Long2ObjectOpenHashMap<CompletableFuture<MapImage>> pendingReloadFutures = new Long2ObjectOpenHashMap();
/*  91 */   private final Map<String, MapMarker> sentMarkers = new ConcurrentHashMap<>();
/*     */   
/*     */   private float updateTimer;
/*     */   
/*     */   private float playerMarkersUpdateTimer;
/*     */   
/*     */   private Integer viewRadiusOverride;
/*     */   
/*     */   private boolean started;
/*     */   
/*     */   private int sentViewRadius;
/*     */   
/*     */   private int lastChunkX;
/*     */   
/*     */   private int lastChunkZ;
/*     */   
/*     */   @Nullable
/*     */   private String currentBiomeName;
/*     */   
/*     */   @Nullable
/*     */   private ZoneDiscoveryInfo currentZone;
/*     */   
/*     */   private boolean allowTeleportToCoordinates = true;
/*     */   private boolean allowTeleportToMarkers = true;
/*     */   private boolean clientHasWorldMapVisible;
/*     */   private Predicate<PlayerRef> playerMapFilter;
/*     */   @Nonnull
/* 118 */   private final Set<String> tempToRemove = new HashSet<>();
/*     */   @Nonnull
/* 120 */   private final Set<MapMarker> tempToAdd = new HashSet<>();
/*     */   @Nonnull
/* 122 */   private final Set<String> tempTestedMarkers = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private TransformComponent transformComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldMapTracker(@Nonnull Player player) {
/* 135 */     this.player = player;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt) {
/*     */     int viewRadius;
/* 141 */     if (!this.started) {
/* 142 */       this.started = true;
/* 143 */       LOGGER.at(Level.INFO).log("Started Generating Map!");
/*     */     } 
/*     */     
/* 146 */     World world = this.player.getWorld();
/* 147 */     if (world == null) {
/*     */       return;
/*     */     }
/* 150 */     if (this.transformComponent == null) {
/* 151 */       this.transformComponent = this.player.getTransformComponent();
/* 152 */       if (this.transformComponent == null)
/*     */         return; 
/*     */     } 
/* 155 */     WorldMapManager worldMapManager = world.getWorldMapManager();
/* 156 */     WorldMapSettings worldMapSettings = worldMapManager.getWorldMapSettings();
/*     */ 
/*     */     
/* 159 */     if (this.viewRadiusOverride != null) {
/* 160 */       viewRadius = this.viewRadiusOverride.intValue();
/*     */     } else {
/* 162 */       viewRadius = worldMapSettings.getViewRadius(this.player.getViewRadius());
/*     */     } 
/*     */     
/* 165 */     Vector3d position = this.transformComponent.getPosition();
/* 166 */     int playerX = MathUtil.floor(position.getX());
/* 167 */     int playerZ = MathUtil.floor(position.getZ());
/* 168 */     int playerChunkX = playerX >> 5;
/* 169 */     int playerChunkZ = playerZ >> 5;
/*     */     
/* 171 */     if (world.isCompassUpdating()) {
/* 172 */       this.playerMarkersUpdateTimer -= dt;
/* 173 */       updatePointsOfInterest(world, viewRadius, playerChunkX, playerChunkZ);
/*     */     } 
/*     */     
/* 176 */     if (worldMapManager.isWorldMapEnabled()) {
/* 177 */       updateWorldMap(world, dt, worldMapSettings, viewRadius, playerChunkX, playerChunkZ);
/*     */     }
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
/*     */   public void updateCurrentZoneAndBiome(@Nonnull Ref<EntityStore> ref, @Nullable ZoneDiscoveryInfo zoneDiscoveryInfo, @Nullable String biomeName, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 193 */     this.currentBiomeName = biomeName;
/* 194 */     this.currentZone = zoneDiscoveryInfo;
/*     */     
/* 196 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 197 */     assert playerComponent != null;
/*     */ 
/*     */ 
/*     */     
/* 201 */     if (!playerComponent.isWaitingForClientReady()) {
/*     */ 
/*     */       
/* 204 */       World world = ((EntityStore)componentAccessor.getExternalData()).getWorld();
/* 205 */       if (zoneDiscoveryInfo != null && discoverZone(world, zoneDiscoveryInfo.regionName()))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 210 */         onZoneDiscovered(ref, zoneDiscoveryInfo, componentAccessor);
/*     */       }
/*     */     } 
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
/*     */   private void onZoneDiscovered(@Nonnull Ref<EntityStore> ref, @Nonnull ZoneDiscoveryInfo zoneDiscoveryInfo, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 226 */     ZoneDiscoveryInfo discoverZoneEventInfo = zoneDiscoveryInfo.clone();
/* 227 */     DiscoverZoneEvent.Display discoverZoneEvent = new DiscoverZoneEvent.Display(discoverZoneEventInfo);
/* 228 */     componentAccessor.invoke(ref, (EcsEvent)discoverZoneEvent);
/*     */ 
/*     */     
/* 231 */     if (discoverZoneEvent.isCancelled() || !discoverZoneEventInfo.display()) {
/*     */       return;
/*     */     }
/*     */     
/* 235 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/* 236 */     assert playerRefComponent != null;
/*     */     
/* 238 */     EventTitleUtil.showEventTitleToPlayer(playerRefComponent, 
/* 239 */         Message.translation(String.format("server.map.region.%s", new Object[] { discoverZoneEventInfo.regionName()
/* 240 */             })), Message.translation(String.format("server.map.zone.%s", new Object[] { discoverZoneEventInfo.zoneName() })), discoverZoneEventInfo
/* 241 */         .major(), discoverZoneEventInfo
/* 242 */         .icon(), discoverZoneEventInfo
/* 243 */         .duration(), discoverZoneEventInfo
/* 244 */         .fadeInDuration(), discoverZoneEventInfo
/* 245 */         .fadeOutDuration());
/*     */     
/* 247 */     String discoverySoundEventId = discoverZoneEventInfo.discoverySoundEventId();
/* 248 */     if (discoverySoundEventId != null) {
/* 249 */       int assetIndex = SoundEvent.getAssetMap().getIndex(discoverySoundEventId);
/* 250 */       if (assetIndex != Integer.MIN_VALUE)
/* 251 */         SoundUtil.playSoundEvent2d(ref, assetIndex, SoundCategory.UI, componentAccessor); 
/*     */     } 
/*     */   }
/*     */   public static final class ZoneDiscoveryInfo extends Record {
/*     */     @Nonnull
/*     */     private final String zoneName; @Nonnull
/*     */     private final String regionName; private final boolean display; @Nullable
/*     */     private final String discoverySoundEventId; @Nullable
/*     */     private final String icon; private final boolean major; private final float duration; private final float fadeInDuration; private final float fadeOutDuration;
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/universe/world/WorldMapTracker$ZoneDiscoveryInfo;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #269	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/universe/world/WorldMapTracker$ZoneDiscoveryInfo;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/universe/world/WorldMapTracker$ZoneDiscoveryInfo;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #269	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/universe/world/WorldMapTracker$ZoneDiscoveryInfo;
/*     */     }
/*     */     
/* 269 */     public ZoneDiscoveryInfo(@Nonnull String zoneName, @Nonnull String regionName, boolean display, @Nullable String discoverySoundEventId, @Nullable String icon, boolean major, float duration, float fadeInDuration, float fadeOutDuration) { this.zoneName = zoneName; this.regionName = regionName; this.display = display; this.discoverySoundEventId = discoverySoundEventId; this.icon = icon; this.major = major; this.duration = duration; this.fadeInDuration = fadeInDuration; this.fadeOutDuration = fadeOutDuration; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/universe/world/WorldMapTracker$ZoneDiscoveryInfo;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #269	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/universe/world/WorldMapTracker$ZoneDiscoveryInfo;
/* 269 */       //   0	8	1	o	Ljava/lang/Object; } @Nonnull public String zoneName() { return this.zoneName; } @Nonnull public String regionName() { return this.regionName; } public boolean display() { return this.display; } @Nullable public String discoverySoundEventId() { return this.discoverySoundEventId; } @Nullable public String icon() { return this.icon; } public boolean major() { return this.major; } public float duration() { return this.duration; } public float fadeInDuration() { return this.fadeInDuration; } public float fadeOutDuration() { return this.fadeOutDuration; }
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
/*     */     @Nonnull
/*     */     public ZoneDiscoveryInfo clone() {
/* 284 */       return new ZoneDiscoveryInfo(this.zoneName, this.regionName, this.display, this.discoverySoundEventId, this.icon, this.major, this.duration, this.fadeInDuration, this.fadeOutDuration);
/*     */     }
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateWorldMap(@Nonnull World world, float dt, @Nonnull WorldMapSettings worldMapSettings, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 308 */     processPendingReloadChunks(world);
/*     */     
/* 310 */     Box2D worldMapArea = worldMapSettings.getWorldMapArea();
/* 311 */     if (worldMapArea == null) {
/* 312 */       int xDiff = Math.abs(this.lastChunkX - playerChunkX);
/* 313 */       int zDiff = Math.abs(this.lastChunkZ - playerChunkZ);
/* 314 */       int chunkMoveDistance = (xDiff > 0 || zDiff > 0) ? (int)Math.ceil(Math.sqrt((xDiff * xDiff + zDiff * zDiff))) : 0;
/* 315 */       this.sentViewRadius = Math.max(0, this.sentViewRadius - chunkMoveDistance);
/*     */       
/* 317 */       this.lastChunkX = playerChunkX;
/* 318 */       this.lastChunkZ = playerChunkZ;
/*     */ 
/*     */       
/* 321 */       this.updateTimer -= dt;
/* 322 */       if (this.updateTimer > 0.0F) {
/*     */         return;
/*     */       }
/* 325 */       if (this.sentViewRadius != chunkViewRadius) {
/* 326 */         if (this.sentViewRadius > chunkViewRadius) this.sentViewRadius = chunkViewRadius;
/*     */         
/* 328 */         unloadImages(chunkViewRadius, playerChunkX, playerChunkZ);
/*     */ 
/*     */ 
/*     */         
/* 332 */         if (this.sentViewRadius < chunkViewRadius) {
/* 333 */           loadImages(world, chunkViewRadius, playerChunkX, playerChunkZ, 20);
/*     */         }
/*     */       } else {
/* 336 */         this.updateTimer = 1.0F;
/*     */       } 
/*     */     } else {
/*     */       
/* 340 */       this.updateTimer -= dt;
/* 341 */       if (this.updateTimer > 0.0F) {
/*     */         return;
/*     */       }
/* 344 */       loadWorldMap(world, worldMapArea, 20);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updatePointsOfInterest(@Nonnull World world, int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 350 */     if (this.transformComponent == null)
/*     */       return; 
/* 352 */     WorldMapManager worldMapManager = world.getWorldMapManager();
/* 353 */     Map<String, WorldMapManager.MarkerProvider> markerProviders = worldMapManager.getMarkerProviders();
/*     */     
/* 355 */     this.tempToAdd.clear();
/* 356 */     this.tempTestedMarkers.clear();
/*     */     
/* 358 */     for (WorldMapManager.MarkerProvider provider : markerProviders.values()) {
/* 359 */       provider.update(world, world.getGameplayConfig(), this, chunkViewRadius, playerChunkX, playerChunkZ);
/*     */     }
/*     */ 
/*     */     
/* 363 */     this.tempToRemove.clear();
/* 364 */     this.tempToRemove.addAll(this.sentMarkers.keySet());
/*     */     
/* 366 */     if (!this.tempTestedMarkers.isEmpty()) {
/* 367 */       this.tempToRemove.removeAll(this.tempTestedMarkers);
/*     */     }
/*     */     
/* 370 */     for (String removedMarkerId : this.tempToRemove) {
/* 371 */       this.sentMarkers.remove(removedMarkerId);
/*     */     }
/*     */     
/* 374 */     if (!this.tempToAdd.isEmpty() || !this.tempToRemove.isEmpty()) {
/* 375 */       MapMarker[] addedMarkers = !this.tempToAdd.isEmpty() ? (MapMarker[])this.tempToAdd.toArray(x$0 -> new MapMarker[x$0]) : null;
/* 376 */       String[] removedMarkers = !this.tempToRemove.isEmpty() ? (String[])this.tempToRemove.toArray(x$0 -> new String[x$0]) : null;
/*     */       
/* 378 */       this.player.getPlayerConnection().writeNoCache((Packet)new UpdateWorldMap(null, addedMarkers, removedMarkers));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trySendMarker(int chunkViewRadius, int playerChunkX, int playerChunkZ, @Nonnull MapMarker marker) {
/* 389 */     trySendMarker(chunkViewRadius, playerChunkX, playerChunkZ, marker.transform.position.x, marker.transform.position.z, marker.transform.orientation.yaw, marker.id, marker.name, marker, (id, name, m) -> m);
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
/*     */   public <T> void trySendMarker(int chunkViewRadius, int playerChunkX, int playerChunkZ, @Nonnull Vector3d markerPos, float markerYaw, @Nonnull String markerId, @Nonnull String markerDisplayName, @Nonnull T param, @Nonnull TriFunction<String, String, T, MapMarker> markerSupplier) {
/* 406 */     trySendMarker(chunkViewRadius, playerChunkX, playerChunkZ, markerPos.x, markerPos.z, markerYaw, markerId, markerDisplayName, param, markerSupplier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> void trySendMarker(int chunkViewRadius, int playerChunkX, int playerChunkZ, double markerX, double markerZ, float markerYaw, @Nonnull String markerId, @Nonnull String markerName, @Nonnull T param, @Nonnull TriFunction<String, String, T, MapMarker> markerSupplier) {
/* 415 */     int markerXBlock = MathUtil.floor(markerX);
/* 416 */     int markerZBlock = MathUtil.floor(markerZ);
/*     */ 
/*     */     
/* 419 */     boolean shouldBeVisible = (chunkViewRadius == -1 || shouldBeVisible(chunkViewRadius, markerXBlock >> 5, markerZBlock >> 5, playerChunkX, playerChunkZ));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 426 */     if (!shouldBeVisible)
/*     */       return; 
/* 428 */     this.tempTestedMarkers.add(markerId);
/*     */     
/* 430 */     boolean needsUpdate = false;
/* 431 */     MapMarker oldMarker = this.sentMarkers.get(markerId);
/* 432 */     if (oldMarker != null) {
/* 433 */       if (!markerName.equals(oldMarker.name)) needsUpdate = true;
/*     */       
/* 435 */       if (!needsUpdate) {
/* 436 */         double distance = Math.abs(oldMarker.transform.orientation.yaw - markerYaw);
/* 437 */         needsUpdate = (distance > 0.05D || (this.playerMarkersUpdateTimer < 0.0F && distance > 0.001D));
/*     */       } 
/*     */       
/* 440 */       if (!needsUpdate) {
/* 441 */         Position oldPosition = oldMarker.transform.position;
/* 442 */         double distance = Vector2d.distance(oldPosition.x, oldPosition.z, markerX, markerZ);
/* 443 */         needsUpdate = (distance > 5.0D || (this.playerMarkersUpdateTimer < 0.0F && distance > 0.1D));
/*     */       } 
/*     */     } else {
/* 446 */       needsUpdate = true;
/*     */     } 
/*     */     
/* 449 */     if (!needsUpdate)
/*     */       return; 
/* 451 */     MapMarker marker = (MapMarker)markerSupplier.apply(markerId, markerName, param);
/* 452 */     this.sentMarkers.put(markerId, marker);
/* 453 */     this.tempToAdd.add(marker);
/*     */   }
/*     */   
/*     */   private void unloadImages(int chunkViewRadius, int playerChunkX, int playerChunkZ) {
/* 457 */     List<MapChunk> currentUnloadList = null;
/* 458 */     List<List<MapChunk>> allUnloadLists = null;
/*     */     
/* 460 */     this.loadedLock.writeLock().lock(); try {
/*     */       ObjectArrayList<MapChunk> objectArrayList; ObjectArrayList<ObjectArrayList<MapChunk>> objectArrayList1;
/* 462 */       int packetSize = 2621427;
/*     */       
/* 464 */       LongIterator iterator = this.loaded.iterator();
/* 465 */       while (iterator.hasNext()) {
/* 466 */         long chunkCoordinates = iterator.nextLong();
/*     */         
/* 468 */         int mapChunkX = ChunkUtil.xOfChunkIndex(chunkCoordinates);
/* 469 */         int mapChunkZ = ChunkUtil.zOfChunkIndex(chunkCoordinates);
/*     */         
/* 471 */         if (shouldBeVisible(chunkViewRadius, playerChunkX, playerChunkZ, mapChunkX, mapChunkZ)) {
/*     */           continue;
/*     */         }
/*     */         
/* 475 */         if (currentUnloadList == null) {
/* 476 */           objectArrayList = new ObjectArrayList(packetSize / 10);
/*     */         }
/* 478 */         objectArrayList.add(new MapChunk(mapChunkX, mapChunkZ, null));
/* 479 */         packetSize -= 10;
/* 480 */         iterator.remove();
/*     */ 
/*     */         
/* 483 */         if (packetSize < 10) {
/* 484 */           packetSize = 2621427;
/*     */           
/* 486 */           if (allUnloadLists == null) {
/* 487 */             objectArrayList1 = new ObjectArrayList(this.loaded.size() / packetSize / 10);
/*     */           }
/* 489 */           objectArrayList1.add(objectArrayList);
/*     */           
/* 491 */           objectArrayList = new ObjectArrayList(packetSize / 10);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 496 */       if (objectArrayList1 != null) {
/* 497 */         for (List<MapChunk> unloadList : objectArrayList1) {
/* 498 */           writeUpdatePacket(unloadList);
/*     */         }
/*     */       }
/* 501 */       writeUpdatePacket((List<MapChunk>)objectArrayList);
/*     */     } finally {
/* 503 */       this.loadedLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processPendingReloadChunks(@Nonnull World world) {
/* 508 */     List<MapChunk> chunksToSend = null;
/*     */     
/* 510 */     this.loadedLock.writeLock().lock(); try {
/*     */       ObjectArrayList<MapChunk> objectArrayList;
/* 512 */       if (this.pendingReloadChunks.isEmpty())
/*     */         return; 
/* 514 */       int imageSize = MathUtil.fastFloor(32.0F * world.getWorldMapManager().getWorldMapSettings().getImageScale());
/* 515 */       int fullMapChunkSize = 23 + 4 * imageSize * imageSize;
/* 516 */       int packetSize = 2621427;
/*     */       
/* 518 */       LongIterator iterator = this.pendingReloadChunks.iterator();
/* 519 */       while (iterator.hasNext()) {
/* 520 */         long chunkCoordinates = iterator.nextLong();
/*     */ 
/*     */         
/* 523 */         CompletableFuture<MapImage> future = (CompletableFuture<MapImage>)this.pendingReloadFutures.get(chunkCoordinates);
/* 524 */         if (future == null) {
/* 525 */           future = world.getWorldMapManager().getImageAsync(chunkCoordinates);
/* 526 */           this.pendingReloadFutures.put(chunkCoordinates, future);
/*     */         } 
/*     */         
/* 529 */         if (!future.isDone()) {
/*     */           continue;
/*     */         }
/*     */         
/* 533 */         iterator.remove();
/* 534 */         this.pendingReloadFutures.remove(chunkCoordinates);
/*     */         
/* 536 */         if (chunksToSend == null) {
/* 537 */           objectArrayList = new ObjectArrayList(packetSize / fullMapChunkSize);
/*     */         }
/*     */         
/* 540 */         int mapChunkX = ChunkUtil.xOfChunkIndex(chunkCoordinates);
/* 541 */         int mapChunkZ = ChunkUtil.zOfChunkIndex(chunkCoordinates);
/* 542 */         objectArrayList.add(new MapChunk(mapChunkX, mapChunkZ, future.getNow(null)));
/* 543 */         this.loaded.add(chunkCoordinates);
/* 544 */         packetSize -= fullMapChunkSize;
/*     */         
/* 546 */         if (packetSize < fullMapChunkSize) {
/* 547 */           writeUpdatePacket((List<MapChunk>)objectArrayList);
/* 548 */           objectArrayList = new ObjectArrayList(2621440 - 13 / fullMapChunkSize);
/* 549 */           packetSize = 2621427;
/*     */         } 
/*     */       } 
/*     */       
/* 553 */       writeUpdatePacket((List<MapChunk>)objectArrayList);
/*     */     } finally {
/* 555 */       this.loadedLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private int loadImages(@Nonnull World world, int chunkViewRadius, int playerChunkX, int playerChunkZ, int maxGeneration) {
/* 560 */     List<MapChunk> currentLoadList = null;
/* 561 */     List<List<MapChunk>> allLoadLists = null;
/*     */     
/* 563 */     this.loadedLock.writeLock().lock(); try {
/*     */       ObjectArrayList<MapChunk> objectArrayList; ObjectArrayList<ObjectArrayList<MapChunk>> objectArrayList1;
/* 565 */       int packetSize = 2621427;
/* 566 */       int imageSize = MathUtil.fastFloor(32.0F * world.getWorldMapManager().getWorldMapSettings().getImageScale());
/* 567 */       int fullMapChunkSize = 23 + 4 * imageSize * imageSize;
/*     */       
/* 569 */       boolean areAllLoaded = true;
/*     */       
/* 571 */       this.spiralIterator.init(playerChunkX, playerChunkZ, this.sentViewRadius, chunkViewRadius);
/* 572 */       while (maxGeneration > 0 && this.spiralIterator.hasNext()) {
/* 573 */         long chunkCoordinates = this.spiralIterator.next();
/* 574 */         if (!this.loaded.contains(chunkCoordinates)) {
/* 575 */           areAllLoaded = false;
/*     */ 
/*     */           
/* 578 */           CompletableFuture<MapImage> future = world.getWorldMapManager().getImageAsync(chunkCoordinates);
/* 579 */           if (!future.isDone()) {
/* 580 */             maxGeneration--;
/*     */             
/*     */             continue;
/*     */           } 
/* 584 */           if (this.loaded.add(chunkCoordinates)) {
/*     */ 
/*     */             
/* 587 */             if (currentLoadList == null) {
/* 588 */               objectArrayList = new ObjectArrayList(packetSize / fullMapChunkSize);
/*     */             }
/*     */             
/* 591 */             int mapChunkX = ChunkUtil.xOfChunkIndex(chunkCoordinates);
/* 592 */             int mapChunkZ = ChunkUtil.zOfChunkIndex(chunkCoordinates);
/* 593 */             objectArrayList.add(new MapChunk(mapChunkX, mapChunkZ, future.getNow(null)));
/* 594 */             packetSize -= fullMapChunkSize;
/*     */ 
/*     */             
/* 597 */             if (packetSize < fullMapChunkSize) {
/* 598 */               packetSize = 2621427;
/*     */               
/* 600 */               if (allLoadLists == null) {
/* 601 */                 objectArrayList1 = new ObjectArrayList();
/*     */               }
/* 603 */               objectArrayList1.add(objectArrayList);
/*     */               
/* 605 */               objectArrayList = new ObjectArrayList(packetSize / fullMapChunkSize);
/*     */             } 
/*     */           }  continue;
/* 608 */         }  if (areAllLoaded) {
/* 609 */           this.sentViewRadius = this.spiralIterator.getCompletedRadius();
/*     */         }
/*     */       } 
/* 612 */       if (areAllLoaded) {
/* 613 */         this.sentViewRadius = this.spiralIterator.getCompletedRadius();
/*     */       }
/*     */ 
/*     */       
/* 617 */       if (objectArrayList1 != null) {
/* 618 */         for (List<MapChunk> unloadList : objectArrayList1) {
/* 619 */           writeUpdatePacket(unloadList);
/*     */         }
/*     */       }
/* 622 */       writeUpdatePacket((List<MapChunk>)objectArrayList);
/*     */     } finally {
/* 624 */       this.loadedLock.writeLock().unlock();
/*     */     } 
/* 626 */     return maxGeneration;
/*     */   }
/*     */   private int loadWorldMap(@Nonnull World world, @Nonnull Box2D worldMapArea, int maxGeneration) {
/*     */     ObjectArrayList<MapChunk> objectArrayList;
/*     */     ObjectArrayList<ObjectArrayList<MapChunk>> objectArrayList1;
/* 631 */     List<MapChunk> currentLoadList = null;
/* 632 */     List<List<MapChunk>> allLoadLists = null;
/*     */     
/* 634 */     this.loadedLock.writeLock().lock();
/*     */     
/* 636 */     try { int packetSize = 2621427;
/* 637 */       int imageSize = MathUtil.fastFloor(32.0F * world.getWorldMapManager().getWorldMapSettings().getImageScale());
/* 638 */       int fullMapChunkSize = 23 + 4 * imageSize * imageSize;
/*     */       
/* 640 */       for (int mapChunkX = MathUtil.floor(worldMapArea.min.x); mapChunkX < MathUtil.ceil(worldMapArea.max.x) && maxGeneration > 0; mapChunkX++) {
/* 641 */         for (int mapChunkZ = MathUtil.floor(worldMapArea.min.y); mapChunkZ < MathUtil.ceil(worldMapArea.max.y) && maxGeneration > 0; mapChunkZ++) {
/* 642 */           long chunkCoordinates = ChunkUtil.indexChunk(mapChunkX, mapChunkZ);
/* 643 */           if (!this.loaded.contains(chunkCoordinates)) {
/*     */             
/* 645 */             CompletableFuture<MapImage> future = CompletableFutureUtil._catch(world.getWorldMapManager().getImageAsync(chunkCoordinates));
/* 646 */             if (!future.isDone()) {
/* 647 */               maxGeneration--;
/*     */             
/*     */             }
/*     */             else {
/*     */ 
/*     */               
/* 653 */               if (currentLoadList == null) {
/* 654 */                 objectArrayList = new ObjectArrayList(packetSize / fullMapChunkSize);
/*     */               }
/* 656 */               objectArrayList.add(new MapChunk(mapChunkX, mapChunkZ, future.getNow(null)));
/* 657 */               this.loaded.add(chunkCoordinates);
/* 658 */               packetSize -= fullMapChunkSize;
/*     */ 
/*     */               
/* 661 */               if (packetSize < fullMapChunkSize) {
/* 662 */                 packetSize = 2621427;
/*     */                 
/* 664 */                 if (allLoadLists == null) {
/* 665 */                   objectArrayList1 = new ObjectArrayList(Math.max(packetSize / fullMapChunkSize, 1));
/*     */                 }
/* 667 */                 objectArrayList1.add(objectArrayList);
/*     */                 
/* 669 */                 objectArrayList = new ObjectArrayList(packetSize / fullMapChunkSize);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }  }
/* 675 */     finally { this.loadedLock.writeLock().unlock(); }
/*     */ 
/*     */     
/* 678 */     if (objectArrayList1 != null) {
/* 679 */       for (List<MapChunk> unloadList : objectArrayList1) {
/* 680 */         writeUpdatePacket(unloadList);
/*     */       }
/*     */     }
/* 683 */     writeUpdatePacket((List<MapChunk>)objectArrayList);
/*     */     
/* 685 */     return maxGeneration;
/*     */   }
/*     */   
/*     */   private void writeUpdatePacket(@Nullable List<MapChunk> list) {
/* 689 */     if (list != null) {
/* 690 */       UpdateWorldMap packet = new UpdateWorldMap((MapChunk[])list.toArray(x$0 -> new MapChunk[x$0]), null, null);
/* 691 */       LOGGER.at(Level.FINE).log("Sending world map update to %s - %d chunks", this.player.getUuid(), list.size());
/* 692 */       this.player.getPlayerConnection().write((Packet)packet);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<String, MapMarker> getSentMarkers() {
/* 698 */     return this.sentMarkers;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Player getPlayer() {
/* 703 */     return this.player;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 707 */     this.loadedLock.writeLock().lock();
/*     */     try {
/* 709 */       this.loaded.clear();
/* 710 */       this.sentViewRadius = 0;
/* 711 */       this.sentMarkers.clear();
/*     */     } finally {
/* 713 */       this.loadedLock.writeLock().unlock();
/*     */     } 
/* 715 */     this.player.getPlayerConnection().write((Packet)new ClearWorldMap());
/*     */   }
/*     */   
/*     */   public void clearChunks(@Nonnull LongSet chunkIndices) {
/* 719 */     this.loadedLock.writeLock().lock();
/*     */     try {
/* 721 */       chunkIndices.forEach(index -> {
/*     */             this.loaded.remove(index);
/*     */             
/*     */             this.pendingReloadChunks.add(index);
/*     */             this.pendingReloadFutures.remove(index);
/*     */           });
/*     */     } finally {
/* 728 */       this.loadedLock.writeLock().unlock();
/*     */     } 
/* 730 */     this.updateTimer = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendSettings(@Nonnull World world) {
/* 736 */     UpdateWorldMapSettings worldMapSettingsPacket = new UpdateWorldMapSettings(world.getWorldMapManager().getWorldMapSettings().getSettingsPacket());
/*     */     
/* 738 */     world.execute(() -> {
/*     */           Store<EntityStore> store = world.getEntityStore().getStore();
/*     */           
/*     */           Ref<EntityStore> ref = this.player.getReference();
/*     */           if (ref == null) {
/*     */             return;
/*     */           }
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           assert playerComponent != null;
/*     */           PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*     */           assert playerRefComponent != null;
/* 749 */           worldMapSettingsPacket.allowTeleportToCoordinates = (this.allowTeleportToCoordinates && playerComponent.getGameMode() != GameMode.Adventure);
/* 750 */           worldMapSettingsPacket.allowTeleportToMarkers = (this.allowTeleportToMarkers && playerComponent.getGameMode() != GameMode.Adventure);
/*     */           playerRefComponent.getPacketHandler().write((Packet)worldMapSettingsPacket);
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
/*     */   private boolean hasDiscoveredZone(@Nonnull String zoneName) {
/* 764 */     return this.player.getPlayerConfigData().getDiscoveredZones().contains(zoneName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean discoverZone(@Nonnull World world, @Nonnull String zoneName) {
/* 775 */     Set<String> discoveredZones = this.player.getPlayerConfigData().getDiscoveredZones();
/* 776 */     if (!discoveredZones.contains(zoneName)) {
/* 777 */       discoveredZones = new HashSet<>(discoveredZones);
/* 778 */       discoveredZones.add(zoneName);
/* 779 */       this.player.getPlayerConfigData().setDiscoveredZones(discoveredZones);
/* 780 */       sendSettings(world);
/*     */       
/* 782 */       return true;
/*     */     } 
/* 784 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean undiscoverZone(@Nonnull World world, @Nonnull String zoneName) {
/* 795 */     Set<String> discoveredZones = this.player.getPlayerConfigData().getDiscoveredZones();
/* 796 */     if (discoveredZones.contains(zoneName)) {
/* 797 */       discoveredZones = new HashSet<>(discoveredZones);
/* 798 */       discoveredZones.remove(zoneName);
/* 799 */       this.player.getPlayerConfigData().setDiscoveredZones(discoveredZones);
/* 800 */       sendSettings(world);
/*     */       
/* 802 */       return true;
/*     */     } 
/* 804 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean discoverZones(@Nonnull World world, @Nonnull Set<String> zoneNames) {
/* 815 */     Set<String> discoveredZones = this.player.getPlayerConfigData().getDiscoveredZones();
/* 816 */     if (!discoveredZones.containsAll(zoneNames)) {
/* 817 */       discoveredZones = new HashSet<>(discoveredZones);
/* 818 */       discoveredZones.addAll(zoneNames);
/* 819 */       this.player.getPlayerConfigData().setDiscoveredZones(discoveredZones);
/* 820 */       sendSettings(world);
/*     */       
/* 822 */       return true;
/*     */     } 
/* 824 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean undiscoverZones(@Nonnull World world, @Nonnull Set<String> zoneNames) {
/* 835 */     Set<String> discoveredZones = this.player.getPlayerConfigData().getDiscoveredZones();
/* 836 */     if (discoveredZones.containsAll(zoneNames)) {
/* 837 */       discoveredZones = new HashSet<>(discoveredZones);
/* 838 */       discoveredZones.removeAll(zoneNames);
/* 839 */       this.player.getPlayerConfigData().setDiscoveredZones(discoveredZones);
/* 840 */       sendSettings(world);
/*     */       
/* 842 */       return true;
/*     */     } 
/* 844 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAllowTeleportToCoordinates() {
/* 851 */     return this.allowTeleportToCoordinates;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllowTeleportToCoordinates(@Nonnull World world, boolean allowTeleportToCoordinates) {
/* 861 */     this.allowTeleportToCoordinates = allowTeleportToCoordinates;
/* 862 */     sendSettings(world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAllowTeleportToMarkers() {
/* 869 */     return this.allowTeleportToMarkers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllowTeleportToMarkers(@Nonnull World world, boolean allowTeleportToMarkers) {
/* 879 */     this.allowTeleportToMarkers = allowTeleportToMarkers;
/* 880 */     sendSettings(world);
/*     */   }
/*     */   
/*     */   public Predicate<PlayerRef> getPlayerMapFilter() {
/* 884 */     return this.playerMapFilter;
/*     */   }
/*     */   
/*     */   public void setPlayerMapFilter(Predicate<PlayerRef> playerMapFilter) {
/* 888 */     this.playerMapFilter = playerMapFilter;
/*     */   }
/*     */   
/*     */   public void setClientHasWorldMapVisible(boolean visible) {
/* 892 */     this.clientHasWorldMapVisible = visible;
/*     */   }
/*     */   
/*     */   public boolean shouldUpdatePlayerMarkers() {
/* 896 */     return (this.clientHasWorldMapVisible || this.playerMarkersUpdateTimer < 0.0F);
/*     */   }
/*     */   
/*     */   public void resetPlayerMarkersUpdateTimer() {
/* 900 */     this.playerMarkersUpdateTimer = 10.0F;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Integer getViewRadiusOverride() {
/* 905 */     return this.viewRadiusOverride;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getCurrentBiomeName() {
/* 910 */     return this.currentBiomeName;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ZoneDiscoveryInfo getCurrentZone() {
/* 915 */     return this.currentZone;
/*     */   }
/*     */   
/*     */   public void setViewRadiusOverride(@Nullable Integer viewRadiusOverride) {
/* 919 */     this.viewRadiusOverride = viewRadiusOverride;
/* 920 */     clear();
/*     */   }
/*     */   
/*     */   public int getEffectiveViewRadius(@Nonnull World world) {
/* 924 */     if (this.viewRadiusOverride != null) {
/* 925 */       return this.viewRadiusOverride.intValue();
/*     */     }
/* 927 */     return world.getWorldMapManager().getWorldMapSettings().getViewRadius(this.player.getViewRadius());
/*     */   }
/*     */   
/*     */   public boolean shouldBeVisible(int chunkViewRadius, long chunkCoordinates) {
/* 931 */     if (this.player == null || this.transformComponent == null) return false; 
/* 932 */     Vector3d position = this.transformComponent.getPosition();
/* 933 */     int chunkX = MathUtil.floor(position.getX()) >> 5;
/* 934 */     int chunkZ = MathUtil.floor(position.getZ()) >> 5;
/*     */     
/* 936 */     int x = ChunkUtil.xOfChunkIndex(chunkCoordinates);
/* 937 */     int z = ChunkUtil.zOfChunkIndex(chunkCoordinates);
/* 938 */     return shouldBeVisible(chunkViewRadius, chunkX, chunkZ, x, z);
/*     */   }
/*     */   
/*     */   public void copyFrom(@Nonnull WorldMapTracker worldMapTracker) {
/* 942 */     this.loadedLock.writeLock().lock();
/*     */     try {
/* 944 */       worldMapTracker.loadedLock.readLock().lock();
/*     */       try {
/* 946 */         this.loaded.addAll((LongCollection)worldMapTracker.loaded);
/* 947 */         for (Map.Entry<String, MapMarker> entry : worldMapTracker.sentMarkers.entrySet()) {
/* 948 */           this.sentMarkers.put(entry.getKey(), new MapMarker(entry.getValue()));
/*     */         }
/*     */       } finally {
/* 951 */         worldMapTracker.loadedLock.readLock().unlock();
/*     */       } 
/*     */     } finally {
/* 954 */       this.loadedLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean shouldBeVisible(int chunkViewRadius, int chunkX, int chunkZ, int x, int z) {
/* 959 */     int xDiff = Math.abs(x - chunkX);
/* 960 */     int zDiff = Math.abs(z - chunkZ);
/* 961 */     int distanceSq = xDiff * xDiff + zDiff * zDiff;
/* 962 */     return (distanceSq <= chunkViewRadius * chunkViewRadius);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\WorldMapTracker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */