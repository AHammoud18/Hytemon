/*    */ package com.hypixel.hytale.builtin.adventure.npcobjectives.systems;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.beacons.LegacySpawnBeaconEntity;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnBeaconCheckRemovalSystem
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/*    */   @Nullable
/*    */   public Query<EntityStore> getQuery() {
/* 24 */     return (Query<EntityStore>)LegacySpawnBeaconEntity.getComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 29 */     LegacySpawnBeaconEntity spawnBeaconComponent = (LegacySpawnBeaconEntity)holder.getComponent(LegacySpawnBeaconEntity.getComponentType());
/* 30 */     assert spawnBeaconComponent != null;
/*    */     
/* 32 */     UUID objectiveUUID = spawnBeaconComponent.getObjectiveUUID();
/* 33 */     if (objectiveUUID != null && ObjectivePlugin.get().getObjectiveDataStore().getObjective(objectiveUUID) == null)
/* 34 */       spawnBeaconComponent.remove(); 
/*    */   }
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\systems\SpawnBeaconCheckRemovalSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */