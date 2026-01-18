/*    */ package com.hypixel.hytale.builtin.portals.commands.utils;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.metadata.AdventureMetadata;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CursedHeldItemCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 21 */   private static final Message MESSAGE_COMMANDS_CURSE_THIS_NOT_HOLDING_ITEM = Message.translation("server.commands.cursethis.notHoldingItem");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CursedHeldItemCommand() {
/* 27 */     super("cursethis", "server.commands.cursethis.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 32 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 33 */     assert playerComponent != null;
/*    */     
/* 35 */     Inventory inventory = playerComponent.getInventory();
/* 36 */     if (inventory.usingToolsItem()) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     ItemStack inHandItemStack = inventory.getActiveHotbarItem();
/* 41 */     if (inHandItemStack == null || inHandItemStack.isEmpty()) {
/* 42 */       playerRef.sendMessage(MESSAGE_COMMANDS_CURSE_THIS_NOT_HOLDING_ITEM);
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     AdventureMetadata adventureMeta = (AdventureMetadata)inHandItemStack.getFromMetadataOrDefault("Adventure", AdventureMetadata.CODEC);
/* 47 */     adventureMeta.setCursed(!adventureMeta.isCursed());
/*    */     
/* 49 */     ItemStack edited = inHandItemStack.withMetadata(AdventureMetadata.KEYED_CODEC, adventureMeta);
/* 50 */     inventory.getHotbar().replaceItemStackInSlot((short)inventory.getActiveHotbarSlot(), inHandItemStack, edited);
/*    */     
/* 52 */     playerRef.sendMessage(Message.translation("server.commands.cursethis.done")
/* 53 */         .param("state", adventureMeta.isCursed()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\command\\utils\CursedHeldItemCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */