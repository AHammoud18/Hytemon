/*    */ package com.hypixel.hytale.server.core.modules.accesscontrol.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ import com.hypixel.hytale.server.core.modules.accesscontrol.provider.HytaleWhitelistProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WhitelistCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public WhitelistCommand(@Nonnull HytaleWhitelistProvider whitelistProvider) {
/* 19 */     super("whitelist", "server.commands.whitelist.desc");
/* 20 */     addSubCommand((AbstractCommand)new WhitelistAddCommand(whitelistProvider));
/* 21 */     addSubCommand((AbstractCommand)new WhitelistRemoveCommand(whitelistProvider));
/* 22 */     addSubCommand((AbstractCommand)new WhitelistEnableCommand(whitelistProvider));
/* 23 */     addSubCommand((AbstractCommand)new WhitelistDisableCommand(whitelistProvider));
/* 24 */     addSubCommand((AbstractCommand)new WhitelistClearCommand(whitelistProvider));
/* 25 */     addSubCommand((AbstractCommand)new WhitelistStatusCommand(whitelistProvider));
/* 26 */     addSubCommand((AbstractCommand)new WhitelistListCommand(whitelistProvider));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\commands\WhitelistCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */