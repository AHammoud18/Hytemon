/*    */ package com.hypixel.hytale.builtin.teleport.commands.teleport;
/*    */ 
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
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class TeleportWorldCommand
/*    */   extends AbstractPlayerCommand {
/* 29 */   private static final Message MESSAGE_WORLD_NOT_FOUND = Message.translation("server.world.notFound");
/* 30 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/* 31 */   private static final Message MESSAGE_WORLD_SPAWN_NOT_SET = Message.translation("server.world.spawn.notSet");
/* 32 */   private static final Message MESSAGE_COMMANDS_TELEPORT_TELEPORTED_TO_WORLD = Message.translation("server.commands.teleport.teleportedToWorld");
/*    */   
/*    */   @Nonnull
/* 35 */   private final RequiredArg<String> worldNameArg = withRequiredArg("worldName", "server.commands.worldport.worldName.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TeleportWorldCommand() {
/* 41 */     super("world", "server.commands.worldport.desc");
/* 42 */     setPermissionGroup(null);
/* 43 */     requirePermission(HytalePermissions.fromCommand("teleport.world"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 48 */     String worldName = (String)this.worldNameArg.get(context);
/*    */ 
/*    */     
/* 51 */     World targetWorld = Universe.get().getWorld(worldName);
/* 52 */     if (targetWorld == null) {
/* 53 */       context.sendMessage(MESSAGE_WORLD_NOT_FOUND.param("worldName", worldName));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 58 */     Transform spawnPoint = targetWorld.getWorldConfig().getSpawnProvider().getSpawnPoint(ref, (ComponentAccessor)store);
/* 59 */     if (spawnPoint == null) {
/* 60 */       context.sendMessage(MESSAGE_WORLD_SPAWN_NOT_SET.param("worldName", worldName));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 65 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 66 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/*    */     
/* 68 */     if (transformComponent != null && headRotationComponent != null) {
/* 69 */       Vector3d previousPos = transformComponent.getPosition().clone();
/* 70 */       Vector3f previousRotation = headRotationComponent.getRotation().clone();
/*    */       
/* 72 */       TeleportHistory teleportHistoryComponent = (TeleportHistory)store.ensureAndGetComponent(ref, TeleportHistory.getComponentType());
/* 73 */       teleportHistoryComponent.append(world, previousPos, previousRotation, "World " + targetWorld
/* 74 */           .getName());
/*    */     } 
/*    */     
/* 77 */     store.addComponent(ref, Teleport.getComponentType(), (Component)new Teleport(targetWorld, spawnPoint));
/*    */     
/* 79 */     Vector3d spawnPos = spawnPoint.getPosition();
/* 80 */     context.sendMessage(MESSAGE_COMMANDS_TELEPORT_TELEPORTED_TO_WORLD
/* 81 */         .param("worldName", worldName)
/* 82 */         .param("x", spawnPos.getX())
/* 83 */         .param("y", spawnPos.getY())
/* 84 */         .param("z", spawnPos.getZ()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\teleport\TeleportWorldCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */