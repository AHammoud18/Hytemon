/*    */ package com.hypixel.hytale.server.core.command.commands.debug;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Constants;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.Options;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.util.ProcessUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class PIDCheckCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 20 */   private static final Message MESSAGE_COMMANDS_PID_CHECK_SINGLEPLAYER_ONLY = Message.translation("server.commands.pidcheck.singlePlayerOnly");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 26 */   private final FlagArg singleplayerFlag = withFlagArg("singleplayer", "server.commands.pidcheck.singleplayer.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 32 */   private final OptionalArg<Integer> pidArg = withOptionalArg("pid", "server.commands.pidcheck.pid.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PIDCheckCommand() {
/* 38 */     super("pidcheck", "server.commands.pidcheck.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 43 */     if (((Boolean)this.singleplayerFlag.get(context)).booleanValue()) {
/*    */       
/* 45 */       if (!Constants.SINGLEPLAYER) {
/* 46 */         context.sendMessage(MESSAGE_COMMANDS_PID_CHECK_SINGLEPLAYER_ONLY);
/*    */         
/*    */         return;
/*    */       } 
/* 50 */       int i = ((Integer)Options.getOptionSet().valueOf(Options.CLIENT_PID)).intValue();
/* 51 */       Message message = Message.translation(ProcessUtil.isProcessRunning(i) ? "server.commands.pidcheck.isRunning" : "server.commands.pidcheck.isNotRunning");
/* 52 */       context.sendMessage(Message.translation("server.commands.pidcheck.clientPIDRunning")
/* 53 */           .param("pid", i)
/* 54 */           .param("running", message));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 59 */     if (!this.pidArg.provided(context)) {
/* 60 */       context.sendMessage(Message.translation("server.commands.pidcheck.pidRequired"));
/*    */       
/*    */       return;
/*    */     } 
/* 64 */     int pid = ((Integer)this.pidArg.get(context)).intValue();
/* 65 */     Message runningMessage = Message.translation(ProcessUtil.isProcessRunning(pid) ? "server.commands.pidcheck.isRunning" : "server.commands.pidcheck.isNotRunning");
/* 66 */     context.sendMessage(Message.translation("server.commands.pidcheck.PIDRunning")
/* 67 */         .param("pid", pid)
/* 68 */         .param("running", runningMessage));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\PIDCheckCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */