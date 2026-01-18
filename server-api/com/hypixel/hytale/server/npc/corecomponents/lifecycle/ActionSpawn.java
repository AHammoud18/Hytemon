/*     */ package com.hypixel.hytale.server.npc.corecomponents.lifecycle;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.util.PhysicsMath;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.FlockMembershipSystems;
/*     */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*     */ import com.hypixel.hytale.server.flock.config.FlockAsset;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderActionSpawn;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionControllerFly;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.systems.NewSpawnStartTickingSystem;
/*     */ import com.hypixel.hytale.server.npc.util.AimingHelper;
/*     */ import com.hypixel.hytale.server.spawning.ISpawnableWithModel;
/*     */ import com.hypixel.hytale.server.spawning.SpawnTestResult;
/*     */ import com.hypixel.hytale.server.spawning.SpawningContext;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActionSpawn
/*     */   extends ActionBase
/*     */ {
/*     */   protected final float spawnDirection;
/*     */   protected final float spawnAngle;
/*     */   protected final boolean fanOut;
/*     */   protected final double minDistance;
/*     */   protected final double maxDistance;
/*     */   protected final String kind;
/*  49 */   protected final Vector3d position = new Vector3d(); protected final String flock; protected final int roleIndex; protected final int maxCount; protected final int minCount; protected final double minDelay; protected final double maxDelay;
/*  50 */   protected final Vector3f rotation = new Vector3f();
/*     */   
/*     */   protected final boolean launchAtTarget;
/*     */   protected final boolean pitchHigh;
/*  54 */   protected final Vector3d targetPosition = new Vector3d();
/*  55 */   protected final Vector3d launchDirection = new Vector3d();
/*     */   
/*     */   @Nullable
/*     */   protected final float[] pitch;
/*     */   
/*     */   protected final double spread;
/*     */   
/*     */   protected final boolean joinFlock;
/*     */   protected final String spawnState;
/*     */   protected final String spawnSubState;
/*     */   protected int spawnsLeft;
/*     */   protected int maxTries;
/*     */   protected float yaw0;
/*     */   protected float yawIncrement;
/*     */   protected double spawnDelay;
/*     */   protected Ref<EntityStore> parent;
/*     */   
/*     */   public ActionSpawn(@Nonnull BuilderActionSpawn builderActionSpawn, @Nonnull BuilderSupport builderSupport) {
/*  73 */     super((BuilderActionBase)builderActionSpawn);
/*  74 */     this.spawnDirection = builderActionSpawn.getSpawnDirection(builderSupport);
/*  75 */     this.spawnAngle = builderActionSpawn.getSpawnAngle(builderSupport);
/*  76 */     this.fanOut = builderActionSpawn.isFanOut(builderSupport);
/*  77 */     double[] distanceRange = builderActionSpawn.getDistanceRange(builderSupport);
/*  78 */     this.minDistance = distanceRange[0];
/*  79 */     this.maxDistance = distanceRange[1];
/*  80 */     int[] countRange = builderActionSpawn.getCountRange(builderSupport);
/*  81 */     this.minCount = countRange[0];
/*  82 */     this.maxCount = countRange[1];
/*  83 */     double[] delayRange = builderActionSpawn.getDelayRange(builderSupport);
/*  84 */     this.minDelay = delayRange[0];
/*  85 */     this.maxDelay = delayRange[1];
/*  86 */     this.kind = builderActionSpawn.getKind(builderSupport);
/*  87 */     this.flock = builderActionSpawn.getFlock(builderSupport);
/*  88 */     this.roleIndex = NPCPlugin.get().getIndex(this.kind);
/*  89 */     this.launchAtTarget = builderActionSpawn.isLaunchAtTarget(builderSupport);
/*  90 */     this.pitchHigh = builderActionSpawn.isPitchHigh(builderSupport);
/*  91 */     this.spread = builderActionSpawn.getSpread(builderSupport);
/*  92 */     this.joinFlock = builderActionSpawn.isJoinFlock(builderSupport);
/*  93 */     this.pitch = this.launchAtTarget ? new float[2] : null;
/*  94 */     this.spawnState = builderActionSpawn.getSpawnState(builderSupport);
/*  95 */     this.spawnSubState = builderActionSpawn.getSpawnSubState(builderSupport);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 100 */     if (!super.canExecute(ref, role, sensorInfo, dt, store) || this.roleIndex < 0 || this.spawnsLeft > 0) {
/* 101 */       return false;
/*     */     }
/* 103 */     if (NPCPlugin.get().tryGetCachedValidRole(this.roleIndex) == null) {
/* 104 */       NPCPlugin.get().getLogger().at(Level.SEVERE).log("NPC of type '%s': Unable to spawn NPC of type '%s' from Action Spawn", role.getRoleName(), this.kind);
/* 105 */       setOnce();
/* 106 */       this.once = true;
/* 107 */       return false;
/*     */     } 
/* 109 */     if (this.launchAtTarget) return (sensorInfo != null && sensorInfo.hasPosition()); 
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 115 */     super.execute(ref, role, sensorInfo, dt, store);
/*     */     
/* 117 */     this.spawnsLeft = RandomExtra.randomRange(this.minCount, this.maxCount);
/* 118 */     if (this.spawnsLeft == 0) {
/* 119 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 123 */     this.maxTries = this.spawnsLeft * 5;
/*     */     
/* 125 */     if (this.launchAtTarget) {
/* 126 */       sensorInfo.getPositionProvider().providePosition(this.targetPosition);
/*     */     }
/* 128 */     else if (this.fanOut) {
/* 129 */       if (this.spawnsLeft == 1) {
/* 130 */         this.yaw0 = this.spawnDirection;
/*     */       } else {
/* 132 */         this.yaw0 = this.spawnDirection - this.spawnAngle / 2.0F;
/* 133 */         this.yawIncrement = this.spawnAngle / (this.spawnsLeft - 1);
/*     */       } 
/*     */     } else {
/* 136 */       this.yaw0 = this.spawnDirection - this.spawnAngle / 2.0F;
/*     */     } 
/*     */ 
/*     */     
/* 140 */     SpawningContext spawningContext = new SpawningContext();
/* 141 */     this.parent = ref;
/* 142 */     Builder<Role> roleBuilder = NPCPlugin.get().tryGetCachedValidRole(this.roleIndex);
/*     */     
/* 144 */     if ((!roleBuilder.isSpawnable() && !(roleBuilder instanceof ISpawnableWithModel)) || !spawningContext.setSpawnable((ISpawnableWithModel)roleBuilder)) {
/* 145 */       this.spawnsLeft = 0;
/* 146 */       return true;
/*     */     } 
/*     */     
/* 149 */     while (this.spawnsLeft > 0) {
/* 150 */       if (trySpawn(ref, spawningContext, store)) {
/* 151 */         if (--this.spawnsLeft == 0) return true;
/*     */         
/* 153 */         if (this.minDelay > 0.0D || this.maxDelay > 0.0D)
/* 154 */           break;  spawningContext.newModel();
/*     */       } 
/* 156 */       if (--this.maxTries == 0) {
/* 157 */         this.spawnsLeft = 0;
/* 158 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 162 */     this.spawnDelay = RandomExtra.randomRange(this.minDelay, this.maxDelay);
/* 163 */     role.addDeferredAction(this::deferredSpawning);
/* 164 */     return true;
/*     */   }
/*     */   protected boolean trySpawn(@Nonnull Ref<EntityStore> ref, @Nonnull SpawningContext spawningContext, @Nonnull Store<EntityStore> store) {
/*     */     double x, z, y;
/* 168 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 170 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 171 */     assert transformComponent != null;
/*     */     
/* 173 */     ModelComponent modelComponent = (ModelComponent)store.getComponent(ref, ModelComponent.getComponentType());
/*     */     
/* 175 */     Vector3d position = transformComponent.getPosition();
/* 176 */     Vector3f bodyRotation = transformComponent.getRotation();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     if (this.launchAtTarget) {
/* 182 */       float eyeHeight = (modelComponent != null) ? modelComponent.getModel().getEyeHeight() : 0.0F;
/*     */       
/* 184 */       x = position.getX();
/* 185 */       z = position.getZ();
/* 186 */       y = position.getY() + eyeHeight;
/*     */     } else {
/* 188 */       double distance = RandomExtra.randomRange(this.minDistance, this.maxDistance);
/* 189 */       float yaw = bodyRotation.getYaw() + this.yaw0;
/*     */       
/* 191 */       if (!this.fanOut) yaw += RandomExtra.randomRange(0.0F, this.spawnAngle);
/*     */       
/* 193 */       x = position.getX() + PhysicsMath.headingX(yaw) * distance;
/* 194 */       z = position.getZ() + PhysicsMath.headingZ(yaw) * distance;
/* 195 */       y = position.getY();
/*     */     } 
/*     */     
/* 198 */     if (!spawningContext.set(world, x, y, z) || spawningContext.canSpawn() != SpawnTestResult.TEST_OK) {
/* 199 */       return false;
/*     */     }
/* 201 */     this.position.assign(spawningContext.xSpawn, spawningContext.ySpawn, spawningContext.zSpawn);
/* 202 */     this.rotation.assign(bodyRotation);
/*     */     
/* 204 */     FlockAsset flockDefinition = (this.flock != null) ? (FlockAsset)FlockAsset.getAssetMap().getAsset(this.flock) : null;
/* 205 */     Pair<Ref<EntityStore>, NPCEntity> npcPair = NPCPlugin.get().spawnEntity(store, this.roleIndex, this.position, this.rotation, spawningContext.getModel(), this::postSpawn);
/* 206 */     FlockPlugin.trySpawnFlock((Ref)npcPair.first(), (NPCEntity)npcPair.second(), store, this.roleIndex, this.position, this.rotation, flockDefinition, this::postSpawn);
/*     */     
/* 208 */     if (this.fanOut) this.yaw0 += this.yawIncrement; 
/* 209 */     return true;
/*     */   }
/*     */   
/*     */   protected void postSpawn(@Nonnull NPCEntity npcComponent, @Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store) {
/* 213 */     NPCEntity parentNpcComponent = (NPCEntity)store.getComponent(this.parent, NPCEntity.getComponentType());
/* 214 */     assert parentNpcComponent != null;
/*     */     
/* 216 */     joinFlock(ref, store);
/* 217 */     launchAtTarget(ref, store);
/* 218 */     if (this.spawnState != null) {
/* 219 */       npcComponent.getRole().getStateSupport().setState(ref, this.spawnState, this.spawnSubState, (ComponentAccessor)store);
/*     */     }
/* 221 */     npcComponent.setSpawnConfiguration(parentNpcComponent.getSpawnConfiguration());
/* 222 */     NewSpawnStartTickingSystem.queueNewSpawn(ref, store);
/*     */   }
/*     */   
/*     */   protected void joinFlock(@Nonnull Ref<EntityStore> targetRef, @Nonnull Store<EntityStore> store) {
/* 226 */     if (!this.joinFlock)
/*     */       return; 
/* 228 */     NPCEntity parentNpcComponent = (NPCEntity)store.getComponent(this.parent, NPCEntity.getComponentType());
/* 229 */     assert parentNpcComponent != null;
/*     */     
/* 231 */     Ref<EntityStore> flockReference = FlockPlugin.getFlockReference(this.parent, (ComponentAccessor)store);
/* 232 */     if (flockReference == null) {
/* 233 */       flockReference = FlockPlugin.createFlock(store, parentNpcComponent.getRole());
/* 234 */       FlockMembershipSystems.join(this.parent, flockReference, store);
/*     */     } 
/* 236 */     FlockMembershipSystems.join(targetRef, flockReference, store);
/*     */   }
/*     */   
/*     */   protected void launchAtTarget(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store) {
/* 240 */     if (!this.launchAtTarget)
/*     */       return; 
/* 242 */     if (this.spread > 0.0D) {
/* 243 */       this.targetPosition.add(RandomExtra.randomRange(-this.spread, this.spread), 0.0D, RandomExtra.randomRange(-this.spread, this.spread));
/*     */     }
/*     */     
/* 246 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 247 */     assert transformComponent != null;
/*     */     
/* 249 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 250 */     assert npcComponent != null;
/*     */     
/* 252 */     Role role = npcComponent.getRole();
/* 253 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 255 */     this.launchDirection.assign(this.targetPosition).subtract(position).normalize();
/* 256 */     double distance = position.distanceTo(this.targetPosition);
/* 257 */     MotionController motionController = role.getActiveMotionController(); if (motionController instanceof MotionControllerFly) { MotionControllerFly flyController = (MotionControllerFly)motionController;
/* 258 */       double endVelocity = flyController.getMinSpeedAfterForceSquared();
/* 259 */       double acceleration = -flyController.getDampingDeceleration();
/* 260 */       double v0 = Math.sqrt(endVelocity - 2.0D * acceleration * distance);
/* 261 */       this.launchDirection.scale(v0);
/* 262 */       role.forceVelocity(this.launchDirection, null, false);
/*     */       
/*     */       return; }
/*     */     
/* 266 */     double height = this.targetPosition.y - position.y;
/* 267 */     double gravity = role.getActiveMotionController().getGravity() * 5.0D;
/* 268 */     double throwSpeed = AimingHelper.ensurePossibleThrowSpeed(distance, height, gravity, 1.0D);
/*     */ 
/*     */ 
/*     */     
/* 272 */     if (!AimingHelper.computePitch(distance, height, throwSpeed, gravity, this.pitch)) {
/* 273 */       throw new IllegalStateException(String.format("Error in computing pitch with distance %s, height %s, and speed %s despite ensuring possible throw speed", new Object[] {
/* 274 */               Double.valueOf(distance), Double.valueOf(height), Double.valueOf(throwSpeed)
/*     */             }));
/*     */     }
/* 277 */     float heading = PhysicsMath.headingFromDirection(this.launchDirection.x, this.launchDirection.z);
/* 278 */     PhysicsMath.vectorFromAngles(heading, this.pitchHigh ? this.pitch[1] : this.pitch[0], this.launchDirection).normalize();
/* 279 */     this.launchDirection.scale(throwSpeed);
/* 280 */     role.forceVelocity(this.launchDirection, null, true);
/*     */   }
/*     */   
/*     */   protected boolean deferredSpawning(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 284 */     this.spawnDelay -= dt;
/* 285 */     if (this.spawnDelay > 0.0D) {
/* 286 */       return false;
/*     */     }
/* 288 */     this.spawnDelay = RandomExtra.randomRange(this.minDelay, this.maxDelay);
/*     */     
/* 290 */     SpawningContext spawningContext = new SpawningContext();
/* 291 */     Builder<Role> roleBuilder = NPCPlugin.get().tryGetCachedValidRole(this.roleIndex);
/*     */     
/* 293 */     if (!roleBuilder.isSpawnable() || !(roleBuilder instanceof ISpawnableWithModel) || !spawningContext.setSpawnable((ISpawnableWithModel)roleBuilder)) {
/* 294 */       return true;
/*     */     }
/*     */     
/*     */     while (true) {
/* 298 */       if (trySpawn(ref, spawningContext, store)) {
/* 299 */         this.spawnsLeft--;
/* 300 */         return (this.spawnsLeft == 0);
/*     */       } 
/* 302 */       this.maxTries--;
/* 303 */       if (this.maxTries <= 0) {
/* 304 */         this.spawnsLeft = 0;
/* 305 */         return true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\lifecycle\ActionSpawn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */