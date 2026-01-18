/*    */ package com.hypixel.hytale.server.core.modules.i18n.commands;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ public class InternationalizationCommands extends AbstractCommandCollection {
/*    */   public InternationalizationCommands() {
/*  7 */     super("lang", "server.commands.i18n.desc");
/*  8 */     addAliases(new String[] { "internationalization", "il8n", "translation" });
/*    */     
/* 10 */     addSubCommand((AbstractCommand)new GenerateI18nCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\i18n\commands\InternationalizationCommands.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */