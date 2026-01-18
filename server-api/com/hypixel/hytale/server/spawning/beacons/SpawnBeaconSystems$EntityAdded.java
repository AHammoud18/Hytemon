/*     */ package com.hypixel.hytale.server.spawning.beacons;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.math.random.RandomExtra;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabCopyableComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.BeaconNPCSpawn;
/*     */ import com.hypixel.hytale.server.spawning.util.FloodFillPositionSelector;
/*     */ import com.hypixel.hytale.server.spawning.wrappers.BeaconSpawnWrapper;
/*     */ import java.util.logging.Level;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAdded
/*     */   extends RefSystem<EntityStore>
/*     */ {
/*     */   private final ComponentType<EntityStore, SpawnBeacon> componentType;
/*     */   
/*     */   public EntityAdded(ComponentType<EntityStore, SpawnBeacon> componentType) {
/* 123 */     this.componentType = componentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public Query<EntityStore> getQuery() {
/* 128 */     return (Query)this.componentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 133 */     SpawnBeacon spawnBeaconComponent = (SpawnBeacon)store.getComponent(ref, this.componentType);
/* 134 */     assert spawnBeaconComponent != null;
/*     */     
/* 136 */     String config = spawnBeaconComponent.getSpawnConfigId();
/* 137 */     int index = BeaconNPCSpawn.getAssetMap().getIndex(config);
/* 138 */     if (index == Integer.MIN_VALUE) {
/*     */       
/* 140 */       SpawnBeaconSystems.LOGGER.at(Level.SEVERE).log("Beacon %s removed due to missing spawn beacon type: %s", ref, config);
/* 141 */       commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/* 145 */     BeaconSpawnWrapper spawnWrapper = SpawningPlugin.get().getBeaconSpawnWrapper(index);
/* 146 */     spawnBeaconComponent.setSpawnWrapper(spawnWrapper);
/*     */     
/* 148 */     FloodFillPositionSelector positionSelector = new FloodFillPositionSelector(((EntityStore)store.getExternalData()).getWorld(), spawnWrapper);
/* 149 */     positionSelector.setCalculatePositionsAfter(RandomExtra.randomRange(SpawnBeaconSystems.POSITION_CALCULATION_DELAY_RANGE[0], SpawnBeaconSystems.POSITION_CALCULATION_DELAY_RANGE[1]));
/* 150 */     commandBuffer.putComponent(ref, FloodFillPositionSelector.getComponentType(), (Component)positionSelector);
/* 151 */     commandBuffer.ensureComponent(ref, PrefabCopyableComponent.getComponentType());
/*     */   }
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\beacons\SpawnBeaconSystems$EntityAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */