/*    */ package com.hypixel.hytale.server.core.command.commands.debug.packs;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacksCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public PacksCommand() {
/* 14 */     super("packs", "server.commands.packs.desc");
/* 15 */     addSubCommand((AbstractCommand)new PacksListCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\packs\PacksCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */