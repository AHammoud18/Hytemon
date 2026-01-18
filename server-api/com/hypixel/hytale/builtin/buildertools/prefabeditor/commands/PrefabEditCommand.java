/*    */ package com.hypixel.hytale.builtin.buildertools.prefabeditor.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabEditCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public PrefabEditCommand() {
/* 14 */     super("editprefab", "server.commands.editprefab.desc");
/* 15 */     addAliases(new String[] { "prefabedit", "pedit" });
/* 16 */     addSubCommand((AbstractCommand)new PrefabEditExitCommand());
/* 17 */     addSubCommand((AbstractCommand)new PrefabEditLoadCommand());
/* 18 */     addSubCommand((AbstractCommand)new PrefabEditCreateNewCommand());
/* 19 */     addSubCommand((AbstractCommand)new PrefabEditSelectCommand());
/* 20 */     addSubCommand((AbstractCommand)new PrefabEditSaveCommand());
/* 21 */     addSubCommand((AbstractCommand)new PrefabEditSaveUICommand());
/* 22 */     addSubCommand((AbstractCommand)new PrefabEditKillEntitiesCommand());
/* 23 */     addSubCommand((AbstractCommand)new PrefabEditSaveAsCommand());
/* 24 */     addSubCommand((AbstractCommand)new PrefabEditUpdateBoxCommand());
/* 25 */     addSubCommand((AbstractCommand)new PrefabEditInfoCommand());
/* 26 */     addSubCommand((AbstractCommand)new PrefabEditTeleportCommand());
/* 27 */     addSubCommand((AbstractCommand)new PrefabEditModifiedCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\prefabeditor\commands\PrefabEditCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */