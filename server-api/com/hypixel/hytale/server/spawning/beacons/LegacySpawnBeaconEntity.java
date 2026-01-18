/*     */ package com.hypixel.hytale.server.spawning.beacons;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.group.EntityGroup;
/*     */ import com.hypixel.hytale.server.core.entity.nameplate.Nameplate;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.DisplayNameComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HiddenFromAdventurePlayers;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentModel;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.FlockMembership;
/*     */ import com.hypixel.hytale.server.npc.components.SpawnBeaconReference;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.spawning.SpawningContext;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.BeaconNPCSpawn;
/*     */ import com.hypixel.hytale.server.spawning.controllers.BeaconSpawnController;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.SpawnSuppressionComponent;
/*     */ import com.hypixel.hytale.server.spawning.util.FloodFillPositionSelector;
/*     */ import com.hypixel.hytale.server.spawning.wrappers.BeaconSpawnWrapper;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
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
/*     */ public class LegacySpawnBeaconEntity
/*     */   extends Entity
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<LegacySpawnBeaconEntity> CODEC;
/*     */   private BeaconSpawnController spawnController;
/*     */   @Nullable
/*     */   protected UUID objectiveUUID;
/*     */   private BeaconSpawnWrapper spawnWrapper;
/*     */   private String spawnConfigId;
/*     */   private Instant nextSpawnAfter;
/*     */   private boolean nextSpawnAfterRealtime;
/*     */   @Nullable
/*     */   private Instant despawnSelfAfter;
/*     */   private int spawnAttempts;
/*     */   private int lastPlayerCount;
/*     */   
/*     */   static {
/*  80 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LegacySpawnBeaconEntity.class, LegacySpawnBeaconEntity::new, Entity.CODEC).append(new KeyedCodec("SpawnConfiguration", (Codec)Codec.STRING), (spawnBeacon, s) -> spawnBeacon.spawnConfigId = s, spawnBeacon -> spawnBeacon.spawnConfigId).add()).append(new KeyedCodec("NextSpawnAfter", (Codec)Codec.INSTANT), (spawnBeacon, instant) -> spawnBeacon.nextSpawnAfter = instant, spawnBeacon -> spawnBeacon.nextSpawnAfter).add()).append(new KeyedCodec("NextSpawnAfterRealtime", (Codec)Codec.BOOLEAN), (spawnBeacon, s) -> spawnBeacon.nextSpawnAfterRealtime = s.booleanValue(), spawnBeacon -> Boolean.valueOf(spawnBeacon.nextSpawnAfterRealtime)).add()).append(new KeyedCodec("DespawnSelfAfter", (Codec)Codec.INSTANT), (spawnBeacon, instant) -> spawnBeacon.despawnSelfAfter = instant, spawnBeacon -> spawnBeacon.despawnSelfAfter).add()).append(new KeyedCodec("LastPlayerCount", (Codec)Codec.INTEGER), (spawnBeacon, i) -> spawnBeacon.lastPlayerCount = i.intValue(), spawnBeacon -> Integer.valueOf(spawnBeacon.lastPlayerCount)).add()).append(new KeyedCodec("ObjectiveUUID", (Codec)Codec.UUID_BINARY), (entity, uuid) -> entity.objectiveUUID = uuid, entity -> entity.objectiveUUID).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static ComponentType<EntityStore, LegacySpawnBeaconEntity> getComponentType() {
/*  87 */     return EntityModule.get().getComponentType(LegacySpawnBeaconEntity.class);
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
/*     */   public LegacySpawnBeaconEntity(@Nullable World world) {
/* 112 */     super(world);
/*     */   }
/*     */ 
/*     */   
/*     */   private LegacySpawnBeaconEntity() {}
/*     */ 
/*     */   
/*     */   public String getSpawnConfigId() {
/* 120 */     return this.spawnConfigId;
/*     */   }
/*     */   
/*     */   public BeaconSpawnController getSpawnController() {
/* 124 */     return this.spawnController;
/*     */   }
/*     */   
/*     */   public void setSpawnController(@Nonnull BeaconSpawnController spawnController) {
/* 128 */     this.spawnController = spawnController;
/*     */   }
/*     */   
/*     */   public Instant getNextSpawnAfter() {
/* 132 */     return this.nextSpawnAfter;
/*     */   }
/*     */   
/*     */   public boolean isNextSpawnAfterRealtime() {
/* 136 */     return this.nextSpawnAfterRealtime;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Instant getDespawnSelfAfter() {
/* 141 */     return this.despawnSelfAfter;
/*     */   }
/*     */   
/*     */   public void setSpawnAttempts(int spawnAttempts) {
/* 145 */     this.spawnAttempts = spawnAttempts;
/*     */   }
/*     */   
/*     */   public BeaconSpawnWrapper getSpawnWrapper() {
/* 149 */     return this.spawnWrapper;
/*     */   }
/*     */   
/*     */   public void setSpawnWrapper(BeaconSpawnWrapper spawnWrapper) {
/* 153 */     this.spawnWrapper = spawnWrapper;
/*     */   }
/*     */   
/*     */   public int getSpawnAttempts() {
/* 157 */     return this.spawnAttempts;
/*     */   }
/*     */   
/*     */   public int getLastPlayerCount() {
/* 161 */     return this.lastPlayerCount;
/*     */   }
/*     */   
/*     */   public void setLastPlayerCount(int lastPlayerCount) {
/* 165 */     this.lastPlayerCount = lastPlayerCount;
/*     */   }
/*     */   
/*     */   private void setSpawnConfiguration(BeaconSpawnWrapper spawn) {
/* 169 */     this.spawnWrapper = spawn;
/*     */   }
/*     */   
/*     */   private void setSpawnConfigId(String spawnConfigId) {
/* 173 */     this.spawnConfigId = spawnConfigId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getObjectiveUUID() {
/* 181 */     return this.objectiveUUID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObjectiveUUID(@Nullable UUID objectiveUUID) {
/* 190 */     this.objectiveUUID = objectiveUUID;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHiddenFromLivingEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 195 */     Player targetPlayerComponent = (Player)componentAccessor.getComponent(targetRef, Player.getComponentType());
/* 196 */     return (targetPlayerComponent == null || targetPlayerComponent.getGameMode() != GameMode.Creative);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCollidable() {
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveTo(@Nonnull Ref<EntityStore> ref, double locX, double locY, double locZ, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 206 */     super.moveTo(ref, locX, locY, locZ, componentAccessor);
/*     */ 
/*     */     
/* 209 */     FloodFillPositionSelector floodFillPositionSelectorComponent = (FloodFillPositionSelector)componentAccessor.getComponent(ref, FloodFillPositionSelector.getComponentType());
/* 210 */     assert floodFillPositionSelectorComponent != null;
/*     */     
/* 212 */     floodFillPositionSelectorComponent.setCalculatePositionsAfter(SpawnBeaconSystems.POSITION_CALCULATION_DELAY_RANGE[1]);
/* 213 */     floodFillPositionSelectorComponent.forceRebuildCache();
/*     */ 
/*     */     
/* 216 */     this.spawnController.clearUnspawnableNPCs();
/*     */   }
/*     */   
/*     */   public void notifyFailedSpawn() {
/* 220 */     this.spawnAttempts++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifySpawn(@Nonnull Player target, @Nonnull Ref<EntityStore> spawnedEntity, @Nonnull Store<EntityStore> store) {
/* 225 */     processSpawn(spawnedEntity, target, store);
/*     */     
/* 227 */     FlockMembership flockMembershipComponent = (FlockMembership)store.getComponent(spawnedEntity, FlockMembership.getComponentType());
/* 228 */     Ref<EntityStore> flockReference = (flockMembershipComponent != null) ? flockMembershipComponent.getFlockRef() : null;
/* 229 */     if (flockReference != null && flockReference.isValid()) {
/* 230 */       EntityGroup entityGroup = (EntityGroup)store.getComponent(flockReference, EntityGroup.getComponentType());
/* 231 */       entityGroup.forEachMemberExcludingSelf((member, sender, beacon, player) -> { if (!store.getArchetype(member).contains(NPCEntity.getComponentType())) return;  beacon.processSpawn(member, player, store); }spawnedEntity, this, target);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     this.spawnController.onJobFinished((ComponentAccessor)store);
/*     */   }
/*     */   
/*     */   public static void prepareNextSpawnTimer(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 241 */     LegacySpawnBeaconEntity legacySpawnBeaconComponent = (LegacySpawnBeaconEntity)componentAccessor.getComponent(ref, getComponentType());
/* 242 */     assert legacySpawnBeaconComponent != null;
/*     */     
/* 244 */     BeaconNPCSpawn beaconSpawn = (BeaconNPCSpawn)legacySpawnBeaconComponent.spawnWrapper.getSpawn();
/*     */     
/* 246 */     boolean realtime = beaconSpawn.isRespawnRealtime();
/*     */     
/* 248 */     if (realtime) {
/* 249 */       Duration[] spawnAfterRange = beaconSpawn.getSpawnAfterRealTimeRange();
/* 250 */       Duration nextValue = RandomExtra.randomDuration(spawnAfterRange[0], spawnAfterRange[1]);
/* 251 */       legacySpawnBeaconComponent.nextSpawnAfter = Instant.now().plus(nextValue);
/* 252 */       legacySpawnBeaconComponent.nextSpawnAfterRealtime = true;
/*     */     } else {
/* 254 */       WorldTimeResource worldTimeResource = (WorldTimeResource)componentAccessor.getResource(WorldTimeResource.getResourceType());
/* 255 */       Duration[] spawnAfterRange = beaconSpawn.getSpawnAfterGameTimeRange();
/* 256 */       Duration nextValue = RandomExtra.randomDuration(spawnAfterRange[0], spawnAfterRange[1]);
/* 257 */       legacySpawnBeaconComponent.nextSpawnAfter = worldTimeResource.getGameTime().plus(nextValue);
/* 258 */       legacySpawnBeaconComponent.nextSpawnAfterRealtime = false;
/*     */     } 
/*     */     
/* 261 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 262 */     assert transformComponent != null;
/*     */     
/* 264 */     transformComponent.markChunkDirty(componentAccessor);
/*     */   }
/*     */   
/*     */   public static void clearDespawnTimer(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 268 */     LegacySpawnBeaconEntity legacySpawnBeaconComponent = (LegacySpawnBeaconEntity)componentAccessor.getComponent(ref, getComponentType());
/* 269 */     assert legacySpawnBeaconComponent != null;
/*     */     
/* 271 */     if (legacySpawnBeaconComponent.despawnSelfAfter == null)
/* 272 */       return;  legacySpawnBeaconComponent.despawnSelfAfter = null;
/*     */     
/* 274 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 275 */     assert transformComponent != null;
/*     */     
/* 277 */     transformComponent.markChunkDirty(componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setToDespawnAfter(@Nonnull Ref<EntityStore> ref, @Nullable Duration duration, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 283 */     LegacySpawnBeaconEntity legacySpawnBeaconComponent = (LegacySpawnBeaconEntity)componentAccessor.getComponent(ref, getComponentType());
/* 284 */     assert legacySpawnBeaconComponent != null;
/*     */     
/* 286 */     if (duration == null || legacySpawnBeaconComponent.despawnSelfAfter != null)
/*     */       return; 
/* 288 */     WorldTimeResource worldTimeResource = (WorldTimeResource)componentAccessor.getResource(WorldTimeResource.getResourceType());
/* 289 */     legacySpawnBeaconComponent.despawnSelfAfter = worldTimeResource.getGameTime().plus(duration);
/*     */     
/* 291 */     TransformComponent transformComponent = (TransformComponent)componentAccessor.getComponent(ref, TransformComponent.getComponentType());
/* 292 */     assert transformComponent != null;
/*     */     
/* 294 */     transformComponent.markChunkDirty(componentAccessor);
/*     */   }
/*     */   
/*     */   public void markNPCUnspawnable(int roleIndex) {
/* 298 */     this.spawnController.markNPCUnspawnable(roleIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean prepareSpawnContext(@Nonnull Vector3d playerPosition, int spawnsThisRound, int roleIndex, @Nonnull SpawningContext spawningContext, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 303 */     FloodFillPositionSelector floodFillPositionSelectorComponent = (FloodFillPositionSelector)commandBuffer.getComponent(this.reference, FloodFillPositionSelector.getComponentType());
/* 304 */     assert floodFillPositionSelectorComponent != null;
/*     */     
/* 306 */     if (!floodFillPositionSelectorComponent.hasPositionsForRole(roleIndex)) {
/*     */       
/* 308 */       markNPCUnspawnable(roleIndex);
/* 309 */       return false;
/*     */     } 
/*     */     
/* 312 */     return floodFillPositionSelectorComponent.prepareSpawnContext(playerPosition, spawnsThisRound, roleIndex, spawningContext, this.spawnWrapper);
/*     */   }
/*     */   
/*     */   private void processSpawn(@Nonnull Ref<EntityStore> ref, @Nonnull Player target, @Nonnull Store<EntityStore> store) {
/* 316 */     SpawnBeaconReference spawnBeaconReference = (SpawnBeaconReference)store.ensureAndGetComponent(ref, SpawnBeaconReference.getComponentType());
/* 317 */     spawnBeaconReference.getReference().setEntity(this.reference, (ComponentAccessor)store);
/* 318 */     spawnBeaconReference.refreshTimeoutCounter();
/* 319 */     this.spawnController.notifySpawnedEntityExists(ref, (ComponentAccessor)store);
/*     */     
/* 321 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 322 */     assert npcComponent != null;
/*     */     
/* 324 */     Role role = npcComponent.getRole();
/* 325 */     BeaconNPCSpawn spawn = (BeaconNPCSpawn)this.spawnWrapper.getSpawn();
/*     */     
/* 327 */     role.getMarkedEntitySupport().setMarkedEntity(spawn.getTargetSlot(), target.getReference());
/*     */     
/* 329 */     String spawnState = spawn.getNpcSpawnState();
/* 330 */     if (spawnState != null) {
/* 331 */       role.getStateSupport().setState(ref, spawnState, spawn.getNpcSpawnSubState(), (ComponentAccessor)store);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Pair<Ref<EntityStore>, LegacySpawnBeaconEntity> create(@Nonnull BeaconSpawnWrapper spawnWrapper, @Nonnull Vector3d position, @Nonnull Vector3f rotation, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 337 */     Holder<EntityStore> holder = createHolder(spawnWrapper, position, rotation);
/* 338 */     Ref<EntityStore> ref = componentAccessor.addEntity(holder, AddReason.SPAWN);
/* 339 */     LegacySpawnBeaconEntity legacySpawnBeaconComponent = (LegacySpawnBeaconEntity)holder.getComponent(getComponentType());
/* 340 */     return Pair.of(ref, legacySpawnBeaconComponent);
/*     */   }
/*     */   public static Holder<EntityStore> createHolder(@Nonnull BeaconSpawnWrapper spawnWrapper, @Nonnull Vector3d position, @Nonnull Vector3f rotation) {
/*     */     Model model;
/* 344 */     LegacySpawnBeaconEntity entity = new LegacySpawnBeaconEntity();
/* 345 */     entity.setSpawnConfiguration(spawnWrapper);
/*     */     
/* 347 */     BeaconNPCSpawn spawn = (BeaconNPCSpawn)spawnWrapper.getSpawn();
/* 348 */     String spawnConfigId = spawn.getId();
/* 349 */     entity.setSpawnConfigId(spawnConfigId);
/*     */     
/* 351 */     String modelName = spawn.getModel();
/* 352 */     ModelAsset modelAsset = null;
/* 353 */     if (modelName != null && !modelName.isEmpty()) {
/* 354 */       modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(modelName);
/*     */     }
/*     */     
/* 357 */     if (modelAsset == null) {
/* 358 */       model = SpawningPlugin.get().getSpawnMarkerModel();
/*     */     } else {
/* 360 */       model = Model.createUnitScaleModel(modelAsset);
/*     */     } 
/*     */     
/* 363 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/* 364 */     holder.addComponent(getComponentType(), (Component)entity);
/* 365 */     holder.addComponent(TransformComponent.getComponentType(), (Component)new TransformComponent(position, rotation));
/* 366 */     holder.ensureComponent(UUIDComponent.getComponentType());
/* 367 */     holder.addComponent(ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/* 368 */     holder.addComponent(PersistentModel.getComponentType(), (Component)new PersistentModel(model.toReference()));
/*     */     
/* 370 */     DisplayNameComponent displayNameComponent = new DisplayNameComponent(Message.raw(spawnConfigId));
/* 371 */     holder.addComponent(DisplayNameComponent.getComponentType(), (Component)displayNameComponent);
/* 372 */     holder.addComponent(Nameplate.getComponentType(), (Component)new Nameplate(spawnConfigId));
/*     */     
/* 374 */     double[] initialSpawnDelay = spawn.getInitialSpawnDelay();
/* 375 */     if (initialSpawnDelay != null) {
/* 376 */       InitialBeaconDelay delay = (InitialBeaconDelay)holder.ensureAndGetComponent(InitialBeaconDelay.getComponentType());
/* 377 */       delay.setupInitialSpawnDelay(initialSpawnDelay);
/*     */     } 
/*     */     
/* 380 */     String suppression = spawn.getSpawnSuppression();
/* 381 */     if (suppression != null && !suppression.isEmpty()) {
/* 382 */       holder.addComponent(SpawnSuppressionComponent.getComponentType(), (Component)new SpawnSuppressionComponent(suppression));
/*     */     }
/*     */     
/* 385 */     holder.ensureComponent(HiddenFromAdventurePlayers.getComponentType());
/*     */     
/* 387 */     return holder;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 393 */     return "LegacySpawnBeaconEntity{nextSpawnAfter=" + String.valueOf(this.nextSpawnAfter) + ", nextSpawnAfterRealtime=" + this.nextSpawnAfterRealtime + ", despawnSelfAfter=" + String.valueOf(this.despawnSelfAfter) + ", spawnAttempts=" + this.spawnAttempts + ", lastPlayerCount=" + this.lastPlayerCount + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\beacons\LegacySpawnBeaconEntity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */