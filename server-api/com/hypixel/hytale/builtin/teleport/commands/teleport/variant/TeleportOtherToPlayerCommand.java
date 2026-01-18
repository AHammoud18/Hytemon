/*    */ package com.hypixel.hytale.builtin.teleport.commands.teleport.variant;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.teleport.components.TeleportHistory;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class TeleportOtherToPlayerCommand
/*    */   extends CommandBase {
/* 26 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/* 27 */   private static final Message MESSAGE_COMMANDS_ERRORS_TARGET_NOT_IN_WORLD = Message.translation("server.commands.errors.targetNotInWorld");
/* 28 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORTED_OTHER_TO_PLAYER = Message.translation("server.commands.teleport.teleportedOtherToPlayer");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 34 */   private final RequiredArg<PlayerRef> targetPlayerArg = withRequiredArg("targetPlayer", "server.commands.teleport.targetPlayer.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 40 */   private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TeleportOtherToPlayerCommand() {
/* 46 */     super("server.commands.teleport.otherToPlayer.desc");
/* 47 */     requirePermission(HytalePermissions.fromCommand("teleport.other"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 59 */     PlayerRef playerToTpRef = (PlayerRef)this.playerArg.get(context);
/* 60 */     Ref<EntityStore> sourceRef = playerToTpRef.getReference();
/*    */ 
/*    */     
/* 63 */     if (sourceRef == null || !sourceRef.isValid()) {
/* 64 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 69 */     PlayerRef targetPlayerRef = (PlayerRef)this.targetPlayerArg.get(context);
/* 70 */     Ref<EntityStore> targetRef = targetPlayerRef.getReference();
/* 71 */     if (targetRef == null || !targetRef.isValid()) {
/* 72 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_TARGET_NOT_IN_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 77 */     Store<EntityStore> sourceStore = sourceRef.getStore();
/* 78 */     World sourceWorld = ((EntityStore)sourceStore.getExternalData()).getWorld();
/* 79 */     Store<EntityStore> targetStore = targetRef.getStore();
/* 80 */     World targetWorld = ((EntityStore)targetStore.getExternalData()).getWorld();
/*    */     
/* 82 */     sourceWorld.execute(() -> {
/*    */           TransformComponent transformComponent = (TransformComponent)sourceStore.getComponent(sourceRef, TransformComponent.getComponentType());
/*    */           assert transformComponent != null;
/*    */           HeadRotation headRotationComponent = (HeadRotation)sourceStore.getComponent(sourceRef, HeadRotation.getComponentType());
/*    */           assert headRotationComponent != null;
/*    */           Vector3d pos = transformComponent.getPosition().clone();
/*    */           Vector3f rotation = headRotationComponent.getRotation().clone();
/*    */           targetWorld.execute(());
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\teleport\variant\TeleportOtherToPlayerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */