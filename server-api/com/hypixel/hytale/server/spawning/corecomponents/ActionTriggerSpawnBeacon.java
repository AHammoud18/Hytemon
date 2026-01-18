/*    */ package com.hypixel.hytale.server.spawning.corecomponents;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.spawning.beacons.SpawnBeacon;
/*    */ import com.hypixel.hytale.server.spawning.corecomponents.builders.BuilderActionTriggerSpawnBeacon;
/*    */ import com.hypixel.hytale.server.spawning.util.FloodFillPositionSelector;
/*    */ import com.hypixel.hytale.server.spawning.wrappers.BeaconSpawnWrapper;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionTriggerSpawnBeacon
/*    */   extends ActionBase {
/*    */   protected final int beaconId;
/*    */   
/*    */   public ActionTriggerSpawnBeacon(@Nonnull BuilderActionTriggerSpawnBeacon builder, @Nonnull BuilderSupport support) {
/* 23 */     super((BuilderActionBase)builder);
/* 24 */     this.beaconId = builder.getBeaconId(support);
/* 25 */     this.range = builder.getRange(support);
/* 26 */     this.targetSlot = builder.getTargetSlot(support);
/*    */   }
/*    */   protected final int range; protected final int targetSlot;
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 31 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && (this.targetSlot == Integer.MIN_VALUE || role.getMarkedEntitySupport().hasMarkedEntityInSlot(this.targetSlot)));
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerWithSupport(@Nonnull Role role) {
/* 36 */     role.getPositionCache().requireSpawnBeaconDistance(this.range);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 41 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 43 */     List<Ref<EntityStore>> spawnBeacons = role.getPositionCache().getSpawnBeaconList();
/* 44 */     for (Ref<EntityStore> spawnBeaconRef : spawnBeacons) {
/* 45 */       SpawnBeacon spawnBeaconComponent = (SpawnBeacon)store.getComponent(spawnBeaconRef, SpawnBeacon.getComponentType());
/* 46 */       assert spawnBeaconComponent != null;
/*    */       
/* 48 */       BeaconSpawnWrapper spawnWrapper = spawnBeaconComponent.getSpawnWrapper();
/* 49 */       if (spawnWrapper.getSpawnIndex() == this.beaconId) {
/*    */         Ref<EntityStore> targetRef;
/* 51 */         if (this.targetSlot != Integer.MIN_VALUE) {
/* 52 */           targetRef = role.getMarkedEntitySupport().getMarkedEntityRef(this.targetSlot);
/*    */         } else {
/* 54 */           targetRef = ref;
/*    */         } 
/*    */         
/* 57 */         FloodFillPositionSelector floodFillPositionSelectorComponent = (FloodFillPositionSelector)store.getComponent(spawnBeaconRef, FloodFillPositionSelector.getComponentType());
/* 58 */         assert floodFillPositionSelectorComponent != null;
/*    */         
/* 60 */         spawnBeaconComponent.manualTrigger(spawnBeaconRef, floodFillPositionSelectorComponent, targetRef, store);
/*    */         
/* 62 */         return true;
/*    */       } 
/*    */     } 
/* 65 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\corecomponents\ActionTriggerSpawnBeacon.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */