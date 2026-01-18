/*     */ package com.hypixel.hytale.server.core.command.commands.server.auth;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.Options;
/*     */ import com.hypixel.hytale.server.core.auth.ServerAuthManager;
/*     */ import com.hypixel.hytale.server.core.auth.SessionServiceClient;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import java.awt.Color;
/*     */ import java.time.Instant;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthStatusCommand
/*     */   extends CommandBase
/*     */ {
/*     */   @Nonnull
/*  20 */   private static final Message MESSAGE_STATUS_HEADER = Message.translation("server.commands.auth.status.format");
/*     */   @Nonnull
/*  22 */   private static final Message MESSAGE_STATUS_CONNECTION_MODE_AUTHENTICATED = Message.translation("server.commands.auth.status.connectionMode.authenticated").color(Color.GREEN);
/*     */   @Nonnull
/*  24 */   private static final Message MESSAGE_STATUS_CONNECTION_MODE_OFFLINE = Message.translation("server.commands.auth.status.connectionMode.offline").color(Color.YELLOW);
/*     */   @Nonnull
/*  26 */   private static final Message MESSAGE_STATUS_CONNECTION_MODE_INSECURE = Message.translation("server.commands.auth.status.connectionMode.insecure").color(Color.ORANGE);
/*     */   @Nonnull
/*  28 */   private static final Message MESSAGE_STATUS_MODE_NONE = Message.translation("server.commands.auth.status.mode.none").color(Color.RED);
/*     */   @Nonnull
/*  30 */   private static final Message MESSAGE_STATUS_MODE_SINGLEPLAYER = Message.translation("server.commands.auth.status.mode.singleplayer").color(Color.GREEN);
/*     */   @Nonnull
/*  32 */   private static final Message MESSAGE_STATUS_MODE_EXTERNAL = Message.translation("server.commands.auth.status.mode.external").color(Color.GREEN);
/*     */   @Nonnull
/*  34 */   private static final Message MESSAGE_STATUS_MODE_OAUTH_BROWSER = Message.translation("server.commands.auth.status.mode.oauthBrowser").color(Color.GREEN);
/*     */   @Nonnull
/*  36 */   private static final Message MESSAGE_STATUS_MODE_OAUTH_DEVICE = Message.translation("server.commands.auth.status.mode.oauthDevice").color(Color.GREEN);
/*     */   @Nonnull
/*  38 */   private static final Message MESSAGE_STATUS_MODE_OAUTH_STORE = Message.translation("server.commands.auth.status.mode.oauthStore").color(Color.GREEN);
/*     */   @Nonnull
/*  40 */   private static final Message MESSAGE_STATUS_TOKEN_PRESENT = Message.translation("server.commands.auth.status.tokenPresent").color(Color.GREEN);
/*     */   @Nonnull
/*  42 */   private static final Message MESSAGE_STATUS_TOKEN_MISSING = Message.translation("server.commands.auth.status.tokenMissing").color(Color.RED);
/*     */   @Nonnull
/*  44 */   private static final Message MESSAGE_STATUS_HELP = Message.translation("server.commands.auth.status.help").color(Color.YELLOW);
/*     */   @Nonnull
/*  46 */   private static final Message MESSAGE_STATUS_PENDING = Message.translation("server.commands.auth.status.pending").color(Color.YELLOW);
/*     */   
/*     */   public AuthStatusCommand() {
/*  49 */     super("status", "server.commands.auth.status.desc");
/*     */   }
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/*     */     String certificateStatus;
/*  54 */     ServerAuthManager authManager = ServerAuthManager.getInstance();
/*     */     
/*  56 */     switch ((Options.AuthMode)Options.getOptionSet().valueOf(Options.AUTH_MODE)) { default: throw new MatchException(null, null);case NONE: case SINGLEPLAYER: case EXTERNAL_SESSION: break; }  Message connectionModeMessage = 
/*     */ 
/*     */       
/*  59 */       MESSAGE_STATUS_CONNECTION_MODE_INSECURE;
/*     */ 
/*     */     
/*  62 */     ServerAuthManager.AuthMode mode = authManager.getAuthMode();
/*  63 */     switch (mode) { default: throw new MatchException(null, null);case NONE: case SINGLEPLAYER: case EXTERNAL_SESSION: case OAUTH_BROWSER: case OAUTH_DEVICE: case OAUTH_STORE: break; }  Message modeMessage = 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  69 */       MESSAGE_STATUS_MODE_OAUTH_STORE;
/*     */ 
/*     */     
/*  72 */     String profileInfo = "";
/*  73 */     SessionServiceClient.GameProfile profile = authManager.getSelectedProfile();
/*  74 */     if (profile != null) {
/*  75 */       String name = (profile.username != null) ? profile.username : "Unknown";
/*  76 */       profileInfo = name + " (" + name + ")";
/*     */     } 
/*     */     
/*  79 */     Message sessionTokenStatus = authManager.hasSessionToken() ? MESSAGE_STATUS_TOKEN_PRESENT : MESSAGE_STATUS_TOKEN_MISSING;
/*  80 */     Message identityTokenStatus = authManager.hasIdentityToken() ? MESSAGE_STATUS_TOKEN_PRESENT : MESSAGE_STATUS_TOKEN_MISSING;
/*     */     
/*  82 */     String expiryStatus = "";
/*  83 */     Instant expiry = authManager.getTokenExpiry();
/*  84 */     if (expiry != null) {
/*  85 */       long secondsRemaining = expiry.getEpochSecond() - Instant.now().getEpochSecond();
/*  86 */       if (secondsRemaining > 0L) {
/*  87 */         long hours = secondsRemaining / 3600L;
/*  88 */         long minutes = secondsRemaining % 3600L / 60L;
/*  89 */         long seconds = secondsRemaining % 60L;
/*  90 */         expiryStatus = String.format("%02d:%02d:%02d remaining", new Object[] { Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds) });
/*     */       } else {
/*  92 */         expiryStatus = "EXPIRED";
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  97 */     if (authManager.getServerCertificate() != null) {
/*  98 */       String fingerprint = authManager.getServerCertificateFingerprint();
/*  99 */       certificateStatus = (fingerprint != null) ? (fingerprint.substring(0, 16) + "...") : "Unknown";
/*     */     } else {
/* 101 */       certificateStatus = "Not loaded";
/*     */     } 
/*     */     
/* 104 */     context.sendMessage(MESSAGE_STATUS_HEADER
/* 105 */         .param("connectionMode", connectionModeMessage)
/* 106 */         .param("tokenMode", modeMessage)
/* 107 */         .param("profile", profileInfo)
/* 108 */         .param("sessionToken", sessionTokenStatus)
/* 109 */         .param("identityToken", identityTokenStatus)
/* 110 */         .param("expiry", expiryStatus)
/* 111 */         .param("certificate", certificateStatus));
/*     */     
/* 113 */     if (mode == ServerAuthManager.AuthMode.NONE && !authManager.isSingleplayer())
/* 114 */       if (authManager.hasPendingProfiles()) {
/* 115 */         context.sendMessage(MESSAGE_STATUS_PENDING);
/* 116 */         SessionServiceClient.GameProfile[] profiles = authManager.getPendingProfiles();
/* 117 */         if (profiles != null) {
/* 118 */           AuthSelectCommand.sendProfileList(context, profiles);
/*     */         }
/*     */       } else {
/* 121 */         context.sendMessage(MESSAGE_STATUS_HELP);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\auth\AuthStatusCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */