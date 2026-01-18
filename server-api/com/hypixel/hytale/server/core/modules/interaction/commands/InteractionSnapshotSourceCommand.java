/*    */ package com.hypixel.hytale.server.core.modules.interaction.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.SelectInteraction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InteractionSnapshotSourceCommand
/*    */   extends CommandBase
/*    */ {
/*    */   public InteractionSnapshotSourceCommand() {
/* 19 */     super("snapshotsource", "server.commands.interaction.snapshotSource.desc");
/* 20 */     addSubCommand((AbstractCommand)new InteractionSetSnapshotSourceCommand());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 25 */     context.sendMessage(Message.translation("server.commands.interaction.snapshotSource.get")
/* 26 */         .param("source", SelectInteraction.SNAPSHOT_SOURCE.name()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\commands\InteractionSnapshotSourceCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */