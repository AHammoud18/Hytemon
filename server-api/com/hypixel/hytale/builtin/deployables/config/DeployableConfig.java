/*     */ package com.hypixel.hytale.builtin.deployables.config;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.deployables.DeployablesUtils;
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableComponent;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.protocol.AnimationSlot;
/*     */ import com.hypixel.hytale.protocol.DeployableConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelParticle;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.entity.hitboxcollision.HitboxCollisionConfig;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public abstract class DeployableConfig implements NetworkSerializable<DeployableConfig> {
/*  32 */   public static final CodecMapCodec<DeployableConfig> CODEC = new CodecMapCodec("Type");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<DeployableConfig> BASE_CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Map<String, StatConfig> statValues;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String deploySoundEventId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String despawnSoundEventId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String dieSoundEventId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String ambientSoundEventId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ModelParticle[] spawnParticles;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ModelParticle[] despawnParticles;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 197 */     BASE_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(DeployableConfig.class).appendInherited(new KeyedCodec("Id", (Codec)Codec.STRING), (o, i) -> o.id = i, o -> o.id, (o, p) -> o.id = p.id).documentation("Used to identify this deployable for uses such as MaxLiveCount").add()).appendInherited(new KeyedCodec("MaxLiveCount", (Codec)Codec.INTEGER), (o, i) -> o.maxLiveCount = i.intValue(), o -> Integer.valueOf(o.maxLiveCount), (o, p) -> o.maxLiveCount = p.maxLiveCount).documentation("The maximum amount of this deployable that can be live at once").add()).appendInherited(new KeyedCodec("Model", (Codec)Codec.STRING), (o, i) -> o.model = i, o -> o.model, (o, p) -> o.model = p.model).addValidator(Validators.nonNull()).addValidator(ModelAsset.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("ModelPreview", (Codec)Codec.STRING), (o, i) -> o.modelPreview = i, o -> o.modelPreview, (o, p) -> o.modelPreview = p.modelPreview).addValidator(ModelAsset.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("ModelScale", (Codec)Codec.FLOAT), (o, i) -> o.modelScale = i.floatValue(), o -> Float.valueOf(o.modelScale), (o, p) -> o.modelScale = p.modelScale).add()).appendInherited(new KeyedCodec("LiveDuration", (Codec)Codec.FLOAT), (o, i) -> o.liveDuration = i.floatValue(), o -> Float.valueOf(o.liveDuration), (o, p) -> o.liveDuration = p.liveDuration).documentation("The duration of the lifetime of the deployable in seconds").add()).appendInherited(new KeyedCodec("Invulnerable", (Codec)Codec.BOOLEAN), (o, i) -> o.invulnerable = i.booleanValue(), o -> Boolean.valueOf(o.invulnerable), (o, p) -> o.invulnerable = p.invulnerable).documentation("Whether this deployable is invulnerable to damage or not").add()).appendInherited(new KeyedCodec("Stats", (Codec)new MapCodec((Codec)StatConfig.CODEC, it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap::new)), (o, i) -> o.statValues = i, o -> o.statValues, (i, o) -> i.statValues = o.statValues).documentation("The default stat configuration for the deployable").add()).appendInherited(new KeyedCodec("DeploySoundEventId", (Codec)Codec.STRING), (o, i) -> o.deploySoundEventId = i, o -> o.deploySoundEventId, (i, o) -> i.deploySoundEventId = o.deploySoundEventId).documentation("The ID of the sound to play upon deployment (at deployment location)").addValidator((Validator)SoundEventValidators.ONESHOT).addValidator((Validator)SoundEventValidators.MONO).add()).appendInherited(new KeyedCodec("DespawnSoundEventId", (Codec)Codec.STRING), (o, i) -> o.despawnSoundEventId = i, o -> o.despawnSoundEventId, (i, o) -> i.despawnSoundEventId = o.despawnSoundEventId).documentation("The ID of the sound to play when despawning").addValidator((Validator)SoundEventValidators.ONESHOT).addValidator((Validator)SoundEventValidators.MONO).add()).appendInherited(new KeyedCodec("DieSoundEventId", (Codec)Codec.STRING), (o, i) -> o.dieSoundEventId = i, o -> o.dieSoundEventId, (i, o) -> i.dieSoundEventId = o.dieSoundEventId).documentation("The ID of the sound to play when despawning due to death").addValidator((Validator)SoundEventValidators.ONESHOT).addValidator((Validator)SoundEventValidators.MONO).add()).appendInherited(new KeyedCodec("AmbientSoundEventId", (Codec)Codec.STRING), (o, i) -> o.ambientSoundEventId = i, o -> o.ambientSoundEventId, (i, o) -> i.ambientSoundEventId = o.ambientSoundEventId).documentation("The ID of the sound to play ambiently from the deployable while it's in the world").addValidator((Validator)SoundEventValidators.LOOPING).addValidator((Validator)SoundEventValidators.MONO).add()).appendInherited(new KeyedCodec("SpawnParticles", (Codec)ModelParticle.ARRAY_CODEC), (o, i) -> o.spawnParticles = i, o -> o.spawnParticles, (i, o) -> i.spawnParticles = o.spawnParticles).documentation("A collection of model particles to play when this deployable is spawned.").add()).appendInherited(new KeyedCodec("DespawnParticles", (Codec)ModelParticle.ARRAY_CODEC), (o, i) -> o.despawnParticles = i, o -> o.despawnParticles, (i, o) -> i.despawnParticles = o.despawnParticles).documentation("A collection of model particles to play when this deployable is despawned.").add()).appendInherited(new KeyedCodec("DebugVisuals", (Codec)Codec.BOOLEAN), (o, i) -> o.debugVisuals = i.booleanValue(), o -> Boolean.valueOf(o.debugVisuals), (i, o) -> i.debugVisuals = o.debugVisuals).documentation("Whether or not to display debug visuals.").add()).appendInherited(new KeyedCodec("AllowPlaceOnWalls", (Codec)Codec.BOOLEAN), (o, i) -> o.allowPlaceOnWalls = i.booleanValue(), o -> Boolean.valueOf(o.allowPlaceOnWalls), (i, o) -> i.allowPlaceOnWalls = o.allowPlaceOnWalls).documentation("Whether or not this deployable can be placed on walls.").add()).appendInherited(new KeyedCodec("WireframeDebugVisuals", (Codec)Codec.BOOLEAN), (o, i) -> o.wireframeDebugVisuals = i.booleanValue(), o -> Boolean.valueOf(o.wireframeDebugVisuals), (i, o) -> i.wireframeDebugVisuals = o.wireframeDebugVisuals).documentation("Whether debug visuals will be wireframe or have color.").add()).appendInherited(new KeyedCodec("HitboxCollisionConfig", (Codec)Codec.STRING), (playerConfig, s) -> playerConfig.hitboxCollisionConfigId = s, playerConfig -> playerConfig.hitboxCollisionConfigId, (playerConfig, parent) -> playerConfig.hitboxCollisionConfigId = parent.hitboxCollisionConfigId).documentation("The HitboxCollision config to apply to the deployable.").addValidator(HitboxCollisionConfig.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("CountTowardsGlobalLimit", (Codec)Codec.BOOLEAN), (o, i) -> o.countTowardsGlobalLimit = i.booleanValue(), o -> Boolean.valueOf(o.countTowardsGlobalLimit), (i, o) -> i.countTowardsGlobalLimit = o.countTowardsGlobalLimit).documentation("Whether or not this deployable counts towards global deployable limit").add()).afterDecode(DeployableConfig::processConfig)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   protected transient int deploySoundEventIndex = 0;
/* 207 */   protected transient int despawnSoundEventIndex = 0;
/* 208 */   protected transient int dieSoundEventIndex = 0;
/* 209 */   protected transient int ambientSoundEventIndex = 0;
/*     */   protected Model generatedModel;
/*     */   protected Model generatedModelPreview;
/*     */   protected String hitboxCollisionConfigId;
/* 213 */   protected int hitboxCollisionConfigIndex = -1;
/*     */   private String id;
/* 215 */   private int maxLiveCount = Integer.MAX_VALUE;
/*     */   private String model;
/*     */   private String modelPreview;
/* 218 */   private float modelScale = 1.0F;
/* 219 */   private float liveDuration = 1.0F;
/*     */ 
/*     */   
/*     */   private boolean invulnerable;
/*     */ 
/*     */   
/*     */   private boolean debugVisuals;
/*     */   
/*     */   private boolean allowPlaceOnWalls;
/*     */   
/*     */   private boolean wireframeDebugVisuals;
/*     */   
/*     */   private boolean countTowardsGlobalLimit = true;
/*     */ 
/*     */   
/*     */   private static void processConfig(DeployableConfig config) {
/* 235 */     if (config.deploySoundEventId != null) {
/* 236 */       config.deploySoundEventIndex = SoundEvent.getAssetMap().getIndex(config.deploySoundEventId);
/*     */     }
/*     */     
/* 239 */     if (config.despawnSoundEventId != null) {
/* 240 */       config.despawnSoundEventIndex = SoundEvent.getAssetMap().getIndex(config.despawnSoundEventId);
/*     */     }
/*     */     
/* 243 */     if (config.dieSoundEventId != null) {
/* 244 */       config.dieSoundEventIndex = SoundEvent.getAssetMap().getIndex(config.dieSoundEventId);
/*     */     }
/*     */     
/* 247 */     if (config.ambientSoundEventId != null) {
/* 248 */       config.ambientSoundEventIndex = SoundEvent.getAssetMap().getIndex(config.ambientSoundEventId);
/*     */     }
/*     */ 
/*     */     
/* 252 */     if (config.generatedModel != null) {
/* 253 */       config.generatedModel = Model.createScaledModel((ModelAsset)ModelAsset.getAssetMap().getAsset(config.model), config.modelScale);
/*     */     }
/*     */     
/* 256 */     if (config.generatedModelPreview != null) {
/* 257 */       config.generatedModelPreview = Model.createScaledModel((ModelAsset)ModelAsset.getAssetMap().getAsset(config.modelPreview), config.modelScale);
/*     */     }
/*     */ 
/*     */     
/* 261 */     if (config.hitboxCollisionConfigId != null) {
/* 262 */       config.hitboxCollisionConfigIndex = HitboxCollisionConfig.getAssetMap().getIndexOrDefault(config.hitboxCollisionConfigId, -1);
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
/*     */   protected static void playAnimation(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull DeployableConfig config, @Nonnull String animationSetKey) {
/* 275 */     EntityStore externalData = (EntityStore)store.getExternalData();
/* 276 */     NetworkId networkIdComponent = (NetworkId)store.getComponent(ref, NetworkId.getComponentType());
/* 277 */     DeployablesUtils.playAnimation(store, networkIdComponent.getId(), ref, config, AnimationSlot.Action, null, animationSetKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void stopAnimation(@Nonnull Store<EntityStore> store, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int index) {
/* 288 */     EntityStore externalData = (EntityStore)store.getExternalData();
/* 289 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 290 */     if (ref == null || !ref.isValid())
/*     */       return; 
/* 292 */     NetworkId networkIdComponent = (NetworkId)archetypeChunk.getComponent(index, NetworkId.getComponentType());
/* 293 */     DeployablesUtils.stopAnimation(store, networkIdComponent.getId(), ref, AnimationSlot.Action);
/*     */   }
/*     */   
/*     */   public Model getModel() {
/* 297 */     if (this.generatedModel != null) return this.generatedModel; 
/* 298 */     this.generatedModel = Model.createScaledModel((ModelAsset)ModelAsset.getAssetMap().getAsset(this.model), this.modelScale);
/* 299 */     return this.generatedModel;
/*     */   }
/*     */   
/*     */   public Model getModelPreview() {
/* 303 */     if (this.modelPreview == null) return null; 
/* 304 */     if (this.generatedModelPreview != null) return this.generatedModelPreview; 
/* 305 */     this.generatedModelPreview = Model.createScaledModel((ModelAsset)ModelAsset.getAssetMap().getAsset(this.modelPreview), this.modelScale);
/* 306 */     return this.generatedModelPreview;
/*     */   }
/*     */   
/*     */   public int getHitboxCollisionConfigIndex() {
/* 310 */     return this.hitboxCollisionConfigIndex;
/*     */   }
/*     */   
/*     */   public long getLiveDurationInMillis() {
/* 314 */     return (long)(this.liveDuration * 1000.0F);
/*     */   }
/*     */   
/*     */   public float getLiveDuration() {
/* 318 */     return this.liveDuration;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 322 */     return this.id;
/*     */   }
/*     */   
/*     */   public int getMaxLiveCount() {
/* 326 */     return this.maxLiveCount;
/*     */   }
/*     */   
/*     */   public boolean getInvulnerable() {
/* 330 */     return this.invulnerable;
/*     */   }
/*     */   
/*     */   public Map<String, StatConfig> getStatValues() {
/* 334 */     return this.statValues;
/*     */   }
/*     */   
/*     */   public int getDespawnSoundEventIndex() {
/* 338 */     return this.despawnSoundEventIndex;
/*     */   }
/*     */   
/*     */   public int getDeploySoundEventIndex() {
/* 342 */     return this.deploySoundEventIndex;
/*     */   }
/*     */   
/*     */   public int getDieSoundEventIndex() {
/* 346 */     return this.dieSoundEventIndex;
/*     */   }
/*     */   
/*     */   public int getAmbientSoundEventIndex() {
/* 350 */     return this.ambientSoundEventIndex;
/*     */   }
/*     */   
/*     */   public ModelParticle[] getSpawnParticles() {
/* 354 */     return this.spawnParticles;
/*     */   }
/*     */   
/*     */   public ModelParticle[] getDespawnParticles() {
/* 358 */     return this.despawnParticles;
/*     */   }
/*     */   
/*     */   public boolean getDebugVisuals() {
/* 362 */     return this.debugVisuals;
/*     */   }
/*     */   
/*     */   public boolean getAllowPlaceOnWalls() {
/* 366 */     return this.allowPlaceOnWalls;
/*     */   }
/*     */   
/*     */   public boolean getWireframeDebugVisuals() {
/* 370 */     return this.wireframeDebugVisuals;
/*     */   }
/*     */   
/*     */   public boolean getCountTowardsGlobalLimit() {
/* 374 */     return this.countTowardsGlobalLimit;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(@Nonnull DeployableComponent deployableComponent, float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */ 
/*     */   
/*     */   public void firstTick(@Nonnull DeployableComponent deployableComponent, float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */ 
/*     */   
/*     */   public DeployableConfig toPacket() {
/* 385 */     DeployableConfig config = new DeployableConfig();
/* 386 */     config.model = getModel().toPacket();
/* 387 */     if (this.modelPreview != null) {
/* 388 */       config.modelPreview = getModelPreview().toPacket();
/*     */     }
/* 390 */     config.allowPlaceOnWalls = this.allowPlaceOnWalls;
/* 391 */     return config;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 396 */     return "DeployableConfig{}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class StatConfig
/*     */   {
/*     */     private static final BuilderCodec<StatConfig> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private float max;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 421 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(StatConfig.class, StatConfig::new).documentation("Initial and maximum values for a stat.")).append(new KeyedCodec("Max", (Codec)Codec.FLOAT), (config, f) -> config.max = f.floatValue(), config -> Float.valueOf(config.max)).addValidator(Validators.nonNull()).addValidator(Validators.greaterThan(Float.valueOf(0.0F))).documentation("The maximum value for the stat.").add()).append(new KeyedCodec("Initial", (Codec)Codec.FLOAT), (config, f) -> config.initial = f.floatValue(), config -> Float.valueOf(config.initial)).documentation("The initial value for the stat. If omitted, will be set to max.").add()).build();
/*     */     }
/*     */     
/* 424 */     private float initial = Float.MAX_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getMax() {
/* 430 */       return this.max;
/*     */     }
/*     */     
/*     */     public float getInitial() {
/* 434 */       return this.initial;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\deployables\config\DeployableConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */