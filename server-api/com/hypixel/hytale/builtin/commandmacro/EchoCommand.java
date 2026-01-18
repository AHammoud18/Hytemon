/*    */ package com.hypixel.hytale.builtin.commandmacro;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class EchoCommand extends CommandBase {
/* 12 */   private final RequiredArg<String> messageArg = withRequiredArg("message", "The message to send to the user of this command", (ArgumentType)ArgTypes.STRING);
/*    */   
/*    */   public EchoCommand() {
/* 15 */     super("echo", "Echos the text you input to the user");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 20 */     context.sender().sendMessage(Message.raw(((String)this.messageArg.get(context)).replace("\"", "")));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\commandmacro\EchoCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */