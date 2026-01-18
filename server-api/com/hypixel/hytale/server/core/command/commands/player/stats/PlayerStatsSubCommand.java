/*    */ package com.hypixel.hytale.server.core.command.commands.player.stats;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerStatsSubCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public PlayerStatsSubCommand() {
/* 14 */     super("stats", "server.commands.player.stats.desc");
/* 15 */     addAliases(new String[] { "stat" });
/* 16 */     addSubCommand((AbstractCommand)new PlayerStatsAddCommand());
/* 17 */     addSubCommand((AbstractCommand)new PlayerStatsGetCommand());
/* 18 */     addSubCommand((AbstractCommand)new PlayerStatsSetCommand());
/* 19 */     addSubCommand((AbstractCommand)new PlayerStatsSetToMaxCommand());
/* 20 */     addSubCommand((AbstractCommand)new PlayerStatsDumpCommand());
/* 21 */     addSubCommand((AbstractCommand)new PlayerStatsResetCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\stats\PlayerStatsSubCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */