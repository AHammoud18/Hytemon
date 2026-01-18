/*    */ package com.hypixel.hytale.server.core.permissions.commands;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.permissions.PermissionsModule;
/*    */ import com.hypixel.hytale.server.core.permissions.provider.PermissionProvider;
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
/*    */ class PermGroupListCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 42 */   private final RequiredArg<String> groupArg = withRequiredArg("group", "server.commands.perm.group.list.group.desc", (ArgumentType)ArgTypes.STRING);
/*    */   
/*    */   public PermGroupListCommand() {
/* 45 */     super("list", "server.commands.perm.group.list.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 50 */     String group = (String)this.groupArg.get(context);
/*    */     
/* 52 */     for (PermissionProvider permissionProvider : PermissionsModule.get().getProviders()) {
/* 53 */       Message header = Message.raw(permissionProvider.getName());
/*    */ 
/*    */       
/* 56 */       Set<Message> groupPermissions = (Set<Message>)permissionProvider.getGroupPermissions(group).stream().map(Message::raw).collect(Collectors.toSet());
/* 57 */       context.sendMessage(MessageFormat.list(header, groupPermissions));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\commands\PermGroupCommand$PermGroupListCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */