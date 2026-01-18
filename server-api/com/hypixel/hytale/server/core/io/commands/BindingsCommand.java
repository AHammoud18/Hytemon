/*    */ package com.hypixel.hytale.server.core.io.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.io.ServerManager;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import io.netty.channel.Channel;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BindingsCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 18 */   private static final Message MESSAGE_IO_SERVER_MANAGER_BINDINGS = Message.translation("server.io.servermanager.bindings");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BindingsCommand() {
/* 24 */     super("bindings", "server.io.servermanager.bindings");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 29 */     List<Channel> listeners = ServerManager.get().getListeners();
/* 30 */     context.sendMessage(MessageFormat.list(MESSAGE_IO_SERVER_MANAGER_BINDINGS, (Collection)listeners
/*    */           
/* 32 */           .stream()
/* 33 */           .map(Channel::toString)
/* 34 */           .map(Message::raw)
/* 35 */           .collect(Collectors.toSet())));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\commands\BindingsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */