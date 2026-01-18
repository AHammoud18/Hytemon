/*     */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.ComponentContext;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.ISensorEntityCollector;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.ISensorEntityPrioritiser;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.SensorWithEntityFilters;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorEntityBase;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.EntityPositionProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.util.IEntityByPriorityFilter;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public abstract class SensorEntityBase
/*     */   extends SensorWithEntityFilters {
/*  34 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*  35 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*     */   @Nullable
/*  37 */   protected static final ComponentType<EntityStore, NPCEntity> NPC_COMPONENT_TYPE = NPCEntity.getComponentType();
/*  38 */   protected static final ComponentType<EntityStore, Player> PLAYER_COMPONENT_TYPE = Player.getComponentType();
/*  39 */   protected static final ComponentType<EntityStore, DeathComponent> DEATH_COMPONENT_TYPE = DeathComponent.getComponentType();
/*     */   
/*     */   protected final double range;
/*     */   
/*     */   protected final double minRange;
/*     */   
/*     */   protected final boolean useProjectedDistance;
/*     */   
/*     */   protected final boolean lockOnTarget;
/*     */   
/*     */   protected final boolean autoUnlockTarget;
/*     */   
/*     */   protected final boolean onlyLockedTarget;
/*     */   protected final int lockedTargetSlot;
/*     */   protected final int ignoredTargetSlot;
/*     */   protected final ISensorEntityPrioritiser prioritiser;
/*     */   protected IEntityByPriorityFilter npcPrioritiser;
/*     */   protected IEntityByPriorityFilter playerPrioritiser;
/*     */   @Nullable
/*     */   protected final ISensorEntityCollector collector;
/*     */   protected int ownRole;
/*  60 */   protected final EntityPositionProvider positionProvider = new EntityPositionProvider();
/*     */   
/*     */   public SensorEntityBase(@Nonnull BuilderSensorEntityBase builder, ISensorEntityPrioritiser prioritiser, @Nonnull BuilderSupport builderSupport) {
/*  63 */     super((BuilderSensorBase)builder, builder.getFilters(builderSupport, prioritiser, ComponentContext.SensorEntity));
/*  64 */     this.range = builder.getRange(builderSupport);
/*  65 */     this.minRange = builder.getMinRange(builderSupport);
/*  66 */     this.lockOnTarget = builder.isLockOnTarget(builderSupport);
/*  67 */     this.autoUnlockTarget = builder.isAutoUnlockTarget(builderSupport);
/*  68 */     this.onlyLockedTarget = builder.isOnlyLockedTarget(builderSupport);
/*  69 */     this.useProjectedDistance = builder.isUseProjectedDistance(builderSupport);
/*  70 */     this.lockedTargetSlot = builder.getLockedTargetSlot(builderSupport);
/*  71 */     this.ignoredTargetSlot = builder.getIgnoredTargetSlot(builderSupport);
/*  72 */     this.prioritiser = prioritiser;
/*  73 */     this.collector = builder.getCollector(builderSupport);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/*  78 */     if (!super.matches(ref, role, dt, store)) {
/*  79 */       this.positionProvider.clear();
/*  80 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  84 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TRANSFORM_COMPONENT_TYPE);
/*  85 */     assert transformComponent != null;
/*     */     
/*  87 */     Vector3d position = transformComponent.getPosition();
/*     */     
/*  89 */     this.ownRole = role.getRoleIndex();
/*     */ 
/*     */ 
/*     */     
/*  93 */     if (this.ignoredTargetSlot == Integer.MIN_VALUE || this.ignoredTargetSlot != this.lockedTargetSlot) {
/*  94 */       Ref<EntityStore> ref1 = filterLockedEntity(ref, position, role, store);
/*  95 */       if (ref1 != null) {
/*     */         
/*  97 */         this.collector.init(ref, role, (ComponentAccessor)store);
/*  98 */         if (!this.collector.terminateOnFirstMatch()) findPlayerOrEntity(ref, position, role, store); 
/*  99 */         this.collector.cleanup();
/* 100 */         return (this.positionProvider.setTarget(ref1, (ComponentAccessor)store) != null);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 105 */     if (this.onlyLockedTarget) {
/* 106 */       this.positionProvider.clear();
/* 107 */       return false;
/*     */     } 
/*     */     
/* 110 */     this.collector.init(ref, role, (ComponentAccessor)store);
/* 111 */     Ref<EntityStore> targetRef = findPlayerOrEntity(ref, position, role, store);
/* 112 */     this.collector.cleanup();
/*     */ 
/*     */     
/* 115 */     if (targetRef == null) {
/* 116 */       this.positionProvider.clear();
/* 117 */       return false;
/*     */     } 
/*     */     
/* 120 */     this.positionProvider.setTarget(targetRef, (ComponentAccessor)store);
/*     */     
/* 122 */     if (this.lockOnTarget) role.getMarkedEntitySupport().setMarkedEntity(this.lockedTargetSlot, targetRef); 
/* 123 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/* 128 */     this.positionProvider.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public InfoProvider getSensorInfo() {
/* 133 */     return (InfoProvider)this.positionProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(@Nonnull Role role) {
/* 138 */     super.registerWithSupport(role);
/* 139 */     if (isGetPlayers()) {
/* 140 */       role.getPositionCache().requirePlayerDistanceSorted(this.range);
/*     */     }
/* 142 */     if (isGetNPCs()) {
/* 143 */       role.getPositionCache().requireEntityDistanceSorted(this.range);
/*     */     }
/* 145 */     this.prioritiser.registerWithSupport(role);
/* 146 */     this.collector.registerWithSupport(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 151 */     super.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/* 152 */     this.prioritiser.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/* 153 */     this.collector.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/* 158 */     super.loaded(role);
/* 159 */     this.prioritiser.loaded(role);
/* 160 */     this.collector.loaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/* 165 */     super.spawned(role);
/* 166 */     this.prioritiser.spawned(role);
/* 167 */     this.collector.spawned(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/* 172 */     super.unloaded(role);
/* 173 */     this.prioritiser.unloaded(role);
/* 174 */     this.collector.unloaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/* 179 */     super.removed(role);
/* 180 */     this.prioritiser.removed(role);
/* 181 */     this.collector.removed(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/* 186 */     super.teleported(role, from, to);
/* 187 */     this.prioritiser.teleported(role, from, to);
/* 188 */     this.collector.teleported(role, from, to);
/*     */   }
/*     */   
/*     */   protected void initialisePrioritiser() {
/* 192 */     this.npcPrioritiser = isGetNPCs() ? this.prioritiser.getNPCPrioritiser() : null;
/* 193 */     this.playerPrioritiser = isGetPlayers() ? this.prioritiser.getPlayerPrioritiser() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isExcludingOwnType() {
/* 201 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Ref<EntityStore> filterLockedEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d position, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 206 */     Ref<EntityStore> target = (this.lockedTargetSlot >= 0) ? role.getMarkedEntitySupport().getMarkedEntityRef(this.lockedTargetSlot) : null;
/* 207 */     if (target == null) return null;
/*     */     
/* 209 */     if (filterEntityWithRange(ref, target, position, role, store)) return target;
/*     */ 
/*     */     
/* 212 */     if (this.autoUnlockTarget) {
/* 213 */       role.getMarkedEntitySupport().clearMarkedEntity(this.lockedTargetSlot);
/*     */     }
/* 215 */     return null;
/*     */   }
/*     */   
/*     */   protected boolean filterEntityWithRange(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Vector3d position, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 219 */     Player playerComponent = (Player)store.getComponent(targetRef, Player.getComponentType());
/* 220 */     if (playerComponent != null) {
/* 221 */       if (!isGetPlayers()) {
/* 222 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 226 */       GameMode gameMode = playerComponent.getGameMode();
/* 227 */       if (gameMode == GameMode.Creative) {
/* 228 */         PlayerSettings playerSettingsComponent = (PlayerSettings)store.getComponent(targetRef, PlayerSettings.getComponentType());
/* 229 */         boolean allowDetection = (playerSettingsComponent != null && playerSettingsComponent.creativeSettings().allowNPCDetection());
/* 230 */         if (!allowDetection) {
/* 231 */           return false;
/*     */         }
/*     */       } 
/* 234 */     } else if (store.getArchetype(targetRef).contains(NPC_COMPONENT_TYPE)) {
/* 235 */       if (!isGetNPCs()) return false; 
/*     */     } else {
/* 237 */       return false;
/*     */     } 
/*     */     
/* 240 */     TransformComponent targetTransformComponent = (TransformComponent)store.getComponent(targetRef, TRANSFORM_COMPONENT_TYPE);
/* 241 */     assert targetTransformComponent != null;
/*     */     
/* 243 */     Vector3d pos = targetTransformComponent.getPosition();
/* 244 */     double squaredDistance = role.getActiveMotionController().getSquaredDistance(position, pos, this.useProjectedDistance);
/* 245 */     if (squaredDistance < this.minRange * this.minRange || squaredDistance > this.range * this.range) {
/* 246 */       return false;
/*     */     }
/* 248 */     return filterEntity(ref, targetRef, role, store);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean filterEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 253 */     if (store.getArchetype(targetRef).contains(DEATH_COMPONENT_TYPE)) {
/* 254 */       return false;
/*     */     }
/*     */     
/* 257 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(targetRef, NPC_COMPONENT_TYPE);
/* 258 */     if (isExcludingOwnType() && npcComponent != null && this.ownRole == npcComponent.getRoleIndex()) {
/* 259 */       return false;
/*     */     }
/*     */     
/* 262 */     return matchesFilters(ref, targetRef, role, store);
/*     */   }
/*     */   
/*     */   protected boolean filterPrioritisedPlayer(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 266 */     return filterPrioritisedEntity(ref, targetRef, role, store, this.playerPrioritiser);
/*     */   }
/*     */   
/*     */   protected boolean filterPrioritisedNPC(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 270 */     return filterPrioritisedEntity(ref, targetRef, role, store, this.npcPrioritiser);
/*     */   }
/*     */   
/*     */   protected boolean filterPrioritisedEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store, @Nonnull IEntityByPriorityFilter playerPrioritiser) {
/* 274 */     if (!filterEntity(ref, targetRef, role, store)) {
/* 275 */       this.collector.collectNonMatching(targetRef, (ComponentAccessor)store);
/* 276 */       return false;
/*     */     } 
/*     */     
/* 279 */     boolean match = playerPrioritiser.test(ref, targetRef, store);
/* 280 */     if (match) {
/* 281 */       this.collector.collectMatching(ref, targetRef, (ComponentAccessor)store);
/*     */     } else {
/* 283 */       this.collector.collectNonMatching(targetRef, (ComponentAccessor)store);
/*     */     } 
/* 285 */     return (this.collector.terminateOnFirstMatch() && match);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Ref<EntityStore> findPlayerOrEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Vector3d position, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
/* 290 */     Ref<EntityStore> target, player = null;
/* 291 */     Ref<EntityStore> npc = null;
/*     */     
/* 293 */     Ref<EntityStore> ignoredEntity = (this.ignoredTargetSlot >= 0) ? role.getMarkedEntitySupport().getMarkedEntityRef(this.ignoredTargetSlot) : null;
/*     */     
/* 295 */     if (isGetPlayers()) {
/* 296 */       this.playerPrioritiser.init(role);
/* 297 */       role.getPositionCache().processPlayersInRange(ref, this.minRange, this.range, this.useProjectedDistance, ignoredEntity, role, (sensorEntityBase, targetRef, role1, ref1) -> sensorEntityBase.filterPrioritisedPlayer(ref1, targetRef, role1, ref1.getStore()), this, ref, (ComponentAccessor)store);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 304 */       player = this.playerPrioritiser.getHighestPriorityTarget();
/* 305 */       this.playerPrioritiser.cleanup();
/*     */     } 
/*     */     
/* 308 */     if (isGetNPCs()) {
/* 309 */       this.npcPrioritiser.init(role);
/* 310 */       role.getPositionCache().processNPCsInRange(ref, this.minRange, this.range, this.useProjectedDistance, ignoredEntity, role, (sensorEntityBase, targetRef, role1, ref1) -> sensorEntityBase.filterPrioritisedNPC(ref1, targetRef, role1, ref1.getStore()), this, ref, (ComponentAccessor)store);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 317 */       npc = this.npcPrioritiser.getHighestPriorityTarget();
/* 318 */       this.npcPrioritiser.cleanup();
/*     */     } 
/*     */ 
/*     */     
/* 322 */     if (npc == null) {
/* 323 */       target = player;
/* 324 */     } else if (player == null) {
/* 325 */       target = npc;
/*     */     } else {
/* 327 */       target = this.prioritiser.pickTarget(ref, role, position, player, npc, this.useProjectedDistance, store);
/*     */     } 
/* 329 */     return target;
/*     */   }
/*     */   
/*     */   protected abstract boolean isGetPlayers();
/*     */   
/*     */   protected abstract boolean isGetNPCs();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\SensorEntityBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */