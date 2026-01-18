/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity.stats;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityStatsSubCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public EntityStatsSubCommand() {
/* 14 */     super("stats", "server.commands.entity.stats.desc");
/* 15 */     addAliases(new String[] { "stat" });
/* 16 */     addSubCommand((AbstractCommand)new EntityStatsDumpCommand());
/* 17 */     addSubCommand((AbstractCommand)new EntityStatsGetCommand());
/* 18 */     addSubCommand((AbstractCommand)new EntityStatsSetCommand());
/* 19 */     addSubCommand((AbstractCommand)new EntityStatsSetToMaxCommand());
/* 20 */     addSubCommand((AbstractCommand)new EntityStatsResetCommand());
/* 21 */     addSubCommand((AbstractCommand)new EntityStatsAddCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\stats\EntityStatsSubCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */