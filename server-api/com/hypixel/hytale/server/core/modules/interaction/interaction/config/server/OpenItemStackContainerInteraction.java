/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.ItemStackContainerConfig;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.PageManager;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.windows.ItemStackContainerWindow;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.windows.Window;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemStackItemContainer;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class OpenItemStackContainerInteraction extends SimpleInstantInteraction {
/*    */   @Nonnull
/* 22 */   public static final BuilderCodec<OpenItemStackContainerInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(OpenItemStackContainerInteraction.class, OpenItemStackContainerInteraction::new, SimpleInstantInteraction.CODEC)
/* 23 */     .documentation("Opens a container contained within the current held item."))
/* 24 */     .build();
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 28 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 29 */     Ref<EntityStore> ref = context.getEntity();
/* 30 */     Store<EntityStore> store = ref.getStore();
/*    */     
/* 32 */     Player playerComponent = (Player)commandBuffer.getComponent(ref, Player.getComponentType());
/* 33 */     if (playerComponent == null)
/*    */       return; 
/* 35 */     PageManager pageManager = playerComponent.getPageManager();
/* 36 */     if (pageManager.getCustomPage() != null)
/*    */       return; 
/* 38 */     ItemStack heldItem = context.getHeldItem();
/* 39 */     if (ItemStack.isEmpty(heldItem))
/*    */       return; 
/* 41 */     byte heldItemSlot = context.getHeldItemSlot();
/* 42 */     ItemContainer itemContainer = playerComponent.getInventory().getSectionById(context.getHeldItemSectionId());
/* 43 */     if (itemContainer == null)
/*    */       return; 
/* 45 */     ItemStack itemStack = itemContainer.getItemStack((short)heldItemSlot);
/* 46 */     ItemStackContainerConfig config = itemStack.getItem().getItemStackContainerConfig();
/* 47 */     ItemStackItemContainer itemStackItemContainer = ItemStackItemContainer.ensureConfiguredContainer(itemContainer, (short)heldItemSlot, config);
/* 48 */     if (itemStackItemContainer == null)
/*    */       return; 
/* 50 */     pageManager.setPageWithWindows(ref, store, Page.Bench, true, new Window[] { (Window)new ItemStackContainerWindow(itemStackItemContainer) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\OpenItemStackContainerInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */