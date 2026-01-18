/*    */ package com.hypixel.hytale.server.core.modules.debug.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.debug.DebugUtils;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DebugShapeShowForceCommand
/*    */   extends CommandBase
/*    */ {
/*    */   public DebugShapeShowForceCommand() {
/* 19 */     super("showforce", "server.commands.debug.shape.showforce.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 24 */     DebugUtils.DISPLAY_FORCES = !DebugUtils.DISPLAY_FORCES;
/* 25 */     context.sendMessage(Message.raw("Display forces: " + (DebugUtils.DISPLAY_FORCES ? "enabled" : "disabled")));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\debug\commands\DebugShapeShowForceCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */