/*    */ package com.hypixel.hytale.server.core.permissions.commands.op;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Constants;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.modules.singleplayer.SingleplayerModule;
/*    */ import com.hypixel.hytale.server.core.permissions.PermissionsModule;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OpSelfCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/* 25 */   private static final Message MESSAGE_COMMANDS_OP_ADDED = Message.translation("server.commands.op.self.added");
/* 26 */   private static final Message MESSAGE_COMMANDS_OP_REMOVED = Message.translation("server.commands.op.self.removed");
/* 27 */   private static final Message MESSAGE_COMMANDS_NON_VANILLA_PERMISSIONS = Message.translation("server.commands.op.self.nonVanillaPermissions");
/* 28 */   private static final Message MESSAGE_COMMANDS_SINGLEPLAYER_OWNER_REQ = Message.translation("server.commands.op.self.singleplayerOwnerReq");
/* 29 */   private static final Message MESSAGE_COMMANDS_MULTIPLAYER_TIP = Message.translation("server.commands.op.self.multiplayerTip");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OpSelfCommand() {
/* 35 */     super("self", "server.commands.op.self.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canGeneratePermission() {
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@NonNullDecl CommandContext context, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
/* 45 */     if (PermissionsModule.get().areProvidersTampered()) {
/* 46 */       playerRef.sendMessage(MESSAGE_COMMANDS_NON_VANILLA_PERMISSIONS);
/*    */       
/*    */       return;
/*    */     } 
/* 50 */     if (Constants.SINGLEPLAYER && !SingleplayerModule.isOwner(playerRef)) {
/* 51 */       playerRef.sendMessage(MESSAGE_COMMANDS_SINGLEPLAYER_OWNER_REQ);
/*    */       
/*    */       return;
/*    */     } 
/* 55 */     if (!Constants.SINGLEPLAYER && !Constants.ALLOWS_SELF_OP_COMMAND) {
/* 56 */       playerRef.sendMessage(MESSAGE_COMMANDS_MULTIPLAYER_TIP
/* 57 */           .param("uuidCommand", "uuid")
/* 58 */           .param("permissionFile", "permissions.json")
/* 59 */           .param("launchArg", "--allow-op"));
/*    */       
/*    */       return;
/*    */     } 
/* 63 */     UUID uuid = playerRef.getUuid();
/*    */     
/* 65 */     PermissionsModule perms = PermissionsModule.get();
/* 66 */     String opGroup = "OP";
/* 67 */     Set<String> groups = perms.getGroupsForUser(uuid);
/*    */     
/* 69 */     if (groups.contains("OP")) {
/* 70 */       perms.removeUserFromGroup(uuid, "OP");
/* 71 */       context.sendMessage(MESSAGE_COMMANDS_OP_REMOVED);
/*    */     } else {
/* 73 */       perms.addUserToGroup(uuid, "OP");
/* 74 */       context.sendMessage(MESSAGE_COMMANDS_OP_ADDED);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\commands\op\OpSelfCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */