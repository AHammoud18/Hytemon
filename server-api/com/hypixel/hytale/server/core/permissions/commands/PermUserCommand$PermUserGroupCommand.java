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
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PermUserGroupCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   public PermUserGroupCommand() {
/* 141 */     super("group", "server.commands.perm.user.group.desc");
/* 142 */     addSubCommand((AbstractCommand)new PermUserGroupListCommand());
/* 143 */     addSubCommand((AbstractCommand)new PermUserGroupAddCommand());
/* 144 */     addSubCommand((AbstractCommand)new PermUserGroupRemoveCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class PermUserGroupListCommand
/*     */     extends CommandBase
/*     */   {
/*     */     @Nonnull
/* 156 */     private final RequiredArg<UUID> uuidArg = withRequiredArg("uuid", "server.commands.perm.user.group.list.uuid.desc", (ArgumentType)ArgTypes.UUID);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PermUserGroupListCommand() {
/* 162 */       super("list", "server.commands.perm.user.group.list.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/* 167 */       UUID uuid = (UUID)this.uuidArg.get(context);
/*     */       
/* 169 */       for (PermissionProvider permissionProvider : PermissionsModule.get().getProviders()) {
/* 170 */         Message header = Message.raw(permissionProvider.getName());
/*     */ 
/*     */         
/* 173 */         Set<Message> groups = (Set<Message>)permissionProvider.getGroupsForUser(uuid).stream().map(Message::raw).collect(Collectors.toSet());
/* 174 */         context.sendMessage(MessageFormat.list(header, groups));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class PermUserGroupAddCommand
/*     */     extends CommandBase
/*     */   {
/*     */     @Nonnull
/* 188 */     private final RequiredArg<UUID> uuidArg = withRequiredArg("uuid", "server.commands.perm.user.group.add.uuid.desc", (ArgumentType)ArgTypes.UUID);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 194 */     private final RequiredArg<String> groupArg = withRequiredArg("group", "server.commands.perm.user.group.add.group.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PermUserGroupAddCommand() {
/* 200 */       super("add", "server.commands.perm.user.group.add.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/* 205 */       UUID uuid = (UUID)this.uuidArg.get(context);
/* 206 */       String group = (String)this.groupArg.get(context);
/*     */       
/* 208 */       PermissionsModule.get().addUserToGroup(uuid, group);
/* 209 */       context.sendMessage(Message.translation("server.commands.perm.userAddedToGroup")
/* 210 */           .param("uuid", uuid.toString())
/* 211 */           .param("group", group));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class PermUserGroupRemoveCommand
/*     */     extends CommandBase
/*     */   {
/*     */     @Nonnull
/* 224 */     private final RequiredArg<UUID> uuidArg = withRequiredArg("uuid", "server.commands.perm.user.group.remove.uuid.desc", (ArgumentType)ArgTypes.UUID);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 230 */     private final RequiredArg<String> groupArg = withRequiredArg("group", "server.commands.perm.user.group.remove.group.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PermUserGroupRemoveCommand() {
/* 236 */       super("remove", "server.commands.perm.user.group.remove.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/* 241 */       UUID uuid = (UUID)this.uuidArg.get(context);
/* 242 */       String group = (String)this.groupArg.get(context);
/*     */       
/* 244 */       PermissionsModule.get().removeUserFromGroup(uuid, group);
/* 245 */       context.sendMessage(Message.translation("server.commands.perm.userRemovedFromGroup")
/* 246 */           .param("uuid", uuid.toString())
/* 247 */           .param("group", group));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\commands\PermUserCommand$PermUserGroupCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */