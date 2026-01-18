/*    */ package com.hypixel.hytale.server.core.command.commands.player.inventory;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.packets.interface_.Page;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.windows.ContainerWindow;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.windows.Window;
/*    */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemStackItemContainer;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class InventoryItemCommand
/*    */   extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_INVENTORY_ITEM_NO_ITEM_IN_HAND = Message.translation("server.commands.inventory.item.noItemInHand");
/*    */   @Nonnull
/* 26 */   private static final Message MESSAGE_COMMANDS_INVENTORY_ITEM_NO_CONTAINER_ON_ITEM = Message.translation("server.commands.inventory.item.noContainerOnItem");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InventoryItemCommand() {
/* 32 */     super("item", "server.commands.inventoryitem.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 37 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 38 */     assert playerComponent != null;
/*    */     
/* 40 */     Inventory inventory = playerComponent.getInventory();
/* 41 */     ItemContainer hotbar = inventory.getHotbar();
/* 42 */     byte activeHotbarSlot = inventory.getActiveHotbarSlot();
/* 43 */     ItemStack activeHotbarItem = inventory.getActiveHotbarItem();
/*    */     
/* 45 */     if (ItemStack.isEmpty(activeHotbarItem)) {
/* 46 */       context.sendMessage(MESSAGE_COMMANDS_INVENTORY_ITEM_NO_ITEM_IN_HAND);
/*    */       
/*    */       return;
/*    */     } 
/* 50 */     ItemStackItemContainer backpackInventory = ItemStackItemContainer.getContainer(hotbar, (short)activeHotbarSlot);
/* 51 */     if (backpackInventory == null || backpackInventory.getCapacity() == 0) {
/* 52 */       context.sendMessage(MESSAGE_COMMANDS_INVENTORY_ITEM_NO_CONTAINER_ON_ITEM);
/*    */       
/*    */       return;
/*    */     } 
/* 56 */     playerComponent.getPageManager().setPageWithWindows(ref, store, Page.Bench, true, new Window[] { (Window)new ContainerWindow((ItemContainer)backpackInventory) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\inventory\InventoryItemCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */