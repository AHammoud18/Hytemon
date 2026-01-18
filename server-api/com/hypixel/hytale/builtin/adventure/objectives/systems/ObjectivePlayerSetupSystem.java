/*    */ package com.hypixel.hytale.builtin.adventure.objectives.systems;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.components.ObjectiveHistoryComponent;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.gameplayconfig.ObjectiveGameplayConfig;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerConfigData;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.data.PlayerWorldData;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectivePlayerSetupSystem
/*    */   extends RefSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final ComponentType<EntityStore, ObjectiveHistoryComponent> objectiveHistoryComponentType;
/*    */   @Nonnull
/*    */   private final ComponentType<EntityStore, Player> playerComponentType;
/*    */   @Nonnull
/* 37 */   private final ComponentType<EntityStore, UUIDComponent> uuidComponentType = UUIDComponent.getComponentType();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectivePlayerSetupSystem(@Nonnull ComponentType<EntityStore, ObjectiveHistoryComponent> objectiveHistoryComponentType, @Nonnull ComponentType<EntityStore, Player> playerComponentType) {
/* 54 */     this.objectiveHistoryComponentType = objectiveHistoryComponentType;
/* 55 */     this.playerComponentType = playerComponentType;
/*    */     
/* 57 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)playerComponentType, (Query)this.uuidComponentType });
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 63 */     return this.query;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 68 */     commandBuffer.ensureComponent(ref, this.objectiveHistoryComponentType);
/*    */     
/* 70 */     Player playerComponent = (Player)store.getComponent(ref, this.playerComponentType);
/* 71 */     assert playerComponent != null;
/*    */     
/* 73 */     UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, this.uuidComponentType);
/* 74 */     assert uuidComponent != null;
/*    */     
/* 76 */     ObjectivePlugin objectiveModule = ObjectivePlugin.get();
/* 77 */     UUID playerUuid = uuidComponent.getUuid();
/* 78 */     PlayerConfigData playerConfigData = playerComponent.getPlayerConfigData();
/*    */     
/* 80 */     Set<UUID> activeObjectiveUUIDs = playerConfigData.getActiveObjectiveUUIDs();
/* 81 */     if (activeObjectiveUUIDs != null) {
/* 82 */       for (UUID objectiveUUID : activeObjectiveUUIDs) {
/* 83 */         objectiveModule.addPlayerToExistingObjective(store, playerUuid, objectiveUUID);
/*    */       }
/*    */     }
/*    */     
/* 87 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 88 */     String worldName = world.getName();
/* 89 */     PlayerWorldData perWorldData = playerConfigData.getPerWorldData(worldName);
/* 90 */     if (perWorldData.isFirstSpawn()) {
/*    */       
/* 92 */       ObjectiveGameplayConfig config = ObjectiveGameplayConfig.get(world.getGameplayConfig());
/* 93 */       Map<String, String> starterObjectiveLinePerWorld = (config != null) ? config.getStarterObjectiveLinePerWorld() : null;
/* 94 */       if (starterObjectiveLinePerWorld != null) {
/*    */         
/* 96 */         String objectiveLineId = starterObjectiveLinePerWorld.get(worldName);
/* 97 */         if (objectiveLineId != null)
/* 98 */           objectiveModule.startObjectiveLine(store, objectiveLineId, Set.of(playerUuid), world.getWorldConfig().getUuid(), null); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\systems\ObjectivePlayerSetupSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */