/*    */ package com.hypixel.hytale.server.core.modules.singleplayer.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ import com.hypixel.hytale.server.core.modules.singleplayer.SingleplayerModule;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public PlayCommand(@Nonnull SingleplayerModule singleplayerModule) {
/* 19 */     super("play", "server.commands.play.desc");
/* 20 */     addSubCommand((AbstractCommand)new PlayLanCommand(singleplayerModule));
/* 21 */     addSubCommand((AbstractCommand)new PlayFriendCommand(singleplayerModule));
/* 22 */     addSubCommand((AbstractCommand)new PlayOnlineCommand(singleplayerModule));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\singleplayer\commands\PlayCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */