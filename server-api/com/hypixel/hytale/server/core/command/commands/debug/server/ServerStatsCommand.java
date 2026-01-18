/*    */ package com.hypixel.hytale.server.core.command.commands.debug.server;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerStatsCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public ServerStatsCommand() {
/* 14 */     super("stats", "server.commands.server.stats.desc");
/* 15 */     addSubCommand((AbstractCommand)new ServerStatsCpuCommand());
/* 16 */     addSubCommand((AbstractCommand)new ServerStatsMemoryCommand());
/* 17 */     addSubCommand((AbstractCommand)new ServerStatsGcCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\server\ServerStatsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */