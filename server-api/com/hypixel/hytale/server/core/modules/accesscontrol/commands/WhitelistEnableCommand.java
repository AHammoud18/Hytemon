/*    */ package com.hypixel.hytale.server.core.modules.accesscontrol.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleWhitelistProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WhitelistEnableCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 15 */   private static final Message MESSAGE_MODULES_WHITELIST_ALREADY_ENABLED = Message.translation("server.modules.whitelist.alreadyEnabled");
/*    */   @Nonnull
/* 17 */   private static final Message MESSAGE_MODULES_WHITELIST_ENABLED = Message.translation("server.modules.whitelist.enabled");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   private final HytaleWhitelistProvider whitelistProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WhitelistEnableCommand(@Nonnull HytaleWhitelistProvider whitelistProvider) {
/* 31 */     super("enable", "server.commands.whitelist.enable.desc");
/* 32 */     addAliases(new String[] { "on" });
/* 33 */     setUnavailableInSingleplayer(true);
/* 34 */     this.whitelistProvider = whitelistProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 39 */     if (this.whitelistProvider.isEnabled()) {
/* 40 */       context.sendMessage(MESSAGE_MODULES_WHITELIST_ALREADY_ENABLED);
/*    */     } else {
/* 42 */       this.whitelistProvider.setEnabled(true);
/* 43 */       context.sendMessage(MESSAGE_MODULES_WHITELIST_ENABLED);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\commands\WhitelistEnableCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */