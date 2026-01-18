/*    */ package com.hypixel.hytale.server.core.entity.entities.player.pages.itemrepair;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceBasePage;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceElement;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemContext;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
/*    */ import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ItemRepairPage
/*    */   extends ChoiceBasePage
/*    */ {
/*    */   public ItemRepairPage(@Nonnull PlayerRef playerRef, @Nonnull ItemContainer itemContainer, double repairPenalty, ItemContext heldItemContext) {
/* 21 */     super(playerRef, getItemElements(itemContainer, repairPenalty, heldItemContext), "Pages/ItemRepairPage.ui");
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder commandBuilder, @Nonnull UIEventBuilder eventBuilder, @Nonnull Store<EntityStore> store) {
/* 26 */     if ((getElements()).length > 0) {
/* 27 */       super.build(ref, commandBuilder, eventBuilder, store);
/*    */       
/*    */       return;
/*    */     } 
/* 31 */     commandBuilder.append(getPageLayout());
/* 32 */     commandBuilder.clear("#ElementList");
/* 33 */     commandBuilder.appendInline("#ElementList", "Label { Text: %customUI.itemRepairPage.noItems; Style: (Alignment: Center); }");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   protected static ChoiceElement[] getItemElements(@Nonnull ItemContainer itemContainer, double repairPenalty, ItemContext heldItemContext) {
/* 38 */     ObjectArrayList<ItemRepairElement> objectArrayList = new ObjectArrayList(); short slot;
/* 39 */     for (slot = 0; slot < itemContainer.getCapacity(); slot = (short)(slot + 1)) {
/* 40 */       ItemStack itemStack = itemContainer.getItemStack(slot);
/* 41 */       if (!ItemStack.isEmpty(itemStack) && !itemStack.isUnbreakable() && itemStack.getDurability() < itemStack.getMaxDurability()) {
/*    */         
/* 43 */         ItemContext itemContext = new ItemContext(itemContainer, slot, itemStack);
/* 44 */         objectArrayList.add(new ItemRepairElement(itemStack, new RepairItemInteraction(itemContext, repairPenalty, heldItemContext)));
/*    */       } 
/*    */     } 
/* 47 */     return (ChoiceElement[])objectArrayList.toArray(x$0 -> new ChoiceElement[x$0]);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\pages\itemrepair\ItemRepairPage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */