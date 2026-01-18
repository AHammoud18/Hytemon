/*    */ package com.hypixel.hytale.server.core.command.commands.player;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.commands.player.camera.PlayerCameraSubCommand;
/*    */ import com.hypixel.hytale.server.core.command.commands.player.effect.PlayerEffectSubCommand;
/*    */ import com.hypixel.hytale.server.core.command.commands.player.stats.PlayerStatsSubCommand;
/*    */ import com.hypixel.hytale.server.core.command.commands.player.viewradius.PlayerViewRadiusSubCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public PlayerCommand() {
/* 18 */     super("player", "server.commands.player.desc");
/* 19 */     addSubCommand((AbstractCommand)new PlayerResetCommand());
/* 20 */     addSubCommand((AbstractCommand)new PlayerStatsSubCommand());
/* 21 */     addSubCommand((AbstractCommand)new PlayerEffectSubCommand());
/* 22 */     addSubCommand((AbstractCommand)new PlayerRespawnCommand());
/* 23 */     addSubCommand((AbstractCommand)new PlayerCameraSubCommand());
/* 24 */     addSubCommand((AbstractCommand)new PlayerViewRadiusSubCommand());
/* 25 */     addSubCommand((AbstractCommand)new PlayerZoneCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\PlayerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */