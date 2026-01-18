/*    */ package com.hypixel.hytale.server.core.command.commands.debug.stresstest;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StressTestCommand
/*    */   extends AbstractCommandCollection
/*    */ {
/*    */   public StressTestCommand() {
/* 14 */     super("stresstest", "server.commands.stresstest.desc");
/* 15 */     addSubCommand((AbstractCommand)new StressTestStartCommand());
/* 16 */     addSubCommand((AbstractCommand)new StressTestStopCommand());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\stresstest\StressTestCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */