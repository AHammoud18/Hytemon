/*    */ package com.hypixel.hytale.server.core.permissions.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.permissions.PermissionsModule;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
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
/*    */ class PermGroupAddCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 71 */   private final RequiredArg<String> groupArg = withRequiredArg("group", "server.commands.perm.group.add.group.desc", (ArgumentType)ArgTypes.STRING);
/*    */   
/*    */   @Nonnull
/* 74 */   private final RequiredArg<List<String>> permissionsArg = withListRequiredArg("permissions", "server.commands.perm.group.add.permissions.desc", (ArgumentType)ArgTypes.STRING);
/*    */   
/*    */   public PermGroupAddCommand() {
/* 77 */     super("add", "server.commands.perm.group.add.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 82 */     String group = (String)this.groupArg.get(context);
/* 83 */     HashSet<String> permissions = new HashSet<>((Collection<? extends String>)this.permissionsArg.get(context));
/*    */     
/* 85 */     PermissionsModule.get().addGroupPermission(group, permissions);
/* 86 */     context.sendMessage(Message.translation("server.commands.perm.addPermToGroup").param("group", group));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\commands\PermGroupCommand$PermGroupAddCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */