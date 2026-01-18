/*     */ package com.hypixel.hytale.builtin.deployables.system;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableComponent;
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableOwnerComponent;
/*     */ import com.hypixel.hytale.builtin.deployables.config.DeployableConfig;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelParticle;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
/*     */ import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class DeployablesSystem
/*     */ {
/*     */   private static void spawnParticleEffect(Ref<EntityStore> sourceRef, CommandBuffer<EntityStore> commandBuffer, Vector3d position, ModelParticle particle) {
/*  36 */     Vector3f particlePositionOffset = particle.getPositionOffset();
/*  37 */     Direction particleRotationOffset = particle.getRotationOffset();
/*  38 */     Vector3d particlePosition = new Vector3d(position.x, position.y, position.z);
/*  39 */     Vector3f particleRotation = new Vector3f(0.0F, 0.0F, 0.0F);
/*     */ 
/*     */     
/*  42 */     if (particlePositionOffset != null) {
/*  43 */       particlePosition.add(particlePositionOffset.x, particlePositionOffset.y, particlePositionOffset.z);
/*     */     }
/*     */ 
/*     */     
/*  47 */     if (particleRotationOffset != null) {
/*  48 */       particleRotation = new Vector3f(particleRotationOffset.yaw, particleRotationOffset.pitch, particleRotationOffset.roll);
/*     */     }
/*     */     
/*  51 */     SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource = (SpatialResource<Ref<EntityStore>, EntityStore>)commandBuffer.getResource(EntityModule.get().getPlayerSpatialResourceType());
/*  52 */     ObjectList<Ref<EntityStore>> results = SpatialResource.getThreadLocalReferenceList();
/*  53 */     playerSpatialResource.getSpatialStructure().collect(particlePosition, 75.0D, (List)results);
/*     */     
/*  55 */     ParticleUtil.spawnParticleEffect(particle.getSystemId(), particlePosition.x, particlePosition.y, particlePosition.z, particleRotation.x, particleRotation.y, particleRotation.z, sourceRef, (List)results, (ComponentAccessor)commandBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DeployableTicker
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     public Query<EntityStore> getQuery() {
/*  65 */       return (Query<EntityStore>)Query.and(new Query[] { (Query)DeployableComponent.getComponentType() });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, ArchetypeChunk<EntityStore> archetypeChunk, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer) {
/*  71 */       DeployableComponent comp = (DeployableComponent)archetypeChunk.getComponent(index, DeployableComponent.getComponentType());
/*  72 */       comp.tick(dt, index, archetypeChunk, store, commandBuffer);
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
/*     */   
/*     */   public static class DeployableRegisterer
/*     */     extends RefSystem<EntityStore>
/*     */   {
/*     */     private static void deregisterOwner(@Nonnull Ref<EntityStore> ref, @Nonnull DeployableComponent deployableComponent, @Nonnull DeployableConfig deployableConfig) {
/*  89 */       Ref<EntityStore> ownerRef = deployableComponent.getOwner();
/*     */ 
/*     */       
/*  92 */       if (ownerRef != null && ownerRef.isValid()) {
/*  93 */         DeployableOwnerComponent deployableOwnerComponent = (DeployableOwnerComponent)ownerRef.getStore().getComponent(ownerRef, DeployableOwnerComponent.getComponentType());
/*  94 */         deployableOwnerComponent.deRegisterDeployable(deployableConfig.getId(), ref);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 100 */       return (Query<EntityStore>)Query.and(new Query[] {
/* 101 */             (Query)DeployableComponent.getComponentType()
/*     */           });
/*     */     }
/*     */     
/*     */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 106 */       DeployableComponent deployableComponent = (DeployableComponent)store.getComponent(ref, DeployableComponent.getComponentType());
/* 107 */       DeployableConfig deployableConfig = deployableComponent.getConfig();
/* 108 */       Vector3d position = ((TransformComponent)store.getComponent(ref, TransformComponent.getComponentType())).getPosition();
/* 109 */       Ref<EntityStore> ownerRef = deployableComponent.getOwner();
/* 110 */       int soundIndex = deployableConfig.getDeploySoundEventIndex();
/*     */       
/* 112 */       SoundUtil.playSoundEvent3d(null, soundIndex, position, (ComponentAccessor)commandBuffer);
/*     */ 
/*     */       
/* 115 */       ModelParticle[] particles = deployableConfig.getSpawnParticles();
/* 116 */       if (particles != null) {
/* 117 */         for (ModelParticle particle : particles) {
/* 118 */           DeployablesSystem.spawnParticleEffect(ref, commandBuffer, position, particle);
/*     */         }
/*     */       }
/*     */       
/* 122 */       if (!ownerRef.isValid())
/*     */         return; 
/* 124 */       DeployableOwnerComponent deployableOwnerComponent = (DeployableOwnerComponent)ownerRef.getStore().getComponent(ownerRef, DeployableOwnerComponent.getComponentType());
/* 125 */       assert deployableOwnerComponent != null;
/* 126 */       deployableOwnerComponent.registerDeployable(ownerRef, deployableComponent, deployableConfig.getId(), ref, store);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 131 */       DeployableComponent deployableComponent = (DeployableComponent)store.getComponent(ref, DeployableComponent.getComponentType());
/* 132 */       DeployableConfig deployableConfig = deployableComponent.getConfig();
/* 133 */       Vector3d position = ((TransformComponent)store.getComponent(ref, TransformComponent.getComponentType())).getPosition();
/*     */ 
/*     */       
/* 136 */       int despawnSoundIndex = deployableConfig.getDespawnSoundEventIndex();
/* 137 */       int dieSoundIndex = deployableConfig.getDieSoundEventIndex();
/*     */ 
/*     */       
/* 140 */       if (dieSoundIndex != 0) {
/* 141 */         EntityStatMap statMap = (EntityStatMap)store.getComponent(ref, EntityStatMap.getComponentType());
/* 142 */         if (statMap != null) {
/* 143 */           EntityStatValue healthStat = statMap.get(DefaultEntityStatTypes.getHealth());
/* 144 */           int removeSound = (healthStat != null && healthStat.get() <= 0.0F) ? dieSoundIndex : despawnSoundIndex;
/* 145 */           SoundUtil.playSoundEvent3d(null, removeSound, position, (ComponentAccessor)commandBuffer);
/*     */         } 
/*     */       } else {
/* 148 */         SoundUtil.playSoundEvent3d(null, despawnSoundIndex, position, (ComponentAccessor)commandBuffer);
/*     */       } 
/*     */ 
/*     */       
/* 152 */       ModelParticle[] particles = deployableConfig.getDespawnParticles();
/* 153 */       if (particles != null) {
/* 154 */         for (ModelParticle particle : particles) {
/* 155 */           DeployablesSystem.spawnParticleEffect(ref, commandBuffer, position, particle);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/* 160 */       deregisterOwner(ref, deployableComponent, deployableConfig);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DeployableOwnerTicker
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     public Query<EntityStore> getQuery() {
/* 168 */       return (Query<EntityStore>)Query.and(new Query[] { (Query)DeployableOwnerComponent.getComponentType() });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, ArchetypeChunk<EntityStore> archetypeChunk, Store<EntityStore> store, CommandBuffer<EntityStore> commandBuffer) {
/* 174 */       DeployableOwnerComponent deployableOwnerComponent = (DeployableOwnerComponent)archetypeChunk.getComponent(index, DeployableOwnerComponent.getComponentType());
/* 175 */       deployableOwnerComponent.tick(commandBuffer);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\deployables\system\DeployablesSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */