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
/*    */ public class PlayLanCommand
/*    */   extends PlayCommandBase
/*    */ {
/*    */   public PlayLanCommand(@Nonnull SingleplayerModule singleplayerModule) {
/* 19 */     super("lan", "server.commands.play.lan.desc", singleplayerModule, Access.LAN);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\singleplayer\commands\PlayLanCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */