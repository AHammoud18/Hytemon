/*    */ package com.hypixel.hytale.server.core.command.commands.debug;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageTranslationTestCommand
/*    */   extends CommandBase
/*    */ {
/*    */   public MessageTranslationTestCommand() {
/* 18 */     super("messagetest", "Test sending messages with nested translated parameter messages");
/* 19 */     addAliases(new String[] { "msgtest" });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 25 */     Message param = Message.translation("server.commands.message.container").param("content", 
/* 26 */         Message.translation("server.commands.message.example")
/* 27 */         .param("random", 25));
/*    */     
/* 29 */     context.sender().sendMessage(param);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\MessageTranslationTestCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */