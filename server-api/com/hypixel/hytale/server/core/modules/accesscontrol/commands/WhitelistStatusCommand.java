/*    */ package com.hypixel.hytale.server.core.modules.accesscontrol.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleWhitelistProvider;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WhitelistStatusCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 16 */   private static final Message MESSAGE_MODULES_WHITELIST_STATUS = Message.translation("server.modules.whitelist.status");
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
/*    */   public WhitelistStatusCommand(@Nonnull HytaleWhitelistProvider whitelistProvider) {
/* 30 */     super("status", "server.commands.whitelist.status.desc");
/* 31 */     this.whitelistProvider = whitelistProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 36 */     context.sendMessage(MESSAGE_MODULES_WHITELIST_STATUS
/* 37 */         .param("status", MessageFormat.enabled(this.whitelistProvider.isEnabled())));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\commands\WhitelistStatusCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */