/*     */ package com.hypixel.hytale.server.spawning.assets.spawns.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.responsecurve.ScaledXYResponseCurve;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawnsuppression.SpawnSuppression;
/*     */ import com.hypixel.hytale.server.spawning.util.FloodFillPositionSelector;
/*     */ import java.time.Duration;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class BeaconNPCSpawn
/*     */   extends NPCSpawn
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, BeaconNPCSpawn>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, BeaconNPCSpawn> CODEC;
/*     */   
/*     */   static {
/* 275 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BeaconNPCSpawn.class, BeaconNPCSpawn::new, NPCSpawn.BASE_CODEC, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).documentation("A spawning configuration used to spawn NPCs around the player when the player is within a specific radius from the beacon. When **Environments** are defined for the beacon, beacons of that type will be dynamically created while the player is in one of the specified environments.")).appendInherited(new KeyedCodec("Model", (Codec)Codec.STRING), (spawn, s) -> spawn.model = s, spawn -> spawn.model, (spawn, parent) -> spawn.model = parent.model).documentation("An optional model to represent the beacon in the world.").addValidator(ModelAsset.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("Environments", (Codec)Codec.STRING_ARRAY), (spawn, o) -> spawn.environments = o, spawn -> spawn.environments, (spawn, parent) -> spawn.environments = parent.environments).documentation("A required list of environments that this configuration covers. Each combination of environment and NPC in this configuration should be unique.\n\nFor Beacon NPC Spawn configurations, this can be left empty. In this case, these define the environments this beacon can be dynamically spawned in. If left empty, the beacon will not be dynamically spawned (e.g. if it should only be spawned by an objective).").addValidator(Validators.nonNull()).addValidator(Validators.uniqueInArray()).addValidator((Validator)Environment.VALIDATOR_CACHE.getArrayValidator()).add()).appendInherited(new KeyedCodec("TargetDistanceFromPlayer", (Codec)Codec.DOUBLE), (spawn, d) -> spawn.targetDistanceFromPlayer = d.doubleValue(), spawn -> Double.valueOf(spawn.targetDistanceFromPlayer), (spawn, parent) -> spawn.targetDistanceFromPlayer = parent.targetDistanceFromPlayer).documentation("Roughly how far the NPC should be spawned away from the player (this is a guideline and not an absolute).").addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).appendInherited(new KeyedCodec("MinDistanceFromPlayer", (Codec)Codec.DOUBLE), (spawn, d) -> spawn.minDistanceFromPlayer = d.doubleValue(), spawn -> Double.valueOf(spawn.minDistanceFromPlayer), (spawn, parent) -> spawn.minDistanceFromPlayer = parent.minDistanceFromPlayer).documentation("A hard cutoff for how close an NPC can be spawned to the player to prevent the guideline distance above resulting in an NPC spawning too close to them.").addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).appendInherited(new KeyedCodec("YRange", (Codec)Codec.INT_ARRAY), (spawn, o) -> spawn.yRange = o, spawn -> spawn.yRange, (spawn, parent) -> spawn.yRange = parent.yRange).documentation("The acceptable y range within which NPCs can be spawned from the beacon. This is defined as offsets from the beacon. With [ -5, 5 ], NPCs can be spawned from five blocks below the beacon up to five blocks above.").addValidator(Validators.intArraySize(2)).add()).appendInherited(new KeyedCodec("MaxSpawnedNPCs", (Codec)Codec.INTEGER), (spawn, i) -> spawn.maxSpawnedNpcs = i.intValue(), spawn -> Integer.valueOf(spawn.maxSpawnedNpcs), (spawn, parent) -> spawn.maxSpawnedNpcs = parent.maxSpawnedNpcs).documentation("The maximum number of NPCs this beacon can have spawned at once.").addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).appendInherited(new KeyedCodec("ConcurrentSpawnsRange", (Codec)Codec.INT_ARRAY), (spawn, i) -> spawn.concurrentSpawnsRange = i, spawn -> spawn.concurrentSpawnsRange, (spawn, parent) -> spawn.concurrentSpawnsRange = parent.concurrentSpawnsRange).documentation("The range from which a random number will be chosen that will represent the number of NPCs to be spawned in the next round between cooldowns.").addValidator(Validators.intArraySize(2)).add()).appendInherited(new KeyedCodec("SpawnAfterGameTimeRange", (Codec)new ArrayCodec((Codec)Codec.DURATION, x$0 -> new Duration[x$0])), (spawn, s) -> spawn.spawnAfterGameTime = s, spawn -> spawn.spawnAfterGameTime, (spawn, parent) -> spawn.spawnAfterGameTime = parent.spawnAfterGameTime).documentation("The random range from which to pick the next game-time based cooldown between spawns. This should be a duration string, e.g. [ \"PT5M\", \"PT10M\" ] which will spawn between 5 and 10 in-game minutes after the last spawn.").addValidator(Validators.arraySize(2)).add()).appendInherited(new KeyedCodec("SpawnAfterRealTimeRange", (Codec)new ArrayCodec((Codec)Codec.DURATION, x$0 -> new Duration[x$0])), (spawn, s) -> spawn.spawnAfterRealTime = s, spawn -> spawn.spawnAfterRealTime, (spawn, parent) -> spawn.spawnAfterRealTime = parent.spawnAfterRealTime).documentation("The random range from which to pick the next real-time based cooldown between spawns. This should be a duration string, e.g. [ \"PT30S\", \"PT80S\" ] which will spawn between 30 and 80 seconds IRL after the last spawn.").addValidator(Validators.arraySize(2)).add()).appendInherited(new KeyedCodec("InitialSpawnDelayRange", (Codec)Codec.DOUBLE_ARRAY), (spawn, s) -> spawn.initialSpawnDelay = s, spawn -> spawn.initialSpawnDelay, (spawn, parent) -> spawn.initialSpawnDelay = parent.initialSpawnDelay).documentation("An optional range from which to pick an initial delay in real time seconds before which the first round of NPCs will be spawned after a beacon is created.").addValidator(Validators.doubleArraySize(2)).add()).appendInherited(new KeyedCodec("NPCIdleDespawnTime", (Codec)Codec.DOUBLE), (spawn, d) -> spawn.npcIdleDespawnTimeSeconds = d.doubleValue(), spawn -> Double.valueOf(spawn.npcIdleDespawnTimeSeconds), (spawn, parent) -> spawn.npcIdleDespawnTimeSeconds = parent.npcIdleDespawnTimeSeconds).documentation("The number of seconds an NPC spawned by this beacon needs to spend idle before it will be despawned due to having no target. If **NPCSpawnState** is omitted, this will be ignored.").addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).appendInherited(new KeyedCodec("BeaconVacantDespawnGameTime", (Codec)Codec.DURATION), (spawn, d) -> spawn.beaconVacantDespawnTime = d, spawn -> spawn.beaconVacantDespawnTime, (spawn, parent) -> spawn.beaconVacantDespawnTime = parent.beaconVacantDespawnTime).documentation("The amount of game time that needs to pass with no players present within the **SpawnRadius** before this beacon will remove itself. This should be a duration string.").add()).appendInherited(new KeyedCodec("BeaconRadius", (Codec)Codec.DOUBLE), (spawn, d) -> spawn.beaconRadius = d.doubleValue(), spawn -> Double.valueOf(spawn.beaconRadius), (spawn, parent) -> spawn.beaconRadius = parent.beaconRadius).documentation("The radius within which a spawned NPC is considered to be under the influence of the beacon and NPCs will be spawned for a player. If an NPC spawned by the beacon moves outside this radius and is not in a busy state, it will begin to tick down the **NPCIdleDespawnTime** (if being considered). It is recommended that this be ~25% larger than the **SpawnRadius**.").addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).appendInherited(new KeyedCodec("SpawnRadius", (Codec)Codec.DOUBLE), (spawn, d) -> spawn.spawnRadius = d.doubleValue(), spawn -> Double.valueOf(spawn.spawnRadius), (spawn, parent) -> spawn.spawnRadius = parent.spawnRadius).documentation("The radius within which NPCs spawns can physically happen (i.e. where their spawn points will be).").addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).appendInherited(new KeyedCodec("NPCSpawnState", (Codec)Codec.STRING), (spawn, s) -> spawn.npcSpawnState = s, spawn -> spawn.npcSpawnState, (spawn, parent) -> spawn.npcSpawnState = parent.npcSpawnState).documentation("An optional state to force the NPC into upon spawn. If this state exists on the NPC, it will immediately enter the state upon spawn. For example, setting this to **Chase** will result in most NPCs immediately going for the player they were spawned around. If omitted, this beacon will allow idle NPCs.").add()).appendInherited(new KeyedCodec("NPCSpawnSubState", (Codec)Codec.STRING), (spawn, s) -> spawn.npcSpawnSubState = s, spawn -> spawn.npcSpawnSubState, (spawn, parent) -> spawn.npcSpawnSubState = parent.npcSpawnSubState).documentation("As with **NPCSpawnStat**, but acts as an additional qualifier to define the desired substate.").add()).appendInherited(new KeyedCodec("TargetSlot", (Codec)Codec.STRING), (spawn, s) -> spawn.targetSlot = s, spawn -> spawn.targetSlot, (spawn, parent) -> spawn.targetSlot = parent.targetSlot).documentation("The locked target slot to set the player to in the NPC.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyString()).add()).appendInherited(new KeyedCodec("SpawnSuppression", (Codec)Codec.STRING), (spawn, s) -> spawn.spawnSuppression = s, spawn -> spawn.spawnSuppression, (spawn, parent) -> spawn.spawnSuppression = parent.spawnSuppression).documentation("An optional spawn suppression that will be tied to this beacon.").addValidator(SpawnSuppression.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("OverrideSpawnSuppressors", (Codec)Codec.BOOLEAN), (spawn, b) -> spawn.overrideSpawnSuppressors = b.booleanValue(), spawn -> Boolean.valueOf(spawn.overrideSpawnSuppressors), (spawn, parent) -> spawn.overrideSpawnSuppressors = parent.overrideSpawnSuppressors).documentation("Whether this beacon should ignore any spawn suppressions.").add()).appendInherited(new KeyedCodec("MaxSpawnsScalingCurve", (Codec)ScaledXYResponseCurve.CODEC), (spawn, s) -> spawn.maxSpawnsScalingCurve = s, spawn -> spawn.maxSpawnsScalingCurve, (spawn, parent) -> spawn.maxSpawnsScalingCurve = parent.maxSpawnsScalingCurve).documentation("A scaled response curve that represents the number of additional mobs to be added to the total **MaxSpawnedNPCs** based on the number of players within the beacon's max **DistanceRange**.").add()).appendInherited(new KeyedCodec("ConcurrentSpawnsScalingCurve", (Codec)ScaledXYResponseCurve.CODEC), (spawn, s) -> spawn.concurrentSpawnsScalingCurve = s, spawn -> spawn.concurrentSpawnsScalingCurve, (spawn, parent) -> spawn.concurrentSpawnsScalingCurve = parent.concurrentSpawnsScalingCurve).documentation("A scaled response curve that represents the number of additional mobs to be added to the total **MaxConcurrentSpawns** based on the number of players within the beacon's max **DistanceRange**.").add()).appendInherited(new KeyedCodec("Debug", (Codec)(new EnumCodec(FloodFillPositionSelector.Debug.class)).documentKey((Enum)FloodFillPositionSelector.Debug.ALL, "Print all maps.").documentKey((Enum)FloodFillPositionSelector.Debug.IRREGULARITIES, "Print only irregular maps.").documentKey((Enum)FloodFillPositionSelector.Debug.DISABLED, "Disable map printing.")), (spawn, b) -> spawn.debug = b, spawn -> spawn.debug, (spawn, parent) -> spawn.debug = parent.debug).documentation("The debug mode. Can be used to print 2d maps of evaluated spawn regions.").add()).build();
/*     */   }
/*     */   
/* 278 */   public static final int[] DEFAULT_Y_RANGE = new int[] { -5, 5 };
/* 279 */   public static final int[] DEFAULT_CONCURRENT_SPAWNS_RANGE = new int[] { 1, 1 };
/* 280 */   private static final Duration[] DEFAULT_RESPAWN_TIME_RANGE = new Duration[] { Duration.ofSeconds(5L), Duration.ofSeconds(10L) };
/*     */   
/*     */   protected String model;
/*     */   
/* 284 */   protected double targetDistanceFromPlayer = 15.0D;
/* 285 */   protected double minDistanceFromPlayer = 5.0D;
/* 286 */   protected int[] yRange = DEFAULT_Y_RANGE;
/*     */   
/* 288 */   protected int maxSpawnedNpcs = 1;
/* 289 */   protected int[] concurrentSpawnsRange = DEFAULT_CONCURRENT_SPAWNS_RANGE;
/*     */   
/*     */   protected Duration[] spawnAfterGameTime;
/*     */   protected Duration[] spawnAfterRealTime;
/*     */   protected double[] initialSpawnDelay;
/* 294 */   protected double npcIdleDespawnTimeSeconds = 10.0D;
/* 295 */   protected double beaconRadius = 20.0D;
/* 296 */   protected double spawnRadius = 15.0D;
/*     */   
/*     */   protected Duration beaconVacantDespawnTime;
/*     */   
/*     */   protected String npcSpawnState;
/*     */   protected String npcSpawnSubState;
/* 302 */   protected String targetSlot = "LockedTarget";
/*     */   
/*     */   protected String spawnSuppression;
/*     */   
/*     */   protected ScaledXYResponseCurve maxSpawnsScalingCurve;
/*     */   
/*     */   protected ScaledXYResponseCurve concurrentSpawnsScalingCurve;
/*     */   
/*     */   protected boolean overrideSpawnSuppressors;
/* 311 */   protected FloodFillPositionSelector.Debug debug = FloodFillPositionSelector.Debug.DISABLED;
/*     */   
/*     */   public BeaconNPCSpawn(String id) {
/* 314 */     super(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 320 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(BeaconNPCSpawn::getAssetStore));
/*     */   
/*     */   private static AssetStore<String, BeaconNPCSpawn, IndexedLookupTableAssetMap<String, BeaconNPCSpawn>> ASSET_STORE;
/*     */   
/*     */   public static AssetStore<String, BeaconNPCSpawn, IndexedLookupTableAssetMap<String, BeaconNPCSpawn>> getAssetStore() {
/* 325 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(BeaconNPCSpawn.class); 
/* 326 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, BeaconNPCSpawn> getAssetMap() {
/* 330 */     return (IndexedLookupTableAssetMap<String, BeaconNPCSpawn>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 335 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getModel() {
/* 339 */     return this.model;
/*     */   }
/*     */   
/*     */   public double getTargetDistanceFromPlayer() {
/* 343 */     return this.targetDistanceFromPlayer;
/*     */   }
/*     */   
/*     */   public double getMinDistanceFromPlayer() {
/* 347 */     return this.minDistanceFromPlayer;
/*     */   }
/*     */   
/*     */   public int[] getYRange() {
/* 351 */     return this.yRange;
/*     */   }
/*     */   
/*     */   public int getMaxSpawnedNpcs() {
/* 355 */     return this.maxSpawnedNpcs;
/*     */   }
/*     */   
/*     */   public int[] getConcurrentSpawnsRange() {
/* 359 */     return this.concurrentSpawnsRange;
/*     */   }
/*     */   
/*     */   public Duration[] getSpawnAfterGameTimeRange() {
/* 363 */     if (this.spawnAfterGameTime == null) {
/* 364 */       return DEFAULT_RESPAWN_TIME_RANGE;
/*     */     }
/* 366 */     return this.spawnAfterGameTime;
/*     */   }
/*     */   
/*     */   public Duration[] getSpawnAfterRealTimeRange() {
/* 370 */     return this.spawnAfterRealTime;
/*     */   }
/*     */   
/*     */   public boolean isRespawnRealtime() {
/* 374 */     return (this.spawnAfterRealTime != null && this.spawnAfterGameTime == null);
/*     */   }
/*     */   
/*     */   public double[] getInitialSpawnDelay() {
/* 378 */     return this.initialSpawnDelay;
/*     */   }
/*     */   
/*     */   public double getNpcIdleDespawnTimeSeconds() {
/* 382 */     return this.npcIdleDespawnTimeSeconds;
/*     */   }
/*     */   
/*     */   public Duration getBeaconVacantDespawnTime() {
/* 386 */     return this.beaconVacantDespawnTime;
/*     */   }
/*     */   
/*     */   public double getBeaconRadius() {
/* 390 */     return this.beaconRadius;
/*     */   }
/*     */   
/*     */   public double getSpawnRadius() {
/* 394 */     return this.spawnRadius;
/*     */   }
/*     */   
/*     */   public String getNpcSpawnState() {
/* 398 */     return this.npcSpawnState;
/*     */   }
/*     */   
/*     */   public String getNpcSpawnSubState() {
/* 402 */     return this.npcSpawnSubState;
/*     */   }
/*     */   
/*     */   public String getSpawnSuppression() {
/* 406 */     return this.spawnSuppression;
/*     */   }
/*     */   
/*     */   public boolean isOverrideSpawnSuppressors() {
/* 410 */     return this.overrideSpawnSuppressors;
/*     */   }
/*     */   
/*     */   public String getTargetSlot() {
/* 414 */     return this.targetSlot;
/*     */   }
/*     */   
/*     */   public ScaledXYResponseCurve getMaxSpawnsScalingCurve() {
/* 418 */     return this.maxSpawnsScalingCurve;
/*     */   }
/*     */   
/*     */   public ScaledXYResponseCurve getConcurrentSpawnsScalingCurve() {
/* 422 */     return this.concurrentSpawnsScalingCurve;
/*     */   }
/*     */   
/*     */   public FloodFillPositionSelector.Debug getDebug() {
/* 426 */     return this.debug;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 432 */     return "BeaconNPCSpawn{id='" + this.id + "', model=" + this.model + ", npcs=" + 
/*     */ 
/*     */       
/* 435 */       Arrays.deepToString((Object[])this.npcs) + ", despawnParameters=" + (
/* 436 */       (this.despawnParameters != null) ? this.despawnParameters.toString() : "Null") + ", environments=" + 
/* 437 */       Arrays.toString((Object[])this.environments) + ", dayTimeRange=" + 
/* 438 */       Arrays.toString(this.dayTimeRange) + ", moonPhaseRange=" + 
/* 439 */       Arrays.toString(this.moonPhaseRange) + ", lightTypeMap=" + (
/* 440 */       (this.lightTypeMap != null) ? this.lightTypeMap.entrySet().stream().map(entry -> String.valueOf(entry.getKey()) + "=" + String.valueOf(entry.getKey())).collect(Collectors.joining(", ", "{", "}")) : "Null") + ", targetDistanceFromPlayer=" + this.targetDistanceFromPlayer + ", minDistanceFromPlayer=" + this.minDistanceFromPlayer + ", yRange=" + 
/*     */ 
/*     */       
/* 443 */       Arrays.toString(this.yRange) + ", maxSpawnedNpcs=" + this.maxSpawnedNpcs + ", concurrentSpawnsRange=" + 
/*     */       
/* 445 */       Arrays.toString(this.concurrentSpawnsRange) + ", spawnAfterGameTimeRange=" + 
/* 446 */       Arrays.toString((Object[])this.spawnAfterGameTime) + ", spawnAfterRealTime=" + 
/* 447 */       Arrays.toString((Object[])this.spawnAfterRealTime) + ", initialSpawnDelay=" + 
/* 448 */       Arrays.toString(this.initialSpawnDelay) + ", npcIdleDespawnTimeSeconds=" + this.npcIdleDespawnTimeSeconds + ", beaconVacantDespawnTime=" + String.valueOf(this.beaconVacantDespawnTime) + ", beaconRadius=" + this.beaconRadius + ", spawnRadius=" + this.spawnRadius + ", npcSpawnState=" + this.npcSpawnState + ", npcSpawnSubState=" + this.npcSpawnSubState + ", spawnSuppression=" + this.spawnSuppression + ", overrideSpawnSuppressors=" + this.overrideSpawnSuppressors + ", targetSlot=" + this.targetSlot + ", scaleDayTimeRange=" + this.scaleDayTimeRange + ", maxSpawnsScalingCurve=" + String.valueOf(this.maxSpawnsScalingCurve) + ", concurrentSpawnsScalingCurve=" + String.valueOf(this.concurrentSpawnsScalingCurve) + ", debug=" + String.valueOf(this.debug) + "}";
/*     */   }
/*     */   
/*     */   protected BeaconNPCSpawn() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\assets\spawns\config\BeaconNPCSpawn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */