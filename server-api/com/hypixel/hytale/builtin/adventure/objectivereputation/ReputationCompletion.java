/*    */ package com.hypixel.hytale.builtin.adventure.objectivereputation;
/*    */ import com.hypixel.hytale.builtin.adventure.objectivereputation.assets.ReputationCompletionAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectivereputation.historydata.ReputationObjectiveRewardHistoryData;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.completion.ObjectiveCompletionAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ObjectiveHistoryData;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ObjectiveRewardHistoryData;
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.ReputationPlugin;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ReputationCompletion extends ObjectiveCompletion {
/*    */   public ReputationCompletion(@Nonnull ReputationCompletionAsset asset) {
/* 17 */     super((ObjectiveCompletionAsset)asset);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ReputationCompletionAsset getAsset() {
/* 23 */     return (ReputationCompletionAsset)super.getAsset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(@Nonnull Objective objective, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 28 */     ReputationPlugin reputationModule = ReputationPlugin.get();
/*    */     
/* 30 */     objective.forEachParticipant((participantReference, asset, objectiveHistoryData) -> {
/*    */           Player playerComponent = (Player)componentAccessor.getComponent(participantReference, Player.getComponentType());
/*    */           
/*    */           if (playerComponent != null) {
/*    */             UUIDComponent uuidComponent = (UUIDComponent)componentAccessor.getComponent(participantReference, UUIDComponent.getComponentType());
/*    */             
/*    */             assert uuidComponent != null;
/*    */             
/*    */             String reputationGroupId = asset.getReputationGroupId();
/*    */             
/*    */             int amount = asset.getAmount();
/*    */             reputationModule.changeReputation(playerComponent, reputationGroupId, amount, componentAccessor);
/*    */             objectiveHistoryData.addRewardForPlayerUUID(uuidComponent.getUuid(), (ObjectiveRewardHistoryData)new ReputationObjectiveRewardHistoryData(reputationGroupId, amount));
/*    */           } 
/* 44 */         }getAsset(), objective.getObjectiveHistoryData());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 50 */     return "ReputationCompletion{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectivereputation\ReputationCompletion.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */