/*    */ package com.hypixel.hytale.server.core.modules.accesscontrol.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleWhitelistProvider;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class WhitelistClearCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 15 */   private static final Message MESSAGE_MODULES_WHITELIST_CLEARED = Message.translation("server.modules.whitelist.cleared");
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
/*    */   public WhitelistClearCommand(@Nonnull HytaleWhitelistProvider whitelistProvider) {
/* 29 */     super("clear", "server.commands.whitelist.clear.desc");
/* 30 */     this.whitelistProvider = whitelistProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 35 */     this.whitelistProvider.modify(list -> {
/*    */           list.clear();
/*    */           
/*    */           return Boolean.valueOf(true);
/*    */         });
/* 40 */     context.sendMessage(MESSAGE_MODULES_WHITELIST_CLEARED);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\commands\WhitelistClearCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */