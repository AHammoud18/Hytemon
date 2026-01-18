/*    */ package com.hypixel.hytale.server.core.command.commands.player.inventory;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ItemStateCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_ITEMSTATE_NO_ITEM = Message.translation("server.commands.itemstate.noItem");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 31 */   private final RequiredArg<String> stateArg = withRequiredArg("state", "server.commands.itemstate.state.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStateCommand() {
/* 37 */     super("itemstate", "server.commands.itemstate.desc");
/* 38 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 43 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 44 */     assert playerComponent != null;
/*    */     
/* 46 */     Inventory inventory = playerComponent.getInventory();
/* 47 */     byte activeHotbarSlot = inventory.getActiveHotbarSlot();
/*    */     
/* 49 */     if (activeHotbarSlot == -1) {
/* 50 */       context.sendMessage(MESSAGE_COMMANDS_ITEMSTATE_NO_ITEM);
/*    */       
/*    */       return;
/*    */     } 
/* 54 */     ItemContainer hotbar = inventory.getHotbar();
/* 55 */     ItemStack item = hotbar.getItemStack((short)activeHotbarSlot);
/*    */     
/* 57 */     if (item == null) {
/* 58 */       context.sendMessage(MESSAGE_COMMANDS_ITEMSTATE_NO_ITEM);
/*    */       
/*    */       return;
/*    */     } 
/* 62 */     String state = (String)this.stateArg.get(context);
/* 63 */     hotbar.setItemStackForSlot((short)activeHotbarSlot, item.withState(state));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\inventory\ItemStateCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */