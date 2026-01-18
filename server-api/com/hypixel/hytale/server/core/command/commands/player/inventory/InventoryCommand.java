/*    */ package com.hypixel.hytale.server.core.command.commands.player.inventory;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InventoryCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public InventoryCommand() {
/* 13 */     super("inventory", "server.commands.inventory.desc");
/* 14 */     addAliases(new String[] { "inv" });
/* 15 */     addSubCommand((AbstractCommand)new InventoryClearCommand());
/* 16 */     addSubCommand((AbstractCommand)new InventorySeeCommand());
/* 17 */     addSubCommand((AbstractCommand)new InventoryItemCommand());
/* 18 */     addSubCommand((AbstractCommand)new InventoryBackpackCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\inventory\InventoryCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */