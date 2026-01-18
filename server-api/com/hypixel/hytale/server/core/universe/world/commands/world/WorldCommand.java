/*    */ package com.hypixel.hytale.server.core.universe.world.commands.world;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ import com.hypixel.hytale.server.core.universe.world.commands.WorldSettingsCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.commands.world.perf.WorldPerfCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.commands.world.tps.WorldTpsCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.commands.worldconfig.WorldConfigCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.commands.worldconfig.WorldPauseCommand;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public WorldCommand() {
/* 19 */     super("world", "server.commands.world.desc");
/* 20 */     addAliases(new String[] { "worlds" });
/*    */     
/* 22 */     addSubCommand((AbstractCommand)new WorldListCommand());
/* 23 */     addSubCommand((AbstractCommand)new WorldRemoveCommand());
/* 24 */     addSubCommand((AbstractCommand)new WorldPruneCommand());
/* 25 */     addSubCommand((AbstractCommand)new WorldLoadCommand());
/* 26 */     addSubCommand((AbstractCommand)new WorldAddCommand());
/* 27 */     addSubCommand((AbstractCommand)new WorldSetDefaultCommand());
/* 28 */     addSubCommand((AbstractCommand)new WorldSaveCommand());
/* 29 */     addSubCommand((AbstractCommand)new WorldPauseCommand());
/* 30 */     addSubCommand((AbstractCommand)new WorldConfigCommand());
/* 31 */     addSubCommand((AbstractCommand)new WorldSettingsCommand());
/* 32 */     addSubCommand((AbstractCommand)new WorldPerfCommand());
/* 33 */     addSubCommand((AbstractCommand)new WorldTpsCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\commands\world\WorldCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */