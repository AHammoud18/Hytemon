/*     */ package com.hypixel.hytale.server.core.permissions.commands;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.permissions.PermissionsModule;
/*     */ import com.hypixel.hytale.server.core.permissions.provider.PermissionProvider;
/*     */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PermGroupCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   public PermGroupCommand() {
/*  27 */     super("group", "server.commands.perm.group.desc");
/*  28 */     addSubCommand((AbstractCommand)new PermGroupListCommand());
/*  29 */     addSubCommand((AbstractCommand)new PermGroupAddCommand());
/*  30 */     addSubCommand((AbstractCommand)new PermGroupRemoveCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class PermGroupListCommand
/*     */     extends CommandBase
/*     */   {
/*     */     @Nonnull
/*  42 */     private final RequiredArg<String> groupArg = withRequiredArg("group", "server.commands.perm.group.list.group.desc", (ArgumentType)ArgTypes.STRING);
/*     */     
/*     */     public PermGroupListCommand() {
/*  45 */       super("list", "server.commands.perm.group.list.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/*  50 */       String group = (String)this.groupArg.get(context);
/*     */       
/*  52 */       for (PermissionProvider permissionProvider : PermissionsModule.get().getProviders()) {
/*  53 */         Message header = Message.raw(permissionProvider.getName());
/*     */ 
/*     */         
/*  56 */         Set<Message> groupPermissions = (Set<Message>)permissionProvider.getGroupPermissions(group).stream().map(Message::raw).collect(Collectors.toSet());
/*  57 */         context.sendMessage(MessageFormat.list(header, groupPermissions));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class PermGroupAddCommand
/*     */     extends CommandBase
/*     */   {
/*     */     @Nonnull
/*  71 */     private final RequiredArg<String> groupArg = withRequiredArg("group", "server.commands.perm.group.add.group.desc", (ArgumentType)ArgTypes.STRING);
/*     */     
/*     */     @Nonnull
/*  74 */     private final RequiredArg<List<String>> permissionsArg = withListRequiredArg("permissions", "server.commands.perm.group.add.permissions.desc", (ArgumentType)ArgTypes.STRING);
/*     */     
/*     */     public PermGroupAddCommand() {
/*  77 */       super("add", "server.commands.perm.group.add.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/*  82 */       String group = (String)this.groupArg.get(context);
/*  83 */       HashSet<String> permissions = new HashSet<>((Collection<? extends String>)this.permissionsArg.get(context));
/*     */       
/*  85 */       PermissionsModule.get().addGroupPermission(group, permissions);
/*  86 */       context.sendMessage(Message.translation("server.commands.perm.addPermToGroup").param("group", group));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class PermGroupRemoveCommand
/*     */     extends CommandBase
/*     */   {
/*     */     @Nonnull
/*  99 */     private final RequiredArg<String> groupArg = withRequiredArg("group", "server.commands.perm.group.remove.group.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 105 */     private final RequiredArg<List<String>> permissionsArg = withListRequiredArg("permissions", "server.commands.perm.group.remove.permissions.desc", (ArgumentType)ArgTypes.STRING);
/*     */     
/*     */     public PermGroupRemoveCommand() {
/* 108 */       super("remove", "server.commands.perm.group.remove.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/* 113 */       String group = (String)this.groupArg.get(context);
/* 114 */       HashSet<String> permissions = new HashSet<>((Collection<? extends String>)this.permissionsArg.get(context));
/*     */       
/* 116 */       PermissionsModule.get().removeGroupPermission(group, permissions);
/* 117 */       context.sendMessage(Message.translation("server.commands.perm.permRemovedFromGroup")
/* 118 */           .param("group", group));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\commands\PermGroupCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */