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
/*    */ public class WhitelistDisableCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 15 */   private static final Message MESSAGE_MODULES_WHITELIST_DISABLED = Message.translation("server.modules.whitelist.disabled");
/*    */   @Nonnull
/* 17 */   private static final Message MESSAGE_MODULES_WHITELIST_ALREADY_DISABLED = Message.translation("server.modules.whitelist.alreadyDisabled");
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
/*    */   public WhitelistDisableCommand(@Nonnull HytaleWhitelistProvider whitelistProvider) {
/* 31 */     super("disable", "server.commands.whitelist.disable.desc");
/* 32 */     addAliases(new String[] { "off" });
/* 33 */     setUnavailableInSingleplayer(true);
/* 34 */     this.whitelistProvider = whitelistProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 39 */     if (this.whitelistProvider.isEnabled()) {
/* 40 */       this.whitelistProvider.setEnabled(false);
/* 41 */       context.sendMessage(MESSAGE_MODULES_WHITELIST_DISABLED);
/*    */     } else {
/* 43 */       context.sendMessage(MESSAGE_MODULES_WHITELIST_ALREADY_DISABLED);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\commands\WhitelistDisableCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */