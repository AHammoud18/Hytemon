/*    */ package com.hypixel.hytale.server.core.command.commands.utility;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.packets.interface_.NotificationStyle;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.util.NotificationUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NotifyCommand
/*    */   extends CommandBase
/*    */ {
/*    */   public NotifyCommand() {
/* 21 */     super("notify", "server.commands.notify.desc");
/* 22 */     setAllowsExtraArguments(true);
/*    */   }
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/*    */     Message message;
/* 27 */     String inputString = context.getInputString();
/* 28 */     String rawArgs = CommandUtil.stripCommandName(inputString).trim();
/*    */     
/* 30 */     if (rawArgs.isEmpty()) {
/* 31 */       context.sendMessage(Message.translation("server.commands.parsing.error.wrongNumberRequiredParameters")
/* 32 */           .param("expected", 1)
/* 33 */           .param("actual", 0));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 38 */     String[] args = rawArgs.split("\\s+");
/* 39 */     if (args.length == 0) {
/* 40 */       context.sendMessage(Message.translation("server.commands.parsing.error.wrongNumberRequiredParameters")
/* 41 */           .param("expected", 1)
/* 42 */           .param("actual", 0));
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     NotificationStyle style = NotificationStyle.Default;
/* 47 */     int messageStartIndex = 0;
/*    */ 
/*    */     
/* 50 */     if (args.length >= 2) {
/* 51 */       String firstArg = args[0];
/* 52 */       if (!firstArg.startsWith("{")) {
/*    */         try {
/* 54 */           style = NotificationStyle.valueOf(firstArg.toUpperCase());
/* 55 */           messageStartIndex = 1;
/* 56 */         } catch (IllegalArgumentException illegalArgumentException) {}
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 63 */     StringBuilder messageBuilder = new StringBuilder();
/* 64 */     for (int i = messageStartIndex; i < args.length; i++) {
/* 65 */       if (i > messageStartIndex) {
/* 66 */         messageBuilder.append(' ');
/*    */       }
/* 68 */       messageBuilder.append(args[i]);
/*    */     } 
/* 70 */     String messageString = messageBuilder.toString();
/*    */ 
/*    */ 
/*    */     
/* 74 */     if (messageString.startsWith("{")) {
/*    */       try {
/* 76 */         message = Message.parse(messageString);
/* 77 */       } catch (IllegalArgumentException e) {
/* 78 */         context.sendMessage(Message.raw("Invalid formatted message: " + e.getMessage()));
/*    */         return;
/*    */       } 
/*    */     } else {
/* 82 */       message = Message.raw(messageString);
/*    */     } 
/*    */     
/* 85 */     Message senderName = Message.raw(context.sender().getDisplayName());
/*    */ 
/*    */     
/* 88 */     NotificationUtil.sendNotificationToUniverse(message, senderName, "announcement", null, style);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\NotifyCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */