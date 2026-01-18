/*    */ package com.hypixel.hytale.server.spawning.local;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class LocalSpawnBeaconSystem
/*    */   extends RefSystem<EntityStore> {
/*    */   private final ComponentType<EntityStore, LocalSpawnBeacon> componentType;
/*    */   private final ResourceType<EntityStore, LocalSpawnState> localSpawnStateResourceType;
/*    */   
/*    */   public LocalSpawnBeaconSystem(ComponentType<EntityStore, LocalSpawnBeacon> componentType, ResourceType<EntityStore, LocalSpawnState> localSpawnStateResourceType) {
/* 23 */     this.componentType = componentType;
/* 24 */     this.localSpawnStateResourceType = localSpawnStateResourceType;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<EntityStore> reference, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*    */ 
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<EntityStore> reference, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 33 */     SpawningPlugin.get().getLogger().at(Level.FINE).log("Triggering forced rerun of local spawn controllers");
/* 34 */     ((LocalSpawnState)store.getResource(this.localSpawnStateResourceType)).forceTriggerControllers();
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 39 */     return (Query)this.componentType;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\local\LocalSpawnBeaconSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */