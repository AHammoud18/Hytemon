/*    */ package com.hypixel.hytale.server.spawning.local;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import com.hypixel.hytale.server.spawning.beacons.LegacySpawnBeaconEntity;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocalSpawnState
/*    */   implements Resource<EntityStore>
/*    */ {
/*    */   public static ResourceType<EntityStore, LocalSpawnState> getResourceType() {
/* 22 */     return SpawningPlugin.get().getLocalSpawnStateResourceType();
/*    */   }
/*    */   
/* 25 */   private final List<Ref<EntityStore>> localControllerList = (List<Ref<EntityStore>>)new ObjectArrayList();
/* 26 */   private final List<LegacySpawnBeaconEntity> localPendingSpawns = (List<LegacySpawnBeaconEntity>)new ObjectArrayList();
/*    */   
/*    */   private boolean forceTriggerControllers;
/*    */   
/*    */   @Nonnull
/*    */   public List<Ref<EntityStore>> getLocalControllerList() {
/* 32 */     return this.localControllerList;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public List<LegacySpawnBeaconEntity> getLocalPendingSpawns() {
/* 37 */     return this.localPendingSpawns;
/*    */   }
/*    */   
/*    */   public boolean pollForceTriggerControllers() {
/* 41 */     boolean result = this.forceTriggerControllers;
/* 42 */     this.forceTriggerControllers = false;
/* 43 */     return result;
/*    */   }
/*    */   
/*    */   public void forceTriggerControllers() {
/* 47 */     this.forceTriggerControllers = true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Resource<EntityStore> clone() {
/* 54 */     LocalSpawnState state = new LocalSpawnState();
/* 55 */     state.localControllerList.addAll(this.localControllerList);
/* 56 */     state.localPendingSpawns.addAll(this.localPendingSpawns);
/* 57 */     return state;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\local\LocalSpawnState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */