/*    */ package com.hypixel.hytale.builtin.adventure.reputation.command;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReputationCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public ReputationCommand() {
/* 14 */     super("reputation", "server.commands.reputation.desc");
/* 15 */     addSubCommand((AbstractCommand)new ReputationAddCommand());
/* 16 */     addSubCommand((AbstractCommand)new ReputationSetCommand());
/* 17 */     addSubCommand((AbstractCommand)new ReputationRankCommand());
/* 18 */     addSubCommand((AbstractCommand)new ReputationValueCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\reputation\command\ReputationCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */