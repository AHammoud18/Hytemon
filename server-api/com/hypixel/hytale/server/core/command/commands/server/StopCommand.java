/*    */ package com.hypixel.hytale.server.core.command.commands.server;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.HytaleServer;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.ShutdownReason;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StopCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 17 */   private static final Message MESSAGE_COMMANDS_STOP_SUCCESS = Message.translation("server.commands.stop.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 23 */   private final FlagArg crashFlag = withFlagArg("crash", "server.commands.stop.crash.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StopCommand() {
/* 29 */     super("stop", "server.commands.stop.desc");
/* 30 */     addAliases(new String[] { "shutdown" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 35 */     context.sendMessage(MESSAGE_COMMANDS_STOP_SUCCESS);
/* 36 */     if (this.crashFlag.provided(context)) {
/* 37 */       HytaleServer.get().shutdownServer(ShutdownReason.CRASH);
/*    */     } else {
/* 39 */       HytaleServer.get().shutdownServer();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\StopCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */