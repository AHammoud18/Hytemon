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
/*    */ import java.util.UUID;
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
/*    */ class PermUserAddCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 77 */   private final RequiredArg<UUID> uuidArg = withRequiredArg("uuid", "server.commands.perm.user.add.uuid.desc", (ArgumentType)ArgTypes.UUID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 83 */   private final RequiredArg<List<String>> permissionsArg = withListRequiredArg("permissions", "server.commands.perm.user.add.permissions.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PermUserAddCommand() {
/* 89 */     super("add", "server.commands.perm.user.add.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 94 */     UUID uuid = (UUID)this.uuidArg.get(context);
/* 95 */     HashSet<String> permissions = new HashSet<>((Collection<? extends String>)this.permissionsArg.get(context));
/*    */     
/* 97 */     PermissionsModule.get().addUserPermission(uuid, permissions);
/* 98 */     context.sendMessage(Message.translation("server.commands.perm.permAddedToUser").param("uuid", uuid.toString()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\commands\PermUserCommand$PermUserAddCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */