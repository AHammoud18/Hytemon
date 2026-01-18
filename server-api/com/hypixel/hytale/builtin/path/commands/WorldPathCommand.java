/*    */ package com.hypixel.hytale.builtin.path.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldPathCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public WorldPathCommand() {
/* 14 */     super("worldpath", "server.commands.worldpath.desc");
/* 15 */     addSubCommand((AbstractCommand)new WorldPathListCommand());
/* 16 */     addSubCommand((AbstractCommand)new WorldPathRemoveCommand());
/* 17 */     addSubCommand((AbstractCommand)new WorldPathSaveCommand());
/* 18 */     addSubCommand((AbstractCommand)new WorldPathBuilderCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\WorldPathCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */