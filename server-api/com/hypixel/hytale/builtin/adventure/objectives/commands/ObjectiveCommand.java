/*    */ package com.hypixel.hytale.builtin.adventure.objectives.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectiveCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public ObjectiveCommand() {
/* 14 */     super("objective", "server.commands.objective");
/* 15 */     addAliases(new String[] { "obj" });
/* 16 */     addSubCommand((AbstractCommand)new ObjectiveStartCommand());
/* 17 */     addSubCommand((AbstractCommand)new ObjectiveCompleteCommand());
/* 18 */     addSubCommand((AbstractCommand)new ObjectivePanelCommand());
/* 19 */     addSubCommand((AbstractCommand)new ObjectiveHistoryCommand());
/* 20 */     addSubCommand((AbstractCommand)new ObjectiveLocationMarkerCommand());
/* 21 */     addSubCommand((AbstractCommand)new ObjectiveReachLocationMarkerCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\commands\ObjectiveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */