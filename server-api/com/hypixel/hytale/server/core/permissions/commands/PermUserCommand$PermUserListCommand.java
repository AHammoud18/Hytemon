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
/*    */ import java.util.UUID;
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
/*    */ class PermUserListCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 45 */   private final RequiredArg<UUID> uuidArg = withRequiredArg("uuid", "server.commands.perm.user.list.uuid.desc", (ArgumentType)ArgTypes.UUID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PermUserListCommand() {
/* 51 */     super("list", "server.commands.perm.user.list.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 56 */     UUID uuid = (UUID)this.uuidArg.get(context);
/*    */     
/* 58 */     for (PermissionProvider permissionProvider : PermissionsModule.get().getProviders()) {
/* 59 */       Message header = Message.raw(permissionProvider.getName());
/*    */ 
/*    */       
/* 62 */       Set<Message> userPermissions = (Set<Message>)permissionProvider.getUserPermissions(uuid).stream().map(Message::raw).collect(Collectors.toSet());
/* 63 */       context.sendMessage(MessageFormat.list(header, userPermissions));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\commands\PermUserCommand$PermUserListCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */