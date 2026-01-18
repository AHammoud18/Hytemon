/*    */ package com.hypixel.hytale.server.core.modules.singleplayer.commands;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.packets.serveraccess.Access;
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
/*    */ 
/*    */ public class PlayOnlineCommand
/*    */   extends PlayCommandBase
/*    */ {
/*    */   public PlayOnlineCommand(@Nonnull SingleplayerModule singleplayerModule) {
/* 19 */     super("online", "server.commands.play.online.desc", singleplayerModule, Access.Open);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\singleplayer\commands\PlayOnlineCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */