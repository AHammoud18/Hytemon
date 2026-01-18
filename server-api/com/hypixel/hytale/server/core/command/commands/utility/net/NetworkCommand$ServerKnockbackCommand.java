/*    */ package com.hypixel.hytale.server.core.command.commands.utility.net;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.entity.knockback.KnockbackSystems;
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
/*    */ class ServerKnockbackCommand
/*    */   extends CommandBase
/*    */ {
/*    */   ServerKnockbackCommand() {
/* 50 */     super("serverknockback", "server.commands.network.serverknockback.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 55 */     KnockbackSystems.ApplyPlayerKnockback.DO_SERVER_PREDICTION = !KnockbackSystems.ApplyPlayerKnockback.DO_SERVER_PREDICTION;
/* 56 */     context.sendMessage(Message.translation("server.commands.network.knockbackServerPredictionEnabled")
/* 57 */         .param("enabled", KnockbackSystems.ApplyPlayerKnockback.DO_SERVER_PREDICTION));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\net\NetworkCommand$ServerKnockbackCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */