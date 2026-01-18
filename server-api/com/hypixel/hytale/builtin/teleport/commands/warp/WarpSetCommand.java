/*    */ package com.hypixel.hytale.builtin.teleport.commands.warp;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.teleport.TeleportPlugin;
/*    */ import com.hypixel.hytale.builtin.teleport.Warp;
/*    */ import com.hypixel.hytale.component.AddReason;
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
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.time.Instant;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class WarpSetCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 30 */   private static final Message MESSAGE_COMMANDS_TELEPORT_WARP_NOT_LOADED = Message.translation("server.commands.teleport.warp.notLoaded");
/*    */   @Nonnull
/* 32 */   private static final Message MESSAGE_COMMANDS_TELEPORT_WARP_RESERVED_KEYWORD = Message.translation("server.commands.teleport.warp.reservedKeyword");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 38 */   private final RequiredArg<String> nameArg = withRequiredArg("name", "server.commands.warp.set.name.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WarpSetCommand() {
/* 44 */     super("set", "server.commands.warp.set.desc");
/* 45 */     requirePermission(HytalePermissions.fromCommand("warp.set"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 50 */     if (!TeleportPlugin.get().isWarpsLoaded()) {
/* 51 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_WARP_NOT_LOADED);
/*    */       
/*    */       return;
/*    */     } 
/* 55 */     Map<String, Warp> warps = TeleportPlugin.get().getWarps();
/* 56 */     String newId = ((String)this.nameArg.get(context)).toLowerCase();
/*    */     
/* 58 */     if ("reload".equals(newId) || "remove".equals(newId) || "set".equals(newId) || "list".equals(newId) || "go".equals(newId)) {
/* 59 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_WARP_RESERVED_KEYWORD);
/*    */       
/*    */       return;
/*    */     } 
/* 63 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 64 */     assert transformComponent != null;
/*    */     
/* 66 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 67 */     assert headRotationComponent != null;
/*    */     
/* 69 */     Vector3d position = transformComponent.getPosition();
/* 70 */     Vector3f rotation = transformComponent.getRotation();
/* 71 */     Vector3f headRotation = headRotationComponent.getRotation();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 76 */     Warp newWarp = new Warp(position.getX(), position.getY(), position.getZ(), headRotation.getYaw(), rotation.getPitch(), rotation.getRoll(), newId, world, playerRef.getUsername(), Instant.now());
/*    */     
/* 78 */     warps.put(newWarp.getId().toLowerCase(), newWarp);
/*    */     
/* 80 */     TeleportPlugin plugin = TeleportPlugin.get();
/* 81 */     plugin.saveWarps();
/*    */     
/* 83 */     store.addEntity(plugin.createWarp(newWarp, store), AddReason.LOAD);
/*    */     
/* 85 */     context.sendMessage(Message.translation("server.commands.teleport.warp.setWarp")
/* 86 */         .param("name", newWarp.getId()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\warp\WarpSetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */