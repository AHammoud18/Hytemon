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
/*    */ public class PlayFriendCommand
/*    */   extends PlayCommandBase
/*    */ {
/*    */   public PlayFriendCommand(@Nonnull SingleplayerModule singleplayerModule) {
/* 19 */     super("friend", "server.commands.play.friend.desc", singleplayerModule, Access.Friend);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\singleplayer\commands\PlayFriendCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */