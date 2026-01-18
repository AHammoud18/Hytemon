/*    */ package com.hypixel.hytale.server.core.command.commands.player.camera;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerCameraDemoSubCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public PlayerCameraDemoSubCommand() {
/* 14 */     super("demo", "server.commands.camera.demo.desc");
/* 15 */     addSubCommand((AbstractCommand)new PlayerCameraDemoActivateCommand());
/* 16 */     addSubCommand((AbstractCommand)new PlayerCameraDemoDeactivateCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\camera\PlayerCameraDemoSubCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */