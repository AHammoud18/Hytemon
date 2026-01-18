/*     */ package com.hypixel.hytale.server.core.command.commands.server.auth;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.auth.AuthCredentialStoreProvider;
/*     */ import com.hypixel.hytale.server.core.auth.ServerAuthManager;
/*     */ import com.hypixel.hytale.server.core.auth.SessionServiceClient;
/*     */ import com.hypixel.hytale.server.core.auth.oauth.OAuthBrowserFlow;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import java.awt.Color;
/*     */ import java.awt.Desktop;
/*     */ import java.net.URI;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthLoginBrowserCommand
/*     */   extends CommandBase
/*     */ {
/*     */   @Nonnull
/*  24 */   private static final Message MESSAGE_SINGLEPLAYER = Message.translation("server.commands.auth.login.singleplayer").color(Color.RED);
/*     */   @Nonnull
/*  26 */   private static final Message MESSAGE_ALREADY_AUTHENTICATED = Message.translation("server.commands.auth.login.alreadyAuthenticated").color(Color.YELLOW);
/*     */   @Nonnull
/*  28 */   private static final Message MESSAGE_STARTING = Message.translation("server.commands.auth.login.browser.starting").color(Color.YELLOW);
/*     */   @Nonnull
/*  30 */   private static final Message MESSAGE_SUCCESS = Message.translation("server.commands.auth.login.browser.success").color(Color.GREEN);
/*     */   @Nonnull
/*  32 */   private static final Message MESSAGE_FAILED = Message.translation("server.commands.auth.login.browser.failed").color(Color.RED);
/*     */   @Nonnull
/*  34 */   private static final Message MESSAGE_PENDING = Message.translation("server.commands.auth.login.pending").color(Color.YELLOW);
/*     */   @Nonnull
/*  36 */   private static final Message MESSAGE_PERSISTENCE_MEMORY = Message.translation("server.commands.auth.login.persistence.memory").color(Color.ORANGE);
/*     */   @Nonnull
/*  38 */   private static final Message MESSAGE_PERSISTENCE_SAVED = Message.translation("server.commands.auth.login.persistence.saved").color(Color.GREEN);
/*     */   
/*     */   private static class AuthFlow
/*     */     extends OAuthBrowserFlow {
/*     */     public void onFlowInfo(String authUrl) {
/*  43 */       AbstractCommand.LOGGER.at(Level.INFO).log("Starting OAuth browser flow...");
/*  44 */       AbstractCommand.LOGGER.at(Level.INFO).log("===================================================================");
/*  45 */       AbstractCommand.LOGGER.at(Level.INFO).log("Please open this URL in your browser to authenticate:");
/*  46 */       AbstractCommand.LOGGER.at(Level.INFO).log("%s", authUrl);
/*  47 */       AbstractCommand.LOGGER.at(Level.INFO).log("===================================================================");
/*     */ 
/*     */       
/*  50 */       if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
/*     */         try {
/*  52 */           Desktop.getDesktop().browse(new URI(authUrl));
/*  53 */           AbstractCommand.LOGGER.at(Level.INFO).log("Browser opened automatically.");
/*  54 */         } catch (Exception e) {
/*  55 */           AbstractCommand.LOGGER.at(Level.INFO).log("Could not open browser automatically. Please open the URL manually.");
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public AuthLoginBrowserCommand() {
/*  62 */     super("browser", "server.commands.auth.login.browser.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/*  67 */     ServerAuthManager authManager = ServerAuthManager.getInstance();
/*     */     
/*  69 */     if (authManager.isSingleplayer()) {
/*  70 */       context.sendMessage(MESSAGE_SINGLEPLAYER);
/*     */       
/*     */       return;
/*     */     } 
/*  74 */     if (authManager.hasSessionToken() && authManager.hasIdentityToken()) {
/*  75 */       context.sendMessage(MESSAGE_ALREADY_AUTHENTICATED);
/*     */       
/*     */       return;
/*     */     } 
/*  79 */     context.sendMessage(MESSAGE_STARTING);
/*     */     
/*  81 */     authManager.startFlowAsync(new AuthFlow()).thenAccept(result -> {
/*     */           SessionServiceClient.GameProfile[] profiles;
/*     */           switch (result) {
/*     */             case SUCCESS:
/*     */               context.sendMessage(MESSAGE_SUCCESS);
/*     */               sendPersistenceFeedback(context);
/*     */               break;
/*     */             case PENDING_PROFILE_SELECTION:
/*     */               context.sendMessage(MESSAGE_PENDING);
/*     */               profiles = authManager.getPendingProfiles();
/*     */               if (profiles != null)
/*     */                 AuthSelectCommand.sendProfileList(context, profiles); 
/*     */               break;
/*     */             case FAILED:
/*     */               context.sendMessage(MESSAGE_FAILED);
/*     */               break;
/*     */           } 
/*     */         });
/*     */   } static void sendPersistenceFeedback(@Nonnull CommandContext context) {
/* 100 */     AuthCredentialStoreProvider provider = HytaleServer.get().getConfig().getAuthCredentialStoreProvider();
/* 101 */     if (provider instanceof com.hypixel.hytale.server.core.auth.MemoryAuthCredentialStoreProvider) {
/* 102 */       String availableTypes = String.join(", ", AuthCredentialStoreProvider.CODEC.getRegisteredIds());
/* 103 */       context.sendMessage(MESSAGE_PERSISTENCE_MEMORY.param("types", availableTypes));
/*     */     } else {
/* 105 */       String typeName = (String)AuthCredentialStoreProvider.CODEC.getIdFor(provider.getClass());
/* 106 */       context.sendMessage(MESSAGE_PERSISTENCE_SAVED.param("type", typeName));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\auth\AuthLoginBrowserCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */