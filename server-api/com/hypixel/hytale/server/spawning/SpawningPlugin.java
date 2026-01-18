/*     */ package com.hypixel.hytale.server.spawning;
/*     */ import com.hypixel.hytale.assetstore.AssetMap;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*     */ import com.hypixel.hytale.assetstore.event.RemovedAssetsEvent;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.KDTree;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.spatial.SpatialStructure;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.server.core.Options;
/*     */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*     */ import com.hypixel.hytale.server.core.asset.LoadAssetEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.config.FlockAsset;
/*     */ import com.hypixel.hytale.server.npc.AllNPCsLoadedEvent;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderInfo;
/*     */ import com.hypixel.hytale.server.npc.components.SpawnBeaconReference;
/*     */ import com.hypixel.hytale.server.npc.components.SpawnMarkerReference;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.systems.SpawnReferenceSystems;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawnmarker.config.SpawnMarker;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.BeaconNPCSpawn;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.RoleSpawnParameters;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.WorldNPCSpawn;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawnsuppression.SpawnSuppression;
/*     */ import com.hypixel.hytale.server.spawning.beacons.InitialBeaconDelay;
/*     */ import com.hypixel.hytale.server.spawning.beacons.LegacySpawnBeaconEntity;
/*     */ import com.hypixel.hytale.server.spawning.beacons.SpawnBeacon;
/*     */ import com.hypixel.hytale.server.spawning.beacons.SpawnBeaconSystems;
/*     */ import com.hypixel.hytale.server.spawning.blockstates.SpawnMarkerBlockReference;
/*     */ import com.hypixel.hytale.server.spawning.blockstates.SpawnMarkerBlockState;
/*     */ import com.hypixel.hytale.server.spawning.blockstates.SpawnMarkerBlockStateSystems;
/*     */ import com.hypixel.hytale.server.spawning.interactions.TriggerSpawnMarkersInteraction;
/*     */ import com.hypixel.hytale.server.spawning.local.LocalSpawnBeacon;
/*     */ import com.hypixel.hytale.server.spawning.local.LocalSpawnController;
/*     */ import com.hypixel.hytale.server.spawning.local.LocalSpawnState;
/*     */ import com.hypixel.hytale.server.spawning.managers.BeaconSpawnManager;
/*     */ import com.hypixel.hytale.server.spawning.managers.SpawnManager;
/*     */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerEntity;
/*     */ import com.hypixel.hytale.server.spawning.spawnmarkers.SpawnMarkerSystems;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.ChunkSuppressionEntry;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.ChunkSuppressionQueue;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.SpawnSuppressionComponent;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.SpawnSuppressionController;
/*     */ import com.hypixel.hytale.server.spawning.suppression.system.SpawnSuppressionSystems;
/*     */ import com.hypixel.hytale.server.spawning.util.FloodFillEntryPoolProviderSimple;
/*     */ import com.hypixel.hytale.server.spawning.util.FloodFillPositionSelector;
/*     */ import com.hypixel.hytale.server.spawning.world.WorldEnvironmentSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.WorldNPCSpawnStat;
/*     */ import com.hypixel.hytale.server.spawning.world.component.ChunkSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.ChunkSpawnedNPCData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.SpawnJobData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.WorldSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.manager.EnvironmentSpawnParameters;
/*     */ import com.hypixel.hytale.server.spawning.world.manager.WorldSpawnManager;
/*     */ import com.hypixel.hytale.server.spawning.world.system.ChunkSpawningSystems;
/*     */ import com.hypixel.hytale.server.spawning.world.system.WorldSpawnJobSystems;
/*     */ import com.hypixel.hytale.server.spawning.wrappers.BeaconSpawnWrapper;
/*     */ import com.hypixel.hytale.server.spawning.wrappers.SpawnWrapper;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bson.BsonDocument;
/*     */ 
/*     */ public class SpawningPlugin extends JavaPlugin {
/*     */   private static final String DEFAULT_SPAWN_MARKER_MODEL = "NPC_Spawn_Marker";
/*     */   private static final int TICK_COLUMN_BUDGET = 20480;
/*     */   
/*     */   public static SpawningPlugin get() {
/* 119 */     return instance;
/*     */   }
/*     */   private static final float OVERPOPULATION_RATIO = 0.25F; private static final int OVERPOPULATION_GROUP_BUFFER = 4;
/*     */   private static SpawningPlugin instance;
/*     */   private Model spawnMarkerModel;
/*     */   private double localSpawnControllerJoinDelay;
/*     */   private int tickColumnBudget;
/* 126 */   private final WorldSpawnManager worldSpawnManager = new WorldSpawnManager();
/* 127 */   private final BeaconSpawnManager beaconSpawnManager = new BeaconSpawnManager();
/*     */   
/* 129 */   private final Config<NPCSpawningConfig> config = withConfig("SpawningModule", NPCSpawningConfig.CODEC);
/*     */   
/*     */   private ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> legacyBeaconSpatialResource;
/*     */   
/*     */   private ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> spawnMarkerSpatialResource;
/*     */   private ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> manualSpawnBeaconSpatialResource;
/*     */   private ComponentType<EntityStore, SpawnMarkerEntity> spawnMarkerComponentType;
/*     */   private ComponentType<EntityStore, LocalSpawnController> localSpawnControllerComponentType;
/*     */   private ResourceType<EntityStore, WorldSpawnData> worldSpawnDataResourceType;
/*     */   private ComponentType<EntityStore, SpawnSuppressionComponent> spawnSuppressorComponentType;
/*     */   private ResourceType<EntityStore, SpawnSuppressionController> spawnSuppressionControllerResourceType;
/*     */   private ComponentType<EntityStore, LocalSpawnBeacon> localSpawnBeaconComponentType;
/*     */   private ResourceType<EntityStore, LocalSpawnState> localSpawnStateResourceType;
/*     */   private ComponentType<ChunkStore, SpawnJobData> spawnJobDataComponentType;
/*     */   private ComponentType<ChunkStore, ChunkSpawnData> chunkSpawnDataComponentType;
/*     */   private ComponentType<ChunkStore, ChunkSpawnedNPCData> chunkSpawnedNPCDataComponentType;
/*     */   private ResourceType<ChunkStore, ChunkSuppressionQueue> chunkSuppressionQueueResourceType;
/*     */   private ComponentType<ChunkStore, ChunkSuppressionEntry> chunkSuppressionEntryComponentType;
/*     */   private ComponentType<EntityStore, InitialBeaconDelay> initialBeaconDelayComponentType;
/*     */   private ComponentType<EntityStore, SpawnMarkerReference> spawnMarkerReferenceComponentType;
/*     */   private ComponentType<EntityStore, SpawnBeaconReference> spawnBeaconReferenceComponentType;
/*     */   private ComponentType<EntityStore, FloodFillPositionSelector> floodFillPositionSelectorComponentType;
/*     */   private ResourceType<EntityStore, FloodFillEntryPoolProviderSimple> floodFillEntryPoolProviderSimpleResourceType;
/*     */   private ComponentType<EntityStore, SpawnMarkerBlockReference> spawnMarkerBlockReferenceComponentType;
/*     */   
/*     */   public SpawningPlugin(@Nonnull JavaPluginInit init) {
/* 155 */     super(init);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setup() {
/* 160 */     instance = this;
/*     */     
/* 162 */     getCommandRegistry().registerCommand((AbstractCommand)new SpawnCommand());
/*     */     
/* 164 */     getEventRegistry().register(LoadedAssetsEvent.class, Environment.class, this::onEnvironmentChange);
/* 165 */     getEventRegistry().register(AllNPCsLoadedEvent.class, this::onLoadedNPCEvent);
/* 166 */     getEventRegistry().register(LoadedAssetsEvent.class, SpawnMarker.class, this::onSpawnMarkersChange);
/* 167 */     getEventRegistry().register(RemovedAssetsEvent.class, SpawnMarker.class, SpawningPlugin::onSpawnMarkersRemove);
/* 168 */     getEventRegistry().register(LoadedAssetsEvent.class, WorldNPCSpawn.class, this::onWorldNPCSpawnsLoaded);
/* 169 */     getEventRegistry().register(LoadedAssetsEvent.class, BeaconNPCSpawn.class, this::onBeaconNPCSpawnsLoaded);
/* 170 */     getEventRegistry().register(RemovedAssetsEvent.class, WorldNPCSpawn.class, this::onWorldNPCSpawnsRemoved);
/* 171 */     getEventRegistry().register(RemovedAssetsEvent.class, BeaconNPCSpawn.class, this::onBeaconNPCSpawnsRemoved);
/* 172 */     getEventRegistry().register(LoadedAssetsEvent.class, ModelAsset.class, this::onModelAssetChange);
/* 173 */     getEventRegistry().register((short)-7, LoadAssetEvent.class, this::onLoadAsset);
/*     */     
/* 175 */     getEntityRegistry().registerEntity("LegacySpawnBeacon", LegacySpawnBeaconEntity.class, LegacySpawnBeaconEntity::new, (DirectDecodeCodec)LegacySpawnBeaconEntity.CODEC);
/* 176 */     getEntityRegistry().registerEntity("SpawnBeacon", SpawnBeacon.class, SpawnBeacon::new, (DirectDecodeCodec)SpawnBeacon.CODEC);
/*     */     
/* 178 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(SpawnMarker.class, (AssetMap)new DefaultAssetMap())
/* 179 */         .setPath("NPC/Spawn/Markers"))
/* 180 */         .setCodec((AssetCodec)SpawnMarker.CODEC))
/* 181 */         .setKeyFunction(SpawnMarker::getId))
/* 182 */         .loadsAfter(new Class[] { FlockAsset.class, ModelAsset.class
/* 183 */           })).loadsBefore(new Class[] { Interaction.class
/* 184 */           })).build());
/*     */     
/* 186 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(WorldNPCSpawn.class, (AssetMap)new IndexedLookupTableAssetMap(x$0 -> new WorldNPCSpawn[x$0]))
/* 187 */         .setPath("NPC/Spawn/World"))
/* 188 */         .setCodec((AssetCodec)WorldNPCSpawn.CODEC))
/* 189 */         .setKeyFunction(WorldNPCSpawn::getId))
/* 190 */         .setReplaceOnRemove(WorldNPCSpawn::new))
/* 191 */         .loadsAfter(new Class[] { Environment.class, BlockSet.class, FlockAsset.class
/* 192 */           })).build());
/*     */     
/* 194 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(BeaconNPCSpawn.class, (AssetMap)new IndexedLookupTableAssetMap(x$0 -> new BeaconNPCSpawn[x$0]))
/* 195 */         .setPath("NPC/Spawn/Beacons"))
/* 196 */         .setCodec((AssetCodec)BeaconNPCSpawn.CODEC))
/* 197 */         .setKeyFunction(BeaconNPCSpawn::getId))
/* 198 */         .setReplaceOnRemove(BeaconNPCSpawn::new))
/* 199 */         .loadsAfter(new Class[] { Environment.class, BlockSet.class, SpawnSuppression.class, FlockAsset.class, ModelAsset.class, ResponseCurve.class
/* 200 */           })).build());
/*     */     
/* 202 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(SpawnSuppression.class, (AssetMap)new IndexedAssetMap())
/* 203 */         .setPath("NPC/Spawn/Suppression"))
/* 204 */         .setCodec((AssetCodec)SpawnSuppression.CODEC))
/* 205 */         .setKeyFunction(SpawnSuppression::getId))
/* 206 */         .setReplaceOnRemove(SpawnSuppression::new))
/* 207 */         .loadsAfter(new Class[] { NPCGroup.class
/* 208 */           })).build());
/*     */     
/* 210 */     NPCPlugin.get().registerCoreComponentType("TriggerSpawnBeacon", com.hypixel.hytale.server.spawning.corecomponents.builders.BuilderActionTriggerSpawnBeacon::new);
/*     */     
/* 212 */     BlockStateModule.get().registerBlockState(SpawnMarkerBlockState.class, "SpawnMarkerBlock", SpawnMarkerBlockState.CODEC, SpawnMarkerBlockState.Data.class, (Codec)SpawnMarkerBlockState.Data.CODEC);
/*     */     
/* 214 */     this.spawnMarkerComponentType = getEntityStoreRegistry().registerComponent(SpawnMarkerEntity.class, "SpawnMarkerComponent", SpawnMarkerEntity.CODEC);
/* 215 */     this.localSpawnControllerComponentType = getEntityStoreRegistry().registerComponent(LocalSpawnController.class, LocalSpawnController::new);
/* 216 */     this.worldSpawnDataResourceType = getEntityStoreRegistry().registerResource(WorldSpawnData.class, WorldSpawnData::new);
/* 217 */     this.localSpawnBeaconComponentType = getEntityStoreRegistry().registerComponent(LocalSpawnBeacon.class, "LocalSpawnBeacon", LocalSpawnBeacon.CODEC);
/* 218 */     this.localSpawnStateResourceType = getEntityStoreRegistry().registerResource(LocalSpawnState.class, LocalSpawnState::new);
/* 219 */     this.legacyBeaconSpatialResource = getEntityStoreRegistry().registerSpatialResource(() -> new KDTree(Ref::isValid));
/* 220 */     this.spawnMarkerSpatialResource = getEntityStoreRegistry().registerSpatialResource(() -> new KDTree(Ref::isValid));
/* 221 */     this.manualSpawnBeaconSpatialResource = getEntityStoreRegistry().registerSpatialResource(() -> new KDTree(Ref::isValid));
/* 222 */     this.spawnSuppressorComponentType = getEntityStoreRegistry().registerComponent(SpawnSuppressionComponent.class, "SpawnSuppression", SpawnSuppressionComponent.CODEC);
/* 223 */     this.spawnSuppressionControllerResourceType = getEntityStoreRegistry().registerResource(SpawnSuppressionController.class, "SpawnSuppressionController", SpawnSuppressionController.CODEC);
/*     */     
/* 225 */     this.initialBeaconDelayComponentType = getEntityStoreRegistry().registerComponent(InitialBeaconDelay.class, InitialBeaconDelay::new);
/* 226 */     this.spawnMarkerReferenceComponentType = getEntityStoreRegistry().registerComponent(SpawnMarkerReference.class, "SpawnMarkerReference", SpawnMarkerReference.CODEC);
/* 227 */     this.spawnBeaconReferenceComponentType = getEntityStoreRegistry().registerComponent(SpawnBeaconReference.class, "SpawnBeaconReference", SpawnBeaconReference.CODEC);
/* 228 */     this.floodFillPositionSelectorComponentType = getEntityStoreRegistry().registerComponent(FloodFillPositionSelector.class, () -> {
/*     */           throw new UnsupportedOperationException("Not implemented");
/*     */         });
/* 231 */     this.floodFillEntryPoolProviderSimpleResourceType = getEntityStoreRegistry().registerResource(FloodFillEntryPoolProviderSimple.class, FloodFillEntryPoolProviderSimple::new);
/* 232 */     this.spawnMarkerBlockReferenceComponentType = getEntityStoreRegistry().registerComponent(SpawnMarkerBlockReference.class, "SpawnMarkerBlockReference", SpawnMarkerBlockReference.CODEC);
/*     */     
/* 234 */     this.spawnJobDataComponentType = getChunkStoreRegistry().registerComponent(SpawnJobData.class, SpawnJobData::new);
/* 235 */     this.chunkSpawnDataComponentType = getChunkStoreRegistry().registerComponent(ChunkSpawnData.class, ChunkSpawnData::new);
/* 236 */     this.chunkSpawnedNPCDataComponentType = getChunkStoreRegistry().registerComponent(ChunkSpawnedNPCData.class, "ChunkSpawnedNPCData", ChunkSpawnedNPCData.CODEC);
/* 237 */     this.chunkSuppressionQueueResourceType = getChunkStoreRegistry().registerResource(ChunkSuppressionQueue.class, ChunkSuppressionQueue::new);
/* 238 */     this.chunkSuppressionEntryComponentType = getChunkStoreRegistry().registerComponent(ChunkSuppressionEntry.class, () -> {
/*     */           throw new UnsupportedOperationException("Not implemented");
/*     */         });
/*     */     
/* 242 */     EntityModule entityModule = EntityModule.get();
/* 243 */     ComponentType<EntityStore, Player> playerComponentType = entityModule.getPlayerComponentType();
/* 244 */     ComponentType<EntityStore, TransformComponent> transformComponentType = entityModule.getTransformComponentType();
/* 245 */     ComponentType<EntityStore, LegacySpawnBeaconEntity> legacySpawnBeaconComponentType = entityModule.getComponentType(LegacySpawnBeaconEntity.class);
/* 246 */     ComponentType<EntityStore, SpawnBeacon> spawnBeaconComponentType = entityModule.getComponentType(SpawnBeacon.class);
/* 247 */     ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialComponent = entityModule.getPlayerSpatialResourceType();
/*     */ 
/*     */     
/* 250 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnSuppressionSystems.EnsureNetworkSendable());
/* 251 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnSuppressionSystems.Load(this.spawnSuppressionControllerResourceType, this.spawnMarkerComponentType, this.chunkSuppressionQueueResourceType, this.chunkSuppressionEntryComponentType));
/*     */     
/* 253 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnSuppressionSystems.Suppressor(this.spawnSuppressorComponentType, this.spawnSuppressionControllerResourceType, this.spawnMarkerComponentType, this.chunkSuppressionQueueResourceType, this.spawnMarkerSpatialResource));
/*     */ 
/*     */     
/* 256 */     getEntityStoreRegistry().registerSystem((ISystem)new LegacyBeaconSpatialSystem(this.legacyBeaconSpatialResource));
/* 257 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnMarkerSpatialSystem(this.spawnMarkerSpatialResource));
/*     */     
/* 259 */     getEntityStoreRegistry().registerSystem((ISystem)new BeaconSpatialSystem(this.manualSpawnBeaconSpatialResource));
/* 260 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnMarkerSuppressionSystem(this.spawnMarkerComponentType, this.spawnSuppressionControllerResourceType));
/*     */     
/* 262 */     getChunkStoreRegistry().registerSystem((ISystem)new ChunkSuppressionSystems.ChunkAdded(this.chunkSuppressionEntryComponentType, this.spawnSuppressionControllerResourceType));
/* 263 */     getChunkStoreRegistry().registerSystem((ISystem)new ChunkSuppressionSystems.Ticking(this.chunkSuppressionEntryComponentType, this.chunkSuppressionQueueResourceType));
/*     */     
/* 265 */     getEntityStoreRegistry().registerSystem((ISystem)new LocalSpawnSetupSystem(playerComponentType));
/* 266 */     getEntityStoreRegistry().registerSystem((ISystem)new LocalSpawnControllerSystem(this.localSpawnControllerComponentType, transformComponentType, 
/* 267 */           WeatherTracker.getComponentType(), this.localSpawnBeaconComponentType, legacySpawnBeaconComponentType, this.localSpawnStateResourceType, this.legacyBeaconSpatialResource));
/*     */     
/* 269 */     getEntityStoreRegistry().registerSystem((ISystem)new LocalSpawnBeaconSystem(this.localSpawnBeaconComponentType, this.localSpawnStateResourceType));
/* 270 */     getEntityStoreRegistry().registerSystem((ISystem)new LocalSpawnForceTriggerSystem(this.localSpawnControllerComponentType, this.localSpawnStateResourceType));
/*     */     
/* 272 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnBeaconSystems.LegacyEntityAdded(legacySpawnBeaconComponentType));
/* 273 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnBeaconSystems.EntityAdded(spawnBeaconComponentType));
/*     */ 
/*     */     
/* 276 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnBeaconSystems.CheckDespawn(legacySpawnBeaconComponentType, this.initialBeaconDelayComponentType));
/* 277 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnBeaconSystems.PositionSelectorUpdate(this.floodFillPositionSelectorComponentType, this.floodFillEntryPoolProviderSimpleResourceType));
/* 278 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnBeaconSystems.ControllerTick(legacySpawnBeaconComponentType, this.floodFillPositionSelectorComponentType, this.initialBeaconDelayComponentType));
/* 279 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnBeaconSystems.SpawnJobTick(legacySpawnBeaconComponentType, this.initialBeaconDelayComponentType));
/* 280 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnBeaconSystems.LoadTimeDelay(this.initialBeaconDelayComponentType));
/*     */     
/* 282 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnMarkerSystems.LegacyEntityMigration());
/* 283 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnMarkerSystems.EnsureNetworkSendable());
/* 284 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnMarkerSystems.CacheMarker(this.spawnMarkerComponentType));
/* 285 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnMarkerSystems.EntityAdded(this.spawnMarkerComponentType));
/* 286 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnMarkerSystems.EntityAddedFromExternal(this.spawnMarkerComponentType));
/* 287 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnMarkerSystems.AddedFromWorldGen());
/* 288 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnMarkerSystems.Ticking(this.spawnMarkerComponentType, playerSpatialComponent));
/*     */     
/* 290 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnReferenceSystems.MarkerAddRemoveSystem(SpawnMarkerReference.getComponentType(), this.spawnMarkerComponentType));
/* 291 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnReferenceSystems.BeaconAddRemoveSystem(SpawnBeaconReference.getComponentType(), legacySpawnBeaconComponentType));
/* 292 */     getEntityStoreRegistry().registerSystem((ISystem)new WorldSpawnTrackingSystem(this.worldSpawnDataResourceType, this.chunkSpawnDataComponentType, this.chunkSpawnedNPCDataComponentType));
/* 293 */     getEntityStoreRegistry().registerSystem((ISystem)new MoonPhaseChangeEventSystem());
/*     */ 
/*     */     
/* 296 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnReferenceSystems.TickingSpawnMarkerSystem(this.spawnMarkerReferenceComponentType, this.spawnMarkerComponentType));
/* 297 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnReferenceSystems.TickingSpawnBeaconSystem(this.spawnBeaconReferenceComponentType));
/*     */     
/* 299 */     getChunkStoreRegistry().registerSystem((ISystem)new WorldSpawningSystem(this.worldSpawnDataResourceType, this.chunkSpawnDataComponentType, this.chunkSpawnedNPCDataComponentType, this.spawnJobDataComponentType));
/*     */ 
/*     */     
/* 302 */     getChunkStoreRegistry().registerSystem((ISystem)new WorldSpawnJobSystems.EntityRemoved(this.worldSpawnDataResourceType, this.spawnJobDataComponentType));
/* 303 */     getChunkStoreRegistry().registerSystem((ISystem)new WorldSpawnJobSystems.Ticking(this.worldSpawnDataResourceType, this.spawnSuppressionControllerResourceType, this.spawnJobDataComponentType, this.chunkSpawnDataComponentType, this.chunkSpawnedNPCDataComponentType));
/*     */ 
/*     */     
/* 306 */     getChunkStoreRegistry().registerSystem((ISystem)new WorldSpawnJobSystems.TickingState(this.worldSpawnDataResourceType, this.spawnJobDataComponentType));
/*     */     
/* 308 */     getChunkStoreRegistry().registerSystem((ISystem)new ChunkSpawningSystems.ChunkRefAdded(this.worldSpawnDataResourceType, this.chunkSpawnDataComponentType, this.chunkSpawnedNPCDataComponentType));
/*     */     
/* 310 */     getChunkStoreRegistry().registerSystem((ISystem)new ChunkSpawningSystems.TickingState(this.worldSpawnDataResourceType, this.chunkSpawnDataComponentType, this.chunkSpawnedNPCDataComponentType));
/*     */ 
/*     */     
/* 313 */     ComponentType<ChunkStore, SpawnMarkerBlockState> spawnMarkerBlockStateComponentType = BlockStateModule.get().getComponentType(SpawnMarkerBlockState.class);
/* 314 */     getChunkStoreRegistry().registerSystem((ISystem)new SpawnMarkerBlockStateSystems.AddOrRemove(spawnMarkerBlockStateComponentType));
/* 315 */     getChunkStoreRegistry().registerSystem((ISystem)new SpawnMarkerBlockStateSystems.TickHeartbeat(spawnMarkerBlockStateComponentType));
/* 316 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnMarkerBlockStateSystems.SpawnMarkerAddedFromExternal(this.spawnMarkerBlockReferenceComponentType));
/* 317 */     getEntityStoreRegistry().registerSystem((ISystem)new SpawnMarkerBlockStateSystems.SpawnMarkerTickHeartbeat(this.spawnMarkerBlockReferenceComponentType));
/*     */     
/* 319 */     getEntityStoreRegistry().registerSystem((ISystem)new EntityModule.HiddenFromPlayerMigrationSystem((Query)this.spawnSuppressorComponentType), true);
/* 320 */     getEntityStoreRegistry().registerSystem((ISystem)new LegacySpawnSuppressorEntityMigration());
/*     */     
/* 322 */     Interaction.CODEC.register("TriggerSpawnMarkers", TriggerSpawnMarkersInteraction.class, TriggerSpawnMarkersInteraction.CODEC);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 327 */     NPCSpawningConfig config = (NPCSpawningConfig)this.config.get();
/* 328 */     String spawnMarkerModelId = config.defaultMarkerModel;
/* 329 */     this.localSpawnControllerJoinDelay = config.localSpawnControllerJoinDelay;
/* 330 */     this.tickColumnBudget = MathUtil.floor(config.spawnBudgetFactor * 20480.0D);
/* 331 */     DefaultAssetMap<String, ModelAsset> modelAssetMap = ModelAsset.getAssetMap();
/* 332 */     ModelAsset modelAsset = (ModelAsset)modelAssetMap.getAsset(spawnMarkerModelId);
/* 333 */     if (modelAsset == null) {
/* 334 */       getLogger().at(Level.SEVERE).log("Spawn marker model %s does not exist");
/* 335 */       modelAsset = (ModelAsset)modelAssetMap.getAsset("NPC_Spawn_Marker");
/* 336 */       if (modelAsset == null) {
/* 337 */         throw new IllegalStateException(String.format("Default spawn marker '%s' not found", new Object[] { "NPC_Spawn_Marker" }));
/*     */       }
/*     */     } 
/* 340 */     this.spawnMarkerModel = Model.createUnitScaleModel(modelAsset);
/*     */     
/* 342 */     setUpWithAllRoles();
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown() {}
/*     */ 
/*     */   
/*     */   public ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> getSpawnMarkerSpatialResource() {
/* 350 */     return this.spawnMarkerSpatialResource;
/*     */   }
/*     */   
/*     */   public ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> getManualSpawnBeaconSpatialResource() {
/* 354 */     return this.manualSpawnBeaconSpatialResource;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, SpawnMarkerEntity> getSpawnMarkerComponentType() {
/* 358 */     return this.spawnMarkerComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, LocalSpawnController> getLocalSpawnControllerComponentType() {
/* 362 */     return this.localSpawnControllerComponentType;
/*     */   }
/*     */   
/*     */   public ResourceType<EntityStore, WorldSpawnData> getWorldSpawnDataResourceType() {
/* 366 */     return this.worldSpawnDataResourceType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, SpawnSuppressionComponent> getSpawnSuppressorComponentType() {
/* 370 */     return this.spawnSuppressorComponentType;
/*     */   }
/*     */   
/*     */   public ResourceType<EntityStore, SpawnSuppressionController> getSpawnSuppressionControllerResourceType() {
/* 374 */     return this.spawnSuppressionControllerResourceType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, LocalSpawnBeacon> getLocalSpawnBeaconComponentType() {
/* 378 */     return this.localSpawnBeaconComponentType;
/*     */   }
/*     */   
/*     */   public ResourceType<EntityStore, LocalSpawnState> getLocalSpawnStateResourceType() {
/* 382 */     return this.localSpawnStateResourceType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, InitialBeaconDelay> getInitialBeaconDelayComponentType() {
/* 386 */     return this.initialBeaconDelayComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, SpawnJobData> getSpawnJobDataComponentType() {
/* 390 */     return this.spawnJobDataComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, ChunkSpawnData> getChunkSpawnDataComponentType() {
/* 394 */     return this.chunkSpawnDataComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, ChunkSpawnedNPCData> getChunkSpawnedNPCDataComponentType() {
/* 398 */     return this.chunkSpawnedNPCDataComponentType;
/*     */   }
/*     */   
/*     */   public ResourceType<ChunkStore, ChunkSuppressionQueue> getChunkSuppressionQueueResourceType() {
/* 402 */     return this.chunkSuppressionQueueResourceType;
/*     */   }
/*     */   
/*     */   public ResourceType<EntityStore, FloodFillEntryPoolProviderSimple> getFloodFillEntryPoolProviderSimpleResourceType() {
/* 406 */     return this.floodFillEntryPoolProviderSimpleResourceType;
/*     */   }
/*     */   
/*     */   public ComponentType<ChunkStore, ChunkSuppressionEntry> getChunkSuppressionEntryComponentType() {
/* 410 */     return this.chunkSuppressionEntryComponentType;
/*     */   }
/*     */   
/*     */   public BeaconSpawnWrapper getBeaconSpawnWrapper(int configId) {
/* 414 */     return (BeaconSpawnWrapper)this.beaconSpawnManager.getSpawnWrapper(configId);
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, SpawnMarkerReference> getSpawnMarkerReferenceComponentType() {
/* 418 */     return this.spawnMarkerReferenceComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, SpawnBeaconReference> getSpawnBeaconReferenceComponentType() {
/* 422 */     return this.spawnBeaconReferenceComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, FloodFillPositionSelector> getFloodFillPositionSelectorComponentType() {
/* 426 */     return this.floodFillPositionSelectorComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, SpawnMarkerBlockReference> getSpawnMarkerBlockReferenceComponentType() {
/* 430 */     return this.spawnMarkerBlockReferenceComponentType;
/*     */   }
/*     */   
/*     */   public boolean shouldNPCDespawn(@Nonnull Store<EntityStore> store, @Nonnull NPCEntity npcComponent, @Nonnull WorldTimeResource timeManager, int configuration, boolean beaconSpawn) {
/* 434 */     if (configuration == Integer.MIN_VALUE) return false;
/*     */     
/* 436 */     SpawnManager manager = beaconSpawn ? (SpawnManager)this.beaconSpawnManager : (SpawnManager)this.worldSpawnManager;
/* 437 */     SpawnWrapper wrapper = manager.getSpawnWrapper(configuration);
/* 438 */     if (wrapper == null) return false;
/*     */     
/* 440 */     if (!beaconSpawn) {
/*     */ 
/*     */       
/* 443 */       int environment = npcComponent.getEnvironment();
/* 444 */       if (environment != Integer.MIN_VALUE) {
/* 445 */         WorldSpawnData worldSpawnData = (WorldSpawnData)store.getResource(WorldSpawnData.getResourceType());
/* 446 */         WorldEnvironmentSpawnData environmentSpawnData = worldSpawnData.getWorldEnvironmentSpawnData(environment);
/* 447 */         if (environmentSpawnData != null) {
/* 448 */           WorldNPCSpawnStat npcSpawnData = (WorldNPCSpawnStat)environmentSpawnData.getNpcStatMap().get(npcComponent.getRoleIndex());
/* 449 */           if (npcSpawnData != null)
/*     */           {
/* 451 */             if (npcSpawnData.getActual() > npcSpawnData.getExpected() * 1.25D + 4.0D) {
/* 452 */               get().getLogger().at(Level.WARNING).log("Removing NPC of type %s due to overpopulation (expected: %f, actual: %d)", npcComponent
/* 453 */                   .getRoleName(), Double.valueOf(npcSpawnData.getExpected()), Integer.valueOf(npcSpawnData.getActual()));
/* 454 */               return true;
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 461 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 462 */     return wrapper.shouldDespawn(world, timeManager);
/*     */   }
/*     */   
/*     */   public Model getSpawnMarkerModel() {
/* 466 */     return this.spawnMarkerModel;
/*     */   }
/*     */   
/*     */   public EnvironmentSpawnParameters getWorldEnvironmentSpawnParameters(int environmentIndex) {
/* 470 */     return this.worldSpawnManager.getEnvironmentSpawnParameters(environmentIndex);
/*     */   }
/*     */   
/*     */   public List<BeaconSpawnWrapper> getBeaconSpawnsForEnvironment(int environmentIndex) {
/* 474 */     return this.beaconSpawnManager.getBeaconSpawns(environmentIndex);
/*     */   }
/*     */   
/*     */   public IntSet getRolesForEnvironment(int environment) {
/* 478 */     return this.worldSpawnManager.getRolesForEnvironment(environment);
/*     */   }
/*     */   
/*     */   public int getTickColumnBudget() {
/* 482 */     return this.tickColumnBudget;
/*     */   }
/*     */   
/*     */   public int getMaxActiveJobs() {
/* 486 */     return ((NPCSpawningConfig)this.config.get()).maxActiveJobs;
/*     */   }
/*     */   
/*     */   public double getLocalSpawnControllerJoinDelay() {
/* 490 */     return this.localSpawnControllerJoinDelay;
/*     */   }
/*     */   
/*     */   public static <T extends NPCSpawn> void validateSpawnsConfigurations(String type, @Nonnull Map<String, T> spawns, @Nonnull List<String> errors) {
/* 494 */     for (Map.Entry<String, T> spawn : spawns.entrySet()) {
/* 495 */       RoleSpawnParameters[] npcs = ((NPCSpawn)spawn.getValue()).getNPCs();
/* 496 */       for (RoleSpawnParameters npc : npcs) {
/*     */         try {
/* 498 */           NPCPlugin.get().validateSpawnableRole(npc.getId());
/* 499 */         } catch (IllegalArgumentException e) {
/* 500 */           errors.add(type + " " + type + ": " + (String)spawn.getKey());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void validateSpawnMarkers(@Nonnull Map<String, SpawnMarker> markers, @Nonnull List<String> errors) {
/* 507 */     for (Iterator<Map.Entry<String, SpawnMarker>> iterator = markers.entrySet().iterator(); iterator.hasNext(); ) { Map.Entry<String, SpawnMarker> marker = iterator.next();
/* 508 */       IWeightedMap<SpawnMarker.SpawnConfiguration> npcs = ((SpawnMarker)marker.getValue()).getWeightedConfigurations();
/* 509 */       npcs.forEach(config -> {
/*     */             try {
/*     */               String npc = config.getNpc();
/*     */               if (npc != null) {
/*     */                 NPCPlugin.get().validateSpawnableRole(npc);
/*     */               }
/* 515 */             } catch (IllegalArgumentException e) {
/*     */               errors.add("Spawn marker " + (String)marker.getKey() + ": " + e.getMessage());
/*     */             } 
/*     */           }); }
/*     */   
/*     */   }
/*     */   
/*     */   public double getEnvironmentDensity(int environmentIndex) {
/* 523 */     EnvironmentSpawnParameters environment = getWorldEnvironmentSpawnParameters(environmentIndex);
/* 524 */     return (environment != null) ? environment.getSpawnDensity() : 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public static class LegacySpawnSuppressorEntityMigration
/*     */     extends EntityModule.MigrationSystem
/*     */   {
/* 532 */     private final ComponentType<EntityStore, PersistentModel> persistentModelComponentType = PersistentModel.getComponentType();
/* 533 */     private final ComponentType<EntityStore, Nameplate> nameplateComponentType = Nameplate.getComponentType();
/* 534 */     private final ComponentType<EntityStore, UUIDComponent> uuidComponentType = UUIDComponent.getComponentType();
/* 535 */     private final ComponentType<EntityStore, UnknownComponents<EntityStore>> unknownComponentsComponentType = EntityStore.REGISTRY.getUnknownComponentType();
/* 536 */     private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.unknownComponentsComponentType, (Query)Query.not((Query)AllLegacyEntityTypesQuery.INSTANCE) });
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 540 */       Map<String, BsonDocument> unknownComponents = ((UnknownComponents)holder.getComponent(this.unknownComponentsComponentType)).getUnknownComponents();
/*     */       
/* 542 */       BsonDocument spawnSuppressor = unknownComponents.remove("SpawnSuppressor");
/* 543 */       if (spawnSuppressor == null)
/*     */         return; 
/* 545 */       Archetype<EntityStore> archetype = holder.getArchetype();
/*     */       
/* 547 */       if (!archetype.contains(this.persistentModelComponentType)) {
/* 548 */         Model.ModelReference modelReference = Entity.MODEL.get(spawnSuppressor).get();
/* 549 */         holder.addComponent(this.persistentModelComponentType, (Component)new PersistentModel(modelReference));
/*     */       } 
/*     */       
/* 552 */       if (!archetype.contains(this.nameplateComponentType)) {
/* 553 */         holder.addComponent(this.nameplateComponentType, (Component)new Nameplate(Entity.DISPLAY_NAME.get(spawnSuppressor).get()));
/*     */       }
/*     */       
/* 556 */       if (!archetype.contains(this.uuidComponentType)) {
/* 557 */         holder.addComponent(this.uuidComponentType, (Component)new UUIDComponent(Entity.UUID.get(spawnSuppressor).get()));
/*     */       }
/*     */       
/* 560 */       holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 570 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 576 */       return RootDependency.firstSet();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void onSpawnMarkersChange(@Nonnull LoadedAssetsEvent<String, SpawnMarker, DefaultAssetMap<String, SpawnMarker>> event) {
/* 581 */     Map<String, SpawnMarker> loadedAssets = event.getLoadedAssets();
/* 582 */     Universe.get().getWorlds().forEach((name, world) -> world.execute(()));
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
/* 597 */     if (NPCPlugin.get().getBuilderManager().isEmpty())
/*     */       return; 
/* 599 */     ObjectArrayList<String> errors = new ObjectArrayList();
/* 600 */     validateSpawnMarkers(event.getLoadedAssets(), (List<String>)errors);
/* 601 */     for (ObjectListIterator<String> objectListIterator = errors.iterator(); objectListIterator.hasNext(); ) { String error = objectListIterator.next();
/* 602 */       getLogger().at(Level.SEVERE).log(error); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void onSpawnMarkersRemove(@Nonnull RemovedAssetsEvent<String, SpawnMarker, DefaultAssetMap<String, SpawnMarker>> event) {
/* 608 */     Set<String> removedAssets = event.getRemovedAssets();
/* 609 */     Universe.get().getWorlds().forEach((name, world) -> world.execute(()));
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
/*     */   private void onEnvironmentChange(@Nonnull LoadedAssetsEvent<String, Environment, IndexedLookupTableAssetMap<String, Environment>> event) {
/* 625 */     IndexedLookupTableAssetMap<String, Environment> environmentAssetMap = Environment.getAssetMap();
/* 626 */     for (Map.Entry<String, Environment> entry : (Iterable<Map.Entry<String, Environment>>)event.getLoadedAssets().entrySet()) {
/* 627 */       String environment = entry.getKey();
/* 628 */       int index = environmentAssetMap.getIndex(environment);
/* 629 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + environment); 
/* 630 */       this.worldSpawnManager.updateSpawnParameters(index, entry.getValue());
/*     */     } 
/* 632 */     WorldSpawnManager.onEnvironmentChanged();
/*     */   }
/*     */   
/*     */   private void onWorldNPCSpawnsLoaded(@Nonnull LoadedAssetsEvent<String, WorldNPCSpawn, IndexedLookupTableAssetMap<String, WorldNPCSpawn>> event) {
/* 636 */     if (NPCPlugin.get().getBuilderManager().isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 641 */     IntOpenHashSet changeSet = new IntOpenHashSet();
/* 642 */     for (String config : event.getLoadedAssets().keySet()) {
/* 643 */       int index = ((IndexedLookupTableAssetMap)event.getAssetMap()).getIndex(config);
/* 644 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + config); 
/* 645 */       changeSet.add(index);
/*     */     } 
/*     */     
/* 648 */     this.worldSpawnManager.rebuildConfigurations((IntSet)changeSet);
/* 649 */     ObjectArrayList<String> errors = new ObjectArrayList();
/* 650 */     validateSpawnsConfigurations("World spawn", event.getLoadedAssets(), (List<String>)errors);
/* 651 */     for (ObjectListIterator<String> objectListIterator = errors.iterator(); objectListIterator.hasNext(); ) { String error = objectListIterator.next();
/* 652 */       getLogger().at(Level.SEVERE).log(error); }
/*     */   
/*     */   }
/*     */   
/*     */   private void onBeaconNPCSpawnsLoaded(@Nonnull LoadedAssetsEvent<String, BeaconNPCSpawn, IndexedLookupTableAssetMap<String, BeaconNPCSpawn>> event) {
/* 657 */     if (NPCPlugin.get().getBuilderManager().isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 662 */     IntOpenHashSet changeSet = new IntOpenHashSet();
/* 663 */     for (String config : event.getLoadedAssets().keySet()) {
/* 664 */       int index = ((IndexedLookupTableAssetMap)event.getAssetMap()).getIndex(config);
/* 665 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + config); 
/* 666 */       changeSet.add(index);
/*     */     } 
/*     */     
/* 669 */     rebuildBeaconSpawnConfigurations((IntSet)changeSet);
/*     */     
/* 671 */     Map<String, BeaconNPCSpawn> loadedAssets = event.getLoadedAssets();
/* 672 */     Universe.get().getWorlds().forEach((name, world) -> world.execute(()));
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
/* 702 */     ObjectArrayList<String> errors = new ObjectArrayList();
/* 703 */     validateSpawnsConfigurations("Beacon spawn", event.getLoadedAssets(), (List<String>)errors);
/* 704 */     for (ObjectListIterator<String> objectListIterator = errors.iterator(); objectListIterator.hasNext(); ) { String error = objectListIterator.next();
/* 705 */       getLogger().at(Level.SEVERE).log(error); }
/*     */   
/*     */   }
/*     */   
/*     */   private void onWorldNPCSpawnsRemoved(@Nonnull RemovedAssetsEvent<String, WorldNPCSpawn, IndexedLookupTableAssetMap<String, WorldNPCSpawn>> event) {
/* 710 */     for (String removed : event.getRemovedAssets()) {
/* 711 */       this.worldSpawnManager.onNPCSpawnRemoved(removed);
/*     */     }
/* 713 */     WorldSpawnManager.onEnvironmentChanged();
/*     */   }
/*     */   
/*     */   private void onBeaconNPCSpawnsRemoved(@Nonnull RemovedAssetsEvent<String, BeaconNPCSpawn, IndexedLookupTableAssetMap<String, BeaconNPCSpawn>> event) {
/* 717 */     for (String removed : event.getRemovedAssets()) {
/* 718 */       this.beaconSpawnManager.onNPCSpawnRemoved(removed);
/*     */     }
/*     */ 
/*     */     
/* 722 */     Set<String> removedAssets = event.getRemovedAssets();
/* 723 */     Universe.get().getWorlds().forEach((name, world) -> world.execute(()));
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
/*     */   private void onLoadedNPCEvent(@Nonnull AllNPCsLoadedEvent loadedNPCEvent) {
/* 751 */     IntOpenHashSet changeSet = new IntOpenHashSet();
/* 752 */     Int2ObjectMap<BuilderInfo> loadedNPCs = loadedNPCEvent.getLoadedNPCs();
/* 753 */     for (ObjectIterator<BuilderInfo> objectIterator = loadedNPCs.values().iterator(); objectIterator.hasNext(); ) { BuilderInfo builder = objectIterator.next();
/* 754 */       String key = builder.getKeyName();
/* 755 */       this.worldSpawnManager.onNPCLoaded(key, (IntSet)changeSet);
/* 756 */       this.beaconSpawnManager.onNPCLoaded(key, (IntSet)changeSet); }
/*     */ 
/*     */     
/* 759 */     this.worldSpawnManager.rebuildConfigurations((IntSet)changeSet);
/* 760 */     rebuildBeaconSpawnConfigurations((IntSet)changeSet);
/*     */   }
/*     */   
/*     */   private void setUpWithAllRoles() {
/* 764 */     IntOpenHashSet changeSet = new IntOpenHashSet();
/*     */     
/* 766 */     IndexedLookupTableAssetMap<String, WorldNPCSpawn> npcWorldSpawnMap = WorldNPCSpawn.getAssetMap();
/* 767 */     Map<String, WorldNPCSpawn> assetMap = npcWorldSpawnMap.getAssetMap();
/* 768 */     int worldSetupCount = 0;
/* 769 */     for (Map.Entry<String, WorldNPCSpawn> entry : assetMap.entrySet()) {
/* 770 */       WorldNPCSpawn value = entry.getValue();
/* 771 */       if (this.worldSpawnManager.addSpawnWrapper(new WorldSpawnWrapper(value))) {
/* 772 */         worldSetupCount++;
/*     */       }
/*     */       
/* 775 */       String key = entry.getKey();
/* 776 */       int index = npcWorldSpawnMap.getIndex(key);
/* 777 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 778 */       changeSet.add(index);
/*     */     } 
/*     */     
/* 781 */     IndexedLookupTableAssetMap<String, BeaconNPCSpawn> beaconSpawnMap = BeaconNPCSpawn.getAssetMap();
/* 782 */     Map<String, BeaconNPCSpawn> beaconSpawnAssetMap = beaconSpawnMap.getAssetMap();
/* 783 */     int beaconSetupCount = 0;
/* 784 */     for (Map.Entry<String, BeaconNPCSpawn> entry : beaconSpawnAssetMap.entrySet()) {
/* 785 */       BeaconNPCSpawn value = entry.getValue();
/* 786 */       if (this.beaconSpawnManager.addSpawnWrapper(new BeaconSpawnWrapper(value))) {
/* 787 */         beaconSetupCount++;
/*     */       }
/*     */       
/* 790 */       String key = entry.getKey();
/* 791 */       int index = beaconSpawnMap.getIndex(key);
/* 792 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 793 */       changeSet.add(index);
/*     */     } 
/*     */     
/* 796 */     WorldSpawnManager.trackNPCs((IntSet)changeSet);
/* 797 */     getLogger().at(Level.INFO).log("Successfully set up %s world spawn configurations", worldSetupCount);
/* 798 */     getLogger().at(Level.INFO).log("Successfully set up %s beacon spawn configurations", beaconSetupCount);
/*     */   }
/*     */   
/*     */   private void rebuildBeaconSpawnConfigurations(@Nullable IntSet changeSet) {
/* 802 */     if (changeSet == null || changeSet.isEmpty())
/*     */       return; 
/* 804 */     int setupCount = 0;
/* 805 */     for (IntIterator<Integer> intIterator = changeSet.iterator(); intIterator.hasNext(); ) { int configIndex = ((Integer)intIterator.next()).intValue();
/* 806 */       this.beaconSpawnManager.removeSpawnWrapper(configIndex);
/* 807 */       BeaconNPCSpawn spawn = (BeaconNPCSpawn)BeaconNPCSpawn.getAssetMap().getAssetOrDefault(configIndex, null);
/* 808 */       if (spawn != null && 
/* 809 */         this.beaconSpawnManager.addSpawnWrapper(new BeaconSpawnWrapper(spawn))) setupCount++;  }
/*     */     
/* 811 */     getLogger().at(Level.INFO).log("Successfully rebuilt %s beacon spawn configurations", setupCount);
/*     */   }
/*     */   
/*     */   private void onModelAssetChange(@Nonnull LoadedAssetsEvent<String, ModelAsset, DefaultAssetMap<String, ModelAsset>> event) {
/* 815 */     if (this.spawnMarkerModel == null)
/* 816 */       return;  Map<String, ModelAsset> modelMap = event.getLoadedAssets();
/* 817 */     ModelAsset modelAsset = modelMap.get(this.spawnMarkerModel.getModelAssetId());
/* 818 */     if (modelAsset == null)
/* 819 */       return;  this.spawnMarkerModel = Model.createUnitScaleModel(modelAsset);
/*     */   }
/*     */   
/*     */   private void onLoadAsset(@Nonnull LoadAssetEvent event) {
/* 823 */     HytaleLogger.getLogger().at(Level.INFO).log("Validating Spawn assets phase...");
/* 824 */     long start = System.nanoTime();
/*     */     
/* 826 */     ObjectArrayList<String> errors = new ObjectArrayList();
/* 827 */     validateSpawnsConfigurations("World spawn", WorldNPCSpawn.getAssetMap().getAssetMap(), (List<String>)errors);
/* 828 */     validateSpawnsConfigurations("Beacon spawn", BeaconNPCSpawn.getAssetMap().getAssetMap(), (List<String>)errors);
/* 829 */     validateSpawnMarkers(SpawnMarker.getAssetMap().getAssetMap(), (List<String>)errors);
/*     */     
/* 831 */     for (ObjectListIterator<String> objectListIterator = errors.iterator(); objectListIterator.hasNext(); ) { String error = objectListIterator.next();
/* 832 */       getLogger().at(Level.SEVERE).log(error); }
/*     */ 
/*     */     
/* 835 */     if (!errors.isEmpty()) event.failed(Options.getOptionSet().has(Options.VALIDATE_ASSETS), "failed to validate spawning assets");
/*     */     
/* 837 */     HytaleLogger.getLogger().at(Level.INFO).log("Spawn assets validation phase completed! Boot time %s, Took %s", FormatUtil.nanosToString(System.nanoTime() - event.getBootStart()), FormatUtil.nanosToString(System.nanoTime() - start));
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
/*     */   public static class NPCSpawningConfig
/*     */   {
/*     */     public static final BuilderCodec<NPCSpawningConfig> CODEC;
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
/*     */     static {
/* 866 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(NPCSpawningConfig.class, NPCSpawningConfig::new).append(new KeyedCodec("SpawnBudgetFactor", (Codec)Codec.DOUBLE), (o, i) -> o.spawnBudgetFactor = i.doubleValue(), o -> Double.valueOf(o.spawnBudgetFactor)).add()).append(new KeyedCodec("MaxActiveJobs", (Codec)Codec.INTEGER), (o, i) -> o.maxActiveJobs = i.intValue(), o -> Integer.valueOf(o.maxActiveJobs)).add()).append(new KeyedCodec("DefaultSpawnMarkerModel", (Codec)Codec.STRING), (o, i) -> o.defaultMarkerModel = i, o -> o.defaultMarkerModel).add()).append(new KeyedCodec("LocalSpawnControllerJoinDelay", (Codec)Codec.DOUBLE), (o, i) -> o.localSpawnControllerJoinDelay = i.doubleValue(), o -> Double.valueOf(o.localSpawnControllerJoinDelay)).add()).build();
/*     */     }
/* 868 */     private double spawnBudgetFactor = 1.0D;
/* 869 */     private int maxActiveJobs = 20;
/* 870 */     private String defaultMarkerModel = "NPC_Spawn_Marker";
/* 871 */     private double localSpawnControllerJoinDelay = 15.0D;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\SpawningPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */