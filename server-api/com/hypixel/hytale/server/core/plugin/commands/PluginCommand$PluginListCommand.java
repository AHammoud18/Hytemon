/*    */ package com.hypixel.hytale.server.core.plugin.commands;
/*    */ 
/*    */ import com.hypixel.hytale.common.plugin.PluginIdentifier;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.plugin.PluginBase;
/*    */ import com.hypixel.hytale.server.core.plugin.PluginManager;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PluginListCommand
/*    */   extends CommandBase
/*    */ {
/*    */   public PluginListCommand() {
/* 78 */     super("list", "server.commands.plugin.list.desc");
/* 79 */     addAliases(new String[] { "ls" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 84 */     PluginManager module = PluginManager.get();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 89 */     Set<Message> plugins = (Set<Message>)module.getPlugins().stream().map(PluginBase::getIdentifier).map(PluginIdentifier::toString).map(Message::raw).collect(Collectors.toSet());
/* 90 */     context.sendMessage(MessageFormat.list(Message.translation("server.commands.plugin.plugins"), plugins));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\commands\PluginCommand$PluginListCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */