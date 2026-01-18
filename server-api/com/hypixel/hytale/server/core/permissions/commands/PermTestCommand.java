/*    */ package com.hypixel.hytale.server.core.permissions.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PermTestCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 22 */   private final RequiredArg<List<String>> nodesArg = withListRequiredArg("nodes", "server.commands.perm.test.nodes.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PermTestCommand() {
/* 28 */     super("test", "server.commands.testperm.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 33 */     CommandSender sender = context.sender();
/* 34 */     List<String> nodes = (List<String>)this.nodesArg.get(context);
/*    */     
/* 36 */     for (String node : nodes)
/* 37 */       context.sendMessage(Message.translation("server.commands.testperm.hasPermission")
/* 38 */           .param("permission", node)
/* 39 */           .param("hasPermission", sender.hasPermission(node))); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\commands\PermTestCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */