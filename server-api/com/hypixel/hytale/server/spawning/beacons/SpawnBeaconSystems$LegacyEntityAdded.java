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
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.asset.type.responsecurve.ScaledXYResponseCurve;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabCopyableComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*     */ import com.hypixel.hytale.server.spawning.assets.spawns.config.BeaconNPCSpawn;
/*     */ import com.hypixel.hytale.server.spawning.controllers.BeaconSpawnController;
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
/*     */ public class LegacyEntityAdded
/*     */   extends RefSystem<EntityStore>
/*     */ {
/*     */   private final ComponentType<EntityStore, LegacySpawnBeaconEntity> componentType;
/*     */   
/*     */   public LegacyEntityAdded(ComponentType<EntityStore, LegacySpawnBeaconEntity> componentType) {
/*  62 */     this.componentType = componentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public Query<EntityStore> getQuery() {
/*  67 */     return (Query)this.componentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  72 */     LegacySpawnBeaconEntity legacySpawnBeaconComponent = (LegacySpawnBeaconEntity)store.getComponent(ref, this.componentType);
/*  73 */     assert legacySpawnBeaconComponent != null;
/*     */     
/*  75 */     String spawnConfigId = legacySpawnBeaconComponent.getSpawnConfigId();
/*  76 */     int index = BeaconNPCSpawn.getAssetMap().getIndex(spawnConfigId);
/*  77 */     if (index == Integer.MIN_VALUE) {
/*     */       
/*  79 */       SpawnBeaconSystems.LOGGER.at(Level.SEVERE).log("Beacon %s removed due to missing spawn beacon type: %s", ref, spawnConfigId);
/*  80 */       commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */       return;
/*     */     } 
/*  83 */     legacySpawnBeaconComponent.setSpawnWrapper(SpawningPlugin.get().getBeaconSpawnWrapper(index));
/*     */     
/*  85 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/*  87 */     BeaconSpawnController spawnController = new BeaconSpawnController(world, ref);
/*  88 */     legacySpawnBeaconComponent.setSpawnController(spawnController);
/*     */     
/*  90 */     BeaconSpawnWrapper spawnWrapper = legacySpawnBeaconComponent.getSpawnWrapper();
/*  91 */     if (spawnWrapper != null) {
/*  92 */       spawnController.initialise(spawnWrapper);
/*  93 */       FloodFillPositionSelector positionSelector = new FloodFillPositionSelector(world, spawnWrapper);
/*  94 */       positionSelector.setCalculatePositionsAfter(RandomExtra.randomRange(SpawnBeaconSystems.POSITION_CALCULATION_DELAY_RANGE[0], SpawnBeaconSystems.POSITION_CALCULATION_DELAY_RANGE[1]));
/*  95 */       commandBuffer.putComponent(ref, FloodFillPositionSelector.getComponentType(), (Component)positionSelector);
/*     */       
/*  97 */       ScaledXYResponseCurve maxSpawnScaleCurve = ((BeaconNPCSpawn)spawnWrapper.getSpawn()).getMaxSpawnsScalingCurve();
/*  98 */       int baseMaxTotalSpawns = spawnController.getBaseMaxTotalSpawns();
/*     */       
/* 100 */       int currentScaledMaxTotalSpawns = (maxSpawnScaleCurve != null) ? (baseMaxTotalSpawns + MathUtil.floor(maxSpawnScaleCurve.computeY(legacySpawnBeaconComponent.getLastPlayerCount()) + 0.25D)) : baseMaxTotalSpawns;
/* 101 */       spawnController.setCurrentScaledMaxTotalSpawns(currentScaledMaxTotalSpawns);
/*     */     } 
/*     */     
/* 104 */     if (reason == AddReason.LOAD) {
/*     */       
/* 106 */       InitialBeaconDelay delay = new InitialBeaconDelay();
/* 107 */       delay.setLoadTimeSpawnDelay(15.0D);
/* 108 */       commandBuffer.putComponent(ref, InitialBeaconDelay.getComponentType(), delay);
/*     */     } 
/*     */     
/* 111 */     commandBuffer.ensureComponent(ref, PrefabCopyableComponent.getComponentType());
/*     */   }
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\beacons\SpawnBeaconSystems$LegacyEntityAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */