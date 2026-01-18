/*    */ package com.hypixel.hytale.server.core.command.commands.debug.stresstest;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StressTestStopCommand
/*    */   extends AbstractAsyncCommand
/*    */ {
/*    */   @Nonnull
/* 19 */   private static final Message MESSAGE_COMMANDS_STRESS_TEST_NOT_STARTED = Message.translation("server.commands.stresstest.notStarted");
/*    */   @Nonnull
/* 21 */   private static final Message MESSAGE_COMMANDS_STRESS_TEST_STOPPED = Message.translation("server.commands.stresstest.stopped");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StressTestStopCommand() {
/* 27 */     super("stop", "server.commands.stresstest.stop.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 33 */     if (!StressTestStartCommand.STATE.compareAndSet(StressTestStartCommand.StressTestState.RUNNING, StressTestStartCommand.StressTestState.STOPPING)) {
/* 34 */       context.sendMessage(MESSAGE_COMMANDS_STRESS_TEST_NOT_STARTED);
/* 35 */       return CompletableFuture.completedFuture(null);
/*    */     } 
/*    */     
/* 38 */     StressTestStartCommand.stop();
/* 39 */     context.sendMessage(MESSAGE_COMMANDS_STRESS_TEST_STOPPED);
/* 40 */     return CompletableFuture.completedFuture(null);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\stresstest\StressTestStopCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */