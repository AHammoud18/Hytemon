/*    */ package com.hypixel.hytale.builtin.teleport.commands.teleport;
/*    */ import com.hypixel.hytale.builtin.teleport.components.TeleportHistory;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class TeleportHomeCommand extends AbstractPlayerCommand {
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORTED_SELF_HOME = Message.translation("server.commands.teleport.teleportedSelfHome");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TeleportHomeCommand() {
/* 31 */     super("home", "server.commands.home.desc");
/* 32 */     requirePermission(HytalePermissions.fromCommand("teleport.home"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 37 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 38 */     assert transformComponent != null;
/*    */     
/* 40 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 41 */     assert headRotationComponent != null;
/*    */     
/* 43 */     Vector3d position = transformComponent.getPosition().clone();
/* 44 */     Vector3f headRotation = headRotationComponent.getRotation().clone();
/*    */     
/* 46 */     TeleportHistory teleportHistoryComponent = (TeleportHistory)store.ensureAndGetComponent(ref, TeleportHistory.getComponentType());
/* 47 */     teleportHistoryComponent.append(world, position, headRotation, "Home");
/*    */     
/* 49 */     Transform homeTransform = Player.getRespawnPosition(ref, world.getName(), (ComponentAccessor)store);
/* 50 */     store.addComponent(ref, Teleport.getComponentType(), (Component)new Teleport(null, homeTransform));
/* 51 */     context.sendMessage(MESSAGE_COMMANDS_TELEPORT_TELEPORTED_SELF_HOME);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\teleport\TeleportHomeCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */