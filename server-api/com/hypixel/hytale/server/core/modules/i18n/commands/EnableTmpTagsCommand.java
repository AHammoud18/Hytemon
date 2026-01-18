/*    */ package com.hypixel.hytale.server.core.modules.i18n.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.HytaleServer;
/*    */ import com.hypixel.hytale.server.core.HytaleServerConfig;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EnableTmpTagsCommand
/*    */   extends CommandBase {
/*    */   public EnableTmpTagsCommand() {
/* 13 */     super("toggletmptags", "server.commands.toggleTmpTags.desc");
/* 14 */     addAliases(new String[] { "tmptag", "tmptags", "tmpstring", "tmpstrings", "tmptext" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 19 */     HytaleServerConfig config = HytaleServer.get().getConfig();
/* 20 */     if (config == null)
/*    */       return; 
/* 22 */     boolean displayTmpTags = !config.isDisplayTmpTagsInStrings();
/* 23 */     config.setDisplayTmpTagsInStrings(!displayTmpTags);
/*    */     
/* 25 */     context.sendMessage(Message.translation("server.commands.toggleTmpTags." + (displayTmpTags ? "enabled" : "disabled")));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\i18n\commands\EnableTmpTagsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */