/*     */ package com.hypixel.hytale.builtin.adventure.npcobjectives.task;
/*     */ import com.hypixel.hytale.builtin.adventure.npcobjectives.assets.KillObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.npcobjectives.assets.KillSpawnBeaconObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.npcobjectives.resources.KillTrackerResource;
/*     */ import com.hypixel.hytale.builtin.adventure.npcobjectives.transaction.KillTaskTransaction;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.CountObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.worldlocationproviders.WorldLocationProvider;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.transaction.SpawnEntityTransactionRecord;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.transaction.TransactionRecord;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.transaction.WorldTransactionRecord;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.beacons.LegacySpawnBeaconEntity;
/*     */ import com.hypixel.hytale.server.spawning.wrappers.BeaconSpawnWrapper;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class KillSpawnBeaconObjectiveTask extends KillObjectiveTask {
/*  30 */   public static final BuilderCodec<KillSpawnBeaconObjectiveTask> CODEC = BuilderCodec.builder(KillSpawnBeaconObjectiveTask.class, KillSpawnBeaconObjectiveTask::new, KillObjectiveTask.CODEC)
/*  31 */     .build();
/*     */   
/*     */   public KillSpawnBeaconObjectiveTask(@Nonnull KillSpawnBeaconObjectiveTaskAsset asset, int taskSetIndex, int taskIndex) {
/*  34 */     super((KillObjectiveTaskAsset)asset, taskSetIndex, taskIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   protected KillSpawnBeaconObjectiveTask() {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public KillSpawnBeaconObjectiveTaskAsset getAsset() {
/*  43 */     return (KillSpawnBeaconObjectiveTaskAsset)super.getAsset();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected TransactionRecord[] setup0(@Nonnull Objective objective, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*  49 */     TransactionRecord[] transactionRecords = this.serializedTransactionRecords;
/*     */     
/*  51 */     if (transactionRecords == null) {
/*  52 */       transactionRecords = setupSpawnBeacons(objective, world, (ComponentAccessor<EntityStore>)store);
/*  53 */       if (TransactionUtil.anyFailed(transactionRecords)) {
/*  54 */         return transactionRecords;
/*     */       }
/*     */     } 
/*     */     
/*  58 */     KillTaskTransaction transaction = new KillTaskTransaction(this, objective, (ComponentAccessor)store);
/*  59 */     ((KillTrackerResource)store.getResource(KillTrackerResource.getResourceType())).watch(transaction);
/*  60 */     return (TransactionRecord[])ArrayUtil.append((Object[])transactionRecords, transaction);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private TransactionRecord[] setupSpawnBeacons(@Nonnull Objective objective, @Nonnull World world, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  65 */     Vector3d position = objective.getPosition(componentAccessor);
/*  66 */     if (position == null) {
/*  67 */       return TransactionRecord.appendFailedTransaction(null, (TransactionRecord)new WorldTransactionRecord(), "No valid position found for the objective.");
/*     */     }
/*     */     
/*  70 */     KillSpawnBeaconObjectiveTaskAsset.ObjectiveSpawnBeacon[] spawnBeaconConfigs = getAsset().getSpawnBeacons();
/*  71 */     TransactionRecord[] transactionRecords = new TransactionRecord[spawnBeaconConfigs.length];
/*  72 */     HytaleLogger logger = ObjectivePlugin.get().getLogger();
/*     */     
/*  74 */     for (int i = 0; i < spawnBeaconConfigs.length; i++) {
/*  75 */       Vector3d spawnPosition = position.clone();
/*  76 */       KillSpawnBeaconObjectiveTaskAsset.ObjectiveSpawnBeacon spawnBeaconConfig = spawnBeaconConfigs[i];
/*  77 */       String spawnBeaconId = spawnBeaconConfig.getSpawnBeaconId();
/*  78 */       int index = BeaconNPCSpawn.getAssetMap().getIndex(spawnBeaconId);
/*  79 */       if (index == Integer.MIN_VALUE) {
/*  80 */         transactionRecords[i] = (new WorldTransactionRecord()).fail("Failed to find spawn beacon " + spawnBeaconId);
/*  81 */         return Arrays.<TransactionRecord>copyOf(transactionRecords, i + 1);
/*     */       } 
/*     */       
/*  84 */       Vector3d offset = spawnBeaconConfig.getOffset();
/*  85 */       if (offset != null) spawnPosition.add(offset);
/*     */       
/*  87 */       WorldLocationProvider worldLocationCondition = spawnBeaconConfig.getWorldLocationProvider();
/*  88 */       if (worldLocationCondition != null) {
/*  89 */         spawnPosition = worldLocationCondition.runCondition(world, spawnPosition.toVector3i()).toVector3d();
/*     */       }
/*     */       
/*  92 */       if (spawnPosition == null) {
/*  93 */         transactionRecords[i] = (new WorldTransactionRecord()).fail("Failed to find a valid position to spawn beacon " + spawnBeaconId);
/*     */       }
/*     */       else {
/*     */         
/*  97 */         BeaconSpawnWrapper wrapper = SpawningPlugin.get().getBeaconSpawnWrapper(index);
/*  98 */         Pair<Ref<EntityStore>, LegacySpawnBeaconEntity> spawnBeaconPair = LegacySpawnBeaconEntity.create(wrapper, spawnPosition, Vector3f.FORWARD, componentAccessor);
/*  99 */         ((LegacySpawnBeaconEntity)spawnBeaconPair.second()).setObjectiveUUID(objective.getObjectiveUUID());
/*     */         
/* 101 */         UUIDComponent spawnBeaconUuidComponent = (UUIDComponent)componentAccessor.getComponent((Ref)spawnBeaconPair.first(), UUIDComponent.getComponentType());
/* 102 */         assert spawnBeaconUuidComponent != null;
/*     */         
/* 104 */         logger.at(Level.INFO).log("Spawned SpawnBeacon '" + spawnBeaconId + "' at position: " + String.valueOf(position));
/*     */         
/* 106 */         transactionRecords[i] = (TransactionRecord)new SpawnEntityTransactionRecord(world.getWorldConfig().getUuid(), spawnBeaconUuidComponent.getUuid());
/*     */       } 
/*     */     } 
/* 109 */     return transactionRecords;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 115 */     return "KillSpawnBeaconObjectiveTask{} " + super.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\task\KillSpawnBeaconObjectiveTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */