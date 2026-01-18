/*     */ package com.hypixel.hytale.builtin.deployables.system;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableComponent;
/*     */ import com.hypixel.hytale.builtin.deployables.component.DeployableOwnerComponent;
/*     */ import com.hypixel.hytale.builtin.deployables.config.DeployableConfig;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelParticle;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
/*     */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ public class DeployableRegisterer
/*     */   extends RefSystem<EntityStore>
/*     */ {
/*     */   private static void deregisterOwner(@Nonnull Ref<EntityStore> ref, @Nonnull DeployableComponent deployableComponent, @Nonnull DeployableConfig deployableConfig) {
/*  89 */     Ref<EntityStore> ownerRef = deployableComponent.getOwner();
/*     */ 
/*     */     
/*  92 */     if (ownerRef != null && ownerRef.isValid()) {
/*  93 */       DeployableOwnerComponent deployableOwnerComponent = (DeployableOwnerComponent)ownerRef.getStore().getComponent(ownerRef, DeployableOwnerComponent.getComponentType());
/*  94 */       deployableOwnerComponent.deRegisterDeployable(deployableConfig.getId(), ref);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Query<EntityStore> getQuery() {
/* 100 */     return (Query<EntityStore>)Query.and(new Query[] {
/* 101 */           (Query)DeployableComponent.getComponentType()
/*     */         });
/*     */   }
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 106 */     DeployableComponent deployableComponent = (DeployableComponent)store.getComponent(ref, DeployableComponent.getComponentType());
/* 107 */     DeployableConfig deployableConfig = deployableComponent.getConfig();
/* 108 */     Vector3d position = ((TransformComponent)store.getComponent(ref, TransformComponent.getComponentType())).getPosition();
/* 109 */     Ref<EntityStore> ownerRef = deployableComponent.getOwner();
/* 110 */     int soundIndex = deployableConfig.getDeploySoundEventIndex();
/*     */     
/* 112 */     SoundUtil.playSoundEvent3d(null, soundIndex, position, (ComponentAccessor)commandBuffer);
/*     */ 
/*     */     
/* 115 */     ModelParticle[] particles = deployableConfig.getSpawnParticles();
/* 116 */     if (particles != null) {
/* 117 */       for (ModelParticle particle : particles) {
/* 118 */         DeployablesSystem.spawnParticleEffect(ref, commandBuffer, position, particle);
/*     */       }
/*     */     }
/*     */     
/* 122 */     if (!ownerRef.isValid())
/*     */       return; 
/* 124 */     DeployableOwnerComponent deployableOwnerComponent = (DeployableOwnerComponent)ownerRef.getStore().getComponent(ownerRef, DeployableOwnerComponent.getComponentType());
/* 125 */     assert deployableOwnerComponent != null;
/* 126 */     deployableOwnerComponent.registerDeployable(ownerRef, deployableComponent, deployableConfig.getId(), ref, store);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 131 */     DeployableComponent deployableComponent = (DeployableComponent)store.getComponent(ref, DeployableComponent.getComponentType());
/* 132 */     DeployableConfig deployableConfig = deployableComponent.getConfig();
/* 133 */     Vector3d position = ((TransformComponent)store.getComponent(ref, TransformComponent.getComponentType())).getPosition();
/*     */ 
/*     */     
/* 136 */     int despawnSoundIndex = deployableConfig.getDespawnSoundEventIndex();
/* 137 */     int dieSoundIndex = deployableConfig.getDieSoundEventIndex();
/*     */ 
/*     */     
/* 140 */     if (dieSoundIndex != 0) {
/* 141 */       EntityStatMap statMap = (EntityStatMap)store.getComponent(ref, EntityStatMap.getComponentType());
/* 142 */       if (statMap != null) {
/* 143 */         EntityStatValue healthStat = statMap.get(DefaultEntityStatTypes.getHealth());
/* 144 */         int removeSound = (healthStat != null && healthStat.get() <= 0.0F) ? dieSoundIndex : despawnSoundIndex;
/* 145 */         SoundUtil.playSoundEvent3d(null, removeSound, position, (ComponentAccessor)commandBuffer);
/*     */       } 
/*     */     } else {
/* 148 */       SoundUtil.playSoundEvent3d(null, despawnSoundIndex, position, (ComponentAccessor)commandBuffer);
/*     */     } 
/*     */ 
/*     */     
/* 152 */     ModelParticle[] particles = deployableConfig.getDespawnParticles();
/* 153 */     if (particles != null) {
/* 154 */       for (ModelParticle particle : particles) {
/* 155 */         DeployablesSystem.spawnParticleEffect(ref, commandBuffer, position, particle);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 160 */     deregisterOwner(ref, deployableComponent, deployableConfig);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\deployables\system\DeployablesSystem$DeployableRegisterer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */