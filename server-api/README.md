# Hytale Server Plugin API Documentation

## Languages
[Türkçe (Turkish)](README_TR.md)

## Core API Documentation
- [English](HYTALE_CORE_API.md)
- [Türkçe](HYTALE_CORE_API_TR.md)


This documentation is auto-generated from the decompiled source code.

## AssetEditor
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.asseteditor.AssetEditorPlugin`

_No public methods found or file parse error._

---

## BlockSpawner
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.blockspawner.BlockSpawnerPlugin`

### Public Methods
```java
public static BlockSpawnerPlugin get();
public Query<ChunkStore> getQuery();
public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer);
public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer);
public void onEntityAdd(@Nonnull Holder<ChunkStore> holder, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store);
public void onEntityRemoved(@Nonnull Holder<ChunkStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store);
public Query<ChunkStore> getQuery();
```

---

## BlockTick
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.blocktick.BlockTickPlugin`

### Public Methods
```java
public static BlockTickPlugin get();
public TickProcedure getTickProcedure(int blockId);
public int discoverTickingBlocks(@Nonnull Holder<ChunkStore> holder, @Nonnull WorldChunk chunk);
```

---

## BlockPhysics
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.blockphysics.BlockPhysicsPlugin`

### Public Methods
```java
public static void validatePrefabs(@Nonnull LoadAssetEvent event);
```

---

## BuilderTools
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin`

_No public methods found or file parse error._

---

## Crafting
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.crafting.CraftingPlugin`

### Description
Manages the crafting system, including recipes, crafting benches (stations), and player unlocked recipes.
It handles checking if a player has the required materials and if a specific recipe is valid for a given bench.

### Public Methods
```java
// Returns the singleton instance
public static CraftingPlugin get();

// Gets all available recipe IDs for a specific bench ID and category.
public static Set<String> getAvailableRecipesForCategory(String benchId, String benchCategoryId);

// Checks if an item stack can be used as a material in the current state of a bench.
public static boolean isValidCraftingMaterialForBench(BenchState benchState, ItemStack itemStack);

// Checks if an item is valid for upgrading a bench.
public static boolean isValidUpgradeMaterialForBench(BenchState benchState, ItemStack itemStack);

// Gets a list of all recipes available for a given Bench block.
public static List<CraftingRecipe> getBenchRecipes(@Nonnull Bench bench);

// Gets recipes for a bench type (e.g. Crafting, Diagram, Structural) and name.
public static List<CraftingRecipe> getBenchRecipes(BenchType benchType, String name);

// Unlocks a recipe for a player ("learns" it). Returns true if it was newly learned.
// Requires the specific player Entity Reference.
public static boolean learnRecipe(@Nonnull Ref<EntityStore> ref, @Nonnull String recipeId, @Nonnull ComponentAccessor<EntityStore> componentAccessor);

// Locks a recipe for a player ("forgets" it).
public static boolean forgetRecipe(@Nonnull Ref<EntityStore> ref, @Nonnull String itemId, @Nonnull ComponentAccessor<EntityStore> componentAccessor);

// Sends a packet to the client syncing their list of known recipes.
public static void sendKnownRecipes(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor);
```

---

## CommandMacro
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.commandmacro.MacroCommandPlugin`

### Public Methods
```java
public static MacroCommandPlugin get();
public void loadCommandMacroAsset(@Nonnull LoadedAssetsEvent<String, MacroCommandBuilder, DefaultAssetMap<String, MacroCommandBuilder>> event);
```

---

## Instances
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.instances.InstancesPlugin`

_No public methods found or file parse error._

---

## LANDiscovery
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.landiscovery.LANDiscoveryPlugin`

### Public Methods
```java
public static LANDiscoveryPlugin get();
public void setLANDiscoveryEnabled(boolean enabled);
public boolean isLANDiscoveryEnabled();
public LANDiscoveryThread getLanDiscoveryThread();
```

---

## NPC
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.server.npc.NPCPlugin`

### Description
The NPC System is one of the most complex systems in Hytale Server. It manages the lifecycle, AI, behavior, and data of all Non-Player Characters.
It uses a "Builder" pattern to construct NPCs from assets and registers various AI components like Sensors (eyes/ears), Actions (attacks/movements), and Motions.
It also interacts heavily with the `EntityStore` to manage components like `Blackboard` (memory), `CombatData`, and `Timers`.

### Public Methods
```java
// Returns the singleton instance of the NPCPlugin
public static NPCPlugin get();

// Spawns an NPC of a specific type (role) at a location.
// Returns a Pair containing the Entity reference and the NPC component.
public Pair<Ref<EntityStore>, INonPlayerCharacter> spawnNPC(@Nonnull Store<EntityStore> store, @Nonnull String npcType, @Nullable String groupType, @Nonnull Vector3d position, @Nonnull Vector3f rotation);

// Reloads all active NPCs that share a specific role index. Useful for live-updating AI behavior.
public static void reloadNPCsWithRole(int roleIndex);

// Gets the manager responsible for NPC blueprints/templates.
public BuilderManager getBuilderManager();

// Gets the map of attitudes (Friendly, Hostile, Neutral) between different factions/groups.
public AttitudeMap getAttitudeMap();

// Gets the map determining how NPCs react to specific items (e.g. holding a weapon vs a flower).
public ItemAttitudeMap getItemAttitudeMap();

// Determines if a specific role name (e.g. "kweebec_guard") exists.
public boolean hasRoleName(String roleName);

// Registers all the core AI factories (Actions, Sensors, Motions). Internal use mostly but good to know.
public void setupNPCLoading();

// Gets the human-readable name of a builder index.
public String getName(int builderIndex);

// Benchmarking methods for performance testing AI roles.
public boolean startRoleBenchmark(double seconds, @Nonnull Consumer<Int2ObjectMap<TimeDistributionRecorder>> onFinished);
```

---

## NPCObjectives
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.npcobjectives.NPCObjectivesPlugin`

### Public Methods
```java
public static NPCObjectivesPlugin get();
public static boolean hasTask(@Nonnull UUID playerUUID, @Nonnull UUID npcId, @Nonnull String taskId);
public static String updateTaskCompletion(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull UUID npcId, @Nonnull String taskId);
public static void startObjective(@Nonnull Ref<EntityStore> playerReference, @Nonnull String taskId, @Nonnull Store<EntityStore> store);
```

---

## ObjectiveReputation
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.objectivereputation.ObjectiveReputationPlugin`

### Public Methods
```java
public static ObjectiveReputationPlugin get();
```

---

## Objectives
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin`

### Description
Handles the questing and objective system. Requires `Objectives` plugin.
Manages "Objective Lines" (quest chains) and individual "Objectives".
Tracks progress for players, handles task completion (e.g. "Gather Wood", "Kill skeletons"), and rewards.

### Public Methods
```java
// Returns the singleton instance
public static ObjectivePlugin get();

// Starts a specific objective for one or more players.
// If markerUUID is provided, it might show a location marker.
public Objective startObjective(@Nonnull String objectiveId, @Nonnull Set<UUID> playerUUIDs, @Nonnull UUID worldUUID, @Nullable UUID markerUUID, @Nonnull Store<EntityStore> store);

// Starts a whole chain of objectives (Objective Line).
public Objective startObjectiveLine(@Nonnull Store<EntityStore> store, @Nonnull String objectiveLineId, @Nonnull Set<UUID> playerUUIDs, @Nonnull UUID worldUUID, @Nullable UUID markerUUID);

// Checks if a player is allowed to start an objective (e.g. if they are not already doing it).
public boolean canPlayerDoObjective(@Nonnull Player player, @Nonnull String objectiveAssetId);

// Checks if a player can start an objective line.
public boolean canPlayerDoObjectiveLine(@Nonnull Player player, @Nonnull String objectiveLineId);

// Marks an objective as completed for the associated players and handles rewards/next steps.
public void objectiveCompleted(@Nonnull Objective objective, @Nonnull Store<EntityStore> store);

// Cancels an active objective.
public void cancelObjective(@Nonnull UUID objectiveUUID, @Nonnull Store<EntityStore> store);

// Adds a player to an already running objective instance (co-op quests).
public void addPlayerToExistingObjective(@Nonnull Store<EntityStore> store, @Nonnull UUID playerUUID, @Nonnull UUID objectiveUUID);

// Removes a player from an objective.
public void removePlayerFromExistingObjective(@Nonnull Store<EntityStore> store, @Nonnull UUID playerUUID, @Nonnull UUID objectiveUUID);

// Stops tracking an objective for a specific player (client side update).
public void untrackObjectiveForPlayer(@Nonnull Objective objective, @Nonnull UUID playerUUID);

// Returns a debug dump of current objective data.
public String getObjectiveDataDump();
```

---

## ObjectiveShop
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.objectiveshop.ObjectiveShopPlugin`

### Public Methods
```java
public static ObjectiveShopPlugin get();
```

---

## Path
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.path.PathPlugin`

_No public methods found or file parse error._

---

## Reputation
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.reputation.ReputationPlugin`

### Public Methods
```java
public static ReputationPlugin get();
public int changeReputation(@Nonnull Player player, @Nonnull Ref<EntityStore> npcRef, int value, @Nonnull ComponentAccessor<EntityStore> componentAccessor);
public int changeReputation(@Nonnull Player player, @Nonnull String reputationGroupId, int value, @Nonnull ComponentAccessor<EntityStore> componentAccessor);
public int changeReputation(@Nonnull World world, @Nonnull String reputationGroupId, int value);
public int getReputationValue(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> playerEntityRef, @Nonnull Ref<EntityStore> npcEntityRef);
public int getReputationValue(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> playerEntityRef, @Nonnull String reputationGroupId);
public int getReputationValue(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> npcRef);
public int getReputationValue(@Nonnull Store<EntityStore> store, String reputationGroupId);
public ReputationRank getReputationRank(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> npcRef);
public ReputationRank getReputationRank(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull String reputationGroupId);
public ReputationRank getReputationRankFromValue(int value);
public ReputationRank getReputationRank(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> npcRef);
public Attitude getAttitude(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> npc);
public Attitude getAttitude(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> npcRef);
```

---

## NPCReputation
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.npcreputation.NPCReputationPlugin`

_No public methods found or file parse error._

---

## Shop
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.shop.ShopPlugin`

### Public Methods
```java
public static ShopPlugin get();
```

---

## ShopReputation
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.shopreputation.ShopReputationPlugin`

_No public methods found or file parse error._

---

## NPCShop
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.npcshop.NPCShopPlugin`

_No public methods found or file parse error._

---

## NPCEditor
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.npceditor.NPCEditorPlugin`

_No public methods found or file parse error._

---

## Stash
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.stash.StashPlugin`

### Public Methods
```java
public static ListTransaction<ItemStackTransaction> stash(@Nonnull ItemContainerState containerState, boolean clearDropList);
public Query<ChunkStore> getQuery();
public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer);
public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer);
public Set<Dependency<ChunkStore>> getDependencies();
```

---

## TagSet
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.tagset.TagSetPlugin`

### Public Methods
```java
public static TagSetPlugin get();
public boolean tagInSet(int tagSet, int tagIndex);
public IntSet getSet(int tagSet);
```

---

## Teleport
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.teleport.TeleportPlugin`

### Description
Manages "Warps" and teleportation logic.
Allows creating, saving, and loading named Warp points in the world.
Used by commands like `/warp` and `/tppos`.

### Public Methods
```java
// Returns the singleton instance
public static TeleportPlugin get();

// Checks if warps have been loaded from disk.
public boolean isWarpsLoaded();

// Loads warps from `warps.json` or `warps.bson` in the universe directory.
public void loadWarps();

// Saves current warps to `warps.json`.
public void saveWarps();

// Creates a Warp entity (marker) in the world.
public Holder<EntityStore> createWarp(@Nonnull Warp warp, @Nonnull Store<EntityStore> store);

// Returns the map of loaded warps. (Note: Inferred from logic, method name generic in decompiled code usually `getWarps()`)
public Map<String, Warp> getWarps();

// Updates markers on the map for players within range.
public void update(@Nonnull World world, @Nonnull GameplayConfig gameplayConfig, @Nonnull WorldMapTracker tracker, int chunkViewRadius, int playerChunkX, int playerChunkZ);
```

---

## Fluid
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.fluid.FluidPlugin`

### Public Methods
```java
public static FluidPlugin get();
public FluidSection getFluidSection(int cx, int cy, int cz);
public BlockSection getBlockSection(int cx, int cy, int cz);
public void setBlock(int x, int y, int z, int blockId);
```

---

## Weather
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.weather.WeatherPlugin`

### Public Methods
```java
public static WeatherPlugin get();
```

---

## WorldGen
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.worldgen.WorldGenPlugin`

### Public Methods
```java
public static WorldGenPlugin get();
```

---

## Farming
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.farming.FarmingPlugin`

### Public Methods
```java
public static FarmingPlugin get();
```

---

## Camera
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.camera.CameraPlugin`

_No public methods found or file parse error._

---

## WorldLocationCondition
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.worldlocationcondition.WorldLocationConditionPlugin`

_No public methods found or file parse error._

---

## NPCCombatActionEvaluator
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.npccombatactionevaluator.NPCCombatActionEvaluatorPlugin`

### Public Methods
```java
public static NPCCombatActionEvaluatorPlugin get();
```

---

## Model
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.model.ModelPlugin`

_No public methods found or file parse error._

---

## Mantling
- **Version**: 1.0.0
- **Description**: Enable mantling
- **Main Class**: `com.hypixel.hytale.builtin.mantling.MantlingPlugin`

_No public methods found or file parse error._

---

## SafetyRoll
- **Version**: 1.0.0
- **Description**: Enable Safety Roll
- **Main Class**: `com.hypixel.hytale.builtin.safetyroll.SafetyRollPlugin`

_No public methods found or file parse error._

---

## SprintForce
- **Version**: 1.0.0
- **Description**: Enable sprint acceleration/deceleration
- **Main Class**: `com.hypixel.hytale.builtin.sprintforce.SprintForcePlugin`

_No public methods found or file parse error._

---

## CrouchSlide
- **Version**: 1.0.0
- **Description**: Enable Crouch Sliding
- **Main Class**: `com.hypixel.hytale.builtin.crouchslide.CrouchSlidePlugin`

_No public methods found or file parse error._

---

## Parkour
- **Version**: 1.0.0
- **Description**: Module to add a timer with a checkpoint system
- **Main Class**: `com.hypixel.hytale.builtin.parkour.ParkourPlugin`

### Public Methods
```java
public static ParkourPlugin get();
public Model getParkourCheckpointModel();
public Object2IntMap<UUID> getCurrentCheckpointByPlayerMap();
public Object2LongMap<UUID> getStartTimeByPlayerMap();
public Int2ObjectMap<UUID> getCheckpointUUIDMap();
public int getLastIndex();
public void updateLastIndex(int index);
public void updateLastIndex();
public void resetPlayer(UUID playerUuid);
```

---

## Mounts
- **Version**: 1.0.0
- **Description**: Module to add mounts
- **Main Class**: `com.hypixel.hytale.builtin.mounts.MountPlugin`

### Public Methods
```java
public static MountPlugin getInstance();
public static void checkDismountNpc(@Nonnull ComponentAccessor<EntityStore> store, @Nonnull Player playerComponent);
public static void dismountNpc(@Nonnull ComponentAccessor<EntityStore> store, int mountEntityId);
public static void resetOriginalPlayerMovementSettings(@Nonnull PlayerRef playerRef, @Nonnull ComponentAccessor<EntityStore> store);
```

---

## HytaleGenerator
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.hytalegenerator.plugin.HytaleGenerator`

### Public Methods
```java
public CompletableFuture<GeneratedChunk> submitChunkRequest(@Nonnull ChunkRequest request);
public NStagedChunkGenerator createStagedChunkGenerator(@Nonnull ChunkRequest.GeneratorProfile generatorProfile, @Nonnull WorldStructureAsset worldStructureAsset, @Nonnull SettingsAsset settingsAsset);
```

---

## Teleporter
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.teleporter.TeleporterPlugin`

_No public methods found or file parse error._

---

## Memories
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.adventure.memories.MemoriesPlugin`

### Public Methods
```java
public static MemoriesPlugin get();
public MemoriesPluginConfig getConfig();
public int getMemoriesLevel(@Nonnull GameplayConfig gameplayConfig);
public int getMemoriesForNextLevel(@Nonnull GameplayConfig gameplayConfig);
public boolean hasRecordedMemory(Memory memory);
public boolean recordPlayerMemories(@Nonnull PlayerMemories playerMemories);
public Set<Memory> getRecordedMemories();
public void clearRecordedMemories();
public void recordAllMemories();
public Object2DoubleMap<String> getCollectionRadius();
public Query<EntityStore> getQuery();
public Set<Dependency<EntityStore>> getDependencies();
public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer);
public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer);
```

---

## Deployables
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.deployables.DeployablesPlugin`

### Public Methods
```java
public static DeployablesPlugin get();
```

---

## Portals
- **Version**: 1.0.0
- **Description**: Module to add portals
- **Main Class**: `com.hypixel.hytale.builtin.portals.PortalsPlugin`

### Public Methods
```java
public static PortalsPlugin getInstance();
public int countActiveFragments();
```

---

## Beds
- **Version**: 1.0.0
- **Description**: Module to handle beds and sleeping in them
- **Main Class**: `com.hypixel.hytale.builtin.beds.BedsPlugin`

### Public Methods
```java
public static BedsPlugin getInstance();
```

---

## Ambience
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.ambience.AmbiencePlugin`

### Public Methods
```java
public static AmbiencePlugin get();
public Model getAmbientEmitterModel();
```

---

## CreativeHub
- **Version**: 1.0.0
- **Main Class**: `com.hypixel.hytale.builtin.creativehub.CreativeHubPlugin`

### Public Methods
```java
public static CreativeHubPlugin get();
public World getOrSpawnHubInstance(@Nonnull World parentWorld, @Nonnull CreativeHubWorldConfig hubConfig, @Nonnull Transform returnPoint);
public World getActiveHubInstance(@Nonnull World parentWorld);
public void clearHubInstance(@Nonnull UUID parentWorldUuid);
public CompletableFuture<World> spawnPermanentWorldFromTemplate(@Nonnull String instanceAssetName, @Nonnull String permanentWorldName);
```

---

