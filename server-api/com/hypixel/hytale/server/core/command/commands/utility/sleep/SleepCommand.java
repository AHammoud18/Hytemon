/*    */ package com.hypixel.hytale.server.core.command.commands.utility.sleep;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SleepCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public SleepCommand() {
/* 14 */     super("sleep", "server.commands.sleep.desc");
/* 15 */     addSubCommand((AbstractCommand)new SleepOffsetCommand());
/* 16 */     addSubCommand((AbstractCommand)new SleepTestCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\sleep\SleepCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */