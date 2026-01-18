/*    */ package com.hypixel.hytale.server.core.command.commands.debug;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.SelectInteraction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HitDetectionCommand
/*    */   extends CommandBase
/*    */ {
/*    */   public HitDetectionCommand() {
/* 20 */     super("hitdetection", "server.commands.hitdetection.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 25 */     SelectInteraction.SHOW_VISUAL_DEBUG = !SelectInteraction.SHOW_VISUAL_DEBUG;
/* 26 */     context.sendMessage(Message.translation("server.commands.hitdetection.toggled")
/* 27 */         .param("debug", SelectInteraction.SHOW_VISUAL_DEBUG));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\HitDetectionCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */