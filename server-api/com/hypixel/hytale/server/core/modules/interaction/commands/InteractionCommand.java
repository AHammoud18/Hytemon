/*    */ package com.hypixel.hytale.server.core.modules.interaction.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InteractionCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public InteractionCommand() {
/* 14 */     super("interaction", "server.commands.interaction.desc");
/* 15 */     addAliases(new String[] { "interact" });
/* 16 */     addSubCommand((AbstractCommand)new InteractionRunCommand());
/* 17 */     addSubCommand((AbstractCommand)new InteractionSnapshotSourceCommand());
/* 18 */     addSubCommand((AbstractCommand)new InteractionClearCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\commands\InteractionCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */