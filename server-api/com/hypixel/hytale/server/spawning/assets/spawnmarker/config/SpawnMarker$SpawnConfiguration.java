/*     */ package com.hypixel.hytale.server.spawning.assets.spawnmarker.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.map.IWeightedElement;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpawnConfiguration
/*     */   implements IWeightedElement
/*     */ {
/*     */   public static final BuilderCodec<SpawnConfiguration> CODEC;
/*     */   
/*     */   static {
/* 255 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SpawnConfiguration.class, SpawnConfiguration::new).documentation("A configuration for an individual weighted NPC to spawn. **Note:** At least one of **RealtimeRespawnTime** and **SpawnAfterGameTime** must be set, matching the **RealtimeRespawn** flag on the marker.")).append(new KeyedCodec("Name", (Codec)Codec.STRING), (spawn, s) -> spawn.npc = s, spawn -> spawn.npc).documentation("The role name of the NPC to spawn (omitting this results in a no-op spawn, i.e. a weighted chance of spawning nothing).").addValidator(Validators.nonEmptyString()).addValidator((Validator)NPCRoleValidator.INSTANCE).add()).append(new KeyedCodec("Weight", (Codec)Codec.DOUBLE, true), (spawn, s) -> spawn.weight = s.doubleValue(), spawn -> Double.valueOf(spawn.weight)).documentation("The spawn chance, relative to the total sum of all weights in this pool.").addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("RealtimeRespawnTime", (Codec)Codec.DOUBLE), (spawn, s) -> spawn.realtimeRespawnTime = s.doubleValue(), spawn -> Double.valueOf(spawn.realtimeRespawnTime)).documentation("A value in seconds that specifies how long after the death of this mob a new mob will be spawned.").add()).append(new KeyedCodec("SpawnAfterGameTime", (Codec)Codec.DURATION), (spawn, s) -> spawn.spawnAfterGameTime = s, spawn -> spawn.spawnAfterGameTime).documentation("A Duration string e.g. of form P2DT3H4M (2 days, 3 hours, and 4 minutes) that specifies how long after the death of this mob a new mob will be spawned based on in-game time.").add()).append(new KeyedCodec("Flock", FlockAsset.CHILD_ASSET_CODEC), (spawn, o) -> spawn.flockDefinitionId = o, spawn -> spawn.flockDefinitionId).documentation("The optional flock definition to spawn around this NPC.").addValidator(FlockAsset.VALIDATOR_CACHE.getValidator()).add()).build();
/*     */   }
/* 257 */   public static final SpawnConfiguration[] EMPTY_ARRAY = new SpawnConfiguration[0];
/*     */   
/*     */   protected String npc;
/*     */   
/*     */   protected double weight;
/*     */   protected double realtimeRespawnTime;
/*     */   protected Duration spawnAfterGameTime;
/*     */   protected String flockDefinitionId;
/* 265 */   protected int flockDefinitionIndex = Integer.MIN_VALUE;
/*     */   
/*     */   public SpawnConfiguration(String npc, double weight, double realtimeRespawnTime, Duration spawnAfterGameTime, String flockDefinitionId) {
/* 268 */     this.npc = npc;
/* 269 */     this.weight = weight;
/* 270 */     this.realtimeRespawnTime = realtimeRespawnTime;
/* 271 */     this.spawnAfterGameTime = spawnAfterGameTime;
/* 272 */     this.flockDefinitionId = flockDefinitionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNpc() {
/* 279 */     return this.npc;
/*     */   }
/*     */   
/*     */   public double getRealtimeRespawnTime() {
/* 283 */     return this.realtimeRespawnTime;
/*     */   }
/*     */   
/*     */   public Duration getSpawnAfterGameTime() {
/* 287 */     return this.spawnAfterGameTime;
/*     */   }
/*     */   
/*     */   public String getFlockDefinitionId() {
/* 291 */     return this.flockDefinitionId;
/*     */   }
/*     */   
/*     */   public int getFlockDefinitionIndex() {
/* 295 */     if (this.flockDefinitionIndex == Integer.MIN_VALUE && this.flockDefinitionId != null) {
/* 296 */       int index = FlockAsset.getAssetMap().getIndex(this.flockDefinitionId);
/* 297 */       if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + this.flockDefinitionId); 
/* 298 */       this.flockDefinitionIndex = index;
/*     */     } 
/* 300 */     return this.flockDefinitionIndex;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public FlockAsset getFlockDefinition() {
/* 305 */     int index = getFlockDefinitionIndex();
/* 306 */     return (index != Integer.MIN_VALUE) ? (FlockAsset)FlockAsset.getAssetMap().getAsset(index) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getWeight() {
/* 311 */     return this.weight;
/*     */   }
/*     */   
/*     */   protected SpawnConfiguration() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\assets\spawnmarker\config\SpawnMarker$SpawnConfiguration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */