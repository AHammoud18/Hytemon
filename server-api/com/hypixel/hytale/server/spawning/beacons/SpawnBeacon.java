/*     */ package com.hypixel.hytale.server.spawning.beacons;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.entity.Entity;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.config.FlockAsset;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.systems.NewSpawnStartTickingSystem;
/*     */ import com.hypixel.hytale.server.spawning.ISpawnableWithModel;
/*     */ import com.hypixel.hytale.server.spawning.SpawningContext;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.BeaconNPCSpawn;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.RoleSpawnParameters;
/*     */ import com.hypixel.hytale.server.spawning.util.FloodFillPositionSelector;
/*     */ import com.hypixel.hytale.server.spawning.wrappers.BeaconSpawnWrapper;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class SpawnBeacon
/*     */   extends Entity
/*     */ {
/*     */   public static final BuilderCodec<SpawnBeacon> CODEC;
/*     */   private BeaconSpawnWrapper spawnWrapper;
/*     */   private String spawnConfigId;
/*     */   
/*     */   static {
/*  49 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(SpawnBeacon.class, SpawnBeacon::new, Entity.CODEC).append(new KeyedCodec("SpawnConfiguration", (Codec)Codec.STRING), (spawnBeacon, s) -> spawnBeacon.spawnConfigId = s, spawnBeacon -> spawnBeacon.spawnConfigId).add()).build();
/*     */   }
/*     */   @Nullable
/*     */   public static ComponentType<EntityStore, SpawnBeacon> getComponentType() {
/*  53 */     return EntityModule.get().getComponentType(SpawnBeacon.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private final IntSet unspawnableRoles = (IntSet)new IntOpenHashSet();
/*  60 */   private final SpawningContext spawningContext = new SpawningContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnBeacon(World world) {
/*  67 */     super(world);
/*     */   }
/*     */   
/*     */   public BeaconSpawnWrapper getSpawnWrapper() {
/*  71 */     return this.spawnWrapper;
/*     */   }
/*     */   
/*     */   public void setSpawnWrapper(@Nonnull BeaconSpawnWrapper spawnWrapper) {
/*  75 */     this.spawnWrapper = spawnWrapper;
/*  76 */     this.spawnConfigId = ((BeaconNPCSpawn)spawnWrapper.getSpawn()).getId();
/*     */   }
/*     */   
/*     */   public String getSpawnConfigId() {
/*  80 */     return this.spawnConfigId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHiddenFromLivingEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  85 */     Player targetPlayerComponent = (Player)componentAccessor.getComponent(targetRef, Player.getComponentType());
/*  86 */     return (targetPlayerComponent == null || targetPlayerComponent.getGameMode() != GameMode.Creative);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCollidable() {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveTo(@Nonnull Ref<EntityStore> ref, double locX, double locY, double locZ, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  96 */     super.moveTo(ref, locX, locY, locZ, componentAccessor);
/*     */ 
/*     */     
/*  99 */     FloodFillPositionSelector positionSelectorComponent = (FloodFillPositionSelector)componentAccessor.getComponent(ref, FloodFillPositionSelector.getComponentType());
/* 100 */     assert positionSelectorComponent != null;
/*     */     
/* 102 */     positionSelectorComponent.setCalculatePositionsAfter(SpawnBeaconSystems.POSITION_CALCULATION_DELAY_RANGE[1]);
/* 103 */     positionSelectorComponent.forceRebuildCache();
/* 104 */     this.unspawnableRoles.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean manualTrigger(@Nonnull Ref<EntityStore> ref, @Nonnull FloodFillPositionSelector positionSelector, @Nonnull Ref<EntityStore> targetRef, @Nonnull Store<EntityStore> store) {
/* 113 */     int concurrentSpawns = RandomExtra.randomRange(((BeaconNPCSpawn)this.spawnWrapper.getSpawn()).getConcurrentSpawnsRange());
/* 114 */     int spawnedCount = 0;
/* 115 */     for (int i = 0; i < concurrentSpawns; i++) {
/* 116 */       RoleSpawnParameters roleSpawnParameters = this.spawnWrapper.pickRole(ThreadLocalRandom.current());
/* 117 */       int roleIndex = NPCPlugin.get().getIndex(roleSpawnParameters.getId());
/* 118 */       if (!this.unspawnableRoles.contains(roleIndex)) {
/*     */         
/* 120 */         ISpawnableWithModel spawnable = (ISpawnableWithModel)NPCPlugin.get().tryGetCachedValidRole(roleIndex);
/* 121 */         this.spawningContext.setSpawnable(spawnable);
/*     */         
/* 123 */         if (!positionSelector.hasPositionsForRole(roleIndex)) {
/* 124 */           markUnspawnable(ref, roleIndex, (ComponentAccessor<EntityStore>)store);
/* 125 */           this.spawningContext.releaseFull();
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 130 */           Vector3d targetPos = ((TransformComponent)targetRef.getStore().getComponent(targetRef, TransformComponent.getComponentType())).getPosition();
/* 131 */           if (!positionSelector.prepareSpawnContext(targetPos, concurrentSpawns, roleIndex, this.spawningContext, this.spawnWrapper)) {
/* 132 */             this.spawningContext.releaseFull();
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 137 */             Vector3d position = this.spawningContext.newPosition();
/* 138 */             Vector3f rotation = this.spawningContext.newRotation();
/* 139 */             FlockAsset flockDefinition = roleSpawnParameters.getFlockDefinition();
/* 140 */             int flockSize = (flockDefinition != null) ? flockDefinition.pickFlockSize() : 1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     return (spawnedCount != 0);
/*     */   }
/*     */   
/*     */   protected void markUnspawnable(Ref<EntityStore> ref, int index, ComponentAccessor<EntityStore> componentAccessor) {
/* 163 */     this.unspawnableRoles.add(index);
/* 164 */     if (this.unspawnableRoles.size() >= this.spawnWrapper.getRoles().size()) {
/* 165 */       UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(ref, UUIDComponent.getComponentType());
/* 166 */       assert uuidComponent != null;
/*     */       
/* 168 */       LOGGER.at(Level.WARNING).log("Removed spawn beacon %s due to being unable to spawn any NPC types", uuidComponent.getUuid());
/* 169 */       remove();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void postSpawn(@Nonnull NPCEntity npc, @Nonnull Ref<EntityStore> selfRef, @Nonnull BeaconNPCSpawn spawn, Ref<EntityStore> targetRef, ComponentAccessor<EntityStore> componentAccessor) {
/* 174 */     Role role = npc.getRole();
/* 175 */     role.getMarkedEntitySupport().setMarkedEntity(spawn.getTargetSlot(), targetRef);
/*     */     
/* 177 */     String spawnState = spawn.getNpcSpawnState();
/* 178 */     if (spawnState != null) {
/* 179 */       role.getStateSupport().setState(selfRef, spawnState, spawn.getNpcSpawnSubState(), componentAccessor);
/*     */     }
/* 181 */     NewSpawnStartTickingSystem.queueNewSpawn(selfRef, selfRef.getStore());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 187 */     return "SpawnBeacon{spawnConfigId='" + this.spawnConfigId + "'} " + super
/*     */       
/* 189 */       .toString();
/*     */   }
/*     */   
/*     */   public SpawnBeacon() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\beacons\SpawnBeacon.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */