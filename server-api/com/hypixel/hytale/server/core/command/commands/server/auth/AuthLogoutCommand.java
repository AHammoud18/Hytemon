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
/*    */ public class AuthLogoutCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 17 */   private static final Message MESSAGE_SINGLEPLAYER = Message.translation("server.commands.auth.logout.singleplayer").color(Color.RED);
/*    */   @Nonnull
/* 19 */   private static final Message MESSAGE_NOT_AUTHENTICATED = Message.translation("server.commands.auth.logout.notAuthenticated").color(Color.YELLOW);
/*    */   @Nonnull
/* 21 */   private static final Message MESSAGE_SUCCESS = Message.translation("server.commands.auth.logout.success").color(Color.GREEN);
/*    */   
/*    */   public AuthLogoutCommand() {
/* 24 */     super("logout", "server.commands.auth.logout.desc");
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
/* 36 */     if (!authManager.hasIdentityToken() && !authManager.hasSessionToken()) {
/* 37 */       context.sendMessage(MESSAGE_NOT_AUTHENTICATED);
/*    */       
/*    */       return;
/*    */     } 
/* 41 */     ServerAuthManager.AuthMode previousMode = authManager.getAuthMode();
/* 42 */     authManager.logout();
/* 43 */     context.sendMessage(MESSAGE_SUCCESS.param("previousMode", previousMode.name()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\auth\AuthLogoutCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */