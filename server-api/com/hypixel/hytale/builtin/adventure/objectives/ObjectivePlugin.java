/*     */ package com.hypixel.hytale.builtin.adventure.objectives;
/*     */ import com.hypixel.hytale.assetstore.AssetMap;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.completion.ObjectiveCompletion;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.components.ObjectiveHistoryComponent;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveLineAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveLocationMarkerAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.completion.ClearObjectiveItemsCompletionAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.completion.GiveItemsCompletionAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.completion.ObjectiveCompletionAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.CraftObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.GatherObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ReachLocationTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.TreasureMapObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.UseBlockObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.UseEntityObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ObjectiveHistoryData;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ObjectiveLineHistoryData;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.interactions.StartObjectiveInteraction;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.markers.objectivelocation.ObjectiveLocationMarker;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.markers.objectivelocation.ObjectiveLocationMarkerSystems;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.markers.reachlocation.ReachLocationMarker;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.markers.reachlocation.ReachLocationMarkerAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.markers.reachlocation.ReachLocationMarkerSystems;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.CraftObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.GatherObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.ObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.ReachLocationTask;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.TreasureMapObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.UseBlockObjectiveTask;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.UseEntityObjectiveTask;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.AndQuery;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.function.function.TriFunction;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.assets.TrackOrUpdateObjective;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerConfigData;
/*     */ import com.hypixel.hytale.server.core.event.events.entity.LivingEntityInventoryChangeEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.datastore.DataStoreProvider;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.AddWorldEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ObjectivePlugin extends JavaPlugin {
/*     */   public static ObjectivePlugin get() {
/*  87 */     return instance;
/*     */   }
/*     */   
/*     */   protected static ObjectivePlugin instance;
/*     */   public static final String OBJECTIVE_LOCATION_MARKER_MODEL_ID = "Objective_Location_Marker";
/*     */   public static final long SAVE_INTERVAL_MINUTES = 5L;
/*  93 */   private final Map<Class<? extends ObjectiveTaskAsset>, TriFunction<ObjectiveTaskAsset, Integer, Integer, ? extends ObjectiveTask>> taskGenerators = new ConcurrentHashMap<>();
/*  94 */   private final Map<Class<? extends ObjectiveCompletionAsset>, Function<ObjectiveCompletionAsset, ? extends ObjectiveCompletion>> completionGenerators = new ConcurrentHashMap<>();
/*  95 */   private final Config<ObjectivePluginConfig> config = withConfig(ObjectivePluginConfig.CODEC);
/*     */   
/*     */   private Model objectiveLocationMarkerModel;
/*     */   private ComponentType<EntityStore, ObjectiveHistoryComponent> objectiveHistoryComponentType;
/*     */   private ComponentType<EntityStore, ReachLocationMarker> reachLocationMarkerComponentType;
/*     */   private ComponentType<EntityStore, ObjectiveLocationMarker> objectiveLocationMarkerComponentType;
/*     */   private ObjectiveDataStore objectiveDataStore;
/*     */   
/*     */   public ObjectivePlugin(@Nonnull JavaPluginInit init) {
/* 104 */     super(init);
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, ObjectiveHistoryComponent> getObjectiveHistoryComponentType() {
/* 108 */     return this.objectiveHistoryComponentType;
/*     */   }
/*     */   
/*     */   public Model getObjectiveLocationMarkerModel() {
/* 112 */     return this.objectiveLocationMarkerModel;
/*     */   }
/*     */   
/*     */   public ObjectiveDataStore getObjectiveDataStore() {
/* 116 */     return this.objectiveDataStore;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/* 121 */     instance = this;
/*     */     
/* 123 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(ObjectiveAsset.class, (AssetMap)new DefaultAssetMap())
/* 124 */         .setPath("Objective/Objectives"))
/* 125 */         .setCodec((AssetCodec)ObjectiveAsset.CODEC))
/* 126 */         .setKeyFunction(ObjectiveAsset::getId))
/* 127 */         .loadsAfter(new Class[] { ItemDropList.class, Item.class, BlockType.class, ReachLocationMarkerAsset.class
/* 128 */           })).build());
/*     */     
/* 130 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(ObjectiveLineAsset.class, (AssetMap)new DefaultAssetMap())
/* 131 */         .setPath("Objective/ObjectiveLines"))
/* 132 */         .setCodec((AssetCodec)ObjectiveLineAsset.CODEC))
/* 133 */         .setKeyFunction(ObjectiveLineAsset::getId))
/* 134 */         .loadsAfter(new Class[] { ObjectiveAsset.class
/* 135 */           })).loadsBefore(new Class[] { GameplayConfig.class
/* 136 */           })).build());
/*     */     
/* 138 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(ObjectiveLocationMarkerAsset.class, (AssetMap)new DefaultAssetMap())
/* 139 */         .setPath("Objective/ObjectiveLocationMarkers"))
/* 140 */         .setCodec((AssetCodec)ObjectiveLocationMarkerAsset.CODEC))
/* 141 */         .setKeyFunction(ObjectiveLocationMarkerAsset::getId))
/* 142 */         .loadsAfter(new Class[] { ObjectiveAsset.class, Environment.class, Weather.class
/* 143 */           })).build());
/*     */     
/* 145 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(ReachLocationMarkerAsset.class, (AssetMap)new DefaultAssetMap())
/* 146 */         .setPath("Objective/ReachLocationMarkers"))
/* 147 */         .setCodec((AssetCodec)ReachLocationMarkerAsset.CODEC))
/* 148 */         .setKeyFunction(ReachLocationMarkerAsset::getId))
/* 149 */         .build());
/*     */     
/* 151 */     this.objectiveDataStore = new ObjectiveDataStore(((ObjectivePluginConfig)this.config.get()).getDataStoreProvider().create(Objective.CODEC));
/*     */     
/* 153 */     this.reachLocationMarkerComponentType = getEntityStoreRegistry().registerComponent(ReachLocationMarker.class, "ReachLocationMarker", ReachLocationMarker.CODEC);
/* 154 */     this.objectiveLocationMarkerComponentType = getEntityStoreRegistry().registerComponent(ObjectiveLocationMarker.class, "ObjectiveLocation", ObjectiveLocationMarker.CODEC);
/*     */     
/* 156 */     registerTask("Craft", CraftObjectiveTaskAsset.class, (Codec<CraftObjectiveTaskAsset>)CraftObjectiveTaskAsset.CODEC, CraftObjectiveTask.class, (Codec<CraftObjectiveTask>)CraftObjectiveTask.CODEC, CraftObjectiveTask::new);
/* 157 */     registerTask("Gather", GatherObjectiveTaskAsset.class, (Codec<GatherObjectiveTaskAsset>)GatherObjectiveTaskAsset.CODEC, GatherObjectiveTask.class, (Codec<GatherObjectiveTask>)GatherObjectiveTask.CODEC, GatherObjectiveTask::new);
/* 158 */     registerTask("UseBlock", UseBlockObjectiveTaskAsset.class, (Codec<UseBlockObjectiveTaskAsset>)UseBlockObjectiveTaskAsset.CODEC, UseBlockObjectiveTask.class, (Codec<UseBlockObjectiveTask>)UseBlockObjectiveTask.CODEC, UseBlockObjectiveTask::new);
/* 159 */     registerTask("UseEntity", UseEntityObjectiveTaskAsset.class, (Codec<UseEntityObjectiveTaskAsset>)UseEntityObjectiveTaskAsset.CODEC, UseEntityObjectiveTask.class, (Codec<UseEntityObjectiveTask>)UseEntityObjectiveTask.CODEC, UseEntityObjectiveTask::new);
/* 160 */     registerTask("TreasureMap", TreasureMapObjectiveTaskAsset.class, (Codec<TreasureMapObjectiveTaskAsset>)TreasureMapObjectiveTaskAsset.CODEC, TreasureMapObjectiveTask.class, (Codec<TreasureMapObjectiveTask>)TreasureMapObjectiveTask.CODEC, TreasureMapObjectiveTask::new);
/* 161 */     registerTask("ReachLocation", ReachLocationTaskAsset.class, (Codec<ReachLocationTaskAsset>)ReachLocationTaskAsset.CODEC, ReachLocationTask.class, (Codec<ReachLocationTask>)ReachLocationTask.CODEC, ReachLocationTask::new);
/*     */     
/* 163 */     registerCompletion("GiveItems", GiveItemsCompletionAsset.class, (Codec<GiveItemsCompletionAsset>)GiveItemsCompletionAsset.CODEC, com.hypixel.hytale.builtin.adventure.objectives.completion.GiveItemsCompletion::new);
/* 164 */     registerCompletion("ClearObjectiveItems", ClearObjectiveItemsCompletionAsset.class, (Codec<ClearObjectiveItemsCompletionAsset>)ClearObjectiveItemsCompletionAsset.CODEC, com.hypixel.hytale.builtin.adventure.objectives.completion.ClearObjectiveItemsCompletion::new);
/*     */     
/* 166 */     getEventRegistry().register(LoadedAssetsEvent.class, ObjectiveLineAsset.class, this::onObjectiveLineAssetLoaded);
/* 167 */     getEventRegistry().register(LoadedAssetsEvent.class, ObjectiveAsset.class, this::onObjectiveAssetLoaded);
/* 168 */     getEventRegistry().register(PlayerDisconnectEvent.class, this::onPlayerDisconnect);
/* 169 */     getEventRegistry().register(LoadedAssetsEvent.class, ObjectiveLocationMarkerAsset.class, ObjectivePlugin::onObjectiveLocationMarkerChange);
/* 170 */     getEventRegistry().register(LoadedAssetsEvent.class, ModelAsset.class, this::onModelAssetChange);
/* 171 */     getEventRegistry().registerGlobal(LivingEntityInventoryChangeEvent.class, this::onLivingEntityInventoryChange);
/* 172 */     getEventRegistry().registerGlobal(AddWorldEvent.class, this::onWorldAdded);
/*     */     
/* 174 */     getCommandRegistry().registerCommand((AbstractCommand)new ObjectiveCommand());
/*     */     
/* 176 */     EntityModule entityModule = EntityModule.get();
/* 177 */     ComponentType<EntityStore, PlayerRef> playerRefComponentType = PlayerRef.getComponentType();
/* 178 */     ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> playerSpatialComponent = entityModule.getPlayerSpatialResourceType();
/*     */     
/* 180 */     getEntityStoreRegistry().registerSystem((ISystem)new ReachLocationMarkerSystems.EntityAdded(this.reachLocationMarkerComponentType));
/* 181 */     getEntityStoreRegistry().registerSystem((ISystem)new ReachLocationMarkerSystems.EnsureNetworkSendable());
/* 182 */     getEntityStoreRegistry().registerSystem((ISystem)new ReachLocationMarkerSystems.Ticking(this.reachLocationMarkerComponentType, playerSpatialComponent));
/*     */     
/* 184 */     getEntityStoreRegistry().registerSystem((ISystem)new ObjectiveLocationMarkerSystems.EnsureNetworkSendableSystem());
/* 185 */     getEntityStoreRegistry().registerSystem((ISystem)new ObjectiveLocationMarkerSystems.InitSystem(this.objectiveLocationMarkerComponentType));
/* 186 */     getEntityStoreRegistry().registerSystem((ISystem)new ObjectiveLocationMarkerSystems.TickingSystem(this.objectiveLocationMarkerComponentType, playerRefComponentType, playerSpatialComponent));
/*     */     
/* 188 */     CommonObjectiveHistoryData.CODEC.register("Objective", ObjectiveHistoryData.class, (Codec)ObjectiveHistoryData.CODEC);
/* 189 */     CommonObjectiveHistoryData.CODEC.register("ObjectiveLine", ObjectiveLineHistoryData.class, (Codec)ObjectiveLineHistoryData.CODEC);
/*     */     
/* 191 */     ObjectiveRewardHistoryData.CODEC.register("Item", ItemObjectiveRewardHistoryData.class, (Codec)ItemObjectiveRewardHistoryData.CODEC);
/*     */     
/* 193 */     this.objectiveHistoryComponentType = getEntityStoreRegistry().registerComponent(ObjectiveHistoryComponent.class, "ObjectiveHistory", ObjectiveHistoryComponent.CODEC);
/*     */     
/* 195 */     getEntityStoreRegistry().registerSystem((ISystem)new ObjectivePlayerSetupSystem(this.objectiveHistoryComponentType, Player.getComponentType()));
/* 196 */     getEntityStoreRegistry().registerSystem((ISystem)new ObjectiveItemEntityRemovalSystem());
/*     */     
/* 198 */     getCodecRegistry(Interaction.CODEC).register("StartObjective", StartObjectiveInteraction.class, StartObjectiveInteraction.CODEC);
/* 199 */     getCodecRegistry(Interaction.CODEC).register("CanBreakRespawnPoint", CanBreakRespawnPointInteraction.class, CanBreakRespawnPointInteraction.CODEC);
/*     */     
/* 201 */     BlockStateModule.get().registerBlockState(TreasureChestState.class, "TreasureChest", (Codec)TreasureChestState.CODEC);
/*     */     
/* 203 */     getCodecRegistry(GameplayConfig.PLUGIN_CODEC).register(ObjectiveGameplayConfig.class, "Objective", (Codec)ObjectiveGameplayConfig.CODEC);
/*     */     
/* 205 */     getEntityStoreRegistry().registerSystem((ISystem)new EntityModule.TangibleMigrationSystem((Query)Query.or(new Query[] { (Query)ObjectiveLocationMarker.getComponentType(), (Query)ReachLocationMarker.getComponentType() }, )), true);
/* 206 */     getEntityStoreRegistry().registerSystem((ISystem)new EntityModule.HiddenFromPlayerMigrationSystem((Query)Query.or(new Query[] { (Query)ObjectiveLocationMarker.getComponentType(), (Query)ReachLocationMarker.getComponentType() }, )), true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start() {
/* 211 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset("Objective_Location_Marker");
/* 212 */     if (modelAsset == null) {
/* 213 */       throw new IllegalStateException(String.format("Default objective location marker model '%s' not found", new Object[] { "Objective_Location_Marker" }));
/*     */     }
/*     */     
/* 216 */     this.objectiveLocationMarkerModel = Model.createUnitScaleModel(modelAsset);
/*     */     
/* 218 */     HytaleServer.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(() -> this.objectiveDataStore.saveToDiskAllObjectives(), 5L, 5L, TimeUnit.MINUTES);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void shutdown() {
/* 224 */     this.objectiveDataStore.saveToDiskAllObjectives();
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, ReachLocationMarker> getReachLocationMarkerComponentType() {
/* 228 */     return this.reachLocationMarkerComponentType;
/*     */   }
/*     */   
/*     */   public ComponentType<EntityStore, ObjectiveLocationMarker> getObjectiveLocationMarkerComponentType() {
/* 232 */     return this.objectiveLocationMarkerComponentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends ObjectiveTaskAsset, U extends ObjectiveTask> void registerTask(String id, Class<T> assetClass, Codec<T> assetCodec, Class<U> implementationClass, Codec<U> implementationCodec, TriFunction<T, Integer, Integer, U> generator) {
/* 239 */     ObjectiveTaskAsset.CODEC.register(id, assetClass, assetCodec);
/* 240 */     ObjectiveTask.CODEC.register(id, implementationClass, implementationCodec);
/*     */     
/* 242 */     this.taskGenerators.put(assetClass, generator);
/* 243 */     this.objectiveDataStore.registerTaskRef(implementationClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends ObjectiveCompletionAsset, U extends ObjectiveCompletion> void registerCompletion(String id, Class<T> assetClass, Codec<T> codec, Function<T, U> generator) {
/* 248 */     ObjectiveCompletionAsset.CODEC.register(id, assetClass, codec);
/*     */     
/* 250 */     this.completionGenerators.put(assetClass, generator);
/*     */   }
/*     */   
/*     */   public ObjectiveTask createTask(@Nonnull ObjectiveTaskAsset task, int taskSetIndex, int taskIndex) {
/* 254 */     return (ObjectiveTask)((TriFunction)this.taskGenerators.get(task.getClass())).apply(task, Integer.valueOf(taskSetIndex), Integer.valueOf(taskIndex));
/*     */   }
/*     */   
/*     */   public ObjectiveCompletion createCompletion(@Nonnull ObjectiveCompletionAsset completionAsset) {
/* 258 */     return ((Function<ObjectiveCompletionAsset, ObjectiveCompletion>)this.completionGenerators.get(completionAsset.getClass())).apply(completionAsset);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Objective startObjective(@Nonnull String objectiveId, @Nonnull Set<UUID> playerUUIDs, @Nonnull UUID worldUUID, @Nullable UUID markerUUID, @Nonnull Store<EntityStore> store) {
/* 263 */     return startObjective(objectiveId, (UUID)null, playerUUIDs, worldUUID, markerUUID, store);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Objective startObjective(@Nonnull String objectiveId, @Nullable UUID objectiveUUID, @Nonnull Set<UUID> playerUUIDs, @Nonnull UUID worldUUID, @Nullable UUID markerUUID, @Nonnull Store<EntityStore> store) {
/* 268 */     ObjectiveAsset asset = (ObjectiveAsset)ObjectiveAsset.getAssetMap().getAsset(objectiveId);
/* 269 */     if (asset == null) {
/* 270 */       getLogger().at(Level.WARNING).log("Failed to find objective asset '%s'", objectiveId);
/* 271 */       return null;
/*     */     } 
/*     */     
/* 274 */     if (markerUUID == null && !asset.isValidForPlayer()) {
/* 275 */       getLogger().at(Level.WARNING).log("Objective %s can't be used for Player", asset.getId());
/* 276 */       return null;
/*     */     } 
/*     */     
/* 279 */     Objective objective = new Objective(asset, objectiveUUID, playerUUIDs, worldUUID, markerUUID);
/* 280 */     boolean setupResult = objective.setup(store);
/* 281 */     Message assetTitleMessage = Message.translation(asset.getTitleKey());
/*     */     
/* 283 */     if (!setupResult || !this.objectiveDataStore.addObjective(objective.getObjectiveUUID(), objective)) {
/* 284 */       getLogger().at(Level.WARNING).log("Failed to start objective %s", asset.getId());
/*     */       
/* 286 */       if (objective.getPlayerUUIDs() == null) return null;
/*     */ 
/*     */       
/* 289 */       objective.forEachParticipant(participantReference -> {
/*     */             PlayerRef playerRefComponent = (PlayerRef)store.getComponent(participantReference, PlayerRef.getComponentType());
/*     */ 
/*     */             
/*     */             if (playerRefComponent != null) {
/*     */               playerRefComponent.sendMessage(Message.translation("server.modules.objective.start.failed").param("title", assetTitleMessage));
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 299 */       return null;
/*     */     } 
/*     */     
/* 302 */     if (objective.getPlayerUUIDs() == null) return objective; 
/* 303 */     TrackOrUpdateObjective trackObjectivePacket = new TrackOrUpdateObjective(objective.toPacket());
/*     */     
/* 305 */     String objectiveAssetId = asset.getId();
/*     */ 
/*     */     
/* 308 */     objective.forEachParticipant(participantReference -> {
/*     */           Player playerComponent = (Player)store.getComponent(participantReference, Player.getComponentType());
/*     */           
/*     */           if (playerComponent == null) {
/*     */             return;
/*     */           }
/*     */           
/*     */           if (!canPlayerDoObjective(playerComponent, objectiveAssetId)) {
/*     */             playerComponent.sendMessage(Message.translation("server.modules.objective.playerAlreadyDoingObjective").param("title", assetTitleMessage));
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           PlayerRef playerRefComponent = (PlayerRef)store.getComponent(participantReference, PlayerRef.getComponentType());
/*     */           
/*     */           assert playerRefComponent != null;
/*     */           
/*     */           UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(participantReference, UUIDComponent.getComponentType());
/*     */           
/*     */           assert uuidComponent != null;
/*     */           
/*     */           objective.addActivePlayerUUID(uuidComponent.getUuid());
/*     */           
/*     */           PlayerConfigData playerConfigData = playerComponent.getPlayerConfigData();
/*     */           HashSet<UUID> activeObjectiveUUIDs = new HashSet<>(playerConfigData.getActiveObjectiveUUIDs());
/*     */           activeObjectiveUUIDs.add(objective.getObjectiveUUID());
/*     */           playerConfigData.setActiveObjectiveUUIDs(activeObjectiveUUIDs);
/*     */           playerRefComponent.sendMessage(Message.translation("server.modules.objective.start.success").param("title", assetTitleMessage));
/*     */           playerRefComponent.sendMessage(objective.getTaskInfoMessage());
/*     */           playerRefComponent.getPacketHandler().writeNoCache((Packet)trackObjectivePacket);
/*     */         });
/* 339 */     objective.markDirty();
/*     */     
/* 341 */     return objective;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlayerDoObjective(@Nonnull Player player, @Nonnull String objectiveAssetId) {
/* 346 */     Set<UUID> activeObjectiveUUIDs = player.getPlayerConfigData().getActiveObjectiveUUIDs();
/* 347 */     if (activeObjectiveUUIDs == null) return true;
/*     */     
/* 349 */     for (UUID objectiveUUID : activeObjectiveUUIDs) {
/* 350 */       Objective objective = this.objectiveDataStore.getObjective(objectiveUUID);
/* 351 */       if (objective == null)
/*     */         continue; 
/* 353 */       if (objective.getObjectiveId().equals(objectiveAssetId)) return false;
/*     */     
/*     */     } 
/* 356 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Objective startObjectiveLine(@Nonnull Store<EntityStore> store, @Nonnull String objectiveLineId, @Nonnull Set<UUID> playerUUIDs, @Nonnull UUID worldUUID, @Nullable UUID markerUUID) {
/* 367 */     ObjectiveLineAsset objectiveLineAsset = (ObjectiveLineAsset)ObjectiveLineAsset.getAssetMap().getAsset(objectiveLineId);
/* 368 */     if (objectiveLineAsset == null) return null;
/*     */     
/* 370 */     String[] objectiveIds = objectiveLineAsset.getObjectiveIds();
/* 371 */     if (objectiveIds == null || objectiveIds.length == 0) return null;
/*     */     
/* 373 */     Universe universe = Universe.get();
/* 374 */     HashSet<UUID> playerList = new HashSet<>();
/*     */     
/* 376 */     for (UUID playerUUID : playerUUIDs) {
/* 377 */       PlayerRef playerRef = universe.getPlayer(playerUUID);
/* 378 */       if (playerRef == null)
/*     */         continue; 
/* 380 */       Ref<EntityStore> playerReference = playerRef.getReference();
/* 381 */       if (playerReference == null || !playerReference.isValid())
/*     */         continue; 
/* 383 */       Player playerComponent = (Player)store.getComponent(playerReference, Player.getComponentType());
/* 384 */       assert playerComponent != null;
/*     */       
/* 386 */       if (canPlayerDoObjectiveLine(playerComponent, objectiveLineId)) {
/* 387 */         playerList.add(playerUUID); continue;
/*     */       } 
/* 389 */       Message objectiveLineIdMessage = Message.translation(objectiveLineId);
/* 390 */       playerRef.sendMessage(Message.translation("server.modules.objective.playerAlreadyDoingObjectiveLine")
/* 391 */           .param("id", objectiveLineIdMessage));
/*     */     } 
/*     */ 
/*     */     
/* 395 */     Objective objective = startObjective(objectiveLineAsset.getObjectiveIds()[0], playerList, worldUUID, markerUUID, store);
/* 396 */     if (objective == null) return null;
/*     */     
/* 398 */     objective.setObjectiveLineHistoryData(new ObjectiveLineHistoryData(objectiveLineId, objectiveLineAsset.getCategory(), objectiveLineAsset.getNextObjectiveLineIds()));
/* 399 */     objective.checkTaskSetCompletion(store);
/*     */     
/* 401 */     return objective;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlayerDoObjectiveLine(@Nonnull Player player, @Nonnull String objectiveLineId) {
/* 406 */     Set<UUID> activeObjectiveUUIDs = player.getPlayerConfigData().getActiveObjectiveUUIDs();
/* 407 */     if (activeObjectiveUUIDs == null) return true;
/*     */     
/* 409 */     for (UUID objectiveUUID : activeObjectiveUUIDs) {
/* 410 */       Objective objective = this.objectiveDataStore.getObjective(objectiveUUID);
/* 411 */       if (objective == null)
/*     */         continue; 
/* 413 */       ObjectiveLineHistoryData objectiveLineHistoryData = objective.getObjectiveLineHistoryData();
/* 414 */       if (objectiveLineHistoryData != null && objectiveLineId.equals(objectiveLineHistoryData.getId())) return false;
/*     */     
/*     */     } 
/* 417 */     return true;
/*     */   }
/*     */   
/*     */   public void objectiveCompleted(@Nonnull Objective objective, @Nonnull Store<EntityStore> store) {
/* 421 */     for (UUID playerUUID : objective.getPlayerUUIDs()) {
/* 422 */       untrackObjectiveForPlayer(objective, playerUUID);
/*     */     }
/*     */     
/* 425 */     UUID objectiveUUID = objective.getObjectiveUUID();
/* 426 */     this.objectiveDataStore.removeObjective(objectiveUUID);
/* 427 */     if (!this.objectiveDataStore.removeFromDisk(objectiveUUID.toString()))
/*     */       return; 
/* 429 */     ObjectiveLineAsset objectiveLineAsset = objective.getObjectiveLineAsset();
/* 430 */     if (objectiveLineAsset == null) {
/* 431 */       storeObjectiveHistoryData(objective);
/*     */       
/*     */       return;
/*     */     } 
/* 435 */     ObjectiveLineHistoryData objectiveLineHistoryData = objective.getObjectiveLineHistoryData();
/* 436 */     assert objectiveLineHistoryData != null;
/*     */     
/* 438 */     objectiveLineHistoryData.addObjectiveHistoryData(objective.getObjectiveHistoryData());
/*     */     
/* 440 */     String nextObjectiveId = objectiveLineAsset.getNextObjectiveId(objective.getObjectiveId());
/* 441 */     if (nextObjectiveId == null) {
/* 442 */       storeObjectiveLineHistoryData(objectiveLineHistoryData, objective.getPlayerUUIDs());
/*     */       
/* 444 */       String[] nextObjectiveLineIds = objectiveLineHistoryData.getNextObjectiveLineIds();
/* 445 */       if (nextObjectiveLineIds == null)
/*     */         return; 
/* 447 */       for (String nextObjectiveLineId : nextObjectiveLineIds) {
/* 448 */         startObjectiveLine(store, nextObjectiveLineId, objective.getPlayerUUIDs(), objective.getWorldUUID(), objective.getMarkerUUID());
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 454 */     Objective newObjective = startObjective(nextObjectiveId, objectiveUUID, objective.getPlayerUUIDs(), objective.getWorldUUID(), objective.getMarkerUUID(), store);
/* 455 */     if (newObjective == null)
/*     */       return; 
/* 457 */     newObjective.setObjectiveLineHistoryData(objectiveLineHistoryData);
/* 458 */     newObjective.checkTaskSetCompletion(store);
/*     */   }
/*     */   
/*     */   public void storeObjectiveHistoryData(@Nonnull Objective objective) {
/* 462 */     String objectiveId = objective.getObjectiveId();
/* 463 */     Universe universe = Universe.get();
/*     */     
/* 465 */     for (Iterator<UUID> iterator = objective.getPlayerUUIDs().iterator(); iterator.hasNext(); ) { UUID playerUUID = iterator.next();
/* 466 */       PlayerRef playerRef = universe.getPlayer(playerUUID);
/* 467 */       if (playerRef == null || !playerRef.isValid()) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 472 */       Ref<EntityStore> playerReference = playerRef.getReference();
/* 473 */       if (playerReference == null || !playerReference.isValid()) {
/*     */         continue;
/*     */       }
/*     */       
/* 477 */       Store<EntityStore> store = playerReference.getStore();
/* 478 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/* 479 */       world.execute(() -> {
/*     */             ObjectiveHistoryComponent objectiveHistoryComponent = (ObjectiveHistoryComponent)store.getComponent(playerReference, this.objectiveHistoryComponentType);
/*     */             assert objectiveHistoryComponent != null;
/*     */             Map<String, ObjectiveHistoryData> completedObjectiveDataMap = objectiveHistoryComponent.getObjectiveHistoryMap();
/*     */             ObjectiveHistoryData completedObjectiveData = completedObjectiveDataMap.get(objectiveId);
/*     */             if (completedObjectiveData != null) {
/*     */               completedObjectiveData.completed(playerUUID, objective.getObjectiveHistoryData());
/*     */             } else {
/*     */               completedObjectiveDataMap.put(objectiveId, objective.getObjectiveHistoryData().cloneForPlayer(playerUUID));
/*     */             } 
/*     */           }); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeObjectiveLineHistoryData(@Nonnull ObjectiveLineHistoryData objectiveLineHistoryData, @Nonnull Set<UUID> playerUUIDs) {
/* 496 */     Map<UUID, ObjectiveLineHistoryData> objectiveLineHistoryPerPlayerMap = objectiveLineHistoryData.cloneForPlayers(playerUUIDs);
/* 497 */     String objectiveLineId = objectiveLineHistoryData.getId();
/* 498 */     Universe universe = Universe.get();
/*     */     
/* 500 */     for (Iterator<Map.Entry<UUID, ObjectiveLineHistoryData>> iterator = objectiveLineHistoryPerPlayerMap.entrySet().iterator(); iterator.hasNext(); ) { Map.Entry<UUID, ObjectiveLineHistoryData> entry = iterator.next();
/* 501 */       UUID playerUUID = entry.getKey();
/*     */       
/* 503 */       PlayerRef playerRef = universe.getPlayer(playerUUID);
/* 504 */       if (playerRef == null || !playerRef.isValid()) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 509 */       Ref<EntityStore> playerReference = playerRef.getReference();
/* 510 */       if (playerReference == null || !playerReference.isValid()) {
/*     */         continue;
/*     */       }
/*     */       
/* 514 */       Store<EntityStore> store = playerReference.getStore();
/* 515 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/* 516 */       world.execute(() -> {
/*     */             ObjectiveHistoryComponent objectiveHistoryComponent = (ObjectiveHistoryComponent)store.getComponent(playerReference, this.objectiveHistoryComponentType);
/*     */             assert objectiveHistoryComponent != null;
/*     */             Map<String, ObjectiveLineHistoryData> completedObjectiveLineDataMap = objectiveHistoryComponent.getObjectiveLineHistoryMap();
/*     */             ObjectiveLineHistoryData completedObjectiveLineData = completedObjectiveLineDataMap.get(objectiveLineId);
/*     */             if (completedObjectiveLineData != null) {
/*     */               completedObjectiveLineData.completed(playerUUID, (ObjectiveLineHistoryData)entry.getValue());
/*     */             } else {
/*     */               completedObjectiveLineDataMap.put(objectiveLineId, (ObjectiveLineHistoryData)entry.getValue());
/*     */             } 
/*     */           }); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelObjective(@Nonnull UUID objectiveUUID, @Nonnull Store<EntityStore> store) {
/* 533 */     Objective objective = this.objectiveDataStore.loadObjective(objectiveUUID, store);
/* 534 */     if (objective == null)
/*     */       return; 
/* 536 */     objective.cancel();
/*     */     
/* 538 */     for (UUID playerUUID : objective.getPlayerUUIDs()) {
/* 539 */       untrackObjectiveForPlayer(objective, playerUUID);
/*     */     }
/*     */     
/* 542 */     this.objectiveDataStore.removeObjective(objectiveUUID);
/* 543 */     this.objectiveDataStore.removeFromDisk(objectiveUUID.toString());
/*     */   }
/*     */   
/*     */   public void untrackObjectiveForPlayer(@Nonnull Objective objective, @Nonnull UUID playerUUID) {
/* 547 */     UUID objectiveUUID = objective.getObjectiveUUID();
/* 548 */     ObjectiveTask[] currentTasks = objective.getCurrentTasks();
/* 549 */     for (ObjectiveTask task : currentTasks) {
/* 550 */       if (task instanceof UseEntityObjectiveTask)
/*     */       {
/* 552 */         this.objectiveDataStore.removeEntityTaskForPlayer(objectiveUUID, ((UseEntityObjectiveTask)task).getAsset().getTaskId(), playerUUID);
/*     */       }
/*     */     } 
/* 555 */     PlayerRef playerRef = Universe.get().getPlayer(playerUUID);
/* 556 */     if (playerRef == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 561 */     Player player = (Player)playerRef.getComponent(Player.getComponentType());
/* 562 */     HashSet<UUID> activeObjectiveUUIDs = new HashSet<>(player.getPlayerConfigData().getActiveObjectiveUUIDs());
/* 563 */     activeObjectiveUUIDs.remove(objectiveUUID);
/* 564 */     player.getPlayerConfigData().setActiveObjectiveUUIDs(activeObjectiveUUIDs);
/*     */     
/* 566 */     playerRef.getPacketHandler().writeNoCache((Packet)new UntrackObjective(objectiveUUID));
/*     */   }
/*     */   
/*     */   public void addPlayerToExistingObjective(@Nonnull Store<EntityStore> store, @Nonnull UUID playerUUID, @Nonnull UUID objectiveUUID) {
/* 570 */     Objective objective = this.objectiveDataStore.loadObjective(objectiveUUID, store);
/* 571 */     if (objective == null)
/*     */       return; 
/* 573 */     objective.addActivePlayerUUID(playerUUID);
/*     */     
/* 575 */     ObjectiveDataStore objectiveDataStore = get().getObjectiveDataStore();
/*     */     
/* 577 */     ObjectiveTask[] currentTasks = objective.getCurrentTasks();
/* 578 */     for (ObjectiveTask task : currentTasks) {
/* 579 */       if (task instanceof UseEntityObjectiveTask)
/*     */       {
/* 581 */         objectiveDataStore.addEntityTaskForPlayer(playerUUID, ((UseEntityObjectiveTask)task).getAsset().getTaskId(), objectiveUUID);
/*     */       }
/*     */     } 
/* 584 */     PlayerRef playerRef = Universe.get().getPlayer(playerUUID);
/* 585 */     if (playerRef == null || !playerRef.isValid()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 590 */     Ref<EntityStore> playerReference = playerRef.getReference();
/* 591 */     if (playerReference == null || !playerReference.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 595 */     Player playerComponent = (Player)store.getComponent(playerReference, Player.getComponentType());
/* 596 */     assert playerComponent != null;
/*     */     
/* 598 */     HashSet<UUID> activeObjectiveUUIDs = new HashSet<>(playerComponent.getPlayerConfigData().getActiveObjectiveUUIDs());
/* 599 */     activeObjectiveUUIDs.add(objectiveUUID);
/* 600 */     playerComponent.getPlayerConfigData().setActiveObjectiveUUIDs(activeObjectiveUUIDs);
/*     */     
/* 602 */     playerRef.getPacketHandler().writeNoCache((Packet)new TrackOrUpdateObjective(objective.toPacket()));
/*     */   }
/*     */   
/*     */   public void removePlayerFromExistingObjective(@Nonnull Store<EntityStore> store, @Nonnull UUID playerUUID, @Nonnull UUID objectiveUUID) {
/* 606 */     Objective objective = this.objectiveDataStore.loadObjective(objectiveUUID, store);
/* 607 */     if (objective == null)
/*     */       return; 
/* 609 */     objective.removeActivePlayerUUID(playerUUID);
/* 610 */     if (objective.getActivePlayerUUIDs().isEmpty()) {
/* 611 */       this.objectiveDataStore.saveToDisk(objectiveUUID.toString(), objective);
/* 612 */       this.objectiveDataStore.unloadObjective(objectiveUUID);
/*     */     } 
/*     */     
/* 615 */     untrackObjectiveForPlayer(objective, playerUUID);
/*     */   }
/*     */   
/*     */   private void onPlayerDisconnect(@Nonnull PlayerDisconnectEvent event) {
/* 619 */     PlayerRef playerRef = event.getPlayerRef();
/* 620 */     Ref<EntityStore> ref = playerRef.getReference();
/* 621 */     if (ref == null)
/*     */       return; 
/* 623 */     Store<EntityStore> store = ref.getStore();
/* 624 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 626 */     world.execute(() -> {
/*     */           if (!ref.isValid()) {
/*     */             return;
/*     */           }
/*     */           UUID playerUUID = playerRef.getUuid();
/*     */           getLogger().at(Level.INFO).log("Checking objectives for disconnecting player '" + playerRef.getUsername() + "' (" + String.valueOf(playerUUID) + ")");
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (playerComponent == null) {
/*     */             return;
/*     */           }
/*     */           Set<UUID> activeObjectiveUUIDs = playerComponent.getPlayerConfigData().getActiveObjectiveUUIDs();
/*     */           if (activeObjectiveUUIDs == null) {
/*     */             getLogger().at(Level.INFO).log("No active objectives found for player '" + playerRef.getUsername() + "' (" + String.valueOf(playerUUID) + ")");
/*     */             return;
/*     */           } 
/*     */           getLogger().at(Level.INFO).log("Processing " + activeObjectiveUUIDs.size() + " active objectives for '" + playerRef.getUsername() + "' (" + String.valueOf(playerUUID) + ")");
/*     */           for (UUID objectiveUUID : activeObjectiveUUIDs) {
/*     */             Objective objective = this.objectiveDataStore.getObjective(objectiveUUID);
/*     */             if (objective == null) {
/*     */               continue;
/*     */             }
/*     */             objective.removeActivePlayerUUID(playerUUID);
/*     */             if (!objective.getActivePlayerUUIDs().isEmpty()) {
/*     */               continue;
/*     */             }
/*     */             this.objectiveDataStore.saveToDisk(objectiveUUID.toString(), objective);
/*     */             this.objectiveDataStore.unloadObjective(objectiveUUID);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void onObjectiveLineAssetLoaded(@Nonnull LoadedAssetsEvent<String, ObjectiveLineAsset, DefaultAssetMap<String, ObjectiveLineAsset>> event) {
/* 660 */     if (this.objectiveDataStore == null)
/*     */       return; 
/* 662 */     for (Iterator<Map.Entry<String, ObjectiveLineAsset>> iterator = event.getLoadedAssets().entrySet().iterator(); iterator.hasNext(); ) { Map.Entry<String, ObjectiveLineAsset> objectiveLineEntry = iterator.next();
/* 663 */       String objectiveLineId = objectiveLineEntry.getKey();
/* 664 */       String[] objectiveIds = ((ObjectiveLineAsset)objectiveLineEntry.getValue()).getObjectiveIds();
/*     */       
/* 666 */       for (Objective activeObjective : this.objectiveDataStore.getObjectiveCollection()) {
/* 667 */         ObjectiveLineHistoryData objectiveLineHistoryData = activeObjective.getObjectiveLineHistoryData();
/*     */         
/* 669 */         if (objectiveLineHistoryData == null || 
/* 670 */           !objectiveLineId.equals(objectiveLineHistoryData.getId()) || 
/* 671 */           ArrayUtil.contains((Object[])objectiveIds, activeObjective.getObjectiveId()))
/*     */           continue; 
/* 673 */         World objectiveWorld = Universe.get().getWorld(activeObjective.worldUUID);
/* 674 */         if (objectiveWorld != null) {
/* 675 */           objectiveWorld.execute(() -> {
/*     */                 Store<EntityStore> store = objectiveWorld.getEntityStore().getStore();
/*     */                 cancelObjective(activeObjective.getObjectiveUUID(), store);
/*     */               });
/*     */         }
/*     */       }  }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void onObjectiveAssetLoaded(@Nonnull LoadedAssetsEvent<String, ObjectiveAsset, DefaultAssetMap<String, ObjectiveAsset>> event) {
/* 687 */     this.objectiveDataStore.getObjectiveCollection().forEach(objective -> objective.reloadObjectiveAsset(event.getLoadedAssets()));
/*     */   }
/*     */   
/*     */   private static void onObjectiveLocationMarkerChange(@Nonnull LoadedAssetsEvent<String, ObjectiveLocationMarkerAsset, DefaultAssetMap<String, ObjectiveLocationMarkerAsset>> event) {
/* 691 */     Map<String, ObjectiveLocationMarkerAsset> loadedAssets = event.getLoadedAssets();
/* 692 */     AndQuery<EntityStore> query = Query.and(new Query[] { (Query)ObjectiveLocationMarker.getComponentType(), (Query)ModelComponent.getComponentType(), (Query)TransformComponent.getComponentType() });
/* 693 */     Universe.get().getWorlds().forEach((s, world) -> world.execute(()));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onModelAssetChange(@Nonnull LoadedAssetsEvent<String, ModelAsset, DefaultAssetMap<String, ModelAsset>> event) {
/* 730 */     Map<String, ModelAsset> modelMap = event.getLoadedAssets();
/* 731 */     ModelAsset modelAsset = modelMap.get("Objective_Location_Marker");
/* 732 */     if (modelAsset == null)
/* 733 */       return;  this.objectiveLocationMarkerModel = Model.createUnitScaleModel(modelAsset);
/*     */   }
/*     */   private void onLivingEntityInventoryChange(@Nonnull LivingEntityInventoryChangeEvent event) {
/*     */     Player player;
/* 737 */     LivingEntity entity = (LivingEntity)event.getEntity();
/* 738 */     if (entity instanceof Player) { player = (Player)entity; }
/*     */     else
/*     */     { return; }
/* 741 */      Set<UUID> activeObjectiveUUIDs = player.getPlayerConfigData().getActiveObjectiveUUIDs();
/* 742 */     if (activeObjectiveUUIDs.isEmpty())
/*     */       return; 
/* 744 */     Set<UUID> inventoryItemObjectiveUUIDs = null;
/*     */ 
/*     */     
/* 747 */     CombinedItemContainer inventory = entity.getInventory().getCombinedHotbarFirst(); short i;
/* 748 */     for (i = 0; i < inventory.getCapacity(); i = (short)(i + 1)) {
/* 749 */       ItemStack itemStack = inventory.getItemStack(i);
/* 750 */       if (!ItemStack.isEmpty(itemStack)) {
/*     */ 
/*     */         
/* 753 */         UUID objectiveUUID = (UUID)itemStack.getFromMetadataOrNull(StartObjectiveInteraction.OBJECTIVE_UUID);
/* 754 */         if (objectiveUUID != null) {
/*     */           
/* 756 */           if (inventoryItemObjectiveUUIDs == null) inventoryItemObjectiveUUIDs = new HashSet<>(activeObjectiveUUIDs); 
/* 757 */           inventoryItemObjectiveUUIDs.add(objectiveUUID);
/*     */         } 
/*     */       } 
/*     */     } 
/* 761 */     for (UUID activeObjectiveUUID : activeObjectiveUUIDs) {
/* 762 */       if (inventoryItemObjectiveUUIDs != null && inventoryItemObjectiveUUIDs.contains(activeObjectiveUUID))
/*     */         continue; 
/* 764 */       Objective objective = this.objectiveDataStore.getObjective(activeObjectiveUUID);
/* 765 */       if (objective == null)
/* 766 */         continue;  ObjectiveAsset objectiveAsset = objective.getObjectiveAsset();
/* 767 */       if (objectiveAsset == null || !objectiveAsset.isRemoveOnItemDrop())
/*     */         continue; 
/* 769 */       Ref<EntityStore> reference = entity.getReference();
/* 770 */       Store<EntityStore> store = reference.getStore();
/* 771 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */       
/* 773 */       world.execute(() -> {
/*     */             UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(reference, UUIDComponent.getComponentType());
/*     */             assert uuidComponent != null;
/*     */             get().removePlayerFromExistingObjective(store, uuidComponent.getUuid(), activeObjectiveUUID);
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onWorldAdded(AddWorldEvent event) {
/* 783 */     event.getWorld().getWorldMapManager().addMarkerProvider("objectives", (WorldMapManager.MarkerProvider)ObjectiveMarkerProvider.INSTANCE);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getObjectiveDataDump() {
/* 788 */     StringBuilder sb = new StringBuilder("Objective Data\n");
/*     */     
/* 790 */     for (Objective objective : this.objectiveDataStore.getObjectiveCollection()) {
/* 791 */       sb.append("Objective ID: ").append(objective.getObjectiveId()).append("\n\t")
/* 792 */         .append("UUID: ").append(objective.getObjectiveUUID()).append("\n\t")
/* 793 */         .append("Players: ").append(Arrays.toString(objective.getPlayerUUIDs().toArray())).append("\n\t")
/* 794 */         .append("Active players: ").append(Arrays.toString(objective.getActivePlayerUUIDs().toArray())).append("\n\n");
/*     */     }
/*     */     
/* 797 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ObjectivePluginConfig
/*     */   {
/*     */     public static final BuilderCodec<ObjectivePluginConfig> CODEC;
/*     */ 
/*     */     
/*     */     static {
/* 807 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ObjectivePluginConfig.class, ObjectivePluginConfig::new).append(new KeyedCodec("DataStore", (Codec)DataStoreProvider.CODEC), (objectivePluginConfig, s) -> objectivePluginConfig.dataStoreProvider = s, objectivePluginConfig -> objectivePluginConfig.dataStoreProvider).add()).build();
/*     */     }
/* 809 */     private DataStoreProvider dataStoreProvider = (DataStoreProvider)new DiskDataStoreProvider("objectives");
/*     */     
/*     */     public DataStoreProvider getDataStoreProvider() {
/* 812 */       return this.dataStoreProvider;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\ObjectivePlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */