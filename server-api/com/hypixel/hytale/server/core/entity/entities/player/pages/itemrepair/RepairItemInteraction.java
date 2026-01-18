/*    */ package com.hypixel.hytale.server.core.entity.entities.player.pages.itemrepair;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.protocol.SoundCategory;
/*    */ import com.hypixel.hytale.protocol.packets.interface_.Page;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.PageManager;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceInteraction;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemContext;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.inventory.transaction.ItemStackSlotTransaction;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.SoundUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.TempAssetIdUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class RepairItemInteraction extends ChoiceInteraction {
/*    */   protected final ItemContext itemContext;
/*    */   
/*    */   public RepairItemInteraction(ItemContext itemContext, double repairPenalty, ItemContext heldItemContext) {
/* 26 */     this.itemContext = itemContext;
/* 27 */     this.repairPenalty = repairPenalty;
/* 28 */     this.heldItemContext = heldItemContext;
/*    */   }
/*    */   protected final double repairPenalty; protected final ItemContext heldItemContext;
/*    */   
/*    */   public void run(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef) {
/* 33 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 34 */     PageManager pageManager = playerComponent.getPageManager();
/*    */     
/* 36 */     ItemStack itemStack = this.itemContext.getItemStack();
/* 37 */     double itemStackDurability = itemStack.getDurability();
/* 38 */     double itemStackMaxDurability = itemStack.getMaxDurability();
/*    */     
/* 40 */     double ratioAmountRepaired = 1.0D - itemStackDurability / itemStackMaxDurability;
/* 41 */     double newMaxDurability = MathUtil.floor(itemStackMaxDurability - itemStack.getItem().getMaxDurability() * this.repairPenalty * ratioAmountRepaired);
/*    */     
/* 43 */     if (itemStackDurability >= newMaxDurability) {
/* 44 */       playerRef.sendMessage(Message.translation("server.general.repair.penaltyTooBig").color("#ff5555"));
/* 45 */       pageManager.setPage(ref, store, Page.None);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 50 */     if (newMaxDurability <= 10.0D) {
/* 51 */       newMaxDurability = 10.0D;
/* 52 */       playerRef.sendMessage(Message.translation("server.general.repair.tooLowDurability").color("#ff5555"));
/*    */     } 
/*    */     
/* 55 */     ItemContainer heldItemContainer = this.heldItemContext.getContainer();
/* 56 */     short heldItemSlot = this.heldItemContext.getSlot();
/* 57 */     ItemStack heldItemStack = this.heldItemContext.getItemStack();
/*    */ 
/*    */     
/* 60 */     ItemStackSlotTransaction slotTransaction = heldItemContainer.removeItemStackFromSlot(heldItemSlot, heldItemStack, 1);
/* 61 */     if (!slotTransaction.succeeded()) {
/*    */       
/* 63 */       pageManager.setPage(ref, store, Page.None);
/*    */       
/*    */       return;
/*    */     } 
/* 67 */     ItemStack newItemStack = itemStack.withRestoredDurability(newMaxDurability);
/*    */ 
/*    */     
/* 70 */     ItemStackSlotTransaction replaceTransaction = this.itemContext.getContainer().replaceItemStackInSlot(this.itemContext.getSlot(), itemStack, newItemStack);
/* 71 */     if (!replaceTransaction.succeeded()) {
/*    */ 
/*    */ 
/*    */       
/* 75 */       SimpleItemContainer.addOrDropItemStack((ComponentAccessor)store, ref, heldItemContainer, heldItemSlot, heldItemStack.withQuantity(1));
/* 76 */       pageManager.setPage(ref, store, Page.None);
/*    */       
/*    */       return;
/*    */     } 
/* 80 */     Message newItemStackMessage = Message.translation(newItemStack.getItem().getTranslationKey());
/* 81 */     playerRef.sendMessage(Message.translation("server.general.repair.successful")
/* 82 */         .param("itemName", newItemStackMessage));
/* 83 */     pageManager.setPage(ref, store, Page.None);
/*    */     
/* 85 */     SoundUtil.playSoundEvent2d(ref, TempAssetIdUtil.getSoundEventIndex("SFX_Item_Repair"), SoundCategory.UI, (ComponentAccessor)store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\pages\itemrepair\RepairItemInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */