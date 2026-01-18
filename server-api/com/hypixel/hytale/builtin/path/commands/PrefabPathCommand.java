/*    */ package com.hypixel.hytale.builtin.path.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabPathCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public PrefabPathCommand() {
/* 14 */     super("path", "server.commands.npcpath.desc");
/* 15 */     addSubCommand((AbstractCommand)new PrefabPathListCommand());
/* 16 */     addSubCommand((AbstractCommand)new PrefabPathNodesCommand());
/* 17 */     addSubCommand((AbstractCommand)new PrefabPathNewCommand());
/* 18 */     addSubCommand((AbstractCommand)new PrefabPathEditCommand());
/* 19 */     addSubCommand((AbstractCommand)new PrefabPathAddCommand());
/* 20 */     addSubCommand((AbstractCommand)new PrefabPathMergeCommand());
/* 21 */     addSubCommand((AbstractCommand)new PrefabPathUpdateCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\PrefabPathCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */