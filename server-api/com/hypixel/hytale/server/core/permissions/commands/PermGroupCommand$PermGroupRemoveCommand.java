/*     */ package com.hypixel.hytale.server.core.permissions.commands;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.permissions.PermissionsModule;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
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
/*     */ class PermGroupRemoveCommand
/*     */   extends CommandBase
/*     */ {
/*     */   @Nonnull
/*  99 */   private final RequiredArg<String> groupArg = withRequiredArg("group", "server.commands.perm.group.remove.group.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 105 */   private final RequiredArg<List<String>> permissionsArg = withListRequiredArg("permissions", "server.commands.perm.group.remove.permissions.desc", (ArgumentType)ArgTypes.STRING);
/*     */   
/*     */   public PermGroupRemoveCommand() {
/* 108 */     super("remove", "server.commands.perm.group.remove.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/* 113 */     String group = (String)this.groupArg.get(context);
/* 114 */     HashSet<String> permissions = new HashSet<>((Collection<? extends String>)this.permissionsArg.get(context));
/*     */     
/* 116 */     PermissionsModule.get().removeGroupPermission(group, permissions);
/* 117 */     context.sendMessage(Message.translation("server.commands.perm.permRemovedFromGroup")
/* 118 */         .param("group", group));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\commands\PermGroupCommand$PermGroupRemoveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */