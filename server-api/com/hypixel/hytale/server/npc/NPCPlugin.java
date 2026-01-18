/*      */ package com.hypixel.hytale.server.npc;
/*      */ import com.hypixel.hytale.assetstore.AssetMap;
/*      */ import com.hypixel.hytale.assetstore.AssetPack;
/*      */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*      */ import com.hypixel.hytale.assetstore.AssetStore;
/*      */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*      */ import com.hypixel.hytale.assetstore.event.LoadedAssetsEvent;
/*      */ import com.hypixel.hytale.assetstore.event.RemovedAssetsEvent;
/*      */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*      */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*      */ import com.hypixel.hytale.builtin.path.path.TransientPathDefinition;
/*      */ import com.hypixel.hytale.builtin.path.waypoint.RelativeWaypointDefinition;
/*      */ import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
/*      */ import com.hypixel.hytale.codec.Codec;
/*      */ import com.hypixel.hytale.codec.KeyedCodec;
/*      */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*      */ import com.hypixel.hytale.codec.schema.config.Schema;
/*      */ import com.hypixel.hytale.common.benchmark.TimeDistributionRecorder;
/*      */ import com.hypixel.hytale.common.util.FormatUtil;
/*      */ import com.hypixel.hytale.component.AddReason;
/*      */ import com.hypixel.hytale.component.ArchetypeChunk;
/*      */ import com.hypixel.hytale.component.CommandBuffer;
/*      */ import com.hypixel.hytale.component.Component;
/*      */ import com.hypixel.hytale.component.ComponentRegistryProxy;
/*      */ import com.hypixel.hytale.component.ComponentType;
/*      */ import com.hypixel.hytale.component.Holder;
/*      */ import com.hypixel.hytale.component.Ref;
/*      */ import com.hypixel.hytale.component.ResourceType;
/*      */ import com.hypixel.hytale.component.Store;
/*      */ import com.hypixel.hytale.component.dependency.Dependency;
/*      */ import com.hypixel.hytale.component.query.Query;
/*      */ import com.hypixel.hytale.component.spatial.KDTree;
/*      */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*      */ import com.hypixel.hytale.component.spatial.SpatialStructure;
/*      */ import com.hypixel.hytale.component.system.ISystem;
/*      */ import com.hypixel.hytale.event.EventRegistry;
/*      */ import com.hypixel.hytale.function.consumer.TriConsumer;
/*      */ import com.hypixel.hytale.logger.HytaleLogger;
/*      */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*      */ import com.hypixel.hytale.math.vector.Vector3d;
/*      */ import com.hypixel.hytale.math.vector.Vector3f;
/*      */ import com.hypixel.hytale.server.core.Options;
/*      */ import com.hypixel.hytale.server.core.asset.AssetModule;
/*      */ import com.hypixel.hytale.server.core.asset.AssetPackRegisterEvent;
/*      */ import com.hypixel.hytale.server.core.asset.AssetPackUnregisterEvent;
/*      */ import com.hypixel.hytale.server.core.asset.GenerateSchemaEvent;
/*      */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*      */ import com.hypixel.hytale.server.core.asset.LoadAssetEvent;
/*      */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*      */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*      */ import com.hypixel.hytale.server.core.asset.type.responsecurve.config.ResponseCurve;
/*      */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*      */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*      */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*      */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*      */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*      */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*      */ import com.hypixel.hytale.server.core.universe.Universe;
/*      */ import com.hypixel.hytale.server.core.universe.world.World;
/*      */ import com.hypixel.hytale.server.core.universe.world.npc.INonPlayerCharacter;
/*      */ import com.hypixel.hytale.server.core.universe.world.path.WorldPathChangedEvent;
/*      */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*      */ import com.hypixel.hytale.server.flock.config.FlockAsset;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.BuilderFactory;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.BuilderInfo;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.BuilderManager;
/*      */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*      */ import com.hypixel.hytale.server.npc.blackboard.Blackboard;
/*      */ import com.hypixel.hytale.server.npc.blackboard.view.attitude.AttitudeMap;
/*      */ import com.hypixel.hytale.server.npc.blackboard.view.attitude.ItemAttitudeMap;
/*      */ import com.hypixel.hytale.server.npc.blackboard.view.combat.CombatViewSystems;
/*      */ import com.hypixel.hytale.server.npc.commands.NPCRunTestsCommand;
/*      */ import com.hypixel.hytale.server.npc.components.FailedSpawnComponent;
/*      */ import com.hypixel.hytale.server.npc.components.SortBufferProviderResource;
/*      */ import com.hypixel.hytale.server.npc.components.StepComponent;
/*      */ import com.hypixel.hytale.server.npc.components.Timers;
/*      */ import com.hypixel.hytale.server.npc.components.messaging.BeaconSupport;
/*      */ import com.hypixel.hytale.server.npc.components.messaging.NPCBlockEventSupport;
/*      */ import com.hypixel.hytale.server.npc.components.messaging.NPCEntityEventSupport;
/*      */ import com.hypixel.hytale.server.npc.components.messaging.PlayerBlockEventSupport;
/*      */ import com.hypixel.hytale.server.npc.components.messaging.PlayerEntityEventSupport;
/*      */ import com.hypixel.hytale.server.npc.config.AttitudeGroup;
/*      */ import com.hypixel.hytale.server.npc.config.ItemAttitudeGroup;
/*      */ import com.hypixel.hytale.server.npc.config.balancing.BalanceAsset;
/*      */ import com.hypixel.hytale.server.npc.corecomponents.ISensorEntityCollector;
/*      */ import com.hypixel.hytale.server.npc.corecomponents.ISensorEntityPrioritiser;
/*      */ import com.hypixel.hytale.server.npc.corecomponents.WeightedAction;
/*      */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderValueToParameterMapping;
/*      */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.Condition;
/*      */ import com.hypixel.hytale.server.npc.decisionmaker.stateevaluator.StateEvaluator;
/*      */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*      */ import com.hypixel.hytale.server.npc.instructions.Action;
/*      */ import com.hypixel.hytale.server.npc.instructions.ActionList;
/*      */ import com.hypixel.hytale.server.npc.instructions.BodyMotion;
/*      */ import com.hypixel.hytale.server.npc.instructions.HeadMotion;
/*      */ import com.hypixel.hytale.server.npc.instructions.Instruction;
/*      */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*      */ import com.hypixel.hytale.server.npc.interactions.ContextualUseNPCInteraction;
/*      */ import com.hypixel.hytale.server.npc.interactions.SpawnNPCInteraction;
/*      */ import com.hypixel.hytale.server.npc.interactions.UseNPCInteraction;
/*      */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*      */ import com.hypixel.hytale.server.npc.navigation.AStarNodePoolProviderSimple;
/*      */ import com.hypixel.hytale.server.npc.role.Role;
/*      */ import com.hypixel.hytale.server.npc.statetransition.StateTransitionController;
/*      */ import com.hypixel.hytale.server.npc.systems.BlackboardSystems;
/*      */ import com.hypixel.hytale.server.npc.systems.ComputeVelocitySystem;
/*      */ import com.hypixel.hytale.server.npc.systems.FailedSpawnSystem;
/*      */ import com.hypixel.hytale.server.npc.systems.MessageSupportSystem;
/*      */ import com.hypixel.hytale.server.npc.systems.MovementStatesSystem;
/*      */ import com.hypixel.hytale.server.npc.systems.NPCDamageSystems;
/*      */ import com.hypixel.hytale.server.npc.systems.NPCDeathSystems;
/*      */ import com.hypixel.hytale.server.npc.systems.NPCInteractionSystems;
/*      */ import com.hypixel.hytale.server.npc.systems.NPCPreTickSystem;
/*      */ import com.hypixel.hytale.server.npc.systems.NPCSystems;
/*      */ import com.hypixel.hytale.server.npc.systems.NewSpawnStartTickingSystem;
/*      */ import com.hypixel.hytale.server.npc.systems.PositionCacheSystems;
/*      */ import com.hypixel.hytale.server.npc.systems.RoleChangeSystem;
/*      */ import com.hypixel.hytale.server.npc.systems.RoleSystems;
/*      */ import com.hypixel.hytale.server.npc.systems.StateEvaluatorSystem;
/*      */ import com.hypixel.hytale.server.npc.systems.SteeringSystem;
/*      */ import com.hypixel.hytale.server.npc.util.SensorSupportBenchmark;
/*      */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*      */ import com.hypixel.hytale.server.npc.util.expression.StdScope;
/*      */ import com.hypixel.hytale.server.npc.valuestore.ValueStore;
/*      */ import it.unimi.dsi.fastutil.Pair;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import java.nio.file.Path;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.logging.Level;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ public class NPCPlugin extends JavaPlugin {
/*      */   @Nonnull
/*  152 */   public static String FACTORY_CLASS_ROLE = "Role";
/*      */   @Nonnull
/*  154 */   public static String FACTORY_CLASS_BODY_MOTION = "BodyMotion";
/*      */   @Nonnull
/*  156 */   public static String FACTORY_CLASS_HEAD_MOTION = "HeadMotion";
/*      */   @Nonnull
/*  158 */   public static String FACTORY_CLASS_ACTION = "Action";
/*      */   @Nonnull
/*  160 */   public static String FACTORY_CLASS_SENSOR = "Sensor";
/*      */   
/*      */   @Nonnull
/*  163 */   public static String FACTORY_CLASS_INSTRUCTION = "Instruction";
/*      */   @Nonnull
/*  165 */   public static String FACTORY_CLASS_TRANSIENT_PATH = "Path";
/*      */   @Nonnull
/*  167 */   public static String FACTORY_CLASS_ACTION_LIST = "ActionList";
/*      */   
/*      */   @Nonnull
/*  170 */   public static String ROLE_ASSETS_PATH = "Server/NPC/Roles";
/*      */   private static NPCPlugin instance;
/*      */   protected List<BuilderDescriptor> builderDescriptors;
/*      */   
/*      */   public static NPCPlugin get() {
/*  175 */     return instance;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  180 */   protected final BuilderManager builderManager = new BuilderManager();
/*      */   
/*      */   protected boolean validateBuilder;
/*  183 */   protected int maxBlackboardBlockCountPerType = 20;
/*      */   protected boolean logFailingTestErrors;
/*      */   protected String[] presetCoverageTestNPCs;
/*      */   @Nonnull
/*  187 */   protected AtomicInteger pathChangeRevision = new AtomicInteger(0);
/*      */   
/*      */   @Nonnull
/*  190 */   protected Lock benchmarkLock = new ReentrantLock();
/*      */   
/*      */   @Nullable
/*      */   protected Int2ObjectMap<TimeDistributionRecorder> roleTickDistribution;
/*      */   
/*      */   @Nullable
/*      */   protected Int2ObjectMap<SensorSupportBenchmark> roleSensorSupportDistribution;
/*      */   
/*      */   @Nullable
/*      */   protected TimeDistributionRecorder roleTickDistributionAll;
/*      */   
/*      */   @Nullable
/*      */   protected SensorSupportBenchmark roleSensorSupportDistributionAll;
/*      */   protected boolean autoReload;
/*      */   private AttitudeMap attitudeMap;
/*      */   private ItemAttitudeMap itemAttitudeMap;
/*  206 */   private static final Vector3f NULL_ROTATION = new Vector3f(0.0F, 0.0F, 0.0F);
/*      */   
/*      */   public static final short PRIORITY_LOAD_NPC = -8;
/*      */   
/*      */   public static final short PRIORITY_SPAWN_VALIDATION = -7;
/*  211 */   private final Config<NPCConfig> config = withConfig("NPCModule", NPCConfig.CODEC);
/*      */   
/*      */   private ResourceType<EntityStore, Blackboard> blackboardResourceType;
/*      */   
/*      */   private ResourceType<EntityStore, CombatViewSystems.CombatDataPool> combatDataPoolResourceType;
/*      */   private ResourceType<EntityStore, RoleChangeSystem.RoleChangeQueue> roleChangeQueueResourceType;
/*      */   private ResourceType<EntityStore, NewSpawnStartTickingSystem.QueueResource> newSpawnStartTickingQueueResourceType;
/*      */   private ResourceType<EntityStore, SortBufferProviderResource> sortBufferProviderResourceResourceType;
/*      */   private ResourceType<EntityStore, AStarNodePoolProviderSimple> aStarNodePoolProviderSimpleResourceType;
/*      */   private ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> npcSpatialResource;
/*      */   private ComponentType<EntityStore, CombatViewSystems.CombatData> combatDataComponentType;
/*      */   private ComponentType<EntityStore, NPCRunTestsCommand.NPCTestData> npcTestDataComponentType;
/*      */   private ComponentType<EntityStore, BeaconSupport> beaconSupportComponentType;
/*      */   private ComponentType<EntityStore, NPCBlockEventSupport> npcBlockEventSupportComponentType;
/*      */   private ComponentType<EntityStore, PlayerBlockEventSupport> playerBlockEventSupportComponentType;
/*      */   private ComponentType<EntityStore, NPCEntityEventSupport> npcEntityEventSupportComponentType;
/*      */   private ComponentType<EntityStore, PlayerEntityEventSupport> playerEntityEventSupportComponentType;
/*      */   private ComponentType<EntityStore, StepComponent> stepComponentType;
/*      */   private ComponentType<EntityStore, FailedSpawnComponent> failedSpawnComponentType;
/*      */   private ComponentType<EntityStore, Timers> timersComponentType;
/*      */   private ComponentType<EntityStore, StateEvaluator> stateEvaluatorComponentType;
/*      */   private ComponentType<EntityStore, ValueStore> valueStoreComponentType;
/*      */   
/*      */   public NPCPlugin(@Nonnull JavaPluginInit init) {
/*  235 */     super(init);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setup() {
/*  240 */     instance = this;
/*      */     
/*  242 */     ComponentRegistryProxy<EntityStore> entityStoreRegistry = getEntityStoreRegistry();
/*  243 */     EventRegistry eventRegistry = getEventRegistry();
/*      */     
/*  245 */     getCommandRegistry().registerCommand((AbstractCommand)new NPCCommand());
/*      */     
/*  247 */     eventRegistry.register(LoadedAssetsEvent.class, ModelAsset.class, this::onModelsChanged);
/*  248 */     eventRegistry.register(LoadedAssetsEvent.class, NPCGroup.class, this::onNPCGroupsLoaded);
/*  249 */     eventRegistry.register(RemovedAssetsEvent.class, NPCGroup.class, this::onNPCGroupsRemoved);
/*  250 */     eventRegistry.register(LoadedAssetsEvent.class, AttitudeGroup.class, this::onAttitudeGroupsLoaded);
/*  251 */     eventRegistry.register(RemovedAssetsEvent.class, AttitudeGroup.class, this::onAttitudeGroupsRemoved);
/*  252 */     eventRegistry.register(LoadedAssetsEvent.class, ItemAttitudeGroup.class, this::onItemAttitudeGroupsLoaded);
/*  253 */     eventRegistry.register(RemovedAssetsEvent.class, ItemAttitudeGroup.class, this::onItemAttitudeGroupsRemoved);
/*  254 */     eventRegistry.register(LoadedAssetsEvent.class, BalanceAsset.class, NPCPlugin::onBalanceAssetsChanged);
/*  255 */     eventRegistry.register(RemovedAssetsEvent.class, BalanceAsset.class, NPCPlugin::onBalanceAssetsRemoved);
/*  256 */     eventRegistry.register(WorldPathChangedEvent.class, this::onPathChange);
/*  257 */     eventRegistry.register(AllNPCsLoadedEvent.class, this::onNPCsLoaded);
/*      */     
/*  259 */     eventRegistry.register((short)-8, LoadAssetEvent.class, event -> {
/*      */           HytaleLogger.getLogger().at(Level.INFO).log("Loading NPC assets phase...");
/*      */           
/*      */           long start = System.nanoTime();
/*      */           
/*      */           this.builderManager.setAutoReload(this.autoReload);
/*      */           
/*      */           boolean validateAssets = Options.getOptionSet().has(Options.VALIDATE_ASSETS);
/*      */           List<AssetPack> assetPacks = AssetModule.get().getAssetPacks();
/*      */           for (int i = 0; i < assetPacks.size(); i++) {
/*      */             boolean includeTests = (i == 0);
/*      */             boolean loadSucceeded = this.builderManager.loadBuilders(assetPacks.get(i), includeTests);
/*      */             if (!loadSucceeded) {
/*      */               event.failed(validateAssets, "failed to validate npc's");
/*      */             }
/*      */           } 
/*      */           HytaleLogger.getLogger().at(Level.INFO).log("Loading NPC assets phase completed! Boot time %s, Took %s", FormatUtil.nanosToString(System.nanoTime() - event.getBootStart()), FormatUtil.nanosToString(System.nanoTime() - start));
/*      */         });
/*  277 */     eventRegistry.register(AssetPackRegisterEvent.class, event -> this.builderManager.loadBuilders(event.getAssetPack(), false));
/*      */ 
/*      */     
/*  280 */     eventRegistry.register(AssetPackUnregisterEvent.class, event -> this.builderManager.unloadBuilders(event.getAssetPack()));
/*      */ 
/*      */ 
/*      */     
/*  284 */     eventRegistry.register(GenerateSchemaEvent.class, this::onSchemaGenerate);
/*      */     
/*  286 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(AttitudeGroup.class, (AssetMap)new IndexedLookupTableAssetMap(x$0 -> new AttitudeGroup[x$0]))
/*  287 */         .setPath("NPC/Attitude/Roles"))
/*  288 */         .setCodec((AssetCodec)AttitudeGroup.CODEC))
/*  289 */         .setKeyFunction(AttitudeGroup::getId))
/*  290 */         .setReplaceOnRemove(AttitudeGroup::new))
/*  291 */         .loadsAfter(new Class[] { NPCGroup.class
/*  292 */           })).build());
/*      */     
/*  294 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(ItemAttitudeGroup.class, (AssetMap)new IndexedLookupTableAssetMap(x$0 -> new ItemAttitudeGroup[x$0]))
/*  295 */         .setPath("NPC/Attitude/Items"))
/*  296 */         .setCodec((AssetCodec)ItemAttitudeGroup.CODEC))
/*  297 */         .setKeyFunction(ItemAttitudeGroup::getId))
/*  298 */         .setReplaceOnRemove(ItemAttitudeGroup::new))
/*  299 */         .loadsAfter(new Class[] { Item.class
/*  300 */           })).build());
/*      */     
/*  302 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(BalanceAsset.class, (AssetMap)new DefaultAssetMap())
/*  303 */         .setPath("NPC/Balancing"))
/*  304 */         .setCodec((AssetCodec)BalanceAsset.CODEC))
/*  305 */         .setKeyFunction(BalanceAsset::getId))
/*  306 */         .loadsAfter(new Class[] { Condition.class
/*  307 */           })).build());
/*      */     
/*  309 */     AssetRegistry.register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(Condition.class, (AssetMap)new IndexedLookupTableAssetMap(x$0 -> new Condition[x$0]))
/*  310 */         .setPath("NPC/DecisionMaking/Conditions"))
/*  311 */         .setCodec((AssetCodec)Condition.CODEC))
/*  312 */         .setKeyFunction(Condition::getId))
/*  313 */         .setReplaceOnRemove(Condition::getAlwaysTrueFor))
/*  314 */         .loadsAfter(new Class[] { ResponseCurve.class, NPCGroup.class, EntityStatType.class
/*  315 */           })).build());
/*      */     
/*  317 */     getEntityRegistry().registerEntity("NPC", NPCEntity.class, NPCEntity::new, (DirectDecodeCodec)NPCEntity.CODEC);
/*      */     
/*  319 */     Interaction.CODEC.register("ContextualUseNPC", ContextualUseNPCInteraction.class, ContextualUseNPCInteraction.CODEC);
/*  320 */     Interaction.CODEC.register("UseNPC", UseNPCInteraction.class, UseNPCInteraction.CODEC);
/*  321 */     Interaction.CODEC.register("SpawnNPC", SpawnNPCInteraction.class, SpawnNPCInteraction.CODEC);
/*      */     
/*  323 */     Interaction.getAssetStore().loadAssets("Hytale:Hytale", List.of(new UseNPCInteraction("*UseNPC")));
/*  324 */     RootInteraction.getAssetStore().loadAssets("Hytale:Hytale", List.of(UseNPCInteraction.DEFAULT_ROOT));
/*      */     
/*  326 */     MigrationModule.get().register("spawnMarkers", com.hypixel.hytale.server.migrations.RenameSpawnMarkerMigration::new);
/*      */     
/*  328 */     setupNPCLoading();
/*      */     
/*  330 */     this.blackboardResourceType = entityStoreRegistry.registerResource(Blackboard.class, Blackboard::new);
/*  331 */     this.combatDataPoolResourceType = entityStoreRegistry.registerResource(CombatViewSystems.CombatDataPool.class, com.hypixel.hytale.server.npc.blackboard.view.combat.CombatViewSystems.CombatDataPool::new);
/*  332 */     this.roleChangeQueueResourceType = entityStoreRegistry.registerResource(RoleChangeSystem.RoleChangeQueue.class, com.hypixel.hytale.server.npc.systems.RoleChangeSystem.RoleChangeQueue::new);
/*  333 */     this.newSpawnStartTickingQueueResourceType = entityStoreRegistry.registerResource(NewSpawnStartTickingSystem.QueueResource.class, com.hypixel.hytale.server.npc.systems.NewSpawnStartTickingSystem.QueueResource::new);
/*  334 */     this.sortBufferProviderResourceResourceType = entityStoreRegistry.registerResource(SortBufferProviderResource.class, SortBufferProviderResource::new);
/*  335 */     this.aStarNodePoolProviderSimpleResourceType = entityStoreRegistry.registerResource(AStarNodePoolProviderSimple.class, AStarNodePoolProviderSimple::new);
/*  336 */     this.npcSpatialResource = entityStoreRegistry.registerSpatialResource(() -> new KDTree(Ref::isValid));
/*      */     
/*  338 */     this.combatDataComponentType = entityStoreRegistry.registerComponent(CombatViewSystems.CombatData.class, com.hypixel.hytale.server.npc.blackboard.view.combat.CombatViewSystems.CombatData::new);
/*  339 */     this.npcTestDataComponentType = entityStoreRegistry.registerComponent(NPCRunTestsCommand.NPCTestData.class, com.hypixel.hytale.server.npc.commands.NPCRunTestsCommand.NPCTestData::new);
/*  340 */     this.beaconSupportComponentType = entityStoreRegistry.registerComponent(BeaconSupport.class, BeaconSupport::new);
/*  341 */     this.npcBlockEventSupportComponentType = entityStoreRegistry.registerComponent(NPCBlockEventSupport.class, NPCBlockEventSupport::new);
/*  342 */     this.playerBlockEventSupportComponentType = entityStoreRegistry.registerComponent(PlayerBlockEventSupport.class, PlayerBlockEventSupport::new);
/*  343 */     this.npcEntityEventSupportComponentType = entityStoreRegistry.registerComponent(NPCEntityEventSupport.class, NPCEntityEventSupport::new);
/*  344 */     this.playerEntityEventSupportComponentType = entityStoreRegistry.registerComponent(PlayerEntityEventSupport.class, PlayerEntityEventSupport::new);
/*  345 */     this.stepComponentType = entityStoreRegistry.registerComponent(StepComponent.class, () -> {
/*      */           throw new UnsupportedOperationException("Not implemented");
/*      */         });
/*  348 */     this.failedSpawnComponentType = entityStoreRegistry.registerComponent(FailedSpawnComponent.class, FailedSpawnComponent::new);
/*  349 */     this.timersComponentType = entityStoreRegistry.registerComponent(Timers.class, () -> {
/*      */           throw new UnsupportedOperationException("Not implemented");
/*      */         });
/*      */     
/*  353 */     this.stateEvaluatorComponentType = entityStoreRegistry.registerComponent(StateEvaluator.class, () -> {
/*      */           throw new UnsupportedOperationException("Not implemented");
/*      */         });
/*  356 */     this.valueStoreComponentType = entityStoreRegistry.registerComponent(ValueStore.class, () -> {
/*      */           throw new UnsupportedOperationException("Not implemented");
/*      */         });
/*      */     
/*  360 */     ComponentType<EntityStore, NPCEntity> npcComponentType = NPCEntity.getComponentType();
/*      */ 
/*      */     
/*  363 */     entityStoreRegistry.registerSystem((ISystem)new BlackboardSystems.InitSystem(this.blackboardResourceType));
/*  364 */     entityStoreRegistry.registerSystem((ISystem)new BlackboardSystems.TickingSystem(this.blackboardResourceType));
/*      */     
/*  366 */     entityStoreRegistry.registerSystem((ISystem)new BlackboardSystems.DamageBlockEventSystem());
/*  367 */     entityStoreRegistry.registerSystem((ISystem)new BlackboardSystems.BreakBlockEventSystem());
/*      */     
/*  369 */     entityStoreRegistry.registerSystem((ISystem)new CombatViewSystems.Ensure(this.combatDataComponentType));
/*  370 */     entityStoreRegistry.registerSystem((ISystem)new CombatViewSystems.EntityRemoved(this.combatDataComponentType, this.combatDataPoolResourceType));
/*  371 */     entityStoreRegistry.registerSystem((ISystem)new CombatViewSystems.Ticking(this.combatDataComponentType, this.combatDataPoolResourceType));
/*  372 */     entityStoreRegistry.registerSystem((ISystem)new NPCSystems.ModelChangeSystem());
/*      */ 
/*      */     
/*  375 */     entityStoreRegistry.registerSystem((ISystem)new RoleBuilderSystem());
/*  376 */     entityStoreRegistry.registerSystem((ISystem)new BalancingInitialisationSystem());
/*  377 */     entityStoreRegistry.registerSystem((ISystem)new RoleSystems.RoleActivateSystem(npcComponentType));
/*  378 */     entityStoreRegistry.registerSystem((ISystem)new PositionCacheSystems.RoleActivateSystem(npcComponentType, this.stateEvaluatorComponentType));
/*      */     
/*  380 */     entityStoreRegistry.registerSystem((ISystem)new NPCInteractionSystems.AddSimulationManagerSystem(npcComponentType));
/*  381 */     entityStoreRegistry.registerSystem((ISystem)new NPCInteractionSystems.TickHeldInteractionsSystem(npcComponentType));
/*      */     
/*  383 */     entityStoreRegistry.registerSystem((ISystem)new FailedSpawnSystem());
/*  384 */     entityStoreRegistry.registerSystem((ISystem)new NPCSystems.AddedSystem(npcComponentType));
/*  385 */     entityStoreRegistry.registerSystem((ISystem)new NPCSystems.AddedFromExternalSystem(npcComponentType));
/*  386 */     entityStoreRegistry.registerSystem((ISystem)new NPCSystems.AddedFromWorldGenSystem());
/*  387 */     entityStoreRegistry.registerSystem((ISystem)new NPCSystems.AddSpawnEntityEffectSystem(npcComponentType));
/*      */ 
/*      */ 
/*      */     
/*  391 */     entityStoreRegistry.registerSystem((ISystem)new RoleSystems.BehaviourTickSystem(npcComponentType, this.stepComponentType));
/*  392 */     entityStoreRegistry.registerSystem((ISystem)new RoleSystems.PreBehaviourSupportTickSystem(npcComponentType));
/*  393 */     entityStoreRegistry.registerSystem((ISystem)new StateEvaluatorSystem(this.stateEvaluatorComponentType, npcComponentType));
/*  394 */     entityStoreRegistry.registerSystem((ISystem)new PositionCacheSystems.UpdateSystem(npcComponentType, this.npcSpatialResource));
/*  395 */     entityStoreRegistry.registerSystem((ISystem)new NPCPreTickSystem(npcComponentType));
/*      */ 
/*      */     
/*  398 */     Set<Dependency<EntityStore>> postBehaviourDependency = (Set)Set.of(new SystemDependency(Order.AFTER, RoleSystems.PostBehaviourSupportTickSystem.class));
/*  399 */     entityStoreRegistry.registerSystem((ISystem)new AvoidanceSystem(npcComponentType));
/*  400 */     entityStoreRegistry.registerSystem((ISystem)new SteeringSystem(npcComponentType));
/*  401 */     entityStoreRegistry.registerSystem((ISystem)new RoleSystems.PostBehaviourSupportTickSystem(npcComponentType));
/*  402 */     entityStoreRegistry.registerSystem((ISystem)new RoleSystems.RoleDebugSystem(npcComponentType, postBehaviourDependency));
/*  403 */     entityStoreRegistry.registerSystem((ISystem)new TimerSystem(this.timersComponentType, postBehaviourDependency));
/*  404 */     entityStoreRegistry.registerSystem((ISystem)new ComputeVelocitySystem(npcComponentType, EntityModule.get().getVelocityComponentType(), postBehaviourDependency));
/*  405 */     entityStoreRegistry.registerSystem((ISystem)new MovementStatesSystem(npcComponentType, EntityModule.get().getVelocityComponentType(), EntityModule.get().getMovementStatesComponentType()));
/*  406 */     entityStoreRegistry.registerSystem((ISystem)new MessageSupportSystem.BeaconSystem(this.beaconSupportComponentType, postBehaviourDependency));
/*  407 */     entityStoreRegistry.registerSystem((ISystem)new MessageSupportSystem.NPCBlockEventSystem(this.npcBlockEventSupportComponentType, postBehaviourDependency));
/*  408 */     entityStoreRegistry.registerSystem((ISystem)new MessageSupportSystem.PlayerBlockEventSystem(this.playerBlockEventSupportComponentType, postBehaviourDependency));
/*  409 */     entityStoreRegistry.registerSystem((ISystem)new MessageSupportSystem.NPCEntityEventSystem(this.npcEntityEventSupportComponentType, postBehaviourDependency));
/*  410 */     entityStoreRegistry.registerSystem((ISystem)new MessageSupportSystem.PlayerEntityEventSystem(this.playerEntityEventSupportComponentType, postBehaviourDependency));
/*      */ 
/*      */     
/*  413 */     entityStoreRegistry.registerSystem((ISystem)new StepCleanupSystem(this.stepComponentType));
/*  414 */     entityStoreRegistry.registerSystem((ISystem)new NewSpawnStartTickingSystem(this.newSpawnStartTickingQueueResourceType));
/*  415 */     entityStoreRegistry.registerSystem((ISystem)new RoleChangeSystem(this.roleChangeQueueResourceType, this.beaconSupportComponentType, this.playerBlockEventSupportComponentType, this.npcBlockEventSupportComponentType, this.playerEntityEventSupportComponentType, this.npcEntityEventSupportComponentType, this.timersComponentType, this.stateEvaluatorComponentType, this.valueStoreComponentType));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  420 */     entityStoreRegistry.registerSystem((ISystem)new NPCSpatialSystem(this.npcSpatialResource));
/*      */ 
/*      */     
/*  423 */     entityStoreRegistry.registerSystem((ISystem)new NPCDeathSystems.NPCKillsEntitySystem());
/*  424 */     entityStoreRegistry.registerSystem((ISystem)new NPCDeathSystems.EntityViewSystem());
/*      */     
/*  426 */     entityStoreRegistry.registerSystem((ISystem)new NPCDamageSystems.FilterDamageSystem());
/*  427 */     entityStoreRegistry.registerSystem((ISystem)new NPCDamageSystems.DamageReceivedSystem());
/*  428 */     entityStoreRegistry.registerSystem((ISystem)new NPCDamageSystems.DamageDealtSystem());
/*  429 */     entityStoreRegistry.registerSystem((ISystem)new NPCDamageSystems.DamageReceivedEventViewSystem());
/*  430 */     entityStoreRegistry.registerSystem((ISystem)new NPCDamageSystems.DropDeathItems());
/*      */     
/*  432 */     entityStoreRegistry.registerSystem((ISystem)new NPCSystems.OnTeleportSystem());
/*  433 */     entityStoreRegistry.registerSystem((ISystem)new NPCSystems.OnDeathSystem());
/*      */     
/*  435 */     entityStoreRegistry.registerSystem((ISystem)new NPCSystems.LegacyWorldGenId());
/*      */     
/*  437 */     entityStoreRegistry.registerSystem((ISystem)new NPCSystems.KillFeedKillerEventSystem());
/*  438 */     entityStoreRegistry.registerSystem((ISystem)new NPCSystems.KillFeedDecedentEventSystem());
/*  439 */     entityStoreRegistry.registerSystem((ISystem)new NPCSystems.PrefabPlaceEntityEventSystem());
/*      */     
/*  441 */     entityStoreRegistry.registerSystem((ISystem)new NPCVelocityInstructionSystem());
/*      */     
/*  443 */     getEntityStoreRegistry().registerSystem((ISystem)new NPCEntityRegenerateStatsSystem());
/*      */   }
/*      */   
/*      */   public void onSchemaGenerate(@Nonnull GenerateSchemaEvent event) {
/*  447 */     Schema schema = this.builderManager.generateSchema(event.getContext());
/*  448 */     event.addSchema("NPCRole.json", schema);
/*  449 */     event.addSchemaLink("NPCRole", List.of("NPC/Roles/*.json", "NPC/Roles/**/*.json"), null);
/*      */     
/*  451 */     Schema.HytaleMetadata hytale = schema.getHytale();
/*  452 */     hytale.setPath("NPC/Roles");
/*  453 */     hytale.setExtension(".json");
/*  454 */     schema.setId("NPCRole.json");
/*  455 */     schema.setTitle("NPCRole");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void start() {
/*  460 */     NPCConfig config = (NPCConfig)this.config.get();
/*  461 */     if (config.isGenerateDescriptors()) {
/*  462 */       generateDescriptors();
/*  463 */       if (config.isGenerateDescriptorsFile()) {
/*  464 */         saveDescriptors();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public ResourceType<EntityStore, Blackboard> getBlackboardResourceType() {
/*  470 */     return this.blackboardResourceType;
/*      */   }
/*      */   
/*      */   public ResourceType<EntityStore, CombatViewSystems.CombatDataPool> getCombatDataPoolResourceType() {
/*  474 */     return this.combatDataPoolResourceType;
/*      */   }
/*      */   
/*      */   public ResourceType<EntityStore, RoleChangeSystem.RoleChangeQueue> getRoleChangeQueueResourceType() {
/*  478 */     return this.roleChangeQueueResourceType;
/*      */   }
/*      */   
/*      */   public ResourceType<EntityStore, NewSpawnStartTickingSystem.QueueResource> getNewSpawnStartTickingQueueResourceType() {
/*  482 */     return this.newSpawnStartTickingQueueResourceType;
/*      */   }
/*      */   
/*      */   public ResourceType<EntityStore, SortBufferProviderResource> getSortBufferProviderResourceResourceType() {
/*  486 */     return this.sortBufferProviderResourceResourceType;
/*      */   }
/*      */   
/*      */   public ResourceType<EntityStore, AStarNodePoolProviderSimple> getAStarNodePoolProviderSimpleResourceType() {
/*  490 */     return this.aStarNodePoolProviderSimpleResourceType;
/*      */   }
/*      */   
/*      */   public ResourceType<EntityStore, SpatialResource<Ref<EntityStore>, EntityStore>> getNpcSpatialResource() {
/*  494 */     return this.npcSpatialResource;
/*      */   }
/*      */   
/*      */   public ComponentType<EntityStore, CombatViewSystems.CombatData> getCombatDataComponentType() {
/*  498 */     return this.combatDataComponentType;
/*      */   }
/*      */   
/*      */   public ComponentType<EntityStore, NPCRunTestsCommand.NPCTestData> getNpcTestDataComponentType() {
/*  502 */     return this.npcTestDataComponentType;
/*      */   }
/*      */   
/*      */   public ComponentType<EntityStore, BeaconSupport> getBeaconSupportComponentType() {
/*  506 */     return this.beaconSupportComponentType;
/*      */   }
/*      */   
/*      */   public ComponentType<EntityStore, NPCBlockEventSupport> getNpcBlockEventSupportComponentType() {
/*  510 */     return this.npcBlockEventSupportComponentType;
/*      */   }
/*      */   
/*      */   public ComponentType<EntityStore, PlayerBlockEventSupport> getPlayerBlockEventSupportComponentType() {
/*  514 */     return this.playerBlockEventSupportComponentType;
/*      */   }
/*      */   
/*      */   public ComponentType<EntityStore, NPCEntityEventSupport> getNpcEntityEventSupportComponentType() {
/*  518 */     return this.npcEntityEventSupportComponentType;
/*      */   }
/*      */   
/*      */   public ComponentType<EntityStore, PlayerEntityEventSupport> getPlayerEntityEventSupportComponentType() {
/*  522 */     return this.playerEntityEventSupportComponentType;
/*      */   }
/*      */   
/*      */   public ComponentType<EntityStore, StepComponent> getStepComponentType() {
/*  526 */     return this.stepComponentType;
/*      */   }
/*      */   
/*      */   public ComponentType<EntityStore, FailedSpawnComponent> getFailedSpawnComponentType() {
/*  530 */     return this.failedSpawnComponentType;
/*      */   }
/*      */   
/*      */   public ComponentType<EntityStore, Timers> getTimersComponentType() {
/*  534 */     return this.timersComponentType;
/*      */   }
/*      */   
/*      */   public ComponentType<EntityStore, StateEvaluator> getStateEvaluatorComponentType() {
/*  538 */     return this.stateEvaluatorComponentType;
/*      */   }
/*      */   
/*      */   public ComponentType<EntityStore, ValueStore> getValueStoreComponentType() {
/*  542 */     return this.valueStoreComponentType;
/*      */   }
/*      */   
/*      */   public void setupNPCLoading() {
/*  546 */     this.builderManager.addCategory(FACTORY_CLASS_ROLE, Role.class);
/*  547 */     this.builderManager.addCategory(FACTORY_CLASS_BODY_MOTION, BodyMotion.class);
/*  548 */     this.builderManager.addCategory(FACTORY_CLASS_HEAD_MOTION, HeadMotion.class);
/*  549 */     this.builderManager.addCategory(FACTORY_CLASS_ACTION, Action.class);
/*  550 */     this.builderManager.addCategory(FACTORY_CLASS_SENSOR, Sensor.class);
/*  551 */     this.builderManager.addCategory(FACTORY_CLASS_INSTRUCTION, Instruction.class);
/*  552 */     this.builderManager.addCategory(FACTORY_CLASS_TRANSIENT_PATH, TransientPathDefinition.class);
/*  553 */     this.builderManager.addCategory(FACTORY_CLASS_ACTION_LIST, ActionList.class);
/*      */ 
/*      */     
/*  556 */     registerCoreFactories();
/*      */     
/*  558 */     registerCoreComponentType("Nothing", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderBodyMotionNothing::new)
/*  559 */       .registerCoreComponentType("Wander", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionWander::new)
/*  560 */       .registerCoreComponentType("WanderInCircle", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionWanderInCircle::new)
/*  561 */       .registerCoreComponentType("WanderInRect", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionWanderInRect::new)
/*  562 */       .registerCoreComponentType("Timer", com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderBodyMotionTimer::new)
/*  563 */       .registerCoreComponentType("Sequence", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderBodyMotionSequence::new)
/*  564 */       .registerCoreComponentType("Flee", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionMoveAway::new)
/*  565 */       .registerCoreComponentType("Seek", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionFind::new)
/*  566 */       .registerCoreComponentType("Leave", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionLeave::new)
/*  567 */       .registerCoreComponentType("Path", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderBodyMotionPath::new)
/*  568 */       .registerCoreComponentType("TakeOff", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionTakeOff::new)
/*  569 */       .registerCoreComponentType("TestProbe", com.hypixel.hytale.server.npc.corecomponents.debug.builders.BuilderBodyMotionTestProbe::new)
/*  570 */       .registerCoreComponentType("Teleport", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionTeleport::new)
/*  571 */       .registerCoreComponentType("Land", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionLand::new)
/*  572 */       .registerCoreComponentType("MatchLook", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionMatchLook::new)
/*  573 */       .registerCoreComponentType("MaintainDistance", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderBodyMotionMaintainDistance::new)
/*  574 */       .registerCoreComponentType("AimCharge", com.hypixel.hytale.server.npc.corecomponents.combat.builders.BuilderBodyMotionAimCharge::new);
/*      */     
/*  576 */     registerCoreComponentType("Aim", com.hypixel.hytale.server.npc.corecomponents.combat.builders.BuilderHeadMotionAim::new)
/*  577 */       .registerCoreComponentType("Watch", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderHeadMotionWatch::new)
/*  578 */       .registerCoreComponentType("Observe", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderHeadMotionObserve::new)
/*  579 */       .registerCoreComponentType("Sequence", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderHeadMotionSequence::new)
/*  580 */       .registerCoreComponentType("Timer", com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderHeadMotionTimer::new)
/*  581 */       .registerCoreComponentType("Nothing", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderHeadMotionNothing::new);
/*      */     
/*  583 */     registerCoreComponentType("Appearance", com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderActionAppearance::new)
/*  584 */       .registerCoreComponentType("Timeout", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderActionTimeout::new)
/*  585 */       .registerCoreComponentType("Spawn", com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderActionSpawn::new)
/*  586 */       .registerCoreComponentType("Nothing", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderActionNothing::new)
/*  587 */       .registerCoreComponentType("Attack", com.hypixel.hytale.server.npc.corecomponents.combat.builders.BuilderActionAttack::new)
/*  588 */       .registerCoreComponentType("State", com.hypixel.hytale.server.npc.corecomponents.statemachine.builders.BuilderActionState::new)
/*  589 */       .registerCoreComponentType("ReleaseTarget", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderActionReleaseTarget::new)
/*  590 */       .registerCoreComponentType("SetMarkedTarget", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderActionSetMarkedTarget::new)
/*  591 */       .registerCoreComponentType("Inventory", com.hypixel.hytale.server.npc.corecomponents.items.builders.BuilderActionInventory::new)
/*  592 */       .registerCoreComponentType("DisplayName", com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderActionDisplayName::new)
/*  593 */       .registerCoreComponentType("Sequence", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderActionSequence::new)
/*  594 */       .registerCoreComponentType("Random", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderActionRandom::new)
/*  595 */       .registerCoreComponentType("Beacon", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderActionBeacon::new)
/*  596 */       .registerCoreComponentType("SetLeashPosition", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionSetLeashPosition::new)
/*  597 */       .registerCoreComponentType("PlaySound", com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderActionPlaySound::new)
/*  598 */       .registerCoreComponentType("Despawn", com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderActionDespawn::new)
/*  599 */       .registerCoreComponentType("PlayAnimation", com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderActionPlayAnimation::new)
/*  600 */       .registerCoreComponentType("DelayDespawn", com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderActionDelayDespawn::new)
/*  601 */       .registerCoreComponentType("SpawnParticles", com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderActionSpawnParticles::new)
/*  602 */       .registerCoreComponentType("Crouch", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderActionCrouch::new)
/*  603 */       .registerCoreComponentType("TimerStart", com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionTimerStart::new)
/*  604 */       .registerCoreComponentType("TimerContinue", com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionTimerContinue::new)
/*  605 */       .registerCoreComponentType("TimerPause", com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionTimerPause::new)
/*  606 */       .registerCoreComponentType("TimerModify", com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionTimerModify::new)
/*  607 */       .registerCoreComponentType("TimerStop", com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionTimerStop::new)
/*  608 */       .registerCoreComponentType("TimerRestart", com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionTimerRestart::new)
/*  609 */       .registerCoreComponentType("Test", com.hypixel.hytale.server.npc.corecomponents.debug.builders.BuilderActionTest::new)
/*  610 */       .registerCoreComponentType("Log", com.hypixel.hytale.server.npc.corecomponents.debug.builders.BuilderActionLog::new)
/*  611 */       .registerCoreComponentType("Role", com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderActionRole::new)
/*  612 */       .registerCoreComponentType("SetFlag", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderActionSetFlag::new)
/*  613 */       .registerCoreComponentType("DropItem", com.hypixel.hytale.server.npc.corecomponents.items.builders.BuilderActionDropItem::new)
/*  614 */       .registerCoreComponentType("PickUpItem", com.hypixel.hytale.server.npc.corecomponents.items.builders.BuilderActionPickUpItem::new)
/*  615 */       .registerCoreComponentType("ResetInstructions", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderActionResetInstructions::new)
/*  616 */       .registerCoreComponentType("ParentState", com.hypixel.hytale.server.npc.corecomponents.statemachine.builders.BuilderActionParentState::new)
/*  617 */       .registerCoreComponentType("Notify", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderActionNotify::new)
/*  618 */       .registerCoreComponentType("TriggerSpawners", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionTriggerSpawners::new)
/*  619 */       .registerCoreComponentType("ResetBlockSensors", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionResetBlockSensors::new)
/*  620 */       .registerCoreComponentType("MakePath", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionMakePath::new)
/*  621 */       .registerCoreComponentType("OverrideAttitude", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderActionOverrideAttitude::new)
/*  622 */       .registerCoreComponentType("SetInteractable", com.hypixel.hytale.server.npc.corecomponents.interaction.builders.BuilderActionSetInteractable::new)
/*  623 */       .registerCoreComponentType("LockOnInteractionTarget", com.hypixel.hytale.server.npc.corecomponents.interaction.builders.BuilderActionLockOnInteractionTarget::new)
/*  624 */       .registerCoreComponentType("StorePosition", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionStorePosition::new)
/*  625 */       .registerCoreComponentType("SetBlockToPlace", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionSetBlockToPlace::new)
/*  626 */       .registerCoreComponentType("PlaceBlock", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionPlaceBlock::new)
/*  627 */       .registerCoreComponentType("RecomputePath", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderActionRecomputePath::new)
/*  628 */       .registerCoreComponentType("IgnoreForAvoidance", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderActionIgnoreForAvoidance::new)
/*  629 */       .registerCoreComponentType("ModelAttachment", com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderActionModelAttachment::new)
/*  630 */       .registerCoreComponentType("SetAlarm", com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionSetAlarm::new)
/*  631 */       .registerCoreComponentType("ToggleStateEvaluator", com.hypixel.hytale.server.npc.corecomponents.statemachine.builders.BuilderActionToggleStateEvaluator::new)
/*  632 */       .registerCoreComponentType("OverrideAltitude", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderActionOverrideAltitude::new)
/*  633 */       .registerCoreComponentType("ResetSearchRays", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionResetSearchRays::new)
/*  634 */       .registerCoreComponentType("Die", com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderActionDie::new)
/*  635 */       .registerCoreComponentType("Remove", com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderActionRemove::new)
/*  636 */       .registerCoreComponentType("ApplyEntityEffect", com.hypixel.hytale.server.npc.corecomponents.combat.builders.BuilderActionApplyEntityEffect::new)
/*  637 */       .registerCoreComponentType("ResetPath", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionResetPath::new)
/*  638 */       .registerCoreComponentType("SetStat", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderActionSetStat::new);
/*      */     
/*  640 */     registerCoreComponentType("Any", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorAny::new)
/*  641 */       .registerCoreComponentType("And", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorAnd::new)
/*  642 */       .registerCoreComponentType("Or", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorOr::new)
/*  643 */       .registerCoreComponentType("Not", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorNot::new)
/*  644 */       .registerCoreComponentType("Player", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorPlayer::new)
/*  645 */       .registerCoreComponentType("Mob", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorEntity::new)
/*  646 */       .registerCoreComponentType("State", com.hypixel.hytale.server.npc.corecomponents.statemachine.builders.BuilderSensorState::new)
/*  647 */       .registerCoreComponentType("InAir", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderSensorInAir::new)
/*  648 */       .registerCoreComponentType("OnGround", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderSensorOnGround::new)
/*  649 */       .registerCoreComponentType("Eval", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorEval::new)
/*  650 */       .registerCoreComponentType("Damage", com.hypixel.hytale.server.npc.corecomponents.combat.builders.BuilderSensorDamage::new)
/*  651 */       .registerCoreComponentType("IsBackingAway", com.hypixel.hytale.server.npc.corecomponents.combat.builders.BuilderSensorIsBackingAway::new)
/*  652 */       .registerCoreComponentType("Kill", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorKill::new)
/*  653 */       .registerCoreComponentType("Beacon", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorBeacon::new)
/*  654 */       .registerCoreComponentType("MotionController", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderSensorMotionController::new)
/*  655 */       .registerCoreComponentType("Leash", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorLeash::new)
/*  656 */       .registerCoreComponentType("Time", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorTime::new)
/*  657 */       .registerCoreComponentType("Count", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorCount::new)
/*  658 */       .registerCoreComponentType("Target", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorTarget::new)
/*  659 */       .registerCoreComponentType("Timer", com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderSensorTimer::new)
/*  660 */       .registerCoreComponentType("Switch", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorSwitch::new)
/*  661 */       .registerCoreComponentType("Light", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorLight::new)
/*  662 */       .registerCoreComponentType("Age", com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderSensorAge::new)
/*  663 */       .registerCoreComponentType("Flag", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorFlag::new)
/*  664 */       .registerCoreComponentType("DroppedItem", com.hypixel.hytale.server.npc.corecomponents.items.builders.BuilderSensorDroppedItem::new)
/*  665 */       .registerCoreComponentType("Path", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorPath::new)
/*  666 */       .registerCoreComponentType("Weather", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorWeather::new)
/*  667 */       .registerCoreComponentType("Block", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorBlock::new)
/*  668 */       .registerCoreComponentType("BlockChange", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorBlockChange::new)
/*  669 */       .registerCoreComponentType("EntityEvent", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorEntityEvent::new)
/*  670 */       .registerCoreComponentType("Random", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorRandom::new)
/*  671 */       .registerCoreComponentType("CanInteract", com.hypixel.hytale.server.npc.corecomponents.interaction.builders.BuilderSensorCanInteract::new)
/*  672 */       .registerCoreComponentType("HasInteracted", com.hypixel.hytale.server.npc.corecomponents.interaction.builders.BuilderSensorHasInteracted::new)
/*  673 */       .registerCoreComponentType("ReadPosition", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorReadPosition::new)
/*  674 */       .registerCoreComponentType("Animation", com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderSensorAnimation::new)
/*  675 */       .registerCoreComponentType("CanPlaceBlock", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorCanPlace::new)
/*  676 */       .registerCoreComponentType("Nav", com.hypixel.hytale.server.npc.corecomponents.movement.builders.BuilderSensorNav::new)
/*  677 */       .registerCoreComponentType("InWater", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorInWater::new)
/*  678 */       .registerCoreComponentType("IsBusy", com.hypixel.hytale.server.npc.corecomponents.statemachine.builders.BuilderSensorIsBusy::new)
/*  679 */       .registerCoreComponentType("InteractionContext", com.hypixel.hytale.server.npc.corecomponents.interaction.builders.BuilderSensorInteractionContext::new)
/*  680 */       .registerCoreComponentType("Alarm", com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderSensorAlarm::new)
/*  681 */       .registerCoreComponentType("AdjustPosition", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorAdjustPosition::new)
/*  682 */       .registerCoreComponentType("SearchRay", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorSearchRay::new)
/*  683 */       .registerCoreComponentType("BlockType", com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorBlockType::new)
/*  684 */       .registerCoreComponentType("Self", com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorSelf::new)
/*  685 */       .registerCoreComponentType("ValueProviderWrapper", com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorValueProviderWrapper::new);
/*      */     
/*  687 */     registerCoreComponentType("Attitude", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterAttitude::new)
/*  688 */       .registerCoreComponentType("LineOfSight", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterLineOfSight::new)
/*  689 */       .registerCoreComponentType("HeightDifference", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterHeightDifference::new)
/*  690 */       .registerCoreComponentType("ViewSector", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterViewSector::new)
/*  691 */       .registerCoreComponentType("Combat", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterCombat::new)
/*  692 */       .registerCoreComponentType("ItemInHand", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterItemInHand::new)
/*  693 */       .registerCoreComponentType("NPCGroup", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterNPCGroup::new)
/*  694 */       .registerCoreComponentType("MovementState", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterMovementState::new)
/*  695 */       .registerCoreComponentType("SpotsMe", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterSpotsMe::new)
/*  696 */       .registerCoreComponentType("StandingOnBlock", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterStandingOnBlock::new)
/*  697 */       .registerCoreComponentType("Stat", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterStat::new)
/*  698 */       .registerCoreComponentType("Inventory", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterInventory::new)
/*  699 */       .registerCoreComponentType("Not", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterNot::new)
/*  700 */       .registerCoreComponentType("And", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterAnd::new)
/*  701 */       .registerCoreComponentType("Or", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterOr::new)
/*  702 */       .registerCoreComponentType("Altitude", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterAltitude::new)
/*  703 */       .registerCoreComponentType("InsideBlock", com.hypixel.hytale.server.npc.corecomponents.entity.filters.builders.BuilderEntityFilterInsideBlock::new);
/*      */     
/*  705 */     registerCoreComponentType("Attitude", com.hypixel.hytale.server.npc.corecomponents.entity.prioritisers.builders.BuilderSensorEntityPrioritiserAttitude::new);
/*      */     
/*  707 */     NPCConfig config = (NPCConfig)this.config.get();
/*  708 */     this.autoReload = config.isAutoReload();
/*  709 */     this.validateBuilder = config.isValidateBuilder();
/*  710 */     this.maxBlackboardBlockCountPerType = config.getMaxBlackboardBlockType();
/*  711 */     this.logFailingTestErrors = config.isLogFailingTestErrors();
/*  712 */     this.presetCoverageTestNPCs = config.getPresetCoverageTestNPCs();
/*      */   }
/*      */   
/*      */   public String[] getPresetCoverageTestNPCs() {
/*  716 */     return this.presetCoverageTestNPCs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Pair<Ref<EntityStore>, INonPlayerCharacter> spawnNPC(@Nonnull Store<EntityStore> store, @Nonnull String npcType, @Nullable String groupType, @Nonnull Vector3d position, @Nonnull Vector3f rotation) {
/*  735 */     int roleIndex = getIndex(npcType);
/*  736 */     if (roleIndex < 0) return null; 
/*  737 */     Pair<Ref<EntityStore>, NPCEntity> npcPair = spawnEntity(store, roleIndex, position, rotation, (Model)null, (TriConsumer<NPCEntity, Ref<EntityStore>, Store<EntityStore>>)null);
/*  738 */     FlockAsset flockDefinition = (groupType != null) ? (FlockAsset)FlockAsset.getAssetMap().getAsset(groupType) : null;
/*  739 */     if (npcPair != null) {
/*  740 */       Ref<EntityStore> npcRef = (Ref<EntityStore>)npcPair.first();
/*  741 */       NPCEntity npcComponent = (NPCEntity)npcPair.second();
/*  742 */       FlockPlugin.trySpawnFlock(npcRef, npcComponent, store, roleIndex, position, rotation, flockDefinition, null);
/*  743 */       return Pair.of(npcPair.first(), npcPair.second());
/*      */     } 
/*      */     
/*  746 */     return null;
/*      */   }
/*      */   
/*      */   public static void reloadNPCsWithRole(int roleIndex) {
/*  750 */     Universe.get().getWorlds().forEach((s, world) -> world.execute(()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onNPCGroupsLoaded(LoadedAssetsEvent<String, NPCGroup, AssetMap<String, NPCGroup>> event) {
/*  766 */     putNPCGroups();
/*      */   }
/*      */   
/*      */   protected void onNPCGroupsRemoved(RemovedAssetsEvent<String, NPCGroup, AssetMap<String, NPCGroup>> event) {
/*  770 */     putNPCGroups();
/*      */   }
/*      */   
/*      */   protected void onAttitudeGroupsLoaded(@Nonnull LoadedAssetsEvent<String, AttitudeGroup, AssetMap<String, AttitudeGroup>> event) {
/*  774 */     if (this.attitudeMap == null) {
/*  775 */       putAttitudeGroups();
/*      */       
/*      */       return;
/*      */     } 
/*  779 */     Map<String, AttitudeGroup> loadedAssets = event.getLoadedAssets();
/*  780 */     IndexedLookupTableAssetMap<String, AttitudeGroup> assets = AttitudeGroup.getAssetMap();
/*  781 */     int attitudeGroupCount = this.attitudeMap.getAttitudeGroupCount();
/*  782 */     for (String id : loadedAssets.keySet()) {
/*  783 */       int index = assets.getIndex(id);
/*  784 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + id); 
/*  785 */       if (index >= attitudeGroupCount) {
/*      */         
/*  787 */         putAttitudeGroups();
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  792 */     loadedAssets.forEach((id, group) -> {
/*      */           int index = assets.getIndex(id);
/*      */           if (index == Integer.MIN_VALUE)
/*      */             throw new IllegalArgumentException("Unknown key! " + id); 
/*      */           this.attitudeMap.updateAttitudeGroup(index, group);
/*      */         });
/*      */   }
/*      */   protected void onAttitudeGroupsRemoved(RemovedAssetsEvent<String, AttitudeGroup, AssetMap<String, AttitudeGroup>> event) {
/*  800 */     putAttitudeGroups();
/*      */   }
/*      */   
/*      */   protected void onItemAttitudeGroupsLoaded(@Nonnull LoadedAssetsEvent<String, ItemAttitudeGroup, AssetMap<String, ItemAttitudeGroup>> event) {
/*  804 */     if (this.itemAttitudeMap == null) {
/*  805 */       putItemAttitudeGroups();
/*      */       
/*      */       return;
/*      */     } 
/*  809 */     Map<String, ItemAttitudeGroup> loadedAssets = event.getLoadedAssets();
/*  810 */     IndexedLookupTableAssetMap<String, ItemAttitudeGroup> assets = ItemAttitudeGroup.getAssetMap();
/*  811 */     int attitudeGroupCount = this.itemAttitudeMap.getAttitudeGroupCount();
/*  812 */     for (String id : loadedAssets.keySet()) {
/*  813 */       int index = assets.getIndex(id);
/*  814 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + id); 
/*  815 */       if (index >= attitudeGroupCount) {
/*      */         
/*  817 */         putItemAttitudeGroups();
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  822 */     loadedAssets.forEach((id, group) -> {
/*      */           int index = assets.getIndex(id);
/*      */           if (index == Integer.MIN_VALUE)
/*      */             throw new IllegalArgumentException("Unknown key! " + id); 
/*      */           this.itemAttitudeMap.updateAttitudeGroup(index, group);
/*      */         });
/*      */   }
/*      */   protected void onItemAttitudeGroupsRemoved(RemovedAssetsEvent<String, ItemAttitudeGroup, AssetMap<String, ItemAttitudeGroup>> event) {
/*  830 */     putItemAttitudeGroups();
/*      */   }
/*      */   
/*      */   private void putItemAttitudeGroups() {
/*  834 */     ItemAttitudeMap.Builder builder = new ItemAttitudeMap.Builder();
/*  835 */     builder.addAttitudeGroups(ItemAttitudeGroup.getAssetMap().getAssetMap());
/*  836 */     this.itemAttitudeMap = builder.build();
/*      */   }
/*      */   
/*      */   protected void onPathChange(WorldPathChangedEvent event) {
/*  840 */     this.pathChangeRevision.getAndIncrement();
/*      */   }
/*      */   
/*      */   public int getPathChangeRevision() {
/*  844 */     return this.pathChangeRevision.get();
/*      */   }
/*      */   
/*      */   protected void onNPCsLoaded(AllNPCsLoadedEvent event) {
/*  848 */     putNPCGroups();
/*      */   }
/*      */   
/*      */   private void putNPCGroups() {
/*  852 */     IndexedLookupTableAssetMap<String, NPCGroup> indexedAssetMap = NPCGroup.getAssetMap();
/*  853 */     Object2IntOpenHashMap<String> tagSetIndexMap = new Object2IntOpenHashMap();
/*  854 */     indexedAssetMap.getAssetMap().forEach((name, group) -> {
/*      */           int index = indexedAssetMap.getIndex(name); if (index == Integer.MIN_VALUE)
/*      */             throw new IllegalArgumentException("Unknown key! " + name); 
/*      */           tagSetIndexMap.put(name, index);
/*      */         });
/*  859 */     TagSetPlugin.get(NPCGroup.class).putAssetSets(indexedAssetMap.getAssetMap(), (Object2IntMap)tagSetIndexMap, this.builderManager.getNameToIndexMap());
/*  860 */     putAttitudeGroups();
/*      */   }
/*      */   
/*      */   private void putAttitudeGroups() {
/*  864 */     AttitudeMap.Builder builder = new AttitudeMap.Builder();
/*  865 */     builder.addAttitudeGroups(AttitudeGroup.getAssetMap().getAssetMap());
/*  866 */     this.attitudeMap = builder.build();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public String getName(int builderIndex) {
/*  871 */     return this.builderManager.lookupName(builderIndex);
/*      */   }
/*      */   
/*      */   public int getIndex(String builderName) {
/*  875 */     return this.builderManager.getIndex(builderName);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Builder<Role> tryGetCachedValidRole(int roleIndex) {
/*  880 */     return this.builderManager.tryGetCachedValidRole(roleIndex);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public BuilderInfo getBuilderInfo(Builder<?> builder) {
/*  885 */     return this.builderManager.getBuilderInfo(builder);
/*      */   }
/*      */   
/*      */   public List<String> getRoleTemplateNames(boolean spawnableOnly) {
/*  889 */     return (List<String>)this.builderManager.collectMatchingBuilders((Collection)new ObjectArrayList(), entry -> 
/*  890 */         (entry.getBuilder().category() == Role.class && (!spawnableOnly || entry.getBuilder().isSpawnable())), (builderInfo, objects) -> objects.add(builderInfo.getKeyName()));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasRoleName(String roleName) {
/*  895 */     return (getRoleBuilderInfo(getIndex(roleName)) != null);
/*      */   }
/*      */   
/*      */   public void validateSpawnableRole(String roleName) {
/*  899 */     BuilderInfo builder = getRoleBuilderInfo(getIndex(roleName));
/*  900 */     if (builder == null) {
/*  901 */       throw new SkipSentryException(new IllegalArgumentException(roleName + " does not exist as a role!"));
/*      */     }
/*  903 */     if (!builder.getBuilder().isSpawnable()) {
/*  904 */       throw new SkipSentryException(new IllegalArgumentException(roleName + " is an abstract role type and cannot be spawned directly!"));
/*      */     }
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public BuilderInfo getRoleBuilderInfo(int roleIndex) {
/*  910 */     BuilderInfo builderInfo = this.builderManager.tryGetBuilderInfo(roleIndex);
/*      */     
/*  912 */     return (builderInfo != null && builderInfo.getBuilder().category() == Role.class) ? builderInfo : null;
/*      */   }
/*      */   
/*      */   public void setBuilderInvalid(int builderIndex) {
/*  916 */     BuilderInfo builderInfo = this.builderManager.tryGetBuilderInfo(builderIndex);
/*      */     
/*  918 */     if (builderInfo != null) builderInfo.setNeedsReload(); 
/*      */   }
/*      */   
/*      */   public AttitudeMap getAttitudeMap() {
/*  922 */     return this.attitudeMap;
/*      */   }
/*      */   
/*      */   public ItemAttitudeMap getItemAttitudeMap() {
/*  926 */     return this.itemAttitudeMap;
/*      */   }
/*      */   
/*      */   public boolean testAndValidateRole(@Nullable BuilderInfo builderInfo) {
/*  930 */     return (builderInfo != null && builderInfo.getBuilder() != null && builderInfo.getBuilder().category() == Role.class && this.builderManager.validateBuilder(builderInfo));
/*      */   }
/*      */   
/*      */   public void forceValidation(int builderIndex) {
/*  934 */     this.builderManager.forceValidation(builderIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Pair<Ref<EntityStore>, NPCEntity> spawnEntity(@Nonnull Store<EntityStore> store, int roleIndex, @Nonnull Vector3d position, Vector3f rotation, Model spawnModel, TriConsumer<NPCEntity, Ref<EntityStore>, Store<EntityStore>> postSpawn) {
/*  940 */     return spawnEntity(store, roleIndex, position, rotation, spawnModel, (TriConsumer<NPCEntity, Holder<EntityStore>, Store<EntityStore>>)null, postSpawn);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Pair<Ref<EntityStore>, NPCEntity> spawnEntity(@Nonnull Store<EntityStore> store, int roleIndex, @Nonnull Vector3d position, @Nullable Vector3f rotation, @Nullable Model spawnModel, @Nullable TriConsumer<NPCEntity, Holder<EntityStore>, Store<EntityStore>> preAddToWorld, @Nullable TriConsumer<NPCEntity, Ref<EntityStore>, Store<EntityStore>> postSpawn) {
/*  946 */     WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(WorldTimeResource.getResourceType());
/*  947 */     NPCEntity npcComponent = new NPCEntity();
/*  948 */     npcComponent.setSpawnInstant(worldTimeResource.getGameTime());
/*      */     
/*  950 */     if (rotation == null) {
/*  951 */       rotation = NULL_ROTATION;
/*      */     }
/*      */     
/*  954 */     npcComponent.saveLeashInformation(position, rotation);
/*      */     
/*  956 */     String roleName = getName(roleIndex);
/*  957 */     if (roleName == null) {
/*  958 */       get().getLogger().at(Level.WARNING).log("Unable to spawn entity with invalid role index: %s!", roleIndex);
/*  959 */       return null;
/*      */     } 
/*      */     
/*  962 */     npcComponent.setRoleName(roleName);
/*  963 */     npcComponent.setRoleIndex(roleIndex);
/*      */     
/*  965 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*  966 */     holder.addComponent(NPCEntity.getComponentType(), (Component)npcComponent);
/*  967 */     holder.addComponent(TransformComponent.getComponentType(), (Component)new TransformComponent(position, rotation));
/*  968 */     holder.addComponent(HeadRotation.getComponentType(), (Component)new HeadRotation(rotation));
/*      */     
/*  970 */     DisplayNameComponent displayNameComponent = new DisplayNameComponent(Message.raw(roleName));
/*  971 */     holder.addComponent(DisplayNameComponent.getComponentType(), (Component)displayNameComponent);
/*      */     
/*  973 */     holder.ensureComponent(UUIDComponent.getComponentType());
/*  974 */     if (spawnModel != null) {
/*  975 */       npcComponent.setInitialModelScale(spawnModel.getScale());
/*  976 */       holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(spawnModel));
/*  977 */       holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(spawnModel.toReference()));
/*      */     } 
/*      */ 
/*      */     
/*  981 */     if (preAddToWorld != null) preAddToWorld.accept(npcComponent, holder, store);
/*      */ 
/*      */ 
/*      */     
/*  985 */     Ref<EntityStore> ref = store.addEntity(holder, AddReason.SPAWN);
/*  986 */     if (ref == null) {
/*  987 */       get().getLogger().at(Level.WARNING).log("Unable to handle non-spawned entity: %s!", getName(roleIndex));
/*  988 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  992 */     if (postSpawn != null) postSpawn.accept(npcComponent, ref, store);
/*      */     
/*  994 */     return Pair.of(ref, npcComponent);
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public BuilderInfo prepareRoleBuilderInfo(int roleIndex) {
/*      */     BuilderInfo builderInfo;
/*      */     try {
/* 1001 */       builderInfo = this.builderManager.getCachedBuilderInfo(roleIndex, Role.class);
/* 1002 */       if (this.validateBuilder) {
/* 1003 */         if (!builderInfo.isValidated()) {
/* 1004 */           this.builderManager.validateBuilder(builderInfo);
/*      */         }
/* 1006 */         if (!builderInfo.isValid()) {
/* 1007 */           throw new SkipSentryException(new IllegalStateException("Builder " + builderInfo.getKeyName() + " failed validation!"));
/*      */         }
/*      */       } 
/* 1010 */     } catch (RuntimeException e) {
/* 1011 */       throw new SkipSentryException(new RuntimeException(String.format("Cannot use role template '%s' (%s): %s", new Object[] { getName(roleIndex), Integer.valueOf(roleIndex), e.getMessage() }), e));
/*      */     } 
/* 1013 */     return builderInfo;
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public static Role buildRole(@Nonnull Builder<Role> roleBuilder, @Nonnull BuilderInfo builderInfo, @Nonnull BuilderSupport builderSupport, int roleIndex) {
/*      */     Role role;
/*      */     try {
/* 1020 */       StdScope scope = roleBuilder.getBuilderParameters().createScope();
/* 1021 */       builderSupport.setScope((Scope)scope);
/* 1022 */       builderSupport.setGlobalScope((Scope)scope);
/* 1023 */       role = (Role)roleBuilder.build(builderSupport);
/* 1024 */       role.postRoleBuilt(builderSupport);
/* 1025 */     } catch (Throwable e) {
/* 1026 */       builderInfo.setNeedsReload();
/* 1027 */       throw new SkipSentryException(e);
/*      */     } 
/*      */     
/* 1030 */     role.setRoleIndex(roleIndex, builderInfo.getKeyName());
/* 1031 */     return role;
/*      */   }
/*      */   
/*      */   protected void onModelsChanged(@Nonnull LoadedAssetsEvent<String, ModelAsset, DefaultAssetMap<String, ModelAsset>> event) {
/* 1035 */     Map<String, ModelAsset> loadedModelAssets = event.getLoadedAssets();
/* 1036 */     Universe.get().getWorlds().values().forEach(world -> world.execute(()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateDescriptors() {
/* 1055 */     getLogger().at(Level.INFO).log("===== Generating descriptors for NPC!");
/* 1056 */     this.builderDescriptors = this.builderManager.generateDescriptors();
/*      */   }
/*      */   
/*      */   protected void saveDescriptors() {
/* 1060 */     getLogger().at(Level.INFO).log("===== Saving descriptors for NPC!");
/* 1061 */     Path path = Path.of("npc_descriptors.json", new String[0]);
/* 1062 */     BuilderManager.saveDescriptors(this.builderDescriptors, path);
/* 1063 */     getLogger().at(Level.INFO).log("Saved NPC descriptors to: %s", path);
/*      */   }
/*      */   
/*      */   public BuilderManager getBuilderManager() {
/* 1067 */     return this.builderManager;
/*      */   }
/*      */   
/*      */   public int getMaxBlackboardBlockCountPerType() {
/* 1071 */     return this.maxBlackboardBlockCountPerType;
/*      */   }
/*      */   
/*      */   public boolean isLogFailingTestErrors() {
/* 1075 */     return this.logFailingTestErrors;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean startRoleBenchmark(double seconds, @Nonnull Consumer<Int2ObjectMap<TimeDistributionRecorder>> onFinished) {
/* 1080 */     this.benchmarkLock.lock();
/*      */     try {
/* 1082 */       if (isBenchmarking()) return false; 
/* 1083 */       this.roleTickDistribution = (Int2ObjectMap<TimeDistributionRecorder>)new Int2ObjectOpenHashMap();
/* 1084 */       this.roleTickDistributionAll = new TimeDistributionRecorder(0.01D, 1.0E-5D);
/* 1085 */       this.roleTickDistribution.put(-1, this.roleTickDistributionAll);
/*      */     } finally {
/* 1087 */       this.benchmarkLock.unlock();
/*      */     } 
/*      */     
/* 1090 */     (new CompletableFuture())
/* 1091 */       .completeOnTimeout(null, Math.round(seconds * 1000.0D), TimeUnit.MILLISECONDS)
/* 1092 */       .thenRunAsync(() -> {
/*      */           Int2ObjectMap<TimeDistributionRecorder> distribution = this.roleTickDistribution;
/*      */           this.benchmarkLock.lock();
/*      */           try {
/*      */             this.roleTickDistribution = null;
/*      */             this.roleTickDistributionAll = null;
/*      */           } finally {
/*      */             this.benchmarkLock.unlock();
/*      */           } 
/*      */           onFinished.accept(distribution);
/*      */         });
/* 1103 */     return true;
/*      */   }
/*      */   
/*      */   public void collectRoleTick(int roleIndex, long nanos) {
/* 1107 */     if (!this.benchmarkLock.tryLock())
/*      */       return;  try {
/* 1109 */       if (this.roleTickDistribution != null) {
/* 1110 */         ((TimeDistributionRecorder)this.roleTickDistribution.computeIfAbsent(roleIndex, i -> new TimeDistributionRecorder(0.01D, 1.0E-5D))).recordNanos(nanos);
/* 1111 */         this.roleTickDistributionAll.recordNanos(nanos);
/*      */       } 
/*      */     } finally {
/* 1114 */       this.benchmarkLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isBenchmarkingRole() {
/* 1119 */     return (this.roleTickDistribution != null);
/*      */   }
/*      */   
/*      */   public boolean startSensorSupportBenchmark(double seconds, @Nonnull Consumer<Int2ObjectMap<SensorSupportBenchmark>> onFinished) {
/* 1123 */     this.benchmarkLock.lock();
/*      */     try {
/* 1125 */       if (isBenchmarking()) return false; 
/* 1126 */       this.roleSensorSupportDistribution = (Int2ObjectMap<SensorSupportBenchmark>)new Int2ObjectOpenHashMap();
/* 1127 */       this.roleSensorSupportDistributionAll = new SensorSupportBenchmark();
/* 1128 */       this.roleSensorSupportDistribution.put(-1, this.roleSensorSupportDistributionAll);
/*      */     } finally {
/* 1130 */       this.benchmarkLock.unlock();
/*      */     } 
/*      */     
/* 1133 */     (new CompletableFuture())
/* 1134 */       .completeOnTimeout(null, Math.round(seconds * 1000.0D), TimeUnit.MILLISECONDS)
/* 1135 */       .thenRunAsync(() -> {
/*      */           Int2ObjectMap<SensorSupportBenchmark> distribution = this.roleSensorSupportDistribution;
/*      */           this.benchmarkLock.lock();
/*      */           try {
/*      */             this.roleSensorSupportDistribution = null;
/*      */             this.roleSensorSupportDistributionAll = null;
/*      */           } finally {
/*      */             this.benchmarkLock.unlock();
/*      */           } 
/*      */           onFinished.accept(distribution);
/*      */         });
/* 1146 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isBenchmarkingSensorSupport() {
/* 1150 */     return (this.roleSensorSupportDistributionAll != null);
/*      */   }
/*      */   
/*      */   protected boolean isBenchmarking() {
/* 1154 */     return (isBenchmarkingRole() || isBenchmarkingSensorSupport());
/*      */   }
/*      */   
/*      */   public void collectSensorSupportPlayerList(int roleIndex, long getNanos, double maxPlayerDistanceSorted, double maxPlayerDistance, double maxPlayerDistanceAvoidance, int numPlayers) {
/* 1158 */     if (!this.benchmarkLock.tryLock())
/*      */       return;  try {
/* 1160 */       if (this.roleSensorSupportDistribution != null) {
/* 1161 */         ((SensorSupportBenchmark)this.roleSensorSupportDistribution.computeIfAbsent(roleIndex, i -> new SensorSupportBenchmark())).collectPlayerList(getNanos, maxPlayerDistanceSorted, maxPlayerDistance, maxPlayerDistanceAvoidance, numPlayers);
/* 1162 */         this.roleSensorSupportDistributionAll.collectPlayerList(getNanos, maxPlayerDistanceSorted, maxPlayerDistance, maxPlayerDistanceAvoidance, numPlayers);
/*      */       } 
/*      */     } finally {
/* 1165 */       this.benchmarkLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void collectSensorSupportEntityList(int roleIndex, long getNanos, double maxEntityDistanceSorted, double maxEntityDistance, double maxEntityDistanceAvoidance, int numEntities) {
/* 1170 */     if (!this.benchmarkLock.tryLock())
/*      */       return;  try {
/* 1172 */       if (this.roleSensorSupportDistribution != null) {
/* 1173 */         ((SensorSupportBenchmark)this.roleSensorSupportDistribution.computeIfAbsent(roleIndex, i -> new SensorSupportBenchmark())).collectEntityList(getNanos, maxEntityDistanceSorted, maxEntityDistance, maxEntityDistanceAvoidance, numEntities);
/* 1174 */         this.roleSensorSupportDistributionAll.collectEntityList(getNanos, maxEntityDistanceSorted, maxEntityDistance, maxEntityDistanceAvoidance, numEntities);
/*      */       } 
/*      */     } finally {
/* 1177 */       this.benchmarkLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void collectSensorSupportLosTest(int roleIndex, boolean cacheHit, long time) {
/* 1182 */     if (!isBenchmarkingSensorSupport() || !this.benchmarkLock.tryLock())
/*      */       return;  try {
/* 1184 */       if (this.roleSensorSupportDistribution != null) {
/* 1185 */         ((SensorSupportBenchmark)this.roleSensorSupportDistribution.computeIfAbsent(roleIndex, i -> new SensorSupportBenchmark())).collectLosTest(cacheHit, time);
/* 1186 */         this.roleSensorSupportDistributionAll.collectLosTest(cacheHit, time);
/*      */       } 
/*      */     } finally {
/* 1189 */       this.benchmarkLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void collectSensorSupportInverseLosTest(int roleIndex, boolean cacheHit) {
/* 1194 */     if (!isBenchmarkingSensorSupport() || !this.benchmarkLock.tryLock())
/*      */       return;  try {
/* 1196 */       if (this.roleSensorSupportDistribution != null) {
/* 1197 */         ((SensorSupportBenchmark)this.roleSensorSupportDistribution.computeIfAbsent(roleIndex, i -> new SensorSupportBenchmark())).collectInverseLosTest(cacheHit);
/* 1198 */         this.roleSensorSupportDistributionAll.collectInverseLosTest(cacheHit);
/*      */       } 
/*      */     } finally {
/* 1201 */       this.benchmarkLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void collectSensorSupportFriendlyBlockingTest(int roleIndex, boolean cacheHit) {
/* 1206 */     if (!isBenchmarkingSensorSupport() || !this.benchmarkLock.tryLock())
/*      */       return;  try {
/* 1208 */       if (this.roleSensorSupportDistribution != null) {
/* 1209 */         ((SensorSupportBenchmark)this.roleSensorSupportDistribution.computeIfAbsent(roleIndex, i -> new SensorSupportBenchmark())).collectFriendlyBlockingTest(cacheHit);
/* 1210 */         this.roleSensorSupportDistributionAll.collectFriendlyBlockingTest(cacheHit);
/*      */       } 
/*      */     } finally {
/* 1213 */       this.benchmarkLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void collectSensorSupportTickDone(int roleIndex) {
/* 1218 */     if (!isBenchmarkingSensorSupport() || !this.benchmarkLock.tryLock())
/*      */       return;  try {
/* 1220 */       if (this.roleSensorSupportDistribution != null) {
/* 1221 */         ((SensorSupportBenchmark)this.roleSensorSupportDistribution.computeIfAbsent(roleIndex, i -> new SensorSupportBenchmark())).tickDone();
/* 1222 */         this.roleSensorSupportDistributionAll.tickDone();
/*      */       } 
/*      */     } finally {
/* 1225 */       this.benchmarkLock.unlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nonnull
/*      */   public <T> NPCPlugin registerCoreComponentType(String name, @Nonnull Supplier<Builder<T>> builder) {
/* 1231 */     BuilderFactory<T> factory = this.builderManager.getFactory(((Builder)builder.get()).category());
/* 1232 */     factory.add(name, builder);
/* 1233 */     return this;
/*      */   }
/*      */   
/*      */   public void setRoleBuilderNeedsReload(Builder<?> builder) {
/* 1237 */     BuilderInfo builderInfo = getBuilderInfo(builder);
/* 1238 */     Objects.requireNonNull(builderInfo, "Have builder but can't get builderInfo for it");
/* 1239 */     builderInfo.setNeedsReload();
/*      */   }
/*      */   
/*      */   protected void registerCoreFactories() {
/* 1243 */     BuilderFactory<Role> roleFactory = new BuilderFactory(Role.class, "Type");
/* 1244 */     roleFactory.add("Generic", com.hypixel.hytale.server.npc.role.builders.BuilderRole::new);
/* 1245 */     roleFactory.add("Abstract", com.hypixel.hytale.server.npc.role.builders.BuilderRoleAbstract::new);
/* 1246 */     roleFactory.add("Variant", com.hypixel.hytale.server.npc.role.builders.BuilderRoleVariant::new);
/* 1247 */     this.builderManager.registerFactory(roleFactory);
/*      */     
/* 1249 */     BuilderFactory<MotionController> motionControllerFactory = new BuilderFactory(MotionController.class, "Type");
/* 1250 */     motionControllerFactory.add("Walk", com.hypixel.hytale.server.npc.movement.controllers.builders.BuilderMotionControllerWalk::new);
/* 1251 */     motionControllerFactory.add("Fly", com.hypixel.hytale.server.npc.movement.controllers.builders.BuilderMotionControllerFly::new);
/* 1252 */     motionControllerFactory.add("Dive", com.hypixel.hytale.server.npc.movement.controllers.builders.BuilderMotionControllerDive::new);
/* 1253 */     this.builderManager.registerFactory(motionControllerFactory);
/*      */     
/* 1255 */     BuilderFactory<Map<String, MotionController>> motionControllerMapFactory = new BuilderFactory(BuilderMotionControllerMapUtil.CLASS_REFERENCE, "Type", com.hypixel.hytale.server.npc.movement.controllers.builders.BuilderMotionControllerMap::new);
/* 1256 */     this.builderManager.registerFactory(motionControllerMapFactory);
/*      */     
/* 1258 */     BuilderFactory<ActionList> actionListFactory = new BuilderFactory(ActionList.class, "Type", com.hypixel.hytale.server.npc.instructions.builders.BuilderActionList::new);
/* 1259 */     this.builderManager.registerFactory(actionListFactory);
/*      */     
/* 1261 */     BuilderFactory<Instruction> instructionFactory = new BuilderFactory(Instruction.class, "Type", com.hypixel.hytale.server.npc.instructions.builders.BuilderInstruction::new);
/* 1262 */     instructionFactory.add("Random", com.hypixel.hytale.server.npc.instructions.builders.BuilderInstructionRandomized::new);
/* 1263 */     instructionFactory.add("Reference", com.hypixel.hytale.server.npc.instructions.builders.BuilderInstructionReference::new);
/* 1264 */     this.builderManager.registerFactory(instructionFactory);
/*      */     
/* 1266 */     BuilderFactory<TransientPathDefinition> transientPathFactory = new BuilderFactory(TransientPathDefinition.class, "Type", com.hypixel.hytale.server.npc.path.builders.BuilderTransientPathDefinition::new);
/* 1267 */     this.builderManager.registerFactory(transientPathFactory);
/*      */     
/* 1269 */     BuilderFactory<RelativeWaypointDefinition> relativeWaypointFactory = new BuilderFactory(RelativeWaypointDefinition.class, "Type", com.hypixel.hytale.server.npc.path.builders.BuilderRelativeWaypointDefinition::new);
/* 1270 */     this.builderManager.registerFactory(relativeWaypointFactory);
/*      */     
/* 1272 */     BuilderFactory<WeightedAction> weightedActionFactory = new BuilderFactory(WeightedAction.class, "Type", com.hypixel.hytale.server.npc.corecomponents.builders.BuilderWeightedAction::new);
/* 1273 */     this.builderManager.registerFactory(weightedActionFactory);
/*      */     
/* 1275 */     BuilderFactory<BuilderValueToParameterMapping.ValueToParameterMapping> valueToParameterMappingFactory = new BuilderFactory(BuilderValueToParameterMapping.ValueToParameterMapping.class, "Type", BuilderValueToParameterMapping::new);
/*      */     
/* 1277 */     this.builderManager.registerFactory(valueToParameterMappingFactory);
/*      */     
/* 1279 */     StateTransitionController.registerFactories(this.builderManager);
/*      */     
/* 1281 */     this.builderManager.registerFactory(new BuilderFactory(BodyMotion.class, "Type"));
/* 1282 */     this.builderManager.registerFactory(new BuilderFactory(HeadMotion.class, "Type"));
/* 1283 */     this.builderManager.registerFactory(new BuilderFactory(Action.class, "Type"));
/* 1284 */     this.builderManager.registerFactory(new BuilderFactory(Sensor.class, "Type"));
/* 1285 */     this.builderManager.registerFactory(new BuilderFactory(IEntityFilter.class, "Type"));
/* 1286 */     this.builderManager.registerFactory(new BuilderFactory(ISensorEntityPrioritiser.class, "Type"));
/* 1287 */     this.builderManager.registerFactory(new BuilderFactory(ISensorEntityCollector.class, "Type"));
/*      */   }
/*      */   
/*      */   protected static void onBalanceAssetsChanged(@Nonnull LoadedAssetsEvent<String, BalanceAsset, DefaultAssetMap<String, BalanceAsset>> event) {
/* 1291 */     Map<String, BalanceAsset> loadedAssets = event.getLoadedAssets();
/* 1292 */     Universe.get().getWorlds().forEach((name, world) -> world.execute(()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void onBalanceAssetsRemoved(@Nonnull RemovedAssetsEvent<String, BalanceAsset, DefaultAssetMap<String, BalanceAsset>> event) {
/* 1311 */     Set<String> removedAssets = event.getRemovedAssets();
/* 1312 */     Universe.get().getWorlds().forEach((name, world) -> world.execute(()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class NPCConfig
/*      */   {
/*      */     public static final BuilderCodec<NPCConfig> CODEC;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean generateDescriptors;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean generateDescriptorsFile;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/* 1374 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(NPCConfig.class, NPCConfig::new).append(new KeyedCodec("Descriptors", (Codec)Codec.BOOLEAN), (o, i) -> o.generateDescriptors = i.booleanValue(), o -> Boolean.valueOf(o.generateDescriptors)).add()).append(new KeyedCodec("DescriptorsFile", (Codec)Codec.BOOLEAN), (o, i) -> o.generateDescriptorsFile = i.booleanValue(), o -> Boolean.valueOf(o.generateDescriptorsFile)).add()).append(new KeyedCodec("AutoReload", (Codec)Codec.BOOLEAN), (o, i) -> o.autoReload = i.booleanValue(), o -> Boolean.valueOf(o.autoReload)).add()).append(new KeyedCodec("ValidateBuilders", (Codec)Codec.BOOLEAN), (o, i) -> o.validateBuilder = i.booleanValue(), o -> Boolean.valueOf(o.validateBuilder)).add()).append(new KeyedCodec("MaxBlackboardBlockType", (Codec)Codec.INTEGER), (o, i) -> o.maxBlackboardBlockType = i.intValue(), o -> Integer.valueOf(o.maxBlackboardBlockType)).add()).append(new KeyedCodec("LogFailingTestErrors", (Codec)Codec.BOOLEAN), (o, i) -> o.logFailingTestErrors = i.booleanValue(), o -> Boolean.valueOf(o.logFailingTestErrors)).add()).append(new KeyedCodec("PresetCoverageTestNPCs", (Codec)Codec.STRING_ARRAY), (o, i) -> o.presetCoverageTestNPCs = i, o -> o.presetCoverageTestNPCs).add()).build();
/*      */     }
/*      */ 
/*      */     
/*      */     private boolean autoReload = true;
/*      */     private boolean validateBuilder = true;
/* 1380 */     private int maxBlackboardBlockType = 20;
/*      */     private boolean logFailingTestErrors;
/* 1382 */     private String[] presetCoverageTestNPCs = new String[] { "Test_Bird", "Test_Block_Sensor", "Test_Attack_Bow", "Test_Combat_Sensor_Sheep", "Test_Bow_Charge", "Test_OffHand_Swap", "Test_Patrol_Path", "Test_Flock_Mixed#4", "Test_Group_Sheep", "Test_Attack_Flying_Ranged", "Test_Interaction_Complete_Task", "Test_Hotbar_Weapon_Swap", "Test_Inventory_Contents", "Test_Dive_Flee", "Test_Jumping", "Test_Walk_Leave", "Test_Walk_Seek", "Test_Watch", "Test_Chained_Path", "Test_Spawn_Action", "Test_State_Evaluator_Toggle", "Test_State_Evaluator_Sleep", "Test_Alarm", "Test_Timer_Repeating", "Test_Action_Model_Attachment", "Test_Animation_State_Change", "Test_Block_Change", "Test_Crouch", "Test_Drop_Item", "Test_Entity_Damage_Event", "Test_Hover_Parrot", "Test_In_Water", "Test_Light_Sensor", "Test_Model_Change", "Test_Particle_Emotions", "Test_Place_Blocks", "Test_Position_Adjustment_Wrapper", "Test_Probe", "Test_Sensor_Age", "Test_Sensor_DroppedItem", "Test_Shoot_At_Block", "Test_Species_Attitude", "Test_Standing_On_Block_Sensor", "Test_Teleport", "Test_Throw_NPC", "Test_Trigger_Spawners", "Test_Weather_Sensor", "Test_Bird_Land" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isGenerateDescriptors() {
/* 1394 */       return this.generateDescriptors;
/*      */     }
/*      */     
/*      */     public boolean isGenerateDescriptorsFile() {
/* 1398 */       return this.generateDescriptorsFile;
/*      */     }
/*      */     
/*      */     public boolean isAutoReload() {
/* 1402 */       return this.autoReload;
/*      */     }
/*      */     
/*      */     public boolean isValidateBuilder() {
/* 1406 */       return this.validateBuilder;
/*      */     }
/*      */     
/*      */     public int getMaxBlackboardBlockType() {
/* 1410 */       return this.maxBlackboardBlockType;
/*      */     }
/*      */     
/*      */     public boolean isLogFailingTestErrors() {
/* 1414 */       return this.logFailingTestErrors;
/*      */     }
/*      */     
/*      */     public String[] getPresetCoverageTestNPCs() {
/* 1418 */       return this.presetCoverageTestNPCs;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class NPCEntityRegenerateStatsSystem extends EntityStatsSystems.Regenerate<NPCEntity> {
/*      */     public NPCEntityRegenerateStatsSystem() {
/* 1424 */       super(EntityStatMap.getComponentType(), NPCEntity.getComponentType());
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\NPCPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */