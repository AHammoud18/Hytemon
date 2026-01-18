/*     */ package com.hypixel.hytale.server.npc.entities;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.AnimationSlot;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.ApplicationEffects;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
/*     */ import com.hypixel.hytale.server.core.entity.AnimationUtils;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ActiveAnimationComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.npc.INonPlayerCharacter;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.blackboard.Blackboard;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.blocktype.BlockTypeView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.EntityEventNotification;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.EventNotification;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.block.BlockEventType;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.block.BlockEventView;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.entity.EntityEventType;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.event.entity.EntityEventView;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.EntityEventSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.EventSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.NPCBlockEventSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.NPCEntityEventSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.PlayerBlockEventSupport;
/*     */ import com.hypixel.hytale.server.npc.components.messaging.PlayerEntityEventSupport;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.RoleDebugFlags;
/*     */ import com.hypixel.hytale.server.npc.storage.AlarmStore;
/*     */ import com.hypixel.hytale.server.npc.util.DamageData;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.WorldNPCSpawn;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.time.Instant;
/*     */ import java.util.EnumMap;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NPCEntity
/*     */   extends LivingEntity
/*     */   implements INonPlayerCharacter
/*     */ {
/*     */   public static final BuilderCodec<NPCEntity> CODEC;
/*     */   private String roleName;
/*     */   
/*     */   static {
/* 144 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(NPCEntity.class, NPCEntity::new, LivingEntity.CODEC).addField(new KeyedCodec("Env", (Codec)Codec.STRING), (npcEntity, s) -> npcEntity.environmentIndex = Environment.getAssetMap().getIndex(s), npcEntity -> { Environment environment = (Environment)Environment.getAssetMap().getAssetOrDefault(npcEntity.environmentIndex, null); return (Function)((environment != null) ? environment.getId() : null); })).addField(new KeyedCodec("HvrPhs", (Codec)Codec.DOUBLE), (npcEntity, d) -> npcEntity.hoverPhase = d.floatValue(), npcEntity -> Double.valueOf(npcEntity.hoverPhase))).addField(new KeyedCodec("HvrHght", (Codec)Codec.DOUBLE), (npcEntity, d) -> npcEntity.hoverHeight = d.doubleValue(), npcEntity -> Double.valueOf(npcEntity.hoverHeight))).addField(new KeyedCodec("SpawnName", (Codec)Codec.STRING), (npcEntity, s) -> { npcEntity.spawnRoleName = s; npcEntity.spawnRoleIndex = NPCPlugin.get().getIndex(s); }npcEntity -> npcEntity.spawnRoleName)).addField(new KeyedCodec("MdlScl", (Codec)Codec.DOUBLE), (npcEntity, d) -> npcEntity.initialModelScale = d.floatValue(), npcEntity -> Double.valueOf(npcEntity.initialModelScale))).addField(new KeyedCodec("SpawnConfig", (Codec)Codec.STRING), (npcEntity, s) -> { npcEntity.spawnConfigurationName = s; npcEntity.spawnConfigurationIndex = WorldNPCSpawn.getAssetMap().getIndex(s); }npcEntity -> npcEntity.spawnConfigurationName)).addField(new KeyedCodec("SpawnInstant", (Codec)Codec.INSTANT), (npcEntity, instant) -> npcEntity.spawnInstant = instant, npcEntity -> npcEntity.spawnInstant)).append(new KeyedCodec("AlarmStore", (Codec)AlarmStore.CODEC), (npcEntity, alarmStore) -> npcEntity.alarmStore = alarmStore, npcEntity -> npcEntity.alarmStore).add()).addField(new KeyedCodec("WorldgenId", (Codec)Codec.INTEGER), (npcEntity, i) -> npcEntity.worldgenId = i.intValue(), npcEntity -> Integer.valueOf(npcEntity.worldgenId))).append(new KeyedCodec("PathManager", (Codec)PathManager.CODEC), (npcEntity, manager) -> npcEntity.pathManager = manager, npcEntity -> npcEntity.pathManager).add()).addField(new KeyedCodec("LeashPos", (Codec)Vector3d.CODEC), (npcEntity, v) -> { npcEntity.leashPoint.assign(v); npcEntity.hasLeashPosition = true; }npcEntity -> npcEntity.requiresLeashPosition() ? npcEntity.leashPoint : null)).addField(new KeyedCodec("LeashHdg", (Codec)Codec.DOUBLE), (npcEntity, v) -> npcEntity.leashHeading = v.floatValue(), npcEntity -> npcEntity.requiresLeashPosition() ? Double.valueOf(npcEntity.leashHeading) : null)).addField(new KeyedCodec("LeashPtch", (Codec)Codec.DOUBLE), (npcEntity, v) -> npcEntity.leashPitch = v.floatValue(), npcEntity -> npcEntity.requiresLeashPosition() ? Double.valueOf(npcEntity.leashPitch) : null)).addField(new KeyedCodec("RoleName", (Codec)Codec.STRING), (npcEntity, s) -> npcEntity.roleName = s, npcEntity -> npcEntity.roleName)).build();
/*     */   }
/*     */   @Nullable
/*     */   public static ComponentType<EntityStore, NPCEntity> getComponentType() {
/* 148 */     return EntityModule.get().getComponentType(NPCEntity.class);
/*     */   }
/*     */ 
/*     */   
/* 152 */   private int roleIndex = Integer.MIN_VALUE;
/*     */   
/*     */   @Nullable
/*     */   private Role role;
/*     */   
/* 157 */   private int spawnRoleIndex = Integer.MIN_VALUE;
/*     */   @Nullable
/*     */   private String spawnRoleName;
/*     */   @Nullable
/*     */   private String spawnConfigurationName;
/* 162 */   private int environmentIndex = Integer.MIN_VALUE;
/* 163 */   private int spawnConfigurationIndex = Integer.MIN_VALUE;
/*     */   
/*     */   private boolean isSpawnTracked;
/*     */   
/*     */   private boolean isDespawning;
/*     */   private boolean isPlayingDespawnAnim;
/*     */   private float despawnRemainingSeconds;
/* 170 */   private float despawnCheckRemainingSeconds = RandomExtra.randomRange(1.0F, 5.0F);
/*     */   
/*     */   private float despawnAnimationRemainingSeconds;
/*     */   
/* 174 */   private float cachedEntityHorizontalSpeedMultiplier = Float.MAX_VALUE;
/*     */ 
/*     */   
/* 177 */   private final Vector3d leashPoint = new Vector3d();
/*     */   
/*     */   private float leashHeading;
/*     */   private float leashPitch;
/*     */   private boolean hasLeashPosition;
/*     */   private float hoverPhase;
/*     */   private double hoverHeight;
/* 184 */   private float initialModelScale = 1.0F;
/*     */ 
/*     */   
/*     */   private Instant spawnInstant;
/*     */ 
/*     */   
/*     */   @Nonnull
/* 191 */   private PathManager pathManager = new PathManager();
/*     */ 
/*     */   
/* 194 */   private final DamageData damageData = new DamageData();
/*     */   
/*     */   @Nullable
/*     */   private BlockTypeView blackboardBlockTypeView;
/*     */   
/*     */   private IntList blackboardBlockTypeSets;
/*     */   
/*     */   private BlockEventView blackboardBlockChangeView;
/*     */   
/*     */   private Map<BlockEventType, IntSet> blackboardBlockChangeSets;
/*     */   private EntityEventView blackboardEntityEventView;
/*     */   private Map<EntityEventType, IntSet> blackboardEntityEventSets;
/*     */   private AlarmStore alarmStore;
/*     */   @Deprecated(forRemoval = true)
/* 208 */   private int worldgenId = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 214 */   private final Set<UUID> reservedBy = new HashSet<>();
/*     */ 
/*     */   
/* 217 */   private final Vector3d oldPosition = new Vector3d();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPCEntity() {
/* 224 */     this.role = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NPCEntity(@Nonnull World world) {
/* 233 */     super(world);
/* 234 */     this.role = null;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public AlarmStore getAlarmStore() {
/* 239 */     if (this.alarmStore == null) this.alarmStore = new AlarmStore(); 
/* 240 */     return this.alarmStore;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Inventory createDefaultInventory() {
/* 246 */     return new Inventory((short)0, Inventory.DEFAULT_ARMOR_CAPACITY, (short)3, (short)0, (short)0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Role getRole() {
/* 251 */     return this.role;
/*     */   }
/*     */   
/*     */   public void invalidateCachedHorizontalSpeedMultiplier() {
/* 255 */     this.cachedEntityHorizontalSpeedMultiplier = Float.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public void storeTickStartPosition(@Nonnull Vector3d position) {
/* 259 */     this.oldPosition.assign(position);
/*     */   }
/*     */   
/*     */   public boolean tickDespawnAnimationRemainingSeconds(float dt) {
/* 263 */     return ((this.despawnAnimationRemainingSeconds -= dt) <= 0.0F);
/*     */   }
/*     */   
/*     */   public void setDespawnAnimationRemainingSeconds(float seconds) {
/* 267 */     this.despawnAnimationRemainingSeconds = seconds;
/*     */   }
/*     */   
/*     */   public boolean tickDespawnRemainingSeconds(float dt) {
/* 271 */     return ((this.despawnRemainingSeconds -= dt) <= 0.0F);
/*     */   }
/*     */   
/*     */   public void setDespawnRemainingSeconds(float seconds) {
/* 275 */     this.despawnRemainingSeconds = seconds;
/*     */   }
/*     */   
/*     */   public void setDespawning(boolean despawning) {
/* 279 */     this.isDespawning = despawning;
/*     */   }
/*     */   
/*     */   public void setPlayingDespawnAnim(boolean playingDespawnAnim) {
/* 283 */     this.isPlayingDespawnAnim = playingDespawnAnim;
/*     */   }
/*     */   
/*     */   public boolean tickDespawnCheckRemainingSeconds(float dt) {
/* 287 */     return ((this.despawnCheckRemainingSeconds -= dt) <= 0.0F);
/*     */   }
/*     */   
/*     */   public void setDespawnCheckRemainingSeconds(float seconds) {
/* 291 */     this.despawnCheckRemainingSeconds = seconds;
/*     */   }
/*     */   
/*     */   public void setInitialModelScale(float scale) {
/* 295 */     this.initialModelScale = scale;
/*     */   }
/*     */   
/*     */   public Vector3d getOldPosition() {
/* 299 */     return this.oldPosition;
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
/*     */   public void playAnimation(@Nonnull Ref<EntityStore> ref, @Nonnull AnimationSlot animationSlot, @Nullable String animationId, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 315 */     Model model = null;
/* 316 */     ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ref, ModelComponent.getComponentType());
/* 317 */     if (modelComponent != null) {
/* 318 */       model = modelComponent.getModel();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 323 */     if (animationSlot != AnimationSlot.Action && animationId != null && model != null && !model.getAnimationSetMap().containsKey(animationId)) {
/* 324 */       ((HytaleLogger.Api)Entity.LOGGER.at(Level.WARNING).atMostEvery(1, TimeUnit.MINUTES)).log("Missing animation '%s' for Model '%s'", animationId, model.getModelAssetId());
/*     */       
/*     */       return;
/*     */     } 
/* 328 */     ActiveAnimationComponent activeAnimationComponent = (ActiveAnimationComponent)componentAccessor.getComponent(ref, ActiveAnimationComponent.getComponentType());
/* 329 */     if (activeAnimationComponent == null) {
/* 330 */       ((HytaleLogger.Api)Entity.LOGGER.at(Level.WARNING).atMostEvery(1, TimeUnit.MINUTES)).log("Missing active animation component for entity: %s", this.roleName);
/*     */       
/*     */       return;
/*     */     } 
/* 334 */     String[] activeAnimations = activeAnimationComponent.getActiveAnimations();
/*     */ 
/*     */     
/* 337 */     if (animationSlot != AnimationSlot.Action && Objects.equals(activeAnimations[animationSlot.ordinal()], animationId))
/*     */       return; 
/* 339 */     activeAnimations[animationSlot.ordinal()] = animationId;
/* 340 */     activeAnimationComponent.setPlayingAnimation(animationSlot, animationId);
/*     */ 
/*     */     
/* 343 */     AnimationUtils.playAnimation(ref, animationSlot, animationId, componentAccessor);
/*     */   }
/*     */   
/*     */   public void clearDamageData() {
/* 347 */     this.damageData.reset();
/*     */   }
/*     */   
/*     */   public void setToDespawn() {
/* 351 */     this.isDespawning = true;
/*     */   }
/*     */   
/*     */   public void setDespawnTime(float time) {
/* 355 */     if (this.isDespawning) {
/* 356 */       this.despawnRemainingSeconds = time;
/*     */     }
/*     */   }
/*     */   
/*     */   public double getDespawnTime() {
/* 361 */     return this.despawnRemainingSeconds;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBreathe(@Nonnull Ref<EntityStore> ref, @Nonnull BlockMaterial breathingMaterial, int fluidId, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 366 */     return this.role.canBreathe(breathingMaterial, fluidId);
/*     */   }
/*     */ 
/*     */   
/*     */   public DamageData getDamageData() {
/* 371 */     return this.damageData;
/*     */   }
/*     */   
/*     */   public boolean getCanCauseDamage(@Nonnull Ref<EntityStore> attackerRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 375 */     return this.role.getCombatSupport().getCanCauseDamage(attackerRef, componentAccessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFlockSetState(@Nonnull Ref<EntityStore> ref, @Nonnull String state, @Nullable String subState, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 382 */     this.role.getStateSupport().setState(ref, state, subState, componentAccessor);
/*     */   }
/*     */   
/*     */   public void onFlockSetTarget(@Nonnull String targetSlot, @Nonnull Ref<EntityStore> target) {
/* 386 */     this.role.setMarkedTarget(targetSlot, target);
/*     */   }
/*     */   
/*     */   public void saveLeashInformation(@Nonnull Vector3d position, @Nonnull Vector3f rotation) {
/* 390 */     this.leashPoint.assign(position);
/* 391 */     this.leashHeading = rotation.getYaw();
/* 392 */     this.leashPitch = rotation.getPitch();
/* 393 */     saveLeashBlockType();
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
/*     */   public void saveLeashBlockType() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean requiresLeashPosition() {
/* 417 */     if (this.role != null) return this.role.requiresLeashPosition(); 
/* 418 */     return this.hasLeashPosition;
/*     */   }
/*     */   
/*     */   public Vector3d getLeashPoint() {
/* 422 */     return this.leashPoint;
/*     */   }
/*     */   
/*     */   public void setLeashPoint(@Nonnull Vector3d leashPoint) {
/* 426 */     this.leashPoint.assign(leashPoint);
/*     */   }
/*     */   
/*     */   public float getLeashHeading() {
/* 430 */     return this.leashHeading;
/*     */   }
/*     */   
/*     */   public void setLeashHeading(float leashHeading) {
/* 434 */     this.leashHeading = leashHeading;
/*     */   }
/*     */   
/*     */   public float getLeashPitch() {
/* 438 */     return this.leashPitch;
/*     */   }
/*     */   
/*     */   public void setLeashPitch(float leashPitch) {
/* 442 */     this.leashPitch = leashPitch;
/*     */   }
/*     */   
/*     */   public float getHoverPhase() {
/* 446 */     return this.hoverPhase;
/*     */   }
/*     */   
/*     */   public void setHoverPhase(float hoverPhase) {
/* 450 */     this.hoverPhase = hoverPhase;
/*     */   }
/*     */   
/*     */   public double getHoverHeight() {
/* 454 */     return this.hoverHeight;
/*     */   }
/*     */   
/*     */   public void setHoverHeight(double hoverHeight) {
/* 458 */     this.hoverHeight = hoverHeight;
/*     */   }
/*     */   
/*     */   public String getRoleName() {
/* 462 */     return this.roleName;
/*     */   }
/*     */   
/*     */   public void setRoleName(String roleName) {
/* 466 */     this.roleName = roleName;
/*     */   }
/*     */   
/*     */   public int getRoleIndex() {
/* 470 */     return this.roleIndex;
/*     */   }
/*     */   
/*     */   public void setRoleIndex(int roleIndex) {
/* 474 */     this.roleIndex = roleIndex;
/*     */   }
/*     */   
/*     */   public void setRole(Role role) {
/* 478 */     this.role = role;
/*     */   }
/*     */   
/*     */   public int getSpawnRoleIndex() {
/* 482 */     return (this.spawnRoleIndex != Integer.MIN_VALUE) ? this.spawnRoleIndex : this.roleIndex;
/*     */   }
/*     */   
/*     */   public void setSpawnRoleIndex(int spawnRoleIndex) {
/* 486 */     if (spawnRoleIndex == this.roleIndex) spawnRoleIndex = Integer.MIN_VALUE; 
/* 487 */     this.spawnRoleIndex = spawnRoleIndex;
/* 488 */     if (spawnRoleIndex == Integer.MIN_VALUE) {
/* 489 */       this.spawnRoleName = null;
/*     */       return;
/*     */     } 
/* 492 */     this.spawnRoleName = NPCPlugin.get().getName(spawnRoleIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockTypeView getBlockTypeBlackboardView(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store) {
/* 504 */     if (this.blackboardBlockTypeView == null) initBlockTypeBlackboardView(ref, (ComponentAccessor<EntityStore>)store);
/*     */     
/* 506 */     if (this.blackboardBlockTypeView.isOutdated(ref, store)) {
/* 507 */       this.blackboardBlockTypeView = this.blackboardBlockTypeView.getUpdatedView(ref, (ComponentAccessor)store);
/*     */     }
/*     */     
/* 510 */     return this.blackboardBlockTypeView;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockTypeView removeBlockTypeBlackboardView() {
/* 515 */     BlockTypeView view = this.blackboardBlockTypeView;
/* 516 */     this.blackboardBlockTypeView = null;
/* 517 */     return view;
/*     */   }
/*     */   
/*     */   public void initBlockTypeBlackboardView(@Nonnull Ref<EntityStore> ref, ComponentAccessor<EntityStore> componentAccessor) {
/* 521 */     if (this.blackboardBlockTypeSets != null) {
/* 522 */       this.blackboardBlockTypeView = (BlockTypeView)((Blackboard)componentAccessor.getResource(Blackboard.getResourceType())).getView(BlockTypeView.class, ref, componentAccessor);
/* 523 */       this.blackboardBlockTypeView.initialiseEntity(ref, this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void initBlockChangeBlackboardView(@Nonnull Ref<EntityStore> ref, ComponentAccessor<EntityStore> componentAccessor) {
/* 528 */     if (this.blackboardBlockChangeSets != null) {
/* 529 */       this.blackboardBlockChangeView = (BlockEventView)((Blackboard)componentAccessor.getResource(Blackboard.getResourceType())).getView(BlockEventView.class, ref, componentAccessor);
/* 530 */       this.blackboardBlockChangeView.initialiseEntity(ref, this);
/*     */     } 
/* 532 */     if (this.blackboardEntityEventSets != null) {
/* 533 */       this.blackboardEntityEventView = (EntityEventView)((Blackboard)componentAccessor.getResource(Blackboard.getResourceType())).getView(EntityEventView.class, ref, componentAccessor);
/* 534 */       this.blackboardEntityEventView.initialiseEntity(ref, this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addBlackboardBlockTypeSets(IntList blackboardBlockSets) {
/* 539 */     this.blackboardBlockTypeSets = blackboardBlockSets;
/*     */   }
/*     */   
/*     */   public IntList getBlackboardBlockTypeSets() {
/* 543 */     return this.blackboardBlockTypeSets;
/*     */   }
/*     */   
/*     */   public void addBlackboardBlockChangeSets(@Nonnull BlockEventType type, @Nonnull IntSet sets) {
/* 547 */     if (this.blackboardBlockChangeSets == null) this.blackboardBlockChangeSets = new EnumMap<>(BlockEventType.class); 
/* 548 */     this.blackboardBlockChangeSets.put(type, sets);
/*     */   }
/*     */   
/*     */   public IntSet getBlackboardBlockChangeSet(BlockEventType type) {
/* 552 */     return this.blackboardBlockChangeSets.getOrDefault(type, null);
/*     */   }
/*     */   
/*     */   public Map<BlockEventType, IntSet> getBlackboardBlockChangeSets() {
/* 556 */     return this.blackboardBlockChangeSets;
/*     */   }
/*     */   public void notifyBlockChange(@Nonnull BlockEventType type, @Nonnull EventNotification notification) {
/*     */     EventSupport<BlockEventType, EventNotification> support;
/* 560 */     Store<EntityStore> store = this.world.getEntityStore().getStore();
/* 561 */     Ref<EntityStore> initiator = notification.getInitiator();
/* 562 */     boolean isPlayer = store.getArchetype(initiator).contains(Player.getComponentType());
/*     */     
/* 564 */     if (isPlayer) {
/* 565 */       support = (EventSupport<BlockEventType, EventNotification>)store.getComponent(this.reference, PlayerBlockEventSupport.getComponentType());
/*     */     } else {
/* 567 */       support = (EventSupport<BlockEventType, EventNotification>)store.getComponent(this.reference, NPCBlockEventSupport.getComponentType());
/*     */     } 
/* 569 */     if (support != null) support.postMessage((Enum)type, notification, getReference(), store); 
/*     */   }
/*     */   
/*     */   public void addBlackboardEntityEventSets(@Nonnull EntityEventType type, @Nonnull IntSet sets) {
/* 573 */     if (this.blackboardEntityEventSets == null) this.blackboardEntityEventSets = new EnumMap<>(EntityEventType.class); 
/* 574 */     this.blackboardEntityEventSets.put(type, sets);
/*     */   }
/*     */   
/*     */   public IntSet getBlackboardEntityEventSet(@Nonnull EntityEventType type) {
/* 578 */     return this.blackboardEntityEventSets.getOrDefault(type, null);
/*     */   }
/*     */   
/*     */   public Map<EntityEventType, IntSet> getBlackboardEntityEventSets() {
/* 582 */     return this.blackboardEntityEventSets;
/*     */   }
/*     */   public void notifyEntityEvent(@Nonnull EntityEventType type, @Nonnull EntityEventNotification notification) {
/*     */     EntityEventSupport support;
/* 586 */     Store<EntityStore> store = this.world.getEntityStore().getStore();
/* 587 */     Ref<EntityStore> initiator = notification.getInitiator();
/* 588 */     boolean isPlayer = store.getArchetype(initiator).contains(Player.getComponentType());
/*     */ 
/*     */     
/* 591 */     if (isPlayer) {
/* 592 */       support = (EntityEventSupport)store.getComponent(this.reference, PlayerEntityEventSupport.getComponentType());
/*     */     } else {
/* 594 */       support = (EntityEventSupport)store.getComponent(this.reference, NPCEntityEventSupport.getComponentType());
/*     */     } 
/*     */     
/* 597 */     if (support != null) {
/* 598 */       support.postMessage(type, notification, this.reference, store);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setEnvironment(int env) {
/* 603 */     this.environmentIndex = env;
/*     */   }
/*     */   
/*     */   public int getEnvironment() {
/* 607 */     return this.environmentIndex;
/*     */   }
/*     */   
/*     */   public int getSpawnConfiguration() {
/* 611 */     return this.spawnConfigurationIndex;
/*     */   }
/*     */   
/*     */   public void setSpawnConfiguration(int spawnConfigurationIndex) {
/* 615 */     if (spawnConfigurationIndex == Integer.MIN_VALUE) {
/* 616 */       this.spawnConfigurationIndex = Integer.MIN_VALUE;
/* 617 */       this.spawnConfigurationName = null;
/*     */       return;
/*     */     } 
/* 620 */     String name = ((WorldNPCSpawn)WorldNPCSpawn.getAssetMap().getAsset(spawnConfigurationIndex)).getId();
/* 621 */     if (name == null) {
/* 622 */       throw new IllegalArgumentException("setSpawnConfiguration: Cannot find spawn configuration name for index: " + spawnConfigurationIndex);
/*     */     }
/* 624 */     this.spawnConfigurationIndex = spawnConfigurationIndex;
/* 625 */     this.spawnConfigurationName = name;
/*     */   }
/*     */   
/*     */   public boolean updateSpawnTrackingState(boolean newState) {
/* 629 */     boolean oldState = this.isSpawnTracked;
/* 630 */     this.isSpawnTracked = newState;
/* 631 */     return oldState;
/*     */   }
/*     */   
/*     */   public boolean isDespawning() {
/* 635 */     return this.isDespawning;
/*     */   }
/*     */   
/*     */   public boolean isPlayingDespawnAnim() {
/* 639 */     return this.isPlayingDespawnAnim;
/*     */   }
/*     */   
/*     */   public EnumSet<RoleDebugFlags> getRoleDebugFlags() {
/* 643 */     return this.role.getDebugSupport().getDebugFlags();
/*     */   }
/*     */   
/*     */   public void setRoleDebugFlags(@Nonnull EnumSet<RoleDebugFlags> flags) {
/* 647 */     this.role.getDebugSupport().setDebugFlags(flags);
/*     */   }
/*     */   
/*     */   public void setSpawnInstant(@Nonnull Instant spawned) {
/* 651 */     this.spawnInstant = spawned;
/*     */   }
/*     */   
/*     */   public Instant getSpawnInstant() {
/* 655 */     return this.spawnInstant;
/*     */   }
/*     */   
/*     */   public void setInventorySize(int hotbarCapacity, int inventoryCapacity, int offHandCapacity) {
/* 659 */     setInventory(new Inventory((short)inventoryCapacity, Inventory.DEFAULT_ARMOR_CAPACITY, (short)hotbarCapacity, (short)offHandCapacity, (short)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public int getLegacyWorldgenId() {
/* 667 */     return this.worldgenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PathManager getPathManager() {
/* 675 */     return this.pathManager;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setAppearance(@Nonnull Ref<EntityStore> ref, @Nonnull String name, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 681 */     if (name.isEmpty()) throw new IllegalArgumentException("Appearance can't be changed to empty");
/*     */     
/* 683 */     ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ref, ModelComponent.getComponentType());
/* 684 */     if (modelComponent == null) return false;
/*     */     
/* 686 */     Model model = modelComponent.getModel();
/* 687 */     if (name.equals(model.getModelAssetId())) return true;
/*     */     
/* 689 */     NPCEntity npcComponent = (NPCEntity)componentAccessor.getComponent(ref, getComponentType());
/* 690 */     assert npcComponent != null;
/*     */     
/* 692 */     ModelAsset modelAsset = (ModelAsset)ModelAsset.getAssetMap().getAsset(name);
/* 693 */     if (modelAsset == null) {
/* 694 */       NPCPlugin.get().getLogger().at(Level.SEVERE).log("Role '%s': Cannot find model '%s'", npcComponent.roleName, name);
/* 695 */       return false;
/*     */     } 
/*     */     
/* 698 */     npcComponent.setAppearance(ref, modelAsset, componentAccessor);
/* 699 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAppearance(@Nonnull Ref<EntityStore> ref, @Nonnull ModelAsset modelAsset, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 705 */     Model model = Model.createScaledModel(modelAsset, this.initialModelScale);
/*     */     
/* 707 */     componentAccessor.putComponent(ref, ModelComponent.getComponentType(), (Component)new ModelComponent(model));
/* 708 */     this.role.updateMotionControllers(ref, model, model.getBoundingBox(), componentAccessor);
/*     */   }
/*     */   
/*     */   public float getCurrentHorizontalSpeedMultiplier(@Nullable Ref<EntityStore> ref, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 712 */     if (this.cachedEntityHorizontalSpeedMultiplier != Float.MAX_VALUE) return this.cachedEntityHorizontalSpeedMultiplier; 
/* 713 */     this.cachedEntityHorizontalSpeedMultiplier = 1.0F;
/*     */     
/* 715 */     if (ref == null || componentAccessor == null) {
/* 716 */       return this.cachedEntityHorizontalSpeedMultiplier;
/*     */     }
/*     */     
/* 719 */     EffectControllerComponent effectControllerComponent = (EffectControllerComponent)componentAccessor.getComponent(ref, EffectControllerComponent.getComponentType());
/* 720 */     if (effectControllerComponent == null) return this.cachedEntityHorizontalSpeedMultiplier;
/*     */ 
/*     */     
/* 723 */     int[] cachedEffectIndexes = effectControllerComponent.getActiveEffectIndexes();
/* 724 */     if (cachedEffectIndexes == null) return this.cachedEntityHorizontalSpeedMultiplier;
/*     */     
/* 726 */     for (int cachedEffectIndex : cachedEffectIndexes) {
/*     */       
/* 728 */       EntityEffect effect = (EntityEffect)EntityEffect.getAssetMap().getAsset(cachedEffectIndex);
/* 729 */       if (effect != null) {
/*     */         
/* 731 */         ApplicationEffects applicationEffects = effect.getApplicationEffects();
/* 732 */         if (applicationEffects != null) {
/*     */           
/* 734 */           float multiplier = applicationEffects.getHorizontalSpeedMultiplier();
/*     */           
/* 736 */           if (multiplier >= 0.0F) this.cachedEntityHorizontalSpeedMultiplier *= multiplier; 
/*     */         } 
/*     */       } 
/* 739 */     }  return this.cachedEntityHorizontalSpeedMultiplier;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 745 */     return "NPCEntity{role=" + String.valueOf(this.role) + ", spawnRoleIndex=" + this.spawnRoleIndex + ", spawnPoint=" + String.valueOf(this.leashPoint) + ", spawnHeading=" + this.leashHeading + ", spawnPitch=" + this.leashPitch + ", environmentIndex='" + this.environmentIndex + "'} " + super
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 752 */       .toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNPCTypeId() {
/* 757 */     return this.roleName;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNPCTypeIndex() {
/* 762 */     return this.roleIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addReservation(@Nonnull UUID playerUUID) {
/* 771 */     this.reservedBy.add(playerUUID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeReservation(@Nonnull UUID playerUUID) {
/* 780 */     this.reservedBy.remove(playerUUID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReserved() {
/* 787 */     return !this.reservedBy.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReservedBy(@Nonnull UUID playerUUID) {
/* 797 */     return this.reservedBy.contains(playerUUID);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\entities\NPCEntity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */