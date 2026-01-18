/*     */ package com.hypixel.hytale.server.spawning.assets.spawnmarker.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIPropertyTitle;
/*     */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.map.IWeightedElement;
/*     */ import com.hypixel.hytale.common.map.IWeightedMap;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.codec.WeightedMapCodec;
/*     */ import com.hypixel.hytale.server.flock.config.FlockAsset;
/*     */ import com.hypixel.hytale.server.npc.validators.NPCRoleValidator;
/*     */ import java.time.Duration;
/*     */ import java.util.function.Supplier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpawnMarker
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, SpawnMarker>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, SpawnMarker> CODEC;
/*     */   
/*     */   static {
/* 140 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(SpawnMarker.class, SpawnMarker::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).documentation("A marker designed to spawn NPCs with its current position and rotation. When the NPC dies, a new NPC will spawn after a defined cooldown. This cooldown can be specified either in terms of game time or real time and begins to count down from the moment the mob dies.")).appendInherited(new KeyedCodec("Model", (Codec)Codec.STRING), (spawnMarker, s) -> spawnMarker.model = s, spawnMarker -> spawnMarker.model, (spawnMarker, parent) -> spawnMarker.model = parent.model).documentation("The optional visual representation to use in the world when in creative mode (this has a default value which can be specified using **DefaultSpawnMarkerModel** in the server config for the spawning plugin).").addValidator(ModelAsset.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("NPCs", (Codec)new WeightedMapCodec((Codec)SpawnConfiguration.CODEC, (IWeightedElement[])SpawnConfiguration.EMPTY_ARRAY)), (spawnMarker, s) -> spawnMarker.weightedConfigurations = s, spawnMarker -> spawnMarker.weightedConfigurations, (spawnMarker, parent) -> spawnMarker.weightedConfigurations = parent.weightedConfigurations).documentation("A weighted list of NPCs and their configurations.").metadata((Metadata)new UIPropertyTitle("NPCs")).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("ExclusionRadius", (Codec)Codec.DOUBLE), (spawnMarker, s) -> spawnMarker.exclusionRadius = s.doubleValue(), spawnMarker -> Double.valueOf(spawnMarker.exclusionRadius), (spawnMarker, parent) -> spawnMarker.exclusionRadius = parent.exclusionRadius).documentation("A radius used to prevent a marker from spawning new NPCs if a player in adventure mode is within range.").addValidator(Validators.greaterThanOrEqual(Double.valueOf(0.0D))).add()).appendInherited(new KeyedCodec("MaxDropHeight", (Codec)Codec.DOUBLE), (spawnMarker, s) -> spawnMarker.maxDropHeightSquared = s.doubleValue() * s.doubleValue(), spawnMarker -> Double.valueOf(spawnMarker.maxDropHeightSquared), (spawnMarker, parent) -> spawnMarker.maxDropHeightSquared = parent.maxDropHeightSquared).documentation("A maximum offset from the marker's position at which the mob can be spawned. Ground mobs are spawned directly on the ground, so if the marker is high in the air due to the building having been destroyed, if the distance between the marker and the ground is greater than this, the marker will not spawn mobs.").addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).appendInherited(new KeyedCodec("RealtimeRespawn", (Codec)Codec.BOOLEAN), (spawnMarker, s) -> spawnMarker.realtimeRespawn = s.booleanValue(), spawnMarker -> Boolean.valueOf(spawnMarker.realtimeRespawn), (spawnMarker, parent) -> spawnMarker.realtimeRespawn = parent.realtimeRespawn).documentation("Whether to use real time or game time for the respawn timer.").add()).appendInherited(new KeyedCodec("ManualTrigger", (Codec)Codec.BOOLEAN), (spawnMarker, s) -> spawnMarker.manualTrigger = s.booleanValue(), spawnMarker -> Boolean.valueOf(spawnMarker.manualTrigger), (spawnMarker, parent) -> spawnMarker.manualTrigger = parent.manualTrigger).documentation("Indicates if the spawn marker has to be triggered manually or not.").add()).appendInherited(new KeyedCodec("DeactivationDistance", (Codec)Codec.DOUBLE), (spawnMarker, d) -> spawnMarker.deactivationDistance = d.doubleValue(), spawnMarker -> Double.valueOf(spawnMarker.deactivationDistance), (spawnMarker, parent) -> spawnMarker.deactivationDistance = parent.deactivationDistance).documentation("If no players are inside this range, the spawn marker will deactivate and store its NPCs once this distance is exceeded.").addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).appendInherited(new KeyedCodec("DeactivationTime", (Codec)Codec.DOUBLE), (spawnMarker, d) -> spawnMarker.deactivationTime = d.doubleValue(), spawnMarker -> Double.valueOf(spawnMarker.deactivationTime), (spawnMarker, parent) -> spawnMarker.deactivationTime = parent.deactivationTime).documentation("The delay before deactivation happens when no players are in range.").addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).validator((asset, results) -> { boolean isRealtime = asset.isRealtimeRespawn(); IWeightedMap<SpawnConfiguration> configs = asset.getWeightedConfigurations(); configs.forEach(()); })).build();
/*     */   }
/* 142 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(SpawnMarker::getAssetStore)); private static AssetStore<String, SpawnMarker, DefaultAssetMap<String, SpawnMarker>> ASSET_STORE; private AssetExtraInfo.Data data; protected String id; protected String model;
/*     */   protected IWeightedMap<SpawnConfiguration> weightedConfigurations;
/*     */   protected double exclusionRadius;
/*     */   
/*     */   public static AssetStore<String, SpawnMarker, DefaultAssetMap<String, SpawnMarker>> getAssetStore() {
/* 147 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(SpawnMarker.class); 
/* 148 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static DefaultAssetMap<String, SpawnMarker> getAssetMap() {
/* 152 */     return (DefaultAssetMap<String, SpawnMarker>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 161 */   protected double maxDropHeightSquared = 4.0D;
/*     */   protected boolean realtimeRespawn;
/*     */   protected boolean manualTrigger;
/* 164 */   protected double deactivationDistance = 40.0D;
/* 165 */   protected double deactivationTime = 5.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IWeightedMap<SpawnConfiguration> getWeightedConfigurations() {
/* 171 */     return this.weightedConfigurations;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 176 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getModel() {
/* 180 */     return this.model;
/*     */   }
/*     */   
/*     */   public double getExclusionRadius() {
/* 184 */     return this.exclusionRadius;
/*     */   }
/*     */   
/*     */   public double getMaxDropHeightSquared() {
/* 188 */     return this.maxDropHeightSquared;
/*     */   }
/*     */   
/*     */   public boolean isRealtimeRespawn() {
/* 192 */     return this.realtimeRespawn;
/*     */   }
/*     */   
/*     */   public boolean isManualTrigger() {
/* 196 */     return this.manualTrigger;
/*     */   }
/*     */   
/*     */   public double getDeactivationDistance() {
/* 200 */     return this.deactivationDistance;
/*     */   }
/*     */   
/*     */   public double getDeactivationTime() {
/* 204 */     return this.deactivationTime;
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
/*     */   public static class SpawnConfiguration
/*     */     implements IWeightedElement
/*     */   {
/*     */     public static final BuilderCodec<SpawnConfiguration> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 255 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SpawnConfiguration.class, SpawnConfiguration::new).documentation("A configuration for an individual weighted NPC to spawn. **Note:** At least one of **RealtimeRespawnTime** and **SpawnAfterGameTime** must be set, matching the **RealtimeRespawn** flag on the marker.")).append(new KeyedCodec("Name", (Codec)Codec.STRING), (spawn, s) -> spawn.npc = s, spawn -> spawn.npc).documentation("The role name of the NPC to spawn (omitting this results in a no-op spawn, i.e. a weighted chance of spawning nothing).").addValidator(Validators.nonEmptyString()).addValidator((Validator)NPCRoleValidator.INSTANCE).add()).append(new KeyedCodec("Weight", (Codec)Codec.DOUBLE, true), (spawn, s) -> spawn.weight = s.doubleValue(), spawn -> Double.valueOf(spawn.weight)).documentation("The spawn chance, relative to the total sum of all weights in this pool.").addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("RealtimeRespawnTime", (Codec)Codec.DOUBLE), (spawn, s) -> spawn.realtimeRespawnTime = s.doubleValue(), spawn -> Double.valueOf(spawn.realtimeRespawnTime)).documentation("A value in seconds that specifies how long after the death of this mob a new mob will be spawned.").add()).append(new KeyedCodec("SpawnAfterGameTime", (Codec)Codec.DURATION), (spawn, s) -> spawn.spawnAfterGameTime = s, spawn -> spawn.spawnAfterGameTime).documentation("A Duration string e.g. of form P2DT3H4M (2 days, 3 hours, and 4 minutes) that specifies how long after the death of this mob a new mob will be spawned based on in-game time.").add()).append(new KeyedCodec("Flock", FlockAsset.CHILD_ASSET_CODEC), (spawn, o) -> spawn.flockDefinitionId = o, spawn -> spawn.flockDefinitionId).documentation("The optional flock definition to spawn around this NPC.").addValidator(FlockAsset.VALIDATOR_CACHE.getValidator()).add()).build();
/*     */     }
/* 257 */     public static final SpawnConfiguration[] EMPTY_ARRAY = new SpawnConfiguration[0];
/*     */     
/*     */     protected String npc;
/*     */     
/*     */     protected double weight;
/*     */     protected double realtimeRespawnTime;
/*     */     protected Duration spawnAfterGameTime;
/*     */     protected String flockDefinitionId;
/* 265 */     protected int flockDefinitionIndex = Integer.MIN_VALUE;
/*     */     
/*     */     public SpawnConfiguration(String npc, double weight, double realtimeRespawnTime, Duration spawnAfterGameTime, String flockDefinitionId) {
/* 268 */       this.npc = npc;
/* 269 */       this.weight = weight;
/* 270 */       this.realtimeRespawnTime = realtimeRespawnTime;
/* 271 */       this.spawnAfterGameTime = spawnAfterGameTime;
/* 272 */       this.flockDefinitionId = flockDefinitionId;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getNpc() {
/* 279 */       return this.npc;
/*     */     }
/*     */     
/*     */     public double getRealtimeRespawnTime() {
/* 283 */       return this.realtimeRespawnTime;
/*     */     }
/*     */     
/*     */     public Duration getSpawnAfterGameTime() {
/* 287 */       return this.spawnAfterGameTime;
/*     */     }
/*     */     
/*     */     public String getFlockDefinitionId() {
/* 291 */       return this.flockDefinitionId;
/*     */     }
/*     */     
/*     */     public int getFlockDefinitionIndex() {
/* 295 */       if (this.flockDefinitionIndex == Integer.MIN_VALUE && this.flockDefinitionId != null) {
/* 296 */         int index = FlockAsset.getAssetMap().getIndex(this.flockDefinitionId);
/* 297 */         if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + this.flockDefinitionId); 
/* 298 */         this.flockDefinitionIndex = index;
/*     */       } 
/* 300 */       return this.flockDefinitionIndex;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public FlockAsset getFlockDefinition() {
/* 305 */       int index = getFlockDefinitionIndex();
/* 306 */       return (index != Integer.MIN_VALUE) ? (FlockAsset)FlockAsset.getAssetMap().getAsset(index) : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getWeight() {
/* 311 */       return this.weight;
/*     */     }
/*     */     
/*     */     protected SpawnConfiguration() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\assets\spawnmarker\config\SpawnMarker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */