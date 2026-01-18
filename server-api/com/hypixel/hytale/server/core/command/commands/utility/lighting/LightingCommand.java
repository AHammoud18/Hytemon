/*    */ package com.hypixel.hytale.server.core.command.commands.utility.lighting;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LightingCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public LightingCommand() {
/* 14 */     super("lighting", "server.commands.lighting.desc");
/* 15 */     addAliases(new String[] { "light" });
/* 16 */     addSubCommand((AbstractCommand)new LightingCalculationCommand());
/* 17 */     addSubCommand((AbstractCommand)new LightingGetCommand());
/* 18 */     addSubCommand((AbstractCommand)new LightingInvalidateCommand());
/* 19 */     addSubCommand((AbstractCommand)new LightingInfoCommand());
/* 20 */     addSubCommand((AbstractCommand)new LightingSendCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\lighting\LightingCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */