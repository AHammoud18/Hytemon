/*    */ package com.hypixel.hytale.server.core.command.commands.server.auth;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.auth.oauth.OAuthDeviceFlow;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import java.util.logging.Level;
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
/*    */ class AuthFlow
/*    */   extends OAuthDeviceFlow
/*    */ {
/*    */   public void onFlowInfo(String userCode, String verificationUri, String verificationUriComplete, int expiresIn) {
/* 35 */     AbstractCommand.LOGGER.at(Level.INFO).log("===================================================================");
/* 36 */     AbstractCommand.LOGGER.at(Level.INFO).log("DEVICE AUTHORIZATION");
/* 37 */     AbstractCommand.LOGGER.at(Level.INFO).log("===================================================================");
/* 38 */     AbstractCommand.LOGGER.at(Level.INFO).log("Visit: %s", verificationUri);
/* 39 */     AbstractCommand.LOGGER.at(Level.INFO).log("Enter code: %s", userCode);
/* 40 */     if (verificationUriComplete != null) {
/* 41 */       AbstractCommand.LOGGER.at(Level.INFO).log("Or visit: %s", verificationUriComplete);
/*    */     }
/* 43 */     AbstractCommand.LOGGER.at(Level.INFO).log("===================================================================");
/* 44 */     AbstractCommand.LOGGER.at(Level.INFO).log("Waiting for authorization (expires in %d seconds)...", expiresIn);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\auth\AuthLoginDeviceCommand$AuthFlow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */