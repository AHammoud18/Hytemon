/*    */ package com.hypixel.hytale.builtin.adventure.objectives.completion;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.completion.GiveItemsCompletionAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.completion.ObjectiveCompletionAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ItemObjectiveRewardHistoryData;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ObjectiveHistoryData;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.EntityUtils;
/*    */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.inventory.container.SimpleItemContainer;
/*    */ import com.hypixel.hytale.server.core.modules.item.ItemModule;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.NotificationUtil;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class GiveItemsCompletion extends ObjectiveCompletion {
/*    */   public GiveItemsCompletion(@Nonnull GiveItemsCompletionAsset asset) {
/* 31 */     super((ObjectiveCompletionAsset)asset);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public GiveItemsCompletionAsset getAsset() {
/* 37 */     return (GiveItemsCompletionAsset)super.getAsset();
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(@Nonnull Objective objective, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 42 */     World world = Universe.get().getWorld(objective.getWorldUUID());
/* 43 */     if (world == null)
/*    */       return; 
/* 45 */     Store<EntityStore> store = world.getEntityStore().getStore();
/* 46 */     boolean showItemNotification = world.getGameplayConfig().getShowItemPickupNotifications();
/*    */     
/* 48 */     objective.forEachParticipant((participantReference, asset, objectiveHistoryData) -> {
/*    */           LivingEntity livingEntity;
/*    */           
/*    */           Entity entity = EntityUtils.getEntity(participantReference, componentAccessor);
/*    */           
/*    */           if (entity instanceof LivingEntity) {
/*    */             livingEntity = (LivingEntity)entity;
/*    */           } else {
/*    */             return;
/*    */           } 
/*    */           
/*    */           Inventory inventory = livingEntity.getInventory();
/*    */           
/*    */           List<ItemStack> itemStacks = ItemModule.get().getRandomItemDrops(asset.getDropListId());
/*    */           
/*    */           SimpleItemContainer.addOrDropItemStacks((ComponentAccessor)store, participantReference, (ItemContainer)inventory.getCombinedHotbarFirst(), itemStacks);
/*    */           
/*    */           Player playerComponent = (Player)componentAccessor.getComponent(participantReference, Player.getComponentType());
/*    */           if (playerComponent != null) {
/*    */             PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(participantReference, PlayerRef.getComponentType());
/*    */             assert playerRefComponent != null;
/*    */             UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(participantReference, UUIDComponent.getComponentType());
/*    */             assert uuidComponent != null;
/*    */             UUID uuid = uuidComponent.getUuid();
/*    */             for (ItemStack itemStack : itemStacks) {
/*    */               objectiveHistoryData.addRewardForPlayerUUID(uuid, (ObjectiveRewardHistoryData)new ItemObjectiveRewardHistoryData(itemStack.getItemId(), itemStack.getQuantity()));
/*    */               if (showItemNotification) {
/*    */                 Message itemNameMessage = Message.translation(itemStack.getItem().getTranslationKey());
/*    */                 NotificationUtil.sendNotification(playerRefComponent.getPacketHandler(), Message.translation("server.objectives.itemObjectiveCompletion").param("item", itemNameMessage), null, itemStack.toPacket());
/*    */               } 
/*    */             } 
/*    */           } 
/* 80 */         }getAsset(), objective.getObjectiveHistoryData());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 86 */     return "GiveItemsCompletion{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\completion\GiveItemsCompletion.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */