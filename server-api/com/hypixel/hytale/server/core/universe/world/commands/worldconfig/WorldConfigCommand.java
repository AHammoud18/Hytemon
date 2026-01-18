/*    */ package com.hypixel.hytale.server.core.universe.world.commands.worldconfig;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldConfigCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public WorldConfigCommand() {
/* 14 */     super("config", "server.commands.world.config.desc");
/* 15 */     addSubCommand((AbstractCommand)new WorldConfigPauseTimeCommand());
/* 16 */     addSubCommand((AbstractCommand)new WorldConfigSeedCommand());
/* 17 */     addSubCommand((AbstractCommand)new WorldConfigSetPvpCommand());
/* 18 */     addSubCommand((AbstractCommand)new WorldConfigSetSpawnCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\worldconfig\WorldConfigCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */