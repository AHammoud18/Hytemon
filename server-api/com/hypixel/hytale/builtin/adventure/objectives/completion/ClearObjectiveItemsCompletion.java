/*    */ package com.hypixel.hytale.builtin.adventure.objectives.completion;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.completion.ClearObjectiveItemsCompletionAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.completion.ObjectiveCompletionAsset;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.CombinedItemContainer;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ClearObjectiveItemsCompletion extends ObjectiveCompletion {
/*    */   public ClearObjectiveItemsCompletion(@Nonnull ObjectiveCompletionAsset asset) {
/* 16 */     super(asset);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ClearObjectiveItemsCompletionAsset getAsset() {
/* 22 */     return (ClearObjectiveItemsCompletionAsset)super.getAsset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(@Nonnull Objective objective, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 27 */     objective.forEachParticipant((participantReference, objectiveUuid) -> { Entity entity = EntityUtils.getEntity(participantReference, componentAccessor); if (entity instanceof LivingEntity) { CombinedItemContainer inventory = ((LivingEntity)entity).getInventory().getCombinedHotbarFirst(); short i; for (i = 0; i < inventory.getCapacity(); i = (short)(i + 1)) { ItemStack itemStack = inventory.getItemStack(i); if (itemStack != null) { UUID savedObjectiveUuid = (UUID)itemStack.getFromMetadataOrNull(StartObjectiveInteraction.OBJECTIVE_UUID); if (objectiveUuid.equals(savedObjectiveUuid)) inventory.removeItemStackFromSlot(i);  }  }  }  }objective
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 44 */         .getObjectiveUUID());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 50 */     return "ClearObjectiveItemsCompletion{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\completion\ClearObjectiveItemsCompletion.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */