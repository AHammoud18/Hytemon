/*    */ package com.hypixel.hytale.builtin.instances.command;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class InstanceEditListCommand extends AbstractAsyncCommand {
/*    */   public InstanceEditListCommand() {
/* 14 */     super("list", "server.commands.instances.edit.list.desc");
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/* 19 */     List<String> instanceAssets = InstancesPlugin.get().getInstanceAssets();
/*    */     
/* 21 */     context.sendMessage(MessageFormat.list(
/* 22 */           Message.translation("server.commands.instances.edit.list.header"), instanceAssets
/* 23 */           .stream().map(Message::raw).toList()));
/*    */     
/* 25 */     return CompletableFuture.completedFuture(null);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\command\InstanceEditListCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */