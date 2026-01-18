/*    */ package com.hypixel.hytale.server.core.command.commands.server.auth;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.auth.ServerAuthManager;
/*    */ import com.hypixel.hytale.server.core.auth.SessionServiceClient;
/*    */ import com.hypixel.hytale.server.core.auth.oauth.OAuthDeviceFlow;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import java.awt.Color;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuthLoginDeviceCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 20 */   private static final Message MESSAGE_SINGLEPLAYER = Message.translation("server.commands.auth.login.singleplayer").color(Color.RED);
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_ALREADY_AUTHENTICATED = Message.translation("server.commands.auth.login.alreadyAuthenticated").color(Color.YELLOW);
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_STARTING = Message.translation("server.commands.auth.login.device.starting").color(Color.YELLOW);
/*    */   @Nonnull
/* 26 */   private static final Message MESSAGE_SUCCESS = Message.translation("server.commands.auth.login.device.success").color(Color.GREEN);
/*    */   @Nonnull
/* 28 */   private static final Message MESSAGE_FAILED = Message.translation("server.commands.auth.login.device.failed").color(Color.RED);
/*    */   @Nonnull
/* 30 */   private static final Message MESSAGE_PENDING = Message.translation("server.commands.auth.login.pending").color(Color.YELLOW);
/*    */   
/*    */   private static class AuthFlow
/*    */     extends OAuthDeviceFlow {
/*    */     public void onFlowInfo(String userCode, String verificationUri, String verificationUriComplete, int expiresIn) {
/* 35 */       AbstractCommand.LOGGER.at(Level.INFO).log("===================================================================");
/* 36 */       AbstractCommand.LOGGER.at(Level.INFO).log("DEVICE AUTHORIZATION");
/* 37 */       AbstractCommand.LOGGER.at(Level.INFO).log("===================================================================");
/* 38 */       AbstractCommand.LOGGER.at(Level.INFO).log("Visit: %s", verificationUri);
/* 39 */       AbstractCommand.LOGGER.at(Level.INFO).log("Enter code: %s", userCode);
/* 40 */       if (verificationUriComplete != null) {
/* 41 */         AbstractCommand.LOGGER.at(Level.INFO).log("Or visit: %s", verificationUriComplete);
/*    */       }
/* 43 */       AbstractCommand.LOGGER.at(Level.INFO).log("===================================================================");
/* 44 */       AbstractCommand.LOGGER.at(Level.INFO).log("Waiting for authorization (expires in %d seconds)...", expiresIn);
/*    */     }
/*    */   }
/*    */   
/*    */   public AuthLoginDeviceCommand() {
/* 49 */     super("device", "server.commands.auth.login.device.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 54 */     ServerAuthManager authManager = ServerAuthManager.getInstance();
/*    */     
/* 56 */     if (authManager.isSingleplayer()) {
/* 57 */       context.sendMessage(MESSAGE_SINGLEPLAYER);
/*    */       
/*    */       return;
/*    */     } 
/* 61 */     if (authManager.hasSessionToken() && authManager.hasIdentityToken()) {
/* 62 */       context.sendMessage(MESSAGE_ALREADY_AUTHENTICATED);
/*    */       
/*    */       return;
/*    */     } 
/* 66 */     context.sendMessage(MESSAGE_STARTING);
/*    */     
/* 68 */     authManager.startFlowAsync(new AuthFlow()).thenAccept(result -> {
/*    */           SessionServiceClient.GameProfile[] profiles;
/*    */           switch (result) {
/*    */             case SUCCESS:
/*    */               context.sendMessage(MESSAGE_SUCCESS);
/*    */               AuthLoginBrowserCommand.sendPersistenceFeedback(context);
/*    */               break;
/*    */             case PENDING_PROFILE_SELECTION:
/*    */               context.sendMessage(MESSAGE_PENDING);
/*    */               profiles = authManager.getPendingProfiles();
/*    */               if (profiles != null)
/*    */                 AuthSelectCommand.sendProfileList(context, profiles); 
/*    */               break;
/*    */             case FAILED:
/*    */               context.sendMessage(MESSAGE_FAILED);
/*    */               break;
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\auth\AuthLoginDeviceCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */