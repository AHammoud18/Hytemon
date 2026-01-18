/*    */ package com.hypixel.hytale.server.core.command.commands.server.auth;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.auth.ServerAuthManager;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import java.awt.Color;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuthCancelCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 17 */   private static final Message MESSAGE_SINGLEPLAYER = Message.translation("server.commands.auth.cancel.singleplayer").color(Color.RED);
/*    */   @Nonnull
/* 19 */   private static final Message MESSAGE_SUCCESS = Message.translation("server.commands.auth.cancel.success").color(Color.YELLOW);
/*    */   @Nonnull
/* 21 */   private static final Message MESSAGE_NOTHING = Message.translation("server.commands.auth.cancel.nothing").color(Color.YELLOW);
/*    */   
/*    */   public AuthCancelCommand() {
/* 24 */     super("cancel", "server.commands.auth.cancel.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 29 */     ServerAuthManager authManager = ServerAuthManager.getInstance();
/*    */     
/* 31 */     if (authManager.isSingleplayer()) {
/* 32 */       context.sendMessage(MESSAGE_SINGLEPLAYER);
/*    */       
/*    */       return;
/*    */     } 
/* 36 */     if (authManager.cancelActiveFlow()) {
/* 37 */       context.sendMessage(MESSAGE_SUCCESS);
/*    */     } else {
/* 39 */       context.sendMessage(MESSAGE_NOTHING);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\auth\AuthCancelCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */