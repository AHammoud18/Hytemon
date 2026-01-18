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
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class TeleportToPlayerCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/* 28 */   private static final Message MESSAGE_COMMANDS_ERRORS_TARGET_NOT_IN_WORLD = Message.translation("server.commands.errors.targetNotInWorld");
/* 29 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORTED_TO_PLAYER = Message.translation("server.commands.teleport.teleportedToPlayer");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 35 */   private final RequiredArg<PlayerRef> targetPlayerArg = withRequiredArg("targetPlayer", "server.commands.teleport.targetPlayer.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TeleportToPlayerCommand() {
/* 41 */     super("server.commands.teleport.toPlayer.desc");
/* 42 */     requirePermission(HytalePermissions.fromCommand("teleport.self"));
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 62 */     PlayerRef targetPlayerRef = (PlayerRef)this.targetPlayerArg.get(context);
/* 63 */     Ref<EntityStore> targetRef = targetPlayerRef.getReference();
/* 64 */     if (targetRef == null || !targetRef.isValid()) {
/* 65 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_TARGET_NOT_IN_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 70 */     Store<EntityStore> targetStore = targetRef.getStore();
/* 71 */     World targetWorld = ((EntityStore)targetStore.getExternalData()).getWorld();
/*    */     
/* 73 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 74 */     assert transformComponent != null;
/*    */     
/* 76 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 77 */     assert headRotationComponent != null;
/*    */     
/* 79 */     Vector3d pos = transformComponent.getPosition().clone();
/* 80 */     Vector3f rotation = headRotationComponent.getRotation().clone();
/*    */     
/* 82 */     targetWorld.execute(() -> {
/*    */           TransformComponent targetTransformComponent = (TransformComponent)targetStore.getComponent(targetRef, TransformComponent.getComponentType());
/*    */           assert targetTransformComponent != null;
/*    */           HeadRotation targetHeadRotationComponent = (HeadRotation)targetStore.getComponent(targetRef, HeadRotation.getComponentType());
/*    */           assert targetHeadRotationComponent != null;
/*    */           Vector3d targetPosition = targetTransformComponent.getPosition().clone();
/*    */           Vector3f targetRotation = targetTransformComponent.getRotation().clone();
/*    */           Vector3f targetHeadRotation = targetHeadRotationComponent.getRotation().clone();
/*    */           world.execute(());
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\teleport\variant\TeleportToPlayerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */