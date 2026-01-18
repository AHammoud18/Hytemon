/*    */ package com.hypixel.hytale.server.core.modules.accesscontrol.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleWhitelistProvider;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class WhitelistListCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/*    */   private final HytaleWhitelistProvider whitelistProvider;
/*    */   
/*    */   public WhitelistListCommand(@Nonnull HytaleWhitelistProvider whitelistProvider) {
/* 29 */     super("list", "server.commands.whitelist.list.desc");
/* 30 */     this.whitelistProvider = whitelistProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 35 */     Set<UUID> whitelist = this.whitelistProvider.getList();
/*    */ 
/*    */ 
/*    */     
/* 39 */     if (whitelist.isEmpty() || whitelist.size() > 10) {
/* 40 */       context.sendMessage(Message.translation("server.modules.whitelist.size")
/* 41 */           .param("size", whitelist.size()));
/*    */     } else {
/* 43 */       context.sendMessage(Message.translation("server.modules.whitelist.list")
/* 44 */           .param("whitelist", whitelist.toString()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\commands\WhitelistListCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */