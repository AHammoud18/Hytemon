/*    */ package com.hypixel.hytale.builtin.blockspawner.command;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSpawnerCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public BlockSpawnerCommand() {
/* 14 */     super("blockspawner", "server.commands.blockspawner.desc");
/*    */     
/* 16 */     addSubCommand((AbstractCommand)new BlockSpawnerSetCommand());
/* 17 */     addSubCommand((AbstractCommand)new BlockSpawnerGetCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blockspawner\command\BlockSpawnerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */