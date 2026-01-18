/*    */ package com.hypixel.hytale.server.core.command.commands.server.auth;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.auth.oauth.OAuthBrowserFlow;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import java.awt.Desktop;
/*    */ import java.net.URI;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class AuthFlow
/*    */   extends OAuthBrowserFlow
/*    */ {
/*    */   public void onFlowInfo(String authUrl) {
/* 43 */     AbstractCommand.LOGGER.at(Level.INFO).log("Starting OAuth browser flow...");
/* 44 */     AbstractCommand.LOGGER.at(Level.INFO).log("===================================================================");
/* 45 */     AbstractCommand.LOGGER.at(Level.INFO).log("Please open this URL in your browser to authenticate:");
/* 46 */     AbstractCommand.LOGGER.at(Level.INFO).log("%s", authUrl);
/* 47 */     AbstractCommand.LOGGER.at(Level.INFO).log("===================================================================");
/*    */ 
/*    */     
/* 50 */     if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
/*    */       try {
/* 52 */         Desktop.getDesktop().browse(new URI(authUrl));
/* 53 */         AbstractCommand.LOGGER.at(Level.INFO).log("Browser opened automatically.");
/* 54 */       } catch (Exception e) {
/* 55 */         AbstractCommand.LOGGER.at(Level.INFO).log("Could not open browser automatically. Please open the URL manually.");
/*    */       }  
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\auth\AuthLoginBrowserCommand$AuthFlow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */