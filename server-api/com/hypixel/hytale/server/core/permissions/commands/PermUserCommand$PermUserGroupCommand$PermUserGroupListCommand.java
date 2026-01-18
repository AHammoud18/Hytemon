/*     */ package com.hypixel.hytale.server.core.permissions.commands;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
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
/*     */ class PermUserGroupListCommand
/*     */   extends CommandBase
/*     */ {
/*     */   @Nonnull
/* 156 */   private final RequiredArg<UUID> uuidArg = withRequiredArg("uuid", "server.commands.perm.user.group.list.uuid.desc", (ArgumentType)ArgTypes.UUID);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PermUserGroupListCommand() {
/* 162 */     super("list", "server.commands.perm.user.group.list.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/* 167 */     UUID uuid = (UUID)this.uuidArg.get(context);
/*     */     
/* 169 */     for (PermissionProvider permissionProvider : PermissionsModule.get().getProviders()) {
/* 170 */       Message header = Message.raw(permissionProvider.getName());
/*     */ 
/*     */       
/* 173 */       Set<Message> groups = (Set<Message>)permissionProvider.getGroupsForUser(uuid).stream().map(Message::raw).collect(Collectors.toSet());
/* 174 */       context.sendMessage(MessageFormat.list(header, groups));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\commands\PermUserCommand$PermUserGroupCommand$PermUserGroupListCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */