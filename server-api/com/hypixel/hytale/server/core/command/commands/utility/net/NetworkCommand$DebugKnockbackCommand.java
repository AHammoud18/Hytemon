/*    */ package com.hypixel.hytale.server.core.command.commands.utility.net;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.entity.player.KnockbackPredictionSystems;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class DebugKnockbackCommand
/*    */   extends CommandBase
/*    */ {
/*    */   DebugKnockbackCommand() {
/* 70 */     super("debugknockback", "server.commands.network.debugknockback.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 75 */     KnockbackPredictionSystems.DEBUG_KNOCKBACK_POSITION = !KnockbackPredictionSystems.DEBUG_KNOCKBACK_POSITION;
/* 76 */     context.sendMessage(Message.translation("server.commands.network.knockbackDebugEnabled")
/* 77 */         .param("enabled", KnockbackPredictionSystems.DEBUG_KNOCKBACK_POSITION));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\net\NetworkCommand$DebugKnockbackCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */