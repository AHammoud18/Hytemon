/*    */ package com.hypixel.hytale.server.core.permissions.commands.op;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*    */ import com.hypixel.hytale.server.core.permissions.PermissionsModule;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class OpRemoveCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_OP_REMOVED = Message.translation("server.commands.op.removed");
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_OP_REMOVED_TARGET = Message.translation("server.commands.op.removed.target");
/*    */   @Nonnull
/* 26 */   private static final Message MESSAGE_COMMANDS_OP_NOT_OP = Message.translation("server.commands.op.notOp");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 32 */   private final RequiredArg<UUID> playerArg = withRequiredArg("player", "server.commands.op.remove.player.desc", (ArgumentType)ArgTypes.PLAYER_UUID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OpRemoveCommand() {
/* 38 */     super("remove", "server.commands.op.remove.desc");
/* 39 */     requirePermission(HytalePermissions.fromCommand("op.remove"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 44 */     UUID uuid = (UUID)this.playerArg.get(context);
/* 45 */     PermissionsModule permissionsModule = PermissionsModule.get();
/* 46 */     String opGroup = "OP";
/*    */ 
/*    */     
/* 49 */     String rawInput = context.getInput((Argument)this.playerArg)[0];
/* 50 */     Message displayMessage = Message.raw(rawInput).bold(true);
/*    */     
/* 52 */     Set<String> groups = permissionsModule.getGroupsForUser(uuid);
/* 53 */     if (groups.contains("OP")) {
/* 54 */       permissionsModule.removeUserFromGroup(uuid, "OP");
/* 55 */       context.sendMessage(MESSAGE_COMMANDS_OP_REMOVED
/* 56 */           .param("username", displayMessage));
/*    */ 
/*    */       
/* 59 */       PlayerRef oppedPlayerRef = Universe.get().getPlayer(uuid);
/* 60 */       if (oppedPlayerRef != null) {
/* 61 */         oppedPlayerRef.sendMessage(MESSAGE_COMMANDS_OP_REMOVED_TARGET);
/*    */       }
/*    */     } else {
/* 64 */       context.sendMessage(MESSAGE_COMMANDS_OP_NOT_OP
/* 65 */           .param("username", displayMessage));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\commands\op\OpRemoveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */