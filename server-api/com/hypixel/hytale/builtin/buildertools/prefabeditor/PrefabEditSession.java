/*     */ package com.hypixel.hytale.builtin.buildertools.prefabeditor;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.math.codec.Vector3iArrayCodec;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.buildertools.BuilderToolHideAnchors;
/*     */ import com.hypixel.hytale.protocol.packets.worldmap.MapMarker;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.PositionUtil;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ 
/*     */ public class PrefabEditSession
/*     */   implements Resource<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<PrefabEditSession> CODEC;
/*     */   private String worldName;
/*     */   private UUID worldArrivedFrom;
/*     */   @Nullable
/*     */   private Transform transformArrivedFrom;
/*     */   private UUID worldCreator;
/*     */   
/*     */   static {
/*  86 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PrefabEditSession.class, PrefabEditSession::new).append(new KeyedCodec("WorldName", (Codec)Codec.STRING, true), (o, worldName) -> o.worldName = worldName, o -> o.worldName).add()).append(new KeyedCodec("WorldArrivedFrom", (Codec)Codec.UUID_STRING, true), (o, worldName) -> o.worldArrivedFrom = worldName, o -> o.worldArrivedFrom).add()).append(new KeyedCodec("TransformArrivedFrom", (Codec)Transform.CODEC, true), (o, positionArrivedFrom) -> o.transformArrivedFrom = positionArrivedFrom, o -> o.transformArrivedFrom).add()).append(new KeyedCodec("WorldCreatorUUID", (Codec)Codec.UUID_STRING, true), (o, worldCreatorUuid) -> o.worldCreator = worldCreatorUuid, o -> o.worldCreator).add()).append(new KeyedCodec("SpawnPoint", (Codec)new Vector3iArrayCodec(), true), (o, spawnPoint) -> o.spawnPoint = spawnPoint, o -> o.spawnPoint).add()).append(new KeyedCodec("LoadedPrefabMetadata", (Codec)new ArrayCodec((Codec)PrefabEditingMetadata.CODEC, x$0 -> new PrefabEditingMetadata[x$0]), false), (editSession, prefabEditingMetadata) -> { for (PrefabEditingMetadata prefabEditMetadata : prefabEditingMetadata) editSession.loadedPrefabMetadata.put(prefabEditMetadata.getUuid(), prefabEditMetadata);  }editSession -> (PrefabEditingMetadata[])editSession.loadedPrefabMetadata.values().toArray(())).add()).afterDecode(editSession -> { PrefabEditSessionManager prefabEditSessionManager = BuilderToolsPlugin.get().getPrefabEditSessionManager(); prefabEditSessionManager.populateActiveEditSession(editSession.getWorldCreator(), editSession); for (PrefabEditingMetadata value : editSession.loadedPrefabMetadata.values()) prefabEditSessionManager.populatePrefabsBeingEdited(value.getPrefabPath());  prefabEditSessionManager.scheduleAnchorEntityRecreation(editSession); })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ResourceType<EntityStore, PrefabEditSession> getResourceType() {
/*  93 */     return BuilderToolsPlugin.get().getPrefabEditSessionResourceType();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 122 */   private final Map<UUID, PrefabEditingMetadata> loadedPrefabMetadata = (Map<UUID, PrefabEditingMetadata>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 129 */   private final Map<UUID, UUID> selectedPrefab = (Map<UUID, UUID>)new Object2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 135 */   private Vector3i spawnPoint = new Vector3i(0, 0, 0);
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
/*     */ 
/*     */   
/*     */   public PrefabEditSession(@Nonnull String worldName, @Nonnull UUID worldCreator, @Nonnull UUID worldArrivedFrom, @Nonnull Transform transformArrivedFrom) {
/* 159 */     this.worldName = worldName;
/* 160 */     this.worldCreator = worldCreator;
/* 161 */     this.worldArrivedFrom = worldArrivedFrom;
/* 162 */     this.transformArrivedFrom = transformArrivedFrom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefabEditSession(@Nonnull PrefabEditSession other) {
/* 171 */     this.worldName = other.worldName;
/* 172 */     this.worldArrivedFrom = other.worldArrivedFrom;
/* 173 */     this.transformArrivedFrom = other.transformArrivedFrom;
/* 174 */     this.worldCreator = other.worldCreator;
/* 175 */     this.spawnPoint = other.spawnPoint;
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
/*     */   public void addPrefab(@Nonnull Path prefabPath, @Nonnull Vector3i minPoint, @Nonnull Vector3i maxPoint, @Nonnull Vector3i anchorPoint, @Nonnull Vector3i pastePosition) {
/* 188 */     if (this.loadedPrefabMetadata.isEmpty()) this.spawnPoint.assign(maxPoint);
/*     */     
/* 190 */     PrefabEditingMetadata prefabEditingMetadata = new PrefabEditingMetadata(prefabPath, minPoint, maxPoint, anchorPoint, pastePosition, Universe.get().getWorld(this.worldName));
/* 191 */     this.loadedPrefabMetadata.put(prefabEditingMetadata.getUuid(), prefabEditingMetadata);
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
/*     */   @Nullable
/*     */   public PrefabEditingMetadata updatePrefabBounds(@Nonnull UUID prefab, @Nonnull Vector3i newMin, @Nonnull Vector3i newMax) {
/* 204 */     PrefabEditingMetadata prefabEditingMetadata = this.loadedPrefabMetadata.get(prefab);
/* 205 */     if (prefabEditingMetadata == null) return null;
/*     */     
/* 207 */     prefabEditingMetadata.setMaxPoint(newMax);
/* 208 */     prefabEditingMetadata.setMinPoint(newMin);
/* 209 */     prefabEditingMetadata.setDirty(true);
/*     */     
/* 211 */     return prefabEditingMetadata;
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
/*     */   public void setSelectedPrefab(@Nonnull Ref<EntityStore> ref, @Nonnull PrefabEditingMetadata prefabEditingMetadata, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 224 */     UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(ref, UUIDComponent.getComponentType());
/* 225 */     assert uuidComponent != null;
/*     */     
/* 227 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 228 */     assert playerComponent != null;
/*     */     
/* 230 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/* 231 */     assert playerRefComponent != null;
/*     */     
/* 233 */     UUID playerUUID = uuidComponent.getUuid();
/*     */     
/* 235 */     if (this.selectedPrefab.get(playerUUID) != null && ((UUID)this.selectedPrefab.get(playerUUID)).equals(prefabEditingMetadata.getUuid())) {
/* 236 */       BlockSelection selection = BuilderToolsPlugin.getState(playerComponent, playerRefComponent).getSelection();
/* 237 */       if (selection != null && prefabEditingMetadata.getMinPoint().equals(selection.getSelectionMin()) && prefabEditingMetadata.getMaxPoint().equals(selection.getSelectionMax())) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 242 */     this.selectedPrefab.put(playerUUID, prefabEditingMetadata.getUuid());
/* 243 */     prefabEditingMetadata.sendAnchorHighlightingPacket(playerRefComponent.getPacketHandler());
/* 244 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRefComponent, (r, s, compAccess) -> s.select(prefabEditingMetadata.getMinPoint(), prefabEditingMetadata.getMaxPoint(), null, compAccess));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void hidePrefabAnchors(@Nonnull PacketHandler packetHandler) {
/* 254 */     packetHandler.writeNoCache((Packet)new BuilderToolHideAnchors());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public PrefabEditingMetadata getSelectedPrefab(@Nonnull UUID playerUuid) {
/* 265 */     UUID prefabUuid = this.selectedPrefab.get(playerUuid);
/* 266 */     if (prefabUuid == null) return null; 
/* 267 */     return this.loadedPrefabMetadata.get(prefabUuid);
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
/*     */   public boolean clearSelectedPrefab(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 279 */     UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(ref, UUIDComponent.getComponentType());
/* 280 */     assert uuidComponent != null;
/*     */     
/* 282 */     UUID playerUUID = uuidComponent.getUuid();
/*     */     
/* 284 */     if (this.selectedPrefab.remove(playerUUID) == null) {
/* 285 */       return false;
/*     */     }
/*     */     
/* 288 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(ref, PlayerRef.getComponentType());
/* 289 */     assert playerRefComponent != null;
/*     */     
/* 291 */     Player playerComponent = (Player)componentAccessor.getComponent(ref, Player.getComponentType());
/* 292 */     assert playerComponent != null;
/*     */     
/* 294 */     hidePrefabAnchors(playerRefComponent.getPacketHandler());
/* 295 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRefComponent, (r, s, compAccess) -> s.deselect(compAccess));
/*     */ 
/*     */     
/* 298 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getWorldName() {
/* 306 */     return this.worldName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUID getWorldArrivedFrom() {
/* 313 */     return this.worldArrivedFrom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Transform getTransformArrivedFrom() {
/* 321 */     return this.transformArrivedFrom;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUID getWorldCreator() {
/* 328 */     return this.worldCreator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getSpawnPoint() {
/* 336 */     return this.spawnPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<UUID, PrefabEditingMetadata> getLoadedPrefabMetadata() {
/* 344 */     return this.loadedPrefabMetadata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markPrefabsDirtyAtPosition(@Nonnull Vector3i position) {
/* 353 */     for (PrefabEditingMetadata metadata : this.loadedPrefabMetadata.values()) {
/* 354 */       if (metadata.isLocationWithinPrefabBoundingBox(position)) {
/* 355 */         metadata.setDirty(true);
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
/*     */   public void markPrefabsDirtyInBounds(@Nonnull Vector3i min, @Nonnull Vector3i max) {
/* 367 */     for (PrefabEditingMetadata metadata : this.loadedPrefabMetadata.values()) {
/* 368 */       if (boundsIntersect(metadata.getMinPoint(), metadata.getMaxPoint(), min, max)) {
/* 369 */         metadata.setDirty(true);
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
/*     */   private static boolean boundsIntersect(@Nonnull Vector3i aMin, @Nonnull Vector3i aMax, @Nonnull Vector3i bMin, @Nonnull Vector3i bMax) {
/* 385 */     return (aMin.x <= bMax.x && aMax.x >= bMin.x && aMin.y <= bMax.y && aMax.y >= bMin.y && aMin.z <= bMax.z && aMax.z >= bMin.z);
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
/*     */   @Nonnull
/*     */   public MapMarker[] createPrefabMarkers() {
/* 398 */     return (MapMarker[])this.loadedPrefabMetadata.values().stream()
/* 399 */       .map(PrefabEditSession::createPrefabMarker)
/* 400 */       .toArray(x$0 -> new MapMarker[x$0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static MapMarker createPrefabMarker(@Nonnull PrefabEditingMetadata metadata) {
/* 411 */     String fileName = metadata.getPrefabPath().getFileName().toString();
/* 412 */     String prefabName = fileName.replace(".prefab.json", "");
/* 413 */     return new MapMarker("prefab-" + 
/* 414 */         String.valueOf(metadata.getUuid()), prefabName, "Prefab.png", 
/*     */ 
/*     */         
/* 417 */         PositionUtil.toTransformPacket(new Transform(metadata.getAnchorEntityPosition().toVector3d())), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PrefabEditSession clone() {
/* 425 */     return new PrefabEditSession(this);
/*     */   }
/*     */   
/*     */   private PrefabEditSession() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\PrefabEditSession.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */