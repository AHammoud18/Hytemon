/*    */ package com.hypixel.hytale.builtin.portals.systems.voidevent;
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.VoidSpawner;
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.config.InvasionPortalConfig;
/*    */ import com.hypixel.hytale.builtin.portals.components.voidevent.config.VoidEventConfig;
/*    */ import com.hypixel.hytale.builtin.portals.integrations.PortalGameplayConfig;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.assets.spawns.config.BeaconNPCSpawn;
/*    */ import com.hypixel.hytale.server.spawning.wrappers.BeaconSpawnWrapper;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public final class VoidSpawnerSystems {
/* 29 */   private static final Query<EntityStore> QUERY = (Query<EntityStore>)Query.and(new Query[] { (Query)VoidSpawner.getComponentType(), (Query)TransformComponent.getComponentType() });
/*    */   
/*    */   public static class Instantiate
/*    */     extends RefSystem<EntityStore> {
/*    */     public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 34 */       HytaleLogger.getLogger().at(Level.INFO).log("Adding void spawner...");
/* 35 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */       
/* 37 */       PortalGameplayConfig gameplayConfig = (PortalGameplayConfig)world.getGameplayConfig().getPluginConfig().get(PortalGameplayConfig.class);
/* 38 */       VoidEventConfig voidEventConfig = gameplayConfig.getVoidEvent();
/* 39 */       InvasionPortalConfig invasionPortalConfig = voidEventConfig.getInvasionPortalConfig();
/* 40 */       List<String> spawnBeacons = invasionPortalConfig.getSpawnBeaconsList();
/* 41 */       if (spawnBeacons.isEmpty()) {
/* 42 */         HytaleLogger.getLogger().at(Level.WARNING).log("No spawn beacons configured for void spawn in GameplayConfig for portal world (no mobs will spawn during void event)");
/*    */         
/*    */         return;
/*    */       } 
/* 46 */       VoidSpawner voidSpawner = (VoidSpawner)commandBuffer.getComponent(ref, VoidSpawner.getComponentType());
/* 47 */       TransformComponent transform = (TransformComponent)commandBuffer.getComponent(ref, TransformComponent.getComponentType());
/*    */       
/* 49 */       Vector3d position = transform.getPosition();
/* 50 */       for (int i = 0; i < spawnBeacons.size(); i++) {
/* 51 */         String spawnBeacon = spawnBeacons.get(i);
/*    */         
/* 53 */         Vector3d beaconPos = position.clone().add(0.0D, 0.5D + 0.1D * i, 0.0D);
/*    */         
/* 55 */         int beaconAssetId = BeaconNPCSpawn.getAssetMap().getIndexOrDefault(spawnBeacon, -1);
/* 56 */         if (beaconAssetId == -1) {
/* 57 */           HytaleLogger.getLogger().at(Level.WARNING).log("No asset found for spawn beacon \"" + spawnBeacon + "\" in GameplayConfig for portal world");
/*    */         } else {
/*    */           
/* 60 */           BeaconSpawnWrapper beaconSpawnWrapper = SpawningPlugin.get().getBeaconSpawnWrapper(beaconAssetId);
/*    */           
/* 62 */           Holder<EntityStore> spawnBeaconRef = LegacySpawnBeaconEntity.createHolder(beaconSpawnWrapper, beaconPos, transform.getRotation());
/* 63 */           commandBuffer.addEntity(spawnBeaconRef, AddReason.SPAWN);
/*    */           
/* 65 */           UUID beaconUuid = ((UUIDComponent)spawnBeaconRef.getComponent(UUIDComponent.getComponentType())).getUuid();
/* 66 */           voidSpawner.getSpawnBeaconUuids().add(beaconUuid);
/*    */         } 
/*    */       } 
/* 69 */       String onSpawnParticles = invasionPortalConfig.getOnSpawnParticles();
/* 70 */       if (onSpawnParticles != null) {
/* 71 */         ParticleUtil.spawnParticleEffect(onSpawnParticles, position, (ComponentAccessor)commandBuffer);
/*    */       }
/*    */     }
/*    */ 
/*    */     
/*    */     public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 77 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/* 78 */       VoidSpawner voidSpawnerComponent = (VoidSpawner)commandBuffer.getComponent(ref, VoidSpawner.getComponentType());
/* 79 */       assert voidSpawnerComponent != null;
/*    */       
/* 81 */       for (UUID spawnBeaconUuid : voidSpawnerComponent.getSpawnBeaconUuids()) {
/* 82 */         Ref<EntityStore> spawnBeaconRef = world.getEntityStore().getRefFromUUID(spawnBeaconUuid);
/* 83 */         if (spawnBeaconRef != null) {
/* 84 */           commandBuffer.removeEntity(spawnBeaconRef, RemoveReason.REMOVE);
/*    */         }
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/*    */     @Nullable
/*    */     public Query<EntityStore> getQuery() {
/* 92 */       return VoidSpawnerSystems.QUERY;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\systems\voidevent\VoidSpawnerSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */