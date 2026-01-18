/*    */ package com.hypixel.hytale.server.core.modules.prefabspawner.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabSpawnerCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public PrefabSpawnerCommand() {
/* 14 */     super("prefabspawner", "server.commands.prefabspawner.desc");
/* 15 */     addAliases(new String[] { "pspawner" });
/*    */     
/* 17 */     addSubCommand((AbstractCommand)new PrefabSpawnerGetCommand());
/* 18 */     addSubCommand((AbstractCommand)new PrefabSpawnerSetCommand());
/* 19 */     addSubCommand((AbstractCommand)new PrefabSpawnerWeightCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\prefabspawner\commands\PrefabSpawnerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */