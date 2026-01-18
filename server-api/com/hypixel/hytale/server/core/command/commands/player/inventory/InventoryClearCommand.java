/*    */ package com.hypixel.hytale.server.core.command.commands.player.inventory;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InventoryClearCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 21 */   private static final Message MESSAGE_COMMANDS_CLEARINV_SUCCESS = Message.translation("server.commands.clearinv.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InventoryClearCommand() {
/* 27 */     super("clear", "server.commands.inventoryclear.desc");
/* 28 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 33 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 34 */     assert playerComponent != null;
/*    */     
/* 36 */     playerComponent.getInventory().clear();
/* 37 */     context.sendMessage(MESSAGE_COMMANDS_CLEARINV_SUCCESS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\inventory\InventoryClearCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */