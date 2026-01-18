/*    */ package com.hypixel.hytale.server.core.command.commands.player.effect;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerEffectSubCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public PlayerEffectSubCommand() {
/* 14 */     super("effect", "server.commands.player.effect.desc");
/* 15 */     addSubCommand((AbstractCommand)new PlayerEffectApplyCommand());
/* 16 */     addSubCommand((AbstractCommand)new PlayerEffectClearCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\effect\PlayerEffectSubCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */